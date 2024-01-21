package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by cyb on 2017/10/25.
 */
public class ReqMsg_BannerConfig_QueryBanner extends ReqMsg {

    private static final long serialVersionUID = 8943661169470048450L;

    private String showPage;

    private String bannerChannel;

    public String getShowPage() {
        return showPage;
    }

    public void setShowPage(String showPage) {
        this.showPage = showPage;
    }

    public String getBannerChannel() {
        return bannerChannel;
    }

    public void setBannerChannel(String bannerChannel) {
        this.bannerChannel = bannerChannel;
    }
}
