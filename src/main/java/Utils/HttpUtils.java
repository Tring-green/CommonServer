package Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by admin on 2016/5/28.
 */
public class HttpUtils {

    public static String[] splitBody(String body, String[] key) throws UnsupportedEncodingException {
        body += "&";
        String[] result = new String[key.length];
        for (int i = 0; i < key.length; i++) {
            String s = RegexUtils.RegexGroup(body, key[i] + "=(.+?)&", 1).trim();
            result[i] = s == null ? null : URLDecoder.decode(s, "utf-8");
        }
        return result;
    }
}
