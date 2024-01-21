package com.pinting.business.service.manage;

import java.util.List;
import java.util.Map;

import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsSysSubAccount;
import com.pinting.business.model.vo.BsActivityLuckyDrawVO;
/**
 * 子账户服务接口
 * @author caonengwen
 *
 */
public interface BsSubAccountService {
	/**
	 * 查询
	 * @param record
	 */
	public List<BsSubAccount> findList(BsSubAccount record);
	
	/**
	 * 统计投资用户、投资金额、成交笔数
	 * @param map
	 * @return map
	 */
	public Map<String,Object> countSubAccount(Map<String,Object> map);
	
	/**
	 * 公众号统计投资用户、投资金额、成交笔数
	 * @param map
	 * @return map
	 */
	public Map<String,Object> countSubAccountByTime(Map<String,Object> map);
	
	/**
	 * 公众号统计产品购买查询
	 * @param map
	 * @return map
	 */
	public Map<String,Object> countSubAccountByPartner(Map<String,Object> map);
	
	/**
	 * 统计奖励金户余额
	 * @return double
	 */
	public double countBonusAccBalance();
	
	/**
	 * 查询用户的加权投资期限
	 * return Map<String,Object>
	 */
	public Map<String,Object> findWeightInvestTrem(Map<String,Object> map);
	
	/**
	 * 统计投资用户、投资金额、成交笔数、年化金额
	 * @param map
	 * @return map
	 */
	public Map<String,Object> statisticSubAccount(Map<String,Object> map);
	
	
	 /**
     * 站岗户资金自动退回消息推送
     * @Title: chargeAuthActBackMessage 
     * @Description: 站岗户资金自动退回消息推送
     * @param type 1、短信2、微信3、app推送
     * @param mobiles 手机号（当微信和app推送时，传入null）
     * @param userIds 用户ID（当短信推送时，传入null）
     * @param openBalance 委托金额（开户金额）
     * @param balance 实际出借金额
     * @param principalAmount 出借本金
     * @param debtAmount  债转付息
     * @throws
     */
    void chargeAuthActBackMessage(Integer type, List<String> mobiles, List<Integer> userIds, String openBalance, String balance,String principalAmount,String debtAmount,String productId,String subAccountId,String openTime);
    
    /**
     * 根据站岗户ID（auth）查询此比委托计划状态
     * @param subAccountId 站岗子账户ID
     * @return
     */
    String queryEntrustStatusBySubAccountId(Integer subAccountId);

	/**
	 * 根据赞分期代偿人用户编号查询账户余额（代偿人账户余额）
	 * @return
     */
	BsSubAccount queryDEPJSHSubAccountByUserId();

	/**
	 * 统计产品站岗红包金额
	 * @return double
	 */
	public Double countRedAccBalance();
	
	/**
	 * 统计7贷产品站岗红包金额
	 * @return double
	 */
	public Double countRed7AccBalance();
	
	/**
	 * 根据账户号查询记录并加行级锁
	 * @param userId
	 * @param code
	 * @return 查询不到记录则返回null
     */
	BsSubAccount findSubAccount4Lock(Integer userId, String code);
	
	/**
	 * 统计产品站岗红包金额
	 * @param productType
	 * @return double
	 */
	public Double sumRedAccBalanceByType(String productType);
	
	/**
	 * 统计周周乐活动日总年化投资额
	 * @param 
	 * @return 
	 */
	public Double selectWeekhaySumAmount();
	
	/**
	 * 统计周周乐活动日年化投资达到额度的用户列表
	 * @param 
	 * @return 
	 */
	public List<BsActivityLuckyDrawVO> selectWeekhayBigInvestors(Double investAmount);
	
	/**
	 * 统计周周乐活动当日投资用户手机号列表
	 * @param 
	 * @return 
	 */
	public List<BsActivityLuckyDrawVO> selectWeekhayAllInvestors();

	/* 2018财务管理-财务总账查询-站岗户 start */
	/* 1、云贷站岗户余额 */
	public Double querySumBgwAuthYunBalance();

	/* 2、7贷站岗户余额 */
	public Double querySumBgwAuthSevrnBalance();

	/* 3、赞时贷站岗户余额 */
	public Double querySumBgwAuthZsdBalance();

	/* 4、赞分期站岗户余额 */
	public Double querySumBgwAuthZanBalance();

	/* 5、财务总账查询（恒丰）–新增借款人余额 */
	public Double querySumLoanBalance();
	/* 2018财务管理-财务总账查询-站岗户 end */

	/* 2018财务管理-财务总账查询-新增自由站岗户业务数据 start */
	/* 1、统计自由站岗产品站岗红包金额 */
	public Double querySumRedFreeAccBalance();

	/* 2、统计自由站岗户余额 */
	public Double querySumBgwAuthFreeBalance();
	/* 2018财务管理-财务总账查询-新增自由站岗户业务数据 end */
	
	/**
	 * 平台存量数据-投资余额本金
	 * @param endTime
	 * @return
	 */
	Double sumFinancesAuthBalance(String endTime, String productType);
	
	/**
	 * 平台存量数据-待还余额本金
	 * @param endTime
	 * @return
	 */
	Double sumLoanRepayBalance(String endTime, String partnerCode);
	
}
