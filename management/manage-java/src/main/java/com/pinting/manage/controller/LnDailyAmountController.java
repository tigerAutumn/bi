package com.pinting.manage.controller;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.enums.VIPId4PartnerEnum;
import com.pinting.business.model.LnDailyAmount;
import com.pinting.business.model.common.PagerModelRspDTO;
import com.pinting.business.model.common.PagerReqDTO;
import com.pinting.business.service.manage.LnDailyAmountMService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.manage.enums.ShowTerminalEnum;
import com.pinting.util.ReturnDWZAjax;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @title 放款资金管理
 * Created by 邹晟 on 2018/06/08.
 */
@Controller
public class LnDailyAmountController {

    @Autowired
    private LnDailyAmountMService lnDailyAmountMService;

    /**
     * 放款资金管理列表页
     *
     * @param pagerReqDTO
     * @param statusSearch
     * @param model
     * @return
     */
    @RequestMapping("/dailyOperations/loanAmountManage/index")
    public String loanAmountManageIndex(PagerReqDTO pagerReqDTO, String statusSearch, Map<String, Object> model) {
        if (StringUtils.isBlank(statusSearch)) {
            statusSearch = Constants.LN_DAILY_AMOUNT_STATUS_AVALIABLE;
        }

        PagerModelRspDTO pageList = lnDailyAmountMService.getLnDailyAmountAll(pagerReqDTO, statusSearch);
        model.put("pageList", pageList);

        Map<String, String> enumDataMap = new LinkedHashMap<>();
        enumDataMap.put(Constants.LN_DAILY_AMOUNT_STATUS_AVALIABLE, "可用");
        enumDataMap.put(Constants.LN_DAILY_AMOUNT_STATUS_INIT, "初始");
        enumDataMap.put(Constants.LN_DAILY_AMOUNT_STATUS_CANCELLED, "失效");
        model.put("statusEnum", enumDataMap);

        model.put("yunAvailableAuthMoney", lnDailyAmountMService.getAvailableBalanceAuthMoney(PartnerEnum.YUN_DAI_SELF));
        model.put("yunQuitAuthMoney", lnDailyAmountMService.getQuitAuthMoney(PartnerEnum.YUN_DAI_SELF, VIPId4PartnerEnum.YUN_DAI_SELF));

        model.put("sevenAvailableAuthMoney", lnDailyAmountMService.getAvailableBalanceAuthMoney(PartnerEnum.SEVEN_DAI_SELF));
        model.put("sevenDianAvailableAuthMoney", lnDailyAmountMService.getAvailableBalanceAuthMoneyForSevenDian(PartnerEnum.YUN_DAI_SELF, PartnerEnum.SEVEN_DAI_SELF, PartnerEnum.FREE));
        model.put("sevenQuitAuthMoney", lnDailyAmountMService.getQuitAuthMoney(PartnerEnum.SEVEN_DAI_SELF, VIPId4PartnerEnum.SEVEN_DAI_SELF));

        model.put("freeAvailableAuthMoney", lnDailyAmountMService.getAvailableBalanceAuthMoney(PartnerEnum.FREE));
        model.put("freeQuitAuthMoney", lnDailyAmountMService.getQuitAuthMoney(PartnerEnum.FREE, VIPId4PartnerEnum.FREE));

        model.put("statusSearch", statusSearch);
        return "/dailyOperations/loanAmountManage/index";
    }

    /**
     * 进入添加或修改页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/dailyOperations/loanAmountManage/addOrModify_page")
    public String addOrModifyPage(Map<String, Object> model) {
        LnDailyAmount lnDailyAmountYunDai = lnDailyAmountMService.getLnDailyAmount(PartnerEnum.YUN_DAI_SELF.getCode(), Constants.LN_DAILY_AMOUNT_STATUS_INIT);
        if (lnDailyAmountYunDai == null) {
            lnDailyAmountYunDai = lnDailyAmountMService.getLnDailyAmount(PartnerEnum.YUN_DAI_SELF.getCode(), Constants.LN_DAILY_AMOUNT_STATUS_AVALIABLE);
        }
        LnDailyAmount lnDailyAmountSevenDai = lnDailyAmountMService.getLnDailyAmount(PartnerEnum.SEVEN_DAI_SELF.getCode(), Constants.LN_DAILY_AMOUNT_STATUS_INIT);
        if (lnDailyAmountSevenDai == null) {
            lnDailyAmountSevenDai = lnDailyAmountMService.getLnDailyAmount(PartnerEnum.SEVEN_DAI_SELF.getCode(), Constants.LN_DAILY_AMOUNT_STATUS_AVALIABLE);
        }
        if (lnDailyAmountYunDai != null && lnDailyAmountSevenDai != null) {
            model.put("freeRateYunDai", lnDailyAmountYunDai.getFreeRate().intValue());
            model.put("freeRateSevenDai", lnDailyAmountSevenDai.getFreeRate().intValue());
        }
        model.put("useDate", DateUtil.nextDay(new Date(), 1));
        return "/dailyOperations/loanAmountManage/addOrModify_page";
    }

    /**
     * 添加或修改操作
     *
     * @param freeRateYunDai
     * @param freeRateSevenDai
     * @param useDate
     * @return
     */
    @RequestMapping("/dailyOperations/loanAmountManage/addOrModify")
    @ResponseBody
    public Map<Object, Object> addOrModify(Integer freeRateYunDai, Integer freeRateSevenDai, Date useDate) {

        if (freeRateYunDai == null) {
            return ReturnDWZAjax.fail("云贷自由站岗户额度比例未配置");
        }
        if (freeRateSevenDai == null) {
            return ReturnDWZAjax.fail("七贷自由站岗户额度比例未配置");
        }

        if (useDate == null) {
            useDate = DateUtil.nextDay(new Date(), 1);
        }

        double freeRateTotal = MoneyUtil.add(freeRateYunDai, freeRateSevenDai).doubleValue();

        try {
            lnDailyAmountMService.initLnDailyAmount(MoneyUtil.multiply(MoneyUtil.divide(freeRateYunDai, freeRateTotal, 2).doubleValue(), 100).doubleValue(),
                    MoneyUtil.multiply(MoneyUtil.divide(freeRateSevenDai, freeRateTotal, 2).doubleValue(), 100).doubleValue(), useDate);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail(e.getMessage());
        }
        return ReturnDWZAjax.success("操作成功！");
    }

    /**
     * 计算预计放款额度(自由站岗户按照比例分配)
     *
     * @param freeRateYunDai
     * @param freeRateSevenDai
     * @return
     */
    @RequestMapping("/dailyOperations/loanAmountManage/calculate")
    @ResponseBody
    public Map<Object, Object> calculate(Integer freeRateYunDai, Integer freeRateSevenDai) {
        Map<Object, Object> parameterMap = new HashMap<>();
        Map<String, Double> mapInfo = lnDailyAmountMService.dailyFreeMoneyCalculate();
        Double dailyFreeCashTotal = mapInfo.get("dailyFreeCashTotal");
        Double dailyYunTotal = mapInfo.get("dailyYunTotal");
        Double dailySevenTotal = mapInfo.get("dailySevenTotal");
        double freeRateTotal = MoneyUtil.add(freeRateYunDai, freeRateSevenDai).doubleValue();
        double yunAuthMoney = MoneyUtil.divide(MoneyUtil.multiply(dailyFreeCashTotal, freeRateYunDai).doubleValue(), freeRateTotal, 2).doubleValue();
        double sevenAuthMoney = MoneyUtil.divide(MoneyUtil.multiply(dailyFreeCashTotal, freeRateSevenDai).doubleValue(), freeRateTotal, 2).doubleValue();
        parameterMap.put("yunAuthMoney", MoneyUtil.format(MoneyUtil.add(yunAuthMoney, dailyYunTotal)));
        parameterMap.put("sevenAuthMoney", MoneyUtil.format(MoneyUtil.add(sevenAuthMoney, dailySevenTotal)));
        parameterMap.put("yunAuthMoneyInt", MoneyUtil.add(yunAuthMoney, dailyYunTotal));
        parameterMap.put("sevenAuthMoneyInt", MoneyUtil.add(sevenAuthMoney, dailySevenTotal));
        return ReturnDWZAjax.success("操作成功！", parameterMap);
    }
}
