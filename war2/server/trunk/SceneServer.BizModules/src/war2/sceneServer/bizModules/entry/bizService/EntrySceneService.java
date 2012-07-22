package war2.sceneServer.bizModules.entry.bizService;

import war2.common.io.IIoWork;
import war2.sceneServer.kernal.IoWorkThreadEnum;
import war2.sceneServer.kernal.Player;
import war2.sceneServer.kernal.SceneBizService;

/**
 * 进入场景服务
 * 
 * @author hjj2019
 * 
 */
public class EntrySceneService extends SceneBizService {
	/**
	 * 进入场景
	 * 
	 * @param ticket
	 * 
	 */
	public void entryScene(String ticket) {
		if (ticket == null) {
			return;
		}

		this.startIoWork(new LoadTicketIoWork(this), IoWorkThreadEnum.login);
	}

	/**
	 * 加在票据完成
	 * 
	 */
	private void entryScene_onLoadTicketFinished(Player player) {
	}

	private static class LoadTicketIoWork implements IIoWork {
		/** 服务对象 */
		private EntrySceneService _serv;
		private Player _player;

		/**
		 * 类参数构造器
		 * 
		 * @param serv 
		 * 
		 */
		public LoadTicketIoWork(EntrySceneService serv) {
			this._serv = serv;
		}

		@Override
		public boolean doInit() {
			return true;
		}

		@Override
		public boolean doAsyncProc() {
			this._player = new Player();
			return true;
		}

		@Override
		public boolean doFinish() {
			// 加载票据完成
			this._serv.entryScene_onLoadTicketFinished(this._player);
			return true;
		}
	}
}
