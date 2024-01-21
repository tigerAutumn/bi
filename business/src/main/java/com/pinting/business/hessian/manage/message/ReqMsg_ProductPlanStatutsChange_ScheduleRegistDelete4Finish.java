/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.manage.message;

import com.pinting.business.model.BsProduct;
import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_ProductPlanStatutsChange_ScheduleRegistDelete4Finish.java, v 0.1 2016-4-22 上午11:52:03 HuanXiong Exp $
 */
public class ReqMsg_ProductPlanStatutsChange_ScheduleRegistDelete4Finish extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -2913144527515234799L;

    private BsProduct bsProduct;

    public BsProduct getBsProduct() {
        return bsProduct;
    }

    public void setBsProduct(BsProduct bsProduct) {
        this.bsProduct = bsProduct;
    }
    
}
