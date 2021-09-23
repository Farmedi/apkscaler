package iits.code.bus;

import android.content.ContentResolver;
import iits.code.dao.SMS_History_DAO;
import iits.code.dto.SMS_DTO;
import java.util.ArrayList;

public class SMS_History_BUS {
    public static ArrayList<SMS_DTO> getAlls(ContentResolver cr) {
        return SMS_History_DAO.getAlls(cr);
    }

    public static int getSMSSentCount(ContentResolver cr) {
        return SMS_History_DAO.getSMSSentCount(cr);
    }

    public static SMS_DTO getSMSSentLast(ContentResolver cr) {
        return SMS_History_DAO.getSMSSentLast(cr);
    }

    public static SMS_DTO getSMSOutBoxLast(ContentResolver cr) {
        return SMS_History_DAO.getSMSOutBoxLast(cr);
    }

    public static int getSMSReceiverCount(ContentResolver cr) {
        return SMS_History_DAO.getSMSReceiverCount(cr);
    }

    public static SMS_DTO getSMSReceiverLast(ContentResolver cr) {
        return SMS_History_DAO.getSMSReceiverLast(cr);
    }

    public static int getCount(ContentResolver cr) {
        return SMS_History_DAO.getCount(cr);
    }

    public static int getThreadCount(ContentResolver cr) {
        return SMS_History_DAO.getThreadCount(cr);
    }

    public static SMS_DTO getLastSMS(ContentResolver cr) {
        return SMS_History_DAO.getLastSMS(cr);
    }

    public static SMS_DTO getSMSNew(ContentResolver cr, long id, long time) {
        return SMS_History_DAO.getSMSNew(cr, id, time);
    }

    public static boolean delete(ContentResolver cr, long id) {
        return SMS_History_DAO.delete(cr, id);
    }
}
