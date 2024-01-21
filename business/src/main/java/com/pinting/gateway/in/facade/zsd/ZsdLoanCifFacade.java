package com.pinting.gateway.in.facade.zsd;

import com.pinting.gateway.hessian.message.zsd.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("ZsdLoanCif")
public class ZsdLoanCifFacade {
	
	private Logger log = LoggerFactory.getLogger(ZsdLoanCifFacade.class);
	
    /**
	 * 借款人信息登记
	 * @param req
	 * @param res
	 */
	public void addLoaner(G2BReqMsg_ZsdLoanCif_AddLoaner req, G2BResMsg_ZsdLoanCif_AddLoaner res){
        //loanUserService.addLoanCif(req,res);
		log.info("收到借款人信息登记请求");
	}

}
