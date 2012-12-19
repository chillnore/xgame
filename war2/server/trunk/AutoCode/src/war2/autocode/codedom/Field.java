package war2.autocode.codedom;

import java.util.ArrayList;
import java.util.List;

import war2.autocode.codedom.constraints.IFieldConstraints;


/**
 * 类字段
 * 
 * @author AfritXia
 * @since 2011/06/18
 * @version $Rev: 30 $
 *
 */
public class Field {
	/** 自动类型 */
	private FieldTypeEnum _type;
	/** 名称 */
	private String _name;
	/** 是否可以为空 */
	private boolean _nullable;
	/** 字段约束列表 */
	private List<IFieldConstraints> _constraintsList;

	/**
	 * 获取类型
	 * 
	 * @return
	 */
	public FieldTypeEnum getType() {
		return this._type;
	}

	/**
	 * 设置类型
	 * 
	 * @param value 
	 * 
	 */
	public void setType(FieldTypeEnum value) {
		this._type = value;
	}

	/**
	 * 获取名称
	 * 
	 * @return
	 */
	public String getName() {
		return this._name;
	}

	/**
	 * 设置名称
	 * 
	 * @param value
	 */
	public void setName(String value) {
		this._name = value;
	}

	/**
	 * 获取是否可以为空值
	 * 
	 * @return
	 */
	public boolean isNullable() {
		return this._nullable;
	}

	/**
	 * 设置是否可以为空值
	 * 
	 * @param value
	 */
	public void setNullable(boolean value) {
		this._nullable = value;
	}

	/**
	 * 获取约束列表
	 * 
	 * @return
	 */
	public List<IFieldConstraints> getConstraintsList() {
		return this._constraintsList;
	}

	/**
	 * 设置约束
	 * 
	 * @param value
	 */
	public void setConstraintsList(List<IFieldConstraints> value) {
		this._constraintsList = value;
	}

	/**
	 * 添加约束
	 * 
	 * @param value
	 */
	public void addConstraints(IFieldConstraints value) {
		if (value == null) {
			return;
		}

		if (this._constraintsList == null) {
			this._constraintsList = new ArrayList<IFieldConstraints>();
		}

		this._constraintsList.add(value);
	}

	/**
	 * 清除约束
	 * 
	 */
	public void clearConstraints() {
		if (this._constraintsList != null) {
			this._constraintsList.clear();
		}
	}
}
