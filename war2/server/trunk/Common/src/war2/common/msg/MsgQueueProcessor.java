package war2.common.msg;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import war2.common.XgameNullArgsError;
import war2.common.action.AbstractMsgActionMap;

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
	 * @param msgActionMap 消息行为字典
	 * @throws XgameNullArgsError if name == null
	 * @throws XgameNullArgsError if msgActionMap == null 
	 * 
	 */
	public MsgQueueProcessor(String name, AbstractMsgActionMap msgActionMap) {
		if (name == null || name.isEmpty()) {
			throw new XgameNullArgsError("name");
		}

		if (msgActionMap == null) {
			throw new XgameNullArgsError("msgActionMap");
		}

		// 线程命名工厂
		ThreadNamingFactory nf = new ThreadNamingFactory();
		// 创建固定线程池
		ExecutorService execServ = Executors.newSingleThreadExecutor(nf);

		// 设置线程名称
		nf.putCurrName(name);
		// 创建运行器
		this._runner = new MsgQueueRunner(msgActionMap);
		// 开启消息线程
		execServ.submit(this._runner);
	}

	@Override
	public void enqueue(AbstractMsg msg) {
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
}
