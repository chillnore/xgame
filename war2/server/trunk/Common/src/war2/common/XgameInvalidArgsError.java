package war2.common;

/**
 * 参数错误
 * 
 * @author haijiang
 *
 */
public class XgameInvalidArgsError extends XgameError {
	/** serialVersionUID */
	private static final long serialVersionUID = 7400420633310393852L;

	/**
	 * 类默认构造器
	 * 
	 */
	public XgameInvalidArgsError() {
		super();
	}

	/**
	 * 类参数构造器
	 * 
	 * @param msg
	 * 
	 */
	public XgameInvalidArgsError(String msg) {
		super(msg);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param err
	 * 
	 */
	public XgameInvalidArgsError(Throwable err) {
		super(err);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param msg
	 * @param err
	 * 
	 */
	public XgameInvalidArgsError(String msg, Throwable err) {
		super(msg, err);
	}
}
