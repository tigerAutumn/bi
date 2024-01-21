package com.pinting.business.service.manage;

import java.util.List;
import java.util.Map;

import com.pinting.business.accounting.finance.enums.UnBindCheckResultEnum;
import com.pinting.business.model.BsBankCard;
import com.pinting.business.model.BsPayReceipt;
import com.pinting.business.model.vo.BsBankCardVO;
import com.pinting.business.model.vo.BsBindBankVO;
import com.pinting.business.model.vo.BsPayOrdersVO;
import com.pinting.business.model.vo.UnBindBankCardRes;

public interface BsBankCardService {
	
	/**
	 * 分页 查询银行卡信息与用户信息表 (bs_bank_card与bs_user表连接)
	 * @param pageNum 页码
	 * @param numPerPage 每页条数
	 * @return 成功返回意见反馈列表，否则返回null
	 */
	public List<BsBankCardVO> findBsBankCardByPage(String cardOwner,String mobile,String obligateMobile,String idCard,Integer bankId,Integer status,Integer isFirst,String cardNo,String pageNum,String numPerPage);
	public int bankCardUserCount(String cardOwner,String mobile,String obligateMobile,String idCard,Integer bankId,Integer status,Integer isFirst,String cardNo);
	
	/**
	 * 分组查询开户行
	 * @return
	 */
	public List<BsBankCardVO> groupByBankName();
	
	/**
	 * 查询bs_bank_card表中绑定失败银行卡在bs_pay_orders记录表中购买失败的数据
	 */
	public int cardRecordCount(String bankCardNo,Integer status);
	public List<BsPayOrdersVO> findCardRecordByPage(String bankCardNo,Integer status,String pageNum,String numPerPage);
	
	/**
	 * id作为条件修改信息
	 * @param record
	 * @return
	 */
	public int updateBsBankCard(BsBankCard record);
	
	/**
	 * 根据BsBankCard表id，查询BsBankCard信息
	 * @author bianyatian
	 * @param id
	 * @return
	 */
	public BsBankCard find(Integer id);
		
	public List<BsBankCard> findBankCards();
	
	/**
	 * 查询绑卡回执信息
	 * @param userId
	 * @param bankCardNo
	 * @param channel
	 * @return 无此卡回执信息或信息有误则返回null
	 */
	public BsPayReceipt findCardReceipt(Integer userId, String bankCardNo, String channel);
	
	/**
	 * 根据主键修改此卡回执信息为解绑
	 * @param id
	 */
	public void modifyCardReceiptUnBind(Integer id);

	/**
	 * 查询赞分期代偿人的绑卡信息
	 * @param userId
	 * @return
     */
	public BsBindBankVO queryZanBankCardInfo(Integer userId);

	/**
	 * 查询cardNo正在使用的银行卡信息
	 * @param cardNo
	 * @param status
	 * @return
	 */
	public BsBankCard findBsBankCardByUserIdAndCardNo(String cardNo, Integer status);
	
	/**
	 * 根据用户id和bs_bank_crad 表id解绑银行卡
	 * @author bianyatian
	 * @param userId
	 * @param bankCardId
	 * @return
	 */
	UnBindBankCardRes unBindCard4ManagePoliceVerify(Integer userId, Integer bankCardId);
	
	
	/**
	 * 根据用户id查询用户是否有正在提现中的交易数据
	 * 和正在提现中、正在充值中的订单数据
	 * @author bianyatian
	 * @param userId
	 * @return
	 */
	UnBindCheckResultEnum userWithdrawTopUpCheck(Integer userId);
}

