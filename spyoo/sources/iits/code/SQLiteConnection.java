package iits.code;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteConnection {
    private static final String DATABASE_NAME = "SPYOO";
    private static final int DATABASE_VERSION = 2;
    private static final String TAG = "SQLiteConnection";
    private final Context context;
    private DataBaseHelper dataBaseHelper = null;
    private SQLiteDatabase db = null;

    public SQLiteConnection(Context context2) {
        this.context = context2;
    }

    public void setDB(SQLiteDatabase db2) {
        this.db = db2;
    }

    public SQLiteDatabase getDB() {
        return this.db;
    }

    /* access modifiers changed from: private */
    public static class DataBaseHelper extends SQLiteOpenHelper {
        DataBaseHelper(Context context) {
            super(context, SQLiteConnection.DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 2);
        }

        public void onCreate(SQLiteDatabase db) {
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion == 1) {
                db.execSQL("DROP TABLE IF EXISTS Setting");
            }
            onCreate(db);
        }
    }

    public void createDataBase() {
        try {
            this.dataBaseHelper = new DataBaseHelper(this.context);
            this.dataBaseHelper.close();
            this.dataBaseHelper = null;
        } catch (SQLException e) {
            Log.e(TAG, "Error on createDataBase function: " + e.toString());
        }
    }

    public void openToWrite() {
        try {
            this.dataBaseHelper = new DataBaseHelper(this.context);
            this.db = this.dataBaseHelper.getWritableDatabase();
        } catch (SQLException e) {
            Log.e(TAG, "Error on openToWrite function: " + e.toString());
        }
    }

    public void openToRead() {
        try {
            this.dataBaseHelper = new DataBaseHelper(this.context);
            this.db = this.dataBaseHelper.getReadableDatabase();
        } catch (SQLException e) {
            Log.e(TAG, "Error on openToRead function: " + e.toString());
            this.db = null;
        }
    }

    public void close() {
        this.dataBaseHelper.close();
        this.dataBaseHelper = null;
    }

    public void deleteDataBase() {
        try {
            if (this.dataBaseHelper != null) {
                this.dataBaseHelper.close();
                if (this.context.deleteDatabase(DATABASE_NAME)) {
                    Log.d(TAG, "deleteDatabase(): database deleted.");
                } else {
                    Log.d(TAG, "deleteDatabase(): database NOT deleted.");
                }
            }
        } catch (SQLException e) {
            Log.e(TAG, "Error on delete function: " + e.toString());
        }
    }

    public void createTable(String sqlString) {
        try {
            if (this.dataBaseHelper != null) {
                this.db.execSQL(sqlString);
            }
        } catch (SQLException e) {
            Log.e(TAG, "Error on createDataTable function: " + e.toString());
        }
    }

    public boolean isConnection() {
        if (this.dataBaseHelper == null) {
            return false;
        }
        return true;
    }
}
