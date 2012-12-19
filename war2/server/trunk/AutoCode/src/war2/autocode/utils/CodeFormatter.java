package war2.autocode.utils;

/**
 * Java 代码格式化工具
 * 
 * @author AfritXia
 * @since 2011/10/30
 * @version $Rev: 0 $
 * 
 */
public class CodeFormatter {
	/**
	 * 格式化 JAVA 代码
	 * 
	 * @param code
	 * @param tabIndex
	 * @return 
	 * 
	 */
	public static String formatJava(String code, int tabIndex) {
		if (code == null || 
			code.isEmpty()) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		String[] lines = code.split("\r\n");

		for (String ln : lines) {
			// 清除行尾的空白
			String tmp = ln.trim();

			if (tmp.length() <= 0) {
				continue;
			}

			if (tmp.startsWith("}")) {
				tabIndex = (--tabIndex < 0) ? 0 : tabIndex;
			}

			for (int i = 0; i < tabIndex; i++) {
				sb.append("\t");
			}

			sb.append(tmp);
			sb.append("\r\n");

			if (tmp.endsWith("{")) {
				tabIndex++;
			}
		}

		return sb.toString();
	}
}
