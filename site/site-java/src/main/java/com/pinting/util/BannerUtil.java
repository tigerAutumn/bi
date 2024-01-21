package com.pinting.util;

import com.pinting.business.hessian.site.message.ReqMsg_BannerConfig_QueryBanner;
import com.pinting.business.hessian.site.message.ResMsg_BannerConfig_QueryBanner;
import com.pinting.site.communicate.CommunicateBusiness;

import java.util.HashMap;
import java.util.List;

/**
 * Created by cyb on 2017/10/25.
 */
public class BannerUtil {

    /**
     * 查詢banner
     * @param communicateBusiness
     * @param channel
     * @param showPage
     * @return
     */
    public static List<HashMap<String, Object>> queryBannerList(CommunicateBusiness communicateBusiness, String channel, String showPage) {
        ReqMsg_BannerConfig_QueryBanner req = new ReqMsg_BannerConfig_QueryBanner();
        String bannerChannel = "";
        if(Constants.CHANNEL_MICRO.equals(channel)) {
            bannerChannel = Constants.BANNER_CHANNEL_MICRO;
        } else if(Constants.CHANNEL_GEN.equals(channel)) {
            bannerChannel = Constants.BANNER_CHANNEL_GEN;
        } else if(Constants.CHANNEL_GEN_178.equals(channel)) {
            bannerChannel = Constants.BANNER_CHANNEL_GEN178;
        }
        req.setBannerChannel(bannerChannel);
        req.setShowPage(showPage);
        ResMsg_BannerConfig_QueryBanner res = (ResMsg_BannerConfig_QueryBanner) communicateBusiness.handleMsg(req);
        return res.getResult();
    }

}
