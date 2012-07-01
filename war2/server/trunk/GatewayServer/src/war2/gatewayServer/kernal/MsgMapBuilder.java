package war2.gatewayServer.kernal;

import war2.common.msg.IMsgMap;
import war2.common.msg.MsgMap;
import war2.gatewayServer.toScene.msg.GSToSceneMsg;

/**
 * 消息字典构建器
 * 
 * @author hjj2019
 *
 */
class MsgMapBuilder {
	/**
	 * 类默认构造器
	 * 
	 */
	private MsgMapBuilder() {
	}

	/**
	 * 构建消息字典
	 * 
	 * @return
	 * 
	 */
	public static IMsgMap build() {
		MsgMap map = new MsgMap();
		map.putMsg((short)1001, new GSToSceneMsg());
		return map;
	}
}
