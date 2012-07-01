package war2.gatewayServer.kernal;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import war2.common.XgameNullArgsError;
import war2.common.msg.AbstractMsg;
import war2.common.msg.IMsgProcessor;

/**
 * 消息 IO 处理器
 *  
 * @author AfritXia
 * @since 2012/6/3
 *
 */
class MINA_IoHandler extends IoHandlerAdapter {
	/** 消息处理器 */
	private IMsgProcessor _msgProc;

	/**
	 * 类参数构造器
	 * 
	 * @param msgProc
	 * @throws XgameNullArgsError if msgProc == null;
	 * 
	 */
	public MINA_IoHandler(IMsgProcessor msgProc) {
		if (msgProc == null) {
			throw new XgameNullArgsError("msgProc");
		}

		this._msgProc = msgProc;
	}

	@Override
	public void sessionCreated(IoSession sess) {
		if (sess == null) {
			return;
		}

		// TODO : 为玩家分派一个 SessionID
		// TODO : 将 Session 加入到 OnlineSessionManager
	}

	@Override
	public void messageReceived(IoSession sess, Object obj) {
		if (sess == null || 
			obj == null) {
			return;
		}

		// 处理消息对象
		this._msgProc.enqueue((AbstractMsg)obj);
	}
}
