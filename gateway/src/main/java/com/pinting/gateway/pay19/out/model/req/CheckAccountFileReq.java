/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.req;

import java.util.Date;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: CheckAccountFileReq.java, v 0.1 2015-8-31 下午8:11:05 BabyShark Exp $
 */
public class CheckAccountFileReq extends BaseReq {

    /**  */
    private static final long serialVersionUID = 8206859180178686132L;
    private Date              checkDate;

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

}
