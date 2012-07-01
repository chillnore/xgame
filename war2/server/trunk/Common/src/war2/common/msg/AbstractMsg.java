package war2.common.msg;

/**
 * 抽象消息
 * 
 * @author haijiang
 * @since 2012/6/3
 * 
 */
public abstract class AbstractMsg {
	/** 会话 ID */
	private long _sessionID;

	/**
	 * 获取消息类型 ID
	 * 
	 * @return
	 */
	public abstract short getMsgTypeID();

	/**
	 * 获取会话 ID 
	 * 
	 * @return
	 * 
	 */
	public long getSessionID() {
		return this._sessionID;
	}

	/**
	 * 设置会话 ID
	 * 
	 * @param value
	 * 
	 */
	void setSessionID(long value) {
		this._sessionID = value;
	}
}
