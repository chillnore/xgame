package war2.autocode.codedom;

import java.util.HashMap;
import java.util.Map;

/**
 * 字段类型
 * 
 * @author AfritXia
 * @since 2011/06/18
 * @version $Rev: 41 $
 * 
 */
public enum FieldTypeEnum {
	/** 无类型 */
	voidType(0, "void"), 
	/** 布尔类型 */
	booleanType(1, "boolean"),
	/** 整数型 */
	intType(2, "int"),
	/** 长整型 */
	longType(4, "long"),
	/** 字符串类型 */
	stringType(8, "string"), 
;
	/** 整型值 */
	private int _intVal;
	/** 字符串值 */
	private String _strVal;
	/** 枚举值字典 */
	private static Map<String, FieldTypeEnum> _strValMap;

	/**
	 * 枚举参数构造器
	 * 
	 * @param intVal
	 * @param strVal
	 */
	private FieldTypeEnum(int intVal, String strVal) {
		this._intVal = intVal;
		this._strVal = strVal;
	}

	/**
	 * 获取整数数值
	 * 
	 * @return
	 */
	public int getIntVal() {
		return this._intVal;
	}

	/**
	 * 获取字符串值
	 * 
	 * @return
	 */
	public String getStrVal() {
		return this._strVal;
	}

	/**
	 * 将字符串值转换为枚举值
	 * 
	 * @param s
	 * @return
	 */
	public static FieldTypeEnum parse(String s) {
		return getStrValMap().get(s);
	}

	/**
	 * 获取字符串值字典
	 * 
	 * @return
	 */
	private static Map<String, FieldTypeEnum> getStrValMap() {
		if (_strValMap == null) {
			_strValMap = new HashMap<String, FieldTypeEnum>();
		}

		for (FieldTypeEnum e : FieldTypeEnum.values()) {
			_strValMap.put(e.getStrVal(), e);
		}

		return _strValMap;
	}
}
