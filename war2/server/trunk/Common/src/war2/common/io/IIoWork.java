package war2.common.io;

/**
 * 异步操作接口
 * 
 * @author haijiang
 *
 */
interface IIoWork {
	/**
	 * 初始化异步操作
	 * 
	 * @return 是否继续向下执行?
	 * <ul>
	 * <li>true, 继续向下执行 doAsyncProc</li>
	 * <li>false, 中断执行</li>
	 * </ul>
	 * 
	 */
	boolean doInit();

	/**
	 * 执行异步过程, <font color='#990000'>该操作会在异步线程中执行</font>
	 * 
	 * @return 是否继续向下执行?
	 * <ul>
	 * <li>true, 继续向下执行 doFinish</li>
	 * <li>false, 中断执行</li>
	 * </ul>
	 * 
	 */
	boolean doAsyncProc();

	/**
	 * 异步过程完成操作
	 * 
	 * @return 是否执行成功?
	 * 
	 */
	boolean doFinish();
}
