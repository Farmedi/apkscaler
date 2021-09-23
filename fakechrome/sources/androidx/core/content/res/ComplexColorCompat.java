package androidx.core.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Shader;
import android.util.Log;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public final class ComplexColorCompat {
    private static final String LOG_TAG = "ComplexColorCompat";
    private int mColor;
    private final ColorStateList mColorStateList;
    private final Shader mShader;

    private ComplexColorCompat(Shader shader, ColorStateList colorStateList, @ColorInt int i) {
        this.mShader = shader;
        this.mColorStateList = colorStateList;
        this.mColor = i;
    }

    static ComplexColorCompat from(@NonNull Shader shader) {
        return new ComplexColorCompat(shader, null, 0);
    }

    static ComplexColorCompat from(@NonNull ColorStateList colorStateList) {
        return new ComplexColorCompat(null, colorStateList, colorStateList.getDefaultColor());
    }

    static ComplexColorCompat from(@ColorInt int i) {
        return new ComplexColorCompat(null, null, i);
    }

    @Nullable
    public Shader getShader() {
        return this.mShader;
    }

    @ColorInt
    public int getColor() {
        return this.mColor;
    }

    public void setColor(@ColorInt int i) {
        this.mColor = i;
    }

    public boolean isGradient() {
        return this.mShader != null;
    }

    public boolean isStateful() {
        return this.mShader == null && this.mColorStateList != null && this.mColorStateList.isStateful();
    }

    public boolean onStateChanged(int[] iArr) {
        int colorForState;
        if (!isStateful() || (colorForState = this.mColorStateList.getColorForState(iArr, this.mColorStateList.getDefaultColor())) == this.mColor) {
            return false;
        }
        this.mColor = colorForState;
        return true;
    }

    public boolean willDraw() {
        return isGradient() || this.mColor != 0;
    }

    @Nullable
    public static ComplexColorCompat inflate(@NonNull Resources resources, @ColorRes int i, @Nullable Resources.Theme theme) {
        try {
            return createFromXml(resources, i, theme);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Failed to inflate ComplexColor.", e);
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0041, code lost:
        if (r1.equals("gradient") != false) goto L_0x0045;
     */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x006f  */
    @androidx.annotation.NonNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static androidx.core.content.res.ComplexColorCompat createFromXml(@androidx.annotation.NonNull android.content.res.Resources r6, @androidx.annotation.ColorRes int r7, @androidx.annotation.Nullable android.content.res.Resources.Theme r8) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        /*
        // Method dump skipped, instructions count: 128
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.res.ComplexColorCompat.createFromXml(android.content.res.Resources, int, android.content.res.Resources$Theme):androidx.core.content.res.ComplexColorCompat");
    }
}
