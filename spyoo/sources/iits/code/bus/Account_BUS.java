package iits.code.bus;

import android.content.Context;
import iits.code.dao.Account_DAO;
import iits.code.dto.Account_DTO;

public class Account_BUS {
    public static void createTable(Context context) {
        Account_DAO.createTable(context);
    }

    public static long insert(Context context, Account_DTO account) {
        return Account_DAO.insert(context, account);
    }

    public static boolean deleteAlls(Context context) {
        return Account_DAO.deleteAlls(context);
    }

    public static Account_DTO get(Context context) {
        return Account_DAO.get(context);
    }

    public static boolean update(Context context, Account_DTO account) {
        return Account_DAO.update(context, account);
    }
}
