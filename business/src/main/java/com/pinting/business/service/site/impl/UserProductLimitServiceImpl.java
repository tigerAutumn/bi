package com.pinting.business.service.site.impl;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.dao.BsUserProductLimitDetailMapper;
import com.pinting.business.dao.BsUserProductLimitMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.facade.site.UserFacade;
import com.pinting.business.hessian.site.message.ReqMsg_UserProductLimit_UserProductLimitAdd;
import com.pinting.business.hessian.site.message.ReqMsg_User_CheckNewUser;
import com.pinting.business.hessian.site.message.ResMsg_UserProductLimit_UserProductLimitAdd;
import com.pinting.business.hessian.site.message.ResMsg_User_CheckNewUser;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.BsUserProductLimit;
import com.pinting.business.model.BsUserProductLimitDetail;
import com.pinting.business.model.BsUserProductLimitDetailExample;
import com.pinting.business.model.BsUserProductLimitExample;
import com.pinting.business.service.site.UserProductLimitService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;

@Service
public class UserProductLimitServiceImpl implements UserProductLimitService {

	@Autowired
	private BsUserProductLimitMapper userProductLimitMapper;
	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;
	@Autowired
	private BsUserProductLimitDetailMapper bsUserProductLimitDetailMapper;
	@Autowired
	private UserFacade userFacade;
	private Logger logger = LoggerFactory.getLogger(UserProductLimitServiceImpl.class);

	@Override
	public List<BsUserProductLimit> findUserProductLimit(
			BsUserProductLimitExample example) {
		return userProductLimitMapper.selectByExample(example);
	}

	@Override
	public void userProductLimitAdd(
			ReqMsg_UserProductLimit_UserProductLimitAdd req,ResMsg_UserProductLimit_UserProductLimitAdd res) {
	    logger.info("================== 添加新手额度开始  userID : "+req.getUserId()+"\t Event : "+req.getEvent()+"====================");
	    BsSysConfig productLimitStartTime = bsSysConfigMapper.selectByPrimaryKey(Constants.CHECK_NEWUSER_START_TIME);
        BsSysConfig productLimitEndTime = bsSysConfigMapper.selectByPrimaryKey(Constants.CHECK_NEWUSER_END_TIME);
        if (productLimitStartTime!=null && productLimitEndTime!=null) {
            Date startTime =DateUtil.parseDateTime(productLimitStartTime.getConfValue());
            Date endTime =DateUtil.parseDateTime(productLimitEndTime.getConfValue());
            Calendar calendarStart=Calendar.getInstance();
            Calendar calendarNow=Calendar.getInstance();
            Calendar calendarEnd=Calendar.getInstance();
            calendarStart.setTime(startTime);
            calendarNow.setTime(new Date());
            calendarEnd.setTime(endTime);
            if (calendarStart.compareTo(calendarNow) <= 0 && calendarNow.compareTo(calendarEnd) <= 0) {
                if(Constants.EVENT_REGISTER.equals(req.getEvent())){
                    registerAdd(req, res);
                } else if(Constants.EVENT_RECOMMEND.equals(req.getEvent())) {
                    recommendAdd(req, res);
                } else if(Constants.EVENT_SHARE.equals(req.getEvent())) {
                    shareAdd(req, res);
                }
            }
        }
	}
	
	/**
	 * 注册添加新手额度
	 * @param req
	 * @param res
	 */
	private void registerAdd(ReqMsg_UserProductLimit_UserProductLimitAdd req,ResMsg_UserProductLimit_UserProductLimitAdd res){
		
		
		BsUserProductLimitExample example = new BsUserProductLimitExample();
		example.createCriteria().andUserIdEqualTo(req.getUserId());
        List<BsUserProductLimit> bsUserProductLimitList = userProductLimitMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(bsUserProductLimitList)) {
			throw new PTMessageException(PTMessageEnum.NEWS_NOT_EXIST);
		}
		BsUserProductLimit bsUserProductLimit = new BsUserProductLimit();
        bsUserProductLimit.setUserId(req.getUserId());
        bsUserProductLimit.setProductId(Constants.NEW_USER_PRODUCT_ID);
        bsUserProductLimit.setLeftAmount(Constants.REGISTER_AMOUNT);
        bsUserProductLimit.setCreateTime(new Date());
        bsUserProductLimit.setUpdateTime(null);
        userProductLimitMapper.insertSelective(bsUserProductLimit);
        
        BsUserProductLimitDetail bsUserProductLimitDetail = new BsUserProductLimitDetail();
        bsUserProductLimitDetail.setUserId(req.getUserId());
        bsUserProductLimitDetail.setProductId(Constants.NEW_USER_PRODUCT_ID);
        bsUserProductLimitDetail.setEvent(Constants.EVENT_REGISTER_CN);
        bsUserProductLimitDetail.setAmount(Constants.REGISTER_AMOUNT);
        bsUserProductLimitDetail.setCreateTime(new Date());
        bsUserProductLimitDetailMapper.insertSelective(bsUserProductLimitDetail);
	}
	
	/**
	 * 邀请添加新手额度
	 * @param req
	 * @param res
	 */
	private void recommendAdd(ReqMsg_UserProductLimit_UserProductLimitAdd req,ResMsg_UserProductLimit_UserProductLimitAdd res){
    	//判断邀请用户是否新用户
    	ReqMsg_User_CheckNewUser reqCheckNewUser= new ReqMsg_User_CheckNewUser();
    	reqCheckNewUser.setUserId(req.getUserId());
    	ResMsg_User_CheckNewUser resCheckNewUser =new ResMsg_User_CheckNewUser();
    	userFacade.checkNewUser(reqCheckNewUser, resCheckNewUser);
    	if (resCheckNewUser.getIsNewUser()) {
    		BsUserProductLimitExample example = new BsUserProductLimitExample();
    		example.createCriteria().andUserIdEqualTo(req.getUserId());
            BsUserProductLimit bsUserProductLimit = userProductLimitMapper.selectByExample(example).get(0);
            bsUserProductLimit.setUserId(req.getUserId());
            bsUserProductLimit.setProductId(Constants.NEW_USER_PRODUCT_ID);
            bsUserProductLimit.setLeftAmount(bsUserProductLimit.getLeftAmount()+Constants.RECOMMEND_AMOUNT);
            bsUserProductLimit.setCreateTime(null);
            bsUserProductLimit.setUpdateTime(new Date());
            userProductLimitMapper.updateByPrimaryKeySelective(bsUserProductLimit); // 要求初始值，否则先判断是否需要插入
            
            BsUserProductLimitDetail bsUserProductLimitDetail = new BsUserProductLimitDetail();
            bsUserProductLimitDetail.setUserId(req.getUserId());
            bsUserProductLimitDetail.setProductId(Constants.NEW_USER_PRODUCT_ID);
            bsUserProductLimitDetail.setEvent(Constants.EVENT_RECOMMEND_CN);
            bsUserProductLimitDetail.setAmount(Constants.RECOMMEND_AMOUNT);
            bsUserProductLimitDetail.setCreateTime(new Date());
            bsUserProductLimitDetailMapper.insertSelective(bsUserProductLimitDetail);
		}
	}
	
	/**
	 * 分享添加新手额度
	 * @param req
	 * @param res
	 */
	private void shareAdd(ReqMsg_UserProductLimit_UserProductLimitAdd req,ResMsg_UserProductLimit_UserProductLimitAdd res) {
        ReqMsg_User_CheckNewUser reqCheckNewUser = new ReqMsg_User_CheckNewUser();
        ResMsg_User_CheckNewUser resCheckNewUser = new ResMsg_User_CheckNewUser();
        reqCheckNewUser.setUserId(req.getUserId());
        userFacade.checkNewUser(reqCheckNewUser, resCheckNewUser);
        // 新用户，则获得新手额度
        if(resCheckNewUser.getIsNewUser() == true) {
            BsUserProductLimitExample bsUserProductLimitExample = new BsUserProductLimitExample();
            bsUserProductLimitExample.createCriteria().andUserIdEqualTo(req.getUserId());
            List<BsUserProductLimit> limits = userProductLimitMapper.selectByExample(bsUserProductLimitExample);
            BsUserProductLimitDetailExample bsUserProductLimitDetailExample = new BsUserProductLimitDetailExample();
            bsUserProductLimitDetailExample.createCriteria().andUserIdEqualTo(req.getUserId()).andEventEqualTo(Constants.EVENT_SHARE_CN);
            List<BsUserProductLimitDetail> details = bsUserProductLimitDetailMapper.selectByExample(bsUserProductLimitDetailExample);
            Double sumShareAmount = 0d;
            if(!CollectionUtils.isEmpty(details)) {
                for (BsUserProductLimitDetail bsUserProductLimitDetail : details) {
                    sumShareAmount = MoneyUtil.add(sumShareAmount, bsUserProductLimitDetail.getAmount()).doubleValue();
                }
            }
            if(!CollectionUtils.isEmpty(limits)) {
                BsUserProductLimit userProductLimit = limits.get(0);
                // 分享已获得额度少于最大分享获得额度，则获得额度
                logger.info("================== 添加新手额度开始  userID : "+req.getUserId()+"\t Event : "+req.getEvent()+ " 比较 ："+ userProductLimit.getLeftAmount().compareTo(Constants.MAX_SHARE_AMOUNT)+ "====================");
                if(userProductLimit.getLeftAmount() != null 
                        && sumShareAmount.compareTo(Constants.MAX_SHARE_AMOUNT) < 0) {
                    // 更新限额表
                    userProductLimit.setLeftAmount(userProductLimit.getLeftAmount() == null ? Constants.EACH_SHARE_AMOUNT : 
                        MoneyUtil.add(Constants.EACH_SHARE_AMOUNT, userProductLimit.getLeftAmount()).doubleValue());
                    userProductLimit.setUpdateTime(new Date());
                    userProductLimitMapper.updateByPrimaryKey(userProductLimit);
                    // 插入明细表
                    BsUserProductLimitDetail bsUserProductLimitDetail = new BsUserProductLimitDetail();
                    bsUserProductLimitDetail.setUserId(req.getUserId());
                    bsUserProductLimitDetail.setProductId(Constants.NEW_USER_PRODUCT_ID);
                    bsUserProductLimitDetail.setEvent(Constants.EVENT_SHARE_CN);
                    bsUserProductLimitDetail.setAmount(Constants.EACH_SHARE_AMOUNT);
                    bsUserProductLimitDetail.setCreateTime(new Date());
                    bsUserProductLimitDetailMapper.insertSelective(bsUserProductLimitDetail);
                }
            } else {
                // 插入限额表
                BsUserProductLimit bsUserProductLimit = new BsUserProductLimit();
                bsUserProductLimit.setUserId(req.getUserId());
                bsUserProductLimit.setProductId(Constants.NEW_USER_PRODUCT_ID);
                bsUserProductLimit.setLeftAmount(Constants.EACH_SHARE_AMOUNT);
                bsUserProductLimit.setCreateTime(new Date());
                bsUserProductLimit.setUpdateTime(new Date());
                userProductLimitMapper.insertSelective(bsUserProductLimit);
                
                // 插入明细表
                BsUserProductLimitDetail bsUserProductLimitDetail = new BsUserProductLimitDetail();
                bsUserProductLimitDetail.setUserId(req.getUserId());
                bsUserProductLimitDetail.setProductId(Constants.NEW_USER_PRODUCT_ID);
                bsUserProductLimitDetail.setEvent(Constants.EVENT_SHARE_CN);
                bsUserProductLimitDetail.setAmount(Constants.EACH_SHARE_AMOUNT);
                bsUserProductLimitDetail.setCreateTime(new Date());
                bsUserProductLimitDetailMapper.insertSelective(bsUserProductLimitDetail);
            }
        }
	}
}
