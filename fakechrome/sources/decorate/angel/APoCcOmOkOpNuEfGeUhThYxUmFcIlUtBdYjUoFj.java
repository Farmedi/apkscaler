package decorate.angel;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.SparseArray;
import com.vivalab.mobile.log.c;
import decorate.angel.IniProcessor.IniFileProcessor;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class APoCcOmOkOpNuEfGeUhThYxUmFcIlUtBdYjUoFj {
    public static String HW_DECODE_PATH = "";
    public static String HW_ENCODE_PATH = "";
    public static String INI_PATH = "";
    public static final int LOAD_MODE_ALL = 65535;
    public static final int LOAD_MODE_BASIC = 1;
    public static final int LOAD_MODE_CAMCORDER = 55;
    public static final int LOAD_MODE_ENGINE = 4;
    public static final int LOAD_MODE_HWCODEC = 2;
    public static final int LOAD_MODE_VE = 23;
    public static String SO_PATH = "";
    private static String TAG = "APoCcOmOkOpNuEfGeUhThYxUmFcIlUtBdYjUoFj";
    public static final String XIAOYING_ASSETS_RELATIVE_PATH = "xiaoying/";
    public static final String XIAOYING_LIBS_ASSETS_RELATIVE_PATH = "xiaoying/libs/";
    private static String ciA = null;
    private static Object cir = new Object();
    private static boolean cis;
    private static ResultListener cit = null;
    private static String ciu = "preload";
    private static boolean[] civ = new boolean[32];
    private static int ciw = 0;
    private static String cix = "XY_Library_Ver";
    private static boolean ciy = true;
    private static String ciz = "";
    public static Set<String> loadedSOSet = new HashSet();
    private static AssetManager mAssetsManager = null;
    private static Context mContext = null;
    public static Boolean m_bTaskModified = true;

    public static void postLoad() {
    }

    public static void setOnResultListener(ResultListener resultListener) {
        cit = resultListener;
    }

    public static void setDebugMode(boolean z) {
        cis = z;
    }

    public static void setAssets(AssetManager assetManager) {
        mAssetsManager = assetManager;
    }

    public static void setContext(Context context) {
        Context applicationContext;
        if (context != null && mContext != (applicationContext = context.getApplicationContext())) {
            mContext = applicationContext;
            String str = null;
            try {
                File cacheDir = applicationContext.getCacheDir();
                if (cacheDir != null) {
                    str = cacheDir.getParent();
                }
            } catch (Throwable unused) {
            }
            if (TextUtils.isEmpty(str)) {
                str = "/data/data/" + mContext.getPackageName();
            }
            ciz = str + "/lib/";
            SO_PATH = str + "/so/";
            AppPreferencesSetting.getInstance().init(context);
            String appSettingStr = AppPreferencesSetting.getInstance().getAppSettingStr(cix, "");
            String appVersion = Utils.getAppVersion(context);
            if (appVersion == null || !appVersion.equals(appSettingStr)) {
                try {
                    FileUtils.deleteDirectory(SO_PATH);
                } catch (Throwable unused2) {
                }
            } else {
                ciy = false;
            }
            HW_ENCODE_PATH = getSoLibraryPath("libhwvideowriter.so");
            HW_DECODE_PATH = getSoLibraryPath("libhwvideoreader.so");
            INI_PATH = SO_PATH + "pretask.ini";
        }
    }

    public static String getSoLibraryPath(String str) {
        String packageName = mContext.getPackageName();
        PackageInfo packageInfo = null;
        try {
            packageInfo = mContext.getPackageManager().getPackageInfo(packageName, 0);
        } catch (Exception unused) {
            List<PackageInfo> installedPackages = mContext.getPackageManager().getInstalledPackages(0);
            if (installedPackages != null && installedPackages.size() > 0) {
                Iterator<PackageInfo> it = installedPackages.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    PackageInfo next = it.next();
                    String str2 = TAG;
                    c.e(str2, "pkg name: " + packageInfo.packageName);
                    if (next.packageName.equals(packageName)) {
                        packageInfo = next;
                        break;
                    }
                }
            } else {
                c.e(TAG, "No packages");
            }
        }
        return packageInfo.applicationInfo.nativeLibraryDir + "/" + str;
    }

    public static synchronized void upgradeThisFile(String str, int i) {
        synchronized (APoCcOmOkOpNuEfGeUhThYxUmFcIlUtBdYjUoFj.class) {
            if (i >= 0 && i <= 31) {
                if (!"".equals(UpgradeConstDef.SO_NAMES[i])) {
                    if (i < 16 || i > 31) {
                        IniParser iniParser = new IniParser();
                        iniParser.load(INI_PATH);
                        iniParser.set(String.format(Locale.US, "%s%02d", ciu, Integer.valueOf(i)), str);
                        FileUtils.createMultilevelDirectory(SO_PATH);
                        iniParser.save(INI_PATH);
                        synchronized (cir) {
                            m_bTaskModified = true;
                        }
                    } else {
                        FileUtils.createMultilevelDirectory(SO_PATH);
                        FileUtils.copyFile(str, SO_PATH + UpgradeConstDef.SO_NAMES[i]);
                    }
                }
            }
        }
    }

    public static synchronized boolean loadLibrary(String str) {
        synchronized (APoCcOmOkOpNuEfGeUhThYxUmFcIlUtBdYjUoFj.class) {
            int libraryIndex = UpgradeConstDef.getLibraryIndex(str);
            String str2 = TAG;
            c.d(str2, "strLibrary:" + libraryIndex);
            if (libraryIndex == -1) {
                return false;
            }
            return loadLibrary(libraryIndex);
        }
    }

    private static synchronized boolean loadLibrary(int i) {
        synchronized (APoCcOmOkOpNuEfGeUhThYxUmFcIlUtBdYjUoFj.class) {
            if (i == -1) {
                return false;
            }
            c.d(TAG, "loadLibrary:" + i);
            if (civ[i]) {
                return true;
            }
            String str = UpgradeConstDef.SO_NAMES[i];
            if ("".equals(str)) {
                return false;
            }
            String str2 = SO_PATH + str;
            if (!FileUtils.isFileExisted(str2)) {
                str2 = ciz + str;
            }
            c.d(TAG, "strLoad:" + str2);
            try {
                if (str2.startsWith(ciz)) {
                    String replace = str.replace("lib", "").replace(".so", "");
                    loadedSOSet.add(replace);
                    System.loadLibrary(replace);
                } else {
                    loadedSOSet.add(str2);
                    System.load(str2);
                }
                civ[i] = true;
            } catch (Throwable th) {
                c.d(TAG, th.getMessage());
                ResultListener resultListener = cit;
                if (resultListener != null) {
                    resultListener.onError(th);
                }
                civ[i] = false;
            }
            return civ[i];
        }
    }

    private static boolean a(Context context, AssetManager assetManager, int i) {
        if (assetManager == null) {
            return false;
        }
        String cpuFeatures = CpuFeatures.getCpuFeatures();
        boolean z = ((cpuFeatures != null ? Integer.parseInt(cpuFeatures) : 0) & CpuFeatures.ANDROID_CPU_ARM_FEATURE_NEON) != 0;
        int i2 = Build.VERSION.SDK_INT;
        SparseArray sparseArray = new SparseArray();
        if ((ciw & 1) != 1 && (i & 1) == 1) {
            int libraryIndex = UpgradeConstDef.getLibraryIndex("libcesplatform.so");
            if (ResourceUtils.isAssetsFileExisted(assetManager, "xiaoying/libs/libcesplatform.so")) {
                sparseArray.put(libraryIndex, "xiaoying/libs/libcesplatform.so");
            }
            String[] strArr = {"libpostprocess.so", "libx264.so", "libffmpeg.so"};
            for (int i3 = 0; i3 < 3; i3++) {
                String str = strArr[i3];
                int libraryIndex2 = UpgradeConstDef.getLibraryIndex(str);
                String str2 = z ? "xiaoying/libs/neon/" + str : "xiaoying/libs/arm11/" + str;
                if (ResourceUtils.isAssetsFileExisted(assetManager, str2)) {
                    sparseArray.put(libraryIndex2, str2);
                } else {
                    if (!FileUtils.isFileExisted(ciz + UpgradeConstDef.SO_NAMES[libraryIndex2])) {
                        sparseArray.put(libraryIndex2, "xiaoying/libs/arm11/" + str);
                    }
                }
            }
            int libraryIndex3 = UpgradeConstDef.getLibraryIndex("libcesplatformutils.so");
            if (ResourceUtils.isAssetsFileExisted(assetManager, "xiaoying/libs/libcesplatformutils.so")) {
                sparseArray.put(libraryIndex3, "xiaoying/libs/libcesplatformutils.so");
            }
        }
        if ((ciw & 4) != 4 && (i & 4) == 4) {
            String str3 = "xiaoying/libs/libcesrenderengine_2.3.so";
            if (i2 >= 14 && ResourceUtils.isAssetsFileExisted(assetManager, "xiaoying/libs/libcesrenderengine_4.0.so")) {
                str3 = "xiaoying/libs/libcesrenderengine_4.0.so";
            }
            if (ResourceUtils.isAssetsFileExisted(assetManager, str3)) {
                sparseArray.put(UpgradeConstDef.getLibraryIndex("libcesrenderengine.so"), str3);
            }
            if (ResourceUtils.isAssetsFileExisted(assetManager, "xiaoying/libs/libcesmediabase.so")) {
                sparseArray.put(UpgradeConstDef.getLibraryIndex("libcesmediabase.so"), "xiaoying/libs/libcesmediabase.so");
            }
        }
        if ((ciw & 23) != 23 && (i & 23) == 23 && ResourceUtils.isAssetsFileExisted(assetManager, "xiaoying/libs/libcesliveeditor.so")) {
            sparseArray.put(UpgradeConstDef.getLibraryIndex("libcesliveeditor.so"), "xiaoying/libs/libcesliveeditor.so");
        }
        if ((ciw & 55) != 55 && (i & 55) == 55 && ResourceUtils.isAssetsFileExisted(assetManager, "xiaoying/libs/libcescamengine.so")) {
            sparseArray.put(UpgradeConstDef.getLibraryIndex("libcescamengine.so"), "xiaoying/libs/libcescamengine.so");
        }
        if ((ciw & 2) != 2 && (i & 2) == 2) {
            aUL();
        }
        if (sparseArray.size() != 0) {
            for (int i4 = 0; i4 < 32; i4++) {
                String str4 = (String) sparseArray.get(i4);
                if (str4 != null && !str4.isEmpty() && !UpgradeConstDef.SO_NAMES[i4].isEmpty()) {
                    String str5 = SO_PATH + UpgradeConstDef.SO_NAMES[i4];
                    if (ciy || !cis || !FileUtils.isFileExisted(str5)) {
                        ResourceUtils.copyFileFromAssets(str4, str5, assetManager);
                    }
                }
            }
        }
        int i5 = ciw | i;
        ciw = i5;
        if ((i5 & 55) == 55) {
            AppPreferencesSetting.getInstance().init(context);
            String appSettingStr = AppPreferencesSetting.getInstance().getAppSettingStr(cix, "");
            String appVersion = Utils.getAppVersion(context);
            if (appVersion != null && !appVersion.equals(appSettingStr)) {
                AppPreferencesSetting.getInstance().setAppSettingStr(cix, appVersion);
            }
        }
        return true;
    }

    public static synchronized boolean preInstall(int i) {
        synchronized (APoCcOmOkOpNuEfGeUhThYxUmFcIlUtBdYjUoFj.class) {
            FileUtils.createMultilevelDirectory(SO_PATH);
            if (!a(mContext, mAssetsManager, i)) {
                return false;
            }
            if (!m_bTaskModified.booleanValue()) {
                return true;
            }
            IniParser iniParser = new IniParser();
            if (!iniParser.load(INI_PATH)) {
                return true;
            }
            for (int i2 = 0; i2 <= 31; i2++) {
                String format = String.format(Locale.US, "%s%02d", ciu, Integer.valueOf(i2));
                String str = iniParser.get(format);
                if (!"".equals(str) && FileUtils.isFileExisted(str)) {
                    String str2 = SO_PATH + UpgradeConstDef.SO_NAMES[i2];
                    FileUtils.deleteFile(str2);
                    FileUtils.copyFile(str, str2);
                    FileUtils.deleteFile(str);
                    iniParser.set(format, (String) null);
                }
            }
            iniParser.save(INI_PATH);
            synchronized (cir) {
                m_bTaskModified = false;
            }
            return true;
        }
    }

    private static String aUK() {
        IniFileProcessor iniFileProcessor;
        String propertyValue;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        String str = ciA;
        if (str != null) {
            return str;
        }
        String str2 = SO_PATH + "CPUConfig.ini";
        String str3 = null;
        if (!ResourceUtils.copyFileFromAssets("xiaoying/ini/CPUConfig.ini", str2, mAssetsManager) || (propertyValue = (iniFileProcessor = new IniFileProcessor(str2)).getPropertyValue("Common", "CPUCount")) == null) {
            return null;
        }
        int intValue = Integer.decode(propertyValue).intValue();
        int i6 = 0;
        try {
            String cpuImplementer = CpuFeatures.getCpuImplementer();
            i = cpuImplementer.contains(".") ? Float.valueOf(cpuImplementer).intValue() : Integer.decode(cpuImplementer).intValue();
        } catch (Exception unused) {
            i = 0;
        }
        try {
            String cpuArch = CpuFeatures.getCpuArch();
            i2 = cpuArch.contains(".") ? Float.valueOf(cpuArch).intValue() : Integer.decode(cpuArch).intValue();
        } catch (Exception unused2) {
            i2 = 0;
        }
        try {
            String cpuVariant = CpuFeatures.getCpuVariant();
            i3 = cpuVariant.contains(".") ? Float.valueOf(cpuVariant).intValue() : Integer.decode(cpuVariant).intValue();
        } catch (Exception unused3) {
            i3 = 0;
        }
        try {
            String cpuPart = CpuFeatures.getCpuPart();
            i4 = cpuPart.contains(".") ? Float.valueOf(cpuPart).intValue() : Integer.decode(cpuPart).intValue();
        } catch (Exception unused4) {
            i4 = 0;
        }
        try {
            String cpuRevision = CpuFeatures.getCpuRevision();
            i5 = cpuRevision.contains(".") ? Float.valueOf(cpuRevision).intValue() : Integer.decode(cpuRevision).intValue();
        } catch (Exception unused5) {
            i5 = 0;
        }
        StringBuffer stringBuffer = new StringBuffer("CPU0");
        while (true) {
            if (i6 >= intValue) {
                break;
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            stringBuffer.append(i6);
            if (i == Integer.decode(iniFileProcessor.getPropertyValue(stringBuffer.toString(), "Implementer")).intValue() && i2 == Integer.decode(iniFileProcessor.getPropertyValue(stringBuffer.toString(), "Arch")).intValue() && i3 == Integer.decode(iniFileProcessor.getPropertyValue(stringBuffer.toString(), "Variant")).intValue() && i4 == Integer.decode(iniFileProcessor.getPropertyValue(stringBuffer.toString(), "Part")).intValue() && i5 == Integer.decode(iniFileProcessor.getPropertyValue(stringBuffer.toString(), "Revision")).intValue()) {
                str3 = iniFileProcessor.getPropertyValue(stringBuffer.toString(), "Type");
                break;
            }
            i6++;
        }
        ciA = str3;
        return str3;
    }

    public static void preLoad() {
        FileUtils.createMultilevelDirectory(SO_PATH);
        for (int i = 0; i <= 15; i++) {
            loadLibrary(i);
        }
    }

    private static void aUL() {
        String str = SO_PATH + "libhwvideowriter.so";
        String str2 = SO_PATH + "libhwvideoreader.so";
        String str3 = "xiaoying/libs/hwcodec/libhwvideowriter.so";
        String str4 = null;
        if (!ResourceUtils.isAssetsFileExisted(mAssetsManager, str3)) {
            str3 = null;
        }
        String str5 = "xiaoying/libs/hwcodec/libhwvideoreader.so";
        if (!ResourceUtils.isAssetsFileExisted(mAssetsManager, str5)) {
            str5 = null;
        }
        int parseInt = Integer.parseInt(Build.VERSION.SDK);
        try {
            str4 = aUK();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (str4 != null) {
            if (parseInt == 9 || parseInt == 10) {
                if (TextUtils.isEmpty(str3)) {
                    str3 = "xiaoying/libs/hwcodec/libhwvideowriter_2.3_qcom.so";
                }
                if (TextUtils.isEmpty(str5)) {
                    str5 = "xiaoying/libs/hwcodec/libhwvideoreader_2.3_qcom.so";
                }
            } else if (parseInt == 14 || parseInt == 15) {
                if (TextUtils.isEmpty(str3)) {
                    str3 = "xiaoying/libs/hwcodec/libhwvideowriter_4.0_qcom.so";
                }
                if (TextUtils.isEmpty(str5)) {
                    str5 = "xiaoying/libs/hwcodec/libhwvideoreader_4.0_qcom.so";
                }
            }
        }
        if (!TextUtils.isEmpty(str3)) {
            if (ciy || !cis || !FileUtils.isFileExisted(str)) {
                ResourceUtils.copyFileFromAssets(str3, str, mAssetsManager);
                HW_ENCODE_PATH = str;
            }
        } else if (FileUtils.isFileExisted(str)) {
            FileUtils.deleteFile(str);
        }
        if (!TextUtils.isEmpty(str5)) {
            if (ciy || !cis || !FileUtils.isFileExisted(str2)) {
                ResourceUtils.copyFileFromAssets(str5, str2, mAssetsManager);
                HW_DECODE_PATH = str2;
            }
        } else if (FileUtils.isFileExisted(str2)) {
            FileUtils.deleteFile(str2);
        }
    }
}
