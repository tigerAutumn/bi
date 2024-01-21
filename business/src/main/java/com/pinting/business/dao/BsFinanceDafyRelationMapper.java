package com.pinting.business.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsFinanceDafyRelation;
import com.pinting.business.model.BsFinanceDafyRelationExample;
import com.pinting.business.model.vo.DafyBusinessManagerVO;
import com.pinting.business.model.vo.FinancialIntentionVO;
import com.pinting.business.model.vo.FinancialRedeemVO;
import com.pinting.business.model.vo.FinancialSettlementVO;

public interface BsFinanceDafyRelationMapper {
    int countByExample(BsFinanceDafyRelationExample example);

    int deleteByExample(BsFinanceDafyRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsFinanceDafyRelation record);

    int insertSelective(BsFinanceDafyRelation record);

    List<BsFinanceDafyRelation> selectByExample(BsFinanceDafyRelationExample example);

    BsFinanceDafyRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsFinanceDafyRelation record, @Param("example") BsFinanceDafyRelationExample example);

    int updateByExample(@Param("record") BsFinanceDafyRelation record, @Param("example") BsFinanceDafyRelationExample example);

    int updateByPrimaryKeySelective(BsFinanceDafyRelation record);

    int updateByPrimaryKey(BsFinanceDafyRelation record);
    
    List<FinancialIntentionVO> selectFinancialIntentionGrid(HashMap<String,Object> paramMap);
    
    Integer countFinancialIntention(HashMap<String,Object> paramMap);
    
    
    List<FinancialIntentionVO> selectFinancialIntentionGridZan(HashMap<String,Object> paramMap);
    
    Integer countFinancialIntentionZan(HashMap<String,Object> paramMap);

    /**
     * 达飞财务-理财结算-普通产品
     * @param paramMap
     * @return
     */
	Integer countFinancialSettlement(HashMap<String, Object> paramMap);
	
	/**
     * 达飞财务-理财结算-分期产品
     * @param paramMap
     * @return
     */
	Integer countFinancialSettlementZan(HashMap<String, Object> paramMap);

	/**
	 * 达飞财务-理财结算-普通产品
	 * @param paramMap
	 * @return
	 */
	List<FinancialSettlementVO> queryFinancialSettlementGrid(
			HashMap<String, Object> paramMap);
	/**
	 * 达飞财务-理财结算-分期产品
	 * @param paramMap
	 * @return
	 */
	List<FinancialSettlementVO> queryFinancialSettlementGridZan(
			HashMap<String, Object> paramMap);
    
    List<DafyBusinessManagerVO> selectDafyBusinessManager(HashMap<String,Object> paramMap);
    
    Integer countDafyBusinessManager(HashMap<String,Object> paramMap);
    
    List<DafyBusinessManagerVO> selectDafyBusinessManagerZan(HashMap<String,Object> paramMap);
    
    Integer countDafyBusinessManagerZan(HashMap<String,Object> paramMap);

    /**
     * 达飞财务-赎回结算-普通产品
     * @param paramMap
     * @return
     */
	List<FinancialRedeemVO> queryFinancialRedeemGrid(
			HashMap<String, Object> paramMap);
	 /**
     * 达飞财务-赎回结算-分期产品
     * @param paramMap
     * @return
     */
	List<FinancialRedeemVO> queryFinancialRedeemGridZan(
			HashMap<String, Object> paramMap);

	/**
	 *  达飞财务-赎回结算-普通产品
	 * @param paramMap
	 * @return
	 */
	Integer countFinancialRedeem(HashMap<String, Object> paramMap);
	/**
	 *  达飞财务-赎回结算-分期产品
	 * @param paramMap
	 * @return
	 */
	Integer countFinancialRedeemZan(HashMap<String, Object> paramMap);
}