package war2.common;

/**
 * 不支持错误
 * 
 * @author Haijiang
 * @since 2012/6/3
 *
 */
public class XgameNotSupportedError extends XgameError {
	/** serialVersionUID */
	private static final long serialVersionUID = 3917831565297901055L;

	/**
	 * 类默认构造器
	 * 
	 */
	public XgameNotSupportedError() {
		super();
	}

	/**
	 * 类参数构造器
	 * 
	 * @param msg
	 * 
	 */
	public XgameNotSupportedError(String msg) {
		super(msg);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param err
	 * 
	 */
	public XgameNotSupportedError(Throwable err) {
		super(err);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param msg
	 * @param err
	 * 
	 */
	public XgameNotSupportedError(String msg, Throwable err) {
		super(msg, err);
	}
}
