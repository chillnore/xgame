package war2.common.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

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
		}

		if (clazzA.equals(clazzB)) {
			// 类自己不能作为自己的派生类, 
			// 所以返回 false
			return false;
		}

		// 类队列
		Queue<Class<?>> cq = new LinkedList<Class<?>>();
		// 父类
		Class<?> superClazz;
		// 接口数组
		Class<?>[] interfaceArray;

		// 获取父类
		superClazz = clazzA.getSuperclass();

		if (superClazz != null) {
			// 将父类入队
			cq.add(superClazz);
		}

		// 获取接口数组
		interfaceArray = clazzA.getInterfaces();

		if (interfaceArray != null && 
			interfaceArray.length > 0) {
			// 将接口数组入队
			cq.addAll(Arrays.asList(interfaceArray));
		}

		while (!cq.isEmpty()) {
			// 获取父类
			Class<?> currClazz = cq.poll();
			
			if (currClazz.equals(clazzB)) {
				// 如果父类与参数中给定的超类相同, 
				// 那么返回 true
				return true;
			}

			// 继续查找父类
			superClazz = currClazz.getSuperclass();

			if (superClazz != null) {
				// 将父类入队
				cq.add(superClazz);
			}

			// 获取接口数组
			interfaceArray = currClazz.getInterfaces();

			if (interfaceArray != null && 
				interfaceArray.length > 0) {
				// 将接口数组入队
				cq.addAll(Arrays.asList(interfaceArray));
			}
		}

		return false;
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
