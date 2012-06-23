package war2.gatewayServer.msg;

import war2.common.msg.AbstractExternalMsg;
import war2.common.msg.IMsgMap;

/**
 * 返回通用消息
 * 
 * @author AfritXia
 * 
 */
public class CommonMsgTypeManager implements IMsgMap {
	@Override
	public Class<? extends AbstractExternalMsg> getMsgClazz(short msgTypeID) {
		return CGCommonMsg.class;
	}

	@Override
	public void putMsgClazz(
		short msgTypeID,
		Class<? extends AbstractExternalMsg> clazz) {
	}
}
