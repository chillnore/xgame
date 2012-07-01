package war2.common.msg;

/**
 * 消息字典, 通过消息 ID 获取消息类
 * 
 * @author AfritXia
 * @since 2012/6/3
 *
 */
public interface IMsgMap {
	/**
	 * 获取消息对象
	 * 
	 * @param msgTypeID
	 * @return 
	 * 
	 */
	AbstractExternalMsg getMsg(short msgTypeID);

	/**
	 * 设置消息对象
	 * 
	 * @param msgTypeID 
	 * @param msg 
	 * 
	 */
	void putMsg(short msgTypeID, AbstractExternalMsg msg);
}
