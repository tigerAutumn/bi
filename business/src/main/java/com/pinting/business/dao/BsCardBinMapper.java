package com.pinting.business.dao;

import com.pinting.business.model.BsCardBin;
import com.pinting.business.model.BsCardBinExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsCardBinMapper {
    int countByExample(BsCardBinExample example);

    int deleteByExample(BsCardBinExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsCardBin record);

    int insertSelective(BsCardBin record);

    List<BsCardBin> selectByExample(BsCardBinExample example);
    
    /**
     * 查询卡bin
     * @param sqlBin
     * @param cardLength
     * @return
     */
    List<BsCardBin> selectCardBin(@Param("sqlBin")String sqlBin,@Param("cardLength")Integer cardLength);

    BsCardBin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsCardBin record, @Param("example") BsCardBinExample example);

    int updateByExample(@Param("record") BsCardBin record, @Param("example") BsCardBinExample example);

    int updateByPrimaryKeySelective(BsCardBin record);

    int updateByPrimaryKey(BsCardBin record);
}