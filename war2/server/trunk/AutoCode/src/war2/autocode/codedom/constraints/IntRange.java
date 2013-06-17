package war2.autocode.codedom.constraints;

import java.util.Properties;

import war2.autocode.codedom.FieldTypeEnum;


/**
 * 整数范围约束
 * 
 * @author AfritXia
 * @since 2011/06/18
 * @version $Rev: 39 $
 *
 */
public class IntRange extends Blank implements IFieldConstraints {
	/** 最小值 */
	private static final String ATTR_MIN_VALUE = "minValue";
	/** 最大值 */
	private static final String ATTR_MAX_VALUE = "maxValue";
	
	/** 最小值 */
	private Integer _minValue;
	/** 最大值 */
	private Integer _maxValue;

	@Override
	public FieldTypeEnum getFieldType() {
		return FieldTypeEnum.intType;
	}

	/**
	 * 获取最小值
	 * 
	 * @return
	 */
	public Integer getMinValue() {
		return this._minValue;
	}

	/**
	 * 设置最小值
	 * 
	 * @param value
	 */
	public void setMinValue(Integer value) {
		this._minValue = value;
	}

	/**
	 * 获取最大值
	 * 
	 * @return
	 */
	public Integer getMaxValue() {
		return this._maxValue;
	}

	/**
	 * 设置最大值
	 * 
	 * @param value
	 */
	public void setMaxValue(Integer value) {
		this._maxValue = value;
	}

	@Override
	public void loadFrom(Properties props) {
		if (props == null) {
			return;
		}

		if (props.containsKey(ATTR_MIN_VALUE)) {
			this._minValue = Integer
				.parseInt(props.getProperty(ATTR_MIN_VALUE));
		}

		if (props.containsKey(ATTR_MAX_VALUE)) {
			this._maxValue = Integer
				.parseInt(props.getProperty(ATTR_MAX_VALUE));
		}
	}
}
