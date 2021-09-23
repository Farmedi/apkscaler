package iits.code.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import iits.code.SQLiteConnection;
import iits.code.dto.GPS_DTO;
import java.util.ArrayList;

public class GPS_DAO {
    private static final String CREATE_TABLE_GPS = "create table if not exists GPS (_id integer primary key autoincrement, time text not null, latitude text not null, altitude text not null, longitude text not null);";
    private static final String DATABASE_TABLE = "GPS";
    public static final String KEY_ALTITUDE = "altitude";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_TIME = "time";
    private static final String TAG = "GPS_DAO";

    public static void createTable(Context context) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            conn.createTable(CREATE_TABLE_GPS);
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

    public static long insert(Context context, GPS_DTO gps) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            ContentValues initialValues = new ContentValues();
            initialValues.put("time", String.valueOf(gps.getTime()));
            initialValues.put(KEY_LATITUDE, String.valueOf(gps.getLatitude()));
            initialValues.put(KEY_LONGITUDE, String.valueOf(gps.getLongitude()));
            initialValues.put(KEY_ALTITUDE, String.valueOf(gps.getAltitude()));
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
            Log.e(TAG, "Error on deleteCall function: " + e.toString());
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

    public static ArrayList<GPS_DTO> getAlls(Context context) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToRead();
            ArrayList<GPS_DTO> arrGPS = new ArrayList<>();
            Cursor c = conn.getDB().query(DATABASE_TABLE, new String[]{"_id", "time", KEY_LATITUDE, KEY_LONGITUDE, KEY_ALTITUDE}, null, null, null, null, "_id DESC");
            if (c == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return null;
            }
            while (c.moveToNext()) {
                GPS_DTO gps = new GPS_DTO();
                gps.setID(c.getLong(c.getColumnIndex("_id")));
                gps.setTime(c.getLong(c.getColumnIndex("time")));
                gps.setLatitude(c.getString(c.getColumnIndex(KEY_LATITUDE)));
                gps.setLongitude(c.getString(c.getColumnIndex(KEY_LONGITUDE)));
                gps.setAltitude(c.getString(c.getColumnIndex(KEY_ALTITUDE)));
                arrGPS.add(gps);
            }
            c.close();
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return arrGPS;
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

    public static GPS_DTO get(Context context, long rowId) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToRead();
            Cursor c = conn.getDB().query(true, DATABASE_TABLE, new String[]{"_id", "time", KEY_LATITUDE, KEY_LONGITUDE, KEY_ALTITUDE}, "_id=" + rowId, null, null, null, null, null);
            if (c == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return null;
            }
            GPS_DTO gps = null;
            if (c.moveToNext()) {
                gps = new GPS_DTO();
                gps.setID(c.getLong(c.getColumnIndex("_id")));
                gps.setTime(c.getLong(c.getColumnIndex("time")));
                gps.setLatitude(c.getString(c.getColumnIndex(KEY_LATITUDE)));
                gps.setLongitude(c.getString(c.getColumnIndex(KEY_LONGITUDE)));
                gps.setAltitude(c.getString(c.getColumnIndex(KEY_ALTITUDE)));
            }
            c.close();
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return gps;
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

    public static GPS_DTO getLastGPS(Context context) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToRead();
            Cursor c = conn.getDB().query(true, DATABASE_TABLE, new String[]{"_id", "time", KEY_LATITUDE, KEY_LONGITUDE, KEY_ALTITUDE}, null, null, null, null, "_id DESC", "1");
            if (c == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return null;
            }
            GPS_DTO gps = null;
            if (c.moveToNext()) {
                gps = new GPS_DTO();
                gps.setID(c.getLong(c.getColumnIndex("_id")));
                gps.setTime(c.getLong(c.getColumnIndex("time")));
                gps.setLatitude(c.getString(c.getColumnIndex(KEY_LATITUDE)));
                gps.setLongitude(c.getString(c.getColumnIndex(KEY_LONGITUDE)));
                gps.setAltitude(c.getString(c.getColumnIndex(KEY_ALTITUDE)));
            }
            c.close();
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return gps;
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

    public static boolean update(Context context, GPS_DTO gps) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            ContentValues args = new ContentValues();
            args.put("time", String.valueOf(gps.getTime()));
            args.put(KEY_LATITUDE, String.valueOf(gps.getLatitude()));
            args.put(KEY_LONGITUDE, String.valueOf(gps.getLongitude()));
            args.put(KEY_ALTITUDE, String.valueOf(gps.getAltitude()));
            boolean result = false;
            if (conn.getDB().update(DATABASE_TABLE, args, "_id=" + gps.getID(), null) > 0) {
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
