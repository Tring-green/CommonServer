package Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	public static String RegexGroup(String targetStr, String patternStr, int which){
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(targetStr);
		if(matcher.find()){
			return matcher.group(which);
		}
		return null;
	}
	public static String RegexString(String targetStr, String patternStr){
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(targetStr);
		if(matcher.find()){
			return matcher.group();
		}
		return null;
	}
}
