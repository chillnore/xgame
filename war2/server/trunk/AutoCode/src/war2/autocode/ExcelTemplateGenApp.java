package war2.autocode;

import java.io.FileInputStream;

import war2.autocode.impl.excel.ExcelProperties;
import war2.autocode.impl.excel.ExcelTemplateGenKernal;
import war2.autocode.impl.excel.work.WorkParams;


/**
 * Excel 模板生成程序
 * 
 * @author AfritXia
 * @since 2011/07/16
 * @version $Rev: 0 $
 * 
 */
public final class ExcelTemplateGenApp {
	/** 默认配置文件 */
	private static final String ALL_PROPERTIES = "${appDir}/conf/all.properties";

	/**
	 * 应用程序主函数
	 * 
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		// 声明应用程序
		ExcelTemplateGenApp theApp;
		// 创建应用程序并执行
		theApp = new ExcelTemplateGenApp();
		theApp.runApp();
	}

	/**
	 * 执行应用程序
	 * 
	 */
	public void runApp() {
		ExcelProperties props = this.newProperties();
		
		ExcelTemplateGenKernal kernal;
		WorkParams params;

		// 设置工作参数
		params = new WorkParams();
		params.setProps(props);

		System.out.println(this.getClass().getSimpleName() + " start");
		System.out.println("--------");
		
		// 运行应用程序
		kernal = new ExcelTemplateGenKernal();
		kernal.runApp(params);

		System.out.println("--------");
		System.out.println("all completed !!");
	}

	/**
	 * 创建配置属性
	 * 
	 * @return
	 */
	private ExcelProperties newProperties() {
		// 创建 excel 属性
		ExcelProperties props = new ExcelProperties();

		String workSpaceDir = this.getWorkSpaceDir();
		String appDir = workSpaceDir + "/autoCode";

		// 设置工作空间目录
		props.put("workSpaceDir", workSpaceDir);

		try {
			// 加载默认配置文件
			props.load(new FileInputStream(ALL_PROPERTIES.replace("${appDir}", appDir)));
		} catch (Exception ex) {
			// 打印异常并退出
			ex.printStackTrace();
			System.exit(-1);
		}

		return props;
	}

	/**
	 * 获取工作空间目录
	 * 
	 * @return
	 */
	private String getWorkSpaceDir() {
		// 获取当前应用目录
		String userDir = System.getProperty("user.dir");

		if (userDir.endsWith("autoCode")) {
			userDir = userDir.substring(0, userDir.length() - 9);
		}

		return userDir;
	}
}
