package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.BsRevenueTransDetailMapper;
import com.pinting.business.model.vo.RevenueTransDetailVO;
import com.pinting.business.service.manage.RevenueTransDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author:      shh
 * Date:        2017/6/16
 * Description: 云贷砍头息代收代付服务
 */
@Service
public class RevenueTransDetailServiceImpl implements RevenueTransDetailService {

    @Autowired
    private BsRevenueTransDetailMapper bsRevenueTransDetailMapper;

    @Override
    public List<RevenueTransDetailVO> queryYunHeadFeeList(String startTime, String endTime, String userName, String mobile, String transType, Integer numPerPage, Integer pageNum) {
        int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
        List<RevenueTransDetailVO> list = bsRevenueTransDetailMapper.selectYunHeadFeeList(startTime, endTime, userName,  mobile, transType, numPerPage, start);
        return list;
    }

    @Override
    public long countYunHeadFee(String startTime, String endTime, String userName, String mobile, String transType) {
        return bsRevenueTransDetailMapper.countYunHeadFee(startTime, endTime, userName,  mobile, transType);
    }

    @Override
    public double sumFeeOfDS(String startTime, String endTime, String userName, String mobile, String transType) {
        Double sumFeeDS = bsRevenueTransDetailMapper.sumFeeOfDS(startTime, endTime, userName,  mobile, transType);
        return null == sumFeeDS ? 0d : sumFeeDS;
    }

    @Override
    public double sumFeeOfDF(String startTime, String endTime, String userName, String mobile, String transType) {
        Double sumFeeDF = bsRevenueTransDetailMapper.sumFeeOfDF(startTime, endTime, userName,  mobile, transType);
        return null == sumFeeDF ? 0d : sumFeeDF;
    }

}