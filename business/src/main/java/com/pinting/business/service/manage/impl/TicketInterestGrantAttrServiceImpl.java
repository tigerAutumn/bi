package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.BsAutoInterestTicketRuleMapper;
import com.pinting.business.dao.BsInterestTicketGrantAttributeMapper;
import com.pinting.business.model.BsAutoInterestTicketRule;
import com.pinting.business.model.BsInterestTicketGrantAttribute;
import com.pinting.business.service.manage.TicketInterestGrantAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by zousheng on 2018/4/3.
 */
@Service
public class TicketInterestGrantAttrServiceImpl implements TicketInterestGrantAttrService {

    @Autowired
    private BsInterestTicketGrantAttributeMapper bsInterestTicketGrantAttributeMapper;

    @Autowired
    private BsAutoInterestTicketRuleMapper bsAutoInterestTicketRuleMapper;

    @Override
    public void addTicketGrantAttribute(BsInterestTicketGrantAttribute attribute) {
        attribute.setCreateTime(new Date());
        attribute.setUpdateTime(new Date());
        bsInterestTicketGrantAttributeMapper.insertSelective(attribute);
    }


    @Override
    public void addAutoTicketGrantRule(BsAutoInterestTicketRule rule) {
        rule.setCreateTime(new Date());
        rule.setUpdateTime(new Date());
        bsAutoInterestTicketRuleMapper.insertSelective(rule);
    }

}
