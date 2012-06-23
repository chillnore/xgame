package war2.common.msg;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import war2.common.XgameNullArgsError;

/**
 * 消息 IO 处理器
 *  
 * @author AfritXia
 * @since 2012/6/3
 *
 */
public class MsgIoHandler extends IoHandlerAdapter {
	/** 消息处理器 */
	private IMsgProcessor _msgProc;

	/**
	 * 类参数构造器
	 * 
	 * @param msgProc
	 * @throws XgameNullArgsError if msgProc == null;
	 * 
	 */
	public MsgIoHandler(IMsgProcessor msgProc) {
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
