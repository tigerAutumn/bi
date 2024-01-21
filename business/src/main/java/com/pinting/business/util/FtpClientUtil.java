package com.pinting.business.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.core.util.GlobEnvUtil;

/**
 * commons-net组件 FtpClient下载文件
 * @Project: business
 * @Title: FtpClientUtil.java
 * @author dingpf
 * @date 2015-3-19 下午2:00:04
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class FtpClientUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(ExcelUtil.class);
	private String username;
	private String password;
	private String ftpHostName;
	private int port = 21;
	private FTPClient ftpClient = new FTPClient();
	private FileOutputStream fos = null;

	public FtpClientUtil(String username, String password, String ftpHostName,
			int port) {
		this.username = username;
		this.password = password;
		this.ftpHostName = ftpHostName;
		this.port = port;
	}
	
	/**
	 * 下载文件
	 * 
	 * @param ftpFileName
	 * @param localDir
	 * @return
	 */
	public boolean download(String ftpFileName, String localDir) {
		connect();
		boolean flag = downFileOrDir(ftpFileName, localDir);
		close(fos);
		return flag;
	}

	/**
	 * 下载文件具体实现
	 * 
	 * @param ftpFileName
	 * @param localDir
	 * @return
	 */
	private boolean downFileOrDir(String ftpFileName, String localDir) {
		boolean flag = false;
		try {
			// 源文件
			File file = new File(ftpFileName);
			// 目标文件
			File temp = new File(localDir);
			if (!temp.exists()) {
				temp.mkdirs();
			}
			ftpClient.enterLocalPassiveMode();
			// 判断是否是目录
			if (isDir(ftpFileName)) {
				String[] names = ftpClient.listNames();
				for (int i = 0; i < names.length; i++) {
					logger.info(names[i] + "^^^^^^^^^^^^^^");
					if (isDir(names[i])) {
						downFileOrDir(ftpFileName + '/' + names[i], localDir
								+ File.separator + names[i]);
						ftpClient.changeToParentDirectory();
					} else {
						File localfile = new File(localDir + File.separator
								+ names[i]);
						if (!localfile.exists()) {
							fos = new FileOutputStream(localfile);
							ftpClient.retrieveFile(names[i], fos);
						} else {
							// 文件已存在，先进行删除
							logger.debug("开始删除已存在文件");
							file.delete();
							logger.debug("已存在文件已经删除");
							fos = new FileOutputStream(localfile);
							ftpClient.retrieveFile(ftpFileName, fos);
						}
					}
				}
			} else {
				File localfile = new File(localDir + File.separator
						+ file.getName());
				if (!localfile.exists()) {
					fos = new FileOutputStream(localfile);
					ftpClient.retrieveFile(ftpFileName, fos);
				} else {
					// 文件已存在，先进行删除
					logger.debug("开始删除已存在文件");
					file.delete();
					logger.debug("已存在文件已经删除");
					fos = new FileOutputStream(localfile);
					ftpClient.retrieveFile(ftpFileName, fos);
				}
				ftpClient.changeToParentDirectory();
			}
			flag = true;
			logger.info("下载成功！");
		} catch (SocketException e) {
			logger.error("连接失败！", e);
		} catch (Exception e) {
			logger.error("下载失败！", e);
		} 
		return flag;
	}

	/**
	 * 连接
	 */
	private void connect() {
		try {
			logger.debug("开始连接...");
			// 连接
			ftpClient.connect(ftpHostName, port);
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
			}
			// 登录
			logger.debug("开始登录！");
			ftpClient.login(username, password);
			ftpClient.setBufferSize(256);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setControlEncoding("utf8");
			logger.debug("登录成功！");
		} catch (SocketException e) {
			logger.error("连接失败！", e);
		} catch (Exception e) {
			logger.error("下载失败！", e);
		}
	}

	/**
	 * 关闭连接及输出流
	 * 
	 * @param fos
	 */
	private void close(FileOutputStream fos) {
		try {
			if (fos != null) {
				fos.close();
			}
			ftpClient.logout();
			logger.info("退出登录");
			ftpClient.disconnect();
			logger.info("关闭连接");
		} catch (IOException e) {
			logger.error("关闭连接失败", e);
		}
	}

	/**
	 * 判断是否是目录
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean isDir(String fileName) {
		boolean flag = false;
		try {
			// 切换目录，若当前是目录则返回true,否则返回false。
			flag = ftpClient.changeWorkingDirectory(fileName);
		} catch (Exception e) {
			logger.error("判断目录失败", e);
		}
		return flag;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFtpHostName() {
		return ftpHostName;
	}

	public void setFtpHostName(String ftpHostName) {
		this.ftpHostName = ftpHostName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public FTPClient getFtpClient() {
		return ftpClient;
	}

	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	public FileOutputStream getFos() {
		return fos;
	}

	public void setFos(FileOutputStream fos) {
		this.fos = fos;
	}

	
	public static void main(String[] args) {
		String ftpHost = GlobEnvUtil.get("dafy.ftp.host");
		String ftpPort = GlobEnvUtil.get("dafy.ftp.port");
		String username = GlobEnvUtil.get("dafy.ftp.username");
		String password = GlobEnvUtil.get("dafy.ftp.password");
		FtpClientUtil ftpUtil = new FtpClientUtil(username,
				password, ftpHost, Integer.valueOf(ftpPort));
		ftpUtil.download("/export/20150326.csv", "f:/");
	}

}
