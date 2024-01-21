package com.pinting.business.dao;

import com.pinting.business.model.BsSysReceiveMoney;
import com.pinting.business.model.BsSysReceiveMoneyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsSysReceiveMoneyMapper {
    int countByExample(BsSysReceiveMoneyExample example);

    int deleteByExample(BsSysReceiveMoneyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsSysReceiveMoney record);

    int insertSelective(BsSysReceiveMoney record);

    List<BsSysReceiveMoney> selectByExample(BsSysReceiveMoneyExample example);

    BsSysReceiveMoney selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsSysReceiveMoney record, @Param("example") BsSysReceiveMoneyExample example);

    int updateByExample(@Param("record") BsSysReceiveMoney record, @Param("example") BsSysReceiveMoneyExample example);

    int updateByPrimaryKeySelective(BsSysReceiveMoney record);

    int updateByPrimaryKey(BsSysReceiveMoney record);
        
    Double sumCheckAccountOrders();
 
    
	/**
	 * 对账结果统计-老产品回款金融
	 * @return
	 */
	Double sumCheckAccountOrders(@Param("status") int status);

	/**
	 * 对账结果统计-老产品回款计数
	 * @return
	 */
	int countCheckAccountOrders(@Param("status") int status);
	
}