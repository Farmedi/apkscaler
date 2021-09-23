package iits.code.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import iits.code.SQLiteConnection;
import iits.code.dto.Call_DTO;
import iits.mamager.FileManager;
import java.util.ArrayList;

public class Call_DAO {
    private static final String CREATE_TABLE_CALL = "create table if not exists Call (_id integer primary key autoincrement, time text not null, direction text not null, number text not null, duration text not null, name text not null);";
    private static final String DATABASE_TABLE = "Call";
    public static final String KEY_DIRECTION = "direction";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_NAME = "name";
    public static final String KEY_NUMBER = "number";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_TIME = "time";
    private static final String TAG = "Call_DAO";

    public static void createTable(Context context) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            conn.createTable(CREATE_TABLE_CALL);
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

    public static long insert(Context context, Call_DTO call) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            ContentValues initialValues = new ContentValues();
            initialValues.put("time", String.valueOf(call.getTime()));
            initialValues.put("direction", String.valueOf(call.getDirection()));
            initialValues.put(KEY_DURATION, String.valueOf(call.getDuration()));
            initialValues.put("number", String.valueOf(call.getNumber()));
            initialValues.put("name", String.valueOf(call.getName()));
            long result = conn.getDB().insert(DATABASE_TABLE, null, initialValues);
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return result;
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on insert function: " + e.toString());
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
            FileManager.WriteLog(3, TAG, "Error on deleteCall function: " + e.toString());
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

    public static ArrayList<Call_DTO> getAlls(Context context) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToRead();
            ArrayList<Call_DTO> arrCall = new ArrayList<>();
            Cursor c = conn.getDB().query(DATABASE_TABLE, new String[]{"_id", "time", "direction", "number", "name", KEY_DURATION}, null, null, null, null, "_id DESC");
            if (c == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return null;
            }
            while (c.moveToNext()) {
                Call_DTO call = new Call_DTO();
                call.setID(c.getLong(c.getColumnIndex("_id")));
                call.setTime(c.getLong(c.getColumnIndex("time")));
                call.setNumber(c.getString(c.getColumnIndex("number")));
                call.setName(c.getString(c.getColumnIndex("name")));
                call.setDirection(c.getInt(c.getColumnIndex("direction")));
                call.setDuration(c.getInt(c.getColumnIndex(KEY_DURATION)));
                arrCall.add(call);
            }
            c.close();
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return arrCall;
        } catch (SQLException e) {
            FileManager.WriteLog(3, TAG, "Error on getAllCalls function: " + e.toString());
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

    public static Call_DTO get(Context context, long rowId) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToRead();
            Cursor c = conn.getDB().query(true, DATABASE_TABLE, new String[]{"_id", "time", "direction", "number", "name", KEY_DURATION}, "_id=" + rowId, null, null, null, null, null);
            if (c == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return null;
            }
            Call_DTO call = null;
            if (c.moveToNext()) {
                call = new Call_DTO();
                call.setID(c.getLong(c.getColumnIndex("_id")));
                call.setTime(c.getLong(c.getColumnIndex("time")));
                call.setNumber(c.getString(c.getColumnIndex("number")));
                call.setName(c.getString(c.getColumnIndex("name")));
                call.setDirection(c.getInt(c.getColumnIndex("direction")));
                call.setDuration(c.getInt(c.getColumnIndex(KEY_DURATION)));
            }
            c.close();
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return call;
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

    public static boolean update(Context context, Call_DTO call) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            ContentValues args = new ContentValues();
            args.put("time", String.valueOf(call.getTime()));
            args.put("direction", String.valueOf(call.getDirection()));
            args.put("number", String.valueOf(call.getNumber()));
            args.put("name", String.valueOf(call.getName()));
            args.put(KEY_DURATION, String.valueOf(call.getDuration()));
            boolean result = false;
            if (conn.getDB().update(DATABASE_TABLE, args, "_id=" + call.getID(), null) > 0) {
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
