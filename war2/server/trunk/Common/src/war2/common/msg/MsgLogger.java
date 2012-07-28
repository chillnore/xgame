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
	private static volatile MsgLogger _instance;
	
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
	public static MsgLogger theInstance() {
		if (_instance == null) {
			synchronized (MsgLogger.class) {
				if (_instance == null) {
					_instance = new MsgLogger();
				}
			}
		}

		return _instance;
	}
}
