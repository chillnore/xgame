package war2.autocode.codedom.ic;

/**
 * 字段中间码
 * 
 * @author AfritXia
 * @since 2011/06/18
 * @version $Rev: 30 $
 *
 */
public interface IFieldCode {
	/**
	 * 获取字段类型字符串
	 * 
	 * @return
	 */
	String getFieldTypeStr();

	/**
	 * 获取私有字段名称
	 * 
	 * @return
	 */
	String getPrivateFieldName();

	/**
	 * 获取 get 属性名称
	 * 
	 * @return
	 */
	String getGetterName();

	/**
	 * 获取 set 属性名称
	 * 
	 * @return
	 */
	String getSetterName();

	/**
	 * 获取是否可以为空
	 * 
	 * @return
	 */
	boolean isNullable();
}
