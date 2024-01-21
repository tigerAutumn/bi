package com.pinting.business.service.site.impl;

import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.dao.BsWaterConservationSignUpMapper;
import com.pinting.business.dao.BsWaterConservationVoteRecordMapper;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.ActivityBaseVO;
import com.pinting.business.model.vo.ActivityWaterSignUpVO;
import com.pinting.business.model.vo.WaterVotePageInfoVO;
import com.pinting.business.service.site.ActivityService;
import com.pinting.business.service.site.AgentActivityService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/3/22
 * Description:
 */
@Service
public class AgentActivityServiceImpl implements AgentActivityService {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private BsWaterConservationSignUpMapper bsWaterConservationSignUpMapper;
    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private BsWaterConservationVoteRecordMapper bsWaterConservationVoteRecordMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public ActivityBaseVO signUpPageInfo(Integer userId) {
        // 1. 活动开始还是结束
        // 2. 用户是否登录
        // 3. 用户是否提交审核过

        ActivityBaseVO result = new ActivityBaseVO();
        int activityId = Constants.ACTIVITY_ID_WATER_SIGN_UP;
        // 1. 报名活动开始还是结束
        result.setIsStart(activityService.duringActivity(activityId));
        // 1.1 如果报名活动已经结束，则判断投票活动是否已经结束
        if(Constants.ACTIVITY_IS_END.equals(result.getIsStart())) {
            String isStart = activityService.duringActivity(Constants.ACTIVITY_ID_WATER_VOTE);
            if(Constants.ACTIVITY_IS_END.equals(isStart)) {
                result.setIsStart("all_end");
            }
        }

        // 2. 用户是否登录
        if(null == userId) {
            result.setIsLogin(Constants.IS_LOGIN_NO);
        } else {
            result.setIsLogin(Constants.IS_LOGIN_YES);
            BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
            if(null == bsUser) {
                result.setIsLogin(Constants.IS_LOGIN_NO);
            }
        }
        // 3. 用户是否提交审核过
        if(result.getIsLogin().equals(Constants.IS_LOGIN_YES)) {
            BsWaterConservationSignUp info = bsWaterConservationSignUpMapper.selectRecentData(userId);
            result.setIsJoined(null == info ? Constants.IS_NO : info.getCheckStatus());
        }
        return result;
    }

    @Override
    public ActivityBaseVO waterSignUp(ActivityWaterSignUpVO req) {
        // 1. 活动开始还是结束
        // 2. 用户是否登录
        // 3. 用户是否提交审核过
        // 3.1 已通过。不能再提交数据
        // 3.2 待审核。不能再提交数据
        // 4 保存数据

        ActivityBaseVO result = new ActivityBaseVO();
        int activityId = Constants.ACTIVITY_ID_WATER_SIGN_UP;
        // 1. 活动开始还是结束
        result.setIsStart(activityService.duringActivity(activityId));
        if(!Constants.ACTIVITY_IS_START.equals(result.getIsStart())) {
            return result;
        }
        // 2. 用户是否登录
        if(null == req.getUserId()) {
            result.setIsLogin(Constants.IS_LOGIN_NO);
            return result;
        } else {
            result.setIsLogin(Constants.IS_LOGIN_YES);
        }
        BsUser bsUser = bsUserMapper.selectByPrimaryKey(req.getUserId());
        if(null == bsUser) {
            result.setIsLogin(Constants.IS_LOGIN_NO);
            return result;
        }
        // 3. 用户是否提交审核过
        // 3.1 已通过。不能再提交数据
        BsWaterConservationSignUpExample example = new BsWaterConservationSignUpExample();
        example.createCriteria().andUserIdEqualTo(req.getUserId()).andActivityIdEqualTo(activityId).andCheckStatusEqualTo(Constants.BENISON_CHECK_STATUS_PASS);
        int passCount = bsWaterConservationSignUpMapper.countByExample(example);
        if(passCount > 0) {
            result.setIsJoined(Constants.BENISON_CHECK_STATUS_PASS);
            return result;
        }
        // 3.2 待审核。不能再提交数据
        BsWaterConservationSignUpExample initExample = new BsWaterConservationSignUpExample();
        initExample.createCriteria().andUserIdEqualTo(req.getUserId()).andActivityIdEqualTo(activityId).andCheckStatusEqualTo(Constants.BENISON_CHECK_STATUS_INIT);
        int initCount = bsWaterConservationSignUpMapper.countByExample(initExample);
        if(initCount > 0) {
            result.setIsJoined(Constants.BENISON_CHECK_STATUS_INIT);
            return result;
        }
        // 4 保存数据
        BsWaterConservationSignUp record = new BsWaterConservationSignUp();
        BsWaterConservationSignUpExample countExample = new BsWaterConservationSignUpExample();
        countExample.createCriteria().andActivityIdEqualTo(activityId);
        int serialNo = bsWaterConservationSignUpMapper.countByExample(countExample);
        BeanUtils.copyProperties(req, record);
        record.setCheckStatus(Constants.BENISON_CHECK_STATUS_INIT);
        record.setActivityId(activityId);
        record.setSerialNo(serialNo + 1);
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        bsWaterConservationSignUpMapper.insertSelective(record);
        return result;
    }

    @Override
    public List<BsWaterConservationSignUp> queryWaterSignUpList(ActivityWaterSignUpVO req) {
        List<BsWaterConservationSignUp> waterList = bsWaterConservationSignUpMapper.selectByPage(req.getStart(), req.getNumPerPage());
        return CollectionUtils.isEmpty(waterList) ? new ArrayList<BsWaterConservationSignUp>() : waterList;
    }

    @Override
    public int countWaterSignUpList(ActivityWaterSignUpVO req) {
        Integer activityId = Constants.ACTIVITY_ID_WATER_SIGN_UP;
        BsWaterConservationSignUpExample example = new BsWaterConservationSignUpExample();
        example.createCriteria().andActivityIdEqualTo(activityId);
        return bsWaterConservationSignUpMapper.countByExample(example);
    }

    @Override
    public void checkWater(BsWaterConservationSignUp req) {
        req.setActivityId(Constants.ACTIVITY_ID_WATER_SIGN_UP);
        req.setUpdateTime(new Date());
        req.setCheckTime(new Date());
        bsWaterConservationSignUpMapper.updateByPrimaryKeySelective(req);
    }

    @Override
    public ActivityBaseVO waterVote(final Integer voteUserId, final Integer signUpId) {
        // 1. 活动是否已经开始
        // 2. 是否已经登录
        // 3. 是否超过了当天投票次数限额
        // 4. 插入投票记录
        // 5. 更新报名表投票个数

        ActivityBaseVO result = new ActivityBaseVO();
        // 1. 活动是否已经开始
        result.setIsStart(activityService.duringActivity(Constants.ACTIVITY_ID_WATER_VOTE));
        if(!Constants.ACTIVITY_IS_START.equals(result.getIsStart())) {
            return result;
        }

        // 2. 是否已经登录
        result.setIsLogin(null == voteUserId ? Constants.IS_LOGIN_NO : Constants.IS_LOGIN_YES);
        BsUser bsUser = bsUserMapper.selectByPrimaryKey(voteUserId);
        result.setIsLogin(null == bsUser ? Constants.IS_LOGIN_NO : Constants.IS_LOGIN_YES);
        if(Constants.IS_LOGIN_NO.equals(result.getIsLogin())) {
            return result;
        }

        // 3. 是否超过了当天投票次数限额
        Date now = new Date();
        String start = DateUtil.format(now, "yyyy-MM-dd") + " 00:00:00";
        String end = DateUtil.format(now, "yyyy-MM-dd") + " 23:59:59";
        Date startTime = null;
        Date endTime = null;
        try {
            startTime = DateUtil.parse(start, "yyyy-MM-dd HH:mm:ss");
            endTime = DateUtil.parse(end, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BsWaterConservationVoteRecordExample example = new BsWaterConservationVoteRecordExample();
        example.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_WATER_VOTE).andVoteUserIdEqualTo(voteUserId).andCreateTimeBetween(startTime, endTime);
        int count = bsWaterConservationVoteRecordMapper.countByExample(example);
        result.setIsJoined(count >= 10 ? Constants.IS_YES : Constants.IS_NO);
        if(Constants.IS_YES.equals(result.getIsJoined())) {
            return result;
        }

        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                // 4. 插入投票记录
                BsWaterConservationVoteRecord record = new BsWaterConservationVoteRecord();
                record.setVoteUserId(voteUserId);
                record.setSignUpId(signUpId);
                record.setActivityId(Constants.ACTIVITY_ID_WATER_VOTE);
                record.setCreateTime(new Date());
                record.setUpdateTime(new Date());
                bsWaterConservationVoteRecordMapper.insertSelective(record);

                // 5. 更新报名表投票个数
                BsWaterConservationSignUp signUp = bsWaterConservationSignUpMapper.selectByPrimaryKey(signUpId);
                signUp.setVoteNum(signUp.getVoteNum() == null ? 1 : signUp.getVoteNum() + 1);
                signUp.setUpdateTime(new Date());
                bsWaterConservationSignUpMapper.updateByPrimaryKeySelective(signUp);
                return true;
            }
        });

        return result;
    }

    @Override
    public WaterVotePageInfoVO waterVotePageInfo(Integer userId, Integer signUpNo, Integer pageNum, Integer numPerPage) {

        // 1. 活动是否已经开始
        // 2. 是否已经登录
        // 3. 是否报名过
        // 4. 获取报名用户名称和编号
        // 5. 获取列表

        WaterVotePageInfoVO result = new WaterVotePageInfoVO();
        // 1. 活动是否已经开始
        result.setIsStart(activityService.duringActivity(Constants.ACTIVITY_ID_WATER_VOTE));
        if(!Constants.ACTIVITY_IS_START.equals(result.getIsStart())) {
            return result;
        }

        // 2. 是否已经登录
        result.setIsLogin(null == userId ? Constants.IS_LOGIN_NO : Constants.IS_LOGIN_YES);
        BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
        result.setIsLogin(null == bsUser ? Constants.IS_LOGIN_NO : Constants.IS_LOGIN_YES);

        // 3. 是否报名过
        if(Constants.IS_LOGIN_YES.equals(result.getIsLogin())) {
            BsWaterConservationSignUpExample example = new BsWaterConservationSignUpExample();
            example.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_WATER_SIGN_UP).andUserIdEqualTo(userId).andCheckStatusEqualTo(Constants.BENISON_CHECK_STATUS_PASS);
            List<BsWaterConservationSignUp> list = bsWaterConservationSignUpMapper.selectByExample(example);
            result.setIsJoined(CollectionUtils.isEmpty(list) ? Constants.IS_NO : Constants.IS_YES);
            if(Constants.IS_YES.equals(result.getIsJoined())) {
                // 4. 获取报名用户名称和编号
                result.setUserName(list.get(0).getUserName());
                result.setSerialNo(new DecimalFormat("000").format(list.get(0).getSerialNo()));
            }
        }

        // 5. 获取列表
        int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
        List<BsWaterConservationSignUp> signUpList = bsWaterConservationSignUpMapper.selectBySignUpNo(signUpNo, start, numPerPage);
        int count = bsWaterConservationSignUpMapper.countBySignUpNo(signUpNo);
        result.setList(signUpList);
        result.setTotalRows(count);
        result.setTotalPages((int) Math.ceil((double) count / numPerPage));
        return result;
    }

}
