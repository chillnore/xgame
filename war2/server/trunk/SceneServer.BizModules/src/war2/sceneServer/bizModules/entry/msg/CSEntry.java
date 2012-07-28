package war2.sceneServer.bizModules.entry.msg;

import org.json.JSONObject;

import war2.common.msg.AbstractExternalMsg;
import war2.common.msg.XgameMsgError;

/**
 * 进入场景
 * 
 * @author hjj2019
 *
 */
public class CSEntry extends AbstractExternalMsg {
	/** 消息类型 ID */
	private static final short MSG_TYPE_ID = 1001;

	/** 登录票据 */
	private String _ticket;

	@Override
	public short getMsgTypeID() {
		return MSG_TYPE_ID;
	}

	/**
	 * 获取登录票据
	 * 
	 * @return 
	 * 
	 */
	public String getTicket() {
		return this._ticket;
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
			// 获取登录票据
			this._ticket = jsonObj.getString("ticket");
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
}
