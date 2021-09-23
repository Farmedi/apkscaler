package iits.code.bus;

import android.content.Context;
import iits.code.dao.Call_DAO;
import iits.code.dto.Call_DTO;
import java.util.ArrayList;

public class Call_BUS {
    public static void createTable(Context context) {
        Call_DAO.createTable(context);
    }

    public static long insert(Context context, Call_DTO call) {
        return Call_DAO.insert(context, call);
    }

    public static boolean delete(Context context, long rowId) {
        return Call_DAO.delete(context, rowId);
    }

    public static boolean deleteAlls(Context context) {
        return Call_DAO.deleteAlls(context);
    }

    public static ArrayList<Call_DTO> getAlls(Context context) {
        return Call_DAO.getAlls(context);
    }

    public static Call_DTO get(Context context, long rowId) {
        return Call_DAO.get(context, rowId);
    }

    public static boolean update(Context context, Call_DTO call) {
        return Call_DAO.update(context, call);
    }
}
