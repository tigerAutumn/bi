package com.pinting.business.service.site.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.pinting.business.dao.Bs2016CheckInUserMapper;
import com.pinting.business.dao.BsActivityLuckyDrawMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsActivityLuckyDraw;
import com.pinting.business.model.vo.EndOf2016YearActivityVO;
import com.pinting.business.service.site.EndOf2016YearActivityService;
import com.pinting.business.util.Constants;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;

/**
 * 
 * Created by shh on 2016/12/01 12:00.
 * 
 * @author shh
 * 
 * 1、进入抽奖页面 -- 入口放在管理台 基于安全方面考虑 2016年终抽奖管理功能中点击"开始抽奖"，进入抽奖页面 
 * 2、抽奖操作
 *    每次选择抽奖奖项时，后台去校验bs_2016_check_in_user表中的手机号，在币港湾注册的用户才可以抽奖；
 *    点击"开始"按钮开始抽奖 --> 按钮变成"停止" --> 点击"停止"变成 "开始"按钮 --> "开始"按钮需要过2秒后才可以再次点击
 * 3、结果显示 幸运奖抽奖，一次抽完，抽奖完成全部显示；其余奖项每抽一次奖显示一条获奖记录 
 * 4、抽奖通知
 *    获奖的用户将以发送短信的方式提醒用户 
 * 5、抽奖规则 
 *    a、同一个用户可以重复抽奖，即可以重复中奖； 
 *    b、同一个奖项每个用户只能中一次奖；
 *    c、随机中奖；
 *    b、特等奖：用户所有在投状态（投资中、结算中）的投资的年化额总和最大的那一位，只计算REG户
 *    d、中奖人数 幸运奖15人，三等奖8人，二等奖3人，一等奖2人，特等奖1人
 */
@Service
public class EndOf2016YearActivityServiceImpl implements EndOf2016YearActivityService {

	private Logger log = LoggerFactory.getLogger(EndOf2016YearActivityServiceImpl.class);

	@Autowired
	private Bs2016CheckInUserMapper bs2016CheckInUserMapper;

	@Autowired
	private BsActivityLuckyDrawMapper bsActivityLuckyDrawMapper;
	
	@Autowired
	private SMSServiceClient smsServiceClient;
	
	@Autowired
	private TransactionTemplate transactionTemplate;

	@Override
	public List<EndOf2016YearActivityVO> quertLotteryMobileList() {
		List<EndOf2016YearActivityVO> lotteryList = bs2016CheckInUserMapper.selectLotteryMobileList();
		return lotteryList;
	}
	
	@Override
	public List<EndOf2016YearActivityVO> queryNoDrawMobileList(Integer activityAwardId) {
		List<EndOf2016YearActivityVO> noDrawList = bs2016CheckInUserMapper.selectNoDrawMobileList(activityAwardId);
		return noDrawList;
	}
	
	@Override
	public int queryNumberOfWinners(Integer activityAwardId) {
		return bs2016CheckInUserMapper.selectNumberOfWinners(activityAwardId);
	}

	@Override
	public List<EndOf2016YearActivityVO> drawLuckyPrizeList() {
		// 1、获取可以抽奖的用户
		List<EndOf2016YearActivityVO> lotteryList = quertLotteryMobileList();
		// 2、保存中奖的用户，随机选取15位不重复的用户
		final List<EndOf2016YearActivityVO> winningList = new ArrayList<EndOf2016YearActivityVO>();
		if (lotteryList != null && lotteryList.size() >= 15) {
			do {
				EndOf2016YearActivityVO obj = new EndOf2016YearActivityVO();
				int randomIndex = Math.abs(new Random().nextInt(lotteryList.size()));
				obj = lotteryList.get(randomIndex);
				winningList.add(obj);
				lotteryList.remove(randomIndex);
			} while (winningList.size() < 15);
		} else {
			throw new PTMessageException(
					PTMessageEnum.ACTIVITY_END_OF_2016YEAR_COUNT);
		}
		// 3、已抽中幸运奖的人数
		Integer count = bs2016CheckInUserMapper.selectNumberOfWinners(Constants.ACTIVITY_END_OF_2016YEAR_AWARDS_LUCKY);
		// 4、把幸运奖中奖用户存bs_activity_lucky_draw表，添加事物处理
		if(count <= 0) {
			
			transactionTemplate.execute(new TransactionCallback<Boolean>() {
			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try {
					if (winningList != null && winningList.size() > 0) {
						for (EndOf2016YearActivityVO vo : winningList) {
							//中奖的人发送短信提醒
							smsServiceClient.sendTemplateMessage(vo.getMobile(), TemplateKey.ENDOF2016_ACTIVITY_WINNING, "幸运奖");
							
							BsActivityLuckyDraw insertObj = new BsActivityLuckyDraw();
							insertObj.setUserId(vo.getUserId());
							insertObj.setActivityId(3);
							insertObj.setAwardId(25);
							insertObj.setIsBackDraw("Y");// 是否后台抽奖-Y
							insertObj.setBackDrawTime(new Date());
							insertObj.setIsUserDraw("N");// 用户是否抽奖-N
							insertObj.setIsWin("Y");// 是否中奖-Y
							insertObj.setIsConfirm("N");// 是否兑奖-N
							insertObj.setCreateTime(new Date());
							insertObj.setUpdateTime(new Date());
							bsActivityLuckyDrawMapper.insertSelective(insertObj);
							log.info("=========幸运奖中奖的手机号："+vo.getMobile()+"=========");
						}
					} else {
						throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
					}
				} catch (Exception e) {
					log.info("=========幸运奖中奖数据存库失败=========");
				} finally {
					status.isRollbackOnly();
				}
				
				return true;
			}
		});
			
		}else{
			throw new PTMessageException(PTMessageEnum.ACTIVITY_END_OF_2016YEAR_COUNTMAX);
		}
		return winningList;
	}

	@Override
	public List<EndOf2016YearActivityVO> grandPrize() {
		// 1、用户所有在投状态（投资中、结算中）的投资的年化额总和最大的那一位
		List<EndOf2016YearActivityVO> result = bs2016CheckInUserMapper.selectAnnualizedAmountMax();
		// 2、中奖人数 特等奖1名
		Integer count = bs2016CheckInUserMapper.selectNumberOfWinners(Constants.ACTIVITY_END_OF_2016YEAR_AWARDS_GRAND);
		// 3、把特等奖中奖用户存bs_activity_lucky_draw表
		if (result == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		} else {
			if(count < 1) {
				//中奖的人发送短信提醒
				smsServiceClient.sendTemplateMessage(result.get(0).getMobile(), TemplateKey.ENDOF2016_ACTIVITY_WINNING, "特等奖");
				
				BsActivityLuckyDraw insertObj = new BsActivityLuckyDraw();
				insertObj.setUserId(result.get(0).getUserId());
				insertObj.setActivityId(3);
				insertObj.setAwardId(29);
				insertObj.setIsBackDraw("Y");// 是否后台抽奖-Y
				insertObj.setBackDrawTime(new Date());
				insertObj.setIsUserDraw("N");// 用户是否抽奖-N
				insertObj.setIsWin("Y");// 是否中奖-Y
				insertObj.setIsConfirm("N");// 是否兑奖-N
				insertObj.setCreateTime(new Date());
				insertObj.setUpdateTime(new Date());
				bsActivityLuckyDrawMapper.insertSelective(insertObj);
			}else{
				throw new PTMessageException(PTMessageEnum.ACTIVITY_END_OF_2016YEAR_COUNTMAX);
			}
		}
		return result;
	}

	@Override
	public List<EndOf2016YearActivityVO> otherAwards(Integer activityAwardId) {
		// 1、获取可以抽奖的用户
		List<EndOf2016YearActivityVO> noDrawList = queryNoDrawMobileList(activityAwardId);

		// 2、保存中奖的用户，随机选取1位不重复的用户 - 一、 二、三等奖
		List<EndOf2016YearActivityVO> winningList = new ArrayList<EndOf2016YearActivityVO>();
		EndOf2016YearActivityVO obj = new EndOf2016YearActivityVO();
		int randomIndex = Math.abs(new Random().nextInt(noDrawList.size()));
		obj = noDrawList.get(randomIndex);
		winningList.add(obj);
		// 3、中奖人数 三等奖8人，二等奖3人，一等奖2人
		Integer count = bs2016CheckInUserMapper.selectNumberOfWinners(activityAwardId);
		
		switch (activityAwardId) {
		case Constants.ACTIVITY_END_OF_2016YEAR_AWARDS_THIRD:
			if(count < 8) {
				//中奖的用户发送短信
				smsServiceClient.sendTemplateMessage(winningList.get(0).getMobile(), TemplateKey.ENDOF2016_ACTIVITY_WINNING, "三等奖");
				
				// 4、把三等奖中奖用户存bs_activity_lucky_draw表
				BsActivityLuckyDraw insertObj = new BsActivityLuckyDraw();
				insertObj.setUserId(winningList.get(0).getUserId());
				insertObj.setActivityId(3);
				insertObj.setAwardId(activityAwardId);
				insertObj.setIsBackDraw("Y");// 是否后台抽奖-Y
				insertObj.setBackDrawTime(new Date());
				insertObj.setIsUserDraw("N");// 用户是否抽奖-N
				insertObj.setIsWin("Y");// 是否中奖-Y
				insertObj.setIsConfirm("N");// 是否兑奖-N
				insertObj.setCreateTime(new Date());
				insertObj.setUpdateTime(new Date());
				bsActivityLuckyDrawMapper.insertSelective(insertObj);
			}else {
				throw new PTMessageException(PTMessageEnum.ACTIVITY_END_OF_2016YEAR_COUNTMAX);
			}
			break;
		case Constants.ACTIVITY_END_OF_2016YEAR_AWARDS_SECOND:
			if(count < 3) {
				//中奖的用户发送短信
				smsServiceClient.sendTemplateMessage(winningList.get(0).getMobile(), TemplateKey.ENDOF2016_ACTIVITY_WINNING, "二等奖");
				
				// 5、把二等奖中奖用户存bs_activity_lucky_draw表
				BsActivityLuckyDraw insertObj = new BsActivityLuckyDraw();
				insertObj.setUserId(winningList.get(0).getUserId());
				insertObj.setActivityId(3);
				insertObj.setAwardId(activityAwardId);
				insertObj.setIsBackDraw("Y");// 是否后台抽奖-Y
				insertObj.setBackDrawTime(new Date());
				insertObj.setIsUserDraw("N");// 用户是否抽奖-N
				insertObj.setIsWin("Y");// 是否中奖-Y
				insertObj.setIsConfirm("N");// 是否兑奖-N
				insertObj.setCreateTime(new Date());
				insertObj.setUpdateTime(new Date());
				bsActivityLuckyDrawMapper.insertSelective(insertObj);
			}else {
				throw new PTMessageException(PTMessageEnum.ACTIVITY_END_OF_2016YEAR_COUNTMAX);
			}
			break;
		case Constants.ACTIVITY_END_OF_2016YEAR_AWARDS_FIRST:
			if(count < 2) {
				//中奖的用户发送短信
				smsServiceClient.sendTemplateMessage(winningList.get(0).getMobile(), TemplateKey.ENDOF2016_ACTIVITY_WINNING, "一等奖");
				
				// 6、把一等奖中奖用户存bs_activity_lucky_draw表
				BsActivityLuckyDraw insertObj = new BsActivityLuckyDraw();
				insertObj.setUserId(winningList.get(0).getUserId());
				insertObj.setActivityId(3);
				insertObj.setAwardId(activityAwardId);
				insertObj.setIsBackDraw("Y");// 是否后台抽奖-Y
				insertObj.setBackDrawTime(new Date());
				insertObj.setIsUserDraw("N");// 用户是否抽奖-N
				insertObj.setIsWin("Y");// 是否中奖-Y
				insertObj.setIsConfirm("N");// 是否兑奖-N
				insertObj.setCreateTime(new Date());
				insertObj.setUpdateTime(new Date());
				bsActivityLuckyDrawMapper.insertSelective(insertObj);
			}else {
				throw new PTMessageException(PTMessageEnum.ACTIVITY_END_OF_2016YEAR_COUNTMAX);
			}
			break;
		default:
			break;
		}
		
		return winningList;
	}

}
