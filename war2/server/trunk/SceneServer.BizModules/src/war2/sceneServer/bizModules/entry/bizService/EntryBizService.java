package war2.sceneServer.bizModules.entry.bizService;

import war2.common.XgameNullArgsError;
import war2.common.io.IIoWork;
import war2.sceneServer.kernal.IoWorkThreadEnum;
import war2.sceneServer.kernal.SceneBizService;

/**
 * 进入场景服务
 * 
 * @author hjj2019
 * 
 */
public class EntryBizService extends SceneBizService {
	/** 进入场景回调 */
	private IEntryCallback _entryCall = null;

	/**
	 * 类默认构造器
	 * 
	 */
	public EntryBizService() {
	}

	/**
	 * 设置进入场景的回调
	 * 
	 * @param value
	 */
	public void putEntryCallback(IEntryCallback value) {
		this._entryCall = value;
	}

	/**
	 * 进入场景
	 * 
	 * @param ticket
	 * 
	 */
	public void entryScene(String ticket) {
		if (ticket == null || 
			ticket.isEmpty()) {
			return;
		}

		// 创建并执行 IO 操作
		this.startIoWork(new LoadTicketIoWork(this, ticket), IoWorkThreadEnum.login);
	}

	/**
	 * 加在票据完成
	 * 
	 * @param result;
	 * 
	 */
	private void entryScene_onLoadTicketFinished(EntryResult result) {
		if (result == null) {
			return;
		}

		if (this._entryCall != null) {
			this._entryCall.handleResult(result);
		}
	}

	/**
	 * 加载票据 IO 操作
	 * 
	 * @author hjj2017
	 * 
	 */
	private static class LoadTicketIoWork implements IIoWork {
		/** 服务对象 */
		private EntryBizService _serv;
		/** 票据 */
		private String _ticket;
		/** 进入场景的结果 */
		private EntryResult _result;

		/**
		 * 类参数构造器
		 * 
		 * @param serv 
		 * @param ticket
		 * 
		 */
		public LoadTicketIoWork(EntryBizService serv, String ticket) {
			if (serv == null) {
				throw new XgameNullArgsError("serv");
			}

			if (ticket == null || 
				ticket.isEmpty()) {
				throw new XgameNullArgsError("ticket");
			}

			this._serv = serv;
			this._ticket = ticket;
		}

		@Override
		public boolean doInit() {
			return true;
		}

		@Override
		public boolean doAsyncProc() {
			this._result = new EntryResult();
			this._result.setTicket(this._ticket);
			return true;
		}

		@Override
		public boolean doFinish() {
			// 加载票据完成
			this._serv.entryScene_onLoadTicketFinished(this._result);
			return true;
		}
	}

	/**
	 * 进入场景的回调接口
	 * 
	 * @author hjj2017
	 *
	 */
	public static interface IEntryCallback {
		/**
		 * 处理结果
		 * 
		 * @param result 
		 * 
		 */
		void handleResult(EntryResult result);
	}
}
