package com.pinting.business.dao;

import com.pinting.business.model.UcUserExt;
import com.pinting.business.model.UcUserExtExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcUserExtMapper {
    long countByExample(UcUserExtExample example);

    int deleteByExample(UcUserExtExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UcUserExt record);

    int insertSelective(UcUserExt record);

    List<UcUserExt> selectByExample(UcUserExtExample example);

    UcUserExt selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UcUserExt record, @Param("example") UcUserExtExample example);

    int updateByExample(@Param("record") UcUserExt record, @Param("example") UcUserExtExample example);

    int updateByPrimaryKeySelective(UcUserExt record);

    int updateByPrimaryKey(UcUserExt record);
    
    List<UcUserExt> selectByBsUserAndStatus(@Param("userId")Integer userId, @Param("userType")String userType, @Param("status")String status);
    
}