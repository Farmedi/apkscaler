package androidx.core.graphics;

import android.graphics.BlendMode;
import android.graphics.PorterDuff;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.MotionEventCompat;

class BlendModeUtils {

    /* renamed from: androidx.core.graphics.BlendModeUtils$1  reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$core$graphics$BlendModeCompat = new int[BlendModeCompat.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(58:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|(3:57|58|60)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(60:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|60) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0056 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0062 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x006e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x007a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0086 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0092 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x009e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x00aa */
        /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x00b6 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x00c2 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:35:0x00ce */
        /* JADX WARNING: Missing exception handler attribute for start block: B:37:0x00da */
        /* JADX WARNING: Missing exception handler attribute for start block: B:39:0x00e6 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:41:0x00f2 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:43:0x00fe */
        /* JADX WARNING: Missing exception handler attribute for start block: B:45:0x010a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:47:0x0116 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:49:0x0122 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:51:0x012e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:53:0x013a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:55:0x0146 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:57:0x0152 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
            // Method dump skipped, instructions count: 351
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.BlendModeUtils.AnonymousClass1.<clinit>():void");
        }
    }

    @Nullable
    @RequiresApi(29)
    static BlendMode obtainBlendModeFromCompat(@NonNull BlendModeCompat blendModeCompat) {
        switch (AnonymousClass1.$SwitchMap$androidx$core$graphics$BlendModeCompat[blendModeCompat.ordinal()]) {
            case 1:
                return BlendMode.CLEAR;
            case 2:
                return BlendMode.SRC;
            case 3:
                return BlendMode.DST;
            case 4:
                return BlendMode.SRC_OVER;
            case 5:
                return BlendMode.DST_OVER;
            case 6:
                return BlendMode.SRC_IN;
            case 7:
                return BlendMode.DST_IN;
            case 8:
                return BlendMode.SRC_OUT;
            case 9:
                return BlendMode.DST_OUT;
            case 10:
                return BlendMode.SRC_ATOP;
            case 11:
                return BlendMode.DST_ATOP;
            case MotionEventCompat.AXIS_RX /*{ENCODED_INT: 12}*/:
                return BlendMode.XOR;
            case MotionEventCompat.AXIS_RY /*{ENCODED_INT: 13}*/:
                return BlendMode.PLUS;
            case MotionEventCompat.AXIS_RZ /*{ENCODED_INT: 14}*/:
                return BlendMode.MODULATE;
            case MotionEventCompat.AXIS_HAT_X /*{ENCODED_INT: 15}*/:
                return BlendMode.SCREEN;
            case 16:
                return BlendMode.OVERLAY;
            case MotionEventCompat.AXIS_LTRIGGER /*{ENCODED_INT: 17}*/:
                return BlendMode.DARKEN;
            case MotionEventCompat.AXIS_RTRIGGER /*{ENCODED_INT: 18}*/:
                return BlendMode.LIGHTEN;
            case 19:
                return BlendMode.COLOR_DODGE;
            case MotionEventCompat.AXIS_RUDDER /*{ENCODED_INT: 20}*/:
                return BlendMode.COLOR_BURN;
            case MotionEventCompat.AXIS_WHEEL /*{ENCODED_INT: 21}*/:
                return BlendMode.HARD_LIGHT;
            case MotionEventCompat.AXIS_GAS /*{ENCODED_INT: 22}*/:
                return BlendMode.SOFT_LIGHT;
            case 23:
                return BlendMode.DIFFERENCE;
            case MotionEventCompat.AXIS_DISTANCE /*{ENCODED_INT: 24}*/:
                return BlendMode.EXCLUSION;
            case 25:
                return BlendMode.MULTIPLY;
            case MotionEventCompat.AXIS_SCROLL /*{ENCODED_INT: 26}*/:
                return BlendMode.HUE;
            case MotionEventCompat.AXIS_RELATIVE_X /*{ENCODED_INT: 27}*/:
                return BlendMode.SATURATION;
            case MotionEventCompat.AXIS_RELATIVE_Y /*{ENCODED_INT: 28}*/:
                return BlendMode.COLOR;
            case 29:
                return BlendMode.LUMINOSITY;
            default:
                return null;
        }
    }

    @Nullable
    static PorterDuff.Mode obtainPorterDuffFromCompat(@Nullable BlendModeCompat blendModeCompat) {
        if (blendModeCompat == null) {
            return null;
        }
        switch (AnonymousClass1.$SwitchMap$androidx$core$graphics$BlendModeCompat[blendModeCompat.ordinal()]) {
            case 1:
                return PorterDuff.Mode.CLEAR;
            case 2:
                return PorterDuff.Mode.SRC;
            case 3:
                return PorterDuff.Mode.DST;
            case 4:
                return PorterDuff.Mode.SRC_OVER;
            case 5:
                return PorterDuff.Mode.DST_OVER;
            case 6:
                return PorterDuff.Mode.SRC_IN;
            case 7:
                return PorterDuff.Mode.DST_IN;
            case 8:
                return PorterDuff.Mode.SRC_OUT;
            case 9:
                return PorterDuff.Mode.DST_OUT;
            case 10:
                return PorterDuff.Mode.SRC_ATOP;
            case 11:
                return PorterDuff.Mode.DST_ATOP;
            case MotionEventCompat.AXIS_RX /*{ENCODED_INT: 12}*/:
                return PorterDuff.Mode.XOR;
            case MotionEventCompat.AXIS_RY /*{ENCODED_INT: 13}*/:
                return PorterDuff.Mode.ADD;
            case MotionEventCompat.AXIS_RZ /*{ENCODED_INT: 14}*/:
                return PorterDuff.Mode.MULTIPLY;
            case MotionEventCompat.AXIS_HAT_X /*{ENCODED_INT: 15}*/:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.OVERLAY;
            case MotionEventCompat.AXIS_LTRIGGER /*{ENCODED_INT: 17}*/:
                return PorterDuff.Mode.DARKEN;
            case MotionEventCompat.AXIS_RTRIGGER /*{ENCODED_INT: 18}*/:
                return PorterDuff.Mode.LIGHTEN;
            default:
                return null;
        }
    }

    private BlendModeUtils() {
    }
}
