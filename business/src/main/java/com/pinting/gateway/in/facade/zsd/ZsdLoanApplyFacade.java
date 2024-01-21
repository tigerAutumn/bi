package com.pinting.gateway.in.facade.zsd;

import java.util.Date;

import com.pinting.business.accounting.loan.service.DepFixedLoanPaymentService;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.gateway.hessian.message.zsd.G2BReqMsg_ZsdLoanApply_AddLoan;
import com.pinting.gateway.hessian.message.zsd.G2BReqMsg_ZsdLoanApply_QueryLoan;
import com.pinting.gateway.hessian.message.zsd.G2BResMsg_ZsdLoanApply_AddLoan;
import com.pinting.gateway.hessian.message.zsd.G2BResMsg_ZsdLoanApply_QueryLoan;

/**
 * 借款申请
 * @author SHENGUOPING
 * @date  2017年8月30日 下午4:09:47
 */
@Component("ZsdLoanApply")
public class ZsdLoanApplyFacade {

	private Logger log = LoggerFactory.getLogger(ZsdLoanApplyFacade.class);

	@Autowired
	private DepFixedLoanPaymentService depFixedLoanPaymentService;
	@Autowired
	private BsSysConfigService bsSysConfigService;
    /**
	 * 借款申请
	 * @param req
	 * @param res
	 */
	public void addLoan(G2BReqMsg_ZsdLoanApply_AddLoan req, G2BResMsg_ZsdLoanApply_AddLoan res){
		log.info("收到赞时贷借款申请请求");
		Date finishDate = DateUtil.parseDate("2018-03-15"); //赞分期提前赎回日期
        BsSysConfig config = bsSysConfigService.findKey( Constants.ZAN_CALLED_AWAY_DATE );
        if(config != null){
        	finishDate = DateUtil.parseDate(config.getConfValue());
        }
        Date now = new Date();
        if(now.compareTo(finishDate) >= 0){
        	throw new PTMessageException(PTMessageEnum.REFUSE_LOAN);

        }else{
        	depFixedLoanPaymentService.loanApply(req);
        }
	}

	/**
	 * 借款结果查询
	 * @param req
	 * @param res
	 */
	public void queryLoan(G2BReqMsg_ZsdLoanApply_QueryLoan req, G2BResMsg_ZsdLoanApply_QueryLoan res){
		if(StringUtil.isBlank(req.getOrderNo())){
			throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_NOT_ENOUGH);
		}
		log.info("收到赞时贷借款结果查询请求");
		depFixedLoanPaymentService.zsdLoanResultQuery(req,res);
	} 
	
}
