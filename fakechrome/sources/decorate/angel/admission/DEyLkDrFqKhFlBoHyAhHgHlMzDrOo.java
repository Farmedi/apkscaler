package decorate.angel.admission;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import com.quvideo.xiaoying.sdk.utils.m;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DEyLkDrFqKhFlBoHyAhHgHlMzDrOo {
    public static final String KEY_PREFER_ADJUST_CAMERA = "pref_adjust_camera";
    public static final String KEY_PREFER_APK_DISABLE_VERSION = "pref_apk_disable_version";
    public static final String KEY_PREFER_APK_LAST_VERSION = "pref_apk_last_version";
    public static final String KEY_PREFER_APK_SERVER_VERSION = "pref_apk_version";
    public static final String KEY_PREFER_APK_URL = "pref_apk_url";
    public static final String KEY_PREFER_BACK_CAMERA_DISPLAY_HORZMIRROR = "pref_back_camera_display_horzmirror";
    public static final String KEY_PREFER_BACK_CAMERA_DISPLAY_OFFSET = "pref_back_camera_display_offset";
    public static final String KEY_PREFER_BIND_SNS = "pref_sns";
    public static final String KEY_PREFER_CAMERA_PREVIEW_MSIZE_HEIGHT = "pref_camera_preview_msize_height";
    public static final String KEY_PREFER_CAMERA_PREVIEW_MSIZE_WIDTH = "pref_camera_preview_msize_width";
    public static final String KEY_PREFER_COMMENT = "pref_comment";
    public static final String KEY_PREFER_DATA_SN = "pref_data_sn";
    public static final String KEY_PREFER_DECODE = "pref_decode";
    public static final String KEY_PREFER_DEVINFO_IMEI = "pref_devinfo_imei";
    public static final String KEY_PREFER_DEVINFO_MAC = "pref_devinfo_mac";
    public static final String KEY_PREFER_ENCODE = "pref_encode";
    public static final String KEY_PREFER_EXPORT_STORAGE = "pref_export_storage";
    public static final String KEY_PREFER_FIRST_FAVOURITE = "pref_first_favourite";
    public static final String KEY_PREFER_FIRST_SEQUENCE = "pref_first_sequence";
    public static final String KEY_PREFER_FIRST_TEXT_FAVOURITE = "pref_first_text_favourite";
    public static final String KEY_PREFER_FIRST_THEME_FAVOURITE = "pref_first_theme_favourite";
    public static final String KEY_PREFER_FIRST_TRANSITION_FAVOURITE = "pref_first_transition_favourite";
    public static final String KEY_PREFER_FRONT_CAMERA_DISPLAY_HORZMIRROR = "pref_front_camera_display_horzmirror";
    public static final String KEY_PREFER_FRONT_CAMERA_DISPLAY_OFFSET = "pref_front_camera_display_offset";
    public static final String KEY_PREFER_GUIDE_MODE_ONOFF = "pref_guide_mode_onoff";
    public static final String KEY_PREFER_GUIDE_MODE_SWITCH = "pref_guide_mode_switch";
    public static final String KEY_PREFER_HELP_CAMERA = "pref_help_camera";
    public static final String KEY_PREFER_HELP_EDIT = "pref_help_edit";
    public static final String KEY_PREFER_HELP_EDIT_DUB = "pref_help_edit_dub";
    public static final String KEY_PREFER_HELP_EDIT_SUBTITLE = "pref_help_edit_subtitle";
    public static final String KEY_PREFER_HELP_EDIT_TRIM_PIC = "pref_help_edit_trim_pic";
    public static final String KEY_PREFER_HELP_EDIT_TRIM_VIDEO = "pref_help_edit_trim_video";
    public static final String KEY_PREFER_HELP_PROJECT = "pref_help_project";
    public static final String KEY_PREFER_IS_FAVOURITE = "pref_is_favourite";
    public static final String KEY_PREFER_MEDIA_STORAGE = "pref_media_storage";
    public static final String KEY_PREFER_NETWORK_USAGE_MOBILE = "pref_network_mobile";
    public static final String KEY_PREFER_PROJECT_IGNORE_PROMPT = "pref_project_ignore_prompt";
    public static final String KEY_PREFER_RECEIVE_NOTIFICATION = "pref_receive_notification";
    public static final String KEY_PREFER_RECORD_SAMPLERATE = "pref_record_samplerate";
    public static final String KEY_PREFER_SERVER = "pref_server";
    public static final String KEY_PREFER_STUDIO_NAME = "pref_studio_name";
    public static final String KEY_PREFER_VIDEO_EXPORT_SETTING = "pref_video_export_setting";
    public static final String KEY_PREFRE_RES_LOST_MSG_SHOW_STATE = "pref_res_lost_msg_show";
    public static final int NETWORK_USAGE_ALL = 3;
    public static final int NETWORK_USAGE_DISABLE = 0;
    public static final int NETWORK_USAGE_MOBILE = 1;
    public static final int NETWORK_USAGE_WIFI = 2;
    public static final int STORAGE_IN_EXTENSION_CARD = 1;
    public static final int STORAGE_IN_PHONE_CARD = 0;
    public static final String TAG = "DEyLkDrFqKhFlBoHyAhHgHlMzDrOo";
    private static Uri cgN;
    private static String cgO;
    private static String cgP;
    private static DEyLkDrFqKhFlBoHyAhHgHlMzDrOo cgQ;
    private static Executor cgR = Executors.newSingleThreadExecutor();
    private boolean bqt = false;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    private DEyLkDrFqKhFlBoHyAhHgHlMzDrOo() {
    }

    public synchronized void removeAppKey(String str) {
        if (this.mPreferences != null) {
            if (this.mEditor != null) {
                cgR.execute(new 1(this, str));
            }
        }
    }

    public synchronized int getAppSettingInt(String str, int i) {
        SharedPreferences sharedPreferences = this.mPreferences;
        if (sharedPreferences != null) {
            if (str != null) {
                return sharedPreferences.getInt(str, i);
            }
        }
        return i;
    }

    public synchronized void setAppSettingInt(String str, int i) {
        if (this.mPreferences != null) {
            if (str != null) {
                cgR.execute(new 2(this, str, i));
            }
        }
    }

    public synchronized long getAppSettingLong(String str, long j) {
        SharedPreferences sharedPreferences = this.mPreferences;
        if (sharedPreferences != null) {
            if (str != null) {
                return sharedPreferences.getLong(str, j);
            }
        }
        return j;
    }

    public synchronized void setAppSettingLong(String str, long j) {
        if (this.mPreferences != null) {
            if (str != null) {
                cgR.execute(new 3(this, str, j));
            }
        }
    }

    public synchronized String getAppSettingStr(String str, String str2) {
        SharedPreferences sharedPreferences = this.mPreferences;
        if (sharedPreferences == null) {
            return str2;
        }
        return sharedPreferences.getString(str, str2);
    }

    public synchronized void setAppSettingStr(String str, String str2) {
        if (this.mPreferences != null) {
            if (str != null) {
                if (str2 == null) {
                    removeAppKey(str);
                } else {
                    cgR.execute(new 4(this, str, str2));
                }
            }
        }
    }

    public synchronized boolean getAppSettingBoolean(String str, boolean z) {
        SharedPreferences sharedPreferences = this.mPreferences;
        if (sharedPreferences != null) {
            if (str != null) {
                return sharedPreferences.getBoolean(str, z);
            }
        }
        return z;
    }

    public synchronized void setAppSettingBoolean(String str, boolean z) {
        if (this.mPreferences != null) {
            if (str != null) {
                cgR.execute(new 5(this, str, z));
                m.d("DEyLkDrFqKhFlBoHyAhHgHlMzDrOo", "setAppSettingBoolean key=" + str + " value=" + z);
            }
        }
    }

    public static synchronized DEyLkDrFqKhFlBoHyAhHgHlMzDrOo getInstance() {
        DEyLkDrFqKhFlBoHyAhHgHlMzDrOo dEyLkDrFqKhFlBoHyAhHgHlMzDrOo;
        synchronized (DEyLkDrFqKhFlBoHyAhHgHlMzDrOo.class) {
            if (cgQ == null) {
                cgQ = new DEyLkDrFqKhFlBoHyAhHgHlMzDrOo();
            }
            dEyLkDrFqKhFlBoHyAhHgHlMzDrOo = cgQ;
        }
        return dEyLkDrFqKhFlBoHyAhHgHlMzDrOo;
    }

    public synchronized boolean init(Context context) {
        ck(context);
        return true;
    }

    public void uninit() {
        this.mEditor = null;
        this.mPreferences = null;
    }

    private void ck(Context context) {
        if (this.mPreferences == null) {
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            this.mPreferences = defaultSharedPreferences;
            if (defaultSharedPreferences != null) {
                this.mEditor = defaultSharedPreferences.edit();
                this.bqt = true;
            }
        }
    }

    public boolean isInit() {
        return this.bqt;
    }

    public static void setDBAccessParam(Uri uri, String str, String str2) {
        cgN = uri;
        cgO = str;
        cgP = str2;
    }

    public synchronized String getAppSettingStrFromDb(Context context, String str, String str2) {
        if (cgO != null) {
            if (cgN != null) {
                Cursor query = context.getContentResolver().query(cgN, new String[]{cgP}, cgO + " = ?", new String[]{str}, null);
                if (query == null) {
                    return str2;
                }
                try {
                    if (query.moveToFirst()) {
                        str2 = query.getString(0);
                    }
                } catch (Exception unused) {
                }
                query.close();
                return str2;
            }
        }
        return null;
    }

    public synchronized void setAppSettingStrToDb(Context context, String str, String str2) {
        if (cgO != null) {
            if (cgN != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(cgO, str);
                contentValues.put(cgP, str2);
                context.getContentResolver().insert(cgN, contentValues);
            }
        }
    }
}
