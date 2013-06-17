package war2.autocode.impl.excel;

import war2.autocode.kernal.UserProperties;

/**
 * Excel 配置
 * 
 * @author AfritXia
 * @since 2011/07/17
 * @version $Rev: 0 $
 * 
 */
@SuppressWarnings("serial")
public final class ExcelProperties extends UserProperties {
	/** Velocity 模板文件目录(Key) */
	public static final String KEY_velFileDir = "velocity.velFileDir";
	/** Excel 模板 xml 定义文件目录(Key) */
	public static final String KEY_templateXmlDefDir = "excel.templateXmlDefDir";
	/** Excel 模板类输出目录(Key) */
	public static final String KEY_templateClazzOutDir = "excel.templateClazzOutDir";

	/**
	 * 类参数构造器
	 * 
	 * @param props
	 * 
	 */
	public ExcelProperties() {
	}

	/**
	 * 获取 Velocity 模板文件目录
	 * 
	 * @return
	 */
	public String getVelFileDir() {
		return this.getProperty(KEY_velFileDir);
	}

	/**
	 * 获取 Excel 模板 xml 定义文件目录
	 * 
	 * @return
	 * 
	 */
	public String getTemplateXmlDefDir() {
		return this.getProperty(KEY_templateXmlDefDir);
	}

	/**
	 * 获取 Excel 模板类输出目录
	 * 
	 * @return
	 * 
	 */
	public String getTemplateClazzOutDir() {
		return this.getProperty(KEY_templateClazzOutDir);
	}
}
