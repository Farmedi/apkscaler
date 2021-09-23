package decorate.angel.admission;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.content.ShapeStroke;
import com.airbnb.lottie.value.Keyframe;
import decorate.angel.admission.moshi.JsonReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

class ShapeStrokeParser {

    /* renamed from: ˊ  reason: contains not printable characters */
    private static JsonReader.Options f21 = JsonReader.Options.ˊ(new String[]{"nm", "c", "w", "o", "lc", "lj", "ml", "hd", "d"});

    /* renamed from: ˋ  reason: contains not printable characters */
    private static final JsonReader.Options f22 = JsonReader.Options.ˊ(new String[]{"n", "v"});

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* renamed from: ˊ  reason: contains not printable characters */
    static ShapeStroke m42(JsonReader jsonReader, LottieComposition lottieComposition) throws IOException {
        char c;
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        float f = 0.0f;
        String str = null;
        AnimatableFloatValue animatableFloatValue = null;
        AnimatableColorValue animatableColorValue = null;
        AnimatableFloatValue animatableFloatValue2 = null;
        ShapeStroke.LineCapType lineCapType = null;
        ShapeStroke.LineJoinType lineJoinType = null;
        AnimatableIntegerValue animatableIntegerValue = null;
        while (jsonReader.ʼ()) {
            switch (jsonReader.ʳ(f21)) {
                case 0:
                    str = jsonReader.י();
                    break;
                case 1:
                    animatableColorValue = AnimatableValueParser.ˎ(jsonReader, lottieComposition);
                    break;
                case 2:
                    animatableFloatValue2 = AnimatableValueParser.ᐝ(jsonReader, lottieComposition);
                    break;
                case 3:
                    animatableIntegerValue = AnimatableValueParser.ʽ(jsonReader, lottieComposition);
                    break;
                case 4:
                    lineCapType = ShapeStroke.LineCapType.values()[jsonReader.ﹳ() - 1];
                    break;
                case 5:
                    lineJoinType = ShapeStroke.LineJoinType.values()[jsonReader.ﹳ() - 1];
                    break;
                case 6:
                    f = (float) jsonReader.ˉ();
                    break;
                case 7:
                    z = jsonReader.ʿ();
                    break;
                case 8:
                    jsonReader.ˋ();
                    while (jsonReader.ʼ()) {
                        jsonReader.ˏ();
                        String str2 = null;
                        AnimatableFloatValue animatableFloatValue3 = null;
                        while (jsonReader.ʼ()) {
                            int r2 = jsonReader.ʳ(f22);
                            if (r2 == 0) {
                                str2 = jsonReader.י();
                            } else if (r2 != 1) {
                                jsonReader.ˇ();
                                jsonReader.ˡ();
                            } else {
                                animatableFloatValue3 = AnimatableValueParser.ᐝ(jsonReader, lottieComposition);
                            }
                        }
                        jsonReader.ʻ();
                        str2.hashCode();
                        switch (str2.hashCode()) {
                            case 100:
                                if (str2.equals("d")) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 103:
                                if (str2.equals("g")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 111:
                                if (str2.equals("o")) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                            case 1:
                                lottieComposition.ᐨ(true);
                                arrayList.add(animatableFloatValue3);
                                break;
                            case 2:
                                animatableFloatValue = animatableFloatValue3;
                                break;
                        }
                    }
                    jsonReader.ᐝ();
                    if (arrayList.size() != 1) {
                        break;
                    } else {
                        arrayList.add(arrayList.get(0));
                        break;
                    }
                    break;
                default:
                    jsonReader.ˡ();
                    break;
            }
        }
        if (animatableIntegerValue == null) {
            animatableIntegerValue = new AnimatableIntegerValue(Collections.singletonList(new Keyframe(100)));
        }
        return new ShapeStroke(str, animatableFloatValue, arrayList, animatableColorValue, animatableIntegerValue, animatableFloatValue2, lineCapType, lineJoinType, f, z);
    }
}
