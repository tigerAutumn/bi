package com.pinting.mall.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.pinting.mall.enums.MallRuleDetailEnum;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.mall.dao.MallAccountJnlMapper;
import com.pinting.mall.dao.MallAccountMapper;
import com.pinting.mall.dao.MallBsSubAccountMapper;
import com.pinting.mall.dao.MallBsUserMapper;
import com.pinting.mall.dao.MallPointsIncomeMapper;
import com.pinting.mall.dao.MallPointsRuleDetailMapper;
import com.pinting.mall.dao.MallPointsRuleMapper;
import com.pinting.mall.dao.MallUserSignMapper;
import com.pinting.mall.enums.MallRuleEnum;
import com.pinting.mall.enums.PTMessageEnum;
import com.pinting.mall.enums.RedisLockEnum;
import com.pinting.mall.exception.PTMessageException;
import com.pinting.mall.model.MallAccount;
import com.pinting.mall.model.MallAccountExample;
import com.pinting.mall.model.MallAccountJnl;
import com.pinting.mall.model.MallAccountJnlExample;
import com.pinting.mall.model.MallBsUser;
import com.pinting.mall.model.MallPointsIncome;
import com.pinting.mall.model.MallPointsRule;
import com.pinting.mall.model.MallPointsRuleDetail;
import com.pinting.mall.model.MallPointsRuleDetailExample;
import com.pinting.mall.model.MallPointsRuleExample;
import com.pinting.mall.model.MallUserSign;
import com.pinting.mall.model.MallUserSignExample;
import com.pinting.mall.model.dto.MallIncomeResultInfo;
import com.pinting.mall.model.dto.MallPointsIncomeQueueDTO;
import com.pinting.mall.service.MallPointsAccountService;
import com.pinting.mall.service.MallPointsService;
import com.pinting.mall.service.MallSendWechatService;
import com.pinting.mall.service.MallSpecialJnlService;
import com.pinting.mall.util.Constants;

/**
 * 签到/投资/累投 按规则多次发放
 * 风测/开通存管/注册/首投按场景单次发放
 * */

@Service
public class MallPointsServiceImpl implements MallPointsService{

	private Logger logger = LoggerFactory.getLogger(MallPointsServiceImpl.class);
    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Autowired
    private MallPointsIncomeMapper pointsIncomeMapper;
    @Autowired
    private MallPointsRuleMapper pointsRuleMapper;
    @Autowired
    private MallPointsRuleDetailMapper pointsRuleDetailMapper;
    @Autowired
    private MallAccountMapper accountMapper;
    @Autowired
    private MallAccountJnlMapper accountJnlMapper;
    @Autowired
    private MallUserSignMapper userSignMapper;
    @Autowired
    private MallBsSubAccountMapper subAccountMapper;
    @Autowired
    private MallBsUserMapper mallBsUserMapper;

    @Autowired
    private MallPointsAccountService pAccountService;
    @Autowired
    private MallSpecialJnlService specialJnlService;
    @Autowired
    private MallSendWechatService sendWechatService;

	@Override
	public MallUserSign signPointsIn(MallPointsIncomeQueueDTO mallPointsDTO) {
		/**
		 * 1.用户UserId+同步锁
		 * 2.
		 *  排除VIP/代偿人
		 * 	循环规则列表:是否满足签到规则(状态+时间范围),
		 * 	校验签到行为,当天已发放则不继续发放
		 * 	满足条件则执行4,不满足更新mall_points_income记录表
		 * 	根据规则计算本次积分
		 * 3.事物开始
		 * 4.更新mall_account(积分账户不存在则开户),mall_points_income,mall_account_jnl
		 * 5.事物结束
		 * 6.释放同步锁
		 * 7.告警信息bs_special_jnl(技术)
		 * */
		MallUserSign userSignInfo = new MallUserSign();
		try {
			//1.积分发放业务锁
			jsClientDaoSupport.lock(RedisLockEnum.MALL_LOCK_GRANT_POINTS.getKey() + mallPointsDTO.getUserId());

			List<MallPointsRule> pointsRuleList = mallPointsRuleFactory(mallPointsDTO);

			if ( CollectionUtils.isEmpty(pointsRuleList) ) return null;
			//校验账户是否存在
			MallAccountExample accountExamp = new MallAccountExample();
			accountExamp.createCriteria().andUserIdEqualTo(mallPointsDTO.getUserId())
				.andAccountTypeEqualTo(Constants.MALL_ACCOUNT_ACCOUNT_TYPE_JSH)
				.andStatusEqualTo(Constants.MALL_ACCOUNT_STATUS_OPEN);
			List<MallAccount> accountList = accountMapper.selectByExample(accountExamp);
			MallAccount account = null;
			if( CollectionUtils.isEmpty(accountList) ) {
				account = pAccountService.openAccount(mallPointsDTO.getUserId());
			} else {
				account = accountList.get(0);
			}

			//(签到校验当天,其他校验AllHis)积分已发放则不继续发放
			MallUserSignExample userSignExmp = new MallUserSignExample();
			userSignExmp.createCriteria().andUserIdEqualTo(mallPointsDTO.getUserId());
			List<MallUserSign> userSignList = userSignMapper.selectByExample(userSignExmp);

			//非首次签到,校验积分是否重复发放
			if(!CollectionUtils.isEmpty(userSignList) &&
					DateUtil.formatYYYYMMDD(userSignList.get(0).getSignTime()).equals(DateUtil.formatYYYYMMDD(new Date()))) {
				MallPointsIncome pIncomeTmp = new MallPointsIncome();
				pIncomeTmp.setId(mallPointsDTO.getId());
				pIncomeTmp.setStatus(Constants.MALL_POINTS_INCOME_STATUS_CANCELLED);
				pIncomeTmp.setNote("积分重复发放");
				pIncomeTmp.setFinishTime(new Date());
				pIncomeTmp.setUpdateTime(new Date());
				pointsIncomeMapper.updateByPrimaryKeySelective(pIncomeTmp);
				return null;
			}

			long totalCalPoints = 0;
			for(MallPointsRule pRule : pointsRuleList) {

				long signInitPoint  = 0l; //规则-签到初始积分
				long signIncrePoint = 0l;//规则-签到递增积分
				long signAwardPoint = 0l;//规则-签到额外奖励积分

				MallPointsRuleDetailExample ruleDetailExmp4InitPoint = new MallPointsRuleDetailExample();
				ruleDetailExmp4InitPoint.createCriteria().andRuleIdEqualTo(pRule.getId());
				List<MallPointsRuleDetail> pointsRuleDetailList = pointsRuleDetailMapper.selectByExample(ruleDetailExmp4InitPoint);
				// 获取签到规则积分配置, 区分APP与PC/H5
				for (MallPointsRuleDetail ruleDetail : pointsRuleDetailList) {
					if (Constants.REQUEST_TERMINAL_ANDROID.equals(mallPointsDTO.getChannel()) || Constants.REQUEST_TERMINAL_IOS.equals(mallPointsDTO.getChannel())) {
						if (MallRuleDetailEnum.MALL_SIGN_INIT_POINT_APP.getCode().equals(ruleDetail.getRuleKey())) {
							signInitPoint = Long.parseLong(ruleDetail.getRuleValue());
						}
						if (MallRuleDetailEnum.MALL_SIGN_INCREMENT_POINT_APP.getCode().equals(ruleDetail.getRuleKey())) {
							signIncrePoint = Long.parseLong(ruleDetail.getRuleValue());
						}
						if (MallRuleDetailEnum.MALL_SIGN_AWARD_POINT_APP.getCode().equals(ruleDetail.getRuleKey())) {
							signAwardPoint = Long.parseLong(ruleDetail.getRuleValue());
						}
					} else {
						if (MallRuleDetailEnum.MALL_SIGN_INIT_POINT.getCode().equals(ruleDetail.getRuleKey())) {
							signInitPoint = Long.parseLong(ruleDetail.getRuleValue());
						}
						if (MallRuleDetailEnum.MALL_SIGN_INCREMENT_POINT.getCode().equals(ruleDetail.getRuleKey())) {
							signIncrePoint = Long.parseLong(ruleDetail.getRuleValue());
						}
						if (MallRuleDetailEnum.MALL_SIGN_AWARD_POINT.getCode().equals(ruleDetail.getRuleKey())) {
							signAwardPoint = Long.parseLong(ruleDetail.getRuleValue());
						}
					}
				}

				logger.info("签到初始积分=["+signInitPoint+"]签到递增积分=["+signIncrePoint+"]签到额外奖励积分=["+signAwardPoint+"]");

				long latestPoints = 0; 	// 上次获得积分
				long calPoints = 0; 	// 本次积分
				int signDays = 0; 		// 累签天数

				Date latestSignTime = new Date();
				// 积分签到表记录不存在 , 则为首次签到
				if( CollectionUtils.isEmpty( userSignList ) ) {
					calPoints = signInitPoint;
					logger.info("用户"+mallPointsDTO.getUserId()+"首次签到.....积分签到表初始化.....本次获得积分=["+calPoints+"]");
					MallUserSign userSignTmp = new MallUserSign();
					userSignTmp.setUserId(mallPointsDTO.getUserId());
					userSignTmp.setSignTime(new Date());
					userSignTmp.setSignDays( 1 );
					userSignTmp.setSignPoints( calPoints ); //本次积分
					userSignTmp.setUpdateTime(new Date());
					userSignTmp.setCreateTime(new Date());
					userSignMapper.insert(userSignTmp);
				} else {
					// 获取上次获得积分、上次签到时间 、累签天数
					latestSignTime = userSignList.get(0).getSignTime();
					latestPoints = userSignList.get(0).getSignPoints();
					//判断是否累签 if diffDays > 1 则累签中断,累计签约天数从0计, 上次积分从初始积分计;
					int diffDays = DateUtil.getDiffeDay(new Date(), latestSignTime);
					signDays = diffDays > 1 ? 1 : userSignList.get(0).getSignDays() + 1;

					if( signDays % 7 == 0 ) { //额外积分 + 隔日积分
						calPoints = MoneyUtil.add( latestPoints ,MoneyUtil.add(signAwardPoint, signIncrePoint).longValue() ).longValue();
					} else if( signDays % 7 == 1 ) { //初始积分
						calPoints = signInitPoint;
					} else {
						calPoints = MoneyUtil.add( latestPoints , signIncrePoint ).longValue();
					}
					logger.info("用户"+mallPointsDTO.getUserId()+"签到,上次签到时间"+latestSignTime+"连续签到"+signDays+"天,.....本次获得积分=["+calPoints+"]");
					MallUserSign userSignTmp = new MallUserSign();
					userSignTmp.setId(userSignList.get(0).getId());
					userSignTmp.setSignTime(new Date());
					userSignTmp.setLatestTime(latestSignTime);
					userSignTmp.setSignDays(signDays);
					userSignTmp.setSignPoints(calPoints);
					userSignTmp.setLatestPoints(latestPoints);
					userSignTmp.setSignDays(signDays);
					userSignTmp.setUpdateTime(new Date());
					userSignMapper.updateByPrimaryKeySelective(userSignTmp);
				}
				//4.更新积分
				MallIncomeResultInfo resultInfo = new MallIncomeResultInfo();
				resultInfo.setAcctId(account.getId());
				resultInfo.setIncomeId(mallPointsDTO.getId());
				resultInfo.setRuleId(pRule.getId());
				resultInfo.setPoints(calPoints);
				resultInfo.setTransType(mallPointsDTO.getTransType());
				//更新积分(事物)
				MallAccountJnl mallAccountJnl = pAccountService.grantsPointsAccount( resultInfo );
				totalCalPoints = MoneyUtil.add(totalCalPoints, calPoints).longValue();
				//签到获取积分微信消息推送
				pointsDistribution(mallPointsDTO.getUserId(), mallAccountJnl, MallRuleEnum.MALL_SIGN.getWxSceneTypeDesc());
			}

			MallPointsIncome pointsIncomeTmp = new MallPointsIncome();
        	pointsIncomeTmp.setId(mallPointsDTO.getId());
        	pointsIncomeTmp.setPoints( totalCalPoints );
        	pointsIncomeTmp.setStatus(Constants.MALL_POINTS_INCOME_STATUS_FINISHED);
        	pointsIncomeTmp.setFinishTime(new Date());
        	pointsIncomeTmp.setUpdateTime(new Date());
        	pointsIncomeMapper.updateByPrimaryKeySelective(pointsIncomeTmp);

			//返回用户签到信息
			userSignList = userSignMapper.selectByExample(userSignExmp);
			userSignInfo = userSignList.get(0);
		} catch (Exception  e) {
			logger.error( MallRuleEnum.getEnumByCode(mallPointsDTO.getTransType()).getMessage()+"积分发放异常:", e);
			specialJnlService.warn4FailNoSMS( 0d , "mall_points_income.id="+mallPointsDTO.getId()+","+e.getMessage(), mallPointsDTO.getId()+"",
					MallRuleEnum.getEnumByCode(mallPointsDTO.getTransType()).getCode());
		} finally {
			jsClientDaoSupport.unlock(RedisLockEnum.MALL_LOCK_GRANT_POINTS.getKey() + mallPointsDTO.getUserId() );
		}
		return userSignInfo;
	}

	@Override
	public void registerPointsIn(MallPointsIncomeQueueDTO mallPointsDTO) {
		/**
		 * 1.业务同步锁
		 * 2.排除VIP/代偿人
		 * 	  循环规则列表 注册规则是否有效(状态+时间范围)
		 *   校验注册行为,已发放一次则不继续发放
		 * 	   满足条件则执行4,不满足更新mall_points_income记录表
		 * 	 根据注册规则计算本次得分
		 * 3.事物开始
		 * 4.更新mall_account(积分账户不存在则开户),mall_points_income,mall_account_jnl
		 * 5.事物结束
		 * 6.释放同步锁
		 * 7.告警信息bs_special_jnl(技术)
		 * */
		try {
			//1.积分发放业务锁
			jsClientDaoSupport.lock( RedisLockEnum.MALL_LOCK_GRANT_POINTS.getKey()  + mallPointsDTO.getUserId() );

			List<MallPointsRule> pointsRuleList = mallPointsRuleFactory(mallPointsDTO);

			if ( CollectionUtils.isEmpty(pointsRuleList) ) return;
			//校验账户是否存在
			MallAccountExample accountExamp = new MallAccountExample();
			accountExamp.createCriteria().andUserIdEqualTo(mallPointsDTO.getUserId())
				.andAccountTypeEqualTo(Constants.MALL_ACCOUNT_ACCOUNT_TYPE_JSH)
				.andStatusEqualTo(Constants.MALL_ACCOUNT_STATUS_OPEN);
			List<MallAccount> accountList = accountMapper.selectByExample(accountExamp);
			MallAccount account = CollectionUtils.isEmpty(accountList) == true ? pAccountService.openAccount(mallPointsDTO.getUserId()) : accountList.get(0);

			//(签到校验当天,其他校验AllHis)积分已发放则不继续发放
			MallAccountJnlExample accountJnlExm = new MallAccountJnlExample();
			accountJnlExm.createCriteria().andUserIdEqualTo(mallPointsDTO.getUserId())
				.andTransTypeEqualTo(mallPointsDTO.getTransType());
			List<MallAccountJnl> accountJnlList = accountJnlMapper.selectByExample(accountJnlExm);

			if( !CollectionUtils.isEmpty(accountJnlList) ) {
				MallPointsIncome pIncomeTmp = new MallPointsIncome();
				pIncomeTmp.setId(mallPointsDTO.getId());
				pIncomeTmp.setStatus(Constants.MALL_POINTS_INCOME_STATUS_CANCELLED);
				pIncomeTmp.setNote("积分重复发放");
				pIncomeTmp.setFinishTime(new Date());
				pIncomeTmp.setUpdateTime(new Date());
				pointsIncomeMapper.updateByPrimaryKeySelective(pIncomeTmp);
				return;
			}
			long totalCalPoints = 0;
			//4.更新积分
			for( MallPointsRule pRule : pointsRuleList ) {
				MallIncomeResultInfo resultInfo = new MallIncomeResultInfo();
				resultInfo.setAcctId(account.getId());
				resultInfo.setIncomeId(mallPointsDTO.getId());
				resultInfo.setRuleId(pRule.getId());
				resultInfo.setPoints(pRule.getPoints());
				resultInfo.setTransType(mallPointsDTO.getTransType());
				//更新积分(事物)
				MallAccountJnl mallAccountJnl = pAccountService.grantsPointsAccount( resultInfo );
				totalCalPoints = MoneyUtil.add(totalCalPoints, pRule.getPoints()).longValue();
				//注册获取积分微信消息推送
				pointsDistribution(mallPointsDTO.getUserId(), mallAccountJnl, MallRuleEnum.MALL_REGISTER.getWxSceneTypeDesc());
			}
			MallPointsIncome pointsIncomeTmp = new MallPointsIncome();
        	pointsIncomeTmp.setId(mallPointsDTO.getId());
        	pointsIncomeTmp.setPoints( totalCalPoints );
        	pointsIncomeTmp.setStatus(Constants.MALL_POINTS_INCOME_STATUS_FINISHED);
        	pointsIncomeTmp.setFinishTime(new Date());
        	pointsIncomeTmp.setUpdateTime(new Date());
        	pointsIncomeMapper.updateByPrimaryKeySelective(pointsIncomeTmp);
		} catch (Exception  e) {
			logger.error( MallRuleEnum.getEnumByCode(mallPointsDTO.getTransType()).getMessage()+"积分发放异常:", e);
			specialJnlService.warn4FailNoSMS( 0d , "mall_points_income.id="+mallPointsDTO.getId()+","+e.getMessage(), mallPointsDTO.getId()+"",
					MallRuleEnum.getEnumByCode(mallPointsDTO.getTransType()).getCode());
		} finally {
			jsClientDaoSupport.unlock( RedisLockEnum.MALL_LOCK_GRANT_POINTS.getKey()  + mallPointsDTO.getUserId());
		}
	}

	@Override
	public void riskAssessmentPointsIn(MallPointsIncomeQueueDTO mallPointsDTO) {
		/**
		 * 1.业务同步锁
		 * 2.排除VIP/代偿人
		 * 	  风险测评规则是否有效(状态+时间范围)
		 *   校验风险测评行为,已发放一次则不继续发放
		 * 	   满足条件则执行4,不满足更新mall_points_income记录表
		 * 	  根据风险测评规则计算本次得分
		 * 3.事物开始
		 * 4.更新mall_account(积分账户不存在则开户),mall_points_income,mall_account_jnl
		 * 5.事物结束
		 * 6.释放同步锁
		 * 7.告警信息bs_special_jnl(技术)
		 * */
		try {
			//1.积分发放业务锁
			jsClientDaoSupport.lock( RedisLockEnum.MALL_LOCK_GRANT_POINTS.getKey()  + mallPointsDTO.getUserId());

			List<MallPointsRule> pointsRuleList = mallPointsRuleFactory(mallPointsDTO);

			if ( CollectionUtils.isEmpty(pointsRuleList) ) return;
			//校验账户是否存在
			MallAccountExample accountExamp = new MallAccountExample();
			accountExamp.createCriteria().andUserIdEqualTo(mallPointsDTO.getUserId())
				.andAccountTypeEqualTo(Constants.MALL_ACCOUNT_ACCOUNT_TYPE_JSH)
				.andStatusEqualTo(Constants.MALL_ACCOUNT_STATUS_OPEN);
			List<MallAccount> accountList = accountMapper.selectByExample(accountExamp);
			MallAccount account = null;
			if( CollectionUtils.isEmpty(accountList) ) {
				account = pAccountService.openAccount(mallPointsDTO.getUserId());
			} else {
				account = accountList.get(0);
			}

			//(签到校验当天,其他校验AllHis)积分已发放则不继续发放
			MallAccountJnlExample accountJnlExm = new MallAccountJnlExample();
			accountJnlExm.createCriteria().andUserIdEqualTo(mallPointsDTO.getUserId())
				.andTransTypeEqualTo(mallPointsDTO.getTransType());
			List<MallAccountJnl> accountJnlList = accountJnlMapper.selectByExample(accountJnlExm);

			if( !CollectionUtils.isEmpty(accountJnlList) ) {
				MallPointsIncome pIncomeTmp = new MallPointsIncome();
				pIncomeTmp.setId(mallPointsDTO.getId());
				pIncomeTmp.setStatus(Constants.MALL_POINTS_INCOME_STATUS_CANCELLED);
				pIncomeTmp.setNote("积分重复发放");
				pIncomeTmp.setFinishTime(new Date());
				pIncomeTmp.setUpdateTime(new Date());
				pointsIncomeMapper.updateByPrimaryKeySelective(pIncomeTmp);
				return;
			}

			long totalCalPoints = 0;
			//4.更新积分
			for( MallPointsRule pRule : pointsRuleList ) {

				MallIncomeResultInfo resultInfo = new MallIncomeResultInfo();
				resultInfo.setAcctId(account.getId());
				resultInfo.setIncomeId(mallPointsDTO.getId());
				resultInfo.setRuleId(pRule.getId());
				resultInfo.setPoints(pRule.getPoints());
				resultInfo.setTransType(mallPointsDTO.getTransType());
				//更新积分(事物)
				MallAccountJnl mallAccountJnl = pAccountService.grantsPointsAccount( resultInfo );
				totalCalPoints = MoneyUtil.add(totalCalPoints, pRule.getPoints()).longValue();
				//完成风险测评获取积分微信消息推送
				pointsDistribution(mallPointsDTO.getUserId(), mallAccountJnl, MallRuleEnum.MALL_FINISH_RISK_ASSESSMENT.getWxSceneTypeDesc());
			}
			//更新mall_points_income记录
        	MallPointsIncome pointsIncomeTmp = new MallPointsIncome();
        	pointsIncomeTmp.setId(mallPointsDTO.getId());
        	pointsIncomeTmp.setPoints( totalCalPoints );
        	pointsIncomeTmp.setStatus(Constants.MALL_POINTS_INCOME_STATUS_FINISHED);
        	pointsIncomeTmp.setFinishTime(new Date());
        	pointsIncomeTmp.setUpdateTime(new Date());
        	pointsIncomeMapper.updateByPrimaryKeySelective(pointsIncomeTmp);
		} catch (Exception  e) {
			logger.error( MallRuleEnum.getEnumByCode(mallPointsDTO.getTransType()).getMessage()+"积分发放异常:", e);
			specialJnlService.warn4FailNoSMS( 0d , "mall_points_income.id="+mallPointsDTO.getId()+","+e.getMessage(), mallPointsDTO.getId()+"",
					MallRuleEnum.getEnumByCode(mallPointsDTO.getTransType()).getCode());
		} finally {
			jsClientDaoSupport.unlock(RedisLockEnum.MALL_LOCK_GRANT_POINTS.getKey()  + mallPointsDTO.getUserId());
		}
	}

	@Override
	public void openDepositePointsIn(MallPointsIncomeQueueDTO mallPointsDTO) {
		/**
		 * 1.业务同步锁
		 * 2.排除VIP/代偿人
		 * 	   开通存管规则是否有效(状态+时间范围)
		 *   校验开通存管行为,已发放一次则不继续发放
		 * 	   满足条件则执行4,不满足更新mall_points_income记录表
		 * 	  根据风险测评规则计算本次得分
		 * 3.事物开始
		 * 4.更新mall_account(积分账户不存在则开户),mall_points_income,mall_account_jnl
		 * 5.事物结束
		 * 6.释放同步锁
		 * 7.告警信息bs_special_jnl(技术)
		 * */
		try {
			//1.积分发放业务锁
			jsClientDaoSupport.lock( RedisLockEnum.MALL_LOCK_GRANT_POINTS.getKey() + mallPointsDTO.getUserId() );

			List<MallPointsRule> pointsRuleList = mallPointsRuleFactory(mallPointsDTO);

			if ( CollectionUtils.isEmpty(pointsRuleList) ) return;
			//校验账户是否存在
			MallAccountExample accountExamp = new MallAccountExample();
			accountExamp.createCriteria().andUserIdEqualTo(mallPointsDTO.getUserId())
				.andAccountTypeEqualTo(Constants.MALL_ACCOUNT_ACCOUNT_TYPE_JSH)
				.andStatusEqualTo(Constants.MALL_ACCOUNT_STATUS_OPEN);
			List<MallAccount> accountList = accountMapper.selectByExample(accountExamp);
			MallAccount account = null;
			if( CollectionUtils.isEmpty(accountList) ) {
				account = pAccountService.openAccount(mallPointsDTO.getUserId());
			} else {
				account = accountList.get(0);
			}
			//(签到校验当天,其他校验AllHis)积分已发放则不继续发放
			MallAccountJnlExample accountJnlExm = new MallAccountJnlExample();
			accountJnlExm.createCriteria().andUserIdEqualTo(mallPointsDTO.getUserId())
				.andTransTypeEqualTo(mallPointsDTO.getTransType());
			List<MallAccountJnl> accountJnlList = accountJnlMapper.selectByExample(accountJnlExm);

			if( !CollectionUtils.isEmpty(accountJnlList) ) {
				MallPointsIncome pIncomeTmp = new MallPointsIncome();
				pIncomeTmp.setId(mallPointsDTO.getId());
				pIncomeTmp.setStatus(Constants.MALL_POINTS_INCOME_STATUS_CANCELLED);
				pIncomeTmp.setNote("积分重复发放");
				pIncomeTmp.setFinishTime(new Date());
				pIncomeTmp.setUpdateTime(new Date());
				pointsIncomeMapper.updateByPrimaryKeySelective(pIncomeTmp);
				return;
			}

			long totalCalPoints = 0;
			//4.更新积分
			for( MallPointsRule pRule : pointsRuleList ) {
				MallIncomeResultInfo resultInfo = new MallIncomeResultInfo();
				resultInfo.setAcctId(account.getId());
				resultInfo.setIncomeId(mallPointsDTO.getId());
				resultInfo.setRuleId(pRule.getId());
				resultInfo.setPoints(pRule.getPoints());
				resultInfo.setTransType(mallPointsDTO.getTransType());
				//更新积分(事物)
				MallAccountJnl mallAccountJnl = pAccountService.grantsPointsAccount( resultInfo );
				totalCalPoints = MoneyUtil.add(totalCalPoints, pRule.getPoints()).longValue();
				//开通存管获取积分微信消息推送
				pointsDistribution(mallPointsDTO.getUserId(), mallAccountJnl, MallRuleEnum.MALL_OPEN_DEPOSIT.getWxSceneTypeDesc());
			}
    		//更新mall_points_income记录
        	MallPointsIncome pointsIncomeTmp = new MallPointsIncome();
        	pointsIncomeTmp.setId(mallPointsDTO.getId());
        	pointsIncomeTmp.setPoints( totalCalPoints );
        	pointsIncomeTmp.setStatus(Constants.MALL_POINTS_INCOME_STATUS_FINISHED);
        	pointsIncomeTmp.setFinishTime(new Date());
        	pointsIncomeTmp.setUpdateTime(new Date());
        	pointsIncomeMapper.updateByPrimaryKeySelective(pointsIncomeTmp);
		} catch (Exception  e) {
			logger.error( MallRuleEnum.getEnumByCode(mallPointsDTO.getTransType()).getMessage()+"积分发放异常:", e);
			specialJnlService.warn4FailNoSMS( 0d , "mall_points_income.id="+mallPointsDTO.getId()+","+e.getMessage(), mallPointsDTO.getId()+"",
					MallRuleEnum.getEnumByCode(mallPointsDTO.getTransType()).getCode());
		} finally {
			jsClientDaoSupport.unlock(RedisLockEnum.MALL_LOCK_GRANT_POINTS.getKey()  + mallPointsDTO.getUserId() );
		}
	}

	@Override
	public void investPointsIn(MallPointsIncomeQueueDTO mallPointsDTO) {

		/**
		 * 1.业务同步锁 +userId
		 * 2.
		 *   排除VIP/代偿人
		 *   循环规则：投资规则是否有效(状态+时间范围)
		 *   校验投资行为,已发放一次则不继续发放
		 * 	   满足条件则执行4,不满足更新mall_points_income记录表
		 * 	  根据投资规则计算本次得分
		 * 	首次投资
		 * 		MALL_POINTS_RULE.get_scene='FIRST_INVEST' MALL_POINTS_RULE.points
		 *  非首次投资
		 *  	MALL_POINTS_RULE.get_scene='INVEST' MALL_POINTS_RULE_DETAIL.rule_value
		 *  累计投资
		 * 		MALL_POINTS_RULE.get_scene='TOTAL_INVEST' MALL_POINTS_RULE_DETAIL.rule_value
		 * 3.事物开始
		 * 4.更新mall_account(积分账户不存在则开户),mall_points_income,mall_account_jnl
		 * 5.事物结束
		 * 6.释放同步锁
		 * 7.告警信息bs_special_jnl(技术)
		 * */
		try {
			//1.积分发放业务锁+userId
			jsClientDaoSupport.lock( RedisLockEnum.MALL_LOCK_GRANT_POINTS.getKey() + mallPointsDTO.getUserId() );

			//排除VIP/代偿人
			List<String> newUserList = Arrays.asList(GlobEnvUtil.get("points_user_black_list").split(","));
			boolean isVipCompensator = newUserList.contains(String.valueOf(mallPointsDTO.getUserId()));
			if ( isVipCompensator ) {
				throw new PTMessageException(PTMessageEnum.NOT_CONDITION_FOUND, ",用户为代偿人/VIP,不允许发放积分");
			}

			//校验行为
			MallPointsIncome pointsIncomeVO = pointsIncomeMapper.selectByPrimaryKey(mallPointsDTO.getId());
			if( pointsIncomeVO == null || Constants.MALL_POINTS_INCOME_STATUS_FINISHED.equals(pointsIncomeVO.getStatus())
					|| Constants.MALL_POINTS_INCOME_STATUS_CANCELLED.equals(pointsIncomeVO.getStatus())) {
				throw new PTMessageException(PTMessageEnum.NOT_CONDITION_FOUND, MallRuleEnum.getEnumByCode(mallPointsDTO.getTransType()).getMessage()+"行为不存在");
			}

			//获取规则信息表
			MallPointsRuleExample pointsRuleExmp = new MallPointsRuleExample();
			pointsRuleExmp.createCriteria().andGetSceneEqualTo(mallPointsDTO.getTransType())
				.andStatusEqualTo(Constants.MALL_RULE_STATUS_OPEN)
				.andTriggerTimeStartLessThanOrEqualTo(pointsIncomeVO.getTransTime())
				.andTriggerTimeEndGreaterThanOrEqualTo(pointsIncomeVO.getTransTime());

			List<MallPointsRule> pointsRuleList = pointsRuleMapper.selectByExample(pointsRuleExmp);
			if( CollectionUtils.isEmpty(pointsRuleList) ) {
				MallPointsIncome pIncomeTmp = new MallPointsIncome();
				pIncomeTmp.setId(mallPointsDTO.getId());
				pIncomeTmp.setStatus(Constants.MALL_POINTS_INCOME_STATUS_CANCELLED);
				pIncomeTmp.setNote("规则信息不存在");
				pIncomeTmp.setFinishTime(new Date());
				pIncomeTmp.setUpdateTime(new Date());
				pointsIncomeMapper.updateByPrimaryKeySelective(pIncomeTmp);
				return;
			}

			//校验账户是否存在
			MallAccountExample accountExamp = new MallAccountExample();
			accountExamp.createCriteria().andUserIdEqualTo(mallPointsDTO.getUserId())
				.andAccountTypeEqualTo(Constants.MALL_ACCOUNT_ACCOUNT_TYPE_JSH)
				.andStatusEqualTo(Constants.MALL_ACCOUNT_STATUS_OPEN);
			List<MallAccount> accountList = accountMapper.selectByExample(accountExamp);

			MallAccount account = CollectionUtils.isEmpty(accountList) == true ? pAccountService.openAccount(mallPointsDTO.getUserId()) : accountList.get(0) ;

			long totalCalPoints = 0;
			boolean updRs = false;
			//4.更新积分
			for( MallPointsRule pRule : pointsRuleList ) {
				long calPoints = 0;
				if( MallRuleEnum.MALL_FIRST_INVEST.getCode().equals(pRule.getGetScene()) ) {

					//首笔投资 积分
					calPoints = pRule.getPoints();
					logger.info("投资规则:"+pRule.getId()+"首次投资发放积分"+calPoints);
					//(签到校验当天,其他校验AllHis)积分已发放则不继续发放
					MallAccountJnlExample accountJnlExm = new MallAccountJnlExample();
					accountJnlExm.createCriteria().andUserIdEqualTo(mallPointsDTO.getUserId())
						.andTransTypeEqualTo(mallPointsDTO.getTransType());
					List<MallAccountJnl> accountJnlList = accountJnlMapper.selectByExample(accountJnlExm);

					if( !CollectionUtils.isEmpty(accountJnlList) ) {
						logger.info("投资规则"+pRule.getId()+"首投积分已发放一次");
						continue;
					}
				} else if ( MallRuleEnum.MALL_INVEST.getCode().equals(pRule.getGetScene()) ) {
					//兑换比例
					MallPointsRuleDetailExample ruleDetailExamp4ExchangeRate = new MallPointsRuleDetailExample();
					ruleDetailExamp4ExchangeRate.createCriteria().andRuleIdEqualTo(pRule.getId()).andRuleKeyEqualTo(MallRuleDetailEnum.MALL_EXCHANGE_RATE.getCode());
					List<MallPointsRuleDetail> ruleDetail4ExchangeRate = pointsRuleDetailMapper.selectByExample(ruleDetailExamp4ExchangeRate);
					logger.info("投资规则"+pRule.getId()+"兑换比例:" + ruleDetail4ExchangeRate.get(0).getRuleValue());
					Double exchangeRate = Double.parseDouble( ruleDetail4ExchangeRate.get(0).getRuleValue() );
					//投资 用户所得积分(四舍五入)=用户获得的收益/兑换比例
					calPoints = MoneyUtil.divide(pointsIncomeVO.getInvestInterest(), exchangeRate, 0).longValue();
				} else if ( MallRuleEnum.MALL_TOTAL_INVEST.getCode().equals(pRule.getGetScene()) ) {
					//(签到校验当天,其他校验AllHis)积分已发放则不继续发放
					MallAccountJnlExample accountJnlExm = new MallAccountJnlExample();
					accountJnlExm.createCriteria().andUserIdEqualTo(mallPointsDTO.getUserId())
						.andRuleIdEqualTo(pRule.getId());
					List<MallAccountJnl> accountJnlList = accountJnlMapper.selectByExample(accountJnlExm);

					if( !CollectionUtils.isEmpty(accountJnlList) ) {
						logger.info("投资规则"+pRule.getId()+"累投积分已发放一次");
						continue;
					}
					//累计投资年化区间
					MallPointsRuleDetailExample ruleDetailExamp4InvestBegin = new MallPointsRuleDetailExample();
					ruleDetailExamp4InvestBegin.createCriteria().andRuleIdEqualTo(pRule.getId()).andRuleKeyEqualTo(MallRuleDetailEnum.MALL_INVEST_AMOUNT_BEGIN.getCode());
					List<MallPointsRuleDetail> ruleDetail4InvestBegin = pointsRuleDetailMapper.selectByExample(ruleDetailExamp4InvestBegin);

					MallPointsRuleDetailExample ruleDetailExamp4InvestEnd = new MallPointsRuleDetailExample();
					ruleDetailExamp4InvestEnd.createCriteria().andRuleIdEqualTo(pRule.getId()).andRuleKeyEqualTo(MallRuleDetailEnum.MALL_INVEST_AMOUNT_END.getCode());
					List<MallPointsRuleDetail> ruleDetail4InvestEnd = pointsRuleDetailMapper.selectByExample(ruleDetailExamp4InvestEnd);

					double points4InvestBegin = Double.parseDouble(ruleDetail4InvestBegin.get(0).getRuleValue());
					double points4InvestEnd = Double.parseDouble(ruleDetail4InvestEnd.get(0).getRuleValue());

					double investIncome = subAccountMapper.sumYearInvestByUserId(mallPointsDTO.getUserId(),
							pRule.getTriggerTimeStart(), pointsIncomeVO.getTransTime());
					//在规则累计投资年化区间内
					if( MoneyUtil.subtract(investIncome, points4InvestBegin).doubleValue() >= 0d
							&& MoneyUtil.subtract(investIncome, points4InvestEnd).doubleValue() < 0d ) {
						calPoints = pRule.getPoints();
						logger.info( "投资规则"+pRule.getId()+"累计年化收益:" + investIncome +",发放积分:"+calPoints);
					} else {
						logger.info("累计年化收益:"+investIncome+",不满足区间["+points4InvestBegin+","+points4InvestEnd+")");
						continue;
					}
				} else {
					continue;
				}

				MallIncomeResultInfo resultInfo = new MallIncomeResultInfo();
				resultInfo.setAcctId(account.getId());
				resultInfo.setIncomeId(mallPointsDTO.getId());
				resultInfo.setRuleId(pRule.getId());
				resultInfo.setPoints(calPoints);
				resultInfo.setTransType(mallPointsDTO.getTransType());
				//更新积分(事物)
				MallAccountJnl mallAccountJnl = pAccountService.grantsPointsAccount( resultInfo );
				updRs = true; // 更新成功 ,设置为true;
				totalCalPoints = MoneyUtil.add(calPoints,totalCalPoints).longValue();
				//投资获取积分微信消息推送
				pointsDistribution(mallPointsDTO.getUserId(), mallAccountJnl, MallRuleEnum.MALL_INVEST.getWxSceneTypeDesc());
			}

			//更新mall_points_income记录
	    	MallPointsIncome pointsIncomeTmp = new MallPointsIncome();
	    	pointsIncomeTmp.setId(pointsIncomeVO.getId());
	    	pointsIncomeTmp.setPoints( totalCalPoints );
	    	pointsIncomeTmp.setStatus( updRs ? Constants.MALL_POINTS_INCOME_STATUS_FINISHED : Constants.MALL_POINTS_INCOME_STATUS_CANCELLED );
	    	pointsIncomeTmp.setFinishTime(new Date());
	    	pointsIncomeTmp.setUpdateTime(new Date());
	    	pointsIncomeMapper.updateByPrimaryKeySelective(pointsIncomeTmp);
		} catch (Exception  e) {
			logger.error( MallRuleEnum.getEnumByCode(mallPointsDTO.getTransType()).getMessage()+"积分发放异常:", e);
			specialJnlService.warn4FailNoSMS( 0d , "mall_points_income.id="+mallPointsDTO.getId()+","+e.getMessage(), mallPointsDTO.getId()+"",
					MallRuleEnum.getEnumByCode(mallPointsDTO.getTransType()).getCode());
		} finally {
			jsClientDaoSupport.unlock(RedisLockEnum.MALL_LOCK_GRANT_POINTS.getKey() + + mallPointsDTO.getUserId() );
		}

	}

	@Override
	public List<MallPointsRule> mallPointsRuleFactory(MallPointsIncomeQueueDTO mallPointsDTO) {

		//排除VIP/代偿人
		List<String> newUserList = Arrays.asList(GlobEnvUtil.get("points_user_black_list").split(","));
		boolean isVipCompensator = newUserList.contains(String.valueOf(mallPointsDTO.getUserId()));
		if ( isVipCompensator ) {
			throw new PTMessageException(PTMessageEnum.NOT_CONDITION_FOUND, ",用户为代偿人/VIP,不允许发放积分");
		}

		//校验行为
		MallPointsIncome pointsIncomeVO = pointsIncomeMapper.selectByPrimaryKey(mallPointsDTO.getId());
		if( pointsIncomeVO == null
				|| Constants.MALL_POINTS_INCOME_STATUS_FINISHED.equals(pointsIncomeVO.getStatus())
				|| Constants.MALL_POINTS_INCOME_STATUS_CANCELLED.equals(pointsIncomeVO.getStatus())) {
			throw new PTMessageException(PTMessageEnum.NOT_CONDITION_FOUND, ","+MallRuleEnum.getEnumByCode(mallPointsDTO.getTransType()).getMessage()+"行为不存在");
		}

		//获取规则信息表
		MallPointsRuleExample pointsRule = new MallPointsRuleExample();
		pointsRule.createCriteria().andGetSceneEqualTo(mallPointsDTO.getTransType())
			.andStatusEqualTo(Constants.MALL_RULE_STATUS_OPEN)
			.andTriggerTimeStartLessThanOrEqualTo(pointsIncomeVO.getTransTime())
			.andTriggerTimeEndGreaterThanOrEqualTo(pointsIncomeVO.getTransTime());

		List<MallPointsRule> pointsRuleList = pointsRuleMapper.selectByExample(pointsRule);

		if( CollectionUtils.isEmpty(pointsRuleList) ) {
			MallPointsIncome pIncomeTmp = new MallPointsIncome();
			pIncomeTmp.setId(mallPointsDTO.getId());
			pIncomeTmp.setStatus(Constants.MALL_POINTS_INCOME_STATUS_CANCELLED);
			pIncomeTmp.setNote("规则信息不存在");
			pIncomeTmp.setFinishTime(new Date());
			pIncomeTmp.setUpdateTime(new Date());
			pointsIncomeMapper.updateByPrimaryKeySelective(pIncomeTmp);
			return null;
		}
		return pointsRuleList;
	}
	
	/**
	 * 积分获取微信发送
	 * @param userId
	 * @param mallAccountJnl
	 * @param transType
	 */
	private void pointsDistribution(Integer userId, MallAccountJnl mallAccountJnl, String transType) {
		MallBsUser user = mallBsUserMapper.selectByPrimaryKey(userId);
		String userName = "";
		if(user.getUserName()!=null) {
			userName = user.getUserName().substring(0, 1);
			for(int i = 0;i<user.getUserName().length()-1;i++) {
				userName  = userName +"*";
			}
		}
		sendWechatService.mallPointsDistribution(userId, userName, mallAccountJnl.getCreateTime(), transType, mallAccountJnl.getPoints(), mallAccountJnl.getAfterBalance());
	}
	
}