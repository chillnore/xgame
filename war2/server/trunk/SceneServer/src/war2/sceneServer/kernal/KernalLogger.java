package war2.sceneServer.kernal;

import war2.common.XgameLogger;

/**
 * 内核日志
 * 
 * @author hjj2017
 * 
 */
class KernalLogger extends XgameLogger {
	/** 单例对象 */
	private static volatile KernalLogger _instance;
	
	/**
	 * 类默认构造器
	 * 
	 */
	private KernalLogger() {
		super(KernalLogger.class.getName());
	}

	/**
	 * 获取单例对象
	 * 
	 * @return
	 */
	public static KernalLogger theInstance() {
		if (_instance == null) {
			synchronized (KernalLogger.class) {
				if (_instance == null) {
					_instance = new KernalLogger();
				}
			}
		}

		return _instance;
	}
}
