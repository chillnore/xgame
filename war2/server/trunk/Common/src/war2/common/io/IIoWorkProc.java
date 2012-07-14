package war2.common.io;

/**
 * IO 工作过程接口
 * 
 * @author haijiang
 *
 */
interface IIoWorkProc<TWork extends IIoWork> {
	/**
	 * 开始工作
	 * 
	 * @param work
	 */
	void startWork(TWork work);
}
