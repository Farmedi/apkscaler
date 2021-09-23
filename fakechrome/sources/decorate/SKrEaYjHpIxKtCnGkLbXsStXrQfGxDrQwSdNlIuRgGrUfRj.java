package decorate;

import android.os.HandlerThread;
import android.text.TextUtils;
import com.b.a.d;
import com.b.a.j;

public class SKrEaYjHpIxKtCnGkLbXsStXrQfGxDrQwSdNlIuRgGrUfRj {
    private static boolean DEBUG = true;
    public static boolean Logable = false;
    private static a chE = null;
    public static CustomLogger customLogger = null;
    public static String customTagPrefix = "QuVideo";

    public static void init(boolean z, String str) {
        DEBUG = z;
        if (z) {
            customTagPrefix = str;
            HandlerThread handlerThread = new HandlerThread("LogException");
            handlerThread.start();
            chE = new a(handlerThread.getLooper());
        }
    }

    public static void initLoggerWriterAdapter() {
        j.a(new d());
    }

    private static String b(StackTraceElement stackTraceElement) {
        String className = stackTraceElement.getClassName();
        String format = String.format("%s.%s(L:%d)", className.substring(className.lastIndexOf(".") + 1), stackTraceElement.getMethodName(), Integer.valueOf(stackTraceElement.getLineNumber()));
        if (TextUtils.isEmpty(customTagPrefix)) {
            return format;
        }
        return customTagPrefix + ":" + format;
    }

    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    public static void d(String str) {
        if (Logable) {
            j.cP(str);
        }
        if (DEBUG && chE != null) {
            chE.post(new 1(b(getCallerStackTraceElement()), str));
        }
    }

    public static void d(String str, Throwable th) {
        if (DEBUG && chE != null) {
            chE.post(new 7(b(getCallerStackTraceElement()), str, th));
        }
    }

    public static void e(String str) {
        if (Logable) {
            j.e(str, new Object[0]);
        }
        if (DEBUG && chE != null) {
            chE.post(new 8(b(getCallerStackTraceElement()), str));
        }
    }

    public static void e(String str, Throwable th) {
        if (Logable) {
            j.e(str + M(th), new Object[0]);
        }
        if (DEBUG && chE != null) {
            chE.post(new 9(b(getCallerStackTraceElement()), str, th));
        }
    }

    public static void i(String str) {
        if (Logable) {
            j.i(str, new Object[0]);
        }
        if (DEBUG && chE != null) {
            chE.post(new 10(b(getCallerStackTraceElement()), str));
        }
    }

    public static void i(String str, Throwable th) {
        if (Logable) {
            j.i(str + M(th), new Object[0]);
        }
        if (DEBUG && chE != null) {
            chE.post(new 11(b(getCallerStackTraceElement()), str, th));
        }
    }

    public static void v(String str) {
        if (Logable) {
            j.v(str, new Object[0]);
        }
        if (DEBUG && chE != null) {
            chE.post(new 12(b(getCallerStackTraceElement()), str));
        }
    }

    public static void v(String str, Throwable th) {
        if (Logable) {
            j.v(str + M(th), new Object[0]);
        }
        if (DEBUG && chE != null) {
            chE.post(new 13(b(getCallerStackTraceElement()), str, th));
        }
    }

    public static void w(String str) {
        if (Logable) {
            j.w(str, new Object[0]);
        }
        if (DEBUG && chE != null) {
            chE.post(new 14(b(getCallerStackTraceElement()), str));
        }
    }

    public static void w(String str, Throwable th) {
        if (Logable) {
            j.w(str + M(th), new Object[0]);
        }
        if (DEBUG && chE != null) {
            chE.post(new 2(b(getCallerStackTraceElement()), str, th));
        }
    }

    public static void w(Throwable th) {
        if (Logable) {
            j.w(M(th), new Object[0]);
        }
        if (DEBUG && chE != null) {
            chE.post(new 3(b(getCallerStackTraceElement()), th));
        }
    }

    public static void wtf(String str) {
        if (Logable) {
            j.o(str, new Object[0]);
        }
        if (DEBUG && chE != null) {
            chE.post(new 4(b(getCallerStackTraceElement()), str));
        }
    }

    public static void wtf(String str, Throwable th) {
        if (Logable) {
            j.o(str + M(th), new Object[0]);
        }
        if (DEBUG && chE != null) {
            chE.post(new 5(b(getCallerStackTraceElement()), str, th));
        }
    }

    public static void wtf(Throwable th) {
        if (DEBUG && chE != null) {
            chE.post(new 6(b(getCallerStackTraceElement()), th));
        }
    }

    private static String M(Throwable th) {
        StringBuilder sb = new StringBuilder();
        sb.append("\r" + th.toString());
        StackTraceElement[] stackTrace = th.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            sb.append("\rat " + stackTraceElement);
        }
        return sb.toString();
    }
}
