package com.pinting.business.service.manage;

import com.pinting.business.model.dto.LoanDTO;
import com.pinting.business.model.vo.LoanDailyStatisticsVO;
import com.pinting.business.model.vo.LoanDailyVO;
import com.pinting.business.model.vo.LoanVO;
import com.pinting.business.model.vo.RepaySchedulePlanVO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * Created by 剑钊 on 2016/11/14.
 */
public interface LoanService {

    /**
     * 借款管理
     * @param loanDTO
     * @return
     */
    List<LoanVO> queryLoanList(LoanDTO loanDTO);

    /**
     * 统计借款管理
     * @param loanDTO
     * @return
     */
    int queryLoanCount (LoanDTO loanDTO);

    /**
     * 根据loanId查询还款计划
     * @param loanId 借款编号
     * @return
     */
    public List<RepaySchedulePlanVO> queryRepaySchedulePlanList(int loanId);

    /**
     * 放款日常管理 列表
     * @param record
     * @return
     */
    public List<LoanDailyVO> queryLoanDailyInfo(LoanDailyVO record);

    /**
     * 放款日常管理 统计
     * @param record
     * @return
     */
    Integer queryLoanDailyCount(LoanDailyVO record);

    /**
     * 放款日常管理 赞分期资金计划总量
     * @param startTime
     * @param endTime
     * @return
     */
    double queryZanDailyTotalAmount(String startTime, String endTime);

    /**
     * 放款日常管理 云贷资金计划总量
     * @param startTime
     * @param endTime
     * @return
     */
    double queryYunDaiDailyTotalAmount(String startTime, String endTime);

    /**
     * 放款日常管理 赞时贷资金计划总量
     * @param startTime
     * @param endTime
     * @return
     */
    double queryZsdDailyTotalAmount(String startTime, String endTime);

    /**
     * 放款日常管理 放款成功的金额、笔数统计
     * @param partnerCode
     * @param startTime
     * @param endTime
     * @return
     */
    public LoanDailyStatisticsVO queryLoanDailyPaiedStatistics(String partnerCode, String startTime, String endTime);

    /**
     * 放款日常管理 放款处理中的金额、笔数统计
     * @param partnerCode
     * @param startTime
     * @param endTime
     * @return
     */
    public LoanDailyStatisticsVO queryLoanDailyPayingStatistics(String partnerCode, String startTime, String endTime);

    /**
     * 根据借贷关系表查询现有债权的总借款本金--relation的leftAmount
     * @param loanId
     * @param status
     * @return
     */
    Double getRelationAmountByLoanIdAndStatus(String loanId, String status);

    /**
     * 放款日常管理 7贷资金计划总量
     * @param startTime
     * @param endTime
     * @return
     */
    double querySevenDailyTotalAmount(String startTime, String endTime);

}
