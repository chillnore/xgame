package war2.gatewayServer;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import war2.common.msg.MsgCodecFactory;
import war2.common.msg.MsgIoHandler;
import war2.common.msg.MsgJsonSerializer;
import war2.common.msg.MsgQueueProcessor;
import war2.gatewayServer.msg.CommonMsgTypeManager;
import war2.gatewayServer.msg.GLMsgIoHandler;

/**
 * 网关服务器内核类
 * 
 * @author Haijiang
 * @since 2012/6/3
 *
 */
public class GatewayKernal {
	/** 消息处理器名称 */
	private static final String MSG_PROC_NAME = "xgame::GatewayServer::msgProc";
	/** client --&gt; gateway 消息解码器 */
	private static final String CG_MSG_CODEC = "xgame::GatewayServer::cgMsgCodec";
	/** gateway --&gt; landing 消息解码器 */
	private static final String GL_MSG_CODEC = "xgame::GatewayServer::glMsgCodec";

	/** 消息处理器 */
	private MsgQueueProcessor _msgQueueProc;
	public static GLMsgIoHandler _glMsgIoHandler;

	/**
	 * 类默认构造器
	 * 
	 */
	public GatewayKernal() {
		// 创建消息处理器
		this._msgQueueProc = new MsgQueueProcessor(MSG_PROC_NAME);
		_glMsgIoHandler = new GLMsgIoHandler(this._msgQueueProc);
	}

	/**
	 * 启动服务器, 开始接收消息
	 * 
	 */
	public void startUp() {
		// 连接到登陆服务器
		this.connectLandingServer();
		// 开启客户端端口监听
		this.startPortListen();
	}

	/**
	 * 连接到登录服务器
	 * 
	 */
	private void connectLandingServer() {
		// 创建 TCP/IP 连接  
		NioSocketConnector conn = new NioSocketConnector();

		// 消息解码器工厂
		MsgCodecFactory mcf = new MsgCodecFactory(
			new MsgJsonSerializer(null));
  
		// 添加消息解码器
		conn.getFilterChain().addLast(GL_MSG_CODEC, new ProtocolCodecFilter(mcf));
		// 服务器的消息处理器
		conn.setHandler(_glMsgIoHandler);

		//连接到服务器：  
		ConnectFuture cf = conn.connect(new InetSocketAddress("localhost", 4401));
		// Wait for the connection attempt to be finished.  
		cf.awaitUninterruptibly();
		cf.getSession().getCloseFuture().awaitUninterruptibly();
		conn.dispose();
	}

	/**
	 * 开启客户端端口监听
	 * 
	 */
	private void startPortListen() {
		// 创建 IO 接收器
		IoAcceptor acceptor = new NioSocketAcceptor();

		// 消息解码器工厂
		MsgCodecFactory mcf = new MsgCodecFactory(
			new MsgJsonSerializer(
			new CommonMsgTypeManager()));

		// 添加自定义编解码器
		acceptor.getFilterChain().addLast(CG_MSG_CODEC, new ProtocolCodecFilter(mcf));

		// 获取会话配置
		IoSessionConfig cfg = acceptor.getSessionConfig();
		
		// 设置缓冲区大小
		cfg.setReadBufferSize(2048);
		// 设置 session 空闲时间
		cfg.setIdleTime(IdleStatus.BOTH_IDLE, 10);

		// 设置 IO 句柄
		acceptor.setHandler(new MsgIoHandler(this._msgQueueProc));

		try {
			// 绑定端口
			acceptor.bind(new InetSocketAddress(4400));
		} catch (Exception ex) {
			// 输出异常并停止服务器
			ex.printStackTrace();
			System.exit(-1);
		}
	}
}
