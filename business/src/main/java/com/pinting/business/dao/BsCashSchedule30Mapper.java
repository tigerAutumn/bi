package com.pinting.business.dao;

import com.pinting.business.model.BsCashSchedule30;
import com.pinting.business.model.BsCashSchedule30Example;
import com.pinting.business.model.vo.BsCashSchedule30VO;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsCashSchedule30Mapper {
    int countByExample(BsCashSchedule30Example example);

    int deleteByExample(BsCashSchedule30Example example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsCashSchedule30 record);

    int insertSelective(BsCashSchedule30 record);

    List<BsCashSchedule30> selectByExample(BsCashSchedule30Example example);

    BsCashSchedule30 selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsCashSchedule30 record, @Param("example") BsCashSchedule30Example example);

    int updateByExample(@Param("record") BsCashSchedule30 record, @Param("example") BsCashSchedule30Example example);

    int updateByPrimaryKeySelective(BsCashSchedule30 record);

    int updateByPrimaryKey(BsCashSchedule30 record);
	ArrayList<BsCashSchedule30> selectCashSchedule30ListPageInfo(
			BsCashSchedule30 bsCashSchedule30);
	
	int countCashSchedule30List(BsCashSchedule30 bsCashSchedule30);

	ArrayList<BsCashSchedule30> selectBsCashScheduleList(
			BsCashSchedule30VO cashSchedule30VO);

	void insertCashScheduleList(ArrayList<BsCashSchedule30> valueList);
	
	/**
	 * 未来30天应付本金合计
	 * @return
	 */
	Double totalCashBaseAmount(BsCashSchedule30 bsCashSchedule30);
	
	/**
	 * 未来30天应付利息合计
	 * @return
	 */
    Double totalBashInterestAmount(BsCashSchedule30 bsCashSchedule30);
    
}