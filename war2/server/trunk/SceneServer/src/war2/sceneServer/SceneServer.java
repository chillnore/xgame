package war2.sceneServer;

import war2.sceneServer.kernal.SceneKernal;

/**
 * 场景服务器
 * 
 * @author hjj2017
 *
 */
public class SceneServer {
	/**
	 * 应用程序主函数
	 * 
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		System.out.println("War2 SceneServer");
		System.out.println("+-------\n");

		// 创建内核程序
		SceneKernal k = SceneKernal.theInstance();

		// 初始化并启动网关服务器
		k.init();
		k.startUp();
	}
}
