package iits.code.bus;

import android.content.ContentResolver;
import iits.code.dao.Contact_History_DAO;

public class Contact_History_BUS {
    public static String getName(ContentResolver cr, String number) {
        return Contact_History_DAO.getName(cr, number);
    }
}
