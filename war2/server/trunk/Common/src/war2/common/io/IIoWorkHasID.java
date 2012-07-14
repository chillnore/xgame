package war2.common.io;

/**
 * 有 ID 的 IO 操作, 该操作一般用于游戏内的 IO 操作. 
 * 通过 ID 值以保证 IO 操作的顺序性!
 * 
 * @author haijiang
 *
 */
public interface IIoWorkHasID extends IIoWork {
	/**
	 * 获取 ID
	 * 
	 * @return
	 */
	int getID();
}
