package com.pinting.business.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsPropertyInfo;
import com.pinting.business.model.BsPropertyInfoExample;

public interface BsPropertyInfoMapper {
    int countByExample(BsPropertyInfoExample example);

    int deleteByExample(BsPropertyInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsPropertyInfo record);

    int insertSelective(BsPropertyInfo record);

    List<BsPropertyInfo> selectByExample(BsPropertyInfoExample example);

    BsPropertyInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsPropertyInfo record, @Param("example") BsPropertyInfoExample example);

    int updateByExample(@Param("record") BsPropertyInfo record, @Param("example") BsPropertyInfoExample example);

    int updateByPrimaryKeySelective(BsPropertyInfo record);

    int updateByPrimaryKey(BsPropertyInfo record);
    
    /** 资产合作方信息 列表 */
    ArrayList<BsPropertyInfo> selectPropertyInfoList(BsPropertyInfo record);
        
    /** 查询资产合作 名称是否已存在 */
    BsPropertyInfo selectPropertyInfoName(BsPropertyInfo record);
     
    /** 查询该资产合作名称 是否已被产品表引用 */
    int selectCountByProductId(@Param("id") Integer id);
    
    /**
     * 根据子账户ID查询购买产品的资金接收方
     * @param subAccountId
     * @return
     */
    String checkPropertySymbol(@Param("subAccountId")Integer subAccountId);
    
}