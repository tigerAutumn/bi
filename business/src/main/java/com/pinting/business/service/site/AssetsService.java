package com.pinting.business.service.site;

import com.pinting.business.hessian.site.message.ResMsg_Invest_AccountBalance;
import com.pinting.business.hessian.site.message.ResMsg_Invest_InvestListQuery;
import com.pinting.business.hessian.site.message.ResMsg_Invest_InvestProportion;
import com.pinting.business.model.BsQuestionnaire;
import com.pinting.business.model.vo.BsSubAccountVO;
import com.pinting.business.model.vo.WithdrawForecastVO;

import java.util.List;
import java.util.Map;

/**
 * Author:      cyb
 * Date:        2016/8/27
 * Description: 我的资产以及相关内页服务
 */
public interface AssetsService {

    /**
     * 账户余额
     * @param userId    用户ID
     * @return
     */
    ResMsg_Invest_AccountBalance accountBalance(String userId);

    /**
     * 委托计划投资列表
     * @param userId        用户ID
     * @param pageNum       当前页（页面当前页码是1，则传入1，无需转换成0）
     * @param numPerPage    每页显示条数
     * @return
     */
    ResMsg_Invest_InvestListQuery commissionPlanList(int userId, int pageNum, int numPerPage);

    /**
     * 充值说明信息
     * @return  RECHANGE_LIMIT-充值下限(元)
     */
    Map<String, Object> queryTopUpInstruction();

    /**
     * 提现说明信息
     * @return  eachMonthWithdrawTimes-每月提现次数，超过则扣除相应手续费；withdrawCounterFee-超过每月提现次数的相应手续费
     */
    Map<String, Object> queryWithdrawInstrction();
    
    /**
     * 根据用户ID查询用户当月可免费提现次数
     * @param userId 用户ID
     * @return 返回可免费提现次数
     */
    Integer queryFreeWithdrawTimesByUserId(Integer userId);
    
    /**
     * 查询用户投资分布列表
     * @param userId
     * @return
     */
    ResMsg_Invest_InvestProportion investProportionList(Integer userId);
    /**
     * 根据用户ID查询赞分期计划
     * @param userId 用户ID
     * @param pageIndex  页码
     * @param pageSize  分页条数
     * @param investStatus   投资状态
     * @return
     */
	List<BsSubAccountVO> zanMyInvestByUserId(Integer userId, Integer pageIndex,
			Integer pageSize, String investStatus);
	/**
	 * 根据用户ID查询用户当前赞分期产品持有金额
	 * 匹配本金-已回款本金+委托中的委托金额
	 * = 开户金额 - 委托退回金额 - 已回款本金+债转支付未还利息
	 * @param userId
	 * @return
	 */
	Double zanMyInvestCurrentHoldByUserId(Integer userId);

    /**
     * 根据金额预测此用户提现到账时间
     * @param userId    用户ID
     * @param amount    提现金额
     * @return
     */
    WithdrawForecastVO withdrawForecast(Integer userId, Double amount);

    /**
     * 风险测评结果
     * @param userId    用户ID
     * @return  测评过-yes；未测评-no；已过期-expire
     */
    String riskStatus(Integer userId);
}
