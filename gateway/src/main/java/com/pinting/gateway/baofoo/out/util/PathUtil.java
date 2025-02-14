package com.pinting.gateway.baofoo.out.util;

import java.io.File;

/**
 * new File("..\path\abc.txt") 中的三个方法获取路径的方法 <br>
 * 1： getPath() 获取相对路径，例如 ..\path\abc.txt <br>
 * 2： getAbslutlyPath() 获取绝对路径，但可能包含 ".." 或 "." 字符，例如D:\otherPath\..\path\abc.txt <br>
 * 3： getCanonicalPath() 获取绝对路径，但不包含 ".." 或 "." 字符，例如 D:\path\abc.txt
 */
public class PathUtil {

	private static String webRootPath;
	private static String rootClassPath;

	@SuppressWarnings("rawtypes")
	public static String getPath(Class clazz) {
		String path = clazz.getResource("").getPath();
		return new File(path).getAbsolutePath();
	}

	public static String getPath(Object object) {
		String path = object.getClass().getResource("").getPath();
		return new File(path).getAbsolutePath();
	}

	public static String getRootClassPath() {
		if (rootClassPath == null) {
			try {
				String path = PathUtil.class.getClassLoader().getResource("").toURI().getPath();
				rootClassPath = new File(path).getAbsolutePath();
			} catch (Exception e) {
				String path = PathUtil.class.getClassLoader().getResource("").getPath();
				rootClassPath = new File(path).getAbsolutePath();
			}
		}
		return rootClassPath;
	}

	public static String getPackagePath(Object object) {
		Package p = object.getClass().getPackage();
		return p != null ? p.getName().replaceAll("\\.", "/") : "";
	}

	public static File getFileFromJar(String file) {
		throw new RuntimeException("Not finish. Do not use this method.");
	}

	public static String getWebRootPath() {
		if (webRootPath == null)
			webRootPath = detectWebRootPath();
		return webRootPath;
	}

	public static void setWebRootPath(String webRootPath) {
		if (webRootPath == null)
			return;
		if (webRootPath.endsWith(File.separator))
			webRootPath = webRootPath.substring(0, webRootPath.length() - 1);
		PathUtil.webRootPath = webRootPath;
	}

	private static String detectWebRootPath() {
		try {
			String path = PathUtil.class.getResource("/").toURI().getPath();
			return new File(path).getParentFile().getParentFile().getCanonicalPath();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
