package war2.common.msg;

import java.nio.charset.Charset;

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
	/** 会话是否已经验证 */
	private static final String SESSION_IS_VALID = "xgame::session::isValid";
	/** 安全策略文件请求 */
	private static final String POLICY_FILE_REQUEST = "<policy-file-request/>";
	/** 序列化器 */
	private IMsgSerializer _serializer = null;
	
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

		if (this.isUnvalidSession(session) && 
			this.isFlashPolicyFileRequest(byteArray)) {
			// 
			// 如果 Session 尚未验证, 
			// 并且当前消息是安全策略文件请求, 
			// 则直接发送 Crossdomain 消息!
			// 
			session.write(new FlashCrossdomainMsg());
			// 设置验证标志到会话
			this.putValidFlagToSession(session);
		} else {
			// 通过序列化工具, 
			// 将字节反序列化为消息!
			AbstractMsg msg = this._serializer.deserialize(byteArray);
	
			if (msg == null) {
				return;
			}

			// 设置会话 ID
			msg.setSessionID(session.getId());
			// 写出消息对象
			output.write(msg);
		}

		// 清除缓存
		buff.limit(0);
		buff.free();
	}

	/**
	 * 会话是否还没有验证 ?
	 * 
	 * @param sess
	 * @return 
	 * 
	 */
	private boolean isUnvalidSession(IoSession sess) {
		if (sess == null) {
			return false;
		}

		// 获取验证标志
		Boolean isValid = (Boolean)sess.getAttribute(SESSION_IS_VALID);
		// 是否已经验证 ?
		return (isValid == null || !isValid);
	}

	/**
	 * 设置验证标志到会话
	 * 
	 * @param sess 
	 * 
	 */
	private void putValidFlagToSession(IoSession sess) {
		if (sess == null) {
			return;
		}

		sess.setAttribute(SESSION_IS_VALID, true);
	}

	/**
	 * 是否为 Flash 安全策略文件请求 ?
	 * 
	 * @param byteArray
	 * @return 
	 * 
	 */
	private boolean isFlashPolicyFileRequest(byte[] byteArray) {
		if (byteArray == null || 
			byteArray.length <= 0) {
			return false;
		}

		String s = null;

		// 将字节数组转化为字符串
		s = new String(byteArray, Charset.forName("utf8"));
		s = s.trim();

		return s.equals(POLICY_FILE_REQUEST);
	}
}
