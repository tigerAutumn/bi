/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.service.site.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.pinting.business.dao.BsAutoRedPacketRuleMapper;
import com.pinting.business.dao.BsProductMapper;
import com.pinting.business.dao.BsRedPacketCheckMapper;
import com.pinting.business.dao.BsRedPacketInfoMapper;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.ReqMsg_RedPacketInfo_ChooseRedPacket;
import com.pinting.business.hessian.site.message.ReqMsg_RedPacketInfo_GetRedPacket;
import com.pinting.business.hessian.site.message.ReqMsg_RedPacketInfo_QueryRedPacketList;
import com.pinting.business.hessian.site.message.ReqMsg_RedPacketInfo_RedPacketList;
import com.pinting.business.hessian.site.message.ResMsg_RedPacketInfo_ChooseRedPacket;
import com.pinting.business.hessian.site.message.ResMsg_RedPacketInfo_GetRedPacket;
import com.pinting.business.hessian.site.message.ResMsg_RedPacketInfo_QueryRedPacketList;
import com.pinting.business.hessian.site.message.ResMsg_RedPacketInfo_RedPacketList;
import com.pinting.business.model.AutoRedPacketParams;
import com.pinting.business.model.BsAutoRedPacketRule;
import com.pinting.business.model.BsAutoRedPacketRuleExample;
import com.pinting.business.model.BsProduct;
import com.pinting.business.model.BsRedPacketCheck;
import com.pinting.business.model.BsRedPacketCheckExample;
import com.pinting.business.model.BsRedPacketInfo;
import com.pinting.business.model.BsRedPacketInfoExample;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.vo.AutoRedPacketTotalAmountVO;
import com.pinting.business.model.vo.BsRedPacketCheckVO;
import com.pinting.business.model.vo.RedPacketInfoGrantVO;
import com.pinting.business.model.vo.RedPacketInfoVO;
import com.pinting.business.service.manage.BsRedPackatInfoService;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.site.RedPacketService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.in.util.MethodRole;

/**
 * 
 * @author HuanXiong
 * @version $Id: RedPacketServiceImpl.java, v 0.1 2016-3-1 下午1:01:30 HuanXiong Exp $
 */
@Service
public class RedPacketServiceImpl implements RedPacketService {
    
    private Logger logger = LoggerFactory.getLogger(RedPacketServiceImpl.class);

    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    
    @Autowired
    private BsRedPacketInfoMapper bsRedPacketInfoMapper;
    @Autowired
    private BsAutoRedPacketRuleMapper autoRedMapper;
    @Autowired
    private BsRedPacketInfoMapper redInfoMapper;
    @Autowired
    private BsRedPacketCheckMapper redCheckMapper;
    @Autowired
    private BsProductMapper bsProductMapper;
    @Autowired
    private BsUserMapper userMapper;
    @Autowired
    private BsRedPackatInfoService redPackatInfoService;
    @Autowired
    private BsRedPackatInfoService redInfoService;
    @Autowired
    private SpecialJnlService specialJnlService;
	@Autowired
	private BsSysConfigService bsSysConfigService;
    @Autowired
    private TransactionTemplate transactionTemplate;


	private String changeTermLimit(String termLimit) {
		StringBuffer buffer = new StringBuffer();
		String[] limits = termLimit.split(",");
		for(String limit : limits) {
			if("1".equals(limit)) {
				buffer.append("30,");
			} else if("3".equals(limit)) {
				buffer.append("90,");
			} else if("6".equals(limit)) {
				buffer.append("180,");
			} else if("12".equals(limit)) {
				buffer.append("365,");
			}
		}
		buffer.delete(buffer.length() - 1, buffer.length());
		return "限" + buffer.toString() + "天产品使用";
	}

    /** 
     */
    @Override
    @MethodRole("APP")
    public void redPacketList(ReqMsg_RedPacketInfo_RedPacketList req, ResMsg_RedPacketInfo_RedPacketList res) {
		List<RedPacketInfoVO> vos = bsRedPacketInfoMapper.selectRedPacketList(req.getUserId(), req.getStatus(), null, req.getProductId());
		if(!CollectionUtils.isEmpty(vos)) {
			res.setCount(vos.size());
			Date now = new Date();
			for (RedPacketInfoVO vo : vos) {
				vo.setTermLimitMsg(changeTermLimit(vo.getTermLimit()));
				vo.setTermLimit(changeTermLimit(vo.getTermLimit()));
				vo.setType(Constants.TICKET_INTEREST_TYPE_RED_PACKET);
				if(DateUtil.compareTo(vo.getUseTimeEnd(), now) < 0) {
					if(Constants.RED_PACKET_STATUS_INIT.equals(vo.getStatus())) {
						vo.setStatus(Constants.RED_PACKET_STATUS_OVER);
					}
				}
			}
			this.sort(vos, req.getAmount());
			res.setDataGrid(BeanUtils.classToArrayList(vos));
		} else {
			res.setCount(0);
			res.setDataGrid(new ArrayList<HashMap<String, Object>>());
		}
    }

	@Override
	public void queryRedPacketList(ReqMsg_RedPacketInfo_QueryRedPacketList req, ResMsg_RedPacketInfo_QueryRedPacketList res) {
		BsRedPacketInfoExample bsRedPacketInfoExample = new BsRedPacketInfoExample();
		Date now = new Date();
		Date days = new Date();
		try {
			days = DateUtil.parse(DateUtil.format(now, "yyyy-MM-dd") + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date day = com.pinting.core.util.DateUtil.addDays(days, -89);
		if(StringUtil.isNotBlank(req.getStatus())) {
			if (Constants.RED_PACKET_STATUS_INIT.equals(req.getStatus())) {
				bsRedPacketInfoExample.createCriteria().andUserIdEqualTo(req.getUserId()).andStatusEqualTo(req.getStatus()).andUseTimeEndGreaterThan(new Date());
			} else if (Constants.RED_PACKET_STATUS_OVER.equals(req.getStatus())) {
				bsRedPacketInfoExample.createCriteria().andUserIdEqualTo(req.getUserId()).andStatusEqualTo(Constants.RED_PACKET_STATUS_INIT).andUseTimeEndLessThan(new Date()).andUseTimeEndGreaterThanOrEqualTo(day);
			} else {
				bsRedPacketInfoExample.createCriteria().andUserIdEqualTo(req.getUserId()).andStatusEqualTo(req.getStatus()).andUpdateTimeGreaterThanOrEqualTo(day);
			}
		}
		Integer count = bsRedPacketInfoMapper.countByExample(bsRedPacketInfoExample);
		res.setCount(count);
		int totalPages = (int) Math.ceil((double) count / req.getNumPerPage());
		if (count > 0 && req.getPageNum() <= totalPages) {
			int start = (req.getPageNum() <= 1) ? 0 : ((req.getPageNum() - 1) * req.getNumPerPage()); // mysql的分页
			List<RedPacketInfoVO> vos = bsRedPacketInfoMapper.selectRedPacketListNew(req.getUserId(), req.getStatus(), start, req.getNumPerPage());
			res.setDataGrid(BeanUtils.classToArrayList(vos) == null? new ArrayList<HashMap<String, Object>>() : BeanUtils.classToArrayList(vos));
			for (Map<String, Object> dataGrid : res.getDataGrid()) {
				DecimalFormat format = new DecimalFormat("#.##");
				dataGrid.put("subtract", format.format(dataGrid.get("subtract")));
				dataGrid.put("termLimitMsg", changeTermLimit((String) dataGrid.get("termLimit")));
			}
		} else {
			res.setDataGrid(new ArrayList<HashMap<String, Object>>());
		}
	}

	/**
     * @see com.pinting.business.service.site.RedPacketService#getRedPacketNum(java.lang.Integer, java.lang.String)
     */
    @Override
    @MethodRole("APP")
    public Integer getRedPacketNum(Integer userId, String status) {
        BsRedPacketInfoExample bsRedPacketInfoExample = new BsRedPacketInfoExample();
        if(StringUtil.isNotBlank(status)) { 
            if(Constants.RED_PACKET_STATUS_INIT.equals(status)) {
                bsRedPacketInfoExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(status).andUseTimeEndGreaterThan(new Date());
            } else if(Constants.RED_PACKET_STATUS_OVER.equals(status)) {
                bsRedPacketInfoExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(Constants.RED_PACKET_STATUS_INIT).andUseTimeEndLessThan(new Date());
            } else { 
                bsRedPacketInfoExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(status);
            }
            Integer count = bsRedPacketInfoMapper.countByExample(bsRedPacketInfoExample);
            return count;
        } else {
            bsRedPacketInfoExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(Constants.RED_PACKET_STATUS_INIT);
            Integer count = bsRedPacketInfoMapper.countByExample(bsRedPacketInfoExample);
            return count;
        }
    }

    /** 
     * @see com.pinting.business.service.site.RedPacketService#findById(com.pinting.business.hessian.site.message.ReqMsg_RedPacketInfo_ChooseRedPacket, com.pinting.business.hessian.site.message.ResMsg_RedPacketInfo_ChooseRedPacket)
     */
    @Override
    public void findById(ReqMsg_RedPacketInfo_ChooseRedPacket req, ResMsg_RedPacketInfo_ChooseRedPacket res) {
        RedPacketInfoVO vo = bsRedPacketInfoMapper.selectByIdAndUserId(req.getId(), req.getUserId());
        Date now = new Date();
        if(DateUtil.compareTo(vo.getUseTimeEnd(), now) < 0) {
            if(!Constants.RED_PACKET_STATUS_USED.equals(vo.getStatus())) {
                vo.setStatus(Constants.RED_PACKET_STATUS_OVER);
            }
        }
        if (vo != null) {
            res.setData(BeanUtils.classToHashMap(vo));
        }
    }

	@Override
	@MethodRole("APP")
	public List<RedPacketInfoVO> getUserRegistRedPakt(Integer userId) {
		List<RedPacketInfoVO> redPacketInfos = bsRedPacketInfoMapper.selectUserRegistRedPackets(userId);
		return CollectionUtils.isEmpty(redPacketInfos)?null:redPacketInfos;
	}

    /** 
     * @see com.pinting.business.service.site.RedPacketService#getRedPacket(com.pinting.business.hessian.site.message.ReqMsg_RedPacketInfo_GetRedPacket, com.pinting.business.hessian.site.message.ResMsg_RedPacketInfo_GetRedPacket)
     */
    @Override
    public void getRedPacket(ReqMsg_RedPacketInfo_GetRedPacket req,
                             ResMsg_RedPacketInfo_GetRedPacket res) {
        List<RedPacketInfoVO> vos2 = new ArrayList<RedPacketInfoVO>();
        List<RedPacketInfoVO> vos = bsRedPacketInfoMapper.selectRedPacketList(req.getUserId(), req.getStatus(), req.getAmount(), null);
        
        for (RedPacketInfoVO vo : vos) {
            BsProduct bsProduct = new BsProduct();
            if(req.getProductId() != null){
                bsProduct = bsProductMapper.selectByPrimaryKey(req.getProductId());
            }
            if(req.getProductId() != null){
                String[] termLimits = vo.getTermLimit().split(",");
                for (String termLimit : termLimits) {
                    Integer termLimitInt = Integer.valueOf(termLimit);
                    if(termLimitInt == bsProduct.getTerm()) {
                        vo.setTermLimitMsg(changeTermLimit(vo.getTermLimit()));
                        vos2.add(vo);
                        break;
                    }
                }
            }
        }
        if(StringUtil.isBlank(req.getStatus()) || req.getStatus().equals(Constants.RED_PACKET_STATUS_OVER)) {
            Date now = new Date();
            for (RedPacketInfoVO vo : vos) {
                vo.setTermLimitMsg(changeTermLimit(vo.getTermLimit()));
                if(DateUtil.compareTo(vo.getUseTimeEnd(), now) < 0) {
                    if(Constants.RED_PACKET_STATUS_INIT.equals(vo.getStatus())) {
                        vo.setStatus(Constants.RED_PACKET_STATUS_OVER);
                    }
                }
            }
        }
        if(!CollectionUtils.isEmpty(vos)) {
            if(req.getProductId() != null) {
                this.sort(vos2, req.getAmount());
                res.setDataGrid(BeanUtils.classToArrayList(vos2));
            } else {
                this.sort(vos, req.getAmount());
                res.setDataGrid(BeanUtils.classToArrayList(vos));
            }
        } else {
            res.setDataGrid(new ArrayList<HashMap<String, Object>>());
        }
    }

    /**
     * 排序
     * @param vos
     * @param amount
     */
    private void sort(List<RedPacketInfoVO> vos, final Double amount) {
        // 未使用的 快过期的在前面
        Collections.sort(vos, new Comparator<RedPacketInfoVO>() {
            @Override
            public int compare(RedPacketInfoVO o1, RedPacketInfoVO o2) {
                Date endDate1 = o1.getUseTimeEnd();
                Date endDate2 = o2.getUseTimeEnd();
                Date now = new Date();
                if(Constants.RED_PACKET_STATUS_INIT.equals(o1.getStatus()) && Constants.RED_PACKET_STATUS_INIT.equals(o2.getStatus()) ) {
                    if(com.pinting.core.util.DateUtil.getDiffeSeconds(endDate1, now) < com.pinting.core.util.DateUtil.getDiffeSeconds(endDate2, now)) {
                        return -1;
                    } else if(com.pinting.core.util.DateUtil.getDiffeSeconds(endDate1, now) > com.pinting.core.util.DateUtil.getDiffeSeconds(endDate2, now)) {
                        return 1;
                    }
                }
                return 0;
            }
        });
        
        // 减的多在前面，减的金额一样，则满的少排在前面
        Collections.sort(vos, new Comparator<RedPacketInfoVO>() {
            @Override
            public int compare(RedPacketInfoVO o1, RedPacketInfoVO o2) {
                Double full1 = o1.getFull();
                Double subtract1 = o1.getSubtract();
                Double full2 = o2.getFull();
                Double subtract2 = o2.getSubtract();
                if (Double.compare(subtract1, subtract2) > 0) {
                    return -1;
                } else if(Double.compare(subtract1, subtract2) == 0) {
                    if(Double.compare(full1, full2) < 0) {
                        return -1;
                    } else if(Double.compare(full1, full2) > 0){
                        return 1;
                    }
                }
                return 0;
            }
        });
        // 如果存在金额，则amount 超过满的在前面，没有的在后面
        if(null != amount && amount > 0) {
            Collections.sort(vos, new Comparator<RedPacketInfoVO>() {
                @Override
                public int compare(RedPacketInfoVO o1, RedPacketInfoVO o2) {
                    if(Double.compare(amount, o1.getFull()) >= 0 && Double.compare(amount, o2.getFull()) < 0) {
                        return -1;
                    } else if(Double.compare(amount, o1.getFull()) < 0 && Double.compare(amount, o2.getFull()) >= 0) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
        }
        
        // 未使用的在前面，已过期的在后面
        Collections.sort(vos, new Comparator<RedPacketInfoVO>() {
            @Override
            public int compare(RedPacketInfoVO o1, RedPacketInfoVO o2) {
                if (Constants.RED_PACKET_STATUS_INIT.equals(o1.getStatus()) && !Constants.RED_PACKET_STATUS_INIT.equals(o2.getStatus())) {
                    return -1;
                } else if(!Constants.RED_PACKET_STATUS_INIT.equals(o1.getStatus()) && Constants.RED_PACKET_STATUS_INIT.equals(o2.getStatus())){
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }

	@Override
	@MethodRole("APP")
	public void awardBonus(Double amount, Integer userId) {
		Date now = new Date();
		BsAutoRedPacketRuleExample autoExample = new BsAutoRedPacketRuleExample();
		autoExample.createCriteria().andTriggerTypeEqualTo(Constants.AUTO_RED_PACKET_TIGGER_TYPE_BUY_FULL).andStatusEqualTo(Constants.AUTO_RED_PACKET_STATUS_AVAILABLE).andDistributeTimeStartLessThanOrEqualTo(now).andDistributeTimeEndGreaterThanOrEqualTo(now).andTriggerAmountStartLessThanOrEqualTo(amount).andTriggerAmountEndGreaterThanOrEqualTo(amount);
		List<BsAutoRedPacketRule> ruleList = autoRedMapper.selectByExample(autoExample);
		if(!CollectionUtils.isEmpty(ruleList)) {
			for(BsAutoRedPacketRule rule : ruleList) {
				BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
				checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
				List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
				if(!CollectionUtils.isEmpty(checkList)) {
					BsRedPacketCheck check = checkList.get(0);
					//判断红包是否已发完
					BsRedPacketInfoExample infoExample = new BsRedPacketInfoExample();
					infoExample.createCriteria().andApplyNoEqualTo(check.getApplyNo()).andSerialNoEqualTo(check.getSerialNo());
					int count = redInfoMapper.countByExample(infoExample);
					if(check.getTotal() > count) {
						//判断渠道是否符合
						BsUser user = userMapper.selectByPrimaryKey(userId);
						if(user != null) {
							Integer agentId = user.getAgentId();
							String[] array = rule.getAgentIds().split(",");
							if(agentId == null) {
								for(String ruleAgent : array) {
									if(Constants.ALL_AGENT.equals(ruleAgent) || Constants.NON_AGENT.equals(ruleAgent)) {
										createAndSendRedInfo(check,user,rule);
										break;
									}
								}
							}
							else {
								for(String ruleAgent : array) {
									if(Constants.ALL_AGENT.equals(ruleAgent)) {
										createAndSendRedInfo(check,user,rule);
										break;
									}
									if(ruleAgent.equals(String.valueOf(String.valueOf(agentId)))) {
										createAndSendRedInfo(check,user,rule);
										break;
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void createAndSendRedInfo(BsRedPacketCheck check, BsUser user, BsAutoRedPacketRule rule) {
		Date now = new Date();
		BsRedPacketInfo redInfo = new BsRedPacketInfo();
		redInfo.setApplyNo(check.getApplyNo());
		redInfo.setSerialNo(check.getSerialNo());
		redInfo.setUserId(user.getId());
		redInfo.setStatus(Constants.RED_PACKET_STATUS_INIT);
		redInfo.setAmount(check.getAmount());
		//有效期类型为固定时段
		if(rule.getValidTermType().equals(Constants.AUTO_RED_PACKET_VALID_TERM_TYPE_FIXED)) {
			redInfo.setUseTimeStart(check.getUseTimeStart());
			redInfo.setUseTimeEnd(check.getUseTimeEnd());
		}
		else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 000);
			redInfo.setUseTimeStart(calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, rule.getAvailableDays()-1);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 000);
			redInfo.setUseTimeEnd(calendar.getTime());
		}
		redInfo.setCreateTime(now);
		redInfoMapper.insertSelective(redInfo);
		
		//发送消息
		String channels = check.getNotifyChannel();
		if(StringUtil.isNotBlank(channels)) {
			String[] array = channels.split(",");
			for(String channel : array) {
				if(Constants.RED_PACKET_CHECK_NOTIFY_CHANNEL_SMS.equals(channel)) {
					List<String> mobiles = new ArrayList<String>();
					mobiles.add(user.getMobile());
					redInfoService.sendRedPacketMessage(1, mobiles, null, "抵扣红包", String.valueOf(redInfo.getAmount()));
				}
				if(Constants.RED_PACKET_CHECK_NOTIFY_CHANNEL_WECHAT.equals(channel)) {
					List<Integer> userIds = new ArrayList<Integer>();
					userIds.add(user.getId());
					redInfoService.sendRedPacketMessage(2, null, userIds, "抵扣红包", String.valueOf(redInfo.getAmount()));
				}
				if(Constants.RED_PACKET_CHECK_NOTIFY_CHANNEL_APP.equals(channel)) {
					List<Integer> userIds = new ArrayList<Integer>();
					userIds.add(user.getId());
					redInfoService.sendRedPacketMessage(3, null, userIds, "抵扣红包", String.valueOf(redInfo.getAmount()));
				}
			}
		}
	}
	
	@Override
	@MethodRole("APP")
	public List<Integer> autoRedPacketSend(AutoRedPacketParams params) {
		List<Integer> redPacketInfoIds = new ArrayList<Integer>();
		BsUser user = userMapper.selectByPrimaryKey(params.getUserId());
		if(user != null) {
			int userId = user.getId();
			Integer agentId = user.getAgentId();
			Date now = new Date();
			
			//需要发送短信的列表
			List<MsgBean> msgBeanList = new ArrayList<MsgBean>();
			
			if(Constants.AUTO_RED_PACKET_TIGGER_TYPE_NEW_USER.equals(params.getTriggerType())) {
				//新用户注册规则
				BsAutoRedPacketRuleExample autoExample = new BsAutoRedPacketRuleExample();
				autoExample.createCriteria().andTriggerTypeEqualTo(Constants.AUTO_RED_PACKET_TIGGER_TYPE_NEW_USER).andStatusEqualTo(Constants.AUTO_RED_PACKET_STATUS_AVAILABLE).andDistributeTimeStartLessThanOrEqualTo(now).andDistributeTimeEndGreaterThanOrEqualTo(now);
				List<BsAutoRedPacketRule> registerRuleList = autoRedMapper.selectByExample(autoExample);
				if(!CollectionUtils.isEmpty(registerRuleList)) {
					for(BsAutoRedPacketRule rule : registerRuleList) {
						if(agentId == null) {
							String adId = "," + Constants.NON_AGENT + ",";
							String agentIds = "," + rule.getAgentIds() + ",";
							if(Constants.ALL_AGENT.equals(rule.getAgentIds()) ||Constants.NON_AGENT.equals(rule.getAgentIds()) || agentIds.contains(adId)) {
								BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
								checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
								List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
								if(!CollectionUtils.isEmpty(checkList)) {
									BsRedPacketCheck check = checkList.get(0);
									int packetId = sendPacketAndSendMsg(rule,check,userId,msgBeanList);
									if(packetId != 0) {
										redPacketInfoIds.add(packetId);
									}
								}
							}
						}else {
							if(!Constants.NON_AGENT.equals(rule.getAgentIds())) {
								if(Constants.ALL_AGENT.equals(rule.getAgentIds())) {
									//所有用户
									BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
									checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
									List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
									if(!CollectionUtils.isEmpty(checkList)) {
										BsRedPacketCheck check = checkList.get(0);
										int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
										if(packetId != 0) {
											redPacketInfoIds.add(packetId);
										}
									}
								}else {
									String adId = "," + agentId + ",";
									String agentIds = "," + rule.getAgentIds() + ",";
									if(agentIds.contains(adId)) {
										//符合渠道
										BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
										checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
										List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
										if(!CollectionUtils.isEmpty(checkList)) {
											BsRedPacketCheck check = checkList.get(0);
											int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
											if(packetId != 0) {
												redPacketInfoIds.add(packetId);
											}
										}
									}
								}
							}
						}
					}
				}
			}else if(Constants.AUTO_RED_PACKET_TIGGER_TYPE_INVITE_FULL.equals(params.getTriggerType())) {
				//邀请满额规则
				Integer recommendId = user.getRecommendId();
				if(recommendId != null) {
					BsAutoRedPacketRuleExample autoExample1 = new BsAutoRedPacketRuleExample();
					//邀请满额
					autoExample1.createCriteria().andTriggerTypeEqualTo(Constants.AUTO_RED_PACKET_TIGGER_TYPE_INVITE_FULL).andStatusEqualTo(Constants.AUTO_RED_PACKET_STATUS_AVAILABLE).andDistributeTimeStartLessThanOrEqualTo(now).andDistributeTimeEndGreaterThanOrEqualTo(now);
					List<BsAutoRedPacketRule> recommendRuleList = autoRedMapper.selectByExample(autoExample1);
					if(!CollectionUtils.isEmpty(recommendRuleList)) {
						for(BsAutoRedPacketRule rule : recommendRuleList) {
							if(agentId == null) {
								String adId = "," + Constants.NON_AGENT + ",";
								String agentIds = "," + rule.getAgentIds() + ",";
								if(Constants.ALL_AGENT.equals(rule.getAgentIds()) ||Constants.NON_AGENT.equals(rule.getAgentIds()) || agentIds.contains(adId)) {
									//查询用户推荐用户数
									int triggerInviteNum = rule.getTriggerInviteNum();
									Map<String,Object> map = new HashMap<String,Object>();
									map.put("recommendId",recommendId);
									map.put("status",1);
									map.put("startTime",rule.getDistributeTimeStart());
									map.put("endTime",rule.getDistributeTimeEnd());
									int recommendCount = userMapper.countRecommendUser(map);
									if(recommendCount >= triggerInviteNum) {
										BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
										checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
										List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
										if(!CollectionUtils.isEmpty(checkList)) {
											BsRedPacketCheck check = checkList.get(0);
											//判断此红包是否已发放，已发放就不会重复发放
											BsRedPacketInfoExample example = new BsRedPacketInfoExample();
											example.createCriteria().andApplyNoEqualTo(check.getApplyNo()).andSerialNoEqualTo(check.getSerialNo()).andUserIdEqualTo(recommendId);
											List<BsRedPacketInfo> redPacketInfoList = redInfoMapper.selectByExample(example);
											if(CollectionUtils.isEmpty(redPacketInfoList)) {
												int packetId = sendPacketAndSendMsg(rule,check,recommendId, msgBeanList);
												if(packetId != 0) {
													redPacketInfoIds.add(packetId);
												}
											}
										}
									}
								}
							}else {
								if(!Constants.NON_AGENT.equals(rule.getAgentIds())) {
									if(Constants.ALL_AGENT.equals(rule.getAgentIds())) {
										//查询用户推荐用户数
										int triggerInviteNum = rule.getTriggerInviteNum();
										Map<String,Object> map = new HashMap<String,Object>();
										map.put("recommendId",recommendId);
										map.put("status",1);
										map.put("startTime",rule.getDistributeTimeStart());
										map.put("endTime",rule.getDistributeTimeEnd());
										int recommendCount = userMapper.countRecommendUser(map);
										if(recommendCount >= triggerInviteNum) {
											BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
											checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
											List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
											if(!CollectionUtils.isEmpty(checkList)) {
												BsRedPacketCheck check = checkList.get(0);
												//判断此红包是否已发放，已发放就不会重复发放
												BsRedPacketInfoExample example = new BsRedPacketInfoExample();
												example.createCriteria().andApplyNoEqualTo(check.getApplyNo()).andSerialNoEqualTo(check.getSerialNo()).andUserIdEqualTo(recommendId);
												List<BsRedPacketInfo> redPacketInfoList = redInfoMapper.selectByExample(example);
												if(CollectionUtils.isEmpty(redPacketInfoList)) {
													int packetId = sendPacketAndSendMsg(rule,check,recommendId, msgBeanList);
													if(packetId != 0) {
														redPacketInfoIds.add(packetId);
													}
												}
											}
										}
									}else {
										String adId = "," + agentId + ",";
										String agentIds = "," + rule.getAgentIds() + ",";
										if(agentIds.contains(adId)) {
											//查询用户推荐用户数
											int triggerInviteNum = rule.getTriggerInviteNum();
											Map<String,Object> map = new HashMap<String,Object>();
											map.put("recommendId",recommendId);
											map.put("status",1);
											map.put("startTime",rule.getDistributeTimeStart());
											map.put("endTime",rule.getDistributeTimeEnd());
											int recommendCount = userMapper.countRecommendUser(map);
											if(recommendCount >= triggerInviteNum) {
												BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
												checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
												List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
												if(!CollectionUtils.isEmpty(checkList)) {
													BsRedPacketCheck check = checkList.get(0);
													//判断此红包是否已发放，已发放就不会重复发放
													BsRedPacketInfoExample example = new BsRedPacketInfoExample();
													example.createCriteria().andApplyNoEqualTo(check.getApplyNo()).andSerialNoEqualTo(check.getSerialNo()).andUserIdEqualTo(recommendId);
													List<BsRedPacketInfo> redPacketInfoList = redInfoMapper.selectByExample(example);
													if(CollectionUtils.isEmpty(redPacketInfoList)) {
														int packetId = sendPacketAndSendMsg(rule,check,recommendId, msgBeanList);
														if(packetId != 0) {
															redPacketInfoIds.add(packetId);
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}else if(Constants.AUTO_RED_PACKET_TIGGER_TYPE_BUY_FULL.equals(params.getTriggerType())) {
				//购买满额规则
				BsAutoRedPacketRuleExample autoExample = new BsAutoRedPacketRuleExample();
				autoExample.createCriteria().andTriggerTypeEqualTo(Constants.AUTO_RED_PACKET_TIGGER_TYPE_BUY_FULL).andStatusEqualTo(Constants.AUTO_RED_PACKET_STATUS_AVAILABLE).andDistributeTimeStartLessThanOrEqualTo(now).andDistributeTimeEndGreaterThanOrEqualTo(now).andTriggerAmountStartLessThanOrEqualTo(params.getAmount()).andTriggerAmountEndGreaterThanOrEqualTo(params.getAmount());
				List<BsAutoRedPacketRule> ruleList = autoRedMapper.selectByExample(autoExample);
				if(!CollectionUtils.isEmpty(ruleList)) {
					for(BsAutoRedPacketRule rule : ruleList) {
						if(agentId == null) {
							String adId = "," + Constants.NON_AGENT + ",";
							String agentIds = "," + rule.getAgentIds() + ",";
							if(Constants.ALL_AGENT.equals(rule.getAgentIds()) ||Constants.NON_AGENT.equals(rule.getAgentIds()) || agentIds.contains(adId)) {
								BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
								checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
								List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
								if(!CollectionUtils.isEmpty(checkList)) {
									BsRedPacketCheck check = checkList.get(0);
									int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
									if(packetId != 0) {
										redPacketInfoIds.add(packetId);
									}
								}
							}
						}else {
							if(!Constants.NON_AGENT.equals(rule.getAgentIds())) {
								if(Constants.ALL_AGENT.equals(rule.getAgentIds())) {
									BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
									checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
									List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
									if(!CollectionUtils.isEmpty(checkList)) {
										BsRedPacketCheck check = checkList.get(0);
										int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
										if(packetId != 0) {
											redPacketInfoIds.add(packetId);
										}
									}
								}else {
									String adId = "," + agentId + ",";
									String agentIds = "," + rule.getAgentIds() + ",";
									if(agentIds.contains(adId)) {
										BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
										checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
										List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
										if(!CollectionUtils.isEmpty(checkList)) {
											BsRedPacketCheck check = checkList.get(0);
											int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
											if(packetId != 0) {
												redPacketInfoIds.add(packetId);
											}
										}
									}
								}
							}
						}
					}
			    }
		   }else if(Constants.AUTO_RED_PACKET_TIGGER_TYPE_318_GAME_OLD_USER.equals(params.getTriggerType())) {
				//318老用户规则
				BsAutoRedPacketRuleExample autoExample = new BsAutoRedPacketRuleExample();
				autoExample.createCriteria().andTriggerTypeEqualTo(Constants.AUTO_RED_PACKET_TIGGER_TYPE_318_GAME_OLD_USER).andStatusEqualTo(Constants.AUTO_RED_PACKET_STATUS_AVAILABLE).andDistributeTimeStartLessThanOrEqualTo(now).andDistributeTimeEndGreaterThanOrEqualTo(now);
				List<BsAutoRedPacketRule> registerRuleList = autoRedMapper.selectByExample(autoExample);
				if(!CollectionUtils.isEmpty(registerRuleList)) {
					for(BsAutoRedPacketRule rule : registerRuleList) {
						if(agentId == null) {
							String adId = "," + Constants.NON_AGENT + ",";
							String agentIds = "," + rule.getAgentIds() + ",";
							if(Constants.ALL_AGENT.equals(rule.getAgentIds()) ||Constants.NON_AGENT.equals(rule.getAgentIds()) || agentIds.contains(adId)) {
								BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
								checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
								List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
								if(!CollectionUtils.isEmpty(checkList)) {
									BsRedPacketCheck check = checkList.get(0);
									int packetId = sendPacketAndSendMsg(rule,check,userId,msgBeanList);
									if(packetId != 0) {
										redPacketInfoIds.add(packetId);
									}
								}
							}
						}else {
							if(!Constants.NON_AGENT.equals(rule.getAgentIds())) {
								if(Constants.ALL_AGENT.equals(rule.getAgentIds())) {
									//所有用户
									BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
									checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
									List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
									if(!CollectionUtils.isEmpty(checkList)) {
										BsRedPacketCheck check = checkList.get(0);
										int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
										if(packetId != 0) {
											redPacketInfoIds.add(packetId);
										}
									}
								}else {
									String adId = "," + agentId + ",";
									String agentIds = "," + rule.getAgentIds() + ",";
									if(agentIds.contains(adId)) {
										//符合渠道
										BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
										checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
										List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
										if(!CollectionUtils.isEmpty(checkList)) {
											BsRedPacketCheck check = checkList.get(0);
											int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
											if(packetId != 0) {
												redPacketInfoIds.add(packetId);
											}
										}
									}
								}
							}
						}
					}
				}
			}else if(Constants.AUTO_RED_PACKET_TIGGER_TYPE_EXCHANGE_4MALL.equals(params.getTriggerType())) {
				//积分商城兑换
				BsAutoRedPacketRuleExample autoExample = new BsAutoRedPacketRuleExample();
				autoExample.createCriteria().andTriggerTypeEqualTo(Constants.AUTO_RED_PACKET_TIGGER_TYPE_EXCHANGE_4MALL).andStatusEqualTo(Constants.AUTO_RED_PACKET_STATUS_AVAILABLE).andDistributeTimeStartLessThanOrEqualTo(now).andDistributeTimeEndGreaterThanOrEqualTo(now);
				List<BsAutoRedPacketRule> registerRuleList = autoRedMapper.selectByExample(autoExample);
				if(!CollectionUtils.isEmpty(registerRuleList)) {
					for(BsAutoRedPacketRule rule : registerRuleList) {
						if(agentId == null) {
							String adId = "," + Constants.NON_AGENT + ",";
							String agentIds = "," + rule.getAgentIds() + ",";
							if(Constants.ALL_AGENT.equals(rule.getAgentIds()) ||Constants.NON_AGENT.equals(rule.getAgentIds()) || agentIds.contains(adId)) {
								BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
								checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
								List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
								if(!CollectionUtils.isEmpty(checkList)) {
									BsRedPacketCheck check = checkList.get(0);
									int packetId = sendPacketAndSendMsg(rule,check,userId,msgBeanList);
									if(packetId != 0) {
										redPacketInfoIds.add(packetId);
									}
								}
							}
						}else {
							if(!Constants.NON_AGENT.equals(rule.getAgentIds())) {
								if(Constants.ALL_AGENT.equals(rule.getAgentIds())) {
									//所有用户
									BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
									checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
									List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
									if(!CollectionUtils.isEmpty(checkList)) {
										BsRedPacketCheck check = checkList.get(0);
										int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
										if(packetId != 0) {
											redPacketInfoIds.add(packetId);
										}
									}
								}else {
									String adId = "," + agentId + ",";
									String agentIds = "," + rule.getAgentIds() + ",";
									if(agentIds.contains(adId)) {
										//符合渠道
										BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
										checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
										List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
										if(!CollectionUtils.isEmpty(checkList)) {
											BsRedPacketCheck check = checkList.get(0);
											int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
											if(packetId != 0) {
												redPacketInfoIds.add(packetId);
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
		//处理发消息逻辑
		sendMessages(msgBeanList);
			
		}
		return redPacketInfoIds;
	}
	
	/**
	 * 红包发放和发送通知消息业务逻辑
	 * @param rule 规则实体类
	 * @param check 红包发放批次审核类
	 * @param userId 用户编号
	 * @return Integer 红包编号
	 */
	private Integer sendPacketAndSendMsg(BsAutoRedPacketRule rule,BsRedPacketCheck check,Integer userId, List<MsgBean> msgBeanList) {
		//判断红包是否已发完
		BsRedPacketInfoExample infoExample = new BsRedPacketInfoExample();
		infoExample.createCriteria().andApplyNoEqualTo(check.getApplyNo()).andSerialNoEqualTo(check.getSerialNo());
		int count = redInfoMapper.countByExample(infoExample);
		if(check.getTotal() > count) {
			Date now = new Date();
			BsRedPacketInfo redInfo = new BsRedPacketInfo();
			redInfo.setApplyNo(check.getApplyNo());
			redInfo.setSerialNo(check.getSerialNo());
			redInfo.setUserId(userId);
			redInfo.setStatus(Constants.RED_PACKET_STATUS_INIT);
			redInfo.setAmount(check.getAmount());
			//有效期类型为固定时段
			if(rule.getValidTermType().equals(Constants.AUTO_RED_PACKET_VALID_TERM_TYPE_FIXED)) {
				redInfo.setUseTimeStart(check.getUseTimeStart());
				redInfo.setUseTimeEnd(check.getUseTimeEnd());
			}
			else {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(now);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 000);
				redInfo.setUseTimeStart(calendar.getTime());
				calendar.add(Calendar.DAY_OF_MONTH, rule.getAvailableDays()-1);
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 000);
				redInfo.setUseTimeEnd(calendar.getTime());
			}
			redInfo.setCreateTime(now);
			redInfo.setUpdateTime(new Date());
			redInfoMapper.insertSelective(redInfo);
			
			//加入需要发短信的列表
			MsgBean msgBean = new MsgBean();
			msgBean.setUserId(userId);
			msgBean.setCheck(check);
			msgBeanList.add(msgBean);
			
			//返回发放红包的Id
			return redInfo.getId() == null ? 0 : redInfo.getId();
		}
		return 0;
	}
	
	//发送消息
	private void sendMessages(List<MsgBean> msgBeanList){
		//发信息提醒用户
		//发给谁
		if(msgBeanList.isEmpty()){
			return;
		}
		
		BsUser user = userMapper.selectByPrimaryKey(msgBeanList.get(0).getUserId());
		if(user == null) {
			return;
		}
		
		List<String> mobiles = new ArrayList<String>();
		mobiles.add(user.getMobile());
		List<Integer> userIds = new ArrayList<Integer>();
		userIds.add(user.getId());
		
		String channels = "";
		//发什么内容
		double amount = 0;
		for (MsgBean msgBean : msgBeanList) {
			channels += msgBean.getCheck().getNotifyChannel() + ",";
			amount = MoneyUtil.add(amount, msgBean.getCheck().getAmount()).doubleValue();
		}
		
		if(StringUtil.isNotEmpty(channels) && channels.contains(Constants.RED_PACKET_CHECK_NOTIFY_CHANNEL_SMS)){//只要包含SMS都要发
			//短信通知
			redPackatInfoService.sendRedPacketMessage(Constants.RED_PACKET_CHECK_NOTIFY_TYPE_SMS, mobiles, null, "抵扣红包", String.valueOf(amount));
		}
		if(StringUtil.isNotEmpty(channels) && channels.contains(Constants.RED_PACKET_CHECK_NOTIFY_CHANNEL_WECHAT)){//只要包含WECHAT都要发
			//微信通知
			redPackatInfoService.sendRedPacketMessage(Constants.RED_PACKET_CHECK_NOTIFY_TYPE_WECHAT, null, userIds, "抵扣红包", String.valueOf(amount));
		}
		if(StringUtil.isNotEmpty(channels) && channels.contains(Constants.RED_PACKET_CHECK_NOTIFY_CHANNEL_APP)){//只要包含APP都要发
			//APP通知
			redPackatInfoService.sendRedPacketMessage(Constants.RED_PACKET_CHECK_NOTIFY_TYPE_APP, null, userIds, "抵扣红包",  String.valueOf(amount));
		}
	}
	
	private class MsgBean{
		private int userId;
		private BsRedPacketCheck check;
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public BsRedPacketCheck getCheck() {
			return check;
		}
		public void setCheck(BsRedPacketCheck check) {
			this.check = check;
		}
		
	}

    /** 
     * @see com.pinting.business.service.site.RedPacketService#autoRedPacketTotalAmount(java.lang.String, java.lang.String)
     */
    @Override
    public Double autoRedPacketTotalAmount(String triggerType, String showTerminal) {
        Double totalAmount = 0d;
        List<AutoRedPacketTotalAmountVO> rules = redCheckMapper.autoRedPacketTotalGrid(triggerType, showTerminal);
        if(!CollectionUtils.isEmpty(rules)) {
            for (AutoRedPacketTotalAmountVO bsAutoRedPacketRule : rules) {
                String agentIds = bsAutoRedPacketRule.getAgentIds();
                String[] agentIdArray = agentIds.split(",");
                for (String agentId : agentIdArray) {
                    if(StringUtil.isBlank(showTerminal)) {
                        // 0，普通用户
                        if("0".equals(agentId)) {
                            totalAmount = MoneyUtil.add(totalAmount, bsAutoRedPacketRule.getSubtract()).doubleValue();
                            break;
                        }
                        // -1，全部渠道
                        else if("-1".equals(agentId)) {
                            totalAmount = MoneyUtil.add(totalAmount, bsAutoRedPacketRule.getSubtract()).doubleValue();
                            break;
                        }
                    } else {
                        // 15，钱报用户
                        if(String.valueOf(Constants.AGENT_QIANBAO_ID).equals(agentId)) {
                            totalAmount = MoneyUtil.add(totalAmount, bsAutoRedPacketRule.getSubtract()).doubleValue();
                            break;
                        }
                        // -1，全部渠道
                        else if("-1".equals(agentId)) {
                            totalAmount = MoneyUtil.add(totalAmount, bsAutoRedPacketRule.getSubtract()).doubleValue();
                            break;
                        }
                    }
                }
            }
        }
        return totalAmount;
    }

    /**
     * 母亲节活动自动红包发放
     * @param serialNo  审核批次号
     * @param userId    用户ID
     */
    @Override
    public void motherDayActivityAutoRedPacketSend(String serialNo, Integer userId) {
        Date now = new Date();
        // 数据校验和资格鉴定-开始
        if (null == userId) {
            throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
        }
        if (StringUtil.isBlank(serialNo)) {
            throw new PTMessageException(PTMessageEnum.RED_POCKET_CHECK_PARAM_ERROR);
        }
        // 活动是否截止
        Date startTime;
        Date endTime;
        try {
            startTime = DateUtil.parse(Constants.MOTHER_DAY_ACTIVITY_START_TIME, "yyyy-MM-dd HH:mm:ss");
            endTime = DateUtil.parse(Constants.MOTHER_DAY_ACTIVITY_END_TIME, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            throw new PTMessageException(PTMessageEnum.DATE_TRANS_ERROR);
        }
        if(DateUtil.compareTo(now, startTime) < 0) {    // 活动还未开始
            throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START);
        }
        if(DateUtil.compareTo(now, endTime) >= 0) { // 活动已经结束
            throw new PTMessageException(PTMessageEnum.ACTIVITY_END);
        }
        
        // 查询该用户在所有当前活动批次的红包表中是否有三条数据
        int redPacketInfoCount = bsRedPacketInfoMapper.countByRedPacketMotherDay(userId);
        if (redPacketInfoCount >= Constants.MOTHER_DRAW_CHANCE) {
            throw new PTMessageException(PTMessageEnum.RED_PACKET_NOT_ELIGIBLE);
        }
        // 数据校验和资格鉴定-结束
        
        // 红包ID列表
        List<Integer> redPacketInfoIds = new ArrayList<Integer>();
        //需要发送短信的列表
        List<MsgBean> msgBeanList = new ArrayList<MsgBean>();
        // 用户信息
        BsUser bsUser = userMapper.selectByPrimaryKey(userId);
        Integer agentId = bsUser.getAgentId();
        
        // 满足条件的红包，是当前选中批次的红包——母亲节活动规则列表
        BsAutoRedPacketRuleExample autoExample = new BsAutoRedPacketRuleExample();
        autoExample.createCriteria()
            .andTriggerTypeEqualTo(Constants.AUTO_RED_PACKET_TIGGER_TYPE_318_GAME_OLD_USER)
            .andStatusEqualTo(Constants.AUTO_RED_PACKET_STATUS_AVAILABLE)
            .andSerialNoEqualTo(serialNo)
            .andDistributeTimeStartLessThanOrEqualTo(now)
            .andDistributeTimeEndGreaterThanOrEqualTo(now);
        List<BsAutoRedPacketRule> motherDayActivityRuleList = autoRedMapper
            .selectByExample(autoExample);
        
        if(!CollectionUtils.isEmpty(motherDayActivityRuleList)) {
            for(BsAutoRedPacketRule rule : motherDayActivityRuleList) {
                 if(agentId == null) {
                      String adId = "," + Constants.NON_AGENT + ",";
                      String agentIds = "," + rule.getAgentIds() + ",";
                      if(Constants.ALL_AGENT.equals(rule.getAgentIds()) || Constants.NON_AGENT.equals(rule.getAgentIds()) || agentIds.contains(adId)) {
                           BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
                           checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
                           List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
                           if(!CollectionUtils.isEmpty(checkList)) {
                                BsRedPacketCheck check = checkList.get(0);
                                int packetId = sendPacketAndSendMsg(rule,check,userId,msgBeanList);
                                if(packetId != 0) {
                                     redPacketInfoIds.add(packetId);
                                }
                           }
                      }
                 } else {
                      if(!Constants.NON_AGENT.equals(rule.getAgentIds())) {
                           if(Constants.ALL_AGENT.equals(rule.getAgentIds())) {
                                //所有用户
                                BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
                                checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
                                List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
                                if(!CollectionUtils.isEmpty(checkList)) {
                                     BsRedPacketCheck check = checkList.get(0);
                                     int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
                                     if(packetId != 0) {
                                          redPacketInfoIds.add(packetId);
                                     }
                                }
                           }else {
                                String adId = "," + agentId + ",";
                                String agentIds = "," + rule.getAgentIds() + ",";
                                if(agentIds.contains(adId)) {
                                     //符合渠道
                                     BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
                                     checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
                                     List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
                                     if(!CollectionUtils.isEmpty(checkList)) {
                                          BsRedPacketCheck check = checkList.get(0);
                                          int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
                                          if(packetId != 0) {
                                               redPacketInfoIds.add(packetId);
                                          }
                                     }
                                }
                           }
                      }
                 }
            }
        }
        if (CollectionUtils.isEmpty(redPacketInfoIds)) {
            throw new PTMessageException(PTMessageEnum.RED_PACKET_OVER);
        }
        //处理发消息逻辑
        sendMessages(msgBeanList);
    }
    
    
    /**
     * 518活动自动红包发放
     * @param amount    触发金额
     * @param term  触发期限
     * @param userId    用户ID
     */
    @Override
    public void financeDay518ActivityAutoRedPacketSend(final Double amount, final Integer term,
                                                       final Integer userId) {
        final Date now = new Date();
        // 数据校验和资格鉴定-开始
        if (null == userId) {
            throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
        }
        if (null == amount) {
            throw new PTMessageException(PTMessageEnum.RED_POCKET_CHECK_PARAM_ERROR);
        }
        if (null == term) {
            throw new PTMessageException(PTMessageEnum.RED_POCKET_CHECK_PARAM_ERROR);
        }
        if (amount.compareTo(Constants.FINANCE_DAY_518_MINIMUM_INVESTMENT_AMOUNT) < 0) {
            throw new PTMessageException(PTMessageEnum.LESS_THAN_MINIMUM_INVESTMENT_AMOUNT);
        }
        // 活动是否截止
        Date startTime;
        Date endTime;
        try {
            startTime = DateUtil.parse(Constants.FINANCE_DAY_518_ACTIVITY_START_TIME,
                "yyyy-MM-dd HH:mm:ss");
            endTime = DateUtil.parse(Constants.FINANCE_DAY_518_ACTIVITY_END_TIME,
                "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            throw new PTMessageException(PTMessageEnum.DATE_TRANS_ERROR);
        }
        if (DateUtil.compareTo(now, startTime) < 0) { // 活动还未开始
            throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START);
        }
        if (DateUtil.compareTo(now, endTime) >= 0) { // 活动已经结束
            throw new PTMessageException(PTMessageEnum.ACTIVITY_END);
        }

        // 查询该用户在所有当前活动批次的红包表中是否有5条数据
        int redPacketInfoCount = bsRedPacketInfoMapper.countByRedPacketMotherDay(userId);
        if (redPacketInfoCount >= Constants.FINANCE_DAY_518_ACTIVITY_DRAW_CHANCE) {
            throw new PTMessageException(PTMessageEnum.RED_PACKET_NOT_ELIGIBLE);
        }
        // 数据校验和资格鉴定-结束

        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {

                // 红包ID列表
                List<Integer> redPacketInfoIds = new ArrayList<Integer>();
                //需要发送短信的列表
                List<MsgBean> msgBeanList = new ArrayList<MsgBean>();
                // 用户信息
                BsUser bsUser = userMapper.selectByPrimaryKey(userId);
                if(null == bsUser) {
                    throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
                }
                Integer agentId = bsUser.getAgentId();

                // 满足条件的自动红包规则，以及满减金额和渠道以及期限，按满额full倒叙
                List<BsRedPacketCheckVO> checkVOList = redCheckMapper
                    .selectEffectiveActivityRedPacketCheckAndRule(String.valueOf(term));

                if (!CollectionUtils.isEmpty(checkVOList)) {
                    boolean isDrawed = false;
                    for (BsRedPacketCheckVO rule : checkVOList) {
                        if (amount >= rule.getFull()) {
                            if (agentId == null) {
                                String adId = "," + Constants.NON_AGENT + ",";
                                String agentIds = "," + rule.getAgentIds() + ",";
                                if (Constants.ALL_AGENT.equals(rule.getAgentIds())
                                    || Constants.NON_AGENT.equals(rule.getAgentIds())
                                    || agentIds.contains(adId)) {
                                    isDrawed = true;
                                    BsRedPacketCheck check = redCheckMapper
                                        .selectByPrimaryKey(rule.getId());
                                    BsAutoRedPacketRuleExample bsAutoRedPacketRuleExample = new BsAutoRedPacketRuleExample();
                                    bsAutoRedPacketRuleExample.createCriteria()
                                        .andSerialNoEqualTo(check.getSerialNo());
                                    List<BsAutoRedPacketRule> packetRules = autoRedMapper
                                        .selectByExample(bsAutoRedPacketRuleExample);
                                    int packetId = sendPacketAndSendMsg(packetRules.get(0),
                                        check, userId, msgBeanList);
                                    if (packetId != 0) {
                                        redPacketInfoIds.add(packetId);
                                    }
                                }
                            } else {
                                if (!Constants.NON_AGENT.equals(rule.getAgentIds())) {
                                    if (Constants.ALL_AGENT.equals(rule.getAgentIds())) {
                                        isDrawed = true;
                                        BsRedPacketCheck check = redCheckMapper
                                            .selectByPrimaryKey(rule.getId());
                                        BsAutoRedPacketRuleExample bsAutoRedPacketRuleExample = new BsAutoRedPacketRuleExample();
                                        bsAutoRedPacketRuleExample.createCriteria()
                                            .andSerialNoEqualTo(check.getSerialNo());
                                        List<BsAutoRedPacketRule> packetRules = autoRedMapper
                                            .selectByExample(bsAutoRedPacketRuleExample);
                                        int packetId = sendPacketAndSendMsg(
                                            packetRules.get(0), check, userId, msgBeanList);
                                        if (packetId != 0) {
                                            redPacketInfoIds.add(packetId);
                                        }
                                    }
                                } else {
                                    String adId = "," + Constants.NON_AGENT + ",";
                                    String agentIds = "," + rule.getAgentIds() + ",";
                                    if (agentIds.contains(adId)) {
                                        isDrawed = true;
                                        BsRedPacketCheck check = redCheckMapper
                                            .selectByPrimaryKey(rule.getId());
                                        BsAutoRedPacketRuleExample bsAutoRedPacketRuleExample = new BsAutoRedPacketRuleExample();
                                        bsAutoRedPacketRuleExample.createCriteria()
                                            .andSerialNoEqualTo(check.getSerialNo());
                                        List<BsAutoRedPacketRule> packetRules = autoRedMapper
                                            .selectByExample(bsAutoRedPacketRuleExample);
                                        int packetId = sendPacketAndSendMsg(
                                            packetRules.get(0), check, userId, msgBeanList);
                                        if (packetId != 0) {
                                            redPacketInfoIds.add(packetId);
                                        }
                                    }
                                }
                            }
                        }
                        if(isDrawed) {
                            break;
                        }
                    }
                }
                if (CollectionUtils.isEmpty(redPacketInfoIds)) {
                    throw new PTMessageException(PTMessageEnum.RED_PACKET_OVER);
                }
                //处理发消息逻辑
                sendMessages(msgBeanList);
                return true;
            }
        });
    }

	@Override
	public void double11ActivityAutoRedPacketSend(final Integer userId, final String redPacketName) {
		final Date now = new Date();
		// 数据校验和资格鉴定-开始
		if (null == userId) {
			throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
		}

		// 数据校验和资格鉴定-结束
		// 红包ID列表
		List<Integer> redPacketInfoIds = new ArrayList<Integer>();
		//需要发送短信的列表
		List<MsgBean> msgBeanList = new ArrayList<MsgBean>();
		// 用户信息
		BsUser bsUser = userMapper.selectByPrimaryKey(userId);
		Integer agentId = bsUser.getAgentId();

		// 满足条件的红包
		BsAutoRedPacketRuleExample autoExample = new BsAutoRedPacketRuleExample();
		autoExample.createCriteria()
				.andTriggerTypeEqualTo(Constants.AUTO_RED_PACKET_TIGGER_TYPE_318_GAME_OLD_USER)
				.andStatusEqualTo(Constants.AUTO_RED_PACKET_STATUS_AVAILABLE)
				.andDistributeTimeStartLessThanOrEqualTo(now)
				.andDistributeTimeEndGreaterThanOrEqualTo(now);
		List<BsAutoRedPacketRule> motherDayActivityRuleList = autoRedMapper
				.selectByExample(autoExample);

		if(!CollectionUtils.isEmpty(motherDayActivityRuleList)) {
			for(BsAutoRedPacketRule rule : motherDayActivityRuleList) {
				if(agentId == null) {
					String adId = "," + Constants.NON_AGENT + ",";
					String agentIds = "," + rule.getAgentIds() + ",";
					if(Constants.ALL_AGENT.equals(rule.getAgentIds()) || Constants.NON_AGENT.equals(rule.getAgentIds()) || agentIds.contains(adId)) {
						BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
						checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo())
								.andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS)
								.andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO)
								.andSerialNameLike("%" + redPacketName + "%");
						List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
						if(!CollectionUtils.isEmpty(checkList)) {
							BsRedPacketCheck check = checkList.get(0);
							int packetId = sendPacketAndSendMsg(rule,check,userId,msgBeanList);
							if(packetId != 0) {
								redPacketInfoIds.add(packetId);
							}
						}
					}
				} else {
					if(!Constants.NON_AGENT.equals(rule.getAgentIds())) {
						if(Constants.ALL_AGENT.equals(rule.getAgentIds())) {
							//所有用户
							BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
							checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo())
									.andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS)
									.andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO)
									.andSerialNameLike("%" + redPacketName + "%");
							List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
							if(!CollectionUtils.isEmpty(checkList)) {
								BsRedPacketCheck check = checkList.get(0);
								int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
								if(packetId != 0) {
									redPacketInfoIds.add(packetId);
								}
							}
						}else {
							String adId = "," + agentId + ",";
							String agentIds = "," + rule.getAgentIds() + ",";
							if(agentIds.contains(adId)) {
								//符合渠道
								BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
								checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo())
										.andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS)
										.andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO)
										.andSerialNameLike("%" + redPacketName + "%");
								List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
								if(!CollectionUtils.isEmpty(checkList)) {
									BsRedPacketCheck check = checkList.get(0);
									int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
									if(packetId != 0) {
										redPacketInfoIds.add(packetId);
									}
								}
							}
						}
					}
				}
			}
		}
		if (CollectionUtils.isEmpty(redPacketInfoIds)) {
			throw new PTMessageException(PTMessageEnum.RED_PACKET_OVER);
		}
		//处理发消息逻辑
		sendMessages(msgBeanList);
	}

	@Override
	public Double sumAmountUsedRedPacket(Integer userId, String date) {
		Double amount =bsRedPacketInfoMapper.getSumAmountByUserTime(userId, date);
		return amount== null?0:amount;
	}

	@Override
	public void autoRedPacketSendByName(Integer userId, String serialName) {
		final Date now = new Date();
		// 数据校验和资格鉴定-开始
		if (null == userId) {
			throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
		}

		// 数据校验和资格鉴定-结束
		// 红包ID列表
		List<Integer> redPacketInfoIds = new ArrayList<Integer>();
		//需要发送短信的列表
		List<MsgBean> msgBeanList = new ArrayList<MsgBean>();
		// 用户信息
		BsUser bsUser = userMapper.selectByPrimaryKey(userId);
		Integer agentId = bsUser.getAgentId();

		// 满足条件的红包
		BsAutoRedPacketRuleExample autoExample = new BsAutoRedPacketRuleExample();
		autoExample.createCriteria()
				.andTriggerTypeEqualTo(Constants.AUTO_RED_PACKET_TIGGER_TYPE_318_GAME_OLD_USER)
				.andStatusEqualTo(Constants.AUTO_RED_PACKET_STATUS_AVAILABLE)
				.andDistributeTimeStartLessThanOrEqualTo(now)
				.andDistributeTimeEndGreaterThanOrEqualTo(now);
		List<BsAutoRedPacketRule> motherDayActivityRuleList = autoRedMapper
				.selectByExample(autoExample);

		if(!CollectionUtils.isEmpty(motherDayActivityRuleList)) {
			for(BsAutoRedPacketRule rule : motherDayActivityRuleList) {
				if(agentId == null) {
					String adId = "," + Constants.NON_AGENT + ",";
					String agentIds = "," + rule.getAgentIds() + ",";
					if(Constants.ALL_AGENT.equals(rule.getAgentIds()) || Constants.NON_AGENT.equals(rule.getAgentIds()) || agentIds.contains(adId)) {
						BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
						checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo())
								.andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS)
								.andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO)
								.andSerialNameLike("%" + serialName + "%");
						List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
						if(!CollectionUtils.isEmpty(checkList)) {
							BsRedPacketCheck check = checkList.get(0);
							int packetId = sendPacketAndSendMsg(rule,check,userId,msgBeanList);
							if(packetId != 0) {
								redPacketInfoIds.add(packetId);
							}
						}
					}
				} else {
					if(!Constants.NON_AGENT.equals(rule.getAgentIds())) {
						if(Constants.ALL_AGENT.equals(rule.getAgentIds())) {
							//所有用户
							BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
							checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo())
									.andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS)
									.andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO)
									.andSerialNameLike("%" + serialName + "%");
							List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
							if(!CollectionUtils.isEmpty(checkList)) {
								BsRedPacketCheck check = checkList.get(0);
								int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
								if(packetId != 0) {
									redPacketInfoIds.add(packetId);
								}
							}
						}else {
							String adId = "," + agentId + ",";
							String agentIds = "," + rule.getAgentIds() + ",";
							if(agentIds.contains(adId)) {
								//符合渠道
								BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
								checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo())
										.andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS)
										.andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO)
										.andSerialNameLike("%" + serialName + "%");
								List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
								if(!CollectionUtils.isEmpty(checkList)) {
									BsRedPacketCheck check = checkList.get(0);
									int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
									if(packetId != 0) {
										redPacketInfoIds.add(packetId);
									}
								}
							}
						}
					}
				}
			}
		}
		if (CollectionUtils.isEmpty(redPacketInfoIds)) {
			throw new PTMessageException(PTMessageEnum.RED_PACKET_OVER);
		}
		//处理发消息逻辑
		sendMessages(msgBeanList);
	}
	
	
	@Override
	public void weChatAutoRedPacketSendByName(Integer userId, String serialName) {
		final Date now = new Date();
		// 数据校验和资格鉴定-开始
		if (null == userId) {
			throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
		}

		// 数据校验和资格鉴定-结束
		// 红包ID列表
		List<Integer> redPacketInfoIds = new ArrayList<Integer>();
		//需要发送短信的列表
		List<MsgBean> msgBeanList = new ArrayList<MsgBean>();
		// 用户信息
		BsUser bsUser = userMapper.selectByPrimaryKey(userId);
		Integer agentId = bsUser.getAgentId();

		// 满足条件的红包
		BsAutoRedPacketRuleExample autoExample = new BsAutoRedPacketRuleExample();
		autoExample.createCriteria()
				.andTriggerTypeEqualTo(Constants.AUTO_RED_PACKET_TIGGER_TYPE_WECHAT_MINI_PROGRAM)
				.andStatusEqualTo(Constants.AUTO_RED_PACKET_STATUS_AVAILABLE)
				.andDistributeTimeStartLessThanOrEqualTo(now)
				.andDistributeTimeEndGreaterThanOrEqualTo(now);
		List<BsAutoRedPacketRule> motherDayActivityRuleList = autoRedMapper
				.selectByExample(autoExample);

		if(!CollectionUtils.isEmpty(motherDayActivityRuleList)) {
			for(BsAutoRedPacketRule rule : motherDayActivityRuleList) {
				if(agentId == null) {
					String adId = "," + Constants.NON_AGENT + ",";
					String agentIds = "," + rule.getAgentIds() + ",";
					if(Constants.ALL_AGENT.equals(rule.getAgentIds()) || Constants.NON_AGENT.equals(rule.getAgentIds()) || agentIds.contains(adId)) {
						BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
						checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo())
								.andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS)
								.andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO)
								.andSerialNameLike("%" + serialName + "%");
						List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
						if(!CollectionUtils.isEmpty(checkList)) {
							BsRedPacketCheck check = checkList.get(0);
							int packetId = sendPacketAndSendMsg(rule,check,userId,msgBeanList);
							if(packetId != 0) {
								redPacketInfoIds.add(packetId);
							}
						}
					}
				} else {
					if(!Constants.NON_AGENT.equals(rule.getAgentIds())) {
						if(Constants.ALL_AGENT.equals(rule.getAgentIds())) {
							//所有用户
							BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
							checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo())
									.andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS)
									.andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO)
									.andSerialNameLike("%" + serialName + "%");
							List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
							if(!CollectionUtils.isEmpty(checkList)) {
								BsRedPacketCheck check = checkList.get(0);
								int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
								if(packetId != 0) {
									redPacketInfoIds.add(packetId);
								}
							}
						}else {
							String adId = "," + agentId + ",";
							String agentIds = "," + rule.getAgentIds() + ",";
							if(agentIds.contains(adId)) {
								//符合渠道
								BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
								checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo())
										.andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS)
										.andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO)
										.andSerialNameLike("%" + serialName + "%");
								List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
								if(!CollectionUtils.isEmpty(checkList)) {
									BsRedPacketCheck check = checkList.get(0);
									int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
									if(packetId != 0) {
										redPacketInfoIds.add(packetId);
									}
								}
							}
						}
					}
				}
			}
		}
		if (CollectionUtils.isEmpty(redPacketInfoIds)) {
			throw new PTMessageException(PTMessageEnum.RED_PACKET_OVER);
		}
		//处理发消息逻辑
		sendMessages(msgBeanList);
	}

	@Override
	public void autoRedPacketPolicyType(Integer userId, String policyType) {
		final Date now = new Date();
		// 数据校验和资格鉴定-开始
		if (null == userId) {
			throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
		}

		// 数据校验和资格鉴定-结束
		// 红包ID列表
		List<Integer> redPacketInfoIds = new ArrayList<Integer>();
		//需要发送短信的列表
		List<MsgBean> msgBeanList = new ArrayList<MsgBean>();
		// 用户信息
		BsUser bsUser = userMapper.selectByPrimaryKey(userId);
		Integer agentId = bsUser.getAgentId();

		// 满足条件的红包
		BsAutoRedPacketRuleExample autoExample = new BsAutoRedPacketRuleExample();
		autoExample.createCriteria()
				.andTriggerTypeEqualTo(Constants.AUTO_RED_PACKET_TIGGER_TYPE_318_GAME_OLD_USER)
				.andStatusEqualTo(Constants.AUTO_RED_PACKET_STATUS_AVAILABLE)
				.andDistributeTimeStartLessThanOrEqualTo(now)
				.andDistributeTimeEndGreaterThanOrEqualTo(now);
		List<BsAutoRedPacketRule> motherDayActivityRuleList = autoRedMapper
				.selectByExample(autoExample);

		if(!CollectionUtils.isEmpty(motherDayActivityRuleList)) {
			for(BsAutoRedPacketRule rule : motherDayActivityRuleList) {
				if(agentId == null) {
					String adId = "," + Constants.NON_AGENT + ",";
					String agentIds = "," + rule.getAgentIds() + ",";
					if(Constants.ALL_AGENT.equals(rule.getAgentIds()) || Constants.NON_AGENT.equals(rule.getAgentIds()) || agentIds.contains(adId)) {
						BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
						checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo())
								.andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS)
								.andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO)
								.andPolicyTypeLike("%" + policyType + "%");
						List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
						if(!CollectionUtils.isEmpty(checkList)) {
							BsRedPacketCheck check = checkList.get(0);
							int packetId = sendPacketAndSendMsg(rule,check,userId,msgBeanList);
							if(packetId != 0) {
								redPacketInfoIds.add(packetId);
							}
						}
					}
				} else {
					if(!Constants.NON_AGENT.equals(rule.getAgentIds())) {
						if(Constants.ALL_AGENT.equals(rule.getAgentIds())) {
							//所有用户
							BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
							checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo())
									.andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS)
									.andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO)
									.andPolicyTypeLike("%" + policyType + "%");
							List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
							if(!CollectionUtils.isEmpty(checkList)) {
								BsRedPacketCheck check = checkList.get(0);
								int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
								if(packetId != 0) {
									redPacketInfoIds.add(packetId);
								}
							}
						}else {
							String adId = "," + agentId + ",";
							String agentIds = "," + rule.getAgentIds() + ",";
							if(agentIds.contains(adId)) {
								//符合渠道
								BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
								checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo())
										.andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS)
										.andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO)
										.andPolicyTypeLike("%" + policyType + "%");
								List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
								if(!CollectionUtils.isEmpty(checkList)) {
									BsRedPacketCheck check = checkList.get(0);
									int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
									if(packetId != 0) {
										redPacketInfoIds.add(packetId);
									}
								}
							}
						}
					}
				}
			}
		}
		if (CollectionUtils.isEmpty(redPacketInfoIds)) {
			throw new PTMessageException(PTMessageEnum.RED_PACKET_OVER);
		}
		//处理发消息逻辑
		sendMessages(msgBeanList);
	}

	@Override
	public void autoRedPacketSendBySeriaNo(Integer userId, String serialNo) {
		Date now = new Date();
		// 数据校验和资格鉴定-开始
		if (null == userId) {
			throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
		}
		if (StringUtil.isBlank(serialNo)) {
			throw new PTMessageException(PTMessageEnum.RED_POCKET_CHECK_PARAM_ERROR);
		}
		// 数据校验和资格鉴定-结束

		// 红包ID列表
		List<Integer> redPacketInfoIds = new ArrayList<Integer>();
		//需要发送短信的列表
		List<MsgBean> msgBeanList = new ArrayList<MsgBean>();
		// 用户信息
		BsUser bsUser = userMapper.selectByPrimaryKey(userId);
		Integer agentId = bsUser.getAgentId();

		// 满足条件的红包，是当前选中批次的红包
		BsAutoRedPacketRuleExample autoExample = new BsAutoRedPacketRuleExample();
		autoExample.createCriteria()
				.andTriggerTypeEqualTo(Constants.AUTO_RED_PACKET_TIGGER_TYPE_318_GAME_OLD_USER)
				.andStatusEqualTo(Constants.AUTO_RED_PACKET_STATUS_AVAILABLE)
				.andSerialNoEqualTo(serialNo)
				.andDistributeTimeStartLessThanOrEqualTo(now)
				.andDistributeTimeEndGreaterThanOrEqualTo(now);
		List<BsAutoRedPacketRule> motherDayActivityRuleList = autoRedMapper
				.selectByExample(autoExample);

		if(!CollectionUtils.isEmpty(motherDayActivityRuleList)) {
			for(BsAutoRedPacketRule rule : motherDayActivityRuleList) {
				if(agentId == null) {
					String adId = "," + Constants.NON_AGENT + ",";
					String agentIds = "," + rule.getAgentIds() + ",";
					if(Constants.ALL_AGENT.equals(rule.getAgentIds()) || Constants.NON_AGENT.equals(rule.getAgentIds()) || agentIds.contains(adId)) {
						BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
						checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
						List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
						if(!CollectionUtils.isEmpty(checkList)) {
							BsRedPacketCheck check = checkList.get(0);
							int packetId = sendPacketAndSendMsg(rule,check,userId,msgBeanList);
							if(packetId != 0) {
								redPacketInfoIds.add(packetId);
							}
						}
					}
				} else {
					if(!Constants.NON_AGENT.equals(rule.getAgentIds())) {
						if(Constants.ALL_AGENT.equals(rule.getAgentIds())) {
							//所有用户
							BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
							checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
							List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
							if(!CollectionUtils.isEmpty(checkList)) {
								BsRedPacketCheck check = checkList.get(0);
								int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
								if(packetId != 0) {
									redPacketInfoIds.add(packetId);
								}
							}
						}else {
							String adId = "," + agentId + ",";
							String agentIds = "," + rule.getAgentIds() + ",";
							if(agentIds.contains(adId)) {
								//符合渠道
								BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
								checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);
								List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
								if(!CollectionUtils.isEmpty(checkList)) {
									BsRedPacketCheck check = checkList.get(0);
									int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
									if(packetId != 0) {
										redPacketInfoIds.add(packetId);
									}
								}
							}
						}
					}
				}
			}
		}
		if (CollectionUtils.isEmpty(redPacketInfoIds)) {
			throw new PTMessageException(PTMessageEnum.RED_PACKET_OVER);
		}
		//处理发消息逻辑
		sendMessages(msgBeanList);
	}

	@Override
	public boolean sendMallRedPacketByName(Integer userId, String serialName, Date orderSuccTime) {
		// 数据校验和资格鉴定-开始
		if (null == userId) {
			return false;
		}

		// 数据校验和资格鉴定-结束
		// 红包ID列表
		List<Integer> redPacketInfoIds = new ArrayList<Integer>();
		//需要发送短信的列表
		List<MsgBean> msgBeanList = new ArrayList<MsgBean>();
		// 用户信息
		BsUser bsUser = userMapper.selectByPrimaryKey(userId);
		Integer agentId = bsUser.getAgentId();

		// 满足条件的红包
		BsAutoRedPacketRuleExample autoExample = new BsAutoRedPacketRuleExample();
		autoExample.createCriteria()
				.andTriggerTypeEqualTo(Constants.AUTO_RED_PACKET_TIGGER_TYPE_EXCHANGE_4MALL)
				.andStatusEqualTo(Constants.AUTO_RED_PACKET_STATUS_AVAILABLE)
				.andDistributeTimeStartLessThanOrEqualTo(orderSuccTime)
				.andDistributeTimeEndGreaterThanOrEqualTo(orderSuccTime);
		List<BsAutoRedPacketRule> exchange4MallRuleList = autoRedMapper
				.selectByExample(autoExample);

		if(!CollectionUtils.isEmpty(exchange4MallRuleList)) {
			for(BsAutoRedPacketRule rule : exchange4MallRuleList) {
				if(agentId == null) {
					String adId = "," + Constants.NON_AGENT + ",";
					String agentIds = "," + rule.getAgentIds() + ",";
					if(Constants.ALL_AGENT.equals(rule.getAgentIds()) || Constants.NON_AGENT.equals(rule.getAgentIds()) || agentIds.contains(adId)) {
						BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
						checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo())
								.andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS)
								.andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO)
								.andSerialNameEqualTo(serialName.trim());
						List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
						if(!CollectionUtils.isEmpty(checkList)) {
							BsRedPacketCheck check = checkList.get(0);
							int packetId = sendPacketAndSendMsg(rule,check,userId,msgBeanList);
							if(packetId != 0) {
								redPacketInfoIds.add(packetId);
							}
						}
					}
				} else {
					if(!Constants.NON_AGENT.equals(rule.getAgentIds())) {
						if(Constants.ALL_AGENT.equals(rule.getAgentIds())) {
							//所有用户
							BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
							checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo())
									.andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS)
									.andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO)
									.andSerialNameEqualTo(serialName.trim());
							List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
							if(!CollectionUtils.isEmpty(checkList)) {
								BsRedPacketCheck check = checkList.get(0);
								int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
								if(packetId != 0) {
									redPacketInfoIds.add(packetId);
								}
							}
						}else {
							String adId = "," + agentId + ",";
							String agentIds = "," + rule.getAgentIds() + ",";
							if(agentIds.contains(adId)) {
								//符合渠道
								BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
								checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo())
										.andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS)
										.andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO)
										.andSerialNameEqualTo(serialName.trim());
								List<BsRedPacketCheck> checkList = redCheckMapper.selectByExample(checkExample);
								if(!CollectionUtils.isEmpty(checkList)) {
									BsRedPacketCheck check = checkList.get(0);
									int packetId = sendPacketAndSendMsg(rule,check,userId, msgBeanList);
									if(packetId != 0) {
										redPacketInfoIds.add(packetId);
									}
								}
							}
						}
					}
				}
			}
		}
		if (CollectionUtils.isEmpty(redPacketInfoIds)) {
			return false;
		}
		try {
			//处理发消息逻辑
			sendMessages(msgBeanList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	public void happyBirthday(String birthday) {
		try {
			jsClientDaoSupport.lock(RedisLockEnum.REDPACKET_HAPPY_BIRTHDAY.getKey());
			// 1. 查询审核通过并且发放中且当前日期是在触发时间内的红包规则记录
			// 2. 判断1中列表是否为空且列表首条数据的left_count是否小于0，是则不继续操作
			// 3. 若2中列表不为空，则查询未发放红包且当日为生日的用户
			// 4. 用户为空，则不继续操作，若不为空，则判断2中列表首条数据的剩余红包数量，若剩余红包数据< 应发红包的用户数量，则发送告警短信
			// 5. 若剩余红包数量>=应发红包的用户数量，则循环用户列表，再循环红包列表对用户进行红包发放操作，插入red_packet_info。红包通知的金额为红包列表中的金额总和
            // 6. 进行短信通知、微信通知、APP通知
			
			// 1. 查询审核通过并且发放中且当前日期是在触发时间内的红包规则记录
			List<RedPacketInfoGrantVO> checkList = autoRedMapper.selectPassRedPackerRuleInfo(Constants.AUTO_RED_PACKET_TIGGER_TYPE_BIRTHDAY_BENEFIT);
            if (!CollectionUtils.isEmpty(checkList)) {
            	logger.info("生日福利红包发放，列表大小：{},信息：{}", checkList.size(), JSON.toJSONString(checkList));
            	
            	// 2. 判断1中列表是否为空且列表首条数据的left_count是否小于0，是则不继续操作
            	if (checkList.get(0).getLeftCount() <= 0) {
            		logger.info("生日福利红包发放首条数据剩余可发数量：{}", checkList.get(0).getLeftCount());
            		return;
            	}
            	List<BsUser> userList = new ArrayList<>();
            	
            	// 3. 若2中列表不为空，则查询未发放红包且当日为生日的用户
            	userList = userMapper.findBirthdayBenefitUserList(birthday, checkList.get(0).getSerialNo());
				
            	if (CollectionUtils.isEmpty(userList)) {
            		logger.info("生日福利红包发放用户数为0");
            		return;
            	}
            	
            	// 4. 用户为空，则不继续操作，若不为空，则判断2中列表首条数据的剩余红包数量，若剩余红包数据< 应发红包的用户数量，则发送告警短信
            	if (checkList.get(0).getLeftCount() < userList.size()) {
            		//发送告警短信,通知开发、运营人员
                    specialJnlService.warnAppoint4Fail(null, "今日生日红包数量不足，未发放生日红包，请管理台配置后重新执行生日红包发放定时，今日发放用户数："+userList.size(), null, "生日福利红包发放", false, 
                    		Constants.EMERGENCY_MOBILE, Constants.PRODUCT_OPERATOR_MOBILE);
                    return;
            	} else {

            		// 5. 若剩余红包数量>=应发红包的用户数量，则循环用户列表，再循环红包列表对用户进行红包发放操作，插入red_packet_info。红包通知的金额为红包列表中的金额总和
            		for (BsUser bsUser : userList) {
            			//需要发送短信的列表
                		List<MsgBean> msgBeanList = new ArrayList<MsgBean>();
            			for (RedPacketInfoGrantVO check : checkList) {
                            boolean isSuccess = happyBirthdayRedPacketGrant(check, bsUser);
                            if(isSuccess) {
                            	//加入需要发短信的列表
                    			MsgBean msgBean = new MsgBean();
                    			msgBean.setUserId(bsUser.getId());
                    			BsRedPacketCheck redPacketCheck = new BsRedPacketCheck();
                    			redPacketCheck.setNotifyChannel(check.getNotifyChannel());
                    			redPacketCheck.setAmount(check.getAmount());
                    			msgBean.setCheck(redPacketCheck);
                    			msgBeanList.add(msgBean);
                            }
                        }
            			//处理发消息逻辑
            			try {
            				sendMessages(msgBeanList);			
            			} catch (Exception e) {
            				logger.error("生日福利红包发放消息异常：{}", e);
            			}
					}
            	}
            	
            	// 红包发完之后，查询明日可发的红包数量，若剩余红包数量不足（小于配置值），发送告警给运营
            	BsSysConfig birthdayConfig = bsSysConfigService.findKey(Constants.HAPPY_BIRTHDAY_REDPACKET_REMIND);
                if(birthdayConfig != null) {
                	Integer birthdayRemindValue = Integer.parseInt(birthdayConfig.getConfValue());
                	Integer birthdayLeftCount = autoRedMapper.selectRedPackerRuleLeftCount(Constants.AUTO_RED_PACKET_TIGGER_TYPE_BIRTHDAY_BENEFIT,com.pinting.core.util.DateUtil.addDays(new Date(), 1));
                	if (birthdayLeftCount != null && birthdayLeftCount.intValue() < birthdayRemindValue.intValue()) {
                		//发送告警短信,通知运营人员
                		specialJnlService.warnAppoint4Fail(null, "生日红包剩余数量不足，请管理员及时配置，剩余可发数量："+birthdayLeftCount, null, "生日福利红包发放", false, 
                				Constants.PRODUCT_OPERATOR_MOBILE);
                	}
                }
                
            } else {
            	logger.info("生日福利红包发放，规则列表为空");
            	//发送告警短信,通知运营人员
        		specialJnlService.warnAppoint4Fail(null, "生日福利红包发放，发放规则列表为空，请管理员及时配置", null, "生日福利红包发放", false, 
        				Constants.PRODUCT_OPERATOR_MOBILE);
            }
		} catch (Exception e) {
			logger.error("生日福利红包发放异常：{}", e);
		} finally {
			jsClientDaoSupport.unlock(RedisLockEnum.REDPACKET_HAPPY_BIRTHDAY.getKey());
		}
	}
	
	private boolean happyBirthdayRedPacketGrant(RedPacketInfoGrantVO check, BsUser user) {
		boolean isSuccess = false;
		int redInfoId = 0;
        try {
        	if (Constants.ALL_AGENT.equals(check.getAgentIds())) {
                logger.info("全部渠道发放生日红包->用户编号：" + user.getId());
                redInfoId = grantRedPacket(user, check);
            } else {
                String[] agentIdArray = check.getAgentIds().split(",");
                for (String agentId : agentIdArray) {
                    if (agentId.equals(String.valueOf(user.getAgentId()))) {
                        logger.info("发放生日红包指定渠道：" + String.valueOf(user.getAgentId()) + "，用户编号：" + user.getId());
                        // 触发
                        redInfoId = grantRedPacket(user, check);
                        break;
                    }
                    // 普通用户与发放生日红包 普通用户0，标识非渠道用户，user.getAgentId() is null
                    if (agentId.equals(Constants.NON_AGENT) && user.getAgentId() == null) {
                        logger.info("发放生日红包指定渠道：" + String.valueOf(user.getAgentId()) + "，用户编号：" + user.getId());
                        // 触发
                        redInfoId = grantRedPacket(user, check);
                        break;
                    }
                }
    	    }
            if (redInfoId != 0) {
            	isSuccess = true;
            }
		} catch (Exception e) {
			logger.info("生日福利红包发放入红包信息表异常：{}", e);
			isSuccess = false;
		}
        return isSuccess;
	}

	private Integer grantRedPacket(BsUser user, RedPacketInfoGrantVO check) {
    	Date now = new Date();
		BsRedPacketInfo redInfo = new BsRedPacketInfo();
		redInfo.setApplyNo(check.getApplyNo());
		redInfo.setSerialNo(check.getSerialNo());
		redInfo.setUserId(user.getId());
		redInfo.setStatus(Constants.RED_PACKET_STATUS_INIT);
		redInfo.setAmount(check.getAmount());
		//有效期类型为固定时段
		if(check.getValidTermType().equals(Constants.AUTO_RED_PACKET_VALID_TERM_TYPE_FIXED)) {
			redInfo.setUseTimeStart(check.getUseTimeStart());
			redInfo.setUseTimeEnd(check.getUseTimeEnd());
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 000);
			redInfo.setUseTimeStart(calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, check.getAvailableDays()-1);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 000);
			redInfo.setUseTimeEnd(calendar.getTime());
		}
		redInfo.setCreateTime(now);
		redInfo.setUpdateTime(now);
		redInfoMapper.insertSelective(redInfo);
		return redInfo.getId();
	}
	 
}