package com.pinting.business.service.site.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsHolidayMapper;
import com.pinting.business.dao.BsPayLimitMapper;
import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.enums.TimeTypeEnum;
import com.pinting.business.model.BsHoliday;
import com.pinting.business.model.BsPayLimit;
import com.pinting.business.model.BsPayLimitExample;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.vo.ManageResVO;
import com.pinting.business.model.vo.SysPayLimitVO;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.in.util.MethodRole;

import java.util.List;

/**
 * 
 * @Project: formalBusiness
 * @Title: SysConfigServiceImpl.java
 * @author Huang MengJian
 * @date 2015-1-21 上午11:20:46
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class SysConfigServiceImpl implements SysConfigService{

	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;
	@Autowired
	private BsPayLimitMapper bsPayLimitMapper;
	@Autowired
	private BsHolidayMapper bsHolidayMapper;
	
	@Override
	@MethodRole("APP")
	public BsSysConfig findConfigByKey(String key) {
		return bsSysConfigMapper.selectByPrimaryKey(key);
	}

	@Override
	public List<BsSysConfig> findConfigByKeys(String... keys) {
		return bsSysConfigMapper.selectByConfKeys(keys);
	}

	@Override
	public BsSysConfig findDaliyBuyTimes() {
		return findConfigByKey(Constants.DAILY_BUY_TIMES);
	}

	@Override
	public BsSysConfig findEmergencyMobile() {
		 return findConfigByKey(Constants.EMERGENCY_MOBILE);
	}

	@Override
	public BsSysConfig findPageSize() {
		return findConfigByKey(Constants.PAGE_SIZE);
	}

	@Override
	public BsSysConfig findPriceCeiling() {
		return findConfigByKey(Constants.PRICE_CEILING);
	}

	@Override
	public BsSysConfig findPriceLimit() {
		return findConfigByKey(Constants.PRICE_LIMIT);
	}

	@Override
	public BsSysConfig findWarmIpOparateTimes() {
		return findConfigByKey(Constants.WARN_IP_OP_TIMES);
	}

	@Override
	public BsSysConfig findWarmUserOparateTimes() {
		return findConfigByKey(Constants.WARN_USER_OP_TIMES);
	}

	@Override
	public BsSysConfig findViewPageNum() {
		return findConfigByKey(Constants.VIEW_PAGE_NUM);
	}

	@Override
	public BsSysConfig findViewNum() {
		return findConfigByKey(Constants.VIEW_NUM);
	}

	@Override
	@MethodRole("APP")
	public BsSysConfig findWithdrawDay() {
		return findConfigByKey(Constants.WITHDRAW_DAYS);
	}

	@Override
	@MethodRole("APP")
	public BsSysConfig findWithdrawMonth() {
		return findConfigByKey(Constants.WITHDRAW_MONTH);
	}

	@Override
	public Double findRatePercentByKey(String key) {
		BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(key);
		if(config != null){
			Double rate = Double.valueOf(config.getConfValue());
			return rate;
		}else{
			return null;
		}
	}

	@Override
	public Double findRateByKey(String key, Integer decimalNum) {
		BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(key);
		if(config != null){
			Double rate = MoneyUtil.divide(Double.valueOf(config.getConfValue()), 100, decimalNum).doubleValue();
			return rate;
		}else{
			return null;
		}
	}

	@Override
	public Integer countSysPayLimit(SysPayLimitVO req) {
		BsPayLimitExample example = new BsPayLimitExample();
		example.createCriteria();
		return bsPayLimitMapper.countByExample(example);
	}

	@Override
	public List<SysPayLimitVO> findSysPayLimitList(SysPayLimitVO req, Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		List<SysPayLimitVO> list = bsPayLimitMapper.findSysPayLimitList(req, position, offset);
		if (!CollectionUtils.isEmpty(list)) {
			for (SysPayLimitVO sysPayLimitVO : list) {
				sysPayLimitVO.setTimeType(TimeTypeEnum.getEnumByTimeType(sysPayLimitVO.getTimeType())==null?
						"":TimeTypeEnum.getEnumByTimeType(sysPayLimitVO.getTimeType()).getDescription());
			}
		}
		return list;
	}

	@Override
	public Boolean updatePayLimitStatus(String status, Integer ruleId, Integer mUserId) {
		Boolean isSuccess = false;
		int count = 0;
		if (StringUtil.isNotEmpty(status)) {
			BsPayLimit bsPayLimit = this.findPayLimitById(ruleId);
			if (bsPayLimit != null) {
				bsPayLimit.setId(bsPayLimit.getId());
				bsPayLimit.setIsDelete(status);
				bsPayLimit.setUpdateTime(new Date());
				bsPayLimit.setmUserId(mUserId);
				count = bsPayLimitMapper.updateByPrimaryKeySelective(bsPayLimit);
			}
		}
		if (count > 0) {
			isSuccess = true;
		}
		return isSuccess;
	}

	@Override
	public BsPayLimit findPayLimitById(Integer id) {
		BsPayLimitExample example = new BsPayLimitExample();
		example.createCriteria().andIdEqualTo(id);
		List<BsPayLimit> list = bsPayLimitMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list)? null : list.get(0);
	}

	private Boolean hasDefaultPayLimit() {
		BsPayLimitExample example = new BsPayLimitExample();
		example.createCriteria().andTimeTypeEqualTo("DEFAULT").andIsDeleteEqualTo(Constants.PAY_LIMIT_DELETE_NO)
			.andPayBusinessTypeEqualTo("BF_DF");
		List<BsPayLimit> list = bsPayLimitMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list)? false : true;
	}
	
	private Boolean hasTimeTypePayLimit(String timeType) {
		BsPayLimitExample example = new BsPayLimitExample();
		example.createCriteria().andTimeTypeEqualTo(timeType).andIsDeleteEqualTo(Constants.PAY_LIMIT_DELETE_NO)
			.andPayBusinessTypeEqualTo("BF_DF");
		List<BsPayLimit> list = bsPayLimitMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list)? false : true;
	}
	
	@Override
	public ManageResVO operatePayLimitConfig(SysPayLimitVO req, String operateFlag) {
		ManageResVO res = new ManageResVO();
		if ("add".equals(operateFlag)) {
			if (hasDefaultPayLimit() && TimeTypeEnum.DEFAULT.getTimeType().equals(req.getTimeType())) {
				res.setReturnCode("has_default_payLimit");
				res.setReturnMsg("已存在默认配置，请勿重复添加！");
				return res;
			}
			if (hasTimeTypePayLimit(req.getTimeType())) {
				res.setReturnCode("has_default_payLimit");
				res.setReturnMsg("已存在对应时间配置，请勿重复添加！");
				return res;
			}
			BsPayLimit payLimit = new BsPayLimit();
			payLimit.setPayBusinessType("BF_DF");
			payLimit.setTimeType(req.getTimeType());
			if (TimeTypeEnum.DEFAULT.getTimeType().equals(req.getTimeType())) {
				payLimit.setTimeStart("00:00:00");
				payLimit.setTimeEnd("23:59:59");
			} else {
				payLimit.setTimeStart(req.getTimeStart());
				payLimit.setTimeEnd(req.getTimeEnd());
			}
			payLimit.setLimitType(req.getLimitType());
			payLimit.setLimitEquleType(req.getLimitEquleType());
			payLimit.setLimitVaule(Integer.parseInt(req.getLimitVaule()));
			payLimit.setIsDelete(Constants.PAY_LIMIT_DELETE_NO);
			payLimit.setmUserId(req.getmUserId());
			payLimit.setCreateTime(new Date());
			payLimit.setUpdateTime(new Date());
			bsPayLimitMapper.insertSelective(payLimit);
		} else if ("update".equals(operateFlag)) {
			if (StringUtil.isNotEmpty(req.getRuleId())) {
				Boolean dateIsPayLimit = this.dateIsPayLimit(Integer.parseInt(req.getRuleId()), new Date());
				if (dateIsPayLimit) {
					res.setReturnCode("dateIsPayLimit_yes");
					res.setReturnMsg("当前时间处于限制时间段内，不允许删除和修改！");
					return res;
				}	
				BsPayLimit payLimit = findPayLimitById(Integer.parseInt(req.getRuleId()));
				BsPayLimit payLimitTemp = new BsPayLimit();
				payLimitTemp.setId(payLimit.getId());
				payLimitTemp.setTimeStart(req.getTimeStart());
				payLimitTemp.setTimeEnd(req.getTimeEnd());
				payLimitTemp.setLimitType(req.getLimitType());
				payLimitTemp.setLimitEquleType(req.getLimitEquleType());
				payLimitTemp.setLimitVaule(Integer.parseInt(req.getLimitVaule()));
				payLimitTemp.setmUserId(req.getmUserId());
				payLimitTemp.setUpdateTime(new Date());
				bsPayLimitMapper.updateByPrimaryKeySelective(payLimitTemp);
			} else {
				res.setReturnCode("operate_id_empty");
				res.setReturnMsg("操作失败，记录不存在！");
			}
			return res;
		}
		return res;
	}

	@Override
	public Boolean dateIsPayLimit(Integer id, Date date) {
		String time = DateUtil.getTime(date);
		Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
		BsPayLimit bsPayLimit = findPayLimitById(id);
		if (bsPayLimit != null) {
			if (TimeTypeEnum.DEFAULT.getTimeType().equals(bsPayLimit.getTimeType())) {
				return true;
			} else if (TimeTypeEnum.HOLIDAY.getTimeType().equals(bsPayLimit.getTimeType())) {
				BsHoliday holiday = bsHolidayMapper.todayIsHolidayOrNot();
				if (holiday != null) {
					// 当天是节假日
		        	BsPayLimitExample example = new BsPayLimitExample();
		    		example.createCriteria().andIdEqualTo(id).andPayBusinessTypeEqualTo("BF_DF")
		    		.andTimeStartLessThanOrEqualTo(time).andTimeEndGreaterThanOrEqualTo(time);
		    		List<BsPayLimit> list = bsPayLimitMapper.selectByExample(example);
					return CollectionUtils.isEmpty(list) ? false : true;
				}
			} else {
				if ((dayWeek-1) == Integer.parseInt(TimeTypeEnum.getEnumByTimeType(bsPayLimit.getTimeType()).getCode())) {
					// 当天是工作日
		        	BsPayLimitExample example = new BsPayLimitExample();
		    		example.createCriteria().andIdEqualTo(id).andPayBusinessTypeEqualTo("BF_DF")
		    		.andTimeStartLessThanOrEqualTo(time).andTimeEndGreaterThanOrEqualTo(time);
		    		List<BsPayLimit> list = bsPayLimitMapper.selectByExample(example);
					return CollectionUtils.isEmpty(list) ? false : true;
				}
			}
		}
		return false;
	}
	
}
