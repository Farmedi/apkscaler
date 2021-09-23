package decorate;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public interface LottieFetchResult extends Closeable {
    String contentType();

    /* renamed from: ˁ  reason: contains not printable characters */
    InputStream m19() throws IOException;

    /* renamed from: І  reason: contains not printable characters */
    boolean m20();

    /* renamed from: ᵙ  reason: contains not printable characters */
    String m21();
}
