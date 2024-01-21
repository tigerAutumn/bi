package com.pinting.business.service.manage.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pinting.business.dao.LnSubAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsAppMessageMapper;
import com.pinting.business.dao.BsAppPushedMessageMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.LnLoanRelationMapper;
import com.pinting.business.hessian.manage.message.ReqMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.hessian.manage.message.ResMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.model.BsAppMessage;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsSubAccountExample;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.vo.BsActivityLuckyDrawVO;
import com.pinting.business.service.manage.BsSubAccountService;
import com.pinting.business.service.manage.PushUtilService;
import com.pinting.business.service.site.SendWechatService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
/**
 * 子账户接口实现类
 * @author caonengwen
 *
 */
@Service
public class BsSubAccountServiceImpl implements BsSubAccountService{
	
	@Autowired
	public BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private PushUtilService pushUtilService;
    @Autowired
    private BsAppMessageMapper appMapper;
    @Autowired
    private BsAppPushedMessageMapper appPushMapper;
    @Autowired
	private SendWechatService sendWechatService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	public LnSubAccountMapper lnSubAccountMapper;
	@Autowired 
	private LnLoanRelationMapper lnLoanRelationMapper;
	
	@Override
	public List<BsSubAccount> findList(BsSubAccount record) {
		BsSubAccountExample example = new BsSubAccountExample();
		if(null != record.getId() && 0 != record.getId()){
			example.createCriteria().andIdEqualTo(record.getId());
		}
		if(StringUtil.isNotEmpty(record.getBankCard())){
			example.createCriteria().andBankCardEqualTo(record.getBankCard());
		}
		if(StringUtil.isNotEmpty(record.getProductType())){
			example.createCriteria().andProductTypeEqualTo(record.getProductType());
		}
		return bsSubAccountMapper.selectByExample(example);
	}

	@Override
	public Map<String, Object> countSubAccount(Map<String, Object> map) {
		return bsSubAccountMapper.countSubAccount(map);
	}
	
	@Override
	public Map<String, Object> countSubAccountByTime(Map<String, Object> map) {
		return bsSubAccountMapper.countSubAccountByTime(map);
	}
	
	@Override
	public Map<String, Object> countSubAccountByPartner(Map<String, Object> map) {
		return bsSubAccountMapper.countSubAccountByPartner(map);
	}

	@Override
	public double countBonusAccBalance() {
		return bsSubAccountMapper.countBonusAccBalance();
	}

	@Override
	public Map<String,Object> findWeightInvestTrem(Map<String, Object> map) {
		return bsSubAccountMapper.selectWeightInvestTrem(map);
	}

	@Override
	public Map<String, Object> statisticSubAccount(Map<String, Object> map) {
		return bsSubAccountMapper.statisticSubAccount(map);
	}
	
	
	/**
     * 站岗户资金自动退回消息推送
     * @Title: chargeAuthActBackMessage
     * @Description: 站岗户资金自动退回消息推送
     * @param type 1、短信2、微信3、app推送
     * @param mobiles 手机号（当微信和app推送时，传入null）
     * @param userIds 用户ID（当短信推送时，传入null）
     * @param openBalance 委托金额（开户金额）
     * @param balance 实际出借金额
     * @throws
     */
	@Override
	public void chargeAuthActBackMessage(Integer type, List<String> mobiles, List<Integer> userIds, String openBalance, String balance, String principalAmount,String debtAmount, String productId,String subAccountId,String openTime) {
		switch(type) {
		case 1:
			for(String mobile : mobiles) {
				try {
					//暂时不需要短信推送
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case 2:
			for(Integer userId : userIds) {
				try {
					sendWechatService.chargeAuthActBackSend(userId, openBalance, balance, productId, subAccountId,openTime);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case 3:
			try {
				Date now = new Date();
				String time = com.pinting.core.util.DateUtil.format(now);
				//新增app消息表
				BsAppMessage message = new BsAppMessage();
				message.setReleasePart(1); //发送终端为币港湾app
				message.setName("委托出借成功");
				message.setTitle("委托出借成功");
				message.setPushChar("您有一笔委托出借成功，查看详情>>>");
				message.setPushAbstract("委托金额"+openBalance+"元；出借交易金额"+balance+"元，其中出借本金"+principalAmount+"元，债转付息"+debtAmount+"元（付息将在借款人还款时回到您的账户中）；未出借资金已转入您的可用余额，您可以再次委托出借或提现。");
				message.setContent(GlobEnvUtil.get("wechat.server.web")+"/micro2.0/match/myMatchApp?productId="+productId+"&subAccountId="+subAccountId);
				message.setPushType(1); //appURL
				//message.setAppPage(15); //债权明细页面
				message.setIsSend(1);
				message.setCreateTime(now);
				message.setPushTime(now);
				appMapper.insertSelective(message);
				
				//新增app消息推送表
				int count = 500;
				int total = userIds.size()%count==0?userIds.size()/count:userIds.size()/count+1;
				for(int j=0;j<total;j++) {
					StringBuffer sql = new StringBuffer("insert into bs_app_pushed_message(user_id,message_id,create_time) values");
					for(int i=j*count;i<count*(j+1);i++) {
						if(i == userIds.size()) {
							break;
						}
						String temp = "("+userIds.get(i)+","+message.getId()+",'"+time+"'),";
						sql.append(temp);
					}
					appPushMapper.insertBsAppPushedMessage(sql.toString().substring(0,sql.toString().length()-1));
				}
				
				//推送
				final ReqMsg_PushUtil_SendCustomizedcast req = new ReqMsg_PushUtil_SendCustomizedcast();
				final ResMsg_PushUtil_SendCustomizedcast res = new ResMsg_PushUtil_SendCustomizedcast();
				req.setUserIdList(userIds);
				req.setTicker(message.getName());
				req.setTitle(message.getTitle());
				req.setText(message.getPushChar());
				req.setProductionMode(GlobEnvUtil.get("umeng.push.environment").trim().equals("test")?false:true);
				HashMap<String,Object> extendMap = new HashMap<String,Object>();
				extendMap.put("goFlag", 3);
				extendMap.put("appPage", message.getAppPage());
				req.setExtendMap(extendMap);
				req.setAndroid_appkey(GlobEnvUtil.get("bgw.android.app.key"));
				req.setAndroid_appMasterSecret(GlobEnvUtil.get("bgw.android.app.master.secret"));
				req.setIos_appkey(GlobEnvUtil.get("bgw.ios.app.key"));
				req.setIos_appMasterSecret(GlobEnvUtil.get("bgw.ios.app.master.secret"));
				
				//ios的额外配置
				req.setAlert(message.getPushChar());
				req.setBadge(1);
				new Thread(new Runnable() {
					public void run() {
						pushUtilService.sendCustomizedcast(req, res);
					}
				}).start();
			}catch(Exception e) {
				e.printStackTrace();
			}
			break;
		}
		
	}
	
	
	@Override
	public String queryEntrustStatusBySubAccountId(Integer subAccountId){
		return bsSubAccountMapper.queryEntrustStatusBySubAccountId(subAccountId);
	}

	@Override
	public BsSubAccount queryDEPJSHSubAccountByUserId() {
		BsSysConfig config = sysConfigService.findConfigByKey(Constants.ZAN_COMPENSATE_USER_ID);
		Integer compUserId = config == null ? 0 : Integer.valueOf(config.getConfValue());
		return bsSubAccountMapper.selectDepJSHSubAccountByUserId(compUserId);
	}

	@Override
	public Double countRedAccBalance() {
		return bsSubAccountMapper.countRedAccBalance();
	}
	
	@Override
	public Double countRed7AccBalance() {
		return bsSubAccountMapper.countRed7AccBalance();
	}

	@Override
	public BsSubAccount findSubAccount4Lock(Integer userId, String code) {
		if(StringUtil.isNotEmpty(code)){
			BsSubAccount tempAct = bsSubAccountMapper.selectSingleSubActByUserAndType(userId, code);
			if(tempAct != null){
				return bsSubAccountMapper.selectSubAccountByIdForLock(tempAct.getId());
			}
		}
		return null;
	}
	
	@Override
	public Double sumRedAccBalanceByType(String productType) {
		return bsSubAccountMapper.sumRedAccBalanceByType(productType);
	}
	
	@Override
	public Double selectWeekhaySumAmount() {
		return bsSubAccountMapper.selectWeekhaySumAmount();
	}
	
	@Override
	public List<BsActivityLuckyDrawVO> selectWeekhayBigInvestors(Double investAmount) {
		return bsSubAccountMapper.selectWeekhayBigInvestors(investAmount);
	}
	
	@Override
	public List<BsActivityLuckyDrawVO> selectWeekhayAllInvestors() {
		return bsSubAccountMapper.selectWeekhayAllInvestors();
	}

	@Override
	public Double querySumBgwAuthYunBalance() {
		return bsSubAccountMapper.selectSumBgwAuthYunBalance();
	}

	@Override
	public Double querySumBgwAuthSevrnBalance() {
		return bsSubAccountMapper.selectSumBgwAuthSevenBalance();
	}

	@Override
	public Double querySumBgwAuthZsdBalance() {
		return bsSubAccountMapper.selectSumBgwAuthZsdBalance();
	}

	@Override
	public Double querySumBgwAuthZanBalance() {
		return bsSubAccountMapper.selectSumBgwAuthZanBalance();
	}

	@Override
	public Double querySumLoanBalance() {
		return lnSubAccountMapper.selectSumLoanBalance();
	}

	@Override
	public Double querySumRedFreeAccBalance() {
		return bsSubAccountMapper.sumRedFreeAccBalance();
	}

	@Override
	public Double querySumBgwAuthFreeBalance() {
		return bsSubAccountMapper.selectSumBgwAuthFreeBalance();
	}
	
	@Override
	public Double sumFinancesAuthBalance(String endTime, String productType) {
		return bsSubAccountMapper.sumFinancesAuthBalance(endTime, productType);
	}

	@Override
	public Double sumLoanRepayBalance(String endTime, String partnerCode) {
		if (StringUtil.isEmpty(partnerCode)) {
			return lnLoanRelationMapper.sumLoanReturnRepayBalance(endTime);
		} else {			
			return lnLoanRelationMapper.sumLoanRepayBalance(endTime, partnerCode);
		}
	}
	
}
