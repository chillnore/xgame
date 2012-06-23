package war2.gatewayServer.msg;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import war2.common.XgameNullArgsError;
import war2.common.msg.AbstractMsg;
import war2.common.msg.IMsgProcessor;

public class GLMsgIoHandler extends IoHandlerAdapter {
	private IoSession _sess;
	/** 消息处理器 */
	private IMsgProcessor _msgProc;

	/**
	 * 类参数构造器
	 * 
	 * @param msgProc
	 * @throws XgameNullArgsError if msgProcr == null;
	 * 
	 */
	public GLMsgIoHandler(IMsgProcessor msgProc) {
		if (msgProc == null) {
			throw new XgameNullArgsError("msgProc");
		}

		this._msgProc = msgProc;
	}

	@Override
	public void sessionCreated(IoSession sess) {
		if (sess == null) {
			return;
		} else {
			this._sess = sess;
		}
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

	public void sendMsg(Object obj) {
		this._sess.write(obj);
	}
}