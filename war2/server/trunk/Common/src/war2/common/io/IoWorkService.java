package war2.common.io;

import war2.common.XgameNullArgsError;
import war2.common.msg.AbstractInternalMsg;
import war2.common.msg.AbstractMsg;
import war2.common.msg.IMsgProcessor;

/**
 * IO 操作处理器
 * 
 * @author haijiang
 * 
 */
public class IoWorkService<E extends Enum<E>> {
	/** 消息处理器 */
	private IMsgProcessor _msgProcessor = null;
	/** 当前工作模式 */
	private IoWorkModeEnum _currWorkMode = null;
	/** IO 操作过程 */
	private IIoWorkProcedure<IIoWork, E> _workProc = null;

	/**
	 * 类默认构造器, 默认工作模式为同步
	 * 
	 * @param threadEnums 
	 * 
	 */
	public IoWorkService(E[] threadEnums) {
		this(null, threadEnums);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param msgProc 消息处理器, 如果消息处理器为空, 那么 IO 过程将运行在同步模式下
	 * @param threadEnums 
	 * 
	 * @see IMsgProcessor
	 * @see IoWorkModeEnum  
	 * 
	 */
	public IoWorkService(IMsgProcessor msgProc, E[] threadEnums) {
		if (msgProc == null) {
			// 同步工作方式
			this._currWorkMode = IoWorkModeEnum.sync;
			this._workProc = new SyncIoWorkProcedure<E>();
		} else {
			// 异步工作方式
			this._currWorkMode = IoWorkModeEnum.async;
			this._msgProcessor = msgProc;
			this._workProc = new AsyncIoWorkProcedure<E>(this, threadEnums);
		}
	}

	/**
	 * 获取当前工作模式, 
	 * 当消息处理器为空时, 当前工作模式为同步模式(sync).
	 * 当消息处理器非空时, 当前工作模式为异步方式(async)
	 * 
	 * @return
	 * 
	 * @see IoWorkModeEnum 
	 * @see IoWorkService#IoWorkService()
	 * @see IoWorkService#IoWorkService(IMsgProcessor)
	 * 
	 */
	public IoWorkModeEnum getCurrWorkMode() {
		return this._currWorkMode;
	}

	/**
	 * 执行游戏内的 IO 操作
	 * 
	 * @param work
	 * @param threadEnum 
	 * 
	 */
	public void startIoWork(IIoWork work, E threadEnum) {
		if (work == null) {
			return;
		}

		if (threadEnum == null) {
			return;
		}

		this._workProc.startWork(work, threadEnum);
	}

	/**
	 * 当 IO 操作完成后调用该函数. 
	 * <font color='#990000'>注意: 这样做的好处是在于只暴露一个 IoWorkService 类来处理 Msg 相关过程!</font>
	 * IO 包中的其他类只与该包中的其他类进行协作, 
	 * 整个包设计比较干净, 
	 * 一旦 Msg 包发生变化, 只修改 IoWorkService 类即可
	 * 
	 * @param work
	 */
	void onIoWorkFinished(StatefulIoWork<E> work) {
		if (work == null) {
			return;
		}

		// 创建 IO 完成消息, 并交给消息处理器处理
		AbstractMsg msg = new IoWorkDoFinishedMsg(work);
		this._msgProcessor.enqueue(msg);
	}

	/**
	 * IO 操作完成消息
	 * 
	 * @author haijiang
	 *
	 */
	private static class IoWorkDoFinishedMsg extends AbstractInternalMsg {
		/** IO 操作 */
		private IIoWork _work;

		/**
		 * 类参数构造器
		 * 
		 * @param work 
		 * @throws IllegalArgumentException if work is null
		 * 
		 */
		public IoWorkDoFinishedMsg(
			IIoWork work) {

			if (work == null) {
				throw new XgameNullArgsError("work");
			}

			this._work = work;
		}

		@Override
		public void execute() {
			this._work.doFinish();
		}
	}
}
