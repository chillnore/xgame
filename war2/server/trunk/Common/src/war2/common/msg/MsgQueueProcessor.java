package war2.common.msg;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

import war2.common.msg.IMsg;
import war2.common.msg.IMsgProcessor;
import war2.common.msg.MsgLogger;
import war2.common.msg.XgameMsgError;

/**
 * 消息队列处理器
 * 
 * @author Haijiang
 * @since 2012/6/3 
 * 
 */
public class MsgQueueProcessor implements IMsgProcessor {
	/** 消息队列运行器 */
	private MsgQueueRunner _runner;

	/**
	 * 类参数构造器
	 * 
	 * @param name 消息处理器名称
	 * 
	 */
	public MsgQueueProcessor(final String name) {
		// 线程命名工厂
		ThreadNamingFactory nf = new ThreadNamingFactory();
		// 创建固定线程池
		ExecutorService execServ = Executors.newSingleThreadExecutor(nf);

		// 设置线程名称
		nf.putCurrName(name);
		// 创建运行器
		this._runner = new MsgQueueRunner();
		// 开启消息线程
		execServ.submit(this._runner);
	}

	@Override
	public void enqueue(IMsg msg) {
		if (msg == null) {
			return;
		}

		// 令消息入队
		this._runner.enqueue(msg);
	}

	/**
	 * 线程命名工厂类
	 * 
	 * @author haijiang
	 * @since 2012/6/3 
	 * 
	 */
	private static class ThreadNamingFactory implements ThreadFactory {
		/** 当前名称 */
		private String _currName;

		/**
		 * 设置当前名称
		 * 
		 * @param value
		 */
		public void putCurrName(String value) {
			this._currName = value;
		}

		@Override
		public Thread newThread(Runnable r) {
			if (r == null) {
				return null;
			} else {
				return new Thread(r, this._currName);
			}
		}
	}

	/**
	 * 消息队列运行器
	 * 
	 * @author haijiang
	 * @since 2012/6/3 
	 *
	 */
	private static class MsgQueueRunner implements Runnable {
		/** 消息队列 */
		private BlockingQueue<IMsg> _msgQueue;

		/**
		 * 类参数构造器
		 * 
		 */
		public MsgQueueRunner() {
			this._msgQueue = new LinkedBlockingQueue<IMsg>();
		}

		/**
		 * 消息入队
		 * 
		 * @param msg
		 */
		public void enqueue(IMsg msg) {
			if (msg == null) {
				return;
			} else {
				this._msgQueue.add(msg);
			}
		}

		@Override
		public void run() {
			while (true) {
				try {
					// 从消息队列中获取消息并执行
					IMsg msg = this._msgQueue.take();
					
					// 记录日志消息
					logInfo("< MsgQueueProcessor[@name='" + Thread.currentThread().getName() + "']$MsgQueueRunner#run > take " + msg.getClass().getSimpleName());

					// 执行消息
					msg.execute();
				} catch (Exception ex) {
					// 记录错误信息
					logError(new XgameMsgError(ex));
				}
			}
		}
	}

	/**
	 * 记录日志信息
	 * 
	 * @param msg 
	 * 
	 */
	private static void logInfo(String msg) {
		if (msg == null || 
			msg.isEmpty()) {
			return;
		}

		MsgLogger.getInstance().logInfo(msg);
	}

	/**
	 * 记录错误信息
	 * 
	 * @param msg 
	 * 
	 */
	private static void logError(Throwable err) {
		if (err == null) {
			return;
		}

		MsgLogger.getInstance().logError(err);
	}
}
