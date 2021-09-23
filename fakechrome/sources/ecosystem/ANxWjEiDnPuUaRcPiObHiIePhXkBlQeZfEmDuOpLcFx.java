package ecosystem;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.alibaba.sdk.android.man.MANServiceProvider;
import com.bytedance.applog.AppLog;
import com.facebook.appevents.AppEventsLogger;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.analytics.HiAnalyticsInstance;
import com.kaka.analysis.mobile.ub.b;
import com.kaka.analysis.mobile.ub.c;
import com.ta.utdid2.device.UTDevice;
import com.umeng.analytics.MobclickAgent;
import ecosystem.userbehaviorutils.AbstractANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx;
import ecosystem.userbehaviorutils.AliONEUserbehaviorLog;
import ecosystem.userbehaviorutils.BRANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx;
import ecosystem.userbehaviorutils.FBANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx;
import ecosystem.userbehaviorutils.FireBaseANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx;
import ecosystem.userbehaviorutils.FlurryANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx;
import ecosystem.userbehaviorutils.GAANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx;
import ecosystem.userbehaviorutils.KakaANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx;
import ecosystem.userbehaviorutils.ThreadHelper;
import ecosystem.userbehaviorutils.UMengANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx;
import ecosystem.userbehaviorutils.util.UBDelayInit;
import ecosystem.userbehaviorutils.util.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx {
    public static boolean DEBUG = false;
    public static final int INIT_FLAG_ALISDK = 16;
    public static final int INIT_FLAG_BYTE_DANCE = 128;
    public static final int INIT_FLAG_FACEBOOK = 32;
    public static final int INIT_FLAG_FIREBASE = 64;
    public static final int INIT_FLAG_FLURRY = 8;
    public static final int INIT_FLAG_GA = 2;
    public static final int INIT_FLAG_HUAWEI = 512;
    public static final int INIT_FLAG_KAKA = 256;
    public static final int INIT_FLAG_UMENG = 1;
    public static final String TAG = "ANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx";
    private static volatile boolean bdh = false;
    private static final AtomicInteger ciB = new AtomicInteger(0);
    private static final List<AbstractANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx> ciC = new CopyOnWriteArrayList();
    private static final List<AbstractANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx> ciD = new CopyOnWriteArrayList();
    private static final UBDelayInit ciE = new UBDelayInit();
    private static OnBehaviorEventListener ciF = null;
    private static int ciG = 0;
    private static boolean ciH = true;
    private static final int ciI = 1019;
    private static int ciJ = 0;
    private static Map<String, Object> ciK = null;
    private static ABTestListener ciL;
    private static ConcurrentHashMap<String, String> ciM;
    public static Application s_Application;
    private static final long startTime = System.currentTimeMillis();

    public static boolean isEnable() {
        return ciH;
    }

    public static void setEnable(boolean z) {
        ciH = z;
    }

    public static void setOnBehaviorEventListener(OnBehaviorEventListener onBehaviorEventListener) {
        ciF = onBehaviorEventListener;
    }

    public static void configDisabledSDK(int i) {
        ciG = i | ciG;
    }

    public static void disableSDKNormEvent(int i) {
        ciJ = i | ciJ;
    }

    public static void setInitParam(Application application, Map<String, Object> map) {
        if (map != null) {
            ciK = new HashMap(map);
        }
        s_Application = application;
        application.registerActivityLifecycleCallbacks(new a());
        ThreadHelper.getInstance().executeTask(new 1());
    }

    private static void f(Application application) {
        if (isEnable()) {
            int i = ciG;
            if ((i & 1019) != 1019 && (i & 128) == 0) {
                try {
                    AbstractANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx bRANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx = new BRANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx(application, ciK);
                    if (!TextUtils.isEmpty(AppLog.class.getSimpleName())) {
                        ciC.add(bRANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx);
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                ciG |= 128;
            }
        }
    }

    private static synchronized void g(Application application) {
        synchronized (ANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx.class) {
            if (isEnable()) {
                int i = ciG;
                if ((i & 1019) != 1019) {
                    if ((i & 16) == 0) {
                        try {
                            if (!TextUtils.isEmpty(MANServiceProvider.class.getSimpleName())) {
                                AbstractANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx aliONEUserbehaviorLog = new AliONEUserbehaviorLog(application, ciK);
                                ciC.add(aliONEUserbehaviorLog);
                                ciD.add(aliONEUserbehaviorLog);
                            }
                        } catch (Throwable unused) {
                        }
                        ciG |= 16;
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static synchronized void init() {
        synchronized (ANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx.class) {
            if (isEnable()) {
                if ((ciG & 1019) != 1019) {
                    Application application = s_Application;
                    if (application != null) {
                        h(application);
                        g(s_Application);
                        i(s_Application);
                        f(s_Application);
                        Context applicationContext = s_Application.getApplicationContext();
                        if ((ciG & 1) == 0) {
                            try {
                                if (MobclickAgent.getAgent() != null) {
                                    ciC.add(new UMengANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx(ciK));
                                }
                            } catch (Throwable unused) {
                            }
                            ciG |= 1;
                        }
                        if ((ciG & 2) == 0) {
                            try {
                                if (!TextUtils.isEmpty(GoogleAnalytics.class.getSimpleName())) {
                                    ciC.add(new GAANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx(ciK));
                                }
                            } catch (Throwable unused2) {
                            }
                            ciG |= 2;
                        }
                        if ((ciG & 8) == 0) {
                            if (applicationContext != null && ciK != null) {
                                try {
                                    if (!TextUtils.isEmpty(FlurryAgent.class.getSimpleName())) {
                                        ciC.add(new FlurryANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx(applicationContext, ciK));
                                    }
                                    ciG |= 8;
                                } catch (Throwable unused3) {
                                }
                            } else {
                                return;
                            }
                        }
                        if ((ciG & 32) == 0) {
                            if (applicationContext != null) {
                                try {
                                    if (!TextUtils.isEmpty(AppEventsLogger.class.getSimpleName())) {
                                        ciC.add(new FBANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx());
                                    }
                                    ciG |= 32;
                                } catch (Throwable unused4) {
                                }
                            } else {
                                return;
                            }
                        }
                        if ((ciG & 64) == 0) {
                            if (applicationContext != null) {
                                try {
                                    if (!TextUtils.isEmpty(FirebaseAnalytics.class.getSimpleName())) {
                                        ciC.add(new FireBaseANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx(applicationContext));
                                    }
                                    ciG |= 64;
                                } catch (Throwable unused5) {
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void h(Application application) {
        if (isEnable()) {
            int i = ciG;
            if ((i & 1019) != 1019 && (i & 256) == 0) {
                try {
                    if (!TextUtils.isEmpty(b.class.getSimpleName())) {
                        Object obj = ciK.get("kaka_config");
                        if (obj instanceof c) {
                            AbstractANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx kakaANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx = new KakaANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx(application, (c) obj);
                            ciC.add(kakaANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx);
                            ciD.add(kakaANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx);
                        }
                    }
                } catch (Throwable unused) {
                }
                ciG |= 256;
            }
        }
    }

    private static void i(Application application) {
        if (isEnable()) {
            int i = ciG;
            if ((i & 1019) != 1019 && (i & 512) == 0) {
                try {
                    if (!TextUtils.isEmpty(HiAnalyticsInstance.class.getSimpleName())) {
                        new Handler(Looper.getMainLooper()).post(new 7(application));
                    }
                } catch (Throwable unused) {
                }
                ciG |= 512;
            }
        }
    }

    public static void setLoggerDebug(boolean z) {
        DEBUG = z;
    }

    public static void setDebugMode(boolean z) {
        ciE.setDebug(z);
        if (bdh) {
            ThreadHelper.getInstance().executeTask(new 8(z));
        }
    }

    public static void onResume(Context context) {
        if (bdh) {
            ThreadHelper.getInstance().executeTask(new 9(context));
        }
    }

    public static void onPause(Context context) {
        if (bdh) {
            ThreadHelper.getInstance().executeTask(new 10(context));
        }
    }

    public static void onKVEvent(String str, HashMap<String, String> hashMap) {
        onKVEvent(s_Application, str, hashMap);
    }

    @Deprecated
    public static void onKVEvent(Context context, String str, HashMap<String, String> hashMap) {
        if (!bdh) {
            ciE.addDelayLog(str, hashMap);
            return;
        }
        HashMap<String, String> aUM = aUM();
        if (hashMap != null) {
            aUM.putAll(hashMap);
        }
        ThreadHelper.getInstance().executeTask(new 11(aUM, str));
    }

    /* access modifiers changed from: private */
    public static boolean a(AbstractANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx abstractANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx, int i) {
        if (((((((abstractANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx instanceof UMengANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx) && (i & 1) != 0) || ((abstractANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx instanceof GAANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx) && (i & 2) != 0)) || ((abstractANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx instanceof FlurryANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx) && (i & 8) != 0)) || ((abstractANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx instanceof AliONEUserbehaviorLog) && (i & 16) != 0)) || ((abstractANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx instanceof FBANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx) && (i & 32) != 0)) || ((abstractANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx instanceof FireBaseANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx) && (i & 64) != 0)) {
            return true;
        }
        return false;
    }

    public static void updateOnlineConfig(Context context) {
        ThreadHelper.getInstance().executeTask(new 12(context));
    }

    public static void onKillProcess(Context context) {
        if (bdh) {
            ThreadHelper.getInstance().executeTask(new 13(context));
        }
    }

    public static void onAliEvent(String str, HashMap<String, String> hashMap) {
        if (!bdh) {
            ciE.addDelayLog(str, hashMap);
            return;
        }
        HashMap<String, String> aUM = aUM();
        if (hashMap != null) {
            aUM.putAll(hashMap);
        }
        ThreadHelper.getInstance().executeTask(new 14(str, aUM));
    }

    public static void updateAccount(String str, long j) {
        ThreadHelper.getInstance().executeTask(new 2(str, j));
    }

    public static void setUserProperty(String str, String str2) {
        ThreadHelper.getInstance().executeTask(new 3(str, str2));
    }

    public static void skipPage(Object obj) {
        ThreadHelper.getInstance().executeTask(new 4(obj));
    }

    public static void pageDisappear(Object obj) {
        ThreadHelper.getInstance().executeTask(new 5(obj));
    }

    public static void pageFragmentAppear(Object obj, String... strArr) {
        ThreadHelper.getInstance().executeTask(new 6(obj, strArr));
    }

    public static void setAbTestListener(ABTestListener aBTestListener) {
        ciL = aBTestListener;
    }

    public static synchronized void addCommonMap(HashMap<String, String> hashMap) {
        synchronized (ANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx.class) {
            if (hashMap != null) {
                if (hashMap.size() != 0) {
                    if (ciM == null) {
                        ciM = new ConcurrentHashMap<>();
                    }
                    ciM.putAll(hashMap);
                }
            }
        }
    }

    public static synchronized void clearCommonMap() {
        synchronized (ANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx.class) {
            ConcurrentHashMap<String, String> concurrentHashMap = ciM;
            if (concurrentHashMap != null) {
                concurrentHashMap.clear();
                ciM = null;
            }
        }
    }

    private static HashMap<String, String> aUM() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("appState", a.aho() ? "fore" : "bg");
        hashMap.put("api_level", String.valueOf(Build.VERSION.SDK_INT));
        Application application = s_Application;
        if (application != null) {
            hashMap.put("packageName", application.getPackageName());
            hashMap.put("appVersionCode", String.valueOf(Utils.getAppVersionCode(s_Application)));
            long currentTimeMillis = System.currentTimeMillis();
            long j = startTime;
            hashMap.put("ub_event_time", String.valueOf(currentTimeMillis - j));
            hashMap.put("uniqueId", UTDevice.getUtdid(s_Application) + "_" + j + "_" + ciB.getAndIncrement());
        }
        ConcurrentHashMap<String, String> concurrentHashMap = ciM;
        if (concurrentHashMap != null && concurrentHashMap.size() > 0) {
            hashMap.putAll(ciM);
        }
        ABTestListener aBTestListener = ciL;
        if (aBTestListener != null && !TextUtils.isEmpty(aBTestListener.getABTestKey()) && !TextUtils.isEmpty(ciL.getABTestValue())) {
            hashMap.put(ciL.getABTestKey(), ciL.getABTestValue());
        }
        return hashMap;
    }

    public static String getFirebaseId() {
        return FireBaseANxWjEiDnPuUaRcPiObHiIePhXkBlQeZfEmDuOpLcFx.firebaseAppInstanceId;
    }
}
