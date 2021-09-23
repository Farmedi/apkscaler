package ecosystem;

import android.graphics.PointF;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.collection.SparseArrayCompat;
import androidx.core.view.animation.PathInterpolatorCompat;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import ecosystem.moshi.JsonReader;
import java.io.IOException;
import java.lang.ref.WeakReference;

class KeyframeParser {

    /* renamed from: ˊ  reason: contains not printable characters */
    private static final Interpolator f25 = new LinearInterpolator();

    /* renamed from: ˋ  reason: contains not printable characters */
    private static SparseArrayCompat<WeakReference<Interpolator>> f26;

    /* renamed from: ˎ  reason: contains not printable characters */
    static JsonReader.Options f27 = JsonReader.Options.ˊ(new String[]{"t", "s", "e", "o", "i", "h", "to", "ti"});

    /* renamed from: ˏ  reason: contains not printable characters */
    static JsonReader.Options f28 = JsonReader.Options.ˊ(new String[]{"x", "y"});

    KeyframeParser() {
    }

    /* renamed from: ʻ  reason: contains not printable characters */
    private static <T> Keyframe<T> m46(JsonReader jsonReader, float f, ValueParser<T> valueParser) throws IOException {
        return new Keyframe<>(valueParser.ˊ(jsonReader, f));
    }

    /* renamed from: ʼ  reason: contains not printable characters */
    private static SparseArrayCompat<WeakReference<Interpolator>> m47() {
        if (f26 == null) {
            f26 = new SparseArrayCompat<>();
        }
        return f26;
    }

    /* renamed from: ʽ  reason: contains not printable characters */
    private static void m48(int i, WeakReference<Interpolator> weakReference) {
        synchronized (KeyframeParser.class) {
            f26.ʿ(i, weakReference);
        }
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    private static WeakReference<Interpolator> m49(int i) {
        WeakReference<Interpolator> weakReference;
        synchronized (KeyframeParser.class) {
            weakReference = (WeakReference) m47().ʼ(i);
        }
        return weakReference;
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    private static Interpolator m50(PointF pointF, PointF pointF2) {
        Interpolator interpolator;
        pointF.x = MiscUtils.ˎ(pointF.x, -1.0f, 1.0f);
        pointF.y = MiscUtils.ˎ(pointF.y, -100.0f, 100.0f);
        pointF2.x = MiscUtils.ˎ(pointF2.x, -1.0f, 1.0f);
        float r0 = MiscUtils.ˎ(pointF2.y, -100.0f, 100.0f);
        pointF2.y = r0;
        int r02 = Utils.ͺ(pointF.x, pointF.y, pointF2.x, r0);
        WeakReference<Interpolator> r1 = m49(r02);
        Interpolator interpolator2 = r1 != null ? r1.get() : null;
        if (r1 == null || interpolator2 == null) {
            try {
                interpolator = PathInterpolatorCompat.ˊ(pointF.x, pointF.y, pointF2.x, pointF2.y);
            } catch (IllegalArgumentException e) {
                if ("The Path cannot loop back on itself.".equals(e.getMessage())) {
                    interpolator = PathInterpolatorCompat.ˊ(Math.min(pointF.x, 1.0f), pointF.y, Math.max(pointF2.x, 0.0f), pointF2.y);
                } else {
                    interpolator = new LinearInterpolator();
                }
            }
            interpolator2 = interpolator;
            try {
                m48(r02, new WeakReference(interpolator2));
            } catch (ArrayIndexOutOfBoundsException unused) {
            }
        }
        return interpolator2;
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    static <T> Keyframe<T> m51(JsonReader jsonReader, LottieComposition lottieComposition, float f, ValueParser<T> valueParser, boolean z, boolean z2) throws IOException {
        if (z && z2) {
            return m53(lottieComposition, jsonReader, f, valueParser);
        }
        if (z) {
            return m52(lottieComposition, jsonReader, f, valueParser);
        }
        return m46(jsonReader, f, valueParser);
    }

    /* renamed from: ˏ  reason: contains not printable characters */
    private static <T> Keyframe<T> m52(LottieComposition lottieComposition, JsonReader jsonReader, float f, ValueParser<T> valueParser) throws IOException {
        Interpolator interpolator;
        Object obj;
        Interpolator interpolator2;
        jsonReader.ˏ();
        PointF pointF = null;
        boolean z = false;
        Object obj2 = null;
        Object obj3 = null;
        PointF pointF2 = null;
        PointF pointF3 = null;
        float f2 = 0.0f;
        PointF pointF4 = null;
        while (jsonReader.ʼ()) {
            switch (jsonReader.ʳ(f27)) {
                case 0:
                    f2 = (float) jsonReader.ˉ();
                    break;
                case 1:
                    obj3 = valueParser.ˊ(jsonReader, f);
                    break;
                case 2:
                    obj2 = valueParser.ˊ(jsonReader, f);
                    break;
                case 3:
                    pointF = JsonUtils.ᐝ(jsonReader, 1.0f);
                    break;
                case 4:
                    pointF4 = JsonUtils.ᐝ(jsonReader, 1.0f);
                    break;
                case 5:
                    if (jsonReader.ﹳ() != 1) {
                        z = false;
                        break;
                    } else {
                        z = true;
                        break;
                    }
                case 6:
                    pointF2 = JsonUtils.ᐝ(jsonReader, f);
                    break;
                case 7:
                    pointF3 = JsonUtils.ᐝ(jsonReader, f);
                    break;
                default:
                    jsonReader.ˡ();
                    break;
            }
        }
        jsonReader.ʻ();
        if (z) {
            interpolator = f25;
            obj = obj3;
        } else {
            if (pointF == null || pointF4 == null) {
                interpolator2 = f25;
            } else {
                interpolator2 = m50(pointF, pointF4);
            }
            interpolator = interpolator2;
            obj = obj2;
        }
        Keyframe<T> keyframe = new Keyframe<>(lottieComposition, obj3, obj, interpolator, f2, (Float) null);
        keyframe.ˌ = pointF2;
        keyframe.ˍ = pointF3;
        return keyframe;
    }

    /* renamed from: ᐝ  reason: contains not printable characters */
    private static <T> Keyframe<T> m53(LottieComposition lottieComposition, JsonReader jsonReader, float f, ValueParser<T> valueParser) throws IOException {
        Interpolator interpolator;
        Interpolator interpolator2;
        Object obj;
        Interpolator interpolator3;
        PointF pointF;
        Keyframe<T> keyframe;
        PointF pointF2;
        float f2;
        PointF pointF3;
        float f3;
        jsonReader.ˏ();
        PointF pointF4 = null;
        boolean z = false;
        PointF pointF5 = null;
        PointF pointF6 = null;
        PointF pointF7 = null;
        Object obj2 = null;
        PointF pointF8 = null;
        PointF pointF9 = null;
        PointF pointF10 = null;
        float f4 = 0.0f;
        PointF pointF11 = null;
        Object obj3 = null;
        while (jsonReader.ʼ()) {
            switch (jsonReader.ʳ(f27)) {
                case 0:
                    pointF2 = pointF4;
                    f4 = (float) jsonReader.ˉ();
                    break;
                case 1:
                    pointF2 = pointF4;
                    obj2 = valueParser.ˊ(jsonReader, f);
                    break;
                case 2:
                    pointF2 = pointF4;
                    obj3 = valueParser.ˊ(jsonReader, f);
                    break;
                case 3:
                    pointF2 = pointF4;
                    f2 = f4;
                    if (jsonReader.ﹶ() != JsonReader.Token.ʽ) {
                        pointF5 = JsonUtils.ᐝ(jsonReader, f);
                        f4 = f2;
                        pointF11 = pointF11;
                        break;
                    } else {
                        jsonReader.ˏ();
                        float f5 = 0.0f;
                        float f6 = 0.0f;
                        float f7 = 0.0f;
                        float f8 = 0.0f;
                        while (jsonReader.ʼ()) {
                            int r14 = jsonReader.ʳ(f28);
                            if (r14 != 0) {
                                if (r14 != 1) {
                                    jsonReader.ˡ();
                                } else if (jsonReader.ﹶ() == JsonReader.Token.ʿ) {
                                    f8 = (float) jsonReader.ˉ();
                                    f6 = f8;
                                } else {
                                    jsonReader.ˋ();
                                    f6 = (float) jsonReader.ˉ();
                                    f8 = (float) jsonReader.ˉ();
                                    jsonReader.ᐝ();
                                }
                            } else if (jsonReader.ﹶ() == JsonReader.Token.ʿ) {
                                f7 = (float) jsonReader.ˉ();
                                f5 = f7;
                            } else {
                                jsonReader.ˋ();
                                f5 = (float) jsonReader.ˉ();
                                f7 = (float) jsonReader.ˉ();
                                jsonReader.ᐝ();
                            }
                        }
                        PointF pointF12 = new PointF(f5, f6);
                        PointF pointF13 = new PointF(f7, f8);
                        jsonReader.ʻ();
                        pointF8 = pointF13;
                        pointF7 = pointF12;
                        pointF11 = pointF11;
                        f4 = f2;
                        break;
                    }
                case 4:
                    if (jsonReader.ﹶ() != JsonReader.Token.ʽ) {
                        pointF2 = pointF4;
                        pointF6 = JsonUtils.ᐝ(jsonReader, f);
                        break;
                    } else {
                        jsonReader.ˏ();
                        float f9 = 0.0f;
                        float f10 = 0.0f;
                        float f11 = 0.0f;
                        float f12 = 0.0f;
                        while (jsonReader.ʼ()) {
                            int r15 = jsonReader.ʳ(f28);
                            if (r15 != 0) {
                                pointF3 = pointF4;
                                if (r15 != 1) {
                                    jsonReader.ˡ();
                                } else if (jsonReader.ﹶ() == JsonReader.Token.ʿ) {
                                    f12 = (float) jsonReader.ˉ();
                                    f4 = f4;
                                    f10 = f12;
                                } else {
                                    f3 = f4;
                                    jsonReader.ˋ();
                                    f10 = (float) jsonReader.ˉ();
                                    f12 = (float) jsonReader.ˉ();
                                    jsonReader.ᐝ();
                                    f4 = f3;
                                }
                            } else {
                                pointF3 = pointF4;
                                f3 = f4;
                                if (jsonReader.ﹶ() == JsonReader.Token.ʿ) {
                                    f11 = (float) jsonReader.ˉ();
                                    f4 = f3;
                                    f9 = f11;
                                } else {
                                    jsonReader.ˋ();
                                    f9 = (float) jsonReader.ˉ();
                                    f11 = (float) jsonReader.ˉ();
                                    jsonReader.ᐝ();
                                    f4 = f3;
                                }
                            }
                            pointF11 = pointF11;
                            pointF4 = pointF3;
                        }
                        pointF2 = pointF4;
                        f2 = f4;
                        PointF pointF14 = new PointF(f9, f10);
                        PointF pointF15 = new PointF(f11, f12);
                        jsonReader.ʻ();
                        pointF10 = pointF15;
                        pointF9 = pointF14;
                        f4 = f2;
                        break;
                    }
                case 5:
                    if (jsonReader.ﹳ() == 1) {
                        z = true;
                        continue;
                    } else {
                        z = false;
                    }
                case 6:
                    pointF11 = JsonUtils.ᐝ(jsonReader, f);
                    continue;
                case 7:
                    pointF4 = JsonUtils.ᐝ(jsonReader, f);
                    continue;
                default:
                    pointF2 = pointF4;
                    jsonReader.ˡ();
                    break;
            }
            pointF4 = pointF2;
        }
        jsonReader.ʻ();
        if (z) {
            interpolator3 = f25;
            obj = obj2;
        } else {
            if (pointF5 != null && pointF6 != null) {
                interpolator3 = m50(pointF5, pointF6);
            } else if (pointF7 == null || pointF8 == null || pointF9 == null || pointF10 == null) {
                interpolator3 = f25;
            } else {
                interpolator2 = m50(pointF7, pointF9);
                interpolator = m50(pointF8, pointF10);
                obj = obj3;
                interpolator3 = null;
                if (interpolator2 != null || interpolator == null) {
                    pointF = pointF11;
                    keyframe = new Keyframe<>(lottieComposition, obj2, obj, interpolator3, f4, (Float) null);
                } else {
                    pointF = pointF11;
                    keyframe = new Keyframe<>(lottieComposition, obj2, obj, interpolator2, interpolator, f4, (Float) null);
                }
                keyframe.ˌ = pointF;
                keyframe.ˍ = pointF4;
                return keyframe;
            }
            obj = obj3;
        }
        interpolator2 = null;
        interpolator = null;
        if (interpolator2 != null) {
        }
        pointF = pointF11;
        keyframe = new Keyframe<>(lottieComposition, obj2, obj, interpolator3, f4, (Float) null);
        keyframe.ˌ = pointF;
        keyframe.ˍ = pointF4;
        return keyframe;
    }
}
