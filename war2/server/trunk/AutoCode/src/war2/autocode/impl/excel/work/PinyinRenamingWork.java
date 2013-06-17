package war2.autocode.impl.excel.work;

import java.util.List;

import war2.autocode.impl.excel.model.ExcelBaseTmplClazz;
import war2.autocode.impl.excel.model.ExcelBaseTmplField;
import war2.autocode.kernal.Logger;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;


/**
 * 拼音命名, 将 column 中文名称翻译成拼音付给对象的 name 属性.
 * <font color='#990000'>注意: 翻译结果不会覆盖 column 值!</font>
 * 拼音的翻译过程调用了 pinyin4j 开源类库
 * 
 * @author AfritXia
 * @since 2011/06/18
 * @version $Rev: 45 $
 *
 */
class PinyinRenamingWork extends AbstractExcelWork {
	/** 拼音格式 */
	private static final 
		HanyuPinyinOutputFormat FORMATTER = new HanyuPinyinOutputFormat();

	static {
		// 输出设置: 小写, 不加音标
		FORMATTER.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		FORMATTER.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		FORMATTER.setVCharType(HanyuPinyinVCharType.WITH_V);
	}

	@Override
	protected void workStart(WorkParams params) {
		if (params == null) {
			return;
		}
		
		// 获取 Excel 模板类列表
		List<ExcelBaseTmplClazz> etcl = params.getExcelBaseTmplClazzList();

		for (ExcelBaseTmplClazz etc : etcl) {
			// 重命名 Excel 模板类
			this.remingExcelTemplateClazz(etc);
			// 记录日志信息
			Logger.log("拼音重命名: " + etc.getSheetName() + " --> " + etc.getName());
		}
	}

	/**
	 * 重命名 Excel 模板类
	 * 
	 * @param etc
	 */
	private void remingExcelTemplateClazz(ExcelBaseTmplClazz etc) {
		// 获取拼音
		String pinyin = stringToPinyin(etc.getSheetName());
		etc.setName(pinyin);

		for (ExcelBaseTmplField f : etc.getFieldList()) {
			// 重命名字段
			this.remingClazzField(f);
		}
	}

	/**
	 * 重命名字段
	 * 
	 * @param cf
	 */
	private void remingClazzField(ExcelBaseTmplField f) {
		if (f == null) {
			return;
		}

		String pinyin = stringToPinyin(f.getColumnName());

		if (pinyin == null || 
			pinyin.isEmpty()) {
			throw new RuntimeException("拼音为空");
		}

		f.setName(pinyin);
	}

	/**
	 * 字符串转成拼音
	 * 
	 * @param s
	 * @return
	 */
	private static String stringToPinyin(String s) {
		if (s == null) {
			return "";
		}

		char[] cs = s.toCharArray();

		String firstWord;
		StringBuffer sb = new StringBuffer();

		for (char c : cs) {
			if ((int)c >=0 && (int)c <= 128) {
				// 是 ascii 码, 不是中文
				sb.append(c);
			} else {
				// 获取拼音字符串
				String pinyin = charToPinyin(c);
	
				// 将首字母大写
				firstWord = String.valueOf(pinyin.charAt(0));
				firstWord = firstWord.toUpperCase();
				
				sb.append(firstWord 
					+ pinyin.substring(1));
			}
		}

		return sb.toString();
	}

	/**
	 * 将单个字符转换成拼音
	 * 
	 * @param c
	 * @return
	 */
	private static String charToPinyin(char c) {
		// 如果是中文
		if (c <= 128) {
			return String.valueOf(c);
		}

		try {  
			// 转换得出结果  
			String[] strs = PinyinHelper
				.toHanyuPinyinStringArray(c, FORMATTER);

			// 是否查出多音字, 默认是查出多音字的第一个字符
			return strs[0];
		} catch (Exception ex) {
			// 将异常抛出
			throw new RuntimeException(ex);
		}
	}
}
