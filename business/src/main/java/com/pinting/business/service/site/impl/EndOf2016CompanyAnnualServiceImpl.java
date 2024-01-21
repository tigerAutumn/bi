package com.pinting.business.service.site.impl;

import com.pinting.business.dao.BsActivityLuckyDrawMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsActivityLuckyDraw;
import com.pinting.business.model.vo.EndOf2016CompanyAnnualVO;
import com.pinting.business.service.site.EndOf2016CompanyAnnualService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.Bs2016AnnualMeetingEmployeeMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 1、抽奖操作
 * 每次抽奖前先去库里查询可以抽奖的名单，未中奖的用户才可以抽奖
 * 2、结果显示
 * 奖项 (七等奖30名 分3次抽取每次抽取10名、六等奖20名 分2次抽取每次抽取10名、五等奖10名 一次抽完)，抽奖完成全部显示对应的中奖者；其余奖项每抽一次奖显示一位中奖者姓名
 * 3、抽奖规则
 * a、同一个用户不能重复抽奖，即可以不能重复中奖；
 * b、随机中奖；
 * c、中奖人数 七等奖30名、六等奖20名、五等奖10名、四等奖3人、三等奖2人、二等奖1人、一等奖1人
 */

@Service
public class EndOf2016CompanyAnnualServiceImpl implements EndOf2016CompanyAnnualService {

	private Logger log = LoggerFactory.getLogger(EndOf2016YearActivityServiceImpl.class);

	@Autowired
	private Bs2016AnnualMeetingEmployeeMapper annualMeetingEmployeeMapper;

	@Autowired
	private BsActivityLuckyDrawMapper bsActivityLuckyDrawMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Override
	public List<EndOf2016CompanyAnnualVO> quertLotteryNameList() {
		return annualMeetingEmployeeMapper.selectLotteryNameList();
	}

	@Override
	public List<EndOf2016CompanyAnnualVO> queryLargestAward() {
		return annualMeetingEmployeeMapper.selectLargestAward();
	}

	@Override
	public int queryNumberOfWinners(Integer activityAwardId) {
		return annualMeetingEmployeeMapper.selectNumberOfWinners(activityAwardId);
	}

	@Override
	public List<EndOf2016CompanyAnnualVO> drawLuckyPrizeList(Integer activityAwardId) {
		// 1、获取可以抽奖的用户
		List<EndOf2016CompanyAnnualVO> lotteryList = quertLotteryNameList();
		// 2、保存中奖的用户，随机选取不重复的用户
		final List<EndOf2016CompanyAnnualVO> winningList = new ArrayList<EndOf2016CompanyAnnualVO>();
		// 3、奖项 (七等奖、六等奖、五等奖)
		int awardId  = activityAwardId;

		switch (awardId) {
			case 30: //七等奖30名 分3次抽取 每次抽10人

				// 4、已中奖的人数
				Integer count_seven = annualMeetingEmployeeMapper.selectNumberOfWinners(activityAwardId);
				// 5、把中奖用户存 bs_2016_annual_meeting_employee 表，添加事物处理
				if(count_seven <= 20) {

					if (lotteryList != null && lotteryList.size() > 0) {
						do {
							EndOf2016CompanyAnnualVO obj = new EndOf2016CompanyAnnualVO();
							int randomIndex = Math.abs(new Random().nextInt(lotteryList.size()));
							obj = lotteryList.get(randomIndex);
							winningList.add(obj);
							lotteryList.remove(randomIndex);
						} while (winningList.size() < 10);
					} else {
						throw new PTMessageException(
								PTMessageEnum.ACTIVITY_END_OF_2016YEAR_COUNT);
					}

					transactionTemplate.execute(new TransactionCallback<Boolean>() {
						@Override
						public Boolean doInTransaction(TransactionStatus status) {
							if (winningList != null && winningList.size() > 0) {
								for (EndOf2016CompanyAnnualVO vo : winningList) {
									BsActivityLuckyDraw insertObj = new BsActivityLuckyDraw();
									insertObj.setUserId(1000);
									insertObj.setActivityId(5);
									insertObj.setAwardId(30);
									insertObj.setIsBackDraw("Y");// 是否后台抽奖-Y
									insertObj.setBackDrawTime(new Date());
									insertObj.setIsUserDraw("N");// 用户是否抽奖-N
									insertObj.setIsWin("Y");// 是否中奖-Y
									insertObj.setIsConfirm("N");// 是否兑奖-N
									insertObj.setCreateTime(new Date());
									insertObj.setUpdateTime(new Date());
									insertObj.setNote(vo.getEmployeeName());
									bsActivityLuckyDrawMapper.insertSelective(insertObj);
									log.info("=========中奖的姓名："+vo.getEmployeeName()+"=========");
								}
							} else {
								throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
							}

						return true;
						}
					});

				}else{
					throw new PTMessageException(PTMessageEnum.ACTIVITY_END_OF_2016YEAR_COUNTMAX);
				}

				break;

			case 31: //六等奖20名 分2次抽取 每次抽10人

				// 6、已中奖的人数
				Integer count_six = annualMeetingEmployeeMapper.selectNumberOfWinners(activityAwardId);
				// 7、把中奖用户存 bs_2016_annual_meeting_employee 表，添加事物处理
				if(count_six <= 10) {

					if (lotteryList != null && lotteryList.size() > 0) {
						do {
							EndOf2016CompanyAnnualVO obj = new EndOf2016CompanyAnnualVO();
							int randomIndex = Math.abs(new Random().nextInt(lotteryList.size()));
							obj = lotteryList.get(randomIndex);
							winningList.add(obj);
							lotteryList.remove(randomIndex);
						} while (winningList.size() < 10);
					} else {
						throw new PTMessageException(
								PTMessageEnum.ACTIVITY_END_OF_2016YEAR_COUNT);
					}

					transactionTemplate.execute(new TransactionCallback<Boolean>() {
						@Override
						public Boolean doInTransaction(TransactionStatus status) {
							if (winningList != null && winningList.size() > 0) {
								for (EndOf2016CompanyAnnualVO vo : winningList) {
									BsActivityLuckyDraw insertObj = new BsActivityLuckyDraw();
									insertObj.setUserId(1000);
									insertObj.setActivityId(5);
									insertObj.setAwardId(31);
									insertObj.setIsBackDraw("Y");// 是否后台抽奖-Y
									insertObj.setBackDrawTime(new Date());
									insertObj.setIsUserDraw("N");// 用户是否抽奖-N
									insertObj.setIsWin("Y");// 是否中奖-Y
									insertObj.setIsConfirm("N");// 是否兑奖-N
									insertObj.setCreateTime(new Date());
									insertObj.setUpdateTime(new Date());
									insertObj.setNote(vo.getEmployeeName());
									bsActivityLuckyDrawMapper.insertSelective(insertObj);
									log.info("=========中奖的姓名："+vo.getEmployeeName()+"=========");
								}
							} else {
								throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
							}

						return true;
						}
					});

				}else{
					throw new PTMessageException(PTMessageEnum.ACTIVITY_END_OF_2016YEAR_COUNTMAX);
				}

				break;

			case 32: //五等奖10名

				// 8、已中奖的人数
				Integer count_five = annualMeetingEmployeeMapper.selectNumberOfWinners(activityAwardId);
				// 9、把中奖用户存 bs_2016_annual_meeting_employee 表，添加事物处理
				if(count_five <= 0) {

					if (lotteryList != null && lotteryList.size() > 0) {
						do {
							EndOf2016CompanyAnnualVO obj = new EndOf2016CompanyAnnualVO();
							int randomIndex = Math.abs(new Random().nextInt(lotteryList.size()));
							obj = lotteryList.get(randomIndex);
							winningList.add(obj);
							lotteryList.remove(randomIndex);
						} while (winningList.size() < 10);
					} else {
						throw new PTMessageException(
								PTMessageEnum.ACTIVITY_END_OF_2016YEAR_COUNT);
					}

					transactionTemplate.execute(new TransactionCallback<Boolean>() {
						@Override
						public Boolean doInTransaction(TransactionStatus status) {
							if (winningList != null && winningList.size() > 0) {
								for (EndOf2016CompanyAnnualVO vo : winningList) {
									BsActivityLuckyDraw insertObj = new BsActivityLuckyDraw();
									insertObj.setUserId(1000);
									insertObj.setActivityId(5);
									insertObj.setAwardId(32);
									insertObj.setIsBackDraw("Y");// 是否后台抽奖-Y
									insertObj.setBackDrawTime(new Date());
									insertObj.setIsUserDraw("N");// 用户是否抽奖-N
									insertObj.setIsWin("Y");// 是否中奖-Y
									insertObj.setIsConfirm("N");// 是否兑奖-N
									insertObj.setCreateTime(new Date());
									insertObj.setUpdateTime(new Date());
									insertObj.setNote(vo.getEmployeeName());
									bsActivityLuckyDrawMapper.insertSelective(insertObj);
									log.info("=========中奖的姓名："+vo.getEmployeeName()+"=========");
								}
							} else {
								throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
							}

							return true;
						}
					});

				}else{
					throw new PTMessageException(PTMessageEnum.ACTIVITY_END_OF_2016YEAR_COUNTMAX);
				}

				break;

			default:
			break;
		}

		return winningList;
	}

	@Override
	public List<EndOf2016CompanyAnnualVO> otherAwards(Integer activityAwardId) {
		// 1、获取可以抽奖的用户
		List<EndOf2016CompanyAnnualVO> lotteryList = quertLotteryNameList();
		// 2、中奖人数 四等奖3名 三等奖2名
		Integer count = annualMeetingEmployeeMapper.selectNumberOfWinners(activityAwardId);
		// 3、保存中奖的用户，随机选取不重复的用户
		List<EndOf2016CompanyAnnualVO> winningList = new ArrayList<EndOf2016CompanyAnnualVO>();
		EndOf2016CompanyAnnualVO obj = new EndOf2016CompanyAnnualVO();
		int randomIndex = Math.abs(new Random().nextInt(lotteryList.size()));
		obj = lotteryList.get(randomIndex);
		winningList.add(obj);

		switch (activityAwardId) {
			case 33: //四等奖3名
				if(count < 3) {
					for (EndOf2016CompanyAnnualVO vo : winningList) {
						BsActivityLuckyDraw insertObj = new BsActivityLuckyDraw();
						insertObj.setUserId(1000);
						insertObj.setActivityId(5);
						insertObj.setAwardId(33);
						insertObj.setIsBackDraw("Y");// 是否后台抽奖-Y
						insertObj.setBackDrawTime(new Date());
						insertObj.setIsUserDraw("N");// 用户是否抽奖-N
						insertObj.setIsWin("Y");// 是否中奖-Y
						insertObj.setIsConfirm("N");// 是否兑奖-N
						insertObj.setCreateTime(new Date());
						insertObj.setUpdateTime(new Date());
						insertObj.setNote(vo.getEmployeeName());
						bsActivityLuckyDrawMapper.insertSelective(insertObj);
						log.info("=========四等奖中奖的姓名："+vo.getEmployeeName()+"=========");
					}
				}else{
					throw new PTMessageException(PTMessageEnum.ACTIVITY_END_OF_2016YEAR_COUNTMAX);
				}
				break;

			case 34: //三等奖2名
				if(count < 2) {
					for (EndOf2016CompanyAnnualVO vo : winningList) {
						BsActivityLuckyDraw insertObj = new BsActivityLuckyDraw();
						insertObj.setUserId(1000);
						insertObj.setActivityId(5);
						insertObj.setAwardId(34);
						insertObj.setIsBackDraw("Y");// 是否后台抽奖-Y
						insertObj.setBackDrawTime(new Date());
						insertObj.setIsUserDraw("N");// 用户是否抽奖-N
						insertObj.setIsWin("Y");// 是否中奖-Y
						insertObj.setIsConfirm("N");// 是否兑奖-N
						insertObj.setCreateTime(new Date());
						insertObj.setUpdateTime(new Date());
						insertObj.setNote(vo.getEmployeeName());
						bsActivityLuckyDrawMapper.insertSelective(insertObj);
						log.info("=========三等奖中奖的姓名："+vo.getEmployeeName()+"=========");
					}
				}else{
					throw new PTMessageException(PTMessageEnum.ACTIVITY_END_OF_2016YEAR_COUNTMAX);
				}
				break;

			default:
				break;
		}
		return winningList;
	}

	@Override
	public List<EndOf2016CompanyAnnualVO> grandPrize(Integer activityAwardId) {
		// 1、获取可以抽奖的用户
		List<EndOf2016CompanyAnnualVO> lotteryList = queryLargestAward();
		// 2、中奖人数 一等奖1名  二等奖1名
		Integer count = annualMeetingEmployeeMapper.selectNumberOfWinners(activityAwardId);
		// 3、保存中奖的用户，随机选取不重复的用户
		List<EndOf2016CompanyAnnualVO> winningList = new ArrayList<EndOf2016CompanyAnnualVO>();
		EndOf2016CompanyAnnualVO obj = new EndOf2016CompanyAnnualVO();
		int randomIndex = Math.abs(new Random().nextInt(lotteryList.size()));
		obj = lotteryList.get(randomIndex);
		winningList.add(obj);

		switch (activityAwardId) {
			case 35: //二等奖1名
				if(count < 1) {
					BsActivityLuckyDraw insertObj = new BsActivityLuckyDraw();
					insertObj.setUserId(1000);
					insertObj.setActivityId(5);
					insertObj.setAwardId(35);
					insertObj.setIsBackDraw("Y");// 是否后台抽奖-Y
					insertObj.setBackDrawTime(new Date());
					insertObj.setIsUserDraw("N");// 用户是否抽奖-N
					insertObj.setIsWin("Y");// 是否中奖-Y
					insertObj.setIsConfirm("N");// 是否兑奖-N
					insertObj.setCreateTime(new Date());
					insertObj.setUpdateTime(new Date());
					insertObj.setNote(winningList.get(0).getEmployeeName());
					bsActivityLuckyDrawMapper.insertSelective(insertObj);
					log.info("=========二等奖中奖的姓名："+winningList.get(0).getEmployeeName()+"=========");
				}else{
					throw new PTMessageException(PTMessageEnum.ACTIVITY_END_OF_2016YEAR_COUNTMAX);
				}
				break;

			case 36: //一等奖1名
				if(count < 1) {
					BsActivityLuckyDraw insertObj = new BsActivityLuckyDraw();
					insertObj.setUserId(1000);
					insertObj.setActivityId(5);
					insertObj.setAwardId(36);
					insertObj.setIsBackDraw("Y");// 是否后台抽奖-Y
					insertObj.setBackDrawTime(new Date());
					insertObj.setIsUserDraw("N");// 用户是否抽奖-N
					insertObj.setIsWin("Y");// 是否中奖-Y
					insertObj.setIsConfirm("N");// 是否兑奖-N
					insertObj.setCreateTime(new Date());
					insertObj.setUpdateTime(new Date());
					insertObj.setNote(winningList.get(0).getEmployeeName());
					bsActivityLuckyDrawMapper.insertSelective(insertObj);
					log.info("=========一等奖中奖的姓名："+winningList.get(0).getEmployeeName()+"=========");
				}else{
					throw new PTMessageException(PTMessageEnum.ACTIVITY_END_OF_2016YEAR_COUNTMAX);
				}
				break;

			default:
				break;
		}

		return winningList;
	}

}
