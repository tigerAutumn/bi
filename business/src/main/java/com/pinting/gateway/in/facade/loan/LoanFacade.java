package com.pinting.gateway.in.facade.loan;

import java.util.Date;

import com.pinting.business.accounting.loan.service.LoanPaymentService;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.vo.LnLoanVO;
import com.pinting.business.service.loan.LoanQueryService;
import com.pinting.business.service.loan.LoanUserService;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.gateway.hessian.message.loan.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by 剑钊 on 2016/8/18.
 */
@Component("Loan")
public class LoanFacade {

	Logger logger = LoggerFactory.getLogger(LoanFacade.class);
    @Autowired
    private LoanPaymentService loanPaymentService;
    @Autowired
    private LoanUserService loanUserService;
    @Autowired
    private LoanQueryService loanQueryService;
    @Autowired
	private BsSysConfigService bsSysConfigService;

    public void loan(G2BReqMsg_Loan_Loan req,G2BResMsg_Loan_Loan res) throws Exception {
    	Date finishDate = DateUtil.parseDate("2018-03-15"); //赞分期提前赎回日期
        BsSysConfig config = bsSysConfigService.findKey( Constants.ZAN_CALLED_AWAY_DATE );
        if(config != null){
        	finishDate = DateUtil.parseDate(config.getConfValue());
        }
        Date now = new Date();
        if(now.compareTo(finishDate) >= 0){
        	logger.info("================当前时间："+DateUtil.format(now)+",ZAN_CALLED_AWAY_DATE："+DateUtil.format(finishDate));
        	throw new PTMessageException(PTMessageEnum.REFUSE_LOAN);

        }else{
        	loanPaymentService.loan(req);
        }
    }
    
    /**
     * 赞分期查询币港湾当日可用额度
     * @param req
     * @param res
     * @throws Exception
     */
    public void dailyAmount(G2BReqMsg_Loan_DailyAmount req, G2BResMsg_Loan_DailyAmount res) throws Exception{
    	
    	loanQueryService.getDailyAmount(req, res);
    }

    public void queryLoan(G2BReqMsg_Loan_QueryLoan req,G2BResMsg_Loan_QueryLoan res){

        LnLoanVO lnLoan=loanUserService.queryLoanStatus(req);

        if(lnLoan!=null){
            res.setLoanId(lnLoan.getPartnerLoanId());
            res.setChannel(lnLoan.getChannel());
            res.setLoanResultCode(lnLoan.getStatus());
            res.setLoanResultMsg(lnLoan.getReturnMsg());
            res.setLoanTime(lnLoan.getLoanTime());
        }else {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND,"找不到借款信息");
        }
    }
}
