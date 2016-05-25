package Base;

import DB.DBHelper;
import Utils.SHAUtil;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by admin on 2016/5/24.
 */
public class HTTPResponse {
    private final Socket connection;
    private OutputStream out;
    private InputStream in;
    private final static Logger logger = Logger.getLogger(HTTPResponse.class.getCanonicalName());
    private Writer writer;

    public HTTPResponse(Socket connection) {
        this.connection = connection;

        try {
            out = connection.getOutputStream();
            writer = new OutputStreamWriter(out);
            in = connection.getInputStream();
            // sendHeader("text/html", "register success.", 0);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error init HTTPResponse", ex);
        }
    }

    public void response() {
        HTTPRequest request = new HTTPRequest(connection);
        String method = request.getMethod();
        if (method.toUpperCase().equals("POST")) {
            String[] contentTypes = request.getContenttype();
            if (contentTypes == null) {
                try {
                    sendHeader("text/html", "register success.", 0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            for (String contentType : contentTypes) {
                if (contentType.contains("application/x-www-form-urlencoded")) {
                    if (request.getFileName().equals("/register")) {
                        try {
                            sendHeader("text/html", "register success.", 0);
                            register(request);
                            return;
                        } catch (IOException e) {
                            logger.log(Level.SEVERE, "Register Error!", e);
                        }
                    }
                }
            }
        }
    }

    public void register(HTTPRequest request) throws IOException {
        String string = request.getRequestbody();
        String[] split = string.split("&");
        String userId = URLDecoder.decode(split[0].split("=")[1], "utf-8");
        String sql = "select userId from account";//SQL语句
        DBHelper db = new DBHelper(sql);//创建DBHelper对象
        DBHelper insert = null;
        ResultSet ret = null;
        try {
            ret = db.pst.executeQuery();//执行语句，得到结果集
            while (ret.next()) {
                String uid = ret.getString(1);
                if (uid.equals(userId)) {
                    sendError("The userId has already existed.Register error happens.", 150);
                    return;
                }
            }
            String passwd = URLDecoder.decode(split[1].split("=")[1], "utf-8");
            insert = new DBHelper("insert into account (userId, passwd) values(?, ?) ");
            insert.pst.setString(1, userId);
            insert.pst.setString(2, SHAUtil.shaEncode(passwd));
            insert.pst.executeUpdate();
            sendRegisterSuccess(userId, passwd);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "sql error!", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "encoding error!", e);
            e.printStackTrace();
        } finally {
            if (insert != null)
                insert.close();
            db.close();
        }

    }

    private void sendRegisterSuccess(String userId, String passwd) throws IOException {
        // JSONObject jsonObject = new JSONObject();
        // jsonObject.put("flag", true);
        // Map<String, String> datas = new HashMap<>();
        // datas.put("userId", userId);
        // datas.put("passwd", passwd);
        // jsonObject.put("data", datas);
        // String json = jsonObject.toString();
        // writer.write("Content-length: " + json.length() + "\r\n\r\n");
        // writer.write(json);
        // writer.flush();
        // writer.close();
    }


    private void sendHeader(String contentType, String message, int length) throws IOException {
        writer.write("HTTP/1.1 200 OK" + "\r\n");
        Date now = new Date();
        writer.write("Date: " + now + "\r\n");
        writer.write("Server: ChatServer 2.0\r\n");
        writer.write("Content-type: " + contentType + "\r\n");
        writer.flush();
    }

    private void sendError(String errorMessage, int errorCode) throws IOException {
        // JSONObject jsonObject = new JSONObject();
        // jsonObject.put("flag", false);
        // jsonObject.put("errorCode", errorCode);
        // jsonObject.put("errorString", errorMessage);
        // String json = jsonObject.toString();
        // writer.write("Content-length: " + json.length() + "\r\n\r\n");
        // writer.write(json);
        // writer.flush();
        // writer.close();
    }
}
