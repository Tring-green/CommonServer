package Utils;

/**
 * Created by admin on 2016/5/28.
 */
public class StringUtils {
    private final String mString;
    public StringUtils(String string) {
        mString = string;
    }
    public int getCharNum(char c) {
        int num = 0;
        for (int i =0;i<mString.length();i++) {
            num = mString.charAt(i) == c ? ++num : num;
        }
        return num;
    }
}
