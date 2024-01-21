package com.pinting.gateway.in.facade.loan;

import com.pinting.business.accounting.loan.service.LoanCardOperateService;
import com.pinting.business.service.loan.LoanUserService;
import com.pinting.gateway.hessian.message.loan.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("LoanCif")
public class LoanCifFacade {

    private Logger logger = LoggerFactory.getLogger(LoanCifFacade.class);
	@Autowired
	private LoanUserService loanUserService;
    @Autowired
    private LoanCardOperateService loanCardOperateService;

    /**
	 * 借款人信息登记
	 * @param req
	 * @param res
	 */
	public void addLoaner(G2BReqMsg_LoanCif_AddLoaner req, G2BResMsg_LoanCif_AddLoaner res){
        loanUserService.addLoanCif(req,res);
		
	}

    public void preBindCard(G2BReqMsg_LoanCif_PreBindCard req, G2BResMsg_LoanCif_PreBindCard res) throws Exception {


        res.setBgwOrderNo(loanCardOperateService.preBindCard(req));
    }

    public void bindCardConfirm(G2BReqMsg_LoanCif_BindCardConfirm req, G2BResMsg_LoanCif_BindCardConfirm res) throws Exception {

        res.setBindId(loanCardOperateService.bindCardConfirm(req));
    }

    public void unBindCard(G2BReqMsg_LoanCif_UnBindCard req, G2BResMsg_LoanCif_UnBindCard res) throws Exception {

        // 赞分期解绑卡接口不执行
        logger.info("不执行赞分期解绑接口，BgwBindId：{}", req.getBgwBindId());
        // loanCardOperateService.unBindCard(req);
    }

}
