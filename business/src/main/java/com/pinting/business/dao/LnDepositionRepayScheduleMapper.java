package com.pinting.business.dao;

import com.pinting.business.accounting.loan.model.DepLimitRepaySchedule;
import com.pinting.business.model.LnDepositionRepaySchedule;
import com.pinting.business.model.LnDepositionRepayScheduleExample;
import com.pinting.business.model.vo.CompensateTransfersPdfVO;
import com.pinting.business.model.vo.LnDepositionRepayScheduleVO;
import com.pinting.business.model.vo.RepayDSDFVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface LnDepositionRepayScheduleMapper {
    long countByExample(LnDepositionRepayScheduleExample example);

    int deleteByExample(LnDepositionRepayScheduleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnDepositionRepaySchedule record);

    int insertSelective(LnDepositionRepaySchedule record);

    List<LnDepositionRepaySchedule> selectByExample(LnDepositionRepayScheduleExample example);

    LnDepositionRepaySchedule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnDepositionRepaySchedule record, @Param("example") LnDepositionRepayScheduleExample example);

    int updateByExample(@Param("record") LnDepositionRepaySchedule record, @Param("example") LnDepositionRepayScheduleExample example);

    int updateByPrimaryKeySelective(LnDepositionRepaySchedule record);

    int updateByPrimaryKey(LnDepositionRepaySchedule record);

    /**
     * 根据借款id查询最大的还款期次
     * @param loanId
     * @return
     */
    int selectMaxSerialIdByLoanId(@Param("loanId") Integer loanId);

    /**
     * 获取线下还款账单列表
     * @param flag
     * @param itemAccount
     */
    List<LnDepositionRepaySchedule> getLimitDesList(@Param("flag") String flag,@Param("itemAccount") Integer itemAccount);

    /**
     * 获取线下还款RETURN账单列表
     * @param flag
     * @param itemAccount
     * @return
     */
    List<DepLimitRepaySchedule> getRetLimitDesList(@Param("flag") String flag, @Param("itemAccount") Integer itemAccount);

    /**
     * 根据id获取DepRepaySchedule信息及合作方
     * @param id
     * @return
     */
    DepLimitRepaySchedule getDepRepayScheduleById(@Param("id") Integer id);

    /**
     * 管理台归集户代收代付列表查询
     * @param name
     * @param mobile
     * @param type
     * @param timeStart
     * @param timeEnd
     * @param position
	 * @param offset
     * @return
     */
    List<RepayDSDFVO> getRepayDSDFList(@Param("name") String name, @Param("mobile") String mobile, @Param("type") String type,
    		@Param("timeStart") String timeStart, @Param("timeEnd") String timeEnd, @Param("status") String status, @Param("position") Integer position,@Param("offset")  Integer offset);

    /**
     * 管理台归集户代收代付列表总条数
     * @param name
     * @param mobile
     * @param type
     * @param timeStart
     * @param timeEnd
     * @return
     */
    int countRepayDSDF(@Param("name") String name, @Param("mobile") String mobile, @Param("type") String type,
    		@Param("timeStart") String timeStart, @Param("timeEnd") String timeEnd, @Param("status")String status);

    /**
     * 管理台归集户代收代付，代收代付总金额
     * @param name
     * @param mobile
     * @param type
     * @param timeStart
     * @param timeEnd
     * @return
     */
    RepayDSDFVO sumRepayDSDF(@Param("name") String name, @Param("mobile") String mobile, @Param("type") String type,
    		@Param("timeStart") String timeStart, @Param("timeEnd") String timeEnd, @Param("status")String status);

    /**
     * 退票数据批量订单号查询计数
     * @param beginTime
     * @param endTime
     * @return
     */
    int countRebateOrder(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 退票数据批量订单号查询列表
     * @param beginTime
     * @param endTime
     * @param numPerPage
     * @return
     */
    List<LnDepositionRepayScheduleVO> queryRebateOrderList(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**
     * 分期产品代偿协议（确认函，通知书），代偿支付金额
     * @param loanId
     * @return
     */
    List<CompensateTransfersPdfVO> selectCompensateTransfer4StageList(@Param("loanId") Integer loanId);

    /**
     * 管理台归集户代收代付，下载批次流水数据查询
     * @param time
     * @return
     */
    List<RepayDSDFVO> selectRepayDSDFBatchFlowList(@Param("time") String time);
}