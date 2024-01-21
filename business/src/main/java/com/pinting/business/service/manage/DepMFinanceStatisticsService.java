package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.vo.CorpusBuyStatisticsVO;
import com.pinting.business.model.vo.HfbankCustomerWithdrawalVO;
import com.pinting.business.model.vo.LnLoanServiceFeeVO;
import com.pinting.business.model.vo.PayFinanceStatisticsVO;
import com.pinting.business.model.vo.UserRechanageStatisticsVO;

public interface DepMFinanceStatisticsService {
/**
	 * 恒丰客户充值记录
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<UserRechanageStatisticsVO> userRechangeStatistics(String userName, String mobile, String startTime,
			String endTime, Integer page, Integer offset);
	
	/**
	 * 恒丰客户充值记录总数
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer userRechangeStatisticsCount(String userName, String mobile, String startTime,
			String endTime);
	/**
	 * 恒丰客户充值总金额
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Double userTotalRechangeAmountStatistics(String userName, String mobile, String startTime,
			String endTime);
	
	/**
	 * 恒丰-云贷本金购买统计-列表
	 * @param userType VIP/NORMAL
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @param partnerCode
	 * @param page
	 * @param offset
	 * @return
	 */
	public List<CorpusBuyStatisticsVO> yunZSDCorpusBuyStatistics(String userType,String userName,
			String mobile, String startTime, String endTime, String partnerCode, Integer page, Integer offset);
	
	/**
	 * 恒丰-云贷本金购买统计-列表条数
	 * @param userType VIP/NORMAL
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @param partnerCode
	 * @return
	 */
	public Integer yunZSDCorpusBuyStatisticsCount(String userType,String userName,
			String mobile, String startTime, String endTime, String partnerCode);
	
	/**
	 * 恒丰-云贷本金购买统计-购买总本金
	 * @param userType VIP/NORMAL
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @param partnerCode
	 * @return
	 */
	public Double yunZSDCorpusBuyStatisticsSumBalance(String userType,String userName,
			String mobile, String startTime, String endTime, String partnerCode);

	/**
	 * 恒丰客户支取统计列表
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param offset
	 * @return
	 */
	public List<HfbankCustomerWithdrawalVO> queryHfbankWithdrawalList(String userName, String startTime,
																	  String endTime, Integer page, Integer offset);

	/**
	 * 恒丰客户支取记录统计
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer queryHfbankWithdrawalCount( String userName, String startTime, String endTime);

	/**
	 * 恒丰客户支取总金额合计
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Double queryHfbankWithdrawalSumAmount(String userName, String startTime, String endTime);

	/**
	 * 恒丰客户支取手续费合计
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Double queryHfbankWithdrawalSumFee(String userName, String startTime, String endTime);

	/**
	 * 宝付客户支取统计列表
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param offset
	 * @return
	 */
	public List<HfbankCustomerWithdrawalVO> queryBaofooWithdrawalList(String userName, String startTime,
																	  String endTime,String transType, Integer page, Integer offset);

	/**
	 * 宝付客户支取记录统计
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer queryBaofooWithdrawalCount( String userName, String startTime, String endTime,String transType);

	/**
	 * 宝付客户支取总金额合计
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Double queryBaofooWithdrawalSumAmount(String userName, String startTime, String endTime,String transType);

	/**
	 * 宝付客户支取手续费合计
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Double queryBaofooWithdrawalSumFee(String userName, String startTime, String endTime,String transType);

/**
	 * 支付融资客户数据（云贷，赞时贷）
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param offset
	 * @return
	 */
	public List<PayFinanceStatisticsVO> payFinanceYunZSDStatistics(String userType, String userName,
																String mobile, String startTime, String endTime,
																String partnerCode, String partnerBusinessFlag,
																Integer page, Integer offset);


	/**
	 * 支付融资客户数据总条数（云贷，赞时贷）
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer payFinanceYunZSDStatisticsCount(String userType, String userName, String mobile,
												String startTime, String endTime, String partnerCode, String partnerBusinessFlag);


	/**
	 * 支付融资客户出借金额总计（云贷，赞时贷）
	 * @param userType
	 * @param userName
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @param partnerCode
	 * @return
	 */
	public Double payFinanceYunZSDTotalAmount(String userType, String userName, String mobile,
											  String startTime, String endTime, String partnerCode, String partnerBusinessFlag);
	
	/**
     * 存管产品统计 - 借款服务费计数
     * @param userName
     * @param mobile
     * @param partnerCode
     * @param startTime
     * @param endTime
	 * @param partnerBusinessFlag 借款产品标识
     * @return
     */
    Integer countLoanServiceFee(String userName, String mobile, String partnerCode, String startTime, String endTime, String partnerBusinessFlag);

    /**
     * 存管产品统计 - 借款服务费列表
     * @param userName
     * @param mobile
     * @param partnerCode
     * @param startTime
     * @param endTime
	 * @param partnerBusinessFlag 借款产品标识
     * @return
     */
    List<LnLoanServiceFeeVO> selectLoanServiceFeeList(String userName, String mobile, String partnerCode, String startTime,
    		String endTime, String partnerBusinessFlag, Integer page, Integer offset);
    
    /**
     * 存管产品统计 - 借款服务费求和
     * @param userName
     * @param mobile
     * @param partnerCode
     * @param startTime
     * @param endTime
	 * @param partnerBusinessFlag 借款产品标识
     * @return
     */
    LnLoanServiceFeeVO sumLnLoanServiceFee(String userName, String mobile, String partnerCode, String startTime,
										   String endTime, String partnerBusinessFlag);
    
}
