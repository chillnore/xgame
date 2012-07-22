package war2.common.io;

/**
 * IO 工作过程接口
 * 
 * @author haijiang
 *
 */
interface IIoWorkProcedure<TWork extends IIoWork, E extends Enum<E>> {
	/**
	 * 开始工作
	 * 
	 * @param work
	 * @param e 
	 * 
	 */
	void startWork(TWork work, E e);
}
