package com.pinting.business.dao;

import com.pinting.business.model.BsUserProductLimit;
import com.pinting.business.model.BsUserProductLimitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsUserProductLimitMapper {
    int countByExample(BsUserProductLimitExample example);

    int deleteByExample(BsUserProductLimitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserProductLimit record);

    int insertSelective(BsUserProductLimit record);

    List<BsUserProductLimit> selectByExample(BsUserProductLimitExample example);

    BsUserProductLimit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUserProductLimit record, @Param("example") BsUserProductLimitExample example);

    int updateByExample(@Param("record") BsUserProductLimit record, @Param("example") BsUserProductLimitExample example);

    int updateByPrimaryKeySelective(BsUserProductLimit record);

    int updateByPrimaryKey(BsUserProductLimit record);
    
    List<BsUserProductLimit> selectUserLimitForLock(@Param("userId")Integer userId,@Param("productId")Integer productId);
}