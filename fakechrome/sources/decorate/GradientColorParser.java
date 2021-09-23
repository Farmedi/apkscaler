package decorate;

import android.graphics.Color;
import com.airbnb.lottie.model.content.GradientColor;
import com.airbnb.lottie.utils.MiscUtils;
import decorate.moshi.JsonReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GradientColorParser implements ValueParser<GradientColor> {

    /* renamed from: ˊ  reason: contains not printable characters */
    private int f4;

    public GradientColorParser(int i) {
        this.f4 = i;
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    private void m13(GradientColor gradientColor, List<Float> list) {
        int i = this.f4 * 4;
        if (list.size() > i) {
            int size = (list.size() - i) / 2;
            double[] dArr = new double[size];
            double[] dArr2 = new double[size];
            int i2 = 0;
            while (i < list.size()) {
                if (i % 2 == 0) {
                    dArr[i2] = (double) list.get(i).floatValue();
                } else {
                    dArr2[i2] = (double) list.get(i).floatValue();
                    i2++;
                }
                i++;
            }
            for (int i3 = 0; i3 < gradientColor.ˎ(); i3++) {
                int i4 = gradientColor.ˊ()[i3];
                gradientColor.ˊ()[i3] = Color.argb(m14((double) gradientColor.ˋ()[i3], dArr, dArr2), Color.red(i4), Color.green(i4), Color.blue(i4));
            }
        }
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    private int m14(double d, double[] dArr, double[] dArr2) {
        double d2;
        int i = 1;
        while (true) {
            if (i >= dArr.length) {
                d2 = dArr2[dArr2.length - 1];
                break;
            }
            int i2 = i - 1;
            double d3 = dArr[i2];
            double d4 = dArr[i];
            if (dArr[i] >= d) {
                d2 = MiscUtils.ι(dArr2[i2], dArr2[i], MiscUtils.ˋ((d - d3) / (d4 - d3), 0.0d, 1.0d));
                break;
            }
            i++;
        }
        return (int) (d2 * 255.0d);
    }

    /* renamed from: ˏ  reason: contains not printable characters */
    public GradientColor m15(JsonReader jsonReader, float f) throws IOException {
        ArrayList arrayList = new ArrayList();
        boolean z = jsonReader.ﹶ() == JsonReader.Token.ʻ;
        if (z) {
            jsonReader.ˋ();
        }
        while (jsonReader.ʼ()) {
            arrayList.add(Float.valueOf((float) jsonReader.ˉ()));
        }
        if (z) {
            jsonReader.ᐝ();
        }
        if (this.f4 == -1) {
            this.f4 = arrayList.size() / 4;
        }
        int i = this.f4;
        float[] fArr = new float[i];
        int[] iArr = new int[i];
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < this.f4 * 4; i4++) {
            int i5 = i4 / 4;
            double floatValue = (double) arrayList.get(i4).floatValue();
            int i6 = i4 % 4;
            if (i6 == 0) {
                fArr[i5] = (float) floatValue;
            } else if (i6 == 1) {
                i2 = (int) (floatValue * 255.0d);
            } else if (i6 == 2) {
                i3 = (int) (floatValue * 255.0d);
            } else if (i6 == 3) {
                iArr[i5] = Color.argb(255, i2, i3, (int) (floatValue * 255.0d));
            }
        }
        GradientColor gradientColor = new GradientColor(fArr, iArr);
        m13(gradientColor, arrayList);
        return gradientColor;
    }
}
