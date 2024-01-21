package com.pinting.business.service.site;

import java.util.List;

import com.pinting.business.model.vo.EndOf2016CompanyAnnualVO;

/**
 * 2016公司年会抽奖活动Service
 *
 * 1、抽奖操作
 * 每次抽奖前先去库里查询可以抽奖的名单，未中奖的用户才可以抽奖
 * 2、结果显示
 * 奖项 (七等奖30名 分3次抽取每次抽取10名、六等奖20名 分2次抽取每次抽取10名、五等奖10名 一次抽完)，抽奖完成全部显示对应的中奖者；其余奖项每抽一次奖显示一位中奖者姓名
 * 3、抽奖规则
 * a、同一个用户不能重复抽奖，即可以不能重复中奖；
 * b、随机中奖；
 * c、中奖人数 七等奖30名、六等奖20名、五等奖10名、四等奖3人、三等奖2人、二等奖1人、一等奖1人
 *
 * Created by shh on 2017/01/14 10:00.
 * @author shh
 */
public interface EndOf2016CompanyAnnualService {
	
	/**
	 * 找出可以抽奖的员工信息
	 * @return
	 */
	List<EndOf2016CompanyAnnualVO> quertLotteryNameList();

	/**
	 * 查找可以抽奖的员工相关信息(一等奖、二等奖)
	 * @return
     */
	List<EndOf2016CompanyAnnualVO> queryLargestAward();

	/**
	 * 统计每个奖项的获奖人数，抽完之后提示抽奖人，剩余的抽奖次数
	 * @param activityAwardId 奖品等级Id
	 * @return
	 */
	int queryNumberOfWinners(Integer activityAwardId);
	
	/**
	 * 七等奖(30名 10名/次 抽三次)，六等奖(20名 10名/次 抽两次)，五等奖(10名一次抽完)
	 * @param activityAwardId  奖品等级Id
	 * @return
     */
	List<EndOf2016CompanyAnnualVO> drawLuckyPrizeList(Integer activityAwardId);
	
	/**
	 * 其它奖项抽取
	 * @param activityAwardId 几等奖
	 * @return
	 */
	List<EndOf2016CompanyAnnualVO> otherAwards(Integer activityAwardId);

	/**
	 * 一等奖(1名)、二等奖(1名)抽奖
	 */
	List<EndOf2016CompanyAnnualVO> grandPrize(Integer activityAwardId);

}
