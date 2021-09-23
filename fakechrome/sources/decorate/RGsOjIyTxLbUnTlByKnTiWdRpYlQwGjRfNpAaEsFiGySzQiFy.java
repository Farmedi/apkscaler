package decorate;

import android.content.res.AssetManager;
import java.io.File;
import java.util.ArrayList;

public class RGsOjIyTxLbUnTlByKnTiWdRpYlQwGjRfNpAaEsFiGySzQiFy {
    public static AssetManager mAssetsManager;
    private ArrayList<String> chW = new ArrayList<>();

    public static void setContext(AssetManager assetManager) {
        mAssetsManager = assetManager;
    }

    public RGsOjIyTxLbUnTlByKnTiWdRpYlQwGjRfNpAaEsFiGySzQiFy(AssetManager assetManager) {
        setContext(assetManager);
    }

    public ArrayList<String> getChangedFileList() {
        return this.chW;
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0054 A[SYNTHETIC, Splitter:B:26:0x0054] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x005e A[SYNTHETIC, Splitter:B:31:0x005e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(java.lang.String r10, java.lang.String r11, android.content.res.AssetManager r12) {
        /*
        // Method dump skipped, instructions count: 103
        */
        throw new UnsupportedOperationException("Method not decompiled: decorate.RGsOjIyTxLbUnTlByKnTiWdRpYlQwGjRfNpAaEsFiGySzQiFy.a(java.lang.String, java.lang.String, android.content.res.AssetManager):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0075 A[SYNTHETIC, Splitter:B:50:0x0075] */
    /* JADX WARNING: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:61:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean b(java.io.InputStream r8, java.io.File r9) {
        /*
        // Method dump skipped, instructions count: 157
        */
        throw new UnsupportedOperationException("Method not decompiled: decorate.RGsOjIyTxLbUnTlByKnTiWdRpYlQwGjRfNpAaEsFiGySzQiFy.b(java.io.InputStream, java.io.File):boolean");
    }

    private void bF(String str, String str2) {
        try {
            String[] list = new File(str2).list();
            if (list != null) {
                ArrayList arrayList = new ArrayList();
                for (String str3 : list) {
                    arrayList.add(str3);
                }
                String[] list2 = mAssetsManager.list(str);
                for (String str4 : list2) {
                    int size = arrayList.size();
                    int i = 0;
                    while (true) {
                        if (i >= size) {
                            break;
                        } else if (((String) arrayList.get(i)).endsWith(str4)) {
                            arrayList.remove(i);
                            break;
                        } else {
                            i++;
                        }
                    }
                }
                int size2 = arrayList.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    String str5 = str2 + File.separator + ((String) arrayList.get(i2));
                    FileUtils.deleteFile(str5);
                    this.chW.add(str5);
                }
                arrayList.clear();
            }
        } catch (Exception unused) {
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x005e, code lost:
        r7 = false;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void copyFolder(java.lang.String r9, java.lang.String r10) {
        /*
        // Method dump skipped, instructions count: 144
        */
        throw new UnsupportedOperationException("Method not decompiled: decorate.RGsOjIyTxLbUnTlByKnTiWdRpYlQwGjRfNpAaEsFiGySzQiFy.copyFolder(java.lang.String, java.lang.String):void");
    }

    private void bG(String str, String str2) {
        if (str != null) {
            String str3 = str2 + File.separator + FileUtils.getFileNameWithExt(str);
            if (copyFileFromAssets(str, str3, mAssetsManager)) {
                this.chW.add(str3);
            }
        }
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isAssetsFileExisted(android.content.res.AssetManager r2, java.lang.String r3) {
        /*
            r0 = 0
            if (r2 == 0) goto L_0x001f
            boolean r1 = android.text.TextUtils.isEmpty(r3)
            if (r1 == 0) goto L_0x000a
            goto L_0x001f
        L_0x000a:
            java.io.InputStream r2 = r2.open(r3)     // Catch:{ Exception -> 0x001f, all -> 0x001d }
            if (r2 == 0) goto L_0x0012
            r3 = 1
            r0 = 1
        L_0x0012:
            if (r2 == 0) goto L_0x001f
            r2.close()     // Catch:{ IOException -> 0x0018 }
            goto L_0x001f
        L_0x0018:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x001f
        L_0x001d:
            r2 = move-exception
            throw r2
        L_0x001f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: decorate.RGsOjIyTxLbUnTlByKnTiWdRpYlQwGjRfNpAaEsFiGySzQiFy.isAssetsFileExisted(android.content.res.AssetManager, java.lang.String):boolean");
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:40:0x006c */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x00dd A[SYNTHETIC, Splitter:B:102:0x00dd] */
    /* JADX WARNING: Removed duplicated region for block: B:108:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x007f A[SYNTHETIC, Splitter:B:49:0x007f] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x009a A[SYNTHETIC, Splitter:B:68:0x009a] */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x00a4 A[SYNTHETIC, Splitter:B:72:0x00a4] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x00b7 A[SYNTHETIC, Splitter:B:81:0x00b7] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x00c0 A[SYNTHETIC, Splitter:B:89:0x00c0] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x00ca A[SYNTHETIC, Splitter:B:93:0x00ca] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean copyFileFromAssets(java.lang.String r5, java.lang.String r6, android.content.res.AssetManager r7) {
        /*
        // Method dump skipped, instructions count: 225
        */
        throw new UnsupportedOperationException("Method not decompiled: decorate.RGsOjIyTxLbUnTlByKnTiWdRpYlQwGjRfNpAaEsFiGySzQiFy.copyFileFromAssets(java.lang.String, java.lang.String, android.content.res.AssetManager):boolean");
    }
}
