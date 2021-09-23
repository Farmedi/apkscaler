package decorate.angel.admission;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import java.io.File;
import java.util.ArrayList;

public class EJiIbLfHnCoBhFwMcOoOo {
    private static ArrayList<String> cic = null;
    private static final String cid = "Android/data/";
    private static Context mContext;

    public static void setApplicationContext(Context context) {
        if (context != null) {
            mContext = context.getApplicationContext();
        }
    }

    public static synchronized ArrayList<String> getStorageList() {
        ArrayList<String> arrayList;
        synchronized (EJiIbLfHnCoBhFwMcOoOo.class) {
            init();
            arrayList = cic;
        }
        return arrayList;
    }

    public static synchronized void clear() {
        synchronized (EJiIbLfHnCoBhFwMcOoOo.class) {
            ArrayList<String> arrayList = cic;
            if (arrayList != null) {
                arrayList.clear();
            }
            cic = null;
        }
    }

    private static String b(Context context, boolean z) {
        String[] split;
        try {
            String str = System.getenv("SECONDARY_STORAGE");
            if (!TextUtils.isEmpty(str) && (split = str.split(":")) != null) {
                if (split.length != 0) {
                    for (String str2 : split) {
                        if (str2 != null) {
                            File file = new File(str2);
                            if (file.isDirectory() && file.canWrite()) {
                                return file.getAbsolutePath();
                            }
                        }
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return null;
    }

    private static ArrayList<String> c(Context context, boolean z) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            arrayList.add(Environment.getExternalStorageDirectory().getAbsolutePath());
            File[] externalCacheDirs = context.getExternalCacheDirs();
            if (externalCacheDirs != null && externalCacheDirs.length > 0) {
                for (File file : externalCacheDirs) {
                    if (file != null && file.isDirectory()) {
                        if (file.canWrite()) {
                            String absolutePath = file.getAbsolutePath();
                            if (absolutePath != null) {
                                int indexOf = absolutePath.indexOf("/Android/");
                                if (indexOf > 0) {
                                    String substring = absolutePath.substring(0, indexOf);
                                    if (!arrayList.contains(substring)) {
                                        arrayList.add(substring);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return arrayList;
    }

    private static ArrayList<String> d(Context context, boolean z) {
        String absolutePath;
        int indexOf;
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            arrayList.add(Environment.getExternalStorageDirectory().getAbsolutePath());
            File externalCacheDir = context.getExternalCacheDir();
            if (externalCacheDir != null && (indexOf = (absolutePath = externalCacheDir.getAbsolutePath()).indexOf("/Android/")) > 0) {
                String substring = absolutePath.substring(0, indexOf);
                if (!arrayList.contains(substring)) {
                    arrayList.add(substring);
                }
            }
            String b = b(context, z);
            if (b != null && !arrayList.contains(b)) {
                arrayList.add(b);
            }
        } catch (Throwable unused) {
        }
        return arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x00ed  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x002a A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.ArrayList<java.lang.String> a(android.content.Context r9, java.util.ArrayList<java.lang.String> r10, boolean r11) {
        /*
        // Method dump skipped, instructions count: 245
        */
        throw new UnsupportedOperationException("Method not decompiled: decorate.angel.admission.EJiIbLfHnCoBhFwMcOoOo.a(android.content.Context, java.util.ArrayList, boolean):java.util.ArrayList");
    }

    private static ArrayList<String> e(Context context, boolean z) {
        ArrayList<String> arrayList = null;
        try {
            if (Build.VERSION.SDK_INT >= 19) {
                arrayList = c(context, z);
            } else {
                arrayList = d(context, z);
            }
            if (arrayList != null && arrayList.size() > 1) {
                arrayList = a(context, arrayList, z);
            }
        } catch (Throwable unused) {
        }
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        if (arrayList.isEmpty()) {
            arrayList.add(Environment.getExternalStorageDirectory().getAbsolutePath());
        }
        return arrayList;
    }

    private static synchronized boolean init() {
        synchronized (EJiIbLfHnCoBhFwMcOoOo.class) {
            if (cic != null) {
                return true;
            }
            cic = e(mContext, true);
            return true;
        }
    }

    public static synchronized int getStorageCount() {
        int i;
        synchronized (EJiIbLfHnCoBhFwMcOoOo.class) {
            init();
            ArrayList<String> arrayList = cic;
            if (arrayList != null) {
                if (!arrayList.isEmpty()) {
                    i = cic.size();
                }
            }
            i = 0;
        }
        return i;
    }

    public static synchronized String getMainStorage() {
        synchronized (EJiIbLfHnCoBhFwMcOoOo.class) {
            init();
            ArrayList<String> arrayList = cic;
            if (arrayList != null) {
                if (!arrayList.isEmpty()) {
                    return cic.get(0);
                }
            }
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
    }

    public static synchronized String getExtStorage() {
        synchronized (EJiIbLfHnCoBhFwMcOoOo.class) {
            init();
            ArrayList<String> arrayList = cic;
            if (arrayList != null) {
                if (!arrayList.isEmpty()) {
                    if (cic.size() <= 1) {
                        return null;
                    }
                    return cic.get(1);
                }
            }
            return null;
        }
    }

    public static synchronized String getStorageRootPath() {
        synchronized (EJiIbLfHnCoBhFwMcOoOo.class) {
            ArrayList<String> e = e(mContext, false);
            if (e != null) {
                if (e.size() != 0) {
                    if (e.size() == 1) {
                        return e.get(0);
                    }
                    String str = e.get(0);
                    String str2 = e.get(1);
                    File file = new File(str);
                    while (true) {
                        if (!str2.contains(str + File.separator)) {
                            file = file.getParentFile();
                            if (file != null) {
                                str = file.getAbsolutePath();
                                if (str.equals(File.separator)) {
                                    break;
                                }
                            } else {
                                str = File.separator;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    return str;
                }
            }
            return File.separator;
        }
    }
}
