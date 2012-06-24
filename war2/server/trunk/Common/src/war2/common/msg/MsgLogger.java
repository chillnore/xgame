package war2.common.msg;

import war2.common.XgameLogger;

/**
 * 消息日志
 * 
 * @author haijiang
 * @since 2012/6/3
 *
 */
public class MsgLogger extends XgameLogger {
	/** 单例对象 */
	private static volatile MsgLogger _theInstance;
	
	/**
	 * 类默认构造器
	 * 
	 */
	private MsgLogger() {
		super(MsgLogger.class.getName());
	}

	/**
	 * 获取单例对象
	 * 
	 * @return
	 */
	public static MsgLogger getInstance() {
		if (_theInstance == null) {
			synchronized (XgameLogger.class) {
				if (_theInstance == null) {
					_theInstance = new MsgLogger();
				}
			}
		}

		return _theInstance;
	}
}
