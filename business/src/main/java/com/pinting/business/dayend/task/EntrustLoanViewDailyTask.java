package com.pinting.business.dayend.task;

import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.dao.BsEntrustLoanViewMapper;
import com.pinting.business.dao.BsMatchMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnLoanRelationMapper;
import com.pinting.business.model.BsEntrustLoanView;
import com.pinting.business.model.BsEntrustLoanViewExample;
import com.pinting.business.model.vo.DailyAmount4LoanVO;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 统计每日借款与委托数据获取定时任务
 * @author bianyatian
 * @2016-10-12 上午10:36:05
 */
@Service
public class EntrustLoanViewDailyTask {
	private Logger log = LoggerFactory.getLogger(EntrustLoanViewDailyTask.class);
	
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	
	@Autowired
	private LoanRelationshipService loanRelationshipService;
	
	@Autowired
	private LnLoanRelationMapper lnLoanRelationMapper;
	
	@Autowired
	private BsMatchMapper bsMatchMapper;
	
	@Autowired
	private LnLoanMapper lnLoanMapper;
	
	@Autowired
	private BsEntrustLoanViewMapper bsEntrustLoanViewMapper;
	
	public void execute() {
		
		Date now = new Date();
		String interestBeginDate =DateUtil.formatYYYYMMDD(now);
		Date date =DateUtil.parseDate(DateUtil.formatYYYYMMDD(DateUtil.addDays(now, -1)));
		log.info("==================【每日借款与委托数据获取】 开始 =================");
		try {
			log.info("==================【每日借款与委托数据获取】 赞分期 =================");
			//VIP用户id列表
			List<Integer> superUserList = loanRelationshipService.getSuperUserList();
			getZanEntrustLoanViewDaily(superUserList, interestBeginDate, date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			
			log.info("==================【每日借款与委托数据获取】 云贷 =================");
			getYunDaiEntrustLoanViewDaily(interestBeginDate, date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			log.info("==================【每日借款与委托数据获取】 七贷 =================");
			get7DaiEntrustLoanViewDaily(interestBeginDate, date);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("==================【每日借款与委托数据获取】 结束 =================");
	}


	/**
	 * 赞分期
	 * @param superUserList 
	 * @param interestBeginDate 
	 * @param date 
	 */
	private void getZanEntrustLoanViewDaily(List<Integer> superUserList, String interestBeginDate, Date date) {
		Date now = new Date();
		BsEntrustLoanView entrustLoanView = new BsEntrustLoanView();
		entrustLoanView.setCreateTime(new Date());
		entrustLoanView.setDate(date);
		entrustLoanView.setPropertySymbol(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN);
		
		//判断是否已经存在
		if(!checkIsAddView(entrustLoanView)){
			//当日投资额
			List<DailyAmount4LoanVO> list = bsSubAccountMapper.getSumOpenBalanceByDate(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN,
					Constants.PRODUCT_TYPE_AUTH, superUserList, interestBeginDate);
			
			entrustLoanView = setEntrustLoanViewInvestAmount(entrustLoanView, list);
			
			//委托金额
			
			String beginDate =DateUtil.formatYYYYMMDD(DateUtil.addDays(now, -1))+" 00:00:00";
			String endDate =DateUtil.formatYYYYMMDD(DateUtil.addDays(now, -1))+" 23:59:59";
			
			//List<DailyAmount4LoanVO>  loanList= lnLoanRelationMapper.getLoanSumAmount(beginDate, endDate, superUserList);
			List<DailyAmount4LoanVO>  loanList= lnLoanMapper.getLoanSumAmount(beginDate, endDate);
			entrustLoanView = setLoanAmount(entrustLoanView,loanList);
			
			bsEntrustLoanViewMapper.insertSelective(entrustLoanView);
		}
		
		

	}

	//判断当日数据是否已经存在,存在返回true
	private boolean checkIsAddView(BsEntrustLoanView entrustLoanView) {
		BsEntrustLoanViewExample example = new BsEntrustLoanViewExample();
		
		example.createCriteria().andDateEqualTo(entrustLoanView.getDate())
			.andPropertySymbolEqualTo(entrustLoanView.getPropertySymbol());
		
		List<BsEntrustLoanView> list = bsEntrustLoanViewMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(list)){
			return true;
		}else{
			return false;
		}
	}


	/**
	 * 借款金额-对象赋值
	 * @param entrustLoanView
	 * @param loanList
	 * @return
	 */
	private BsEntrustLoanView setLoanAmount(
			BsEntrustLoanView entrustLoanView, List<DailyAmount4LoanVO> loanList) {
		//初始化数据
		entrustLoanView.setTodayLoan7(0d);
		entrustLoanView.setTodayLoan30(0d);
		entrustLoanView.setTodayLoan60(0d);
		entrustLoanView.setTodayLoan90(0d);
		entrustLoanView.setTodayLoan120(0d);
		entrustLoanView.setTodayLoan150(0d);
		entrustLoanView.setTodayLoan180(0d);
		entrustLoanView.setTodayLoan270(0d);
		entrustLoanView.setTodayLoan365(0d);
		if(CollectionUtils.isNotEmpty(loanList)){
			for (DailyAmount4LoanVO dailyAmount4LoanVO : loanList) {
				switch (dailyAmount4LoanVO.getTerm()) {
				case 1:
					entrustLoanView.setTodayLoan30(dailyAmount4LoanVO.getSumAmount());
					break;
				case 2:
					entrustLoanView.setTodayLoan60(dailyAmount4LoanVO.getSumAmount());
					break;
				case 3:
					entrustLoanView.setTodayLoan90(dailyAmount4LoanVO.getSumAmount());
					break;
				case 4:
					entrustLoanView.setTodayLoan120(dailyAmount4LoanVO.getSumAmount());
					break;
				case 5:
					entrustLoanView.setTodayLoan150(dailyAmount4LoanVO.getSumAmount());
					break;
				case 6:
					entrustLoanView.setTodayLoan180(dailyAmount4LoanVO.getSumAmount());
					break;
				case 9:
					entrustLoanView.setTodayLoan270(dailyAmount4LoanVO.getSumAmount());
					break;
				case 12:
					entrustLoanView.setTodayLoan365(dailyAmount4LoanVO.getSumAmount());
					break;
				case -7:
					entrustLoanView.setTodayLoan7(dailyAmount4LoanVO.getSumAmount());
					break;
				default:
					break;
				}
			}
		}
		return entrustLoanView;
	}


	/**
	 * 遍历查询出来的list，存入对象，投资金额（委托金额）
	 * @param entrustLoanView
	 * @param list
	 * @return
	 */
	private BsEntrustLoanView setEntrustLoanViewInvestAmount(
			BsEntrustLoanView entrustLoanView, List<DailyAmount4LoanVO> list) {
		//初始化数据
		entrustLoanView.setTodayInvest7(0d);
		entrustLoanView.setTodayInvest30(0d);
		entrustLoanView.setTodayInvest60(0d);
		entrustLoanView.setTodayInvest90(0d);
		entrustLoanView.setTodayInvest120(0d);
		entrustLoanView.setTodayInvest150(0d);
		entrustLoanView.setTodayInvest180(0d);
		entrustLoanView.setTodayInvest270(0d);
		entrustLoanView.setTodayInvest365(0d);
		if(CollectionUtils.isNotEmpty(list)){
			for (DailyAmount4LoanVO dailyAmount4LoanVO : list) {
				switch (dailyAmount4LoanVO.getTerm()) {
				case 1:
					entrustLoanView.setTodayInvest30(dailyAmount4LoanVO.getSumAmount());
					break;
				case 2:
					entrustLoanView.setTodayInvest60(dailyAmount4LoanVO.getSumAmount());
					break;
				case 3:
					entrustLoanView.setTodayInvest90(dailyAmount4LoanVO.getSumAmount());
					break;
				case 4:
					entrustLoanView.setTodayInvest120(dailyAmount4LoanVO.getSumAmount());
					break;
				case 5:
					entrustLoanView.setTodayInvest150(dailyAmount4LoanVO.getSumAmount());
					break;
				case 6:
					entrustLoanView.setTodayInvest180(dailyAmount4LoanVO.getSumAmount());
					break;
				case 9:
					entrustLoanView.setTodayInvest270(dailyAmount4LoanVO.getSumAmount());
					break;
				case 12:
					entrustLoanView.setTodayInvest365(dailyAmount4LoanVO.getSumAmount());
					break;
				case -7:
					entrustLoanView.setTodayInvest7(dailyAmount4LoanVO.getSumAmount());
					break;
				default:
					break;
				}
			}
		}
		return entrustLoanView;
	}


	private void getYunDaiEntrustLoanViewDaily(String interestBeginDate, Date date) {
		
		
		BsEntrustLoanView entrustLoanView = new BsEntrustLoanView();
		entrustLoanView.setCreateTime(new Date());
		entrustLoanView.setDate(date);
		entrustLoanView.setPropertySymbol(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI);
		//判断是否已经存在
		if(!checkIsAddView(entrustLoanView)){
			//当日投资额
			List<DailyAmount4LoanVO> list = bsSubAccountMapper.getSumOpenBalanceByDate(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI,
					Constants.PRODUCT_TYPE_REG, null, interestBeginDate);
			entrustLoanView = setEntrustLoanViewInvestAmount(entrustLoanView, list);
			
			//委托金额
			Date now = new Date();
			String matchDate =DateUtil.formatYYYYMMDD(DateUtil.addDays(now, -1));
			List<DailyAmount4LoanVO>  loanList= bsMatchMapper.getMacthedSumAmount(matchDate, Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI);
			entrustLoanView = setLoanAmount(entrustLoanView,loanList);
		
			bsEntrustLoanViewMapper.insertSelective(entrustLoanView);
		}
	}

	private void get7DaiEntrustLoanViewDaily(String interestBeginDate, Date date) {
		BsEntrustLoanView entrustLoanView = new BsEntrustLoanView();
		entrustLoanView.setCreateTime(new Date());
		entrustLoanView.setDate(date);
		entrustLoanView.setPropertySymbol(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI);
		//判断是否已经存在
		if(!checkIsAddView(entrustLoanView)){
		
			//当日投资额
			List<DailyAmount4LoanVO> list = bsSubAccountMapper.getSumOpenBalanceByDate(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI,
					Constants.PRODUCT_TYPE_REG, null, interestBeginDate);
			entrustLoanView = setEntrustLoanViewInvestAmount(entrustLoanView, list);
			
			//委托金额
			Date now = new Date();
			String matchDate =DateUtil.formatYYYYMMDD(DateUtil.addDays(now, -1));
			List<DailyAmount4LoanVO>  loanList= bsMatchMapper.getMacthedSumAmount(matchDate, Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI);
			entrustLoanView = setLoanAmount(entrustLoanView,loanList);
	
			bsEntrustLoanViewMapper.insertSelective(entrustLoanView);
		}
		
	}
}
