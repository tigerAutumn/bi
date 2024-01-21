package com.pinting.business.aspect;

/**
 * 
 * @ClassName: Product49Aspect 
 * @Description: 针对"318大促新手标"(产品ID为49)进行后置通知拦截
 * @author lenovo
 * @date 2016年3月16日 下午1:37:50 
 *
 */
//@Aspect
//@Component
//@Order(2)
@Deprecated
public class Product49Aspect {
	
	/*@Autowired
	private BsUserProductLimitMapper limitMapper;
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Autowired
	private BsUserProductLimitDetailMapper detailMapper;
	
	@Autowired
	private BsPayOrdersMapper payOrderMapper;
	
	@Autowired
	private BsSubAccountMapper subAccountMapper;
	
	@Autowired
	private BsRedPacketInfoMapper redPacketInfoMapper;

	@Pointcut("execution(public * com.pinting.business.accounting.buyproduct.service.impl.UserBuyProductServiceImpl.notify(..))")
	public void pay19Pointcut(){}
	
	@Pointcut("execution(public * com.pinting.business.accounting.buyproduct.service.impl.UserBuyProductServiceImpl.channelNotify(..))")
	public void reapalPointcut(){}
	
	@Pointcut("execution(public * com.pinting.business.accounting.buyproduct.service.impl.UserBuyProductServiceImpl.balanceBuy(..))")
	public void balanceBuyPointcut(){}
	
	@Pointcut("execution(public * com.pinting.business.accounting.buyproduct.service.impl.UserBuyProductServiceImpl.notifyEBank(..))")
	public void eBankPointcut(){}*/
	
	/*@AfterReturning("pay19Pointcut()")
	public void pay19After(JoinPoint call) {
		G2BReqMsg_Pay19QuickPay_PayResultNotice req = (G2BReqMsg_Pay19QuickPay_PayResultNotice)call.getArgs()[0];
		BsPayOrdersExample orderExample = new BsPayOrdersExample();
		orderExample.createCriteria().andUserIdEqualTo(Integer.parseInt(req.getUserId())).andOrderNoEqualTo(req.getOrderId()).andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS);
		List<BsPayOrders> orderList = payOrderMapper.selectByExample(orderExample);
		if(!CollectionUtils.isEmpty(orderList)) {
			updateUserLimit(orderList.get(0));
		}
	}
	
	@AfterReturning("reapalPointcut()")
	public void reapalAfter(JoinPoint call) {
		G2BReqMsg_ReapalQuickPay_ReapalNotify req = (G2BReqMsg_ReapalQuickPay_ReapalNotify)call.getArgs()[0];
		BsPayOrdersExample orderExample = new BsPayOrdersExample();
		orderExample.createCriteria().andOrderNoEqualTo(req.getOrderNo()).andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS);
		List<BsPayOrders> orderList = payOrderMapper.selectByExample(orderExample);
		if(!CollectionUtils.isEmpty(orderList)) {
			updateUserLimit(orderList.get(0));
		}
	}
	
	@AfterReturning("balanceBuyPointcut()")
	public void balanceBuyAfter(JoinPoint call) {
		ReqMsg_RegularBuy_BalanceBuy req = (ReqMsg_RegularBuy_BalanceBuy)call.getArgs()[0];
		//318大促新手标
		if(Constants.NEW_USER_PRODUCT_ID.equals(req.getProductId())) {
			final Integer userId = req.getUserId();
			final Integer productId = req.getProductId();
			final Double finalAmount = req.getAmount();
			transactionTemplate.execute(new TransactionCallbackWithoutResult(){

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					Date now = new Date();
					List<BsUserProductLimit> userLimitList = limitMapper.selectUserLimitForLock(userId, productId);
					if(!CollectionUtils.isEmpty(userLimitList)) {
						BsUserProductLimit userLimit = userLimitList.get(0);
						Double left = MoneyUtil.subtract(userLimit.getLeftAmount(), finalAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						userLimit.setLeftAmount(left);
						userLimit.setUpdateTime(now);
						limitMapper.updateByPrimaryKeySelective(userLimit);
						
						BsUserProductLimitDetail detail = new BsUserProductLimitDetail();
						detail.setAmount(-finalAmount);
						detail.setCreateTime(now);
						detail.setEvent("购买318大促新手标,用户额度减少"+finalAmount+"元");
						detail.setProductId(productId);
						detail.setUserId(userId);
						detailMapper.insertSelective(detail);
					}
				}
				
			});
		}
	}
	
	@AfterReturning("eBankPointcut()")
	public void eBankAfter(JoinPoint call) {
		G2BReqMsg_Pay19NewCounter_NewCounterResultNotice req = (G2BReqMsg_Pay19NewCounter_NewCounterResultNotice)call.getArgs()[0];
		BsPayOrdersExample orderExample = new BsPayOrdersExample();
		orderExample.createCriteria().andOrderNoEqualTo(req.getMxOrderId()).andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS);
		List<BsPayOrders> orderList = payOrderMapper.selectByExample(orderExample);
		if(!CollectionUtils.isEmpty(orderList)) {
			updateUserLimit(orderList.get(0));
		}
	}
	
	private void updateUserLimit(BsPayOrders order) {
		//查询子账户
		BsSubAccount subAccount = subAccountMapper.selectByPrimaryKey(order.getSubAccountId());
		//318大促新手标
		if(Constants.NEW_USER_PRODUCT_ID.equals(subAccount.getProductId())) {
			Double amount = order.getAmount();
			//查询红包
			BsRedPacketInfoExample redPacketExample = new BsRedPacketInfoExample();
			redPacketExample.createCriteria().andOrderNoEqualTo(order.getOrderNo()).andStatusEqualTo(Constants.RED_PACKET_STATUS_USED);
			List<BsRedPacketInfo> redPacketList = redPacketInfoMapper.selectByExample(redPacketExample);
			if(!CollectionUtils.isEmpty(redPacketList)) {
				amount = MoneyUtil.add(amount, redPacketList.get(0).getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
			final Double finalAmount = amount;
			final Integer userId = order.getUserId();
			final Integer productId = subAccount.getProductId();
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult(){

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					Date now = new Date();
					List<BsUserProductLimit> userLimitList = limitMapper.selectUserLimitForLock(userId, productId);
					if(!CollectionUtils.isEmpty(userLimitList)) {
						BsUserProductLimit userLimit = userLimitList.get(0);
						Double left = MoneyUtil.subtract(userLimit.getLeftAmount(), finalAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						userLimit.setLeftAmount(left);
						userLimit.setUpdateTime(now);
						limitMapper.updateByPrimaryKeySelective(userLimit);
						
						BsUserProductLimitDetail detail = new BsUserProductLimitDetail();
						detail.setAmount(-finalAmount);
						detail.setCreateTime(now);
						detail.setEvent("购买318大促新手标,用户额度减少"+finalAmount+"元");
						detail.setProductId(productId);
						detail.setUserId(userId);
						detailMapper.insertSelective(detail);
					}
				}
				
			});
		}
		
	}*/
}
