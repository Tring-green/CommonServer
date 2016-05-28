import DB.DBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by admin on 2016/5/28.
 */
public class TestDB {
    public static void main(String[] args) throws SQLException {
        try {
            DBHelper.getInstance().insert("insert into Account (userId, passwd, token) values(?, ?, ?)",
                    new String[]{"abc", "def", "ggg"});

            DBHelper.getInstance().insert("insert into Account (userId, passwd, token) values(?, ?, ?)",
                    new String[]{"asdfa", "asdfasd", "asdf"});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet ret = null;
        ret = DBHelper.getInstance().query("select * from account");//执行语句，得到结果集
        if (ret != null) {
            while (ret.next()) {
                String uid = ret.getString(1);
                System.out.println(uid);
            }
        }
        DBHelper.close();

    }
}
