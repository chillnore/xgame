package war2.common.io;

import war2.common.XgameLogger;

/**
 * IO 日志
 * 
 * @author haijiang
 *
 */
public class IoLogger extends XgameLogger {
	/** 单例对象 */
	private static volatile IoLogger _instance;
	
	/**
	 * 类默认构造器
	 * 
	 */
	private IoLogger() {
		super(IoLogger.class.getName());
	}

	/**
	 * 获取单例对象
	 * 
	 * @return
	 */
	public static IoLogger theInstance() {
		if (_instance == null) {
			synchronized (XgameLogger.class) {
				if (_instance == null) {
					_instance = new IoLogger();
				}
			}
		}

		return _instance;
	}
}
