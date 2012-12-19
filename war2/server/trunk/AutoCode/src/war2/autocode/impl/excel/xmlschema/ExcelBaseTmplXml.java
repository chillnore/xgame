package war2.autocode.impl.excel.xmlschema;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * &lt;excelBaseTmpl&gt; 标记
 * 
 * @author AfritXia
 * @since 2011/12/17
 *
 */
@Root(name = "excelBaseTmpl")
public class ExcelBaseTmplXml {
	/** xsi:schemaLocation */
	@Attribute(name = "schemaLocation")
	private String _schemaLocation;
	/** 包名 */
	@Attribute(name = "packageName")
	private String _packageName;
	/** 文件名称 */
	@Attribute(name = "excelFileName")
	private String _excelFileName;
	/** 基础模板列表 */
	@ElementList(entry = "baseTmpl", inline = true)
	private List<BaseTmplXml> _baseTmplList;

	/**
	 * 获取 schemaLocation
	 * 
	 * @return
	 */
	public String getSchemaLocation() {
		return _schemaLocation;
	}

	/**
	 * 设置 schemaLocation 
	 * 
	 * @param value
	 */
	public void setSchemaLocation(String value) {
		this._schemaLocation = value;
	}

	/**
	 * 获取包名称
	 * 
	 * @return
	 */
	public String getPackageName() {
		return this._packageName;
	}

	/**
	 * 设置包名称
	 * 
	 * @param value
	 */
	public void setPackageName(String value) {
		this._packageName = value;
	}

	/**
	 * 获取 Excel 文件名称
	 * 
	 * @return
	 */
	public String getExcelFileName() {
		return this._excelFileName;
	}

	/**
	 * 设置 Excel 文件名称
	 * 
	 * @param value
	 */
	public void setExcelFilename(String value) {
		this._excelFileName = value;
	}

	/**
	 * 获取基础模板列表
	 * 
	 * @return
	 */
	public List<BaseTmplXml> getBaseTemplList() {
		return this._baseTmplList;
	}

	/**
	 * 设置基础模板列表
	 * 
	 * @param value
	 */
	public void setBaseTmplList(List<BaseTmplXml> value) {
		this._baseTmplList = value;
	}
}
