package war2.common.io;

import java.text.MessageFormat;
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
				// 获取 IO 操作
				StatefulIoWork<E> work = this.takeIoWork();

				if (work != null) {
					// 执行异步过程并进入下一步
					work.doAsyncProc();
					this._workProcedure.nextStep(work);
				}
			} catch (Exception ex) {
				// 记录异常信息
				logError(ex);
			}
		}
	}

	/**
	 * 获取 IO 操作
	 * 
	 * @return 
	 * 
	 */
	private StatefulIoWork<E> takeIoWork() {
		try {
			StatefulIoWork<E> work = this._workQueue.take();

			if (work == null) {
				return null;
			}

			// 记录日志信息
			logInfo(MessageFormat.format("AsyncIoWorkRunner[@name={0}]#run >> take : StatefulIoWork", 
				Thread.currentThread().getName()));

			return work;
		} catch (Exception ex) {
			// 抛出异常信息
			throw new XgameIoWorkError(ex);
		}
	}

	/**
	 * 记录日志信息
	 * 
	 * @param msg 
	 * 
	 */
	private static void logInfo(String msg) {
		if (msg == null || 
			msg.isEmpty()) {
			return;
		}

		IoWorkLogger.theInstance().logInfo(msg);
	}

	/**
	 * 记录错误信息
	 * 
	 * @param msg 
	 * 
	 */
	private static void logError(Throwable err) {
		if (err == null) {
			return;
		}

		IoWorkLogger.theInstance().logError(err);
	}
}
