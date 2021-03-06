package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* access modifiers changed from: package-private */
public final class WrappedDrawableState extends Drawable.ConstantState {
    int mChangingConfigurations;
    Drawable.ConstantState mDrawableState;
    ColorStateList mTint = null;
    PorterDuff.Mode mTintMode = WrappedDrawableApi14.DEFAULT_TINT_MODE;

    WrappedDrawableState(@Nullable WrappedDrawableState wrappedDrawableState) {
        if (wrappedDrawableState != null) {
            this.mChangingConfigurations = wrappedDrawableState.mChangingConfigurations;
            this.mDrawableState = wrappedDrawableState.mDrawableState;
            this.mTint = wrappedDrawableState.mTint;
            this.mTintMode = wrappedDrawableState.mTintMode;
        }
    }

    @NonNull
    public Drawable newDrawable() {
        return newDrawable(null);
    }

    @NonNull
    public Drawable newDrawable(@Nullable Resources resources) {
        if (Build.VERSION.SDK_INT >= 21) {
            return new WrappedDrawableApi21(this, resources);
        }
        return new WrappedDrawableApi14(this, resources);
    }

    public int getChangingConfigurations() {
        return this.mChangingConfigurations | (this.mDrawableState != null ? this.mDrawableState.getChangingConfigurations() : 0);
    }

    /* access modifiers changed from: package-private */
    public boolean canConstantState() {
        return this.mDrawableState != null;
    }
}
