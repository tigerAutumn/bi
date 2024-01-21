package com.pinting.business.dao;

import com.pinting.business.model.BsWxUserInfo;
import com.pinting.business.model.BsWxUserInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsWxUserInfoMapper {
    int countByExample(BsWxUserInfoExample example);

    int deleteByExample(BsWxUserInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsWxUserInfo record);

    int insertSelective(BsWxUserInfo record);

    List<BsWxUserInfo> selectByExample(BsWxUserInfoExample example);

    BsWxUserInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsWxUserInfo record, @Param("example") BsWxUserInfoExample example);

    int updateByExample(@Param("record") BsWxUserInfo record, @Param("example") BsWxUserInfoExample example);

    int updateByPrimaryKeySelective(BsWxUserInfo record);

    int updateByPrimaryKey(BsWxUserInfo record);
}