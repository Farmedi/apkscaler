package iits.code.bus;

import android.content.Context;
import iits.code.dao.SMS_DAO;
import iits.code.dto.SMS_DTO;
import java.util.ArrayList;

public class SMS_BUS {
    public static void createTable(Context context) {
        SMS_DAO.createTable(context);
    }

    public static long insert(Context context, SMS_DTO sms) {
        return SMS_DAO.insert(context, sms);
    }

    public static boolean delete(Context context, long rowId) {
        return SMS_DAO.delete(context, rowId);
    }

    public static boolean deleteAlls(Context context) {
        return SMS_DAO.deleteAlls(context);
    }

    public static ArrayList<SMS_DTO> getAlls(Context context) {
        return SMS_DAO.getAlls(context);
    }

    public static SMS_DTO get(Context context, long rowId) {
        return SMS_DAO.get(context, rowId);
    }

    public static boolean update(Context context, SMS_DTO sms) {
        return SMS_DAO.update(context, sms);
    }
}
