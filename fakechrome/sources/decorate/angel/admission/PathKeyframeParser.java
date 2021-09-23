package decorate.angel.admission;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.animation.keyframe.PathKeyframe;
import com.airbnb.lottie.utils.Utils;
import decorate.angel.admission.moshi.JsonReader;
import java.io.IOException;

class PathKeyframeParser {
    /* renamed from: ˊ  reason: contains not printable characters */
    static PathKeyframe m38(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        return new PathKeyframe(lottieComposition, KeyframeParser.ˎ(jsonReader, lottieComposition, Utils.ᐝ(), PathParser.f20, jsonReader.ﹶ() == JsonReader.Token.ʽ, false));
    }
}
