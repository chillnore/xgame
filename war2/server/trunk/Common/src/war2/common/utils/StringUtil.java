package war2.common.utils;

/**
 * 字符串工具类
 * 
 * @author hjj2017
 *
 */
public class StringUtil {
	/**
	 * 类默认构造器
	 * 
	 */
	private StringUtil() {
	}

	/**
	 * 使用连接符连接字符串数组
	 * 
	 * @param strs
	 * @param conn
	 * @return
	 */
	public static String join(String[] strs, char conn) {
		return join(strs, String.valueOf(conn));
	}

	/**
	 * 使用连接符连接字符串数组
	 * 
	 * @param strs
	 * @param conn
	 * @return 
	 * 
	 */
	public static String join(String[] strs, String conn) {
		if (strs == null || 
			strs.length <= 0) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < strs.length; i++) {
			if (i > 0) {
				// 添加连接符
				sb.append(conn);
			}

			// 添加字符串
			sb.append(strs[i]);
		}

		return sb.toString();
	}
}
