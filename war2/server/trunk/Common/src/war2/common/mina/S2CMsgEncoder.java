package war2.common.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import war2.common.XgameNullArgsError;
import war2.common.msg.AbstractExternalMsg;
import war2.common.msg.IMsgSerializer;

/**
 * Server 2 Client 消息编码器
 * 
 * @author AfritXia
 * @version $Rev: 17 $
 * 
 */
public class S2CMsgEncoder extends ProtocolEncoderAdapter {
	/** 序列化器 */
	private IMsgSerializer _serializer;

	/**
	 * 类默认构造器
	 * 
	 * @param serializer 
	 * @throws IllegalArgumentException if serializer == null 
	 * 
	 */
	public S2CMsgEncoder(IMsgSerializer serializer) {
		if (serializer == null) {
			throw new XgameNullArgsError("serializer");
		}

		this._serializer = serializer;
	}

	@Override
	public void encode(IoSession sess, Object obj, ProtocolEncoderOutput output) {
		if (obj == null || 
			output == null) {
			return;
		}

		AbstractExternalMsg msg = (AbstractExternalMsg)obj;
		// 将属性容器序列化为字节
		byte[] bytes = this._serializer.serialize(msg);

		if (bytes == null) {
			return;
		}

		// 创建字节缓存
		IoBuffer buff = IoBuffer.allocate(bytes.length);
		
		buff.put(bytes);
		buff.flip();

		// 写出缓存
		output.write(buff);
	}
}
