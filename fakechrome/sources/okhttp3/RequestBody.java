package okhttp3;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.ByteString;

public abstract class RequestBody {
    public long contentLength() throws IOException {
        return -1;
    }

    @Nullable
    public abstract MediaType contentType();

    public abstract void writeTo(BufferedSink bufferedSink) throws IOException;

    public static RequestBody create(@Nullable MediaType mediaType, String str) {
        Charset charset = StandardCharsets.UTF_8;
        if (mediaType != null && (charset = mediaType.charset()) == null) {
            charset = StandardCharsets.UTF_8;
            mediaType = MediaType.parse(mediaType + "; charset=utf-8");
        }
        return create(mediaType, str.getBytes(charset));
    }

    public static RequestBody create(@Nullable final MediaType mediaType, final ByteString byteString) {
        return new RequestBody() {
            /* class okhttp3.RequestBody.AnonymousClass1 */

            @Override // okhttp3.RequestBody
            @Nullable
            public MediaType contentType() {
                return MediaType.this;
            }

            @Override // okhttp3.RequestBody
            public long contentLength() throws IOException {
                return (long) byteString.size();
            }

            @Override // okhttp3.RequestBody
            public void writeTo(BufferedSink bufferedSink) throws IOException {
                bufferedSink.write(byteString);
            }
        };
    }

    public static RequestBody create(@Nullable MediaType mediaType, byte[] bArr) {
        return create(mediaType, bArr, 0, bArr.length);
    }

    public static RequestBody create(@Nullable final MediaType mediaType, final byte[] bArr, final int i, final int i2) {
        if (bArr == null) {
            throw new NullPointerException("content == null");
        }
        Util.checkOffsetAndCount((long) bArr.length, (long) i, (long) i2);
        return new RequestBody() {
            /* class okhttp3.RequestBody.AnonymousClass2 */

            @Override // okhttp3.RequestBody
            @Nullable
            public MediaType contentType() {
                return MediaType.this;
            }

            @Override // okhttp3.RequestBody
            public long contentLength() {
                return (long) i2;
            }

            @Override // okhttp3.RequestBody
            public void writeTo(BufferedSink bufferedSink) throws IOException {
                bufferedSink.write(bArr, i, i2);
            }
        };
    }

    public static RequestBody create(@Nullable final MediaType mediaType, final File file) {
        if (file != null) {
            return new RequestBody() {
                /* class okhttp3.RequestBody.AnonymousClass3 */

                @Override // okhttp3.RequestBody
                @Nullable
                public MediaType contentType() {
                    return MediaType.this;
                }

                @Override // okhttp3.RequestBody
                public long contentLength() {
                    return file.length();
                }

                /* JADX WARNING: Code restructure failed: missing block: B:10:0x0014, code lost:
                    r3 = th;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:5:0x000f, code lost:
                    r3 = th;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:6:0x0010, code lost:
                    r1 = null;
                 */
                @Override // okhttp3.RequestBody
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void writeTo(okio.BufferedSink r3) throws java.io.IOException {
                    /*
                        r2 = this;
                        java.io.File r0 = r2
                        okio.Source r0 = okio.Okio.source(r0)
                        r3.writeAll(r0)     // Catch:{ Throwable -> 0x0012, all -> 0x000f }
                        if (r0 == 0) goto L_0x000e
                        r0.close()
                    L_0x000e:
                        return
                    L_0x000f:
                        r3 = move-exception
                        r1 = 0
                        goto L_0x0015
                    L_0x0012:
                        r1 = move-exception
                        throw r1     // Catch:{ all -> 0x0014 }
                    L_0x0014:
                        r3 = move-exception
                    L_0x0015:
                        if (r0 == 0) goto L_0x0025
                        if (r1 == 0) goto L_0x0022
                        r0.close()     // Catch:{ Throwable -> 0x001d }
                        goto L_0x0025
                    L_0x001d:
                        r0 = move-exception
                        r1.addSuppressed(r0)
                        goto L_0x0025
                    L_0x0022:
                        r0.close()
                    L_0x0025:
                        throw r3
                    */
                    throw new UnsupportedOperationException("Method not decompiled: okhttp3.RequestBody.AnonymousClass3.writeTo(okio.BufferedSink):void");
                }
            };
        }
        throw new NullPointerException("file == null");
    }
}
