package com.pinting.business.service.site;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.BsPayLimit;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.vo.ManageResVO;
import com.pinting.business.model.vo.SysPayLimitVO;

import java.util.List;

/**
 * 
 * @Project: Business
 * @Title: SysConfigService.java
 * @author Huang MengJian
 * @date 2015-1-21 上午11:18:33
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface SysConfigService {

	/**
	 * 根据key查询系统配置表
	 * @param key
	 * @author Huang MengJian
	 * @return 返回系统配置信息, 没有找到，返回null
	 */
	public BsSysConfig findConfigByKey(String key);

	/**
	 * 根据keys查询系统配置表
	 * @param keys
	 * @author zousheng
	 * @return 返回系统配置信息, 没有找到，返回null
	 */
	public List<BsSysConfig> findConfigByKeys(String... keys);

	/**
	 * 查询每天购买产品的次数
	 * @author Huang MengJian
	 * @return 返回系统配置信息, 没有找到，返回null
	 */
	public BsSysConfig findDaliyBuyTimes();
	
	/**
	 * 系统告警通知手机号码
	 * @return 返回系统配置信息, 没有找到，返回null
	 */
	public BsSysConfig findEmergencyMobile();
	
	/**
	 * 一个手机号每天可以发送验证码次数
	 * @return 返回系统配置信息, 没有找到，返回null
	 */
	public BsSysConfig findPageSize();
	
	/**
	 * 购买上限金额
	 * @return 返回系统配置信息, 没有找到，返回null
	 */
	public BsSysConfig findPriceCeiling();
	
	/**
	 * 购买下限金额
	 * @return 返回系统配置信息, 没有找到，返回null
	 */
	public BsSysConfig findPriceLimit();
	
	/**
	 * 每分钟某IP操作次数告警门限
	 * @return 返回系统配置信息, 没有找到，返回null
	 */
	public BsSysConfig findWarmIpOparateTimes();
	
	/**
	 * 每分钟某用户操作次数告警门限
	 * @return 返回系统配置信息, 没有找到，返回null
	 */
	public BsSysConfig findWarmUserOparateTimes();
	
	/**
	 * 主页后台数据查询条数
	 * @return 返回系统配置信息, 没有找到，返回null
	 */
	public BsSysConfig findViewPageNum();
	
	/**
	 * 主页购买数据界面显示条数
	 * @return 返回系统配置信息, 没有找到，返回null
	 */
	public BsSysConfig findViewNum();
	
	/**
	 * 当天提现次数限制
	 * @return
	 */
	public BsSysConfig findWithdrawDay();
	/**
	 * 当月提现次数限制
	 * @return
	 */
	public BsSysConfig findWithdrawMonth();
	
	/**
	 * 根据key查询，并转化成conf_value%的数
	 * @param key
	 * @return
	 */
	public Double findRatePercentByKey(String key);

	/**
	 * 根据key查询，并转化成conf_value/100的数，保留decimalNum位小数
	 * @param key
	 * @param decimalNum
	 * @return
	 */
	public Double findRateByKey(String key, Integer decimalNum);
	/**
	 * 宝付代付归集户配置 计数
	 * @return
	 */
	Integer countSysPayLimit(SysPayLimitVO req);
	
	/**
	 * 宝付代付归集户配置 列表
	 * @param req
	 * @return
	 */
	List<SysPayLimitVO> findSysPayLimitList(SysPayLimitVO req, Integer page, Integer offset);
	
	/**
	 * 宝付代付归集户配置，更新状态
	 * @param status
	 * @param ruleId
	 * @return
	 */
	Boolean updatePayLimitStatus(String status, Integer ruleId, Integer mUserId);
	
	/**
	 * 判断当前时间处于限制时间段内，不允许删除和修改
	 * @param date
	 * @return
	 */
	Boolean dateIsPayLimit(Integer id, Date date);
	
	/**
	 * 根据主键找宝付代付归集户配置信息
	 * @param id
	 * @return
	 */
	BsPayLimit findPayLimitById(Integer id);
	
	/**
	 * 宝付代付归集户配置添加和更新实现
	 * @param req
	 * @return
	 */
	ManageResVO operatePayLimitConfig(SysPayLimitVO req, String operateFlag);
	
}
