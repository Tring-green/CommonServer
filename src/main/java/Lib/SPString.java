package Lib;

/**
 * Created by admin on 2016/5/28.
 */
public class SPString {
    private final String mString;
    public SPString(String string) {
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
