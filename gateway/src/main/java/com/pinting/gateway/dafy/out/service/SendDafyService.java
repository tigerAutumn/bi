package com.pinting.gateway.dafy.out.service;

import com.pinting.gateway.dafy.out.model.BasicInformationReqModel;
import com.pinting.gateway.dafy.out.model.BasicInformationResModel;
import com.pinting.gateway.dafy.out.model.BindBankcardReqModel;
import com.pinting.gateway.dafy.out.model.BindBankcardResModel;
import com.pinting.gateway.dafy.out.model.BuyProductReqModel;
import com.pinting.gateway.dafy.out.model.BuyProductResModel;
import com.pinting.gateway.dafy.out.model.CheckAccountReqModel;
import com.pinting.gateway.dafy.out.model.CheckAccountResModel;
import com.pinting.gateway.dafy.out.model.CustomerWithdrawReqModel;
import com.pinting.gateway.dafy.out.model.CustomerWithdrawResModel;
import com.pinting.gateway.dafy.out.model.DafyIncrementDataReqModel;
import com.pinting.gateway.dafy.out.model.DafyIncrementDataResModel;
import com.pinting.gateway.dafy.out.model.DafyIncrementDeptReqModel;
import com.pinting.gateway.dafy.out.model.DafyIncrementDeptResModel;
import com.pinting.gateway.dafy.out.model.DafyIncrementUserReqModel;
import com.pinting.gateway.dafy.out.model.DafyIncrementUserResModel;
import com.pinting.gateway.dafy.out.model.GetLoanRelationUrlReqModel;
import com.pinting.gateway.dafy.out.model.GetLoanRelationUrlResModel;
import com.pinting.gateway.dafy.out.model.QueryCurrDeptInfoByDeptCodeReqModel;
import com.pinting.gateway.dafy.out.model.QueryCurrDeptInfoByDeptCodeResModel;
import com.pinting.gateway.dafy.out.model.QueryDeptInfoByDeptCodeReqModel;
import com.pinting.gateway.dafy.out.model.QueryDeptInfoByDeptCodeResModel;
import com.pinting.gateway.dafy.out.model.QueryLoanRelationshipReqModel;
import com.pinting.gateway.dafy.out.model.QueryLoanRelationshipResModel;
import com.pinting.gateway.dafy.out.model.QueryRepayJnlReqModel;
import com.pinting.gateway.dafy.out.model.QueryRepayJnlResModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByCurrDeptCodeReqModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByCurrDeptCodeResModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByDeptCodeReqModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByDeptCodeResModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByIdReqModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByIdResModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByPhoneReqModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByPhoneResModel;
import com.pinting.gateway.dafy.out.model.QueryWXAccountDetailReqModel;
import com.pinting.gateway.dafy.out.model.QueryWXAccountDetailResModel;
import com.pinting.gateway.dafy.out.model.RegisterCustomerReqModel;
import com.pinting.gateway.dafy.out.model.RegisterCustomerResModel;
import com.pinting.gateway.dafy.out.model.SysBatchBuyProductReqModel;
import com.pinting.gateway.dafy.out.model.SysBatchBuyProductResModel;
import com.pinting.gateway.dafy.out.model.SysWithdrawReqModel;
import com.pinting.gateway.dafy.out.model.SysWithdrawResModel;

/**
 * 向达飞发起请求 接口服务
 * @Project: gateway
 * @Title: DafySendService.java
 * @author dingpf
 * @date 2015-2-10 下午6:22:55
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface SendDafyService {
	
	//交易码
	public static final String TRANS_CODE_LOGIN = "login";//登录
	public static final String TRANS_CODE_REGISTER="registerCustomer";//用户注册
	
	/**
	 * 用户注册
	 * @param req 请求数据 RegisterCustomerReqModel
	 * @return RegisterCustomerResModel
	 */
	public RegisterCustomerResModel register(RegisterCustomerReqModel req);
	
	/**
	 * 绑定一个银行卡
	 * @param req BindBankCardReqModel
	 * @return BindBankCardResModel
	 */
	public BindBankcardResModel bindCard(BindBankcardReqModel req);
	
	/**
	 * 购买达飞产品
	 * 支付过程:
	 * 1.由网新客户端发起,发给网新后台
	 * 2.网新后台整理参数,发送给网新网关层
	 * 3.网关层发送给达飞后台
	 * 4.分三种情况:
	 * 	1)系统层通讯失败,比如token超时,hash解密异常。此时达飞后台返回EXPR或者REFU，网关并不关注retHtml
	 * 	2)系统层通讯成功，但业务参数（金额超限等）不合法，此时达飞访问网新提供的urlError，获得html返回给网关层
	 * 	3)以上皆成功，此时达飞访问自己的跳转页面，获得html后返回给网关层
	 * 5.网关层把html传给网新后台
	 * 6.网新后台再把完整的html返回给客户端
	 * @param req BuyProductReqModel
	 * @return BuyProductResModel
	 */
	public BuyProductResModel buyProduct(BuyProductReqModel req);
	
	/**
	 * 资金对账 
	 * @param req CheckAccountReqModel（对账日期）
	 * @return CheckAccountResModel（资金对账列表）
	 */
	public CheckAccountResModel checkAccount(CheckAccountReqModel req);
	
	/**
	 * 债权关系查询 2015-12-01之前的旧的投资
	 * @param req QueryLoanRelationReqModel
	 * @return QueryLoadRelationResModel
	 */
	public GetLoanRelationUrlResModel getLoanRelationUrl(GetLoanRelationUrlReqModel req);
	
	/**
	 * 基本信息接口（达飞注册+实名认证+卡绑定）
	 * @param req basicInformation
	 * @return BasicInformationResModel
	 */
	public BasicInformationResModel basicInformation(BasicInformationReqModel req);
	
	/**
	 * 用户提现接口
	 * @param req CustomerWithdrawReqModel
	 * @return CustomerWithdrawResModel
	 */
	public CustomerWithdrawResModel customerWithdraw(CustomerWithdrawReqModel req);

	/**
	 * 品听提现接口
	 * @param req WXWithdrawReqModel
	 * @return WXWithdrawResModel
	 */
	public SysWithdrawResModel sysWithdraw(SysWithdrawReqModel req);
	/**
	 * 查询网新账户明细
	 * @param req
	 * @return
	 */
	public QueryWXAccountDetailResModel wXAccountDetailQuery(QueryWXAccountDetailReqModel req);
	
	/**
	 * 系统批量购买达飞理财
	 * @param req
	 * @return
	 */
	public SysBatchBuyProductResModel sysBatchBuyProduct(SysBatchBuyProductReqModel req);

	/**
	 * 系统批量购买7贷理财
	 * @param req
	 * @return
     */
	com.pinting.gateway.loan7.out.model.SysBatchBuyProductResModel sysBatchBuy7Product(com.pinting.gateway.loan7.out.model.SysBatchBuyProductReqModel req) ;
	/**
	 * 债权关系查询  2015-12-01之后的投资
	 * @param req QueryLoanRelationReqModel
	 * @return QueryLoadRelationResModel
	 */
	public QueryLoanRelationshipResModel getLoanRelationNewResModel(QueryLoanRelationshipReqModel req);
	
	/**
	 * 云贷2.0 ，根据借款编号、客户编号 查询该笔借款的还款流水记录
	 * @param req
	 * @return
	 */
	public QueryRepayJnlResModel queryRepayJnl(QueryRepayJnlReqModel req);
	
	/**
	 * 根据电话查询达飞业务员信息
	 * @param req
	 * @return
	 */
	public QueryUserInfoByPhoneResModel queryUserInfoByPhone(QueryUserInfoByPhoneReqModel req);
	
	/**
	 * 根据id查询达飞业务员信息
	 * @param req
	 * @return
	 */
	public QueryUserInfoByIdResModel queryUserInfoById(QueryUserInfoByIdReqModel req);
	
	/**
	 * 根据部门编码查询当前部门及子部门下的达飞业务员
	 * @param req
	 * @return
	 */
	public QueryUserInfoByDeptCodeResModel queryUserInfoByDeptCode(QueryUserInfoByDeptCodeReqModel req);
	
	/**
	 * 根据部门编码查询当前部门下的达飞业务员
	 * @param req
	 * @return
	 */
	public QueryUserInfoByCurrDeptCodeResModel queryUserInfoByCurrDeptCode(QueryUserInfoByCurrDeptCodeReqModel req);
	
	/**
	 * 根据部门编码查询当前部门的下级部门
	 * @param req
	 * @return
	 */
	public QueryDeptInfoByDeptCodeResModel queryDeptInfoByDeptCode(QueryDeptInfoByDeptCodeReqModel req);
	
	/**
	 * 根据部门编码查询当前部门
	 * @param req
	 * @return
	 */
	public QueryCurrDeptInfoByDeptCodeResModel queryCurrDeptInfoByDeptCode(QueryCurrDeptInfoByDeptCodeReqModel req);
	
	/**
	 * 达飞用户信息增量同步
	 * @param req
	 * @return
	 */
	public DafyIncrementUserResModel incrementUserInfo(DafyIncrementUserReqModel req);
	
	/**
	 * 达飞部门信息增量同步
	 * @param req
	 * @return
	 */
	public DafyIncrementDeptResModel incrementDeptInfo(DafyIncrementDeptReqModel req);
	
	/**
	 * 达飞权限信息增量同步
	 * @param req
	 * @return
	 */
	public DafyIncrementDataResModel incrementDataInfo(DafyIncrementDataReqModel req);

}
