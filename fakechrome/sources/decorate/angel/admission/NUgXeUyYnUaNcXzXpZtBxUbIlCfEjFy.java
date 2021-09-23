package decorate.angel.admission;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.text.TextUtils;
import androidx.core.os.EnvironmentCompat;
import com.quvideo.xiaoying.sdk.utils.m;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NUgXeUyYnUaNcXzXpZtBxUbIlCfEjFy {
    public static final int FILE_TYPE_3GPP = 203;
    public static final int FILE_TYPE_3GPP2 = 204;
    public static final int FILE_TYPE_AAC = 8;
    public static final int FILE_TYPE_AMR = 4;
    public static final int FILE_TYPE_ASF = 209;
    public static final int FILE_TYPE_AVI = 208;
    public static final int FILE_TYPE_AWB = 5;
    public static final int FILE_TYPE_BMP = 304;
    public static final int FILE_TYPE_FLV = 211;
    public static final int FILE_TYPE_GIF = 302;
    public static final int FILE_TYPE_IMY = 103;
    public static final int FILE_TYPE_JPEG = 301;
    public static final int FILE_TYPE_K3G = 207;
    public static final int FILE_TYPE_M3U = 401;
    public static final int FILE_TYPE_M4A = 2;
    public static final int FILE_TYPE_M4V = 202;
    public static final int FILE_TYPE_MID = 101;
    public static final int FILE_TYPE_MOV = 210;
    public static final int FILE_TYPE_MP3 = 1;
    public static final int FILE_TYPE_MP4 = 201;
    public static final int FILE_TYPE_OGG = 7;
    public static final int FILE_TYPE_PLS = 402;
    public static final int FILE_TYPE_PNG = 303;
    public static final int FILE_TYPE_RAW = 212;
    public static final int FILE_TYPE_SKM = 206;
    public static final int FILE_TYPE_SMF = 102;
    public static final int FILE_TYPE_UNKNOWN = 0;
    public static final int FILE_TYPE_WAV = 3;
    public static final int FILE_TYPE_WBMP = 305;
    public static final int FILE_TYPE_WMA = 6;
    public static final int FILE_TYPE_WMV = 205;
    public static final int FILE_TYPE_WPL = 403;
    private static final String TAG = "NUgXeUyYnUaNcXzXpZtBxUbIlCfEjFy_LOG";
    private static final int chJ = 1;
    private static final int chK = 99;
    private static final int chL = 101;
    private static final int chM = 199;
    private static final int chN = 201;
    private static final int chO = 299;
    private static final int chP = 301;
    private static final int chQ = 399;
    private static final int chR = 401;
    private static final int chS = 499;
    private static HashMap<String, a> chT = new HashMap<>();
    public static final String s_strFileExtFilter;

    public static boolean IsAudioFileType(int i) {
        if (i < 1 || i > chK) {
            return i >= 101 && i <= chM;
        }
        return true;
    }

    public static boolean IsImageFileType(int i) {
        return i >= 301 && i <= chQ;
    }

    public static boolean IsPlayListFileType(int i) {
        return i >= 401 && i <= chS;
    }

    public static boolean IsSupportedVideoFileType(int i) {
        return i >= 201 && i <= 204;
    }

    public static boolean IsVideoFileType(int i) {
        return i >= 201 && i <= chO;
    }

    public static boolean IsVideoWMVType(int i) {
        return i == 205;
    }

    static {
        c("MP3", 1, "audio/mpeg");
        c("M4A", 2, "audio/mp4");
        c("M4A", 2, "audio/3gpp");
        c("WAV", 3, "audio/x-wav");
        c("AMR", 4, "audio/amr");
        c("AWB", 5, "audio/amr-wb");
        c("WMA", 6, "audio/x-ms-wma");
        c("OGG", 7, "application/ogg");
        c("OGA", 7, "application/ogg");
        c("AAC", 8, "audio/aac");
        c("MID", 101, "audio/midi");
        c("MIDI", 101, "audio/midi");
        c("XMF", 101, "audio/midi");
        c("RTTTL", 101, "audio/midi");
        c("SMF", 102, "audio/sp-midi");
        c("IMY", 103, "audio/imelody");
        c("RTX", 101, "audio/midi");
        c("OTA", 101, "audio/midi");
        c("MP4", 201, "video/mp4");
        c("M4V", 202, "video/mp4");
        c("3GP", 203, "video/3gpp");
        c("3GPP", 203, "video/3gpp");
        c("3G2", 204, "video/3gpp2");
        c("3GPP2", 204, "video/3gpp2");
        c("WMV", 205, "video/x-ms-wmv");
        c("SKM", 206, "video/skm");
        c("K3G", 207, "video/k3g");
        c("AVI", 208, "video/avi");
        c("ASF", 209, "video/asf");
        c("MOV", 210, "video/mp4");
        c("FLV", 211, "video/mp4");
        c("RAW", 212, "video/raw");
        c("JPG", 301, "image/jpeg");
        c("JPEG", 301, "image/jpeg");
        c("GIF", 302, "image/gif");
        c("PNG", 303, "image/png");
        c("BMP", 304, "image/x-ms-bmp");
        c("WBMP", 305, "image/vnd.wap.wbmp");
        c("M3U", 401, "audio/x-mpegurl");
        c("PLS", 402, "audio/x-scpls");
        c("WPL", 403, "application/vnd.ms-wpl");
        StringBuilder sb = new StringBuilder();
        for (String str : chT.keySet()) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(str);
        }
        s_strFileExtFilter = sb.toString();
    }

    static void c(String str, int i, String str2) {
        chT.put(str, new a(i, str2));
    }

    public static boolean IsVideoFileType(String str) {
        return IsVideoFileType(GetFileMediaType(str));
    }

    public static boolean IsImageFileType(String str) {
        return IsImageFileType(GetFileMediaType(str));
    }

    public static int GetFileMediaType(String str) {
        int lastIndexOf;
        a aVar;
        if (!TextUtils.isEmpty(str) && (lastIndexOf = str.lastIndexOf(".")) >= 0 && lastIndexOf != str.length() - 1 && (aVar = chT.get(str.substring(lastIndexOf + 1).toUpperCase(Locale.US))) != null) {
            return a.a(aVar);
        }
        return 0;
    }

    public static String GetFileMimeType(String str) {
        int lastIndexOf;
        a aVar;
        if (!TextUtils.isEmpty(str) && (lastIndexOf = str.lastIndexOf(".")) >= 0 && lastIndexOf != str.length() - 1 && (aVar = chT.get(str.substring(lastIndexOf + 1).toUpperCase(Locale.US))) != null) {
            return aVar.aUJ();
        }
        return "";
    }

    public static String GetMediaFileExt(String str) {
        if (TextUtils.isEmpty(str)) {
            return EnvironmentCompat.MEDIA_UNKNOWN;
        }
        for (Map.Entry<String, a> entry : chT.entrySet()) {
            if (str.equalsIgnoreCase(a.b(entry.getValue()))) {
                return entry.getKey();
            }
        }
        return EnvironmentCompat.MEDIA_UNKNOWN;
    }

    public static Bitmap getVideoThumbFromFile(String str, int i, int i2) {
        m.i(TAG, "getVideoThumbnailFromFile in ,fileName:" + str);
        Bitmap bitmap = null;
        if (str != null && str.length() > 0) {
            try {
                Bitmap createVideoThumbnail = ThumbnailUtils.createVideoThumbnail(str, 1);
                if (createVideoThumbnail != null) {
                    bitmap = ThumbnailUtils.extractThumbnail(createVideoThumbnail, i, i2, 2);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            m.i(TAG, "getVideoThumbnailFromFile out");
        }
        return bitmap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x002c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long getAudioMediaDuration(java.lang.String r4) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 0
            r3 = 10
            if (r0 >= r3) goto L_0x0009
            return r1
        L_0x0009:
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            if (r0 == 0) goto L_0x0010
            return r1
        L_0x0010:
            r0 = 0
            android.media.MediaMetadataRetriever r3 = new android.media.MediaMetadataRetriever     // Catch:{ all -> 0x0029 }
            r3.<init>()     // Catch:{ all -> 0x0029 }
            r3.setDataSource(r4)     // Catch:{ all -> 0x0027 }
            r4 = 9
            java.lang.String r4 = r3.extractMetadata(r4)     // Catch:{ all -> 0x0027 }
            long r0 = java.lang.Long.parseLong(r4)     // Catch:{ all -> 0x0027 }
            r3.release()
            return r0
        L_0x0027:
            r0 = r3
            goto L_0x002a
        L_0x0029:
        L_0x002a:
            if (r0 == 0) goto L_0x002f
            r0.release()
        L_0x002f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: decorate.angel.admission.NUgXeUyYnUaNcXzXpZtBxUbIlCfEjFy.getAudioMediaDuration(java.lang.String):long");
    }

    public static boolean IsSupportedAudioFileType(int i) {
        return i == 1 || i == 2 || IsSupportedVideoFileType(i);
    }

    public static int getVideoDuration(String str) {
        if (Build.VERSION.SDK_INT >= 10) {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            try {
                mediaMetadataRetriever.setDataSource(str);
                String extractMetadata = mediaMetadataRetriever.extractMetadata(9);
                if (extractMetadata == null || extractMetadata.length() <= 0) {
                    try {
                        mediaMetadataRetriever.release();
                    } catch (Exception unused) {
                    }
                } else {
                    int parseInt = Integer.parseInt(extractMetadata);
                    try {
                        mediaMetadataRetriever.release();
                    } catch (Exception unused2) {
                    }
                    return parseInt;
                }
            } catch (Throwable th) {
                try {
                    mediaMetadataRetriever.release();
                } catch (Exception unused3) {
                }
                throw th;
            }
        }
        return 0;
    }

    public static MSize getVideoResolution(String str) {
        if (Build.VERSION.SDK_INT < 10) {
            return new MSize(0, 0);
        }
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(str);
            MediaMetadataRetriever mediaMetadataRetriever2 = new MediaMetadataRetriever();
            mediaMetadataRetriever2.setDataSource(str);
            MSize mSize = new MSize(Integer.valueOf(mediaMetadataRetriever2.extractMetadata(18)).intValue(), Integer.valueOf(mediaMetadataRetriever2.extractMetadata(19)).intValue());
            try {
                mediaMetadataRetriever.release();
            } catch (Exception unused) {
            }
            return mSize;
        } catch (Throwable th) {
            try {
                mediaMetadataRetriever.release();
            } catch (Exception unused2) {
            }
            throw th;
        }
    }
}
