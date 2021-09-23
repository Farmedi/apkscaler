package okhttp3;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;

public abstract class ResponseBody implements Closeable {
    @Nullable
    private Reader reader;

    public abstract long contentLength();

    @Nullable
    public abstract MediaType contentType();

    public abstract BufferedSource source();

    public final InputStream byteStream() {
        return source().inputStream();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0062, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0066, code lost:
        if (r2 != null) goto L_0x0068;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0068, code lost:
        $closeResource(r3, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006b, code lost:
        throw r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final byte[] bytes() throws java.io.IOException {
        /*
        // Method dump skipped, instructions count: 108
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.ResponseBody.bytes():byte[]");
    }

    private static /* synthetic */ void $closeResource(Throwable th, AutoCloseable autoCloseable) {
        if (th != null) {
            try {
                autoCloseable.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
        } else {
            autoCloseable.close();
        }
    }

    public final Reader charStream() {
        Reader reader2 = this.reader;
        if (reader2 != null) {
            return reader2;
        }
        BomAwareReader bomAwareReader = new BomAwareReader(source(), charset());
        this.reader = bomAwareReader;
        return bomAwareReader;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001b, code lost:
        if (r0 != null) goto L_0x001d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001d, code lost:
        $closeResource(r1, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0020, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0017, code lost:
        r2 = move-exception;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String string() throws java.io.IOException {
        /*
            r3 = this;
            okio.BufferedSource r0 = r3.source()
            r1 = 0
            java.nio.charset.Charset r2 = r3.charset()     // Catch:{ Throwable -> 0x0019 }
            java.nio.charset.Charset r2 = okhttp3.internal.Util.bomAwareCharset(r0, r2)     // Catch:{ Throwable -> 0x0019 }
            java.lang.String r2 = r0.readString(r2)     // Catch:{ Throwable -> 0x0019 }
            if (r0 == 0) goto L_0x0016
            $closeResource(r1, r0)
        L_0x0016:
            return r2
        L_0x0017:
            r2 = move-exception
            goto L_0x001b
        L_0x0019:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0017 }
        L_0x001b:
            if (r0 == 0) goto L_0x0020
            $closeResource(r1, r0)
        L_0x0020:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.ResponseBody.string():java.lang.String");
    }

    private Charset charset() {
        MediaType contentType = contentType();
        return contentType != null ? contentType.charset(StandardCharsets.UTF_8) : StandardCharsets.UTF_8;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        Util.closeQuietly(source());
    }

    public static ResponseBody create(@Nullable MediaType mediaType, String str) {
        Charset charset = StandardCharsets.UTF_8;
        if (mediaType != null && (charset = mediaType.charset()) == null) {
            charset = StandardCharsets.UTF_8;
            mediaType = MediaType.parse(mediaType + "; charset=utf-8");
        }
        Buffer writeString = new Buffer().writeString(str, charset);
        return create(mediaType, writeString.size(), writeString);
    }

    public static ResponseBody create(@Nullable MediaType mediaType, byte[] bArr) {
        return create(mediaType, (long) bArr.length, new Buffer().write(bArr));
    }

    public static ResponseBody create(@Nullable MediaType mediaType, ByteString byteString) {
        return create(mediaType, (long) byteString.size(), new Buffer().write(byteString));
    }

    public static ResponseBody create(@Nullable final MediaType mediaType, final long j, final BufferedSource bufferedSource) {
        if (bufferedSource != null) {
            return new ResponseBody() {
                /* class okhttp3.ResponseBody.AnonymousClass1 */

                @Override // okhttp3.ResponseBody
                @Nullable
                public MediaType contentType() {
                    return MediaType.this;
                }

                @Override // okhttp3.ResponseBody
                public long contentLength() {
                    return j;
                }

                @Override // okhttp3.ResponseBody
                public BufferedSource source() {
                    return bufferedSource;
                }
            };
        }
        throw new NullPointerException("source == null");
    }

    static final class BomAwareReader extends Reader {
        private final Charset charset;
        private boolean closed;
        @Nullable
        private Reader delegate;
        private final BufferedSource source;

        BomAwareReader(BufferedSource bufferedSource, Charset charset2) {
            this.source = bufferedSource;
            this.charset = charset2;
        }

        @Override // java.io.Reader
        public int read(char[] cArr, int i, int i2) throws IOException {
            if (this.closed) {
                throw new IOException("Stream closed");
            }
            Reader reader = this.delegate;
            if (reader == null) {
                InputStreamReader inputStreamReader = new InputStreamReader(this.source.inputStream(), Util.bomAwareCharset(this.source, this.charset));
                this.delegate = inputStreamReader;
                reader = inputStreamReader;
            }
            return reader.read(cArr, i, i2);
        }

        @Override // java.io.Closeable, java.io.Reader, java.lang.AutoCloseable
        public void close() throws IOException {
            this.closed = true;
            if (this.delegate != null) {
                this.delegate.close();
            } else {
                this.source.close();
            }
        }
    }
}
