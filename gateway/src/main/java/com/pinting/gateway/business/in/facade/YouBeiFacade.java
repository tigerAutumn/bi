package com.pinting.gateway.business.in.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.hessian.message.youbei.B2GReqMsg_YouBei_CheckRealName;
import com.pinting.gateway.hessian.message.youbei.B2GResMsg_YouBei_CheckRealName;
import com.pinting.gateway.util.BeanUtils;
import com.pinting.gateway.youbei.out.model.RealNameModel;
import com.pinting.gateway.youbei.out.service.SendYouBeiService;

@Component("YouBei")
public class YouBeiFacade {

	@Autowired
	private SendYouBeiService youbeiService;
	
	public void checkRealName(B2GReqMsg_YouBei_CheckRealName req, B2GResMsg_YouBei_CheckRealName res) {
		String name = req.getName();
		String idCard = req.getIdCard();
		String cardNo = req.getCardNo();
		String mobile = req.getMobile();
		RealNameModel model = youbeiService.checkRealName(name, idCard, cardNo, mobile);
		if(model != null) {
			res.setRealNameModel(BeanUtils.transBeanMap(model));
		}
		else {
			throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "，调用有贝接口失败，通讯失败");
		}
	}
}
