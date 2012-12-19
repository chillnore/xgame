package war2.autocode.impl.excel.work;

import java.util.List;

import war2.autocode.impl.excel.ExcelProperties;
import war2.autocode.impl.excel.model.ExcelBaseTmplClazz;
import war2.autocode.kernal.AbstractWorkParams;


/**
 * 工作参数
 * 
 * @author AfritXia
 * @version $Rev: 46 $
 *
 */
public class WorkParams extends AbstractWorkParams {
	/** Xml 文件名称 */
	private String _xmlFileName;
	/** 模板类列表 */
	private List<ExcelBaseTmplClazz> _excelBaseTmplClazzList;
	/** excel 属性 */
	private ExcelProperties _props;

	/**
	 * 类默认构造器
	 * 
	 */
	public WorkParams() {
	}

	/**
	 * 获取 Xml 文件名称
	 * 
	 * @return
	 */
	public String getXmlFileName() {
		return this._xmlFileName;
	}

	/**
	 * 设置 Xml 文件名称
	 * 
	 * @param value
	 */
	public void setXmlFileName(String value) {
		this._xmlFileName = value;
	}

	/**
	 * 获取 Excel 模板类列表
	 * 
	 * @return
	 */
	public List<ExcelBaseTmplClazz> getExcelBaseTmplClazzList() {
		return this._excelBaseTmplClazzList;
	}

	/**
	 * 设置 Excel 模板类列表
	 * 
	 * @param value
	 */
	public void setExcelBaseTmplClazzList(List<ExcelBaseTmplClazz> value) {
		this._excelBaseTmplClazzList = value;
	}

	/**
	 * 获取 excel 属性
	 * 
	 * @return
	 */
	public ExcelProperties getProps() {
		return this._props;
	}

	/**
	 * 设置 excel 属性
	 * 
	 * @param value
	 */
	public void setProps(ExcelProperties value) {
		this._props = value;
	}
}
