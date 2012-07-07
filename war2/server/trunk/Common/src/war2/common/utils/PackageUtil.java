package war2.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import war2.common.XgameError;
import war2.common.XgameNullArgsError;

/**
 * 名称空间实用工具
 * 
 * @author hjj2017
 * 
 */
public class PackageUtil {
	/**
	 * 类默认构造器
	 * 
	 */
	private PackageUtil() {
	}

	/**
	 * 获取名称空间下的所有目录或文件名称
	 * 
	 * @param packageName
	 * @param showAbsolutePath 
	 * @return
	 * @throws XgameNullArgsError if packageName == null || packageName.isEmpty 
	 * 
	 */
	public static List<String> list(String packageName, boolean showAbsolutePath) {
		if (packageName == null || 
			packageName.isEmpty()) {
			throw new XgameNullArgsError("packageName");
		}

		if (!existsPackage(packageName)) {
			// 如果名称空间不存在, 
			// 则直接返回空值
			return null;
		}

		// 获取名称空间目录
		String packageDir = StringUtil.join(packageName.split("\\."), "/");

		// 获取类加载器
		ClassLoader cl = Thread
			.currentThread()
			.getContextClassLoader();

		// 创建输入流
		InputStream is = cl.getResourceAsStream(packageDir);

		System.out.println("====");
		System.out.println(PackageUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile());
		

		if (is == null) {
			return null;
		}

		byte[] bytes = new byte[2048];
		StringBuilder sb = new StringBuilder();

		try {
			while (is.read(bytes) > 0) {
				// 将字节转成 UTF-8 格式字符串
				String s = new String(bytes, Charset.forName("utf8"));
				sb.append(s.trim());
			}
		} catch (IOException ex) {
			// 抛出异常
			throw new XgameError(ex);
		}

		String[] strs = sb.toString().split("\n");

		if (showAbsolutePath) {
			// 如果是显示完整目录, 
			// 则添加绝对路径
			List<String> resultList = new ArrayList<String>(strs.length);

			for (String str : strs) {
				// 添加绝对路径
				resultList.add(packageName + "." + str);
			}

			return resultList;
		} else {
			// 如果是不需要显示完整目录, 
			// 则直接返回当前数组
			return Arrays.asList(strs);
		}
	}

	/**
	 * 递归获取名称空间下的所有目录或文件名称, 广度优先方式
	 * 
	 * @param packageName
	 * @return 
	 * 
	 */
	public static List<String> recursiveList(String packageName) {
		// 列表名称空间下的所有目录和文件
		List<String> sl = list(packageName, true);

		if (sl == null || 
			sl.size() <= 0) {
			return null;
		}

		// 字符串队列
		Queue<String> strQueue = new LinkedList<>();
		// 结果列表
		List<String> resultList = new ArrayList<String>();

		strQueue.addAll(sl);

		while (!strQueue.isEmpty()) {
			// 获取字符串
			String str = strQueue.poll();
			// 添加字符串到结果列表
			resultList.add(str);

			if (existsPackage(str)) {
				// 如果当前字符串是名称空间
				sl = list(str, true);

				if (sl == null || 
					sl.size() <= 0) {
					continue;
				}

				strQueue.addAll(sl);
			}
		}

		return resultList;
	}

	/**
	 * 是否存在名称空间? 
	 * 
	 * @param packageName
	 * @return 
	 * 
	 */
	public static boolean existsPackage(String packageName) {
		if (packageName == null || 
			packageName.isEmpty()) {
			return false;
		}

		return true;
	}

	/**
	 * 列表指定 URL 文件、指定包中的所有类
	 * 
	 * @param url
	 * @param basePackage
	 * @param recursive
	 * @retun
	 * 
	 */
	public static Set<Class<?>> listClazz(
		String filePath, 
		boolean recursive, 
		IClazzNameFilter filter) {
		File fileObj = new File(filePath);

		if (fileObj.isDirectory()) {
			return null;
		} else if (fileObj.isFile() && 
			filePath.endsWith(".jar")) {
			// 给定参数是 jar 文件, 
			// 那么从 jar 文件中获取类列表
			return listClazzFormJar(filePath, recursive, filter);
		} else {
			// 抛出异常
			throw new XgameError("Cannot load Classes from " + filePath);
		}
	}

	/**
	 * 从 .jar 文件中获取类列表
	 * 
	 * @param filePath
	 * @param recursive 
	 * @param filter 
	 * @return 
	 * 
	 */
	private static Set<Class<?>> listClazzFormJar(
		String filePath, 
		boolean recursive, 
		IClazzNameFilter filter) {
		// 结果对象
		Set<Class<?>> resultSet = new HashSet<Class<?>>();

		try {
			// 创建 .jar 文件读入流
			JarInputStream jarIn = new JarInputStream(new FileInputStream(filePath));
			// 进入点
			JarEntry entry;

			while ((entry = jarIn.getNextJarEntry()) != null) {
				if (entry.isDirectory()) {
					continue;
				}

				// 获取进入点名称
				String entryName = entry.getName();

				if (!entryName.endsWith(".class")) {
					// 如果不是以 .class 结尾, 
					// 则说明不是 JAVA 类文件, 直接跳过!
					continue;
				}

				String clazzName;

				// 清除最后的 .class 结尾
				clazzName = entryName.substring(0, entryName.lastIndexOf('.'));
				// 将所有的 / 修改为 .
				clazzName = StringUtil.join(clazzName.split("/"), ".");

				if ((filter != null) && 
				    !filter.accept(clazzName)) {
					// 如果过滤器不为空, 
					// 且过滤器不接受当前类名称, 
					// 则直接跳过!
					continue;
				}

				// 尝试创建类, 并添加到集合中
				resultSet.add(Class.forName(clazzName));
			}

			// 关闭 jar 输入流
			jarIn.close();
		} catch (Exception ex) {
			// 抛出异常
			throw new XgameError(ex);
		}

		return resultSet;
	}

	/**
	 * 类名称过滤器
	 * 
	 * @author hjj2019
	 *
	 */
	public static interface IClazzNameFilter {
		/**
		 * 是否接受当前文件名称?
		 * 
		 * @param clazzName
		 * @return 
		 * 
		 */
		boolean accept(String clazzName);
	}
}
