package war2.common.utils;

/**
 * 类实用工具
 * 
 * @author hjj2019
 *
 */
public class ClazzUtil {
	/**
	 * 类默认构造器
	 * 
	 */
	private ClazzUtil() {
	}

	/**
	 * 是否为派生类
	 * 
	 * @param currClazz
	 * @param superClazz
	 * @return 
	 * 
	 */
	public static boolean isDrivedClass(Class<?> currClazz, Class<?> superClazz) {
		if (currClazz == null || 
			superClazz == null) {
			return false;
		}

		// 获取父类
		Class<?> parentClazz = currClazz.getSuperclass();

		while (parentClazz != null) {
			if (parentClazz.equals(superClazz)) {
				// 如果父类与参数中给定的超类相同, 
				// 那么返回 true
				return true;
			} else {
				// 继续查找父类
				parentClazz = parentClazz.getSuperclass();
			}
		}

		return false;
	}
}
