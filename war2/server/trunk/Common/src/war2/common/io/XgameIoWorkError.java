package war2.common.io;

import war2.common.XgameError;

/**
 * IO 错误
 * 
 * @author haijiang
 *
 */
public class XgameIoWorkError extends XgameError {
	/** serialVersionUID */
	private static final long serialVersionUID = 3844453167745941900L;

	/**
	 * 类默认构造器
	 * 
	 */
	public XgameIoWorkError() {
		super();
	}

	/**
	 * 类参数构造器
	 * 
	 * @param msg
	 */
	public XgameIoWorkError(String msg) {
		super(msg);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param error
	 */
	public XgameIoWorkError(Throwable error) {
		super(error);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param msg
	 * @param error
	 */
	public XgameIoWorkError(String msg, Throwable error) {
		super(msg, error);
	}
}
