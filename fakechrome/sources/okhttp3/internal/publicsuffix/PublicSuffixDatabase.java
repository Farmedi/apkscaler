package okhttp3.internal.publicsuffix;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.IDN;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import okhttp3.internal.platform.Platform;

public final class PublicSuffixDatabase {
    private static final String[] EMPTY_RULE = new String[0];
    private static final byte EXCEPTION_MARKER = 33;
    private static final String[] PREVAILING_RULE = {"*"};
    public static final String PUBLIC_SUFFIX_RESOURCE = "publicsuffixes.gz";
    private static final byte[] WILDCARD_LABEL = {42};
    private static final PublicSuffixDatabase instance = new PublicSuffixDatabase();
    private final AtomicBoolean listRead = new AtomicBoolean(false);
    private byte[] publicSuffixExceptionListBytes;
    private byte[] publicSuffixListBytes;
    private final CountDownLatch readCompleteLatch = new CountDownLatch(1);

    public static PublicSuffixDatabase get() {
        return instance;
    }

    public String getEffectiveTldPlusOne(String str) {
        int i;
        if (str == null) {
            throw new NullPointerException("domain == null");
        }
        String[] split = IDN.toUnicode(str).split("\\.");
        String[] findMatchingRule = findMatchingRule(split);
        if (split.length == findMatchingRule.length && findMatchingRule[0].charAt(0) != '!') {
            return null;
        }
        if (findMatchingRule[0].charAt(0) == '!') {
            i = split.length - findMatchingRule.length;
        } else {
            i = split.length - (findMatchingRule.length + 1);
        }
        StringBuilder sb = new StringBuilder();
        String[] split2 = str.split("\\.");
        while (i < split2.length) {
            sb.append(split2[i]);
            sb.append('.');
            i++;
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x0078 A[LOOP:3: B:37:0x0078->B:41:0x0085, LOOP_START, PHI: r1 
      PHI: (r1v3 int) = (r1v0 int), (r1v4 int) binds: [B:36:0x0076, B:41:0x0085] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00a3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String[] findMatchingRule(java.lang.String[] r8) {
        /*
        // Method dump skipped, instructions count: 202
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.publicsuffix.PublicSuffixDatabase.findMatchingRule(java.lang.String[]):java.lang.String[]");
    }

    private static String binarySearchBytes(byte[] bArr, byte[][] bArr2, int i) {
        int i2;
        int i3;
        int i4;
        int length = bArr.length;
        int i5 = 0;
        while (i5 < length) {
            int i6 = (i5 + length) / 2;
            while (i6 > -1 && bArr[i6] != 10) {
                i6--;
            }
            int i7 = i6 + 1;
            int i8 = 1;
            while (true) {
                i2 = i7 + i8;
                if (bArr[i2] == 10) {
                    break;
                }
                i8++;
            }
            int i9 = i2 - i7;
            int i10 = i;
            boolean z = false;
            int i11 = 0;
            int i12 = 0;
            while (true) {
                if (z) {
                    z = false;
                    i3 = 46;
                } else {
                    i3 = bArr2[i10][i11] & 255;
                }
                i4 = i3 - (bArr[i7 + i12] & 255);
                if (i4 == 0) {
                    i12++;
                    i11++;
                    if (i12 == i9) {
                        break;
                    } else if (bArr2[i10].length == i11) {
                        if (i10 == bArr2.length - 1) {
                            break;
                        }
                        i10++;
                        z = true;
                        i11 = -1;
                    }
                } else {
                    break;
                }
            }
            if (i4 >= 0) {
                if (i4 <= 0) {
                    int i13 = i9 - i12;
                    int length2 = bArr2[i10].length - i11;
                    while (true) {
                        i10++;
                        if (i10 >= bArr2.length) {
                            break;
                        }
                        length2 += bArr2[i10].length;
                    }
                    if (length2 >= i13) {
                        if (length2 <= i13) {
                            return new String(bArr, i7, i9, StandardCharsets.UTF_8);
                        }
                    }
                }
                i5 = i2 + 1;
            }
            length = i7 - 1;
        }
        return null;
    }

    private void readTheListUninterruptibly() {
        boolean z = false;
        while (true) {
            try {
                readTheList();
                break;
            } catch (InterruptedIOException unused) {
                Thread.interrupted();
                z = true;
            } catch (IOException e) {
                Platform.get().log(5, "Failed to read public suffix list", e);
                if (z) {
                    Thread.currentThread().interrupt();
                    return;
                }
                return;
            } catch (Throwable th) {
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x003f, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0043, code lost:
        if (r0 != null) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0045, code lost:
        if (r1 != null) goto L_0x0047;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x004c, code lost:
        r1.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0050, code lost:
        r0.close();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void readTheList() throws java.io.IOException {
        /*
            r4 = this;
            java.lang.Class<okhttp3.internal.publicsuffix.PublicSuffixDatabase> r0 = okhttp3.internal.publicsuffix.PublicSuffixDatabase.class
            java.lang.String r1 = "publicsuffixes.gz"
            java.io.InputStream r0 = r0.getResourceAsStream(r1)
            if (r0 != 0) goto L_0x000b
            return
        L_0x000b:
            okio.GzipSource r1 = new okio.GzipSource
            okio.Source r0 = okio.Okio.source(r0)
            r1.<init>(r0)
            okio.BufferedSource r0 = okio.Okio.buffer(r1)
            r1 = 0
            int r2 = r0.readInt()     // Catch:{ Throwable -> 0x0041 }
            byte[] r2 = new byte[r2]     // Catch:{ Throwable -> 0x0041 }
            r0.readFully(r2)     // Catch:{ Throwable -> 0x0041 }
            int r3 = r0.readInt()     // Catch:{ Throwable -> 0x0041 }
            byte[] r3 = new byte[r3]     // Catch:{ Throwable -> 0x0041 }
            r0.readFully(r3)     // Catch:{ Throwable -> 0x0041 }
            if (r0 == 0) goto L_0x0030
            r0.close()
        L_0x0030:
            monitor-enter(r4)
            r4.publicSuffixListBytes = r2     // Catch:{ all -> 0x003c }
            r4.publicSuffixExceptionListBytes = r3     // Catch:{ all -> 0x003c }
            monitor-exit(r4)     // Catch:{ all -> 0x003c }
            java.util.concurrent.CountDownLatch r0 = r4.readCompleteLatch
            r0.countDown()
            return
        L_0x003c:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        L_0x003f:
            r2 = move-exception
            goto L_0x0043
        L_0x0041:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x003f }
        L_0x0043:
            if (r0 == 0) goto L_0x0053
            if (r1 == 0) goto L_0x0050
            r0.close()     // Catch:{ Throwable -> 0x004b }
            goto L_0x0053
        L_0x004b:
            r0 = move-exception
            r1.addSuppressed(r0)
            goto L_0x0053
        L_0x0050:
            r0.close()
        L_0x0053:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.publicsuffix.PublicSuffixDatabase.readTheList():void");
    }

    /* access modifiers changed from: package-private */
    public void setListBytes(byte[] bArr, byte[] bArr2) {
        this.publicSuffixListBytes = bArr;
        this.publicSuffixExceptionListBytes = bArr2;
        this.listRead.set(true);
        this.readCompleteLatch.countDown();
    }
}
