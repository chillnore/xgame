package war2.autocode.kernal;

/**
 * 工作节点接口
 * 
 * @author AfritXia
 * @since 2011/07/17
 * @version $Rev: 46 $
 *
 * @param <Tparams> 工作参数类型
 * 
 */
public interface IWorkNode<Tparams extends IWorkParams> {
	/**
	 * 开始工作
	 * 
	 * @param params 工作参数, 存放当前工作变量或上一步执行结果
	 */
	void work(Tparams params);
}
