package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.BsInterestTicketGrantAttributeMapper;
import com.pinting.business.dao.BsTicketGrantPlanCheckMapper;
import com.pinting.business.dao.BsTicketPreDetailMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsInterestTicketGrantAttrVO;
import com.pinting.business.model.vo.BsTicketGrantPlanCheckVO;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.business.service.manage.TicketGrantplanCheckService;
import com.pinting.business.service.manage.TicketInterestGrantAttrService;
import com.pinting.business.util.ArrayUtils;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.List;

@Service
public class TicketGrantPlanCheckServiceImpl implements TicketGrantplanCheckService {

    private Logger log = LoggerFactory.getLogger(TicketGrantPlanCheckServiceImpl.class);

    @Autowired
    private BsTicketGrantPlanCheckMapper bsTicketGrantplanCheckMapper;
    @Autowired
    private TicketInterestGrantAttrService ticketInterestGrantAttrService;
    @Autowired
    private BsInterestTicketGrantAttributeMapper bsInterestTicketGrantAttributeMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private BsUserService bsUserService;
    @Autowired
    private BsTicketPreDetailMapper bsTicketPreDetailMapper;

    @Override
    public List<BsTicketGrantPlanCheckVO> getGrantManagerment(BsTicketGrantPlanCheckVO req) {
        return bsTicketGrantplanCheckMapper.selectTicketInterestGrantManagerment(req);
    }

    @Override
    public int getGrantManagermentCount(BsTicketGrantPlanCheckVO req) {
        return bsTicketGrantplanCheckMapper.getTicketInterestGrantManagermentCount(req);
    }

    @Override
    public BsInterestTicketGrantAttrVO getAutoTicketGrantDetail(BsInterestTicketGrantAttrVO req) {
        return bsTicketGrantplanCheckMapper.selectAutoTicketInterestGrantDetail(req.getCheckId());
    }

    @Override
    public BsInterestTicketGrantAttrVO getManualTicketGrantDetail(BsInterestTicketGrantAttrVO req) {
        return bsTicketGrantplanCheckMapper.selectManualTicketInterestGrantDetail(req.getCheckId());
    }

    @Override
    public void addTicketGrantPlan(BsTicketGrantPlanCheck check) {
        check.setCreateTime(new Date());
        check.setUpdateTime(new Date());
        bsTicketGrantplanCheckMapper.insertSelective(check);
    }

    @Override
    public void addAutoTicketGrantPlan(final BsInterestTicketGrantAttrVO req) {
        log.info("新增自动加息券发放计划，发放计划名称：" + req.getSerialName());

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                String serialNo = Util.getSerialNo();

                BsTicketGrantPlanCheck check = new BsTicketGrantPlanCheck();
                check.setSerialNo(serialNo);
                check.setSerialName(req.getSerialName());
                check.setTicketType(Constants.TICKET_INTEREST_TYPE_INTEREST_TICKET); // 加息券
                check.setDistributeType(Constants.TICKET_DISTRIBUTE_TYPE_AUTO); // 自动
                check.setApplicant(req.getApplicant());
                check.setApplyTime(new Date());
                check.setCheckStatus(Constants.TICKET_CHECK_STATUS_UNCHECKED);// 待审核
                check.setGrantStatus(Constants.TICKET_GRANT_STATUS_INIT); // 未发放

                BsInterestTicketGrantAttribute attribute = new BsInterestTicketGrantAttribute();
                attribute.setSerialNo(serialNo);
                attribute.setTicketName(check.getSerialName());
                attribute.setTicketApr(req.getTicketApr());
                attribute.setGrantTotal(req.getGrantTotal());
                attribute.setGrantNum(0);
                attribute.setValidTermType(req.getValidTermType());
                if (req.getValidTermType() != null) {
                    if (Constants.AUTO_TICKET_VALID_TERM_TYPE_AFTER_RECEIVE.equals(req.getValidTermType())) {
                        //有效期类型--发放后生效
                        attribute.setAvailableDays(req.getAvailableDays());
                    } else if (Constants.AUTO_TICKET_VALID_TERM_TYPE_FIXED.equals(req.getValidTermType())) {
                        attribute.setUseTimeStart(DateUtil.parseDateTime(req.getUseTimeStart()));
                        attribute.setUseTimeEnd(DateUtil.parseDateTime(req.getUseTimeEnd()));
                    }
                }
                attribute.setNotifyChannel(req.getNotifyChannel());
                attribute.setInvestLimit(req.getInvestLimit());
                attribute.setProductLimit(req.getProductLimit());
                attribute.setTermLimit(req.getTermLimit());

                BsAutoInterestTicketRule rule = new BsAutoInterestTicketRule();
                rule.setSerialNo(serialNo);
                String agentIds = req.getAgentIds();
                //存在-1
                if (agentIds.indexOf("-1") >= 0) {
                    agentIds = "-1";
                } else if (agentIds.endsWith(",")) {
                    agentIds = agentIds.substring(0, agentIds.length() - 1);
                }
                rule.setAgentIds(agentIds);
                rule.setTriggerType(req.getTriggerType());
                rule.setTriggerTimeStart(DateUtil.parseDateTime(req.getTriggerTimeStart()));
                rule.setTriggerTimeEnd(DateUtil.parseDateTime(req.getTriggerTimeEnd()));

                addTicketGrantPlan(check);
                ticketInterestGrantAttrService.addTicketGrantAttribute(attribute);
                ticketInterestGrantAttrService.addAutoTicketGrantRule(rule);
            }
        });
    }

    @Override
    public void addManualTicketGrantPlan(final BsInterestTicketGrantAttrVO req) {
        log.info("新增手动加息券发放计划，发放计划名称：" + req.getSerialName());
        final List<Integer> userIds = ArrayUtils.split2Integer(req.getUserIds());
        int userCount = bsUserService.findCountUserByIds(userIds);
        if (req.getGrantTotal().compareTo(userCount) != 0) {
            log.info("加息券有效发放用户数量" + userIds.size() + "与实际发放用户数量" + userCount + "不一致");
            throw new PTMessageException(PTMessageEnum.TICKET_CHECK_GRANT_COUNT_ERROR);
        }

        log.info("手动加息券实际发放用户数量：" + userCount);
        req.setGrantTotal(userCount);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                String serialNo = Util.getSerialNo();

                BsTicketGrantPlanCheck check = new BsTicketGrantPlanCheck();
                check.setSerialNo(serialNo);
                check.setSerialName(req.getSerialName());
                check.setTicketType(Constants.TICKET_INTEREST_TYPE_INTEREST_TICKET); // 加息券
                check.setDistributeType(Constants.TICKET_DISTRIBUTE_TYPE_MANUAL); // 手动
                check.setApplicant(req.getApplicant());
                check.setApplyTime(new Date());
                check.setCheckStatus(Constants.TICKET_CHECK_STATUS_UNCHECKED);// 待审核
                check.setGrantStatus(Constants.TICKET_GRANT_STATUS_INIT); // 未发放
                addTicketGrantPlan(check);

                BsInterestTicketGrantAttribute attribute = new BsInterestTicketGrantAttribute();
                attribute.setSerialNo(serialNo);
                attribute.setTicketName(check.getSerialName());
                attribute.setTicketApr(req.getTicketApr());
                attribute.setGrantTotal(req.getGrantTotal());
                attribute.setGrantNum(0);
                attribute.setValidTermType(Constants.AUTO_TICKET_VALID_TERM_TYPE_FIXED);
                attribute.setUseTimeStart(DateUtil.parseDateTime(req.getUseTimeStart()));
                attribute.setUseTimeEnd(DateUtil.parseDateTime(req.getUseTimeEnd()));
                attribute.setNotifyChannel(req.getNotifyChannel());
                attribute.setInvestLimit(req.getInvestLimit());
                attribute.setProductLimit(req.getProductLimit());
                attribute.setTermLimit(req.getTermLimit());
                ticketInterestGrantAttrService.addTicketGrantAttribute(attribute);

                for (Integer userId : userIds) {
                    BsTicketPreDetail ticketPreDetail = new BsTicketPreDetail();
                    ticketPreDetail.setUserId(userId);
                    ticketPreDetail.setSerialNo(check.getSerialNo());
                    ticketPreDetail.setCreateTime(new Date());
                    ticketPreDetail.setUpdateTime(new Date());
                    bsTicketPreDetailMapper.insertSelective(ticketPreDetail);
                }
            }
        });
    }

    @Override
    public void disableTicket(Integer id) {
        log.info("加息券停用，发放计划ID：" + id);
        BsTicketGrantPlanCheck check = bsTicketGrantplanCheckMapper.selectByPrimaryKey(id);
        if (check == null || !Constants.TICKET_CHECK_STATUS_PASS.equals(check.getCheckStatus())) {
            log.info("加息券不存在或状态有误，发放计划ID：" + id);
            throw new PTMessageException(PTMessageEnum.TICKET_CHECK_NOT_EXIST);
        }

        if (!Constants.TICKET_GRANT_STATUS_INIT.equals(check.getGrantStatus())
                && !Constants.TICKET_GRANT_STATUS_PROCESS.equals(check.getGrantStatus())) {
            log.info("加息券发放状态有误，发放计划ID：" + id);
            throw new PTMessageException(PTMessageEnum.TICKET_CHECK_GRANT_STATUS_ERROR);
        }

        check.setGrantStatus(Constants.TICKET_GRANT_STATUS_CLOSE);
        check.setUpdateTime(new Date());
        bsTicketGrantplanCheckMapper.updateByPrimaryKeySelective(check);
    }

    @Override
    public void checkTicketGrantPlan(Integer checkId, boolean isPass, Integer checher) {
        log.info("审核加息券发放计划，发放计划ID：" + checkId + "审核结果：" + isPass);

        BsTicketGrantPlanCheck grantPlanCheck = bsTicketGrantplanCheckMapper.selectByPrimaryKey(checkId);
        if (grantPlanCheck == null || !Constants.TICKET_CHECK_STATUS_UNCHECKED.equals(grantPlanCheck.getCheckStatus())) {
            throw new PTMessageException(PTMessageEnum.TICKET_CHECK_NOT_EXIST);
        }

        if (isPass) {
            // 审核通过
            grantPlanCheck.setCheckStatus(Constants.TICKET_CHECK_STATUS_PASS);

            // 自动发放，审核通过后，发放状态: 发放中
            if (Constants.TICKET_DISTRIBUTE_TYPE_AUTO.equals(grantPlanCheck.getDistributeType())) {
                grantPlanCheck.setGrantStatus(Constants.TICKET_GRANT_STATUS_PROCESS);
            }
        } else {
            // 审核拒绝
            grantPlanCheck.setCheckStatus(Constants.TICKET_CHECK_STATUS_REFUSE);
        }
        grantPlanCheck.setChecker(checher);
        grantPlanCheck.setCheckTime(new Date());
        grantPlanCheck.setUpdateTime(new Date());

        bsTicketGrantplanCheckMapper.updateByPrimaryKeySelective(grantPlanCheck);
    }

    @Override
    public void manualInterestTicketSend(String checkIdStr) {
        log.info("发放手动加息券，发放计划ID：" + checkIdStr);

        final List<Integer> checkIds = ArrayUtils.split2Integer(checkIdStr);
        for (Integer checkId : checkIds) {
            BsTicketGrantPlanCheck check = bsTicketGrantplanCheckMapper.selectByPrimaryKey(checkId);
            if (check != null && Constants.TICKET_CHECK_STATUS_PASS.equals(check.getCheckStatus())
                    && Constants.TICKET_DISTRIBUTE_TYPE_MANUAL.equals(check.getDistributeType())
                    && Constants.TICKET_GRANT_STATUS_INIT.equals(check.getGrantStatus())) {
                BsInterestTicketGrantAttributeExample example = new BsInterestTicketGrantAttributeExample();
                example.createCriteria().andSerialNoEqualTo(check.getSerialNo());
                List<BsInterestTicketGrantAttribute> attributes = bsInterestTicketGrantAttributeMapper.selectByExample(example);
                if (CollectionUtils.isEmpty(attributes)) {
                    log.info("发放手动加息券(" + check.getSerialName() + ")，加息券不存在或状态有误");
                    throw new PTMessageException(PTMessageEnum.TICKET_CHECK_NOT_EXIST.getCode(), "(" + check.getSerialName() + ")" + PTMessageEnum.TICKET_CHECK_NOT_EXIST.getMessage());
                }
                BsInterestTicketGrantAttribute attribute = attributes.get(0);
                if (attribute.getUseTimeEnd().before(new Date())) {
                    log.info("加息券(" + check.getSerialName() + ")使用结束时间不能早于当前时间，使用结束时间->" + attribute.getUseTimeEnd());
                    throw new PTMessageException(PTMessageEnum.TICKET_ATTR_USETIMEEND_ERROR.getCode(), "(" + check.getSerialName() + ")" + PTMessageEnum.TICKET_ATTR_USETIMEEND_ERROR.getMessage());
                }
            } else {
                log.info("发放手动加息券(" + checkId + ")，加息券不存在或状态有误");
                throw new PTMessageException(PTMessageEnum.TICKET_CHECK_NOT_EXIST.getCode(), "发放计划中有" + PTMessageEnum.TICKET_CHECK_NOT_EXIST.getMessage());
            }
        }

        new Thread(new Runnable() {
            public void run() {
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        for (Integer checkId : checkIds) {
                            BsTicketGrantPlanCheck check = bsTicketGrantplanCheckMapper.selectByPrimaryKey(checkId);
                            // 修改加息券属性表
                            BsInterestTicketGrantAttributeExample example = new BsInterestTicketGrantAttributeExample();
                            example.createCriteria().andSerialNoEqualTo(check.getSerialNo());
                            List<BsInterestTicketGrantAttribute> attributes = bsInterestTicketGrantAttributeMapper.selectByExample(example);
                            // 修改属性表信息
                            BsInterestTicketGrantAttribute attribute = attributes.get(0);
                            attribute.setGrantNum(attribute.getGrantTotal());
                            attribute.setUpdateTime(new Date());
                            bsInterestTicketGrantAttributeMapper.updateByPrimaryKeySelective(attribute);
                            check.setGrantStatus(Constants.TICKET_GRANT_STATUS_FINISH);
                            check.setUpdateTime(new Date());
                            //修改批次表信息
                            bsTicketGrantplanCheckMapper.updateByPrimaryKeySelective(check);
                            //发放手动红包
                            bsTicketGrantplanCheckMapper.insertInterestTicketInfo(check.getId());
                        }
                    }
                });
            }
        }).start();
    }
}
