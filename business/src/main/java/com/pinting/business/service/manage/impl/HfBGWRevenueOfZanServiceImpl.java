package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.BsSysAccountJnlMapper;
import com.pinting.business.model.vo.HFBGWRevenueOfZanVO;
import com.pinting.business.service.manage.HfBGWRevenueOfZanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询恒丰币港湾营收（赞分期）
 * Created by cyb on 2018/4/20.
 */
@Service
public class HfBGWRevenueOfZanServiceImpl implements HfBGWRevenueOfZanService {


    @Autowired
    private BsSysAccountJnlMapper bsSysAccountJnlMapper;

    @Override
    public List<HFBGWRevenueOfZanVO> queryHFBGWRevenueOfZan(String startTime, String endTime, Integer pageNum, Integer numPerPage) {
        Integer start = null;
        if(pageNum != null) {
            start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage); // mysql的分页
        }
        List<HFBGWRevenueOfZanVO> hFBGWRevenueOfZanList = bsSysAccountJnlMapper.selectHFBGWRevenueOfZan(startTime, endTime, start, numPerPage);
        return CollectionUtils.isEmpty(hFBGWRevenueOfZanList) ? new ArrayList<HFBGWRevenueOfZanVO>() : hFBGWRevenueOfZanList;
    }

    @Override
    public int countHFBGWRevenueOfZan(String startTime, String endTime) {
        return bsSysAccountJnlMapper.countHFBGWRevenueOfZan(startTime, endTime);
    }

    @Override
    public Double sumHFBGWRevenueOfZan(String startTime, String endTime) {
        return bsSysAccountJnlMapper.sumHFBGWRevenueOfZan(startTime, endTime);
    }
}
