package okio;

import java.io.IOException;
import javax.annotation.Nullable;

public final class Pipe {
    final Buffer buffer = new Buffer();
    @Nullable
    private Sink foldedSink;
    final long maxBufferSize;
    private final Sink sink = new PipeSink();
    boolean sinkClosed;
    private final Source source = new PipeSource();
    boolean sourceClosed;

    public Pipe(long j) {
        if (j < 1) {
            throw new IllegalArgumentException("maxBufferSize < 1: " + j);
        }
        this.maxBufferSize = j;
    }

    public final Source source() {
        return this.source;
    }

    public final Sink sink() {
        return this.sink;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003b, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003e, code lost:
        monitor-enter(r6.buffer);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r6.sourceClosed = true;
        r6.buffer.notifyAll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x004b, code lost:
        throw r7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void fold(okio.Sink r7) throws java.io.IOException {
        /*
            r6 = this;
        L_0x0000:
            okio.Buffer r0 = r6.buffer
            monitor-enter(r0)
            okio.Sink r1 = r6.foldedSink     // Catch:{ all -> 0x004c }
            if (r1 == 0) goto L_0x000f
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException     // Catch:{ all -> 0x004c }
            java.lang.String r1 = "sink already folded"
            r7.<init>(r1)     // Catch:{ all -> 0x004c }
            throw r7     // Catch:{ all -> 0x004c }
        L_0x000f:
            okio.Buffer r1 = r6.buffer     // Catch:{ all -> 0x004c }
            boolean r1 = r1.exhausted()     // Catch:{ all -> 0x004c }
            r2 = 1
            if (r1 == 0) goto L_0x001e
            r6.sourceClosed = r2     // Catch:{ all -> 0x004c }
            r6.foldedSink = r7     // Catch:{ all -> 0x004c }
            monitor-exit(r0)     // Catch:{ all -> 0x004c }
            return
        L_0x001e:
            okio.Buffer r1 = new okio.Buffer     // Catch:{ all -> 0x004c }
            r1.<init>()     // Catch:{ all -> 0x004c }
            okio.Buffer r3 = r6.buffer     // Catch:{ all -> 0x004c }
            okio.Buffer r4 = r6.buffer     // Catch:{ all -> 0x004c }
            long r4 = r4.size     // Catch:{ all -> 0x004c }
            r1.write(r3, r4)     // Catch:{ all -> 0x004c }
            okio.Buffer r3 = r6.buffer     // Catch:{ all -> 0x004c }
            r3.notifyAll()     // Catch:{ all -> 0x004c }
            monitor-exit(r0)     // Catch:{ all -> 0x004c }
            long r3 = r1.size     // Catch:{ all -> 0x003b }
            r7.write(r1, r3)     // Catch:{ all -> 0x003b }
            r7.flush()     // Catch:{ all -> 0x003b }
            goto L_0x0000
        L_0x003b:
            r7 = move-exception
            okio.Buffer r1 = r6.buffer
            monitor-enter(r1)
            r6.sourceClosed = r2     // Catch:{ all -> 0x0048 }
            okio.Buffer r0 = r6.buffer     // Catch:{ all -> 0x0048 }
            r0.notifyAll()     // Catch:{ all -> 0x0048 }
            monitor-exit(r1)     // Catch:{ all -> 0x0048 }
            goto L_0x004b
        L_0x0048:
            r7 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0048 }
            throw r7
        L_0x004b:
            throw r7
        L_0x004c:
            r7 = move-exception
            monitor-exit(r0)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Pipe.fold(okio.Sink):void");
    }

    final class PipeSink implements Sink {
        final PushableTimeout timeout = new PushableTimeout();

        PipeSink() {
        }

        @Override // okio.Sink
        public void write(Buffer buffer, long j) throws IOException {
            Sink sink;
            synchronized (Pipe.this.buffer) {
                if (!Pipe.this.sinkClosed) {
                    while (true) {
                        if (j <= 0) {
                            sink = null;
                            break;
                        } else if (Pipe.this.foldedSink != null) {
                            sink = Pipe.this.foldedSink;
                            break;
                        } else if (Pipe.this.sourceClosed) {
                            throw new IOException("source is closed");
                        } else {
                            long size = Pipe.this.maxBufferSize - Pipe.this.buffer.size();
                            if (size == 0) {
                                this.timeout.waitUntilNotified(Pipe.this.buffer);
                            } else {
                                long min = Math.min(size, j);
                                Pipe.this.buffer.write(buffer, min);
                                j -= min;
                                Pipe.this.buffer.notifyAll();
                            }
                        }
                    }
                } else {
                    throw new IllegalStateException("closed");
                }
            }
            if (sink != null) {
                this.timeout.push(sink.timeout());
                try {
                    sink.write(buffer, j);
                } finally {
                    this.timeout.pop();
                }
            }
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            Sink sink;
            synchronized (Pipe.this.buffer) {
                if (Pipe.this.sinkClosed) {
                    throw new IllegalStateException("closed");
                } else if (Pipe.this.foldedSink != null) {
                    sink = Pipe.this.foldedSink;
                } else if (!Pipe.this.sourceClosed || Pipe.this.buffer.size() <= 0) {
                    sink = null;
                } else {
                    throw new IOException("source is closed");
                }
            }
            if (sink != null) {
                this.timeout.push(sink.timeout());
                try {
                    sink.flush();
                } finally {
                    this.timeout.pop();
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0046, code lost:
            if (r1 == null) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0048, code lost:
            r5.timeout.push(r1.timeout());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            r1.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x005a, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x005b, code lost:
            r5.timeout.pop();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0060, code lost:
            throw r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
            return;
         */
        @Override // java.io.Closeable, java.lang.AutoCloseable, okio.Sink
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void close() throws java.io.IOException {
            /*
            // Method dump skipped, instructions count: 101
            */
            throw new UnsupportedOperationException("Method not decompiled: okio.Pipe.PipeSink.close():void");
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return this.timeout;
        }
    }

    final class PipeSource implements Source {
        final Timeout timeout = new Timeout();

        PipeSource() {
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j) throws IOException {
            synchronized (Pipe.this.buffer) {
                if (Pipe.this.sourceClosed) {
                    throw new IllegalStateException("closed");
                }
                while (Pipe.this.buffer.size() == 0) {
                    if (Pipe.this.sinkClosed) {
                        return -1;
                    }
                    this.timeout.waitUntilNotified(Pipe.this.buffer);
                }
                long read = Pipe.this.buffer.read(buffer, j);
                Pipe.this.buffer.notifyAll();
                return read;
            }
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            synchronized (Pipe.this.buffer) {
                Pipe.this.sourceClosed = true;
                Pipe.this.buffer.notifyAll();
            }
        }

        @Override // okio.Source
        public Timeout timeout() {
            return this.timeout;
        }
    }
}
