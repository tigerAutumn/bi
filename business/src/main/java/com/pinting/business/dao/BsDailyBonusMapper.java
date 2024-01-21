package com.pinting.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsDailyBonus;
import com.pinting.business.model.BsDailyBonusExample;
import com.pinting.business.model.vo.BsDailyBonusVO;

public interface BsDailyBonusMapper {
    int countByExample(BsDailyBonusExample example);

    int deleteByExample(BsDailyBonusExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsDailyBonus record);

    int insertSelective(BsDailyBonus record);

    List<BsDailyBonus> selectByExample(BsDailyBonusExample example);

    List<BsDailyBonus> selectByExamplePage(Map<String, Object> data);
    
    BsDailyBonus selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsDailyBonus record, @Param("example") BsDailyBonusExample example);

    int updateByExample(@Param("record") BsDailyBonus record, @Param("example") BsDailyBonusExample example);

    int updateByPrimaryKeySelective(BsDailyBonus record);

    int updateByPrimaryKey(BsDailyBonus record);
    
    BsDailyBonusVO selectSumDailyBonusByUserIdAndTime(Map<String, Object> data);

	Double sumShouldBonus();

	Double sumIncarnateBonus();
	
	/**
	 * 奖励金记录统计
	 * @param record
	 * @return
	 */
	List<BsDailyBonusVO> bsDailyBonusPage(BsDailyBonusVO record);
	
	/**
	 * 奖励金记录统计 
	 * @param record
	 * @return
	 */
	int bsDailyBonusCount(BsDailyBonusVO record);
	
	/**
	 * 奖励金总金额
	 * @param record
	 * @return
	 */
	Double bonusSum(BsDailyBonusVO record);
	
	BsDailyBonusVO subAccountKeySumBonus(BsDailyBonusVO record);
	
	/**
	 * 客服菜单用户奖励金查询-详情查看-根据subAccountId查询
	 * @param record
	 * @return
	 */
	List<BsDailyBonusVO> bsDailyBonus4ServiceDetail(BsDailyBonusVO record);
	
	Double bonusSum4ServiceDetail(BsDailyBonusVO record);
	
	
}