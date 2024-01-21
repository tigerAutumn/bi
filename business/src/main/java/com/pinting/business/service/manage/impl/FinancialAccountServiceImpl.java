/**

 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.service.manage.impl;

import java.text.DecimalFormat;
import java.util.*;

import com.pinting.business.dao.*;
import com.pinting.business.model.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.manage.message.ReqMsg_FinancialAccount_ListQuery1;
import com.pinting.business.hessian.manage.message.ReqMsg_FinancialAccount_ListQuery2;
import com.pinting.business.hessian.manage.message.ReqMsg_FinancialAccount_ListQuery3;
import com.pinting.business.hessian.manage.message.ReqMsg_FinancialAccount_SaleAgentData;
import com.pinting.business.hessian.manage.message.ReqMsg_FinancialAccount_SaleReceivable;
import com.pinting.business.hessian.manage.message.ResMsg_FinancialAccount_ListQuery1;
import com.pinting.business.hessian.manage.message.ResMsg_FinancialAccount_ListQuery2;
import com.pinting.business.hessian.manage.message.ResMsg_FinancialAccount_ListQuery3;
import com.pinting.business.hessian.manage.message.ResMsg_FinancialAccount_SaleAgentData;
import com.pinting.business.hessian.manage.message.ResMsg_FinancialAccount_SaleReceivable;
import com.pinting.business.model.BsAgent;
import com.pinting.business.model.BsAgentExample;
import com.pinting.business.model.BsFinanceWithdrawRecord;
import com.pinting.business.model.BsSysSubAccount;
import com.pinting.business.model.BsSysSubAccountExample;
import com.pinting.business.service.manage.FinancialAccountService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;

/**
 * 
 * @author HuanXiong
 * @version $Id: FinancialAccountServiceImpl.java, v 0.1 2016-2-15 下午12:20:04 HuanXiong Exp $
 */
@Service
public class FinancialAccountServiceImpl implements FinancialAccountService {

    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private BsAgentMapper bsAgentMapper;
    @Autowired
    private BsSysSubAccountService bsSysSubAccountService;
    @Autowired
    private BsFinanceWithdrawRecordMapper bsFinanceWithdrawRecordMapper;
    @Autowired
    private BsSysSubAccountMapper bsSysSubAccountMapper;
    @Autowired
    private BsBatchBuyMapper bsBatchBuyMapper;
    
    /** 
     * @see com.pinting.business.service.manage.FinancialAccountService#listQuery2(com.pinting.business.hessian.manage.message.ReqMsg_FinancialAccount_ListQuery2, com.pinting.business.hessian.manage.message.ResMsg_FinancialAccount_ListQuery2)
     */
    @Override
    public void listQuery2(ReqMsg_FinancialAccount_ListQuery2 req,
                           ResMsg_FinancialAccount_ListQuery2 res) {
        Map<String, String> map = getStartTimeAndEndTime(req.getTime(), req.getType());
        String startTime = map.get("startTime");
        String endTime = map.get("endTime");
        Integer count = bsSubAccountMapper.countFinancialAccount2(req.getType(), startTime, endTime);
        List<FinancialAccountVO> list = bsSubAccountMapper.selectFinancialAccount2(req.getType(), startTime, endTime, req.getStart(), req.getNumPerPage());
        Double totalInvestAmount = bsSubAccountMapper.sumTotalInvestAmountFinancialAccount2();
        Double totalReturnInterestAmount = bsSubAccountMapper.sumCurrentMonthInterestReturnAmount();
        res.setFinancialAccountList(BeanUtils.classToArrayList(list));
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        res.setTotalInvestAmount(decimalFormat.format(totalInvestAmount));
        res.setTotalReturnInterestAmount(decimalFormat.format(totalReturnInterestAmount));
        res.setCount(count);
    }
    
    private Map<String, String> getStartTimeAndEndTime(String time, String type) {
        String startTime = null;
        String endTime = null;
        Map<String, String> result = new HashMap<String, String>();
        if("day".equals(type)) {
            if(StringUtil.isNotBlank(time)) {
                startTime = time;
            }
        } else if("month".equals(type)) {
            if(StringUtil.isNotBlank(time)) {
                startTime = time + "-1";
                Date endDate = DateUtil.addMonths(DateUtil.parse(time, "yyyy-MM"), 1);
                endTime = DateUtil.formatDateTime(endDate, "yyyy-MM-dd");
            }
        } else if("year".equals(type)) {
            if(StringUtil.isNotBlank(time)) {
                startTime = time;
            }
        }
        result.put("startTime", startTime);
        result.put("endTime", endTime);
        return result;
    }

	@Override
	public void listQuery1(ReqMsg_FinancialAccount_ListQuery1 req,
			ResMsg_FinancialAccount_ListQuery1 res) {
        Map<String, String> map = getStartTimeAndEndTime(req.getTime(), "month");
        String startTime = map.get("startTime");
        String endTime = map.get("endTime");
        Double sumTotalInvestAmount = bsSubAccountMapper.sumTotalInvestAmountFinancialAccount1();
        Integer count = bsSubAccountMapper.countFinancialAccount1(startTime, endTime);
        List<FinancialAccountVO> list = bsSubAccountMapper.selectFinancialAccount1(startTime, endTime, req.getStart(), req.getNumPerPage());
        res.setFinancialAccountList(BeanUtils.classToArrayList(list));
        res.setCount(count);
        res.setSumTotalInvestAmount(sumTotalInvestAmount);
	}

	@Override
	public void listQuery3(ReqMsg_FinancialAccount_ListQuery3 req,
			ResMsg_FinancialAccount_ListQuery3 res) {
		Double totalInvestAmount = 0.00 ;
		Double totalReturnAmount = 0.00;
		Integer count = 0;
		List<FinancialAccountVO> list = new ArrayList<FinancialAccountVO>();
		if("day".equals(req.getType())) {
			//精确到日
			if(StringUtil.isNotBlank(req.getTime())) {
				count = bsSubAccountMapper.countFinancialAccount3Day(req.getTime());
				if(count > 0){
					list = bsSubAccountMapper.selectFinancialAccount3Day(req.getTime(), req.getStart(), req.getNumPerPage());
					totalInvestAmount = bsSubAccountMapper.sumInvestAmountDay3(req.getTime());
			        totalReturnAmount = bsSubAccountMapper.sumReturnAmountDay3(req.getTime());
				}
			}
        }else if("month".equals(req.getType())){
        	//精确到月
        	if(StringUtil.isNotBlank(req.getTime())) {
                String endTime = req.getTime() + "-1";
                count = bsSubAccountMapper.countFinancialAccount3Month(endTime);
    			if(count > 0){
    				list = bsSubAccountMapper.selectFinancialAccount3Month(endTime, req.getStart(), req.getNumPerPage());
    				totalInvestAmount = bsSubAccountMapper.sumInvestAmountMonth3(endTime);
    		        totalReturnAmount = bsSubAccountMapper.sumReturnAmountMonth3(endTime);
    			}
            }
        	
        }
		res.setFinancialAccountList(BeanUtils.classToArrayList(list));
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        res.setTotalInvestAmount(decimalFormat.format(totalInvestAmount));
        res.setTotalReturnInterestAmount(decimalFormat.format(totalReturnAmount));
        res.setCount(count);
		
	}

    /** 
     * @see com.pinting.business.service.manage.FinancialAccountService#saleReceivable(com.pinting.business.hessian.manage.message.ReqMsg_FinancialAccount_SaleReceivable, com.pinting.business.hessian.manage.message.ResMsg_FinancialAccount_SaleReceivable)
     */
    @Override
    public void saleReceivable(ReqMsg_FinancialAccount_SaleReceivable req,
                               ResMsg_FinancialAccount_SaleReceivable res) {
/*        Map<String, String> map = getStartTimeAndEndTime(req.getTime(), req.getType());
        String startTime = map.get("startTime");
        String endTime = map.get("endTime");*/
        String startTime =req.getStartTime();
        String endTime = req.getEndTime();
        List<SaleReceivableVO> vos = bsSubAccountMapper.selectSaleReceivables(startTime, endTime, req.getStart(), req.getNumPerPage());
        Integer count = bsSubAccountMapper.countSaleReceivables(req.getType(), startTime, endTime);
        res.setSaleReceivableList(BeanUtils.classToArrayList(vos));
        res.setCount(count);
    }

    /** 
     * @see com.pinting.business.service.manage.FinancialAccountService#selectSaleAgentData(com.pinting.business.hessian.manage.message.ReqMsg_FinancialAccount_SaleAgentData, com.pinting.business.hessian.manage.message.ResMsg_FinancialAccount_SaleAgentData)
     */
    @Override
    public void selectSaleAgentData(ReqMsg_FinancialAccount_SaleAgentData req,
                                    ResMsg_FinancialAccount_SaleAgentData res) {
        List<SaleAgentDataVO> vos = bsSubAccountMapper.selectSaleAgentData(req.getAgentId(), req.getStartTime(), req.getEndTime(), req.getStart(), req.getNumPerPage());
        Double sumCP = bsSubAccountMapper.sumCPSaleAgentData(req.getAgentId(), req.getStartTime(), req.getEndTime(), req.getStart(), req.getNumPerPage());
        
        Integer count = bsSubAccountMapper.countSaleAgentData(req.getAgentId(), req.getStartTime(), req.getEndTime());
        BsAgentExample agentExample = new BsAgentExample();
        agentExample.setOrderByClause("id desc");
        List<BsAgent> agents = bsAgentMapper.selectByExample(agentExample);
        
        
        res.setSaleAgentData(BeanUtils.classToArrayList(vos));
        res.setSumCpAgentData(sumCP);
        res.setAgents(BeanUtils.classToArrayList(agents));
        res.setCount(count);
    }

    /** 
     */
    @Override
    public void financialRegistry(Double amount, String withdrawTime, Integer opUserId) {
        // 校验数据开始
        if(amount == null || amount == 0) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        if(StringUtil.isBlank(withdrawTime)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        if(opUserId == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        // 校验数据结束
        BsFinanceWithdrawRecord financeWithdrawRecord = new BsFinanceWithdrawRecord();
        financeWithdrawRecord.setAmount(amount);
        financeWithdrawRecord.setCreateTime(new Date());
        financeWithdrawRecord.setOpUserId(opUserId);
        financeWithdrawRecord.setWithdrawTime(DateUtil.parse(withdrawTime, "yyyy-MM-dd HH:mm:ss"));
        bsFinanceWithdrawRecordMapper.insertSelective(financeWithdrawRecord);
        
    }

    @Override
    public List<FinanceWithdrawRecordVO> financeWithdrawRecords(FinanceWithdrawRecordVO req) {
        return bsFinanceWithdrawRecordMapper.selectFinanceWithdrawRecords(req.getStartTime(), req.getEndTime(), 
            req.getStart(), req.getNumPerPage());
    }

    /** 
     * @see com.pinting.business.service.manage.FinancialAccountService#countFinanceWithdrawRecords(com.pinting.business.model.vo.FinanceWithdrawRecordVO)
     */
    @Override
    public Integer countFinanceWithdrawRecords(FinanceWithdrawRecordVO req) {
        return bsFinanceWithdrawRecordMapper.countFinanceWithdrawRecords(req.getStartTime(), req.getEndTime());
    }

    /** 
     * @see com.pinting.business.service.manage.FinancialAccountService#confirmFinancialRegistry(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public void confirmFinancialRegistry(Integer id, Integer confirmUserId) {
        BsFinanceWithdrawRecord financeWithdrawRecord = bsFinanceWithdrawRecordMapper.selectByPrimaryKey(id);
        financeWithdrawRecord.setConfirmTime(new Date());
        financeWithdrawRecord.setConfirmUserId(confirmUserId);
        bsFinanceWithdrawRecordMapper.updateByPrimaryKey(financeWithdrawRecord);
        bsSysSubAccountService.financialRegistry(financeWithdrawRecord.getAmount(), confirmUserId);
    }

    @Override
    public Map<String, Object> queryAccountData() {
        return bsSubAccountMapper.selectAccountData();
    }

    @Override
    public List<FinanceOverviewForBeijingVO> queryFinanceOverviewForBeijingList(String startTime, String endTime, Integer pageNum, Integer numPerPage) {
        List<FinanceOverviewForBeijingVO> result = new ArrayList<>();
        int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage); // mysql的分页;
//        List<FinanceOverviewDetailForBeijingVO> totalBuyAmountList = bsSubAccountMapper.selectTotalBuyAmount(startTime, endTime, start, numPerPage);
        List<FinanceOverviewDetailForBeijingVO> buyAmountForYunDaiList = bsSubAccountMapper.selectBuyAmountForYunDai(startTime, endTime, start, numPerPage);
        List<FinanceOverviewDetailForBeijingVO> buyAmountFor7DaiList = bsSubAccountMapper.selectBuyAmountFor7Dai(startTime, endTime, start, numPerPage);
        List<FinanceOverviewDetailForBeijingVO> buyAmountForZanList = bsSubAccountMapper.selectBuyAmountForZan(startTime, endTime, start, numPerPage);
//        List<FinanceOverviewDetailForBeijingVO> totalReturnAmountList = bsSubAccountMapper.selectTotalReturnAmount(startTime, endTime, start, numPerPage);
        List<FinanceOverviewDetailForBeijingVO> returnAmountForYunDaiList = bsSubAccountMapper.selectReturnAmountForYunDai(startTime, endTime, start, numPerPage);
        List<FinanceOverviewDetailForBeijingVO> returnAmountFor7DaiList = bsSubAccountMapper.selectReturnAmountFor7Dai(startTime, endTime, start, numPerPage);
        List<FinanceOverviewDetailForBeijingVO> returnAmountForZanList = bsSubAccountMapper.selectReturnAmountForZan(startTime, endTime, start, numPerPage);
//        List<FinanceOverviewDetailForBeijingVO> totalFinanceAmountList = bsSubAccountMapper.selectTotalFinanceAmount(startTime, endTime, start, numPerPage);
        List<FinanceOverviewDetailForBeijingVO> financeAmountToYunDaiList = bsSubAccountMapper.selectFinanceAmountToYunDai(startTime, endTime, start, numPerPage);
        List<FinanceOverviewDetailForBeijingVO> financeAmountTo7DaiList = bsSubAccountMapper.selectFinanceAmountTo7Dai(startTime, endTime, start, numPerPage);
        List<FinanceOverviewDetailForBeijingVO> financeAmountToZanList = bsSubAccountMapper.selectFinanceAmountToZan(startTime, endTime, start, numPerPage);

        Date startDate = DateUtil.parse(startTime, "yyyy-MM-dd");
        Date endDate = DateUtil.parse(endTime, "yyyy-MM-dd");


        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        endCalendar.add(Calendar.DAY_OF_MONTH, -start);
        Calendar betweenCalendar = Calendar.getInstance();
        betweenCalendar.setTime(startDate);
        while (true) {
            FinanceOverviewForBeijingVO detail = new FinanceOverviewForBeijingVO();
            String endTimeYYYYMMDD = DateUtil.formatYYYYMMDD(endCalendar.getTime());

            for (FinanceOverviewDetailForBeijingVO vo : buyAmountForYunDaiList) {
                if(endTimeYYYYMMDD.equals(DateUtil.formatYYYYMMDD(vo.getTime()))) {
                    // 当日云贷理财金额
                    detail.setTime(DateUtil.formatYYYYMMDD(vo.getTime()));
                    detail.setBuyAmountForYunDai(vo.getAmount() == null ? 0d : vo.getAmount());break;
                }
            }
            for (FinanceOverviewDetailForBeijingVO vo : buyAmountFor7DaiList) {
                if(endTimeYYYYMMDD.equals(DateUtil.formatYYYYMMDD(vo.getTime()))) {
                    // 当日七贷理财金额
                    detail.setTime(DateUtil.formatYYYYMMDD(vo.getTime()));
                    detail.setBuyAmountFor7Dai(vo.getAmount() == null ? 0d : vo.getAmount());break;
                }
            }
            for (FinanceOverviewDetailForBeijingVO vo : buyAmountForZanList) {
                if(endTimeYYYYMMDD.equals(DateUtil.formatYYYYMMDD(vo.getTime()))) {
                    detail.setTime(DateUtil.formatYYYYMMDD(vo.getTime()));
                    // 当日赞分期理财金额
                    detail.setBuyAmountForZan(vo.getAmount() == null ? 0d : vo.getAmount());break;
                }
            }
            // 当日理财总金额
            detail.setTotalBuyAmount(MoneyUtil.add(MoneyUtil.add(detail.getBuyAmountForYunDai(), detail.getBuyAmountFor7Dai()).doubleValue(), detail.getBuyAmountForZan()).doubleValue());

            for (FinanceOverviewDetailForBeijingVO vo : returnAmountForYunDaiList) {
                if(endTimeYYYYMMDD.equals(DateUtil.formatYYYYMMDD(vo.getTime()))) {
                    detail.setTime(DateUtil.formatYYYYMMDD(vo.getTime()));
                    // 当日云贷赎回金额
                    detail.setReturnAmountForYunDai(vo.getAmount() == null ? 0d : vo.getAmount());break;
                }
            }
            for (FinanceOverviewDetailForBeijingVO vo : returnAmountFor7DaiList) {
                if(endTimeYYYYMMDD.equals(DateUtil.formatYYYYMMDD(vo.getTime()))) {
                    detail.setTime(DateUtil.formatYYYYMMDD(vo.getTime()));
                    // 当日7贷赎回金额
                    detail.setReturnAmountFor7Dai(vo.getAmount() == null ? 0d : vo.getAmount());break;
                }
            }
            for (FinanceOverviewDetailForBeijingVO vo : returnAmountForZanList) {
                if(endTimeYYYYMMDD.equals(DateUtil.formatYYYYMMDD(vo.getTime()))) {
                    detail.setTime(DateUtil.formatYYYYMMDD(vo.getTime()));
                    // 当日赞分期赎回金额
                    detail.setReturnAmountForZan(vo.getAmount() == null ? 0d : vo.getAmount());break;
                }
            }
            // 当日赎回总金额
            detail.setTotalReturnAmount(MoneyUtil.add(MoneyUtil.add(detail.getReturnAmountForYunDai(), detail.getReturnAmountFor7Dai()).doubleValue(), detail.getReturnAmountForZan()).doubleValue());
            for (FinanceOverviewDetailForBeijingVO vo : financeAmountToYunDaiList) {
                if(endTimeYYYYMMDD.equals(DateUtil.formatYYYYMMDD(vo.getTime()))) {
                    detail.setTime(DateUtil.formatYYYYMMDD(vo.getTime()));
                    // 当日云贷融资金额
                    detail.setFinanceAmountToYunDai(vo.getAmount() == null ? 0d : vo.getAmount());break;
                }
            }
            for (FinanceOverviewDetailForBeijingVO vo : financeAmountTo7DaiList) {
                if(endTimeYYYYMMDD.equals(DateUtil.formatYYYYMMDD(vo.getTime()))) {
                    detail.setTime(DateUtil.formatYYYYMMDD(vo.getTime()));
                    // 当日7贷融资金额
                    detail.setFinanceAmountTo7Dai(vo.getAmount() == null ? 0d : vo.getAmount());break;
                }
            }
            for (FinanceOverviewDetailForBeijingVO vo : financeAmountToZanList) {
                if(endTimeYYYYMMDD.equals(DateUtil.formatYYYYMMDD(vo.getTime()))) {
                    detail.setTime(DateUtil.formatYYYYMMDD(vo.getTime()));
                    // 当日赞分期融资金额
                    detail.setFinanceAmountToZan(vo.getAmount() == null ? 0d : vo.getAmount());break;
                }
            }
            // 当日融资总金额
            detail.setTotalFinanceAmount(MoneyUtil.add(MoneyUtil.add(detail.getFinanceAmountToYunDai(), detail.getFinanceAmountTo7Dai()).doubleValue(), detail.getFinanceAmountToZan()).doubleValue());

            if(null == detail.getTime()) {
            } else {
                result.add(detail);
            }
            endCalendar.add(Calendar.DAY_OF_MONTH, -1);
            if (betweenCalendar.getTimeInMillis() > endCalendar.getTimeInMillis())
                break;
        }
        Collections.sort(result, new Comparator<FinanceOverviewForBeijingVO>() {
            @Override
            public int compare(FinanceOverviewForBeijingVO o1, FinanceOverviewForBeijingVO o2) {
                Date date1 = DateUtil.parse(o1.getTime(), "yyyy-MM-dd");
                Date date2 = DateUtil.parse(o2.getTime(), "yyyy-MM-dd");
                if(date2.after(date1)) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        return result;
    }

    @Override
    public int countFinanceOverviewForBeijingList(String startTime, String endTime) {
        List<Integer> countList = new ArrayList<>();
        Integer a = bsSubAccountMapper.countBuyAmountForYunDai(startTime, endTime);
        Integer b = bsSubAccountMapper.countBuyAmountFor7Dai(startTime, endTime);
        Integer c = bsSubAccountMapper.countBuyAmountForZan(startTime, endTime);
        Integer d = bsSubAccountMapper.countReturnAmountForYunDai(startTime, endTime);
        Integer e = bsSubAccountMapper.countReturnAmountFor7Dai(startTime, endTime);
        Integer f = bsSubAccountMapper.countReturnAmountForZan(startTime, endTime);
        Integer g = bsSubAccountMapper.countFinanceAmountToYunDai(startTime, endTime);
        Integer h = bsSubAccountMapper.countFinanceAmountTo7Dai(startTime, endTime);
        Integer i = bsSubAccountMapper.countFinanceAmountToZan(startTime, endTime);
        countList.add(a == null ? 0 : a);
        countList.add(b == null ? 0 : b);
        countList.add(c == null ? 0 : c);
        countList.add(d == null ? 0 : d);
        countList.add(e == null ? 0 : e);
        countList.add(f == null ? 0 : f);
        countList.add(g == null ? 0 : g);
        countList.add(h == null ? 0 : h);
        countList.add(i == null ? 0 : i);
        int max = Collections.max(countList);
        return max;
    }

    @Override
    public FinanceOverviewForBeijingVO queryFinanceOverviewForForBeijingTotal(String startTime, String endTime) {
        FinanceOverviewForBeijingVO result = new FinanceOverviewForBeijingVO();

        Double a = bsSubAccountMapper.sumBuyAmountForZan(startTime, endTime);
        Double b = bsSubAccountMapper.sumBuyAmountFor7Dai(startTime, endTime);
        Double c = bsSubAccountMapper.sumReturnAmountForYunDai(startTime, endTime);
        Double d = bsSubAccountMapper.sumReturnAmountFor7Dai(startTime, endTime);
        Double e = bsSubAccountMapper.sumReturnAmountForZan(startTime, endTime);
        Double f = bsSubAccountMapper.sumFinanceAmountToYunDai(startTime, endTime);
        Double g = bsSubAccountMapper.sumFinanceAmountTo7Dai(startTime, endTime);
        Double h = bsSubAccountMapper.sumFinanceAmountToZan(startTime, endTime);
        Double i = bsSubAccountMapper.sumBuyAmountForYunDai(startTime, endTime);

        result.setBuyAmountForZan(a == null ? 0d : a);
        result.setBuyAmountFor7Dai(b == null ? 0d : b);
        result.setBuyAmountForYunDai(i == null ? 0d : i);

        result.setReturnAmountFor7Dai(d == null ? 0d : d);
        result.setReturnAmountForYunDai(c == null ? 0d : c);
        result.setReturnAmountForZan(e == null ? 0d : e);
        result.setFinanceAmountTo7Dai(g == null ? 0d : g);
        result.setFinanceAmountToYunDai(f == null ? 0d : f);
        result.setFinanceAmountToZan(h == null ? 0d : h);

        result.setTotalBuyAmount(MoneyUtil.add(MoneyUtil.add(result.getBuyAmountForYunDai(), result.getBuyAmountFor7Dai()).doubleValue(), result.getBuyAmountForZan()).doubleValue());
        result.setTotalReturnAmount(MoneyUtil.add(MoneyUtil.add(result.getReturnAmountForYunDai(), result.getReturnAmountFor7Dai()).doubleValue(), result.getReturnAmountForZan()).doubleValue());
        result.setTotalFinanceAmount(MoneyUtil.add(MoneyUtil.add(result.getFinanceAmountToYunDai(), result.getFinanceAmountTo7Dai()).doubleValue(), result.getFinanceAmountToZan()).doubleValue());

        return result;
    }
    
    @Override
    public Double queryTotalFinancialManagement() {
        Double a = bsSubAccountMapper.sumTotalAmountForYunDai();
        Double b = bsSubAccountMapper.sumTotalAmountFor7Dai();
        Double c = bsSubAccountMapper.sumTotalAmountForZan();
        Double result = MoneyUtil.add(MoneyUtil.add((a == null ? 0d : a), (b == null ? 0d : b)).doubleValue() , (c == null ? 0d : c)).doubleValue();
        return result;
    }

    @Override
    public List<SysNotReceivableVO> querySysNotReceivableInfo(String dateTime) {
        List<SysNotReceivableVO> resultList = new ArrayList<SysNotReceivableVO>();
        //默认查询当天时间
        if(StringUtil.isBlank(dateTime)) {
            dateTime = DateUtil.formatYYYYMMDD(new Date());
            resultList = bsBatchBuyMapper.selectSysNotReceivableInfo(dateTime);
        }else {
            resultList = bsBatchBuyMapper.selectSysNotReceivableInfo(dateTime);
        }
        return resultList.size() > 0 ? resultList : null;
    }
}
