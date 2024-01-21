package com.pinting.mall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinting.mall.model.MallBsWxUserInfo;
import com.pinting.mall.model.MallBsWxUserInfoExample;

public interface MallBsWxUserInfoMapper {

	int countByExample(MallBsWxUserInfoExample example);

    int deleteByExample(MallBsWxUserInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MallBsWxUserInfo record);

    int insertSelective(MallBsWxUserInfo record);

    List<MallBsWxUserInfo> selectByExample(MallBsWxUserInfoExample example);

    MallBsWxUserInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallBsWxUserInfo record, @Param("example") MallBsWxUserInfoExample example);

    int updateByExample(@Param("record") MallBsWxUserInfo record, @Param("example") MallBsWxUserInfoExample example);

    int updateByPrimaryKeySelective(MallBsWxUserInfo record);

    int updateByPrimaryKey(MallBsWxUserInfo record);
}