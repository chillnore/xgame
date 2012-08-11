package war2.sceneServer.bizModules.move.msg;

import org.json.JSONObject;

import war2.common.msg.AbstractExternalMsg;
import war2.common.msg.XgameMsgError;
import war2.common.msg.buffer.MsgBuffer;

/**
 * 移动
 * 
 * @author hjj2017
 *
 */
public class SCMoveTo extends AbstractExternalMsg {
	/** 消息类型 ID */
	private static final short MSG_TYPE_ID = 1003;

	/** 玩家 ID */
	private String _playerID;
	/** 目标位置 X */
	private int _x = -1;
	/** 目标位置 Y */
	private int _y = -1;

	/**
	 * 获取玩家 ID
	 * 
	 * @return
	 */
	public String getPlayerID() {
		return this._playerID;
	}

	/**
	 * 设置玩家 ID 
	 * 
	 * @param value
	 */
	public void setPlayerID(String value) {
		this._playerID = value;
	}

	/**
	 * 获取目标位置 X
	 * 
	 * @return
	 */
	public int getX() {
		return this._x;
	}

	/**
	 * 设置目标位置 X
	 * 
	 * @param value 
	 * 
	 */
	public void setX(int value) {
		this._x = value;
	}

	/**
	 * 获取目标位置 Y
	 * 
	 * @return
	 */
	public int getY() {
		return this._y;
	}

	/**
	 * 设置目标位置 Y
	 * 
	 * @param value 
	 * 
	 */
	public void setY(int value) {
		this._y = value;
	}

	@Override
	public JSONObject serializeToJson() {
		try {
			JSONObject jsonObj = new JSONObject();

			jsonObj.put("playerID", this._playerID);
			jsonObj.put("x", this._x);
			jsonObj.put("y", this._y);
	
			return jsonObj;
		} catch (Exception ex) {
			throw new XgameMsgError(ex);
		}
	}

	@Override
	public void deserializeFromJson(JSONObject jsonObj) {
		if (jsonObj == null) {
			return;
		}

		try {
			this._playerID = jsonObj.getString("playerID");
			this._x = jsonObj.getInt("x");
			this._y = jsonObj.getInt("y");
		} catch (Exception ex) {
			throw new XgameMsgError(ex);
		}
	}

	@Override
	public byte[] serializeToBytes() {
		MsgBuffer mb = MsgBuffer.allocate(2048);

//		mb.putString(this._playerID, null);
		mb.putInt(this._x);
		mb.putInt(this._y);
		mb.flip();

		return mb.array();
	}

	@Override
	public void deserializeFromBytes(byte[] bytes) {
		if (bytes == null || 
			bytes.length <= 0) {
			return;
		}

		MsgBuffer mb = MsgBuffer.wrap(bytes);
		this._x = mb.getInt();
		this._y = mb.getInt();
	}

	@Override
	public short getMsgTypeID() {
		return MSG_TYPE_ID;
	}
}
