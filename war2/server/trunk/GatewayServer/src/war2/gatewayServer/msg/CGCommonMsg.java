package war2.gatewayServer.msg;

import org.json.JSONObject;

import war2.common.msg.IMsg;
import war2.gatewayServer.handler.CGMsgHandler;

/**
 * 客户端通用消息
 * 
 * @author Haijiang
 *
 */
public class CGCommonMsg implements IMsg {
	/** 消息 ID */
	private short _typeID;
	/** 原始 json 对象 */
	private JSONObject _originalJson;
	/** 原始消息字节 */
	private byte[] _originalBytes;

	@Override
	public short getMsgTypeID() {
		return this._typeID;
	}

	/**
	 * 设置消息 ID
	 * 
	 * @param value
	 * 
	 */
	public void setMsgTypeID(short value) {
		this._typeID = value;
	}

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
	public void execute() {
		CGMsgHandler.theInstance().handleClientCommonMsg(this);
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
