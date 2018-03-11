package engine.string;

import java.io.UnsupportedEncodingException;

public class URLUtil {
	private final static String ENCODE = "UTF-8";

	public static String decode(String str) {
		if (str == null)
			return null;

		String result = null;
		try {
			result = java.net.URLDecoder.decode(str, ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String encode(String str) {
		if (str == null)
			return null;

		String result = null;
		try {
			result = java.net.URLEncoder.encode(str, ENCODE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
}
