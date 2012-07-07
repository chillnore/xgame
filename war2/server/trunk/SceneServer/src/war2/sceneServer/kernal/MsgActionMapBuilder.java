package war2.sceneServer.kernal;

import war2.common.action.IMsgActionMap;
import war2.common.action.MsgActionMap;

/**
 * 消息字典构建器
 * 
 * @author hjj2019
 *
 */
class MsgActionMapBuilder {
	/**
	 * 类默认构造器
	 * 
	 */
	private MsgActionMapBuilder() {
	}

	/**
	 * 构建消息字典
	 * 
	 * @return
	 * 
	 */
	public static IMsgActionMap build() {
		return new MsgActionMap();
	}
}
