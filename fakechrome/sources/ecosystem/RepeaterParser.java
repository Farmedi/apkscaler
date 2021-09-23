package ecosystem;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.Repeater;
import ecosystem.moshi.JsonReader;
import java.io.IOException;

class RepeaterParser {

    /* renamed from: ˊ  reason: contains not printable characters */
    private static JsonReader.Options f29 = JsonReader.Options.ˊ(new String[]{"nm", "c", "o", "tr", "hd"});

    /* renamed from: ˊ  reason: contains not printable characters */
    static Repeater m54(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        boolean z = false;
        String str = null;
        AnimatableFloatValue animatableFloatValue = null;
        AnimatableFloatValue animatableFloatValue2 = null;
        AnimatableTransform animatableTransform = null;
        while (jsonReader.ʼ()) {
            int r1 = jsonReader.ʳ(f29);
            if (r1 == 0) {
                str = jsonReader.י();
            } else if (r1 == 1) {
                animatableFloatValue = AnimatableValueParser.ʻ(jsonReader, lottieComposition, false);
            } else if (r1 == 2) {
                animatableFloatValue2 = AnimatableValueParser.ʻ(jsonReader, lottieComposition, false);
            } else if (r1 == 3) {
                animatableTransform = AnimatableTransformParser.ʼ(jsonReader, lottieComposition);
            } else if (r1 != 4) {
                jsonReader.ˡ();
            } else {
                z = jsonReader.ʿ();
            }
        }
        return new Repeater(str, animatableFloatValue, animatableFloatValue2, animatableTransform, z);
    }
}
