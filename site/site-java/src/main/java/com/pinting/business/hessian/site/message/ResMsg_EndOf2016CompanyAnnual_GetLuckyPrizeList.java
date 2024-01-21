package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 2016公司年会抽奖活动 五六七等奖抽取 出参
 * Created by shh on 2017/1/14 15:25.
 */
public class ResMsg_EndOf2016CompanyAnnual_GetLuckyPrizeList extends ResMsg {

    private static final long serialVersionUID = 7286243483623938386L;

    /* 屏幕滚动姓名*/
    private ArrayList<HashMap<String,Object>> scrollLlist;

    /* 最终中奖的名单*/
    private ArrayList<HashMap<String,Object>> valueList;

    public ArrayList<HashMap<String, Object>> getScrollLlist() {
        return scrollLlist;
    }

    public void setScrollLlist(ArrayList<HashMap<String, Object>> scrollLlist) {
        this.scrollLlist = scrollLlist;
    }

    public ArrayList<HashMap<String, Object>> getValueList() {
        return valueList;
    }

    public void setValueList(ArrayList<HashMap<String, Object>> valueList) {
        this.valueList = valueList;
    }
}
