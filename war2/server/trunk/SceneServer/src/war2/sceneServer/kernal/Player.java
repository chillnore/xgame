package war2.sceneServer.kernal;

/**
 * 玩家
 * 
 * @author hjj2019
 *
 */
public class Player {
	/** ID */
	private String _ID;

	/**
	 * 类默认构造器
	 * 
	 */
	public Player() {
	}

	/**
	 * 获取 ID
	 * 
	 * @return
	 * 
	 */
	public String getID() {
		return this._ID;
	}

	/**
	 * 设置 ID
	 * 
	 * @param value
	 * 
	 */
	public void setID(String value) {
		this._ID = value;
	}
}
