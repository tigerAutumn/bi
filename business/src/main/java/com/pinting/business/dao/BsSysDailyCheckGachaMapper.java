package com.pinting.business.dao;

import com.pinting.business.model.BsSysDailyCheckGacha;
import com.pinting.business.model.BsSysDailyCheckGachaExample;
import com.pinting.business.model.vo.BsSysDailyCheckGachaVO;
import com.pinting.core.util.DateUtil;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsSysDailyCheckGachaMapper {
    int countByExample(BsSysDailyCheckGachaExample example);

    int deleteByExample(BsSysDailyCheckGachaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsSysDailyCheckGacha record);

    int insertSelective(BsSysDailyCheckGacha record);

    List<BsSysDailyCheckGacha> selectByExample(BsSysDailyCheckGachaExample example);

    BsSysDailyCheckGacha selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsSysDailyCheckGacha record, @Param("example") BsSysDailyCheckGachaExample example);

    int updateByExample(@Param("record") BsSysDailyCheckGacha record, @Param("example") BsSysDailyCheckGachaExample example);

    int updateByPrimaryKeySelective(BsSysDailyCheckGacha record);

    int updateByPrimaryKey(BsSysDailyCheckGacha record);
    
    /**
     * 对账结果导出列表
     * @param startTime
     * @param endTime
     * @return
     */
    List<BsSysDailyCheckGachaVO> findSysDailyCheckGachaList(@Param("merchantNo") String merchantNo, @Param("checkDate")String checkDate);
    
    /**
     * 对账结果导出列表，辅助商户
     * @param startTime
     * @param endTime
     * @return
     */
    List<BsSysDailyCheckGachaVO> findAssistSysDailyCheckGachaList(@Param("merchantNo") String merchantNo, @Param("checkDate")String checkDate);
    
    /**
     * 对账结果导出计数
     * @param startTime
     * @param endTime
     * @return
     */
    int countSysDailyCheckGacha(@Param("merchantNo") String merchantNo, @Param("checkDate")String checkDate);
    
    /**
     * 对账结果统计
     * @param startTime
     * @param endTime
     * @return
     */
    List<BsSysDailyCheckGacha> selectSysDailyCheckGacha(@Param("merchantNo") String merchantNo, @Param("checkDate") String checkDate);
    
    /**
     * 恒丰对账结果导出列表
     * @param startTime
     * @param endTime
     * @return
     */
    List<BsSysDailyCheckGachaVO> findHfbankDailyCheckGachaList(@Param("checkDate")String checkDate);
    
    /**
     * 恒丰对账结果统计
     * @param startTime
     * @param endTime
     * @return
     */
    List<BsSysDailyCheckGacha> selectHfbankDailyCheckGacha(@Param("channel") String channel, @Param("checkDate") String checkDate);
    
}