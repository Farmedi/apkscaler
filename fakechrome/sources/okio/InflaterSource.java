package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public final class InflaterSource implements Source {
    private int bufferBytesHeldByInflater;
    private boolean closed;
    private final Inflater inflater;
    private final BufferedSource source;

    public InflaterSource(Source source2, Inflater inflater2) {
        this(Okio.buffer(source2), inflater2);
    }

    InflaterSource(BufferedSource bufferedSource, Inflater inflater2) {
        if (bufferedSource == null) {
            throw new IllegalArgumentException("source == null");
        } else if (inflater2 == null) {
            throw new IllegalArgumentException("inflater == null");
        } else {
            this.source = bufferedSource;
            this.inflater = inflater2;
        }
    }

    @Override // okio.Source
    public long read(Buffer buffer, long j) throws IOException {
        boolean refill;
        int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
        if (i < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        } else if (this.closed) {
            throw new IllegalStateException("closed");
        } else if (i == 0) {
            return 0;
        } else {
            do {
                refill = refill();
                try {
                    Segment writableSegment = buffer.writableSegment(1);
                    int inflate = this.inflater.inflate(writableSegment.data, writableSegment.limit, (int) Math.min(j, (long) (8192 - writableSegment.limit)));
                    if (inflate > 0) {
                        writableSegment.limit += inflate;
                        long j2 = (long) inflate;
                        buffer.size += j2;
                        return j2;
                    }
                    if (!this.inflater.finished()) {
                        if (this.inflater.needsDictionary()) {
                        }
                    }
                    releaseInflatedBytes();
                    if (writableSegment.pos != writableSegment.limit) {
                        return -1;
                    }
                    buffer.head = writableSegment.pop();
                    SegmentPool.recycle(writableSegment);
                    return -1;
                } catch (DataFormatException e) {
                    throw new IOException(e);
                }
            } while (!refill);
            throw new EOFException("source exhausted prematurely");
        }
    }

    public final boolean refill() throws IOException {
        if (!this.inflater.needsInput()) {
            return false;
        }
        releaseInflatedBytes();
        if (this.inflater.getRemaining() != 0) {
            throw new IllegalStateException("?");
        } else if (this.source.exhausted()) {
            return true;
        } else {
            Segment segment = this.source.buffer().head;
            this.bufferBytesHeldByInflater = segment.limit - segment.pos;
            this.inflater.setInput(segment.data, segment.pos, this.bufferBytesHeldByInflater);
            return false;
        }
    }

    private void releaseInflatedBytes() throws IOException {
        if (this.bufferBytesHeldByInflater != 0) {
            int remaining = this.bufferBytesHeldByInflater - this.inflater.getRemaining();
            this.bufferBytesHeldByInflater -= remaining;
            this.source.skip((long) remaining);
        }
    }

    @Override // okio.Source
    public Timeout timeout() {
        return this.source.timeout();
    }

    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (!this.closed) {
            this.inflater.end();
            this.closed = true;
            this.source.close();
        }
    }
}