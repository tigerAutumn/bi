package com.pinting.business.service.site.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.dao.BsProductMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.BsPayOrdersExample;
import com.pinting.business.model.BsProduct;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.vo.UserInfoVO;
import com.pinting.business.service.site.OrderService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.in.util.MethodRole;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private BsPayOrdersMapper payOrderMapper;
	@Autowired
	private BsUserMapper bsUserMapper;
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private BsProductMapper bsProductMapper;
	
	@Override
	@MethodRole("APP")
	public Double calculateTotal(Integer userId, Integer bankId) {
		Date now = new Date();
		String startDay = DateUtil.format(now, "yyyy-MM-dd")+" 00:00:00";
		String endDay = DateUtil.format(now, "yyyy-MM-dd")+" 23:59:59";
		Double totalDay = payOrderMapper.calculateTotal(Constants.CHANNEL_TRS_QUICKPAY, startDay, endDay, bankId, userId);
		if(totalDay == null) {
			totalDay = 0.0;
		}
		return totalDay;
	}

	@Override
	@MethodRole("APP")
	public Double dayTopUpTotal(Integer userId) {
		Date now = new Date();
		String startDay = DateUtil.format(now, "yyyy-MM-dd")+" 00:00:00";
		String endDay = DateUtil.format(now, "yyyy-MM-dd")+" 23:59:59";
		Double totalDay = payOrderMapper.topUpTotal(startDay, endDay, userId);
		if(totalDay == null) {
			totalDay = 0.0;
		}
		return totalDay;
	}

	@Override
	@MethodRole("APP")
	public Double yesterdayTopUpTotal(Integer userId) {
		Date now = new Date();
		Long yes = now.getTime() - 24*3600*1000;
		Date yester = new Date(yes);
		String startDay = DateUtil.format(yester, "yyyy-MM-dd")+" 00:00:00";
		String endDay = DateUtil.format(yester, "yyyy-MM-dd")+" 23:59:59";
		Double yesterday = payOrderMapper.topUpTotal(startDay, endDay, userId);
		if(yesterday == null) {
			yesterday = 0.0;
		}
		return yesterday;
	}

	@Override
	@MethodRole("APP")
	public UserInfoVO reapalQuickCMB(String orderNo,Integer productId) {
		BsPayOrdersExample example = new BsPayOrdersExample();
		example.createCriteria().andOrderNoEqualTo(orderNo);
		List<BsPayOrders> bsPayOrders = payOrderMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(bsPayOrders)) {
			return null;
		}
		/**
		 	private Integer userId;  //用户ID
			private Double amount;   //购买金额
			private String cardNo; //银行卡号
			private String userName;   //用户姓名
			private String idCard; //身份证号
			private String mobile;  //手机号码
			private Integer productId; //理财产品ID
			private Integer bankId; //银行ID
			private String bankName; //银行名称
			private Integer isBind; //用户是否绑定@1已绑定,2未绑定
			private Integer transType; //交易类型1卡购买,2充值
			private Integer terminalType; //终端类型@1手机端,2PC端
			private Integer placeOrder; //下单类型@1预下单,2正式下单
			private String orderNo; //币港湾订单号
			private String verifyCode; //正式下单时输入的短信
			private String userMacAddr; //用户MAC地址
			private String userIpAddr; //用户IP地址
		 */
		BsUser bsUser = bsUserMapper.selectByPrimaryKey(bsPayOrders.get(0).getUserId());
		BsProduct bsProduct = bsProductMapper.selectByPrimaryKey(productId);
		UserInfoVO  userInfoVO = new UserInfoVO();
		userInfoVO.setUserId(bsPayOrders.get(0).getUserId());
		userInfoVO.setAmount(bsPayOrders.get(0).getAmount());
		userInfoVO.setCardNo(bsPayOrders.get(0).getBankCardNo());
		userInfoVO.setUserName(bsPayOrders.get(0).getUserName());
		userInfoVO.setIdCard(bsPayOrders.get(0).getIdCard());
		userInfoVO.setMobile(bsPayOrders.get(0).getMobile());
		if (bsProduct != null) {
			userInfoVO.setProductId(bsProduct.getId());
			userInfoVO.setProductName(bsProduct.getName());
		}
		userInfoVO.setBankId(bsPayOrders.get(0).getBankId());
		userInfoVO.setBankName(bsPayOrders.get(0).getBankName());
		userInfoVO.setIsBind(bsUser.getIsBindBank());
		if ("CARD_BUY_PRODUCT".equals(bsPayOrders.get(0).getTransType())) {
			userInfoVO.setTransType(1);
		}else {
			userInfoVO.setTransType(2);
		}
		
		return userInfoVO;
	}

	@Override
	public Double sumWithdrawUpperLimit(int userId) {
		Double amount = payOrderMapper.sumWithdrawUpperLimit(userId);
		return amount;
	}

	@Override
	public Double sumWithdrawCheckAmount(int userId) {
		Double amount = payOrderMapper.sumWithdrawCheckAmount(userId);
		return amount;
	}

	@Override
	public Double topUpSubBuyBalanceToday(int userId) {
		Double amount = payOrderMapper.topUpSubBuyBalanceToday(userId);
		return amount;
	}

	@Override
	public Double sumPayingAmountByTransType(Integer userId, String transType, int status) {
		Double amount = 0d;
		BsPayOrdersExample example = new BsPayOrdersExample();
		example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(status).andTransTypeEqualTo(transType);
		List<BsPayOrders> payOrderses = payOrderMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(payOrderses)) {
			for (BsPayOrders order: payOrderses) {
				amount = MoneyUtil.add(order.getAmount(), amount).doubleValue();
			}
		}
		return amount;
	}
}
