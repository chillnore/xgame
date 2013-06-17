package war2.autocode.impl.excel.work;

import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import war2.autocode.impl.excel.model.ExcelBaseTmplClazz;
import war2.autocode.kernal.Logger;


/**
 * 生成 Java 类
 * 
 * @author AfritXia
 * @since 2011/06/20
 * @version $Rev: 45 $
 *
 */
class GenJavaClassWork extends AbstractExcelWork {
	/** 中间码 */
	private static final Java6ClazzCode CODE = new Java6ClazzCode();

	@Override
	protected void workStart(WorkParams params) {
		if (params == null) {
			return;
		}

		// 获取 vel 文件目录
		String velFileDir = params
			.getProps()
			.getVelFileDir();

		try {
			Properties props = new Properties();

			props.setProperty("resource.loader", "file");
			props.setProperty("file.resource.loader.path", velFileDir);
			
			// 初始化 Velocity
			Velocity.init(props);
		} catch (Exception ex) {
			// 将异常抛出
			throw new RuntimeException(ex);
		}

		for (ExcelBaseTmplClazz ebtc : params.getExcelBaseTmplClazzList()) {
			// 写出 Java 类文件
			this.writeJavaClass(ebtc);
			// 生成 Java 类
			Logger.log("生成 Java 类: " + ebtc.getName());
		}
	}

	/**
	 * 写出 Java 类文件
	 * 
	 * @param ebtc
	 */
	private void writeJavaClass(ExcelBaseTmplClazz ebtc) {
		if (ebtc == null) {
			return;
		}

		// 设置要输出的类
		CODE.setCustomClazz(ebtc);

		// 创建并设置上下文
		VelocityContext vctx = new VelocityContext();
		vctx.put("ebtc", CODE);

		// 创建并渲染到输出流
		StringWriter sw = new StringWriter();
		Velocity.mergeTemplate("excelBaseTmpl.java.vm", "utf-8", vctx, sw);

		// 设置代码
		ebtc.setCode(sw.toString());
	}
}
