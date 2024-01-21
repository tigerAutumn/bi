package com.pinting.business.aspect;

import com.pinting.business.dao.BsFinanceDafyRelationMapper;
import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.dao.BsUserCustomerManagerMapper;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_BalanceBuy;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_BalanceBuy;
import com.pinting.business.model.BsFinanceDafyRelation;
import com.pinting.business.model.BsUserCustomerManager;
import com.pinting.business.model.BsUserCustomerManagerExample;
import com.pinting.business.service.site.ActivityService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryUserById;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryUserById;
import com.pinting.gateway.out.service.DafyTransportService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: Product49Aspect 
 * @Description: 产品购买结果拦截，调用云贷接口，将购买信息跟客户经理关联
 * @author lenovo
 * @date 2016年3月16日 下午1:37:50 
 *
 */
@Aspect
@Component
@Order(4)
public class ProductBuyForCustomerAspect {
	
	@Autowired
	private BsPayOrdersMapper payOrderMapper;
	
	@Autowired
	private BsUserCustomerManagerMapper userCustomerMapper;
	
	@Autowired
	private BsFinanceDafyRelationMapper relationMapper;
	
	@Autowired
	private DafyTransportService dafyService;
	
	@Autowired
	private SpecialJnlService specialJnlService;

	@Autowired
	private ActivityService activityService;
	
	/*@Pointcut("execution(public * com.pinting.business.accounting.buyproduct.service.impl.UserBuyProductServiceImpl.notify(..))")
	public void pay19Pointcut(){}*/
	
	/*@Pointcut("execution(public * com.pinting.business.accounting.buyproduct.service.impl.UserBuyProductServiceImpl.channelNotify(..))")
	public void reapalPointcut(){}*/
	
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.DepUserBalanceBuyServiceImpl.buyFixed(..))")
	public void balanceBuyPointcut(){}
	
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.DepUserBalanceBuyServiceImpl.buyStage(..))")
	public void balanceBuyPointcutAUTH(){}
	
	/*@Pointcut("execution(public * com.pinting.business.accounting.buyproduct.service.impl.UserBuyProductServiceImpl.notifyEBank(..))")
	public void eBankPointcut(){}*/
	
	/*@AfterReturning("pay19Pointcut()")
	public void pay19After(JoinPoint call) {
		G2BReqMsg_Pay19QuickPay_PayResultNotice req = (G2BReqMsg_Pay19QuickPay_PayResultNotice)call.getArgs()[0];
		BsPayOrdersExample orderExample = new BsPayOrdersExample();
		orderExample.createCriteria().andOrderNoEqualTo(req.getOrderId()).andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS).andTransTypeEqualTo(Constants.TRANS_CARD_BUY_PRODUCT);
		List<BsPayOrders> orderList = payOrderMapper.selectByExample(orderExample);
		if(!CollectionUtils.isEmpty(orderList)) {
			BsPayOrders order = orderList.get(0);
			createRelation(order.getSubAccountId(), order.getUserId());
		}
	}*/
	
	/*@AfterReturning("reapalPointcut()")
	public void reapalAfter(JoinPoint call) {
		G2BReqMsg_ReapalQuickPay_ReapalNotify req = (G2BReqMsg_ReapalQuickPay_ReapalNotify)call.getArgs()[0];
		BsPayOrdersExample orderExample = new BsPayOrdersExample();
		orderExample.createCriteria().andOrderNoEqualTo(req.getOrderNo()).andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS).andTransTypeEqualTo(Constants.TRANS_CARD_BUY_PRODUCT);
		List<BsPayOrders> orderList = payOrderMapper.selectByExample(orderExample);
		if(!CollectionUtils.isEmpty(orderList)) {
			BsPayOrders order = orderList.get(0);
			createRelation(order.getSubAccountId(), order.getUserId());
		}
	}*/
	
	@AfterReturning("balanceBuyPointcut()")
	public void balanceBuyAfter(JoinPoint call) {
		ReqMsg_RegularBuy_BalanceBuy req = (ReqMsg_RegularBuy_BalanceBuy)call.getArgs()[0];
		ResMsg_RegularBuy_BalanceBuy res = (ResMsg_RegularBuy_BalanceBuy)call.getArgs()[1];
		HashMap<String, Object> extendProMap = res.getExtendMap();
		Integer subAccountId = (Integer) extendProMap.get("subAccountId");
		Integer userId = req.getUserId();
		createRelation(subAccountId, userId);


		// 活动切面
//		activityService.generateInvestNumber(userId, subAccountId);
	}
	
	
	@AfterReturning("balanceBuyPointcutAUTH()")
	public void balanceBuyAUTHAfter(JoinPoint call) {
		ReqMsg_RegularBuy_BalanceBuy req = (ReqMsg_RegularBuy_BalanceBuy)call.getArgs()[0];
		ResMsg_RegularBuy_BalanceBuy res = (ResMsg_RegularBuy_BalanceBuy)call.getArgs()[1];
		HashMap<String, Object> extendProMap = res.getExtendMap();
		Integer subAccountId = (Integer) extendProMap.get("subAccountId");
		Integer userId = req.getUserId();
		createRelation(subAccountId, userId);

		// 活动切面
//		activityService.generateInvestNumber(userId, subAccountId);
	}
	
	/*@AfterReturning("eBankPointcut()")
	public void eBankAfter(JoinPoint call) {
		G2BReqMsg_Pay19NewCounter_NewCounterResultNotice req = (G2BReqMsg_Pay19NewCounter_NewCounterResultNotice)call.getArgs()[0];
		BsPayOrdersExample orderExample = new BsPayOrdersExample();
		orderExample.createCriteria().andOrderNoEqualTo(req.getMxOrderId()).andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS).andTransTypeEqualTo(Constants.TRANS_CARD_BUY_PRODUCT);
		List<BsPayOrders> orderList = payOrderMapper.selectByExample(orderExample);
		if(!CollectionUtils.isEmpty(orderList)) {
			BsPayOrders order = orderList.get(0);
			createRelation(order.getSubAccountId(), order.getUserId());
		}
		
	}*/
	
	private void createRelation(Integer subAccountId, Integer userId) {
		//查询用户id查询关联的客户经理
		BsUserCustomerManagerExample example = new BsUserCustomerManagerExample();
		example.createCriteria().andUserIdEqualTo(userId).andGradeEqualTo(1);
		List<BsUserCustomerManager> userCustomerList = userCustomerMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(userCustomerList)) {
			Integer dafyUserId = userCustomerList.get(0).getCustomerManagerDafyId();
			Date now = new Date();
			BsFinanceDafyRelation relation = new BsFinanceDafyRelation();
			relation.setSubAccountId(subAccountId);
			relation.setCustomerManagerDafyId(dafyUserId);
			relation.setCreateTime(now);
			relation.setUpdateTime(now);
			
			//通过达飞客户经理id调用云贷接口，获得客户经理信息
			B2GReqMsg_Customer_QueryUserById req = new B2GReqMsg_Customer_QueryUserById();
			req.setlUserId(String.valueOf(dafyUserId));
			B2GResMsg_Customer_QueryUserById res = dafyService.queryUserById(req);
			if(res.getCode() != null) {
				if(res.getCode() == 0 && res.getStrSalesman() != null) {
					Map<String,Object> model = res.getStrSalesman();
					relation.setDafyDeptId(Integer.valueOf((String)model.get("lDeptId")));
					relation.setDafyDeptCode((String)model.get("strDeptCode"));
					relation.setDafyDeptName((String)model.get("strDeptName"));
				}
				else {
					relation.setNote(res.getMsg());
				}
			}
			else {
				//告警
				specialJnlService.warn4Fail(null, "{达飞理财关系登记}子账户编号["+subAccountId+"]调用云贷接口失败",
						String.valueOf(subAccountId),"达飞理财关系登记",false);
				relation.setNote("调用云贷接口失败");
			}
			//新增达飞云贷理财关系表
			relationMapper.insertSelective(relation);
		}
	}
}
