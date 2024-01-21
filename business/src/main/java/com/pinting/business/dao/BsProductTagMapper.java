package com.pinting.business.dao;

import com.pinting.business.model.BsProductTag;
import com.pinting.business.model.BsProductTagExample;
import java.util.List;

import com.pinting.business.model.vo.BsProductTagContentVO;
import org.apache.ibatis.annotations.Param;

public interface BsProductTagMapper {
    int countByExample(BsProductTagExample example);

    int deleteByExample(BsProductTagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsProductTag record);

    int insertSelective(BsProductTag record);

    List<BsProductTag> selectByExample(BsProductTagExample example);

    BsProductTag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsProductTag record, @Param("example") BsProductTagExample example);

    int updateByExample(@Param("record") BsProductTag record, @Param("example") BsProductTagExample example);

    int updateByPrimaryKeySelective(BsProductTag record);

    int updateByPrimaryKey(BsProductTag record);

    /**
     * 根据产品id查询提醒、加息标签的内容
     * @param productId
     * @return
     */
    BsProductTagContentVO selectProductTagContentById(@Param("productId") Integer productId);

}