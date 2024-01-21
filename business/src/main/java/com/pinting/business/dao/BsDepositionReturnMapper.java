package com.pinting.business.dao;

import com.pinting.business.model.BsDepositionReturn;
import com.pinting.business.model.BsDepositionReturnExample;
import com.pinting.business.model.vo.InvestExpireVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsDepositionReturnMapper {
    int countByExample(BsDepositionReturnExample example);

    int deleteByExample(BsDepositionReturnExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsDepositionReturn record);

    int insertSelective(BsDepositionReturn record);

    List<BsDepositionReturn> selectByExample(BsDepositionReturnExample example);

    BsDepositionReturn selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsDepositionReturn record, @Param("example") BsDepositionReturnExample example);

    int updateByExample(@Param("record") BsDepositionReturn record, @Param("example") BsDepositionReturnExample example);

    int updateByPrimaryKeySelective(BsDepositionReturn record);

    int updateByPrimaryKey(BsDepositionReturn record);

    /**
     * 投资到期统计
     * @param type
     * @param mobile
     * @param userName
     * @param startTime
     * @param endTime
     * @param position
     * @param offset
     * @return
     */
    List<InvestExpireVO> investExpire(@Param("partnerCode") String partnerCode,
                                      @Param("type") String type,
                                      @Param("mobile") String mobile,
                                      @Param("userName") String userName,
                                      @Param("startTime") String startTime,
                                      @Param("endTime") String endTime,
                                      @Param("position") Integer position,
                                      @Param("offset") Integer offset);

    /**
     * 投资到期记录数
     * @param type
     * @param mobile
     * @param userName
     * @param startTime
     * @param endTime
     * @return
     */
    Integer investExpireCount(@Param("partnerCode") String partnerCode,
                              @Param("type") String type,
                              @Param("mobile") String mobile,
                              @Param("userName") String userName,
                              @Param("startTime") String startTime,
                              @Param("endTime") String endTime);

    /**
     * 投资到期总计
     * @param partnerCode
     * @param type
     * @param mobile
     * @param userName
     * @param startTime
     * @param endTime
     * @return
     */
    Double investExpireTotalAmount(@Param("partnerCode") String partnerCode,
                                   @Param("type") String type,
                                   @Param("mobile") String mobile,
                                   @Param("userName") String userName,
                                   @Param("startTime") String startTime,
                                   @Param("endTime") String endTime);
}