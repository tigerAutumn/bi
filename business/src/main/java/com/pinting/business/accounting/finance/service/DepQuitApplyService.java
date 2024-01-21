package com.pinting.business.accounting.finance.service;

import com.pinting.business.model.BsDepositionQuitApply;

public interface DepQuitApplyService {
	

    /**
     * 新增申请退出记录
     * @param record
     * @return 0：重复插入或插入异常；1：新增成功
     */
    public int addDepositionQuitApply(BsDepositionQuitApply record);

}
