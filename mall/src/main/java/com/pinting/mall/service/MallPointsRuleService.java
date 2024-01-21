package com.pinting.mall.service;

import java.util.List;

import com.pinting.mall.model.MallPointsRule;
import com.pinting.mall.model.MallPointsRuleDetail;
import com.pinting.mall.model.common.PagerModelRspDTO;
import com.pinting.mall.model.common.PagerReqDTO;
import com.pinting.mall.model.dto.MallPointsRuleDTO;
import com.pinting.mall.model.vo.MallExchangeOrdersVO;

/**
 * 积分设置服务
 * @author SHENGUOPING
 * @date  2018年5月11日 上午10:18:01
 */
public interface MallPointsRuleService {

	/**
	 * 查询积分设置列表
	 * @param status
	 * @return
	 */
	List<MallPointsRule> selectByStatus(String status);
	
	/**
	 * 查询积分设置
	 * @param id
	 * @return
	 */
	MallPointsRule findById(Integer id);
	
	/**
	 * 新增积分设置
	 * @param req
	 * @return
	 */
	Boolean addMallPointRule(MallPointsRuleDTO req);
	
	/**
	 * 分页查询积分设置列表
	 * @return
	 */
	List<MallPointsRule> findPointsRuleList(String status, int pageNum, int numPerPage);
	
	/**
	 * 管理台积分设置列表，新的分页插件
	 * @param record
	 * @param pagerReq
	 * @return
	 */
    PagerModelRspDTO<MallPointsRule> findPointsRuleList(String status, PagerReqDTO pagerReq);
    
	/**
	 * 更新积分设置状态
	 * @param status
	 * @return
	 */
	Boolean updateStatus(String status, Integer ruleId);
	
	/**
	 * 更改积分设置
	 * @param req
	 * @return
	 */
	Boolean updateMallPointRule(MallPointsRuleDTO req);
	
	/**
	 * 查询积分设置详情
	 * @param id
	 * @return
	 */
	MallPointsRuleDetail findByRuleId(Integer ruleId, String ruleKey);
	
}
