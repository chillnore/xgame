package war2.sceneServer.kernal;

import org.apache.mina.core.session.IoSession;

import war2.common.msg.AbstractExternalMsg;
import war2.common.msg.AbstractMsg;
import war2.common.msg.IMsgAction;

/**
 * 网关消息处理器
 * 
 * @author hjj2019
 *
 * @param <TMsg>
 * 
 */
public class SceneMsgAction<TMsg extends AbstractMsg> implements IMsgAction<TMsg> {
	@Override
	public void execute(TMsg msg) {	
	}

	/**
	 * 发送消息给客户端
	 * 
	 * @param msg
	 * 
	 */
	protected void sendMsgToClient(AbstractExternalMsg msg) {
		if (msg == null) {
			return;
		}

		// 根据玩家 ID 获取 IO 会话
		IoSession session = OnlineSessionManager.theInstance().getSessionByPlayerID(null);

		if (session == null) {
			return;
		} else {
			session.write(msg);
		}
	}

	/**
	 * 设置键值对到会话 
	 * 
	 * @param key
	 * @param value 
	 * @param sessionID
	 * 
	 */
	protected void putKeyValueToSession(String key, Object value, long sessionID) {
		if (sessionID <= 0) {
			return;
		}

		if (key == null || 
			key.isEmpty()) {
			return;
		}

		if (value == null) {
			return;
		}

		// 获取会话对象
		IoSession session = OnlineSessionManager.theInstance().getSessionByID(sessionID);

		if (session == null) {
			return;
		} else {
			// 设置会话属性值
			session.setAttribute(key, value);
		}
	}

	/**
	 * 根据关键字名称和会话 ID 获取数值
	 * 
	 * @param key
	 * @param sessionID
	 * @return
	 * 
	 */
	protected Object getKeyValueBySessionID(String key, long sessionID) {
		if (key == null || 
			key.isEmpty()) {
			return null;
		}

		if (sessionID <= 0) {
			return null;
		}

		// 获取会话对象
		IoSession session = OnlineSessionManager.theInstance().getSessionByID(sessionID);

		if (session == null) {
			return null;
		} else {
			// 设置会话属性值
			return session.getAttribute(key);
		}
	}

	/**
	 * 设置玩家对象到会话
	 * 
	 * @param player
	 * @param sessionID
	 * 
	 */
	protected void putPlayerToSession(Player player, long sessionID) {
		if (player == null || 
			sessionID <= 0) {
			return;
		} else {
			OnlineSessionManager.theInstance().putPlayerToSession(player, sessionID);
		}
	}

	/**
	 * 根据会话 ID 获取玩家对象
	 * 
	 * @param sessionID
	 * @return 
	 * 
	 */
	protected Player getPlayerBySessionId(long sessionID) {
		if (sessionID <= 0) {
			return null;
		} else {
			return OnlineSessionManager.theInstance().getPlayerBySessionId(sessionID);
		}
	}
}
