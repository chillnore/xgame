package war2.common;

/**
 * 参数错误
 * 
 * @author haijiang
 * @since 2012/6/3
 *
 */
public class XgameNullArgsError extends XgameError {
	/** serialVersionUID */
	private static final long serialVersionUID = 9070716904017022631L;

	/**
	 * 类默认构造器
	 * 
	 */
	public XgameNullArgsError() {
		super();
	}

	/**
	 * 类参数构造器
	 * 
	 * @param msg
	 * 
	 */
	public XgameNullArgsError(String msg) {
		super(msg);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param err
	 * 
	 */
	public XgameNullArgsError(Throwable err) {
		super(err);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param msg
	 * @param err
	 * 
	 */
	public XgameNullArgsError(String msg, Throwable err) {
		super(msg, err);
	}
}
