package decorate;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.animation.keyframe.PathKeyframe;
import com.airbnb.lottie.value.Keyframe;
import decorate.moshi.JsonReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class KeyframesParser {

    /* renamed from: ˊ  reason: contains not printable characters */
    static JsonReader.Options f5 = JsonReader.Options.ˊ(new String[]{"k"});

    /* renamed from: ˊ  reason: contains not printable characters */
    static <T> List<Keyframe<T>> m17(JsonReader jsonReader, LottieComposition lottieComposition, float f, ValueParser<T> valueParser, boolean z) throws IOException {
        ArrayList arrayList = new ArrayList();
        if (jsonReader.ﹶ() == JsonReader.Token.ʾ) {
            lottieComposition.ˊ("Lottie doesn't support expressions.");
            return arrayList;
        }
        jsonReader.ˏ();
        while (jsonReader.ʼ()) {
            if (jsonReader.ʳ(f5) != 0) {
                jsonReader.ˡ();
            } else if (jsonReader.ﹶ() == JsonReader.Token.ʻ) {
                jsonReader.ˋ();
                if (jsonReader.ﹶ() == JsonReader.Token.ʿ) {
                    arrayList.add(KeyframeParser.ˎ(jsonReader, lottieComposition, f, valueParser, false, z));
                } else {
                    while (jsonReader.ʼ()) {
                        arrayList.add(KeyframeParser.ˎ(jsonReader, lottieComposition, f, valueParser, true, z));
                    }
                }
                jsonReader.ᐝ();
            } else {
                arrayList.add(KeyframeParser.ˎ(jsonReader, lottieComposition, f, valueParser, false, z));
            }
        }
        jsonReader.ʻ();
        m18(arrayList);
        return arrayList;
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    public static <T> void m18(List<? extends Keyframe<T>> list) {
        int i;
        Object obj;
        int size = list.size();
        int i2 = 0;
        while (true) {
            i = size - 1;
            if (i2 >= i) {
                break;
            }
            PathKeyframe pathKeyframe = (Keyframe) list.get(i2);
            i2++;
            Keyframe keyframe = (Keyframe) list.get(i2);
            ((Keyframe) pathKeyframe).ʽ = Float.valueOf(keyframe.ʼ);
            if (((Keyframe) pathKeyframe).ˎ == null && (obj = keyframe.ˋ) != null) {
                ((Keyframe) pathKeyframe).ˎ = obj;
                if (pathKeyframe instanceof PathKeyframe) {
                    pathKeyframe.ͺ();
                }
            }
        }
        Keyframe keyframe2 = (Keyframe) list.get(i);
        if ((keyframe2.ˋ == null || keyframe2.ˎ == null) && list.size() > 1) {
            list.remove(keyframe2);
        }
    }
}
