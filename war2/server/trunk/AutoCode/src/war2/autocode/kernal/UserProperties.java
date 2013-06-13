package war2.autocode.kernal;

import java.util.Properties;
import java.util.Set;

/**
 * 用户自定义属性
 * 
 * @author AfritXia
 * @since 2011/10/30
 * @version $Rev: 0 $
 * 
 */
public class UserProperties extends Properties {
	/** serialVersionUID */
	private static final long serialVersionUID = 5395066363309366552L;

	/**
	 * 设置键和值
	 * 
	 * @param key
	 * @param value 
	 * 
	 */
	public Object put(Object key, Object val) {
		if (key == null) {
			return null;
		}

		if (!(val instanceof String)) {
			// 如果值不是字符串类型, 
			// 则调用父类函数
			return super.put(key, val);
		}

		// 将值转换成字符串
		String strVal = (String)val;
		// 获取关键字集合
		Set<Object> keys = this.keySet();

		// 替换值中的变量, 例如:
		// basedir=/C:/Windows
		// system32dir=${basedir}/System32
		// 在设置 system32dir 键和值时, 会替换 ${basedir} 变量, 最终变成:
		// system32dir=/C:/Windows/System32
		// 
		for (Object keyIndex : keys) {
			if (!(keyIndex instanceof String)) {
				// 如果关键字不是字符串类型, 
				// 则直接跳过
				continue;
			}

			Object valIndex = this.get(keyIndex);

			if (!(valIndex instanceof String)) {
				// 如果数值不是字符串类型, 
				// 则直接跳过
				continue;
			}

			// 替换变量值
			strVal = strVal.replace("${" + keyIndex + "}", (String)valIndex);
		}

		return super.put(key, strVal);
	}
}
