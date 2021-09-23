package iits.code.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import iits.code.SQLiteConnection;
import iits.code.dto.SMS_DTO;
import java.util.ArrayList;

public class SMS_DAO {
    public static final String CREATE_TABLE_SMS = "create  table if not exists SMS (_id integer primary key autoincrement , time text not null, direction text not null, number text not null, message text not null, name text not null);";
    private static final String DATABASE_TABLE = "SMS";
    public static final String KEY_DIRECTION = "direction";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_NAME = "name";
    public static final String KEY_NUMBER = "number";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_TIME = "time";
    private static final String TAG = "SMS_DAO";

    public static void createTable(Context context) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            conn.createTable(CREATE_TABLE_SMS);
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

    public static long insert(Context context, SMS_DTO sms) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            ContentValues initialValues = new ContentValues();
            initialValues.put("time", String.valueOf(sms.getTime()));
            initialValues.put("direction", String.valueOf(sms.getDirection()));
            initialValues.put(KEY_MESSAGE, String.valueOf(sms.getMessage()));
            initialValues.put("number", String.valueOf(sms.getNumber()));
            initialValues.put("name", String.valueOf(sms.getName()));
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
            Log.e(TAG, "Error on deletesms function: " + e.toString());
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

    public static ArrayList<SMS_DTO> getAlls(Context context) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToRead();
            ArrayList<SMS_DTO> arrSMS = new ArrayList<>();
            Cursor c = conn.getDB().query(DATABASE_TABLE, new String[]{"_id", "time", "direction", "number", "name", KEY_MESSAGE}, null, null, null, null, "_id DESC");
            if (c == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return null;
            }
            while (c.moveToNext()) {
                SMS_DTO sms = new SMS_DTO();
                sms.setID(c.getLong(c.getColumnIndex("_id")));
                sms.setTime(c.getLong(c.getColumnIndex("time")));
                sms.setNumber(c.getString(c.getColumnIndex("number")));
                sms.setName(c.getString(c.getColumnIndex("name")));
                sms.setDirection(c.getInt(c.getColumnIndex("direction")));
                sms.setMessage(c.getString(c.getColumnIndex(KEY_MESSAGE)));
                arrSMS.add(sms);
            }
            c.close();
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return arrSMS;
        } catch (SQLException e) {
            Log.e(TAG, "Error on getAllsmss function: " + e.toString());
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

    public static SMS_DTO get(Context context, long rowId) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToRead();
            Cursor c = conn.getDB().query(true, DATABASE_TABLE, new String[]{"_id", "time", "direction", "number", "name", KEY_MESSAGE}, "_id=" + rowId, null, null, null, null, null);
            if (c == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return null;
            }
            SMS_DTO sms = null;
            if (c.moveToNext()) {
                sms = new SMS_DTO();
                sms.setID(c.getLong(c.getColumnIndex("_id")));
                sms.setTime(c.getLong(c.getColumnIndex("time")));
                sms.setNumber(c.getString(c.getColumnIndex("number")));
                sms.setName(c.getString(c.getColumnIndex("name")));
                sms.setDirection(c.getInt(c.getColumnIndex("direction")));
                sms.setMessage(c.getString(c.getColumnIndex(KEY_MESSAGE)));
            }
            c.close();
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return sms;
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

    public static boolean update(Context context, SMS_DTO sms) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            ContentValues args = new ContentValues();
            args.put("time", String.valueOf(sms.getTime()));
            args.put("direction", String.valueOf(sms.getDirection()));
            args.put("number", String.valueOf(sms.getNumber()));
            args.put("name", String.valueOf(sms.getName()));
            args.put(KEY_MESSAGE, String.valueOf(sms.getMessage()));
            boolean result = false;
            if (conn.getDB().update(DATABASE_TABLE, args, "_id=" + sms.getID(), null) > 0) {
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
