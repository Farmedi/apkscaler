package okhttp3.internal;

import java.util.concurrent.ThreadFactory;

/* access modifiers changed from: package-private */
public final /* synthetic */ class Util$$Lambda$0 implements ThreadFactory {
    private final String arg$1;
    private final boolean arg$2;

    Util$$Lambda$0(String str, boolean z) {
        this.arg$1 = str;
        this.arg$2 = z;
    }

    public Thread newThread(Runnable runnable) {
        return Util.lambda$threadFactory$0$Util(this.arg$1, this.arg$2, runnable);
    }
}
