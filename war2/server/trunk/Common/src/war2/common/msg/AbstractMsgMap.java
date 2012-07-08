package war2.common.msg;

/**
 * 消息字典, 通过消息 ID 获取消息类
 * 
 * @author AfritXia
 * @since 2012/6/3
 *
 */
public abstract class AbstractMsgMap {
	/**
	 * 获取消息对象
	 * 
	 * @param msgTypeID
	 * @return 
	 * 
	 */
	public abstract AbstractExternalMsg getMsg(short msgTypeID);

	/**
	 * 设置消息对象
	 * 
	 * @param msgTypeID 
	 * @param msg 
	 * 
	 */
	public abstract void putMsg(short msgTypeID, AbstractExternalMsg msg);

	/**
	 * 创建一个新的默认消息字典
	 * 
	 * @return 
	 * 
	 */
	public static AbstractMsgMap newDefault() {
		// 创建并返回默认字典
		return new DefaultMsgMap();
	}
}
