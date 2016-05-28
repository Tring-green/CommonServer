package DB;

import Domain.Account;
import Utils.StringUtils;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by admin on 2016/5/28.
 */
public class AccountDao {
    private DBHelper mHelper;

    public AccountDao() {
        this.mHelper = DBHelper.getInstance();
    }

    public Account getByUserId(String userId) throws SQLException, UnsupportedEncodingException {
        String sql = "select * from " + SPDB.Account.TABLE_NAME + " where "
                + SPDB.Account.COLUMN_USERID + "=?";
        ResultSet ret = mHelper.query(sql, new String[]{userId});
        if (ret != null) {
            while (ret.next()) {
                String uid = ret.getString(1);
                if (uid.equals(userId)) {
                    Account a = new Account();
                    a.getData().setUserId(uid);
                    a.getData().setPasswd(ret.getString(2));
                    a.getData().setName(ret.getString(3));
                    String sex = ret.getString(4);
                    a.getData().setSex(Integer.parseInt(sex == null ? "0" : sex));
                    a.getData().setIcon(ret.getString(5));
                    a.getData().setSign(ret.getString(6));
                    a.getData().setArea(ret.getString(7));
                    a.getData().setToken(ret.getString(8));
                    return a;
                }
            }
        }
        return null;
    }

    public int updateAccount(String oldSql, String[] key, String[] value) throws SQLException {
        String sql = StringUtils.replaceOneyByOne(oldSql, key, "\\?=");
        return mHelper.update(sql, value);
    }

}
