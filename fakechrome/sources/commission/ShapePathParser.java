package commission;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableShapeValue;
import com.airbnb.lottie.model.content.ShapePath;
import commission.moshi.JsonReader;
import java.io.IOException;

class ShapePathParser {

    /* renamed from: ˊ  reason: contains not printable characters */
    static JsonReader.Options f3 = JsonReader.Options.ˊ(new String[]{"nm", "ind", "ks", "hd"});

    /* renamed from: ˊ  reason: contains not printable characters */
    static ShapePath m12(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        int i = 0;
        String str = null;
        AnimatableShapeValue animatableShapeValue = null;
        boolean z = false;
        while (jsonReader.ʼ()) {
            int r4 = jsonReader.ʳ(f3);
            if (r4 == 0) {
                str = jsonReader.י();
            } else if (r4 == 1) {
                i = jsonReader.ﹳ();
            } else if (r4 == 2) {
                animatableShapeValue = AnimatableValueParser.ʾ(jsonReader, lottieComposition);
            } else if (r4 != 3) {
                jsonReader.ˡ();
            } else {
                z = jsonReader.ʿ();
            }
        }
        return new ShapePath(str, i, animatableShapeValue, z);
    }
}
