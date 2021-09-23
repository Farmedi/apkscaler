package okhttp3.internal.http;

import java.io.IOException;
import java.net.ProtocolException;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.connection.StreamAllocation;
import okhttp3.internal.duplex.DuplexRequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public final class CallServerInterceptor implements Interceptor {
    private final boolean forWebSocket;

    public CallServerInterceptor(boolean z) {
        this.forWebSocket = z;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response response;
        RealInterceptorChain realInterceptorChain = (RealInterceptorChain) chain;
        Call call = realInterceptorChain.call();
        HttpCodec httpStream = realInterceptorChain.httpStream();
        StreamAllocation streamAllocation = realInterceptorChain.streamAllocation();
        RealConnection realConnection = (RealConnection) realInterceptorChain.connection();
        Request request = realInterceptorChain.request();
        long currentTimeMillis = System.currentTimeMillis();
        realInterceptorChain.eventListener().requestHeadersStart(call);
        httpStream.writeRequestHeaders(request);
        realInterceptorChain.eventListener().requestHeadersEnd(call, request);
        Response.Builder builder = null;
        if (HttpMethod.permitsRequestBody(request.method()) && request.body() != null) {
            if ("100-continue".equalsIgnoreCase(request.header("Expect"))) {
                httpStream.flushRequest();
                realInterceptorChain.eventListener().responseHeadersStart(call);
                builder = httpStream.readResponseHeaders(true);
            }
            if (builder == null) {
                if (request.body() instanceof DuplexRequestBody) {
                    httpStream.flushRequest();
                    request.body().writeTo(Okio.buffer(new CountingSink(httpStream.createRequestBody(request, -1))));
                } else {
                    realInterceptorChain.eventListener().requestBodyStart(call);
                    CountingSink countingSink = new CountingSink(httpStream.createRequestBody(request, request.body().contentLength()));
                    BufferedSink buffer = Okio.buffer(countingSink);
                    request.body().writeTo(buffer);
                    buffer.close();
                    realInterceptorChain.eventListener().requestBodyEnd(call, countingSink.successfulCount);
                }
            } else if (!realConnection.isMultiplexed()) {
                streamAllocation.noNewStreams();
            }
        }
        if (!(request.body() instanceof DuplexRequestBody)) {
            httpStream.finishRequest();
        }
        if (builder == null) {
            realInterceptorChain.eventListener().responseHeadersStart(call);
            builder = httpStream.readResponseHeaders(false);
        }
        builder.request(request).handshake(streamAllocation.connection().handshake()).sentRequestAtMillis(currentTimeMillis).receivedResponseAtMillis(System.currentTimeMillis());
        Internal.instance.initCodec(builder, httpStream);
        Response build = builder.build();
        int code = build.code();
        if (code == 100) {
            Response.Builder readResponseHeaders = httpStream.readResponseHeaders(false);
            readResponseHeaders.request(request).handshake(streamAllocation.connection().handshake()).sentRequestAtMillis(currentTimeMillis).receivedResponseAtMillis(System.currentTimeMillis());
            Internal.instance.initCodec(readResponseHeaders, httpStream);
            build = readResponseHeaders.build();
            code = build.code();
        }
        realInterceptorChain.eventListener().responseHeadersEnd(call, build);
        if (!this.forWebSocket || code != 101) {
            response = build.newBuilder().body(httpStream.openResponseBody(build)).build();
        } else {
            response = build.newBuilder().body(Util.EMPTY_RESPONSE).build();
        }
        if ("close".equalsIgnoreCase(response.request().header("Connection")) || "close".equalsIgnoreCase(response.header("Connection"))) {
            streamAllocation.noNewStreams();
        }
        if ((code != 204 && code != 205) || response.body().contentLength() <= 0) {
            return response;
        }
        throw new ProtocolException("HTTP " + code + " had non-zero Content-Length: " + response.body().contentLength());
    }

    static final class CountingSink extends ForwardingSink {
        long successfulCount;

        CountingSink(Sink sink) {
            super(sink);
        }

        @Override // okio.ForwardingSink, okio.Sink
        public void write(Buffer buffer, long j) throws IOException {
            super.write(buffer, j);
            this.successfulCount += j;
        }
    }
}
