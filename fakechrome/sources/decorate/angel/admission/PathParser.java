package decorate.angel.admission;

import android.graphics.PointF;
import decorate.angel.admission.moshi.JsonReader;
import java.io.IOException;

public class PathParser implements ValueParser<PointF> {

    /* renamed from: ˊ  reason: contains not printable characters */
    public static final PathParser f20 = new PathParser();

    private PathParser() {
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    public PointF m39(JsonReader jsonReader, float f) throws IOException {
        return JsonUtils.ᐝ(jsonReader, f);
    }
}
