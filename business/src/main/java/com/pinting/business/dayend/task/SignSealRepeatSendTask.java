package com.pinting.business.dayend.task;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.ProtocolSealVO;
import com.pinting.business.dao.BsUserSealFileMapper;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.BsUserSealFile;
import com.pinting.business.model.LnLoan;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.manage.SignRepeatSendService;
import com.pinting.business.service.protocol.LoanAgreementSignSealService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;

@Service
public class SignSealRepeatSendTask {
	private Logger log = LoggerFactory.getLogger(SignSealRepeatSendTask.class);
	@Autowired
    private BsUserSealFileMapper bsUserSealFileMapper;
	@Autowired
	private SignRepeatSendService signRepeatSendService;
	@Autowired
    private BsSysConfigService bsSysConfigService;
	@Autowired
	private LoanAgreementSignSealService loanAgreementSignSealService;
	@Autowired
	private LnLoanMapper lnLoanMapper;
	
	private static Integer signId = 0;
	/**
	 * 任务执行
	 */
	public void execute() {
		log.info("=========定时任务{签章重发}开始=========");
		//signId为0说明任务刚启动或者已经重发完一轮
		if (signId == 0) {
			signId = bsUserSealFileMapper.selectMaxId() + 1;
		}
		List<BsUserSealFile> userSealFiles = new ArrayList<>();
		//按id从大到小排列查询n条
        BsSysConfig sys = bsSysConfigService.findKey(Constants.SIGN_SEAL_REPEAT_SEND_NUM);
		Integer selectNum = sys != null ? Integer.valueOf(sys.getConfValue()) : 5;
		userSealFiles = bsUserSealFileMapper.selectRepeatSendList(signId,selectNum);
		//遍历重发
		for (BsUserSealFile bsUserSealFile : userSealFiles) {
			Integer id = bsUserSealFile.getId();
			signId = id;
			if ("LOAN_AGREEMENT".equals(bsUserSealFile.getSealType()) && "YUN_DAI_SELF".equals(bsUserSealFile.getUserSrc())) {
				try {
					BsSysConfig bsSysConfig = bsSysConfigService.findKey(Constants.LOAN_AGREEMENT_CHANGE_THREE_PART_TIME);
					if (bsSysConfig == null) {
						log.error("签章请求重发>>>>查询不到对应的配置表信息（LOAN_AGREEMENT_CHANGE_THREE_PART_TIME 借款协议四方改三方生效时间）");
						continue;
					}
					
					Date loanAgreeChangeTime = null;
					try {
						loanAgreeChangeTime = DateUtil.parse(bsSysConfig.getConfValue(), "yyyy-MM-dd HH:mm:ss");
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(Integer.parseInt(bsUserSealFile.getRelativeInfo()));
					if (DateUtil.compareTo(loanAgreeChangeTime, lnLoan.getLoanTime())> 0) {
						signRepeatSendService.signLoanAgreementYun(id);
					}else {
						ProtocolSealVO protocolSeal = new ProtocolSealVO(bsUserSealFile.getAgreementNo(), lnLoan);
						loanAgreementSignSealService.protocolSeal(PartnerEnum.YUN_DAI_SELF, protocolSeal);
					}
				} catch (Exception e) {
					log.error("云贷签章"+id+"请求重发异常");
					continue;
				}
			} else if ("LOAN_AGREEMENT".equals(bsUserSealFile.getSealType()) && PartnerEnum.SEVEN_DAI_SELF.getCode().equals(bsUserSealFile.getUserSrc())) {
				
				BsSysConfig bsSysConfig = bsSysConfigService.findKey(Constants.LOAN_AGREEMENT_CHANGE_THREE_PART_TIME);
				if (bsSysConfig == null) {
					log.error("签章请求重发>>>>查询不到对应的配置表信息（LOAN_AGREEMENT_CHANGE_THREE_PART_TIME 借款协议四方改三方生效时间）");
					continue;
				}
				
				Date loanAgreeChangeTime = null;
				try {
					loanAgreeChangeTime = DateUtil.parse(bsSysConfig.getConfValue(), "yyyy-MM-dd HH:mm:ss");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				try {
					LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(Integer.parseInt(bsUserSealFile.getRelativeInfo()));
					if (DateUtil.compareTo(loanAgreeChangeTime, lnLoan.getLoanTime())> 0) {
						signRepeatSendService.signLoanAgreement7(id);
					}else {
						ProtocolSealVO protocolSeal = new ProtocolSealVO(bsUserSealFile.getAgreementNo(), lnLoan);
						loanAgreementSignSealService.protocolSeal(PartnerEnum.SEVEN_DAI_SELF, protocolSeal);
					}
				} catch (Exception e) {
					log.error("七贷签章"+id+"请求重发异常");
					continue;
				}
			} else if ("ZSD_BORROW_SERVICES".equals(bsUserSealFile.getSealType())) {
				try {
					signRepeatSendService.signBorrowServicesZsd(id);
				} catch (Exception e) {
					log.error("赞时贷签章"+id+"请求重发异常");
					continue;
				}
			} else if ("BORROW_SERVICES".equals(bsUserSealFile.getSealType())) {
				try {
					signRepeatSendService.signBorrowServicesZan(id);
				} catch (Exception e) {
					log.error("赞分期签章"+id+"请求重发异常");
					continue;
				}
			}else {
				log.info("签章"+id+"请求重发>>>>重发数据类型暂不支持 type = "+ bsUserSealFile.getSealType() +",userSrc = " + bsUserSealFile.getUserSrc());
				continue;
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}  
			log.info("=========签章"+id+"重发成功=========");
		}
		//查询出来的条目小于5证明已经重发了一轮
		if (userSealFiles.size() < selectNum) {
			log.info("=========签章重发完一轮，开始新一轮重发=========");
			signId = 0;
		} 
	}
}
