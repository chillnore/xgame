package war2.common.msg;

import java.nio.charset.Charset;

import org.json.JSONObject;

/**
 * Flash 安全策略文件
 * 
 * @author hjj2017
 *
 */
class FlashCrossdomainMsg extends AbstractExternalMsg {
	/** 消息类型 ID */
	static final int MSG_TYPE_ID = -2048;
	/** 消息体 */
	private static final byte[] MSG_BODY = "<cross-domain-policy><site-control permitted-cross-domain-policies=\"all\" /><allow-access-from domain=\"*\" to-ports=\"*\" /></cross-domain-policy>\0".getBytes(Charset.forName("utf8"));

	@Override
	public JSONObject serializeToJson() {
		return null;
	}

	@Override
	public void deserializeFromJson(JSONObject jsonObj) {
	}

	@Override
	public byte[] serializeToBytes() {
		return MSG_BODY;
	}

	@Override
	public void deserializeFromBytes(byte[] bytes) {
	}

	@Override
	public short getMsgTypeID() {
		return MSG_TYPE_ID;
	}
}
