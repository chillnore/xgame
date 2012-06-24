package war2.gatewayServer;

import war2.gatewayServer.kernal.GatewayKernal;

/**
 * 主应用程序类
 * 
 * @author Haijiang
 * @since 2012/6/3
 *
 */
public class GatewayApp {
	/**
	 * 应用程序主函数
	 * 
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		// 创建内核程序
		GatewayKernal k = new GatewayKernal();
		// 启动网关服务器
		k.startUp();
	}
}
