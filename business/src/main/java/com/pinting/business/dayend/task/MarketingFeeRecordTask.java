package com.pinting.business.dayend.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import com.pinting.business.accounting.service.SysReceiveMoneyService;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsSysReceiveMoney;
import com.pinting.business.service.manage.BsSpecialJnlService;
import com.pinting.business.service.site.SMSService;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.FtpClientUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.MoneyUtil;

/**
 * 营销费用打款记录　　@@@@@@作废
 * @Project: business
 * @Title: MarketingFeeRecordTask.java
 * @author dingpf
 * @date 2015-4-13 下午4:33:39
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Deprecated
public class MarketingFeeRecordTask {
	private Logger log = LoggerFactory.getLogger(MarketingFeeRecordTask.class);
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private SysReceiveMoneyService sysReceiveMoneyService;
	@Autowired
	private BsSpecialJnlService bsSpecialJnlService;
	@Autowired
	private SubAccountService bsSubAccountService;
	
	@Autowired
	private SMSService smsService;
	
	
	/**
	 * 任务执行
	 */
	protected void execute() {
		// 日终【营销费用打款记录】
		getMarketingFeeRecordTask();
	}

	private void getMarketingFeeRecordTask() {
		/*log.info("==================日终【营销费用打款记录】开始====================");
		BufferedReader br = null;
		try {
			//ftp获得文件，并保存
			String ftpHost = GlobEnvUtil.get("dafy.ftp.host");
			String ftpPort = GlobEnvUtil.get("dafy.ftp.port");
			String username = GlobEnvUtil.get("dafy.ftp.username");
			String password = GlobEnvUtil.get("dafy.ftp.password");
			String localDir = GlobEnvUtil.get("dafy.sysReceiveMoney.download.file");
			String sourceFileName = GlobEnvUtil.get("dafy.sysReceiveMoney.ftp.file.marketingFee.addr") + "MarketingFee" + DateUtil.formatDateTime(new Date(), "yyyy") + ".csv";
			FtpClientUtil ftpClient = new FtpClientUtil(username, password, ftpHost, Integer.valueOf(ftpPort));
			boolean flag = ftpClient.download(sourceFileName, localDir);
			if(!flag){
				throw new PTMessageException(PTMessageEnum.DAFY_FTP_DOWNLOAD_FAIL);
			}
			log.info("==================日终【营销费用打款记录】保存文件到本地成功====================");
			File dest = new File(localDir + "MarketingFee" + DateUtil.formatDateTime(new Date(), "yyyy") + ".csv");
			//解析文件数据，保存到list列表中
			br = new BufferedReader(new InputStreamReader(new FileInputStream(dest),"UTF-8"));
			List<BsSysReceiveMoney> list = new ArrayList<BsSysReceiveMoney>();
			String temp = "";
			int count = 0;
			double amount = 0.0;
			while((temp = br.readLine()) != null){
				if(count > 0){
					String[] sysReceiveMoney = temp.split(",");
					BsSysReceiveMoney tempSysReceiveMoney = new BsSysReceiveMoney();
					tempSysReceiveMoney.setType(sysReceiveMoney[1]);
					tempSysReceiveMoney.setAmount(Double.valueOf(sysReceiveMoney[2]));
					tempSysReceiveMoney.setCreateTime(DateUtil.parseDateTime(sysReceiveMoney[3]));
					tempSysReceiveMoney.setFinishTime(DateUtil.parseDateTime(sysReceiveMoney[4]));
					tempSysReceiveMoney.setStatus(sysReceiveMoney[5]);
					if(DateUtil.formatYYYYMMDD(tempSysReceiveMoney.getFinishTime()).equals(DateUtil.formatYYYYMMDD(new Date()))){
						amount += Double.valueOf(sysReceiveMoney[2]);
					}
					list.add(tempSysReceiveMoney);
				}
				count++;
			}
			log.info("==================日终【营销费用打款记录】解析文件数据成功====================");
			log.info("==================日终【营销费用打款记录】发现营销费用打款记录总计：【" + (count-1) + "】条,总金额为：【"+amount+"】元====================");
			double sysAmount = MoneyUtil.defaultRound(new BigDecimal(bsSubAccountService.selectTodayMarketingCosts())).doubleValue();
			amount = MoneyUtil.defaultRound(new BigDecimal(amount)).doubleValue();
			if(sysAmount != amount){  //比较达飞打款与系统记账是否一致
				log.info("==================营销费用打款金额与系统纪录金额不符====================");
				String type = "日终【营销费用打款记录】";
				String detail = "【" + DateUtil.format(new Date()) + "】日终：营销费用打款记录跑批失败：打款金额不符，打款金额【"+amount+"】元  系统总金额：【"+sysAmount+"】元";
				bsSpecialJnlService.addSpecialJnl(type, detail);
				smsService.sendSysManagerMobiles("营销费用打款记录跑批失败",true);
			}
			
			//批量新增到数据库
			sysReceiveMoneyService.batchAddSysReceiveMoneys(list, Constants.WXACCOUNT_TRS_TYPE_MARKETING_FEE);
			log.info("==================日终【营销费用打款记录】批量新增到数据库成功====================");
		} catch (Exception e) {
			// 由于跑批失败，此处需记录失败信息 到 特殊交易流水表
			log.error("==================日终【营销费用打款记录】失败，【特殊交易流水表】新增失败记录====================" , e );
			String type = "日终【营销费用打款记录】";
			String detail = "【" + DateUtil.format(new Date()) + "】日终：营销费用打款记录跑批失败：" + e.getMessage();
			bsSpecialJnlService.addSpecialJnl(type, detail);
			smsService.sendSysManagerMobiles("营销费用打款记录跑批失败",true);
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			log.info("==================日终【营销费用打款记录】结束====================");
		}*/
		
	}
	
	public static void main(String[] args) {
//		new MarketingFeeRecordTask().execute();
//		20150327.csv
		
	}
}
