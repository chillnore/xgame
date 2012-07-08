package war2.common.action;

import war2.common.msg.AbstractMsg;

/**
 * 消息活动字典
 * 
 * @author hjj2017
 *
 */
public abstract class AbstractMsgActionMap {
	/**
	 * 获取消息行为
	 * 
	 * @param msgTypeID
	 * @return
	 */
	public abstract IMsgAction<? extends AbstractMsg> getMsgAction(short msgTypeID);

	/**
	 * 设置消息行为
	 * 
	 * @param msgTypeID
	 * @param action
	 */
	public abstract void putMsgAction(short msgTypeID, IMsgAction<? extends AbstractMsg> action);

	/**
	 * 创建一个新的默认消息行为字典
	 * 
	 * @return 
	 * 
	 */
	public static AbstractMsgActionMap newDefault() {
		// 创建并返回默认消息行为字典
		return new DefaultMsgActionMap();
	}
}
