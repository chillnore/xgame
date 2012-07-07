package war2.common.action;

import war2.common.msg.AbstractMsg;

/**
 * 消息活动字典
 * 
 * @author hjj2017
 *
 */
public interface IMsgActionMap {
	/**
	 * 获取消息行为
	 * 
	 * @param msgTypeID
	 * @return
	 */
	IMsgAction<? extends AbstractMsg> getMsgAction(short msgTypeID);

	/**
	 * 设置消息行为
	 * 
	 * @param msgTypeID
	 * @param action
	 */
	void putMsgAction(short msgTypeID, IMsgAction<? extends AbstractMsg> action);
}
