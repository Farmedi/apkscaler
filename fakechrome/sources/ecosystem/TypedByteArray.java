package ecosystem;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Objects;

public class TypedByteArray implements TypedInput, TypedOutput {
    private final byte[] bytes;
    private final String mimeType;

    public TypedByteArray(String str, byte[] bArr) {
        str = str == null ? "application/unknown" : str;
        Objects.requireNonNull(bArr, "bytes");
        this.mimeType = str;
        this.bytes = bArr;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TypedByteArray typedByteArray = (TypedByteArray) obj;
        return Arrays.equals(this.bytes, typedByteArray.bytes) && this.mimeType.equals(typedByteArray.mimeType);
    }

    public String fileName() {
        return null;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public int hashCode() {
        return (this.mimeType.hashCode() * 31) + Arrays.hashCode(this.bytes);
    }

    public InputStream in() throws IOException {
        return new ByteArrayInputStream(this.bytes);
    }

    public long length() {
        return (long) this.bytes.length;
    }

    public String mimeType() {
        return this.mimeType;
    }

    public String toString() {
        return "TypedByteArray[length=" + length() + "]";
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(this.bytes);
    }
}
