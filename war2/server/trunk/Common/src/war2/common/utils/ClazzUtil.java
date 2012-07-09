package war2.common.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

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
	 * 判断 A 是否为 B 的派生类
	 * 
	 * @param clazzA
	 * @param clazzB
	 * @return 
	 * 
	 */
	public static boolean isDrivedClass(
		Class<?> clazzA, 
		Class<?> clazzB) {

		if (clazzA == null || 
			clazzB == null) {
			return false;
		} else if (clazzA.equals(clazzB)) {
			// 类自己不能作为自己的派生类, 
			// 所以返回 false
			return false;
		} else {
			// 判断 A 是否为 B 的派生类
			return clazzB.isAssignableFrom(clazzA);
		}
	}

	/**
	 * 是否为具体的派生类, 即, 派生类不是接口或抽象类
	 * 
	 * @param currClazz
	 * @param superClazz
	 * @return
	 */
	public static boolean isConcreteDrivedClass(
		Class<?> currClazz, 
		Class<?> superClazz) {

		if (!isDrivedClass(currClazz, superClazz)) {
			// 如果连派生类的条件都不满足, 
			// 则直接返回 false
			return false;
		}

		// 获取修饰符
		int mod = currClazz.getModifiers();

		if (Modifier.isAbstract(mod) || 
			Modifier.isInterface(mod)) {
			// 如果使用了 abstract 或者 interface, 
			// 则直接返回 false
			return false;
		}

		return true;
	}

	/**
	 * 从类中获取指定名称的方法
	 * 
	 * @param methodName 
	 * @param clazz
	 * @return 
	 * 
	 */
	public static Method getMethod(String methodName, Class<?> clazz) {
		if (methodName == null ||
			methodName.isEmpty()) {
			return null;
		}

		if (clazz == null) {
			return null;
		}

		// 获取已经定义的方法
		Method[] methods = clazz.getDeclaredMethods();

		if (methods == null) {
			return null;
		}

		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}

		return null;
	}
}
