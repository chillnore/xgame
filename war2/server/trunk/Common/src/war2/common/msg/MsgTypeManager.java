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
public class MsgTypeManager implements IMsgTypeManager {
	/** 消息类型字典 */
	private Map<Short, Class<? extends IMsg>> _msgMap;

	/**
	 * 类默认构造器
	 * 
	 */
	public MsgTypeManager() {
		this._msgMap = new HashMap<Short, Class<? extends IMsg>>();
	}

	/**
	 * 注册消息类
	 * 
	 * @param msgTypeID
	 * @param msgClazz 
	 * @throws XgameInvalidArgsError if msgTypeID <= 0
	 * @throws XgameNullArgsError if msgClazz == null 
	 * 
	 */
	public void register(
		short msgTypeID, 
		Class<? extends IMsg> msgClazz) {
		if (msgTypeID <= 0) {
			// 消息类型 ID 不能小于或等于 0
			throw new XgameInvalidArgsError("msgTypeID <= 0");
		}

		if (msgClazz == null) {
			// 消息类不能为空
			throw new XgameNullArgsError("msgClazz");
		}

		if (this._msgMap.containsKey(msgTypeID)) {
			// 如果消息类型 ID 已经被注册过, 
			// 则直接抛出异常
			throw new XgameMsgError("msgTypeID duplicate, msgTypeID = " + msgTypeID);
		}

		// 设置消息类型
		this._msgMap.put(msgTypeID, msgClazz);
	}

	@Override
	public Class<? extends IMsg> getMsgClazz(short msgTypeID) {
		return this._msgMap.get(msgTypeID);
	}
}
