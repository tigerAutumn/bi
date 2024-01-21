package com.pinting.business.dayend.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsLoanRelativeRecord;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.service.manage.BsSpecialJnlService;
import com.pinting.business.service.site.LoanRelationService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.FtpClientUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
@Deprecated
public class GetLoanRelationTask {
	private Logger log = LoggerFactory.getLogger(GetLoanRelationTask.class);
	@Autowired
	private LoanRelationService loanRelationService;
	@Autowired
	private BsSpecialJnlService bsSpecialJnlService;
	@Autowired
	private SpecialJnlService specialJnlService;
	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;
	/**
	 * 任务执行
	 */
	protected void execute() {
		// 日终【获取达飞债权关系数据】
		//getDafyLoanRelationTask();
		try {
			getDafyLoanRelationAll4NewTask();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("==================日终【获取达飞云贷2.0全量债权关系数据】报错====================");
		}
		
		try {
			get7DaiLoanRelationAll4NewTask();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("==================日终【获取达飞7贷全量债权关系数据】报错====================");
		}
		
	}

	/**
	 * 达飞云贷债权数据拉取及存库
	 */
	private void getDafyLoanRelationAll4NewTask() {
		log.info("==================日终【获取达飞云贷2.0全量债权关系数据】开始====================");
		BufferedReader br = null;
		try {
			//ftp获得文件，并保存
			String ftpHost = GlobEnvUtil.get("dafy.ftp.host");
			String ftpPort = GlobEnvUtil.get("dafy.ftp.port");
			String username = GlobEnvUtil.get("dafy.ftp.username");
			String password = GlobEnvUtil.get("dafy.ftp.password");
			String localDir = GlobEnvUtil.get("dafy.loanRelation.new.download.file")+"yundai/";
			String sourceFileName = GlobEnvUtil.get("dafy.loanRelation.new.ftp.file.addr") + "loanRelation_" + DateUtil.formatDateTime(DateUtil.addDays(new Date(), -1), "yyyyMMdd") + ".csv";
			FtpClientUtil ftpClient = new FtpClientUtil(username, password, ftpHost, Integer.valueOf(ftpPort));
			boolean flag = ftpClient.download(sourceFileName, localDir);
			if(!flag){
				throw new PTMessageException(PTMessageEnum.DAFY_FTP_DOWNLOAD_FAIL);
			}
			log.info("==================日终【获取达飞云贷2.0全量债权关系数据】保存文件到本地成功====================");
			File dest = new File(localDir + "loanRelation_" + DateUtil.formatDateTime(DateUtil.addDays(new Date(), -1), "yyyyMMdd") + ".csv");
			//解析文件数据，保存到list列表中
			br = new BufferedReader(new InputStreamReader(new FileInputStream(dest),"GBk"));
			List<BsLoanRelativeRecord> relationlist = new ArrayList<BsLoanRelativeRecord>();
			String tempRelation = "";
			int count = 0;
			String time = bsSysConfigMapper.selectByPrimaryKey(Constants.MATCH_BUY_START_TIME).getConfValue();
			Date limitTime = DateUtil.addDays(DateUtil.parseDate(time), 1);//用户理财购买时间在该时间之后（包含）的数据将会保存
			while((tempRelation = br.readLine()) != null){
				if(count > 0){
					String[] relation = tempRelation.split(",");
					Date buyTime = StringUtils.isBlank(relation[1]) ? null : DateUtil.parseDate(relation[1]);
					
					if(buyTime != null && limitTime.compareTo(buyTime)<=0){
						BsLoanRelativeRecord loanRelative = new BsLoanRelativeRecord();
						loanRelative.setPropertySymbol(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI);
						loanRelative.setProductName(relation[0]);  // 达飞产品名称--理财产品名称
						loanRelative.setBuyTime(DateUtil.parseDate(relation[1])); //理财购买时间
						loanRelative.setCustomerId(relation[2]);    // 达飞客户号
	                    loanRelative.setLenderCustomerId(relation[2]);    // 借出人客户号(理财人)
	                    loanRelative.setOrderNo(relation[3]);		// 理财产品单号
	                    loanRelative.setLenderIdCard(relation[4]);    // 借出人身份证
	                    loanRelative.setLenderName(relation[5]);    // 借出人姓名
	                    
						loanRelative.setBorrowerCustomerId(relation[6]);    // 借入人客户号
	                    loanRelative.setBorrowerIdCard(relation[7]);    // 借入人身份证
	                    loanRelative.setBorrowerName(relation[8]);    // 借入人姓名
	                    loanRelative.setBorrowerBankcard(relation[9]);		//借款人银行卡号 
	                    
	                    loanRelative.setBorrowProductName(relation[10]);
	                    loanRelative.setBorrowAmount(StringUtils.isBlank(relation[11]) ? null : Double.valueOf(relation[11]));	//借款金额
	                    loanRelative.setAmount(StringUtils.isBlank(relation[12]) ? null : Double.valueOf(relation[12]));    // 匹配金额
	                    //loanRelative.setInitAmount(StringUtils.isBlank(relation[12]) ? null : Double.valueOf(relation[12]));	//初始匹配金额
	                   
	                    loanRelative.setBorrowId(relation[13]);		//借款编号
	                    loanRelative.setBorrowTransNo(relation[14]);	//放款交易号
	                    loanRelative.setBorrowApplyTime(StringUtils.isBlank(relation[15]) ? null : DateUtil.parseDateTime(relation[15]));	//借款申请时间
	                    loanRelative.setTime(StringUtils.isBlank(relation[16]) ? null : DateUtil.parseDateTime(relation[16]));    // 放款完成时间
	                    loanRelative.setIsRepay(Constants.LOAN_RELATIVE_IS_REPAY_N);  // 是否已还款
	                    
	                    loanRelative.setBorrowerCreditAmount(Double.valueOf(relation[18]));	//借款人授信额度
	                    loanRelative.setBorrowerBorrowNum(StringUtils.isBlank(relation[19]) ? null : Integer.parseInt(relation[19]));	//借款人借款次数
	                    loanRelative.setBorrowerTotalAmount(StringUtils.isBlank(relation[20]) ? null : Double.valueOf(relation[20]));	//借款人累计借款金额
	                    loanRelative.setChannel(Constants.LOAN_RELATIVE_CANNEL_DAFY_NEW_ALL);  // 渠道来源
	                    
						
						loanRelative.setRemark(null);    // 借款用途
						loanRelative.setRepayTime(null);  // 还款时间
						loanRelative.setCreateTime(new Date());
						loanRelative.setUpdateTime(new Date());
						relationlist.add(loanRelative);
					}
					
				}
				count++;
			}
			log.info("==================日终【获取达飞云贷2.0全量债权关系数据】解析文件数据成功====================");
			log.info("==================日终【获取达飞云贷2.0全量债权关系数据】发现债权关系条数总计：【" + (count-1) + "】条====================");
			//无债权明细获得
			if(count <= 1){
				//告警
				specialJnlService.warn4Fail(null, "定时任务{获取达飞云贷2.0全量债权关系数据}文件下载无明细，请检查", 
						null, "新云贷全量债权关系数据获取", true);
			}else{
				//批量新增到数据库
				loanRelationService.addFTPNewLoanRelationsRecord(relationlist, Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI);
				
				log.info("==================日终【获取达飞云贷2.0全量债权关系数据】批量新增到数据库成功====================");
			}
			
		} catch (Exception e) {
			// 由于跑批失败，此处需记录失败信息 到 特殊交易流水表
			log.error("==================日终【获取达飞云贷2.0全量债权关系数据】失败，【特殊交易流水表】新增失败记录====================" , e );
			String detail = "【" + DateUtil.format(new Date()) + "】日终：获取达飞云贷2.0全量债权关系数据跑批失败：" +  StringUtil.left(e.getMessage(), 20);
			//告警
			specialJnlService.warn4Fail(null, detail, null, "新云贷全量债权关系数据获取", true);
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			log.info("==================日终【获取达飞云贷2.0全量债权关系数据】结束====================");
		}
		
	}
	
	/**
	 * 7贷债权数据拉取及存款
	 */
	private void get7DaiLoanRelationAll4NewTask() {
		log.info("==================日终【获取7贷全量债权关系数据】开始====================");
		BufferedReader br = null;
		try {
			//ftp获得文件，并保存
			String ftpHost = GlobEnvUtil.get("7dai.ftp.host");
			String ftpPort = GlobEnvUtil.get("7dai.ftp.port");
			String username = GlobEnvUtil.get("7dai.ftp.username");
			String password = GlobEnvUtil.get("7dai.ftp.password");
			String localDir = GlobEnvUtil.get("dafy.loanRelation.new.download.file")+"7dai/";
			String sourceFileName = GlobEnvUtil.get("7dai.loanRelation.new.ftp.file.addr") +"loanRelation_" + DateUtil.formatDateTime(DateUtil.addDays(new Date(), -1), "yyyyMMdd") + ".csv";
			FtpClientUtil ftpClient = new FtpClientUtil(username, password, ftpHost, Integer.valueOf(ftpPort));
			boolean flag = ftpClient.download(sourceFileName, localDir);
			if(!flag){
				throw new PTMessageException(PTMessageEnum.DAFY_FTP_DOWNLOAD_FAIL);
			}
			log.info("==================日终【获取7贷全量债权关系数据】保存文件到本地成功====================");
			File dest = new File(localDir + "loanRelation_" + DateUtil.formatDateTime(DateUtil.addDays(new Date(), -1), "yyyyMMdd") + ".csv");
			//解析文件数据，保存到list列表中
			br = new BufferedReader(new InputStreamReader(new FileInputStream(dest),"utf-8"));
			List<BsLoanRelativeRecord> relationlist = new ArrayList<BsLoanRelativeRecord>();
			String tempRelation = "";
			int count = 0;
			String time = bsSysConfigMapper.selectByPrimaryKey(Constants.MATCH_BUY_START_TIME).getConfValue();
			Date limitTime = DateUtil.addDays(DateUtil.parseDate(time), 1);//用户理财购买时间在该时间之后（包含）的数据将会保存
			while((tempRelation = br.readLine()) != null){
				if(count > 0){
					String[] relation = tempRelation.split(",");
					Date buyTime = StringUtils.isBlank(relation[1]) ? null : DateUtil.parseDate(relation[1]);
					
					if(buyTime != null && limitTime.compareTo(buyTime)<=0){
						BsLoanRelativeRecord loanRelative = new BsLoanRelativeRecord();
						loanRelative.setPropertySymbol(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI);
						loanRelative.setProductName(relation[0]);  // 达飞产品名称--理财产品名称
						loanRelative.setBuyTime(DateUtil.parseDate(relation[1])); //理财购买时间
						loanRelative.setCustomerId(relation[2]);    // 达飞客户号
	                    loanRelative.setLenderCustomerId(relation[2]);    // 借出人客户号(理财人)
	                    loanRelative.setOrderNo(relation[3]);		// 理财产品单号
	                    loanRelative.setLenderIdCard(relation[4]);    // 借出人身份证
	                    loanRelative.setLenderName(relation[5]);    // 借出人姓名
	                    
						loanRelative.setBorrowerCustomerId(relation[6]);    // 借入人客户号
	                    loanRelative.setBorrowerIdCard(relation[7]);    // 借入人身份证
	                    loanRelative.setBorrowerName(relation[8]);    // 借入人姓名
	                    loanRelative.setBorrowerBankcard(relation[9]);		//借款人银行卡号 
	                    
	                    loanRelative.setBorrowProductName(relation[10]);
	                    loanRelative.setBorrowAmount(StringUtils.isBlank(relation[11]) ? null : Double.valueOf(relation[11]));	//借款金额
	                    loanRelative.setAmount(StringUtils.isBlank(relation[12]) ? null : Double.valueOf(relation[12]));    // 匹配金额
	                    //loanRelative.setInitAmount(StringUtils.isBlank(relation[12]) ? null : Double.valueOf(relation[12]));	//初始匹配金额
	                   
	                    loanRelative.setBorrowId(relation[13]);		//借款编号
	                    loanRelative.setBorrowTransNo(relation[14]);	//放款交易号
	                    loanRelative.setBorrowApplyTime(StringUtils.isBlank(relation[15]) ? null : DateUtil.parseDateTime(relation[15]));	//借款申请时间
	                    loanRelative.setTime(StringUtils.isBlank(relation[16]) ? null : DateUtil.parseDateTime(relation[16]));    // 放款完成时间
	                    loanRelative.setIsRepay(Constants.LOAN_RELATIVE_IS_REPAY_N);  // 是否已还款
	                    
	                    loanRelative.setBorrowerCreditAmount(Double.valueOf(relation[18]));	//借款人授信额度
	                    loanRelative.setBorrowerBorrowNum(StringUtils.isBlank(relation[19]) ? null : Integer.parseInt(relation[19]));	//借款人借款次数
	                    loanRelative.setBorrowerTotalAmount(StringUtils.isBlank(relation[20]) ? null : Double.valueOf(relation[20]));	//借款人累计借款金额
	                    loanRelative.setChannel(Constants.LOAN_RELATIVE_CANNEL_DAFY_NEW_ALL);  // 渠道来源
	                    
						
						loanRelative.setRemark(null);    // 借款用途
						loanRelative.setRepayTime(null);  // 还款时间
						loanRelative.setCreateTime(new Date());
						loanRelative.setUpdateTime(new Date());
						relationlist.add(loanRelative);
					}
					
				}
				count++;
			}
			log.info("==================日终【获取7贷全量债权关系数据】解析文件数据成功====================");
			log.info("==================日终【获取7贷全量债权关系数据】发现债权关系条数总计：【" + (count-1) + "】条====================");
			//无债权明细获得
			if(count <= 1){
				//告警
				specialJnlService.warn4Fail(null, "定时任务{获取7贷全量债权关系数据}文件下载无明细，请检查", 
						null, "新云贷全量债权关系数据获取", true);
			}else{
				//批量新增到数据库
				loanRelationService.addFTPNewLoanRelationsRecord(relationlist, Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI);
				
				log.info("==================日终【获取7贷全量债权关系数据】批量新增到数据库成功====================");
			}
			
		} catch (Exception e) {
			// 由于跑批失败，此处需记录失败信息 到 特殊交易流水表
			log.error("==================日终【获取7贷全量债权关系数据】失败，【特殊交易流水表】新增失败记录====================" , e );
			String detail = "【" + DateUtil.format(new Date()) + "】日终：获取7贷全量债权关系数据跑批失败：" +  StringUtil.left(e.getMessage(), 20);
			//告警
			specialJnlService.warn4Fail(null, detail, null, "7贷全量债权关系数据获取", true);
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			log.info("==================日终【获取7贷全量债权关系数据】结束====================");
		}
		
	}

	@Deprecated
	private void getDafyLoanRelationTask() {
		log.info("==================日终【获取达飞老系统债权关系数据】开始====================");
		BufferedReader br = null;
		try {
			//获取URL
			/*String downloadUrl = loanRelationService.getDafyLoanRelationAddr();
			log.info("==================日终【获取达飞债权关系数据】获取URL成功：" + downloadUrl + "====================");
			//保存到本地文件
			URL url = new URL(downloadUrl);
			File dest = new File(GlobEnvUtil.get("dafy.loanRelation.download.file") 
					+ DateUtil.formatYYYYMMDD(new Date()) + ".csv");
			FileUtils.copyURLToFile(url, dest);*/
			
			//ftp获得文件，并保存
			String ftpHost = GlobEnvUtil.get("dafy.ftp.host");
			String ftpPort = GlobEnvUtil.get("dafy.ftp.port");
			String username = GlobEnvUtil.get("dafy.ftp.username");
			String password = GlobEnvUtil.get("dafy.ftp.password");
			String localDir = GlobEnvUtil.get("dafy.loanRelation.download.file");
			String sourceFileName = GlobEnvUtil.get("dafy.loanRelation.ftp.file.addr") + DateUtil.formatDateTime(new Date(), "yyyyMMdd") + ".csv";
			FtpClientUtil ftpClient = new FtpClientUtil(username, password, ftpHost, Integer.valueOf(ftpPort));
			boolean flag = ftpClient.download(sourceFileName, localDir);
			if(!flag){
				throw new PTMessageException(PTMessageEnum.DAFY_FTP_DOWNLOAD_FAIL);
			}
			log.info("==================日终【获取达飞老系统债权关系数据】保存文件到本地成功====================");
			File dest = new File(localDir + DateUtil.formatDateTime(new Date(), "yyyyMMdd") + ".csv");
			//解析文件数据，保存到list列表中
			br = new BufferedReader(new InputStreamReader(new FileInputStream(dest),"UTF-8"));
			List<BsLoanRelativeRecord> relationlist = new ArrayList<BsLoanRelativeRecord>();
			String tempRelation = "";
			int count = 0;
			while((tempRelation = br.readLine()) != null){
				if(count > 0){
					String[] relation = tempRelation.split(",");
					BsLoanRelativeRecord loanRelative = new BsLoanRelativeRecord();
					loanRelative.setBorrowerCustomerId(relation[0]);    // 借入人客户号
					loanRelative.setBorrowerName(relation[1]);    // 借入人姓名
                    loanRelative.setBorrowerIdCard(relation[2]);    // 借入人身份证
                    loanRelative.setCustomerId(relation[3]);    // 达飞客户号
                    loanRelative.setLenderCustomerId(relation[3]);    // 借出人客户号
                    loanRelative.setLenderName(relation[4]);    // 借出人姓名
                    loanRelative.setLenderIdCard(relation[5]);    // 借出人身份证
                    loanRelative.setProductName(relation[6]);  // 达飞产品名称
                    loanRelative.setAmount(Double.valueOf(relation[7]));    // 金额
                    loanRelative.setLeftAmount(Double.valueOf(relation[7]));    //未匹配金额
                    loanRelative.setTime(DateUtil.parseDateTime(relation[8]));    // 借款时间
                    loanRelative.setChannel(Constants.LOAN_RELATIVE_CANNEL_DAFY_OLD);  // 渠道来源
                    loanRelative.setIsRepay(Constants.LOAN_RELATIVE_IS_REPAY_N);  // 是否已还款
					loanRelative.setOrderNo(null);  // 理财产品单号
					loanRelative.setRemark(null);    // 借款用途
					loanRelative.setRepayTime(null);  // 还款时间
					loanRelative.setCreateTime(new Date());
					loanRelative.setUpdateTime(new Date());
					relationlist.add(loanRelative);
				}
				count++;
			}
			log.info("==================日终【获取达飞老系统债权关系数据】解析文件数据成功====================");
			log.info("==================日终【获取达飞老系统债权关系数据】发现债权关系条数总计：【" + (count-1) + "】条====================");
			//批量新增到数据库
			loanRelationService.batchAddLoanRelations(relationlist);
			log.info("==================日终【获取达飞老系统债权关系数据】批量新增到数据库成功====================");
		} catch (Exception e) {
			// 由于跑批失败，此处需记录失败信息 到 特殊交易流水表
			log.error("==================日终【获取达飞老系统债权关系数据】失败，【特殊交易流水表】新增失败记录====================" , e );
			String type = "日终【获取达飞老系统债权关系数据】";
			String detail = "【" + DateUtil.format(new Date()) + "】日终：获取达飞老系统债权关系数据跑批失败：" +  StringUtil.left(e.getMessage(), 20);
			bsSpecialJnlService.addSpecialJnl(type, detail);
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			log.info("==================日终【获取达飞老系统债权关系数据】结束====================");
		}
		
	}
	
	public static void main(String[] args) {
		new GetLoanRelationTask().execute();
//		20150327.csv
	}
}
