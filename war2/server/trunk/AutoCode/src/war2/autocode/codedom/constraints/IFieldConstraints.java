package war2.autocode.codedom.constraints;

import java.util.Properties;

import war2.autocode.codedom.FieldTypeEnum;


/**
 * 字段约束
 * 
 * @author AfritXia
 * @since 2011/06/18
 * @version $Rev: 30 $
 *
 */
public interface IFieldConstraints {
	/**
	 * 获取 ID
	 * 
	 * @return
	 */
	int getID();

	/**
	 * 获取类型
	 * 
	 * @return
	 */
	FieldTypeEnum getFieldType();

	/**
	 * 从 props 中加载
	 * 
	 * @param props
	 */
	void loadFrom(Properties props);
}
