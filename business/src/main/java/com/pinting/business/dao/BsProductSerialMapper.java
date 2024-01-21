package com.pinting.business.dao;

import com.pinting.business.model.BsProductSerial;
import com.pinting.business.model.BsProductSerialExample;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsProductSerialMapper {
    int countByExample(BsProductSerialExample example);

    int deleteByExample(BsProductSerialExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsProductSerial record);

    int insertSelective(BsProductSerial record);

    List<BsProductSerial> selectByExample(BsProductSerialExample example);

    BsProductSerial selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsProductSerial record, @Param("example") BsProductSerialExample example);

    int updateByExample(@Param("record") BsProductSerial record, @Param("example") BsProductSerialExample example);

    int updateByPrimaryKeySelective(BsProductSerial record);

    int updateByPrimaryKey(BsProductSerial record);
    
    /** 产品系列表 列表 */
    ArrayList<BsProductSerial> selectProductSerialInfo(BsProductSerial record);
        
    /** 产品系列表 统计 */
    int selectCountProductSerial();
        
    /** 查询产品系列表 名称是否已存在 */
    BsProductSerial selectBsProductSerial(BsProductSerial record);
     
    /** 查询该系列产品名称 是否已被产品表引用 */
    int selectCountOfProductName(@Param("serialId") Integer serialId);
    
    
}