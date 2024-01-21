/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.service.manage.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsAccountMapper;
import com.pinting.business.dao.BsAgentMapper;
import com.pinting.business.dao.BsBankCardMapper;
import com.pinting.business.dao.BsDailyStatMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.manage.message.ReqMsg_Data_DataStatistics;
import com.pinting.business.hessian.manage.message.ReqMsg_Data_HistoryData;
import com.pinting.business.hessian.manage.message.ResMsg_Data_DataStatistics;
import com.pinting.business.hessian.manage.message.ResMsg_Data_HistoryData;
import com.pinting.business.model.BsAccount;
import com.pinting.business.model.BsAccountExample;
import com.pinting.business.model.BsAgent;
import com.pinting.business.model.BsAgentExample;
import com.pinting.business.model.BsBankCard;
import com.pinting.business.model.BsDailyStat;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.vo.DailyStatVO;
import com.pinting.business.service.manage.DataStatisticsService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;

/**
 * 
 * @author HuanXiong
 * @version $Id: DataStatisticsServiceImpl.java, v 0.1 2015-12-14 下午7:19:28 HuanXiong Exp $
 */
@Service
public class DataStatisticsServiceImpl implements DataStatisticsService {
    
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private BsAccountMapper bsAccountMapper;
    @Autowired
    private BsDailyStatMapper bsDailyStatMapper;
    @Autowired
    private BsAgentMapper bsAgentMapper;
    @Autowired
    private BsBankCardMapper bsBankCardMapper;
    
    /** 
     * @see com.pinting.business.service.manage.DataStatisticsService#dataStatistics(com.pinting.business.hessian.manage.message.ReqMsg_Data_DataStatistics, com.pinting.business.hessian.manage.message.ResMsg_Data_DataStatistics)
     */
    @Override
    public void dataStatistics(ReqMsg_Data_DataStatistics reqMsg, ResMsg_Data_DataStatistics resMsg) {
    	//选择查询的渠道列表
    	List<Object> agentIds = null;
    	if(StringUtil.isNotEmpty(reqMsg.getAgentIds())) {
			String[] ids = reqMsg.getAgentIds().split(",");
			if(ids.length > 0) {
				agentIds = new ArrayList<Object>();
				for (String str : ids) {
					if(StringUtil.isNotEmpty(str)) {
						agentIds.add(Integer.valueOf(str));
					}
				}
			}
		}
        
        // 日期格式化
        Date now = new Date();
        String dateStrYYYYMMDD = DateUtil.formatDateTime(now, "yyyy-MM-dd");
        Date dateAfter = DateUtil.parse(dateStrYYYYMMDD, "yyyy-MM-dd"); 
        Date dateCurrent = DateUtil.addDays(dateAfter, -1);     // 当前统计的数据时间
        // 实时查询累计数据
        Map<String, Object> totalDatas = this.queryTotalDatas(now);
        resMsg.setTotalDatas(totalDatas);
        
        // 2、图表数据
        String startTime;
        String endTime;
        
        // 2.1、按日查询
        if ("day".equals(reqMsg.getTimeType())) {
            List<BsDailyStat> dailyStats = new ArrayList<BsDailyStat>();
            // 时间区间不为空，则取时间区间，否则，按最近几天查询
            if(!StringUtil.isBlank(reqMsg.getStartTime())) {
                startTime = reqMsg.getStartTime();
                endTime = reqMsg.getEndTime();
                dailyStats = bsDailyStatMapper.selectDailyDataCharts(agentIds,reqMsg.getNonAgentId(), reqMsg.getStartTime(), reqMsg.getEndTime());
            } else {
                Integer currenDays = Integer.parseInt(reqMsg.getCurrentDays());
                Date startDate = DateUtil.addDays(dateCurrent, -currenDays+1);
                startTime = DateUtil.formatDateTime(startDate, "yyyy-MM-dd");
                endTime = DateUtil.formatDateTime(dateCurrent, "yyyy-MM-dd");
                reqMsg.setStartTime(startTime);
                reqMsg.setEndTime(endTime);
                dailyStats = bsDailyStatMapper.selectDailyDataCharts(agentIds,reqMsg.getNonAgentId(), startTime, endTime);
            }
            List<DailyStatVO> list = new ArrayList<DailyStatVO>();
            List<DailyStatVO> dataTables = new ArrayList<DailyStatVO>();
            Date startDate = DateUtil.parse(startTime, "yyyy-MM-dd");
            Date endDate = DateUtil.parse(endTime, "yyyy-MM-dd");
            Calendar betweenCalendar = Calendar.getInstance();
            betweenCalendar.setTime(startDate);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endDate);
            while (true) {
                DailyStatVO vo = new DailyStatVO();
                Date betweenDate = betweenCalendar.getTime();
                String betweenTime = DateUtil.formatDateTime(betweenDate, "yyyy-MM-dd");
                vo.setTime(betweenTime);
                for (BsDailyStat dailyStat : dailyStats) {
                    String date = DateUtil.formatDateTime(dailyStat.getStatDate(), "yyyy-MM-dd");
                    if (betweenTime.equals(date)) {
                        vo.setDailyNewRegistUser(dailyStat.getDailyNewRegistUser() == null ? 0 : dailyStat.getDailyNewRegistUser());
                        vo.setDailyNewUserInvestAmount(dailyStat.getDailyNewUserInvestAmount() == null ? 0d : dailyStat.getDailyNewUserInvestAmount());
                        vo.setDailyOldUserInvestAmount(dailyStat.getDailyOldUserInvestAmount() == null ? 0d : dailyStat.getDailyOldUserInvestAmount());
                        vo.setDailyTotalInvestAmount(MoneyUtil.add(vo.getDailyNewUserInvestAmount(), vo.getDailyOldUserInvestAmount()).doubleValue());
                        vo.setDailyNewBindUser(dailyStat.getDailyNewBindUser() == null ? 0 : dailyStat.getDailyNewBindUser());
                        vo.setDailyNewInvestUser(dailyStat.getDailyNewInvestUser() == null ? 0 : dailyStat.getDailyNewInvestUser());
                        vo.setDailyNewUserInvestAnnual(dailyStat.getDailyNewUserInvestAnnual() == null ? "0.00" : MoneyUtil.format(dailyStat.getDailyNewUserInvestAnnual()));
                        vo.setDailyInvestUser(dailyStat.getDailyInvestUser() == null ? 0 : dailyStat.getDailyInvestUser());
                        vo.setDailyInvestAnnual(dailyStat.getDailyInvestAnnual() == null ? "0.00" : MoneyUtil.format(dailyStat.getDailyInvestAnnual()));
                        break;
                    } else {
                        vo.setDailyNewRegistUser(0);
                        vo.setDailyNewUserInvestAmount(0d);
                        vo.setDailyOldUserInvestAmount(0d);
                        vo.setTotalInvestAmount("0.0");
                        
                        vo.setDailyNewBindUser(0);
                        vo.setDailyNewInvestUser(0);
                        vo.setDailyNewUserInvestAnnual("0.00");
                        vo.setDailyInvestUser(0);
                        vo.setDailyInvestAnnual("0.00");
                    }
                }
                list.add(vo);
                dataTables.add(vo);
                betweenCalendar.add(Calendar.DAY_OF_MONTH, 1);
                if (betweenCalendar.getTimeInMillis() > endCalendar.getTimeInMillis())
                    break;
            }
            Collections.sort(dataTables, new Comparator<DailyStatVO>() {
                @Override
                public int compare(DailyStatVO o1, DailyStatVO o2) {
                    Date date1 = DateUtil.parse(o1.getTime(), "yyyy-MM-dd");
                    Date date2 = DateUtil.parse(o2.getTime(), "yyyy-MM-dd");
                    if(date2.after(date1)) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
            resMsg.setChartDatas(BeanUtils.classToArrayList(list));
            resMsg.setDataTables(BeanUtils.classToArrayList(dataTables));
        }
        // 2.2、按时查询
        else if("hour".equals(reqMsg.getTimeType())) {
            // 当日新注册用户
            List<BsAccount> accounts = bsAccountMapper.selectDailyNewRegistUser(reqMsg.getStartTime(), agentIds,reqMsg.getNonAgentId());
            // 当日新用户购买金额 + 16/新投资用户年化投资金额
            List<BsSubAccount> bsNewSubAccounts = bsSubAccountMapper.selectDailyNewUserInvestAmount(reqMsg.getStartTime(), agentIds,reqMsg.getNonAgentId());
            // 当日老用户购买金额
            List<BsSubAccount> bsOldSubAccounts = bsSubAccountMapper.selectDailyOldUserInvestAmount(reqMsg.getStartTime(), agentIds,reqMsg.getNonAgentId());
            // 13、新增绑卡用户数 TODO
            List<BsBankCard> dailyNewBindUserCounts = bsBankCardMapper.countDailyBindCardUser(reqMsg.getStartTime(), agentIds,reqMsg.getNonAgentId());
            // 14、新增投资用户数
            List<BsSubAccount> dailyNewInvestUserCounts = bsSubAccountMapper.selectDailyNewInvestUser(reqMsg.getStartTime(), agentIds,reqMsg.getNonAgentId());
            // 15、投资用户数 
            List<BsSubAccount> dailyInvestUserCounts = bsSubAccountMapper.selectDailyInvestUser(reqMsg.getStartTime(), agentIds,reqMsg.getNonAgentId());
            // 17、年化投资金额 
            List<BsSubAccount> bsInvestAnnual = bsSubAccountMapper.selectInvestAnnual(reqMsg.getStartTime(), agentIds,reqMsg.getNonAgentId());
            
            List<DailyStatVO> vos = new ArrayList<DailyStatVO>();
            List<DailyStatVO> dataTables = new ArrayList<DailyStatVO>();
            
            // 当天每小时的数据
            int hours = 23; // 一天24小时
            for (int i = 0; i <= hours; i++) {
                DailyStatVO vo = new DailyStatVO();
                vo.setTime(i + ":00");
                vo.setDailyNewRegistUser(0);
                vo.setDailyNewUserInvestAmount(0d);
                vo.setDailyOldUserInvestAmount(0d);
                Double dailyInvestAnnual = 0d;
                Double dailyNewUserInvestAnnual = 0d;
                // 新注册用户数
                for (BsAccount bsAccount : accounts) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(bsAccount.getOpenTime());
                    if (calendar.get(Calendar.HOUR_OF_DAY) == i) {
                        vo.setDailyNewRegistUser(vo.getDailyNewRegistUser() == null ? 1 : vo.getDailyNewRegistUser()+1);
                    }
                }
                // 当日新用户购买金额+年化金额
                for (BsSubAccount bsSubAccount : bsNewSubAccounts) {
                    Calendar calendar = Calendar.getInstance();
                    if(bsSubAccount == null) {
                        break;
                    }
                    calendar.setTime(bsSubAccount.getOpenTime());
                    if (calendar.get(Calendar.HOUR_OF_DAY) == i) {
                        vo.setDailyNewUserInvestAmount(vo.getDailyNewUserInvestAmount() == null ? bsSubAccount.getBalance() : (MoneyUtil.add(bsSubAccount.getBalance(), vo.getDailyNewUserInvestAmount()).doubleValue()));
                        dailyNewUserInvestAnnual = dailyNewUserInvestAnnual == null ? bsSubAccount.getFreezeBalance() : MoneyUtil.add(bsSubAccount.getFreezeBalance(), dailyNewUserInvestAnnual).doubleValue();
                    }
                }
                // 当日老用户购买金额
                for (BsSubAccount bsSubAccount : bsOldSubAccounts) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(bsSubAccount.getOpenTime());
                    if (calendar.get(Calendar.HOUR_OF_DAY) == i) {
                        vo.setDailyOldUserInvestAmount(vo.getDailyOldUserInvestAmount() == null ? bsSubAccount.getBalance() : (MoneyUtil.add(bsSubAccount.getBalance(), vo.getDailyOldUserInvestAmount()).doubleValue()));
                    }
                }
                // 当日绑卡用户数
                for (BsBankCard card : dailyNewBindUserCounts) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(card.getBindTime());
                    if (calendar.get(Calendar.HOUR_OF_DAY) == i) {
                        vo.setDailyNewBindUser(vo.getDailyNewBindUser() == null ? 1 : vo.getDailyNewBindUser() + 1);
                    }
                }
                // 新增投资用户数
                for (BsSubAccount bsSubAccount : dailyNewInvestUserCounts) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(bsSubAccount.getOpenTime());
                    if (calendar.get(Calendar.HOUR_OF_DAY) == i) {
                        vo.setDailyNewInvestUser(vo.getDailyNewInvestUser() == null ? 1 : vo.getDailyNewInvestUser() + 1);
                    }
                }
                // 投资用户数
                for (BsSubAccount bsSubAccount : dailyInvestUserCounts) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(bsSubAccount.getOpenTime());
                    if (calendar.get(Calendar.HOUR_OF_DAY) == i) {
                        vo.setDailyInvestUser(vo.getDailyInvestUser() == null ? 1 : vo.getDailyInvestUser() + 1);
                    }
                }
                // 年化投资金额
                for (BsSubAccount bsSubAccount : bsInvestAnnual) {
                    Calendar calendar = Calendar.getInstance();
                    if(bsSubAccount== null) {
                        break;
                    }
                    calendar.setTime(bsSubAccount.getOpenTime());
                    if (calendar.get(Calendar.HOUR_OF_DAY) == i) {
                        dailyInvestAnnual = dailyInvestAnnual == null ? bsSubAccount.getBalance() : MoneyUtil.add(bsSubAccount.getBalance(), dailyInvestAnnual).doubleValue();
                    }
                }
                vo.setDailyNewUserInvestAmount(vo.getDailyNewUserInvestAmount() == null ? 0d : vo.getDailyNewUserInvestAmount());
                vo.setDailyOldUserInvestAmount(vo.getDailyOldUserInvestAmount() == null ? 0d : vo.getDailyOldUserInvestAmount());
                vo.setDailyTotalInvestAmount(MoneyUtil.add(vo.getDailyNewUserInvestAmount(), vo.getDailyOldUserInvestAmount()).doubleValue());
                vo.setDailyNewUserInvestAnnual(MoneyUtil.format(dailyNewUserInvestAnnual));
                vo.setDailyInvestAnnual(MoneyUtil.format(dailyInvestAnnual));
                vos.add(vo);
                dataTables.add(vo);
            }
            
            Collections.sort(dataTables, new Comparator<DailyStatVO>() {
                @Override
                public int compare(DailyStatVO o1, DailyStatVO o2) {
                    Date date1 = DateUtil.parse(o1.getTime(), "HH:mm");
                    Date date2 = DateUtil.parse(o2.getTime(), "HH:mm");
                    if(date2.after(date1)) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
            resMsg.setChartDatas(BeanUtils.classToArrayList(vos));
            resMsg.setDataTables(BeanUtils.classToArrayList(dataTables));
        }
        
        // 3、根据查询条件 显示
//        List<BsDailyStat> dailyStats = bsDailyStatMapper.selectDailyDataGrid(reqMsg.getStart(), reqMsg.getNumPerPage());
//        Integer count = bsDailyStatMapper.countDailyDataGrid();
//        resMsg.setDataTables(BeanUtils.classToArrayList(dailyStats));
//        resMsg.setCount(count);
    }

    /**
     * 实时查询累计数据
     * @param now
     * @return
     */
    private Map<String, Object> queryTotalDatas(Date now) {
        String dateStrYYYYMMDD = DateUtil.formatDateTime(now, "yyyy-MM-dd");
        Date dateCurrent = DateUtil.parse(dateStrYYYYMMDD, "yyyy-MM-dd");  // 当前统计的数据时间
        String dateStr = dateStrYYYYMMDD + " 00:00:00";
        Date date = DateUtil.parse(dateStr, "yyyy-MM-dd hh:mm:ss");
        DailyStatVO dailyStatVO = new DailyStatVO();
        
        // 1、累计注册人数 
        BsAccountExample accountExample = new BsAccountExample();
        Integer totalRegisterUser = bsAccountMapper.countByExample(accountExample);
        // 2、累计投资人数
        Integer totalInvestUser = bsSubAccountMapper.countTotalInvestUser();
        // 3、累计参与复投人数
        Integer totalReInvestUser = bsSubAccountMapper.countTotalReInvestUser();
        // 4、累计投资金额
        Double totalInvestAmount = bsSubAccountMapper.sumTotalInvestAmount();
        // 5、当日微信关注数
        // 6、当日新增用户数
        BsAccountExample bsAccountExample = new BsAccountExample();
        bsAccountExample.createCriteria().andOpenTimeBetween(date, DateUtil.addDays(date, 1));
        Integer dailyNewRegistUser = bsAccountMapper.countByExample(bsAccountExample);
        // 7、新增用户当日投资笔数
        Integer dailyNewUserInvestTimes = bsSubAccountMapper.countdailyNewInvestNumbers(DateUtil.format(DateUtil.addDays(dateCurrent, 1)));
        // 8、新增用户当日投资额
        Double dailyNewUserInvestAmount = bsSubAccountMapper.sumDailyNewInvestBalance(DateUtil.format(DateUtil.addDays(dateCurrent, 1)));
        // 9、当日老用户（第二次及以上投资的）投资笔数
        Integer dailyOldUserInvestTimes = bsSubAccountMapper.countDailyOldInvestTimes(DateUtil.format(DateUtil.addDays(dateCurrent, 1)));
        // 10、当日老用户（第二次及以上投资的）投资金额
        Double dailyOldUserInvestAmount = bsSubAccountMapper.sumDailyOldInvestBalance(DateUtil.format(DateUtil.addDays(dateCurrent, 1)));
        // 11、当日全部投资笔数  
        Integer dailyTotalInvestTimes = bsSubAccountMapper.countDailyTotalInvestTimes(DateUtil.format(DateUtil.addDays(dateCurrent, 1)));
        // 12、当日全部投资金额
        Double dailyTotalInvestAmount = bsSubAccountMapper.sumDailyTotalInvestBalance(DateUtil.format(DateUtil.addDays(dateCurrent, 1)));

       
        DecimalFormat df = new DecimalFormat("0.00");
        dailyStatVO.setTotalRegistUser(totalRegisterUser == null ? 0 : totalRegisterUser);
        dailyStatVO.setTotalInvestUser(totalInvestUser == null ? 0 : totalInvestUser);
        dailyStatVO.setTotalInvestAmount(df.format(totalInvestAmount));
        dailyStatVO.setTotalReinvestUser(totalReInvestUser == null ? 0 : totalReInvestUser);
        dailyStatVO.setDailyNewWxUser(0);
        dailyStatVO.setDailyNewRegistUser(dailyNewRegistUser);
        dailyStatVO.setDailyNewUserInvestTimes(dailyNewUserInvestTimes);
        dailyStatVO.setDailyNewUserInvestAmount(dailyNewUserInvestAmount);
        dailyStatVO.setDailyOldUserInvestTimes(dailyOldUserInvestTimes);
        dailyStatVO.setDailyOldUserInvestAmount(dailyOldUserInvestAmount);
        dailyStatVO.setDailyTotalInvestAmount(dailyTotalInvestAmount);
        dailyStatVO.setDailyTotalInvestTimes(dailyTotalInvestTimes);
        return BeanUtils.transBeanMap(dailyStatVO);
    }
    
    /** 
     * @see com.pinting.business.service.manage.DataStatisticsService#historyData(com.pinting.business.hessian.manage.message.ReqMsg_Data_HistoryData, com.pinting.business.hessian.manage.message.ResMsg_Data_HistoryData)
     */
    @Override
    public void historyData(ReqMsg_Data_HistoryData req, ResMsg_Data_HistoryData res) {
        modifiedHistoryDatas(req, res);
    }
    
    /**
     * 历史数据统计
     * @param req
     * @param res
     */
    private void modifiedHistoryDatas(ReqMsg_Data_HistoryData req, ResMsg_Data_HistoryData res) {
        try {
            // 日期格式化
            Date now = new Date();
            String dateStrYYYYMMDD = DateUtil.formatDateTime(now, "yyyy-MM-dd");
            Date dateAfter = DateUtil.parse(dateStrYYYYMMDD, "yyyy-MM-dd"); 
            Date dateCurrent = DateUtil.addDays(dateAfter, -1);     // 当前统计的数据时间
            
            if(StringUtil.isBlank(req.getStartTime()) && StringUtil.isBlank(req.getEndTime())) {
                req.setEndTime(DateUtil.formatYYYYMMDD(dateCurrent));
            }
            
            Date startTime = DateUtil.parse(req.getStartTime(), "yyyy-MM-dd");
            Date endTime = DateUtil.parse(req.getEndTime(), "yyyy-MM-dd");
            // 起止的所有时间
            List<Date> openTimes = new ArrayList<Date>();
            while(startTime.before(endTime)) {
                openTimes.add(startTime);
                startTime = DateUtil.addDays(startTime, 1);
            }
            openTimes.add(endTime);
            
            // 1、当日新增微信关注数
            // 2、当日新增用户数
            List<Map<String, Object>> dailyNewRegisterNum = bsAccountMapper.dailyNewRegisterNum(req.getStartTime(), req.getEndTime());
            // 3、新增用户当日投资笔数
            List<Map<String, Object>> dailyNewInvestNum = bsSubAccountMapper.dailyNewInvestNum(req.getStartTime(), req.getEndTime());
            // 4、新增用户当日投资额
            List<Map<String, Object>> dailyNewInvestAmount = bsSubAccountMapper.dailyNewInvestAmount(req.getStartTime(), req.getEndTime());
            // 5、当日老用户（第二次及以上投资的）投资笔数
            List<Map<String, Object>> dailyOldInvestNum = bsSubAccountMapper.dailyOldInvestNum(req.getStartTime(), req.getEndTime());
            // 6、当日老用户（第二次及以上投资的）投资金额
            List<Map<String, Object>> dailyOldInvestAmount = bsSubAccountMapper.dailyOldInvestAmount(req.getStartTime(), req.getEndTime());
            // 7、当日全部投资笔数  
            List<Map<String, Object>> dailyTotalInvestNum = bsSubAccountMapper.dailyTotalInvestNum(req.getStartTime(), req.getEndTime());
            // 8、当日全部投资金额
            List<Map<String, Object>> dailyTotalInvestAmount = bsSubAccountMapper.dailyTotalInvestAmount(req.getStartTime(), req.getEndTime());
            // 9、累计注册人数
            List<Map<String, Object>> totalRegistUser = bsAccountMapper.totalRegistUser(req.getStartTime(), req.getEndTime());
            // 10、累计投资人数
            List<Map<String, Object>> totalInvestUser = bsSubAccountMapper.totalInvestUser(req.getStartTime(), req.getEndTime());
            // 11、参与复投人数
//            List<Map<String, Object>> totalReInvestUser = bsSubAccountMapper.totalReInvestUser(req.getStartTime(), req.getEndTime());
            
            // 12、累计投资金额
            List<Map<String, Object>> totalInvestAmount = bsSubAccountMapper.totalInvestAmount(req.getStartTime(), req.getEndTime());
            
            // 所有渠道ID
            List<Integer> agentIds = new ArrayList<Integer>();
            agentIds.add(null);
            BsAgentExample example = new BsAgentExample();
            List<BsAgent> agents = bsAgentMapper.selectByExample(example);
            for (BsAgent bsAgent : agents) {
                agentIds.add(bsAgent.getId());
            }
            
            List<BsDailyStat> bsDailyStats = new ArrayList<BsDailyStat>();
            // ================================ 数据 =======================================
            for (Date openTime : openTimes) {
                // 11、参与复投人数
                List<Map<String, Object>> totalRepeatNumber = bsSubAccountMapper.countTotalRepeatNumber(DateUtil.format(openTime), DateUtil.format(openTime));
                
                for (Integer agentId : agentIds) {
                    BsDailyStat bsDailyStat = new BsDailyStat();
                    bsDailyStat.setAgentId(agentId);
                    bsDailyStat.setStatDate(openTime);
                    // 1.当日新增用户数
                    for (Map<String, Object> map : dailyNewRegisterNum) {
                        if(agentId == map.get("agent_id") && DateUtil.isSameDay(openTime, (Date) map.get("open_time"))){
                            if (map.get("count") instanceof Double) {
                                bsDailyStat.setDailyNewRegistUser(((Double)map.get("count")).intValue());
                            }else if(map.get("count") instanceof Long) {
                                bsDailyStat.setDailyNewRegistUser(((Long)map.get("count")).intValue());
                            } else if(map.get("count") instanceof Integer) {
                                bsDailyStat.setDailyNewRegistUser(Integer.parseInt(map.get("count").toString()));
                            } else if (map.get("count") instanceof BigDecimal) {
                                bsDailyStat.setDailyNewRegistUser(((BigDecimal)map.get("count")).intValue());
                            } 
                        }
                    }
                    // 2.当日新增用户投资笔数
                    for (Map<String, Object> map : dailyNewInvestNum) {
                        if(agentId == map.get("agent_id") && DateUtil.isSameDay(openTime, (Date) map.get("open_time"))){
                            if (map.get("count") instanceof Double) {
                                bsDailyStat.setDailyNewUserInvestTimes(((Double)map.get("count")).intValue());
                            }else if(map.get("count") instanceof Long) {
                                bsDailyStat.setDailyNewUserInvestTimes(((Long)map.get("count")).intValue());
                            } else if(map.get("count") instanceof Integer) {
                                bsDailyStat.setDailyNewUserInvestTimes(Integer.parseInt(map.get("count").toString()));
                            } else if (map.get("count") instanceof BigDecimal) {
                                bsDailyStat.setDailyNewUserInvestTimes(((BigDecimal)map.get("count")).intValue());
                            } 
                        }
                    }
                    // 3.当日新增用户投资额
                    for (Map<String, Object> map : dailyNewInvestAmount) {
                        if(agentId == map.get("agent_id") && DateUtil.isSameDay(openTime, (Date) map.get("open_time"))){
                            if (map.get("count") instanceof Double) {
                                bsDailyStat.setDailyNewUserInvestAmount(((Double)map.get("count")));
                            } else if (map.get("count") instanceof BigDecimal) {
                                bsDailyStat.setDailyNewUserInvestAmount(((BigDecimal)map.get("count")).doubleValue());
                            } 
                        }
                    }
                    // 4.当日老用户（第二次及以上投资的）投资笔数
                    for (Map<String, Object> map : dailyOldInvestNum) {
                        if(agentId == map.get("agent_id") && DateUtil.isSameDay(openTime, (Date) map.get("open_time"))){
                            if (map.get("count") instanceof Double) {
                                bsDailyStat.setDailyOldUserInvestTimes(((Double)map.get("count")).intValue());
                            }else if(map.get("count") instanceof Long) {
                                bsDailyStat.setDailyOldUserInvestTimes(((Long)map.get("count")).intValue());
                            } else if(map.get("count") instanceof Integer) {
                                bsDailyStat.setDailyOldUserInvestTimes(Integer.parseInt(map.get("count").toString()));
                            } else if (map.get("count") instanceof BigDecimal) {
                                bsDailyStat.setDailyOldUserInvestTimes(((BigDecimal)map.get("count")).intValue());
                            } 
                        }
                    }
                    // 5.当日老用户（第二次及以上投资的）投资金额
                    for (Map<String, Object> map : dailyOldInvestAmount) {
                        if(agentId == map.get("agent_id") && DateUtil.isSameDay(openTime, (Date) map.get("open_time"))){
                            if (map.get("count") instanceof Double) {
                                bsDailyStat.setDailyOldUserInvestAmount(((Double)map.get("count")));
                            } else if (map.get("count") instanceof BigDecimal) {
                                bsDailyStat.setDailyOldUserInvestAmount(((BigDecimal)map.get("count")).doubleValue());
                            } 
                        }
                    }
                    // 6.当日全部投资笔数  
                    for (Map<String, Object> map : dailyTotalInvestNum) {
                        if(agentId == map.get("agent_id") && DateUtil.isSameDay(openTime, (Date) map.get("open_time"))){
                            if (map.get("count") instanceof Double) {
                                bsDailyStat.setDailyTotalInvestTimes(((Double)map.get("count")).intValue());
                            }else if(map.get("count") instanceof Long) {
                                bsDailyStat.setDailyTotalInvestTimes(((Long)map.get("count")).intValue());
                            } else if(map.get("count") instanceof Integer) {
                                bsDailyStat.setDailyTotalInvestTimes(Integer.parseInt(map.get("count").toString()));
                            } else if (map.get("count") instanceof BigDecimal) {
                                bsDailyStat.setDailyTotalInvestTimes(((BigDecimal)map.get("count")).intValue());
                            } 
                        }
                    }
                    // 7.当日全部投资金额
                    for (Map<String, Object> map : dailyTotalInvestAmount) {
                        if(agentId == map.get("agent_id") && DateUtil.isSameDay(openTime, (Date) map.get("open_time"))){
                            if (map.get("count") instanceof Double) {
                                bsDailyStat.setDailyTotalInvestAmount(((Double)map.get("count")));
                            } else if (map.get("count") instanceof BigDecimal) {
                                bsDailyStat.setDailyTotalInvestAmount(((BigDecimal)map.get("count")).doubleValue());
                            } 
                        }
                    }
                    // 8.累计注册人数
                    for (Map<String, Object> map : totalRegistUser) {
                        if(agentId == map.get("agent_id") && DateUtil.isSameDay(openTime, (Date) map.get("open_time"))){
                            if (map.get("count") instanceof Double) {
                                bsDailyStat.setTotalRegistUser(((Double)map.get("count")).intValue());
                            }else if(map.get("count") instanceof Long) {
                                bsDailyStat.setTotalRegistUser(((Long)map.get("count")).intValue());
                            } else if(map.get("count") instanceof Integer) {
                                bsDailyStat.setTotalRegistUser(Integer.parseInt(map.get("count").toString()));
                            } else if (map.get("count") instanceof BigDecimal) {
                                bsDailyStat.setTotalRegistUser(((BigDecimal)map.get("count")).intValue());
                            } 
                        }
                    }
                    // 9.累计复投人数
//                    for (Map<String, Object> map : totalReInvestUser) {
                    for (Map<String, Object> map : totalRepeatNumber) {
                        if(agentId == map.get("agent_id")){
                            if (map.get("count") instanceof Double) {
                                bsDailyStat.setTotalReinvestUser(((Double)map.get("count")).intValue());
                            }else if(map.get("count") instanceof Long) {
                                bsDailyStat.setTotalReinvestUser(((Long)map.get("count")).intValue());
                            } else if(map.get("count") instanceof Integer) {
                                bsDailyStat.setTotalReinvestUser(Integer.parseInt(map.get("count").toString()));
                            } else if (map.get("count") instanceof BigDecimal) {
                                bsDailyStat.setTotalReinvestUser(((BigDecimal)map.get("count")).intValue());
                            } 
                        }
                    }
                    // 10.累计投资人数
                    for (Map<String, Object> map : totalInvestUser) {
                        if(agentId == map.get("agent_id") && DateUtil.isSameDay(openTime, (Date) map.get("open_time"))){
                            if (map.get("count") instanceof Double) {
                                bsDailyStat.setTotalInvestUser(((Double)map.get("count")).intValue());
                            }else if(map.get("count") instanceof Long) {
                                bsDailyStat.setTotalInvestUser(((Long)map.get("count")).intValue());
                            } else if(map.get("count") instanceof Integer) {
                                bsDailyStat.setTotalInvestUser(Integer.parseInt(map.get("count").toString()));
                            } else if (map.get("count") instanceof BigDecimal) {
                                bsDailyStat.setTotalInvestUser(((BigDecimal)map.get("count")).intValue());
                            } 
                        }
                    }
                    // 11.累计总投资金额
                    for (Map<String, Object> map : totalInvestAmount) {
                        if(agentId == map.get("agent_id") && DateUtil.isSameDay(openTime, (Date) map.get("open_time"))){
                            if (map.get("count") instanceof Double) {
                                bsDailyStat.setTotalInvestAmount(((Double)map.get("count")));
                            } else if (map.get("count") instanceof BigDecimal) {
                                bsDailyStat.setTotalInvestAmount(((BigDecimal)map.get("count")).doubleValue());
                            } 
                        }
                    }
                    bsDailyStats.add(bsDailyStat);
                }
            }
            
            // 最新的一天统计数据
//            BsDailyStatExample dailyStatExample = new BsDailyStatExample();
//            dailyStatExample.createCriteria().andStatDateLessThan(DateUtil.parse(req.getStartTime(), "yyyy-MM-dd"));
//            dailyStatExample.setOrderByClause("id desc");
//            List<BsDailyStat> list = bsDailyStatMapper.selectByExample(dailyStatExample);
            List<BsDailyStat> list = bsDailyStatMapper.selectDailyStat(req.getStartTime(), agentIds.size());
            for (int i = 0; i < bsDailyStats.size(); i++) {
                if(i / agentIds.size() == 0) {
                    if(!CollectionUtils.isEmpty(list)){
                        for (int j = 0; j < agentIds.size(); j++) {
                            if(bsDailyStats.get(i).getAgentId() == list.get(j).getAgentId()){
                                if(null == bsDailyStats.get(i).getTotalInvestAmount()){
                                    bsDailyStats.get(i).setTotalInvestAmount(list.get(j).getTotalInvestAmount());
                                } 
                                if(null == bsDailyStats.get(i).getTotalInvestUser()){
                                    bsDailyStats.get(i).setTotalInvestUser(list.get(j).getTotalInvestUser());
                                }
                                if(null == bsDailyStats.get(i).getTotalRegistUser()){
                                    bsDailyStats.get(i).setTotalRegistUser(list.get(j).getTotalRegistUser());
                                }
                                if(null == bsDailyStats.get(i).getTotalReinvestUser()){
                                    bsDailyStats.get(i).setTotalReinvestUser(list.get(j).getTotalReinvestUser());
                                }
                            }
                        }
                    }
                } else {
                    if(DateUtil.isSameDay(DateUtil.addDays(bsDailyStats.get(i - agentIds.size()).getStatDate(), 1), bsDailyStats.get(i).getStatDate())) {
                        BsDailyStat start = bsDailyStats.get(i - agentIds.size());
                        BsDailyStat end = bsDailyStats.get(i);
                        if(start.getAgentId() == end.getAgentId()){
                            if(null == end.getTotalInvestAmount()){
                                bsDailyStats.get(i).setTotalInvestAmount(start.getTotalInvestAmount());
                            }
                            if(null == end.getTotalInvestUser()){
                                bsDailyStats.get(i).setTotalInvestUser(start.getTotalInvestUser());
                            }
                            if(null == end.getTotalRegistUser()){
                                bsDailyStats.get(i).setTotalRegistUser(start.getTotalRegistUser());
                            }
                            if(null == end.getTotalReinvestUser()){
                                bsDailyStats.get(i).setTotalReinvestUser(start.getTotalReinvestUser());
                            }
                        }
                    }
                }
            }
            for (BsDailyStat bsDailyStat : bsDailyStats) {
                bsDailyStatMapper.insertSelective(bsDailyStat);
            }
        } catch (Exception e) {
            throw new PTMessageException(PTMessageEnum.DB_CUD_NO_EFFECT, PTMessageEnum.DB_CUD_NO_EFFECT.getMessage());
        }
    }
    
    public static void main(String[] args) {/*
        String dateStrYYYYMMDD = DateUtil.formatDateTime(new Date(), "yyyy-MM-dd");
        Date dateCurrent = DateUtil.parse(dateStrYYYYMMDD, "yyyy-MM-dd");  // 当前统计的数据时间
        String dateStr = dateStrYYYYMMDD + " 00:00:00";
        Date date = DateUtil.parse(dateStr, "yyyy-MM-dd hh:mm:ss");
        
        String timeStr = "2015-12-22 00:00:00";
        Date d = DateUtil.parse(timeStr, "yyyy-MM-dd HH:mm:ss");
//        System.out.println(dateStrYYYYMMDD);
//        System.out.println(dateStr);
//        System.out.println(d.toString());
//        
        Integer beishu = 35 / 12;   // 2
        Integer yushu = 35 % 12;    // 1
        
        System.out.println(3/12);  // 2
        System.out.println(yushu);  // 11
        System.out.println(beishu == (35 - yushu)/12);  // true
        System.out.println((35 - yushu));  // true
        System.out.println((35 - yushu) - (12-yushu));  // true
        System.out.println(((35 - yushu) - (12-yushu))%12);  // true
    */}

    
    
    
    
    
    
    
    
    

}
