package war2.gatewayServer.msg;

import war2.common.msg.IMsg;
import war2.common.msg.IMsgTypeManager;

/**
 * 返回通用消息
 * 
 * @author AfritXia
 * 
 */
public class CommonMsgTypeManager implements IMsgTypeManager {
	@Override
	public Class<? extends IMsg> getMsgClazz(short msgTypeID) {
		return CGCommonMsg.class;
	}

}
