package war2.common.msg;

import java.io.IOException;

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
		byte[] byteArray = new byte[buff.capacity()];

		try {
			// 将缓存数据读入字节数组
			buff.asInputStream().read(byteArray);
		} catch (IOException ex) {
			// 记录异常信息
			MsgLogger.getInstance().logError(new XgameMsgError(ex));
			return;
		}

		AbstractMsg msg = this._serializer.deserialize(byteArray);

		if (msg == null) {
			return;
		}

		// 设置会话 ID
		msg.setSessionID(session.getId());
		
		// 清除缓存
		buff.position(buff.limit());
		buff.free();

		// 写出消息对象
		output.write(msg);
	}
}
