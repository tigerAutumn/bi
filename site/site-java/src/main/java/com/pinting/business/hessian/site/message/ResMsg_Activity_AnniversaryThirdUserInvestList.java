package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:      cyb
 * Date:        2017/2/27
 * Description: 2017周年庆第三期活动主页面-用户已瓜分奖金
 */
public class ResMsg_Activity_AnniversaryThirdUserInvestList extends ResMsg {

    private static final long serialVersionUID = 3215619982349576390L;

    /* 是否登录 */
    private String isLogin;

    /* 是否开始标识 */
    private String isStart;

    /* 五重礼我瓜分到的奖金列表 */
    private List<HashMap<String, Object>> userAwardList;


    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public String getIsStart() {
        return isStart;
    }

    public void setIsStart(String isStart) {
        this.isStart = isStart;
    }

    public List<HashMap<String, Object>> getUserAwardList() {
        return userAwardList;
    }

    public void setUserAwardList(List<HashMap<String, Object>> userAwardList) {
        this.userAwardList = userAwardList;
    }
}
