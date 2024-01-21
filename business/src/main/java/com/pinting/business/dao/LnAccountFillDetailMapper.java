package com.pinting.business.dao;

import com.pinting.business.model.LnAccountFillDetail;
import com.pinting.business.model.LnAccountFillDetailExample;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.vo.LnAccountFillDetailVO;
import org.apache.ibatis.annotations.Param;

public interface LnAccountFillDetailMapper {
    int countByExample(LnAccountFillDetailExample example);

    int deleteByExample(LnAccountFillDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnAccountFillDetail record);

    int insertSelective(LnAccountFillDetail record);

    List<LnAccountFillDetail> selectByExample(LnAccountFillDetailExample example);

    LnAccountFillDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnAccountFillDetail record, @Param("example") LnAccountFillDetailExample example);

    int updateByExample(@Param("record") LnAccountFillDetail record, @Param("example") LnAccountFillDetailExample example);

    int updateByPrimaryKeySelective(LnAccountFillDetail record);

    int updateByPrimaryKey(LnAccountFillDetail record);

    /**
     * 根据条件计算总金额
     * @param fillDate
     * @param fillType
     * @param status 补账状态
     * @param partnerCode 合作方
     * @return
     */
    Double sumAmount(@Param("fillDate") Date fillDate, @Param("fillType") String fillType,
                     @Param("status") String status, @Param("partnerCode") String partnerCode);


    /**
     * 根据条件获取列表
     * @param fillDate
     * @param fillType
     * @param status 补账状态
     * @param partnerCode 合作方
     * @return
     */
    List<LnAccountFillDetailVO> getList(@Param("fillDate") Date fillDate, @Param("fillType") String fillType,
                                        @Param("status") String status, @Param("partnerCode") String partnerCode);
}