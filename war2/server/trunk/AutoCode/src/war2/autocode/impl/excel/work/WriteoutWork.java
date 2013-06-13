package war2.autocode.impl.excel.work;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import war2.autocode.impl.excel.model.ExcelBaseTmplClazz;
import war2.autocode.kernal.Logger;
import war2.autocode.utils.StringUtil;


/**
 * 写出模板类文件
 * 
 * @author AfritXia
 * @since 2011/07/03
 * @version $Rev: 0 $
 * 
 */
class WriteoutWork extends AbstractExcelWork {
	@Override
	protected void workStart(WorkParams params) {
		if (params == null) {
			return;
		}

		// 获取模板类输出目录
		String baseDir = params
			.getProps()
			.getTemplateClazzOutDir();
		
		// 获取模板列列表
		List<ExcelBaseTmplClazz> cl = params.getExcelBaseTmplClazzList();

		for (ExcelBaseTmplClazz c : cl) {
			// 生成 Java 代码文件
			this.createJavaFile(baseDir, c);
			// 记录日志信息
			Logger.log("生成 Java 代码文件: " + c.getName());
		}
	}

	/**
	 * 生成 Java 代码文件
	 * 
	 * @param baseDir
	 * @param clazz
	 */
	private void createJavaFile(String baseDir, ExcelBaseTmplClazz clazz) {
		if (baseDir == null || 
			baseDir.isEmpty()) {
			return;
		}

		if (clazz == null) {
			return;
		}

		// 获取包目录
		String pkgPath = this.getPackagePath(clazz);
		// 获取绝对目录
		String absPath = baseDir + "/" + pkgPath;
		// 获取绝对文件
		String absFile = absPath + "/" + clazz.getName() + "BaseTmpl.java";

		// 写出 Java 代码文件
		this.writeJavaCode(absFile, clazz.getCode());
	}

	/**
	 * 获取包目录
	 * 
	 * @param clazz
	 * @return 
	 * 
	 */
	private String getPackagePath(ExcelBaseTmplClazz clazz) {
		if (clazz == null) {
			return null;
		}

		// 获取包名称
		String packageName = clazz.getPackageName();

		if (packageName == null) {
			return null;
		}

		return StringUtil.splitAndJoin(packageName, "\\.", "/");
	}

	/**
	 * 写出 Java 文件
	 * 
	 * @param absoluteFilename
	 * @param code
	 */
	private void writeJavaCode(String absoluteFilename, String code) {
		if (absoluteFilename == null || 
			absoluteFilename.isEmpty()) {
			return;
		}

		try {
			OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(absoluteFilename), "utf-8");

			ow.write(code);
			ow.flush();
			ow.close();
		} catch (Exception ex) {
			// 抛出异常
			throw new RuntimeException(ex);
		}
	}
}
