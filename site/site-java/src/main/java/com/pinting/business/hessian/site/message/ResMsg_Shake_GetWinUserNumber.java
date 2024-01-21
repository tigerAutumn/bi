/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_Shake_GetWinUserNumber.java, v 0.1 2016-3-11 下午2:13:28 HuanXiong Exp $
 */
public class ResMsg_Shake_GetWinUserNumber extends ResMsg {

    /**  */
    private static final long serialVersionUID = 3728304101319614360L;
    

    // ========== 528活动参数 开始 =======================================
    private HashMap<String, Object> product;
    // ========== 528活动参数 结束 =====================================
    
    // ========== 母亲节活动参数 开始 =====================================
    private int chanceCount;
    // ========== 母亲节活动参数 结束 =====================================
    
    // ========== 318摇一摇参数 开始 =====================================
    private int count;
    // ========== 318摇一摇参数 结束 =====================================

    public int getCount() {
        return count;
    }

    public int getChanceCount() {
        return chanceCount;
    }

    public void setChanceCount(int chanceCount) {
        this.chanceCount = chanceCount;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public HashMap<String, Object> getProduct() {
        return product;
    }

    public void setProduct(HashMap<String, Object> product) {
        this.product = product;
    }

}
