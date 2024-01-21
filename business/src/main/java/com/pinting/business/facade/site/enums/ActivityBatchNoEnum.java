/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.facade.site.enums;

/**
 * 
 * @author HuanXiong
 * @version $Id: ActivityBatchNoEnum.java, v 0.1 2016-1-22 下午5:20:43 HuanXiong Exp $
 */
public enum ActivityBatchNoEnum {
    
    // 活动批次
    NORMAL_BATCH_NO_25("1", "普通金蛋（25%）"),
    NORMAL_BATCH_NO_100("2", "普通金蛋（100%）"),
    SET_AUGER_BATCH_NO("3", "镶钻金蛋"),
    SUPREME_BATCH_NO("4", "至尊钻蛋");
    
    private String batchNo;
    
    private String name;

    private ActivityBatchNoEnum(String batchNo, String name) {
        this.batchNo = batchNo;
        this.name = name;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
