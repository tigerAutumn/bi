package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.UpdateHFUserApplyVO;
import com.pinting.business.service.manage.HFUserActionService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_UpdatePlatAcct;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_UpdatePlatAcct;
import com.pinting.gateway.out.service.HfbankTransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cyb on 2017/10/16.
 */
@Service
public class HFUserActionServiceImpl implements HFUserActionService {

    private final Logger logger = LoggerFactory.getLogger(HFUserActionServiceImpl.class);

    @Autowired
    private HfbankTransportService hfbankTransportService;
    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private BsUpdateHfUserApplyMapper bsUpdateHfUserApplyMapper;
    @Autowired
    private BsHfbankUserExtMapper bsHfbankUserExtMapper;
    @Autowired
    private BsBankCardMapper bsBankCardMapper;
    @Autowired
    private UcUserMapper ucUserMapper;
    @Autowired
    private UcUserExtMapper ucUserExtMapper;

    @Override
    public List<UpdateHFUserApplyVO> applyQuery(String userName, String mobile, String cardMobile, String startTime,
                                                String endTime, String mUser, Integer pageNum, Integer numPerPage) {
        int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage); // mysql的分页
        return bsUserMapper.selectUpdateHFUserInfo(userName, mobile, cardMobile, startTime, endTime, mUser, start, numPerPage);
    }

    @Override
    public int applyQuery(String userName, String mobile, String cardMobile, String startTime, String endTime, String mUser) {
        return bsUserMapper.countUpdateHFUserInfo(userName, mobile, cardMobile, startTime, endTime, mUser);
    }

    @Override
    public List<UpdateHFUserApplyVO> passQuery(String userName, String mobile, String cardMobile, String startTime,
                                               String endTime, String mUser, Integer pageNum, Integer numPerPage) {
        int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage); // mysql的分页
        return bsUserMapper.selectUpdateHFUserApplyInfo(userName, mobile, cardMobile, startTime, endTime, mUser, start, numPerPage);
    }

    @Override
    public int passQuery(String userName, String mobile, String cardMobile, String startTime, String endTime, String mUser) {
        return bsUserMapper.countUpdateHFUserApplyInfo(userName, mobile, cardMobile, startTime, endTime, mUser);
    }

    @Override
    public void apply(Integer userId, Integer mUserId, String mobile) {
        BsUserExample userExample = new BsUserExample();
        userExample.createCriteria().andIdNotEqualTo(userId).andMobileEqualTo(mobile);
        int sameMobile = bsUserMapper.countByExample(userExample);
        if(sameMobile > 0) {
            throw new PTMessageException(PTMessageEnum.APPLY_DATA_TOO_MORE.getCode(), "此手机号已被其他注册账户占用");
        }

        BsHfbankUserExtExample extExample = new BsHfbankUserExtExample();
        extExample.createCriteria().andUserIdEqualTo(userId);
        List<BsHfbankUserExt> bsHfbankUserExt = bsHfbankUserExtMapper.selectByExample(extExample);
        if(CollectionUtils.isEmpty(bsHfbankUserExt)) {
            throw new PTMessageException(PTMessageEnum.HF_NOT_BIND);
        } else if(StringUtil.isBlank(bsHfbankUserExt.get(0).getHfUserId())) {
            throw new PTMessageException(PTMessageEnum.HF_NOT_BIND);
        }

        BsBankCardExample cardExample = new BsBankCardExample();
        cardExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(Constants.BANK_CARD_NORMAL);
        int cardCount = bsBankCardMapper.countByExample(cardExample);
        if(cardCount <= 0) {
            throw new PTMessageException(PTMessageEnum.USER_NOT_BIND_CARD);
        }

        BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
        BsUpdateHfUserApplyExample example = new BsUpdateHfUserApplyExample();
        example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(Constants.HF_BANK_UPDATE_USER_APPLY_STATUS_INIT);
        int count = bsUpdateHfUserApplyMapper.countByExample(example);
        if(count > 0) {
            throw new PTMessageException(PTMessageEnum.APPLY_DATA_TOO_MORE);
        }

        BsUpdateHfUserApply bsUpdateHfUserApply = new BsUpdateHfUserApply();
        bsUpdateHfUserApply.setApplyUserId(mUserId);
        bsUpdateHfUserApply.setApplyTime(new Date());
        bsUpdateHfUserApply.setNewMobile(mobile);
        bsUpdateHfUserApply.setOldMobile(bsUser.getMobile());
        bsUpdateHfUserApply.setCreateTime(new Date());
        bsUpdateHfUserApply.setUpdateTime(new Date());
        bsUpdateHfUserApply.setUserId(userId);
        bsUpdateHfUserApply.setStatus(Constants.HF_BANK_UPDATE_USER_APPLY_STATUS_INIT);
        bsUpdateHfUserApplyMapper.insertSelective(bsUpdateHfUserApply);
    }

    @Override
    public void updatePlatAcct(Integer id, Integer mUserId) {
        BsUpdateHfUserApply bsUpdateHfUserApply = bsUpdateHfUserApplyMapper.selectByPrimaryKey(id);
        if(Constants.HF_BANK_UPDATE_USER_APPLY_STATUS_SUCCESS.equals(bsUpdateHfUserApply.getStatus())) {
            throw new PTMessageException(PTMessageEnum.APPLY_DATA_IS_SUCCESS);
        }

        BsHfbankUserExtExample extExample = new BsHfbankUserExtExample();
        extExample.createCriteria().andUserIdEqualTo(bsUpdateHfUserApply.getUserId());
        List<BsHfbankUserExt> bsHfbankUserExt = bsHfbankUserExtMapper.selectByExample(extExample);
        if(CollectionUtils.isEmpty(bsHfbankUserExt)) {
            throw new PTMessageException(PTMessageEnum.HF_NOT_BIND);
        } else if(StringUtil.isBlank(bsHfbankUserExt.get(0).getHfUserId())) {
            throw new PTMessageException(PTMessageEnum.HF_NOT_BIND);
        }

        BsUser bsUser = new BsUser();
        bsUser.setId(bsUpdateHfUserApply.getUserId());
        bsUser.setMobile(bsUpdateHfUserApply.getNewMobile());
        bsUserMapper.updateByPrimaryKeySelective(bsUser);

        List<UcUserExt> ucUserExtList = ucUserExtMapper.selectByBsUserAndStatus(bsUser.getId(), Constants.UC_USER_TYPE_BGW, Constants.UC_USER_OPEN);
        UcUser ucUser = new UcUser();
        ucUser.setId(ucUserExtList.get(0).getUcUserId());
        ucUser.setMobile(bsUpdateHfUserApply.getNewMobile());
        ucUser.setUpdateTime(new Date());
        ucUserMapper.updateByPrimaryKeySelective(ucUser);

        String hfUserId = bsHfbankUserExt.get(0).getHfUserId();

        B2GReqMsg_HFBank_UpdatePlatAcct req = new B2GReqMsg_HFBank_UpdatePlatAcct();
        req.setOrder_no(Util.generateOrderNo(bsUser.getId()));
        req.setPlatcust(hfUserId);
        req.setMobile(bsUpdateHfUserApply.getNewMobile());
        req.setPartner_trans_date(new Date());
        req.setPartner_trans_time(new Date());
        req.setCus_type("1");
        B2GResMsg_HFBank_UpdatePlatAcct res;
        try {
            res = hfbankTransportService.updatePlatAcct(req);
        } catch (Exception e) {
            e.printStackTrace();
            update2Fail(bsUpdateHfUserApply, mUserId, ConstantUtil.BSRESCODE_FAIL, "通讯异常");
            return;
        }

        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            update2Success(bsUpdateHfUserApply, mUserId);
        } else {
            update2Fail(bsUpdateHfUserApply, mUserId, res.getResCode(), res.getResMsg());
        }
    }

    private void update2Success(BsUpdateHfUserApply bsUpdateHfUserApply, Integer mUserId) {
        BsUpdateHfUserApply apply = new BsUpdateHfUserApply();
        apply.setId(bsUpdateHfUserApply.getId());
        apply.setStatus(Constants.HF_BANK_UPDATE_USER_APPLY_STATUS_SUCCESS);
        apply.setCheckUserId(mUserId);
        apply.setPassTime(new Date());
        apply.setUpdateTime(new Date());
        bsUpdateHfUserApplyMapper.updateByPrimaryKeySelective(apply);
    }

    private void update2Fail(BsUpdateHfUserApply bsUpdateHfUserApply, Integer mUserId, String code, String message) {
        BsUpdateHfUserApply apply = new BsUpdateHfUserApply();
        apply.setId(bsUpdateHfUserApply.getId());
        apply.setStatus(Constants.HF_BANK_UPDATE_USER_APPLY_STATUS_FAIL);
        apply.setCheckUserId(mUserId);
        apply.setPassTime(new Date());
        apply.setUpdateTime(new Date());
        apply.setCode(code);
        apply.setMessage(message);
        bsUpdateHfUserApplyMapper.updateByPrimaryKeySelective(apply);
    }
}
