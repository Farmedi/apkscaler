package commission;

import android.util.Pair;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieResult;
import com.airbnb.lottie.utils.Logger;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

public class NetworkFetcher {

    /* renamed from: ˊ  reason: contains not printable characters */
    private final NetworkCache f1;

    /* renamed from: ˋ  reason: contains not printable characters */
    private final LottieNetworkFetcher f2;

    public NetworkFetcher(NetworkCache networkCache, LottieNetworkFetcher lottieNetworkFetcher) {
        this.f1 = networkCache;
        this.f2 = lottieNetworkFetcher;
    }

    /* renamed from: ʻ  reason: contains not printable characters */
    private LottieResult<LottieComposition> m1(String str, InputStream inputStream, String str2) throws IOException {
        if (str2 == null) {
            return LottieCompositionFactory.ᐧ(new ZipInputStream(inputStream), (String) null);
        }
        return LottieCompositionFactory.ᐧ(new ZipInputStream(new FileInputStream(this.f1.ʻ(str, inputStream, FileExtension.ʽ))), str);
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    private LottieComposition m2(String str, String str2) {
        Pair r5;
        LottieResult lottieResult;
        if (str2 == null || (r5 = this.f1.ˊ(str)) == null) {
            return null;
        }
        FileExtension fileExtension = (FileExtension) r5.first;
        InputStream inputStream = (InputStream) r5.second;
        if (fileExtension == FileExtension.ʽ) {
            lottieResult = LottieCompositionFactory.ᐧ(new ZipInputStream(inputStream), str);
        } else {
            lottieResult = LottieCompositionFactory.ͺ(inputStream, str);
        }
        if (lottieResult.ˋ() != null) {
            return (LottieComposition) lottieResult.ˋ();
        }
        return null;
    }

    /* renamed from: ˋ  reason: contains not printable characters */
    private LottieResult<LottieComposition> m3(String str, String str2) {
        Logger.ˊ("Fetching " + str);
        Closeable closeable = null;
        try {
            LottieFetchResult r1 = this.f2.ˊ(str);
            if (r1.І()) {
                LottieResult<LottieComposition> r5 = m4(str, r1.ˁ(), r1.contentType(), str2);
                StringBuilder sb = new StringBuilder();
                sb.append("Completed fetch from network. Success: ");
                sb.append(r5.ˋ() != null);
                Logger.ˊ(sb.toString());
                if (r1 != null) {
                    try {
                        r1.close();
                    } catch (IOException e) {
                        Logger.ˏ("LottieFetchResult close failed ", e);
                    }
                }
                return r5;
            }
            LottieResult<LottieComposition> lottieResult = new LottieResult<>(new IllegalArgumentException(r1.ᵙ()));
            if (r1 != null) {
                try {
                    r1.close();
                } catch (IOException e2) {
                    Logger.ˏ("LottieFetchResult close failed ", e2);
                }
            }
            return lottieResult;
        } catch (Exception e3) {
            LottieResult<LottieComposition> lottieResult2 = new LottieResult<>(e3);
            if (0 != 0) {
                try {
                    closeable.close();
                } catch (IOException e4) {
                    Logger.ˏ("LottieFetchResult close failed ", e4);
                }
            }
            return lottieResult2;
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    closeable.close();
                } catch (IOException e5) {
                    Logger.ˏ("LottieFetchResult close failed ", e5);
                }
            }
            throw th;
        }
    }

    /* renamed from: ˏ  reason: contains not printable characters */
    private LottieResult<LottieComposition> m4(String str, InputStream inputStream, String str2, String str3) throws IOException {
        FileExtension fileExtension;
        LottieResult<LottieComposition> lottieResult;
        if (str2 == null) {
            str2 = "application/json";
        }
        if (str2.contains("application/zip") || str.split("\\?")[0].endsWith(".lottie")) {
            Logger.ˊ("Handling zip response.");
            fileExtension = FileExtension.ʽ;
            lottieResult = m1(str, inputStream, str3);
        } else {
            Logger.ˊ("Received json response.");
            fileExtension = FileExtension.ʼ;
            lottieResult = m5(str, inputStream, str3);
        }
        if (!(str3 == null || lottieResult.ˋ() == null)) {
            this.f1.ᐝ(str, fileExtension);
        }
        return lottieResult;
    }

    /* renamed from: ᐝ  reason: contains not printable characters */
    private LottieResult<LottieComposition> m5(String str, InputStream inputStream, String str2) throws IOException {
        if (str2 == null) {
            return LottieCompositionFactory.ͺ(inputStream, (String) null);
        }
        return LottieCompositionFactory.ͺ(new FileInputStream(new File(this.f1.ʻ(str, inputStream, FileExtension.ʼ).getAbsolutePath())), str);
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    public LottieResult<LottieComposition> m6(String str, String str2) {
        LottieComposition r0 = m2(str, str2);
        if (r0 != null) {
            return new LottieResult<>(r0);
        }
        Logger.ˊ("Animation for " + str + " not found in cache. Fetching from network.");
        return m3(str, str2);
    }
}
