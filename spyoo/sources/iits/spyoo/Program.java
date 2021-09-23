package iits.spyoo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import iits.mamager.FileManager;
import java.util.Timer;
import java.util.TimerTask;

public class Program extends Activity {
    private static final String TAG = "Program";
    private TimerTask task = new TimerTask() {
        /* class iits.spyoo.Program.AnonymousClass1 */

        public void run() {
            try {
                Intent i = new Intent(Program.this, Login.class);
                i.setFlags(268435456);
                Program.this.startActivity(i);
                if (Program.this.timer != null) {
                    Program.this.timer.cancel();
                }
                Program.this.finish();
            } catch (Exception e) {
                FileManager.WriteLog(3, Program.TAG, "Error on run function: " + e.toString());
            }
        }
    };
    private Timer timer;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        try {
            Settings.System.putInt(getContentResolver(), "wifi_sleep_policy", 2);
            this.timer = new Timer();
            this.timer.schedule(this.task, 3000, 3000);
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on onCreate function: " + e.toString());
        }
    }
}
