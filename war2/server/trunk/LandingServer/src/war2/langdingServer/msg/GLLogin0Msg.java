package war2.langdingServer.msg;

import org.json.JSONObject;

import war2.common.msg.IMsg;
import war2.langdingServer.handler.LoginMsgHandler;

/**
 * 登陆消息
 * 
 * @author Haijiang
 * @since 2012/6/3
 *
 */
public class GLLogin0Msg implements IMsg {
	private String _userName;
//	private String _userPass;

	public String getUserName() {
		return this._userName;
	}

	public void setUserName(String value) {
		this._userName = value;
	}

	@Override
	public short getMsgTypeID() {
		return 1001;
	}

	@Override
	public void execute() {
		LoginMsgHandler.theInstance().handleLogin0Msg(this);
	}

	@Override
	public JSONObject serializeToJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deserializeFromJson(JSONObject jsonObj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] serializeToBytes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deserializeFromBytes(byte[] bytes) {
		// TODO Auto-generated method stub
		
	}

}
