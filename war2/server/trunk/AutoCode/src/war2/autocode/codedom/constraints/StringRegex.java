package war2.autocode.codedom.constraints;

import java.util.Properties;

import war2.autocode.codedom.FieldTypeEnum;


/**
 * 字符串正则表达式约束
 * 
 * @author AfritXia
 * @since 2011/06/24
 * @version $Rev: 0 $
 *
 */
public class StringRegex extends Blank implements IFieldConstraints {
	/** 正则表达式 */
	private String _regex;
	
	@Override
	public FieldTypeEnum getFieldType() {
		return FieldTypeEnum.stringType;
	}

	/**
	 * 获取正则表达式
	 * 
	 * @return
	 */
	public String getRegex() {
		return this._regex;
	}

	/**
	 * 设置正则表达式
	 * 
	 * @param value
	 */
	public void setRegex(String value) {
		this._regex = value;
	}

	@Override
	public void loadFrom(Properties props) {
		throw new RuntimeException("unimplements");
	}
}
