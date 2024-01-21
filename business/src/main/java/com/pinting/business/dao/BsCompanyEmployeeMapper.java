package com.pinting.business.dao;

import com.pinting.business.model.BsCompanyEmployee;
import com.pinting.business.model.BsCompanyEmployeeExample;
import com.pinting.business.model.vo.BsCompanyEmployeeVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsCompanyEmployeeMapper {
    int countByExample(BsCompanyEmployeeExample example);

    int deleteByExample(BsCompanyEmployeeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsCompanyEmployee record);

    int insertSelective(BsCompanyEmployee record);

    List<BsCompanyEmployee> selectByExample(BsCompanyEmployeeExample example);

    BsCompanyEmployee selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsCompanyEmployee record, @Param("example") BsCompanyEmployeeExample example);

    int updateByExample(@Param("record") BsCompanyEmployee record, @Param("example") BsCompanyEmployeeExample example);

    int updateByPrimaryKeySelective(BsCompanyEmployee record);

    int updateByPrimaryKey(BsCompanyEmployee record);

	List<BsCompanyEmployeeVO> queryCompanyEmployeeIndex(@Param("id") Integer id,
			@Param("employeeCode")String employeeCode, @Param("employeeName")String employeeName, @Param("deptId")Integer deptId,
			@Param("deptName")String deptName,@Param("startTime")String startTime,@Param("endTime")String endTime, @Param("start")Integer start, @Param("numPerPage")Integer numPerPage);

	Integer queryCompanyEmployeeCount(@Param("id") Integer id,
			@Param("employeeCode")String employeeCode, @Param("employeeName")String employeeName, @Param("deptId")Integer deptId,
			@Param("deptName")String deptName,@Param("startTime")String startTime,@Param("endTime")String endTime);
}