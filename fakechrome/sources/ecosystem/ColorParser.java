package ecosystem;

import android.graphics.Color;
import ecosystem.moshi.JsonReader;
import java.io.IOException;

public class ColorParser implements ValueParser<Integer> {

    /* renamed from: ˊ  reason: contains not printable characters */
    public static final ColorParser f24 = new ColorParser();

    private ColorParser() {
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    public Integer m44(JsonReader jsonReader, float f) throws IOException {
        boolean z = jsonReader.ﹶ() == JsonReader.Token.ʻ;
        if (z) {
            jsonReader.ˋ();
        }
        double r0 = jsonReader.ˉ();
        double r2 = jsonReader.ˉ();
        double r4 = jsonReader.ˉ();
        double r6 = jsonReader.ﹶ() == JsonReader.Token.ʿ ? jsonReader.ˉ() : 1.0d;
        if (z) {
            jsonReader.ᐝ();
        }
        if (r0 <= 1.0d && r2 <= 1.0d && r4 <= 1.0d) {
            r0 *= 255.0d;
            r2 *= 255.0d;
            r4 *= 255.0d;
            if (r6 <= 1.0d) {
                r6 *= 255.0d;
            }
        }
        return Integer.valueOf(Color.argb((int) r6, (int) r0, (int) r2, (int) r4));
    }
}
