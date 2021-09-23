package decorate.angel;

import android.graphics.Color;
import android.graphics.Rect;
import android.view.animation.Interpolator;
import androidx.core.view.MotionEventCompat;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextFrame;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import decorate.angel.moshi.JsonReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class LayerParser {

    /* renamed from: ˊ  reason: contains not printable characters */
    private static final JsonReader.Options f8 = JsonReader.Options.ˊ(new String[]{"nm", "ind", "refId", "ty", "parent", "sw", "sh", "sc", "ks", "tt", "masksProperties", "shapes", "t", "ef", "sr", "st", "w", "h", "ip", "op", "tm", "cl", "hd"});

    /* renamed from: ˋ  reason: contains not printable characters */
    private static final JsonReader.Options f9 = JsonReader.Options.ˊ(new String[]{"d", "a"});

    /* renamed from: ˎ  reason: contains not printable characters */
    private static final JsonReader.Options f10 = JsonReader.Options.ˊ(new String[]{"nm"});

    /* renamed from: ˊ  reason: contains not printable characters */
    public static Layer m26(LottieComposition lottieComposition) {
        Rect r3 = lottieComposition.ˋ();
        return new Layer(Collections.emptyList(), lottieComposition, "__container", -1, Layer.LayerType.ʻ, -1, (String) null, Collections.emptyList(), new AnimatableTransform(), 0, 0, 0, 0.0f, 0.0f, r3.width(), r3.height(), (AnimatableTextFrame) null, (AnimatableTextProperties) null, Collections.emptyList(), Layer.MatteType.ʻ, (AnimatableFloatValue) null, false);
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    public static Layer m27(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        ArrayList arrayList;
        ArrayList arrayList2;
        float f;
        Enum r1 = Layer.MatteType.ʻ;
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        jsonReader.ˏ();
        Float valueOf = Float.valueOf(1.0f);
        Float valueOf2 = Float.valueOf(0.0f);
        Enum r31 = r1;
        float f2 = 1.0f;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        boolean z = false;
        Layer.LayerType layerType = null;
        String str = null;
        AnimatableTransform animatableTransform = null;
        AnimatableTextFrame animatableTextFrame = null;
        AnimatableTextProperties animatableTextProperties = null;
        AnimatableFloatValue animatableFloatValue = null;
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        long j = -1;
        long j2 = 0;
        String str2 = null;
        String str3 = "UNSET";
        while (jsonReader.ʼ()) {
            switch (jsonReader.ʳ(f8)) {
                case 0:
                    str3 = jsonReader.י();
                    break;
                case 1:
                    j2 = (long) jsonReader.ﹳ();
                    break;
                case 2:
                    str = jsonReader.י();
                    break;
                case 3:
                    int r4 = jsonReader.ﹳ();
                    layerType = Layer.LayerType.ʿ;
                    if (r4 >= layerType.ordinal()) {
                        break;
                    } else {
                        layerType = Layer.LayerType.values()[r4];
                        break;
                    }
                case 4:
                    j = (long) jsonReader.ﹳ();
                    break;
                case 5:
                    i = (int) (((float) jsonReader.ﹳ()) * Utils.ᐝ());
                    break;
                case 6:
                    i2 = (int) (((float) jsonReader.ﹳ()) * Utils.ᐝ());
                    break;
                case 7:
                    i3 = Color.parseColor(jsonReader.י());
                    break;
                case 8:
                    animatableTransform = AnimatableTransformParser.ʼ(jsonReader, lottieComposition);
                    break;
                case 9:
                    int r42 = jsonReader.ﹳ();
                    if (r42 < Layer.MatteType.values().length) {
                        r31 = Layer.MatteType.values()[r42];
                        int i6 = 1.ˊ[r31.ordinal()];
                        if (i6 == 1) {
                            lottieComposition.ˊ("Unsupported matte type: Luma");
                        } else if (i6 == 2) {
                            lottieComposition.ˊ("Unsupported matte type: Luma Inverted");
                        }
                        lottieComposition.ˑ(1);
                        break;
                    } else {
                        lottieComposition.ˊ("Unsupported matte type: " + r42);
                        break;
                    }
                case 10:
                    jsonReader.ˋ();
                    while (jsonReader.ʼ()) {
                        arrayList3.add(MaskParser.ˊ(jsonReader, lottieComposition));
                    }
                    lottieComposition.ˑ(arrayList3.size());
                    jsonReader.ᐝ();
                    break;
                case 11:
                    jsonReader.ˋ();
                    while (jsonReader.ʼ()) {
                        ContentModel r43 = ContentModelParser.ˊ(jsonReader, lottieComposition);
                        if (r43 != null) {
                            arrayList4.add(r43);
                        }
                    }
                    jsonReader.ᐝ();
                    break;
                case MotionEventCompat.AXIS_RX:
                    jsonReader.ˏ();
                    while (jsonReader.ʼ()) {
                        int r44 = jsonReader.ʳ(f9);
                        if (r44 == 0) {
                            animatableTextFrame = AnimatableValueParser.ˏ(jsonReader, lottieComposition);
                        } else if (r44 != 1) {
                            jsonReader.ˇ();
                            jsonReader.ˡ();
                        } else {
                            jsonReader.ˋ();
                            if (jsonReader.ʼ()) {
                                animatableTextProperties = AnimatableTextPropertiesParser.ˊ(jsonReader, lottieComposition);
                            }
                            while (jsonReader.ʼ()) {
                                jsonReader.ˡ();
                            }
                            jsonReader.ᐝ();
                        }
                    }
                    jsonReader.ʻ();
                    break;
                case MotionEventCompat.AXIS_RY:
                    jsonReader.ˋ();
                    ArrayList arrayList5 = new ArrayList();
                    while (jsonReader.ʼ()) {
                        jsonReader.ˏ();
                        while (jsonReader.ʼ()) {
                            if (jsonReader.ʳ(f10) != 0) {
                                jsonReader.ˇ();
                                jsonReader.ˡ();
                            } else {
                                arrayList5.add(jsonReader.י());
                            }
                        }
                        jsonReader.ʻ();
                    }
                    jsonReader.ᐝ();
                    lottieComposition.ˊ("Lottie doesn't support layer effects. If you are using them for  fills, strokes, trim paths etc. then try adding them directly as contents  in your shape. Found: " + arrayList5);
                    break;
                case MotionEventCompat.AXIS_RZ:
                    f2 = (float) jsonReader.ˉ();
                    break;
                case MotionEventCompat.AXIS_HAT_X:
                    f5 = (float) jsonReader.ˉ();
                    break;
                case 16:
                    i4 = (int) (((float) jsonReader.ﹳ()) * Utils.ᐝ());
                    break;
                case MotionEventCompat.AXIS_LTRIGGER:
                    i5 = (int) (((float) jsonReader.ﹳ()) * Utils.ᐝ());
                    break;
                case MotionEventCompat.AXIS_RTRIGGER:
                    f3 = (float) jsonReader.ˉ();
                    break;
                case 19:
                    f4 = (float) jsonReader.ˉ();
                    break;
                case MotionEventCompat.AXIS_RUDDER:
                    animatableFloatValue = AnimatableValueParser.ʻ(jsonReader, lottieComposition, false);
                    break;
                case MotionEventCompat.AXIS_WHEEL:
                    str2 = jsonReader.י();
                    break;
                case MotionEventCompat.AXIS_GAS:
                    z = jsonReader.ʿ();
                    break;
                default:
                    jsonReader.ˇ();
                    jsonReader.ˡ();
                    break;
            }
        }
        jsonReader.ʻ();
        float f6 = f3 / f2;
        float f7 = f4 / f2;
        ArrayList arrayList6 = new ArrayList();
        if (f6 > 0.0f) {
            arrayList = arrayList3;
            arrayList2 = arrayList6;
            arrayList2.add(new Keyframe(lottieComposition, valueOf2, valueOf2, (Interpolator) null, 0.0f, Float.valueOf(f6)));
            f = 0.0f;
        } else {
            arrayList = arrayList3;
            arrayList2 = arrayList6;
            f = 0.0f;
        }
        if (f7 <= f) {
            f7 = lottieComposition.ʻ();
        }
        arrayList2.add(new Keyframe(lottieComposition, valueOf, valueOf, (Interpolator) null, f6, Float.valueOf(f7)));
        arrayList2.add(new Keyframe(lottieComposition, valueOf2, valueOf2, (Interpolator) null, f7, Float.valueOf(Float.MAX_VALUE)));
        if (str3.endsWith(".ai") || "ai".equals(str2)) {
            lottieComposition.ˊ("Convert your Illustrator layers to shape layers.");
        }
        return new Layer(arrayList4, lottieComposition, str3, j2, layerType, j, str, arrayList, animatableTransform, i, i2, i3, f2, f5, i4, i5, animatableTextFrame, animatableTextProperties, arrayList2, r31, animatableFloatValue, z);
    }
}
