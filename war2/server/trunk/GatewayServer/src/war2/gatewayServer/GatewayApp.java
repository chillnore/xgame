package war2.gatewayServer;

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
		GatewayKernal k = new GatewayKernal();
		k.startUp();
	}
}
