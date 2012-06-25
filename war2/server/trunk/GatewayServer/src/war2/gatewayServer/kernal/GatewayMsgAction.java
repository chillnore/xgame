package war2.gatewayServer.kernal;

import org.apache.mina.core.session.IoSession;

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
public class GatewayMsgAction<TMsg extends AbstractMsg> implements IMsgAction<TMsg> {
	@Override
	public void execute(TMsg msg) {	
	}

	/**
	 * 发送消息给客户端
	 * 
	 * @param msg
	 * 
	 */
	protected void sendMsg2Client(AbstractMsg msg) {
		if (msg == null) {
			return;
		}

		// 根据玩家 ID 获取 IO 会话
		IoSession sess = OnlineSessionManager.theInstance().getSessionByPlayerID(null);

		if (sess == null) {
			return;
		} else {
			sess.write(msg);
		}
	}

	/**
	 * 设置对象实例到 Session 
	 * 
	 * @param key
	 * @param value 
	 * @param sessionID
	 * 
	 */
	protected void putKeyValue2Session(String key, Object value, long sessionID) {
		if (sessionID <= 0) {
			return;
		}

		if (key == null || 
			key.isEmpty()) {
			return;
		}

		// 获取会话对象
		IoSession sess = OnlineSessionManager.theInstance().getSessionByID(sessionID);

		if (sess == null) {
			return;
		}

		if (value == null) {
			// 如果值为空, 
			// 则直接移除会话中的属性
			sess.removeAttribute(key);
		} else {
			// 设置会话属性值
			sess.setAttribute(key, value);
		}
	}
}
