package war2.autocode.impl.excel.work;

import java.util.ArrayList;
import java.util.List;

import war2.autocode.codedom.ic.AbstractCustomClazzCode;
import war2.autocode.impl.excel.model.ExcelBaseTmplClazz;
import war2.autocode.impl.excel.model.ExcelBaseTmplField;


/**
 * Java 6 代码风格
 * 
 * @author AfritXia
 * @since 2011/06/18
 * @version $Rev: 45 $
 * 
 */
public class Java6ClazzCode extends AbstractCustomClazzCode<ExcelBaseTmplClazz> {
	/**
	 * 获取工作表单名称
	 * 
	 * @return
	 */
	public String getSheetName() {
		if (this.getCustomClazz() == null) {
			return null;
		} else {
			return this.getCustomClazz().getSheetName();
		}
	}

	/**
	 * 获取字段列表
	 * 
	 * @return 
	 * 
	 */
	public List<Java6FieldCode> getFieldList() {
		// 字段列表
		List<Java6FieldCode> fl = new ArrayList<Java6FieldCode>();
		// 获取自定义类
		ExcelBaseTmplClazz customClazz = this.getCustomClazz();
		
		if (customClazz == null || 
			customClazz.getFieldList() == null) {
			return fl;
		}

		for (ExcelBaseTmplField f : customClazz.getFieldList()) {
			// 添加 Java 6 字段中间码
			fl.add(new Java6FieldCode(f));
		}

		return fl;
	}
}
