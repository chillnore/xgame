package war2.gatewayServer.kernal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.session.IoSession;

/**
 * 在线玩家管理器
 * 
 * @author hjj2019
 * 
 */
final class OnlineSessionManager {
	/** 玩家数据 */
	private static final String SESSION_PLAYER_KEY = "player";
	/** 对象实例 */
	private static volatile OnlineSessionManager _instance;

	/** 会话字典 */
	private Map<Long, IoSession> _sessionMap;
	/** 玩家 ID => 会话 ID 字典 */
	private Map<String, Long> _playerID2SessionIDMap;

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
					_instance.init();
				}
			}
		}

		return _instance;
	}

	/**
	 * 初始化
	 * 
	 */
	private void init() {
		this._sessionMap = new ConcurrentHashMap<Long, IoSession>();
	}

	/**
	 * 添加 IO 会话对象
	 * 
	 * @param session
	 * 
	 */
	public void addSession(IoSession session) {
		if (session == null) {
			return;
		}

		Long sessionID = session.getId();

		if (sessionID == null || 
			sessionID <= 0) {
			return;
		} else {
			this._sessionMap.put(sessionID, session);
		}
	}

	/**
	 * 根据会话 ID 获取 IO 会话对象
	 * 
	 * @param sessionID
	 * @return
	 * 
	 */
	public IoSession getSessionByID(long sessionID) {
		if (sessionID <= 0) {
			return null;
		} else {
			return this._sessionMap.get(sessionID);
		}
	}

	/**
	 * 设置 IO 会话与 Player 对象的关联
	 * 
	 * @param sessionID
	 * @param player
	 * 
	 */
	public void putPlayer2Session(Object player, long sessionID) {
		if (sessionID <= 0) {
			return;
		}

		// 获取会话对象
		IoSession session = this.getSessionByID(sessionID);

		if (session == null) {
			return;
		}

		if (player == null) {
			// 如果玩家对象为空, 
			// 则清除会话中保存的玩家对象
			session.removeAttribute(SESSION_PLAYER_KEY);
		} else {
			// 如果玩家对象不为空, 
			// 将玩家对象存入会话对象
			session.setAttribute(SESSION_PLAYER_KEY, player);
		}
	}

	/**
	 * 跟进玩家 ID 获取 IO 会话对象
	 * 
	 * @param playerID
	 * @return 
	 * 
	 */
	public IoSession getSessionByPlayerID(String playerID) {
		return null;
	}
}
