package war2.sceneServer.kernal;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Set;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import war2.common.XgameError;
import war2.common.XgameNullArgsError;
import war2.common.action.AbstractMsgActionMap;
import war2.common.action.IMsgAction;
import war2.common.io.IoWorkService;
import war2.common.msg.AbstractExternalMsg;
import war2.common.msg.AbstractMsg;
import war2.common.msg.AbstractMsgMap;
import war2.common.msg.AsyncMsgQueueProcessor;
import war2.common.msg.MINA_MsgCodecFactory;
import war2.common.msg.MsgJsonSerializer;
import war2.common.utils.ClazzUtil;
import war2.common.utils.PackageUtil;
import war2.common.utils.PackageUtil.IClazzFilter;

/**
 * 网关服务器内核类
 * 
 * @author Haijiang
 * @since 2012/6/3
 *
 */
public class SceneKernal {
	/** client --&gt; sceneServer 消息解码器 */
	private static final String CS_MSG_CODEC = "xgame::csMsgCodec";
	/** 场景业务模块包名称 */
	private static final String SCENE_BIZ_MODULES_PACKAGE = "war2.sceneServer.bizModules";

	/** 对象实例 */
	private static volatile SceneKernal _instance = null;
	/** 消息处理器 */
	private AsyncMsgQueueProcessor _msgQueueProc = null;
	/** 消息字典 */
	private AbstractMsgMap _msgMap = AbstractMsgMap.newDefault();
	/** 消息行为字典 */
	private AbstractMsgActionMap _msgActionMap = AbstractMsgActionMap.newDefault();
	/** IO 操作服务 */
	private IoWorkService<IoWorkThreadEnum> _ioWorkServ = null;

	/**
	 * 类默认构造器
	 * 
	 */
	private SceneKernal() {
	}

	/**
	 * 获取对象实例
	 * 
	 * @return
	 * 
	 */
	public static SceneKernal theInstance() {
		if (_instance == null) {
			synchronized (SceneKernal.class) {
				if (_instance == null) {
					_instance = new SceneKernal();
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
	public AsyncMsgQueueProcessor getMsgQueueProcessor() {
		return this._msgQueueProc;
	}

	/**
	 * 获取 IO 操作服务
	 * 
	 * @return 
	 * 
	 */
	public IoWorkService<IoWorkThreadEnum> getIoWorkService() {
		return this._ioWorkServ;
	}

	/**
	 * 初始化服务器
	 * 
	 */
	public void init() {
		// 初始化场景业务模块
		this.initSceneBizModules();
		// 初始化消息队列处理器
		this.initMsgQueueProc();
		// 初始化 IO 操作服务
		this.initIoWorkService();
	}

	/**
	 * 初始化场景业务模块
	 * 
	 */
	private void initSceneBizModules() {
		// 记录启动日志
		logInfo(":: init SceneBizModules");

		// 获取当前应用目录
		String sceneBizModulesDir = this.getSceneBizModulesDir();

		// 加在场景服务器业务模块
		logInfo(sceneBizModulesDir);

		// 获取类列表
		Set<Class<?>> clazzSet = PackageUtil.listClazz(
			sceneBizModulesDir, 
			true, 
			new MyClazzFilter());

		if (clazzSet == null || 
			clazzSet.size() <= 0) {
			return;
		}

		for (Class<?> currClazz : clazzSet) {
			if (ClazzUtil.isConcreteDrivedClass(
				currClazz, 
				AbstractExternalMsg.class)) {
				// 如果是消息类, 
				// 则注册到消息字典
				@SuppressWarnings("unchecked")
				Class<? extends AbstractExternalMsg> msgClazz = (Class<AbstractExternalMsg>)currClazz;
				this.registerMsg(msgClazz);
			}

			if (ClazzUtil.isConcreteDrivedClass(
				currClazz, 
				IMsgAction.class)) {
				// 如果是行为类, 
				// 则注册到行为字典
				@SuppressWarnings("unchecked")
				Class<? extends IMsgAction<?>> msgActionClazz = (Class<IMsgAction<?>>)currClazz;
				this.registerMsgAction(msgActionClazz);
			}
		}
	}

	/**
	 * 初始化消息队列处理器
	 * 
	 */
	private void initMsgQueueProc() {
		// 记录消息队列初始化日志
		logInfo(":: init MsgQueueProcessor");
		
		// 创建消息处理器
		this._msgQueueProc = new AsyncMsgQueueProcessor(this._msgActionMap);
	}

	/**
	 * 获取场景业务模块目录
	 * 
	 * @return 
	 * 
	 */
	private String getSceneBizModulesDir() {
		// 获取用户目录
		// TODO : 需要扩展成从配置文件读取
		return System.getProperty("user.dir") + "/../SceneServer.BizModules/bin";
	}

	/**
	 * 注册消息类
	 * 
	 * @param clazz
	 * 
	 */
	private void registerMsg(Class<? extends AbstractExternalMsg> clazz) {
		if (clazz == null) {
			return;
		}

		try {
			// 创建消息实例
			AbstractExternalMsg msgObj = clazz.newInstance();
			// 添加消息到字典
			this._msgMap.putMsg(msgObj.getMsgTypeID(), msgObj);
			
			// 记录消息注册日志
			logInfo(":::: register MsgClazz : " + clazz.getName());
		} catch (Exception ex) {
			// 抛出异常
			throw new XgameError(ex);
		}
	}

	/**
	 * 注册消息行为
	 * 
	 * @param clazz
	 * 
	 */
	private void registerMsgAction(Class<? extends IMsgAction<?>> clazz) {
		if (clazz == null) {
			return;
		}

		try {
			// 创建消息行为对象
			IMsgAction<?> msgActionObj = clazz.newInstance();
			// 添加消息行为到字典
			this._msgActionMap.putMsgAction(getMsgTypeID(msgActionObj), msgActionObj);

			// 记录消息行为注册日志
			logInfo(":::: register MsgActionClazz : " + clazz.getName());
		} catch (Exception ex) {
			// 抛出异常
			throw new XgameError(ex);
		}
	}

	/**
	 * 从消息行为中获取消息类型 ID 
	 * 
	 * @param action
	 * @return
	 * 
	 */
	private static short getMsgTypeID(IMsgAction<?> action) {
		if (action == null) {
			throw new XgameNullArgsError("action");
		}

		// 获取行为类
		Class<?> actionClazz = action.getClass();
		
		// 获取 execute 方法
		Method executeMethod = ClazzUtil.getMethod(
			"execute", actionClazz);

		if (executeMethod == null) {
			// 如果找不到 execute 函数, 
			// 则直接抛出异常!
			throw new XgameError(
				"Can not find execute method in " + 
				actionClazz.getName());
		}

		// 获取第一个参数类
		@SuppressWarnings("unchecked")
		Class<? extends AbstractMsg> msgClazz = (Class<AbstractMsg>)executeMethod.getParameterTypes()[0];

		if (msgClazz == null) {
			// 如果找不到参数类型, 
			// 则直接抛出异常!
			throw new XgameError(
				"Can not find params[0] type in " + 
				actionClazz.getName() + "." + executeMethod.getName());
		}

		try {
			// 创建消息类对象
			AbstractMsg msgObj = msgClazz.newInstance();
			// 获取消息类型 ID
			return msgObj.getMsgTypeID();
		} catch (Exception ex) {
			// 抛出异常
			throw new XgameError(ex);
		}
	}

	/**
	 * 自定义类过滤器, 用于 PackageUtil.listClazz
	 * 
	 * @author hjj2019
	 * @see PackageUtil#listClazz(String, boolean, IClazzFilter)
	 *
	 */
	private static class MyClazzFilter implements IClazzFilter {
		@Override
		public boolean accept(Class<?> clazz) {
			if (clazz == null) {
				return false;
			} else {
				return clazz.getName().startsWith(SCENE_BIZ_MODULES_PACKAGE);
			}
		}
	}

	/**
	 * 初始化 IO 操作服务
	 * 
	 */
	private void initIoWorkService() {
		// 记录异步操作服务初始化日志
		logInfo(":: init IoWorkService");
		
		// 创建 IO 操作服务
		this._ioWorkServ = new IoWorkService<IoWorkThreadEnum>(
			this._msgQueueProc, 
			IoWorkThreadEnum.values());
	}

	/**
	 * 启动服务器, 开始接收消息
	 * 
	 */
	public void startUp() {
		// 开启客户端端口监听
		this.startPortListen();

		// 记录准备完成日志
		logInfo(":: ok!!");
	}

	/**
	 * 开启客户端端口监听
	 * 
	 */
	private void startPortListen() {
		// 记录异步操作服务初始化日志
		logInfo(":: start PortListen");
				
		// 创建 IO 接收器
		IoAcceptor acceptor = new NioSocketAcceptor();

		// 消息解码器工厂
		MINA_MsgCodecFactory mcf = new MINA_MsgCodecFactory(new MsgJsonSerializer(this._msgMap));

		// 添加自定义编解码器
		acceptor.getFilterChain().addLast(CS_MSG_CODEC, new ProtocolCodecFilter(mcf));

		// 获取会话配置
		IoSessionConfig cfg = acceptor.getSessionConfig();
		
		// 设置缓冲区大小
		cfg.setReadBufferSize(4096);
		// 设置 session 空闲时间
		cfg.setIdleTime(IdleStatus.BOTH_IDLE, 10);

		// 设置 IO 句柄
		acceptor.setHandler(new MINA_IoHandler(SceneKernal.theInstance().getMsgQueueProcessor()));

		try {
			// 绑定端口
			acceptor.bind(new InetSocketAddress(4400));
		} catch (Exception ex) {
			// 输出异常并停止服务器
			ex.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * 记录消息日志
	 * 
	 * @param msg
	 * 
	 */
	private static void logInfo(String msg) {
		if (msg == null || 
			msg.isEmpty()) {
			return;
		}

		KernalLogger.theInstance().logInfo(msg);
	}
}
