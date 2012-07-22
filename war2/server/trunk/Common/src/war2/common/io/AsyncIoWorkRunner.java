package war2.common.io;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import war2.common.XgameNullArgsError;

/**
 * 异步操作线程
 * 
 * @author hjj2019
 *
 * @param <E> 
 * 
 */
class AsyncIoWorkRunner<E extends Enum<E>> implements Runnable {
	/** 工作队列 */
	private BlockingQueue<StatefulIoWork<E>> _workQueue = null;
	/** 工作过程 */
	private AsyncIoWorkProcedure<E> _workProcedure = null;

	/**
	 * 类参数构造器
	 * 
	 * @param workProc 
	 * @throws XgameNullArgsError if workProc == null 
	 * 
	 */
	public AsyncIoWorkRunner(AsyncIoWorkProcedure<E> workProc) {
		if (workProc == null) {
			throw new XgameNullArgsError("workProc");
		}

		this._workQueue = new LinkedBlockingQueue<StatefulIoWork<E>>();
		this._workProcedure = workProc;
	}

	/**
	 * IO 工作入队
	 * 
	 * @param work
	 */
	public void enqueue(StatefulIoWork<E> work) {
		if (work == null) {
			return;
		} else {
			this._workQueue.add(work);
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				StatefulIoWork<E> work = this._workQueue.take();

				if (work != null) {
					work.doAsyncProc();
					this._workProcedure.nextStep(work);
				}
			} catch (Exception ex) {
				// 记录异常信息
				IoWorkLogger.theInstance().logError(new XgameIoWorkError(Thread.currentThread().getName() + " error", ex));
			}
		}
	}
}
