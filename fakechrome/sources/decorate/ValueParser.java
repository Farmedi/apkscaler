package decorate;

import decorate.moshi.JsonReader;
import java.io.IOException;

interface ValueParser<V> {
    /* renamed from: ˊ  reason: contains not printable characters */
    V m23(JsonReader jsonReader, float f) throws IOException;
}
