package war2.autocode.impl.excel;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import war2.autocode.impl.excel.work.ExcelWorkFactory;
import war2.autocode.impl.excel.work.WorkParams;
import war2.autocode.kernal.IAppKernal;
import war2.autocode.kernal.IWorkNode;
import war2.autocode.kernal.Logger;
import war2.autocode.kernal.annotations.Manager;
import war2.autocode.utils.FileTraveller;


/**
 * EXCEL 模板生成工具
 * 
 * @author AfritXia
 * @since 2011/07/16
 * @version $Rev: 46 $
 *
 */
@Manager
public class ExcelTemplateGenKernal implements IAppKernal<WorkParams> {
	@Override
	public void runApp(WorkParams params) {
		if (params == null) {
			return;
		}

		String tmplXmlDir = params
			.getProps()
			.getTemplateXmlDefDir();

		// 获取文件列表
		List<String> fl = this.getFileList(tmplXmlDir);

		if (fl == null || 
			fl.isEmpty()) {
			Logger.log("文件列表为空");
			return;
		}

		// 创建工作链
		IWorkNode<WorkParams> workChain = ExcelWorkFactory.createWorkChain();

		for (String fname : fl) {
			// 设置 Xml 文件名称并开始工作
			params.setXmlFileName(fname);
			workChain.work(params);
		}
	}

	/**
	 * 获取文件列表
	 * 
	 * @param dir 
	 * @return
	 */
	private List<String> getFileList(String dir) {
		if (dir == null || 
			dir.isEmpty()) {
			return Collections.emptyList();
		}

		// 打印日志信息
		Logger.log("遍历 " + dir + " 收集 .basetmpl.xml 文件");
		// 获取文件列表
		List<String> fl = (new FileTraveller()).travel(
			dir, new MyFileFilter());

		// 转换成数组并排序
		Object[] objArray = fl.toArray();
		Arrays.sort(objArray);

		List<String> resultList = new ArrayList<String>();

		for (Object obj : objArray) {
			resultList.add(obj.toString());
		}

		return resultList;
	}

	/**
	 * 自定义文件过滤器
	 * 
	 * @author AfritXia
	 * @version $Rev: 46 $
	 *
	 */
	private static class MyFileFilter implements FileFilter {
		@Override
		public boolean accept(File pathname) {
			String pathnameStr = pathname.getName();

			if (pathname.isDirectory() && 
				pathnameStr.endsWith(".svn")) {
				// 忽略 .svn 目录
				return false;
			}

			if (pathname.isFile() &&
			   !pathnameStr.endsWith(".basetmpl.xml")) {
				// 忽略非 Xml 文档
				return false;
			}

			return true;
		}
	}
}
