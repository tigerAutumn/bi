/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import java.util.List;
import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_Product_QianBaoProductList.java, v 0.1 2015-11-25 下午9:18:51 HuanXiong Exp $
 */
public class ResMsg_Product_QianBaoProductList extends ResMsg {

    /**  */
    private static final long serialVersionUID = -7273175893081212885L;
    
    private List<Map<String,Object>> ProductLst;

    public List<Map<String, Object>> getProductLst() {
        return ProductLst;
    }

    public void setProductLst(List<Map<String, Object>> productLst) {
        ProductLst = productLst;
    }

}
