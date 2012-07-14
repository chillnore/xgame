package war2.common.io;

/**
 * 异步 IO 操作门面类
 * 
 * @author haijiang
 *
 */
class AsyncIoWorkProcFacade implements IIoWorkProc<IIoWork> { 
	/** 有 ID 的工作过程 */
	private AsyncIoWorkProcHasID _workProcHasID;
	/** 无 ID 的工作过程 */
	private AsyncIoWorkProcNotID _workProcNotID;

	/**
	 * 类默认构造器
	 * 
	 * @param workServ 
	 * 
	 */
	public AsyncIoWorkProcFacade(IoWorkService workServ) {
		this._workProcHasID = new AsyncIoWorkProcHasID(workServ);
		this._workProcNotID = new AsyncIoWorkProcNotID(workServ);
	}

	@Override
	public void startWork(IIoWork work) {
		if (work == null) {
			return;
		}

		if (work instanceof IIoWorkHasID) {
			// 以有 ID 的方式处理 IO 操作
			this._workProcHasID.startWork((IIoWorkHasID)work);
		} else if (work instanceof IIoWorkNotID) {
			// 以无 ID 的方式处理 IO 操作
			this._workProcNotID.startWork((IIoWorkNotID)work);
		} else {
			// 无法处理的情况
			IoLogger.theInstance().logError(
				new XgameIoError("unknown workServ type"));
		}
	}
}
