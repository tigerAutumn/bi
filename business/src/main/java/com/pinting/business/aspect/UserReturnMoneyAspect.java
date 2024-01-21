package com.pinting.business.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.pinting.business.accounting.service.impl.process.UpdateRepayPlanProcess;
import com.pinting.business.dao.BsBatchReturnDetailMapper;
import com.pinting.business.model.BsBatchReturnDetail;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.vo.BsSubAccountVO;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.Constants;
import com.pinting.gateway.hessian.message.qb178.B2GReqMsg_APP178_UpdateRepayPlan;
import com.pinting.gateway.out.service.App178TransportService;

/**
 * 用户回款到余额
 * 1、通知钱报178app
 * @author bianyatian
 * @2017-8-2 上午9:36:19
 */
@Aspect
@Component
@Order(8)
public class UserReturnMoneyAspect {
	
	private Logger log = LoggerFactory.getLogger(UserReturnMoneyAspect.class);
	@Autowired
	private BsBatchReturnDetailMapper bsBatchReturnDetailMapper;
	@Autowired
    private UserService userService;
	@Autowired
    private SubAccountService subAccountService;
	@Autowired
	private App178TransportService app178TransportService;
	
	//用户回款到余额
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserReturnMoneyServiceImpl.return2Balance(..))")
	public void return2balancePointcut(){}
	
	@AfterReturning("return2balancePointcut()")
	public void return2balanceAfter(JoinPoint call) {
		try {
			BsBatchReturnDetail batchReturnDetail = (BsBatchReturnDetail) call.getArgs()[0];
			//核对用户渠道
			Integer userId = batchReturnDetail.getUserId();
			BsUser user = userService.findUserById(userId);
			
			Integer detailId = batchReturnDetail.getId();
			BsBatchReturnDetail detailTemp = bsBatchReturnDetailMapper.selectByPrimaryKey(detailId);
			if(user.getAgentId() != null && Constants.AGENT_QIANBAO_ID == user.getAgentId() &&
					detailTemp != null && Constants.RETURN_STATUS_SUCCESS.equals(detailTemp.getReturnStatus())){
				//钱报用户且回款成功,查询产品信息，发送通知
				Integer subAccountId = batchReturnDetail.getSubAccountId();
				BsSubAccountVO productAccount = subAccountService.findMyInvestByUserIdAndId(userId, subAccountId);
			
				log.info("钱报178APP平台更新还款计划状态切面，进行通知");
				B2GReqMsg_APP178_UpdateRepayPlan req = new B2GReqMsg_APP178_UpdateRepayPlan();
				req.setUserAccount(user.getMobile());
				req.setProductCode(productAccount.getProductId());
				req.setPeriod(subAccountId);
				req.setJetPlanStatus("FINISH");
				req.setRealDate(detailTemp.getUpdateTime());
				req.setPlanDate(productAccount.getLastFinishInterestDate());
	
				UpdateRepayPlanProcess process = new UpdateRepayPlanProcess();
				process.setApp178TransportService(app178TransportService);
				process.setUpdateRepayPlan(req);
				new Thread(process).start();
				
			}
		} catch(Exception e ) {
			log.error("钱报178APP平台更新还款计划通知失败 {}:",e.getMessage());
			e.printStackTrace();
		}
	}
}
