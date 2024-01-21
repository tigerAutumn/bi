package com.pinting.business.dao;

import com.pinting.business.model.BsCompanyDept;
import com.pinting.business.model.BsCompanyDeptExample;
import com.pinting.business.model.vo.BsCompanyDeptVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsCompanyDeptMapper {
    int countByExample(BsCompanyDeptExample example);

    int deleteByExample(BsCompanyDeptExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsCompanyDept record);

    int insertSelective(BsCompanyDept record);

    List<BsCompanyDept> selectByExample(BsCompanyDeptExample example);

    BsCompanyDept selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsCompanyDept record, @Param("example") BsCompanyDeptExample example);

    int updateByExample(@Param("record") BsCompanyDept record, @Param("example") BsCompanyDeptExample example);

    int updateByPrimaryKeySelective(BsCompanyDept record);

    int updateByPrimaryKey(BsCompanyDept record);

	List<BsCompanyDeptVO> queryFinancialStatisticsIndex(@Param("deptCode")String deptCode, @Param("deptName")String deptName, @Param("parentId")Integer parentId,@Param("parentName")String parentName,@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("start")Integer start,
    		@Param("pageSize")Integer pageSize);

	Integer queryFinancialStatisticsCount(@Param("deptCode")String deptCode, @Param("deptName")String deptName, @Param("parentId")Integer parentId,@Param("parentName")String parentName,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	List<BsCompanyDeptVO> queryFinancialStatisticsDeptEmployeeTree(@Param("parentId")Integer parentId);

}