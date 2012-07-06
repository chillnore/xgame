package war2.sceneServer.kernal;

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
	private Map<String, Long> _playerIDToSessionIDMap;

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
		// 创建 IO 会话字典
		this._sessionMap = new ConcurrentHashMap<Long, IoSession>();
		// 创玩家 ID 到会话 ID 的字典
		this._playerIDToSessionIDMap = new ConcurrentHashMap<String, Long>();
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

		long sessionID = session.getId();

		if (sessionID <= 0) {
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
	public void putPlayerToSession(Player player, long sessionID) {
		if (player == null || 
			sessionID <= 0) {
			return;
		}

		// 获取会话对象
		IoSession session = this.getSessionByID(sessionID);

		if (session == null) {
			return;
		}

		// 将玩家对象存入会话对象
		session.setAttribute(SESSION_PLAYER_KEY, player);
		// 管家玩家 ID 和 会话 ID
		this._playerIDToSessionIDMap.put(player.getID(), sessionID);
	}

	/**
	 * 取消 IO 会话与 Player 对象的关联
	 * 
	 * @param sessionID
	 * 
	 */
	public void removePlayerBySession(long sessionID) {
		if (sessionID <= 0) {
			return;
		}

		// 获取会话对象
		IoSession session = this.getSessionByID(sessionID);

		if (session == null) {
			return;
		}

		// 获取玩家对象
		Player player = (Player)session.getAttribute(SESSION_PLAYER_KEY);

		if (player != null) {
			// 如果玩家对象不为空, 
			// 则取消玩家 ID 与会话 ID 的关联关系!
			this._playerIDToSessionIDMap.remove(player.getID());
		}

		// 将玩家对象移出会话对象
		session.removeAttribute(SESSION_PLAYER_KEY);
	}

	/**
	 * 跟进玩家 ID 获取 IO 会话对象
	 * 
	 * @param playerID
	 * @return 
	 * 
	 */
	public IoSession getSessionByPlayerID(String playerID) {
		if (playerID == null || 
			playerID.isEmpty()) {
			return null;
		}

		// 获取会话 ID
		Long sessionID = this._playerIDToSessionIDMap.get(playerID);

		if (sessionID == null || 
			sessionID <= 0) {
			return null;
		}

		// 获取 IO 会话对象
		IoSession session = this._sessionMap.get(sessionID);

		if (session == null) {
			// 
			// 如果 IO 会话对象为空, 
			// 则取消玩家 ID 与会话 ID 的关联关系!
			// 注意: 一定是现有 IoSession, 然后才有的 Player...
			// 如果 IoSession 已经不存在了, 
			// 那么 Player 也必然不存在!
			// 
			this._playerIDToSessionIDMap.remove(playerID);
		}

		return session;
	}

	/**
	 * 根据会话 ID 获取玩家对象
	 * 
	 * @param sessionID
	 * @return
	 * 
	 */
	public Player getPlayerBySessionId(long sessionID) {
		if (sessionID <= 0) {
			return null;
		}

		// 获取会话对象
		IoSession session = this.getSessionByID(sessionID);

		if (session == null) {
			return null;
		} else {
			return (Player)session.getAttribute(SESSION_PLAYER_KEY);
		}
	}
}
