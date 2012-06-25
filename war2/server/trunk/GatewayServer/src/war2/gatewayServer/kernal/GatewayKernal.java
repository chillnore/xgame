package war2.gatewayServer.kernal;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import war2.common.mina.MinaCodecFactory;
import war2.common.msg.MsgJsonSerializer;
import war2.common.msg.MsgQueueProcessor;

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

	/** 对象实例 */
	private static volatile GatewayKernal _instance;
	/** 消息处理器 */
	private MsgQueueProcessor _msgQueueProc;

	/**
	 * 类默认构造器
	 * 
	 */
	private GatewayKernal() {
	}

	/**
	 * 获取对象实例
	 * 
	 * @return
	 * 
	 */
	public static GatewayKernal theInstance() {
		if (_instance == null) {
			synchronized (GatewayKernal.class) {
				if (_instance == null) {
					_instance = new GatewayKernal();
				}
			}
		}

		return _instance;
	}

	/**
	 * 获取消息队列处理器
	 * 
	 * @return 
	 * 
	 */
	public MsgQueueProcessor getMsgQueueProcessor() {
		return this._msgQueueProc;
	}

	/**
	 * 初始化服务器
	 * 
	 */
	public void init() {
		// 创建消息处理器
		this._msgQueueProc = new MsgQueueProcessor(MSG_PROC_NAME, null);
	}

	/**
	 * 启动服务器, 开始接收消息
	 * 
	 */
	public void startUp() {
		// 开启客户端端口监听
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
		MinaCodecFactory mcf = new MinaCodecFactory(
			new MsgJsonSerializer(null));

		// 添加自定义编解码器
		acceptor.getFilterChain().addLast(CG_MSG_CODEC, new ProtocolCodecFilter(mcf));

		// 获取会话配置
		IoSessionConfig cfg = acceptor.getSessionConfig();
		
		// 设置缓冲区大小
		cfg.setReadBufferSize(2048);
		// 设置 session 空闲时间
		cfg.setIdleTime(IdleStatus.BOTH_IDLE, 10);

		// 设置 IO 句柄
		acceptor.setHandler(new GatewayIoHandler(GatewayKernal.theInstance().getMsgQueueProcessor()));

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
