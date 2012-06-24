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
		}

		sess.write(msg);
	}
}
