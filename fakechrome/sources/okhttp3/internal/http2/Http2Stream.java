package okhttp3.internal.http2;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import javax.annotation.Nullable;
import okhttp3.Headers;
import okhttp3.internal.Util;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class Http2Stream {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    long bytesLeftInWriteWindow;
    final Http2Connection connection;
    ErrorCode errorCode = null;
    private boolean hasResponseHeaders;
    private final Deque<Headers> headersQueue = new ArrayDeque();
    final int id;
    final StreamTimeout readTimeout = new StreamTimeout();
    final FramingSink sink;
    private final FramingSource source;
    long unacknowledgedBytesRead = 0;
    final StreamTimeout writeTimeout = new StreamTimeout();

    Http2Stream(int i, Http2Connection http2Connection, boolean z, boolean z2, @Nullable Headers headers) {
        if (http2Connection == null) {
            throw new NullPointerException("connection == null");
        }
        this.id = i;
        this.connection = http2Connection;
        this.bytesLeftInWriteWindow = (long) http2Connection.peerSettings.getInitialWindowSize();
        this.source = new FramingSource((long) http2Connection.okHttpSettings.getInitialWindowSize());
        this.sink = new FramingSink();
        this.source.finished = z2;
        this.sink.finished = z;
        if (headers != null) {
            this.headersQueue.add(headers);
        }
        if (isLocallyInitiated() && headers != null) {
            throw new IllegalStateException("locally-initiated streams shouldn't have headers yet");
        } else if (!isLocallyInitiated() && headers == null) {
            throw new IllegalStateException("remotely-initiated streams should have headers");
        }
    }

    public int getId() {
        return this.id;
    }

    public synchronized boolean isOpen() {
        if (this.errorCode != null) {
            return false;
        }
        if ((this.source.finished || this.source.closed) && ((this.sink.finished || this.sink.closed) && this.hasResponseHeaders)) {
            return false;
        }
        return true;
    }

    public boolean isLocallyInitiated() {
        if (this.connection.client == ((this.id & 1) == 1)) {
            return true;
        }
        return false;
    }

    public Http2Connection getConnection() {
        return this.connection;
    }

    /* JADX INFO: finally extract failed */
    public synchronized Headers takeHeaders() throws IOException {
        this.readTimeout.enter();
        while (this.headersQueue.isEmpty() && this.errorCode == null) {
            try {
                waitForIo();
            } catch (Throwable th) {
                this.readTimeout.exitAndThrowIfTimedOut();
                throw th;
            }
        }
        this.readTimeout.exitAndThrowIfTimedOut();
        if (!this.headersQueue.isEmpty()) {
        } else {
            throw new StreamResetException(this.errorCode);
        }
        return this.headersQueue.removeFirst();
    }

    public synchronized Headers trailers() throws IOException {
        if (this.errorCode != null) {
            throw new StreamResetException(this.errorCode);
        }
        if (this.source.finished && this.source.receiveBuffer.exhausted()) {
            if (!this.source.readBuffer.exhausted()) {
            }
        }
        throw new IllegalStateException("too early; can't read the trailers yet");
        return this.source.trailers != null ? this.source.trailers : Util.EMPTY_HEADERS;
    }

    public synchronized ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public void writeHeaders(List<Header> list, boolean z, boolean z2) throws IOException {
        if (list == null) {
            throw new NullPointerException("headers == null");
        }
        synchronized (this) {
            this.hasResponseHeaders = true;
            if (z) {
                this.sink.finished = true;
            }
        }
        if (!z2) {
            synchronized (this.connection) {
                z2 = this.connection.bytesLeftInWriteWindow == 0;
            }
        }
        this.connection.writeHeaders(this.id, z, list);
        if (z2) {
            this.connection.flush();
        }
    }

    public void enqueueTrailers(Headers headers) {
        synchronized (this) {
            if (this.sink.finished) {
                throw new IllegalStateException("already finished");
            } else if (headers.size() == 0) {
                throw new IllegalArgumentException("trailers.size() == 0");
            } else {
                this.sink.trailers = headers;
            }
        }
    }

    public Timeout readTimeout() {
        return this.readTimeout;
    }

    public Timeout writeTimeout() {
        return this.writeTimeout;
    }

    public Source getSource() {
        return this.source;
    }

    public Sink getSink() {
        synchronized (this) {
            if (!this.hasResponseHeaders && !isLocallyInitiated()) {
                throw new IllegalStateException("reply before requesting the sink");
            }
        }
        return this.sink;
    }

    public void close(ErrorCode errorCode2) throws IOException {
        if (closeInternal(errorCode2)) {
            this.connection.writeSynReset(this.id, errorCode2);
        }
    }

    public void closeLater(ErrorCode errorCode2) {
        if (closeInternal(errorCode2)) {
            this.connection.writeSynResetLater(this.id, errorCode2);
        }
    }

    private boolean closeInternal(ErrorCode errorCode2) {
        synchronized (this) {
            if (this.errorCode != null) {
                return false;
            }
            if (this.source.finished && this.sink.finished) {
                return false;
            }
            this.errorCode = errorCode2;
            notifyAll();
            this.connection.removeStream(this.id);
            return true;
        }
    }

    /* access modifiers changed from: package-private */
    public void receiveData(BufferedSource bufferedSource, int i) throws IOException {
        this.source.receive(bufferedSource, (long) i);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0018  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void receiveHeaders(okhttp3.Headers r3, boolean r4) {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.hasResponseHeaders     // Catch:{ all -> 0x002e }
            r1 = 1
            if (r0 == 0) goto L_0x000f
            if (r4 != 0) goto L_0x0009
            goto L_0x000f
        L_0x0009:
            okhttp3.internal.http2.Http2Stream$FramingSource r0 = r2.source     // Catch:{ all -> 0x002e }
            okhttp3.internal.http2.Http2Stream.FramingSource.access$202(r0, r3)     // Catch:{ all -> 0x002e }
            goto L_0x0016
        L_0x000f:
            r2.hasResponseHeaders = r1     // Catch:{ all -> 0x002e }
            java.util.Deque<okhttp3.Headers> r0 = r2.headersQueue     // Catch:{ all -> 0x002e }
            r0.add(r3)     // Catch:{ all -> 0x002e }
        L_0x0016:
            if (r4 == 0) goto L_0x001c
            okhttp3.internal.http2.Http2Stream$FramingSource r3 = r2.source     // Catch:{ all -> 0x002e }
            r3.finished = r1     // Catch:{ all -> 0x002e }
        L_0x001c:
            boolean r3 = r2.isOpen()     // Catch:{ all -> 0x002e }
            r2.notifyAll()     // Catch:{ all -> 0x002e }
            monitor-exit(r2)     // Catch:{ all -> 0x002e }
            if (r3 != 0) goto L_0x002d
            okhttp3.internal.http2.Http2Connection r3 = r2.connection
            int r4 = r2.id
            r3.removeStream(r4)
        L_0x002d:
            return
        L_0x002e:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Stream.receiveHeaders(okhttp3.Headers, boolean):void");
    }

    /* access modifiers changed from: package-private */
    public synchronized void receiveRstStream(ErrorCode errorCode2) {
        if (this.errorCode == null) {
            this.errorCode = errorCode2;
            notifyAll();
        }
    }

    /* access modifiers changed from: private */
    public final class FramingSource implements Source {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        boolean closed;
        boolean finished;
        private final long maxByteCount;
        private final Buffer readBuffer = new Buffer();
        private final Buffer receiveBuffer = new Buffer();
        private Headers trailers;

        FramingSource(long j) {
            this.maxByteCount = j;
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j) throws IOException {
            ErrorCode errorCode;
            long read;
            if (j < 0) {
                throw new IllegalArgumentException("byteCount < 0: " + j);
            }
            while (true) {
                errorCode = null;
                synchronized (Http2Stream.this) {
                    Http2Stream.this.readTimeout.enter();
                    try {
                        if (Http2Stream.this.errorCode != null) {
                            errorCode = Http2Stream.this.errorCode;
                        }
                        if (this.closed) {
                            throw new IOException("stream closed");
                        } else if (this.readBuffer.size() > 0) {
                            read = this.readBuffer.read(buffer, Math.min(j, this.readBuffer.size()));
                            Http2Stream.this.unacknowledgedBytesRead += read;
                            if (errorCode == null && Http2Stream.this.unacknowledgedBytesRead >= ((long) (Http2Stream.this.connection.okHttpSettings.getInitialWindowSize() / 2))) {
                                Http2Stream.this.connection.writeWindowUpdateLater(Http2Stream.this.id, Http2Stream.this.unacknowledgedBytesRead);
                                Http2Stream.this.unacknowledgedBytesRead = 0;
                            }
                        } else if (this.finished || errorCode != null) {
                            read = -1;
                        } else {
                            Http2Stream.this.waitForIo();
                        }
                    } finally {
                        Http2Stream.this.readTimeout.exitAndThrowIfTimedOut();
                    }
                }
            }
            read = -1;
            Http2Stream.this.readTimeout.exitAndThrowIfTimedOut();
            if (read != -1) {
                updateConnectionFlowControl(read);
                return read;
            } else if (errorCode == null) {
                return -1;
            } else {
                throw new StreamResetException(errorCode);
            }
        }

        private void updateConnectionFlowControl(long j) {
            Http2Stream.this.connection.updateConnectionFlowControl(j);
        }

        /* access modifiers changed from: package-private */
        public void receive(BufferedSource bufferedSource, long j) throws IOException {
            boolean z;
            boolean z2;
            boolean z3;
            while (j > 0) {
                synchronized (Http2Stream.this) {
                    z = this.finished;
                    z2 = false;
                    z3 = this.readBuffer.size() + j > this.maxByteCount;
                }
                if (z3) {
                    bufferedSource.skip(j);
                    Http2Stream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                    return;
                } else if (z) {
                    bufferedSource.skip(j);
                    return;
                } else {
                    long read = bufferedSource.read(this.receiveBuffer, j);
                    if (read == -1) {
                        throw new EOFException();
                    }
                    j -= read;
                    synchronized (Http2Stream.this) {
                        if (this.readBuffer.size() == 0) {
                            z2 = true;
                        }
                        this.readBuffer.writeAll(this.receiveBuffer);
                        if (z2) {
                            Http2Stream.this.notifyAll();
                        }
                    }
                }
            }
        }

        @Override // okio.Source
        public Timeout timeout() {
            return Http2Stream.this.readTimeout;
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            long size;
            synchronized (Http2Stream.this) {
                this.closed = true;
                size = this.readBuffer.size();
                this.readBuffer.clear();
                Http2Stream.this.notifyAll();
            }
            if (size > 0) {
                updateConnectionFlowControl(size);
            }
            Http2Stream.this.cancelStreamIfNecessary();
        }
    }

    /* access modifiers changed from: package-private */
    public void cancelStreamIfNecessary() throws IOException {
        boolean z;
        boolean isOpen;
        synchronized (this) {
            z = !this.source.finished && this.source.closed && (this.sink.finished || this.sink.closed);
            isOpen = isOpen();
        }
        if (z) {
            close(ErrorCode.CANCEL);
        } else if (!isOpen) {
            this.connection.removeStream(this.id);
        }
    }

    /* access modifiers changed from: package-private */
    public final class FramingSink implements Sink {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long EMIT_BUFFER_SIZE = 16384;
        boolean closed;
        boolean finished;
        private final Buffer sendBuffer = new Buffer();
        private Headers trailers;

        FramingSink() {
        }

        @Override // okio.Sink
        public void write(Buffer buffer, long j) throws IOException {
            this.sendBuffer.write(buffer, j);
            while (this.sendBuffer.size() >= EMIT_BUFFER_SIZE) {
                emitFrame($assertionsDisabled);
            }
        }

        /* JADX INFO: finally extract failed */
        private void emitFrame(boolean z) throws IOException {
            long min;
            boolean z2;
            synchronized (Http2Stream.this) {
                Http2Stream.this.writeTimeout.enter();
                while (Http2Stream.this.bytesLeftInWriteWindow <= 0 && !this.finished && !this.closed && Http2Stream.this.errorCode == null) {
                    try {
                        Http2Stream.this.waitForIo();
                    } catch (Throwable th) {
                        Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
                        throw th;
                    }
                }
                Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
                Http2Stream.this.checkOutNotClosed();
                min = Math.min(Http2Stream.this.bytesLeftInWriteWindow, this.sendBuffer.size());
                Http2Stream.this.bytesLeftInWriteWindow -= min;
            }
            Http2Stream.this.writeTimeout.enter();
            if (z) {
                try {
                    if (min == this.sendBuffer.size()) {
                        z2 = true;
                        Http2Stream.this.connection.writeData(Http2Stream.this.id, z2, this.sendBuffer, min);
                        Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
                    }
                } catch (Throwable th2) {
                    Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
                    throw th2;
                }
            }
            z2 = $assertionsDisabled;
            Http2Stream.this.connection.writeData(Http2Stream.this.id, z2, this.sendBuffer, min);
            Http2Stream.this.writeTimeout.exitAndThrowIfTimedOut();
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            synchronized (Http2Stream.this) {
                Http2Stream.this.checkOutNotClosed();
            }
            while (this.sendBuffer.size() > 0) {
                emitFrame($assertionsDisabled);
                Http2Stream.this.connection.flush();
            }
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return Http2Stream.this.writeTimeout;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x001e, code lost:
            if (r8.sendBuffer.size() <= 0) goto L_0x0022;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0020, code lost:
            r0 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0022, code lost:
            r0 = okhttp3.internal.http2.Http2Stream.FramingSink.$assertionsDisabled;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x0025, code lost:
            if (r8.trailers == null) goto L_0x0029;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0027, code lost:
            r3 = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0029, code lost:
            r3 = okhttp3.internal.http2.Http2Stream.FramingSink.$assertionsDisabled;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x002a, code lost:
            if (r3 == false) goto L_0x004c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0034, code lost:
            if (r8.sendBuffer.size() <= 0) goto L_0x003a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0036, code lost:
            emitFrame(okhttp3.internal.http2.Http2Stream.FramingSink.$assertionsDisabled);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x003a, code lost:
            r8.this$0.connection.writeHeaders(r8.this$0.id, true, okhttp3.internal.Util.toHeaderBlock(r8.trailers));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x004c, code lost:
            if (r0 == false) goto L_0x005c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0056, code lost:
            if (r8.sendBuffer.size() <= 0) goto L_0x006b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0058, code lost:
            emitFrame(true);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x005c, code lost:
            r8.this$0.connection.writeData(r8.this$0.id, true, null, 0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x006b, code lost:
            r2 = r8.this$0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x006d, code lost:
            monitor-enter(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
            r8.closed = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x0070, code lost:
            monitor-exit(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0071, code lost:
            r8.this$0.connection.flush();
            r8.this$0.cancelStreamIfNecessary();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:34:0x007d, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0011, code lost:
            if (r8.this$0.sink.finished != false) goto L_0x006b;
         */
        @Override // java.io.Closeable, java.lang.AutoCloseable, okio.Sink
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void close() throws java.io.IOException {
            /*
            // Method dump skipped, instructions count: 132
            */
            throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http2.Http2Stream.FramingSink.close():void");
        }
    }

    /* access modifiers changed from: package-private */
    public void addBytesToWriteWindow(long j) {
        this.bytesLeftInWriteWindow += j;
        if (j > 0) {
            notifyAll();
        }
    }

    /* access modifiers changed from: package-private */
    public void checkOutNotClosed() throws IOException {
        if (this.sink.closed) {
            throw new IOException("stream closed");
        } else if (this.sink.finished) {
            throw new IOException("stream finished");
        } else if (this.errorCode != null) {
            throw new StreamResetException(this.errorCode);
        }
    }

    /* access modifiers changed from: package-private */
    public void waitForIo() throws InterruptedIOException {
        try {
            wait();
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
            throw new InterruptedIOException();
        }
    }

    /* access modifiers changed from: package-private */
    public class StreamTimeout extends AsyncTimeout {
        StreamTimeout() {
        }

        /* access modifiers changed from: protected */
        @Override // okio.AsyncTimeout
        public void timedOut() {
            Http2Stream.this.closeLater(ErrorCode.CANCEL);
        }

        /* access modifiers changed from: protected */
        @Override // okio.AsyncTimeout
        public IOException newTimeoutException(IOException iOException) {
            SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
            if (iOException != null) {
                socketTimeoutException.initCause(iOException);
            }
            return socketTimeoutException;
        }

        public void exitAndThrowIfTimedOut() throws IOException {
            if (exit()) {
                throw newTimeoutException(null);
            }
        }
    }
}
