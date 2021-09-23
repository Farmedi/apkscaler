package decorate;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import decorate.moshi.JsonReader;
import java.io.IOException;

class ShapeTrimPathParser {

    /* renamed from: ˊ  reason: contains not printable characters */
    private static JsonReader.Options f6 = JsonReader.Options.ˊ(new String[]{"s", "e", "o", "nm", "m", "hd"});

    /* renamed from: ˊ  reason: contains not printable characters */
    static ShapeTrimPath m22(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        boolean z = false;
        String str = null;
        ShapeTrimPath.Type type = null;
        AnimatableFloatValue animatableFloatValue = null;
        AnimatableFloatValue animatableFloatValue2 = null;
        AnimatableFloatValue animatableFloatValue3 = null;
        while (jsonReader.ʼ()) {
            int r1 = jsonReader.ʳ(f6);
            if (r1 == 0) {
                animatableFloatValue = AnimatableValueParser.ʻ(jsonReader, lottieComposition, false);
            } else if (r1 == 1) {
                animatableFloatValue2 = AnimatableValueParser.ʻ(jsonReader, lottieComposition, false);
            } else if (r1 == 2) {
                animatableFloatValue3 = AnimatableValueParser.ʻ(jsonReader, lottieComposition, false);
            } else if (r1 == 3) {
                str = jsonReader.י();
            } else if (r1 == 4) {
                type = ShapeTrimPath.Type.ˏ(jsonReader.ﹳ());
            } else if (r1 != 5) {
                jsonReader.ˡ();
            } else {
                z = jsonReader.ʿ();
            }
        }
        return new ShapeTrimPath(str, type, animatableFloatValue, animatableFloatValue2, animatableFloatValue3, z);
    }
}
