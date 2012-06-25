package war2.common.msg;

import java.util.HashMap;
import java.util.Map;

import war2.common.XgameInvalidArgsError;
import war2.common.XgameNullArgsError;


/**
 * 消息类型管理器
 * 
 * @author AfritXia
 * @since 2011/9/25
 * @version $Rev: 0 $
 * 
 */
public class MsgMap implements IMsgMap {
	/** 消息类型字典 */
	private Map<Short, AbstractExternalMsg> _msgMap;

	/**
	 * 类默认构造器
	 * 
	 */
	public MsgMap() {
		this._msgMap = new HashMap<Short, AbstractExternalMsg>();
	}

	@Override
	public void putMsg(AbstractExternalMsg msg) {
		if (msg == null) {
			throw new XgameNullArgsError("msg");
		}

		if (msg.getMsgTypeID() <= 0) {
			// 消息类型 ID 不能小于或等于 0
			throw new XgameInvalidArgsError("msgTypeID <= 0");
		}

		if (this._msgMap.containsKey(msg.getMsgTypeID())) {
			// 如果消息类型 ID 已经被注册过, 
			// 则直接抛出异常
			throw new XgameMsgError("msgTypeID is duplicate, msgTypeID = " + msg.getMsgTypeID());
		}

		// 设置消息
		this._msgMap.put(msg.getMsgTypeID(), msg);
	}

	@Override
	public AbstractExternalMsg getMsg(short msgTypeID) {
		if (msgTypeID <= 0) {
			return null;
		} else {
			return this._msgMap.get(msgTypeID);
		}
	}
}