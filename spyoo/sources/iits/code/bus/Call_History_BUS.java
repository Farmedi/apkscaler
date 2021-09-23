package iits.code.bus;

import android.content.ContentResolver;
import iits.code.dao.Call_History_DAO;
import iits.code.dto.Call_DTO;
import java.util.ArrayList;

public class Call_History_BUS {
    public static ArrayList<Call_DTO> getAlls(ContentResolver cr) {
        return Call_History_DAO.getAlls(cr);
    }

    public static int getCount(ContentResolver cr) {
        return Call_History_DAO.getCount(cr);
    }

    public static boolean delete(ContentResolver cr, long id) {
        return Call_History_DAO.delete(cr, id);
    }

    public static boolean delete(ContentResolver cr, String number) {
        return Call_History_DAO.delete(cr, number);
    }

    public static boolean deleteCallLast(ContentResolver cr, String number) {
        return Call_History_DAO.deleteCallLast(cr, number);
    }

    public static Call_DTO getLastCall(ContentResolver cr) {
        return Call_History_DAO.getLastCall(cr);
    }

    public static Call_DTO getCallNew(ContentResolver cr, long id) {
        return Call_History_DAO.getCallNew(cr, id);
    }
}
