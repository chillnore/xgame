package war2.common.io;

/**
 * 同步的 IO 工作过程
 * 
 * @author haijiang
 *
 */
class SyncIoWorkProcedure<E extends Enum<E>> implements IIoWorkProcedure<IIoWork, E> {
	@Override
	public void startWork(IIoWork work, E threadEnum) {
		if (work == null) {
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
	
		work.doInit();
		this.nextStep(work);
	}

	/**
	 * 调用异步操作对象的 doAsyncProc 函数
	 * 
	 * @param work
	 */
	private void callAsyncProc(StatefulIoWork<E> work) {
		if (work == null) {
			return;
		}

		work.doAsyncProc();
		this.nextStep(work);
	}

	/**
	 * 调用异步操作对象的 doFinish 函数
	 * 
	 * @param work
	 */
	private void callFinish(StatefulIoWork<E> work) {
		if (work == null) {
			return;
		}

		work.doFinish();
	}

	/**
	 * 执行下异步操作
	 * 
	 * @param work
	 */
	private void nextStep(StatefulIoWork<E> work) {
		if (work == null) {
			return;
		}

		// 获取当前工作状态
		IoWorkStateEnum currState = work.getCurrState();
	
		if (currState == null) {
			this.callInit(work);
			return;
		}
	
		switch (work.getCurrState()) {
		case initOk:
			// 执行异步过程
			this.callAsyncProc(work);
			return;
	
		case asyncProcOk:
			// 执行结束过程
			this.callFinish(work);
			return;
	
		default:
			return;
		}
	}
}
