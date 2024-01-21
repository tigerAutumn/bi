package com.pinting.business.test.dao;

import com.pinting.business.accounting.finance.service.DepUserBalanceBuyService;
import com.pinting.business.accounting.finance.service.DepUserBonusWithdrawService;
import com.pinting.business.accounting.finance.service.DepUserTopUpService;
import com.pinting.business.accounting.finance.service.UserCardOperateService;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_BalanceBuy;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_Order;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_BalanceBuy;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_Order;
import com.pinting.business.test.TestBase;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author:      cyb
 * Date:        2017/4/11
 * Description: 测试用户操作类（绑卡充值购买提现）
 */
public class TestUserOperateService extends TestBase {

}
