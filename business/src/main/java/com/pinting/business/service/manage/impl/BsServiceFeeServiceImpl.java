package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.BsServiceFeeMapper;
import com.pinting.business.model.BsServiceFeeExample;
import com.pinting.business.model.vo.BsServiceFeeVO;
import com.pinting.business.service.manage.BsServiceFeeService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/1/23
 * Description: 手续费服务
 */
@Service
public class BsServiceFeeServiceImpl implements BsServiceFeeService {

    @Autowired
    private BsServiceFeeMapper bsServiceFeeMapper;

    @Override
    public List<BsServiceFeeVO> findServiceFeePage(String feeType, String startTime, String endTime, String channel, String partnerCode, Integer numPerPage, Integer pageNum) {
        int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
        List<BsServiceFeeVO> grid = bsServiceFeeMapper.selectByParams(feeType, startTime, endTime, channel, partnerCode, numPerPage, start);
        return grid;
    }

    @Override
    public long countServiceFee(String feeType, String startTime, String endTime, String channel, String partnerCode) {
        return bsServiceFeeMapper.countByParams(feeType, startTime, endTime, channel, partnerCode);
    }

    @Override
    public double sumFeeOfDF(String feeType, String startTime, String endTime, String channel, String partnerCode) {
        Double feeOfDF = bsServiceFeeMapper.sumFeeOfDF(feeType, startTime, endTime, channel, partnerCode);
        return null == feeOfDF ? 0d : feeOfDF;
    }

    @Override
    public List<BsServiceFeeVO> queryWithdrawFeeList(String startTime, String endTime, String userName, String mobile, String channel, Integer numPerPage, Integer pageNum) {
        int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
        List<BsServiceFeeVO> resultList = bsServiceFeeMapper.selectWithdrawFeeList(startTime, endTime, userName, mobile, channel, numPerPage, start);
        return resultList;
    }

    @Override
    public long conutWithdrawFee(String startTime, String endTime, String userName, String mobile, String channel) {
        return bsServiceFeeMapper.conutWithdrawFee(startTime, endTime, userName, mobile, channel);
    }

    @Override
    public double sumWithdrawFee(String startTime, String endTime, String userName, String mobile, String channel) {
        Double sumFee = bsServiceFeeMapper.sumWithdrawFee(startTime, endTime, userName, mobile, channel);
        return null == sumFee ? 0d : sumFee;
    }
}
