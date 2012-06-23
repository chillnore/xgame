package war2.common.msg;

/**
 * 消息序列化接口
 * 
 * @author haijiang
 * @since 2012/6/3
 *
 */
public interface IMsgSerializer {
	/**
	 * 将字节数组反序列号成消息对象
	 * 
	 * @param bytes
	 * @return
	 * 
	 */
	AbstractMsg deserialize(byte[] bytes);

	/**
	 * 将消息对象序列化为字节数组
	 * 
	 * @param msg
	 * @return 
	 * 
	 */
	byte[] serialize(AbstractExternalMsg msg);
}
