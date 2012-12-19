package war2.autocode.impl.excel.model;

import war2.autocode.codedom.CustomClazz;

/**
 * Excel 模板类
 * 
 * @author AfritXia
 * @since 2011/06/18
 * @version $Rev: 30 $
 *
 */
public class ExcelBaseTmplClazz extends CustomClazz<ExcelBaseTmplField> {
	/** 工作表单名称 */
	private String _sheetName;

	/**
	 * 获取 Excel 工作表单名称
	 * 
	 * @return
	 */
	public String getSheetName() {
		return this._sheetName;
	}

	/**
	 * 设置 Excel 工作表单名称
	 * 
	 * @param value
	 */
	public void setSheet(String value) {
		this._sheetName = value;
	}
}
