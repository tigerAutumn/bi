package com.pinting.business.service.site.impl;

import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.ReqMsg_Ticket_TicketList;
import com.pinting.business.hessian.site.message.ResMsg_Ticket_TicketList;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.InterestTicketInfoVO;
import com.pinting.business.model.vo.TicketGrantPlanCheckVO;
import com.pinting.business.model.vo.TicketInterestNotifyVO;
import com.pinting.business.service.manage.BsTicketInterestService;
import com.pinting.business.service.site.AppNoticeService;
import com.pinting.business.service.site.TicketInterestService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.in.util.MethodRole;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by cyb on 2018/4/2.
 */
@Service
public class TicketInterestServiceImpl implements TicketInterestService {

    private Logger logger = LoggerFactory.getLogger(TicketInterestServiceImpl.class);

    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private BsInterestTicketInfoMapper bsInterestTicketInfoMapper;
    @Autowired
    private BsInterestTicketGrantAttributeMapper bsInterestTicketGrantAttributeMapper;
    @Autowired
    private BsTicketGrantPlanCheckMapper bsTicketGrantPlanCheckMapper;
    @Autowired
    private BsAutoInterestTicketRuleMapper autoTicketMapper;
    @Autowired
    private BsTicketInterestService bsTicketInterestService;    @Autowired
    private SMSServiceClient smsServiceClient;
    @Autowired
    private AppNoticeService appNoticeService;

    @Override
    public void ticketInterestList(ReqMsg_Ticket_TicketList req, ResMsg_Ticket_TicketList res) {
        List<HashMap<String, Object>> dataList = new ArrayList<>();
        if (Constants.TICKET_INTEREST_BIZ_TYPE_BUY.equals(req.getBizType())) {
            // 购买入口
            res.setType(req.getType());
            res.setUserId(req.getUserId());
            List<InterestTicketInfoVO> list = bsInterestTicketInfoMapper.selectBuyTicketINITList(req.getUserId(), req.getAmount(), req.getProductId());
            if (!CollectionUtils.isEmpty(list)) {
                this.sort(list, req.getAmount());
                res.setCount(list.size());
                for (InterestTicketInfoVO ticket : list) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", ticket.getId());
                    map.put("userId", ticket.getUserId());
                    map.put("serialName", ticket.getSerialName());
                    map.put("full", ticket.getFull());
                    map.put("termLimit", ticket.getTermLimit());
                    map.put("productLimit", ticket.getProductLimit());
                    map.put("rate", ticket.getRate());
                    map.put("useTimeStart", DateUtil.formatDateTime(ticket.getUseTimeStart(), "yyyy-MM-dd HH:mm:ss"));
                    map.put("useTimeEnd", DateUtil.formatDateTime(ticket.getUseTimeEnd(), "yyyy-MM-dd HH:mm:ss"));
                    map.put("status", ticket.getStatus());
                    map.put("term", ticket.getTerm());
                    map.put("interest", ticket.getInterest());
                    map.put("type", req.getType());
                    map.put("isSupport", ticket.getIsSupportIncrInterest());
                    dataList.add(map);
                }
            }
            res.setDataList(dataList);
        } else if (Constants.TICKET_INTEREST_BIZ_TYPE_TICKET.equals(req.getBizType())) {
            // 普通优惠券列表入口
            BsInterestTicketInfoExample bsInterestTicketInfoExample = new BsInterestTicketInfoExample();
            Date now = new Date();
            Date days = new Date();
            days = DateUtil.parse(com.pinting.business.util.DateUtil.format(now, "yyyy-MM-dd") + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            Date day = com.pinting.core.util.DateUtil.addDays(days, -89);
            if (StringUtil.isNotBlank(req.getStatus())) {
                if (Constants.RED_PACKET_STATUS_INIT.equals(req.getStatus())) {
                    bsInterestTicketInfoExample.createCriteria().andUserIdEqualTo(req.getUserId()).andStatusEqualTo(req.getStatus()).andUseTimeEndGreaterThanOrEqualTo(new Date()).andUseTimeStartLessThanOrEqualTo(new Date());
                } else if (Constants.RED_PACKET_STATUS_OVER.equals(req.getStatus())) {
                    bsInterestTicketInfoExample.createCriteria().andUserIdEqualTo(req.getUserId()).andStatusEqualTo(Constants.RED_PACKET_STATUS_INIT).andUseTimeEndLessThan(new Date()).andUseTimeEndGreaterThanOrEqualTo(day);
                } else {
                    bsInterestTicketInfoExample.createCriteria().andUserIdEqualTo(req.getUserId()).andStatusEqualTo(req.getStatus()).andUseTimeGreaterThanOrEqualTo(day);
                }
            }
            Integer count = bsInterestTicketInfoMapper.countByExample(bsInterestTicketInfoExample);
            res.setCount(count);
            int totalPages = (int) Math.ceil((double) count / req.getNumPerPage());
            if (count > 0 && req.getPageNum() <= totalPages) {
                int start = (req.getPageNum() <= 1) ? 0 : ((req.getPageNum() - 1) * req.getNumPerPage()); // mysql的分页
                List<InterestTicketInfoVO> vos = bsInterestTicketInfoMapper.selectInterestTicketListNew(req.getUserId(), req.getStatus(), start, req.getNumPerPage());
                res.setDataList(BeanUtils.classToArrayList(vos) == null ? new ArrayList<HashMap<String, Object>>() : BeanUtils.classToArrayList(vos));
            } else {
                res.setDataList(new ArrayList<HashMap<String, Object>>());
            }
        }
    }

    private void sort(List<InterestTicketInfoVO> list, final Double amount) {
        // 如果存在金额，则amount 超过满的在前面，没有的在后面
        if (null != amount && amount > 0) {
            Collections.sort(list, new Comparator<InterestTicketInfoVO>() {
                @Override
                public int compare(InterestTicketInfoVO o1, InterestTicketInfoVO o2) {
                    if (Double.compare(amount, Double.valueOf(o1.getFull())) >= 0 && Double.compare(amount, Double.valueOf(o2.getFull())) < 0) {
                        return -1;
                    } else if (Double.compare(amount, Double.valueOf(o1.getFull())) < 0 && Double.compare(amount, Double.valueOf(o2.getFull())) >= 0) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
        }
    }


    @Override
    public boolean happyBirthday(String birthday, Integer numPerPage) {
        // 1. 审核已通过（check_status = PASS，grant_status = PROCESS）的 bs_ticket_grant_plan_check 记录
        // 2. 触发条件是生日触发（bs_auto_interest_ticket_rule.trigger_type = HAPPY_BIRTHDAY）且当前时间触发时间之内的记录
        // 3. 查询所有未发放加息券且当日生日的用户，用户ID顺序排列
        // 4. 查询 bs_interest_ticket_grant_attribute 表中grant_num加上当前发放的记录数是否<=grant_total，小于，则继续发放，并更新grant_num；等于发完不再对后续用户进行发放，并更新grant_status = FINISH
        // 5. 判断 bs_interest_ticket_grant_attribute 的 valid_term_type
        // 5.1 valid_term_type = FIXED 固定时间段生效：获取 use_time_start 和 use_time_end 作为有效时间
        // 5.2 valid_term_type = AFTER_RECEIVE 发放后有效天数：获取 available_days 计算有效时间
        // 6. 为所有未发放加息券且当日生日的用户插入 BsInterestTicketInfo 数据
        boolean isFinished = false;
        try {
            jsClientDaoSupport.lock(RedisLockEnum.TICKET_HAPPY_BIRTHDAY.getKey());
            logger.info("用户生日触发自动加息券开始");
            List<TicketGrantPlanCheckVO> checkList = bsTicketGrantPlanCheckMapper.selectPassAndProcessCheck(birthday, null);
            for (TicketGrantPlanCheckVO check : checkList) {
                happyBirthdayGrant(check, birthday, 0, numPerPage);
            }

            logger.info("用户生日触发自动加息券结束");
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.TICKET_HAPPY_BIRTHDAY.getKey());
        }
        return isFinished;
    }

    private boolean happyBirthdayGrant(TicketGrantPlanCheckVO check, String birthday, Integer unqualifiedCount, Integer numPerPage) {
        boolean finish = false;

        Integer start = unqualifiedCount; // 过滤不符合要求的用户数量
        List<BsUser> userList = bsUserMapper.selectHaveNoTicketUserList(check.getSerialNo(), birthday, start, numPerPage);

        if (CollectionUtils.isEmpty(userList)) {
            logger.info("触发自动加息券发放计划用户数：0");
            finish = true;
        } else {
            logger.info("触发自动加息券发放计划用户数：" + userList.size());
            if (numPerPage.compareTo(userList.size()) > 0) {
                finish = true;
            }
            for (BsUser user : userList) {
                if (check.getGrantTotal().compareTo(check.getGrantNum()) > 0) {
                    // 发放总数 > 已发放数量
                    if (Constants.ALL_AGENT.equals(check.getAgentIds())) {
                        logger.info("全部渠道发放加息券->用户编号：" + user.getId());
                        unqualifiedCount += grantInterestTicket(birthday, user, check) ? 0 : 1;
                    } else {
                        String[] agentIdArray = check.getAgentIds().split(",");
                        for (String agentId : agentIdArray) {
                            if (agentId.equals(String.valueOf(user.getAgentId()))) {
                                logger.info("指定->渠道：" + String.valueOf(user.getAgentId()) + "，用户编号：" + user.getId());
                                // 触发
                                unqualifiedCount += grantInterestTicket(birthday, user, check) ? 0 : 1;
                                break;
                            }
                            // 普通用户与发放加息券  普通用户0，标识非渠道用户，user.getAgentId() is null
                            if (agentId.equals(Constants.NON_AGENT) && user.getAgentId() == null) {
                                logger.info("指定->渠道：" + String.valueOf(user.getAgentId()) + "，用户编号：" + user.getId());
                                // 触发
                                unqualifiedCount += grantInterestTicket(birthday, user, check) ? 0 : 1;
                                break;
                            }
                            unqualifiedCount += 1; // 没有发放的，不符合要求的用户数量
                        }
                    }
                } else {
                    logger.info("自动加息券发放总数已达上限");
                    finish = true;
                    break;
                }
            }
        }
        if (!finish) {
            happyBirthdayGrant(check, birthday, unqualifiedCount, numPerPage);
        }
        return finish;
    }

    private boolean grantInterestTicket(final String birthday, final BsUser user, final TicketGrantPlanCheckVO checkVO) {
        try {
            Integer infoId = transactionTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus status) {

                    List<TicketGrantPlanCheckVO> checkList = bsTicketGrantPlanCheckMapper.selectPassAndProcessCheck(birthday, checkVO.getCheckId());
                    TicketGrantPlanCheckVO check = checkList.get(0);
                    if (check.getGrantTotal().compareTo(check.getGrantNum()) <= 0) {
                        logger.info("自动加息券发放总数已达上限");
                        checkVO.setGrantNum(check.getGrantNum());
                        return null;
                    }

                    // 插入加息券信息表
                    BsInterestTicketInfo info = new BsInterestTicketInfo();
                    info.setStatus(Constants.TICKET_INTEREST_STATUS_INIT);
                    info.setSerialNo(check.getSerialNo());
                    info.setUserId(user.getId());
                    info.setTicketApr(check.getTicketApr());
                    info.setMsgStatus(Constants.ICKET_MSG_STATUS_NOT);
                    if (check.getValidTermType().equals(Constants.AUTO_TICKET_VALID_TERM_TYPE_FIXED)) {
                        // 固定时间段
                        if (check.getUseTimeStart().after(new Date())) {
                            info.setUseTimeStart(check.getUseTimeStart());
                        } else {
                            info.setUseTimeStart(new Date());
                        }
                        info.setUseTimeEnd(check.getUseTimeEnd());
                    } else if (check.getValidTermType().equals(Constants.AUTO_TICKET_VALID_TERM_TYPE_AFTER_RECEIVE)) {
                        // 发放后有效天数
                        info.setUseTimeStart(new Date());
                        Calendar calendar = Calendar.getInstance();
            			calendar.add(Calendar.DAY_OF_MONTH, check.getAvailableDays()-1);
            			calendar.set(Calendar.HOUR_OF_DAY, 23);
            			calendar.set(Calendar.MINUTE, 59);
            			calendar.set(Calendar.SECOND, 59);
            			calendar.set(Calendar.MILLISECOND, 000);
            			info.setUseTimeEnd(calendar.getTime());
                    }
                    info.setCreateTime(new Date());
                    info.setUpdateTime(new Date());
                    bsInterestTicketInfoMapper.insertSelective(info);

                    // 发放数量已达上限，更新加息券审核表，发放状态：发放结束
                    // 触发结束时间为最后一天，更新加息券审核表，发放状态：发放结束
                    if (check.getGrantTotal().equals(check.getGrantNum() + 1)) {
                        BsTicketGrantPlanCheck planCheck = new BsTicketGrantPlanCheck();
                        planCheck.setId(check.getCheckId());
                        planCheck.setGrantStatus(Constants.TICKET_GRANT_STATUS_FINISH);
                        planCheck.setUpdateTime(new Date());
                        bsTicketGrantPlanCheckMapper.updateByPrimaryKeySelective(planCheck);
                    }

                    // 更新加息券审核相关属性
                    BsInterestTicketGrantAttribute attr = new BsInterestTicketGrantAttribute();
                    attr.setId(check.getAttrId());
                    attr.setGrantNum(check.getGrantNum() + 1);
                    attr.setUpdateTime(new Date());
                    bsInterestTicketGrantAttributeMapper.updateByPrimaryKeySelective(attr);

                    checkVO.setGrantNum(check.getGrantNum() + 1); // 批次发放加息券成功，已发放数量+1
                    return info.getId();
                }
            });

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("【自动发放加息券】生日触发，发放失败，用户ID：{}， 加息券审核ID：{}", user.getId(), checkVO.getCheckId());
            return false;
        }
    }

    /**
     * @see com.pinting.business.service.site.TicketInterestService#getInterestTicketNum(java.lang.Integer, java.lang.String)
     */
    @Override
    @MethodRole("APP")
    public Integer getInterestTicketNum(Integer userId, String status) {
        BsInterestTicketInfoExample bsInterestTicketInfoExample = new BsInterestTicketInfoExample();
        if (StringUtil.isNotBlank(status)) {
            if (Constants.RED_PACKET_STATUS_INIT.equals(status)) {
                bsInterestTicketInfoExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(status).andUseTimeEndGreaterThanOrEqualTo(new Date()).andUseTimeStartLessThanOrEqualTo(new Date());
            } else if (Constants.RED_PACKET_STATUS_OVER.equals(status)) {
                bsInterestTicketInfoExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(Constants.RED_PACKET_STATUS_INIT).andUseTimeEndLessThan(new Date());
            } else {
                bsInterestTicketInfoExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(status);
            }
            Integer count = bsInterestTicketInfoMapper.countByExample(bsInterestTicketInfoExample);
            return count;
        } else {
            bsInterestTicketInfoExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(Constants.RED_PACKET_STATUS_INIT);
            Integer count = bsInterestTicketInfoMapper.countByExample(bsInterestTicketInfoExample);
            return count;
        }
    }

	@Override
	public boolean sendMallTicketByName(Integer userId, String ticketName, Date orderSuccTime) {
		boolean flag = false;
		List<TicketGrantPlanCheckVO> checkList = bsTicketGrantPlanCheckMapper.selectMallTickets(orderSuccTime, 
				Constants.TICKET_TRIGGER_TYPE_EXCHANGE_4MALL, ticketName,  null);
		if(CollectionUtils.isEmpty(checkList)){
			return flag;
		}else{
			//查询用户信息
			BsUser user = bsUserMapper.selectByPrimaryKey(userId);
			
			for (TicketGrantPlanCheckVO ticketGrantPlanCheckVO : checkList) {
				if (Constants.ALL_AGENT.equals(ticketGrantPlanCheckVO.getAgentIds())) {
					logger.info("发放加息券，所有渠道：" + String.valueOf(user.getAgentId()) + "，用户编号：" + user.getId());
                    // 触发
					flag = sendMallInterestTicket(orderSuccTime, user, ticketName, ticketGrantPlanCheckVO, Constants.TICKET_TRIGGER_TYPE_EXCHANGE_4MALL);
	            } else {
	            	 logger.info("发放加息券，指定渠道，券的适用渠道："+ticketGrantPlanCheckVO.getAgentIds()
	            			 +";用户编号：" + user.getId()+"用户渠道：" + String.valueOf(user.getAgentId()));
	                String[] agentIdArray = ticketGrantPlanCheckVO.getAgentIds().split(",");
	                for (String agentId : agentIdArray) {
	                    if (agentId.equals(String.valueOf(user.getAgentId()))) {
	                        // 触发
	                    	flag = sendMallInterestTicket(orderSuccTime, user, ticketName, ticketGrantPlanCheckVO, Constants.TICKET_TRIGGER_TYPE_EXCHANGE_4MALL) ;
	                        break;
	                    }
	                    // 普通用户与发放加息券  普通用户0，标识非渠道用户，user.getAgentId() is null
	                    if (agentId.equals(Constants.NON_AGENT) && user.getAgentId() == null) {
	                        // 触发
	                    	flag = sendMallInterestTicket(orderSuccTime, user, ticketName, ticketGrantPlanCheckVO, Constants.TICKET_TRIGGER_TYPE_EXCHANGE_4MALL);
	                        break;
	                    }
	                }
	            }
			}
			
		}
		return flag;
	}

	private boolean sendMallInterestTicket(final Date orderSuccTime, final BsUser user, final String ticketName,
			final TicketGrantPlanCheckVO checkVO, String ticketTriggerType) {
		boolean flag = false;
		try {
            Integer infoId = transactionTemplate.execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus status) {

                    List<TicketGrantPlanCheckVO> checkList = bsTicketGrantPlanCheckMapper.selectMallTickets(orderSuccTime, 
            				Constants.TICKET_TRIGGER_TYPE_EXCHANGE_4MALL, ticketName,  checkVO.getCheckId());
                    TicketGrantPlanCheckVO check = checkList.get(0);
                    if (check.getGrantTotal().compareTo(check.getGrantNum()) <= 0) {
                        logger.info("自动加息券发放总数已达上限");
                        checkVO.setGrantNum(check.getGrantNum());
                        return null;
                    }

                    // 插入加息券信息表
                    BsInterestTicketInfo info = new BsInterestTicketInfo();
                    info.setStatus(Constants.TICKET_INTEREST_STATUS_INIT);
                    info.setSerialNo(check.getSerialNo());
                    info.setUserId(user.getId());
                    info.setTicketApr(check.getTicketApr());
                    info.setMsgStatus(Constants.ICKET_MSG_STATUS_NOT);
                    if (check.getValidTermType().equals(Constants.AUTO_TICKET_VALID_TERM_TYPE_FIXED)) {
                        // 固定时间段
                        if (check.getUseTimeStart().after(new Date())) {
                            info.setUseTimeStart(check.getUseTimeStart());
                        } else {
                            info.setUseTimeStart(new Date());
                        }
                        info.setUseTimeEnd(check.getUseTimeEnd());
                    } else if (check.getValidTermType().equals(Constants.AUTO_TICKET_VALID_TERM_TYPE_AFTER_RECEIVE)) {
                        // 发放后有效天数
                    	info.setUseTimeStart(new Date());
                        Calendar calendar = Calendar.getInstance();
            			calendar.add(Calendar.DAY_OF_MONTH, check.getAvailableDays()-1);
            			calendar.set(Calendar.HOUR_OF_DAY, 23);
            			calendar.set(Calendar.MINUTE, 59);
            			calendar.set(Calendar.SECOND, 59);
            			calendar.set(Calendar.MILLISECOND, 000);
            			info.setUseTimeEnd(calendar.getTime());
                    }
                    info.setCreateTime(new Date());
                    info.setUpdateTime(new Date());
                    bsInterestTicketInfoMapper.insertSelective(info);

                    // 发放数量已达上限，更新加息券审核表，发放状态：发放结束
                    // 触发结束时间为最后一天，更新加息券审核表，发放状态：发放结束
                    if (check.getGrantTotal().equals(check.getGrantNum() + 1)) {
                        BsTicketGrantPlanCheck planCheck = new BsTicketGrantPlanCheck();
                        planCheck.setId(check.getCheckId());
                        planCheck.setGrantStatus(Constants.TICKET_GRANT_STATUS_FINISH);
                        planCheck.setUpdateTime(new Date());
                        bsTicketGrantPlanCheckMapper.updateByPrimaryKeySelective(planCheck);
                    }

                    // 更新加息券审核相关属性
                    BsInterestTicketGrantAttribute attr = new BsInterestTicketGrantAttribute();
                    attr.setId(check.getAttrId());
                    attr.setGrantNum(check.getGrantNum() + 1);
                    attr.setUpdateTime(new Date());
                    bsInterestTicketGrantAttributeMapper.updateByPrimaryKeySelective(attr);

                    checkVO.setGrantNum(check.getGrantNum() + 1); // 批次发放加息券成功，已发放数量+1
                    return info.getId();
                }
            });
            if(infoId != null){
            	try {
            		//发送通知
                    sendTicketNotify(infoId);
				} catch (Exception e) {
					e.printStackTrace();
				}
                flag = true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("【发放加息券】积分商城触发，发放失败，用户ID：{}， 加息券审核ID：{}", user.getId(), checkVO.getCheckId());
        }
		return flag;
		
	}

	private void sendTicketNotify(Integer infoId) {
		 TicketInterestNotifyVO notifyVO = bsInterestTicketInfoMapper.selectInterestTicketNotifyByInfoId(infoId);
		if (StringUtil.isNotBlank(notifyVO.getNotifyChannel())) {
            String[] notifyChannels = notifyVO.getNotifyChannel().split(",");
            List<Integer> userIdList = new ArrayList<>();
            userIdList.add(notifyVO.getUserId());
            logger.info("加息券提现通知用户编号：" + notifyVO.getUserId() + "加息券名称:" + notifyVO.getTicketName() + "通知渠道：" + notifyVO.getNotifyChannel());
            for (String notifyChannel : notifyChannels) {
                try {
                    if (Constants.TICKET_NOTIFY_CHANNEL_WECHAT.equals(notifyChannel)) {
                        continue;
//                        sendWechatService.ticketInterestGrant(notifyVO.getUserId(), notifyVO.getTicketName(), notifyVO.getTicketApr());
                    } else if (Constants.TICKET_NOTIFY_CHANNEL_SMS.equals(notifyChannel)) {
                        smsServiceClient.sendTemplateMessage(notifyVO.getMobile(), TemplateKey.MESSAGE_TICKET_GRANT, notifyVO.getTicketApr().toString() + "%", notifyVO.getTicketName());
                    } else if (Constants.TICKET_NOTIFY_CHANNEL_APP.equals(notifyChannel)) {
                    	appNoticeService.sendTicketMessage("领取加息券通知",  "恭喜你获得" + notifyVO.getTicketApr() + "%" + notifyVO.getTicketName() + "。", userIdList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // 更新加息券通知状态为 FINISHED 已发送
        BsInterestTicketInfo record = new BsInterestTicketInfo();
        record.setId(notifyVO.getId());
        record.setMsgStatus(Constants.ICKET_MSG_STATUS_FINISHED);
        record.setUpdateTime(new Date());
        bsInterestTicketInfoMapper.updateByPrimaryKeySelective(record);
		
	}

	@Override
	public void weChatAutoTicketSendByName(Integer userId, String serialName) {
		final Date now = new Date();
		// 数据校验和资格鉴定-开始
		if (null == userId) {
			throw new PTMessageException(PTMessageEnum.CHECK_USER_LOGIN_OUT_ERROR);
		}

		// 数据校验和资格鉴定-结束
		// 加息券ID列表
		List<Integer> ticketInfoIds = new ArrayList<Integer>();
		//需要发送短信的列表
		List<MsgBean> msgBeanList = new ArrayList<MsgBean>();
		// 用户信息
		BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
		Integer agentId = bsUser.getAgentId();

		// 满足条件的加息券
		BsAutoInterestTicketRuleExample autoExample = new BsAutoInterestTicketRuleExample();
		autoExample.createCriteria()
				.andTriggerTypeEqualTo(Constants.TICKET_TRIGGER_TYPE_WECHAT_MINI_PROGRAM)
				.andTriggerTimeStartLessThanOrEqualTo(now)
				.andTriggerTimeEndGreaterThanOrEqualTo(now);
		List<BsAutoInterestTicketRule> weChatMiniProgramRuleList = autoTicketMapper
				.selectByExample(autoExample);

		if(!CollectionUtils.isEmpty(weChatMiniProgramRuleList)) {
			for(BsAutoInterestTicketRule rule : weChatMiniProgramRuleList) {
				if(agentId == null) {
					String adId = "," + Constants.NON_AGENT + ",";
					String agentIds = "," + rule.getAgentIds() + ",";
					if(Constants.ALL_AGENT.equals(rule.getAgentIds()) || Constants.NON_AGENT.equals(rule.getAgentIds()) || agentIds.contains(adId)) {
						BsTicketGrantPlanCheckExample checkExample = new BsTicketGrantPlanCheckExample();
						checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo())
								.andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS)
								.andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO)
								.andSerialNameLike("%" + serialName.substring(4) + "%");
						List<BsTicketGrantPlanCheck> checkList = bsTicketGrantPlanCheckMapper.selectByExample(checkExample);
						
						if(!CollectionUtils.isEmpty(checkList)) {
							BsInterestTicketGrantAttributeExample attributeExample = new BsInterestTicketGrantAttributeExample();
							attributeExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andTicketAprEqualTo(Double.valueOf(serialName.substring(0,3)));
							List<BsInterestTicketGrantAttribute> attributeList = bsInterestTicketGrantAttributeMapper.selectByExample(attributeExample);
							if (!CollectionUtils.isEmpty(attributeList)) {
								BsInterestTicketGrantAttribute attribute = attributeList.get(0);
								int ticketId = sendTicketAndSendMsg(rule,attribute,userId,msgBeanList);
								if(ticketId != 0) {
									ticketInfoIds.add(ticketId);
								}
							}
						}
					}
				} else {
					if(!Constants.NON_AGENT.equals(rule.getAgentIds())) {
						if(Constants.ALL_AGENT.equals(rule.getAgentIds())) {
							//所有用户
							BsTicketGrantPlanCheckExample checkExample = new BsTicketGrantPlanCheckExample();
							checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo())
									.andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS)
									.andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO)
									.andSerialNameLike("%" + serialName.substring(4) + "%");
							List<BsTicketGrantPlanCheck> checkList = bsTicketGrantPlanCheckMapper.selectByExample(checkExample);
							if(!CollectionUtils.isEmpty(checkList)) {
								BsInterestTicketGrantAttributeExample attributeExample = new BsInterestTicketGrantAttributeExample();
								attributeExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andTicketAprEqualTo(Double.valueOf(serialName.substring(0,3)));
								List<BsInterestTicketGrantAttribute> attributeList = bsInterestTicketGrantAttributeMapper.selectByExample(attributeExample);
								if (!CollectionUtils.isEmpty(attributeList)) {
									BsInterestTicketGrantAttribute attribute = attributeList.get(0);
									int ticketId = sendTicketAndSendMsg(rule,attribute,userId,msgBeanList);
									if(ticketId != 0) {
										ticketInfoIds.add(ticketId);
									}
								}
							}
						}else {
							String adId = "," + agentId + ",";
							String agentIds = "," + rule.getAgentIds() + ",";
							if(agentIds.contains(adId)) {
								//符合渠道
								BsTicketGrantPlanCheckExample checkExample = new BsTicketGrantPlanCheckExample();
								checkExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo())
										.andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS)
										.andDistributeTypeEqualTo(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO)
										.andSerialNameLike("%" + serialName.substring(4) + "%");
								List<BsTicketGrantPlanCheck> checkList = bsTicketGrantPlanCheckMapper.selectByExample(checkExample);
								if(!CollectionUtils.isEmpty(checkList)) {
									BsInterestTicketGrantAttributeExample attributeExample = new BsInterestTicketGrantAttributeExample();
									attributeExample.createCriteria().andSerialNoEqualTo(rule.getSerialNo()).andTicketAprEqualTo(Double.valueOf(serialName.substring(0,3)));
									List<BsInterestTicketGrantAttribute> attributeList = bsInterestTicketGrantAttributeMapper.selectByExample(attributeExample);
									if (!CollectionUtils.isEmpty(attributeList)) {
										BsInterestTicketGrantAttribute attribute = attributeList.get(0);
										int ticketId = sendTicketAndSendMsg(rule,attribute,userId,msgBeanList);
										if(ticketId != 0) {
											ticketInfoIds.add(ticketId);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if (CollectionUtils.isEmpty(ticketInfoIds)) {
			throw new PTMessageException(PTMessageEnum.INTEREST_TICKET_OVER);
		}
		//处理发消息逻辑
		sendMessages(msgBeanList);
	}
	
	private class MsgBean{
		private int userId;
		private BsInterestTicketGrantAttribute attribute;
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public BsInterestTicketGrantAttribute getAttribute() {
			return attribute;
		}
		public void setAttribute(BsInterestTicketGrantAttribute attribute) {
			this.attribute = attribute;
		}
		
	}
	
	//发送消息
	private void sendMessages(List<MsgBean> msgBeanList){
		//发信息提醒用户
		//发给谁
		if(msgBeanList.isEmpty()){
			return;
		}
		
		BsUser user = bsUserMapper.selectByPrimaryKey(msgBeanList.get(0).getUserId());
		if(user == null) {
			return;
		}
		
		List<String> mobiles = new ArrayList<String>();
		mobiles.add(user.getMobile());
		List<Integer> userIds = new ArrayList<Integer>();
		userIds.add(user.getId());
		
		String channels = "";
		//发什么内容
		double ticketApr = 0;
		for (MsgBean msgBean : msgBeanList) {
			channels += msgBean.getAttribute().getNotifyChannel() + ",";
			ticketApr = MoneyUtil.add(ticketApr, msgBean.getAttribute().getTicketApr()).doubleValue();
		}
		
		if(StringUtil.isNotEmpty(channels) && channels.contains(Constants.TICKET_NOTIFY_CHANNEL_SMS)){//只要包含SMS都要发
			//短信通知
			bsTicketInterestService.sendTicketMessage(Constants.TICKET_NOTIFY_TYPE_SMS, mobiles, null, "加息券", String.valueOf(ticketApr)+"%");
		}
		if(StringUtil.isNotEmpty(channels) && channels.contains(Constants.TICKET_NOTIFY_CHANNEL_WECHAT)){//只要包含WECHAT都要发
			//微信通知
			bsTicketInterestService.sendTicketMessage(Constants.TICKET_NOTIFY_TYPE_WECHAT, null, userIds, "加息券", String.valueOf(ticketApr)+"%");
		}
		if(StringUtil.isNotEmpty(channels) && channels.contains(Constants.TICKET_NOTIFY_CHANNEL_APP)){//只要包含APP都要发
			//APP通知
			bsTicketInterestService.sendTicketMessage(Constants.TICKET_NOTIFY_TYPE_APP, null, userIds, "加息券",  String.valueOf(ticketApr)+"%");
		}
	}
	
	/**
	 * 加息券发放和发送通知消息业务逻辑
	 * @param rule 规则实体类
	 * @param check 加息券发放批次审核类
	 * @param userId 用户编号
	 * @return Integer 红包编号
	 */
	private Integer sendTicketAndSendMsg(BsAutoInterestTicketRule rule,BsInterestTicketGrantAttribute attribute, Integer userId, List<MsgBean> msgBeanList) {
		//判断加息券是否已发完
		BsInterestTicketInfoExample infoExample = new BsInterestTicketInfoExample();
		infoExample.createCriteria().andSerialNoEqualTo(attribute.getSerialNo());
		int count = bsInterestTicketInfoMapper.countByExample(infoExample);
		if(attribute.getGrantTotal() > count) {
			Date now = new Date();
			BsInterestTicketInfo ticketInfo = new BsInterestTicketInfo();
			ticketInfo.setSerialNo(attribute.getSerialNo());
			ticketInfo.setUserId(userId);
			ticketInfo.setStatus(Constants.RED_PACKET_STATUS_INIT);
			ticketInfo.setTicketApr(attribute.getTicketApr());
			ticketInfo.setMsgStatus("FINISHED");
			//有效期类型为固定时段
			if(attribute.getValidTermType().equals(Constants.AUTO_RED_PACKET_VALID_TERM_TYPE_FIXED)) {
				ticketInfo.setUseTimeStart(attribute.getUseTimeStart());
				ticketInfo.setUseTimeEnd(attribute.getUseTimeEnd());
			}
			else {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(now);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 000);
				ticketInfo.setUseTimeStart(calendar.getTime());
				calendar.add(Calendar.DAY_OF_MONTH, attribute.getAvailableDays()-1);
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 000);
				ticketInfo.setUseTimeEnd(calendar.getTime());
			}
			ticketInfo.setCreateTime(now);
			ticketInfo.setUpdateTime(new Date());
			bsInterestTicketInfoMapper.insertSelective(ticketInfo);
			
			//加入需要发短信的列表
			MsgBean msgBean = new MsgBean();
			msgBean.setUserId(userId);
			msgBean.setAttribute(attribute);
			msgBeanList.add(msgBean);
			
			//返回发放加息券的Id
			return ticketInfo.getId() == null ? 0 : ticketInfo.getId();
		}
		return 0;
	}
}
