package okhttp3.apache;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import org.apache.http.HttpEntity;

final class HttpEntityBody extends RequestBody {
    private static final MediaType DEFAULT_MEDIA_TYPE = MediaType.get("application/octet-stream");
    private final HttpEntity entity;
    private final MediaType mediaType;

    HttpEntityBody(HttpEntity httpEntity, String str) {
        this.entity = httpEntity;
        if (str != null) {
            this.mediaType = MediaType.parse(str);
        } else if (httpEntity.getContentType() != null) {
            this.mediaType = MediaType.parse(httpEntity.getContentType().getValue());
        } else {
            this.mediaType = DEFAULT_MEDIA_TYPE;
        }
    }

    @Override // okhttp3.RequestBody
    public long contentLength() {
        return this.entity.getContentLength();
    }

    @Override // okhttp3.RequestBody
    public MediaType contentType() {
        return this.mediaType;
    }

    @Override // okhttp3.RequestBody
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        this.entity.writeTo(bufferedSink.outputStream());
    }
}
