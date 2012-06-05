package war2.gatewayServer.handler;

import war2.gatewayServer.GatewayKernal;
import war2.gatewayServer.msg.CGCommonMsg;

/**
 * CG 消息处理器
 * 
 * @author Haijiang
 * @since 2012/6/3
 *
 */
public class CGMsgHandler {
	/** 单例对象 */
	private static CGMsgHandler _instance;

	/**
	 * 类默认构造器
	 * 
	 */
	private CGMsgHandler() {
	}

	/**
	 * 获取单例对象
	 * 
	 * @return 
	 * 
	 */
	public static CGMsgHandler theInstance() {
		if (_instance == null) {
			synchronized (CGMsgHandler.class) {
				if (_instance == null) {
					_instance = new CGMsgHandler();
				}
			}
		}

		return _instance;
	}

	/**
	 * 处理 CG 消息
	 * 
	 * @param cgmsg
	 * 
	 */
	public void handleClientCommonMsg(CGCommonMsg cgmsg) {
		if (cgmsg == null) {
			return;
		}

		if (cgmsg.getMsgTypeID() == 1001) {
			// TODO : 将消息发送到登陆服务器进行处理
			GatewayKernal._glMsgIoHandler.sendMsg(cgmsg);
		} else {
			// TODO : 将消息发送场景服务器进行处理
		}
	}
}
