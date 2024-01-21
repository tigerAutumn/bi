package com.pinting.business.dao;

import com.pinting.business.model.BsUserPoliceVerify;
import com.pinting.business.model.BsUserPoliceVerifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsUserPoliceVerifyMapper {
    int countByExample(BsUserPoliceVerifyExample example);

    int deleteByExample(BsUserPoliceVerifyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserPoliceVerify record);

    int insertSelective(BsUserPoliceVerify record);

    List<BsUserPoliceVerify> selectByExample(BsUserPoliceVerifyExample example);

    BsUserPoliceVerify selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUserPoliceVerify record, @Param("example") BsUserPoliceVerifyExample example);

    int updateByExample(@Param("record") BsUserPoliceVerify record, @Param("example") BsUserPoliceVerifyExample example);

    int updateByPrimaryKeySelective(BsUserPoliceVerify record);

    int updateByPrimaryKey(BsUserPoliceVerify record);
}