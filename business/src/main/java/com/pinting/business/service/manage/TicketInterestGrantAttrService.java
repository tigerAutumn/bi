package com.pinting.business.service.manage;

import com.pinting.business.model.BsAutoInterestTicketRule;
import com.pinting.business.model.BsInterestTicketGrantAttribute;

/**
 * Created by zousheng on 2018/4/3.
 */
public interface TicketInterestGrantAttrService {

    /**
     * 新增加息券发放属性
     * @param attribute
     */
    public void addTicketGrantAttribute(BsInterestTicketGrantAttribute attribute);

    /**
     * 新增加息券自动发放规则
     * @param rule
     */
    public void addAutoTicketGrantRule(BsAutoInterestTicketRule rule);
}