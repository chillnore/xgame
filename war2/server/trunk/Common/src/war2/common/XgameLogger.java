package war2.common;

import org.apache.log4j.Logger;

/**
 * 日志
 * 
 * @author haijiang
 *
 */
public class XgameLogger {
	/** 日志名称 */
	private String _loggerName = null;

	/**
	 * 类参数构造器
	 * 
	 * @param loggerName
	 * @throws XgameNullArgsError if loggerName == null or loggerName.isEmpty
	 * 
	 */
	public XgameLogger(String loggerName) {
		if (loggerName == null || 
			loggerName.isEmpty()) {
			throw new XgameNullArgsError("loggerName");
		}

		this._loggerName = loggerName;
	}

	/**
	 * 记录 Info 级别的信息
	 * 
	 * @param msg
	 */
	public void logInfo(String msg) {
		if (msg == null || 
			msg.isEmpty()) {
			return;
		}

		this.getLogger().info(msg);
	}

	/**
	 * 记录 Warning 级别的消息
	 * 
	 * @param msg 
	 * 
	 */
	public void logWarn(String msg) {
		if (msg == null || 
			msg.isEmpty()) {
			return;
		}

		this.getLogger().warn(msg);
	}

	/**
	 * 记录错误信息
	 * 
	 * @param msg
	 */
	public void logError(String msg) {
		if (msg == null || 
			msg.isEmpty()) {
			return;
		}

		this.getLogger().error(msg);
	}

	/**
	 * 记录错误信息
	 * 
	 * @param msg
	 */
	public void logError(Throwable err) {
		if (err == null) {
			return;
		}

		this.getLogger().error(null, err);
	}

	/**
	 * 记录错误信息
	 * 
	 * @param msg
	 */
	public void logError(String msg, Throwable err) {
		if ((msg == null || msg.isEmpty()) && 
			(err == null)) {
			return;
		}

		this.getLogger().error(msg, err);
	}

	/**
	 * 记录调试信息
	 * 
	 * @param msg 
	 * 
	 */
	public void logDebug(String msg) {
		if (msg == null || 
			msg.isEmpty()) {
			return;
		}

		this.getLogger().debug(msg);
	}

	/**
	 * 获取日志对象
	 * 
	 * @return
	 */
	private Logger getLogger() {
		return Logger.getLogger(this._loggerName);
	}
}
