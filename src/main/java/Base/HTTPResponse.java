package Base;

import DB.DBHelper;
import Domain.HTTPError;
import Domain.HttpRegister;
import Utils.SHAUtil;
import com.google.gson.Gson;

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
                    sendHeader("text/html");
                    sendError("format error", 100);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            for (String contentType : contentTypes) {
                if (contentType.contains("application/x-www-form-urlencoded")) {
                    if (request.getFileName().equals("/register")) {
                        try {
                            register(request);
                            return;
                        } catch (IOException e) {
                            try {
                                sendHeader("text/html");
                                logger.log(Level.SEVERE, "Register Error!", e);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    public void register(HTTPRequest request) throws IOException {
        String string = request.getRequestbody();
        String[] split = string.split("&");
        String[] s1 = split[0].split("=");
        String[] s2 = split[1].split("=");
        String userId = null, passwd = null;
        if (s1[0].equals("userId")) {
            userId = URLDecoder.decode(s1[1], "utf-8");
            passwd = URLDecoder.decode(s2[1], "utf-8");
        } else {
            userId = URLDecoder.decode(s2[1], "utf-8");
            passwd = URLDecoder.decode(s1[1], "utf-8");
        }
        String sql = "select userId from account";//SQL语句
        DBHelper db = new DBHelper(sql);//创建DBHelper对象
        DBHelper insert = null;
        ResultSet ret = null;
        try {
            ret = db.pst.executeQuery();//执行语句，得到结果集
            if (ret != null) {
                while (ret.next()) {
                    String uid = ret.getString(1);
                    if (uid.equals(userId)) {
                        sendHeader("text/html");
                        sendError("The userId has already existed.Register error happens.", 150);
                        return;
                    }
                }
            }
            insert = new DBHelper("insert into account (userId, passwd) values(?, ?) ");
            insert.pst.setString(1, userId);
            insert.pst.setString(2, SHAUtil.shaEncode(passwd));insert.pst.executeUpdate();
            sendHeader("text/html");
            sendRegisterSuccess(userId, passwd);
            logger.log(Level.INFO, "register success!");
        } catch (SQLException e) {
            sendHeader("text/html");
            sendError("sql error!", 151);
            logger.log(Level.SEVERE, "sql error!", e);
        } catch (Exception e) {
            sendHeader("text/html");
            sendError("encoding error!", 152);
            logger.log(Level.SEVERE, "encoding error!", e);
            e.printStackTrace();
        } finally {
            if (insert != null)
                insert.close();
            db.close();
        }

    }

    private void sendRegisterSuccess(String userId, String passwd) throws IOException {
        Gson gson = new Gson();
        HttpRegister register = new HttpRegister();
        register.setFlag(true);
        register.setData(new HttpRegister.datas(userId, passwd));
        String json = gson.toJson(register);
        writer.write("Content-length: " + json.length() + "\r\n\r\n");
        writer.write(json);
        writer.flush();
        writer.close();
    }


    private void sendHeader(String contentType) throws IOException {
        writer.write("HTTP/1.1 200 OK" + "\r\n");
        Date now = new Date();
        writer.write("Date: " + now + "\r\n");
        writer.write("Server: ChatServer 2.0\r\n");
        writer.write("Content-type: " + contentType + "\r\n");
        writer.flush();
    }

    private void sendError(String errorMessage, int errorCode) throws IOException {
        HTTPError error = new HTTPError(errorCode, errorMessage);
        Gson gson = new Gson();
        String json = gson.toJson(error);
        writer.write("Content-length: " + json.length() + "\r\n\r\n");
        writer.write(json);
        writer.flush();
        writer.close();
    }
}
