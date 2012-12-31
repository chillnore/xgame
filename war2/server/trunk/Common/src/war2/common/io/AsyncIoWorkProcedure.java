package war2.common.io;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import war2.common.XgameNullArgsError;

/**
 * 异步 IO 过程
 * 
 * @author hjj2019
 *
 * @param <E> 
 * 
 */
class AsyncIoWorkProcedure<E extends Enum<E>> implements IIoWorkProcedure<IIoWork, E> {
	/** 过程名称 */
	private static final String PROCEDURE_NAME = "xgame::AsyncIoWorkProcedure";
	/** IO 工作服务 */
	private IoWorkService<E> _workServ = null;
	/** 运行器字典 */
	private Map<E, AsyncIoWorkRunner<E>> _runnerMap = null;

	/**
	 * 类参数构造器
	 * 
	 * @param workServ 
	 * @param threadEnums 
	 * 
	 * @throws XgameNullArgsError if workServ == null 
	 * @throws XgameNullArgsError if threadEnums == null || threadEnums.length <= 0 
	 * 
	 */
	public AsyncIoWorkProcedure(IoWorkService<E> workServ, E[] threadEnums) {
		if (workServ == null) {
			throw new XgameNullArgsError("workServ");
		}

		if (threadEnums == null || 
			threadEnums.length <= 0) {
			throw new XgameNullArgsError("threadEnums");
		}

		this._workServ = workServ;
		// 创建运行器数组
		this._runnerMap = this.createIoWorkRunnerMap(threadEnums);
	}

	/**
	 * 创建 IO 操作执行器字典
	 * 
	 * @param threadEnums
	 * @return 
	 * 
	 */
	private Map<E, AsyncIoWorkRunner<E>> createIoWorkRunnerMap(E[] threadEnums) {
		if (threadEnums == null || 
			threadEnums.length <= 0) {
			throw new XgameNullArgsError("threadEnums");
		}

		// IO 操作执行器字典
		Map<E, AsyncIoWorkRunner<E>> runnerMap = new HashMap<E, AsyncIoWorkRunner<E>>();

		// 创建线程命名工厂
		ThreadNamingFactory nf = new ThreadNamingFactory();
		// 创建线程池
		ExecutorService execServ = Executors.newFixedThreadPool(
			threadEnums.length, nf);
		
		for (E threadEnum : threadEnums) {
			// 运行器名称
			String runnerName = PROCEDURE_NAME + "::" + threadEnum.name();
			// 修改当前名称
			nf.putCurrName(runnerName);
			// 创建运行器
			AsyncIoWorkRunner<E> runner = new AsyncIoWorkRunner<E>(this);

			// 添加运行器到字典中
			runnerMap.put(threadEnum, runner);
			
			// 提交并执行线程
			execServ.submit(runner);
		}

		return runnerMap;
	}

	@Override
	public void startWork(IIoWork work, E threadEnum) {
		if (work == null || 
			threadEnum == null) {
			return;
		}

		// 将异步操作包装成一个有状态的对象, 
		// 然后带入 callInit, callAsyncProc, callFinish 等函数中!
		this.nextStep(new StatefulIoWork<E>(work, threadEnum));
	}

	/**
	 * 调用异步操作对象的 doInit 函数
	 * 
	 * @param work
	 */
	private void callInit(StatefulIoWork<E> work) {
		if (work == null) {
			return;
		}

		// 执行初始化过程并进入下一步
		work.doInit();
		this.nextStep(work);
	}

	/**
	 * 调用异步操作对象的 doAsyncProc 函数
	 * 
	 * @param work
	 */
	private void callAsyncProc(StatefulIoWork<E> work) {
		if (work == null || 
			work.getThreadEnum() == null) {
			return;
		}

		// 获取运行器
		AsyncIoWorkRunner<E> runner = this.getAsyncIoWorkRunner(work.getThreadEnum());

		if (runner == null) {
			return;
		}

		// 将 IO 操作入队到异步线程中
		runner.enqueue(work);
	}

	/**
	 * 获取操作运行器
	 * 
	 * @param threadEnum 
	 * @return
	 * 
	 */
	private AsyncIoWorkRunner<E> getAsyncIoWorkRunner(E threadEnum) {
		if (threadEnum == null) {
			return null;
		} else {
			return this._runnerMap.get(threadEnum);
		}
	}

	/**
	 * 调用异步操作对象的 doFinish 函数
	 * 
	 * @param work
	 */
	private void callFinish(final StatefulIoWork<E> work) {
		if (work == null) {
			return;
		}

		// 
		// 由 IO 操作服务来接管当前操作, 
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
	void nextStep(StatefulIoWork<E> work) {
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
}
