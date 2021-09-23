package decorate.angel.admission;

import com.airbnb.lottie.model.Font;
import decorate.angel.admission.moshi.JsonReader;
import java.io.IOException;

class FontParser {

    /* renamed from: ˊ  reason: contains not printable characters */
    private static final JsonReader.Options f16 = JsonReader.Options.ˊ(new String[]{"fFamily", "fName", "fStyle", "ascent"});

    /* renamed from: ˊ  reason: contains not printable characters */
    static Font m31(JsonReader jsonReader) throws IOException {
        jsonReader.ˏ();
        String str = null;
        String str2 = null;
        float f = 0.0f;
        String str3 = null;
        while (jsonReader.ʼ()) {
            int r4 = jsonReader.ʳ(f16);
            if (r4 == 0) {
                str = jsonReader.י();
            } else if (r4 == 1) {
                str3 = jsonReader.י();
            } else if (r4 == 2) {
                str2 = jsonReader.י();
            } else if (r4 != 3) {
                jsonReader.ˇ();
                jsonReader.ˡ();
            } else {
                f = (float) jsonReader.ˉ();
            }
        }
        jsonReader.ʻ();
        return new Font(str, str3, str2, f);
    }
}
