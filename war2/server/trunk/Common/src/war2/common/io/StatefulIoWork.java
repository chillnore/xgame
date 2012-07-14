package war2.common.io;

import war2.common.XgameNullArgsError;

/**
 * 有状态的异步操作
 * 
 * @author haijiang
 * 
 */
class StatefulIoWork implements IIoWork {
	/** 异步操作 */
	private IIoWork _innerWork = null;
	/** 当前状态 */
	private IoWorkStateEnum _currState = null;
	/** 原有线程名称 */
	private String _origThreadName = null;

	/**
	 * 类参数构造器
	 * 
	 * @param work
	 * @throws XgameNullArgsError if work == null 
	 * 
	 */
	public StatefulIoWork(IIoWork work) {
		if (work == null) {
			throw new XgameNullArgsError("work");
		}

		this._innerWork = work;
		// 设置当前操作对象所属线程名称
		this._origThreadName = Thread.currentThread().getName();
	}

	/**
	 * 获取内嵌工作
	 * 
	 * @return
	 */
	public IIoWork getInnerWork() {
		return this._innerWork;
	}

	/**
	 * 获取当前状态
	 * 
	 * @return
	 */
	public IoWorkStateEnum getCurrState() {
		return this._currState;
	}

	/**
	 * 设置当前状态
	 * 
	 * @param value
	 */
	private void setCurrState(IoWorkStateEnum value) {
		if (value == null) {
			return;
		}

		this._currState = value;
	}

	/**
	 * 获取当前操作所属线程名称
	 * 
	 * @return
	 */
	public String getOrigThreadName() {
		return this._origThreadName;
	}

	@Override
	public boolean doInit() {
		// 获取执行结果
		boolean result = this._innerWork.doInit();

		// 如果继续向下执行, 
		// 则设置当前状态为: 初始化成功
		this.setCurrState(
			result 
			? IoWorkStateEnum.initOk 
			: IoWorkStateEnum.exit);

		return result;
	}

	@Override
	public boolean doAsyncProc() {
		// 获取执行结果
		boolean result = this._innerWork.doAsyncProc();

		// 如果继续向下执行, 
		// 则设置当前状态为: 异步操作完成
		this.setCurrState(
			result 
			? IoWorkStateEnum.asyncProcOk 
			: IoWorkStateEnum.exit);

		return result;
	}

	@Override
	public boolean doFinish() {
		// 获取执行结果
		boolean result = this._innerWork.doFinish();

		// 设置当前状态为: 已结束
		this.setCurrState(
			result 
			? IoWorkStateEnum.finished 
			: IoWorkStateEnum.exit);

		return result;
	}
}
