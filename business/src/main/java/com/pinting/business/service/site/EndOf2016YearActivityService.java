package com.pinting.business.service.site;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.vo.EndOf2016YearActivityVO;

/**
 * 2016客户年终抽奖活动Service
 * Created by shh on 2016/11/30 15:00.
 * @author shh
 * 
 * 1、抽奖操作
 *    每次选择抽奖奖项时，后台去校验bs_2016_check_in_user表中的手机号，在币港湾注册的用户才可以抽奖；
 *    点击"开始"按钮开始抽奖 --> 按钮变成"停止" --> 点击"停止"变成 "开始"按钮  --> "开始"按钮需要过2秒后才可以再次点击
 * 2、结果显示
 *    幸运奖抽奖，一次抽完，15名，抽奖完成全部显示；其余奖项每抽一次奖显示一条获奖记录
 * 3、抽奖通知
 *    获奖的用户将以发送短信的方式提醒用户
 * 4、抽奖规则
 *    a、同一个用户可以重复抽奖，即可以重复中奖；
 *    b、同一个奖项每个用户只能中一次奖；
 *    c、随机中奖；
 *    b、特等奖：用户所有在投状态（投资中、结算中）的投资的年化额总和最大的那一位，只计算REG户
 *    d、中奖人数 幸运奖15人，三等奖8人，二等奖3人，一等奖2人，特等奖1人
 */
public interface EndOf2016YearActivityService {
	
	/**
	 * 找出可以抽奖的用户手机号
	 * @return
	 */
	List<EndOf2016YearActivityVO> quertLotteryMobileList();
	
	/**
	 * 查找未抽奖(一、二、三等奖)的用户手机号相关信息
	 * @param activityAwardId 奖品等级Id
	 * @return
	 */
	List<EndOf2016YearActivityVO> queryNoDrawMobileList(Integer activityAwardId);
	
	/**
	 * 统计幸运奖，特等奖、一、二、三等奖的获奖人数，抽完之后提示抽奖人
	 * @param activityAwardId 奖品等级Id
	 * @return
	 */
	int queryNumberOfWinners(Integer activityAwardId);
	
	/**
	 * 幸运奖抽奖，一次抽完，15名
	 * @return
	 */
	List<EndOf2016YearActivityVO> drawLuckyPrizeList();
	
	/**
	 * 特等奖抽奖，1名，用户所有在投状态（投资中、结算中）的投资的年化额总和最大的那一位
	 */
	List<EndOf2016YearActivityVO> grandPrize();
	
	/**
	 * 其它奖项抽取
	 * @param activityAwardId 几等奖
	 * @return
	 */
	List<EndOf2016YearActivityVO> otherAwards(Integer activityAwardId);
	
}
