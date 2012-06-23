package war2.common.msg;

/**
 * 抽象消息
 * 
 * @author haijiang
 * @since 2012/6/3
 *
 */
public abstract class AbstractMsg {
	/** 消息类型 */
	private short _msgTypeID = 0;
	/** 会话 ID */
	private String _sessionID;

	/**
	 * 获取消息类型 ID
	 * 
	 * @return
	 */
	public short getMsgTypeID() {
		return this._msgTypeID;
	}

	/**
	 * 设置消息类型
	 * 
	 * @param value
	 * 
	 */
	void setMsgTypeID(short value) {
		this._msgTypeID = value;
	}

	/**
	 * 获取会话 ID 
	 * 
	 * @return
	 * 
	 */
	public String getSessionID() {
		return this._sessionID;
	}

	/**
	 * 设置会话 ID
	 * 
	 * @param value
	 * 
	 */
	void setSessionID(String value) {
		this._sessionID = value;
	}
}
