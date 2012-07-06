package war2.sceneServer.kernal;

import war2.common.msg.IMsgActionMap;
import war2.common.msg.IMsgMap;

/**
 * 游戏模块
 * 
 * @author hjj2017
 * 
 */
public interface IGameModule {
	/**
	 * 设置消息字典
	 * 
	 * @param msgMap
	 * @param msgActionMap
	 * 
	 */
	void putMsgs(IMsgMap msgMap, IMsgActionMap msgActionMap);

	void putExcelTemplate();

	void putDB();
}
