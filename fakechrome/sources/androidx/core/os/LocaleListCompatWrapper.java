package androidx.core.os;

import android.os.Build;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

/* access modifiers changed from: package-private */
public final class LocaleListCompatWrapper implements LocaleListInterface {
    private static final Locale EN_LATN = LocaleListCompat.forLanguageTagCompat("en-Latn");
    private static final Locale LOCALE_AR_XB = new Locale("ar", "XB");
    private static final Locale LOCALE_EN_XA = new Locale("en", "XA");
    private static final Locale[] sEmptyList = new Locale[0];
    private final Locale[] mList;
    @NonNull
    private final String mStringRepresentation;

    @Override // androidx.core.os.LocaleListInterface
    @Nullable
    public Object getLocaleList() {
        return null;
    }

    @Override // androidx.core.os.LocaleListInterface
    public Locale get(int i) {
        if (i < 0 || i >= this.mList.length) {
            return null;
        }
        return this.mList[i];
    }

    @Override // androidx.core.os.LocaleListInterface
    public boolean isEmpty() {
        return this.mList.length == 0;
    }

    @Override // androidx.core.os.LocaleListInterface
    public int size() {
        return this.mList.length;
    }

    @Override // androidx.core.os.LocaleListInterface
    public int indexOf(Locale locale) {
        for (int i = 0; i < this.mList.length; i++) {
            if (this.mList[i].equals(locale)) {
                return i;
            }
        }
        return -1;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LocaleListCompatWrapper)) {
            return false;
        }
        Locale[] localeArr = ((LocaleListCompatWrapper) obj).mList;
        if (this.mList.length != localeArr.length) {
            return false;
        }
        for (int i = 0; i < this.mList.length; i++) {
            if (!this.mList[i].equals(localeArr[i])) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.mList.length; i2++) {
            i = (i * 31) + this.mList[i2].hashCode();
        }
        return i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < this.mList.length; i++) {
            sb.append(this.mList[i]);
            if (i < this.mList.length - 1) {
                sb.append(',');
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override // androidx.core.os.LocaleListInterface
    public String toLanguageTags() {
        return this.mStringRepresentation;
    }

    LocaleListCompatWrapper(@NonNull Locale... localeArr) {
        if (localeArr.length == 0) {
            this.mList = sEmptyList;
            this.mStringRepresentation = "";
            return;
        }
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < localeArr.length; i++) {
            Locale locale = localeArr[i];
            if (locale == null) {
                throw new NullPointerException("list[" + i + "] is null");
            }
            if (!hashSet.contains(locale)) {
                Locale locale2 = (Locale) locale.clone();
                arrayList.add(locale2);
                toLanguageTag(sb, locale2);
                if (i < localeArr.length - 1) {
                    sb.append(',');
                }
                hashSet.add(locale2);
            }
        }
        this.mList = (Locale[]) arrayList.toArray(new Locale[arrayList.size()]);
        this.mStringRepresentation = sb.toString();
    }

    @VisibleForTesting
    static void toLanguageTag(StringBuilder sb, Locale locale) {
        sb.append(locale.getLanguage());
        String country = locale.getCountry();
        if (country != null && !country.isEmpty()) {
            sb.append('-');
            sb.append(locale.getCountry());
        }
    }

    private static String getLikelyScript(Locale locale) {
        if (Build.VERSION.SDK_INT < 21) {
            return "";
        }
        String script = locale.getScript();
        return !script.isEmpty() ? script : "";
    }

    private static boolean isPseudoLocale(Locale locale) {
        return LOCALE_EN_XA.equals(locale) || LOCALE_AR_XB.equals(locale);
    }

    @IntRange(from = 0, to = 1)
    private static int matchScore(Locale locale, Locale locale2) {
        if (locale.equals(locale2)) {
            return 1;
        }
        if (!locale.getLanguage().equals(locale2.getLanguage()) || isPseudoLocale(locale) || isPseudoLocale(locale2)) {
            return 0;
        }
        String likelyScript = getLikelyScript(locale);
        if (!likelyScript.isEmpty()) {
            return likelyScript.equals(getLikelyScript(locale2)) ? 1 : 0;
        }
        String country = locale.getCountry();
        if (country.isEmpty() || country.equals(locale2.getCountry())) {
            return 1;
        }
        return 0;
    }

    private int findFirstMatchIndex(Locale locale) {
        for (int i = 0; i < this.mList.length; i++) {
            if (matchScore(locale, this.mList[i]) > 0) {
                return i;
            }
        }
        return Integer.MAX_VALUE;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001d, code lost:
        if (r5 < Integer.MAX_VALUE) goto L_0x0023;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int computeFirstMatchIndex(java.util.Collection<java.lang.String> r4, boolean r5) {
        /*
            r3 = this;
            java.util.Locale[] r0 = r3.mList
            int r0 = r0.length
            r1 = 0
            r2 = 1
            if (r0 != r2) goto L_0x0008
            return r1
        L_0x0008:
            java.util.Locale[] r0 = r3.mList
            int r0 = r0.length
            if (r0 != 0) goto L_0x000f
            r4 = -1
            return r4
        L_0x000f:
            r0 = 2147483647(0x7fffffff, float:NaN)
            if (r5 == 0) goto L_0x0020
            java.util.Locale r5 = androidx.core.os.LocaleListCompatWrapper.EN_LATN
            int r5 = r3.findFirstMatchIndex(r5)
            if (r5 != 0) goto L_0x001d
            return r1
        L_0x001d:
            if (r5 >= r0) goto L_0x0020
            goto L_0x0023
        L_0x0020:
            r5 = 2147483647(0x7fffffff, float:NaN)
        L_0x0023:
            java.util.Iterator r4 = r4.iterator()
        L_0x0027:
            boolean r2 = r4.hasNext()
            if (r2 == 0) goto L_0x0042
            java.lang.Object r2 = r4.next()
            java.lang.String r2 = (java.lang.String) r2
            java.util.Locale r2 = androidx.core.os.LocaleListCompat.forLanguageTagCompat(r2)
            int r2 = r3.findFirstMatchIndex(r2)
            if (r2 != 0) goto L_0x003e
            return r1
        L_0x003e:
            if (r2 >= r5) goto L_0x0027
            r5 = r2
            goto L_0x0027
        L_0x0042:
            if (r5 != r0) goto L_0x0045
            return r1
        L_0x0045:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.os.LocaleListCompatWrapper.computeFirstMatchIndex(java.util.Collection, boolean):int");
    }

    private Locale computeFirstMatch(Collection<String> collection, boolean z) {
        int computeFirstMatchIndex = computeFirstMatchIndex(collection, z);
        if (computeFirstMatchIndex == -1) {
            return null;
        }
        return this.mList[computeFirstMatchIndex];
    }

    @Override // androidx.core.os.LocaleListInterface
    public Locale getFirstMatch(@NonNull String[] strArr) {
        return computeFirstMatch(Arrays.asList(strArr), false);
    }
}
