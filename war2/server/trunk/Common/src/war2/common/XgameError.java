package war2.common;

/**
 * 游戏异常
 * 
 * @author haijiang
 * @since 2012/6/3
 *
 */
public class XgameError extends RuntimeException {
	/** serialVersionUID */
	private static final long serialVersionUID = -1001L;

	/**
	 * 类默认构造器
	 * 
	 */
	public XgameError() {
		super();
	}

	/**
	 * 类参数构造器
	 * 
	 * @param msg
	 * 
	 */
	public XgameError(String msg) {
		super(msg);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param err
	 * 
	 */
	public XgameError(Throwable err) {
		super(err);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param msg
	 * @param err
	 * 
	 */
	public XgameError(String msg, Throwable err) {
		super(msg, err);
	}
}
