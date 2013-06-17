package war2.autocode.impl.excel.model;

/**
 * 多语言注解
 * 
 * @author AfritXia
 * @version $Rev: 0 $
 * @since 2011/12/17
 *
 */
public class MultiLangAnnotation {
	/** 符号 */
	private String _symbol;
	/** 分组名称 */
	private String _groupName;

	/**
	 * 类参数构造器
	 * 
	 * @param symbol
	 * @param groupName
	 * @throws IllegalArgumentException if symbol == null or symbol is empty
	 */
	public MultiLangAnnotation(String symbol, String groupName) {
		if (symbol == null || 
			symbol.isEmpty()) {
			throw new IllegalArgumentException("symbol is null");
		}

		this._symbol = this.checkSymbol(symbol);
		this._groupName = groupName;
	}

	/**
	 * 类参数构造器
	 * 
	 * @param s
	 * @throws IllegalArgumentException if symbol == null or symbol is empty
	 */
	public MultiLangAnnotation(String s) {
		if (s == null || 
			s.isEmpty()) {
			throw new IllegalArgumentException("s is null");
		}

		String[] params = s.split(",");

		if (params.length < 2) {
			// 必须有 "," 分割字符串
			throw new IllegalArgumentException("s format error");
		}

		this._symbol = this.checkSymbol(params[0].trim());
		this._groupName = params[1].trim();
	}

	/**
	 * 检查符号有效性
	 * 
	 * @param symbol
	 * @return 
	 * @throws IllegalArgumentException if symbol == null or symbol is empty
	 * @throws IllegalArgumentException if symbol != key and symbol != val
	 */
	private String checkSymbol(String symbol) {
		if (symbol == null || 
			symbol.isEmpty()) {
			throw new IllegalArgumentException("symbol is null");
		}

		if (symbol.equals("key")) {
			return symbol;
		}

		if (symbol.equals("val")) {
			return symbol;
		}

		throw new IllegalArgumentException("symbol is not key or val");
	}

	/**
	 * 获取符号
	 * 
	 * @return
	 */
	public String getSymbol() {
		return this._symbol;
	}

	/**
	 * 获取分组名称
	 * 
	 * @return
	 */
	public String getGroupName() {
		return this._groupName;
	}
}
