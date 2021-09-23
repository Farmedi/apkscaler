package decorate;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import com.quvideo.mobile.component.utils.q;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

public class YCsLcQwYlUrStKfObIoBrLqBgPzYeObQiBjYwFmTlUnCzWtTrJf {
    public static final String HTTP_CACHE_DIR = "http";
    public static final String HTTP_FILE_PREFIX = "http://";
    private static final int INITIAL_CAPACITY = 32;
    private static final String TAG = "YCsLcQwYlUrStKfObIoBrLqBgPzYeObQiBjYwFmTlUnCzWtTrJf";
    private static final float bme = 0.75f;
    private static File cjj = null;
    private static final String cjk = "cache_";
    private static final int cjl = 4;
    private static final long cjr = 259200000;
    private int EG = 0;
    private int cjm = 0;
    private final int cjn = 64;
    private long cjo = 5242880;
    private Bitmap.CompressFormat cjp = Bitmap.CompressFormat.JPEG;
    private int cjq = 85;
    private final Map<String, String> cjs = new LinkedHashMap(32, bme, true);
    protected final File mCacheDir;

    public static synchronized YCsLcQwYlUrStKfObIoBrLqBgPzYeObQiBjYwFmTlUnCzWtTrJf openCache(Context context, File file, long j) {
        synchronized (YCsLcQwYlUrStKfObIoBrLqBgPzYeObQiBjYwFmTlUnCzWtTrJf.class) {
            if (cjj == null) {
                File diskCacheDir = getDiskCacheDir(context, HTTP_CACHE_DIR);
                cjj = diskCacheDir;
                if (!diskCacheDir.exists()) {
                    cjj.mkdirs();
                }
            }
            if (!file.exists()) {
                file.mkdirs();
            }
            if (!file.isDirectory() || !file.canWrite() || Utils.getUsableSpace(file) <= j) {
                return null;
            }
            return new YCsLcQwYlUrStKfObIoBrLqBgPzYeObQiBjYwFmTlUnCzWtTrJf(file, j);
        }
    }

    private YCsLcQwYlUrStKfObIoBrLqBgPzYeObQiBjYwFmTlUnCzWtTrJf(File file, long j) {
        this.mCacheDir = file;
        this.cjo = j;
    }

    public static boolean isHttpFile(String str) {
        if (str != null && str.length() > 7) {
            return HTTP_FILE_PREFIX.equalsIgnoreCase(str.substring(0, 7));
        }
        return false;
    }

    private static String toHexString(byte[] bArr, String str) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(Integer.toHexString(b & 255));
            sb.append(str);
        }
        return sb.toString();
    }

    private static byte[] sE(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            if (instance == null) {
                return str.getBytes();
            }
            instance.update(str.getBytes());
            return instance.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return str.getBytes();
        }
    }

    private static String getMD5(String str) {
        return toHexString(sE(str), "");
    }

    private static String a(String str, Bitmap.CompressFormat compressFormat) {
        return getMD5(str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0013, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0018, code lost:
        if (isHttpFile(r5) != false) goto L_0x0035;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0020, code lost:
        if (r0.endsWith(".jpg") == false) goto L_0x0025;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0022, code lost:
        r2 = android.graphics.Bitmap.CompressFormat.JPEG;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0025, code lost:
        r2 = android.graphics.Bitmap.CompressFormat.PNG;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002d, code lost:
        if (a(r6, r0, r2, r4.cjq) == false) goto L_0x0035;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x002f, code lost:
        flushCache();
        put(r5, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:?, code lost:
        r0 = getFile(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0011, code lost:
        if (r0 != null) goto L_0x0014;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String put(java.lang.String r5, android.graphics.Bitmap r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            java.util.Map<java.lang.String, java.lang.String> r0 = r4.cjs     // Catch:{ all -> 0x0037 }
            java.lang.Object r0 = r0.get(r5)     // Catch:{ all -> 0x0037 }
            r1 = 0
            if (r0 == 0) goto L_0x000c
            monitor-exit(r4)     // Catch:{ all -> 0x0037 }
            return r1
        L_0x000c:
            monitor-exit(r4)     // Catch:{ all -> 0x0037 }
            java.lang.String r0 = r4.getFile(r5)     // Catch:{ all -> 0x0036 }
            if (r0 != 0) goto L_0x0014
            return r1
        L_0x0014:
            boolean r2 = isHttpFile(r5)     // Catch:{ all -> 0x0036 }
            if (r2 != 0) goto L_0x0035
            java.lang.String r2 = ".jpg"
            boolean r2 = r0.endsWith(r2)     // Catch:{ all -> 0x0036 }
            if (r2 == 0) goto L_0x0025
            android.graphics.Bitmap$CompressFormat r2 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ all -> 0x0036 }
            goto L_0x0027
        L_0x0025:
            android.graphics.Bitmap$CompressFormat r2 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ all -> 0x0036 }
        L_0x0027:
            int r3 = r4.cjq     // Catch:{ all -> 0x0036 }
            boolean r6 = r4.a(r6, r0, r2, r3)     // Catch:{ all -> 0x0036 }
            if (r6 == 0) goto L_0x0035
            r4.flushCache()     // Catch:{ all -> 0x0036 }
            r4.put(r5, r0)     // Catch:{ all -> 0x0036 }
        L_0x0035:
            r1 = r0
        L_0x0036:
            return r1
        L_0x0037:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: decorate.YCsLcQwYlUrStKfObIoBrLqBgPzYeObQiBjYwFmTlUnCzWtTrJf.put(java.lang.String, android.graphics.Bitmap):java.lang.String");
    }

    public synchronized void remove(String str) {
        if (!isHttpFile(str)) {
            String str2 = this.cjs.get(str);
            if (str2 == null) {
                str2 = getFile(str);
            }
            try {
                File file = new File(str2);
                if (file.exists() && file.isFile()) {
                    this.EG = this.cjs.size();
                    this.cjm = (int) (((long) this.cjm) - file.length());
                    file.delete();
                }
            } catch (Exception unused) {
            }
            if (this.cjs.get(str) != null) {
                this.cjs.remove(str);
            }
        }
    }

    private synchronized void put(String str, String str2) {
        this.cjs.put(str, str2);
        this.EG = this.cjs.size();
        this.cjm = (int) (((long) this.cjm) + new File(str2).length());
    }

    private synchronized void flushCache() {
        if (!this.cjs.isEmpty()) {
            for (int i = 0; i < 4 && (this.EG > 64 || ((long) this.cjm) > this.cjo); i++) {
                Map.Entry<String, String> next = this.cjs.entrySet().iterator().next();
                long length = new File(next.getValue()).length();
                this.cjs.remove(next.getKey());
                this.EG = this.cjs.size();
                this.cjm = (int) (((long) this.cjm) - length);
            }
        }
    }

    public String getFile(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String a = a(str, this.cjp);
        if (isHttpFile(str)) {
            return n(cjj, a);
        }
        return n(this.mCacheDir, a);
    }

    public Bitmap get(String str) {
        String file = getFile(str);
        if (file == null) {
            return null;
        }
        try {
            if (!new File(file).exists()) {
                return null;
            }
            Bitmap decodeFileInStream = Utils.decodeFileInStream(file);
            new File(file).setLastModified(System.currentTimeMillis());
            return decodeFileInStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public Bitmap get(String str, long j) {
        String file = getFile(str);
        if (file == null) {
            return null;
        }
        try {
            if (!new File(file).exists() || isCacheExpirationed(file, j)) {
                return null;
            }
            Bitmap decodeFileInStream = Utils.decodeFileInStream(file);
            new File(file).setLastModified(System.currentTimeMillis());
            return decodeFileInStream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public boolean isCacheExpirationed(String str, long j) {
        try {
            File file = new File(str);
            if (System.currentTimeMillis() - file.lastModified() <= j) {
                return false;
            }
            file.delete();
            return true;
        } catch (Throwable th) {
            th.printStackTrace();
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001c, code lost:
        if (new java.io.File(r0).exists() == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0022, code lost:
        if (isHttpFile(r4) != false) goto L_0x0027;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0024, code lost:
        put(r4, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0027, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x000d, code lost:
        r0 = getFile(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0011, code lost:
        if (r0 == null) goto L_?;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean containsKey(java.lang.String r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.util.Map<java.lang.String, java.lang.String> r0 = r3.cjs     // Catch:{ all -> 0x002a }
            boolean r0 = r0.containsKey(r4)     // Catch:{ all -> 0x002a }
            r1 = 1
            if (r0 == 0) goto L_0x000c
            monitor-exit(r3)     // Catch:{ all -> 0x002a }
            return r1
        L_0x000c:
            monitor-exit(r3)     // Catch:{ all -> 0x002a }
            java.lang.String r0 = r3.getFile(r4)
            if (r0 == 0) goto L_0x0028
            java.io.File r2 = new java.io.File
            r2.<init>(r0)
            boolean r2 = r2.exists()
            if (r2 == 0) goto L_0x0028
            boolean r2 = isHttpFile(r4)
            if (r2 != 0) goto L_0x0027
            r3.put(r4, r0)
        L_0x0027:
            return r1
        L_0x0028:
            r4 = 0
            return r4
        L_0x002a:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: decorate.YCsLcQwYlUrStKfObIoBrLqBgPzYeObQiBjYwFmTlUnCzWtTrJf.containsKey(java.lang.String):boolean");
    }

    public void clearCache() {
        ac(this.mCacheDir);
    }

    public static void clearCache(Context context, String str) {
        clearCache(context, str, cjr);
    }

    public static void clearCache(Context context, String str, long j) {
        e(getDiskCacheDir(context, str), j);
    }

    private static void ac(File file) {
        e(file, cjr);
    }

    /* access modifiers changed from: private */
    public static void d(File file, long j) {
        File[] listFiles = file.listFiles();
        if (!(listFiles == null || listFiles.length == 0)) {
            long currentTimeMillis = System.currentTimeMillis();
            for (int i = 0; i < listFiles.length; i++) {
                try {
                    if (listFiles[i] != null) {
                        if (listFiles[i].exists()) {
                            if (listFiles[i].isDirectory()) {
                                d(listFiles[i], j);
                            } else if (listFiles[i].lastModified() + j < currentTimeMillis) {
                                listFiles[i].delete();
                            }
                        }
                    }
                } catch (Throwable unused) {
                }
            }
        }
    }

    private static void e(File file, long j) {
        if (file != null) {
            new 1(j).w(new Object[]{file});
        }
    }

    public static File getDiskCacheDir(Context context, String str) {
        String pm = q.aIx().pm(".thumbnail");
        if (!TextUtils.isEmpty(str)) {
            pm = pm + File.separator + str;
        }
        return new File(pm);
    }

    public static String createFilePath(File file, String str) {
        return n(file, a(str, null));
    }

    private static String n(File file, String str) {
        if (file == null || TextUtils.isEmpty(str)) {
            return null;
        }
        return file.getAbsolutePath() + File.separator + str;
    }

    public void setCompressParams(Bitmap.CompressFormat compressFormat, int i) {
        this.cjp = compressFormat;
        this.cjq = i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0026  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(android.graphics.Bitmap r4, java.lang.String r5, android.graphics.Bitmap.CompressFormat r6, int r7) throws java.io.IOException, java.io.FileNotFoundException {
        /*
            r3 = this;
            if (r4 == 0) goto L_0x002a
            boolean r0 = r4.isRecycled()
            if (r0 != 0) goto L_0x002a
            if (r5 != 0) goto L_0x000b
            goto L_0x002a
        L_0x000b:
            r0 = 0
            java.io.BufferedOutputStream r1 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x0023 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ all -> 0x0023 }
            r2.<init>(r5)     // Catch:{ all -> 0x0023 }
            r5 = 262144(0x40000, float:3.67342E-40)
            r1.<init>(r2, r5)     // Catch:{ all -> 0x0023 }
            boolean r4 = r4.compress(r6, r7, r1)     // Catch:{ all -> 0x0020 }
            r1.close()
            return r4
        L_0x0020:
            r4 = move-exception
            r0 = r1
            goto L_0x0024
        L_0x0023:
            r4 = move-exception
        L_0x0024:
            if (r0 == 0) goto L_0x0029
            r0.close()
        L_0x0029:
            throw r4
        L_0x002a:
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: decorate.YCsLcQwYlUrStKfObIoBrLqBgPzYeObQiBjYwFmTlUnCzWtTrJf.a(android.graphics.Bitmap, java.lang.String, android.graphics.Bitmap$CompressFormat, int):boolean");
    }
}
