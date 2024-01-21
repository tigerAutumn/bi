package com.pinting.mall.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pinting.mall.dao.MallBsSpecialJnlMapper;
import com.pinting.mall.model.MallBsSpecialJnl;
import com.pinting.mall.service.MallSpecialJnlService;
import com.pinting.mall.util.Constants;

import java.util.Date;

@Service
public class MallSpecialJnlServiceImpl implements MallSpecialJnlService {
    private Logger log = LoggerFactory.getLogger(MallSpecialJnlServiceImpl.class);
    @Autowired
    private MallBsSpecialJnlMapper bsSpecialJnlMapper;
    
    @Override
    public void warn4FailNoSMS(Double amount, String detail, String orderNo,
                               String type) {
        try {
            log.error("告警>>>>>>" + detail);
            // 告警表插入
            MallBsSpecialJnl specialJnl = new MallBsSpecialJnl();
            specialJnl.setAmount(amount);
            specialJnl.setDetail(detail);
            specialJnl.setOrderNo(orderNo);
            specialJnl.setStatus(Constants.SPECIAL_JNL_STATUS_CREATE);
            specialJnl.setType(type);
            specialJnl.setOpTime(new Date());
            specialJnl.setUpdateTime(new Date());
            bsSpecialJnlMapper.insertSelective(specialJnl);
        } catch (Exception e) {
            log.warn("==================告警发生异常====================", e);
        }
    }
}