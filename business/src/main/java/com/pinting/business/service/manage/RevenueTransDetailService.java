package com.pinting.business.service.manage;

import com.pinting.business.model.vo.RevenueTransDetailVO;

import java.util.List;

/**
 * Author:      shh
 * Date:        2017/6/16
 * Description: 云贷砍头息代收代付服务
 */
public interface RevenueTransDetailService {

    /**
     * 分页查询云贷砍头息代收代付
     * @param startTime
     * @param endTime
     * @param userName
     * @param mobile
     * @param transType
     * @param numPerPage
     * @param pageNum
     * @return
     */
    List<RevenueTransDetailVO> queryYunHeadFeeList(String startTime, String endTime, String userName, String mobile,
                                                   String transType, Integer numPerPage, Integer pageNum);

    /**
     * 统计云贷砍头息代收代付记录
     * @return
     */
    long countYunHeadFee(String startTime, String endTime, String userName, String mobile, String transType);

    /**
     * 云贷砍头息代收-总额
     * @return
     */
    double sumFeeOfDS(String startTime, String endTime, String userName, String mobile, String transType);

    /**
     * 云贷砍头息代付-总额
     * @return
     */
    double sumFeeOfDF(String startTime, String endTime, String userName, String mobile, String transType);

}