package com.pinting.gateway.in.facade.mobile;

import com.pinting.business.accounting.finance.service.UserBonusExtractService;
import com.pinting.business.dao.BsSpecialJnlMapper;
import com.pinting.business.hessian.site.message.ReqMsg_Bonus_BonusToJSH;
import com.pinting.business.hessian.site.message.ReqMsg_Bonus_RecommendBonusListQuery;
import com.pinting.business.hessian.site.message.ResMsg_Bonus_BonusToJSH;
import com.pinting.business.hessian.site.message.ResMsg_Bonus_RecommendBonusListQuery;
import com.pinting.business.model.BsDailyBonus;
import com.pinting.business.model.BsSpecialJnl;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.service.site.BonusService;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.util.Constants;
import com.pinting.gateway.in.util.InterfaceVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * @Project: business
 * @Title: BonusFacade.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:44:02
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("appBonus")
public class BonusFacade{
	@Autowired
	private BonusService bonusService;
	@Autowired
	private UserBonusExtractService bonusToJSHService;
	@Autowired
	private BsSpecialJnlMapper bsSpecialJnlMapper;
	@Autowired
	private SubAccountService subAccountService;
	
	@InterfaceVersion("RecommendBonusListQuery/1.0.0")
	public void recommendBonusListQuery(ReqMsg_Bonus_RecommendBonusListQuery req, ResMsg_Bonus_RecommendBonusListQuery resp) {
		//是否可提现标志
		boolean withdrawFlag = req.getWithdrawFlag() != null && Constants.CAN_WITHDRAW_BONUS.equals(req.getWithdrawFlag()) ? true : false;
		
		List<BsDailyBonus> bsDailyBonusList=bonusService.findDailyBonusByUserId(req.getUserId(), req.getPageIndex(), req.getPageSize(), withdrawFlag);
		ArrayList<HashMap<String, Object>> bonuss=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DecimalFormat format = new DecimalFormat("#.##");
		if(bsDailyBonusList != null && bsDailyBonusList.size() > 0){
			bonuss=new ArrayList<HashMap<String,Object>>();
			for (BsDailyBonus bsDailyBonus : bsDailyBonusList) {
				HashMap<String, Object> mapBonus=new HashMap<String, Object>();
				mapBonus.put("earningsDate", bsDailyBonus.getTime());
				mapBonus.put("amount", bsDailyBonus.getBonus());
				mapBonus.put("bonusType", bsDailyBonus.getType());
				mapBonus.put("note", bsDailyBonus.getNote());
				mapBonus.put("useTime", bsDailyBonus.getUseTime());
				if (bsDailyBonus.getUseTime() != null && !"".equals(bsDailyBonus.getUseTime())) {
					String useTime  = sdf.format(bsDailyBonus.getUseTime());
					mapBonus.put("detail", useTime+"使用"+format.format(bsDailyBonus.getTicketApr())+"% 加息券，加入"+bsDailyBonus.getProductName()+"，加息收益已入奖励金账户。");
				} else {
					mapBonus.put("detail","");
				}
				bonuss.add(mapBonus);
			}
		}
		BsSpecialJnl special = bsSpecialJnlMapper.selectBonusByUserId(req.getUserId());
		if(special == null){
			resp.setHaveSpecial("false");
		}else{
			resp.setHaveSpecial("true");
			resp.setSpecialBonusAmount(special.getAmount());
		}
		
		//Integer userId = req.getUserId();
		/*Double amount = req.getAmount(); */
		/*BsUserAssetVO bsUserAsset = userService.findUserAssetByUserId(req.getUserId());
	    HashMap<String, Object> userAsset = BeanUtils.transBeanMap(bsUserAsset);
	    if (userAsset != null && userAsset.size() > 0) {
	    	resp.setBonus((Double) userAsset.get("currentBonus"));
	    } else {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }*/
		
		BsSubAccount jljAccount = subAccountService.findJLJSubAccountByUserId(req.getUserId());//用户子账户奖励金户
		if(jljAccount != null){
			resp.setBonus((Double)jljAccount.getCanWithdraw());
		}
		
		int pageSize=req.getPageSize();
		resp.setTotalCount((int)Math.ceil((double)bonusService.findDailyBonusCountByUserId(req.getUserId(), withdrawFlag)/pageSize));
		resp.setBonuss(bonuss);
		resp.setPageIndex(req.getPageIndex());
		
	}
	
	@InterfaceVersion("BonusToJSH/1.0.0")
	public void bonusToJSH(ReqMsg_Bonus_BonusToJSH req, ResMsg_Bonus_BonusToJSH res){
		Integer userId = req.getUserId();
		/*Double amount = req.getAmount(); */
		BsSubAccount suAcccount = subAccountService.findJLJSubAccountByUserId(userId);
		/*BsUserAssetVO bsUserAsset = userService.findUserAssetByUserId(req.getUserId());
	    HashMap<String, Object> userAsset = BeanUtils.transBeanMap(bsUserAsset);*/
	    if (suAcccount != null) {
	    	if(suAcccount.getCanWithdraw() <= 0){
	    		res.setFlag(false);
	            res.setResMsg("未抱歉，暂无可提现的奖励金");
	    	}else{
	    		//奖励金转账户余额，具体操作，查看service
		    	res.setFlag(bonusToJSHService.transBonusToJSH(userId, (Double) suAcccount.getCanWithdraw()));
	    	}
	    	
	    } else {
           res.setFlag(false);
           res.setResMsg("未获取到用户信息，请重试");
        }
		
	}
}
