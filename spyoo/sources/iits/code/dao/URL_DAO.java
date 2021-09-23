package iits.code.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import iits.code.SQLiteConnection;
import iits.code.dto.URL_DTO;
import java.util.ArrayList;

public class URL_DAO {
    private static final String CREATE_TABLE_URL = "create table if not exists URL (_id integer primary key autoincrement, time text not null, title text not null, url text not null, bookmark text not null);";
    private static final String DATABASE_TABLE = "URL";
    public static final String KEY_BOOKMARK = "bookmark";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_TIME = "time";
    public static final String KEY_TITLE = "title";
    public static final String KEY_URL = "url";
    private static final String TAG = "Call_DAO";

    public static void createTable(Context context) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            conn.createTable(CREATE_TABLE_URL);
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error on insert function: " + e.toString());
            if (conn.isConnection()) {
                conn.close();
            }
        } catch (Throwable th) {
            if (conn.isConnection()) {
                conn.close();
            }
            throw th;
        }
    }

    public static long insert(Context context, URL_DTO url) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            ContentValues initialValues = new ContentValues();
            initialValues.put("time", String.valueOf(url.getTime()));
            initialValues.put(KEY_TITLE, String.valueOf(url.getTitle()));
            initialValues.put(KEY_URL, String.valueOf(url.getURL()));
            initialValues.put(KEY_BOOKMARK, String.valueOf(url.getBookMark()));
            long result = conn.getDB().insert(DATABASE_TABLE, null, initialValues);
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return result;
        } catch (Exception e) {
            Log.e(TAG, "Error on insert function: " + e.toString());
            if (conn.isConnection()) {
                conn.close();
            }
            return -1;
        } catch (Throwable th) {
            if (conn.isConnection()) {
                conn.close();
            }
            throw th;
        }
    }

    public static boolean delete(Context context, long rowId) {
        SQLiteConnection conn = new SQLiteConnection(context);
        boolean result = false;
        try {
            conn.openToWrite();
            if (conn.getDB().delete(DATABASE_TABLE, "_id=" + rowId, null) > 0) {
                result = true;
            }
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return result;
        } catch (Exception e) {
            Log.e(TAG, "Error on delete function: " + e.toString());
            if (conn.isConnection()) {
                conn.close();
            }
            return false;
        } catch (Throwable th) {
            if (conn.isConnection()) {
                conn.close();
            }
            throw th;
        }
    }

    public static boolean deleteAlls(Context context) {
        SQLiteConnection conn = new SQLiteConnection(context);
        boolean result = false;
        try {
            conn.openToWrite();
            if (conn.getDB().delete(DATABASE_TABLE, null, null) > 0) {
                result = true;
            }
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return result;
        } catch (Exception e) {
            Log.e(TAG, "Error on deleteAlls function: " + e.toString());
            if (conn.isConnection()) {
                conn.close();
            }
            return false;
        } catch (Throwable th) {
            if (conn.isConnection()) {
                conn.close();
            }
            throw th;
        }
    }

    public static ArrayList<URL_DTO> getAlls(Context context) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToRead();
            ArrayList<URL_DTO> arrURL = new ArrayList<>();
            Cursor c = conn.getDB().query(DATABASE_TABLE, new String[]{"_id", "time", KEY_TITLE, KEY_URL, KEY_BOOKMARK}, null, null, null, null, "_id DESC");
            if (c == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return null;
            }
            while (c.moveToNext()) {
                URL_DTO url = new URL_DTO();
                url.setID(c.getLong(c.getColumnIndex("_id")));
                url.setTime(c.getLong(c.getColumnIndex("time")));
                url.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
                url.setURL(c.getString(c.getColumnIndex(KEY_URL)));
                url.setBookMark(c.getInt(c.getColumnIndex(KEY_BOOKMARK)));
                arrURL.add(url);
            }
            c.close();
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return arrURL;
        } catch (SQLException e) {
            Log.e(TAG, "Error on getAllCalls function: " + e.toString());
            if (conn.isConnection()) {
                conn.close();
            }
            return null;
        } catch (Throwable th) {
            if (conn.isConnection()) {
                conn.close();
            }
            throw th;
        }
    }

    public static URL_DTO get(Context context, long rowId) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToRead();
            Cursor c = conn.getDB().query(true, DATABASE_TABLE, new String[]{"_id", "time", KEY_TITLE, KEY_URL, KEY_BOOKMARK}, "_id=" + rowId, null, null, null, null, null);
            if (c == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return null;
            }
            URL_DTO url = null;
            if (c.moveToNext()) {
                url = new URL_DTO();
                url.setID(c.getLong(c.getColumnIndex("_id")));
                url.setTime(c.getLong(c.getColumnIndex("time")));
                url.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
                url.setURL(c.getString(c.getColumnIndex(KEY_URL)));
                url.setBookMark(c.getInt(c.getColumnIndex(KEY_BOOKMARK)));
            }
            c.close();
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return url;
        } catch (SQLException e) {
            Log.e(TAG, "Error on get function: " + e.toString());
            if (conn.isConnection()) {
                conn.close();
            }
            return null;
        } catch (Throwable th) {
            if (conn.isConnection()) {
                conn.close();
            }
            throw th;
        }
    }

    public static boolean update(Context context, URL_DTO url) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            ContentValues args = new ContentValues();
            args.put("time", String.valueOf(url.getTime()));
            args.put(KEY_TITLE, String.valueOf(url.getTitle()));
            args.put(KEY_URL, String.valueOf(url.getURL()));
            args.put(KEY_BOOKMARK, String.valueOf(url.getBookMark()));
            boolean result = false;
            if (conn.getDB().update(DATABASE_TABLE, args, "_id=" + url.getID(), null) > 0) {
                result = true;
            }
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return result;
        } catch (Exception e) {
            Log.e(TAG, "Error on update function: " + e.toString());
            if (conn.isConnection()) {
                conn.close();
            }
            return false;
        } catch (Throwable th) {
            if (conn.isConnection()) {
                conn.close();
            }
            throw th;
        }
    }
}
