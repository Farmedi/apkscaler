package okio;

import java.io.IOException;

final class PeekSource implements Source {
    private final Buffer buffer;
    private boolean closed;
    private int expectedPos;
    private Segment expectedSegment = this.buffer.head;
    private long pos;
    private final BufferedSource upstream;

    PeekSource(BufferedSource bufferedSource) {
        this.upstream = bufferedSource;
        this.buffer = bufferedSource.buffer();
        this.expectedPos = this.expectedSegment != null ? this.expectedSegment.pos : -1;
    }

    @Override // okio.Source
    public long read(Buffer buffer2, long j) throws IOException {
        if (this.closed) {
            throw new IllegalStateException("closed");
        } else if (this.expectedSegment == null || (this.expectedSegment == this.buffer.head && this.expectedPos == this.buffer.head.pos)) {
            this.upstream.request(this.pos + j);
            if (this.expectedSegment == null && this.buffer.head != null) {
                this.expectedSegment = this.buffer.head;
                this.expectedPos = this.buffer.head.pos;
            }
            long min = Math.min(j, this.buffer.size - this.pos);
            if (min <= 0) {
                return -1;
            }
            this.buffer.copyTo(buffer2, this.pos, min);
            this.pos += min;
            return min;
        } else {
            throw new IllegalStateException("Peek source is invalid because upstream source was used");
        }
    }

    @Override // okio.Source
    public Timeout timeout() {
        return this.upstream.timeout();
    }

    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.closed = true;
    }
}
