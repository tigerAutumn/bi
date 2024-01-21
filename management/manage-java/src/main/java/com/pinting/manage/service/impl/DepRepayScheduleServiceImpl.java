package com.pinting.manage.service.impl;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.DepLimitRepaySchedule;
import com.pinting.business.accounting.loan.model.DepRepaySchedule;
import com.pinting.business.accounting.loan.model.Repay2InvestorReq;
import com.pinting.business.accounting.loan.model.RepayInfo;
import com.pinting.business.accounting.loan.service.DepOfflineRepayService;
import com.pinting.business.dao.LnDepositionRepayScheduleMapper;
import com.pinting.business.dao.LnDepositionTargetMapper;
import com.pinting.business.model.LnDepositionTarget;
import com.pinting.business.model.LnDepositionTargetExample;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.manage.service.DepRepayScheduleService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepRepayScheduleServiceImpl implements DepRepayScheduleService {
	private Logger log = LoggerFactory.getLogger(DepRepayScheduleServiceImpl.class);
	
	@Autowired
	private LnDepositionRepayScheduleMapper depRepayScheduleMapper;
	@Autowired
	private LnDepositionTargetMapper lnDepositionTargetMapper;
	@Autowired
	private SpecialJnlService specialJnlService;
	@Autowired
	private DepOfflineRepayService depOfflineRepayService;
	
	@Override
	public String doRepay2Investor(Integer depRepayScheduleId,String fileName) {
		
		DepLimitRepaySchedule repaySchedule = depRepayScheduleMapper.getDepRepayScheduleById(depRepayScheduleId);

		if(StringUtils.isBlank(repaySchedule.getRepayOrderNo())){
			log.info("=============【标的回款重发】该笔为逾期还款=================");
            return "该笔为逾期还款！";
		}

		if(LoanStatus.DEP_REPAY_SCHEDULE_RETURN_FLAG_RETURN_PROCESS.getCode().equals(repaySchedule.getReturnFlag())
				|| LoanStatus.DEP_REPAY_SCHEDULE_RETURN_FLAG_RETURN_FAIL.getCode().equals(repaySchedule.getReturnFlag())){

			//赞分期回款需要根据计划日期回款
            Repay2InvestorReq repay2InvestorReq = new Repay2InvestorReq();
            DepRepaySchedule repayScheduleRPItem = new DepRepaySchedule();
            repayScheduleRPItem.setRepayAmount(repaySchedule.getPlanTotal());
            repayScheduleRPItem.setRepayFee(0d);
            LnDepositionTargetExample exampleRP = new LnDepositionTargetExample();
            exampleRP.createCriteria().andLoanIdEqualTo(repaySchedule.getLoanId());
            List<LnDepositionTarget> depositionTargetsRP = lnDepositionTargetMapper.selectByExample(exampleRP);
            if(CollectionUtils.isEmpty(depositionTargetsRP)){
            	log.info("=============【标的回款重发】未找到借款编号"+repaySchedule.getLoanId()+"对应的标的=================");
                specialJnlService.warn4FailNoSMS(null, "未找到借款编号"+repaySchedule.getLoanId()+"对应的标的" ,null, "标的不存在");
                return "未找到借款编号"+repaySchedule.getLoanId()+"对应的标的！";
            }
            repayScheduleRPItem.setTargetId(depositionTargetsRP.get(0).getId());
            repayScheduleRPItem.setLoanId(repaySchedule.getLoanId());
            repayScheduleRPItem.setId(repaySchedule.getId());
            repayScheduleRPItem.setSerialId(repaySchedule.getSerialId());
            repayScheduleRPItem.setPartnerEnum(PartnerEnum.getEnumByCode(repaySchedule.getPartnerCode()));
            repay2InvestorReq.setDepRepaySchedule(repayScheduleRPItem);
            //读取标的回款成功的记账数据
            List<RepayInfo> yunAccountList = new ArrayList<RepayInfo>();
            //读取文件
            if(PartnerEnum.YUN_DAI_SELF.getCode().equals(repaySchedule.getPartnerCode()) || 
            		PartnerEnum.ZSD.getCode().equals(repaySchedule.getPartnerCode()) ||
					PartnerEnum.SEVEN_DAI_SELF.getCode().equals(repaySchedule.getPartnerCode())){
            	if(StringUtils.isBlank(fileName)){
            		log.info("=============【标的回款重发】回款数据为"+PartnerEnum.getEnumByCode(repaySchedule.getPartnerCode()).getName() +",必须输入文件名=================");
            		return "回款数据为"+PartnerEnum.getEnumByCode(repaySchedule.getPartnerCode()).getName() +",必须输入文件名";
            	}
            	yunAccountList = getList(fileName);
            	if(CollectionUtils.isNotEmpty(yunAccountList)){
                	depOfflineRepayService.repay2InvestorSucc(repay2InvestorReq, yunAccountList);
                    return "";
                }else{
                	log.info("=============【标的回款重发】未读取到文件数据=================");
                	return "未读取到文件数据";
                }
            }else{
            	depOfflineRepayService.repay2InvestorSucc(repay2InvestorReq, null);
                return "";
            }
            
            
		}else{
			log.info("=============【标的回款重发】借款人还款到标的状态不为失败或处理中=================");
			return "借款人还款到标的状态不为失败或处理中！";
		}
	}

	/**
	 * 读取文件返回数据
	 * @param fileName
	 * @return
	 */
	private List<RepayInfo> getList(String fileName) {
		List<RepayInfo>  list = new ArrayList<RepayInfo>();
		BufferedReader br = null;
		String prePath =  GlobEnvUtil.get("dep.offline.return.path");
		try {
			log.info("=============【标的回款重发】读取文件数据=================");
			log.info("=============【标的回款重发】读取文件的路径=================" + prePath + File.separator + fileName);
			File file = new File(prePath + File.separator + fileName);
			//解析文件数据，保存到list列表中
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBk"));
			String tempRelation = "";
			int count = 0;
			while((tempRelation = br.readLine()) != null){
				if(count > 0){
					String[] infos = tempRelation.split(",");
					RepayInfo account = new RepayInfo();
					account.setPartner(PartnerEnum.getEnumByCode(infos[0]));
	                account.setInvestorUserId(StringUtils.isBlank(infos[1]) ? null : Integer.parseInt(infos[1]));
	                account.setAuthActId(StringUtils.isBlank(infos[2]) ? null : Integer.parseInt(infos[2]));
	                account.setDiffActId(StringUtils.isBlank(infos[3]) ? null : Integer.parseInt(infos[3]));
	                account.setDiffAmount(StringUtils.isBlank(infos[4]) ? null : Double.valueOf(infos[4]));
	                account.setPrincipal(StringUtils.isBlank(infos[5]) ? null : Double.valueOf(infos[5]));
	                account.setInterest(StringUtils.isBlank(infos[6]) ? null : Double.valueOf(infos[6]));
	                account.setServiceFee(StringUtils.isBlank(infos[7]) ? null : Double.valueOf(infos[7]));
	                account.setLnFinancePlanId(StringUtils.isBlank(infos[8]) ? null : Integer.parseInt(infos[8]));
	                list.add(account);
				}
				count++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
