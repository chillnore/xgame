package war2.autocode.utils;

/**
 * 字符串实用工具类
 * 
 * @author AfritXia
 * @since 2011/06/18
 * @version $Rev: 30 $
 *
 */
public final class StringUtil {
	/**
	 * 类默认构造器
	 * 
	 */
	private StringUtil() {
	}

	/**
	 * 是否为空字符串
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNullOrEmpty(String s) {
		return (s == null || s.isEmpty());
	}

	/**
	 * 首字母大写
	 * 
	 * @param s
	 * @return
	 */
	public static String firstCharToUpperCase(String s) {
		if (s == null || 
			s.equals("")) {
			return s;
		}

		if (s.length() == 1) {
			return s.toUpperCase();
		}

		return String.valueOf(s.charAt(0)).toUpperCase() + s.substring(1);
	}

	/**
	 * 首字母小写
	 * 
	 * @param s
	 * @return
	 */
	public static String firstCharToLowerCase(String s) {
		if (s == null || 
			s.equals("")) {
			return s;
		}

		if (s.length() == 1) {
			return s.toUpperCase();
		}

		return String.valueOf(s.charAt(0)).toLowerCase() + s.substring(1);
	}

	/**
	 * 以字符串 x 将 s 分割为字符串数组, 并将使用字符串 v 连接字符串数组
	 * 
	 * @param s
	 * @param x
	 * @param v
	 * @return
	 * 
	 */
	public static String splitAndJoin(String s, String x, String v) {
		if (s == null || 
			s.isEmpty()) {
			return null;
		}

		if (x == null || 
			x.isEmpty()) {
			return s;
		}

		if (v == null) {
			v = "";
		}

		String[] ss = s.split(x);
		String result = "";

		for (String t : ss) {
			result += (v + t);
		}

		return result.substring(v.length());
	}
}
