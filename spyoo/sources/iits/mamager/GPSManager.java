package iits.mamager;

import android.content.Context;
import iits.code.bus.GPS_BUS;
import iits.code.dto.GPS_DTO;
import iits.common.Constants;
import iits.common.Format;
import iits.common.HTTPWeb;
import java.util.ArrayList;

public class GPSManager {
    private static final String TAG = "CallManager";

    private static ArrayList<String> getArrayParameterKeyGPS() {
        ArrayList<String> arrKey = new ArrayList<>();
        arrKey.add("deviceid");
        arrKey.add("date");
        arrKey.add("time");
        arrKey.add("lat");
        arrKey.add("lon");
        arrKey.add("alt");
        arrKey.add("os");
        return arrKey;
    }

    private static ArrayList<String> getArrayParameterValueGPS(String deviceID, GPS_DTO gps) {
        ArrayList<String> arrValue = new ArrayList<>();
        if (deviceID.equals("") || gps == null) {
            return null;
        }
        arrValue.add(String.valueOf(deviceID));
        arrValue.add(Format.formatDate(gps.getTime()));
        arrValue.add(Format.formatTime(gps.getTime()));
        arrValue.add(String.valueOf(gps.getLatitude()));
        arrValue.add(String.valueOf(gps.getLongitude()));
        arrValue.add(String.valueOf(gps.getAltitude()));
        arrValue.add(Constants.OS);
        return arrValue;
    }

    public static void sendDataOfGPSToWebsite(Context context, String url, String deviceID) {
        try {
            ArrayList<GPS_DTO> arrGPS = GPS_BUS.getAlls(context);
            ArrayList<String> arrKey = getArrayParameterKeyGPS();
            int n = arrGPS.size() - 1;
            FileManager.WriteLog(1, TAG, "n= " + String.valueOf(n + 1));
            for (int i = n; i >= 0; i--) {
                if (HTTPWeb.sendPostMethod(url, arrKey, getArrayParameterValueGPS(deviceID, arrGPS.get(i))) != null) {
                    FileManager.WriteLog(1, TAG, "send gps: (" + arrGPS.get(i).getLatitude() + "," + arrGPS.get(i).getLongitude() + "," + arrGPS.get(i).getAltitude() + ")");
                    GPS_BUS.delete(context, arrGPS.get(i).getID());
                    arrGPS.remove(i);
                }
            }
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on sendDataOfGPSToWebsite function: " + e.toString());
        }
    }
}
