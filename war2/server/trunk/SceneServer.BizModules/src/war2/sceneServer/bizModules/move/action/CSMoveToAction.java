package war2.sceneServer.bizModules.move.action;

import war2.sceneServer.bizModules.move.msg.CSMoveTo;
import war2.sceneServer.bizModules.move.msg.SCMoveTo;
import war2.sceneServer.kernal.SceneMsgAction;

/**
 * 移动
 * 
 * @author hjj2017
 *
 */
public class CSMoveToAction extends SceneMsgAction<CSMoveTo> {
	@Override
	public void execute(CSMoveTo csmsg) {
		if (csmsg == null) {
			return;
		}

//		// 获取玩家信息
//		Player p = this.getPlayerBySessionId(csmsg.getSessionID());
//
//		if (p == null) {
//			return;
//		}

		SCMoveTo scmsg = new SCMoveTo();

		scmsg.setPlayerID(String.valueOf(csmsg.getSessionID()));
		scmsg.setX(csmsg.getX());
		scmsg.setY(csmsg.getY());

		// 将引动消息广播给所有人
		this.broadcast(scmsg);
	}
}
