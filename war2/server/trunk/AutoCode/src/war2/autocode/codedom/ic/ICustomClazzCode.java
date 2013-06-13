package war2.autocode.codedom.ic;

import java.util.List;

/**
 * 自定义类中间码
 * 
 * @author AfritXia
 * @since 2011/06/18
 * @version $Rev: 30 $
 *
 */
public interface ICustomClazzCode {
	/**
	 * 获取包名称
	 * 
	 * @return
	 */
	String getPackageName();

	/**
	 * 获取引用列表
	 * 
	 * @return
	 */
	List<String> getImportList();

	/**
	 * 获取类名称
	 * 
	 * @return
	 */
	String getClazzName();
}
