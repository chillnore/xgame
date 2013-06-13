package war2.autocode.impl.excel.work;

import java.util.List;

import war2.autocode.codedom.constraints.IFieldConstraints;
import war2.autocode.codedom.ic.AbstractFieldCode;
import war2.autocode.codedom.ic.Java6CodeUtil;
import war2.autocode.impl.excel.model.ExcelBaseTmplField;


/**
 * Java 6 字段中间码
 * 
 * @author AfritXia
 * @since 2011/06/18
 * @version $Rev: 45 $
 *
 */
public class Java6FieldCode extends AbstractFieldCode<ExcelBaseTmplField> {
	/**
	 * 类默认构造器
	 * 
	 */
	public Java6FieldCode() {
	}

	/**
	 * 类参数构造器
	 * 
	 * @param f
	 */
	public Java6FieldCode(ExcelBaseTmplField f) {
		super.setField(f);
	}

	@Override
	public String getFieldTypeStr() {
		return Java6CodeUtil.getFieldTypeStr(this.getFieldType());
	}

	@Override
	public String getPrivateFieldName() {
		return Java6CodeUtil.getFieldName(this.getFieldName());
	}

	@Override
	public String getGetterName() {
		return Java6CodeUtil.getGetterName(this.getFieldName());
	}

	@Override
	public String getSetterName() {
		return Java6CodeUtil.getSetterName(this.getFieldName());
	}

	/**
	 * 获取 Excel 数据列名称
	 * 
	 * @return
	 * 
	 */
	public String getColumnName() {
		return this.getField().getColumnName();
	}

	/**
	 * 是否为多语言列
	 * 
	 * @return
	 */
	public boolean isMultiLangField() {
		return this.getField().getMultiLangAnnotation() != null;
	}

	/**
	 * 获取多语言符号
	 * 
	 * @return
	 */
	public String getMultiLangSymbol() {
		return this.getField()
			.getMultiLangAnnotation()
			.getSymbol();
	}

	/**
	 * 获取多语言分组名称
	 * 
	 * @return
	 */
	public String getMultiLangGroupName() {
		return this.getField()
			.getMultiLangAnnotation()
			.getGroupName();
	}

	/**
	 * 获取字段约束
	 * 
	 * @return 
	 * 
	 */
	public List<IFieldConstraints> getConstraintsList() {
		return this.getField().getConstraintsList();
	}
}
