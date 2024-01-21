package com.pinting.mall.dao;

import com.pinting.mall.model.MallCommodityInfo;
import com.pinting.mall.model.MallCommodityInfoExample;
import com.pinting.mall.model.MallCommodityInfoWithBLOBs;
import com.pinting.mall.model.manange.MallCommodityInfoVO;
import com.pinting.mall.model.vo.MallCommodityInfoAndTypeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MallCommodityInfoMapper {
    int countByExample(MallCommodityInfoExample example);

    int deleteByExample(MallCommodityInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MallCommodityInfoWithBLOBs record);

    int insertSelective(MallCommodityInfoWithBLOBs record);

    List<MallCommodityInfoWithBLOBs> selectByExampleWithBLOBs(MallCommodityInfoExample example);

    List<MallCommodityInfo> selectByExample(MallCommodityInfoExample example);

    MallCommodityInfoWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallCommodityInfoWithBLOBs record, @Param("example") MallCommodityInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") MallCommodityInfoWithBLOBs record, @Param("example") MallCommodityInfoExample example);

    int updateByExample(@Param("record") MallCommodityInfo record, @Param("example") MallCommodityInfoExample example);

    int updateByPrimaryKeySelective(MallCommodityInfoWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(MallCommodityInfoWithBLOBs record);

    int updateByPrimaryKey(MallCommodityInfo record);

    /**
     * 根据商品类别id查询商品列表
     * 已上架商品：上架时间倒序【推荐有库存>未推荐有库存>已售罄】
     *
     * @param typeId
     * @return
     * @author bianyatian
     */
    List<MallCommodityInfo> selectCommInfoByTypeId(@Param("typeId") Integer typeId);

    /**
     * 查询商品信息列表
     *
     * @param record
     * @return
     */
    List<MallCommodityInfoVO> selectMallCommodityInfoList(MallCommodityInfoVO record);

    /**
     * 根据商品ID查询（加锁）
     *
     * @param id
     * @return
     */
    MallCommodityInfo selectMallCommodityByIdForLock(Integer id);

    /**
     * 根据商品ID查询商品基础信息与类型信息
     *
     * @param id
     * @return
     */
    MallCommodityInfoAndTypeVO selectMallCommodityInfoAndTypeById(Integer id);
}