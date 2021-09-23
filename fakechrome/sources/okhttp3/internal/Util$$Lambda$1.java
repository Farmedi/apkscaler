package okhttp3.internal;

import java.util.Comparator;

final /* synthetic */ class Util$$Lambda$1 implements Comparator {
    static final Comparator $instance = new Util$$Lambda$1();

    private Util$$Lambda$1() {
    }

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        return ((String) obj).compareTo((String) obj2);
    }
}
