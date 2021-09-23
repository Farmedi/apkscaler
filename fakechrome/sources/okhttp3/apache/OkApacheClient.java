package okhttp3.apache;

import java.io.IOException;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.RequestLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public final class OkApacheClient implements HttpClient {
    private OkHttpClient client;
    private final HttpParams params;

    private static Request transformRequest(HttpRequest httpRequest) {
        Request.Builder builder = new Request.Builder();
        RequestLine requestLine = httpRequest.getRequestLine();
        String method = requestLine.getMethod();
        builder.url(requestLine.getUri());
        Header[] allHeaders = httpRequest.getAllHeaders();
        RequestBody requestBody = null;
        String str = null;
        for (Header header : allHeaders) {
            String name = header.getName();
            if ("Content-Type".equalsIgnoreCase(name)) {
                str = header.getValue();
            } else {
                builder.header(name, header.getValue());
            }
        }
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            HttpEntity entity = ((HttpEntityEnclosingRequest) httpRequest).getEntity();
            if (entity != null) {
                requestBody = new HttpEntityBody(entity, str);
                Header contentEncoding = entity.getContentEncoding();
                if (contentEncoding != null) {
                    builder.header(contentEncoding.getName(), contentEncoding.getValue());
                }
            } else {
                requestBody = Util.EMPTY_REQUEST;
            }
        }
        builder.method(method, requestBody);
        return builder.build();
    }

    private static HttpResponse transformResponse(Response response) {
        BasicHttpResponse basicHttpResponse = new BasicHttpResponse(HttpVersion.HTTP_1_1, response.code(), response.message());
        ResponseBody body = response.body();
        InputStreamEntity inputStreamEntity = new InputStreamEntity(body.byteStream(), body.contentLength());
        basicHttpResponse.setEntity(inputStreamEntity);
        Headers headers = response.headers();
        int size = headers.size();
        for (int i = 0; i < size; i++) {
            String name = headers.name(i);
            String value = headers.value(i);
            basicHttpResponse.addHeader(name, value);
            if ("Content-Type".equalsIgnoreCase(name)) {
                inputStreamEntity.setContentType(value);
            } else if ("Content-Encoding".equalsIgnoreCase(name)) {
                inputStreamEntity.setContentEncoding(value);
            }
        }
        return basicHttpResponse;
    }

    public OkApacheClient() {
        this(new OkHttpClient());
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [okhttp3.apache.OkApacheClient$1, org.apache.http.params.HttpParams] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public OkApacheClient(okhttp3.OkHttpClient r2) {
        /*
            r1 = this;
            r1.<init>()
            okhttp3.apache.OkApacheClient$1 r0 = new okhttp3.apache.OkApacheClient$1
            r0.<init>()
            r1.params = r0
            r1.client = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.apache.OkApacheClient.<init>(okhttp3.OkHttpClient):void");
    }

    public HttpParams getParams() {
        return this.params;
    }

    public ClientConnectionManager getConnectionManager() {
        throw new UnsupportedOperationException();
    }

    public HttpResponse execute(HttpUriRequest httpUriRequest) throws IOException {
        return execute((HttpHost) null, (HttpRequest) httpUriRequest, (HttpContext) null);
    }

    public HttpResponse execute(HttpUriRequest httpUriRequest, HttpContext httpContext) throws IOException {
        return execute((HttpHost) null, (HttpRequest) httpUriRequest, httpContext);
    }

    public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest) throws IOException {
        return execute(httpHost, httpRequest, (HttpContext) null);
    }

    public HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws IOException {
        return transformResponse(this.client.newCall(transformRequest(httpRequest)).execute());
    }

    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler) throws IOException {
        return (T) execute(null, httpUriRequest, responseHandler, null);
    }

    public <T> T execute(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws IOException {
        return (T) execute(null, httpUriRequest, responseHandler, httpContext);
    }

    public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler) throws IOException {
        return (T) execute(httpHost, httpRequest, responseHandler, null);
    }

    public <T> T execute(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws IOException {
        HttpResponse execute = execute(httpHost, httpRequest, httpContext);
        try {
            return (T) responseHandler.handleResponse(execute);
        } finally {
            consumeContentQuietly(execute);
        }
    }

    private static void consumeContentQuietly(HttpResponse httpResponse) {
        try {
            httpResponse.getEntity().consumeContent();
        } catch (Throwable unused) {
        }
    }
}
