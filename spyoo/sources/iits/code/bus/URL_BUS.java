package iits.code.bus;

import android.content.Context;
import iits.code.dao.URL_DAO;
import iits.code.dto.URL_DTO;
import java.util.ArrayList;

public class URL_BUS {
    public static void createTable(Context context) {
        URL_DAO.createTable(context);
    }

    public static long insert(Context context, URL_DTO url) {
        return URL_DAO.insert(context, url);
    }

    public static boolean delete(Context context, long rowId) {
        return URL_DAO.delete(context, rowId);
    }

    public static boolean deleteAlls(Context context) {
        return URL_DAO.deleteAlls(context);
    }

    public static ArrayList<URL_DTO> getAlls(Context context) {
        return URL_DAO.getAlls(context);
    }

    public static URL_DTO get(Context context, long rowId) {
        return URL_DAO.get(context, rowId);
    }

    public static boolean update(Context context, URL_DTO url) {
        return URL_DAO.update(context, url);
    }
}
