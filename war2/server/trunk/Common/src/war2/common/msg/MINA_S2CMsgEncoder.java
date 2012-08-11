package war2.common.msg;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import war2.common.XgameNullArgsError;

/**
 * Server 2 Client 消息编码器
 * 
 * @author AfritXia
 * @version $Rev: 17 $
 * 
 */
class MINA_S2CMsgEncoder extends ProtocolEncoderAdapter {
	/** 序列化器 */
	private IMsgSerializer _serializer;

	/**
	 * 类默认构造器
	 * 
	 * @param serializer 
	 * @throws IllegalArgumentException if serializer == null 
	 * 
	 */
	public MINA_S2CMsgEncoder(IMsgSerializer serializer) {
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

		try {
			// 获取消息对象
			AbstractExternalMsg msg = (AbstractExternalMsg)obj;
			// 创建字节数组
			byte[] bytes = null;
			
			if (msg.getMsgTypeID() == FlashCrossdomainMsg.MSG_TYPE_ID) {
				// 如果当前消息是 Flash 安全策略文件, 
				// 直接获取消息字节数组
				bytes = msg.serializeToBytes();
			} else {
				// 通过序列化工具获取消息字节数组
				bytes = this._serializer.serialize(msg);
			}

			if (bytes == null) {
				return;
			}

			// 创建字节缓存
			IoBuffer buff = IoBuffer.allocate(bytes.length);
			
			buff.put(bytes);
			buff.flip();
	
			// 写出缓存
			output.write(buff);
		} catch (Exception ex) {
			// 记录异常日志
			logError(new XgameMsgError(ex));
		}
	}

	/**
	 * 记录异常信息
	 * 
	 * @param err 
	 * 
	 */
	private static void logError(Throwable err) {
		if (err == null) {
			return;
		}

		MsgLogger.theInstance().logError(err);
	}
}
