package war2.common.msg;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import war2.common.XgameNullArgsError;

/**
 * 结果编码器
 * 
 * @author AfritXia
 * @version $Rev: 17 $
 *
 */
public class GCMsgEncoder extends ProtocolEncoderAdapter {
	/** 序列化器 */
	private IMsgSerializer _serializer;

	/**
	 * 类默认构造器
	 * 
	 * @param serializer 
	 * @throws IllegalArgumentException if serializer == null 
	 * 
	 */
	public GCMsgEncoder(IMsgSerializer serializer) {
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

		IMsg msg = (IMsg)obj;
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
