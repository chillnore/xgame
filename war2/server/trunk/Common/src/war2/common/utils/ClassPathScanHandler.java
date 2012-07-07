package war2.common.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

/**
 * 扫描指定包下的class文件
 * 
 * @author hjj2019
 * 
 */
public class ClassPathScanHandler {
	/** 是否排除内部类? */
	private boolean _excludeInner = true;

	/**
	 * 过滤规则适用情况 true—>搜索符合规则的 false->排除符合规则的
	 */
	private boolean checkInOrEx = true;

	/**
	 * 过滤规则列表 如果是null或者空，即全部符合不过滤
	 */
	private List<String> classFilters = null;

	/**
	 * 类默认构造器
	 * 
	 */
	public ClassPathScanHandler() {
	}

	/**
	 * excludeInner:是否排除内部类 true->是 false->否<br>
	 * checkInOrEx：过滤规则适用情况 true—>搜索符合规则的 false->排除符合规则的<br>
	 * classFilters：自定义过滤规则，如果是null或者空，即全部符合不过滤
	 * 
	 * @param excludeInner
	 * @param checkInOrEx
	 * @param classFilters
	 */
	public ClassPathScanHandler(
		boolean excludeInner, 
		boolean checkInOrEx,
		List<String> classFilters) {
		this._excludeInner = excludeInner;
		this.checkInOrEx = checkInOrEx;
		this.classFilters = classFilters;

	}

	/**
	 * 扫描包
	 * 
	 * @param basePackage 基础包
	 * @param recursive 是否递归搜索子包
	 * @return 
	 * 
	 */
	public Set<Class<?>> getPackageAllClasses(
		String basePackage,
		boolean recursive) {

		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();

		String packageName = basePackage;

		if (packageName.endsWith(".")) {
			// 清除掉末尾的 .
			packageName = packageName.substring(0, packageName.lastIndexOf('.'));
		}

		// 将类名称转换成目录
		String package2Path = packageName.replace('.', '/');

		Enumeration<URL> dirs;

		try {
			// 获取资源目录
			dirs = Thread.currentThread().getContextClassLoader().getResources(package2Path);

			while (dirs.hasMoreElements()) {
				// 获取资源 URL
				URL url = dirs.nextElement();
				// 获取 URL 协议
				String protocol = url.getProtocol();

				if ("file".equals(protocol)) {
					// 如果是文件
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 文件方式扫描
					this.doScanPackageClassesByFile(
						classes, packageName, filePath, recursive);
				} else if ("jar".equals(protocol)) {
					this.doScanPackageClassesByJar(
						classes, packageName, url, recursive);
				}
			}
		} catch (IOException e) {
		}

		return classes;
	}

	/**
	 * 以jar的方式扫描包下的所有Class文件<br>
	 * 
	 * @param basePackage
	 *            eg：michael.utils.
	 * @param url
	 * @param recursive
	 * @param classes
	 */
	private void doScanPackageClassesByJar(
		Set<Class<?>> classes, 
		String basePackage, 
		URL url,
		final boolean recursive) {

		String packageName = basePackage;
		String package2Path = packageName.replace('.', '/');
		JarFile jar;
		try {
			jar = ((JarURLConnection) url.openConnection()).getJarFile();
			Enumeration<JarEntry> entries = jar.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				String name = entry.getName();
				if (!name.startsWith(package2Path) || entry.isDirectory()) {
					continue;
				}

				// 判断是否递归搜索子包
				if (!recursive
						&& name.lastIndexOf('/') != package2Path.length()) {
					continue;
				}
				// 判断是否过滤 inner class
				if (this._excludeInner && name.indexOf('$') != -1) {
					continue;
				}
				String classSimpleName = name
						.substring(name.lastIndexOf('/') + 1);
				// 判定是否符合过滤条件
				if (this.filterClassName(classSimpleName)) {
					String className = name.replace('/', '.');
					className = className.substring(0, className.length() - 6);
					try {
						classes.add(Thread.currentThread()
								.getContextClassLoader().loadClass(className));
					} catch (ClassNotFoundException e) {
					}
				}
			}
		} catch (IOException e) {
		}
	}

	/**
	 * 以文件的方式扫描包下的所有Class文件
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	private void doScanPackageClassesByFile(Set<Class<?>> classes,
			String packageName, String packagePath, boolean recursive) {
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		final boolean fileRecursive = recursive;
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义文件过滤规则
			public boolean accept(File file) {
				if (file.isDirectory()) {
					return fileRecursive;
				}
				String filename = file.getName();
				if (_excludeInner && filename.indexOf('$') != -1) {
					return false;
				}
				return filterClassName(filename);
			}
		});
		for (File file : dirfiles) {
			if (file.isDirectory()) {
				doScanPackageClassesByFile(classes,
						packageName + "." + file.getName(),
						file.getAbsolutePath(), recursive);
			} else {
				String className = file.getName().substring(0,
						file.getName().length() - 6);
				try {
					classes.add(Thread.currentThread().getContextClassLoader()
							.loadClass(packageName + '.' + className));

				} catch (ClassNotFoundException e) {
				}
			}
		}
	}

	/**
	 * 根据过滤规则判断类名
	 * 
	 * @param className
	 * @return
	 */
	private boolean filterClassName(String className) {
		if (!className.endsWith(".class")) {
			return false;
		}
		if (null == this.classFilters || this.classFilters.isEmpty()) {
			return true;
		}
		String tmpName = className.substring(0, className.length() - 6);
		boolean flag = false;
		for (String str : classFilters) {
			String tmpreg = "^" + str.replace("*", ".*") + "$";
			Pattern p = Pattern.compile(tmpreg);
			if (p.matcher(tmpName).find()) {
				flag = true;
				break;
			}
		}
		return (checkInOrEx && flag) || (!checkInOrEx && !flag);
	}

	/**
	 * @return the excludeInner
	 */
	public boolean isExcludeInner() {
		return _excludeInner;
	}

	/**
	 * @return the checkInOrEx
	 */
	public boolean isCheckInOrEx() {
		return checkInOrEx;
	}

	/**
	 * @return the classFilters
	 */
	public List<String> getClassFilters() {
		return classFilters;
	}

	/**
	 * @param pExcludeInner
	 *            the excludeInner to set
	 */
	public void setExcludeInner(boolean pExcludeInner) {
		_excludeInner = pExcludeInner;
	}

	/**
	 * @param pCheckInOrEx
	 *            the checkInOrEx to set
	 */
	public void setCheckInOrEx(boolean pCheckInOrEx) {
		checkInOrEx = pCheckInOrEx;
	}

	/**
	 * @param pClassFilters
	 *            the classFilters to set
	 */
	public void setClassFilters(List<String> pClassFilters) {
		classFilters = pClassFilters;
	}
}
