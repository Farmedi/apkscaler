package iits.code.bus;

import android.content.ContentResolver;
import iits.code.dao.URL_History_DAO;
import iits.code.dto.URL_DTO;
import java.util.ArrayList;

public class URL_History_BUS {
    public static ArrayList<URL_DTO> getAlls(ContentResolver cr) {
        return URL_History_DAO.getAlls(cr);
    }

    public static URL_DTO getURLLast(ContentResolver cr) {
        return URL_History_DAO.getURLLast(cr);
    }

    public static URL_DTO getAccessLastURL(ContentResolver cr, long oldDate) {
        return URL_History_DAO.getAccessLastURL(cr, oldDate);
    }

    public static int getCount(ContentResolver cr) {
        return URL_History_DAO.getCount(cr);
    }
}
