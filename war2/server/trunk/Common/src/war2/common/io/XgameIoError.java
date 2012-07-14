package war2.common.io;

import war2.common.XgameError;

/**
 * IO 错误
 * 
 * @author haijiang
 *
 */
public class XgameIoError extends XgameError {
	/** serialVersionUID */
	private static final long serialVersionUID = 3844453167745941900L;

	/**
	 * 类默认构造器
	 * 
	 */
	public XgameIoError() {
		super();
	}

	/**
	 * 类参数构造器
	 * 
	 * @param msg
	 */
	public XgameIoError(String msg) {
		super(msg);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param error
	 */
	public XgameIoError(Throwable error) {
		super(error);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param msg
	 * @param error
	 */
	public XgameIoError(String msg, Throwable error) {
		super(msg, error);
	}
}
