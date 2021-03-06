package androidx.core.os;

import android.os.Build;
import androidx.annotation.ChecksSdkIntAtLeast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresOptIn;
import androidx.annotation.RestrictTo;
import androidx.core.view.MotionEventCompat;

public class BuildCompat {

    @RequiresOptIn
    public @interface PrereleaseSdkCheck {
    }

    private BuildCompat() {
    }

    @RestrictTo({RestrictTo.Scope.TESTS})
    protected static boolean isAtLeastPreReleaseCodename(@NonNull String str, @NonNull String str2) {
        if (!"REL".equals(str2) && str2.compareTo(str) >= 0) {
            return true;
        }
        return false;
    }

    @ChecksSdkIntAtLeast(api = MotionEventCompat.AXIS_DISTANCE)
    @Deprecated
    public static boolean isAtLeastN() {
        return Build.VERSION.SDK_INT >= 24;
    }

    @ChecksSdkIntAtLeast(api = 25)
    @Deprecated
    public static boolean isAtLeastNMR1() {
        return Build.VERSION.SDK_INT >= 25;
    }

    @ChecksSdkIntAtLeast(api = MotionEventCompat.AXIS_SCROLL)
    @Deprecated
    public static boolean isAtLeastO() {
        return Build.VERSION.SDK_INT >= 26;
    }

    @ChecksSdkIntAtLeast(api = MotionEventCompat.AXIS_RELATIVE_X)
    @Deprecated
    public static boolean isAtLeastOMR1() {
        return Build.VERSION.SDK_INT >= 27;
    }

    @ChecksSdkIntAtLeast(api = MotionEventCompat.AXIS_RELATIVE_Y)
    @Deprecated
    public static boolean isAtLeastP() {
        return Build.VERSION.SDK_INT >= 28;
    }

    @ChecksSdkIntAtLeast(api = 29)
    @Deprecated
    public static boolean isAtLeastQ() {
        return Build.VERSION.SDK_INT >= 29;
    }

    @ChecksSdkIntAtLeast(api = 30)
    @Deprecated
    public static boolean isAtLeastR() {
        return Build.VERSION.SDK_INT >= 30;
    }

    @ChecksSdkIntAtLeast(api = 31, codename = "S")
    public static boolean isAtLeastS() {
        return Build.VERSION.SDK_INT >= 31 || isAtLeastPreReleaseCodename("S", Build.VERSION.CODENAME);
    }

    @ChecksSdkIntAtLeast(codename = "T")
    @PrereleaseSdkCheck
    public static boolean isAtLeastT() {
        return isAtLeastPreReleaseCodename("T", Build.VERSION.CODENAME);
    }
}
