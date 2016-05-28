package DB;

import Utils.StringUtils;

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

    private static DBHelper instance;

    public DBHelper() {
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static DBHelper getInstance() {
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
                    instance = new DBHelper();
                }
            }
        }
        return instance;
    }

    public DBHelper(String sql) {
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            pst = conn.prepareStatement(sql);//准备执行语句
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement insert(String sql, String[] values) throws SQLException {
        this.pst = conn.prepareStatement(sql);
        int num = new StringUtils(sql).getCharNum('?');
        for (int i = 1; i <= num; i++) {
            this.pst.setString(i, values[i]);
        }
        pst.executeUpdate();
        return pst;
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