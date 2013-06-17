package war2.autocode.impl.excel.work;

import war2.autocode.kernal.annotations.Secretary;

/**
 * excel 导出工作工厂类
 * 
 * @author AfritXia
 * @since 2011/07/16
 * @version $Rev: 0 $
 * 
 */
@Secretary
public final class ExcelWorkFactory {
	/**
	 * 类默认构造器
	 * 
	 */
	private ExcelWorkFactory() {
	}

	/**
	 * 获取工作节点
	 * 
	 * @return 
	 * 
	 */
	public static AbstractExcelWork createWorkChain() {
		// 创建工作列表
		AbstractExcelWork[] wl = new AbstractExcelWork[] {
			new ParseXmlWork(), 
			new PinyinRenamingWork(), 
			new GenJavaClassWork(), 
			new FormatJavaWork(), 
			new WriteoutWork(), 
		};

		for (int i = 1; i < wl.length; i++) {
			AbstractExcelWork currWork = wl[i - 1];
			AbstractExcelWork nextWork = wl[i];

			// 设置下一步工作
			currWork.putNextWork(nextWork);
		}

		if (wl.length > 0) {
			// 将最后一步的下一步工作清空
			wl[wl.length - 1].putNextWork(null);
		}

		return wl[0];
	}
}
