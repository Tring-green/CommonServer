package Base;

import Utils.StreamUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by admin on 2016/5/24.
 */
public class HTTPRequest {
    private final static Logger logger = Logger.getLogger(HTTPRequest.class.getCanonicalName());

    public final Socket connection;

    private Map<String, String[]> content = new HashMap<>();
    private String header;
    private String mMethod;
    private String mFileName;
    private String mVersion;
    private String mRequestbody;

    public HTTPRequest(Socket connection) {
        this.connection = connection;
        handleRequest();
    }

    private void handleRequest() {
        try {
            InputStream in = connection.getInputStream();
            BufferedReader br = StreamUtils.createBufferedReader(in, "US-ASCII");
            String requestLine;
            List<String> requestHeader = new ArrayList<>();
            int len;
            long startTime = System.currentTimeMillis();
            // while ((len = in.read()) != -1) {
            //     System.out.print((char)len);
            // }
            while (true) {
                requestLine = br.readLine();
                if (requestLine.length() != 0) {
                    requestHeader.add(requestLine);
                } else {
                    mRequestbody =  br.readLine();
                    break;
                }
            }
            header = requestHeader.get(0);
            String[] tokens = header.split("\\s+");
            mMethod = tokens[0];
            mFileName = tokens[1];
            mVersion = tokens[2];
            logger.info("request header:\r\n" + mMethod + " " + mFileName + " " + mVersion);
            logger.info(connection.getRemoteSocketAddress() + " " + mMethod);
            for (int i = 1; i < requestHeader.size(); i++) {
                String line = requestHeader.get(i);
                String[] split = line.split(":");
                String[] strings = split[1].split(";");
                content.put(split[0].trim(), strings);
                logger.info(split[0].trim() + " " + strings[0]);
            }


        } catch (IOException ex) {
            logger.severe("error handle request!");
        }
    }

    public String getRequestbody() {
        return mRequestbody;
    }

    public String[] getValue(String key) {
        return content.get(key);
    }

    public String getMethod() {
        return mMethod;
    }

    public String getFileName() {
        return mFileName;
    }

    public String getVersion() {
        return mVersion;
    }

    public String[] getContenttype() {
        return content.get("Content-Type");
    }


}
