package engine.string;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 判断一个string是否是纯数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 将String分割
	 * @param string
	 * @param sp1
	 * @param sp2
	 * @return
	 */
	public static Map<String, String> split(String string, String sp1, String sp2) {
		String[] contexts = string.split(sp1);
		Map<String,String> ret = new HashMap<>();
		for (String str : contexts) {
			String[] subcontexts = str.split(sp2);
			if (subcontexts.length == 2) {
				ret.put(URLUtil.decode(subcontexts[0]), URLUtil.decode(subcontexts[1]));
			}
 		}
		return ret;
	}


	public static String repeat(String s, int count) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append(s);
		}
		return sb.toString();
	}

	/**
	 * 将指定字符进行格式化
	 * 
	 * @param s
	 * @param objs
	 * @return
	 */
	public static String format(String s, Object... objs) {
		if (objs != null) {
			for (int i = 0; i < objs.length; i++) {
				String sobj = objs[i].toString();
				s = s.replace("{" + i + "}", sobj);
			}
		}
		return s;
	}

	/**
	 * 如果是utf8MB4区的字符，不予通过
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static boolean checkUTF8MB4(String str) {
		if (null == str || str.length() == 0) {
			return false;
		}
		try {
			byte[] bytes = str.getBytes("UTF-8");
			for (int i = 0; i < bytes.length; i++) {
				byte b = bytes[i];
				// 四字节字符即utf8mb4字符
				if ((b & 0xF0) == 0xF0) {
					return true;
				}
				// 三字节字符
				if ((b & 0xE0) == 0xE0) {
					i += 2;
					continue;
				}
				// 双字节字符
				if ((b & 0xC0) == 0xC0) {
					i += 1;
					continue;
				}
				// 单字节字符(跳过)
				// if (b >> 7 == 0) {
				// continue;
				// }
			}
		} catch (Exception e) {
			return true; // 有异常就当是有非法
		}
		return false;
	}

	/**
	 * 转换为字符串唯一键
	 * 
	 * @param str
	 * @return
	 */
	public static String toUniqueKey(String str) {
		String key = str.trim();
		key = key.replaceAll("[¹]", "1");
		key = key.replaceAll("[²]", "2");
		key = key.replaceAll("[³]", "3");
		key = key.replaceAll("[a,ª,À,Á,Â,Ã,Ä,Å,à,á,â,ã,ä,å]", "A");
		key = key.replaceAll("[b]", "B");
		key = key.replaceAll("[c,Ç,ç,ç]", "C");
		key = key.replaceAll("[d]", "D");
		key = key.replaceAll("[e,È,É,Ê,Ë,è,é,ê,ë]", "E");
		key = key.replaceAll("[f]", "F");
		key = key.replaceAll("[g]", "G");
		key = key.replaceAll("[h]", "H");
		key = key.replaceAll("[i,Ì,Í,Î,Ï,ì,í,î,ï]", "I");
		key = key.replaceAll("[j]", "J");
		key = key.replaceAll("[k]", "K");
		key = key.replaceAll("[l]", "L");
		key = key.replaceAll("[m]", "M");
		key = key.replaceAll("[n,Ñ,ñ]", "N");
		key = key.replaceAll("[o,º,Ò,Ó,Ô,Õ,Ö,ò,ó,ô,õ,ö]", "O");
		key = key.replaceAll("[p]", "P");
		key = key.replaceAll("[q]", "Q");
		key = key.replaceAll("[r]", "R");
		key = key.replaceAll("[s,Š,š]", "S");
		key = key.replaceAll("[t]", "T");
		key = key.replaceAll("[u,Ù,Ú,Û,Ü,ù,ú,û,ü]", "U");
		key = key.replaceAll("[v]", "V");
		key = key.replaceAll("[w]", "W");
		key = key.replaceAll("[x]", "X");
		key = key.replaceAll("[y,Ÿ,Ý,ý]", "Y");
		key = key.replaceAll("[z,Ž,ž]", "Z");
		key = key.replaceAll("[œ]", "Œ");
		key = key.replaceAll("[æ]", "Æ");
		key = key.replaceAll("[ð]", "Ð");
		key = key.replaceAll("[ø]", "Ø");
		key = key.replaceAll("[þ]", "Þ");
		return key;
	}

}
