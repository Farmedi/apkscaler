package commission;

import java.util.Objects;

public final class Preconditions {
    /* renamed from: ˊ  reason: contains not printable characters */
    public static void m8(boolean z, Object obj) {
        if (!z) {
            throw new IllegalArgumentException(String.valueOf(obj));
        }
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    public static <T> T m9(T t) {
        Objects.requireNonNull(t);
        return t;
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    public static <T> T m10(T t, Object obj) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(String.valueOf(obj));
    }
}
