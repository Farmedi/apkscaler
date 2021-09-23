package decorate.angel.admission;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.DropBoxManager;
import android.preference.PreferenceManager;
import decorate.angel.admission.UpgradeManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GHaAiIfPyIwPeKxDgRyGuMbDsJbIeYdUlXdLxCi {
    private static final String TAG = "GHaAiIfPyIwPeKxDgRyGuMbDsJbIeYdUlXdLxCi";
    public static final String TOMBSTONE_POSTFIX_DEL = ".del";
    public static final String TOMBSTONE_PREFIX = "Tombstone_";
    private static final String cij = "Tomb_CrashTimestamp";
    private static final String cik = "Tome_CheckTimestamp";
    private static final String cil = " scr ";
    private static final String cim = "code around pc:";
    private static final String cin = "SYSTEM_TOMBSTONE";
    private static final int cio = 36;
    private static final int ciq = 16;
    private long cip = 0;

    private static String cT(long j) {
        return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date(j));
    }

    public GHaAiIfPyIwPeKxDgRyGuMbDsJbIeYdUlXdLxCi(Context context) {
        try {
            DropBoxManager dropBoxManager = (DropBoxManager) context.getSystemService("dropbox");
            if (dropBoxManager == null) {
                return;
            }
            if (dropBoxManager.isTagEnabled(cin)) {
                SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                long j = defaultSharedPreferences.getLong(cik, System.currentTimeMillis());
                this.cip = defaultSharedPreferences.getLong(cij, 0);
                long j2 = j + 1;
                boolean z = false;
                String format = String.format(Locale.US, ">>> %s <<<", context.getPackageName());
                while (true) {
                    DropBoxManager.Entry nextEntry = dropBoxManager.getNextEntry(cin, j2);
                    if (nextEntry == null) {
                        break;
                    }
                    String text = nextEntry.getText(2048);
                    if (text != null && text.contains(format)) {
                        long timeMillis = nextEntry.getTimeMillis();
                        this.cip = timeMillis;
                        b(context, timeMillis);
                        z = true;
                    }
                    nextEntry.close();
                    j2 = nextEntry.getTimeMillis() + 1;
                }
                SharedPreferences.Editor edit = defaultSharedPreferences.edit();
                if (z) {
                    edit.putLong(cij, this.cip);
                }
                edit.putLong(cik, System.currentTimeMillis());
                edit.commit();
            }
        } catch (Exception unused) {
        }
    }

    private String cW(long j) {
        String cT = cT(j);
        return CommonConfigure.APP_CACHE_PATH + "Tombstone_" + cT + ".txt";
    }

    private String sD(String str) {
        if (str == null) {
            return null;
        }
        for (int i = 0; i < UpgradeManager.UpgradeConstDef.SO_NAMES.length; i++) {
            String str2 = UpgradeManager.SO_PATH + UpgradeManager.UpgradeConstDef.SO_NAMES[i];
            if (!"".equals(str2) && str2.equals(str)) {
                return str2;
            }
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x009f  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00a5 A[SYNTHETIC, Splitter:B:56:0x00a5] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getNeedRecoveryLibName() {
        /*
        // Method dump skipped, instructions count: 174
        */
        throw new UnsupportedOperationException("Method not decompiled: decorate.angel.admission.GHaAiIfPyIwPeKxDgRyGuMbDsJbIeYdUlXdLxCi.getNeedRecoveryLibName():java.lang.String");
    }

    private static String getAppVersion(Context context) {
        String str = "0";
        try {
            String str2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            if (str2 != null) {
                try {
                    return str2.length() <= 0 ? str : str2;
                } catch (Exception unused) {
                    str = str2;
                    return str;
                }
            }
        } catch (Exception unused2) {
            return str;
        }
    }

    private static String el(Context context) {
        return ((((("AppVersion:" + getAppVersion(context) + "\r\n") + "BRAND:" + Build.BRAND + "\r\n") + "CPU_ABI:" + Build.CPU_ABI + "\r\n") + "DEVICE:" + Build.DEVICE + "\r\n") + "MODEL:" + Build.MODEL + "\r\n") + "SDK:" + Build.VERSION.SDK + "\r\n";
    }

    /* JADX WARNING: Removed duplicated region for block: B:58:0x00b5  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00c2 A[SYNTHETIC, Splitter:B:64:0x00c2] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00c7 A[Catch:{ Exception -> 0x00ca }] */
    /* JADX WARNING: Removed duplicated region for block: B:74:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(android.content.Context r5, long r6) {
        /*
        // Method dump skipped, instructions count: 208
        */
        throw new UnsupportedOperationException("Method not decompiled: decorate.angel.admission.GHaAiIfPyIwPeKxDgRyGuMbDsJbIeYdUlXdLxCi.b(android.content.Context, long):void");
    }
}
