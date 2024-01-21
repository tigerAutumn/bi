package com.pinting.business.facade.site;

import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.vo.EndOf2016CompanyAnnualVO;
import com.pinting.business.service.site.EndOf2016CompanyAnnualService;
import com.pinting.business.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 2016公司年会抽奖活动Facade
 * Created by shh on 2017/01/14 10:00.
 * @author shh
 */
@Component("EndOf2016CompanyAnnual")
public class EndOf2016CompanyAnnualFacade {

    @Autowired
    private EndOf2016CompanyAnnualService endOf2016CompanyAnnualService;

    /**
     * 抽奖滚屏数据
     * @param req
     * @param res
     */
    public void lotteryScrollingData(ReqMsg_EndOf2016CompanyAnnual_LotteryScrollingData req, ResMsg_EndOf2016CompanyAnnual_LotteryScrollingData res) {
        //1、可以抽奖的名字/滚动的名字
        List<EndOf2016CompanyAnnualVO> lotteryList = endOf2016CompanyAnnualService.quertLotteryNameList();
        //2、页面最终显示的中奖名单
        res.setValueList(BeanUtils.classToArrayList(lotteryList));
    }

    /**
     * 抽取五、六、七等奖
     * @param req
     * @param res
     */
    public void getLuckyPrizeList(ReqMsg_EndOf2016CompanyAnnual_GetLuckyPrizeList req, ResMsg_EndOf2016CompanyAnnual_GetLuckyPrizeList res) {
        //1、五、六、七等奖中奖列表
        List<EndOf2016CompanyAnnualVO> luckyLlist = endOf2016CompanyAnnualService.drawLuckyPrizeList(req.getActivityAwardId());
        //2、页面最终显示的中奖名单
        res.setValueList(BeanUtils.classToArrayList(luckyLlist));
    }

    /**
     * 抽取一、二等奖
     * @param req
     * @param res
     */
    public void getGrandPrize(ReqMsg_EndOf2016CompanyAnnual_GetGrandPrize req, ResMsg_EndOf2016CompanyAnnual_GetGrandPrize res) {
        //1、页面显示 一、二等奖中奖名单
        List<EndOf2016CompanyAnnualVO> grandPrizeList = endOf2016CompanyAnnualService.grandPrize(req.getActivityAwardId());
        res.setValueList(BeanUtils.classToArrayList(grandPrizeList));
    }

    /**
     * 抽取三、四等奖
     * @param req
     * @param res
     */
    public void getOtherAwards(ReqMsg_EndOf2016CompanyAnnual_GetOtherAwards req, ResMsg_EndOf2016CompanyAnnual_GetOtherAwards res) {
        //1、页面显示三、四等奖中奖名单
        List<EndOf2016CompanyAnnualVO> otherAwardsObj = endOf2016CompanyAnnualService.otherAwards(req.getActivityAwardId());
        res.setValueList(BeanUtils.classToArrayList(otherAwardsObj));
    }

    /**
     * 查询每个奖项 已中奖的人次
     * @param req
     * @param res
     */
    public void numberOfDraws(ReqMsg_EndOf2016CompanyAnnual_NumberOfDraws req, ResMsg_EndOf2016CompanyAnnual_NumberOfDraws res) {
        int maxNumberOfDraws = endOf2016CompanyAnnualService.queryNumberOfWinners(req.getActivityAwardId());
        res.setMaxNumberOfDraws(maxNumberOfDraws);
    }

}
