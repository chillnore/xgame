package war2.common.action;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import war2.common.XgameError;
import war2.common.XgameInvalidArgsError;
import war2.common.XgameNullArgsError;
import war2.common.msg.AbstractMsg;
import war2.common.msg.XgameMsgError;
import war2.common.utils.ClazzUtil;

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

	/**
	 * 添加消息行为
	 * 
	 * @param action
	 * 
	 */
	public void putMsgAction(
		IMsgAction<? extends AbstractMsg> action) {

		if (action == null) {
			return;
		} else {
			this.putMsgAction(getMsgTypeID(action), action);
		}
	}

	/**
	 * 从消息行为中获取消息类型 ID 
	 * 
	 * @param action
	 * @return
	 * 
	 */
	private static short getMsgTypeID(IMsgAction<?> action) {
		if (action == null) {
			throw new XgameNullArgsError("action");
		}

		// 获取行为类
		Class<?> actionClazz = action.getClass();
		
		// 获取 execute 方法
		Method executeMethod = ClazzUtil.getMethod(
			"execute", actionClazz);

		if (executeMethod == null) {
			// 如果找不到 execute 函数, 
			// 则直接抛出异常!
			throw new XgameError(
				"Can not find execute method in " + 
				actionClazz.getName());
		}

		// 获取第一个参数类
		Class<?> msgClazz = executeMethod.getParameterTypes()[0];

		if (msgClazz == null) {
			// 如果找不到参数类型, 
			// 则直接抛出异常!
			throw new XgameError(
				"Can not find params[0] type in " + 
				actionClazz.getName() + "." + executeMethod.getName());
		}

		try {
			// 创建消息类对象
			AbstractMsg msgObj = (AbstractMsg)msgClazz.newInstance();
			// 获取消息类型 ID
			return msgObj.getMsgTypeID();
		} catch (Exception ex) {
			// 抛出异常
			throw new XgameError(ex);
		}
	}
}
