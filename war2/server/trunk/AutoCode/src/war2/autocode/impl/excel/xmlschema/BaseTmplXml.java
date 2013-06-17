package war2.autocode.impl.excel.xmlschema;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * &lt;baseTmpl&gt; 标记
 * 
 * @author AfritXia
 *
 */
@Root(name = "baseTmpl")
public class BaseTmplXml {
	/** 工作表单 */
	@Attribute(name = "sheet")
	private String _sheet;
	/** 字段列表 */
	@ElementList(entry = "field", inline = true)
	private List<FieldXml> _fieldList;

	/**
	 * 获取工作表单
	 * 
	 * @return
	 */
	public String getSheet() {
		return _sheet;
	}

	/**
	 * 设置工作表单
	 * 
	 * @param value
	 */
	public void setSheet(String value) {
		this._sheet = value;
	}

	/**
	 * 获取字段列表
	 * 
	 * @return
	 */
	public List<FieldXml> getFieldList() {
		return this._fieldList;
	}

	/**
	 * 设置字段列表
	 * 
	 * @param value
	 */
	public void setFieldList(List<FieldXml> value) {
		this._fieldList = value;
	}
}
