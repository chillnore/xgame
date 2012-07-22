package war2.sceneServer.bizModules.entry.action;

import war2.common.XgameNullArgsError;
import war2.sceneServer.bizModules.entry.bizService.EntryBizService;
import war2.sceneServer.bizModules.entry.bizService.EntryResult;
import war2.sceneServer.bizModules.entry.msg.CSEntry;
import war2.sceneServer.kernal.SceneMsgAction;

/**
 * 进入场景
 * 
 * @author hjj2019
 * 
 */
public class CSEntryAction extends SceneMsgAction<CSEntry> {
	/** 进入场景服务 */
	private EntryBizService _entryBizServ;

	/**
	 * 类默认构造器
	 * 
	 */
	public CSEntryAction() {
		// 创建进入场景的服务
		this._entryBizServ = new EntryBizService();
		// 添加进入场景回调
		this._entryBizServ.putEntryCallback(new EntryCallback(this));
	}

	@Override
	public void execute(CSEntry msg) {
		if (msg == null) {
			return;
		}

		this._entryBizServ.entryScene("123456");
	}

	/**
	 * 在消息行为中处理进入场景的结果
	 * 
	 * @param result 
	 * 
	 */
	private void handleResult(EntryResult result) {
		if (result == null) {
			return;
		}

		System.out.println("result");
	}

	/**
	 * 进入场景回调
	 * 
	 * @author hjj2017
	 * 
	 */
	private class EntryCallback implements EntryBizService.IEntryCallback {
		/** 消息行为 */
		private CSEntryAction _action;

		/**
		 * 类参数构造器
		 * 
		 * @param action 
		 * 
		 */
		public EntryCallback(CSEntryAction action) {
			if (action == null) {
				throw new XgameNullArgsError("action");
			}

			this._action = action;
		}

		@Override
		public void handleResult(EntryResult result) {
			if (result == null) {
				// 如果结果为空, 
				// 则直接退出!
				return;
			} else {
				// 如果结果不为空, 
				// 在消息行为中处理进入场景的结果
				this._action.handleResult(result);
			}
		}
	}
}
