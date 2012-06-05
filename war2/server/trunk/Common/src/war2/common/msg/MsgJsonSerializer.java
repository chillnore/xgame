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

	/** 消息类型管理器 */
	private IMsgTypeManager _msgTypeMang;

	/**
	 * 类参数构造器
	 * 
	 * @param msgTypeMang
	 * @throws XgameNullArgsError if msgTypeMang == null 
	 * 
	 */
	public MsgJsonSerializer(IMsgTypeManager msgTypeMang) {
		if (msgTypeMang == null) {
			throw new XgameNullArgsError("msgTypeMang");
		}

		this._msgTypeMang = msgTypeMang;
	}

	@Override
	public IMsg deserialize(byte[] byteArray) {
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

			// 获取消息类
			Class<? extends IMsg> msgClazz = this._msgTypeMang.getMsgClazz((short)msgTypeID);

			if (msgClazz == null) {
				// 如果消息类为空, 则直接返回!
				return null;
			}

			// 获取消息体
			jsonObj = jsonObj.getJSONObject(MSG_BODY);
			// 创建消息对象
			IMsg msgObj = msgClazz.newInstance();

			// 反序列化消息对象并返回
			msgObj.deserializeFromJson(jsonObj);
			return msgObj;
		} catch (Exception ex) {
			// 记录错误信息
			MsgLogger.getInstance().logError(new XgameMsgError(ex));
			return null;
		}
	}

	@Override
	public byte[] serialize(IMsg msg) {
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
