package war2.common.io;

import war2.common.XgameNullArgsError;

/**
 * 有状态的异步操作
 * 
 * @author haijiang
 * 
 */
class StatefulIoWork<E extends Enum<E>> implements IIoWork {
	/** 异步操作 */
	private IIoWork _innerWork = null;
	/** 线程枚举 */
	private E _threadEnum = null;
	/** 当前状态 */
	private IoWorkStateEnum _currState = null;

	/**
	 * 类参数构造器
	 * 
	 * @param work
	 * @param threadEnum 
	 * 
	 * @throws XgameNullArgsError if work == null 
	 * 
	 */
	public StatefulIoWork(IIoWork work, E threadEnum) {
		if (work == null) {
			throw new XgameNullArgsError("work");
		}

		this._innerWork = work;
		this._threadEnum = threadEnum;
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
	 * 获取线程枚举
	 * 
	 * @return 
	 * 
	 */
	public E getThreadEnum() {
		return this._threadEnum;
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
