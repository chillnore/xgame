package war2.common.msg;

import java.nio.charset.Charset;

import org.json.JSONObject;

import war2.common.XgameNullArgsError;

/**
 * JSON 序列化
 * 
 * @author haijiang
 * @since 2012/5/3
 *
 */
public class MsgJsonSerializer implements IMsgSerializer {
	/** MsgTypeID */
	public static final String MSG_TYPE_ID = "msgTypeID";
	/** MsgBody */
	public static final String MSG_BODY = "msgBody";
	/** 玩家 ID */
	public static final String PLAYER_ID = "playerID";

	/** 消息字典 */
	private AbstractMsgMap _msgMap;

	/**
	 * 类参数构造器
	 * 
	 * @param msgMap
	 * @throws XgameNullArgsError if msgMap == null 
	 * 
	 */
	public MsgJsonSerializer(AbstractMsgMap msgMap) {
		if (msgMap == null) {
			throw new XgameNullArgsError("msgMap");
		}

		this._msgMap = msgMap;
	}

	@Override
	public AbstractMsg deserialize(byte[] byteArray) {
		if (byteArray == null) {
			return null;
		}

		// 获取 JSON 字符串
		String jsonStr = new String(byteArray, Charset.forName("UTF-8"));

		try {
			// 创建 JSON 对象
			JSONObject jsonObj = new JSONObject(jsonStr);
			// 获取消息类型 ID
			int msgTypeID = jsonObj.getInt(MSG_TYPE_ID);

			// 获取消息
			AbstractExternalMsg msg = this._msgMap.getMsg((short)msgTypeID);

			if (msg == null) {
				// 如果消息类为空, 则直接返回!
				return null;
			}

			// 获取消息体
			jsonObj = jsonObj.getJSONObject(MSG_BODY);
			// 复制消息对象
			AbstractExternalMsg msg_copy = msg.copy();

			// 反序列化消息对象并返回
			msg_copy.deserializeFromJson(jsonObj);
			return msg_copy;
		} catch (Exception ex) {
			// 记录错误信息
			MsgLogger.getInstance().logError(new XgameMsgError(ex));
			return null;
		}
	}

	@Override
	public byte[] serialize(AbstractExternalMsg msg) {
		if (msg == null) {
			return null;
		}

		// 令消息反序列化为 JSON 对象
		JSONObject msgJson = msg.serializeToJson();

		if (msgJson == null) {
			// 如果 JSON 对象为空, 则直接返回!
			return null;
		}

		JSONObject allJson = new JSONObject();

		try {
			// 设置消息类型和消息体
			allJson.put(MSG_TYPE_ID, msg.getMsgTypeID());
			allJson.put(MSG_BODY, msgJson);

			// 获取 JSON 字符串
			String jsonStr = allJson.toString();

			return jsonStr.getBytes("UTF-8");
		} catch (Exception ex) {
			// 记录错误信息
			MsgLogger.getInstance().logError(new XgameMsgError(ex));
			return null;
		}
	}
}
