package com.pinting.business.accounting.finance.service;


/**
 * 
 * @project business
 * @title UserDepQuitService.java
 * @author Dragon & cat
 * @date 2017-4-5
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description  存管固定期限产品退出服务
 */
public interface UserDepFixedQuitService {
	
    /**
     * 退出服务
     */
    void quit(Integer subAccountId);
}
