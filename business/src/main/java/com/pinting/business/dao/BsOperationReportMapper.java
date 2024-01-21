package com.pinting.business.dao;

import com.pinting.business.model.BsOperationReport;
import com.pinting.business.model.BsOperationReportExample;
import com.pinting.business.model.vo.BsOperationReportVO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BsOperationReportMapper {
    int countByExample(BsOperationReportExample example);

    int deleteByExample(BsOperationReportExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsOperationReport record);

    int insertSelective(BsOperationReport record);

    List<BsOperationReport> selectByExample(BsOperationReportExample example);

    BsOperationReport selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsOperationReport record, @Param("example") BsOperationReportExample example);

    int updateByExample(@Param("record") BsOperationReport record, @Param("example") BsOperationReportExample example);

    int updateByPrimaryKeySelective(BsOperationReport record);

    int updateByPrimaryKey(BsOperationReport record);

	List<BsOperationReportVO> queryOperationReportList(@Param("start") Integer start,
			@Param("numPerPage")Integer numPerPage);

	Integer queryOperationReportCount();
	
    List<BsOperationReportVO> selectByYear(@Param("year") String year, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

}