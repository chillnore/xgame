package war2.common.msg;

import java.util.HashMap;
import java.util.Map;

import war2.common.XgameInvalidArgsError;
import war2.common.XgameNullArgsError;

/**
 * 消息行为字典
 * 
 * @author hjj2019
 *
 */
public class MsgActionMap implements IMsgActionMap {
	/** 消息类型字典 */
	private Map<Short, IMsgAction<?>> _innerMap;

	/**
	 * 类默认构造器
	 * 
	 */
	public MsgActionMap() {
		this._innerMap = new HashMap<Short, IMsgAction<?>>();
	}

	@Override
	public IMsgAction<? extends AbstractMsg> getMsgAction(short msgTypeID) {
		if (msgTypeID <= 0) {
			return null;
		} else {
			return this._innerMap.get(msgTypeID);
		}
	}

	@Override
	public void putMsgAction(
		short msgTypeID,
		IMsgAction<? extends AbstractMsg> action) {

		if (msgTypeID <= 0) {
			// 消息类型 ID 不能小于或等于 0
			throw new XgameInvalidArgsError("msgTypeID <= 0");
		}

		if (action == null) {
			throw new XgameNullArgsError("action");
		}

		if (this._innerMap.containsKey(msgTypeID)) {
			// 如果消息类型 ID 已经被注册过, 
			// 则直接抛出异常
			throw new XgameMsgError("msgTypeID " + msgTypeID +" is duplicate");
		}

		// 设置消息行为
		this._innerMap.put(msgTypeID, action);
	}

}
