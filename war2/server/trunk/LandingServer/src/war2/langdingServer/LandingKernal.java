package war2.langdingServer;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import war2.common.msg.MsgCodecFactory;
import war2.common.msg.MsgIoHandler;
import war2.common.msg.MsgJsonSerializer;
import war2.common.msg.MsgTypeManager;
import war2.langdingServer.msg.GLLogin0Msg;
import war2.langdingServer.msg.LandingMsgProcessor;

public class LandingKernal {
	/** 编码/解码器名称 */
	private static final String MSG_CODEC = "xgame::LandingServer::msgCodec";

	/** 消息类型管理器 */
	private MsgTypeManager _msgTypeMang;
	/** 消息处理器 */
	private LandingMsgProcessor _msgProcessor;

	/**
	 * 类默认构造器
	 * 
	 */
	public LandingKernal() {
		this._msgTypeMang = new MsgTypeManager();
		this._msgProcessor = new LandingMsgProcessor();
	}

	/**
	 * 启动服务器, 开始接收消息
	 * 
	 */
	public void startUp() {
		this._msgTypeMang.register((short)1001, GLLogin0Msg.class);
		// 开启端口监听
		this.startPortListen();
	}

	/**
	 * 开启客户端端口监听
	 * 
	 */
	private void startPortListen() {
		// 创建 IO 接收器
		IoAcceptor acceptor = new NioSocketAcceptor();

		// 消息解码器工厂
		MsgCodecFactory mcf = new MsgCodecFactory(new MsgJsonSerializer(this._msgTypeMang));
		// 添加自定义编解码器
		acceptor.getFilterChain().addLast(MSG_CODEC, new ProtocolCodecFilter(mcf));
		// 设置缓冲区大小
		acceptor.getSessionConfig().setReadBufferSize(2048);
		// 设置 session 空闲时间
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		// 设置 IO 句柄
		acceptor.setHandler(new MsgIoHandler(this._msgProcessor));

		try {
			// 绑定端口
			acceptor.bind(new InetSocketAddress(4401));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
	}
}
