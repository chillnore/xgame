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
	 * 获取消息类
	 * 
	 * @param msgTypeID
	 * @return
	 */
	Class<? extends AbstractExternalMsg> getMsgClazz(short msgTypeID);

	/**
	 * 设置消息类
	 * 
	 * @param msgTypeID
	 * @param clazz
	 */
	void putMsgClazz(short msgTypeID, Class<? extends AbstractExternalMsg> clazz);
}
