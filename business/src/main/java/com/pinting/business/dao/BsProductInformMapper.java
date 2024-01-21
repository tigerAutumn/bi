package com.pinting.business.dao;

import com.pinting.business.model.BsProductInform;
import com.pinting.business.model.BsProductInformExample;
import com.pinting.business.model.vo.ProductInformVO;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsProductInformMapper {
    int countByExample(BsProductInformExample example);

    int deleteByExample(BsProductInformExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsProductInform record);

    int insertSelective(BsProductInform record);

    List<BsProductInform> selectByExample(BsProductInformExample example);

    BsProductInform selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsProductInform record, @Param("example") BsProductInformExample example);

    int updateByExample(@Param("record") BsProductInform record, @Param("example") BsProductInformExample example);

    int updateByPrimaryKeySelective(BsProductInform record);

    int updateByPrimaryKey(BsProductInform record);
    
    /**
     * 根据时间和提醒类型查询需要提醒的列表
     * @param startTime
     * @param endTime
     * @param remindType
     * @return
     */
    List<ProductInformVO> getListByTime(@Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("remindType")String remindType);
}