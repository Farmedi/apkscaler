package okhttp3;

import java.util.Comparator;

final /* synthetic */ class CipherSuite$$Lambda$0 implements Comparator {
    static final Comparator $instance = new CipherSuite$$Lambda$0();

    private CipherSuite$$Lambda$0() {
    }

    @Override // java.util.Comparator
    public int compare(Object obj, Object obj2) {
        return CipherSuite.lambda$static$0$CipherSuite((String) obj, (String) obj2);
    }
}
