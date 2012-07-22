package war2.sceneServer.kernal;

/**
 * IO 操作线程枚举
 * 
 * @author hjj2019
 *
 */
public enum IoWorkThreadEnum {
	/** 登陆 */
	login, 
	/** 数据库操作 */
	db,
	/** 第三方接口 */
	thirdPartyInterface,
;
}
