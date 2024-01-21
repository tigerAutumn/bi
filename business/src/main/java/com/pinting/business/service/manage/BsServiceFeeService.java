package com.pinting.business.service.manage;

import com.pinting.business.model.BsServiceFee;
import com.pinting.business.model.vo.BsServiceFeeVO;

import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/1/23
 * Description: 手续费服务
 */
public interface BsServiceFeeService {

    /**
     * 分页查询手续费
     * @param feeType
     * @param startTime
     * @param endTime
     * @param channel
     * @param numPerPage
     * @param pageNum
     * @return
     */
    List<BsServiceFeeVO> findServiceFeePage(String feeType, String startTime, String endTime, String channel, String partnerCode, Integer numPerPage, Integer pageNum);

    /**
     * 统计手续费个数
     * @param feeType
     * @param startTime
     * @param endTime
     * @param channel
     * @return
     */
    long countServiceFee(String feeType, String startTime, String endTime, String channel, String partnerCode);

    /**
     * 统计代付手续费总额
     * @return
     */
    double sumFeeOfDF(String feeType, String startTime, String endTime, String channel, String partnerCode);

    /**
     * 分页查询提现手续费
     * @param startTime
     * @param endTime
     * @param userName
     * @param mobile
     * @param channel
     * @param numPerPage
     * @param pageNum
     * @return
     */
    List<BsServiceFeeVO> queryWithdrawFeeList(String startTime, String endTime, String userName, String mobile,
                                              String channel, Integer numPerPage, Integer pageNum);

    /**
     * 统计提现手续费记录
     * @return
     */
    long conutWithdrawFee(String startTime, String endTime, String userName, String mobile, String channel);

    /**
     * 统计提现手续费总额
     * @return
     */
    double sumWithdrawFee(String startTime, String endTime, String userName, String mobile, String channel);

}
