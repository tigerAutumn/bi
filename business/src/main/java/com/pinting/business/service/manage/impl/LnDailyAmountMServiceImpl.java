package com.pinting.business.service.manage.impl;

import com.github.pagehelper.PageHelper;
import com.pinting.business.accounting.finance.model.ProductType;
import com.pinting.business.accounting.finance.model.SubAccountCode;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.enums.VIPId4PartnerEnum;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.LnDailyAmountMapper;
import com.pinting.business.model.LnDailyAmount;
import com.pinting.business.model.LnDailyAmountExample;
import com.pinting.business.model.common.PagerModelRspDTO;
import com.pinting.business.model.common.PagerReqDTO;
import com.pinting.business.service.manage.LnDailyAmountMService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 每日额度管理
 */
@Service
public class LnDailyAmountMServiceImpl implements LnDailyAmountMService {

    @Autowired
    private LnDailyAmountMapper lnDailyAmountMapper;
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;

    public PagerModelRspDTO<LnDailyAmount> getLnDailyAmountAll(PagerReqDTO pagerReqDTO, String status) {
        //查询运营最近一次有效的额度配置
        LnDailyAmountExample lnDailyAmountExample = new LnDailyAmountExample();
        lnDailyAmountExample.createCriteria().andStatusEqualTo(status);
        lnDailyAmountExample.setOrderByClause("update_time desc");
        PageHelper.startPage(pagerReqDTO.getPageNum(), pagerReqDTO.getNumPerPage());
        List<LnDailyAmount> lnDailyAmountList = lnDailyAmountMapper.selectByExample(lnDailyAmountExample);
        return new PagerModelRspDTO(lnDailyAmountList);
    }

    public LnDailyAmount getLnDailyAmount(String partnerCode, String status) {
        //查询运营最近一次有效的额度配置
        LnDailyAmountExample lnDailyAmountExample = new LnDailyAmountExample();
        lnDailyAmountExample.createCriteria().andPartnerCodeEqualTo(partnerCode).andStatusEqualTo(status);
        lnDailyAmountExample.setOrderByClause("update_time desc");
        List<LnDailyAmount> lnDailyAmountList = lnDailyAmountMapper.selectByExample(lnDailyAmountExample);
        if (CollectionUtils.isNotEmpty(lnDailyAmountList)) {
            return lnDailyAmountList.get(0);
        } else {
            return null;
        }
    }

    public double getAvailableBalanceAuthMoney(PartnerEnum partnerEnum) {
        ProductType productType = SubAccountCode.productTypeMap.get(partnerEnum);
        String authProductType = productType.getAuthCode();
        String redProductType = productType.getRedCode();
        return bsSubAccountMapper.dailyAvailableBalanceMoneyCalculate(authProductType, redProductType);
    }

    public double getAvailableBalanceAuthMoneyForSevenDian(PartnerEnum... partnerEnums) {
        List<String> productTypes = new ArrayList<>();
        for (PartnerEnum partnerEnum : partnerEnums) {
            ProductType productType = SubAccountCode.productTypeMap.get(partnerEnum);
            productTypes.add(productType.getAuthCode());
            productTypes.add(productType.getRedCode());
        }
        return bsSubAccountMapper.dailyAvailableBalanceMoneyCalculateForSevenDian(productTypes);
    }

    public double getQuitAuthMoney(PartnerEnum partnerEnum, VIPId4PartnerEnum vipId4PartnerEnum) {
        ProductType productType = SubAccountCode.productTypeMap.get(partnerEnum);
        String authProductType = productType.getAuthCode();
        String redProductType = productType.getRedCode();
        return bsSubAccountMapper.dailyQuitAuthMoneyCalculate(authProductType, redProductType);
    }

    public void initLnDailyAmount(double freeRateYunDai, double freeRateSevenDai, Date useDate) {
        LnDailyAmount lnDailyAmountYunDai = getLnDailyAmount(PartnerEnum.YUN_DAI_SELF.getCode(), Constants.LN_DAILY_AMOUNT_STATUS_INIT);
        if (lnDailyAmountYunDai == null) {
            LnDailyAmount lnDailyAmountTemp = new LnDailyAmount();
            lnDailyAmountTemp.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
            lnDailyAmountTemp.setStatus(Constants.LN_DAILY_AMOUNT_STATUS_INIT);
            lnDailyAmountTemp.setFreeRate(freeRateYunDai);
            lnDailyAmountTemp.setUseDate(useDate);
            lnDailyAmountTemp.setCreateTime(new Date());
            lnDailyAmountTemp.setUpdateTime(new Date());
            lnDailyAmountMapper.insertSelective(lnDailyAmountTemp);
        } else {
            LnDailyAmount lnDailyAmountTemp = new LnDailyAmount();
            lnDailyAmountTemp.setId(lnDailyAmountYunDai.getId());
            lnDailyAmountTemp.setFreeRate(freeRateYunDai);
            lnDailyAmountTemp.setUseDate(useDate);
            lnDailyAmountTemp.setUpdateTime(new Date());
            lnDailyAmountMapper.updateByPrimaryKeySelective(lnDailyAmountTemp);
        }

        LnDailyAmount lnDailyAmountSevenDai = getLnDailyAmount(PartnerEnum.SEVEN_DAI_SELF.getCode(), Constants.LN_DAILY_AMOUNT_STATUS_INIT);
        if (lnDailyAmountSevenDai == null) {
            LnDailyAmount lnDailyAmountTemp = new LnDailyAmount();
            lnDailyAmountTemp.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
            lnDailyAmountTemp.setStatus(Constants.LN_DAILY_AMOUNT_STATUS_INIT);
            lnDailyAmountTemp.setFreeRate(freeRateSevenDai);
            lnDailyAmountTemp.setUseDate(useDate);
            lnDailyAmountTemp.setCreateTime(new Date());
            lnDailyAmountTemp.setUpdateTime(new Date());
            lnDailyAmountMapper.insertSelective(lnDailyAmountTemp);
        } else {
            LnDailyAmount lnDailyAmountTemp = new LnDailyAmount();
            lnDailyAmountTemp.setId(lnDailyAmountSevenDai.getId());
            lnDailyAmountTemp.setFreeRate(freeRateSevenDai);
            lnDailyAmountTemp.setUseDate(useDate);
            lnDailyAmountTemp.setUpdateTime(new Date());
            lnDailyAmountMapper.updateByPrimaryKeySelective(lnDailyAmountTemp);
        }
    }

    public Map<String, Double> dailyFreeMoneyCalculate() {
        String nextDay = DateUtil.getDate(DateUtil.nextDay(new Date(), 1));
        Map<String, Double> mapInfo = new HashMap<>();
        // 自由资金户每日总额度
        Double dailyFreeCashTotal = bsSubAccountMapper.dailyMoneyCalculateFixedDay(nextDay);
        // 云贷资金户每日总额度
        Double dailyYunTotal = bsSubAccountMapper.dailyMoneyCountFixedDay(nextDay);
        // 七贷资金户每日总额度
        Double dailySevenTotal = bsSubAccountMapper.sevenDaiSelfDailyMoneyCountFixedDay(nextDay);

        mapInfo.put("dailyFreeCashTotal", dailyFreeCashTotal);
        mapInfo.put("dailyYunTotal", dailyYunTotal);
        mapInfo.put("dailySevenTotal", dailySevenTotal);
        return mapInfo;
    }
}
