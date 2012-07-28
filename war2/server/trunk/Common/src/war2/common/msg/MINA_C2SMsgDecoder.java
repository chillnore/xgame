package war2.common.msg;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import war2.common.XgameNullArgsError;

/**
 * Client 2 Server 消息解码器
 * 
 * @author AfritXia
 * @since 2012/1/24
 * @version $Rev: 17 $
 *
 */
class MINA_C2SMsgDecoder extends ProtocolDecoderAdapter {
	/** 序列化器 */
	private IMsgSerializer _serializer;
	
	/**
	 * 类参数构造器
	 * 
	 * @param serializer 序列化器
	 * @throws IllegalArgumentException if serializer == null
	 * 
	 */
	public MINA_C2SMsgDecoder(IMsgSerializer serializer) {
		if (serializer == null) {
			throw new XgameNullArgsError("serializer");
		}

		this._serializer = serializer;
	}
	
	@Override
	public void decode(IoSession session, IoBuffer buff, ProtocolDecoderOutput output) {
		if (buff == null || 
			output == null) {
			return;
		}

		// 创建字节数组
		byte[] byteArray = buff.array();

		// 将字节反序列化为消息
		AbstractMsg msg = this._serializer.deserialize(byteArray);

		if (msg == null) {
			return;
		}

		// 设置会话 ID
		msg.setSessionID(session.getId());
		
		// 清除缓存
		buff.limit(0);
		buff.free();

		// 写出消息对象
		output.write(msg);
	}
}
