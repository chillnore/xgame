package war2.autocode.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 文件旅行者, 遍历文件夹收集指定文件
 * 
 * @author AfritXia
 * @since 2011/10/29
 * @version $Rev: 30 $
 *
 */
public final class FileTraveller {
	/**
	 * 类默认构造器
	 * 
	 */
	public FileTraveller() {
	}

	/**
	 * 广度优先算法遍历目录查找文件
	 * 
	 * @param root
	 * @return
	 */
	public List<String> travel(String root) {
		return this.travel(new File(root), DummyFileFilter.theInstance());
	}

	/**
	 * 广度优先算法遍历目录查找文件
	 * 
	 * @param root
	 * @param filter
	 * @return
	 */
	public List<String> travel(String root, FileFilter filter) {
		return this.travel(new File(root), filter);
	}

	/**
	 * 广度优先算法遍历目录查找文件
	 * 
	 * @param root
	 * @return
	 */
	public List<String> travel(File root) {
		return this.travel(root, DummyFileFilter.theInstance());
	}

	/**
	 * 广度优先算法遍历目录查找文件
	 * 
	 * @param root
	 * @param filter
	 * @return
	 * @throws IllegalArgumentException if root == null
	 * @throws IllegalArgumentException if filter == null
	 * 
	 */
	public List<String> travel(
		File root, FileFilter filter) {

		if (root == null) {
			throw new IllegalArgumentException("root is null");
		}

		if (filter == null) {
			throw new IllegalArgumentException("filter is null");
		}

		// 创建文件列表和队列
		List<String> fileList = new LinkedList<String>();
		Queue<File> fileQueue = new LinkedList<File>();

		// 根目录事先入队
		fileQueue.add(root);

		while (true) {
			// 从队尾取出文件
			File f = fileQueue.poll();

			if (f == null) {
				break;
			}

			if (!filter.accept(f)) {
				continue;
			}

			if (f.isFile()) {
				fileList.add(f.getAbsolutePath());
			} else {
				fileQueue.addAll(listFiles(f, filter));
			}
		}

		return fileList;
	}

	/**
	 * 获取子目录和文件列表
	 * 
	 * @param root
	 * @param filter
	 * @return
	 */
	private static List<File> listFiles(File root, FileFilter filter) {
		// XXX 在调用该函数之前, 
		// 已经对 root 参数做过空值判断
		// 
		if (!root.isDirectory()) {
			return Collections.emptyList();
		}

		List<File> fl = new LinkedList<File>();

		if (root.isDirectory()) {
			// 获取子目录和文件列表
			File[] subfs = root.listFiles(filter);
			// 增加到集合
			fl.addAll(Arrays.asList(subfs));
		}

		return fl;
	}

	/**
	 * 虚设文件过滤器
	 * 
	 * @author AfritXia
	 * @version $Rev: 30 $
	 *
	 */
	private static class DummyFileFilter implements FileFilter {
		/** 单例对象 */
		private static DummyFileFilter _instance;

		static {
			_instance = new DummyFileFilter();
		}

		/**
		 * 类默认构造器
		 * 
		 */
		private DummyFileFilter() {
		}

		/**
		 * 获取虚设文件过滤器单例对象
		 * 
		 * @return
		 */
		public static DummyFileFilter theInstance() {
			return _instance;
		}

		@Override
		public boolean accept(File pathname) {
			return true;
		}
	}
}
