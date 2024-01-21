package com.pinting.business.service.site.impl;

import com.pinting.business.dao.BsBankCardMapper;
import com.pinting.business.dao.BsHfbankUserExtMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.DepGuideVO;
import com.pinting.business.model.vo.WaiteActivateInfoVO;
import com.pinting.business.service.site.HFBankDepSiteService;
import com.pinting.business.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/4/12
 * Description: 存管引导相关（前端相关）
 */
@Service
public class HFBankDepSiteServiceImpl implements HFBankDepSiteService {

    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private BsHfbankUserExtMapper bsHfbankUserExtMapper;
    @Autowired
    private BsBankCardMapper bsBankCardMapper;

    @Override
    public DepGuideVO findDepGuideInfo(Integer userId) {
        DepGuideVO result = new DepGuideVO();

        // 账户类型
        // 1. 普通户-没有开通存管户
        BsSubAccount depJSH = bsSubAccountMapper.selectDEPJSHSubAccountByUserId(userId);
        if(null == depJSH) {
            result.setAccountType(Constants.HFBANK_GUIDE_ACCOUNT_TYPE_SIMPLE);
        } else {
            // 2. 存管户-只开通了存管户 或者 既开通了存管户又有普通户且普通户没有余额
            BsSubAccount jsh = bsSubAccountMapper.selectJSHSubAccountByUserId(userId);
            if(null == jsh || (jsh.getBalance().compareTo(0d) == 0
                    && jsh.getAvailableBalance().compareTo(0d) == 0
                    && jsh.getCanWithdraw().compareTo(0d) == 0
                    && jsh.getFreezeBalance().compareTo(0d) == 0)) {
                result.setAccountType(Constants.HFBANK_GUIDE_ACCOUNT_TYPE_DEP);
            } else {
                // 3. 双帐户-既开通了存管户又有普通户且（普通户存在余额或者有投资中结算中的港湾计划）
                result.setAccountType(Constants.HFBANK_GUIDE_ACCOUNT_TYPE_DOUBLE);
            }
        }

        // 是否实名
        BsBankCardExample cardExample = new BsBankCardExample();
        List<Integer> statusList = new ArrayList<>();
        statusList.add(Constants.BANK_CARD_NORMAL);
        statusList.add(Constants.BANK_CARD_UNBIND);
        cardExample.createCriteria().andStatusIn(statusList).andUserIdEqualTo(userId);
        int bindName = bsBankCardMapper.countByExample(cardExample);
        result.setIsBindName(bindName > 0 ? Constants.YES : Constants.NO);

        // 是否恒丰开户
        // 1. 未绑卡用户
        BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
        if(Constants.IS_BIND_BANK_YES != bsUser.getIsBindBank() || Constants.IS_BIND_NAME_YES != bsUser.getIsBindName()) {
            result.setIsOpened(Constants.HFBANK_GUIDE_NO_BIND_CARD);
            return result;
        }
        // 2. 已绑卡用户，恒丰批量开户失败
        BsHfbankUserExtExample allExtExample = new BsHfbankUserExtExample();
        allExtExample.createCriteria().andUserIdEqualTo(userId).andStatusNotEqualTo(Constants.HFBANK_USER_EXT_STATUS_CANCEL);
        List<BsHfbankUserExt> allExts = bsHfbankUserExtMapper.selectByExample(allExtExample);
        if(CollectionUtils.isEmpty(allExts)) {
            result.setIsOpened(Constants.HFBANK_GUIDE_FAILED_BIND_HF);
            return result;
        }
        // 3. 已绑卡用户，恒丰批量开户成功，未激活
        BsHfbankUserExtExample waitExtExample = new BsHfbankUserExtExample();
        waitExtExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(Constants.HFBANK_USER_EXT_STATUS_WAIT_ACTIVATE);
        List<BsHfbankUserExt> waitExts = bsHfbankUserExtMapper.selectByExample(waitExtExample);
        if(!CollectionUtils.isEmpty(waitExts)) {
            result.setIsOpened(Constants.HFBANK_GUIDE_WAIT_ACTIVATE);
            return result;
        }
        // 4. 已激活
        BsHfbankUserExtExample openedExtExample = new BsHfbankUserExtExample();
        openedExtExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(Constants.HFBANK_USER_EXT_STATUS_OPEN);
        List<BsHfbankUserExt> openedExts = bsHfbankUserExtMapper.selectByExample(openedExtExample);
        if(!CollectionUtils.isEmpty(openedExts)) {
            result.setIsOpened(Constants.HFBANK_GUIDE_OPEN);
            return result;
        }
        return result;
    }

    @Override
    public WaiteActivateInfoVO waiteActivateInfo(Integer userId) {
        WaiteActivateInfoVO result = bsUserMapper.selectWaiteActivateInfo(userId);
        return result;
    }

    @Override
    public void activate(Integer userId) {

        BsHfbankUserExtExample example = new BsHfbankUserExtExample();
        example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(Constants.HFBANK_USER_EXT_STATUS_OPEN);
        long count = bsHfbankUserExtMapper.countByExample(example);
        if(count > 0) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "您已激活，无需再次激活");
        }

        BsHfbankUserExt ext = new BsHfbankUserExt();
        ext.setUpdateTime(new Date());
        ext.setStatus(Constants.HFBANK_USER_EXT_STATUS_OPEN);
        BsHfbankUserExtExample updateExample = new BsHfbankUserExtExample();
        updateExample.createCriteria().andUserIdEqualTo(userId);
        bsHfbankUserExtMapper.updateByExampleSelective(ext, updateExample);
    }

}
