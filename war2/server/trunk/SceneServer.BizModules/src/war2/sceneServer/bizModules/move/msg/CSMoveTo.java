package war2.sceneServer.bizModules.move.msg;

import org.json.JSONObject;

import war2.common.msg.AbstractExternalMsg;
import war2.common.msg.XgameMsgError;

/**
 * 移动消息
 * 
 * @author hjj2017
 *
 */
public class CSMoveTo extends AbstractExternalMsg {
	/** 消息类型 ID */
	private static final short MSG_TYPE_ID = 1002;

	/** 目标位置 X */
	private int _x = -1;
	/** 目标位置 Y */
	private int _y = -1;

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
		return null;
	}

	@Override
	public void deserializeFromJson(JSONObject jsonObj) {
		if (jsonObj == null) {
			return;
		}

		try {
			this._x = jsonObj.getInt("x");
			this._y = jsonObj.getInt("y");
		} catch (Exception ex) {
			throw new XgameMsgError(ex);
		}
	}

	@Override
	public byte[] serializeToBytes() {
		return null;
	}

	@Override
	public void deserializeFromBytes(byte[] bytes) {
	}

	@Override
	public short getMsgTypeID() {
		return MSG_TYPE_ID;
	}
}
