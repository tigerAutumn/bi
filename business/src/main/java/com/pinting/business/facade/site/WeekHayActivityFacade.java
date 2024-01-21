package com.pinting.business.facade.site;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_WeekHayActivity_AddUserRemind;
import com.pinting.business.hessian.site.message.ReqMsg_WeekHayActivity_CheckWeekhayStatus;
import com.pinting.business.hessian.site.message.ReqMsg_WeekHayActivity_CountUserRemind;
import com.pinting.business.hessian.site.message.ResMsg_WeekHayActivity_AddUserRemind;
import com.pinting.business.hessian.site.message.ResMsg_WeekHayActivity_CheckWeekhayStatus;
import com.pinting.business.hessian.site.message.ResMsg_WeekHayActivity_CountUserRemind;
import com.pinting.business.model.BsProductInform;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.service.site.BsProductInformService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;

@Component("WeekHayActivity")
public class WeekHayActivityFacade {

	@Autowired
	private BsProductInformService bsProductInformService;
	@Autowired
    private SysConfigService sysConfigService;
	
	/**
	 * 周周乐活动-新增用户提醒数据
	 * @param req
	 * @param res
	 */
	public void addUserRemind(ReqMsg_WeekHayActivity_AddUserRemind req, ResMsg_WeekHayActivity_AddUserRemind res){
		//校验时间
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Integer week = cal.get(Calendar.DAY_OF_WEEK)-1;
		if(week != Constants.WEEKHAY_WEEK_3 && week != Constants.WEEKHAY_WEEK_4){
			res.setResCode("999");
			res.setResMsg("本期尚未开始");
			return ;
		}
		BsProductInform inform = new BsProductInform();
		if(week == Constants.WEEKHAY_WEEK_4) {
			Date Wednesday = DateUtil.addDays(new Date(), -1);//周三的时间
			inform.setCreateTime(DateUtil.parseDate(DateUtil.formatYYYYMMDD(Wednesday)));
		}else if(week == Constants.WEEKHAY_WEEK_3){
			inform.setCreateTime(DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date())));//周三的时间
		}
		
		//校验用户是否有数据
		inform.setUserId(req.getUserId());
		inform.setRemindType(Constants.REMIND_TYPE_WEEKACTIVITY);
		
		int count = bsProductInformService.countByUserTime(inform);
		if(count == 0){
			BsProductInform informTemp = new BsProductInform();
			informTemp.setUserId(req.getUserId());
			informTemp.setRemindType(Constants.REMIND_TYPE_WEEKACTIVITY);
			informTemp.setCreateTime(new Date());
			informTemp.setUpdateTime(new Date());
			bsProductInformService.addProductInform(informTemp);
			return;
		}else{
			res.setResCode("999");
			res.setResMsg("已设置短信提醒");
			return;
		}
	}
	
	/**
	 * 周周乐活动-查询用户是否已经添加此次活动期的提醒
	 * @param req
	 * @param res
	 */
	public void countUserRemind(ReqMsg_WeekHayActivity_CountUserRemind req, ResMsg_WeekHayActivity_CountUserRemind res){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Integer week = cal.get(Calendar.DAY_OF_WEEK)-1;
		if(week != Constants.WEEKHAY_WEEK_4 && week != Constants.WEEKHAY_WEEK_3){
			res.setResCode("999");
			res.setResMsg("本期尚未开始");
			return ;
		}
		BsProductInform inform = new BsProductInform();
		if(week == Constants.WEEKHAY_WEEK_4) {
			Date Wednesday = DateUtil.addDays(new Date(), -1);//周三的时间
			inform.setCreateTime(DateUtil.parseDate(DateUtil.formatYYYYMMDD(Wednesday)));
		}else if(week == Constants.WEEKHAY_WEEK_3){
			inform.setCreateTime(DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date())));//周三的时间
		}
		inform.setUserId(req.getUserId());
		inform.setRemindType(Constants.REMIND_TYPE_WEEKACTIVITY);
		Integer count = bsProductInformService.countByUserTime(inform);
		res.setRemindCount(count);
	}
	
	//查询周周乐活动状态
    public ResMsg_WeekHayActivity_CheckWeekhayStatus checkWeekhayStatus(ReqMsg_WeekHayActivity_CheckWeekhayStatus req, ResMsg_WeekHayActivity_CheckWeekhayStatus res) {
    	if (Constants.WEEKHAY_LUCKY_LENDERS.equals(req.getActivityType())) {
    		BsSysConfig activityTimeConfig = sysConfigService.findConfigByKey(Constants.LUCKY_LENDERS_ACTIVITY_TIME);
    		String activityTime = activityTimeConfig.getConfValue();
    		String[] list = activityTime.split(";");
    		res.setDisplayTime(list[0]);
    		
    		BsSysConfig activityStartTimeConfig = sysConfigService.findConfigByKey(Constants.LUCKY_LENDERS_ACTIVITY_START_TIME);
    		Integer activityStartTime = Integer.valueOf(activityStartTimeConfig.getConfValue());
    		res.setActivityStartTime(activityStartTime);
		}
    	return res;
    }
}
