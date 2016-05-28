import Utils.StringUtils;

/**
 * Created by admin on 2016/5/28.
 */
public class CommonTest {
    public static void main(String[] args) {
        StringUtils stringUtils = new StringUtils("abcccqwecsadajiwe");
        int num = stringUtils.getCharNum('c');
        System.out.println(num);
    }
}
