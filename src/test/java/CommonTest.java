import DB.AccountDao;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

/**
 * Created by admin on 2016/5/28.
 */
public class CommonTest {
    public static void main(String[] args) throws UnsupportedEncodingException, SQLException {
        AccountDao dao = new AccountDao();
        int account = dao.updateAccount("update account set ?=?, ?=?, ?=? where ?=?", new String[]{"passwd", "sex", "area", "userId"},
                new String[]{"123123", "2", "asdaaa", "12345"});
        System.out.println(account);
    }
}
