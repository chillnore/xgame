package war2.common.io;

import war2.common.XgameLogger;

/**
 * IO 日志
 * 
 * @author haijiang
 *
 */
public class IoWorkLogger extends XgameLogger {
	/** 单例对象 */
	private static volatile IoWorkLogger _instance;
	
	/**
	 * 类默认构造器
	 * 
	 */
	private IoWorkLogger() {
		super(IoWorkLogger.class.getName());
	}

	/**
	 * 获取单例对象
	 * 
	 * @return
	 */
	public static IoWorkLogger theInstance() {
		if (_instance == null) {
			synchronized (XgameLogger.class) {
				if (_instance == null) {
					_instance = new IoWorkLogger();
				}
			}
		}

		return _instance;
	}
}
