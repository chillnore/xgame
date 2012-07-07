package war2.common.msg;

import war2.common.XgameError;

/**
 * 消息错误
 * 
 * @author haijiang
 * @since 2012/6/3
 * 
 */
public class XgameMsgError extends XgameError {
	/** serialVersionUID */
	private static final long serialVersionUID = 8136836021493508981L;

	/**
	 * 类默认构造器
	 * 
	 */
	public XgameMsgError() {
		super();
	}

	/**
	 * 类参数构造器
	 * 
	 * @param msg
	 * 
	 */
	public XgameMsgError(String msg) {
		super(msg);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param err
	 * 
	 */
	public XgameMsgError(Throwable err) {
		super(err);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param msg
	 * @param err
	 * 
	 */
	public XgameMsgError(String msg, Throwable err) {
		super(msg, err);
	}
}
