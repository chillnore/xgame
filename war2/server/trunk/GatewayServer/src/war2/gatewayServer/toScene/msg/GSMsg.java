package war2.gatewayServer.toScene.msg;

import org.json.JSONObject;

import war2.common.msg.AbstractExternalMsg;

/**
 * 将网关服务器发送到场景服务器的消息
 * 
 * @author Haijiang
 *
 */
public class GSMsg extends AbstractExternalMsg {
	/** 原始 json 对象 */
	private JSONObject _originalJson;
	/** 原始消息字节 */
	private byte[] _originalBytes;

	/**
	 * 获取原始消息字节
	 * 
	 * @return 
	 * 
	 */
	public byte[] getOriginalBytes() {
		return this._originalBytes;
	}

	/**
	 * 设置原始消息字节
	 * 
	 * @param value
	 * 
	 */
	public void setOriginalBytes(byte[] value) {
		this._originalBytes = value;
	}

	@Override
	public JSONObject serializeToJson() {
		return this._originalJson;
	}

	@Override
	public void deserializeFromJson(JSONObject jsonObj) {
		this._originalJson = jsonObj;
	}

	@Override
	public byte[] serializeToBytes() {
		return this._originalBytes;
	}

	@Override
	public void deserializeFromBytes(byte[] bytes) {
		this._originalBytes = bytes;
	}
}
