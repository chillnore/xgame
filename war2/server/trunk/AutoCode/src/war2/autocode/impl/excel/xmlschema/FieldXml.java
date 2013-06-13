package war2.autocode.impl.excel.xmlschema;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import war2.autocode.codedom.constraints.IFieldConstraints;


/**
 * &lt;field&gt; 标记
 * 
 * @author AfritXia
 *
 */
@Root(name = "field")
public class FieldXml {
	/** 列名称 */
	@Attribute(name = "column")
	private String _column;
	/** 字段类型 */
	@Attribute(name = "type")
	private String _type;
	/** 注释信息 */
	@Attribute(name = "comment", required = false)
	private String _comment;
	/** 是否可以为空 */
	@Attribute(name = "nullable", required = false)
	private boolean _nullable = false;
	/** 多语言相关 */
	@Attribute(name = "multiLang", required = false)
	private String _multiLang;
	/** 字段约束 */
	@Element(name = "constraints", required = false)
	private ConstraintsXml _constraints;

	/**
	 * 获取列名称
	 * 
	 * @return
	 */
	public String getColumn() {
		return this._column;
	}

	/**
	 * 设置列名称
	 * 
	 * @param value
	 */
	public void setColumn(String value) {
		this._column = value;
	}

	/**
	 * 获取类型
	 * 
	 * @return
	 */
	public String getType() {
		return this._type;
	}

	/**
	 * 设置类型
	 * 
	 * @param value
	 */
	public void setType(String value) {
		this._type = value;
	}

	/**
	 * 获取注释信息
	 * 
	 * @return
	 */
	public String getComment() {
		return this._comment;
	}

	/**
	 * 设置注释信息
	 * 
	 * @param value
	 */
	public void setComment(String value) {
		this._comment = value;
	}

	/**
	 * 获取是否可以为空
	 * 
	 * @return
	 */
	public boolean isNullable() {
		return this._nullable;
	}

	/**
	 * 设置是否可以为空
	 * 
	 * @param value
	 */
	public void setNullable(boolean value) {
		this._nullable = value;
	}

	/**
	 * 是否为多语言字段
	 * 
	 * @return
	 */
	public Boolean isMultiLangField() {
		return this._multiLang != null;
	}

	/**
	 * 设置多语言相关
	 * 
	 * @return
	 */
	public String getMultiLang() {
		return this._multiLang;
	}

	/**
	 * 设置多语言相关
	 * 
	 * @param value
	 */
	public void setMultiLang(String value) {
		this._multiLang = value;
	}

	/**
	 * 获取约束配置
	 * 
	 * @return
	 */
	public ConstraintsXml getConstraints() {
		return this._constraints;
	}

	/**
	 * 设置约束配置
	 * 
	 * @param value
	 */
	public void setConstraints(ConstraintsXml value) {
		this._constraints = value;
	}

	/**
	 * 是否有约束
	 * 
	 * @return
	 */
	public boolean hasConstraints() {
		if (this._constraints == null) {
			return false;
		} 

		// 获取约束列表
		List<?> constrList = this._constraints.getConstraintsList();

		if (constrList == null || 
			constrList.size() <= 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 获取约束列表
	 * 
	 * @return
	 */
	public List<IFieldConstraints> getConstraintsList() {
		if (this._constraints == null) {
			return null;
		} else {
			return this._constraints.getConstraintsList();
		}
	}

	/**
	 * 获取第一个约束
	 * 
	 * @return
	 */
	public IFieldConstraints getFirstConstraints() {
		if (this._constraints == null) {
			return null;
		} else {
			return this._constraints.getFirstConstraints();
		}
	}
}
