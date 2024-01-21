package com.pinting.mall.dao;

import com.pinting.mall.model.MallBsUser;
import com.pinting.mall.model.MallBsUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MallBsUserMapper {
    long countByExample(MallBsUserExample example);

    int deleteByExample(MallBsUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MallBsUser record);

    int insertSelective(MallBsUser record);

    List<MallBsUser> selectByExample(MallBsUserExample example);

    MallBsUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallBsUser record, @Param("example") MallBsUserExample example);

    int updateByExample(@Param("record") MallBsUser record, @Param("example") MallBsUserExample example);

    int updateByPrimaryKeySelective(MallBsUser record);

    int updateByPrimaryKey(MallBsUser record);
    
    /**
     * 查询当日生日，且未发放生日积分，且为非代偿人和VIP的用户数据
     * @author bianyatian
     * @param birthday
     * @return
     */
    List<MallBsUser> getBirthdayUserList(@Param("birthday")String birthday,@Param("vipCompUserList")List<String> vipCompUserList);
}