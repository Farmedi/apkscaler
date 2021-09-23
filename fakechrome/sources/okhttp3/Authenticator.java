package okhttp3;

import java.io.IOException;
import javax.annotation.Nullable;

public interface Authenticator {
    public static final Authenticator NONE = Authenticator$$Lambda$0.$instance;

    @Nullable
    Request authenticate(@Nullable Route route, Response response) throws IOException;
}
