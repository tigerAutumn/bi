package com.pinting.business.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsProfitLoss;
import com.pinting.business.model.BsProfitLossExample;

public interface BsProfitLossMapper {
    int countByExample(BsProfitLossExample example);

    int deleteByExample(BsProfitLossExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsProfitLoss record);

    int insertSelective(BsProfitLoss record);

    List<BsProfitLoss> selectByExample(BsProfitLossExample example);

    BsProfitLoss selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsProfitLoss record, @Param("example") BsProfitLossExample example);

    int updateByExample(@Param("record") BsProfitLoss record, @Param("example") BsProfitLossExample example);

    int updateByPrimaryKeySelective(BsProfitLoss record);

    int updateByPrimaryKey(BsProfitLoss record);
	ArrayList<BsProfitLoss> selectProfitLossListPageInfo(
			BsProfitLoss profitLoss);

	BsProfitLoss selectProfitLossByClearDateMonth();

	Double sumProfite();

}