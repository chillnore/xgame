package war2.autocode.impl.excel.xmlschema;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementListUnion;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;

import war2.autocode.codedom.constraints.IFieldConstraints;


/**
 * 约束标记
 * 
 * @author AfritXia
 *
 */
@Root(name = "constraints")
public class ConstraintsXml {
	/** 约束 */
	@ElementListUnion({
		// int 数值约束
		@ElementList(entry = "intRange", inline = true, required = false),
		@ElementList(entry = "intEnums", inline = true, required = false),

		// 字符串约束
		@ElementList(entry = "stringEnums", inline = true, required = false), 
		@ElementList(entry = "stringRegex", inline = true, required = false), 
	})
	@Convert(ConstraintsConverter.class)
	private List<IFieldConstraints> _fcList;

	/**
	 * 获取约束列表
	 * 
	 * @return
	 */
	public List<IFieldConstraints> getConstraintsList() {
		return this._fcList;
	}

	/**
	 * 设置约束列表
	 * 
	 * @param value
	 */
	public void setConstraintsList(List<IFieldConstraints> value) {
		this._fcList = value;
	}

	/**
	 * 获取第一个约束
	 * 
	 * @return
	 */
	public IFieldConstraints getFirstConstraints() {
		if (this._fcList == null || 
			this._fcList.size() <= 0) {
			return null;
		} else {
			return this._fcList.get(0);
		}
	}
}
