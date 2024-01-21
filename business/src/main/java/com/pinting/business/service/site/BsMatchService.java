package com.pinting.business.service.site;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsMatch;
import com.pinting.business.model.BsMatchRepayDetail;
import com.pinting.business.model.vo.BsMatchVO;
import com.pinting.business.model.vo.BsMatchWarnVO;

public interface BsMatchService {

	/**
	 * 查询bs_match表中，关联bs_loan_relative_record表中，未还款的is_repay = 'N'的match的金额总和
	 * 
	 * @param subAccountId
	 * @return
	 */
	public Double sumAccountRepay(Integer subAccountId, String Channel,
			String isRepay);

	/**
	 * 新增
	 * 
	 * @param bsMatch
	 */
	public void addBsMatch(BsMatch bsMatch);

	/**
	 * 根据userId和产品id查询债权明细
	 * 
	 * @param userId
	 * @param productId
	 * @param subAccountId
	 * @return
	 */
	List<BsMatchVO> getMatchListByUserIdProductId(Integer userId,
			Integer productId, Integer subAccountId, Integer start, Integer pageSize);
	
	/**
	 * 根据userId和产品id查询债权明细，包括迁移后数据
	 * 
	 * @param userId
	 * @param productId
	 * @param subAccountId
	 * @return
	 */
	List<BsMatchVO> getMatchListIncludePostMigration(Integer userId,
			Integer productId, Integer subAccountId, Integer start, Integer pageSize);
	
	/**
	 * 根据userId和产品id查询债权总条数
	 * @param userId
	 * @param productId
	 * @param subAccountId
	 * @return
	 */
	int countMatchListByUserIdProductId(Integer userId, Integer productId,Integer subAccountId);
	
	/**
	 * 根据userId和产品id查询债权总条数,包括迁移后数据
	 * @param userId
	 * @param productId
	 * @param subAccountId
	 * @return
	 */
	int countMatchListIncludePostMigration(Integer userId, Integer productId,Integer subAccountId);

	/**
	 * 根据matchId查询债权关系还款明细
	 * @param matchId
	 * @return
	 */
	List<BsMatchRepayDetail> getListByMatchId(Integer matchId);
	
	/**
	 * 根据债权关系编号查询未还完匹配列表，按剩余本金升序排序
	 * @param loanRelativeId
	 * @return
	 */
	List<BsMatch> findMatchesByLoanRelativeId(Integer loanRelativeId);
	
	/**
	 * 还款处理
	 * 修改剩余本金、还款金额和状态，并记录还款明细
	 * @param bsMatch 还款前原匹配数据
	 * @param repayAmount 还款金额
	 */
	void modifyMatch4Repay(BsMatch bsMatch, Double repayAmount);
	
	 /**
     * 查询某日匹配总金额和某日购买总金额不同的数据
     * @return
     */
    List<BsMatchWarnVO> getMatchDiffBatchBuyAmount();
    
    /**
     * 查询某笔投资匹配金额平均值超过limitAmount的数据
     * @param limitAmount
     * @return
     */
    List<BsMatchWarnVO> getAvgAmountWarn(Double limitAmount);
    
    /**
     * 查询理财到期，需要结束匹配的数据列表
     * @param propertySymbol 资金源类型
     * @return
     */
    List<BsMatch> findOverMatches(String propertySymbol);
	
}
