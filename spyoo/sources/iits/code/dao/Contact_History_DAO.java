package iits.code.dao;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import iits.mamager.FileManager;

public class Contact_History_DAO {
    private static final String TAG = "Contact_History_DAO";

    public static String getName(ContentResolver cr, String number) {
        String name = "";
        try {
            Cursor c = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            while (c.moveToNext()) {
                String id = c.getString(c.getColumnIndex("_id"));
                if (Integer.parseInt(c.getString(c.getColumnIndex("has_phone_number"))) > 0) {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, "contact_id = ?", new String[]{id}, null);
                    while (true) {
                        if (pCur.moveToNext()) {
                            if (PhoneNumberUtils.compare(number, pCur.getString(pCur.getColumnIndex("data1")))) {
                                name = c.getString(c.getColumnIndex("display_name"));
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    pCur.close();
                }
            }
            c.close();
            return name;
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on getName function: " + e.toString());
            return "";
        }
    }
}
