package war2.common.msg;

import org.json.JSONObject;

/**
 * 消息接口
 * 
 * @author haijiang
 * @since 2012/6/3
 *
 */
public interface IMsg {
	/**
	 * 获取消息类型 ID
	 * 
	 * @return
	 */
	short getMsgTypeID();
	
	/**
	 * 执行消息
	 * 
	 */
	void execute();

	/**
	 * 序列化为 JSON 对象
	 * 
	 * @return
	 */
	JSONObject serializeToJson();

	/**
	 * 从 JSON 对象反序列化
	 * 
	 * @param jsonObj
	 */
	void deserializeFromJson(JSONObject jsonObj);

	/**
	 * 序列化为字节数组
	 * 
	 * @return
	 */
	byte[] serializeToBytes();

	/**
	 * 从字节数组反序列化
	 * 
	 * @param bytes
	 */
	void deserializeFromBytes(byte[] bytes);
}
