/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_User_MyRecommend.java, v 0.1 2015-11-17 下午5:20:57 HuanXiong Exp $
 */
public class ResMsg_User_MyRecommend extends ResMsg {

    /**  */
    private static final long serialVersionUID = -2960595209566806358L;
    
    private List<String> mobiles;

    public List<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<String> mobiles) {
        this.mobiles = mobiles;
    }
    
}
