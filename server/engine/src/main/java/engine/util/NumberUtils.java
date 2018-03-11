package engine.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtils {

	/**
	 * 
	 * @param s
	 *            2*3*4
	 * @return 24
	 */
	public static int str2int(String s) {
		int r = 1;

		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(s);
		while (matcher.find()) {
			r *= Integer.parseInt(matcher.group());
		}

		return r;
	}
}
