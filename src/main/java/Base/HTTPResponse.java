package Base;

import DB.AccountDao;
import DB.DBHelper;
import Domain.Account;
import Domain.HTTPError;
import Domain.HTTPSuccess;
import Lib.SPError;
import Utils.HttpUtils;
import Utils.SHAUtil;
import Utils.StringUtils;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
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
    private Gson gson;
    private String mRequestbody;
    private HTTPRequest mRequest;

    public HTTPResponse(Socket connection) {
        this.connection = connection;
        this.gson = new Gson();
        try {
            out = connection.getOutputStream();
            writer = new OutputStreamWriter(out, "utf-8");
            in = connection.getInputStream();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error init HTTPResponse", ex);
        }
    }

    public void response() {
        try {
            mRequest = new HTTPRequest(connection);
            String method = mRequest.getMethod();
            if (method.toUpperCase().equals("POST")) {
                mRequestbody = mRequest.getRequestbody();
                if (StringUtils.isEmpty(mRequestbody)) {
                    return;
                }
                String[] contentTypes = mRequest.getContenttype();
                if (contentTypes == null) {
                    sendError("format error", 100);
                }
                if (mRequest.containsInContentType("application/x-www-form-urlencoded")) {
                    if (mRequest.getFileName().equals("/register")) {
                        register();
                    }
                    if (mRequest.getFileName().equals("/user/nameChange")) {
                        updateAccount();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    sendError("server error!", SPError.ERROR_SERVER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateAccount() throws IOException, SQLException {
        String userId = mRequest.getValue("userId")[0];
        String token = mRequest.getValue("token")[0];
        String[] result = HttpUtils.splitBody(mRequestbody, new String[]{"name"});
        String name = result[0];
        int update = DBHelper.getInstance().update("update Account set name=? where userId=?", new String[]{name, userId});
        if(update == 0)
            sendError("update error", 160);
        else
            sendSuccess("update succeed!");
    }



    public void register() throws IOException, SQLException {
        String[] result = HttpUtils.splitBody(mRequestbody, new String[]{"userId", "passwd"});
        String userId = result[0], passwd = result[1];
        AccountDao dao = new AccountDao();
        Account account = dao.getByUserId(userId);
        if (account != null) {
            sendError("The userId has already existed.Register error happens.", 150);
            return;
        }
        try {
            passwd = SHAUtil.shaEncode(passwd);
            String token = SHAUtil.shaEncode(passwd);
            DBHelper.getInstance().insert("insert into Account (userId, passwd, token) values(?, ?, ?)",
                    new String[]{userId, passwd, token});
            sendRegisterSuccess(userId, token);
        } catch (SQLException e) {
            sendError("sql error!", 151);
        } catch (Exception e) {
            sendError("encoding error!", 152);
        }
    }

    private void sendRegisterSuccess(String userId, String token) throws IOException {
        logger.info("register success!");
        sendHeader("text/html");
        Account account = new Account(true, new Account.Data(userId, null, "张三", 0, "/test/images/zhangsan.png",
                "我的个性签名", "深圳", token));
        writeContentLengthAndClose(gson.toJson(account));
    }

    private void sendError(String errorMessage, int errorCode) throws IOException {
        logger.log(Level.SEVERE, errorMessage);
        sendHeader("text/html");
        HTTPError error = new HTTPError(errorCode, errorMessage);
        writeContentLengthAndClose(gson.toJson(error));
    }

    private void sendSuccess(String success) throws IOException {
        logger.log(Level.INFO, success);
        sendHeader("text/html");
        HTTPSuccess httpSuccess = new HTTPSuccess(true);
        writeContentLengthAndClose(gson.toJson(httpSuccess));
    }
    private void sendHeader(String contentType) throws IOException {
        writer.write("HTTP/1.1 200 OK" + "\r\n");
        Date now = new Date();
        writer.write("Date: " + now + "\r\n");
        writer.write("Server: ChatServer 2.0\r\n");
        writer.write("Content-type: " + contentType + "\r\n");
        writer.flush();
    }

    private void writeContentLengthAndClose(String str) throws IOException {
        writer.write("Content-length: " + str.length() + "\r\n\r\n");
        writer.write(str);
        writer.flush();
        writer.close();
        writer = null;
    }
}
