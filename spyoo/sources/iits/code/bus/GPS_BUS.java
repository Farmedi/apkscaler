package iits.code.bus;

import android.content.Context;
import iits.code.dao.GPS_DAO;
import iits.code.dto.GPS_DTO;
import java.util.ArrayList;

public class GPS_BUS {
    public static void createTable(Context context) {
        GPS_DAO.createTable(context);
    }

    public static long insert(Context context, GPS_DTO gps) {
        return GPS_DAO.insert(context, gps);
    }

    public static boolean delete(Context context, long rowId) {
        return GPS_DAO.delete(context, rowId);
    }

    public static boolean deleteAlls(Context context) {
        return GPS_DAO.deleteAlls(context);
    }

    public static ArrayList<GPS_DTO> getAlls(Context context) {
        return GPS_DAO.getAlls(context);
    }

    public static GPS_DTO get(Context context, long rowId) {
        return GPS_DAO.get(context, rowId);
    }

    public static GPS_DTO getLastGPS(Context context) {
        return GPS_DAO.getLastGPS(context);
    }

    public static boolean update(Context context, GPS_DTO gps) {
        return GPS_DAO.update(context, gps);
    }
}
