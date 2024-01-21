package com.pinting.business.dao;

import com.pinting.business.model.LnBadDetail;
import com.pinting.business.model.LnBadDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LnBadDetailMapper {
    long countByExample(LnBadDetailExample example);

    int deleteByExample(LnBadDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnBadDetail record);

    int insertSelective(LnBadDetail record);

    List<LnBadDetail> selectByExample(LnBadDetailExample example);

    LnBadDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnBadDetail record, @Param("example") LnBadDetailExample example);

    int updateByExample(@Param("record") LnBadDetail record, @Param("example") LnBadDetailExample example);

    int updateByPrimaryKeySelective(LnBadDetail record);

    int updateByPrimaryKey(LnBadDetail record);
    
    /**
     * 坏账户余额（赞分期） 
     * @return
     */
    Double selectBadloansZanAmount();
    
}