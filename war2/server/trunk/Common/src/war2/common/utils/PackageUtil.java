package war2.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
}
