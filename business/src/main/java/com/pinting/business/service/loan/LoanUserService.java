package com.pinting.business.service.loan;

import com.pinting.business.model.LnBindCard;
import com.pinting.business.model.LnUser;
import com.pinting.business.model.vo.LnLoanVO;
import com.pinting.business.model.vo.ZANRevenueModelVO;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_LoanCif_AddLoaner;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Loan_QueryLoan;
import com.pinting.gateway.hessian.message.loan.G2BResMsg_LoanCif_AddLoaner;

/**
 * 借款用户相关服务
 * @author bianyatian
 * @2016-8-31 下午4:10:19
 */
public interface LoanUserService {

	/**
	 * 借款人信息登记
	 * @param req
	 */
	void addLoanCif(G2BReqMsg_LoanCif_AddLoaner req,G2BResMsg_LoanCif_AddLoaner res);

	/**
	 * 查询借款人信息是否存在
	 * @param userId 合作方用户id
	 * @param channel 渠道
	 * @return
	 */
	LnUser queryLoanUserExist(String userId, String channel);

	/**
	 * 查询用户是否绑卡
	 * @param userId 合作方用户id
	 * @param bindId bgw绑定id
	 * @param channel 渠道
	 * @return
	 */
	LnBindCard queryLoanBindCardExist(String userId, String bindId, String channel);


	/**
	 * 查询借款状态
	 * @param req
	 * @return
	 */
	LnLoanVO queryLoanStatus(G2BReqMsg_Loan_QueryLoan req);
	
	/**
	 * 某个借款用户是否有逾期
	 * @param loanUserId 借款用户编号
	 * @return true-有，false-没有
	 */
	boolean isLoanUserLate(Integer loanUserId);
	
	/**
	 * 某个借款用户某笔借款是否有逾期
	 * @param loanUserId 借款用户编号
	 * @param loanId 借款编号
	 * @return
	 */
	boolean isLoanUserLoanLate(Integer loanUserId, Integer loanId);
	
	/**
	 * 某个借款用户某笔某期是否有逾期
	 * @param loanUserId 借款用户编号
	 * @param loanId 借款编号
	 * @param serialId 期次
	 * @return
	 */
	boolean isLoanUserLoanTermLate(Integer loanUserId, Integer loanId, Integer serialId);

	/**
	 * 计算某笔借款某期应还本金
	 * @param loanId 借款编号
	 * @param serialId 期次
	 * @return
	 */
	Double calPrincipal(Integer loanId, Integer serialId);
	
	/**
	 * 计算某笔借款某期应还币港湾利息
	 * @param loanId 借款编号
	 * @param serialId 期次
	 * @return
	 */
	Double calInterest(Integer loanId, Integer serialId);
	
	/**
	 * 计算某笔借款某期应还监管费
	 * @param loanId 借款编号
	 * @param serialId 期次
	 * @return
	 */
	Double calSuperviseFee(Integer loanId, Integer serialId);
	
	/**
	 * 计算某笔借款某期应还信息服务费
	 * @param loanId 借款编号
	 * @param serialId 期次
	 * @return
	 */
	Double calInfoServiceFee(Integer loanId, Integer serialId);
	
	/**
	 * 计算某笔借款某期应还账户管理费
	 * @param loanId 借款编号
	 * @param serialId 期次
	 * @return
	 */
	Double calAccountManageFee(Integer loanId, Integer serialId);
	
	/**
	 * 计算某笔借款某期应还保费
	 * @param loanId 借款编号
	 * @param serialId 期次
	 * @return
	 */
	Double calPremium(Integer loanId, Integer serialId);
	
	/**
	 * 计算某笔借款某期应提滞纳金
	 * @param loanId 借款编号
	 * @param serialId 期次
	 * @return
	 */
	Double calLateFee(Integer loanId, Integer serialId);
	
	/**
	 * 计算某笔借款某期应还总费用
	 * =本金+利息+监管费+信息服务费+账户管理费+保费+滞纳金
	 * @param loanId 借款编号
	 * @param serialId 期次
	 * @return
	 */
	Double calTotalRepay(Integer loanId, Integer serialId);
	
	/**
	 * 计算某笔借款某期应还总费用--在计算营收时使用
	 * @param loanId
	 * @param serialId
	 * @return
	 */
	ZANRevenueModelVO calTotalRepay4ZANRevenue(Integer loanId, Integer serialId);
	
	/**
	 * =本金+利息+监管费+信息服务费+账户管理费+保费
	 * @param loanId 借款编号
	 * @param serialId 期次
	 * @return
	 */
	Double calTotalRepayNoLate(Integer loanId, Integer serialId);

	/**
	 * 自主放款-查询用户是否有绑卡记录
	 * @param userId 合作方用户id
	 * @param channel 渠道
	 * @return
	 */
	LnBindCard queryIncrBindCardExist(String userId, String channel, String bankCard);
	/**
	 * 根据身份证号查询用户是否已经进行过实名认证并且返回恒丰平台客户编号
	 * @param idCard 身份证号
	 * @return
	 */
	String queryBindCardExist(String idCard);
	
	/**
	 * 查询借款人信息
	 * @param hfUserId 恒丰用户id
	 * @return
	 */
	LnUser queryLoanUserByHfUserId(String hfUserId);
	
	/**
	 * 查询用户是否绑卡
	 * @param userId 合作方用户id
	 * @return
	 */
	LnBindCard selectLoanBindCardExist(Integer userId);
	
}
