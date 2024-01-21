package com.pinting.business.dao;

import com.pinting.business.model.BsAssetsList;
import com.pinting.business.model.BsAssetsListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsAssetsListMapper {
    int countByExample(BsAssetsListExample example);

    int deleteByExample(BsAssetsListExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsAssetsList record);

    int insertSelective(BsAssetsList record);

    List<BsAssetsList> selectByExample(BsAssetsListExample example);
    List<BsAssetsList> findAssetsListByPage(BsAssetsList model);

    BsAssetsList selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsAssetsList record, @Param("example") BsAssetsListExample example);

    int updateByExample(@Param("record") BsAssetsList record, @Param("example") BsAssetsListExample example);

    int updateByPrimaryKeySelective(BsAssetsList record);

    int updateByPrimaryKey(BsAssetsList record);
}