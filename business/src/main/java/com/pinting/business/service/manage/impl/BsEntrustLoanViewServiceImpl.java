package com.pinting.business.service.manage.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.dao.BsEntrustLoanViewMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.LnLoanRelationMapper;
import com.pinting.business.hessian.manage.message.ReqMsg_BsEntrustLoanView_GetList;
import com.pinting.business.hessian.manage.message.ResMsg_BsEntrustLoanView_GetList;
import com.pinting.business.model.BsEntrustLoanView;
import com.pinting.business.model.BsEntrustLoanViewExample;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.vo.DailyAmount4LoanVO;
import com.pinting.business.service.loan.AuthBalanaceQueryService;
import com.pinting.business.service.manage.BsEntrustLoanViewService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;

@Service
public class BsEntrustLoanViewServiceImpl implements BsEntrustLoanViewService {
	
	@Autowired
	private BsEntrustLoanViewMapper bsEntrustLoanViewMapper;
	@Autowired
	private LnLoanRelationMapper lnLoanRelationMapper;
	@Autowired
	private LoanRelationshipService loanRelationshipService;
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private AuthBalanaceQueryService authBalanaceQueryService;
	
	

	@Override
	public void getListByTimePropertySymbol(ReqMsg_BsEntrustLoanView_GetList req,
			ResMsg_BsEntrustLoanView_GetList res) {
		//赞分期数据需要查询VIP实时,数据存字段todayLoanx,总数存todayLoan7
		if(Constants.PROPERTY_SYMBOL_ZAN.equals(req.getPropertySymbol())){
			BsEntrustLoanView VIPview = new BsEntrustLoanView();
			VIPview = getZANVIPList();
			res.setVIPview(VIPview);
		}
		
		String propertySymbol = req.getPropertySymbol();
		String startDate = req.getStartDate();
		String endDate = req.getEndDate();
		
		List<BsEntrustLoanView> returnList = new ArrayList<BsEntrustLoanView>();
		BsEntrustLoanView sumView = bsEntrustLoanViewMapper.getSumList(propertySymbol, startDate, endDate);
		if(sumView == null){
			sumView = initSumEntrustLoanView();
		}
		sumView.setId(-1);
		returnList.add(sumView);
		int count = bsEntrustLoanViewMapper.countList(propertySymbol, startDate, endDate);
		if(count > 0){
			List<BsEntrustLoanView> normalList = bsEntrustLoanViewMapper.getList(propertySymbol, startDate, endDate,req.getStart(), req.getNumPerPage());
			returnList.addAll(normalList);
		}
		
		res.setTotalRows(count);
		res.setValueList(BeanUtils.classToArrayList(returnList));
	}
	

	private BsEntrustLoanView initSumEntrustLoanView() {
		BsEntrustLoanView entrustLoanView = new BsEntrustLoanView();
		entrustLoanView.setTodayInvest7(0d);
		entrustLoanView.setTodayInvest30(0d);
		entrustLoanView.setTodayInvest90(0d);
		entrustLoanView.setTodayInvest180(0d);
		entrustLoanView.setTodayInvest270(0d);
		entrustLoanView.setTodayInvest365(0d);
		entrustLoanView.setTodayLoan7(0d);
		entrustLoanView.setTodayLoan30(0d);
		entrustLoanView.setTodayLoan90(0d);
		entrustLoanView.setTodayLoan180(0d);
		entrustLoanView.setTodayLoan270(0d);
		entrustLoanView.setTodayLoan365(0d);
		return entrustLoanView;
	}


	@Override
	public BsEntrustLoanView getZANVIPList() {
		BsEntrustLoanView VIPview = new BsEntrustLoanView();
		//VIP当前持有债权查询
		List<DailyAmount4LoanVO> loanList = lnLoanRelationMapper.getVIPLoanedSumAmount(loanRelationshipService.getSuperUserList());
		VIPview.setTodayLoan30(0d);
		VIPview.setTodayLoan60(0d);
		VIPview.setTodayLoan90(0d);
		VIPview.setTodayLoan120(0d);
		VIPview.setTodayLoan150(0d);
		VIPview.setTodayLoan180(0d);
		VIPview.setTodayLoan270(0d);
		VIPview.setTodayLoan365(0d);
		Double sum = 0d;
		if(CollectionUtils.isNotEmpty(loanList)){
			for (DailyAmount4LoanVO dailyAmount4LoanVO : loanList) {
				switch (dailyAmount4LoanVO.getTerm()) {
				case 1:
					VIPview.setTodayLoan30(dailyAmount4LoanVO.getSumAmount());
					sum = MoneyUtil.add(sum, dailyAmount4LoanVO.getSumAmount()).doubleValue();
					break;
				case 2:
					VIPview.setTodayLoan60(dailyAmount4LoanVO.getSumAmount());
					sum = MoneyUtil.add(sum, dailyAmount4LoanVO.getSumAmount()).doubleValue();
					break;
				case 3:
					VIPview.setTodayLoan90(dailyAmount4LoanVO.getSumAmount());
					sum = MoneyUtil.add(sum, dailyAmount4LoanVO.getSumAmount()).doubleValue();
					break;
				case 4:
					VIPview.setTodayLoan120(dailyAmount4LoanVO.getSumAmount());
					sum = MoneyUtil.add(sum, dailyAmount4LoanVO.getSumAmount()).doubleValue();
					break;
				case 5:
					VIPview.setTodayLoan150(dailyAmount4LoanVO.getSumAmount());
					sum = MoneyUtil.add(sum, dailyAmount4LoanVO.getSumAmount()).doubleValue();
					break;
				case 6:
					VIPview.setTodayLoan180(dailyAmount4LoanVO.getSumAmount());
					sum = MoneyUtil.add(sum, dailyAmount4LoanVO.getSumAmount()).doubleValue();
					break;
				case 9:
					VIPview.setTodayLoan270(dailyAmount4LoanVO.getSumAmount());
					sum = MoneyUtil.add(sum, dailyAmount4LoanVO.getSumAmount()).doubleValue();
					break;
				case 12:
					VIPview.setTodayLoan365(dailyAmount4LoanVO.getSumAmount());
					sum = MoneyUtil.add(sum, dailyAmount4LoanVO.getSumAmount()).doubleValue();
					break;
				default:
					break;
				}
			}
		}
		//普通用户当前站岗可用
		VIPview.setLeft2Loan30(0d);
		VIPview.setLeft2Loan60(0d);
		VIPview.setLeft2Loan90(0d);
		VIPview.setLeft2Loan120(0d);
		VIPview.setLeft2Loan150(0d);
		VIPview.setLeft2Loan180(0d);
		VIPview.setLeft2Loan270(0d);
		VIPview.setLeft2Loan365(0d);
		
		int day = 5;//站岗资金保留天数默认天数
		BsSysConfig configDay = sysConfigService.findConfigByKey(Constants.DAY_4_WAIT_LOAN); //站岗资金保留天数
		if(configDay != null){
			day = Integer.parseInt(configDay.getConfValue());
		}
		Date now =new Date();
		//起息日在此日期之前的
		String minInterestBeginDate =DateUtil.formatYYYYMMDD( DateUtil.addDays(now, -day));
		BsSysConfig limit = sysConfigService.findConfigByKey(Constants.MATCH_LIMIT_AMOUNT);//债权匹配时低于该金额的不进行债权承接
		Double limitAmount = 20d;//普通理财人债权匹配时低于该金额的不进行债权承接
		if(limit != null){
			limitAmount = Double.valueOf(limit.getConfValue());
		}
		//获取VIP理财用户列表
		List<Integer> superUserList = loanRelationshipService.getSuperUserList();
		List<DailyAmount4LoanVO> normalUserList = bsSubAccountMapper.getSumBalanceByProductType(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN, 
				Constants.PRODUCT_TYPE_AUTH, superUserList, minInterestBeginDate, "no",limitAmount,null);
		Double vipSum=0d;
		if(!CollectionUtils.isEmpty(normalUserList)){
			for (DailyAmount4LoanVO dailyAmount4LoanVO : normalUserList) {
				switch (dailyAmount4LoanVO.getTerm()) {
				case 1:
					VIPview.setLeft2Loan30(dailyAmount4LoanVO.getSumAmount());
					vipSum = MoneyUtil.add(vipSum, dailyAmount4LoanVO.getSumAmount()).doubleValue();
					break;
				case 2:
					VIPview.setLeft2Loan60(dailyAmount4LoanVO.getSumAmount());
					vipSum = MoneyUtil.add(vipSum, dailyAmount4LoanVO.getSumAmount()).doubleValue();
					break;
				case 3:
					VIPview.setLeft2Loan90(dailyAmount4LoanVO.getSumAmount());
					vipSum = MoneyUtil.add(vipSum, dailyAmount4LoanVO.getSumAmount()).doubleValue();
					break;
				case 4:
					VIPview.setLeft2Loan120(dailyAmount4LoanVO.getSumAmount());
					vipSum = MoneyUtil.add(vipSum, dailyAmount4LoanVO.getSumAmount()).doubleValue();
					break;
				case 5:
					VIPview.setLeft2Loan150(dailyAmount4LoanVO.getSumAmount());
					vipSum = MoneyUtil.add(vipSum, dailyAmount4LoanVO.getSumAmount()).doubleValue();
					break;
				case 6:
					VIPview.setLeft2Loan180(dailyAmount4LoanVO.getSumAmount());
					vipSum = MoneyUtil.add(vipSum, dailyAmount4LoanVO.getSumAmount()).doubleValue();
					break;
				case 9:
					VIPview.setLeft2Loan270(dailyAmount4LoanVO.getSumAmount());
					vipSum = MoneyUtil.add(vipSum, dailyAmount4LoanVO.getSumAmount()).doubleValue();
					break;
				case 12:
					VIPview.setLeft2Loan365(dailyAmount4LoanVO.getSumAmount());
					vipSum = MoneyUtil.add(vipSum, dailyAmount4LoanVO.getSumAmount()).doubleValue();
					break;
				default:
					break;
				}
			}
		}
		
		//VIP用户当前站岗可用
		VIPview.setLeft2LoanVIP(authBalanaceQueryService.getSuperAuthBalance(superUserList));
		
		VIPview.setTodayLoanSum(sum);
		VIPview.setLeft2LoanSum(vipSum);
		return VIPview;
	}


	
}
