package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      shh
 * Date:        2017/8/22
 * Description: 根据url channel查询banner信息 入参
 */
public class ReqMsg_BannerConfig_GetBanner extends ReqMsg {

    private static final long serialVersionUID = 8918376281905618275L;

    private String url;

    private String bannerChannel;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBannerChannel() {
        return bannerChannel;
    }

    public void setBannerChannel(String bannerChannel) {
        this.bannerChannel = bannerChannel;
    }
}
