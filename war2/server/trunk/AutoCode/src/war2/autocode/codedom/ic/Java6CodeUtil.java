package war2.autocode.codedom.ic;

import war2.autocode.codedom.FieldTypeEnum;
import war2.autocode.utils.StringUtil;

/**
 * Java 6 中间码实用工具类
 * 
 * @author AfritXia
 * @since 2011/06/18
 * @version $Rev: 30 $
 *
 */
public final class Java6CodeUtil {
	/**
	 * 类默认构造器
	 * 
	 */
	private Java6CodeUtil() {
	}

	/**
	 * 获取字段名称
	 * 
	 * @param fn
	 * @return
	 * 
	 */
	public static String getFieldName(String fn) {
		if (StringUtil.isNullOrEmpty(fn)) {
			throw new IllegalArgumentException("fn is null");
		}

		return '_' + StringUtil.firstCharToLowerCase(fn);
	}

	/**
	 * 获取 getter 名称
	 * 
	 * @param fn 
	 * @return
	 * 
	 */
	public static String getGetterName(String fn) {
		if (StringUtil.isNullOrEmpty(fn)) {
			throw new IllegalArgumentException("fn is null");
		}

		return "get" + StringUtil.firstCharToUpperCase(fn);
	}

	/**
	 * 获取 setter 名称
	 * 
	 * @param fn
	 * @return 
	 * 
	 */
	public static String getSetterName(String fn)  {
		if (StringUtil.isNullOrEmpty(fn))  {
			throw new IllegalArgumentException("fn is null");
		}

		return "set" + StringUtil.firstCharToUpperCase(fn);
	}

	/**
	 * 获取类型字符串
	 * 
	 * @param ft
	 * @return 
	 * 
	 */
	public static String getFieldTypeStr(FieldTypeEnum ft) {
		if (ft == FieldTypeEnum.booleanType) {
			return "boolean";
		} else if (ft == FieldTypeEnum.intType) {
			return "int";
		} else if (ft == FieldTypeEnum.longType) {
			return "long";
		} else if (ft == FieldTypeEnum.stringType) {
			return "String";
		} else {
			throw new RuntimeException("fieldType error!");
		}
	}
}
