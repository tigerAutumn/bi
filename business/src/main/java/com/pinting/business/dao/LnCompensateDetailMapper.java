package com.pinting.business.dao;

import com.pinting.business.model.LnCompensateDetail;
import com.pinting.business.model.LnCompensateDetailExample;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.LnLoan;
import org.apache.ibatis.annotations.Param;

public interface LnCompensateDetailMapper {
    int countByExample(LnCompensateDetailExample example);

    int deleteByExample(LnCompensateDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnCompensateDetail record);

    int insertSelective(LnCompensateDetail record);

    List<LnCompensateDetail> selectByExample(LnCompensateDetailExample example);

    LnCompensateDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnCompensateDetail record, @Param("example") LnCompensateDetailExample example);

    int updateByExample(@Param("record") LnCompensateDetail record, @Param("example") LnCompensateDetailExample example);

    int updateByPrimaryKeySelective(LnCompensateDetail record);

    int updateByPrimaryKey(LnCompensateDetail record);

    /**
     * 查询某个时间段里代偿明细表，代偿成功的最大id号
     * @return
     */
    Integer selectLnCompensateDetailMaxId();

    /**
     * 依次查询小于最大id号的五条记录
     */
    List<LnCompensateDetail> selectLateAgreementRepeatGenerateList(@Param("id") Integer id, @Param("selectNum") Integer selectNum);
    

	/**
	 * 对账完成后， 代偿明细
	 * @param status
	 * @param orderNo
	 * @return
	 */
	List<LnCompensateDetail> selectLnCompensateDetailList(@Param("status")String status, @Param("orderNo") String orderNo);

    /**
     * 根据ln_repay_schedule.finish_time查询已结清且有本金代偿记录、未生成代偿协议的借款信息
     * @param startTime
     * @param endTime
     * @return
     */
    List<LnLoan> selectSettledLnLoan4RepayScheduleFinishTime(@Param("maxId") Integer maxId, @Param("selectNum") Integer selectNum, @Param("startTime")Date startTime, @Param("endTime")Date endTime);
}