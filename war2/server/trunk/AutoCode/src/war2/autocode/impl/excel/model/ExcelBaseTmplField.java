package war2.autocode.impl.excel.model;

import war2.autocode.codedom.Field;

/**
 * Excel 模板字段
 * 
 * @author AfritXia
 * @since 2011/06/18
 * @version $Rev: 30 $
 *
 */
public class ExcelBaseTmplField extends Field {
	/** 数据列名称 */
	private String _columnName;
	/** 多语言注解 */
	private MultiLangAnnotation _mla;

	/**
	 * 获取数据列名称
	 * 
	 * @return
	 */
	public String getColumnName() {
		return this._columnName;
	}

	/**
	 * 设置数据列名称
	 * 
	 * @param value
	 */
	public void setColumnName(String value) {
		this._columnName = value;
	}

	/**
	 * 获取多语言注解
	 * 
	 * @return
	 */
	public MultiLangAnnotation getMultiLangAnnotation() {
		return this._mla;
	}

	/**
	 * 设置多语言注解
	 * 
	 * @param value
	 */
	public void setMultiLangAnnotation(MultiLangAnnotation value) {
		this._mla = value;
	}

	/**
	 * 获取是否为多语言字段
	 * 
	 * @return
	 */
	public boolean isMultiLangField() {
		return this._mla != null;
	}
}
