package war2.sceneServer.kernal;

import war2.common.io.IIoWork;

/**
 * 场景业务服务
 * 
 * @author hjj2019
 *
 */
public class SceneBizService {
	/**
	 * 启动 IO 操作
	 * 
	 * @param work
	 * @param threadEnum 
	 * 
	 */
	protected void startIoWork(IIoWork work, IoWorkThreadEnum threadEnum) {
		if (work == null || 
			threadEnum == null) {
			return;
		}

		SceneKernal.theInstance()
			.getIoWorkService()
			.startIoWork(work, threadEnum);
	}
}
