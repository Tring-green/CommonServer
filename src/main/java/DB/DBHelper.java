package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBHelper {
    public static final String url = "jdbc:mysql://127.0.0.1/chatdb";
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = "root";
    public static final String password = "zhh123456";
    private final static Logger logger = Logger.getLogger(DBHelper.class.getCanonicalName());

    public Connection conn = null;
    public PreparedStatement pst = null;

    public DBHelper(String sql) {
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            pst = conn.prepareStatement(sql);//准备执行语句
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (this.conn != null)
                this.conn.close();
            if (this.pst != null)
                this.pst.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "database close error.", e);
        }
    }
}  