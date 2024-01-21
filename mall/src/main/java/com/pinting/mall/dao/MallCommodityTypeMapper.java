package com.pinting.mall.dao;

import com.pinting.mall.model.MallCommodityType;
import com.pinting.mall.model.MallCommodityTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MallCommodityTypeMapper {
    int countByExample(MallCommodityTypeExample example);

    int deleteByExample(MallCommodityTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MallCommodityType record);

    int insertSelective(MallCommodityType record);

    List<MallCommodityType> selectByExample(MallCommodityTypeExample example);

    MallCommodityType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallCommodityType record, @Param("example") MallCommodityTypeExample example);

    int updateByExample(@Param("record") MallCommodityType record, @Param("example") MallCommodityTypeExample example);

    int updateByPrimaryKeySelective(MallCommodityType record);

    int updateByPrimaryKey(MallCommodityType record);

    /**
     * 查询商品类别名称是否已存在
     * @param record
     * @return
     */
    MallCommodityType selectCommTypeName(MallCommodityType record);

    /**
     * 分页查询商品类别列表
     * @return
     */
    List<MallCommodityType> selectMallCommodityTypelList();

    /**
     * 查询首页商品类别列表（修改时间倒序【推荐>未推荐】）
     * @author bianyatian
     * @return
     */
    List<MallCommodityType> selectCommTypeList4Index();
    
}