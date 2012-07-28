package war2.sceneServer.bizModules.entry.bizService;

/**
 * 进入场景结果
 * 
 * @author hjj2017
 *
 */
public class EntryResult {
	/** 登录票据 */
	private String _ticket = null;

	/**
	 * 类默认构造器
	 * 
	 */
	public EntryResult() {
	}

	/**
	 * 获取登录票据
	 * 
	 * @return
	 */
	public String getTicket() {
		return this._ticket;
	}

	/**
	 * 设置登录票据
	 * 
	 * @param value
	 */
	public void setTicket(String value) {
		this._ticket = value;
	}
}
