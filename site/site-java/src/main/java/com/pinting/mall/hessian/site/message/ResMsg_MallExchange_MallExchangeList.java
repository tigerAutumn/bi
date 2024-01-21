package com.pinting.mall.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 积分商城商品兑换列表
 *
 * @author shh
 * @date 2018/5/16 11:27
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public class ResMsg_MallExchange_MallExchangeList extends ResMsg {

    private static final long serialVersionUID = 4671833468949361888L;

    private Integer userId;

    /* 总页数 */
    private Integer totalCount;

    /* 当前页 */
    private Integer pageIndex;

    private ArrayList<HashMap<String, Object>> MallExchangeData;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public ArrayList<HashMap<String, Object>> getMallExchangeData() {
        return MallExchangeData;
    }

    public void setMallExchangeData(ArrayList<HashMap<String, Object>> mallExchangeData) {
        MallExchangeData = mallExchangeData;
    }
}
