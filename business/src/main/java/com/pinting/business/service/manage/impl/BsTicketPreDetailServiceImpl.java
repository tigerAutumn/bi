package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.BsTicketPreDetailMapper;
import com.pinting.business.model.vo.BsTicketPreDetailVO;
import com.pinting.business.service.manage.BsTicketPreDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BsTicketPreDetailServiceImpl implements BsTicketPreDetailService {

    @Autowired
    private BsTicketPreDetailMapper bsTicketPreDetailMapper;

    @Override
    public List<BsTicketPreDetailVO> getBsUserBySerialNo(BsTicketPreDetailVO req) {
        return bsTicketPreDetailMapper.selectBsUserBySerialNo(req);
    }

    @Override
    public int getCountBySerialNo(BsTicketPreDetailVO req) {
        return bsTicketPreDetailMapper.selectCountBySerialNo(req);
    }

}
