package DB;

import java.sql.*;
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

    public String findAccoun() {
        return null;
    }

    public DBHelper insert(String sql, String[] values) throws SQLException {
        pst = conn.prepareStatement(sql);
        for (int i = 1; i <= values.length; i++) {
            pst.setString(i, values[i - 1]);
        }
        pst.executeUpdate();
        return this;
    }

    public ResultSet query(String sql) throws SQLException {
        pst = conn.prepareStatement(sql);
        return pst.executeQuery();
    }

    public ResultSet query(String sql, String[] values) throws SQLException {
        pst = conn.prepareStatement(sql);
        for (int i = 1; i <= values.length; i++) {
            pst.setString(i, values[i - 1]);
        }
        return pst.executeQuery();
    }

    public int update(String sql, String[] newValue) throws SQLException {
        pst = conn.prepareStatement(sql);
        int i;
        for (i = 1; i <= newValue.length; i++) {
            pst.setString(i, newValue[i - 1]);
        }
        return pst.executeUpdate();
    }


    public static void close() {
        try {
            if (instance.conn != null)
                instance.conn.close();
            if (instance.pst != null)
                instance.pst.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "database close error.", e);
        }
    }
}  