package decorate.angel.admission;

import java.io.IOException;
import java.io.OutputStream;

public interface TypedOutput {
    String fileName();

    long length();

    String mimeType();

    void writeTo(OutputStream outputStream) throws IOException;
}
