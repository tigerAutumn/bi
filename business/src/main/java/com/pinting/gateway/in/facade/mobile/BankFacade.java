package com.pinting.gateway.in.facade.mobile;

import com.pinting.business.accounting.finance.service.UserCardOperateService;
import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsBindBankVO;
import com.pinting.business.model.vo.Pay19BankVO;
import com.pinting.business.model.vo.UnbindCheckResVO;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.site.*;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.in.util.InterfaceVersion;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @Project: business
 * @Title: BankFacade.java
 * @author Huang MengJian
 * @date 2015-2-11 下午6:43:08
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("appBank")
public class BankFacade {

    @Autowired
    private BankCardService      bankCardService;
    @Autowired
    private UserService          userService;
    @Autowired
    private UserTransDetailService userTransDetailService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private HolidayService holidayService;
    @Autowired
    private UserChannelService userChannelService;
    @Autowired
    private SpecialJnlService  specialJnlService;
    @Autowired
    private UserCardOperateService userCardOperateService;
    @Autowired
    private AssetsService assetsService;
	@Autowired
	private BsSysConfigService bsSysConfigService;
    /**
     * 查询卡bin
     * 
     * @param req
     * @param res
     */
    @InterfaceVersion("QueryBankBin/1.0.0")
    public void queryBankBin(ReqMsg_Bank_QueryBankBin req, ResMsg_Bank_QueryBankBin res) {
        BsCardBin bsCardBin = bankCardService.queryBankBin(req.getCardNo());
        res.setBankCardLen(bsCardBin.getBankCardLen());
        res.setBankId(bsCardBin.getBankId());
        res.setCardBin(bsCardBin.getCardBin());
        res.setCardBinLen(bsCardBin.getCardBinLen());
        res.setBankCardFuncType(bsCardBin.getBankCardFuncType());
        if(bsCardBin.getBankId() != null) {
            BsBank bank = bankCardService.findBankById(bsCardBin.getBankId());
            res.setBankName(bank.getName());
            
            Double amount = orderService.calculateTotal(req.getUserId(), bsCardBin.getBankId());
            res.setAmount(amount==null?0.0:amount);
        }
        
    }
    
    /**
     * 
     * @Title: selectBindBankList 
     * @Description: 查询用户绑定的银行卡
     * @param req
     * @param res
     * @throws
     */
    @InterfaceVersion("bindBankList/1.0.0")
    public void bindBankList(ReqMsg_Bank_bindBankList req, ResMsg_Bank_bindBankList res) {
    	List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
    	List<Map<String,Object>> able = new ArrayList<Map<String,Object>>();
    	List<Map<String,Object>> disable = new ArrayList<Map<String,Object>>();
		
		//查询账户余额
		BsSubAccount sub = userService.selectSubAccount(req.getUserId(), Constants.PRODUCT_TYPE_JSH, Constants.SUBACCOUNT_STATUS_OPEN).get(0);
		res.setSubAccountId(sub.getId());
		res.setAvailableBalance(sub.getAvailableBalance());
		
		List<BsBindBankVO> list = bankCardService.selectBindBankList(req.getUserId(), Constants.PAY_TYPE_QUICK, null);
		//查询支持的快捷支付银行列表
		ReqMsg_Bank_Pay19BankList reqPay19BankMsg = new ReqMsg_Bank_Pay19BankList();
		reqPay19BankMsg.setUserId(req.getUserId());
		ResMsg_Bank_Pay19BankList resPay19BankMsg = new ResMsg_Bank_Pay19BankList();
		pay19BankList(reqPay19BankMsg,resPay19BankMsg);
		
		//匹配用户快捷银行信息进行银行信息替换

		
    	for(BsBindBankVO vo : list) {

    		//判断19付银行通道是否可用
    		Date current = new Date();
    		Date start = vo.getForbiddenStart();
    		Date end = vo.getForbiddenEnd();
    		if (start != null && end != null) {
        		int before = com.pinting.business.util.DateUtil.compareTo(current, start);
        		int after = com.pinting.business.util.DateUtil.compareTo(current, end);
        		if(vo.getIsAvailable() == Constants.IS_AVAILABLE_ABLE) {
        			if(!(before < 0 || after > 0)) {
        				vo.setIsAvailable(Constants.IS_AVAILABLE_DISABLE);
        			}
        		}
    		}

    		vo.setOneTop(MoneyUtil.divide(vo.getOneTop(), 10000.0).doubleValue());
    		vo.setDayTop(MoneyUtil.divide(vo.getDayTop(), 10000.0).doubleValue());
			for (Map<String, Object> pay19QuickBank : resPay19BankMsg.getBankList()) {
				if (vo.getBankId().equals(pay19QuickBank.get("bankId"))) {
					vo.setOneTop((Double)pay19QuickBank.get("oneTop"));
					vo.setDayTop((Double)pay19QuickBank.get("dayTop"));
					vo.setMonthTop((Double)pay19QuickBank.get("monthTop"));
					vo.setIsAvailable((Integer)pay19QuickBank.get("isAvailable"));
					vo.setDailyNotice((String)pay19QuickBank.get("dailyNotice"));
				}
			}
    		
    		if(vo.getIsAvailable() == Constants.IS_AVAILABLE_ABLE) {
    			able.add(BeanUtils.transBeanMap(vo));
    		}
    		else {
    			disable.add(BeanUtils.transBeanMap(vo));
    		}
    	}
    	result.addAll(able);
    	result.addAll(disable);
    	Boolean s= userService.isBindBank(req.getUserId());
        res.setBindBank(s);
    	res.setBankList(result);
    	//与购买无关，查询最小充值金额
    	BsSysConfig bsSysConfig = sysConfigService.findConfigByKey(Constants.RECHANGE_LIMIT);
    	res.setRechangeLimit(bsSysConfig.getConfValue());
    }
    
    /**
     * 
     * @Title: queryDefaultBank 
     * @Description: 判断用户是否绑定，已绑定的老用户显示最近一次使用的银行卡
     * @param req
     * @param res
     * @throws
     */
    @InterfaceVersion("QueryDefaultBank/1.0.0")
    public void queryDefaultBank(ReqMsg_Bank_QueryDefaultBank req, ResMsg_Bank_QueryDefaultBank res) {
    	//判断用户是否绑卡
    	if(userService.isBindBank(req.getUserId())) {
    		res.setBindBank(true);
    		//查询最近一次使用的银行卡
    		BsBindBankVO vo = bankCardService.selectDefaultBank(req.getUserId());
    		if (vo == null) {
    			//告警
    			specialJnlService.warn4Fail(null, "{默认卡编号不匹配}用户ID:"+req.getUserId(),null,"默认卡编号不匹配",false);
    			//查询安全卡
    			vo = bankCardService.selectSafeBankCard(req.getUserId());
    			if(vo == null) {
    				//告警
        			specialJnlService.warn4Fail(null, "{安全卡编号不匹配}用户ID:"+req.getUserId(),null,"安全卡编号不匹配",false);
    				
    				res.setBindBank(false);
        			return;
    			}
    			
			}
    		//查询用户当期啊银行通道信息
    		Bs19payBank bs19payBank = bankCardService.selectChannelBankInfo(req.getUserId(), vo.getBankId());
    		//查询用户信息
    		BsUser user = userService.findUserById(req.getUserId());
    		if(user != null) {
    			res.setMobile(user.getMobile().trim());
    		}
    		//查询用户账户余额
    		BsSubAccount sub = userService.selectSubAccount(req.getUserId(), Constants.PRODUCT_TYPE_JSH, Constants.SUBACCOUNT_STATUS_OPEN).get(0);
    		res.setSubAccountId(sub.getId());
    		res.setAvailableBalance(sub.getAvailableBalance()==null?0.0:sub.getAvailableBalance());
    		res.setDailyNotice(bs19payBank.getDailyNotice());
    		res.setBankId(vo.getBankId());
    		res.setCardNo(vo.getCardNo());
    		res.setOneTop(MoneyUtil.divide(bs19payBank.getOneTop(), 10000.0).doubleValue());
    		res.setDayTop(MoneyUtil.divide(bs19payBank.getDayTop(), 10000.0).doubleValue());
    		res.setBankName(vo.getBankName());
    		res.setIsFirst(vo.getIsFirst());
    		//res.setMobile(vo.getMobile());
    		res.setUserName(vo.getUserName());
    		res.setIdCard(vo.getIdCard());
    		//银行卡当日已使用的额度
    		Double amount = orderService.calculateTotal(req.getUserId(), vo.getBankId());
    		res.setAmount(amount==null?0.0:amount);
    	}
    	else {
    		res.setBindBank(false);
    	}
    	//与购买无关，查询最小充值金额
    	BsSysConfig bsSysConfig = sysConfigService.findConfigByKey(Constants.RECHANGE_LIMIT);
    	res.setRechangeLimit(bsSysConfig.getConfValue());
    }

    /**
     * 先检查当前时间是否在节假日时间区间内（start时间的开始之前一天再往前推半个小时，结束时间当天的0点）
     * 1、在节假日禁止的时间区间内
     * 	 a、查询出所有主通道is_main=1快捷银行卡列表
     *   b、检查银行卡是否在禁用时间区间内：禁用区间则is_available返回2，否则直接返回
     * 
     * 2、不在节假日的时间区间内
     * 	 a、查询出所有优先级为1  channel_priority=1快捷银行卡列表
     *   b、根据有无用户ID判断是否进行用户优先通道替换
     * 	  	有用户ID传入：查询用户优先通道配置表中是否有用户优先通道配置，有优先通道则
     * 		根据通道ID查询出通道银行相关信息替换对应的银行
     * 	 	无用户ID传入：不进行替换
     *   c、检查银行卡是否在禁用时间区间内：禁用区间则is_available返回2，否则直接返回
     * 	  
     * 	
     * @Title: pay19BankList 
     * @Description: 查询支持快捷支付的优先级最高银行
     * @param req
     * @param res
     * @throws
     */
    @InterfaceVersion("Pay19BankList/1.0.0")
    public void pay19BankList(ReqMsg_Bank_Pay19BankList req, ResMsg_Bank_Pay19BankList res) {
    	/**
    	 * 1、节假日
			逻辑：
				a、查询出 is_main= 1的快捷支付银行列表

				b、判断forbidden时间区间
				          在时间区间内：通道设置为不可用 （is_available=2）
				          不在时间区间：直接返回 is_available
			
			2、 非节假日
			    a、先查出优先级为1的快捷银行列表
			    b、判断是否有userId 进行个人优先通道信息替换
			             有：
			                   查询user_channel表是否有数据  
			                        有则查询bank_channel_id,根据bank_channel_id查找bs_19pay_bank 表中对应的
			快捷银行信息，和list数据匹配替换对应的银行数据，得到一个新的List
			
			 c、判断forbidden时间区间
			          在时间区间内：通道设置为不可用 （is_available=2）
			          不在时间区间：直接返回 is_available

    	 */
    	
    	//检查当前时间是否在hiliday表控制的假日时间之内
    	boolean isHoliday = holidayService.isHolidayTimeList(new Date());
    	if (isHoliday) { //在假期时间内
    		//1-a、查询出 is_main= 1的快捷支付银行列表
	    	List<Pay19BankVO> list = bankCardService.selectMainBankList(Constants.PAY_TYPE_QUICK);
	    	List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
	    	List<Map<String,Object>> able = new ArrayList<Map<String,Object>>();
	    	List<Map<String,Object>> disable = new ArrayList<Map<String,Object>>();
	    	//1-b、判断forbidden时间区间
	    	// 	在时间区间内：通道设置为不可用 （is_available=2）
	    	//  不在时间区间：直接返回 is_available

	    	for(Pay19BankVO vo : list) {
	    		//判断19付银行通道是否可用
	    		Date current = new Date();
	    		Date start = vo.getForbiddenStart();
	    		Date end = vo.getForbiddenEnd();
	    		if (start != null && end != null) {
	    			int before = com.pinting.business.util.DateUtil.compareTo(current, start);
		    		int after = com.pinting.business.util.DateUtil.compareTo(current, end);
		    		if(vo.getIsAvailable() == Constants.IS_AVAILABLE_ABLE) {
		    			if(!(before < 0 || after > 0)) {
		    				vo.setIsAvailable(Constants.IS_AVAILABLE_DISABLE);
		    			}
		    		}
				}

	    		vo.setOneTop(MoneyUtil.divide(vo.getOneTop() == null ? 0: vo.getOneTop(), 10000.0).doubleValue());
	    		vo.setDayTop(MoneyUtil.divide(vo.getDayTop() == null ? 0: vo.getDayTop(), 10000.0).doubleValue());
	    		if(vo.getIsAvailable() == Constants.IS_AVAILABLE_ABLE) {
	    			able.add(BeanUtils.transBeanMap(vo));
	    		}
	    		else {
	    			disable.add(BeanUtils.transBeanMap(vo));
	    		}
	    	}
	    	result.addAll(able);
	    	result.addAll(disable);
	    	res.setBankList(result);
		}else { //非节假日
			// 2-a、先查出优先级为1的快捷银行列表
	    	List<Pay19BankVO> list = bankCardService.selectFirstBankList(Constants.PAY_TYPE_QUICK);
	    	//2-b、判断是否有userId 进行个人优先通道信息替换
	    	if (req.getUserId() != null) {
	    		//查询用户是否配置了优先支付通道
	    		List<BsUserChannel> bsUserChannelList = userChannelService.queryUserChannel(req.getUserId());
	    		if (!CollectionUtils.isEmpty(bsUserChannelList)) {
	    			for (BsUserChannel UserChanne : bsUserChannelList) {
		    			Pay19BankVO pay19BankVO= bankCardService.selectFirstBank(UserChanne.getBankChannelId());
		    			int temp = 0;
		    			//将用户配置通道的信息覆盖到List
		    			if (pay19BankVO!=null) {
							for(Pay19BankVO vo : list) {
								if (vo.getBankId() == pay19BankVO.getBankId()) {
									list.get(temp).setId(pay19BankVO.getId());
									list.get(temp).setBankId(pay19BankVO.getBankId());
									list.get(temp).setChannel(pay19BankVO.getChannel());
									list.get(temp).setChannelPriority(pay19BankVO.getChannelPriority());
									list.get(temp).setIsMain(pay19BankVO.getIsMain());
									list.get(temp).setPay19BankCode(pay19BankVO.getPay19BankCode());
									list.get(temp).setPayType(pay19BankVO.getPayType());
									list.get(temp).setOneTop(pay19BankVO.getOneTop()==null ? 0: pay19BankVO.getOneTop());
									list.get(temp).setDayTop(pay19BankVO.getDayTop()==null ? 0: pay19BankVO.getDayTop());
									list.get(temp).setMonthTop(pay19BankVO.getMonthTop()==null ? 0: pay19BankVO.getMonthTop());
									list.get(temp).setForbiddenStart(pay19BankVO.getForbiddenStart());
									list.get(temp).setForbiddenEnd(pay19BankVO.getForbiddenEnd());
									list.get(temp).setIsAvailable(pay19BankVO.getIsAvailable());
									list.get(temp).setNotice(pay19BankVO.getNotice());
									list.get(temp).setDailyNotice(pay19BankVO.getDailyNotice());
									list.get(temp).setBankName(pay19BankVO.getBankName());
									list.get(temp).setSmallLogo(pay19BankVO.getSmallLogo());
									list.get(temp).setLargeLogo(pay19BankVO.getLargeLogo());
								}
								temp = temp +1;
							}
						}
		    		
					}

				}
			}
	    	//2-c、判断forbidden时间区间
	    	//  在时间区间内：通道设置为不可用 （is_available=2）
	    	//  不在时间区间：直接返回 is_available

	    	List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
	    	List<Map<String,Object>> able = new ArrayList<Map<String,Object>>();
	    	List<Map<String,Object>> disable = new ArrayList<Map<String,Object>>();
	    	
	    	for(Pay19BankVO vo : list) {
	    		//判断19付银行通道是否可用
	    		Date current = new Date();
	    		Date start = vo.getForbiddenStart();
	    		Date end = vo.getForbiddenEnd();
	    		if (start != null && end != null) {
		    		int before = com.pinting.business.util.DateUtil.compareTo(current, start);
		    		int after = com.pinting.business.util.DateUtil.compareTo(current, end);
		    		if(vo.getIsAvailable() == Constants.IS_AVAILABLE_ABLE) {
		    			if(!(before < 0 || after > 0)) {
		    				vo.setIsAvailable(Constants.IS_AVAILABLE_DISABLE);
		    			}
		    		}
	    		}
	    		vo.setOneTop(MoneyUtil.divide(vo.getOneTop() == null ? 0: vo.getOneTop(), 10000.0).doubleValue());
	    		vo.setDayTop(MoneyUtil.divide(vo.getDayTop() == null ? 0: vo.getDayTop(), 10000.0).doubleValue());
	    		if(vo.getIsAvailable() == Constants.IS_AVAILABLE_ABLE) {
	    			able.add(BeanUtils.transBeanMap(vo));
	    		}
	    		else {
	    			disable.add(BeanUtils.transBeanMap(vo));
	    		}
	    	}
	    	result.addAll(able);
	    	result.addAll(disable);
	    	res.setBankList(result);
		}
    	
    }
    
    
    /**
     * 设置回款卡
     * 
     * @param req
     * @param res
     */
    @InterfaceVersion("SetIsFirstBankCard/1.0.0")
    public void setIsFirstBankCard(ReqMsg_Bank_SetIsFirstBankCard req, ResMsg_Bank_SetIsFirstBankCard res) {
        bankCardService.setIsFirstBankCard(req.getUserId(), req.getCardId());
    }
    
    /**
     * 回款路径列表
     * 
     * @param req
     * @param resp
     */
    @InterfaceVersion("ReturnPath/1.0.0")
    public void returnPath(ReqMsg_Bank_ReturnPath req, ResMsg_Bank_ReturnPath resp) {
        BsUser bsUser = userService.findUserById(req.getUserId());
        BsBankCard bankCard = bankCardService.findFirstBankCardByUserId(req.getUserId());
        if(bankCard != null && bankCard.getBankId() != null) {
        	BsBank bank = bankCardService.findBankById(bankCard.getBankId());
        	if(bank != null) {
        		 resp.setSmallLogo(bank.getSmallLogo());
        	     resp.setLargeLogo(bank.getLargeLogo());
        	}
        	
        	resp.setBankName(bankCard.getBankName());
            resp.setCardId(bankCard.getId());
            resp.setCardNo(bankCard.getCardNo());
        } 
        resp.setReturnPath(String.valueOf(bsUser.getReturnPath()));
    }
    
    @InterfaceVersion("QueryFirstCard/1.0.0")
    public void queryFirstCard(ReqMsg_Bank_QueryFirstCard req,ResMsg_Bank_QueryFirstCard resp){
    	BsBankCard bankCard = bankCardService.findFirstBankCardByUserId(req.getUserId());
    	if(bankCard != null && bankCard.getBankId() != null) {
    		BsBank bank = bankCardService.findBankById(bankCard.getBankId());
    		if(bank != null) {
    			resp.setLargeLogo(bank.getLargeLogo());
            	resp.setSmallLogo(bank.getSmallLogo());
    		}
    		resp.setBankId(bankCard.getBankId());
    		resp.setBankName(bankCard.getBankName());
    		resp.setCardNo(bankCard.getCardNo());
    		resp.setCardId(bankCard.getId());
    	}
    	//查询用户账户余额
		BsSubAccount sub = userService.selectSubAccount(req.getUserId(), Constants.PRODUCT_TYPE_JSH, Constants.SUBACCOUNT_STATUS_OPEN).get(0);
		resp.setCan_withdraw(sub.getCanWithdraw());
		DateUtil.firstDayOfMonth();
		//查询当月用户剩余可免费提现次数
		Integer freeWithdrawTimes =  assetsService.queryFreeWithdrawTimesByUserId(req.getUserId());
		resp.setCan_withdraw_times(freeWithdrawTimes);
		
		Map<String, Object> map =  assetsService.queryWithdrawInstrction();
		String eachMonthWithdrawTimes = (String)map.get("eachMonthWithdrawTimes");
		String  withdrawCounterFee = (String)map.get("withdrawCounterFee");
		
		resp.setEachMonthWithdrawTimes(Integer.parseInt(eachMonthWithdrawTimes));
		resp.setWithdrawCounterFee(Double.parseDouble(withdrawCounterFee));
		
		
		//与购买无关，查询最小充值金额
    	BsSysConfig bsSysConfig1 = sysConfigService.findConfigByKey(Constants.RECHANGE_LIMIT);
    	resp.setRechageLimit(bsSysConfig1.getConfValue());
		
		//与购买无关，查询最小提现金额
    	BsSysConfig bsSysConfig2 = sysConfigService.findConfigByKey(Constants.WITHDRAW_LIMIT);
    	resp.setWithdrawLimit(bsSysConfig2.getConfValue());
    	
    	//查询当日提现限额和用户已提现金额
		BsSysConfig dayUpperLimitConfig = bsSysConfigService.findKey(Constants.DAY_WITHDRAW_UPPER_LIMIT);
		Double dayUpperLimit = Double.parseDouble(dayUpperLimitConfig.getConfValue());
		Double amount = orderService.sumWithdrawUpperLimit(req.getUserId());
		
		resp.setDayWithdrawUpperLimit(dayUpperLimit);
		resp.setUserWithdrawAmount(amount);
		//查询单笔提现上限
    	BsSysConfig bsSysConfigWithdrawLimit = sysConfigService.findConfigByKey(Constants.SINGLE_WITHDRAW_UPPER_LIMIT);
    	resp.setSingleWithdrawUpperLimit(Double.parseDouble(bsSysConfigWithdrawLimit.getConfValue()));

		BsSysConfig withdrawApplyLimitConfig = bsSysConfigService.findKey(Constants.WITHDRAW_APPLY_LIMIT);
		Double withdrawApplyLimit = withdrawApplyLimitConfig == null ? 50000d : Double.valueOf(withdrawApplyLimitConfig.getConfValue());
		resp.setWithdrawApplyLimit(withdrawApplyLimit);
	}
    
    
    /**
     * 
     * @Title: alreadyUseMoney 
     * @Description: 计算用户当日已使用的银行额度
     * @param req
     * @param res
     * @throws
     */
    @InterfaceVersion("AlreadyUseMoney/1.0.0")
    public void alreadyUseMoney(ReqMsg_Bank_AlreadyUseMoney req, ResMsg_Bank_AlreadyUseMoney res) {
    	Double amount = orderService.calculateTotal(req.getUserId(), req.getBankId());
    	res.setAmount(amount==null?0.0:amount);
    }
    
    /**
     * 
     * @Title: queryBankInfoByUser 
     * @Description: 查询用户绑定的单个银行信息
     * @param req
     * @param res
     * @throws
     */
    @InterfaceVersion("QueryBankInfoByUser/1.0.0")
    public void queryBankInfoByUser(ReqMsg_Bank_QueryBankInfoByUser req, ResMsg_Bank_QueryBankInfoByUser res) {
    	List<BsBindBankVO> list = bankCardService.selectBindBankList(req.getUserId(), Constants.PAY_TYPE_QUICK, req.getBankId());
    	if(list != null && list.size() > 0) {
    		BsBindBankVO vo = list.get(0);
    		res.setBankId(vo.getBankId());
    		res.setBankName(vo.getBankName());
    		res.setCardNo(vo.getCardNo());
    		res.setIdCard(vo.getIdCard());
    		res.setMobile(vo.getMobile());
    		res.setUserName(vo.getUserName());
    		res.setDailyNotice(vo.getDailyNotice());
    	}
    }
    
    /**
     * 
     * @Title: preBindCard
     * @Description: 我的银行卡--预绑卡
     * @param req
     * @param res
     * @throws
     */
    @InterfaceVersion("PreBindCard/1.0.0")
    public void preBindCard(ReqMsg_Bank_PreBindCard req, ResMsg_Bank_PreBindCard res) {
    	String orderNo = userCardOperateService.preBindCard(req.getUserName(), req.getIdCard(), req.getCardNo(), req.getMobile(), req.getBankId(), req.getUserId(), req.getTerminalType());
    	res.setOrderNo(orderNo);
    }
    
    /**
     * 
     * @Title: preBindCard
     * @Description: 我的银行卡--正式绑卡
     * @param req
     * @param res
     * @throws
     */
    @InterfaceVersion("BindCard/1.0.0")
    public void bindCard(ReqMsg_Bank_BindCard req, ResMsg_Bank_BindCard res) {
		userCardOperateService.bindCard(req.getOrderNo(), req.getSmsCode(), req.getUserId());
    }
    
    
    /**
     * 
     * 根据支付通道和支付类型查询银行列表
     * @Description: 查询宝付支持的快捷支付银行卡列表
     * @param req   ReqMsg_Bank_ChannelBank
     * @param res   ResMsg_Bank_ChannelBank
     */
    @InterfaceVersion("ChannelBank/1.0.0")
    public void channelBank(ReqMsg_Bank_ChannelBank req, ResMsg_Bank_ChannelBank res) {
        List<Pay19BankVO> bankList = bankCardService.queryBankByChannelAndPayType(req.getPayChannel(), req.getPayType());
        res.setBankList(BeanUtils.classToArrayList(bankList));
    }
    
    
    /**
     * @Title: unbindCheck
     * @Description: 解绑前校验
     * @param req
     * @param res
     * @throws
     */
    @InterfaceVersion("UnbindCheck/1.0.0")
    public void unbindCheck(ReqMsg_Bank_UnbindCheck req, ResMsg_Bank_UnbindCheck res) {
    	UnbindCheckResVO resVo = userCardOperateService.unBindCheck(req.getUserId(), req.getBankCardId());
    	res.setCheckResult(resVo.getCheckResult());
    	res.setIdCard(resVo.getIdCard());
    	res.setUserName(resVo.getUserName());
    }

	/**
	 *
	 * @Title: UnbindApplyPoliceVerify
	 * @Description: 解绑卡申请--人脸核身验证
	 * @param req
	 * @param res
	 * @throws
	 */
	@InterfaceVersion("UnbindApplyPoliceVerify/1.0.0")
	public void unbindApplyPoliceVerify(ReqMsg_Bank_UnbindApplyPoliceVerify req, ResMsg_Bank_UnbindApplyPoliceVerify res) {
		userCardOperateService.unbindApplyPoliceVerify(req, res);
	}

	/**
	 *
	 * @Title: UploadPicPoliceVerify
	 * @Description: 解绑卡申请--人脸核身验证
	 * @param req
	 * @param res
	 * @throws
	 */
	@InterfaceVersion("UploadPicPoliceVerify/1.0.0")
	public void uploadPicPoliceVerify(ReqMsg_Bank_UploadPicPoliceVerify req, ResMsg_Bank_UploadPicPoliceVerify res) {
	    userCardOperateService.uploadPicPoliceVerify(req, res);
	}
}
