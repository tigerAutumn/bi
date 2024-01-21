package com.pinting.business.dayend.task;

import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.ProductCodeNameEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsMatchVO;
import com.pinting.business.service.manage.BsSpecialJnlService;
import com.pinting.business.service.site.*;
import com.pinting.business.util.Constants;
import com.pinting.business.util.FtpClientUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.*;

/**
 * 债权关系匹配
 * @author bianyatian
 * @2016-1-22 下午2:07:29
 */
@Service
public class CheckAddMatchTask {
	private Logger log = LoggerFactory.getLogger(CheckAddMatchTask.class);
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private BsMatchService bsMatchService;
	@Autowired
	private BsMatchMapper bsMatchMapper;
	@Autowired
	private SpecialJnlService specialJnlService;
	@Autowired
	private BsSpecialJnlService bsSpecialJnlService;
	@Autowired
	private BsLoanRelativeRecordMapper bsLoanRelativeRecordMapper;
	@Autowired
	private BsAccountMapper bsAccountMapper;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;
	@Autowired
	private LoanRelationService loanRelationService;
	@Autowired
	private BsLoanRelativeAmountChangeMapper bsLoanRelativeAmountChangeMapper;
	@Autowired
	private BsAgentViewConfigService bsAgentViewConfigService;
	
	String propertySymbol_YunDai = Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI;
	
	public void execute() {
		//oldLoanMatch(); //2015-12-1之前的债权关系匹配
		//newLoanMatch(); //2015-12-1之后的债权关系匹配
		try {
			getDafyLoanRelationAll4NewTask();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("==================日终【获取达飞云贷2.0全量债权关系数据】报错====================");
		}
		//理财到期，匹配关系结束，金额回退重新匹配
		matchOver();
		//匹配金额变动还款处理
		repayAmountChange();
		//当日新增数据的匹配
		newAddLoanMatch(); 
		//针对债权不足的补丁操作
		loanPatch();
		log.info("==================日终【云贷债权匹配结束】====================");
		
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
	                    loanRelative.setLenderIdCard(StringUtil.isEmpty(relation[4])?"1303**********0324":relation[4]);    // 借出人身份证
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
	 * 理财到期，匹配关系结束，金额回退重新匹配
	 * 1、查询 尚有匹配数据、理财已到期、债权非“已全部还款” 的匹配数据列表
	 * 2、针对上述列表，分3种情况处理
	 * 2.1、此债权无任何还款变动：则将匹配置为结束，记录还款表，直接回退金额至债权数据表
	 * 2.2、有还款变动尚未处理，且匹配结束金额<还款变动金额：则将匹配置为结束，记录还款表，待处理还款变动金额减少
	 * 2.3、有还款变动尚未处理，且匹配结束金额>=还款变动金额：则将匹配置为结束，记录还款表，还款变动记录直接设为已处理，并回退部分金额至债权数据表
	 */
	private void matchOver() {
		log.info("===============云贷【理财到期，匹配关系结束】开始=================");
		//查询 尚有匹配数据、理财已到期、债权非“已全部还款”、 债权记录的资金源为云贷的匹配数据列表
		final List<BsMatch> matches = bsMatchService.findOverMatches(propertySymbol_YunDai);
		if(!CollectionUtils.isEmpty(matches)){
			//将匹配置为结束，记录还款表，金额回退
			transactionTemplate.execute(new TransactionCallbackWithoutResult(){
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					for (final BsMatch bsMatch : matches) {
						Double overMatchAmount = bsMatch.getLeftPrincipal();
						//查询金额变动表是否有记录
						BsLoanRelativeAmountChangeExample changeExample = new BsLoanRelativeAmountChangeExample();
						changeExample.createCriteria().andDealStatusEqualTo(Constants.MATCH_AMOUNT_DEAL_STATUS_N)
							.andLoanRelativeIdEqualTo(bsMatch.getLoanRelativeId())
							.andPropertySymbolEqualTo(propertySymbol_YunDai);
						List<BsLoanRelativeAmountChange> changes = bsLoanRelativeAmountChangeMapper.selectByExample(changeExample);
						//将匹配置为结束，记录还款表
						bsMatchService.modifyMatch4Repay(bsMatch, overMatchAmount);
						//无还款金额变动，直接回退金额至债权数据表
						if(CollectionUtils.isEmpty(changes)){
							log.info("===============云贷【理财到期，匹配关系结束】债权编号【"+bsMatch.getLoanRelativeId()+"】无还款金额变动，直接回退金额至债权数据表=================");
							BsLoanRelativeRecord record = bsLoanRelativeRecordMapper.selectByPrimaryKey(bsMatch.getLoanRelativeId());
							Double leftAmount = MoneyUtil.add(record.getLeftAmount(), overMatchAmount).doubleValue();
							record.setLeftAmount(leftAmount);
							bsLoanRelativeRecordMapper.updateByPrimaryKeySelective(record);
						}
						//有还款金额变动，回退金额需比较处理
						else{
							BsLoanRelativeAmountChange change = changes.get(0);
							Double totalRepayAmount = change.getRepayAmount();
							//匹配结束金额<还款变动金额
							if(overMatchAmount < totalRepayAmount){
								log.info("===============云贷【理财到期，匹配关系结束】债权编号【"+bsMatch.getLoanRelativeId()+"】匹配结束金额<还款变动金额，还款变动金额变少=================");
								change.setUpdateTime(new Date());
								change.setRepayAmount(MoneyUtil.subtract(totalRepayAmount, overMatchAmount).doubleValue());
								bsLoanRelativeAmountChangeMapper.updateByPrimaryKeySelective(change);
							}
							//匹配结束金额>=还款变动金额
							else{
								log.info("===============云贷【理财到期，匹配关系结束】债权编号【"+bsMatch.getLoanRelativeId()+"】匹配结束金额>=还款变动金额，还款变动记录作已处理操作，并剩余部分金额回退=================");
								change.setUpdateTime(new Date());
								change.setDealStatus(Constants.MATCH_AMOUNT_DEAL_STATUS_Y);
								bsLoanRelativeAmountChangeMapper.updateByPrimaryKeySelective(change);
								Double overAmount = MoneyUtil.subtract(overMatchAmount, totalRepayAmount).doubleValue();
								if(overAmount > 0){
									BsLoanRelativeRecord record = bsLoanRelativeRecordMapper.selectByPrimaryKey(bsMatch.getLoanRelativeId());
									Double leftAmount = MoneyUtil.add(record.getLeftAmount(), overAmount).doubleValue();
									record.setLeftAmount(leftAmount);
									bsLoanRelativeRecordMapper.updateByPrimaryKeySelective(record);
								}
							}
						}
					}
				}
			});
			
		}else{
			log.info("===============云贷【理财到期，匹配关系结束】无理财到期的匹配数据列表=================");
		}
		log.info("===============云贷【理财到期，匹配关系结束】结束=================");
	}

	/**
	 * 根据匹配金额变动，针对已匹配的债权关系进行借款金额修改和还款明细记录
	 * 1、查询匹配金额变动表，筛选出未处理数据列表A
	 * 2、遍历A{
	 * 		2.1、
	 * 		查询债权表是否有剩余未匹配金额，{
	 * 			如果剩余未匹配金额<变动还款金额，还款金额变少，并修改剩余未匹配金额为0
	 * 			如果剩余未匹配金额>=变动还款金额，修改剩余未匹配金额变少，并修改变动还款记录状态为已处理，continue
	 * 		}
	 * 		2.2、
	 * 		若其中元素a，在匹配表中无关联，则直接修改a为已处理；
	 * 		若其中元素a，在匹配表中有关联，得到关联列表B（按照金额从小到大排序）
	 * 			遍历B{
	 * 				修改元素b的借款金额，或改状态
	 * 				并在还款明细表中记录此变动
	 * 			}，
	 * 			遍历B结束后，修改a为已处理
	 * }
	 */
	private void repayAmountChange(){
		log.info("===============云贷【还款金额匹配】开始=================");
		BsLoanRelativeAmountChangeExample amountChangeExample = new BsLoanRelativeAmountChangeExample();
		amountChangeExample.createCriteria().andDealStatusEqualTo(Constants.MATCH_AMOUNT_DEAL_STATUS_N)
			.andPropertySymbolEqualTo(propertySymbol_YunDai);
		//查询匹配金额变动表，筛选出未处理数据列表A
		List<BsLoanRelativeAmountChange> amountChanges = bsLoanRelativeAmountChangeMapper.selectByExample(amountChangeExample);
		if(!CollectionUtils.isEmpty(amountChanges)){
			log.info("===============云贷【还款金额匹配】新增["+amountChanges.size()+"]条金额变动需匹配处理=================");
			for (BsLoanRelativeAmountChange amountChange : amountChanges) {
				//查询债权表是否有剩余未匹配金额
				BsLoanRelativeRecord loanRelativeRecord = bsLoanRelativeRecordMapper.selectByPrimaryKey(amountChange.getLoanRelativeId());
				if(loanRelativeRecord.getLeftAmount() > 0){
					//如果剩余未匹配金额<变动还款金额
					if(loanRelativeRecord.getLeftAmount() < amountChange.getRepayAmount()){
						//还款金额变少，并修改剩余未匹配金额为0
						Double realRepayAmount = MoneyUtil.subtract(amountChange.getRepayAmount(), loanRelativeRecord.getLeftAmount()).doubleValue();
						amountChange.setRepayAmount(realRepayAmount);
						
						BsLoanRelativeRecord updateRecord = new BsLoanRelativeRecord();
						updateRecord.setId(amountChange.getLoanRelativeId());
						updateRecord.setLeftAmount(0d);
						bsLoanRelativeRecordMapper.updateByPrimaryKeySelective(updateRecord);
					}
					//如果剩余未匹配金额>=变动还款金额
					else{
						//修改剩余未匹配金额变少，并修改变动还款记录状态为已处理，continue
						Double realLeftAmount = MoneyUtil.subtract(loanRelativeRecord.getLeftAmount(), amountChange.getRepayAmount()).doubleValue();
						BsLoanRelativeRecord updateRecord = new BsLoanRelativeRecord();
						updateRecord.setId(amountChange.getLoanRelativeId());
						updateRecord.setLeftAmount(realLeftAmount);
						bsLoanRelativeRecordMapper.updateByPrimaryKeySelective(updateRecord);
						
						BsLoanRelativeAmountChange updateAmountChange = new BsLoanRelativeAmountChange();
						updateAmountChange.setId(amountChange.getId());
						updateAmountChange.setUpdateTime(new Date());
						updateAmountChange.setDealStatus(Constants.MATCH_AMOUNT_DEAL_STATUS_Y);
						bsLoanRelativeAmountChangeMapper.updateByPrimaryKeySelective(updateAmountChange);
						continue;
					}
				}
				
				//查询匹配表是否有关联
				List<BsMatch> matches = bsMatchService.findMatchesByLoanRelativeId(amountChange.getLoanRelativeId());
				//在匹配表中无关联，则直接修改a为已处理
				if(CollectionUtils.isEmpty(matches)){
					BsLoanRelativeAmountChange updateAmountChange = new BsLoanRelativeAmountChange();
					updateAmountChange.setId(amountChange.getId());
					updateAmountChange.setUpdateTime(new Date());
					updateAmountChange.setDealStatus(Constants.MATCH_AMOUNT_DEAL_STATUS_Y);
					bsLoanRelativeAmountChangeMapper.updateByPrimaryKeySelective(updateAmountChange);
				}
				//在匹配表中有关联，遍历B
				else{
					//处理前初始还款金额
					Double totalRepayAmount = amountChange.getRepayAmount();
					for (BsMatch bsMatch : matches) {
						//剩余借款本金
						Double leftPrinciple = bsMatch.getLeftPrincipal();
						//如果剩余借款本金>=还款金额，则修改剩余本金，并记录还款明细，还款金额匹配完成跳出循环
						if(leftPrinciple >= totalRepayAmount){
							bsMatchService.modifyMatch4Repay(bsMatch, totalRepayAmount);
							totalRepayAmount = 0d;
							break;
						}
						//如果剩余借款本金<还款金额，则修改剩余本金为0，并记录还款明细
						else{
							bsMatchService.modifyMatch4Repay(bsMatch, leftPrinciple);
							totalRepayAmount = MoneyUtil.subtract(totalRepayAmount, leftPrinciple).doubleValue();
						}
					}
					if(totalRepayAmount > 0){
						log.warn("===============云贷【还款金额匹配】还款编号["+amountChange.getId()+"]还款金额未完全匹配=================");
					}
					//遍历B结束后，修改a为已处理
					BsLoanRelativeAmountChange updateAmountChange = new BsLoanRelativeAmountChange();
					updateAmountChange.setId(amountChange.getId());
					updateAmountChange.setUpdateTime(new Date());
					updateAmountChange.setDealStatus(Constants.MATCH_AMOUNT_DEAL_STATUS_Y);
					bsLoanRelativeAmountChangeMapper.updateByPrimaryKeySelective(updateAmountChange);
				}
			}
		}
		log.info("===============云贷【还款金额匹配】结束=================");
	}
	

	
	/**
	 * 债权匹配
	 * 
	 * 1.查询用户渠道为钱报的状态status为2-投资中，且product_type='REG'的数据 - 按金额倒序排---列表A
	 * 2.查询用户渠道为非钱报的状态status为2-投资中，且product_type='REG'的数据 - 按金额倒序排---列表B
	 * AB匹配规则只是在金额控制方面不同
	 * 3.循环列表A+B{
	 * 		1)sub_account_id 查询已匹配金额a,c = b(A的购买金额) - a 
	 * 		if(c > 0){
	 *			根据理财产品购买时间按金额倒序查询债权记录数据--列表C
	 *			循环列表C{
	 *				匹配金额d，添加债权匹配关系，c = c- d;
	 *				修改债权记录数据的剩余匹配金额
	 *			}
	 * 		}
	 * }
	 * 
	 * 
	 */
	private void newAddLoanMatch() {
		String time = sysConfigService.findConfigByKey(Constants.MATCH_BUY_START_TIME).getConfValue();
		Date buyLimitTime = DateUtil.parseDate(time); //理财购买时间在该时间之后的数据将会保存
		Date today = DateUtil.addDays(DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date())), -1);
		//起息日在理财购买后一日 
		Date interestBeginLimitDate = DateUtil.addDays(buyLimitTime, 1);
		Integer oldCount = bsSubAccountMapper.countNewSubAccountREGList(interestBeginLimitDate, today, propertySymbol_YunDai);
		if(oldCount >0){
			Integer pageSize = 100;
			Integer c = oldCount/pageSize + (oldCount%pageSize> 0?1:0);
			for(int i=0;i<c;i++){
				//获取起息日在interestBeginLimitDate之后，且status为2-投资中, 债权关系匹配且钱报用户的投资在前
				List<BsSubAccount> list = bsSubAccountMapper.getNewSubAccountREGList(i*pageSize,pageSize,interestBeginLimitDate, today, propertySymbol_YunDai);
				if(list != null && list.size()>0){
					log.info("===============定时任务【云贷-债权关系匹配-新】"+interestBeginLimitDate+"之后status为2,获取列表："+list.size()+"=================");
					dayfLoanSubAccountMatch(list);
				}
			}
			
		}
		
		
	}

	//具体匹配
	private void dayfLoanSubAccountMatch(List<BsSubAccount> accountList) {
		//说明：BsSubAccount中的note目前为用户的agent_id，当为15时匹配金额需做特殊处理
		log.info("==========定时任务【云贷-债权关系匹配-达飞云贷2.0-全量】循环匹配开始==========");
		if(accountList != null && accountList.size()>0){
			for (BsSubAccount bsSubAccount : accountList) {
				try {
					//已经匹配的金额
					Double matchedAmount = bsMatchService.sumAccountRepay(bsSubAccount.getId(),
							Constants.LOAN_RELATIVE_CANNEL_DAFY_NEW_ALL, Constants.LOAN_RELATIVE_IS_REPAY_N);
					//理财金额<已匹配金额，告警
					if(bsSubAccount.getOpenBalance().compareTo(matchedAmount)< 0){
						//2.2 productAmount < matchAmount 告警
						//告警
						specialJnlService.warn4Fail(null, "{债权关系匹配}异常：subAccountId:"+bsSubAccount.getId()
								+"理财金额:"+bsSubAccount.getOpenBalance()+"已匹配金额:"+matchedAmount,
								null,"债权关系匹配",true);
					}else if(bsSubAccount.getOpenBalance().compareTo(matchedAmount) > 0){
						//还有金额未匹配进行匹配
						Double needMatchAmount = MoneyUtil.subtract(bsSubAccount.getOpenBalance(), matchedAmount).doubleValue(); //需要匹配的金额
						String buyTimeEnd = DateUtil.format(bsSubAccount.getInterestBeginDate());
						String buyTimeStart = buyTimeEnd;
						matchNeedAmount(needMatchAmount, buyTimeStart, buyTimeEnd, bsSubAccount);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 匹配尚未完成的匹配
	 * @param needMatchAmount
	 * @param buyTimeStart
	 * @param buyTimeEnd
	 * @param bsSubAccount
	 */
	private void matchNeedAmount(Double needMatchAmount, String buyTimeStart, String buyTimeEnd, BsSubAccount bsSubAccount){
		if(needMatchAmount > 0){
			List<BsLoanRelativeRecord> loanList = new ArrayList<BsLoanRelativeRecord>();
			loanList = bsLoanRelativeRecordMapper.selectByBuyTime(buyTimeStart, buyTimeEnd, propertySymbol_YunDai); //获取符合匹配条件的债权数据
			
			Integer userId = bsAccountMapper.selectByPrimaryKey(bsSubAccount.getAccountId()).getUserId(); //用户id
			if(loanList != null && loanList.size()>0){
				for (BsLoanRelativeRecord bsLoanRelativeRecord : loanList) {
					//得到匹配金额
					Double matchAmount = getMatchAmount(bsLoanRelativeRecord.getLeftAmount(),bsSubAccount.getNote(),needMatchAmount);
					if(matchAmount > 0){
						match(matchAmount,userId,bsSubAccount.getId(),bsLoanRelativeRecord.getId(),bsLoanRelativeRecord.getLeftAmount());
						needMatchAmount = MoneyUtil.subtract(needMatchAmount, matchAmount).doubleValue();
					}
					if(needMatchAmount == 0){
						break;
					}
				}
				
				matchNeedAmount(needMatchAmount, buyTimeStart, buyTimeEnd, bsSubAccount);
				
			}else{
				log.warn("==========定时任务【债权关系匹配-达飞云贷2.0-全量】编号"+bsSubAccount.getId()+"无债权数据列表匹配或未匹配完全==========");
				String type = "定时任务【债权关系匹配-达飞云贷2.0-全量】";
				String detail = "定时任务【债权关系匹配-达飞云贷2.0-全量】编号"+bsSubAccount.getId()+"无债权数据列表匹配或未匹配完全";
				//bsSpecialJnlService.addSpecialJnl(type, detail);
			}
		}
	}
	
	/**
	 * 根据可匹配金额和用户的渠道算可匹配金额
	 * @param leftAmount ---》bsLoanRelativeRecord表中的剩余匹配金额
	 * @param note ---》agentid
	 * @param needMatchAmount ---》需要匹配的金额
	 * @return
	 */
	private Double getMatchAmount(Double leftAmount, String note, Double needMatchAmount) {
		Double matchAmount = 0.0;
		if(StringUtils.isNotBlank(note) && bsAgentViewConfigService.isQianbao(Integer.parseInt(note))){
			/*钱报用户
			 * needMatchAmount<=5000，直接匹配
			 * needMatchAmount>5000，计算可以匹配的金额
			 */
			if(needMatchAmount <= 5000){
				matchAmount = toMatch(needMatchAmount,leftAmount);
			}else{
				Double canMatchAmount = getRand178Amount();
				//判断计算出来的可匹配金额是否大于需要匹配的金额，若canMatchAmount>needMatchAmount,应该匹配needMatchAmount
				if(MoneyUtil.subtract(canMatchAmount,needMatchAmount).doubleValue() > 0){
					canMatchAmount = needMatchAmount; 
				}
				matchAmount = toMatch(canMatchAmount,leftAmount);
			}

		}else{
			/*非钱报用户
			 * needMatchAmount：0~2万，取0~5千随机数；
			 * 					2~10万，取5千~2万随机数；
			 * 					10万~50万，取2万~4万随机数；
			 * 					>50万，取4~5万随机数
			 */
			Random rand = new Random();
			Double canMatchAmount = 0.0;
			if(needMatchAmount <= 20000){
				canMatchAmount = (double)((rand.nextInt(50)+1)*100); //[100,5000]
				//判断计算出来的可匹配金额是否大于需要匹配的金额，若canMatchAmount>needMatchAmount,应该匹配needMatchAmount
				if(MoneyUtil.subtract(canMatchAmount,needMatchAmount).doubleValue() > 0){
					canMatchAmount = needMatchAmount; 
				}
			}else if(needMatchAmount > 20000 && needMatchAmount <= 100000){
				canMatchAmount = (double)((rand.nextInt(150)+1)*100+5000); //(5000,20000]
				if(MoneyUtil.subtract(canMatchAmount,needMatchAmount).doubleValue() > 0){
					canMatchAmount = needMatchAmount; 
				}
			}else if(needMatchAmount > 100000 && needMatchAmount <= 500000){
				canMatchAmount = (double)((rand.nextInt(200)+1)*100+20000); //(2000,40000]
				if(MoneyUtil.subtract(canMatchAmount,needMatchAmount).doubleValue() > 0){
					canMatchAmount = needMatchAmount; 
				}
			}else{
				canMatchAmount = (double)((rand.nextInt(100)+1)*100+40000); //(40000,50000]
				if(MoneyUtil.subtract(canMatchAmount,needMatchAmount).doubleValue() > 0){
					canMatchAmount = needMatchAmount; 
				}
			}
			matchAmount = toMatch(canMatchAmount,leftAmount);
		}
		return matchAmount;
	}

	/**
	 *	canMatchAmount<=leftAmount,匹配canMatchAmount，否则，匹配leftAmount
	 * @param canMatchAmount
	 * @param leftAmount
	 * @return
	 */
	private Double toMatch(Double canMatchAmount, Double leftAmount) {
		Double matchAmount = 0.0;
		if(MoneyUtil.subtract(canMatchAmount,leftAmount).doubleValue() <=0 ){
			matchAmount = canMatchAmount;
		}else{
			matchAmount = leftAmount;
		}
		return matchAmount;
	}

	/**
	 * 获得钱报用户的随机匹配金额
	 * [1,4]取随机数， luckyNumber>3,则在3000-4000取随机数， luckyNumber<=3,则在4000-5000取随机数
	 * @return
	 */
	private Double getRand178Amount() {
		Random rand = new Random();
		Double getRand178Amount = 3000.0;
        Integer luckyNumber = rand.nextInt(4) + 1; // [1,4]
        if(luckyNumber<=3){
        	getRand178Amount = (double)((rand.nextInt(10)+1)*100+4000); //(4000,5000]
        }else{
        	getRand178Amount = (double)((rand.nextInt(10)+1)*100+3000); //(3000,4000]
        }
		return getRand178Amount;
	}
	

	/**
	 * 1.1 获取起息日在2015-12-1之后，且status为2-投资中,且product_type='REG'的数据 - 按金额倒序排
	 * 1.2 传列表进行债权关系匹配
	 * 
	 * 2.1 获取BsSysConfig的债权匹配限制时间，该时间前对已结算或结算中的数据进行匹配
	 * 2.2 获取起息日在2015-12-1之后，且status为5-已结算或7-结算中,且product_type='REG'的数据 - 按金额倒序排
	 * 2.3 传列表进行债权关系匹配
	 */
	@Deprecated
	private void newLoanMatch() {
		log.info("===============定时任务【债权关系匹配-新】开始=================");
		Date today = DateUtil.addDays(new Date(), -1);
		try {
			Integer oldCount = bsSubAccountMapper.countNewSubAccountREGList(DateUtil.parseDate("2015-12-01"), today, propertySymbol_YunDai);
			if(oldCount >0){
				Integer pageSize = 100;
				Integer c = oldCount/pageSize + (oldCount%pageSize> 0?1:0);
				for(int i=0;i<c;i++){
					//获取起息日在2015-12-1之后，且status为2-投资中, 债权关系匹配
					List<BsSubAccount> list = bsSubAccountMapper.getNewSubAccountREGList(i*pageSize,pageSize,DateUtil.parseDate("2015-12-01"), today, propertySymbol_YunDai);
					if(list != null && list.size()>0){
						log.info("===============定时任务【债权关系匹配-新】2015-12-1之后status为2,获取列表："+list.size()+"=================");
						newLoanMatch(list);
					}
				}
				
			}
			
			
			//获取起息日在2015-12-1之后，且status为5-已结算或7-结算中, 债权关系匹配
			BsSysConfig config = sysConfigService.findConfigByKey(Constants.MATCH_LIMIT_TIME);
			if(config != null){
				Date now = new Date();
				Date limitDate = DateUtil.parseDate(config.getConfValue());
				log.info("===============now:"+now+"limitDate(配置表查到时间)："+limitDate);
				if(now.getTime() < limitDate.getTime()){
					Integer count = bsSubAccountMapper.countNewSubAccountREGReturnList();
					if(count > 0){
						Integer pageSize = 100;
						Integer c = count/pageSize + (count%pageSize> 0?1:0);
						for(int i=0;i<c;i++){
							List<BsSubAccount> accountList = bsSubAccountMapper.getNewSubAccountREGReturnList(i*pageSize,pageSize);
							if(accountList != null && accountList.size()>0){
								log.info("===============定时任务【债权关系匹配-新】2015-12-1之后status为5或7,获取列表："+accountList.size()+"=================");
								newLoanMatch(accountList);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("===============定时任务【债权关系匹配-新】出错================="+e);
		}
		
	}

	/**
	 * 循环列表，进行债权匹配
	 * 1. 根据sub_account_id查询bs_match表中，关联bs_loan_relative_record表中，未还款的is_repay = 'N'的match的金额总和matchAmount
	 * 即已匹配未还款的总金额 matchAmount
	 * 	1.1 productAmount == matchAmount 不操作
	 * 	1.2 productAmount < matchAmount 告警
	 * 	1.3 productAmount > matchAmount 匹配
	 * 2. 匹配
	 * 2.1 根据sub_account_id找到未匹配的left_amount为0的记录
	 * 2.2 需要匹配的金额needMatchAmount = productAmount - matchAmount > 0 时，循环匹配
	 * 3.循环匹配
	 * 	3.1 needMatchAmount >= leftAmount,匹配金额为leftAmount-->B,needMatchAmount = needMatchAmount - leftAmount;
	 * 	3.2 needMatchAmount < leftAmount,匹配金额为needMatchAmount-->B,needMatchAmount=0
	 * 4.完成匹配，新增匹配表数据，修改bs_loan_relative_record中leftAmount = leftAmount-B
	 * @param accountList
	 */
	@Deprecated
	private void newLoanMatch(List<BsSubAccount> accountList) {
		log.info("==========定时任务【债权关系匹配-新】循环匹配开始==========");
		if(accountList != null && accountList.size()>0){
			for (BsSubAccount bsSubAccount : accountList) {
				try {
					//1. 根据sub_account_id查询bs_match表中，关联bs_loan_relative_record表中，未还款的is_repay = 'N'的match的金额总和matchAmount
					Double matchAmount = bsSubAccount.getOpenBalance();
					if(Constants.SUBACCOUNT_STATUS_FINANCING.equals(bsSubAccount.getStatus())){
						//状态为投资中，查询已记录债权关系的未还款的总金额
						matchAmount = bsMatchService.sumAccountRepay(bsSubAccount.getId(),
								Constants.LOAN_RELATIVE_CANNEL_DAFY_NEW, Constants.LOAN_RELATIVE_IS_REPAY_N);
					}else{
						matchAmount = bsMatchService.sumAccountRepay(bsSubAccount.getId(),
								Constants.LOAN_RELATIVE_CANNEL_DAFY_NEW,null);
					}
					log.info("==========定时任务【债权关系匹配-新】matchAmount:"+matchAmount+";productAmount:"+bsSubAccount.getOpenBalance()+"==========");
					if(bsSubAccount.getOpenBalance().compareTo(matchAmount)< 0){
						//2.2 productAmount < matchAmount 告警
						//告警
						specialJnlService.warn4Fail(null, "{债权关系匹配}异常：subAccountId:"+bsSubAccount.getId()
								+"productAmount:"+bsSubAccount.getOpenBalance()+"matchAmount:"+matchAmount,
								null,"债权关系匹配",true);
					}else if(bsSubAccount.getOpenBalance().compareTo(matchAmount) > 0){
						List<BsLoanRelativeRecord> loanList = new ArrayList<BsLoanRelativeRecord>();
						if(Constants.SUBACCOUNT_STATUS_FINANCING.equals(bsSubAccount.getStatus())){
							//状态为投资中，查询未还款的列表
							loanList = bsLoanRelativeRecordMapper.selectBySubAccountId(bsSubAccount.getId(), Constants.LOAN_RELATIVE_IS_REPAY_N) ;
							
						}else{
							loanList = bsLoanRelativeRecordMapper.selectBySubAccountId(bsSubAccount.getId(), null) ;
						}
						if(loanList != null && loanList.size()>0){
							Double needMatchAmount = MoneyUtil.subtract(bsSubAccount.getOpenBalance(), matchAmount).doubleValue(); //需要匹配的金额
							BsAccount bsAccount = bsAccountMapper.selectByPrimaryKey(bsSubAccount.getAccountId());
							for (BsLoanRelativeRecord loanRelative : loanList) {
								try {
									//判断是否需要继续匹配
									if(needMatchAmount.compareTo(0d)>0){
										if(needMatchAmount.compareTo(loanRelative.getLeftAmount()) >= 0){
											log.info("==========定时任务【债权关系匹配-新】新增,匹配金额："+loanRelative.getLeftAmount()+";用户id:"+bsAccount.getUserId()+";loanRelativeId:"+loanRelative.getId());
											match(loanRelative.getLeftAmount(),bsAccount.getUserId(),bsSubAccount.getId(),loanRelative.getId(),loanRelative.getLeftAmount());
											needMatchAmount = MoneyUtil.subtract(needMatchAmount, loanRelative.getLeftAmount()).doubleValue();
										}else{
											log.info("==========定时任务【债权关系匹配-新】新增,匹配金额："+needMatchAmount+";用户id:"+bsAccount.getUserId()+";loanRelativeId:"+loanRelative.getId());
											match(needMatchAmount,bsAccount.getUserId(),bsSubAccount.getId(),loanRelative.getId(),loanRelative.getLeftAmount());
											needMatchAmount = 0d;
										}
									}
								} catch (Exception e) {
									log.error("定时任务【债权关系匹配-新】匹配出错BsLoanRelativeRecord-id:【"+loanRelative.getId()+"】"+e);
								}
								
							}
						}
					}
				} catch (Exception e) {
					log.error("定时任务【债权关系匹配-新】匹配出错BsSubAccount-id:【"+bsSubAccount.getId()+"】"+e);
				}
				
			}
		}
	}


	/**
	 * 2015-12-1之前的债权关系匹配
	 * 1.获取起息日在2015-12-1及之前，且status为2-投资中,且product_type='REG'的数据列表，其中购买产品金额为productAmount
	 * 2.根据sub_account_id查询bs_match表中，关联bs_loan_relative_record表中，未还款的is_repay = 'N'的match的金额总和
	 * 		即已匹配未还款的总金额 matchAmount
	 * 	2.1 productAmount == matchAmount 不操作
	 * 	2.2 productAmount < matchAmount 告警
	 * 	2.3 productAmount > matchAmount 匹配
	 * 3.匹配
	 *  3.1 查找购买了该产品的用户，在债权记录表中相应产品的，同用户的所有匹配金额不为0的记录
	 *  3.2 需要匹配的金额needMatchAmount = productAmount - matchAmount > 0 时，循环匹配
	 * 4.循环匹配
	 * 	4.1 needMatchAmount >= leftAmount,匹配金额为leftAmount-->B,needMatchAmount = needMatchAmount - leftAmount;
	 * 	4.2 needMatchAmount < leftAmount,匹配金额为needMatchAmount-->B,needMatchAmount=0
	 * 5.完成匹配，新增匹配表数据，修改bs_loan_relative_record中leftAmount = leftAmount-B
	 */
	private void oldLoanMatch() {
		List<BsSubAccount> accountList = new ArrayList<BsSubAccount>();
		Integer count = bsSubAccountMapper.countOldSubAccountREGList();
		if(count > 0){
			Integer pageSize = 100;
			Integer c = count/pageSize + (count%pageSize> 0?1:0);
			for(int i=0;i<c;i++){
				//1. 获取起息日在2015-12-1及之前，且status为2-投资中,且product_type='REG'的数据列表，其中购买产品金额为productAmount
				accountList = bsSubAccountMapper.getOldSubAccountREGList(i*pageSize,pageSize);
				if(accountList != null && accountList.size()>0){
					log.info("===============定时任务【债权关系匹配-旧】2015-12-1之前status为2,获取列表："+accountList.size()+"=================");
					for (BsSubAccount bsSubAccount : accountList) {
						try {
							//2. 根据sub_account_id查询bs_match表中，关联bs_loan_relative_record表中，未还款的is_repay = 'N'的match的金额总和matchAmount
							Double matchAmount = bsMatchService.sumAccountRepay(bsSubAccount.getId(),
									Constants.LOAN_RELATIVE_CANNEL_DAFY_OLD, Constants.LOAN_RELATIVE_IS_REPAY_N);
							log.info("==========定时任务【债权关系匹配-旧】matchAmount:"+matchAmount+";productAmount:"+bsSubAccount.getOpenBalance()+"==========");
							if(bsSubAccount.getOpenBalance().compareTo(matchAmount)< 0){
								//2.2 productAmount < matchAmount 告警
								//告警
								specialJnlService.warn4Fail(null, "{债权关系匹配}异常：subAccountId:"+bsSubAccount.getId()
										+"productAmount:"+bsSubAccount.getOpenBalance()+"matchAmount:"+matchAmount,
										null,"债权关系匹配",true);
							}else if(bsSubAccount.getOpenBalance().compareTo(matchAmount) > 0){
								
								//2.3 productAmount > matchAmount 匹配,获取可以匹配的数据，查询未匹配金额>0,未还款,用户对应，产品对应
								List<BsLoanRelativeRecord> loanList = bsLoanRelativeRecordMapper.selectByAccountIdProName(bsSubAccount.getAccountId(),
										ProductCodeNameEnum.getEnumByCode(bsSubAccount.getProductCode()).getProductName());
								if(loanList != null && loanList.size()>0){
									BsAccount bsAccount = bsAccountMapper.selectByPrimaryKey(bsSubAccount.getAccountId());
									Double needMatchAmount = MoneyUtil.subtract(bsSubAccount.getOpenBalance(), matchAmount).doubleValue(); //需要匹配的金额
									for (BsLoanRelativeRecord loanRelative : loanList) {
										try {
											//判断是否需要继续匹配
											if(needMatchAmount.compareTo(0d)>0){
												if(needMatchAmount.compareTo(loanRelative.getLeftAmount()) >= 0){
													log.info("==========定时任务【债权关系匹配-旧】新增,匹配金额："+loanRelative.getLeftAmount()+";用户id:"+bsAccount.getUserId()+";loanRelativeId:"+loanRelative.getId());
													match(loanRelative.getLeftAmount(),bsAccount.getUserId(),bsSubAccount.getId(),loanRelative.getId(),loanRelative.getLeftAmount());
													needMatchAmount = MoneyUtil.subtract(needMatchAmount, loanRelative.getLeftAmount()).doubleValue();
												}else{
													log.info("==========定时任务【债权关系匹配-旧】新增,匹配金额："+needMatchAmount+";用户id:"+bsAccount.getUserId()+";loanRelativeId:"+loanRelative.getId());
													match(needMatchAmount,bsAccount.getUserId(),bsSubAccount.getId(),loanRelative.getId(),loanRelative.getLeftAmount());
													needMatchAmount = 0d;
												}
											}
										} catch (Exception e) {
											log.error("定时任务【债权关系匹配-旧】匹配出错BsLoanRelativeRecord-id:【"+loanRelative.getId()+"】"+e);
										}
									}
								}
							}
						} catch (Exception e) {
							log.error("==========定时任务【债权关系匹配-旧】匹配出错BsSubAccount-id:【"+bsSubAccount.getId()+"】"+e);
						}
						
					}
				}
			}
		}
		
		
		
	}

	/**
	 * 记录匹配数据，修改记录表的剩余匹配金额
	 * @param amount 匹配金额
	 * @param userId 用户id
	 * @param subAccountId 子账户id
	 * @param recordId loanRelativeRecord ID
	 * @param leftAmount 本地剩余匹配金额
	 */
	private void match(final Double amount, final Integer userId, final Integer subAccountId,
			final Integer recordId, final Double leftAmount) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				//新增BsMatch表
				BsMatch bsMatch = new BsMatch();
				bsMatch.setAmount(amount);
				bsMatch.setRepayAmount(0.0);
				bsMatch.setLeftPrincipal(amount);
				bsMatch.setRepayStatus(Constants.BORROW_ING); //借款中
				bsMatch.setLoanRelativeId(recordId);
				bsMatch.setPropertySymbol(propertySymbol_YunDai);
				bsMatch.setSubAccountId(subAccountId);
				bsMatch.setUserId(userId);
				bsMatch.setNote("借款中");
				bsMatchService.addBsMatch(bsMatch);
				//修改bs_loan_relative_record
				BsLoanRelativeRecord loanRelativeRecord = new BsLoanRelativeRecord();
				loanRelativeRecord.setId(recordId);
				loanRelativeRecord.setLeftAmount(MoneyUtil.subtract(leftAmount, amount).doubleValue());
				loanRelativeRecord.setUpdateTime(new Date());
				bsLoanRelativeRecordMapper.updateByPrimaryKeySelective(loanRelativeRecord);
				}
			});
	}
	

	/**
	 * 针对债权不足的补丁操作
	 * 1.根据时间分组查询匹配额和投资额相差100元以上的列表A
	 * 作A的for循环{
	 * 	1.1根据投资起息日time查询需要再次分配债权的列表，判断，列表为空，则跳过该日的其他操作，否则继续
	 * 	1.2得到该日投资总额sumInvestBalance、未匹配的总金额sumNoMacthAmount,计算某日所需转让的匹配金额amount4Day
	 * 		percent = sumNoMacthAmount/sumInvestBalance+a(固定增加的比例)
	 * 		amount4Day = (percent)*(sumInvestBalance-sumNoMacthAmount);
	 * 	1.3根据time查询已经完全匹配的和匹配金额超过一定比例且已匹配金额超过一定值的列表 B
	 * 	1.4 作B的for循环{
	 * 		a.计算针对该笔投资所需要转让的债权金额amount4Day4Sub=(percent)*subAmount(某笔投资金额)
	 * 		b.amount4Day>0的前提下，查询该笔投资匹配的债权记录列表C，得到应分配出来的债权数据列表D，新添一个应分配给他人的金额字段（toOtherMatch）
	 * 	}
	 *	1.5.D不为空的前提下，作D的for循环{
	 *		a.根据time查询某日未匹配的列表、且匹配投资比例不超过30%的列表，金额从小到大列表E
	 *		b.E不为空的前提下，作E的for循环{
	 *			分配债权，修改原match表的数据，新增还款记录表的信息，新增新的债权匹配关系。
	 *		}
	 * }
	 * 
	 * 
	 * 
	 */
	private void loanPatch() {
		try {
			Date interestBeginDate = DateUtil.addDays(DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date())), -1); 
			List<Map<String, Object>> noMatchMaps = bsSubAccountMapper.getNoMatchList(interestBeginDate, propertySymbol_YunDai);
			if(!noMatchMaps.isEmpty()){
				log.info("==========定时任务【云贷-债权关系匹配-新】统计日债权匹配差额超100：共" + noMatchMaps.size() + "天==========");
				Double matchedPercent = 0.7;//匹配金额占投资金额的比例
				Double topBalance = 10000d;//已匹配金额的下线
				
				for(Map<String, Object> map : noMatchMaps){
					List<BsMatchVO> mList = new ArrayList<BsMatchVO>(); //记录可以分配的债权数据
					
					Date time = (Date) map.get("interestBeginDate"); //投资起息日
					
					//判断是否有需要再次分配的未完全匹配的债权列表
					//List<Map<String, Object>> needMatch4DayMaps = bsSubAccountMapper.getNeedMatchList(time, matchedPercent,0.3, propertySymbol_YunDai);
					Integer needMatch4DayCount = bsSubAccountMapper.getNeedMatchCount(time, matchedPercent,0.3, propertySymbol_YunDai);
					if(needMatch4DayCount == 0){
						log.info("==========定时任务【云贷-债权关系匹配-新】" + DateUtil.formatYYYYMMDD(time) + "债权无需再次匹配==========");
						continue;
					}else{
						log.info("==========定时任务【云贷-债权关系匹配-新】" + DateUtil.formatYYYYMMDD(time) + "获得" +needMatch4DayCount+ "笔债权需再次匹配==========");
					}
					
					//Double sumBalance = (Double) map.get("sumBalance"); //未匹配对应的投资总额
					Double sumNoMacthAmount = (Double) map.get("sumAmount"); //未匹配的总金额
					Double sumInvestBalance = bsSubAccountMapper.sumInvestBalanceByDate(time, propertySymbol_YunDai); //某日当前投资总额
					
					String value = sysConfigService.findConfigByKey(Constants.MATCH_PATCH_ADD_PERCENT).getConfValue(); //固定比例，用于分配债权
					Double percent = MoneyUtil.divide(sumNoMacthAmount, sumInvestBalance).doubleValue();//未匹配金额和当日投资总额的比例
					if(percent < 1){
						//不是所有投资都未匹配
						percent = MoneyUtil.add(percent,
								MoneyUtil.divide(Double.valueOf(value), 100, 2).doubleValue()).doubleValue();
								/*(double)(percent+Double.valueOf(value)/100);*/ //未匹配金额和当日投资总额的比例+固定比例
						Integer amount4Day = MoneyUtil.multiply(MoneyUtil.subtract(sumInvestBalance, sumNoMacthAmount).doubleValue(), percent).intValue();
						// Integer amount4Day = (int)(percent*(sumInvestBalance-sumNoMacthAmount)); //某日待转让的匹配金额
						
						//根据起息时间查询已经完全匹配的和匹配金额超过一定比例的列表且已匹配金额超过一定值
						List<Map<String, Object>> matchedMaps = bsSubAccountMapper.getMatchedList(time, matchedPercent, topBalance, propertySymbol_YunDai);
						if(!matchedMaps.isEmpty()){
							log.info("==========定时任务【云贷-债权关系匹配-新】" + DateUtil.formatYYYYMMDD(time) + "查到"+matchedMaps.size()+"笔理财可转让债权匹配==========");
							
							for(Map<String, Object> matched : matchedMaps){
								
								Integer amount4Day4Sub  =(int) ((Double)matched.get("matched_amount")*percent); //针对该笔投资所需要转让的债权金额
								if(amount4Day4Sub == 0){
									log.warn("==========定时任务【云贷-债权关系匹配-新】子账户编号" + matched.get("id") + "待转让的匹配金额计算为0，无法转让匹配关系==========");
									continue;
								}
								if(amount4Day4Sub < 10){
									amount4Day4Sub = 10;
								}
								if(amount4Day == 0){
									log.warn("==========定时任务【云贷-债权关系匹配-新】" + DateUtil.formatYYYYMMDD(time) + "待转让的匹配金额为0，无法转让匹配关系==========");
									break;
								}else if(amount4Day < amount4Day4Sub){
									amount4Day4Sub = amount4Day;
								}
								if(amount4Day4Sub > (Double)matched.get("matched_amount")){
									amount4Day4Sub = ((Double)matched.get("matched_amount")).intValue();
								}
								
								
								//根据subAccountId查询已经匹配的匹配数据
								BsMatchExample example = new BsMatchExample();
								example.createCriteria().andSubAccountIdEqualTo((Integer)matched.get("id"))
									.andRepayStatusNotEqualTo("3");
								example.setOrderByClause("left_principal desc");
								List<BsMatch> matchList = bsMatchMapper.selectByExample(example);
								if(matchList != null && matchList.size() > 0){
									for (BsMatch bsMatch : matchList) {
										Double mAmount = bsMatch.getLeftPrincipal();
										BsMatchVO toOtherMatch = new BsMatchVO();
										toOtherMatch.setAmount(bsMatch.getAmount());
										toOtherMatch.setId(bsMatch.getId());
										toOtherMatch.setLastRepayDate(bsMatch.getLastRepayDate());
										toOtherMatch.setLeftPrincipal(bsMatch.getLeftPrincipal());
										toOtherMatch.setLoanRelativeId(bsMatch.getLoanRelativeId());
										toOtherMatch.setMatchDate(bsMatch.getMatchDate());
										toOtherMatch.setNote(bsMatch.getNote());
										toOtherMatch.setRepayAmount(bsMatch.getRepayAmount());
										toOtherMatch.setRepayStatus(bsMatch.getRepayStatus());
										toOtherMatch.setSubAccountId(bsMatch.getSubAccountId());
										toOtherMatch.setUpdateTime(bsMatch.getUpdateTime());
										toOtherMatch.setUserId(bsMatch.getUserId());
										if(mAmount>=amount4Day4Sub){
											//表示该笔匹配记录即可满足所需的转出金额
											toOtherMatch.setToOtherAmount(Double.valueOf(amount4Day4Sub));
											mList.add(toOtherMatch);
											amount4Day = amount4Day - amount4Day4Sub;
											break;
										}else{
											amount4Day4Sub = amount4Day4Sub-mAmount.intValue();
											toOtherMatch.setToOtherAmount(Double.valueOf(mAmount));
											mList.add(toOtherMatch);
											amount4Day = amount4Day - mAmount.intValue();
										}
									}
								}
							}
						}
					
					}
					if(mList != null && mList.size() > 0){
						log.info("==========定时任务【云贷-债权关系匹配-新】" + DateUtil.formatYYYYMMDD(time) + "获得"+mList.size()+"笔匹配明细 待执行关系转让==========");
						//有可以分配的债权
						//查询需要获得债权的列表；
						for (BsMatchVO match : mList) {
							Double beMatchAmount = 0d;//某日，补丁操作中已匹配金额
							//根据time查询某日未匹配的列表、投资金额小于5000的或（投资金额大于5000且匹配投资比例不超过30%）的列表，按投资金额从小到大
							List<Map<String, Object>> needMatchMaps = bsSubAccountMapper.getNeedMatchList(time, matchedPercent,0.3, propertySymbol_YunDai);
							if(!needMatchMaps.isEmpty()){
								Double canMatchAmount = match.getToOtherAmount(); //可以分配的债权金额
								for (Map<String, Object> map2 : needMatchMaps) {
									if(canMatchAmount>0){
										Double needMatchAmount = (Double) map2.get("amount"); //需要分配的债权金额
										Double balance = (Double) map2.get("balance");  //投资金额
										Integer amount4DayInit = (int)(percent*(sumInvestBalance-sumNoMacthAmount)); //某日待转让的匹配金额
										
										if(amount4DayInit < sumNoMacthAmount && balance > 5000){
											//可以分配的债权金额<未匹配金额且投资金额大于5000
											Double p = (MoneyUtil.subtract(amount4DayInit, beMatchAmount).doubleValue())/
													(MoneyUtil.subtract(sumNoMacthAmount, beMatchAmount).doubleValue());
											int needMatchAmountTemp =  (int) (p*needMatchAmount);
											if(needMatchAmountTemp == 0){
												break;
											}else{
												needMatchAmount = (double) needMatchAmountTemp;
											}
										}
										
										Integer subAccountId = (Integer) map2.get("id"); 
										Integer userId = (Integer) map2.get("user_id"); 
										//进行一系列的匹配操作
										if(canMatchAmount > needMatchAmount){
											toMatch(match,needMatchAmount,subAccountId,userId);
											canMatchAmount = MoneyUtil.subtract(canMatchAmount, needMatchAmount).doubleValue();
											beMatchAmount = MoneyUtil.add(beMatchAmount, needMatchAmount).doubleValue();
											
											//用于转让的匹配数据本身的剩余匹配金额发生变化
											match.setLeftPrincipal(MoneyUtil.subtract(match.getLeftPrincipal(), needMatchAmount).doubleValue());
											match.setRepayAmount(MoneyUtil.add(match.getRepayAmount()==null?0:match.getRepayAmount(), needMatchAmount).doubleValue());
										}else{
											toMatch(match,canMatchAmount,subAccountId,userId);
											beMatchAmount = MoneyUtil.add(beMatchAmount, canMatchAmount).doubleValue();
											canMatchAmount = 0d;
										}
										
									}else{
										break;
									}
								}
							}else{
								break;
							}
						}
					}
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void toMatch(BsMatchVO match, Double matchAmount,
			Integer subAccountId, Integer userId) {
		
		//还款
		bsMatchService.modifyMatch4Repay(match, matchAmount);
		//新增BsMatch表
		BsMatch bsMatch = new BsMatch();
		bsMatch.setAmount(matchAmount);
		bsMatch.setRepayAmount(0.0);
		bsMatch.setLeftPrincipal(matchAmount);
		bsMatch.setRepayStatus(Constants.BORROW_ING); //借款中
		bsMatch.setLoanRelativeId(match.getLoanRelativeId());
		bsMatch.setPropertySymbol(propertySymbol_YunDai);
		bsMatch.setSubAccountId(subAccountId);
		bsMatch.setUserId(userId);
		bsMatch.setNote("借款中");
		bsMatchService.addBsMatch(bsMatch);	
			
	}


	public static void main(String[] args) {
		
		Integer count = 1980;
		if(count > 0){
			Integer pageSize = 100;
			Integer c = count/pageSize + (count%pageSize> 0?1:0);
			for(int i=0;i<c;i++){
				//1. 获取起息日在2015-12-1及之前，且status为2-投资中,且product_type='REG'的数据列表，其中购买产品金额为productAmount
				//accountList = bsSubAccountMapper.getOldSubAccountREGList(i*pageSize,pageSize);
				System.out.println(i*pageSize+">>>>>><<<<<<<<"+pageSize);
			}
		}
	}
}
