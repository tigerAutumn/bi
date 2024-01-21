package com.pinting.business.dao;

import com.pinting.business.model.BsRevenueTransRecord;
import com.pinting.business.model.BsRevenueTransRecordExample;
import com.pinting.business.model.vo.DafyRevenueSelfForCheckVO;
import com.pinting.business.model.vo.ZsdRevenueForCheckVO;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BsRevenueTransRecordMapper {
    long countByExample(BsRevenueTransRecordExample example);

    int deleteByExample(BsRevenueTransRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsRevenueTransRecord record);

    int insertSelective(BsRevenueTransRecord record);

    List<BsRevenueTransRecord> selectByExample(BsRevenueTransRecordExample example);

    BsRevenueTransRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsRevenueTransRecord record, @Param("example") BsRevenueTransRecordExample example);

    int updateByExample(@Param("record") BsRevenueTransRecord record, @Param("example") BsRevenueTransRecordExample example);

    int updateByPrimaryKeySelective(BsRevenueTransRecord record);

    int updateByPrimaryKey(BsRevenueTransRecord record);

    Date selectLastSettleDate();

    /**
     * 获取结算日期
     * @return
     */
    Date selectLastSettleDateByCode(@Param("partnerCode") String partnerCode);
        
    /**
     * 查询某时间段内的营收记录
     * @return
     */
    List<BsRevenueTransRecord> selectYesRevenueTransRecord(@Param("partnerCode") String partnerCode, @Param("begin") Date begin, 
    		@Param("end") Date end, @Param("type") String type);
    
    /**
     *
     * @param time
     * @param start
     * @param numPerPage
     * @return
     */
    List<DafyRevenueSelfForCheckVO> selectForCheck(@Param("time") String time, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**
     * 获取赞时贷营收对账单数据
     * @param time
     * @param start
     * @param numPerPage
     * @return
     */
    List<ZsdRevenueForCheckVO> selectForZsdCheck(@Param("time") String time, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**
     * 成功七贷营收对账单
     * @param time
     * @param start
     * @param numPerPage
     * @return
     */
    List<DafyRevenueSelfForCheckVO> selectForSevenCheck(@Param("time") String time, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);
}