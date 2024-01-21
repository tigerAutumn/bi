package com.pinting.business.dayend.task;

import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsAssetsList;
import com.pinting.business.service.manage.BsSpecialJnlService;
import com.pinting.business.service.manage.MAccountService;
import com.pinting.business.service.site.SMSService;
import com.pinting.business.util.CASignature;
import com.pinting.business.util.Constants;
import com.pinting.business.util.FtpClientUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

/**
 * 资管计划文件下载
 * @Project: business
 * @Title: AssetsManagePlanTask.java
 * @author dingpf
 * @date 2015-4-13 下午4:33:39
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Deprecated
public class AssetsManagePlanTask {
	private Logger log = LoggerFactory.getLogger(AssetsManagePlanTask.class);
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private SMSService smsService;
	@Autowired
	private BsSpecialJnlService bsSpecialJnlService;
	@Autowired
	private MAccountService mAccountService;
	
	/**
	 * 任务执行
	 */
	protected void execute() {
		// 日终【资管计划文件下载】
		getAssetsManagePlanFileTask();
	}

	private void getAssetsManagePlanFileTask() {
		log.info("==================日终【资管计划文件下载】开始====================");
		FileInputStream localFileIn = null;
		try {
			//ftp获得文件，并保存
			String ftpHost = GlobEnvUtil.get("dafy.ftp.host");
			String ftpPort = GlobEnvUtil.get("dafy.ftp.port");
			String username = GlobEnvUtil.get("dafy.ftp.username");
			String password = GlobEnvUtil.get("dafy.ftp.password");
			String localDir = GlobEnvUtil.get("dafy.assetsManagePlan.download.file");
			String sourceFileName = GlobEnvUtil.get("dafy.assetsManagePlan.ftp.file.addr") 
					+ DateUtil.formatDateTime(DateUtil.addDays(new Date(), -1), "yyyyMMdd") + ".zip";
			String signFileName = GlobEnvUtil.get("dafy.assetsManagePlan.ftp.file.addr") 
					+ DateUtil.formatDateTime(DateUtil.addDays(new Date(), -1), "yyyyMMdd") + "zip.sgn";
			
			
			FtpClientUtil ftpClient = new FtpClientUtil(username, password, ftpHost, Integer.valueOf(ftpPort));
			boolean flag = ftpClient.download(sourceFileName, localDir);
			if(!flag){
				throw new PTMessageException(PTMessageEnum.DAFY_FTP_DOWNLOAD_FAIL, sourceFileName);
			}
			boolean sgnFlag = ftpClient.download(signFileName, localDir);
			if(!sgnFlag){
				throw new PTMessageException(PTMessageEnum.DAFY_FTP_DOWNLOAD_FAIL, signFileName);
			}
			localFileIn = new FileInputStream(localDir + DateUtil.formatDateTime(DateUtil.addDays(new Date(), -1), "yyyyMMdd") + ".zip");
			byte[] temp = new byte[8];
			int length = 0;
			boolean isFileEmpty = false;
			while ((length = localFileIn.read(temp)) != -1) {
				isFileEmpty = true;
				break;
			}
			if(!isFileEmpty){
				throw new PTMessageException(PTMessageEnum.DAFY_FTP_DOWNLOAD_NULL, sourceFileName);
			}
			
			log.info("==================日终【资管计划文件下载】保存文件到本地成功====================");
			
			log.info("==================日终【资管计划文件下载】开始验签====================");
			CASignature.KEY_STORE_PASSWORD = GlobEnvUtil.get("dafy.signature.KEY_STORE_PASSWORD");
			CASignature.PUBLIC_KEY_NAME = GlobEnvUtil.get("dafy.signature.PUBLIC_KEY_NAME");
			CASignature.PUBLIC_KEY_FILE_PATH = GlobEnvUtil.get("dafy.signature.PUBLIC_KEY_FILE_PATH");
			CASignature.SOURCE_FILE_COPY = localDir + DateUtil.formatDateTime(DateUtil.addDays(new Date(), -1), "yyyyMMdd") + ".zip";
			CASignature.SIGN_FILE_COPY = localDir + DateUtil.formatDateTime(DateUtil.addDays(new Date(), -1), "yyyyMMdd") + "zip.sgn";
			boolean signVerifyFlag = CASignature.wangxinVerify();
			log.info("==================日终【资管计划文件下载】验签结果：" + signVerifyFlag + "====================");
			
			log.info("==================日终【资管计划文件下载】开始记录，提供后台下载====================");
			BsAssetsList bsAssetsList = new BsAssetsList();
			bsAssetsList.setCreateTime(new Date());
			bsAssetsList.setFileAddress(GlobEnvUtil.get("dafy.assetsManagePlan.download.local.url") 
					+ DateUtil.formatDateTime(DateUtil.addDays(new Date(), -1), "yyyyMMdd") + ".zip");
			bsAssetsList.setStatus(signVerifyFlag?Constants.ASSETS_MANAGE_PLAN_SIGN_TRUE:Constants.ASSETS_MANAGE_PLAN_SIGN_FALSE);
			mAccountService.addBsAssetsPlan(bsAssetsList);
			
		} catch (Exception e) {
			// 由于跑批失败，此处需记录失败信息 到 特殊交易流水表
			log.error("==================日终【资管计划文件下载】失败，【特殊交易流水表】新增失败记录====================" , e );
			String type = "日终【资管计划文件下载】";
			String detail = "【" + DateUtil.format(new Date()) + "】日终：资管计划文件下载跑批失败：" + e.getMessage();
			bsSpecialJnlService.addSpecialJnl(type, detail);
			//smsService.sendSysManagerMobiles("日终【资管计划文件下载】跑批失败。");
		}finally{
			if(localFileIn != null){
				try {
					localFileIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			log.info("==================日终【资管计划文件下载】结束====================");
		}
		
	}
	
	public static void main(String[] args) {
		new AssetsManagePlanTask().execute();
		
	}
}
