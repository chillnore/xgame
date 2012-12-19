package war2.autocode.kernal;

import java.text.SimpleDateFormat;

/**
 * 日志记录器
 * 
 * @author AfritXia
 * @since 2011/10/31
 * @version $Rev: 0 $
 *
 */
public class Logger {
	/** 日期格式 */
	private static final SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss");

	/**
	 * 类默认构造器
	 * 
	 */
	private Logger() {
	}

	/**
	 * 记录日志信息
	 * 
	 * @param msg 
	 * 
	 */
	public static void log(String msg) {
		if (msg == null || 
			msg.isEmpty()) {
			return;
		}

		System.out.print("[" + SDF.format(System.currentTimeMillis()) + "] ");
		System.out.println(msg);
	}
}
