package decorate.angel;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.ShapeGroup;
import decorate.angel.moshi.JsonReader;
import java.io.IOException;
import java.util.ArrayList;

class ShapeGroupParser {

    /* renamed from: ˊ  reason: contains not printable characters */
    private static JsonReader.Options f11 = JsonReader.Options.ˊ(new String[]{"nm", "hd", "it"});

    /* renamed from: ˊ  reason: contains not printable characters */
    static ShapeGroup m28(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        ArrayList arrayList = new ArrayList();
        String str = null;
        boolean z = false;
        while (jsonReader.ʼ()) {
            int r3 = jsonReader.ʳ(f11);
            if (r3 == 0) {
                str = jsonReader.י();
            } else if (r3 == 1) {
                z = jsonReader.ʿ();
            } else if (r3 != 2) {
                jsonReader.ˡ();
            } else {
                jsonReader.ˋ();
                while (jsonReader.ʼ()) {
                    ContentModel r32 = ContentModelParser.ˊ(jsonReader, lottieComposition);
                    if (r32 != null) {
                        arrayList.add(r32);
                    }
                }
                jsonReader.ᐝ();
            }
        }
        return new ShapeGroup(str, arrayList, z);
    }
}
