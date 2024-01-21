package com.pinting.business.model.vo;

/**
 * 产品标签内容vo 首页新手标、产品详情页使用
 *
 * @author shh
 * @date 2018/5/2 20:15
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public class BsProductTagContentVO {

    /* 提醒标签内容 */
    private String remindTagContent;

    /* 加息标签内容 */
    private String interestRatesTagContent;

    public String getRemindTagContent() {
        return remindTagContent;
    }

    public void setRemindTagContent(String remindTagContent) {
        this.remindTagContent = remindTagContent;
    }

    public String getInterestRatesTagContent() {
        return interestRatesTagContent;
    }

    public void setInterestRatesTagContent(String interestRatesTagContent) {
        this.interestRatesTagContent = interestRatesTagContent;
    }
}
