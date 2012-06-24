package war2.gatewayServer.kernal;

import org.apache.mina.core.session.IoSession;

/**
 * 在线玩家管理器
 * 
 * @author hjj2019
 * 
 */
public class OnlineSessionManager {
	/** 对象实例 */
	private static volatile OnlineSessionManager _instance;

	/**
	 * 类默认构造器
	 * 
	 */
	private OnlineSessionManager() {
	}

	/**
	 * 获取在线玩家管理器对象实例
	 * 
	 * @return
	 */
	public static OnlineSessionManager theInstance() {
		if (_instance == null) {
			synchronized (OnlineSessionManager.class) {
				if (_instance == null) {
					_instance = new OnlineSessionManager();
				}
			}
		}

		return _instance;
	}

	/**
	 * 跟进玩家 ID 获取 IO 会话
	 * 
	 * @param playerID
	 * @return 
	 * 
	 */
	public IoSession getSessionByPlayerID(String playerID) {
		return null;
	}
}
