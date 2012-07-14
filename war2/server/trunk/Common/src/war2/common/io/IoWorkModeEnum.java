package war2.common.io;

/**
 * IO 工作模式枚举
 * 
 * @author haijiang
 *
 */
public enum IoWorkModeEnum {
	/** 同步模式 */
	sync(0, "sync"),
	/** 异步模式 */
	async(1, "async")
;
	/** 整型数值 */
	private int _intVal;
	/** 字符串值 */
	private String _strVal;

	/**
	 * 枚举参数构造器
	 * 
	 * @param intVal
	 * @param strVal
	 */
	IoWorkModeEnum(int intVal, String strVal) {
		this._intVal = intVal;
		this._strVal = strVal;
	}

	/**
	 * 获取 int 数值
	 * 
	 * @return
	 */
	public int getIntVal() {
		return this._intVal;
	}

	/**
	 * 获取字符串值
	 * 
	 * @return
	 */
	public String getStrVal() {
		return this._strVal;
	}
}
