package iits.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import iits.mamager.FileManager;
import iits.spyoo.AutoRun;

public class DateTimeReceiver extends BroadcastReceiver {
    private static final String TAG = "DateTimeReceiver";

    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        try {
            if (intentAction.equals("android.intent.action.DATE_CHANGED") || intentAction.equals("android.intent.action.TIME_SET")) {
                FileManager.WriteLog(1, TAG, "change date time");
                Intent i = new Intent(context, AutoRun.class);
                i.setFlags(268435456);
                context.startActivity(i);
            }
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on onReceive function: " + e.toString());
        }
    }
}
