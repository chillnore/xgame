package war2.gatewayServer.toScene.action;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import war2.common.XgameNullArgsError;
import war2.common.msg.AbstractMsg;
import war2.common.msg.IMsgProcessor;

/**
 * 场景 IO 处理器
 * 
 * @author hjj2019
 *
 */
public class GSIoHandler extends IoHandlerAdapter {
	/** IO 会话 */
	private IoSession _session;
	/** 消息处理器 */
	private IMsgProcessor _msgProc;

	/**
	 * 类参数构造器
	 * 
	 * @param msgProc
	 * @throws XgameNullArgsError if msgProcr == null;
	 * 
	 */
	public GSIoHandler(IMsgProcessor msgProc) {
		if (msgProc == null) {
			throw new XgameNullArgsError("msgProc");
		}

		this._msgProc = msgProc;
	}

	@Override
	public void sessionCreated(IoSession session) {
		if (session == null) {
			return;
		} else {
			this._session = session;
		}
	}

	@Override
	public void messageReceived(IoSession session, Object obj) {
		if (session == null || 
			obj == null) {
			return;
		}

		// 处理消息对象
		this._msgProc.enqueue((AbstractMsg)obj);
	}

	/**
	 * 发送消息到场景服务器
	 * 
	 * @param msg
	 * 
	 */
	public void sendMsgToScene(AbstractMsg msg) {
		this._session.write(msg);
	}
}