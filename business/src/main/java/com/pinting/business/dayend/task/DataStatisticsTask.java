/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.dayend.task;

import com.pinting.business.dao.*;
import com.pinting.business.model.BsAgent;
import com.pinting.business.model.BsAgentExample;
import com.pinting.business.model.BsDailyStat;
import com.pinting.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 数据统计定时跑批
 * 数据 {
 *      1、当日新增微信关注数
 *      2、当日新增用户数
 *      3、新增用户投资笔数
 *      4、新增用户投资额
 *      5、当日老用户（第二次及以上投资的）投资笔数
 *      6、当日老用户（第二次及以上投资的）投资金额
 *      7、当日全部投资笔数
 *      8、当日全部投资金额按照
 * }
 * @author HuanXiong
 * @version $Id: DataStatisticsTask.java, v 0.1 2015-12-10 下午4:27:53 HuanXiong Exp $
 */
@Service
public class DataStatisticsTask {
    
    private Logger logger = LoggerFactory.getLogger(DataStatisticsTask.class);
    
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
    
    public void execute() {
        // 数据统计
//        Date now0 = DateUtil.parse("2016-07-06", "yyyy-MM-dd");
//        dataStatistics(now4);
        dataStatistics(new Date());
    }
    
    private void dataStatistics(Date now) {
        logger.info("==================日终【数据统计定时跑批】开始====================");
        try {
            // 日期格式化
//            Date now = new Date();
//            String dateStrYYYYMMDD = DateUtil.formatDateTime(now, "yyyy-MM-dd");
//            Date dateAfter = DateUtil.parse(dateStrYYYYMMDD, "yyyy-MM-dd");     // 23
//            Date dateCurrent = DateUtil.addDays(dateAfter, -1);     // 当前统计的数据时间    22
            
//            Date now = DateUtil.parse("2016-07-06", "yyyy-MM-dd");
            String dateStrYYYYMMDD = DateUtil.formatDateTime(now, "yyyy-MM-dd");
            Date dateAfter = DateUtil.parse(dateStrYYYYMMDD, "yyyy-MM-dd");
            Date dateCurrent = DateUtil.addDays(dateAfter, -1);     // 当前统计的数据时间
            
            // 1、当日新增微信关注数
            // BsWxInfoExample bsWxInfoExample = new BsWxInfoExample();
            // bsWxInfoExample.createCriteria().andFollowTimeBetween(dateCurrent, dateAfter);
            // int wxInfoCount = bsWxInfoMapper.countByExample(bsWxInfoExample);
            // 2、当日新增用户数
            List<Map<String, Object>> newUserCountMaps = bsAccountMapper.currentNewAccountNumber(DateUtil.format(dateCurrent), DateUtil.format(dateAfter));
            // 3、新增用户当日投资笔数
            List<Map<String, Object>> newInvestNumbers = bsSubAccountMapper.countCurrentNewInvestNumber(DateUtil.format(DateUtil.addDays(dateCurrent, 1)), DateUtil.format(DateUtil.addDays(dateAfter, 1)));
            // 4、新增用户当日投资额 || 16、新投资用户年化投资金额-2016-7-1 year_invest_amount
            List<Map<String, Object>> currentNewInvestBalance = bsSubAccountMapper.sumCurrentNewInvestBalance(DateUtil.format(DateUtil.addDays(dateCurrent, 1)), DateUtil.format(DateUtil.addDays(dateAfter, 1)));
            // 5、当日老用户（第二次及以上投资的）投资笔数
            List<Map<String, Object>> oldInvestTimes = bsSubAccountMapper.countCurrentOldInvestTimes(DateUtil.format(DateUtil.addDays(dateCurrent, 1)), DateUtil.format(DateUtil.addDays(dateAfter, 1)));
            // 6、当日老用户（第二次及以上投资的）投资金额
            List<Map<String, Object>> oldInvestBalance = bsSubAccountMapper.sumCurrentOldInvestBalance(DateUtil.format(DateUtil.addDays(dateCurrent, 1)), DateUtil.format(DateUtil.addDays(dateAfter, 1)));
            // 7、当日全部投资笔数  
            List<Map<String, Object>> currentTotalInvestTimes = bsSubAccountMapper.currentTotalInvestTimes(DateUtil.format(DateUtil.addDays(dateCurrent, 1)), DateUtil.format(DateUtil.addDays(dateAfter, 1)));
            // 8、当日全部投资金额 || 17、年化投资金额-2016-7-1 year_invest_amount
            List<Map<String, Object>> currentTotalInvestBalance = bsSubAccountMapper.sumCurrentTotalInvestBalance(DateUtil.format(DateUtil.addDays(dateCurrent, 1)), DateUtil.format(DateUtil.addDays(dateAfter, 1)));
            // 新增的 
            // 9、累计注册人数
            List<Map<String, Object>> totalRegisterNumber = bsAccountMapper.countRegisterNumber(DateUtil.format(dateCurrent), DateUtil.format(dateAfter));
            
            // 10、累计投资人数
            List<Map<String, Object>> totalInvestNumber = bsSubAccountMapper.countTotalInvestNumber(DateUtil.format(DateUtil.addDays(dateCurrent, 1)), DateUtil.format(DateUtil.addDays(dateAfter, 1)));
            
            // 11、参与复投人数
            List<Map<String, Object>> totalRepeatNumber = bsSubAccountMapper.countTotalRepeatNumber(DateUtil.format(DateUtil.addDays(dateCurrent, 1)), DateUtil.format(DateUtil.addDays(dateAfter, 1)));
            
            // 12、累计投资金额
            List<Map<String, Object>> totalBalance = bsSubAccountMapper.sumTotalBalance(DateUtil.format(DateUtil.addDays(dateCurrent, 1)), DateUtil.format(DateUtil.addDays(dateAfter, 1)));
            
            // 13、新增绑卡用户数
            List<Map<String, Object>> newBindCardUserCountMaps = bsBankCardMapper.countBindCardUser(DateUtil.format(dateCurrent), DateUtil.format(dateAfter));
            // 14、新增投资用户数
            List<Map<String, Object>> newInvestUserCountMaps = bsSubAccountMapper.countNewInvestUser(DateUtil.format(dateCurrent), DateUtil.format(dateAfter));
            // 15、投资用户数
            List<Map<String, Object>> investUserCountMaps = bsSubAccountMapper.countInvestUser(DateUtil.format(dateCurrent), DateUtil.format(dateAfter));
            
            // 所有渠道ID
            List<Integer> agentIds = new ArrayList<Integer>();
            BsAgentExample example = new BsAgentExample();
            List<BsAgent> agents = bsAgentMapper.selectByExample(example);
            agentIds.add(null);
            for (BsAgent bsAgent : agents) {
                agentIds.add(bsAgent.getId());
            }
            
            // ================================ 数据 =======================================
            List<BsDailyStat> bsDailyStats = new ArrayList<BsDailyStat>();
            for (Integer agentId : agentIds) {
                BsDailyStat bsDailyStat = new BsDailyStat();
                bsDailyStat.setAgentId(agentId);
                bsDailyStat.setStatDate(dateCurrent);
                
                // 当日新增用户数
                for (Map<String, Object> map : newUserCountMaps) {
                    if(agentId == map.get("agent_id")){
                        bsDailyStat.setDailyNewRegistUser(((Long)map.get("count")).intValue());
                    }
                }
                // 当日新增用户投资笔数
                for (Map<String, Object> map : newInvestNumbers) {
                    if(agentId == map.get("agent_id")){
                        bsDailyStat.setDailyNewUserInvestTimes(((Long)map.get("count")).intValue());
                    }
                }
                // 当日新增用户投资额 || 新投资用户年化投资金额-2016-7-1 year_invest_amount
                for (Map<String, Object> map : currentNewInvestBalance) {
                    if(agentId == map.get("agent_id")){
                        bsDailyStat.setDailyNewUserInvestAmount((Double)map.get("amount"));
                        if (map.get("year_invest_amount") instanceof Double) {
                            bsDailyStat.setDailyNewUserInvestAnnual((Double)map.get("year_invest_amount"));
                        } else if (map.get("year_invest_amount") instanceof BigDecimal) {
                            bsDailyStat.setDailyNewUserInvestAnnual(((BigDecimal)map.get("year_invest_amount")).doubleValue());
                        } else {
                            bsDailyStat.setDailyNewUserInvestAnnual(Double.parseDouble(map.get("year_invest_amount").toString()));
                        }
                    }
                }
                // 当日老用户（第二次及以上投资的）投资笔数
                for (Map<String, Object> map : oldInvestTimes) {
                    if(agentId == map.get("agent_id")){
                        if (map.get("count") instanceof Double) {
                            bsDailyStat.setDailyOldUserInvestTimes(((Double)map.get("count")).intValue());
                        } else if (map.get("count") instanceof BigDecimal) {
                            bsDailyStat.setDailyOldUserInvestTimes(((BigDecimal)map.get("count")).intValue());
                        } else {
                            bsDailyStat.setDailyOldUserInvestTimes(Integer.parseInt(map.get("count").toString()));
                        }
                        
                    }
                }
                // 当日老用户（第二次及以上投资的）投资金额
                for (Map<String, Object> map : oldInvestBalance) {
                    if(agentId == map.get("agent_id")){
                        bsDailyStat.setDailyOldUserInvestAmount((Double)map.get("amount"));
                    }
                }
                // 当日全部投资笔数  
                for (Map<String, Object> map : currentTotalInvestTimes) {
                    if(agentId == map.get("agent_id")){
                        bsDailyStat.setDailyTotalInvestTimes(((Long)map.get("count")).intValue());
                    }
                }
                // 8、当日全部投资金额 || 新投资用户年化投资金额-2016-7-1 year_invest_amount
                for (Map<String, Object> map : currentTotalInvestBalance) {
                    if(agentId == map.get("agent_id")){
                        bsDailyStat.setDailyTotalInvestAmount((Double)map.get("amount"));
                        if (map.get("year_invest_amount") instanceof Double) {
                            bsDailyStat.setDailyInvestAnnual((Double)map.get("year_invest_amount"));
                        } else if (map.get("year_invest_amount") instanceof BigDecimal) {
                            bsDailyStat.setDailyInvestAnnual(((BigDecimal)map.get("year_invest_amount")).doubleValue());
                        } else {
                            bsDailyStat.setDailyInvestAnnual(Double.parseDouble(map.get("year_invest_amount").toString()));
                        }
                    }
                }
                for (Map<String, Object> map : totalBalance) {
                    if(agentId == map.get("agent_id")){
                        bsDailyStat.setTotalInvestAmount(((Double)map.get("count")));
                    }
                }
                for (Map<String, Object> map : totalInvestNumber) {
                    if(agentId == map.get("agent_id")){
                        bsDailyStat.setTotalInvestUser(Integer.parseInt(map.get("count").toString()));
                    }
                }
                for (Map<String, Object> map : totalRegisterNumber) {
                    if(agentId == map.get("agent_id")){
                        bsDailyStat.setTotalRegistUser(Integer.parseInt(map.get("count").toString()));
                    }
                }
                for (Map<String, Object> map : totalRepeatNumber) {
                    if(agentId == map.get("agent_id")){
                        bsDailyStat.setTotalReinvestUser(Integer.parseInt(map.get("count").toString()));
                    }
                }
                for (Map<String, Object> map : newBindCardUserCountMaps) {
                    if(agentId == map.get("agent_id")){
                        bsDailyStat.setDailyNewBindUser(Integer.parseInt(map.get("count").toString()));
                    }
                }
                for (Map<String, Object> map : newInvestUserCountMaps) {
                    if(agentId == map.get("agent_id")){
                        bsDailyStat.setDailyNewInvestUser(Integer.parseInt(map.get("count").toString()));
                    }
                }
                for (Map<String, Object> map : investUserCountMaps) {
                    if(agentId == map.get("agent_id")){
                        bsDailyStat.setDailyInvestUser(Integer.parseInt(map.get("count").toString()));
                    }
                }
//                bsDailyStatMapper.insertSelective(bsDailyStat);
                bsDailyStats.add(bsDailyStat);
            }
            
            // 最新的一天统计数据
//            BsDailyStatExample dailyStatExample = new BsDailyStatExample();
//            dailyStatExample.createCriteria().andStatDateLessThan(dateCurrent);
//            dailyStatExample.setOrderByClause("id desc");
//            List<BsDailyStat> list = bsDailyStatMapper.selectByExample(dailyStatExample);
            List<BsDailyStat> list = bsDailyStatMapper.selectDailyStat(DateUtil.formatYYYYMMDD(dateCurrent), agentIds.size());
            for (int i = 0; i < bsDailyStats.size(); i++) {
                // bsDailyStats没有数据的时候
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
                }
                // bsDailyStats有数据的时候
                else {
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
            e.printStackTrace();
            logger.error("==================日终【数据统计定时跑批】失败====================");
        }
        logger.info("==================日终【数据统计定时跑批】结束====================");
    }
}

