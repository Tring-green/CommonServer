import DB.DBHelper;

import java.sql.SQLException;

/**
 * Created by admin on 2016/5/28.
 */
public class TestDB {
    public static void main(String[] args) throws SQLException {
        DBHelper insert = new DBHelper("insert into Account (userId, passwd, token) values(?, ?, ?) ");
        insert.pst.setString(1, "abc");
        insert.pst.setString(2, "cde");
        insert.pst.setString(3, "efc");
        insert.pst.executeUpdate();

        insert.pst = insert.conn.prepareStatement("insert into Account (userId, passwd, token) values(?, ?, ?) ");
        insert.pst.setString(1, "abcd");
        insert.pst.setString(2, "cded");
        insert.pst.setString(3, "efcd");
        insert.pst.executeUpdate();
        if (insert != null)
            insert.close();
    }
}
