package com.pinting.schedule.dayend.task;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.mall.dao.MallAccountJnlMapper;
import com.pinting.mall.dao.MallAccountMapper;
import com.pinting.mall.dao.MallBsUserMapper;
import com.pinting.mall.dao.MallPointsIncomeMapper;
import com.pinting.mall.dao.MallPointsRuleMapper;
import com.pinting.mall.enums.MallRuleEnum;
import com.pinting.mall.enums.RedisLockEnum;
import com.pinting.mall.model.MallAccount;
import com.pinting.mall.model.MallAccountExample;
import com.pinting.mall.model.MallBsUser;
import com.pinting.mall.model.MallPointsIncome;
import com.pinting.mall.model.MallPointsRule;
import com.pinting.mall.model.MallPointsRuleExample;
import com.pinting.mall.model.dto.MallIncomeResultInfo;
import com.pinting.mall.service.MallPointsAccountService;
import com.pinting.mall.util.Constants;

/**
 * 生日积分发放
 * @project schedule
 * @author bianyatian
 * @2018-8-13 下午2:00:28
 */
@Service
public class BirthdayPointsTask {
	private Logger log = LoggerFactory.getLogger(BirthdayPointsTask.class);
	
	private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	
	@Autowired
    private MallPointsRuleMapper mallPointsRuleMapper;
	@Autowired
	private MallBsUserMapper mallBsUserMapper;
	@Autowired
	private MallPointsIncomeMapper mallPointsIncomeMapper;
	@Autowired
	private MallAccountMapper accountMapper;
	@Autowired
	private MallAccountJnlMapper accountJnlMapper;
	@Autowired
    private MallPointsAccountService pAccountService;
	
	public void execute(){
		try {
			log.info(">>>>>start【生日福利积分】发放定时任务 start<<<<<");
			/**
			 * 1、查询mall_points_rule表符合规则的数据列表
			 * 2、若存在多条只取第一条去发放积分
			 * 3、查询未发送生日积分的且当日生日的用户数据列表（排除VIP和代偿用户）
			 * 4、循环用户列表，发放积分：
			 * 		4.1、mall_points_income新增数据
			 * 		4.2、检查是否已开户，开户，记开户流水
			 * 		4.3、积分户新增，记流水
			 */
			//查询是否存在生日场景规则
			MallPointsRuleExample pointsRule = new MallPointsRuleExample();
			pointsRule.createCriteria().andGetSceneEqualTo(MallRuleEnum.MALL_BIRTHDAY.getCode()) 
				.andStatusEqualTo(Constants.MALL_RULE_STATUS_OPEN) 
				.andTriggerTimeStartLessThanOrEqualTo(new Date()) 
				.andTriggerTimeEndGreaterThanOrEqualTo(new Date());
			List<MallPointsRule> pointsRuleList = mallPointsRuleMapper.selectByExample(pointsRule);
			if(CollectionUtils.isNotEmpty(pointsRuleList)){
				MallPointsRule birthdayRule = pointsRuleList.get(0);
				//查询VIP/代偿人
				List<String> vipCompUserList = Arrays.asList(GlobEnvUtil.get("points_user_black_list").split(","));
				String birthday = DateUtil.formatDateTime(new Date(), "MMdd");
				List<MallBsUser> userList = mallBsUserMapper.getBirthdayUserList(birthday, vipCompUserList);
				if(CollectionUtils.isNotEmpty(userList)){
					for (MallBsUser mallBsUser : userList) {
						sendPoints(mallBsUser,birthdayRule);
					}
				}
			}
			log.info(">>>>>end【生日福利积分】发放定时任务 end<<<<<");
		} catch (Exception e) {
			log.error(">>>>>end【生日福利积分】发放异常 end<<<<<",e);
		}
	}

	/**
	 * 发放积分
	 * @author bianyatian
	 * @param mallBsUser
	 * @param birthdayRule
	 */
	private void sendPoints(MallBsUser mallBsUser, MallPointsRule birthdayRule) {
		try {
			//记录收入交易明细
			MallPointsIncome pointsIncome = new MallPointsIncome();
			pointsIncome.setUserId(mallBsUser.getId());
			pointsIncome.setTransType(MallRuleEnum.MALL_BIRTHDAY.getCode());
			pointsIncome.setTransTime(new Date());
			pointsIncome.setPoints(0l);
			pointsIncome.setStatus(Constants.MALL_POINTS_INCOME_STATUS_INIT);
			pointsIncome.setCreateTime(new Date());
			pointsIncome.setUpdateTime(new Date());
			mallPointsIncomeMapper.insertSelective(pointsIncome);
			//积分发放业务锁，校验账户是否存在
			jsClientDaoSupport.lock( RedisLockEnum.MALL_LOCK_GRANT_POINTS.getKey() + mallBsUser.getId() );

			MallAccountExample accountExamp = new MallAccountExample();
			accountExamp.createCriteria().andUserIdEqualTo(mallBsUser.getId())
				.andAccountTypeEqualTo(Constants.MALL_ACCOUNT_ACCOUNT_TYPE_JSH)
				.andStatusEqualTo(Constants.MALL_ACCOUNT_STATUS_OPEN);
			List<MallAccount> accountList = accountMapper.selectByExample(accountExamp);
			MallAccount account = null;
			if( CollectionUtils.isEmpty(accountList) ) {
				account = pAccountService.openAccount(mallBsUser.getId());
			} else {
				account = accountList.get(0);
			}
			//更新积分 -- 生日场景
			MallIncomeResultInfo resultInfo = new MallIncomeResultInfo();
			resultInfo.setAcctId(account.getId());
			resultInfo.setIncomeId(pointsIncome.getId());
			resultInfo.setRuleId(birthdayRule.getId());
			resultInfo.setPoints(birthdayRule.getPoints());
			resultInfo.setTransType(pointsIncome.getTransType());
			//更新积分(事物)
			pAccountService.grantsPointsAccount( resultInfo );
			
			//更新收入交易明细表
			MallPointsIncome pointsIncomeTemp = new MallPointsIncome();
			pointsIncomeTemp.setId(pointsIncome.getId());
			pointsIncomeTemp.setStatus(Constants.MALL_POINTS_INCOME_STATUS_FINISHED);
			pointsIncomeTemp.setPoints(birthdayRule.getPoints());
			pointsIncomeTemp.setUpdateTime(new Date());
			pointsIncomeTemp.setFinishTime(new Date());
			mallPointsIncomeMapper.updateByPrimaryKeySelective(pointsIncomeTemp);
			
		} catch (Exception e) {
			log.error(">>>>>end【生日福利积分】发放异常，用户id="+mallBsUser.getId()+"<<<<<",e);
		} finally {
			jsClientDaoSupport.unlock(RedisLockEnum.MALL_LOCK_GRANT_POINTS.getKey() + mallBsUser.getId());
		}
		
	}
}
