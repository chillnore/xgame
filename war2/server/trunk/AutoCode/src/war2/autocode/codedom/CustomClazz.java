package war2.autocode.codedom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义类
 * 
 * @author AfritXia
 * @since 2011/06/18
 * @version $Rev: 31 $
 * 
 */
public class CustomClazz<TField extends Field> {
	/** 包名称 */
	private String _packageName;
	/** 包引用集合 */
	private List<String> _importList;
	/** 类名称 */
	private String _name;
	/** 字段列表 */
	private List<TField> _fieldList;
	/** 代码 */
	private String _code;

	/**
	 * 获取包名称
	 * 
	 * @return
	 */
	public String getPackageName() {
		return this._packageName;
	}

	/**
	 * 设置包名称
	 * 
	 * @param value
	 */
	public void setPackageName(String value) {
		this._packageName = value;
	}

	/**
	 * 获取包引用集合
	 * 
	 * @return
	 */
	public List<String> getImportList() {
		return this._importList;
	}

	/**
	 * 获取已排序的引用包集合
	 * 
	 * @return
	 */
	public List<String> getSortedImportList() {
		if (this._importList == null) {
			return null;
		}

		// 获取 import 字符串数组
		String[] importArray = this._importList.toArray(new String[0]);
		// 排序
		Arrays.sort(importArray);

		List<String> resultList = new ArrayList<String>();
		
		for (String $import : importArray) {
			// 添加到集合
			resultList.add($import);
		}

		return resultList;
	}

	/**
	 * 设置包引用集合
	 * 
	 * @param value 
	 */
	public void setImportList(List<String> value) {
		this._importList = value;
	}

	/**
	 * 添加包引用集合
	 * 
	 * @param value 
	 */
	public void addImport(String value) {
		if (value == null || 
			value.isEmpty()) {
			return;
		}

		if (this._importList == null) {
			this._importList = new ArrayList<String>();
		}

		if (this._importList.contains(value)) {
			return;
		} else {
			this._importList.add(value);
		}
	}

	/**
	 * 清除包引用集合
	 * 
	 */
	public void clearImportList() {
		if (this._importList != null) {
			this._importList.clear();
		}
	}

	/**
	 * 获取类名称
	 * 
	 * @return
	 */
	public String getName() {
		return this._name;
	}

	/**
	 * 设置类名称
	 * 
	 * @param value
	 */
	public void setName(String value) {
		this._name = value;
	}

	/**
	 * 获取字段列表
	 * 
	 * @return
	 */
	public List<TField> getFieldList() {
		return this._fieldList;
	}

	/**
	 * 设置字段列表
	 * 
	 * @param value
	 */
	public void setFieldList(List<TField> value) {
		this._fieldList = value;
	}

	/**
	 * 增加字段列表
	 * 
	 * @param value
	 */
	public void addField(TField value) {
		if (value == null) {
			return;
		}

		if (this._fieldList == null) {
			this._fieldList = new ArrayList<TField>();
		}

		this._fieldList.add(value);
	}

	/**
	 * 清除字段列表
	 * 
	 */
	public void clearFieldList() {
		if (this._fieldList != null) {
			this._fieldList.clear();
		}
	}

	/**
	 * 获取代码
	 * 
	 * @return
	 */
	public String getCode() {
		return this._code;
	}

	/**
	 * 设置代码
	 * 
	 * @param value
	 */
	public void setCode(String value) {
		this._code = value;
	}
}
