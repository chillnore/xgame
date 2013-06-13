package war2.autocode.codedom.constraints;

import java.util.Properties;

import war2.autocode.codedom.FieldTypeEnum;


/**
 * 整数枚举约束
 * 
 * @author AfritXia
 * @since 2011/06/24
 * @version $Rev: 0 $
 * 
 */
public class IntEnums extends Blank implements IFieldConstraints {
	/** 枚举值 */
	private static final String ATTR_VALUES = "values";

	/** 枚举值 */
	private int[] _values;

	@Override
	public FieldTypeEnum getFieldType() {
		return FieldTypeEnum.intType;
	}

	/**
	 * 获取枚举值
	 * 
	 * @return
	 */
	public int[] getValues() {
		return this._values;
	}

	/**
	 * 设置枚举值
	 * 
	 * @param value
	 */
	public void setValues(int[] value) {
		this._values = value;
	}

	@Override
	public void loadFrom(Properties props) {
		if (props == null || 
		   !props.containsKey(ATTR_VALUES)) {
			return;
		}

		// 获取枚举值字符串
		String strVals = props.getProperty(ATTR_VALUES);
		// 枚举值数组
		String[] strValArray = strVals.split(",");

		int len = strValArray.length;
		int[] intVals = new int[len];

		for (int i = 0; i < len; i++) {
			intVals[i] = Integer.parseInt(strValArray[i].trim());
		}

		this.setValues(intVals);
	}
}
