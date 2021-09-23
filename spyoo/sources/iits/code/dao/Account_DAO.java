package iits.code.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;
import iits.code.SQLiteConnection;
import iits.code.dto.Account_DTO;

public class Account_DAO {
    private static final String CREATE_TABLE_ACCOUNT = "create table if not exists Account (_id integer primary key autoincrement, userid text not null, username text not null, password text not null,rememberlogin text not null, serialmachine text not null);";
    private static final String DATABASE_TABLE = "Account";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_REMEMBERLOGIN = "rememberlogin";
    public static final String KEY_SERIALMACHINE = "serialmachine";
    public static final String KEY_USERID = "userid";
    public static final String KEY_USERNAME = "username";
    private static final String TAG = "Account_DAO";

    public static void createTable(Context context) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            conn.createTable(CREATE_TABLE_ACCOUNT);
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

    public static long insert(Context context, Account_DTO account) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            ContentValues initialValues = new ContentValues();
            initialValues.put(KEY_USERID, String.valueOf(account.getUserid()));
            initialValues.put(KEY_USERNAME, String.valueOf(account.getUsername()));
            initialValues.put(KEY_PASSWORD, String.valueOf(account.getPassword()));
            initialValues.put(KEY_REMEMBERLOGIN, String.valueOf(account.getRememberLogin()));
            initialValues.put(KEY_SERIALMACHINE, String.valueOf(account.getSerialMachine()));
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
            Log.e(TAG, "Error on deleteAll function: " + e.toString());
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

    public static Account_DTO get(Context context) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToRead();
            if (conn.getDB() == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return null;
            }
            Cursor c = conn.getDB().query(true, DATABASE_TABLE, new String[]{KEY_USERID, KEY_USERNAME, KEY_PASSWORD, KEY_REMEMBERLOGIN, KEY_SERIALMACHINE}, null, null, null, null, null, null);
            if (c == null) {
                if (conn.isConnection()) {
                    conn.close();
                }
                return null;
            }
            Account_DTO account = null;
            if (c.moveToNext()) {
                account = new Account_DTO();
                account.setUserid(c.getLong(c.getColumnIndex(KEY_USERID)));
                account.setUsername(c.getString(c.getColumnIndex(KEY_USERNAME)));
                account.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
                account.setRememberLogin(Boolean.parseBoolean(c.getString(c.getColumnIndex(KEY_REMEMBERLOGIN))));
                account.setSerialMachine(c.getString(c.getColumnIndex(KEY_SERIALMACHINE)));
            }
            c.close();
            conn.close();
            if (conn.isConnection()) {
                conn.close();
            }
            return account;
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

    public static boolean update(Context context, Account_DTO account) {
        SQLiteConnection conn = new SQLiteConnection(context);
        try {
            conn.openToWrite();
            ContentValues args = new ContentValues();
            args.put(KEY_USERID, String.valueOf(account.getUserid()));
            args.put(KEY_USERNAME, String.valueOf(account.getUsername()));
            args.put(KEY_PASSWORD, String.valueOf(account.getPassword()));
            args.put(KEY_REMEMBERLOGIN, String.valueOf(account.getRememberLogin()));
            args.put(KEY_SERIALMACHINE, String.valueOf(account.getSerialMachine()));
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
