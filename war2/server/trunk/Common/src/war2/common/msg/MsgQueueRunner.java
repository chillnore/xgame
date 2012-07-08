package war2.common.msg;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import war2.common.XgameNullArgsError;
import war2.common.action.IMsgAction;
import war2.common.action.AbstractMsgActionMap;

/**
 * 消息队列运行器
 * 
 * @author haijiang
 * @since 2012/6/3 
 *
 */
final class MsgQueueRunner implements Runnable {
	/** 消息队列 */
	private BlockingQueue<AbstractMsg> _msgQueue;
	/** 消息行为字典 */
	private AbstractMsgActionMap _msgActionMap;

	/**
	 * 类参数构造器
	 * 
	 * @param msgActionMap 
	 * @throws XgameNullArgsError if msgActionMap == null
	 * 
	 */
	public MsgQueueRunner(AbstractMsgActionMap msgActionMap) {
		if (msgActionMap == null) {
			throw new XgameNullArgsError("msgActionMap");
		}

		this._msgQueue = new LinkedBlockingQueue<AbstractMsg>();
		this._msgActionMap = msgActionMap;
	}

	/**
	 * 消息入队
	 * 
	 * @param msg
	 */
	public void enqueue(AbstractMsg msg) {
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
				AbstractMsg msg = this._msgQueue.take();

				if (msg == null) {
					// 如果消息为空, 
					// 则直接跳过并处理下一个消息
					continue;
				}
				
				// 记录日志消息
				logInfo("MsgQueueProcessor[@name='" + Thread.currentThread().getName() + "']$MsgQueueRunner#run >> take : " + msg.getClass().getSimpleName());

				// 获取消息行为
				@SuppressWarnings("unchecked")
				IMsgAction<AbstractMsg> msgAction = (IMsgAction<AbstractMsg>)this._msgActionMap.getMsgAction(msg.getMsgTypeID());

				if (msgAction == null) {
					// 如果消息行为为空, 
					// 则直接跳过并处理下一个消息
					continue;
				}

				// 执行消息
				msgAction.execute(msg);
			} catch (Exception ex) {
				// 记录错误信息
				logError(new XgameMsgError(ex));
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
