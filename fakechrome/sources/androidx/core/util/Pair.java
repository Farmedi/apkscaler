package androidx.core.util;

import androidx.annotation.NonNull;

public class Pair<F, S> {
    public final F first;
    public final S second;

    public Pair(F f, S s) {
        this.first = f;
        this.second = s;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) obj;
        if (!ObjectsCompat.equals(pair.first, this.first) || !ObjectsCompat.equals(pair.second, this.second)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = this.first == null ? 0 : this.first.hashCode();
        if (this.second != null) {
            i = this.second.hashCode();
        }
        return hashCode ^ i;
    }

    @NonNull
    public String toString() {
        return "Pair{" + ((Object) this.first) + " " + ((Object) this.second) + "}";
    }

    @NonNull
    public static <A, B> Pair<A, B> create(A a, B b) {
        return new Pair<>(a, b);
    }
}
