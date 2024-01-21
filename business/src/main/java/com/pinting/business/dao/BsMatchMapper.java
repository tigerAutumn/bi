package com.pinting.business.dao;

import com.pinting.business.model.BsMatch;
import com.pinting.business.model.BsMatchExample;
import com.pinting.business.model.vo.BsMatchVO;
import com.pinting.business.model.vo.BsMatchWarnVO;
import com.pinting.business.model.vo.DailyAmount4LoanVO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BsMatchMapper {
    int countByExample(BsMatchExample example);

    int deleteByExample(BsMatchExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsMatch record);

    int insertSelective(BsMatch record);

    List<BsMatch> selectByExample(BsMatchExample example);

    BsMatch selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsMatch record, @Param("example") BsMatchExample example);

    int updateByExample(@Param("record") BsMatch record, @Param("example") BsMatchExample example);

    int updateByPrimaryKeySelective(BsMatch record);

    int updateByPrimaryKey(BsMatch record);
    
    /**
     * 根据sub_account_id查询bs_match表中，关联bs_loan_relative_record表中，match的金额总和
     * @param subAccountId
     * @return
     */
    Double sumAccountRepay(@Param("subAccountId")Integer subAccountId,
    		@Param("channel") String channel,@Param("isRepay") String isRepay);
    
    /**
     * 根据userId和产品id查询债权总条数
     * @param userId
     * @param productId
     * @param subAccountId
     * @return
     */
    int countMatchListByUserIdProductId(Map<String, Object> data);
    
    /**
     * 根据userId和产品id查询债权总条数,包含迁移后数据
     * @param userId
     * @param productId
     * @param subAccountId
     * @return
     */
    int countMatchListIncludePostMigration(Map<String, Object> data);
    
    /**
     * 根据userId和产品id查询债权明细
     * l.time ---> createTime
     * @param userId
     * @param productId
     * @param subAccountId
     * @return
     */
    List<BsMatchVO> getMatchListByUserIdProductId(Map<String, Object> data);
    
    /**
     * 根据userId和产品id查询债权明细，包括迁移后数据
     * l.time ---> createTime
     * @param userId
     * @param productId
     * @param subAccountId
     * @return
     */
    List<BsMatchVO> getMatchListIncludePostMigration(Map<String, Object> data);
    
    /**
     * 根据subAccountId查询债权明细
     * @param subAccountId
     * @return
     */
    List<BsMatchVO> getMatchListBySubAccountId(@Param("subAccountId")Integer subAccountId);
    
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
    List<BsMatchWarnVO> getAvgAmountWarn(@Param("limitAmount") Double limitAmount);

    /**
     * 查询理财到期，需要结束匹配的数据列表
     * @return
     */
    List<BsMatch> selectOverMatches(@Param("propertySymbol")String propertySymbol);
    
    /**
     * 查询某日匹配成功金额，按投资期限统计
     * @param matchDate
     * @param propertySymbol
     * @return
     */
    List<DailyAmount4LoanVO> getMacthedSumAmount(@Param("matchDate")String matchDate,@Param("propertySymbol")String propertySymbol);
}