package decorate.angel.admission;

import android.graphics.Path;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableGradientColorValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.content.GradientFill;
import com.airbnb.lottie.model.content.GradientType;
import com.airbnb.lottie.value.Keyframe;
import decorate.angel.admission.moshi.JsonReader;
import java.io.IOException;
import java.util.Collections;

class GradientFillParser {

    /* renamed from: ˊ  reason: contains not printable characters */
    private static final JsonReader.Options f18 = JsonReader.Options.ˊ(new String[]{"nm", "g", "o", "t", "s", "e", "r", "hd"});

    /* renamed from: ˋ  reason: contains not printable characters */
    private static final JsonReader.Options f19 = JsonReader.Options.ˊ(new String[]{"p", "k"});

    /* renamed from: ˊ  reason: contains not printable characters */
    static GradientFill m36(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        AnimatableIntegerValue animatableIntegerValue = null;
        Path.FillType fillType = Path.FillType.WINDING;
        String str = null;
        GradientType gradientType = null;
        AnimatableGradientColorValue animatableGradientColorValue = null;
        AnimatablePointValue animatablePointValue = null;
        AnimatablePointValue animatablePointValue2 = null;
        boolean z = false;
        while (jsonReader.ʼ()) {
            switch (jsonReader.ʳ(f18)) {
                case 0:
                    str = jsonReader.י();
                    break;
                case 1:
                    int i = -1;
                    jsonReader.ˏ();
                    while (jsonReader.ʼ()) {
                        int r3 = jsonReader.ʳ(f19);
                        if (r3 == 0) {
                            i = jsonReader.ﹳ();
                        } else if (r3 != 1) {
                            jsonReader.ˇ();
                            jsonReader.ˡ();
                        } else {
                            animatableGradientColorValue = AnimatableValueParser.ʼ(jsonReader, lottieComposition, i);
                        }
                    }
                    jsonReader.ʻ();
                    break;
                case 2:
                    animatableIntegerValue = AnimatableValueParser.ʽ(jsonReader, lottieComposition);
                    break;
                case 3:
                    gradientType = jsonReader.ﹳ() == 1 ? GradientType.ʻ : GradientType.ʼ;
                    break;
                case 4:
                    animatablePointValue = AnimatableValueParser.ͺ(jsonReader, lottieComposition);
                    break;
                case 5:
                    animatablePointValue2 = AnimatableValueParser.ͺ(jsonReader, lottieComposition);
                    break;
                case 6:
                    fillType = jsonReader.ﹳ() == 1 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD;
                    break;
                case 7:
                    z = jsonReader.ʿ();
                    break;
                default:
                    jsonReader.ˇ();
                    jsonReader.ˡ();
                    break;
            }
        }
        return new GradientFill(str, gradientType, fillType, animatableGradientColorValue, animatableIntegerValue == null ? new AnimatableIntegerValue(Collections.singletonList(new Keyframe(100))) : animatableIntegerValue, animatablePointValue, animatablePointValue2, (AnimatableFloatValue) null, (AnimatableFloatValue) null, z);
    }
}
