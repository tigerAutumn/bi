package com.pinting.business.dao;

import com.pinting.business.model.BsCustomerDafyDept;
import com.pinting.business.model.BsCustomerDafyDeptExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsCustomerDafyDeptMapper {
    int countByExample(BsCustomerDafyDeptExample example);

    int deleteByExample(BsCustomerDafyDeptExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsCustomerDafyDept record);

    int insertSelective(BsCustomerDafyDept record);

    List<BsCustomerDafyDept> selectByExample(BsCustomerDafyDeptExample example);

    BsCustomerDafyDept selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsCustomerDafyDept record, @Param("example") BsCustomerDafyDeptExample example);

    int updateByExample(@Param("record") BsCustomerDafyDept record, @Param("example") BsCustomerDafyDeptExample example);

    int updateByPrimaryKeySelective(BsCustomerDafyDept record);

    int updateByPrimaryKey(BsCustomerDafyDept record);
}