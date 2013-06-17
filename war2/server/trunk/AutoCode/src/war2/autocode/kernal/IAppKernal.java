package war2.autocode.kernal;

/**
 * 应用核心接口
 * 
 * @author AfritXia
 * @since 2011/07/17
 * @version $Rev: 0 $
 * 
 * @param <Tparam> 工作参数类型
 * 
 */
public interface IAppKernal<Tparam extends IWorkParams> {
	/**
	 * 运行
	 * 
	 * @param params
	 * 
	 */
	void runApp(Tparam params);
}
