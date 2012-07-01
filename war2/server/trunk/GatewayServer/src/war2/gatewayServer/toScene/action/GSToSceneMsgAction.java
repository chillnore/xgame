package war2.gatewayServer.toScene.action;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import war2.common.msg.MINA_CodecFactory;
import war2.common.msg.MsgJsonSerializer;
import war2.gatewayServer.kernal.GatewayKernal;
import war2.gatewayServer.kernal.GatewayMsgAction;
import war2.gatewayServer.kernal.Player;
import war2.gatewayServer.toScene.msg.GSToSceneMsg;

/**
 * 将消息发送给场景服务器
 * 
 * @author Haijiang
 * @since 2012/6/3
 *
 */
public class GSToSceneMsgAction extends GatewayMsgAction<GSToSceneMsg> {
	/** gateway --&gt; scene 消息解码器 */
	private static final String GS_MSG_CODEC = "xgame::GatewayServer::gsMsgCodec";
	/** IO 处理器 */
	private MINA_IoHandler _ioHandler;

	/**
	 * 类默认构造器
	 * 
	 */
	public GSToSceneMsgAction() {
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
		MINA_CodecFactory mcf = new MINA_CodecFactory(
			new MsgJsonSerializer(null));

		// 创建 IO 处理器
		this._ioHandler = new MINA_IoHandler(GatewayKernal.theInstance().getMsgQueueProcessor());
		
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
	public void execute(GSToSceneMsg cgmsg) {
		if (cgmsg == null) {
			return;
		}

		// 获取会话 ID
		long sessionID = cgmsg.getSessionID();

		if (sessionID <= 0) {
			return;
		}

		// 获取玩家对象
		Player player = this.getPlayerBySessionId(sessionID);

		if (player == null) {
			return;
		}

		// 设置玩家 ID
		cgmsg.setPlayerID(player.getID());
		// 发送消息到场景服务器
		this._ioHandler.sendMsgToScene(cgmsg);
	}
}
