package war2.autocode.impl.excel.work;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import war2.autocode.impl.excel.model.ExcelBaseTmplClazz;
import war2.autocode.kernal.Logger;
import war2.autocode.utils.CodeFormatter;


/**
 * 格式化 Java 类
 * 
 * @author haijiang.jin
 * @since 2011/06/20
 * @version $Rev: 45 $
 * 
 */
class FormatJavaWork extends AbstractExcelWork {
	/** 格式化开始 */
	private static final String FORMAT_START = "/// __FORMAT_START";
	/** 格式化结束 */
	private static final String FORMAT_OVER = "/// __FORMAT_OVER";
	
	@Override
	protected void workStart(WorkParams params) {
		if (params == null) {
			return;
		}

		// 获取模板类列表
		List<ExcelBaseTmplClazz> cl = params.getExcelBaseTmplClazzList();

		for (ExcelBaseTmplClazz c : cl) {
			// 格式化 Java 代码
			c.setCode(formatJavaCode(c.getCode()));
			// 生成 Java 类
			Logger.log("格式化 Java 代码: " + c.getName());
		}
	}

	/**
	 * 格式化 Java 代码
	 * 
	 * @param code
	 * @return 
	 * 
	 */
	private static String formatJavaCode(String code) {
		if (code == null || 
			code.isEmpty()) {
			return null;
		}

		// 获取需要格式化的字符串
		String formatCode = getNeedToFormatCode(code);
		// 获取 tab 缩进位置
		int tabIndex = getTabIndex(getFormatStartCommonLine(code));

		// 格式化 Java 代码
		String resultCode = CodeFormatter.formatJava(
			formatCode, tabIndex);

		if (resultCode != null) {
			// 将格式化后的代码串替换原来的代码
			code = code.replace(formatCode, resultCode);
		}

		return code;
	}

	/**
	 * 获取需要格式化的代码
	 * 
	 * @param code
	 * @return 
	 * 
	 */
	private static String getNeedToFormatCode(String code) {
		if (code == null || 
			code.isEmpty()) {
			return null;
		}

		int pos0 = code.indexOf(FORMAT_START);
		int pos1 = code.indexOf(FORMAT_OVER, pos0);

		if (pos1 == -1) {
			return null;
		}

		// 过滤掉 __FORMAT_START 整行
		pos0 = code.indexOf("\r\n", pos0) + 2;

		// 获取要格式化的代码
		return code.substring(pos0, pos1);
	}

	/**
	 * 获取格式化开始行 "/// __FORMAT_START(...)"
	 * 
	 * @param code
	 * @return
	 */
	private static String getFormatStartCommonLine(String code) {
		int pos0 = code.indexOf(FORMAT_START);
		int pos1 = code.indexOf("\r\n", pos0);

		if (pos1 == -1) {
			return null;
		}

		return code.substring(pos0, pos1);
	}

	/**
	 * 获取 tab 缩进位置
	 * 
	 * @param formatStartCommonLine
	 * @return
	 * 
	 */
	private static int getTabIndex(String formatStartCommonLine) {
		if (formatStartCommonLine == null || 
			formatStartCommonLine.isEmpty()) {
			return 0;
		}

		// 构建正则表达式
		Pattern p = Pattern.compile("(tabIndex=\\d+?){1}");
		// 获取匹配对象
		Matcher m = p.matcher(formatStartCommonLine);
		
		if (!m.find()) {
			// 如果没有找到任何匹配
			return 0;
		}

		return extractIntVal(m.group());
	}

	/**
	 * 从字符串中获取整数值
	 * 
	 * @param s
	 * @return 
	 * 
	 */
	private static int extractIntVal(String s) {
		if (s == null || 
			s.isEmpty()) {
			return 0;
		}

		// 构建正则表达式
		Pattern p = Pattern.compile("(\\d+?){1}");
		// 获取匹配对象
		Matcher m = p.matcher(s);

		if (!m.find()) {
			// 如果没有找到任何匹配 
			return 0;
		}

		return Integer.parseInt(m.group());
	}
}
