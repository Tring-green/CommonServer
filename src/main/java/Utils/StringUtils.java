package Utils;

/**
 * Created by admin on 2016/5/28.
 */
public class StringUtils {
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 ? true : false;
    }

    public static String replaceOneyByOne(String target, String[] replacement, String c) {
        for (int i =0;i<replacement.length;i++) {
            target = target.replaceFirst(c, replacement[i] + "=");
        }
        return target;
    }
}
