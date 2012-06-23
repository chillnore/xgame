package war2.common.msg;

import org.json.JSONObject;

/**
 * 外部消息
 * 
 * @author hjj2017
 *
 */
public abstract class AbstractExternalMsg extends AbstractMsg {
	/**
	 * 序列化为 JSON 对象
	 * 
	 * @return
	 */
	public abstract JSONObject serializeToJson();

	/**
	 * 从 JSON 对象反序列化
	 * 
	 * @param jsonObj
	 */
	public abstract void deserializeFromJson(JSONObject jsonObj);

	/**
	 * 序列化为字节数组
	 * 
	 * @return
	 */
	public abstract byte[] serializeToBytes();

	/**
	 * 从字节数组反序列化
	 * 
	 * @param bytes
	 */
	public abstract void deserializeFromBytes(byte[] bytes);
}
