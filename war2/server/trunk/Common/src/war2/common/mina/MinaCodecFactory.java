package war2.common.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import war2.common.XgameNullArgsError;
import war2.common.msg.IMsgSerializer;

/**
 * 消息编解码器工厂类
 * 
 * @author AfritXia
 * @since 2012/1/24
 * 
 */
public class MinaCodecFactory implements ProtocolCodecFactory {
	/** 序列化器 */
	private IMsgSerializer _serializer;

	/**
	 * 类参数构造器
	 * 
	 * @param serializer
	 * @throws XgameNullArgsError if serializer == null 
	 * 
	 */
	public MinaCodecFactory(IMsgSerializer serializer) {
		if (serializer == null) {
			throw new XgameNullArgsError("serializer");
		}

		this._serializer = serializer;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession sess) {
		return new C2SMsgDecoder(this._serializer);
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession sess) {
		return new S2CMsgEncoder(this._serializer);
	}
}
