package war2.langdingServer.handler;

import war2.langdingServer.msg.GLLogin0Msg;
import war2.langdingServer.service.LoginService;


public class LoginMsgHandler {
	/** 单例对象 */
	private static LoginMsgHandler _instance;

	/**
	 * 类默认构造器
	 * 
	 */
	private LoginMsgHandler() {
	}

	/**
	 * 获取单例对象
	 * 
	 * @return 
	 * 
	 */
	public static LoginMsgHandler theInstance() {
		if (_instance == null) {
			synchronized (LoginMsgHandler.class) {
				if (_instance == null) {
					_instance = new LoginMsgHandler();
				}
			}
		}

		return _instance;
	}

	/**
	 * 处理 CG 消息
	 * 
	 * @param glmsg
	 * 
	 */
	public void handleLogin0Msg(GLLogin0Msg glmsg) {
		if (glmsg == null) {
			return;
		}

		(new LoginService()).validate(glmsg.getUserName(), "123456");
	}
}
