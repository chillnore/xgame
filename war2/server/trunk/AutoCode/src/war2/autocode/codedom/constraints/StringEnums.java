package war2.autocode.codedom.constraints;

import java.util.Properties;

import war2.autocode.codedom.FieldTypeEnum;


/**
 * 字符串类型约束
 * 
 * @author AfritXia
 * @since 2011/06/18
 * @version $Rev: 35 $
 *
 */
public class StringEnums extends Blank implements IFieldConstraints {
	/** 枚举值 */
	private String[] _enums;

	@Override
	public FieldTypeEnum getFieldType() {
		return FieldTypeEnum.stringType;
	}

	/**
	 * 获取枚举值
	 * 
	 * @return
	 */
	public String[] getEnums() {
		return this._enums;
	}

	/**
	 * 设置枚举值
	 * 
	 * @param value
	 */
	public void setEnums(String[] value) {
		this._enums = value;
	}

	@Override
	public void loadFrom(Properties props) {
		throw new RuntimeException("unimplements");
	}
}
