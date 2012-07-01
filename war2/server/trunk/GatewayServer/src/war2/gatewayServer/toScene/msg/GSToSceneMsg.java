package war2.gatewayServer.toScene.msg;

import org.json.JSONException;
import org.json.JSONObject;

import war2.common.msg.AbstractExternalMsg;
import war2.common.msg.MsgJsonSerializer;

/**
 * 将网关服务器发送到场景服务器的消息
 * 
 * @author Haijiang
 *
 */
public class GSToSceneMsg extends AbstractExternalMsg {
	/** 消息 ID */
	private static final short MSG_TYPE_ID = -101;

	/** 原始消息体 */
	private JSONObject _origMsgBodyJson;
	/** 原始消息体字节 */
	private byte[] _origMsgBodyBytes;
	/** 玩家 ID */
	private String _playerID;

	@Override
	public short getMsgTypeID() {
		return MSG_TYPE_ID;
	}

	/**
	 * 获取玩家 ID
	 * 
	 * @return 
	 * 
	 */
	public String getPlayerID() {
		return this._playerID;
	}

	/**
	 * 设置玩家 ID
	 * 
	 * @param value
	 * 
	 */
	public void setPlayerID(String value) {
		this._playerID = value;
	}

	@Override
	public JSONObject serializeToJson() {
		JSONObject jsonObj = new JSONObject();
		
		try {
			// 消息类型 ID
			jsonObj.put(MsgJsonSerializer.MSG_TYPE_ID, this.getMsgTypeID());

			this._origMsgBodyJson.put("PlayerID", this.getPlayerID());
			// 消息体
			jsonObj.put(MsgJsonSerializer.MSG_BODY, this._origMsgBodyJson);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}

		return jsonObj;
	}

	@Override
	public void deserializeFromJson(JSONObject jsonObj) {
		this._origMsgBodyJson = jsonObj;
	}

	@Override
	public byte[] serializeToBytes() {
		return this._origMsgBodyBytes;
	}

	@Override
	public void deserializeFromBytes(byte[] bytes) {
		this._origMsgBodyBytes = bytes;
	}
}
