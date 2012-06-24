package war2.gatewayServer.toScene.action;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import war2.common.mina.MinaCodecFactory;
import war2.common.msg.MsgJsonSerializer;
import war2.gatewayServer.kernal.GatewayKernal;
import war2.gatewayServer.kernal.GatewayMsgAction;
import war2.gatewayServer.toScene.msg.CGToSceneMsg;

/**
 * 将消息发送给场景服务器
 * 
 * @author Haijiang
 * @since 2012/6/3
 *
 */
public class CGToSceneMsgAction extends GatewayMsgAction<CGToSceneMsg> {
	/** gateway --&gt; scene 消息解码器 */
	private static final String GS_MSG_CODEC = "xgame::GatewayServer::gsMsgCodec";
	/** IO 处理器 */
	private CGToSceneIoHandler _ioHandler;

	/**
	 * 类默认构造器
	 * 
	 */
	public CGToSceneMsgAction() {
		// 连接到场景服务器
		this.connectSceneServer();
	}

	/**
	 * 连接到场景服务器
	 * 
	 */
	private void connectSceneServer() {
		// 创建 TCP/IP 连接  
		NioSocketConnector conn = new NioSocketConnector();

		// 消息解码器工厂
		MinaCodecFactory mcf = new MinaCodecFactory(
			new MsgJsonSerializer(null));

		// 创建 IO 处理器
		this._ioHandler = new CGToSceneIoHandler(GatewayKernal.theKernal().getMsgQueueProcessor());
		
		// 添加消息解码器
		conn.getFilterChain().addLast(GS_MSG_CODEC, new ProtocolCodecFilter(mcf));
		// 服务器的消息处理器
		conn.setHandler(this._ioHandler);

		//连接到服务器
		ConnectFuture cf = conn.connect(new InetSocketAddress("127.0.0.1", 4401));

		// 进入等待循环
		cf.awaitUninterruptibly();
		cf.getSession().getCloseFuture().awaitUninterruptibly();

		// 释放连接
		conn.dispose();
	}

	@Override
	public void execute(CGToSceneMsg cgmsg) {
		if (cgmsg == null) {
			return;
		} else {
			this._ioHandler.sendMsg2Scene(cgmsg);
		}
	}
}
