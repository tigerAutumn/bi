package com.pinting.business.dayend.task;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnLoanRelationMapper;
import com.pinting.business.dao.LnRepayScheduleMapper;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.vo.RepayScheduleVO;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.business.util.ExportUtil;
import com.pinting.core.util.GlobEnvUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangpeng
 * @date 2018-1-15 下午8:00:23
 */
@Service
public class NotCompensatedWarnTask {

    @Autowired
    private LnRepayScheduleMapper lnRepayScheduleMapper;
    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private LnLoanRelationMapper lnLoanRelationMapper;
    @Autowired
    private SpecialJnlService specialJnlService;

    private Logger log = LoggerFactory.getLogger(NotCompensatedWarnTask.class);

    /**
     * 任务执行
     */
    public void execute() {
        //未代偿告警
        notCompensatedTask();
        
        //账单不存在告警
//		billNotExistTask();
    }

    private void notCompensatedTask() {
        log.info("==================【查询未代偿】开始=======================");
        //查询未代偿(排除赞分期数据)
        List<RepayScheduleVO> repaySchedules = lnRepayScheduleMapper.selectNotCompensated(PartnerEnum.ZAN.getCode());
        if (CollectionUtils.isEmpty(repaySchedules)) {
            log.info("================= 全部已代偿，不需要发送告警 =====================");
            return;
        }
        log.info("================= 存在未代偿条目，发送告警  =====================");

        String yesterday = DateUtil.getDate(DateUtil.nextDay(new Date(), -1));

        // 查询未代偿合作方
        List<String> partners = lnRepayScheduleMapper.selectNotCompensatedPartner(PartnerEnum.ZAN.getCode());
        StringBuilder partnerName = new StringBuilder();
        for (String partnerCode : partners) {
            partnerName.append(PartnerEnum.getEnumByCode(partnerCode).getName()).append("，");
        }

        if (StringUtils.isNotBlank(partnerName.toString())) {
            partnerName.deleteCharAt(partnerName.length() -1);
        }

        // 告警发送运营/产品，技术
        String detail = "[" + partnerName.toString() + "]合作方，" + yesterday + "存在未代偿账单，请及时联系合作方技术处理";
        specialJnlService.warnAppoint4Fail(null, detail, null, "存在未代偿", true, Constants.PRODUCT_OPERATOR_MOBILE, Constants.EMERGENCY_MOBILE);
        for (RepayScheduleVO repayScheduleVO : repaySchedules) {
            specialJnlService.warn4FailNoSMS(null,
                    "计划还款编号[" + repayScheduleVO.getPartnerRepayId() + "]未代偿", null, "未代偿");
        }
    }
    
    //生成七贷每日未代偿账单
    public void generatorCompensate() {
    	String yesterday = DateUtil.getDate(DateUtil.nextDay(new Date(), -1));
    	List<RepayScheduleVO> repaySchedules = lnRepayScheduleMapper.selectNotCompensatedDetail(yesterday, PartnerEnum.SEVEN_DAI_SELF.getCode());
        StringBuffer header = new StringBuffer();
        header.append("账单编号");
    	List<String> content = new ArrayList<>();
        content.add(header.toString());
    	for( RepayScheduleVO repayScheduleVO : repaySchedules ) {
    		Double leftAmount = lnLoanRelationMapper.getRelationAmountByLoanId(repayScheduleVO.getLoanId());
    		if( leftAmount > 0 ) {
    			StringBuffer contentBuffer = new StringBuffer();
    			contentBuffer.append(repayScheduleVO.getPartnerRepayId());
    			content.add(contentBuffer.toString());
    		}
    	}
    	log.info("生成七贷未代偿对账单文件开始，保存路径"+GlobEnvUtil.get("self.compensate.7dai.account.path"));
       	ExportUtil.exportLocalCSV(GlobEnvUtil.get("self.compensate.7dai.account.path") + File.separator , content, "compensate_" + DateUtil.format(new Date(), "yyyyMMdd") + ".csv");
       	log.info("生成七贷未代偿对账单文件结束");
    }
    
    private void billNotExistTask() {
        log.info("==================【查询成功且没有账单的放款】开始=======================");
        //查询成功且没有账单的放款
        List<LnLoan> lnLoanList = new ArrayList<>();
        lnLoanList = lnLoanMapper.selectBillNotExist();
        if (CollectionUtils.isEmpty(lnLoanList)) {
            log.info("================= 全部成功放款都已经生成账单，不需要发送告警 =====================");
            return;
        }
        log.info("================= 存在成功且没有账单的放款，发送告警  =====================");
        specialJnlService.warn4Fail(null, "存在成功且没有账单的放款，请尽快处理", null, "成功放款，没有账单", true);
        for (LnLoan lnLoan : lnLoanList) {
            specialJnlService.warn4FailNoSMS(null,
                    "借款编号[" + lnLoan.getId() + "]放款成功但是没有生成账单", null, "未生成账单");
        }
    }
}
