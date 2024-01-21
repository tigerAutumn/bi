package com.pinting.business.dayend.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.loan.enums.FilePath;
import com.pinting.business.accounting.loan.enums.HFAccountTypeEnum;
import com.pinting.business.accounting.loan.util.UncompressUtil;
import com.pinting.business.dao.BsHfbankBalanceCheckRecordMapper;
import com.pinting.business.dao.BsSysAccountJnlMapper;
import com.pinting.business.dao.BsSysBalanceDailySnapMapper;
import com.pinting.business.dao.BsUserBalanceDailyRecordMapper;
import com.pinting.business.model.BsHfbankBalanceCheckRecord;
import com.pinting.business.model.BsHfbankBalanceCheckRecordExample;
import com.pinting.business.model.BsSysBalanceDailySnap;
import com.pinting.business.model.BsSysBalanceDailySnapExample;
import com.pinting.business.model.BsUserBalanceDailyRecord;
import com.pinting.business.model.BsUserBalanceDailyRecordExample;
import com.pinting.business.model.vo.BsSysAccountJnlCheckVO;
import com.pinting.business.model.vo.HFBalanceDetailVO;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_BalanceInfo;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_BalanceInfo;
import com.pinting.gateway.out.service.HfbankTransportService;


/**
 * 恒丰账户余额对账
 * @author bianyatian
 * @2017-5-10 下午8:30:06
 */
@Service
public class HfAccountBalanceCheckTask {
	
	private Logger logger = LoggerFactory.getLogger(HfAccountBalanceCheckTask.class);
	
	@Autowired
	private BsUserBalanceDailyRecordMapper bsUserBalanceDailyRecordMapper;
	@Autowired
	private BsSysAccountJnlMapper bsSysAccountJnlMapper;
	@Autowired
	private BsHfbankBalanceCheckRecordMapper bsHfbankBalanceCheckRecordMapper;
	@Autowired
	private HfbankTransportService hfbankTransportService;
	@Autowired
	private SpecialJnlService specialJnlService;
	@Autowired
	private BsSysBalanceDailySnapMapper bsSysBalanceDailySnapMapper;
	
	public void execute() {
		logger.info("=============【恒丰账户余额对账】开始===============================");
		/**
		 * 1、查询用户账户余额每日记录表bs_user_balance_daily_record当日总计数据是否存在，不存在则查询系统流水并记录
		 * 2、查询恒丰对账文件账户余额数据记录表bs_hfbank_balance_check_record当日数据是否存在，不存在拉取对账文件，并记录，已存在则结束
		 * 3、拉取对账文件和，比对bs_user_balance_daily_record的总余额和bs_hfbank_balance_check_record的平台数据的总余额
		 * 4、数据不一致则发送短信告警
		 */
		Date todayDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
		Date yesterdayDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -1)));
		
		//查询用户账户余额每日记录表数据
		BsUserBalanceDailyRecordExample userRecordExample = new BsUserBalanceDailyRecordExample();
		userRecordExample.createCriteria().andPaycheckDateEqualTo(yesterdayDate);
		List<BsUserBalanceDailyRecord> userBalanceRecordList = bsUserBalanceDailyRecordMapper.selectByExample(userRecordExample);
		BsUserBalanceDailyRecord bgwRecord = new BsUserBalanceDailyRecord();
		if(CollectionUtils.isEmpty(userBalanceRecordList)){
			//统计并新增
			BsSysAccountJnlCheckVO checkVo = bsSysAccountJnlMapper.selectLastJnlByDate(todayDate);
			bgwRecord.setBalance(checkVo.getSumBalance());
			bgwRecord.setDepJshBalance(checkVo.getUserBalance());
			bgwRecord.setYunAuthBalance(checkVo.getAuthYun());
			bgwRecord.setZanAuthBalance(checkVo.getAuthZan());
			bgwRecord.setPaycheckDate(yesterdayDate);
			bgwRecord.setCreateTime(new Date());
			bgwRecord.setUpdateTime(new Date());
			bsUserBalanceDailyRecordMapper.insertSelective(bgwRecord);
		}
		
		BsHfbankBalanceCheckRecordExample example = new BsHfbankBalanceCheckRecordExample();
		example.createCriteria().andPaycheckDateEqualTo(yesterdayDate);
		List<BsHfbankBalanceCheckRecord> hfRecordList = bsHfbankBalanceCheckRecordMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(hfRecordList)){
			//调用gateway，获取文件地址
			String path = getPath(yesterdayDate);
			// 解析文件
			List<BsHfbankBalanceCheckRecord> list = parseFile(yesterdayDate, path);
			 if(CollectionUtils.isEmpty(list)) {
				 // 1. 对账文件无数据，告警
				 logger.info("==================恒丰账户余额对账失败：文件无明细记录，可能下载文件异常，需要您的确认，请检查");
				 specialJnlService.warn4Fail(null, "恒丰账户余额账失败：无文件或文件无明细记录，可能下载文件异常，需要您的确认，请检查", null, "恒丰账户余额对账文件无文件或无明细", true);
				 return;
			 } else {
				 // 2. 对账文件存在数据，做记录
				 for (BsHfbankBalanceCheckRecord bsHfbankBalanceCheckRecord : list) {
					 bsHfbankBalanceCheckRecord.setCreateTime(new Date());
					 bsHfbankBalanceCheckRecord.setUpdateTime(new Date());
					 bsHfbankBalanceCheckRecordMapper.insertSelective(bsHfbankBalanceCheckRecord);
				 }
				 // 2.1  用户余额存入系统余额快照
				 BsHfbankBalanceCheckRecord hfRecord = list.get(0);
				 BsHfbankBalanceCheckRecord hfSysRecord = list.get(1);
				 saveHfAccSnap(Constants.SYS_ACC_HF_USER_BALANCE, todayDate, hfRecord, hfSysRecord);
				 
				 //3.对账
				 if(bgwRecord != null && StringUtil.isBlank(hfRecord.getHfUserId())){
					 logger.info("==================恒丰账户余额对账:bgw总余额:"+bgwRecord.getBalance()+",恒丰总余额:"+hfRecord.getBalance());
					 if(bgwRecord.getBalance().compareTo(hfRecord.getBalance()) != 0){
						 Double diffAmount = MoneyUtil.subtract(bgwRecord.getBalance(),hfRecord.getBalance()).doubleValue();
						//告警
						 specialJnlService.warn4Fail(diffAmount, "恒丰账户余额对账不一致,bgw总余额:"+bgwRecord.getBalance()+",恒丰总余额:"+hfRecord.getBalance()
								 ,null, "恒丰账户余额对账不一致",false);
					 }
				 }
			 }
		}
		
	}
	
	/**
	 * 恒丰账户存储
	 * 根据日期和类型查询数据
	 * @param code
	 * @param today
	 */
	private void saveHfAccSnap(String code, Date today, BsHfbankBalanceCheckRecord hfRecord, BsHfbankBalanceCheckRecord hfSysRecord) {
		try {
			BsSysBalanceDailySnapExample example = new BsSysBalanceDailySnapExample();
			example.createCriteria().andCodeEqualTo(code).andSnapDateEqualTo(today);
			List<BsSysBalanceDailySnap> list = bsSysBalanceDailySnapMapper.selectByExample(example);
			if(CollectionUtils.isEmpty(list)){
				BsSysBalanceDailySnap sysBalanceDailySnap = new BsSysBalanceDailySnap();
				sysBalanceDailySnap.setAccountType(Constants.SYS_SNAP_ACC_TYPE_HFBANK);
				Double balance = MoneyUtil.subtract(hfRecord.getBalance(), hfSysRecord.getBalance()).doubleValue();
				Double availableBalance = MoneyUtil.subtract(hfRecord.getAvailableBalance(), hfSysRecord.getAvailableBalance()).doubleValue();
				Double freezeBalance = MoneyUtil.subtract(hfRecord.getFreezeBalance(), hfSysRecord.getFreezeBalance()).doubleValue();
				sysBalanceDailySnap.setBalance(balance);
				sysBalanceDailySnap.setAvailableBalance(availableBalance);
				sysBalanceDailySnap.setFreezeBalance(freezeBalance);
				sysBalanceDailySnap.setCode(code);
				sysBalanceDailySnap.setSnapDate(today);
				sysBalanceDailySnap.setUpdateTime(new Date());
				sysBalanceDailySnap.setCreateTime(new Date());
				bsSysBalanceDailySnapMapper.insertSelective(sysBalanceDailySnap);
			}
		} catch (Exception e) {
			logger.error("恒丰余额快照存储异常", e);
		}
	}
	
	/**
	 * 调用gateway，获取文件地址
	 * @param yesterdayDate
	 * @return
	 */
	private String getPath(Date yesterdayDate) {
		logger.info("==================调用【恒丰账户余额对账gateway】开始==========================");
		String path = "";
		B2GReqMsg_HFBank_BalanceInfo req = new B2GReqMsg_HFBank_BalanceInfo();
		String orderNo = Util.generateOrderNo4BaoFoo(8);
		req.setOrder_no(orderNo);
		req.setPartner_trans_date(new Date());
		req.setPartner_trans_time(new Date());
		req.setPrecheck_flag(Constants.HF_PRECHECK_FLAG_NO);  // 0-不是预对账；1-是预对账
        req.setPaycheck_date(yesterdayDate);
        try {
        	B2GResMsg_HFBank_BalanceInfo res = new B2GResMsg_HFBank_BalanceInfo();
        	res = hfbankTransportService.balanceInfo(req);
        	if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())
        			&& StringUtils.isNotBlank(res.getDestFileName())) {
        		logger.info("==================调用【恒丰账户余额对账gateway】成功", JSONObject.fromObject(res));
        		path = res.getDestFileName();
        	}else{
        		logger.error("==================调用【恒丰账户余额对账gateway】失败",StringUtil.isBlank(res.getRemsg()) ? res.getResMsg() : res.getRemsg());
        		//告警 
//                specialJnlService.warn4Fail(null, "【恒丰账户余额对账】,失败：余额对账明细获取失败", "", "余额对账文件查询失败",false);
        	}
		} catch (Exception e) {
			logger.error("==================调用【恒丰账户余额对账gateway】失败",e.getMessage());
			// 告警 
//            specialJnlService.warn4Fail(null, "【恒丰账户余额对账】,失败：余额对账明细获取失败", "", "余额对账文件查询失败",false);
			e.printStackTrace();
		}
		logger.info("==================调用【恒丰账户余额对账gateway】结束==========================");
		return path;
	}
	
	/**
	 * 解析账户余额对账文件
	 * @param time
	 * @param path
	 * @return
	 */
	private List<BsHfbankBalanceCheckRecord> parseFile(Date date, String path) {
        logger.info("==================恒丰账户余额对账文件解析开始：{}", date);

        // 具体解析
        UncompressUtil.uncompress(path, FilePath.HF_CHECK_FILE_PATH.getCode(), "hf_balance", date,null);
        String filePath = FilePath.HF_CHECK_FILE_PATH.getCode() + DateUtil.formatYYYYMMDD(date) + "_" + "hf_balance" + ".txt";
        List<BsHfbankBalanceCheckRecord> list = new ArrayList<BsHfbankBalanceCheckRecord>();
        
        File file = new File(filePath);
        int row = 0;
        if (file.isFile() && file.exists()) { 
        	InputStreamReader read = null;//考虑到编码格式
        	try {
                read = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;
                //行读取
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    String[] account = lineTxt.split("\\|");
                    BsHfbankBalanceCheckRecord balanceVo = new BsHfbankBalanceCheckRecord();
                    row ++;
                    logger.info("==================恒丰账户余额对账文件account[1]"+account[1]+"===============" );
                    // 第一行是汇总信息:平台编号|对账日期|总金额|可用金额|冻结金额|
                    if (row == 1) {
                    	balanceVo.setPlatNo(account[0]);
                    	balanceVo.setPaycheckDate(StringUtil.isNotBlank(account[1])? DateUtil.parse(account[1],"yyyyMMdd"):null);
                    	balanceVo.setBalance(StringUtil.isNotBlank(account[2])? MoneyUtil.defaultRound(account[2]).doubleValue():null);
                    	balanceVo.setAvailableBalance(StringUtil.isNotBlank(account[3])?MoneyUtil.defaultRound(account[3]).doubleValue():null);
                    	balanceVo.setFreezeBalance(StringUtil.isNotBlank(account[4])?MoneyUtil.defaultRound(account[4]).doubleValue():null);
                    	list.add(balanceVo);
                    	continue;
                    }
                    //明细：平台编号|对账日期|平台客户编号|总金额|可用金额|冻结金额|
                    balanceVo.setPlatNo(account[0]);
                	balanceVo.setPaycheckDate(StringUtil.isNotBlank(account[1])?DateUtil.parse(account[1],"yyyyMMdd"):null);
                	balanceVo.setHfUserId(account[2]);
                	balanceVo.setBalance(StringUtil.isNotBlank(account[3])? MoneyUtil.defaultRound(account[3]).doubleValue():null);
                	balanceVo.setAvailableBalance(StringUtil.isNotBlank(account[4])?MoneyUtil.defaultRound(account[4]).doubleValue():null);
                	balanceVo.setFreezeBalance(StringUtil.isNotBlank(account[5])?MoneyUtil.defaultRound(account[5]).doubleValue():null);
                	list.add(balanceVo);
                }
                read.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("==================恒丰账户余额对账文件解析结束==================");
        return list;
    }
}
