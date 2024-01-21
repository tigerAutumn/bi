package com.pinting.business.util;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author SHENGP
 * @date  2017年5月11日 上午11:23:00
 */
public class FileUtil extends FileUtils {

	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 创建单个文件
	 * @param descFileName 文件名，包含路径
	 * @return 如果创建成功，则返回true，否则返回false
	 */
	public static boolean createFile(String descFileName) {
		File file = new File(descFileName);
		if (file.exists()) {
			logger.debug("文件 " + descFileName + " 已存在!");
			return false;
		}
		if (descFileName.endsWith(File.separator)) {
			logger.debug(descFileName + " 为目录，不能创建目录!");
			return false;
		}
		if (!file.getParentFile().exists()) {
			// 如果文件所在的目录不存在，则创建目录
			if (!file.getParentFile().mkdirs()) {
				logger.debug("创建文件所在的目录失败!");
				return false;
			}
		}

		// 创建文件
		try {
			if (file.createNewFile()) {
				logger.debug(descFileName + " 文件创建成功!");
				return true;
			} else {
				logger.debug(descFileName + " 文件创建失败!");
				return false;
			}
		} catch (Exception e) {
			logger.debug(descFileName + " 文件创建失败!", e);
			return false;
		}

	}

	/**
	 * 创建目录
	 * @param descDirName 目录名,包含路径
	 * @return 如果创建成功，则返回true，否则返回false
	 */
	public static boolean createDirectory(String descDirName) {
		String descDirNames = descDirName;
		if (!descDirNames.endsWith(File.separator)) {
			descDirNames = descDirNames + File.separator;
		}
		File descDir = new File(descDirNames);
		if (descDir.exists()) {
			logger.debug("目录 " + descDirNames + " 已存在!");
			return false;
		}
		// 创建目录
		if (descDir.mkdirs()) {
			logger.debug("目录 " + descDirNames + " 创建成功!");
			return true;
		} else {
			logger.debug("目录 " + descDirNames + " 创建失败!");
			return false;
		}

	}

	/**
	 * 写入文件
	 * @param fileName 要写入的文件
	 * @param append 如果true，那么String将被添加到文件的末尾而不是覆盖
	 */
	public static void writeToFile(String fileName, String content, boolean append) {
		try {
			FileUtils.write(new File(fileName), content, "utf-8", append);
			logger.debug("文件 " + fileName + " 写入成功!");
		} catch (IOException e) {
			logger.debug("文件 " + fileName + " 写入失败! " + e.getMessage(), e);
		}
	}

	/**
	 * 写入文件
	 * @param fileName 要写入的文件
	 */
	public static void writeToFile(String fileName, String content, String encoding, boolean append) {
		try {
			FileUtils.write(new File(fileName), content, encoding, append);
			logger.debug("文件 " + fileName + " 写入成功!");
		} catch (IOException e) {
			logger.debug("文件 " + fileName + " 写入失败! " + e.getMessage(), e);
		}
	}
	
	/**
	 * 压缩文件或目录
	 * @param srcDirName 压缩的根目录
	 * @param fileName 根目录下的待压缩的文件名或文件夹名，其中*或""表示跟目录下的全部文件
	 * @param descFileName 目标zip文件
	 */
	public static void zipFiles(String srcDirName, String fileName,
			String descFileName) {
		// 判断目录是否存在
		if (srcDirName == null) {
			logger.debug("文件压缩失败，目录 " + srcDirName + " 不存在!");
			return;
		}
		File fileDir = new File(srcDirName);
		if (!fileDir.exists() || !fileDir.isDirectory()) {
			logger.debug("文件压缩失败，目录 " + srcDirName + " 不存在!");
			return;
		}
		String dirPath = fileDir.getAbsolutePath();
		File descFile = new File(descFileName);
		try {
			ZipOutputStream zouts = new ZipOutputStream(new FileOutputStream(
					descFile));
			if ("*".equals(fileName) || "".equals(fileName)) {
				FileUtil.zipDirectoryToZipFile(dirPath, fileDir, zouts);
			} else {
				File file = new File(fileDir, fileName);
				if (file.isFile()) {
					FileUtil.zipFilesToZipFile(dirPath, file, zouts);
				} else {
					FileUtil.zipDirectoryToZipFile(dirPath, file, zouts);
				}
			}
			zouts.close();
			logger.debug(descFileName + " 文件压缩成功!");
		} catch (Exception e) {
			logger.debug("文件压缩失败：" + e.getMessage(), e);
		}

	}

	/**
	 * 解压缩ZIP文件，将ZIP文件里的内容解压到descFileName目录下
	 * @param zipFileName 需要解压的ZIP文件
	 * @param descFileName 目标文件
	 */
	public static boolean unZipFiles(String zipFileName, String descFileName) {
		String descFileNames = descFileName;
		if (!descFileNames.endsWith(File.separator)) {
			descFileNames = descFileNames + File.separator;
		}		
        try {
			// 根据ZIP文件创建ZipFile对象
			ZipFile zipFile = new ZipFile(zipFileName);
			ZipEntry entry = null;
			String entryName = null;
			String descFileDir = null;
			byte[] buf = new byte[4096];
			int readByte = 0;
			// 获取ZIP文件里所有的entry
			@SuppressWarnings("rawtypes")
			Enumeration enums = zipFile.entries();
			// 遍历所有entry
			while (enums.hasMoreElements()) {
				entry = (ZipEntry) enums.nextElement();
				// 获得entry的名字
				entryName = entry.getName();
				descFileDir = descFileNames + entryName;
				if (entry.isDirectory()) {
					// 如果entry是一个目录，则创建目录
					new File(descFileDir).mkdirs();
					continue;
				} else {
					// 如果entry是一个文件，则创建父目录
					new File(descFileDir).getParentFile().mkdirs();
				}
				File file = new File(descFileDir);
				// 打开文件输出流
				OutputStream os = new FileOutputStream(file);
				// 从ZipFile对象中打开entry的输入流
		        InputStream is = zipFile.getInputStream(entry);
				while ((readByte = is.read(buf)) != -1) {
					os.write(buf, 0, readByte);
				}
				os.close();
				is.close();
			}
			zipFile.close();
			logger.debug("文件解压成功!");
			return true;
		} catch (Exception e) {
			logger.debug("文件解压失败：" + e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 将目录压缩到ZIP输出流
	 * @param dirPath 目录路径
	 * @param fileDir 文件信息
	 * @param zouts 输出流
	 */
	public static void zipDirectoryToZipFile(String dirPath, File fileDir, ZipOutputStream zouts) {
		if (fileDir.isDirectory()) {
			File[] files = fileDir.listFiles();
			// 空的文件夹
			if (files.length == 0) {
				// 目录信息
				ZipEntry entry = new ZipEntry(getEntryName(dirPath, fileDir));
				try {
					zouts.putNextEntry(entry);
					zouts.closeEntry();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				return;
			}
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					// 如果是文件，则调用文件压缩方法
					FileUtil.zipFilesToZipFile(dirPath, files[i], zouts);
				} else {
					// 如果是目录，则递归调用
					FileUtil.zipDirectoryToZipFile(dirPath, files[i], zouts);
				}
			}
		}
	}

	/**
	 * 将文件压缩到ZIP输出流
	 * @param dirPath 目录路径
	 * @param file 文件
	 * @param zouts 输出流
	 */
	public static void zipFilesToZipFile(String dirPath, File file, ZipOutputStream zouts) {
		FileInputStream fin = null;
		ZipEntry entry = null;
		// 创建复制缓冲区
		byte[] buf = new byte[4096];
		int readByte = 0;
		if (file.isFile()) {
			try {
				// 创建一个文件输入流
				fin = new FileInputStream(file);
				// 创建一个ZipEntry
				entry = new ZipEntry(getEntryName(dirPath, file));
				// 存储信息到压缩文件
				zouts.putNextEntry(entry);
				// 复制字节到压缩文件
				while ((readByte = fin.read(buf)) != -1) {
					zouts.write(buf, 0, readByte);
				}
				zouts.closeEntry();
				fin.close();
				logger.debug("添加文件 " + file.getAbsolutePath() + " 到zip文件中!");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 获取待压缩文件在ZIP文件中entry的名字，即相对于跟目录的相对路径名
	 * @param dirPath 目录名
	 * @param file entry文件名
	 * @return
	 */
	private static String getEntryName(String dirPath, File file) {
		String dirPaths = dirPath;
		if (!dirPaths.endsWith(File.separator)) {
			dirPaths = dirPaths + File.separator;
		}
		String filePath = file.getAbsolutePath();
		// 对于目录，必须在entry名字后面加上"/"，表示它将以目录项存储
		if (file.isDirectory()) {
			filePath += "/";
		}
		int index = filePath.indexOf(dirPaths);
		return filePath.substring(index + dirPaths.length());
	}

	/**
	 * 获取文件扩展名(返回小写)
	 * @param fileName 文件名
	 * @return 例如：test.jpg  返回：  jpg
	 */
	public static String getFileExtension(String fileName) {
		if ((fileName == null) || (fileName.lastIndexOf(".") == -1) || (fileName.lastIndexOf(".") == fileName.length() - 1)) {
			return null;
		}
		return StringUtils.lowerCase(fileName.substring(fileName.lastIndexOf(".") + 1));
	}

	/**
	 * 获取文件名，不包含扩展名
	 * @param fileName 文件名
	 * @return 例如：d:\files\test.jpg  返回：d:\files\test
	 */
	public static String getFileNameWithoutExtension(String fileName) {
		if ((fileName == null) || (fileName.lastIndexOf(".") == -1)) {
			return null;
		}
		return fileName.substring(0, fileName.lastIndexOf("."));
	}
	
	/**
	 * 删除文件，可以删除单个文件或文件夹
	 * 
	 * @param fileName 被删除的文件名
	 * @return 如果删除成功，则返回true，否是返回false
	 */
	public static boolean delFile(String fileName) {
 		File file = new File(fileName);
		if (!file.exists()) {
			logger.debug(fileName + " 文件不存在!");
			return true;
		} else {
			if (file.isFile()) {
				return FileUtil.deleteFile(fileName);
			} else {
				return FileUtil.deleteDirectory(fileName);
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName 被删除的文件名
	 * @return 如果删除成功，则返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				logger.debug("删除文件 " + fileName + " 成功!");
				return true;
			} else {
				logger.debug("删除文件 " + fileName + " 失败!");
				return false;
			}
		} else {
			logger.debug(fileName + " 文件不存在!");
			return true;
		}
	}

	/**
	 * 删除目录及目录下的文件
	 * 
	 * @param dirName 被删除的目录所在的文件路径
	 * @return 如果目录删除成功，则返回true，否则返回false
	 */
	public static boolean deleteDirectory(String dirName) {
		String dirNames = dirName;
		if (!dirNames.endsWith(File.separator)) {
			dirNames = dirNames + File.separator;
		}
		File dirFile = new File(dirNames);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			logger.debug(dirNames + " 目录不存在!");
			return true;
		}
		boolean flag = true;
		// 列出全部文件及子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = FileUtil.deleteFile(files[i].getAbsolutePath());
				// 如果删除文件失败，则退出循环
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = FileUtil.deleteDirectory(files[i]
						.getAbsolutePath());
				// 如果删除子目录失败，则退出循环
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			logger.debug("删除目录失败!");
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			logger.debug("删除目录 " + dirName + " 成功!");
			return true;
		} else {
			logger.debug("删除目录 " + dirName + " 失败!");
			return false;
		}
	}

	/**
	 * 读取上传的file.txt文件
	 * @param inputStream
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static List<String> readLines(InputStream inputStream, String encoding) throws IOException {

		BOMInputStream bomInputStream = null;
		List var3;
		try {
			//使用BOMInputStream自动去除UTF-8中的BOM！！！
			bomInputStream = new BOMInputStream(inputStream);
			var3 = IOUtils.readLines(bomInputStream, encoding);
		} finally {
			IOUtils.closeQuietly(bomInputStream);
			IOUtils.closeQuietly(inputStream);
		}

		return var3;
	}

	/**
	 * 下载txt文件
	 * @param fileName
	 * @param response
	 * @param write
	 */
	public static void downloadTxtFile(String fileName, String write, HttpServletResponse response) {
		BufferedOutputStream buff = null;
		ServletOutputStream outStr = null;
		try {
			String enableFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
			response.setContentType("text/plain");// 一下两行关键的设置
			response.addHeader("Content-Disposition",
					"attachment;filename=" + enableFileName);// filename指定默认的名字
			outStr = response.getOutputStream();// 建立
			buff = new BufferedOutputStream(outStr);
			buff.write(write.getBytes("UTF-8"));
			buff.flush();
			buff.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buff.close();
				outStr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		String str = "30000001|20150415|9|210002.00|"  +"\r\n" + "30000001|20150415|121515|10045425103559643|30000.00|C|1|101"+"\r\n"+
				"30000001|20150415|121515|10045425103559643|30000.00|T|1|101"+"\r\n"
				+ "qerererrefdsdfdggdsasdfdfdgghrtretetetxds";
		String srcPath = "d:\\fie\\";
		String fileName = srcPath + DateUtil.getSimpleDate(new Date())+".txt";
		String destFileName = srcPath + DateUtil.getSimpleDate(new Date())+".zip";
		if(srcPath.lastIndexOf(File.separator) != srcPath.length()-1){
			srcPath = srcPath+File.separator;
		} 
		if(createFile(fileName)) {
			writeToFile(fileName, str, false);
			//zipFiles(srcPath, "*", destPath);
			zipFiles(FilenameUtils.getFullPath(fileName), FilenameUtils.getName(fileName), destFileName);
			deleteFile(fileName);
		}
	}
	
}
