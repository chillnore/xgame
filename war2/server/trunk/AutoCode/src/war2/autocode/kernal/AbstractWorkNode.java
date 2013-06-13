package war2.autocode.kernal;

/**
 * 抽象工作节点
 * 
 * @author AfritXia
 * @version $Rev: 45 $
 *
 */
public abstract class AbstractWorkNode<Tparams extends IWorkParams> implements IWorkNode<Tparams> {
	/** 下一个工作 */
	private IWorkNode<Tparams> _nextWork;

	/**
	 * 类默认构造器
	 * 
	 */
	public AbstractWorkNode() {
	}

	/**
	 * 类参数构造器
	 * 
	 * @param nextWork
	 */
	public AbstractWorkNode(IWorkNode<Tparams> nextWork) {
		this._nextWork = nextWork;
	}

	/**
	 * 设置下一工作
	 * 
	 * @param nextWork
	 */
	public void putNextWork(IWorkNode<Tparams> nextWork) {
		this._nextWork = nextWork;
	}

	@Override
	public void work(Tparams params) {
		// 开始工作
		this.workStart(params);

		if (this._nextWork != null) {
			this._nextWork.work(params);
		}
	}

	/**
	 * 开始工作
	 * 
	 * @param params
	 */
	protected abstract void workStart(Tparams params);
}
