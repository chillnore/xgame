package war2.autocode.codedom.ic;

import war2.autocode.codedom.Field;
import war2.autocode.codedom.FieldTypeEnum;

/**
 * 字段中间码
 * 
 * @author AfritXia
 * @since 2011/06/18
 * @version $Rev: 30 $
 *
 */
public class AbstractFieldCode<TField extends Field> implements IFieldCode {
	/** 字段 */
	private TField _field;

	/**
	 * 获取字段
	 * 
	 * @return
	 */
	public TField getField() {
		return this._field;
	}

	/**
	 * 获取字段名称
	 * 
	 * @return
	 */
	public String getFieldName() {
		if (this._field == null) {
			return null;
		} else {
			return this._field.getName();
		}
	}

	/**
	 * 获取字段类型
	 * 
	 * @return
	 */
	public FieldTypeEnum getFieldType() {
		if (this._field == null) {
			return null;
		} else {
			return this._field.getType();
		}
	}

	/**
	 * 设置字段
	 * 
	 * @param value 
	 * 
	 */
	public void setField(TField value) {
		this._field = value;
	}

	@Override
	public String getFieldTypeStr() {
		if (this._field == null) {
			return null;
		} else {
			return this._field.getType().getStrVal();
		}
	}

	@Override
	public String getPrivateFieldName() {
		if (this._field == null) {
			return null;
		} else {
			return this._field.getName();
		}
	}

	@Override
	public String getGetterName() {
		if (this._field == null) {
			return null;
		} else {
			return this._field.getName();
		}
	}

	@Override
	public String getSetterName() {
		if (this._field == null) {
			return null;
		} else {
			return this._field.getName();
		}
	}

	@Override
	public boolean isNullable() {
		if (this._field == null) {
			return false;
		} else {
			return this._field.isNullable();
		}
	}
}
