package war2.common.io;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

import war2.common.XgameNullArgsError;

/**
 * 异步 IO 工作过程, 有 ID
 * 
 * @author haijiang
 * 
 */
class AsyncIoWorkProcHasID implements IIoWorkProc<IIoWorkHasID> {
	/** 处理器名称 */
	private static final String PROCESSOR_NAME = "xgame::AsyncIoWorkProcHasID";
	/** 最大线程数量 */
	private static final int MAX_THREADS = 8;

	/** IO 工作服务 */
	private IoWorkService _workServ = null;
	/** 运行器数组 */
	private IoWorkRunner[] _runnerArray = null;

	/**
	 * 类参数构造器
	 * 
	 * @param workServ 
	 * @throws XgameNullArgsError if workServ == null 
	 * 
	 */
	public AsyncIoWorkProcHasID(IoWorkService workServ) {
		if (workServ == null) {
			throw new XgameNullArgsError("workServ");
		}

		this._workServ = workServ;
		// 创建运行器数组
		this._runnerArray = new IoWorkRunner[MAX_THREADS];

		// 创建线程命名工厂
		ThreadNamingFactory nf = new ThreadNamingFactory();
		// 创建线程池
		ExecutorService execServ = Executors.newFixedThreadPool(MAX_THREADS, nf);
		
		for (int i = 0; i < MAX_THREADS; i++) {
			// 运行器名称
			String runnerName = PROCESSOR_NAME + "::" + (i > 9 ? i : "0" + i);
			// 修改当前名称
			nf.putCurrName(runnerName);
			// 创建运行器
			IoWorkRunner runner = new IoWorkRunner(this);

			// 添加运行器到数组中
			this._runnerArray[i] = runner;
			
			// 提交并执行线程
			execServ.submit(runner);
		}
	}

	@Override
	public void startWork(IIoWorkHasID work) {
		if (work == null) {
			return;
		}

		// 将异步操作包装成一个有状态的对象, 
		// 然后带入 callInit, callAsyncProc, callFinish 等函数中!
		this.nextStep(new StatefulIoWork(work));
	}

	/**
	 * 调用异步操作对象的 doInit 函数
	 * 
	 * @param work
	 */
	private void callInit(StatefulIoWork work) {
		if (work == null) {
			return;
		}

		work.doInit();
		this.nextStep(work);
	}

	/**
	 * 调用异步操作对象的 doAsyncProc 函数
	 * 
	 * @param work
	 */
	private void callAsyncProc(StatefulIoWork work) {
		if (work == null) {
			return;
		}

		// 获取有 ID 的工作
		IIoWorkHasID workHasID = (IIoWorkHasID)work.getInnerWork();

		if (workHasID == null) {
			return;
		}

		// 获取运行器
		IoWorkRunner runner = this.getWorkRunner(this._runnerArray, workHasID.getID());

		if (runner == null) {
			return;
		}

		runner.enqueue(work);
	}

	/**
	 * 获取操作运行器
	 * 
	 * @param runnerArray
	 * @param workID
	 * @return
	 * 
	 */
	private IoWorkRunner getWorkRunner(IoWorkRunner[] runnerArray, int workID) {
		if (runnerArray == null || 
			runnerArray.length <= 0) {
			return null;
		}

		if (workID < 0) {
			workID = 0;
		}

		int index = workID % runnerArray.length;
		return runnerArray[index];
	}

	/**
	 * 调用异步操作对象的 doFinish 函数
	 * 
	 * @param work
	 */
	private void callFinish(final StatefulIoWork work) {
		if (work == null) {
			return;
		}

		// 
		// 有 IO 操作服务来接管当前操作, 
		// 这一步的确是绕了一个弯...
		// 注意: 这样做的好处是在于只暴露一个 IoWorkService 类
		// 来处理 Msg 相关过程!
		// IO 包中的其他类只与该包中的其他类进行协作
		// 整个包设计比较干净, 
		// 一旦 Msg 包发生变化, 只修改 IoWorkService 类即可
		// 
		this._workServ.onIoWorkFinished(work);
	}

	/**
	 * 执行下一步操作
	 * 
	 * @param work
	 */
	private void nextStep(StatefulIoWork work) {
		if (work == null) {
			return;
		}

		IoWorkStateEnum currState = work.getCurrState();

		if (currState == null) {
			this.callInit(work);
			return;
		}

		switch (work.getCurrState()) {
		case initOk:
			this.callAsyncProc(work);
			return;

		case asyncProcOk:
			this.callFinish(work);
			return;

		default:
			return;
		}
	}

	/**
	 * 线程命名工厂类
	 * 
	 * @author haijiang
	 *
	 */
	private static class ThreadNamingFactory implements ThreadFactory {
		/** 当前名称 */
		private String _currName;

		/**
		 * 设置当前名称
		 * 
		 * @param value
		 */
		public void putCurrName(String value) {
			this._currName = value;
		}

		@Override
		public Thread newThread(Runnable r) {
			if (r == null) {
				return null;
			} else {
				return new Thread(r, this._currName);
			}
		}
	}

	/**
	 * IO 工作运行器
	 * 
	 * @author haijiang
	 *
	 */
	private static class IoWorkRunner implements Runnable {
		/** 工作队列 */
		private BlockingQueue<StatefulIoWork> _workQueue = null;
		/** 工作过程 */
		private AsyncIoWorkProcHasID _workProc = null;

		/**
		 * 类参数构造器
		 * 
		 * @param workProc 
		 * @throws XgameNullArgsError if workProc == null 
		 * 
		 */
		public IoWorkRunner(AsyncIoWorkProcHasID workProc) {
			if (workProc == null) {
				throw new XgameNullArgsError("workProc");
			}

			this._workQueue = new LinkedBlockingQueue<StatefulIoWork>();
			this._workProc = workProc;
		}

		/**
		 * IO 工作入队
		 * 
		 * @param work
		 */
		public void enqueue(StatefulIoWork work) {
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
					StatefulIoWork work = this._workQueue.take();

					if (work != null) {
						work.doAsyncProc();
						this._workProc.nextStep(work);
					}
				} catch (Exception ex) {
					// 记录异常信息
					IoLogger.theInstance().logError(new XgameIoError("AsyncIoWorkProcHasID::IoWorkRunner error", ex));
				}
			}
		}
	}
}
