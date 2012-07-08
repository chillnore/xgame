package war2.sceneServer.bizModules.entry.msg;

import org.json.JSONObject;

import war2.common.msg.AbstractExternalMsg;

/**
 * 进入场景
 * 
 * @author hjj2019
 *
 */
public class CSEntryScene extends AbstractExternalMsg {
	/** 消息类型 ID */
	private static final short MSG_TYPE_ID = 100;

	@Override
	public short getMsgTypeID() {
		return MSG_TYPE_ID;
	}

	@Override
	public JSONObject serializeToJson() {
		return null;
	}

	@Override
	public void deserializeFromJson(JSONObject jsonObj) {
	}

	@Override
	public byte[] serializeToBytes() {
		return null;
	}

	@Override
	public void deserializeFromBytes(byte[] bytes) {
	}
}
