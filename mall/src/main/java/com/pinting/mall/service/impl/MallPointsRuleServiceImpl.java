package com.pinting.mall.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.mall.dao.MallPointsRuleDetailMapper;
import com.pinting.mall.dao.MallPointsRuleMapper;
import com.pinting.mall.enums.MallRuleDetailEnum;
import com.pinting.mall.enums.MallRuleEnum;
import com.pinting.mall.model.MallPointsRule;
import com.pinting.mall.model.MallPointsRuleDetail;
import com.pinting.mall.model.MallPointsRuleDetailExample;
import com.pinting.mall.model.MallPointsRuleExample;
import com.pinting.mall.model.MallPointsRuleExample.Criteria;
import com.pinting.mall.model.common.PagerModelRspDTO;
import com.pinting.mall.model.common.PagerReqDTO;
import com.pinting.mall.model.dto.MallPointsRuleDTO;
import com.pinting.mall.service.MallPointsRuleService;
import com.pinting.mall.util.Constants;

/**
 *
 * @author SHENGUOPING
 * @date  2018年5月11日 上午10:19:47
 */
@Service
public class MallPointsRuleServiceImpl implements MallPointsRuleService {

	@Autowired
	private MallPointsRuleMapper mallPointsRuleMapper;
	@Autowired
	private MallPointsRuleDetailMapper mallPointsRuleDetailMapper;

	
	private Logger log = LoggerFactory.getLogger(MallPointsRuleServiceImpl.class);

	@Override
	public List<MallPointsRule> selectByStatus(String status) {
		
		return mallPointsRuleMapper.selectByStatus(status);
	}

	@Override
	public Boolean addMallPointRule(MallPointsRuleDTO req) {
		Boolean isSuccess = false;
		try {
			String scene = req.getGetScene();
			// 参数校验，业务校验
			if (!checkPointRuleParams(req)) {
				return isSuccess;
			}
			
			if (isSimpleRule(scene)) {
				MallPointsRule mallPointsRule = fillMallPointsRule(req.getOpUserId(), 
						req.getTriggerTimeStart(), req.getTriggerTimeEnd());
				mallPointsRule.setGetScene(scene);
				mallPointsRule.setGetTimes(Constants.MALL_GET_TIMES_ONE);
				mallPointsRule.setGetTimeType(Constants.MALL_GET_TIME_TYPE_REAL_TIME);
				mallPointsRule.setPoints(req.getPoints() == null? 0L : req.getPoints().longValue());
				mallPointsRule.setStatus(req.getStatus());
				mallPointsRuleMapper.insertSelective(mallPointsRule);
			} else {
				MallPointsRule record = fillMallPointsRule(req.getOpUserId(), 
						req.getTriggerTimeStart(), req.getTriggerTimeEnd());
				record.setStatus(req.getStatus());
				record.setGetScene(scene);
				// 加入产品： 多次获取，日终入账
				if (MallRuleEnum.MALL_INVEST.getCode().equals(scene)) {
					record.setGetTimes(Constants.MALL_GET_TIMES_MORE);
					record.setGetTimeType(Constants.MALL_GET_TIME_TYPE_DAY);
					record.setPoints(null);
					mallPointsRuleMapper.insertSelective(record);
					addMallPointsRuleDetail(record.getId(), MallRuleDetailEnum.MALL_EXCHANGE_RATE.getCode(), req.getRuleValueExchangeRate());
					// 累计加入产品： 单次获取，日终入账
				} else if(MallRuleEnum.MALL_TOTAL_INVEST.getCode().equals(scene)) {
					record.setGetTimes(Constants.MALL_GET_TIMES_ONE);
					record.setGetTimeType(Constants.MALL_GET_TIME_TYPE_DAY);
					record.setPoints(StringUtil.isEmpty(req.getRuleValueTotalInvest())? 0L : Long.valueOf(req.getRuleValueTotalInvest()));
					mallPointsRuleMapper.insertSelective(record);
					// 累计加入产品场景，积分规则详情
					addMallPointsRuleDetail(record.getId(), MallRuleDetailEnum.MALL_INVEST_AMOUNT_BEGIN.getCode(), req.getRuleValueInvestAmountBegin());
					addMallPointsRuleDetail(record.getId(), MallRuleDetailEnum.MALL_INVEST_AMOUNT_END.getCode(), req.getRuleValueInvestAmountEnd());
					// 签到： 多次获取，实时入账
				} else if(MallRuleEnum.MALL_SIGN.getCode().equals(scene)) {
					record.setGetTimes(Constants.MALL_GET_TIMES_MORE);
					record.setGetTimeType(Constants.MALL_GET_TIME_TYPE_REAL_TIME);
					record.setPoints(null);
					mallPointsRuleMapper.insertSelective(record);
					// 签到场景，积分规则详情
					addMallPointsRuleDetail(record.getId(), MallRuleDetailEnum.MALL_SIGN_INIT_POINT.getCode(), req.getRuleValueSignInitPoint());
					addMallPointsRuleDetail(record.getId(), MallRuleDetailEnum.MALL_SIGN_INCREMENT_POINT.getCode(), req.getRuleValueSignIncrementPoint());
					addMallPointsRuleDetail(record.getId(), MallRuleDetailEnum.MALL_SIGN_AWARD_POINT.getCode(), req.getRuleValueSignAwardPoint());
					// APP签到场景，积分规则详情
					addMallPointsRuleDetail(record.getId(), MallRuleDetailEnum.MALL_SIGN_INIT_POINT_APP.getCode(), req.getRuleValueSignInitPointApp());
					addMallPointsRuleDetail(record.getId(), MallRuleDetailEnum.MALL_SIGN_INCREMENT_POINT_APP.getCode(), req.getRuleValueSignIncrementPointApp());
					addMallPointsRuleDetail(record.getId(), MallRuleDetailEnum.MALL_SIGN_AWARD_POINT_APP.getCode(), req.getRuleValueSignAwardPointApp());
				}
			}
			isSuccess = true;
		} catch (Exception e) {
			isSuccess = false;
			log.error("method addMallPointRule exception:{}", e);
		} 	
		return isSuccess;
	}
	
	/**
	 * 新增和编辑积分设置，参数校验
	 */
	private Boolean checkPointRuleParams(MallPointsRuleDTO req) {
		Boolean checkParamsLegal = false;
		String triggerTimeStart = req.getTriggerTimeStart();
		String triggerTimeEnd = req.getTriggerTimeEnd();
		String scene = req.getGetScene();
		String status = req.getStatus();
		// 除累计加入产品积分奖励进行触发时间校验，新增的多条规则不允许时间重复
		if (Constants.MALL_RULE_STATUS_OPEN.equals(status) && !MallRuleEnum.MALL_TOTAL_INVEST.getCode().equals(scene)) {
			MallPointsRuleExample example = new MallPointsRuleExample();
			Criteria criteria = example.createCriteria();
			criteria.andGetSceneEqualTo(scene).andStatusEqualTo(status);
			// 如果是编辑操作，排除自身
			if (req.getRuleId() != null) {
				criteria.andIdNotEqualTo(req.getRuleId());
			}
			List<MallPointsRule> list = mallPointsRuleMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(list)) {
				for (MallPointsRule mallPointsRule : list) {
					if (!(triggerTimeStart.compareTo(DateUtil.format(mallPointsRule.getTriggerTimeEnd())) > 0 ||
							triggerTimeEnd.compareTo(DateUtil.format(mallPointsRule.getTriggerTimeStart())) < 0)) {
						return checkParamsLegal;						
					}
				}
			}
		}
		// 累计加入产品积分奖励进行金额校验
		if (Constants.MALL_RULE_STATUS_OPEN.equals(status) && MallRuleEnum.MALL_TOTAL_INVEST.getCode().equals(scene)) {
			MallPointsRuleExample example = new MallPointsRuleExample();
			Criteria criteria = example.createCriteria();
			criteria.andGetSceneEqualTo(scene).andStatusEqualTo(status);
			// 如果是编辑操作，排除自身
			if (req.getRuleId() != null) {
				criteria.andIdNotEqualTo(req.getRuleId());
			}
			List<MallPointsRule> list = mallPointsRuleMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(list)) {
				for (MallPointsRule mallPointsRule : list) {
					if (triggerTimeStart.compareTo(DateUtil.format(mallPointsRule.getTriggerTimeEnd())) > 0 ||
							triggerTimeEnd.compareTo(DateUtil.format(mallPointsRule.getTriggerTimeStart())) < 0) {
						continue;
					} else {
						MallPointsRuleDetail mallPointsRuleEndDetail = this.findByRuleId(mallPointsRule.getId(), MallRuleDetailEnum.MALL_INVEST_AMOUNT_END.getCode());
						if(mallPointsRuleEndDetail != null) {		
							if (MoneyUtil.subtract(req.getRuleValueInvestAmountBegin(), mallPointsRuleEndDetail.getRuleValue()).doubleValue() >= 0) {
								continue;
							}
						}
						MallPointsRuleDetail MallPointsRuleBeginDetail = this.findByRuleId(mallPointsRule.getId(), MallRuleDetailEnum.MALL_INVEST_AMOUNT_BEGIN.getCode());
						if (MallPointsRuleBeginDetail != null) {				
							if (MoneyUtil.subtract(req.getRuleValueInvestAmountEnd(), MallPointsRuleBeginDetail.getRuleValue()).doubleValue() <= 0) {
								continue;
							} else {
								return checkParamsLegal;
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 是否简单规则(成功注册、成功开通存管、成功首次加入产品、成功完成风险测评、用户生日奖励定义为简单规则，
	 * 只涉及积分规则表，其他规则 还涉及积分规则详情表)
	 * @param scene
	 * @return
	 */
	private Boolean isSimpleRule(String scene) {
		Boolean isSimpleRule = false;
		if (MallRuleEnum.MALL_REGISTER.getCode().equals(scene) || MallRuleEnum.MALL_OPEN_DEPOSIT.getCode().equals(scene) ||
				MallRuleEnum.MALL_FINISH_RISK_ASSESSMENT.getCode().equals(scene) || MallRuleEnum.MALL_FIRST_INVEST.getCode().equals(scene)||
				MallRuleEnum.MALL_BIRTHDAY.getCode().equals(scene)) {
			isSimpleRule = true;
		}
		return isSimpleRule;
	}
	
	/**
	 * 初始化积分设置规则信息
	 * @param userId
	 * @param triggerStartTime
	 * @param triggerEndTime
	 * @return
	 */
	private MallPointsRule fillMallPointsRule(Integer userId, String triggerStartTime, String triggerEndTime) {
		MallPointsRule mallPointsRule = new MallPointsRule();
		mallPointsRule.setCreateTime(new Date());
		mallPointsRule.setUpdateTime(new Date());
		mallPointsRule.setCreator(userId);
		mallPointsRule.setLastOperator(userId);
		mallPointsRule.setTriggerTimeStart(DateUtil.parseDateTime(triggerStartTime));
		mallPointsRule.setTriggerTimeEnd(DateUtil.parseDateTime(triggerEndTime));
		return mallPointsRule;
	}

	@Override
	public List<MallPointsRule> findPointsRuleList(String status, int pageNum, int numPerPage) {
		Integer position = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
		List<MallPointsRule> list = mallPointsRuleMapper.findPointsRuleList(status, position, numPerPage);
        return CollectionUtils.isEmpty(list) ? null : list;
	}

	@Override
	public Boolean updateStatus(String status, Integer ruleId) {
		Boolean isSuccess = false;
		int count = 0;
		if (StringUtil.isNotEmpty(status) && (Constants.MALL_RULE_STATUS_OPEN.equals(status) ||
				Constants.MALL_RULE_STATUS_CLOSE.equals(status) || Constants.MALL_RULE_STATUS_DELETED.equals(status))) {
			MallPointsRule mallPointsRule = this.findById(ruleId);
			if (mallPointsRule != null) {
				mallPointsRule.setStatus(status);
				mallPointsRule.setUpdateTime(new Date());
				count = mallPointsRuleMapper.updateByPrimaryKeySelective(mallPointsRule);
			}
		}
		if (count > 0) {
			isSuccess = true;
		}
		return isSuccess;
	}

	@Override
	public MallPointsRule findById(Integer id) {
		MallPointsRuleExample example = new MallPointsRuleExample();
		example.createCriteria().andIdEqualTo(id);
		List<MallPointsRule> list = mallPointsRuleMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Boolean updateMallPointRule(MallPointsRuleDTO req) {
		Boolean isSuccess = false;
		try {
			String scene = req.getGetScene();
			MallPointsRule mallPointsRule = this.findById(req.getRuleId());
			// 校验参数(开启状态的时间不能重复，累计加入产品积分奖励进行金额校验) 
			if (!checkPointRuleParams(req)) {
				return isSuccess;
			}
			
			mallPointsRule.setStatus(req.getStatus());
			mallPointsRule.setTriggerTimeStart(DateUtil.parseDateTime(req.getTriggerTimeStart()));
			mallPointsRule.setTriggerTimeEnd(DateUtil.parseDateTime(req.getTriggerTimeEnd()));
			mallPointsRule.setUpdateTime(new Date());
			mallPointsRule.setLastOperator(req.getOpUserId());
			if (isSimpleRule(scene)) {
				mallPointsRule.setPoints(req.getPoints() == null? 0L : req.getPoints().longValue());
				mallPointsRuleMapper.updateByPrimaryKeySelective(mallPointsRule);
				
				// 加入产品：
			} else if (MallRuleEnum.MALL_INVEST.getCode().equals(scene)) {
				mallPointsRuleMapper.updateByPrimaryKeySelective(mallPointsRule);
				MallPointsRuleDetail investRuleDetail = this.findByRuleId(req.getRuleId(), MallRuleDetailEnum.MALL_EXCHANGE_RATE.getCode());
				if (investRuleDetail != null) {
					investRuleDetail.setRuleValue(req.getRuleValueExchangeRate());
					investRuleDetail.setUpdateTime(new Date());
					mallPointsRuleDetailMapper.updateByPrimaryKeySelective(investRuleDetail);
				}
				
				// 累计加入产品：
			} else if (MallRuleEnum.MALL_TOTAL_INVEST.getCode().equals(scene)) {
				mallPointsRule.setPoints(StringUtil.isEmpty(req.getRuleValueTotalInvest())? 0L : Long.valueOf(req.getRuleValueTotalInvest()));
				mallPointsRuleMapper.updateByPrimaryKeySelective(mallPointsRule);
				MallPointsRuleDetail totalInvestRuleDetailBegin = this.findByRuleId(req.getRuleId(), MallRuleDetailEnum.MALL_INVEST_AMOUNT_BEGIN.getCode());
				if (totalInvestRuleDetailBegin != null) { 
					totalInvestRuleDetailBegin.setRuleValue(req.getRuleValueInvestAmountBegin());
					totalInvestRuleDetailBegin.setUpdateTime(new Date());
					mallPointsRuleDetailMapper.updateByPrimaryKeySelective(totalInvestRuleDetailBegin);
				}
				mallPointsRuleMapper.updateByPrimaryKeySelective(mallPointsRule);
				MallPointsRuleDetail totalInvestRuleDetailEnd = this.findByRuleId(req.getRuleId(), MallRuleDetailEnum.MALL_INVEST_AMOUNT_END.getCode());
				if (totalInvestRuleDetailEnd != null) { 
					totalInvestRuleDetailEnd.setRuleValue(req.getRuleValueInvestAmountEnd());
					totalInvestRuleDetailEnd.setUpdateTime(new Date());
					mallPointsRuleDetailMapper.updateByPrimaryKeySelective(totalInvestRuleDetailEnd);
				}
				
				// 签到：
			} else if (MallRuleEnum.MALL_SIGN.getCode().equals(scene)) {
				mallPointsRuleMapper.updateByPrimaryKeySelective(mallPointsRule);
				saveMallPointsRuleDetail(req.getRuleId(), MallRuleDetailEnum.MALL_SIGN_INIT_POINT.getCode(), req.getRuleValueSignInitPoint());
				saveMallPointsRuleDetail(req.getRuleId(), MallRuleDetailEnum.MALL_SIGN_INCREMENT_POINT.getCode(), req.getRuleValueSignIncrementPoint());
				saveMallPointsRuleDetail(req.getRuleId(), MallRuleDetailEnum.MALL_SIGN_AWARD_POINT.getCode(), req.getRuleValueSignAwardPoint());

				// APP签到场景，积分规则详情
				saveMallPointsRuleDetail(req.getRuleId(), MallRuleDetailEnum.MALL_SIGN_INIT_POINT_APP.getCode(), req.getRuleValueSignInitPointApp());
				saveMallPointsRuleDetail(req.getRuleId(), MallRuleDetailEnum.MALL_SIGN_INCREMENT_POINT_APP.getCode(), req.getRuleValueSignIncrementPointApp());
				saveMallPointsRuleDetail(req.getRuleId(), MallRuleDetailEnum.MALL_SIGN_AWARD_POINT_APP.getCode(), req.getRuleValueSignAwardPointApp());
			}
			isSuccess = true;
		} catch (Exception e) {
			log.error("method updateMallPointRule exception:{}", e);
			isSuccess = false;
		}
		return isSuccess;
	}

	@Override
	public MallPointsRuleDetail findByRuleId(Integer ruleId, String ruleKey) {
		MallPointsRuleDetailExample example = new MallPointsRuleDetailExample();
		example.createCriteria().andRuleIdEqualTo(ruleId).andRuleKeyEqualTo(ruleKey);
		List<MallPointsRuleDetail> list = mallPointsRuleDetailMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PagerModelRspDTO<MallPointsRule> findPointsRuleList(String status, PagerReqDTO pagerReq) {
		PageHelper.startPage(pagerReq.getPageNum(), pagerReq.getNumPerPage());
        List<MallPointsRule> list = mallPointsRuleMapper.findPointsRuleList(status, pagerReq.getPageNum(), pagerReq.getNumPerPage());
        return new PagerModelRspDTO(pagerReq, list);
	}

	/**
	 * 保存规则属性值
	 * @param ruleId
	 * @param ruleKey
	 * @param ruleValue
	 */
	private void saveMallPointsRuleDetail(Integer ruleId, String ruleKey, String ruleValue) {
		MallPointsRuleDetail signInitRuleDetail = findByRuleId(ruleId, ruleKey);
		if (signInitRuleDetail != null) {
			signInitRuleDetail.setRuleValue(ruleValue);
			signInitRuleDetail.setUpdateTime(new Date());
			mallPointsRuleDetailMapper.updateByPrimaryKeySelective(signInitRuleDetail);
		} else {
			addMallPointsRuleDetail(ruleId, ruleKey, ruleValue);
		}
	}

	/**
	 * 新增规则属性值
	 * @param ruleId
	 * @param ruleKey
	 * @param ruleValue
	 */
	private void addMallPointsRuleDetail(Integer ruleId, String ruleKey, String ruleValue) {
		MallPointsRuleDetail signInitRuleDetail = new MallPointsRuleDetail();
		signInitRuleDetail.setRuleId(ruleId);
		signInitRuleDetail.setRuleKey(ruleKey);
		signInitRuleDetail.setRuleValue(ruleValue);
		signInitRuleDetail.setCreateTime(new Date());
		signInitRuleDetail.setUpdateTime(new Date());
		mallPointsRuleDetailMapper.insertSelective(signInitRuleDetail);
	}
	
}
