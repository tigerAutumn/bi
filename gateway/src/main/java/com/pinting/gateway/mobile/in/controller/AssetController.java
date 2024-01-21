package com.pinting.gateway.mobile.in.controller;

import com.alibaba.fastjson.JSONObject;
import com.pinting.business.hessian.site.message.*;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.baiduai.out.model.AuthTokenResModel;
import com.pinting.gateway.baiduai.out.service.BaiduAiService;
import com.pinting.gateway.business.out.service.AppAssetBusinessService;
import com.pinting.gateway.business.out.service.AppIndexBusinessService;
import com.pinting.gateway.mobile.in.enums.OpenMessageEnum;
import com.pinting.gateway.mobile.in.util.CheckUtil;
import com.pinting.gateway.mobile.in.util.Constants;
import com.pinting.gateway.util.FileUtil;
import com.pinting.gateway.util.GatewayMoneyUtil;
import com.pinting.open.base.controller.ControllerCallBack;
import com.pinting.open.base.controller.OpenController;
import com.pinting.open.base.exception.OpenException;
import com.pinting.open.base.request.Request;
import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.asset.*;
import com.pinting.open.pojo.request.asset.*;
import com.pinting.open.pojo.response.asset.*;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Scope("prototype")
@RequestMapping("mobile/asset")
public class AssetController extends OpenController {

	private Logger log = LoggerFactory.getLogger(AssetController.class);

	@Autowired
	private AppAssetBusinessService appAssetBusinessService;
	@Autowired
	private AppIndexBusinessService appIndexBusinessService;
	@Autowired
	private BaiduAiService baiduAiService;

	// 我的资产
	@ResponseBody
	@RequestMapping("my_asset/v_1.0.0")
	public String myAsset(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, MyAssetRequest.class,
				new ControllerCallBack() {

					@Override
					public void doCheck(Request request, Response response) {
						MyAssetRequest myAssetRequest = (MyAssetRequest) request;
						Integer userId = myAssetRequest.getUserId();
						if (userId == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						MyAssetResponse myAssetResponse = (MyAssetResponse) response;
						MyAssetRequest myAssetRequest = (MyAssetRequest) request;

						// 查询
						ReqMsg_User_AssetInfoQuery  reqAssetInfoQuery = new ReqMsg_User_AssetInfoQuery();
						reqAssetInfoQuery.setUserId(myAssetRequest.getUserId());
						reqAssetInfoQuery.setVersion("1.0.0");
						ResMsg_User_AssetInfoQuery resAssetInfoQuery = (ResMsg_User_AssetInfoQuery) appAssetBusinessService.assetInfoQuery(reqAssetInfoQuery);
						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resAssetInfoQuery.getResCode())) {

							/**
							 * private String mobile; //手机号码 private Double
							 * assetAmount; //账户总资产 private Double
							 * totalInvestEarnings; //累计收益 private Double
							 * investEarnings;   //当前收益 private Double
							 * totalBonus;//活动奖励 private Double availableBalance;
							 * //结算户账户可用余额 private Double dayInvestEarnings; //当日收益
							 * private Double jljCanWithdraw; ///奖励金账户可提现金额，我的奖励
							 * private Integer investNum; //我的投资（当前购买产品数） private
							 * int less7Days; //七天内产品到期的数目 private Integer
							 * processingNum;//当前用户处理中订单的数量(交易明细) private Integer
							 * bankCardCount; //银行卡张数
							 */
							myAssetResponse.setMobile(resAssetInfoQuery.getMobile());
							myAssetResponse.setAssetAmount(resAssetInfoQuery.getAssetAmount());
							myAssetResponse.setTotalInvestEarnings(resAssetInfoQuery.getTotalInvestEarnings());
							myAssetResponse.setInvestEarnings(resAssetInfoQuery.getInvestEarnings());
							myAssetResponse.setTotalBonus(resAssetInfoQuery.getTotalBonus());
							myAssetResponse.setAvailableBalance(resAssetInfoQuery.getAvailableBalance());
							myAssetResponse.setDayInvestEarnings(resAssetInfoQuery.getDayInvestEarnings());
							myAssetResponse.setJljCanWithdraw(resAssetInfoQuery.getJljCanWithdraw());
							myAssetResponse.setInvestNum(resAssetInfoQuery.getInvestNum());
							myAssetResponse.setLess7Days(resAssetInfoQuery.getLess7Days());
							myAssetResponse.setProcessingNum(resAssetInfoQuery.getProcessingNum());
							myAssetResponse.setBankCardCount(resAssetInfoQuery.getBankCardCount());
							myAssetResponse.setRedPacketNum(resAssetInfoQuery.getRedPacketNum());
							//BeanUtils.copyProperties(resAssetInfoQuery, myAssetResponse);
						}
						else {
							throw new OpenException(resAssetInfoQuery.getResCode(),resAssetInfoQuery.getResMsg());
						}

					}
				});
	}

	// 我的资产(包含了回款卡和交易密码)
	@ResponseBody
	@RequestMapping("my_asset/v_1.0.1")
	public String myAsset_1(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, MyAssetRequest.class,
				new ControllerCallBack() {

					@Override
					public void doCheck(Request request, Response response) {
						MyAssetRequest myAssetRequest = (MyAssetRequest) request;
						Integer userId = myAssetRequest.getUserId();
						if (userId == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						MyAssetResponse myAssetResponse = (MyAssetResponse) response;
						MyAssetRequest myAssetRequest = (MyAssetRequest) request;

						// 查询
						ReqMsg_User_AssetInfoQuery  reqAssetInfoQuery = new ReqMsg_User_AssetInfoQuery();
						reqAssetInfoQuery.setUserId(myAssetRequest.getUserId());
						reqAssetInfoQuery.setVersion("1.0.0");
						ResMsg_User_AssetInfoQuery resAssetInfoQuery = (ResMsg_User_AssetInfoQuery) appAssetBusinessService.assetInfoQuery(reqAssetInfoQuery);

						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resAssetInfoQuery.getResCode())) {

							/**
							 * private String mobile; //手机号码 private Double
							 * assetAmount; //账户总资产 private Double
							 * totalInvestEarnings; //累计收益 private Double
							 * investEarnings;   //当前收益 private Double
							 * totalBonus;//活动奖励 private Double availableBalance;
							 * //结算户账户可用余额 private Double dayInvestEarnings; //当日收益
							 * private Double jljCanWithdraw; ///奖励金账户可提现金额，我的奖励
							 * private Integer investNum; //我的投资（当前购买产品数） private
							 * int less7Days; //七天内产品到期的数目 private Integer
							 * processingNum;//当前用户处理中订单的数量(交易明细) private Integer
							 * bankCardCount; //银行卡张数
							 */
							myAssetResponse.setMobile(resAssetInfoQuery.getMobile());
							myAssetResponse.setAssetAmount(resAssetInfoQuery.getAssetAmount());
							myAssetResponse.setTotalInvestEarnings(resAssetInfoQuery.getTotalInvestEarnings());
							myAssetResponse.setInvestEarnings(resAssetInfoQuery.getInvestEarnings());
							myAssetResponse.setTotalBonus(resAssetInfoQuery.getTotalBonus());
							myAssetResponse.setAvailableBalance(resAssetInfoQuery.getAvailableBalance());
							myAssetResponse.setDayInvestEarnings(resAssetInfoQuery.getDayInvestEarnings());
							myAssetResponse.setJljCanWithdraw(resAssetInfoQuery.getJljCanWithdraw());
							myAssetResponse.setInvestNum(resAssetInfoQuery.getInvestNum());
							myAssetResponse.setLess7Days(resAssetInfoQuery.getLess7Days());
							myAssetResponse.setProcessingNum(resAssetInfoQuery.getProcessingNum());
							myAssetResponse.setBankCardCount(resAssetInfoQuery.getBankCardCount());
							myAssetResponse.setRedPacketNum(resAssetInfoQuery.getRedPacketNum());
						}
						else {
							throw new OpenException(resAssetInfoQuery.getResCode(),resAssetInfoQuery.getResMsg());
						}

						//查询回款卡和交易密码
						ReqMsg_User_InfoQuery reqUserInfoQuery = new ReqMsg_User_InfoQuery();
						reqUserInfoQuery.setUserId(myAssetRequest.getUserId());
						reqUserInfoQuery.setVersion("1.0.0");
						ResMsg_User_InfoQuery resUserInfoQuery = (ResMsg_User_InfoQuery) appAssetBusinessService.queryAccountBalance(reqUserInfoQuery);
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resUserInfoQuery.getResCode())) {
							//回款卡和交易密码
							if (resUserInfoQuery.getExcitPaypassword() == 2) {  //判断是否存在支付密码 1:有交易密码；2：无交易密码
								myAssetResponse.setHavePayPassword(false);
                            } else {
                            	myAssetResponse.setHavePayPassword(true);
                            }
                            if(resUserInfoQuery.getIsBindBank() == 2 ){ //判断是否存在回款卡
                            	myAssetResponse.setHaveFirstCard(false);
                            } else {
                            	myAssetResponse.setHaveFirstCard(true);
                            }
						}
						else {
							throw new OpenException(resUserInfoQuery.getResCode(),resUserInfoQuery.getResMsg());
						}
					}
				});
		}


	// 我的资产(回款路径只能回款到余额)
	@ResponseBody
	@RequestMapping("my_asset/v_1.0.2")
	public String myAsset_2(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, MyAssetRequest.class,
				new ControllerCallBack() {

					@Override
					public void doCheck(Request request, Response response) {
						MyAssetRequest myAssetRequest = (MyAssetRequest) request;
						Integer userId = myAssetRequest.getUserId();
						if (userId == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						MyAssetResponse myAssetResponse = (MyAssetResponse) response;
						MyAssetRequest myAssetRequest = (MyAssetRequest) request;

						// 查询
						ReqMsg_User_AssetInfoQuery  reqAssetInfoQuery = new ReqMsg_User_AssetInfoQuery();
						reqAssetInfoQuery.setUserId(myAssetRequest.getUserId());
						reqAssetInfoQuery.setVersion("1.0.0");
						ResMsg_User_AssetInfoQuery resAssetInfoQuery = (ResMsg_User_AssetInfoQuery) appAssetBusinessService.assetInfoQuery(reqAssetInfoQuery);

						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resAssetInfoQuery.getResCode())) {

							/**
							 * private String mobile; //手机号码 private Double
							 * assetAmount; //账户总资产 private Double
							 * totalInvestEarnings; //累计收益 private Double
							 * investEarnings;   //当前收益 private Double
							 * totalBonus;//活动奖励 private Double availableBalance;
							 * //结算户账户可用余额 private Double dayInvestEarnings; //当日收益
							 * private Double jljCanWithdraw; ///奖励金账户可提现金额，我的奖励
							 * private Integer investNum; //我的投资（当前购买产品数） private
							 * int less7Days; //七天内产品到期的数目 private Integer
							 * processingNum;//当前用户处理中订单的数量(交易明细) private Integer
							 * bankCardCount; //银行卡张数
							 */
							myAssetResponse.setMobile(resAssetInfoQuery.getMobile());
							myAssetResponse.setAssetAmount(resAssetInfoQuery.getAssetAmount());
							myAssetResponse.setTotalInvestEarnings(resAssetInfoQuery.getTotalInvestEarnings());
							myAssetResponse.setInvestEarnings(resAssetInfoQuery.getInvestEarnings());
							myAssetResponse.setTotalBonus(resAssetInfoQuery.getTotalBonus());
							myAssetResponse.setAvailableBalance(resAssetInfoQuery.getAvailableBalance());
							myAssetResponse.setDayInvestEarnings(resAssetInfoQuery.getDayInvestEarnings());
							myAssetResponse.setJljCanWithdraw(resAssetInfoQuery.getJljCanWithdraw());
							myAssetResponse.setInvestNum(resAssetInfoQuery.getInvestNum());
							myAssetResponse.setLess7Days(resAssetInfoQuery.getLess7Days());
							myAssetResponse.setProcessingNum(resAssetInfoQuery.getProcessingNum());
							myAssetResponse.setBankCardCount(resAssetInfoQuery.getBankCardCount());
							myAssetResponse.setRedPacketNum(resAssetInfoQuery.getRedPacketNum());
							myAssetResponse.setIsShowReturnPath(resAssetInfoQuery.getIsShowReturnPath());
						}
						else {
							throw new OpenException(resAssetInfoQuery.getResCode(),resAssetInfoQuery.getResMsg());
						}

						//查询回款卡和交易密码
						ReqMsg_User_InfoQuery reqUserInfoQuery = new ReqMsg_User_InfoQuery();
						reqUserInfoQuery.setUserId(myAssetRequest.getUserId());
						reqUserInfoQuery.setVersion("1.0.0");
						ResMsg_User_InfoQuery resUserInfoQuery = (ResMsg_User_InfoQuery) appAssetBusinessService.queryAccountBalance(reqUserInfoQuery);
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resUserInfoQuery.getResCode())) {
							//回款卡和交易密码
							if (resUserInfoQuery.getExcitPaypassword() == 2) {  //判断是否存在支付密码 1:有交易密码；2：无交易密码
								myAssetResponse.setHavePayPassword(false);
                            } else {
                            	myAssetResponse.setHavePayPassword(true);
                            }
                            if(resUserInfoQuery.getIsBindBank() == 2 ){ //判断是否存在回款卡
                            	myAssetResponse.setHaveFirstCard(false);
                            } else {
                            	myAssetResponse.setHaveFirstCard(true);
                            }
						}
						else {
							throw new OpenException(resUserInfoQuery.getResCode(),resUserInfoQuery.getResMsg());
						}
					}
				});
		}






	// 我的资产(存管改造)
	@ResponseBody
	@RequestMapping("my_asset/v_1.0.3")
	public String myAsset_3(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, MyAssetRequest.class,
				new ControllerCallBack() {

					@Override
					public void doCheck(Request request, Response response) {
						MyAssetRequest myAssetRequest = (MyAssetRequest) request;
						Integer userId = myAssetRequest.getUserId();
						if (userId == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						MyAssetResponse myAssetResponse = (MyAssetResponse) response;
						MyAssetRequest myAssetRequest = (MyAssetRequest) request;

						// 查询
						ReqMsg_User_AssetInfoQuery  reqAssetInfoQuery = new ReqMsg_User_AssetInfoQuery();
						reqAssetInfoQuery.setUserId(myAssetRequest.getUserId());
						reqAssetInfoQuery.setVersion("1.0.1");
						ResMsg_User_AssetInfoQuery resAssetInfoQuery = (ResMsg_User_AssetInfoQuery) appAssetBusinessService.assetInfoQuery(reqAssetInfoQuery);

						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resAssetInfoQuery.getResCode())) {
							myAssetResponse.setMobile(resAssetInfoQuery.getMobile());
							myAssetResponse.setAssetAmount(resAssetInfoQuery.getAssetAmount());
							myAssetResponse.setTotalInvestEarnings(resAssetInfoQuery.getTotalInvestEarnings());
							myAssetResponse.setInvestEarnings(resAssetInfoQuery.getInvestEarnings());
							myAssetResponse.setTotalBonus(resAssetInfoQuery.getTotalBonus());
							myAssetResponse.setAvailableBalance(resAssetInfoQuery.getAvailableBalance());
							myAssetResponse.setDayInvestEarnings(resAssetInfoQuery.getDayInvestEarnings());
							myAssetResponse.setJljCanWithdraw(resAssetInfoQuery.getJljCanWithdraw());
							myAssetResponse.setInvestNum(resAssetInfoQuery.getInvestNum());
							myAssetResponse.setLess7Days(resAssetInfoQuery.getLess7Days());
							myAssetResponse.setProcessingNum(resAssetInfoQuery.getProcessingNum());
							myAssetResponse.setBankCardCount(resAssetInfoQuery.getBankCardCount());
							myAssetResponse.setRedPacketNum(resAssetInfoQuery.getRedPacketNum());
							myAssetResponse.setIsShowReturnPath(resAssetInfoQuery.getIsShowReturnPath());
							myAssetResponse.setDepBalance(resAssetInfoQuery.getDepAvailableBalance());
							myAssetResponse.setDepAccountStatus(resAssetInfoQuery.getDepAccountStatus());
							myAssetResponse.setNoticeType(resAssetInfoQuery.getNoticeType());
							myAssetResponse.setRiskStatus(resAssetInfoQuery.getRiskStatus());
						}
						else {
							throw new OpenException(resAssetInfoQuery.getResCode(),resAssetInfoQuery.getResMsg());
						}

						//查询回款卡和交易密码
						ReqMsg_User_InfoQuery reqUserInfoQuery = new ReqMsg_User_InfoQuery();
						reqUserInfoQuery.setUserId(myAssetRequest.getUserId());
						reqUserInfoQuery.setVersion("1.0.0");
						ResMsg_User_InfoQuery resUserInfoQuery = (ResMsg_User_InfoQuery) appAssetBusinessService.queryAccountBalance(reqUserInfoQuery);
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resUserInfoQuery.getResCode())) {
							//回款卡和交易密码
							if (resUserInfoQuery.getExcitPaypassword() == 2) {  //判断是否存在支付密码 1:有交易密码；2：无交易密码
								myAssetResponse.setHavePayPassword(false);
                            } else {
                            	myAssetResponse.setHavePayPassword(true);
                            }
                            if(resUserInfoQuery.getIsBindBank() == 2 ){ //判断是否存在回款卡
                            	myAssetResponse.setHaveFirstCard(false);
                            } else {
                            	myAssetResponse.setHaveFirstCard(true);
                            }
						}
						else {
							throw new OpenException(resUserInfoQuery.getResCode(),resUserInfoQuery.getResMsg());
						}
					}
				});
		}


	/**
	 * 【账户余额】提现校验 是否设置交易密码，安全卡绑定
	 *
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
	@RequestMapping("check_before_withdraw/v_1.0.0")
	public String checkBeforeWithdraw(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, BalanceRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						BalanceRequest balanceRequest = (BalanceRequest) request;
						Integer userId = balanceRequest.getUserId();
						if (userId == null) {
							throw new OpenException(OpenMessageEnum.USER_ID_EMPTY.getCode(),OpenMessageEnum.USER_ID_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						BalanceResponse balanceResponse = (BalanceResponse) response;
						BalanceRequest balanceRequest = (BalanceRequest) request;
						// 查询
						ReqMsg_Invest_AccountBalance reqMsg = new ReqMsg_Invest_AccountBalance();
						reqMsg.setUserId(String.valueOf(balanceRequest.getUserId()));
						reqMsg.setVersion("1.0.0");
						try {
							ResMsg_Invest_AccountBalance res = (ResMsg_Invest_AccountBalance) appAssetBusinessService.accountBalance(reqMsg);
						    if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
						        balanceResponse.setSuccess(true);
	                            if ("YES".equals(res.getHavePayPassword())) {  //判断是否存在支付密码 YES:有交易密码；NO：无交易密码
	                                balanceResponse.setHavePayPassword(true);
	                            } else {
	                                balanceResponse.setHavePayPassword(false);
	                            }
	                            if("YES".equals(res.getStatus())){ //判断是否存在回款卡
	                                balanceResponse.setHaveFirstCard(true);
	                            } else {
	                                balanceResponse.setHaveFirstCard(false);
	                            }
	                            balanceResponse.setAvailableBalance(res.getAvailableBalance());
								balanceResponse.setDepAvailableBalance(res.getDepAvailableBalance());
						    } else {
						        balanceResponse.setSuccess(false);
						        throw new OpenException(res.getResCode(), res.getResMsg());
						    }
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new OpenException("999", e.getMessage());
                        }

					}
				});
	}

	// 投资收益列表
	@ResponseBody
	@RequestMapping("day_invest_earnings/v_1.0.0")
	public String dayInvestEarnings(HttpServletRequest req,
			HttpServletResponse resp) {
		return this.execute(req, DayInvestEarningsRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						/**
						 * private Integer userId; //用户ID
						 */
						DayInvestEarningsRequest dayInvestEarningsMoreRequest = (DayInvestEarningsRequest) request;
						Integer userId = dayInvestEarningsMoreRequest
								.getUserId();
						Integer pageIndex = dayInvestEarningsMoreRequest.getPageIndex();

						if (userId == null || pageIndex == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						DayInvestEarningsResponse dayInvestEarningsMoreResponse = (DayInvestEarningsResponse) response;
						DayInvestEarningsRequest dayInvestEarningsMoreRequest = (DayInvestEarningsRequest) request;

						// 查询数据
						ReqMsg_Invest_EarningsListQuery  reqDayInvestEarnings = new ReqMsg_Invest_EarningsListQuery();
						reqDayInvestEarnings.setVersion("1.0.0");
						reqDayInvestEarnings.setUserId(dayInvestEarningsMoreRequest.getUserId());
						reqDayInvestEarnings.setPageIndex(dayInvestEarningsMoreRequest.getPageIndex());
						reqDayInvestEarnings.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
						ResMsg_Invest_EarningsListQuery resDayInvestEarnings = (ResMsg_Invest_EarningsListQuery) appAssetBusinessService.dayInvestEarnings(reqDayInvestEarnings);
						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resDayInvestEarnings.getResCode())) {


							/**
							 *
							 private Integer totalCount; //总页数
							 *
							 * 以下循环： earningsDate 投资时间 必填 String amount 金额 必填 Double
							 * remark 备注 可选 String
							 *
							 * private List<InvestEarnings> investEarnings;
							 */
							List<InvestEarnings> investEarningList= new  ArrayList<InvestEarnings>();
							if (resDayInvestEarnings.getInvestEarnings() != null) {
								for (HashMap<String, Object> obj : resDayInvestEarnings.getInvestEarnings()) {
									InvestEarnings investEarnings = new InvestEarnings();

									investEarnings.setEarningsDate(DateUtil.formatDateTime((Date) obj.get("earningsDate"), DateUtil.DATE_FORMAT));
									investEarnings.setAmount((Double)obj.get("amount"));
									investEarnings.setRemark("收益");
									investEarningList.add(investEarnings);
								}
							}

							dayInvestEarningsMoreResponse.setTotalPage(resDayInvestEarnings.getTotalCount());
							dayInvestEarningsMoreResponse.setInvestEarnings(investEarningList);

						}
						else {
							throw new OpenException(resDayInvestEarnings.getResCode(),resDayInvestEarnings.getResMsg());
						}

					}
				});
	}

	// 奖励金明细
	@ResponseBody
	@RequestMapping("my_bonus/v_1.0.0")
	public String myBonus(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, MyBonusRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						/**
						 * private Integer userId; //用户ID
						 */
						MyBonusRequest myBonusRequest = (MyBonusRequest) request;
						Integer userId = myBonusRequest.getUserId();
						Integer pageIndex = myBonusRequest.getPageIndex();
						if (userId == null || pageIndex == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						MyBonusResponse myBonusResponse = (MyBonusResponse) response;
						MyBonusRequest myBonusRequest = (MyBonusRequest) request;
						// 查询奖励金明细列表数据
						// 查询数据
						ReqMsg_Bonus_RecommendBonusListQuery reqBonus = new ReqMsg_Bonus_RecommendBonusListQuery();
						reqBonus.setVersion("1.0.0");
						reqBonus.setUserId(myBonusRequest.getUserId());
						reqBonus.setPageIndex(myBonusRequest.getPageIndex());
						reqBonus.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
						ResMsg_Bonus_RecommendBonusListQuery resBonus = (ResMsg_Bonus_RecommendBonusListQuery) appAssetBusinessService.myBonus(reqBonus);
						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resBonus.getResCode())) {
							List<Bonuss> bonussesList= new  ArrayList<Bonuss>();
							/**
							 * 以下循环： earningsDate 投资时间 必填 Date amount 金额 必填 Double
							 * remark 备注 可选 String private List<Bonuss> bonuss;
							 */
							if (resBonus.getBonuss() != null) {
								for (HashMap<String, Object> obj : resBonus.getBonuss()) {
									Bonuss bonuss = new Bonuss();
									bonuss.setEarningsDate(DateUtil.formatDateTime((Date) obj.get("earningsDate"), DateUtil.DATE_FORMAT));
									bonuss.setAmount((Double)obj.get("amount"));
									String bonusType = (String)obj.get("bonusType");
									String note = (String)obj.get("note");
									bonuss.setDetail((String)obj.get("detail"));
									if (Constants.BONUS_TYPE_ACTIVITY.equals(bonusType) ) {
										if (note == null || "".equals(note)) {
											bonuss.setRemark("活动奖励");
										} else {
											bonuss.setRemark(note);
										}
									} else if (Constants.BONUS_TYPE_BONUS_WITHDRAW.equals(bonusType)) {
										bonuss.setRemark("提现到银行卡");
									} else if (Constants.BONUS_TYPE_DEP_FILL_INTEREST.equals(bonusType)) {
										bonuss.setRemark("奖励金返还");
									} else if (Constants.BONUS_TYPE_RECOMMEND.equals(bonusType)) {
										bonuss.setRemark("推荐奖励");
									} else if (Constants.BONUS_TYPE_INTEREST_TICKET.equals(bonusType)) {
										bonuss.setRemark("加息收益");
									} else if (org.apache.commons.lang.StringUtils.isEmpty(bonusType)
											&& ((Double)obj.get("amount")) < 0) {
										bonuss.setRemark("转出");
									}
									bonussesList.add(bonuss);
								}
							}

							myBonusResponse.setTotalPage(resBonus.getTotalCount());
							myBonusResponse.setBonus(resBonus.getBonus());
							if ("true".equals(resBonus.getHaveSpecial())) {
								myBonusResponse.setHaveSpecial(true);
							}else {
								myBonusResponse.setHaveSpecial(false);
							}
							myBonusResponse.setSpecialBonusAmount(resBonus.getSpecialBonusAmount());
							myBonusResponse.setBonuss(bonussesList);

						}
						else {
							throw new OpenException(resBonus.getResCode(),resBonus.getResMsg());
						}


					}
				});
	}

	// 奖励金转出到余额
	@ResponseBody
	@RequestMapping("my_bonus_to_jsh/v_1.0.0")
	public String myBonusToJSH(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, MyBonusToJSHRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						/**
						 * private Integer userId; //用户ID
						 */
						MyBonusToJSHRequest myBonusToJSHRequest = (MyBonusToJSHRequest) request;
						Integer userId = myBonusToJSHRequest.getUserId();
						if (userId == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						MyBonusToJSHRequest myBonusToJSHRequest = (MyBonusToJSHRequest) request;

						//参数设置
						ReqMsg_Bonus_BonusToJSH reqBonusToJSH = new ReqMsg_Bonus_BonusToJSH();
						reqBonusToJSH.setVersion("1.0.0");
						reqBonusToJSH.setUserId(myBonusToJSHRequest.getUserId());
						//转出操作
						ResMsg_Bonus_BonusToJSH resBonustBonusToJSH = (ResMsg_Bonus_BonusToJSH) appAssetBusinessService.myBonusToJSH(reqBonusToJSH);
						//返回数据处理
						if(!ConstantUtil.BSRESCODE_SUCCESS.equals(resBonustBonusToJSH.getResCode())) {
							throw new OpenException(resBonustBonusToJSH.getResCode(),resBonustBonusToJSH.getResMsg());
						}
					}
				});
	}

	//我的投资
	@ResponseBody
	@RequestMapping("my_investment/v_1.0.0")
	public String myInvestment(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, MyInvestmentRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						/**
						 * private Integer userId; //用户ID
						 */
						MyInvestmentRequest myInvestmentRequest = (MyInvestmentRequest) request;
						Integer userId = myInvestmentRequest.getUserId();
						Integer pageIndex = myInvestmentRequest.getPageIndex();
						if (userId == null || pageIndex==null ) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						MyInvestmentResponse myInvestmentResponse = (MyInvestmentResponse) response;
						MyInvestmentRequest myInvestmentRequest = (MyInvestmentRequest) request;

						//参数设置
						ReqMsg_Invest_InvestListQuery reqInvestListQuery = new ReqMsg_Invest_InvestListQuery();
						reqInvestListQuery.setVersion("1.0.0");
						reqInvestListQuery.setUserId(myInvestmentRequest.getUserId());
						reqInvestListQuery.setStartPage(myInvestmentRequest.getPageIndex()*Constants.EXCHANGE_PAGE_SIZE);
						reqInvestListQuery.setPageSize(Constants.EXCHANGE_PAGE_SIZE_TRADING_DETAIL);
						//查询操作
						ResMsg_Invest_InvestListQuery resInvestListQuery = (ResMsg_Invest_InvestListQuery) appAssetBusinessService.myInvestment(reqInvestListQuery);

						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resInvestListQuery.getResCode())) {
							/**
							 * //投资金额 private Double balance;
							 * //投资开始日期 private String interestBeginDate;
							 * //投资结束日期 private String investEndTime;
							 * //投资剩余天数 private Integer investDay;
							 * //现利息基数 private Double productRate;
							 * //投资状态 private Integer status;
							 */
							List<InvestAccount> investAccountList= new  ArrayList<InvestAccount>();
							if (resInvestListQuery.getValueList() != null) {
								for (HashMap<String, Object> obj : resInvestListQuery.getValueList()) {
									InvestAccount investAccount = new InvestAccount();
									investAccount.setInvestId((Integer)obj.get("id"));
									investAccount.setBalance((Double)obj.get("balance"));
									investAccount.setInterestBeginDate(DateUtil.formatDateTime((Date)obj.get("interestBeginDate"), "yyyy.MM.dd"));
									investAccount.setInvestEndTime(DateUtil.formatDateTime((Date)obj.get("investEndTime"), "yyyy.MM.dd"));
									investAccount.setInvestDay((Integer)obj.get("investDay"));
									investAccount.setProductRate((Double)obj.get("productRate"));
									investAccount.setStatus((Integer)obj.get("status"));
									Double yield = 0d;
									Integer term = (Integer)obj.get("term");
									if(term == 12) {
										yield = (Double)obj.get("balance") * (Double)obj.get("productRate") / 100;
									}
									else {
										if(term < 0) {
											term = Math.abs(term);
											yield = (Double)obj.get("balance") * (Double)obj.get("productRate") * term / 36500;
										}
										else {
											yield = (Double)obj.get("balance") * (Double)obj.get("productRate") * term * 30 / 36500;
										}
									}
									investAccount.setYield(yield);
									investAccountList.add(investAccount);
								}
							}
							myInvestmentResponse.setTotalPage(resInvestListQuery.getTotal());
							myInvestmentResponse.setProcessingNum(resInvestListQuery.getProcessingNum());
							myInvestmentResponse.setInvestAccounts(investAccountList);
						}
						else {
							throw new OpenException(resInvestListQuery.getResCode(),resInvestListQuery.getResMsg());
						}

					}
				});
	}

	//我的投资
	@ResponseBody
	@RequestMapping("my_investment/v_1.0.1")
	public String myInvestment_1(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, MyInvestmentRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						/**
						 * private Integer userId; //用户ID
						 */
						MyInvestmentRequest myInvestmentRequest = (MyInvestmentRequest) request;
						Integer userId = myInvestmentRequest.getUserId();
						Integer pageIndex = myInvestmentRequest.getPageIndex();
						if (userId == null || pageIndex==null ) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						MyInvestmentResponse myInvestmentResponse = (MyInvestmentResponse) response;
						MyInvestmentRequest myInvestmentRequest = (MyInvestmentRequest) request;

						//参数设置
						ReqMsg_Invest_InvestListQuery reqInvestListQuery = new ReqMsg_Invest_InvestListQuery();
						reqInvestListQuery.setVersion("1.0.1");
						reqInvestListQuery.setUserId(myInvestmentRequest.getUserId());
						reqInvestListQuery.setStartPage(myInvestmentRequest.getPageIndex()*Constants.EXCHANGE_PAGE_SIZE_TRADING_DETAIL);
						reqInvestListQuery.setPageSize(Constants.EXCHANGE_PAGE_SIZE_TRADING_DETAIL);
						//查询操作
						ResMsg_Invest_InvestListQuery resInvestListQuery = (ResMsg_Invest_InvestListQuery) appAssetBusinessService.myInvestment(reqInvestListQuery);

						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resInvestListQuery.getResCode())) {
							/**
							 * //投资金额 private Double balance;
							 * //投资开始日期 private String interestBeginDate;
							 * //投资结束日期 private String investEndTime;
							 * //投资剩余天数 private Integer investDay;
							 * //现利息基数 private Double productRate;
							 * //投资状态 private Integer status;
							 * //产品开始时间 startTime
							 * //红包抵扣 redAmount
							 * //加息幅度ticketApr
							 * //加息收益interestAmount
							 * //计划管理页面《出借服务协议》（no）与《授权委托书》（yes）协议名字显示判断 powerAttorneyFlag（no、yes）
							 */
							List<InvestAccount> investAccountList= new  ArrayList<InvestAccount>();
							if (resInvestListQuery.getValueList() != null) {
								for (HashMap<String, Object> obj : resInvestListQuery.getValueList()) {
									InvestAccount investAccount = new InvestAccount();
									investAccount.setInvestId((Integer)obj.get("id"));
									investAccount.setBalance((Double)obj.get("balance"));
									investAccount.setInterestBeginDate(DateUtil.formatDateTime((Date)obj.get("interestBeginDate"), "yyyy-MM-dd"));
									investAccount.setInvestEndTime(DateUtil.formatDateTime((Date)obj.get("investEndTime"), "yyyy-MM-dd"));
									investAccount.setInvestDay((Integer)obj.get("investDay"));
									investAccount.setProductRate((Double)obj.get("productRate"));
									investAccount.setStatus((Integer)obj.get("status"));
									Double yield = 0d;
									Integer term = (Integer)obj.get("term");
									if(term == 12) {
										yield = (Double)obj.get("balance") * (Double)obj.get("productRate") / 100;
									}
									else {
										if(term < 0) {
											term = Math.abs(term);
											yield = (Double)obj.get("balance") * (Double)obj.get("productRate") * term / 36500;
										}
										else {
											yield = (Double)obj.get("balance") * (Double)obj.get("productRate") * term * 30 / 36500;
										}
									}
									investAccount.setYield(yield);
									if(obj.get("redAmount") != null) {
										investAccount.setRedAmount((Double)obj.get("redAmount"));
									}
									investAccount.setStartTime(DateUtil.formatDateTime((Date)obj.get("startTime"), "yyyy-MM-dd"));
									investAccount.setProductId((Integer)obj.get("productId"));
									investAccount.setProductName((String)obj.get("productName"));
									investAccount.setPropertyType((String)obj.get("propertyType"));
									investAccount.setNow(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd"));
									investAccount.setPowerAttorneyFlag((String)obj.get("powerAttorneyFlag"));
									investAccount.setTicketApr((Double)obj.get("ticketApr"));
									investAccount.setInterestAmount((Double)obj.get("interestAmount"));
									investAccountList.add(investAccount);
								}
							}
							myInvestmentResponse.setTotalPage(resInvestListQuery.getTotal());
							myInvestmentResponse.setInvestAccounts(investAccountList);
						}
						else {
							throw new OpenException(resInvestListQuery.getResCode(),resInvestListQuery.getResMsg());
						}

					}
				});
	}




	//我的投资-委托计划
	@ResponseBody
	@RequestMapping("my_investment_entrust/v_1.0.1")
	public String myInvestmentEntrust_1(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, MyInvestmentEntrustRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						/**
						 * private Integer userId; //用户ID
						 */
						MyInvestmentEntrustRequest myInvestmentRequest = (MyInvestmentEntrustRequest) request;
						Integer userId = myInvestmentRequest.getUserId();

						Integer pageIndexEntrust = myInvestmentRequest.getPageIndexEntrust();
						if (userId == null || pageIndexEntrust == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						MyInvestmentEntrustResponse myInvestmentResponse = (MyInvestmentEntrustResponse) response;
						MyInvestmentEntrustRequest myInvestmentRequest = (MyInvestmentEntrustRequest) request;

						//参数设置
						ReqMsg_Invest_InvestEntrustListQuery reqInvestListQuery = new ReqMsg_Invest_InvestEntrustListQuery();
						reqInvestListQuery.setVersion("1.0.0");
						reqInvestListQuery.setUserId(myInvestmentRequest.getUserId());


						reqInvestListQuery.setStartPageEntrust(myInvestmentRequest.getPageIndexEntrust()+1);
						reqInvestListQuery.setPageSizeEntrust(Constants.EXCHANGE_PAGE_SIZE_TRADING_DETAIL);


						//查询操作
						ResMsg_Invest_InvestEntrustListQuery resInvestListQuery = (ResMsg_Invest_InvestEntrustListQuery) appAssetBusinessService.myInvestmentEntrust(reqInvestListQuery);

						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resInvestListQuery.getResCode())) {
							/**
							 * //投资金额 private Double balance;
							 * //投资开始日期 private String interestBeginDate;
							 * //投资结束日期 private String investEndTime;
							 * //投资剩余天数 private Integer investDay;
							 * //现利息基数 private Double productRate;
							 * //投资状态 private Integer status;
							 * //产品开始时间 startTime
							 * //红包抵扣 redAmount
							 */

							List<InvestAccount> investEntrustAccountList= new  ArrayList<InvestAccount>();
							if (resInvestListQuery.getInvestAccountsEntrust() != null) {
								for (HashMap<String, Object> obj : resInvestListQuery.getInvestAccountsEntrust()) {

									InvestAccount investAccount = new InvestAccount();
									investAccount.setProductName((String)obj.get("productName"));
									investAccount.setProductRate((Double)obj.get("productRate"));
									investAccount.setEntrustBalance((Double)obj.get("openBalance"));
									Integer term = (Integer)obj.get("term");
									investAccount.setTerm(String.valueOf(term));
									investAccount.setOpenTime(DateUtil.formatDateTime((Date)obj.get("openTime"), "yyyy-MM-dd"));
									investAccount.setBalance((Double)obj.get("balance"));
									//investAccount.setReturnBalance((Double)obj.get("returnBalance"));
									investAccount.setEntrustStatus((String)obj.get("entrustStatus"));
									investAccount.setInvestId((Integer)obj.get("id"));
									investAccount.setNow(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd"));
									investAccount.setReturnType((String)obj.get("returnType"));
									investAccount.setProductId((Integer)obj.get("productId"));

									investAccount.setReturnAmount((Double)obj.get("returnAmount"));
									investAccount.setReceiveInterestAmount((Double)obj.get("receiveInterestAmount"));
									investAccount.setReceivePrincipalAmount((Double)obj.get("receivePrincipalAmount"));

									investEntrustAccountList.add(investAccount);

								}
							}
							myInvestmentResponse.setTotalPageEntrust(resInvestListQuery.getTotalPageEntrust());
							myInvestmentResponse.setInvestAccounts(investEntrustAccountList);

						}
						else {
							throw new OpenException(resInvestListQuery.getResCode(),resInvestListQuery.getResMsg());
						}

					}
				});
	}



	//我的投资-委托计划
	@ResponseBody
	@RequestMapping("my_investment_entrust/v_1.0.0")
	public String myInvestmentEntrust(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, MyInvestmentEntrustRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						/**
						 * private Integer userId; //用户ID
						 */
						MyInvestmentEntrustRequest myInvestmentRequest = (MyInvestmentEntrustRequest) request;
						Integer userId = myInvestmentRequest.getUserId();

						Integer pageIndexEntrust = myInvestmentRequest.getPageIndexEntrust();
						if (userId == null || pageIndexEntrust == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						MyInvestmentEntrustResponse myInvestmentResponse = (MyInvestmentEntrustResponse) response;
						MyInvestmentEntrustRequest myInvestmentRequest = (MyInvestmentEntrustRequest) request;

						//参数设置
						ReqMsg_Invest_InvestEntrustListQuery reqInvestListQuery = new ReqMsg_Invest_InvestEntrustListQuery();
						reqInvestListQuery.setVersion("1.0.0");
						reqInvestListQuery.setUserId(myInvestmentRequest.getUserId());


						reqInvestListQuery.setStartPageEntrust(myInvestmentRequest.getPageIndexEntrust()+1);
						reqInvestListQuery.setPageSizeEntrust(Constants.EXCHANGE_PAGE_SIZE_TRADING_DETAIL);


						//查询操作
						ResMsg_Invest_InvestEntrustListQuery resInvestListQuery = (ResMsg_Invest_InvestEntrustListQuery) appAssetBusinessService.myInvestmentEntrust(reqInvestListQuery);

						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resInvestListQuery.getResCode())) {
							/**
							 * //投资金额 private Double balance;
							 * //投资开始日期 private String interestBeginDate;
							 * //投资结束日期 private String investEndTime;
							 * //投资剩余天数 private Integer investDay;
							 * //现利息基数 private Double productRate;
							 * //投资状态 private Integer status;
							 * //产品开始时间 startTime
							 * //红包抵扣 redAmount
							 */

							List<InvestAccount> investEntrustAccountList= new  ArrayList<InvestAccount>();
							if (resInvestListQuery.getInvestAccountsEntrust() != null) {
								for (HashMap<String, Object> obj : resInvestListQuery.getInvestAccountsEntrust()) {

									InvestAccount investAccount = new InvestAccount();

									investAccount.setProductName((String)obj.get("productName"));
									investAccount.setProductRate((Double)obj.get("productRate"));
									investAccount.setEntrustBalance((Double)obj.get("openBalance"));
									Integer term = (Integer)obj.get("term");
									investAccount.setTerm(String.valueOf(term));
									investAccount.setOpenTime(DateUtil.formatDateTime((Date)obj.get("openTime"), "yyyy-MM-dd"));
									investAccount.setBalance((Double)obj.get("balance"));
									investAccount.setReturnBalance((Double)obj.get("returnBalance"));
									//investAccount.setInterestBeginDate(DateUtil.formatDateTime((Date)obj.get("interestBeginDate"), "yyyy-MM-dd"));
									investAccount.setEntrustStatus((String)obj.get("entrustStatus"));
									investAccount.setInvestId((Integer)obj.get("id"));
									investAccount.setNow(DateUtil.formatDateTime(new Date(), "yyyy-MM-dd"));

									investAccount.setReturnType((String)obj.get("returnType"));
									investAccount.setProductId((Integer)obj.get("productId"));


									investEntrustAccountList.add(investAccount);

								}
							}
							myInvestmentResponse.setTotalPageEntrust(resInvestListQuery.getTotalPageEntrust());
							myInvestmentResponse.setInvestAccounts(investEntrustAccountList);

						}
						else {
							throw new OpenException(resInvestListQuery.getResCode(),resInvestListQuery.getResMsg());
						}

					}
				});
	}


	//债权明细
	@ResponseBody
	@RequestMapping("myLoadMatch/v_1.0.0")
	public String myLoadMatch(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, MyLoadMatchRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						/**
						 * private Integer userId; //用户ID
						 */
						MyLoadMatchRequest myLoadMatchRequest = (MyLoadMatchRequest) request;
						Integer userId = myLoadMatchRequest.getUserId();
						Integer subAccountId = myLoadMatchRequest.getSubAccountId();
						Integer pageIndex = myLoadMatchRequest.getPageIndex();
						Integer productId = myLoadMatchRequest.getProductId();
						if (subAccountId == null || pageIndex == null || userId == null || productId == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						MyLoadMatchResponse myLoadMatchResponse = (MyLoadMatchResponse) response;
						MyLoadMatchRequest myLoadMatchRequest = (MyLoadMatchRequest) request;

						//参数设置
						ReqMsg_Invest_LoadMatch loadMatchReq = new ReqMsg_Invest_LoadMatch();
						loadMatchReq.setVersion("1.0.0");
						loadMatchReq.setUserId(myLoadMatchRequest.getUserId());
						loadMatchReq.setSubAccountId(myLoadMatchRequest.getSubAccountId());
						loadMatchReq.setProductId(myLoadMatchRequest.getProductId());
						loadMatchReq.setStart(myLoadMatchRequest.getPageIndex()*Constants.EXCHANGE_PAGE_SIZE_TRADING_DETAIL);
						loadMatchReq.setPageSize(Constants.EXCHANGE_PAGE_SIZE_TRADING_DETAIL);
						//查询操作
						ResMsg_Invest_LoadMatch loadMatchRes = (ResMsg_Invest_LoadMatch) appAssetBusinessService.myloadMatch(loadMatchReq);

						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(loadMatchRes.getResCode())) {
							List<LoadMatchVO> dataList = new ArrayList<LoadMatchVO>();
							if (loadMatchRes.getDataList() != null) {
								DecimalFormat df = new DecimalFormat("0.00");
								for (HashMap<String, Object> obj : loadMatchRes.getDataList()) {
									LoadMatchVO vo = new LoadMatchVO();
									String borrowerName = (String)obj.get("borrowerName");
									Integer length = borrowerName.length();
									borrowerName = borrowerName.substring(0, 1);
									for(int i=1;i<length;i++) {
										borrowerName += "*";
									}
									vo.setAmount(df.format((Double)obj.get("amount")));
									vo.setBorrowerName(borrowerName);
									vo.setRepayStatus((String)obj.get("repayStatus"));
									dataList.add(vo);
								}
							}
							myLoadMatchResponse.setDataList(dataList);
							myLoadMatchResponse.setTotalPage(loadMatchRes.getTotal());

						}
						else {
							throw new OpenException(loadMatchRes.getResCode(),loadMatchRes.getResMsg());
						}

					}
				});
	}


	//债权明细(添加委托计划债权)
	@ResponseBody
	@RequestMapping("myLoadMatch/v_1.0.1")
	public String myLoadMatch_1(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, MyLoadMatchRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						/**
						 * private Integer userId; //用户ID
						 */
						MyLoadMatchRequest myLoadMatchRequest = (MyLoadMatchRequest) request;
						Integer userId = myLoadMatchRequest.getUserId();
						Integer subAccountId = myLoadMatchRequest.getSubAccountId();
						Integer pageIndex = myLoadMatchRequest.getPageIndex();
						Integer productId = myLoadMatchRequest.getProductId();
						if (subAccountId == null || pageIndex == null || userId == null || productId == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						MyLoadMatchResponse myLoadMatchResponse = (MyLoadMatchResponse) response;
						MyLoadMatchRequest myLoadMatchRequest = (MyLoadMatchRequest) request;

						//参数设置
						ReqMsg_Invest_LoadMatch loadMatchReq = new ReqMsg_Invest_LoadMatch();
						loadMatchReq.setVersion("1.0.0");
						loadMatchReq.setUserId(myLoadMatchRequest.getUserId());
						loadMatchReq.setSubAccountId(myLoadMatchRequest.getSubAccountId());
						loadMatchReq.setProductId(myLoadMatchRequest.getProductId());
						loadMatchReq.setStart(myLoadMatchRequest.getPageIndex()*Constants.EXCHANGE_PAGE_SIZE_TRADING_DETAIL);
						loadMatchReq.setPageSize(Constants.EXCHANGE_PAGE_SIZE_TRADING_DETAIL);
						//查询操作
						ResMsg_Invest_LoadMatch loadMatchRes = (ResMsg_Invest_LoadMatch) appAssetBusinessService.myloadMatch(loadMatchReq);

						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(loadMatchRes.getResCode())) {

							if (Constants.PRODUCT_PROPERTY_SYMBOL_ZAN.equals(loadMatchRes.getPropertySymbol())) {
								List<LoadMatchVO> dataList = new ArrayList<LoadMatchVO>();
								if (loadMatchRes.getDataList() != null) {
									DecimalFormat df = new DecimalFormat("0.00");
									for (HashMap<String, Object> obj : loadMatchRes.getDataList()) {
										LoadMatchVO vo = new LoadMatchVO();
										String borrowerName = (String)obj.get("userName");
										Integer length = borrowerName.length();
										borrowerName = borrowerName.substring(0, 1);
										for(int i=1;i<length;i++) {
											borrowerName += "*";
										}
										vo.setAmount(df.format((Double)obj.get("approveAmount")));
										vo.setBorrowerName(borrowerName);
										vo.setRepayStatus((String)obj.get("status"));

										vo.setPeriod((Integer)obj.get("period"));
										vo.setRepayedPeriodCount((Integer)obj.get("repayedPeriodCount"));
										dataList.add(vo);
									}
								}
								myLoadMatchResponse.setDataList(dataList);
								myLoadMatchResponse.setTotalPage(loadMatchRes.getTotal());
							}else {
								List<LoadMatchVO> dataList = new ArrayList<LoadMatchVO>();
								if (loadMatchRes.getDataList() != null) {
									DecimalFormat df = new DecimalFormat("0.00");
									for (HashMap<String, Object> obj : loadMatchRes.getDataList()) {
										LoadMatchVO vo = new LoadMatchVO();
										String borrowerName = (String)obj.get("borrowerName");
										Integer length = borrowerName.length();
										borrowerName = borrowerName.substring(0, 1);
										for(int i=1;i<length;i++) {
											borrowerName += "*";
										}
										vo.setAmount(df.format((Double)obj.get("amount")));
										vo.setBorrowerName(borrowerName);
										vo.setRepayStatus((String)obj.get("repayStatus"));
										dataList.add(vo);
									}
								}
								myLoadMatchResponse.setDataList(dataList);
								myLoadMatchResponse.setTotalPage(loadMatchRes.getTotal());
							}


						}
						else {
							throw new OpenException(loadMatchRes.getResCode(),loadMatchRes.getResMsg());
						}

					}
				});
	}

	//债权关系显示时间
	@ResponseBody
	@RequestMapping("loadMatchTime/v_1.0.0")
	public String loadMatchTime(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, LoadMatchTimeRequest.class, new ControllerCallBack() {

			@Override
			public void doOperation(Request request, Response response) {
				LoadMatchTimeResponse timeResp = (LoadMatchTimeResponse) response;
				ReqMsg_Invest_SysLoadMatchTime reqLoadMatchTime = new ReqMsg_Invest_SysLoadMatchTime();
				reqLoadMatchTime.setVersion("1.0.0");
				ResMsg_Invest_SysLoadMatchTime resLoadMatchTime = appAssetBusinessService.loadMatchTime(reqLoadMatchTime);
				timeResp.setLoadMatchTime(resLoadMatchTime.getLoadMatchTime());
			}
		});
	}

	// 交易明细
	@ResponseBody
	@RequestMapping("trading_detail/v_1.0.0")
	public String tradingDetail(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, TradingDetailRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						log.info("交易明细列表v_1.0.0校验开始");
						/**
						 * private Integer userId; //用户ID
						 */
						TradingDetailRequest tradingDetailRequest = (TradingDetailRequest) request;
						Integer userId = tradingDetailRequest.getUserId();
						Integer pageIndex = tradingDetailRequest.getPageIndex();
						if (userId == null || pageIndex ==null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						TradingDetailResponse tradingDetaiResponse = (TradingDetailResponse) response;
						TradingDetailRequest tradingDetailRequest = (TradingDetailRequest) request;

						//参数设置
						ReqMsg_TransDetail_QueryByUserId reqTransDetail = new ReqMsg_TransDetail_QueryByUserId();
						reqTransDetail.setVersion("1.0.0");
						reqTransDetail.setUserId(tradingDetailRequest.getUserId());
						reqTransDetail.setPageIndex(tradingDetailRequest.getPageIndex());
						reqTransDetail.setPageSize(Constants.EXCHANGE_PAGE_SIZE_TRADING_DETAIL);
						log.info("交易明细列表v_1.0.0 发起请求，用户 {}", reqTransDetail.getUserId());
						//查询操作
						ResMsg_TransDetail_QueryByUserId resTransDetail = (ResMsg_TransDetail_QueryByUserId) appAssetBusinessService.tradingDetail(reqTransDetail);

						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resTransDetail.getResCode())) {

							List<TradingDetail> tradingDetailList = new ArrayList<TradingDetail>();
							if (resTransDetail.getTransPrincipals() != null) {
								for (HashMap<String, Object> obj : resTransDetail.getTransPrincipals()) {

									/**
									 * private String transName; //交易类型名称
									 * private String  transTime; //交易时间 "yyyy-MM-dd HH:mm"
									 * private Double transAmount; //交易金额
									 * private String transStatus; //交易状态
									 */
									TradingDetail tradingDetail = new TradingDetail();
									tradingDetail.setTransName((String)obj.get("transName"));
									tradingDetail.setTransTime(DateUtil.formatDateTime((Date)obj.get("transTime"), "yyyy-MM-dd HH:mm"));
									tradingDetail.setTransAmount((Double)obj.get("transAmount"));
									String statusString = (String)obj.get("transStatus");
									if ("成功".equals(statusString)) {
										tradingDetail.setTransStatus("交易成功");
									}else {
										tradingDetail.setTransStatus((String)obj.get("transStatus"));
									}

									tradingDetailList.add(tradingDetail);
								}
							}
							tradingDetaiResponse.setTotalPage(resTransDetail.getTotalCount());
							tradingDetaiResponse.setTradingDetailList(tradingDetailList);

						}
						else {
							throw new OpenException(resTransDetail.getResCode(),resTransDetail.getResMsg());
						}

					}
				});
	}

	// 交易明细
	@ResponseBody
	@RequestMapping("trading_detail/v_1.0.1")
	public String tradingDetail_1(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, TradingDetailRequest.class,
			new ControllerCallBack() {
				@Override
				public void doCheck(Request request, Response response) {
					/**
					 * private Integer userId; //用户ID
					 */
					log.info("交易明细列表v_1.0.1校验开始");
					TradingDetailRequest tradingDetailRequest = (TradingDetailRequest) request;
					Integer userId = tradingDetailRequest.getUserId();
					Integer pageIndex = tradingDetailRequest.getPageIndex();
					if (userId == null || pageIndex ==null) {
						throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
					}
				}

				@Override
				public void doOperation(Request request, Response response) {
					TradingDetailResponse tradingDetaiResponse = (TradingDetailResponse) response;
					TradingDetailRequest tradingDetailRequest = (TradingDetailRequest) request;

					//参数设置
					ReqMsg_TransDetail_QueryByUserId reqTransDetail = new ReqMsg_TransDetail_QueryByUserId();
					reqTransDetail.setVersion("1.0.1");
					reqTransDetail.setUserId(tradingDetailRequest.getUserId());
					reqTransDetail.setPageIndex(tradingDetailRequest.getPageIndex());
					reqTransDetail.setPageSize(Constants.EXCHANGE_PAGE_SIZE_TRADING_DETAIL);
					log.info("交易明细列表v_1.0.1发起请求，用户 {}", reqTransDetail.getUserId());
					//查询操作
					ResMsg_TransDetail_QueryByUserId resTransDetail = (ResMsg_TransDetail_QueryByUserId) appAssetBusinessService.tradingDetail(reqTransDetail);

					//返回数据处理
					if(ConstantUtil.BSRESCODE_SUCCESS.equals(resTransDetail.getResCode())) {

						List<TradingDetail> tradingDetailList = new ArrayList<TradingDetail>();
						if (resTransDetail.getTransPrincipals() != null) {
							for (HashMap<String, Object> obj : resTransDetail.getTransPrincipals()) {

								/**
								 * private String transType; //交易类型
								 * private String transName; //交易类型名称
								 * private String  transTime; //交易时间 "yyyy-MM-dd HH:mm"
								 * private Double transAmount; //交易金额
								 * private String transStatus; //交易状态
								 */
								TradingDetail tradingDetail = new TradingDetail();
								tradingDetail.setTransName((String)obj.get("transName"));
								tradingDetail.setTransTime(DateUtil.formatDateTime((Date)obj.get("transTime"), "yyyy-MM-dd HH:mm"));
								tradingDetail.setTransAmount((Double)obj.get("transAmount"));
								tradingDetail.setTransType((String) obj.get("transType"));
								String statusString = (String)obj.get("transStatus");
								if ("成功".equals(statusString)) {
									tradingDetail.setTransStatus("交易成功");
								}else {
									tradingDetail.setTransStatus((String)obj.get("transStatus"));
								}

								tradingDetailList.add(tradingDetail);
							}
						}
						tradingDetaiResponse.setTotalPage(resTransDetail.getTotalCount());
						tradingDetaiResponse.setTradingDetailList(tradingDetailList);

					}
					else {
						throw new OpenException(resTransDetail.getResCode(),resTransDetail.getResMsg());
					}

				}
			});
	}

	/**
	 * 交易明细中-回款分期明细
	 * @param req
	 * @param resp
     * @return
     */
	@ResponseBody
	@RequestMapping("payment_stage_details/v_1.0.0")
	public String paymentStageDetails(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, PaymentStageDetailsRequest.class, new ControllerCallBack() {
			@Override
			public void doCheck(Request request, Response response) {
				PaymentStageDetailsRequest paymentStageDetailsRequest = (PaymentStageDetailsRequest) request;
				Integer userId = paymentStageDetailsRequest.getUserId();
				String time = paymentStageDetailsRequest.getTime();
				if (userId == null || StringUtils.isEmpty(time)) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				PaymentStageDetailsResponse paymentStageDetailsResponse = (PaymentStageDetailsResponse) response;
				PaymentStageDetailsRequest paymentStageDetailsRequest = (PaymentStageDetailsRequest) request;

				//参数设置
				ReqMsg_TransDetail_QueryZanReturnDetail queryZanReturnDetail = new ReqMsg_TransDetail_QueryZanReturnDetail();
				queryZanReturnDetail.setVersion("1.0.0");
				queryZanReturnDetail.setUserId(paymentStageDetailsRequest.getUserId());
				queryZanReturnDetail.setTime(paymentStageDetailsRequest.getTime());
				//查询操作
				ResMsg_TransDetail_QueryZanReturnDetail resTransDetail = appAssetBusinessService.queryZanReturnDetail(queryZanReturnDetail);

				//返回数据处理
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(resTransDetail.getResCode())) {
					List<HashMap<String, Object>> list = resTransDetail.getList();
					paymentStageDetailsResponse.setList(list);
				} else {
					throw new OpenException(resTransDetail.getResCode(),resTransDetail.getResMsg());
				}
			}
		});
	}

	// 我的银行卡
	@ResponseBody
	@RequestMapping("bank_card/v_1.0.0")
	public String bankCard(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, BankCardRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						/**
						 * private Integer userId; //用户ID
						 */
						BankCardRequest bankCardRequest = (BankCardRequest) request;
						Integer userId = bankCardRequest.getUserId();
						if (userId == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						BankCardResponse bankCardResponse = (BankCardResponse) response;
						BankCardRequest bankCardRequest = (BankCardRequest) request;

						//参数设置
						ReqMsg_User_BankListQuery reqBankListQuery = new ReqMsg_User_BankListQuery();
						reqBankListQuery.setVersion("1.0.0");
						reqBankListQuery.setUserId(bankCardRequest.getUserId());
						//查询操作
						ResMsg_User_BankListQuery resBankListQuery = (ResMsg_User_BankListQuery) appAssetBusinessService.bankCard(reqBankListQuery);

						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resBankListQuery.getResCode())) {
							/**
							 * private Integer id; //卡ID
							 * private String bankName; //卡银行名称
							 * private String cardNo; //卡号
							 * private String
							 * smallLogo; //小图标
							 * private String largeLogo; //大图标
							 * private Integer isFirst; //是否回款卡
							 */
							List<BankCard> bankCardList = new ArrayList<BankCard>();
							if (resBankListQuery.getBankCards() != null) {
								for (HashMap<String, Object> obj : resBankListQuery.getBankCards()) {
									BankCard bankCard = new BankCard();
									bankCard.setId((Integer)obj.get("id"));
									bankCard.setBankName((String)obj.get("bankName"));
									bankCard.setCardNo((String)obj.get("cardNo"));
									bankCard.setSmallLogo((String)obj.get("smallLogo"));
									bankCard.setLargeLogo((String)obj.get("largeLogo"));
									bankCard.setIsFirst((Integer)obj.get("isFirst"));
									bankCardList.add(bankCard);
								}
							}
							bankCardResponse.setSize(resBankListQuery.getBankCards() == null ? 0 : resBankListQuery.getBankCards().size());
							bankCardResponse.setBankCards(bankCardList);
						}
						else {
							throw new OpenException(resBankListQuery.getResCode(),resBankListQuery.getResMsg());
						}

					}
				});
	}

	// 账户安全中心
	@ResponseBody
	@RequestMapping("safe_center/v_1.0.0")
	public String safeCenter(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, SafeCenterRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						/**
						 * private Integer userId; //用户ID
						 */
						SafeCenterRequest safeCenterRequest = (SafeCenterRequest) request;
						Integer userId = safeCenterRequest.getUserId();
						if (userId == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						SafeCenterResponse safeCenterResponse = (SafeCenterResponse) response;
						SafeCenterRequest safeCenterRequest = (SafeCenterRequest) request;

						//参数设置
						ReqMsg_User_InfoQuery reqInfoQuery = new ReqMsg_User_InfoQuery();
						reqInfoQuery.setVersion("1.0.0");
						reqInfoQuery.setUserId(safeCenterRequest.getUserId());
						//查询操作
						ResMsg_User_InfoQuery resInfoQuery = (ResMsg_User_InfoQuery) appAssetBusinessService.safeCenter(reqInfoQuery);
						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resInfoQuery.getResCode())) {

							// 模拟返回数据
							/**
							 * private Integer isBindPayPassword; // 绑定真实姓名标志 1：已绑定 2：未绑定
							 * private String bankName; //银行卡所属名称
							 * private String cardNo; //银行卡卡号
							 * private Integer cardId; //银行卡ID
							 */
					        //判断是否存在支付密码 1:有交易密码；2：无交易密码
					        if (resInfoQuery.getExcitPaypassword() == 2) {
					        	safeCenterResponse.setIsBindPayPassword(false);
					        }else {
					        	safeCenterResponse.setIsBindPayPassword(true);
							}
							safeCenterResponse.setCardId(resInfoQuery.getCardId());
							safeCenterResponse.setCardNo(resInfoQuery.getCardNo());
							safeCenterResponse.setBankName(resInfoQuery.getBankName());
						}
						else {
							throw new OpenException(resInfoQuery.getResCode(),resInfoQuery.getResMsg());
						}

					}
				});
	}


	// 账户安全中心
	@ResponseBody
	@RequestMapping("safe_center/v_1.0.1")
	public String safeCenter_1(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, SafeCenterRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						/**
						 * private Integer userId; //用户ID
						 */
						SafeCenterRequest safeCenterRequest = (SafeCenterRequest) request;
						Integer userId = safeCenterRequest.getUserId();
						if (userId == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						SafeCenterResponse safeCenterResponse = (SafeCenterResponse) response;
						SafeCenterRequest safeCenterRequest = (SafeCenterRequest) request;

						//参数设置
						ReqMsg_User_InfoQuery reqInfoQuery = new ReqMsg_User_InfoQuery();
						reqInfoQuery.setVersion("1.0.0");
						reqInfoQuery.setUserId(safeCenterRequest.getUserId());
						//查询操作
						ResMsg_User_InfoQuery resInfoQuery = (ResMsg_User_InfoQuery) appAssetBusinessService.safeCenter(reqInfoQuery);
						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resInfoQuery.getResCode())) {

							// 模拟返回数据
							/**
							 * private Integer isBindPayPassword; // 绑定真实姓名标志 1：已绑定 2：未绑定
							 * private String bankName; //银行卡所属名称
							 * private String cardNo; //银行卡卡号
							 * private Integer cardId; //银行卡ID
							 */
					        //判断是否存在支付密码 1:有交易密码；2：无交易密码
					        if (resInfoQuery.getExcitPaypassword() == 2) {
					        	safeCenterResponse.setIsBindPayPassword(false);
					        }else {
					        	safeCenterResponse.setIsBindPayPassword(true);
							}
							safeCenterResponse.setCardId(resInfoQuery.getCardId());
							safeCenterResponse.setCardNo(resInfoQuery.getCardNo());
							safeCenterResponse.setBankName(resInfoQuery.getBankName());
							safeCenterResponse.setIsShowReturnPath(resInfoQuery.getIsShowReturnPath());
						}
						else {
							throw new OpenException(resInfoQuery.getResCode(),resInfoQuery.getResMsg());
						}

					}
				});
	}


	// 账户安全中心_支付密码设置
	@ResponseBody
	@RequestMapping("safe_pay_password_set/v_1.0.0")
	public String safePayPasswordSet(HttpServletRequest req,
			HttpServletResponse resp) {
		return this.execute(req, SafePayPasswordSetRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						/**
						 * private Integer userId; //用户ID
						 */
						SafePayPasswordSetRequest safePayPasswordSetRequest = (SafePayPasswordSetRequest) request;
						Integer userId = safePayPasswordSetRequest
								.getUserId();
						String  payPassWord = safePayPasswordSetRequest.getPayPassWord();
						if (userId == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}

						if(!CheckUtil.ParamCheck(safePayPasswordSetRequest, "userId","payPassWord")) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}

						if(payPassWord.length() < 6 || payPassWord.length() > 16 || !CheckUtil.RegCheck(payPassWord, "^[0-9a-zA-Z_]+$")) {
							throw new OpenException(OpenMessageEnum.PASSWORD_NOT_NORM.getCode(),OpenMessageEnum.PASSWORD_NOT_NORM.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						SafePayPasswordSetResponse safePayPasswordSetResponse = (SafePayPasswordSetResponse) response;
						SafePayPasswordSetRequest safePayPasswordSetRequest = (SafePayPasswordSetRequest) request;

						//参数设置
						ReqMsg_User_SetUpTraderPassword reqSetUpTraderPassword = new ReqMsg_User_SetUpTraderPassword();
						reqSetUpTraderPassword.setVersion("1.0.0");
						reqSetUpTraderPassword.setUserId(safePayPasswordSetRequest.getUserId());
						reqSetUpTraderPassword.setPayPassword(safePayPasswordSetRequest.getPayPassWord());
						//查询操作
						ResMsg_User_SetUpTraderPassword resSetUpTraderPassword = (ResMsg_User_SetUpTraderPassword) appAssetBusinessService.safePayPasswordSet(reqSetUpTraderPassword);
						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resSetUpTraderPassword.getResCode())) {
							BeanUtils.copyProperties(resSetUpTraderPassword, safePayPasswordSetResponse);
						}
						else {
							throw new OpenException(resSetUpTraderPassword.getResCode(),resSetUpTraderPassword.getResMsg());
						}

					}
				});
	}

	// 账户安全中心_支付密码修改
	@ResponseBody
	@RequestMapping("safe_pay_password_change/v_1.0.0")
	public String safePayPasswordChange(HttpServletRequest req,
			HttpServletResponse resp) {
		return this.execute(req, SafePayPasswordChangeRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						/**
						 * private Integer userId; //用户ID
						 */
						SafePayPasswordChangeRequest safePayPasswordChangeRequest = (SafePayPasswordChangeRequest) request;
						Integer userId = safePayPasswordChangeRequest.getUserId();
						String oldPayPassWord = safePayPasswordChangeRequest.getOldPayPassWord();
						String newPayPassWord = safePayPasswordChangeRequest.getNewPayPassWord();
						if (userId == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}

						if(!CheckUtil.ParamCheck(safePayPasswordChangeRequest, "userId","oldPayPassWord","newPayPassWord")) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}

						if(oldPayPassWord.length() < 6 || oldPayPassWord.length() > 16 || !CheckUtil.RegCheck(oldPayPassWord, "^[0-9a-zA-Z_]+$")) {
							throw new OpenException(OpenMessageEnum.PASSWORD_NOT_NORM.getCode(),OpenMessageEnum.PASSWORD_NOT_NORM.getMessage());
						}

						if(newPayPassWord.length() < 6 || newPayPassWord.length() > 16 || !CheckUtil.RegCheck(newPayPassWord, "^[0-9a-zA-Z_]+$")) {
							throw new OpenException(OpenMessageEnum.PASSWORD_NOT_NORM.getCode(),OpenMessageEnum.PASSWORD_NOT_NORM.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						SafePayPasswordChangeResponse safePayPasswordChangeResponse = (SafePayPasswordChangeResponse) response;
						SafePayPasswordChangeRequest safePayPasswordChangeRequest = (SafePayPasswordChangeRequest) request;

						//参数设置
						ReqMsg_Profile_PayPasswordModify reqPayPasswordModify = new ReqMsg_Profile_PayPasswordModify();
						reqPayPasswordModify.setVersion("1.0.0");
						reqPayPasswordModify.setUserId(safePayPasswordChangeRequest.getUserId());
						reqPayPasswordModify.setOldPayPassWord(safePayPasswordChangeRequest.getOldPayPassWord());
						reqPayPasswordModify.setNewPayPassWord(safePayPasswordChangeRequest.getNewPayPassWord());
						//查询操作
						ResMsg_Profile_PayPasswordModify resPayPasswordModify = (ResMsg_Profile_PayPasswordModify) appAssetBusinessService.safePayPasswordChange(reqPayPasswordModify);
						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resPayPasswordModify.getResCode())) {
							BeanUtils.copyProperties(resPayPasswordModify, safePayPasswordChangeResponse);
						}
						else {
							throw new OpenException(resPayPasswordModify.getResCode(),resPayPasswordModify.getResMsg());
						}

					}
				});
	}

	// 账户安全中心_支付密码找回
	@ResponseBody
	@RequestMapping("safe_pay_password_forget/v_1.0.0")
	public String safePayPasswordForget(HttpServletRequest req,
			HttpServletResponse resp) {
		return this.execute(req, SafePayPasswordForgetRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						/**
						 * private Integer userId; //用户ID
						 */
						SafePayPasswordForgetRequest safePayPasswordForgetRequest = (SafePayPasswordForgetRequest) request;
						Integer userId = safePayPasswordForgetRequest.getUserId();
						String mobileCode = safePayPasswordForgetRequest.getMobileCode();//用户输入验证码
						String password = safePayPasswordForgetRequest.getPassword();

						if (userId == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}

						if(!CheckUtil.ParamCheck(safePayPasswordForgetRequest, "userId","mobileCode","password")) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}

						if(mobileCode.length()!=4 || !CheckUtil.RegCheck(mobileCode, "^[0-9]*$")) {
							throw new OpenException(OpenMessageEnum.VERIFYCODE_NOT_NORM.getCode(),OpenMessageEnum.VERIFYCODE_NOT_NORM.getMessage());
						}

						if(password.length() < 6 || password.length() > 16 || !CheckUtil.RegCheck(password, "^[0-9a-zA-Z_]+$")) {
							throw new OpenException(OpenMessageEnum.PASSWORD_NOT_NORM.getCode(),OpenMessageEnum.PASSWORD_NOT_NORM.getMessage());
						}

					}

					@Override
					public void doOperation(Request request, Response response) {
						SafePayPasswordForgetRequest safePayPasswordForgetRequest = (SafePayPasswordForgetRequest) request;

						ReqMsg_User_InfoQuery reqUserInfo = new ReqMsg_User_InfoQuery();
						reqUserInfo.setUserId(safePayPasswordForgetRequest.getUserId());
						reqUserInfo.setVersion("1.0.0");
						ResMsg_User_InfoQuery resUserInfo = appIndexBusinessService.appIndexUserInfo(reqUserInfo);
						if(!ConstantUtil.BSRESCODE_SUCCESS.equals(resUserInfo.getResCode())) {
							throw new OpenException(resUserInfo.getResCode(),resUserInfo.getResMsg());
						}

						//参数设置
						ReqMsg_User_FindPayPassword reqFindPayPassword = new ReqMsg_User_FindPayPassword();
						reqFindPayPassword.setVersion("1.0.0");
						reqFindPayPassword.setUserId(safePayPasswordForgetRequest.getUserId());
						reqFindPayPassword.setMobile(resUserInfo.getMobile());
						reqFindPayPassword.setMobileCode(safePayPasswordForgetRequest.getMobileCode());
						reqFindPayPassword.setPassword(safePayPasswordForgetRequest.getPassword());
						//查询操作
						ResMsg_User_FindPayPassword resFindPayPassword = (ResMsg_User_FindPayPassword) appAssetBusinessService.safePayPasswordForget(reqFindPayPassword);
						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resFindPayPassword.getResCode())) {
							if (!resFindPayPassword.getIsValidateSuccess()) {
								throw new OpenException(OpenMessageEnum.FIND_PAY_PASSWORD_ERROR.getCode(),OpenMessageEnum.FIND_PAY_PASSWORD_ERROR.getMessage());
							}
						}
						else {
							throw new OpenException(resFindPayPassword.getResCode(),resFindPayPassword.getResMsg());
						}

					}
				});
	}

	// 账户安全中心_回款路径_修改
	@ResponseBody
	@RequestMapping("safe_return_path_change/v_1.0.0")
	public String safeReturnPathChange(HttpServletRequest req,
			HttpServletResponse resp) {
		return this.execute(req, ReturnPathChangeRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						/**
						 * private Integer userId; //用户ID
						 */
						ReturnPathChangeRequest returnPathChangeRequest = (ReturnPathChangeRequest) request;
						Integer userId = returnPathChangeRequest.getUserId();
						if (userId == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						ReturnPathChangeRequest returnPathChangeRequest = (ReturnPathChangeRequest) request;

						//参数设置
						ReqMsg_Bank_SetIsFirstBankCard reqSetIsFirstBankCard = new ReqMsg_Bank_SetIsFirstBankCard();
						reqSetIsFirstBankCard.setVersion("1.0.0");
						reqSetIsFirstBankCard.setUserId(returnPathChangeRequest.getUserId());
						reqSetIsFirstBankCard.setCardId(returnPathChangeRequest.getCardId());
						//查询操作
						ResMsg_Bank_SetIsFirstBankCard resSetIsFirstBankCard = (ResMsg_Bank_SetIsFirstBankCard) appAssetBusinessService.safeReturnPathChange(reqSetIsFirstBankCard);
						//返回数据处理
						if(!ConstantUtil.BSRESCODE_SUCCESS.equals(resSetIsFirstBankCard.getResCode())) {
							throw new OpenException(resSetIsFirstBankCard.getResCode(),resSetIsFirstBankCard.getResMsg());
						}
					}
				});
	}


    // 账户安全中心_回款路径_查询回款卡
    @ResponseBody
    @RequestMapping("safe_return_bank_card/v_1.0.0")
    public String safeReturnBankCard(HttpServletRequest req,
            HttpServletResponse resp) {
        return this.execute(req, ReturnBankCardRequest.class,
                new ControllerCallBack() {
                    @Override
                    public void doCheck(Request request, Response response) {
                        /**
                         * private Integer userId; //用户ID
                         */
                        ReturnBankCardRequest returnBankCardRequest = (ReturnBankCardRequest) request;
                        Integer userId = returnBankCardRequest.getUserId();
                        if (userId == null) {
                            throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
                        }
                    }

                    @Override
                    public void doOperation(Request request, Response response) {
                        ReturnBankCardResponse returnBankCardResponse = (ReturnBankCardResponse) response;
                        ReturnBankCardRequest returnBankCardRequest = (ReturnBankCardRequest) request;

                        //参数设置
                        ReqMsg_Bank_ReturnPath reqReturnPath = new ReqMsg_Bank_ReturnPath();
                        reqReturnPath.setVersion("1.0.0");
                        reqReturnPath.setUserId(returnBankCardRequest.getUserId());
                        //查询操作
                        ResMsg_Bank_ReturnPath resReturnPath = (ResMsg_Bank_ReturnPath) appAssetBusinessService.safeReturnBankCard(reqReturnPath);
                        //返回数据处理
                        if(ConstantUtil.BSRESCODE_SUCCESS.equals(resReturnPath.getResCode())) {
                            BeanUtils.copyProperties(resReturnPath, returnBankCardResponse);
                        }
                        else {
                            throw new OpenException(resReturnPath.getResCode(),resReturnPath.getResMsg());
                        }

                    }
                });
    }

	/**
	 * 账户余额_提现
	 *
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
	@RequestMapping("balance_withdraw/v_1.0.0")
	public String balanceWithdraw(HttpServletRequest req,
			HttpServletResponse resp) {
		return this.execute(req, BalanceWithdrawRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						BalanceWithdrawRequest balanceWithdrawRequest = (BalanceWithdrawRequest) request;
						if(balanceWithdrawRequest.getCardId() == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(), "未绑定回款银行卡");
						}
						if(!CheckUtil.ParamCheck(balanceWithdrawRequest, "userId", "cardId", "amount", "terminalType")) {
						    throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						BalanceWithdrawResponse balanceWithdrawResponse = (BalanceWithdrawResponse) response;
						BalanceWithdrawRequest balanceWithdrawRequest = (BalanceWithdrawRequest) request;
						ReqMsg_UserBalance_Withdraw req = new ReqMsg_UserBalance_Withdraw();
						req.setVersion("1.0.0");
						req.setAmount(balanceWithdrawRequest.getAmount());
						req.setUserId(balanceWithdrawRequest.getUserId());
						req.setPassword(balanceWithdrawRequest.getPayPassword());
						req.setTerminalType(balanceWithdrawRequest.getTerminalType());
						ResMsg_UserBalance_Withdraw res = appAssetBusinessService.balanceWithdraw(req);
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
						    balanceWithdrawResponse.setSuccess(true);
						    balanceWithdrawResponse.setNeedCheck(res.isNeedCheck());
						    if(res.isNeedCheck() && null != res.getTime()) {
						        balanceWithdrawResponse.setTime(DateUtil.format(DateUtil.addDays(res.getTime(),1)));
						    }
						} else {
						    balanceWithdrawResponse.setSuccess(false);
						    throw new OpenException(res.getResCode(), res.getResMsg());
						}
					}
				});
	}



	/**
	 * 账户余额_提现
	 *
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
	@RequestMapping("balance_withdraw/v_1.0.1")
	public String balanceWithdraw_1(HttpServletRequest req,
			HttpServletResponse resp) {
		return this.execute(req, BalanceWithdrawRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						BalanceWithdrawRequest balanceWithdrawRequest = (BalanceWithdrawRequest) request;
						if(balanceWithdrawRequest.getCardId() == null || balanceWithdrawRequest.getCardId() == 0) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(), "未绑定回款银行卡");
						}
						if(!CheckUtil.ParamCheck(balanceWithdrawRequest, "userId", "cardId", "amount", "terminalType")) {
						    throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						BalanceWithdrawResponse balanceWithdrawResponse = (BalanceWithdrawResponse) response;
						BalanceWithdrawRequest balanceWithdrawRequest = (BalanceWithdrawRequest) request;
						ReqMsg_UserBalance_Withdraw req = new ReqMsg_UserBalance_Withdraw();
						req.setVersion("1.0.0");
						req.setAmount(balanceWithdrawRequest.getAmount());
						req.setUserId(balanceWithdrawRequest.getUserId());
						req.setPassword(balanceWithdrawRequest.getPayPassword());
						req.setTerminalType(balanceWithdrawRequest.getTerminalType());
						ResMsg_UserBalance_Withdraw res = appAssetBusinessService.balanceWithdraw(req);
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
						    balanceWithdrawResponse.setSuccess(true);
						    balanceWithdrawResponse.setNeedCheck(res.isNeedCheck());
						    if(res.isNeedCheck() && null != res.getTime()) {
						        balanceWithdrawResponse.setTime(DateUtil.format(DateUtil.addDays(res.getTime(),1)));
						    }
						    balanceWithdrawResponse.setOrderNo(res.getOrderNo());
						    SimpleDateFormat formatterMonth = new SimpleDateFormat("YYYY年MM月dd日 HH:mm:ss");
							String dateString = formatterMonth.format(res.getWithdrawTime());
						    balanceWithdrawResponse.setWithdrawTime(dateString);
						} else {
						    balanceWithdrawResponse.setSuccess(false);
						    throw new OpenException(res.getResCode(), res.getResMsg());
						}
					}
				});
	}





	/**
	 * 账户余额_提现存管改造v_1.0.2
	 *
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
	@RequestMapping("balance_withdraw/v_1.0.2")
	public String balanceWithdraw_2(HttpServletRequest req,
			HttpServletResponse resp) {
		return this.execute(req, BalanceWithdrawRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						BalanceWithdrawRequest balanceWithdrawRequest = (BalanceWithdrawRequest) request;
						if(balanceWithdrawRequest.getCardId() == null || balanceWithdrawRequest.getCardId() == 0) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(), "未绑定回款银行卡");
						}
						if(!CheckUtil.ParamCheck(balanceWithdrawRequest, "userId", "cardId", "amount", "terminalType")) {
						    throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						BalanceWithdrawResponse balanceWithdrawResponse = (BalanceWithdrawResponse) response;
						BalanceWithdrawRequest balanceWithdrawRequest = (BalanceWithdrawRequest) request;
						ReqMsg_UserBalance_Withdraw req = new ReqMsg_UserBalance_Withdraw();
						req.setVersion("1.0.1");
						req.setAmount(balanceWithdrawRequest.getAmount());
						req.setUserId(balanceWithdrawRequest.getUserId());
						req.setPassword(balanceWithdrawRequest.getPayPassword());
						req.setTerminalType(balanceWithdrawRequest.getTerminalType());
						req.setAccountType(balanceWithdrawRequest.getAccountType());
						ResMsg_UserBalance_Withdraw res = appAssetBusinessService.balanceWithdraw(req);
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
						    balanceWithdrawResponse.setSuccess(true);
						    balanceWithdrawResponse.setNeedCheck(res.isNeedCheck());
						    if(res.isNeedCheck() && null != res.getTime()) {
						        balanceWithdrawResponse.setTime(DateUtil.format(DateUtil.addDays(res.getTime(),1)));
						    }
						    balanceWithdrawResponse.setOrderNo(res.getOrderNo());
						    SimpleDateFormat formatterMonth = new SimpleDateFormat("YYYY年MM月dd日 HH:mm:ss");
							String dateString = formatterMonth.format(res.getWithdrawTime());
						    balanceWithdrawResponse.setWithdrawTime(dateString);
						} else {
						    balanceWithdrawResponse.setSuccess(false);
						    throw new OpenException(res.getResCode(), res.getResMsg());
						}
					}
				});
	}




	//我的投资_投资购买协议
	@ResponseBody
	@RequestMapping("invest_buy_agree_ment/v_1.0.0")
	public String investBuyAgreeMent(HttpServletRequest req,
			HttpServletResponse resp) {
		return this.execute(req, InvestBuyAgreeMentRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						/**
						 * private Integer userId; //用户ID
						 */
						InvestBuyAgreeMentRequest investBuyAgreeMentRequest = (InvestBuyAgreeMentRequest) request;
						Integer userId = investBuyAgreeMentRequest.getUserId();
						Integer subAccountid = investBuyAgreeMentRequest.getSubAccountId();
						if (userId == null || subAccountid==null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						InvestBuyAgreeMentResponse investBuyAgreeMentResponse = (InvestBuyAgreeMentResponse) response;
						InvestBuyAgreeMentRequest investBuyAgreeMentRequest = (InvestBuyAgreeMentRequest) request;

						//参数设置
						ReqMsg_Invest_BuyAgreeMent reqBuyAgreeMent = new ReqMsg_Invest_BuyAgreeMent();
						reqBuyAgreeMent.setUserId(investBuyAgreeMentRequest.getUserId());
						reqBuyAgreeMent.setSubAccountId(investBuyAgreeMentRequest.getSubAccountId());
						//查询操作
						ResMsg_Invest_BuyAgreeMent resBuyAgreeMent = (ResMsg_Invest_BuyAgreeMent) appAssetBusinessService.buyAgreeMent(reqBuyAgreeMent);
						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resBuyAgreeMent.getResCode())) {
							/**
							    private String            mobile;                                  //	手机号
							    private String            idCard;                                  //	身份证号码
							    private String            userName;                                //	用户真实姓名
							    private Integer     	  dayNum;									//	投资期限
							    private Double            rate;										//	利率
							    private String 			  name;										//	产品名称
							    private Double 			  amount;  									//	投资金额
							    private String 			  times;									//
							    private String 			  openTime;             					//	协议签署日期
							    private String 			  beginTime;
							 */
							BeanUtils.copyProperties(resBuyAgreeMent, investBuyAgreeMentResponse);

						}
						else {
							throw new OpenException(resBuyAgreeMent.getResCode(),resBuyAgreeMent.getResMsg());
						}

					}
				});
	}

	// 提现 ================================================
	@ResponseBody
    @RequestMapping("withdrawal_index/v_1.0.0")
    public String withdrawalIndexRequest(HttpServletRequest req,
            HttpServletResponse resp) {
	        return this.execute(req, WithdrawalIndexRequest.class,  new ControllerCallBack() {

	            @Override
                public void doCheck(Request request, Response response) {
	                WithdrawalIndexRequest indexRequest = (WithdrawalIndexRequest) request;
                    Integer userId = indexRequest.getUserId();
                    if (userId == null) {
                        throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
                    }
                }

                @Override
                public void doOperation(Request request, Response response) {
                    WithdrawalIndexRequest indexRequest = (WithdrawalIndexRequest) request;
                    WithdrawalIndexResponse indexResponse = (WithdrawalIndexResponse) response;

                    appAssetBusinessService.withdrawalIndexRequest(indexRequest, indexResponse);
                }
            });
	}



	@ResponseBody
    @RequestMapping("withdrawal_index/v_1.0.1")
    public String withdrawalIndexRequest_1(HttpServletRequest req,
            HttpServletResponse resp) {
	        return this.execute(req, WithdrawalIndexRequest.class,  new ControllerCallBack() {

	            @Override
                public void doCheck(Request request, Response response) {
	                WithdrawalIndexRequest indexRequest = (WithdrawalIndexRequest) request;
                    Integer userId = indexRequest.getUserId();
                    if (userId == null) {
                        throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
                    }
                }

                @Override
                public void doOperation(Request request, Response response) {
                    WithdrawalIndexRequest indexRequest = (WithdrawalIndexRequest) request;
                    WithdrawalIndexResponse indexResponse = (WithdrawalIndexResponse) response;

                    appAssetBusinessService.withdrawalIndexRequest_1(indexRequest, indexResponse);
                }
            });
	}


	//账户余额_提现_页面信息_存管改造v_1.0.2
	@ResponseBody
    @RequestMapping("withdrawal_index/v_1.0.2")
    public String withdrawalIndexRequest_2(HttpServletRequest req,
            HttpServletResponse resp) {
	        return this.execute(req, WithdrawalIndexRequest.class,  new ControllerCallBack() {

	            @Override
                public void doCheck(Request request, Response response) {
	                WithdrawalIndexRequest indexRequest = (WithdrawalIndexRequest) request;
                    Integer userId = indexRequest.getUserId();
                    if (userId == null) {
                        throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
                    }
                }

                @Override
                public void doOperation(Request request, Response response) {
                    WithdrawalIndexRequest indexRequest = (WithdrawalIndexRequest) request;
                    WithdrawalIndexResponse indexResponse = (WithdrawalIndexResponse) response;

                    appAssetBusinessService.withdrawalIndexRequest_2(indexRequest, indexResponse);
                }
            });
	}

	// =================================== 充值 开始 =======================================

	/**
     * 【充值】充值首页面{
     *     1、未绑卡
     *     2、已绑卡
     * }
     * @param req
     * @param resp
     * @return
     */
    @ResponseBody
    @RequestMapping("balance_recharge/v_1.0.0")
    public String balanceRecharge(HttpServletRequest req,
            HttpServletResponse resp) {
        return this.execute(req, BalanceRechargeRequest.class,
                new ControllerCallBack() {
                    @Override
                    public void doCheck(Request request, Response response) {
                        BalanceRechargeRequest balanceRechargeRequest = (BalanceRechargeRequest) request;
                        Integer userId = balanceRechargeRequest.getUserId();
                        if (userId == null) {
                            throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
                        }
                    }

                    @Override
                    public void doOperation(Request request, Response response) {
                        BalanceRechargeResponse balanceRechargeResponse = (BalanceRechargeResponse) response;
                        BalanceRechargeRequest balanceRechargeRequest = (BalanceRechargeRequest) request;

                        ReqMsg_Bank_bindBankList req = new ReqMsg_Bank_bindBankList();
                        req.setVersion("1.0.0");
                        req.setUserId(balanceRechargeRequest.getUserId());
                        ResMsg_Bank_bindBankList resMsgBankList = appAssetBusinessService.queryBindBank(req);
                        if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsgBankList.getResCode())) {
                            balanceRechargeResponse.setSuccess(true);
                            balanceRechargeResponse.setBindBank(resMsgBankList.isBindBank());
                            balanceRechargeResponse.setRechangeLimit(Double.parseDouble(resMsgBankList.getRechangeLimit()));
                            if (resMsgBankList.isBindBank()) {
                                // 已绑卡实名
                                for (int i = 0; i < resMsgBankList.getBankList().size(); i++) {
                                    Integer isFirst = (Integer) resMsgBankList.getBankList().get(i).get("isFirst");
                                    if (isFirst == 1) {
                                        List<BalanceRechargeBankCard> list = new ArrayList<BalanceRechargeBankCard>();
                                        BalanceRechargeBankCard bankCard = new BalanceRechargeBankCard();
                                        bankCard.setBankId((Integer)resMsgBankList.getBankList().get(i).get("bankId"));
                                        bankCard.setBankName((String)resMsgBankList.getBankList().get(i).get("bankName"));
                                        bankCard.setCardNo((String)resMsgBankList.getBankList().get(i).get("cardNo"));
                                        bankCard.setDayTop((Double)resMsgBankList.getBankList().get(i).get("dayTop"));
                                        bankCard.setId((Integer)resMsgBankList.getBankList().get(i).get("id"));
                                        bankCard.setIsAvailable((Integer)resMsgBankList.getBankList().get(i).get("isAvailable"));
                                        bankCard.setIsFirst(isFirst);
                                        bankCard.setOneTop((Double)resMsgBankList.getBankList().get(i).get("oneTop"));
                                        bankCard.setUserId(balanceRechargeRequest.getUserId());
                                        bankCard.setDailyNotice((String)resMsgBankList.getBankList().get(i).get("dailyNotice"));
										bankCard.setLargeLogo((String)resMsgBankList.getBankList().get(i).get("largeLogo"));
										bankCard.setSmallLogo((String)resMsgBankList.getBankList().get(i).get("smallLogo"));
                                        list.add(bankCard);
                                        balanceRechargeResponse.setBankList(list);
                                    }
                                }
                            }
                        } else {
                            throw new OpenException(resMsgBankList.getResCode(), resMsgBankList.getResMsg());
                        }

                    }
                });
    }

	/**
	 * 【充值】未绑卡充值预下单
	 *
	 * @param req
	 * @param resp
	 * @return
	 */
    @Deprecated
	@ResponseBody
    @RequestMapping("/recharge/noBindPreOrder/v_1.0.0")
    public String noBindPreOrder(HttpServletRequest req,
            HttpServletResponse resp) {
	    return this.execute(req, NoBindPreOrderRequest.class, new ControllerCallBack() {

	        @Override
	        public void doCheck(Request request, Response response) {
	            super.doCheck(request, response);
	            NoBindPreOrderRequest noBindPreOrderRequest = (NoBindPreOrderRequest) request;
                if (noBindPreOrderRequest.getUserId() == null) {
                    throw new OpenException(OpenMessageEnum.USER_ID_EMPTY.getCode(), OpenMessageEnum.USER_ID_EMPTY.getMessage());
                }
                if (noBindPreOrderRequest.getAmount() == null) {
                    throw new OpenException(OpenMessageEnum.RECHARGE_AMOUNT_IS_NOT_SPECIFICATION.getCode(), OpenMessageEnum.RECHARGE_AMOUNT_IS_NOT_SPECIFICATION.getMessage());
                }
                if (noBindPreOrderRequest.getBankId() == null) {
                    throw new OpenException(OpenMessageEnum.BALANCE_RECHAGE_BANK_EMPTY.getCode(), OpenMessageEnum.BALANCE_RECHAGE_BANK_EMPTY.getMessage());
                }
                if (StringUtil.isBlank(noBindPreOrderRequest.getCardNo())) {
                    throw new OpenException(OpenMessageEnum.CARDNO_NOT_NORM.getCode(), OpenMessageEnum.CARDNO_NOT_NORM.getMessage());
                }
                if (StringUtil.isBlank(noBindPreOrderRequest.getBankName())) {
                    throw new OpenException(OpenMessageEnum.BALANCE_RECHAGE_BANK_EMPTY.getCode(), OpenMessageEnum.BALANCE_RECHAGE_BANK_EMPTY.getMessage());
                }
                if (StringUtil.isBlank(noBindPreOrderRequest.getIdCard())) {
                    throw new OpenException(OpenMessageEnum.IDCARD_NOT_NORM.getCode(), OpenMessageEnum.IDCARD_NOT_NORM.getMessage());
                }
                if (StringUtil.isBlank(noBindPreOrderRequest.getMobile())) {
                    throw new OpenException(OpenMessageEnum.MOBILE_NOT_NORM.getCode(), OpenMessageEnum.MOBILE_NOT_NORM.getMessage());
                }
                if (StringUtil.isBlank(noBindPreOrderRequest.getUserName())) {
                    throw new OpenException(OpenMessageEnum.USER_NAME_IS_EMPTY.getCode(), OpenMessageEnum.USER_NAME_IS_EMPTY.getMessage());
                }
                if (noBindPreOrderRequest.getTerminalType() == null) {
                    throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(), OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
                }

                if (noBindPreOrderRequest.getAmount() < 1) {// 充值金额必须大于1
                    throw new OpenException(OpenMessageEnum.RECHARGE_AMOUNT_IS_NOT_SPECIFICATION.getCode(),OpenMessageEnum.RECHARGE_AMOUNT_IS_NOT_SPECIFICATION.getMessage());
                }
                if(noBindPreOrderRequest.getCardNo().length() > 20 || !CheckUtil.RegCheck(noBindPreOrderRequest.getCardNo(), "^[0-9]*$")) {
                    throw new OpenException(OpenMessageEnum.CARDNO_NOT_NORM.getCode(),OpenMessageEnum.CARDNO_NOT_NORM.getMessage());
                }
                if(noBindPreOrderRequest.getUserName().length() > 10) {
                    throw new OpenException(OpenMessageEnum.USERNAME_NOT_NORM.getCode(),OpenMessageEnum.USERNAME_NOT_NORM.getMessage());
                }
                if(!CheckUtil.RegCheck(noBindPreOrderRequest.getIdCard(), "(\\d{14}[0-9xX])|(\\d{17}[0-9xX])")) {
                    throw new OpenException(OpenMessageEnum.IDCARD_NOT_NORM.getCode(),OpenMessageEnum.IDCARD_NOT_NORM.getMessage());
                }
                if(noBindPreOrderRequest.getMobile().length() != 11 || !CheckUtil.RegCheck(noBindPreOrderRequest.getMobile(), "^[0-9]*$")) {
                    throw new OpenException(OpenMessageEnum.MOBILE_NOT_NORM.getCode(),OpenMessageEnum.MOBILE_NOT_NORM.getMessage());
                }
	        }

            @Override
            public void doOperation(Request request, Response response) {
                NoBindPreOrderRequest noBindPreOrderRequest = (NoBindPreOrderRequest)request;
                NoBindPreOrderResponse noBindPreOrderResponse = (NoBindPreOrderResponse)response;
                ReqMsg_RegularBuy_Order reqMsg_RegularBuy_Order = new ReqMsg_RegularBuy_Order();
                reqMsg_RegularBuy_Order.setVersion("1.0.0");
                reqMsg_RegularBuy_Order.setUserId(noBindPreOrderRequest.getUserId());
                reqMsg_RegularBuy_Order.setAmount(noBindPreOrderRequest.getAmount());
                reqMsg_RegularBuy_Order.setCardNo(noBindPreOrderRequest.getCardNo());
                reqMsg_RegularBuy_Order.setUserName(noBindPreOrderRequest.getUserName());
                reqMsg_RegularBuy_Order.setIdCard(noBindPreOrderRequest.getIdCard());
                reqMsg_RegularBuy_Order.setMobile(noBindPreOrderRequest.getMobile());
                reqMsg_RegularBuy_Order.setBankId(noBindPreOrderRequest.getBankId());
                reqMsg_RegularBuy_Order.setBankName(noBindPreOrderRequest.getBankName());
                reqMsg_RegularBuy_Order.setTerminalType(noBindPreOrderRequest.getTerminalType());
                reqMsg_RegularBuy_Order.setIsBind(Constants.IS_BIND_NO);   // 未绑卡
                reqMsg_RegularBuy_Order.setTransType(Constants.TRANS_TYPE_TOP_UP);    // 充值
                reqMsg_RegularBuy_Order.setPlaceOrder(Constants.PLACE_ORDER_PREPARE);   // 预下单

                ResMsg_RegularBuy_Order resMsg_RegularBuy_Order = appAssetBusinessService.rechargePreOrder(reqMsg_RegularBuy_Order);
                if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg_RegularBuy_Order.getResCode())) {
                    noBindPreOrderResponse.setSuccess(true);
                    noBindPreOrderResponse.setOrderNo(resMsg_RegularBuy_Order.getOrderNo());
                    noBindPreOrderResponse.setHtml(resMsg_RegularBuy_Order.getHtml());
                } else {
                   throw new OpenException(resMsg_RegularBuy_Order.getResCode(), resMsg_RegularBuy_Order.getResMsg());
                }
            }
        });
	}

	/**
	 * 【充值】未绑卡充值正式下单
	 *
	 * @param req
	 * @param resp
	 * @return
	 */
	@Deprecated
	@ResponseBody
    @RequestMapping("/recharge/noBindOrder/v_1.0.0")
    public String noBindOrder(HttpServletRequest req,
            HttpServletResponse resp) {
        return this.execute(req, NoBindOrderRequest.class, new ControllerCallBack() {

            @Override
            public void doCheck(Request request, Response response) {
                super.doCheck(request, response);
                NoBindOrderRequest noBindOrderRequest = (NoBindOrderRequest) request;
                if (noBindOrderRequest.getUserId() == null) {
                    throw new OpenException(OpenMessageEnum.USER_ID_EMPTY.getCode(), OpenMessageEnum.USER_ID_EMPTY.getMessage());
                }
                if (noBindOrderRequest.getAmount() == null) {
                    throw new OpenException(OpenMessageEnum.RECHARGE_AMOUNT_IS_NOT_SPECIFICATION.getCode(), OpenMessageEnum.RECHARGE_AMOUNT_IS_NOT_SPECIFICATION.getMessage());
                }
                if (noBindOrderRequest.getBankId() == null) {
                    throw new OpenException(OpenMessageEnum.BALANCE_RECHAGE_BANK_EMPTY.getCode(), OpenMessageEnum.BALANCE_RECHAGE_BANK_EMPTY.getMessage());
                }
                if (StringUtil.isBlank(noBindOrderRequest.getCardNo())) {
                    throw new OpenException(OpenMessageEnum.CARDNO_NOT_NORM.getCode(), OpenMessageEnum.CARDNO_NOT_NORM.getMessage());
                }
                if (StringUtil.isBlank(noBindOrderRequest.getBankName())) {
                    throw new OpenException(OpenMessageEnum.BALANCE_RECHAGE_BANK_EMPTY.getCode(), OpenMessageEnum.BALANCE_RECHAGE_BANK_EMPTY.getMessage());
                }
                if (StringUtil.isBlank(noBindOrderRequest.getIdCard())) {
                    throw new OpenException(OpenMessageEnum.IDCARD_NOT_NORM.getCode(), OpenMessageEnum.IDCARD_NOT_NORM.getMessage());
                }
                if (StringUtil.isBlank(noBindOrderRequest.getMobile())) {
                    throw new OpenException(OpenMessageEnum.MOBILE_NOT_NORM.getCode(), OpenMessageEnum.MOBILE_NOT_NORM.getMessage());
                }
                if (StringUtil.isBlank(noBindOrderRequest.getUserName())) {
                    throw new OpenException(OpenMessageEnum.USER_NAME_IS_EMPTY.getCode(), OpenMessageEnum.USER_NAME_IS_EMPTY.getMessage());
                }
                if (noBindOrderRequest.getTerminalType() == null) {
                    throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(), OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
                }

                if(!CheckUtil.RegCheck(noBindOrderRequest.getVerifyCode(), "^[0-9]*$")) {
                    throw new OpenException(OpenMessageEnum.VERIFYCODE_NOT_NORM.getCode(),OpenMessageEnum.VERIFYCODE_NOT_NORM.getMessage());
                }
                if (noBindOrderRequest.getAmount() < 1) {// 充值金额必须大于1
                    throw new OpenException(OpenMessageEnum.RECHARGE_AMOUNT_IS_NOT_SPECIFICATION.getCode(),OpenMessageEnum.RECHARGE_AMOUNT_IS_NOT_SPECIFICATION.getMessage());
                }
                if(noBindOrderRequest.getCardNo().length() > 20 || !CheckUtil.RegCheck(noBindOrderRequest.getCardNo(), "^[0-9]*$")) {
                    throw new OpenException(OpenMessageEnum.CARDNO_NOT_NORM.getCode(),OpenMessageEnum.CARDNO_NOT_NORM.getMessage());
                }
                if(noBindOrderRequest.getUserName().length() > 10) {
                    throw new OpenException(OpenMessageEnum.USERNAME_NOT_NORM.getCode(),OpenMessageEnum.USERNAME_NOT_NORM.getMessage());
                }
                if(!CheckUtil.RegCheck(noBindOrderRequest.getIdCard(), "(\\d{14}[0-9xX])|(\\d{17}[0-9xX])")) {
                    throw new OpenException(OpenMessageEnum.IDCARD_NOT_NORM.getCode(),OpenMessageEnum.IDCARD_NOT_NORM.getMessage());
                }
                if(noBindOrderRequest.getMobile().length() != 11 || !CheckUtil.RegCheck(noBindOrderRequest.getMobile(), "^[0-9]*$")) {
                    throw new OpenException(OpenMessageEnum.MOBILE_NOT_NORM.getCode(),OpenMessageEnum.MOBILE_NOT_NORM.getMessage());
                }
            }

            @Override
            public void doOperation(Request request, Response response) {
                NoBindOrderRequest noBindOrderRequest = (NoBindOrderRequest)request;
                NoBindOrderResponse noBindOrderResponse = (NoBindOrderResponse)response;

                ReqMsg_RegularBuy_Order reqMsg_RegularBuy_Order = new ReqMsg_RegularBuy_Order();
                reqMsg_RegularBuy_Order.setVersion("1.0.0");
                reqMsg_RegularBuy_Order.setUserId(noBindOrderRequest.getUserId());
                reqMsg_RegularBuy_Order.setAmount(noBindOrderRequest.getAmount());
                reqMsg_RegularBuy_Order.setCardNo(noBindOrderRequest.getCardNo());
                reqMsg_RegularBuy_Order.setUserName(noBindOrderRequest.getUserName());
                reqMsg_RegularBuy_Order.setIdCard(noBindOrderRequest.getIdCard());
                reqMsg_RegularBuy_Order.setMobile(noBindOrderRequest.getMobile());
                reqMsg_RegularBuy_Order.setBankId(noBindOrderRequest.getBankId());
                reqMsg_RegularBuy_Order.setBankName(noBindOrderRequest.getBankName());
                reqMsg_RegularBuy_Order.setTerminalType(noBindOrderRequest.getTerminalType());
                reqMsg_RegularBuy_Order.setIsBind(Constants.IS_BIND_NO);   // 未绑卡
                reqMsg_RegularBuy_Order.setTransType(Constants.TRANS_TYPE_TOP_UP);    // 充值
                reqMsg_RegularBuy_Order.setPlaceOrder(Constants.PLACE_ORDER_FORMAL);   // 正式下单
                reqMsg_RegularBuy_Order.setOrderNo(noBindOrderRequest.getOrderNo());
                reqMsg_RegularBuy_Order.setVerifyCode(noBindOrderRequest.getVerifyCode());
                ResMsg_RegularBuy_Order resMsg_RegularBuy_Order = appAssetBusinessService.rechargePreOrder(reqMsg_RegularBuy_Order);
                if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg_RegularBuy_Order.getResCode())) {
                    noBindOrderResponse.setSuccess(true);
                } else {
                    throw new OpenException(resMsg_RegularBuy_Order.getResCode(), resMsg_RegularBuy_Order.getResMsg());
                }
            }
        });
    }

    /**
     * 充值，绑卡用户预下单
     *
     * @param req
     * @param resp
     * @return
     */
    @ResponseBody
    @RequestMapping("balance_recharge_pro/v_1.0.0")
    public String balanceRechargePro(HttpServletRequest req,
            HttpServletResponse resp) {
        return this.execute(req, BalanceRechargeProRequest.class,
                new ControllerCallBack() {
                    @Override
                    public void doCheck(Request request, Response response) {
                        BalanceRechargeProRequest balanceRechargeProRequest = (BalanceRechargeProRequest) request;
                        Integer userId = balanceRechargeProRequest.getUserId();
                        Double amount = balanceRechargeProRequest.getAmount();
                        Integer bankId = balanceRechargeProRequest.getBankId();

                        if (balanceRechargeProRequest.getTerminalType() == null) {
                            throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(), OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
                        }
                        if (userId == null) {
                            throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
                        }
                        if (amount == null) {
                            throw new OpenException(OpenMessageEnum.BALANCE_RECHAGE_AMOUNT_EMPTY.getCode(), OpenMessageEnum.BALANCE_RECHAGE_AMOUNT_EMPTY.getMessage());
                        }
                        if (bankId == null) {
                            throw new OpenException(OpenMessageEnum.BALANCE_RECHAGE_BANK_EMPTY.getCode(), "请先绑定银行卡");
                        }
                        if (amount < 1 ) {// 充值金额必须大于等于1
                            throw new OpenException(OpenMessageEnum.RECHARGE_AMOUNT_IS_NOT_SPECIFICATION.getCode(),OpenMessageEnum.RECHARGE_AMOUNT_IS_NOT_SPECIFICATION.getMessage());
                        }
                    }

                    @Override
                    public void doOperation(Request request, Response response) {
                        BalanceRechargeProResponse balanceRechargeProResponse = (BalanceRechargeProResponse) response;
                        BalanceRechargeProRequest balanceRechargeProRequest = (BalanceRechargeProRequest) request;

                        ReqMsg_RegularBuy_Order reqMsg = new ReqMsg_RegularBuy_Order();
                        reqMsg.setVersion("1.0.0");
                        reqMsg.setAmount(balanceRechargeProRequest.getAmount());
                        reqMsg.setBankId(balanceRechargeProRequest.getBankId());
                        reqMsg.setUserId(balanceRechargeProRequest.getUserId());
                        reqMsg.setTerminalType(balanceRechargeProRequest.getTerminalType());
                        reqMsg.setIsBind(Constants.IS_BIND_YES);   // 已绑卡
                        reqMsg.setTransType(Constants.TRANS_TYPE_TOP_UP);    // 充值
                        reqMsg.setPlaceOrder(Constants.PLACE_ORDER_PREPARE);   // 预下单
                        ResMsg_RegularBuy_Order resMsg_RegularBuy_Order = appAssetBusinessService.rechargePreOrder(reqMsg);
                        if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg_RegularBuy_Order.getResCode())) {
                            balanceRechargeProResponse.setSuccess(true);
                            balanceRechargeProResponse.setMobile(reqMsg.getMobile());
                            balanceRechargeProResponse.setOrderNo(resMsg_RegularBuy_Order.getOrderNo());
                            balanceRechargeProResponse.setHtml(resMsg_RegularBuy_Order.getHtml());
                        } else {
                            throw new OpenException(resMsg_RegularBuy_Order.getResCode(), resMsg_RegularBuy_Order.getResMsg());
                        }
                    }
                });
    }

    /**
     * 充值，绑卡用户正式下单
     *
     * @param req
     * @param resp
     * @return
     */
    @ResponseBody
    @RequestMapping("balance_recharge_form/v_1.0.0")
    public String balanceRechargeForm(HttpServletRequest req,
            HttpServletResponse resp) {
        return this.execute(req, BalanceRechargeFormRequest.class,
                new ControllerCallBack() {
                    @Override
                    public void doCheck(Request request, Response response) {
                        BalanceRechargeFormRequest balanceRechargeFormRequest = (BalanceRechargeFormRequest) request;
                        Integer userId = balanceRechargeFormRequest.getUserId();
                        Double amount = balanceRechargeFormRequest.getAmount();
                        Integer bankId = balanceRechargeFormRequest.getBankId();
                        String orderNo = balanceRechargeFormRequest.getOrderNo();
                        String verifyCode = balanceRechargeFormRequest.getVerifyCode();

                        if (balanceRechargeFormRequest.getTerminalType() == null) {
                            throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(), OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
                        }
                        if (userId == null) {
                            throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
                        }
                        if (amount == null) {
                            throw new OpenException(OpenMessageEnum.BALANCE_RECHAGE_AMOUNT_EMPTY.getCode(),OpenMessageEnum.BALANCE_RECHAGE_AMOUNT_EMPTY.getMessage());
                        }
                        if (bankId == null) {
                            throw new OpenException(OpenMessageEnum.BALANCE_RECHAGE_BANK_EMPTY.getCode(),OpenMessageEnum.BALANCE_RECHAGE_BANK_EMPTY.getMessage());
                        }
                        if (StringUtil.isBlank(orderNo)) {
                            throw new OpenException(OpenMessageEnum.BALANCE_RECHAGE_ORDERNO_EMPTY.getCode(),OpenMessageEnum.BALANCE_RECHAGE_ORDERNO_EMPTY.getMessage());
                        }
                        if (StringUtil.isBlank(verifyCode)) {
                            throw new OpenException(OpenMessageEnum.BALANCE_RECHAGE_VERIFYCODE_EMPTY.getCode(),OpenMessageEnum.BALANCE_RECHAGE_VERIFYCODE_EMPTY.getMessage());
                        }
                        if (amount < 1 ) {// 充值金额必须大于等于1
                            throw new OpenException(OpenMessageEnum.RECHARGE_AMOUNT_IS_NOT_SPECIFICATION.getCode(),OpenMessageEnum.RECHARGE_AMOUNT_IS_NOT_SPECIFICATION.getMessage());
                        }
                    }

                    @Override
                    public void doOperation(Request request, Response response) {
                        BalanceRechargeFormResponse balanceRechargeFormResponse = (BalanceRechargeFormResponse) response;
                        BalanceRechargeFormRequest balanceRechargeFormRequest = (BalanceRechargeFormRequest) request;

                        ReqMsg_RegularBuy_Order reqMsg = new ReqMsg_RegularBuy_Order();
                        reqMsg.setVersion("1.0.0");
                        reqMsg.setAmount(balanceRechargeFormRequest.getAmount());
                        reqMsg.setBankId(balanceRechargeFormRequest.getBankId());
                        reqMsg.setUserId(balanceRechargeFormRequest.getUserId());
                        reqMsg.setOrderNo(balanceRechargeFormRequest.getOrderNo());
                        reqMsg.setVerifyCode(balanceRechargeFormRequest.getVerifyCode());
                        reqMsg.setTerminalType(balanceRechargeFormRequest.getTerminalType());
                        reqMsg.setIsBind(Constants.IS_BIND_YES);   // 已绑卡
                        reqMsg.setTransType(Constants.TRANS_TYPE_TOP_UP);    // 充值
                        reqMsg.setPlaceOrder(Constants.PLACE_ORDER_FORMAL);   // 正式下单
                        ResMsg_RegularBuy_Order order = appAssetBusinessService.rechargeOrder(reqMsg);

                        if (ConstantUtil.BSRESCODE_SUCCESS.equals(order.getResCode())) {
                            balanceRechargeFormResponse.setSuccess(true);
                        } else {
                            throw new OpenException(order.getResCode(), order.getResMsg());
                        }
                    }
                });
    }




    /**
     * 充值，绑卡用户正式下单
     *
     * @param req
     * @param resp
     * @return
     */
    @ResponseBody
    @RequestMapping("balance_recharge_form/v_1.0.1")
    public String balanceRechargeForm_1(HttpServletRequest req,
            HttpServletResponse resp) {
        return this.execute(req, BalanceRechargeFormRequest.class,
                new ControllerCallBack() {
                    @Override
                    public void doCheck(Request request, Response response) {
                        BalanceRechargeFormRequest balanceRechargeFormRequest = (BalanceRechargeFormRequest) request;
                        Integer userId = balanceRechargeFormRequest.getUserId();
                        Double amount = balanceRechargeFormRequest.getAmount();
                        Integer bankId = balanceRechargeFormRequest.getBankId();
                        String orderNo = balanceRechargeFormRequest.getOrderNo();
                        String verifyCode = balanceRechargeFormRequest.getVerifyCode();

                        if (balanceRechargeFormRequest.getTerminalType() == null) {
                            throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(), OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
                        }
                        if (userId == null) {
                            throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
                        }
                        if (amount == null) {
                            throw new OpenException(OpenMessageEnum.BALANCE_RECHAGE_AMOUNT_EMPTY.getCode(),OpenMessageEnum.BALANCE_RECHAGE_AMOUNT_EMPTY.getMessage());
                        }
                        if (bankId == null) {
                            throw new OpenException(OpenMessageEnum.BALANCE_RECHAGE_BANK_EMPTY.getCode(),OpenMessageEnum.BALANCE_RECHAGE_BANK_EMPTY.getMessage());
                        }
                        if (StringUtil.isBlank(orderNo)) {
                            throw new OpenException(OpenMessageEnum.BALANCE_RECHAGE_ORDERNO_EMPTY.getCode(),OpenMessageEnum.BALANCE_RECHAGE_ORDERNO_EMPTY.getMessage());
                        }
                        if (StringUtil.isBlank(verifyCode)) {
                            throw new OpenException(OpenMessageEnum.BALANCE_RECHAGE_VERIFYCODE_EMPTY.getCode(),OpenMessageEnum.BALANCE_RECHAGE_VERIFYCODE_EMPTY.getMessage());
                        }
                        if (amount < 1 ) {// 充值金额必须大于等于1
                            throw new OpenException(OpenMessageEnum.RECHARGE_AMOUNT_IS_NOT_SPECIFICATION.getCode(),OpenMessageEnum.RECHARGE_AMOUNT_IS_NOT_SPECIFICATION.getMessage());
                        }
                    }

                    @Override
                    public void doOperation(Request request, Response response) {
                        BalanceRechargeFormResponse balanceRechargeFormResponse = (BalanceRechargeFormResponse) response;
                        BalanceRechargeFormRequest balanceRechargeFormRequest = (BalanceRechargeFormRequest) request;

                        ReqMsg_RegularBuy_Order reqMsg = new ReqMsg_RegularBuy_Order();
                        reqMsg.setVersion("1.0.0");
                        reqMsg.setAmount(balanceRechargeFormRequest.getAmount());
                        reqMsg.setBankId(balanceRechargeFormRequest.getBankId());
                        reqMsg.setUserId(balanceRechargeFormRequest.getUserId());
                        reqMsg.setOrderNo(balanceRechargeFormRequest.getOrderNo());
                        reqMsg.setVerifyCode(balanceRechargeFormRequest.getVerifyCode());
                        reqMsg.setTerminalType(balanceRechargeFormRequest.getTerminalType());
                        reqMsg.setIsBind(Constants.IS_BIND_YES);   // 已绑卡
                        reqMsg.setTransType(Constants.TRANS_TYPE_TOP_UP);    // 充值
                        reqMsg.setPlaceOrder(Constants.PLACE_ORDER_FORMAL);   // 正式下单
                        ResMsg_RegularBuy_Order order = appAssetBusinessService.rechargeOrder(reqMsg);

                        if (ConstantUtil.BSRESCODE_SUCCESS.equals(order.getResCode())) {
                            balanceRechargeFormResponse.setSuccess(true);

                            //返回充值时间
                            SimpleDateFormat formatterMonth = new SimpleDateFormat("YYYY年MM月dd日 HH:mm:ss");
							String dateString = formatterMonth.format(new Date());
                            balanceRechargeFormResponse.setRechargeTime(dateString);
                        } else {
                            throw new OpenException(order.getResCode(), order.getResMsg());
                        }
                    }
                });
    }
	// =================================== 充值 结束 =======================================


	// =================================== 安全中心校验交易密码 开始 =======================================

	/**
	 *
	 * 校验交易密码
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
    @RequestMapping("/safe/checkPayPassword/v_1.0.0")
    public String checkPayPassword(HttpServletRequest req,
            HttpServletResponse resp) {
        return this.execute(req, CheckPayPasswordRequest.class, new ControllerCallBack() {

            @Override
            public void doCheck(Request request, Response response) {
				/**
				 * private Integer userId; //用户ID
				 */
            	CheckPayPasswordRequest checkPayPasswordRequest = (CheckPayPasswordRequest) request;
				Integer userId = checkPayPasswordRequest
						.getUserId();
				String  payPassword = checkPayPasswordRequest.getPayPassword();

				if (userId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}

				if(!CheckUtil.ParamCheck(checkPayPasswordRequest, "userId","payPassword")) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}

				if(payPassword.length() < 6 || payPassword.length() > 16 || !CheckUtil.RegCheck(payPassword, "^[0-9a-zA-Z_]+$")) {
					throw new OpenException(OpenMessageEnum.PASSWORD_NOT_NORM.getCode(),OpenMessageEnum.PASSWORD_NOT_NORM.getMessage());
				}
            }

            @Override
            public void doOperation(Request request, Response response) {
                CheckPayPasswordRequest checkPayPasswordRequest = (CheckPayPasswordRequest)request;
                CheckPayPasswordResponse checkPayPasswordResponse = (CheckPayPasswordResponse)response;

				//参数设置
				ReqMsg_User_CheckPayPassword reqCheckPayPassword = new ReqMsg_User_CheckPayPassword();
				reqCheckPayPassword.setVersion("1.0.0");
				reqCheckPayPassword.setUserId(checkPayPasswordRequest.getUserId());
				reqCheckPayPassword.setPayPassword(checkPayPasswordRequest.getPayPassword());
				//查询操作
				ResMsg_User_CheckPayPassword resCheckPayPassword = (ResMsg_User_CheckPayPassword) appAssetBusinessService.checkPayPassword(reqCheckPayPassword);
				//返回数据处理
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(resCheckPayPassword.getResCode())) {
					/**
					 	private boolean truePayPassword;
					    private Integer failNums;
					    private String toastMsg;
					 */
					checkPayPasswordResponse.setTruePayPassword(resCheckPayPassword.isTruePayPassword());
					checkPayPasswordResponse.setFailNums(resCheckPayPassword.getFailNums());
					checkPayPasswordResponse.setToastMsg(resCheckPayPassword.getToastMsg());
				}
				else {
					if ("910031".equals(resCheckPayPassword.getResCode())) {
						checkPayPasswordResponse.setTruePayPassword(resCheckPayPassword.isTruePayPassword());
						checkPayPasswordResponse.setFailNums(resCheckPayPassword.getFailNums());
						checkPayPasswordResponse.setToastMsg(resCheckPayPassword.getToastMsg());
					}else {
						throw new OpenException(resCheckPayPassword.getResCode(),resCheckPayPassword.getResMsg());
					}
				}
            }
        });
    }

    // =================================== 安全中心校验交易密码 结束 =======================================

	// 我的资产
	@ResponseBody
	@RequestMapping("total_assets/v_1.0.0")
	public String totalAssets(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, TotalAssetsRequest.class,
				new ControllerCallBack() {

					@Override
					public void doCheck(Request request, Response response) {
						TotalAssetsRequest totalAssetsRequest = (TotalAssetsRequest) request;
						Integer userId = totalAssetsRequest.getUserId();
						if (userId == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						TotalAssetsResponse totalAssetsResponse = (TotalAssetsResponse) response;
						TotalAssetsRequest totalAssetsRequest = (TotalAssetsRequest) request;

						// 查询
						ReqMsg_User_AssetInfoQuery  reqAssetInfoQuery = new ReqMsg_User_AssetInfoQuery();
						reqAssetInfoQuery.setVersion("1.0.0");
						reqAssetInfoQuery.setUserId(totalAssetsRequest.getUserId());
						ResMsg_User_AssetInfoQuery resAssetInfoQuery = (ResMsg_User_AssetInfoQuery) appAssetBusinessService.assetInfoQuery(reqAssetInfoQuery);
						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resAssetInfoQuery.getResCode())) {

							/**
							 * 	private Double assetAmount; //账户总资产
								private Double regularAmount; //投资本金
								private Double investEarnings; //投资收益
								private Double balance; //账户余额
								private Double jljBalance; //我的奖励
							 */
							totalAssetsResponse.setAssetAmount(resAssetInfoQuery.getAssetAmount());
							totalAssetsResponse.setRegularAmount(resAssetInfoQuery.getRegularAmount());
							totalAssetsResponse.setInvestEarnings(resAssetInfoQuery.getInvestEarnings());
							totalAssetsResponse.setBalance(resAssetInfoQuery.getBalance());
							totalAssetsResponse.setJljBalance(resAssetInfoQuery.getJljBalance());
						}
						else {
							throw new OpenException(resAssetInfoQuery.getResCode(),resAssetInfoQuery.getResMsg());
						}

					}
				});
	}


	// 我的资产
	@ResponseBody
	@RequestMapping("total_assets/v_1.0.1")
	public String totalAssets_1(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, TotalAssetsRequest.class,
				new ControllerCallBack() {

					@Override
					public void doCheck(Request request, Response response) {
						TotalAssetsRequest totalAssetsRequest = (TotalAssetsRequest) request;
						Integer userId = totalAssetsRequest.getUserId();
						if (userId == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						TotalAssetsResponse totalAssetsResponse = (TotalAssetsResponse) response;
						TotalAssetsRequest totalAssetsRequest = (TotalAssetsRequest) request;

						// 查询
						ReqMsg_User_AssetInfoQuery  reqAssetInfoQuery = new ReqMsg_User_AssetInfoQuery();
						reqAssetInfoQuery.setVersion("1.0.1");
						reqAssetInfoQuery.setUserId(totalAssetsRequest.getUserId());
						ResMsg_User_AssetInfoQuery resAssetInfoQuery = (ResMsg_User_AssetInfoQuery) appAssetBusinessService.assetInfoQuery(reqAssetInfoQuery);
						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resAssetInfoQuery.getResCode())) {

							/**
							 * 	private Double assetAmount; //账户总资产
								private Double regularAmount; //投资本金
								private Double investEarnings; //投资收益
								private Double balance; //账户余额
								private Double jljBalance; //我的奖励
							 */
							totalAssetsResponse.setAssetAmount(resAssetInfoQuery.getAssetAmount());
							totalAssetsResponse.setRegularAmount(resAssetInfoQuery.getRegularAmount());
							totalAssetsResponse.setInvestEarnings(resAssetInfoQuery.getInvestEarnings());
							totalAssetsResponse.setBalance(resAssetInfoQuery.getBalance());
							totalAssetsResponse.setJljBalance(resAssetInfoQuery.getJljBalance());
							totalAssetsResponse.setDepBalance(resAssetInfoQuery.getDepBalance());
							totalAssetsResponse.setDepAccountStatus(resAssetInfoQuery.getDepAccountStatus());
							totalAssetsResponse.setDepFreezeBalance(resAssetInfoQuery.getDepFreezeBalance());
							totalAssetsResponse.setFreezeBalance(resAssetInfoQuery.getFreezeBalance());
						}
						else {
							throw new OpenException(resAssetInfoQuery.getResCode(),resAssetInfoQuery.getResMsg());
						}

					}
				});
	}

	//消息列表
	@ResponseBody
	@RequestMapping("messageList/v_1.0.0")
	public String messageList(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, MessageListRequest.class, new ControllerCallBack(){

			@Override
			public void doCheck(Request request, Response response) {
				MessageListRequest req = (MessageListRequest) request;
				Integer userId = req.getUserId();
				Integer page = req.getPage();
				if (userId == null || page == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				MessageListRequest req = (MessageListRequest) request;
				MessageListResponse resp = (MessageListResponse) response;
				ReqMsg_User_MessageList listRequest = new ReqMsg_User_MessageList();
				listRequest.setUserId(req.getUserId());
				listRequest.setPage(req.getPage());
				listRequest.setVersion("1.0.0");
				ResMsg_User_MessageList listResponse = appAssetBusinessService.messageList(listRequest);
				List<Message> list = new ArrayList<Message>();
				List<Map<String, Object>> temp = listResponse.getDataLst();
				for(Map<String,Object> map : temp) {
					Message m = new Message();
					Integer pushType = (Integer)map.get("pushType");
					m.setId((Integer)map.get("id"));
					m.setPushAbstract((String)map.get("pushAbstract"));
					m.setPushTime((String)map.get("pushTime"));
					m.setPushType(pushType);
					m.setTitle((String)map.get("title"));
					//推送类型为url
					if(pushType == 1) {
						m.setUrl((String)map.get("url"));
					}
					//推送类型为app内页
					if(pushType == 3) {
						m.setAppPage((Integer)map.get("appPage"));
					}
					list.add(m);
				}
				resp.setDataList(list);
				resp.setTotalPage(listResponse.getTotalPage());
			}

		});
	}

	//消息详情
	@ResponseBody
	@RequestMapping("messageInfo/v_1.0.0")
	public String messageInfo(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, MessageInfoRequest.class, new ControllerCallBack(){

			@Override
			public void doCheck(Request request, Response response) {
				MessageInfoRequest req = (MessageInfoRequest) request;
				Integer id = req.getId();
				if (id == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				MessageInfoRequest req = (MessageInfoRequest) request;
				MessageInfoResponse resp = (MessageInfoResponse) response;
				ReqMsg_User_MessageInfo infoRequest = new ReqMsg_User_MessageInfo();
				infoRequest.setId(req.getId());
				infoRequest.setVersion("1.0.0");
				ResMsg_User_MessageInfo infoResponse = appAssetBusinessService.messageInfo(infoRequest);
				resp.setContent(infoResponse.getContent());
				resp.setPushTime(infoResponse.getPushTime());
				resp.setTitle(infoResponse.getTitle());
			}

		});
	}

	//我的红包列表（包含了购买时候的匹配红包-此接口多用）
	@ResponseBody
	@RequestMapping("myRedPacketList/v_1.0.0")
	public String myRedPacketList(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, MyRedPacketListRequest.class, new ControllerCallBack(){

			@Override
			public void doCheck(Request request, Response response) {
				MyRedPacketListRequest req = (MyRedPacketListRequest) request;
				Integer userId = req.getUserId();
				if (userId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				MyRedPacketListRequest req = (MyRedPacketListRequest) request;
				MyRedPacketListResponse resp = (MyRedPacketListResponse) response;
				ReqMsg_RedPacketInfo_RedPacketList redRequest = new ReqMsg_RedPacketInfo_RedPacketList();
				redRequest.setUserId(req.getUserId());
				redRequest.setStatus(req.getStatus());
				redRequest.setAmount(req.getAmount());
				redRequest.setProductId(req.getProductId());
				redRequest.setVersion("1.0.0");
				ResMsg_RedPacketInfo_RedPacketList redResponse = appAssetBusinessService.redPacketList(redRequest);
				List<RedPacket> redList = new ArrayList<RedPacket>();
				ArrayList<HashMap<String, Object>> temp = redResponse.getDataGrid();
				for(Map<String,Object> map : temp) {
					RedPacket red = new RedPacket();
					red.setId((Integer)map.get("id"));
					red.setFull((Double)map.get("full"));
					red.setSerialName((String)map.get("serialName"));
					red.setStatus((String)map.get("status"));
					red.setSubtract((Double)map.get("subtract"));
					red.setUseTimeEnd(DateUtil.formatYYYYMMDD((Date)map.get("useTimeEnd")));
					red.setUseTimeStart(DateUtil.formatYYYYMMDD((Date)map.get("useTimeStart")));
					red.setTarget((String)map.get("termLimitMsg"));
					redList.add(red);
				}
				resp.setDataList(redList);
			}

		});
	}

	//我的红包列表-（单独给红包列表使用）
	@ResponseBody
	@RequestMapping("redPacketList/v_1.0.0")
	public String myRedPacketList_1_0_1(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, RedPacketListRequest.class, new ControllerCallBack(){

			@Override
			public void doCheck(Request request, Response response) {
				RedPacketListRequest req = (RedPacketListRequest) request;
				if (req.getUserId() == null || StringUtils.isEmpty(req.getStatus())) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(), OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				RedPacketListRequest req = (RedPacketListRequest) request;
				RedPacketListResponse resp = (RedPacketListResponse) response;
				ReqMsg_RedPacketInfo_QueryRedPacketList redRequest = new ReqMsg_RedPacketInfo_QueryRedPacketList();
				redRequest.setUserId(req.getUserId());
				redRequest.setStatus(req.getStatus());
				redRequest.setNumPerPage(req.getNumPerPage());
				redRequest.setPageNum(req.getPageNum());
				redRequest.setVersion("1.0.0");
				ResMsg_RedPacketInfo_QueryRedPacketList redResponse = appAssetBusinessService.queryRedPacketList(redRequest);
				List<RedPacket> redList = new ArrayList<RedPacket>();
				ArrayList<HashMap<String, Object>> temp = redResponse.getDataGrid();
				for(Map<String,Object> map : temp) {
					RedPacket red = new RedPacket();
					red.setId((Integer)map.get("id"));
					if(map.get("full") instanceof String) {
						red.setFull(Double.valueOf((String)map.get("full")));
					} else {
						red.setFull((Double)map.get("full"));
					}
					red.setSerialName((String)map.get("serialName"));
					red.setStatus((String)map.get("status"));
					if(map.get("subtract") instanceof String) {
						red.setSubtract(Double.valueOf((String)map.get("subtract")));
					} else {
						red.setSubtract((Double)map.get("subtract"));
					}
					red.setUseTimeEnd(DateUtil.formatYYYYMMDD((Date)map.get("useTimeEnd")));
					red.setUseTimeStart(DateUtil.formatYYYYMMDD((Date)map.get("useTimeStart")));
					red.setTarget((String)map.get("termLimitMsg"));
					redList.add(red);
				}
				resp.setDataList(redList);
				resp.setCount(redResponse.getCount());
			}

		});
	}

	//我的银行卡--预绑卡
	@ResponseBody
	@RequestMapping("preBindCard/v_1.0.0")
	public String preBindCard(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, BankCardBindPreRequest.class, new ControllerCallBack(){

			@Override
			public void doCheck(Request request, Response response) {

				BankCardBindPreRequest req = (BankCardBindPreRequest) request;
				Integer userId = req.getUserId();
				if (userId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				if (StringUtil.isBlank(req.getUserName())){
					throw new OpenException(OpenMessageEnum.USER_NAME_IS_EMPTY.getCode(),OpenMessageEnum.USER_NAME_IS_EMPTY.getMessage());
				}
				if (StringUtil.isBlank(req.getIdCard())){
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				if (StringUtil.isBlank(req.getCardNo())){
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				if (req.getBankId()==null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				if (StringUtil.isBlank(req.getMobile())){
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				if (StringUtil.isBlank(req.getTerminalType())){
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}

                if(!CheckUtil.RegCheck(req.getIdCard(), "(\\d{14}[0-9xX])|(\\d{17}[0-9xX])")) {
                    throw new OpenException(OpenMessageEnum.IDCARD_NOT_NORM.getCode(),OpenMessageEnum.IDCARD_NOT_NORM.getMessage());
                }
                if(req.getCardNo().length() > 20 || !CheckUtil.RegCheck(req.getCardNo(), "^[0-9]*$")) {
                    throw new OpenException(OpenMessageEnum.CARDNO_NOT_NORM.getCode(),OpenMessageEnum.CARDNO_NOT_NORM.getMessage());
                }
                if(req.getMobile().length() != 11 || !CheckUtil.RegCheck(req.getMobile(), "^[0-9]*$")) {
                    throw new OpenException(OpenMessageEnum.MOBILE_NOT_NORM.getCode(),OpenMessageEnum.MOBILE_NOT_NORM.getMessage());
                }
			}

			@Override
			public void doOperation(Request request, Response response) {
				BankCardBindPreRequest req = (BankCardBindPreRequest) request;
				BankCardBindPreResponse resp = (BankCardBindPreResponse) response;

				ReqMsg_Bank_PreBindCard bindCardRequest = new ReqMsg_Bank_PreBindCard();
				bindCardRequest.setUserId(String.valueOf(req.getUserId()));
				bindCardRequest.setUserName(req.getUserName());
				bindCardRequest.setIdCard(req.getIdCard());
				bindCardRequest.setCardNo(req.getCardNo());
				bindCardRequest.setBankId(String.valueOf(req.getBankId()));
				bindCardRequest.setMobile(req.getMobile());
				bindCardRequest.setTerminalType(req.getTerminalType());
				bindCardRequest.setVersion("1.0.0");
				ResMsg_Bank_PreBindCard bindResponse = appAssetBusinessService.preBindCard(bindCardRequest);

				if(ConstantUtil.BSRESCODE_SUCCESS.equals(bindResponse.getResCode())) {
					resp.setSuccess(true);
					resp.setOrderNo(bindResponse.getOrderNo());
				}
				else {
					resp.setSuccess(false);
					throw new OpenException(bindResponse.getResCode(),bindResponse.getResMsg());
				}

			}

		});
	}


	//我的银行卡--正式绑卡
	@ResponseBody
	@RequestMapping("bindCard/v_1.0.0")
	public String bindCard(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, BankCardBindRequest.class, new ControllerCallBack(){

			@Override
			public void doCheck(Request request, Response response) {

				BankCardBindRequest req = (BankCardBindRequest) request;
				Integer userId = req.getUserId();
				if (userId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				if (StringUtil.isBlank(req.getSmsCode())){
					throw new OpenException(OpenMessageEnum.BALANCE_RECHAGE_VERIFYCODE_EMPTY.getCode(),OpenMessageEnum.BALANCE_RECHAGE_VERIFYCODE_EMPTY.getMessage());
				}
				if (StringUtil.isBlank(req.getOrderNo())){
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}

				if(!CheckUtil.RegCheck(req.getSmsCode(), "^[0-9]*$")) {
					throw new OpenException(OpenMessageEnum.VERIFYCODE_NOT_NORM.getCode(),OpenMessageEnum.VERIFYCODE_NOT_NORM.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				BankCardBindRequest req = (BankCardBindRequest) request;
				BankCardBindResponse resp = (BankCardBindResponse) response;

				ReqMsg_Bank_BindCard bindCardRequest = new ReqMsg_Bank_BindCard();
				bindCardRequest.setUserId(String.valueOf(req.getUserId()));
				bindCardRequest.setSmsCode(req.getSmsCode());
				bindCardRequest.setOrderNo(req.getOrderNo());
				bindCardRequest.setVersion("1.0.0");
				ResMsg_Bank_BindCard redResponse = appAssetBusinessService.bindCard(bindCardRequest);

				if (ConstantUtil.BSRESCODE_SUCCESS.equals(redResponse.getResCode())) {
					resp.setSuccess(true);
				} else {
					resp.setSuccess(false);
					throw new OpenException(redResponse.getResCode(), redResponse.getResMsg());
				}
			}

		});
	}

	//回款计划列表
	@ResponseBody
	@RequestMapping("repayScheduleList/v_1.0.0")
	public String repayScheduleList(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, RepayScheduleListRequest.class, new ControllerCallBack(){

			@Override
			public void doCheck(Request request, Response response) {
				RepayScheduleListRequest req = (RepayScheduleListRequest) request;
				Integer userId = req.getUserId();
				if (userId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				RepayScheduleListRequest req = (RepayScheduleListRequest) request;
				RepayScheduleListResponse resp = (RepayScheduleListResponse) response;

				ReqMsg_RepaySchedule_RepaymentPlanList repayScheduleRequest = new ReqMsg_RepaySchedule_RepaymentPlanList();
				repayScheduleRequest.setUserId(req.getUserId());
				repayScheduleRequest.setVersion("1.0.0");
				ResMsg_RepaySchedule_RepaymentPlanList repayScheduleResponse = appAssetBusinessService.repaymentPlanList(repayScheduleRequest);

				List<CashTransferSchemesVO> repayScheduleList = new ArrayList<CashTransferSchemesVO>();
				ArrayList<HashMap<String, Object>> temp = repayScheduleResponse.getRepaymentPlanList();
				for(Map<String,Object> map : temp) {
					CashTransferSchemesVO cashTransferSchemes = new CashTransferSchemesVO();
					cashTransferSchemes.setPlanDate((String) map.get("planDate"));
					cashTransferSchemes.setRepayScheduleTotalAmount((Double) map.get("repayScheduleTotalAmount"));
					cashTransferSchemes.setPlanTotalRepaied((Double) map.get("planTotalRepaied"));
					repayScheduleList.add(cashTransferSchemes);
				}
				resp.setDataList(repayScheduleList);
			}

		});
	}

	//回款计划详情
	@ResponseBody
	@RequestMapping("repayScheduleDetails/v_1.0.0")
	public String repayScheduleDetails(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, RepayScheduleDetailsRequest.class, new ControllerCallBack(){

			@Override
			public void doCheck(Request request, Response response) {
				RepayScheduleDetailsRequest req = (RepayScheduleDetailsRequest) request;
				Integer userId = req.getUserId();
				String planDate = req.getPlanDate();
				if (userId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				if (StringUtil.isBlank(planDate)) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				RepayScheduleDetailsRequest req = (RepayScheduleDetailsRequest) request;
				RepayScheduleDetailsResponse resp = (RepayScheduleDetailsResponse) response;

				ReqMsg_RepaySchedule_RepayScheduleDetails detailsRequest = new ReqMsg_RepaySchedule_RepayScheduleDetails();
				detailsRequest.setUserId(req.getUserId());
				detailsRequest.setPlanDate(req.getPlanDate());
				detailsRequest.setVersion("1.0.0");
				ResMsg_RepaySchedule_RepayScheduleDetails detailsResponse = appAssetBusinessService.repayScheduleDetails(detailsRequest);

				List<CashTransferSchemesVO> repayScheduleList = new ArrayList<CashTransferSchemesVO>();
				ArrayList<HashMap<String, Object>> temp = detailsResponse.getRepayScheduleDetailsList();
				//已还款的计划金额
				Double planTotalRepaied = 0d;
				//待回款金额
				Double receivableAmount = 0d;
				for(Map<String,Object> map : temp) {
					CashTransferSchemesVO cashTransferSchemes = new CashTransferSchemesVO();
					planTotalRepaied = (Double) map.get("planTotalRepaied");
					receivableAmount = (Double) map.get("receivableAmount");
					cashTransferSchemes.setPlanDate((String) map.get("planDate"));
					cashTransferSchemes.setRepayScheduleTotalAmount((Double) map.get("repayScheduleTotalAmount"));
					cashTransferSchemes.setPlanTotalRepaied((Double) map.get("planTotalRepaied"));
					/**
					 * 待回款=0，为已还完；
					 * 待回款>0 & 已回款>0，为部分回款；
					 * 待回款>0 & 已回款=0，为待回款
					 */
					if(receivableAmount == 0d) {
						cashTransferSchemes.setRepayStatus("已还完");
					} else if(receivableAmount > 0 && planTotalRepaied > 0) {
						cashTransferSchemes.setRepayStatus("部分回款");
					} else if(receivableAmount > 0 && planTotalRepaied == 0d) {
						cashTransferSchemes.setRepayStatus("待回款");
					}
					repayScheduleList.add(cashTransferSchemes);
				}

				if(repayScheduleList != null && repayScheduleList.size() > 0) {
					CashTransferSchemesVO vo = new CashTransferSchemesVO();
					//计划回款金额合计
					Double sumRepayScheduleAmount = 0d;
					//已还款计划金额合计
					Double sumPlanTotalRepaied = 0d;

					for(CashTransferSchemesVO obj : repayScheduleList) {
						sumRepayScheduleAmount += obj.getRepayScheduleTotalAmount();
						sumPlanTotalRepaied += obj.getPlanTotalRepaied();
					}
					vo.setPlanDate("合计");
					vo.setRepayScheduleTotalAmount(sumRepayScheduleAmount);
					vo.setPlanTotalRepaied(sumPlanTotalRepaied);
					vo.setRepayStatus("");
					repayScheduleList.add(vo);
				}
				resp.setDataList(repayScheduleList);
			}
		});
	}



	//我的资产_获取激活页面数据_存管改造 v_1.0.0
	@ResponseBody
	@RequestMapping("activate_page_info/v_1.0.0")
	public String activatePageInfo(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, ActivatePageInfoRequest.class, new ControllerCallBack(){

			@Override
			public void doCheck(Request request, Response response) {
				ActivatePageInfoRequest req = (ActivatePageInfoRequest) request;
				Integer userId = req.getUserId();

				if (userId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}

			}

			@Override
			public void doOperation(Request request, Response response) {
				ActivatePageInfoRequest req = (ActivatePageInfoRequest) request;
				ActivatePageInfoResponse resp = (ActivatePageInfoResponse) response;

				ReqMsg_User_ActivatePageInfo activatePageInfoRequest = new ReqMsg_User_ActivatePageInfo();
				activatePageInfoRequest.setUserId(req.getUserId());
				activatePageInfoRequest.setVersion("1.0.0");
				ResMsg_User_ActivatePageInfo activatePageInfoResponse = appAssetBusinessService.activatePageInfo(activatePageInfoRequest);

				if (ConstantUtil.BSRESCODE_SUCCESS.equals(activatePageInfoResponse.getResCode())) {
					resp.setMobile(activatePageInfoResponse.getMobile());
					resp.setUserName(activatePageInfoResponse.getUserName());
					resp.setIdCard(activatePageInfoResponse.getIdCard());
					resp.setBankCard(activatePageInfoResponse.getBankCard());
					resp.setBankId(activatePageInfoResponse.getBankId());
					resp.setBankName(activatePageInfoResponse.getBankName());
					resp.setSmallLogo(activatePageInfoResponse.getSmallLogo());
					resp.setLargeLogo(activatePageInfoResponse.getLargeLogo());
					resp.setDepAccount(activatePageInfoResponse.getDepAccount());
				} else {
					resp.setSuccess(false);
					throw new OpenException(activatePageInfoResponse.getResCode(), activatePageInfoResponse.getResMsg());
				}


			}
		});
	}


	//我的资产_激活存管账户 v_1.0.0
	@ResponseBody
	@RequestMapping("activate_dep_account/v_1.0.0")
	public String activateDepAccount(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, ActivateDepAccountRequest.class, new ControllerCallBack(){

			@Override
			public void doCheck(Request request, Response response) {
				ActivateDepAccountRequest req = (ActivateDepAccountRequest) request;
				Integer userId = req.getUserId();
				if (userId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				String mobileCode = req.getMobileCode();
				if (StringUtil.isBlank(mobileCode)) {
                    throw new OpenException(OpenMessageEnum.BALANCE_RECHAGE_VERIFYCODE_EMPTY.getCode(), OpenMessageEnum.BALANCE_RECHAGE_VERIFYCODE_EMPTY.getMessage());
                }

			}

			@Override
			public void doOperation(Request request, Response response) {
				ActivateDepAccountRequest req = (ActivateDepAccountRequest) request;
				ActivateDepAccountResponse resp = (ActivateDepAccountResponse) response;

				ReqMsg_User_ActivateDepAccount activateDepAccountRequest = new ReqMsg_User_ActivateDepAccount();
				activateDepAccountRequest.setUserId(req.getUserId());
				activateDepAccountRequest.setMobileCode(req.getMobileCode());
				activateDepAccountRequest.setVersion("1.0.0");
				ResMsg_User_ActivateDepAccount activateDepAccountResponse = appAssetBusinessService.activateDepAccount(activateDepAccountRequest);

				if (ConstantUtil.BSRESCODE_SUCCESS.equals(activateDepAccountResponse.getResCode())) {

				} else {
					resp.setSuccess(false);
					throw new OpenException(activateDepAccountResponse.getResCode(), activateDepAccountResponse.getResMsg());
				}


			}
		});
	}


	//我的奖励金收益_奖励金转出页面数据_存管改造 v_1.0.0
	@ResponseBody
	@RequestMapping("my_bonus_withdraw_info/v_1.0.0")
	public String myBonusWithdrawInfo(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, MyBonusWithdrawInfoRequest.class, new ControllerCallBack(){

			@Override
			public void doCheck(Request request, Response response) {
				MyBonusWithdrawInfoRequest req = (MyBonusWithdrawInfoRequest) request;
				Integer userId = req.getUserId();
				if (userId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {

				MyBonusWithdrawInfoRequest withdrawInfoRequest = (MyBonusWithdrawInfoRequest) request;
				MyBonusWithdrawInfoResponse withdrawInfoResponse = (MyBonusWithdrawInfoResponse) response;

                appAssetBusinessService.myBonusWithdrawInfo(withdrawInfoRequest, withdrawInfoResponse);
			}
		});
	}

	//我的奖励金收益_奖励金转出页面数据_存管改造 v_1.0.1
	@ResponseBody
	@RequestMapping("my_bonus_withdraw_info/v_1.0.1")
	public String myBonusWithdrawInfo_1_0_1(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, BonusWithdrawInfoRequest.class, new ControllerCallBack(){

			@Override
			public void doCheck(Request request, Response response) {
				BonusWithdrawInfoRequest req = (BonusWithdrawInfoRequest) request;
				Integer userId = req.getUserId();
				if (userId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {

				BonusWithdrawInfoRequest withdrawInfoRequest = (BonusWithdrawInfoRequest) request;
				BonusWithdrawInfoResponse withdrawInfoResponse = (BonusWithdrawInfoResponse) response;

				MyBonusWithdrawInfoRequest myWithdrawInfoRequest = new MyBonusWithdrawInfoRequest();
				myWithdrawInfoRequest.setUserId(withdrawInfoRequest.getUserId());
				MyBonusWithdrawInfoResponse myWithdrawInfoResponse = new MyBonusWithdrawInfoResponse();
				appAssetBusinessService.myBonusWithdrawInfo(myWithdrawInfoRequest, myWithdrawInfoResponse);

				withdrawInfoResponse.setCardId(myWithdrawInfoResponse.getCardId());
				withdrawInfoResponse.setCardNo(myWithdrawInfoResponse.getCardNo());
				withdrawInfoResponse.setBankName(myWithdrawInfoResponse.getBankName());
				withdrawInfoResponse.setWithdrawTimes(myWithdrawInfoResponse.getWithdrawTimes());
				withdrawInfoResponse.setSmallLogo(myWithdrawInfoResponse.getSmallLogo());
				withdrawInfoResponse.setLargeLogo(myWithdrawInfoResponse.getLargeLogo());
				withdrawInfoResponse.setCanWithdraw(format(myWithdrawInfoResponse.getCanWithdraw()));
				withdrawInfoResponse.setLimitWithdraw(format(myWithdrawInfoResponse.getLimitWithdraw()));
				withdrawInfoResponse.setTotalAmount(format(myWithdrawInfoResponse.getTotalAmount()));
				withdrawInfoResponse.setSingleWithdrawUpperLimit(format(myWithdrawInfoResponse.getSingleWithdrawUpperLimit()));
				withdrawInfoResponse.setDayWithdrawUpperLimit(format(myWithdrawInfoResponse.getDayWithdrawUpperLimit()));
				withdrawInfoResponse.setUserWithdrawAmount(format(myWithdrawInfoResponse.getUserWithdrawAmount()));
			}

			private String format(Double amount) {
				return new DecimalFormat("0.00").format(MoneyUtil.round(new BigDecimal(amount), 2, RoundingMode.HALF_UP.ordinal()));
			}

		});
	}


	/**
	 * 我的奖励金收益_奖励金转出_存管改造 v_1.0.0
	 *
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
	@RequestMapping("my_bonus_withdraw/v_1.0.0")
	public String myBonusWithdraw(HttpServletRequest req,
			HttpServletResponse resp) {
		return this.execute(req, MyBonusWithdrawRequest.class,
				new ControllerCallBack() {
					@Override
					public void doCheck(Request request, Response response) {
						MyBonusWithdrawRequest myBonusWithdrawRequest = (MyBonusWithdrawRequest) request;
						if(myBonusWithdrawRequest.getCardId() == null || myBonusWithdrawRequest.getCardId() == 0) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(), "未绑定回款银行卡");
						}
						if(!CheckUtil.ParamCheck(myBonusWithdrawRequest, "userId", "cardId", "bonusAmount","payPassword", "terminalType")) {
						    throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						MyBonusWithdrawResponse myBonusWithdrawResponse = (MyBonusWithdrawResponse) response;
						MyBonusWithdrawRequest myBonusWithdrawRequest = (MyBonusWithdrawRequest) request;

						ReqMsg_UserWithdraw_MyBonusWithdraw req = new ReqMsg_UserWithdraw_MyBonusWithdraw();
						req.setVersion("1.0.0");
						req.setBonusAmount(myBonusWithdrawRequest.getBonusAmount());
						req.setUserId(myBonusWithdrawRequest.getUserId());
						req.setPayPassword(myBonusWithdrawRequest.getPayPassword());
						req.setTerminalType(myBonusWithdrawRequest.getTerminalType());
						req.setCardId(myBonusWithdrawRequest.getCardId());
						ResMsg_UserWithdraw_MyBonusWithdraw res = appAssetBusinessService.myBonusWithdraw(req);
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
							myBonusWithdrawResponse.setSuccess(true);
						    myBonusWithdrawResponse.setOrderNo(res.getOrderNo());
							myBonusWithdrawResponse.setWithdrawTime(res.getWithdrawTime());
						} else {
							myBonusWithdrawResponse.setSuccess(false);
						    throw new OpenException(res.getResCode(), res.getResMsg());
						}




					}
				});
	}


	/**
	 * 优惠券兼容加息券列表、红包列表以及购买的时候所需要的红包和加息券 v_1.0.0
	 *
	 * @param req
	 * @param res
	 * @return
	 */
	@ResponseBody
	@RequestMapping("tickets/v_1.0.0")
	public String tickets(HttpServletRequest req, HttpServletResponse res) {
		return this.execute(req, TicketListRequest.class, new ControllerCallBack() {
			@Override
			public void doCheck(Request request, Response response) {
				TicketListRequest ticketListRequest = (TicketListRequest) request;
				if(ticketListRequest.getUserId() == null) {
					throw new OpenException(OpenMessageEnum.USER_ID_EMPTY.getCode(), OpenMessageEnum.USER_ID_EMPTY.getMessage());
				}
				if(StringUtils.isEmpty(ticketListRequest.getBizType()) || StringUtils.isEmpty(ticketListRequest.getType())) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(), OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				TicketListResponse ticketListResponse = (TicketListResponse) response;
				TicketListRequest ticketListRequest = (TicketListRequest) request;

				ReqMsg_Ticket_TicketList req = new ReqMsg_Ticket_TicketList();
				req.setVersion("1.0.0");
				req.setAmount(ticketListRequest.getAmount());
				req.setUserId(ticketListRequest.getUserId());
				req.setBizType(ticketListRequest.getBizType());
				req.setType(ticketListRequest.getType());
				req.setStatus(ticketListRequest.getStatus());
				req.setNumPerPage(ticketListRequest.getNumPerPage());
				req.setPageNum(ticketListRequest.getPageNum());
				req.setProductId(ticketListRequest.getProductId());
				ResMsg_Ticket_TicketList res = appAssetBusinessService.ticketList(req);

				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					ticketListResponse.setCount(res.getCount());
					ticketListResponse.setUserId(res.getUserId());
					ticketListResponse.setType(res.getType());
					ticketListResponse.setIsSupportRed(res.getIsSupportRed());
					ticketListResponse.setIsSupportInterestTicket(res.getIsSupportInterestTicket());
					List<Ticket> tickets = new ArrayList<Ticket>();
					if(!CollectionUtils.isEmpty(res.getDataList())) {
						for(HashMap<String, Object> map: res.getDataList()) {
							Ticket ticket = new Ticket();
							ticket.setTicketId((Integer)map.get("id"));
							ticket.setType(req.getType());
							ticket.setStatus((String)map.get("status"));
							if(map.get("rate") instanceof String) {
								ticket.setRate(GatewayMoneyUtil.format2Point((String)map.get("rate")));
							} else if(map.get("rate") instanceof Double) {
								ticket.setRate(GatewayMoneyUtil.format2Point(String.valueOf((Double)map.get("rate"))));
							}
							if(map.get("full") instanceof String) {
								ticket.setFull(GatewayMoneyUtil.format2Point((String)map.get("full")));
							} else if(map.get("full") instanceof Double) {
								ticket.setFull(GatewayMoneyUtil.format2Point((Double)map.get("full")));
							}
							if(map.get("subtract") instanceof String) {
								ticket.setSubtract(GatewayMoneyUtil.format2Point((String)map.get("subtract")));
							} else if(map.get("subtract") instanceof Double) {
								ticket.setSubtract(GatewayMoneyUtil.format2Point((Double)map.get("subtract")));
							}
							if(map.get("useTime") instanceof String) {
								ticket.setUseTime((String)map.get("useTime"));
							} else if(map.get("useTime") instanceof Date) {
								ticket.setUseTime(DateUtil.formatDateTime((Date)map.get("useTime"), "yyyy-MM-dd HH:mm:ss"));
							}
							ticket.setSerialName((String)map.get("serialName"));
							if(map.get("useTimeStart") instanceof String) {
								ticket.setUseTimeStart((String)map.get("useTimeStart"));
							} else if(map.get("useTimeStart") instanceof Date) {
								ticket.setUseTimeStart(DateUtil.formatDateTime((Date)map.get("useTimeStart"), "yyyy-MM-dd"));
							}
							if(map.get("useTimeEnd") instanceof String) {
								ticket.setUseTimeEnd((String)map.get("useTimeEnd"));
							} else if(map.get("useTimeEnd") instanceof Date) {
								ticket.setUseTimeEnd(DateUtil.formatDateTime((Date)map.get("useTimeEnd"), "yyyy-MM-dd"));
							}
							ticket.setSerialName((String)map.get("serialName"));
							ticket.setTarget((String)map.get("termLimit"));
							ticket.setProductLimit((String)map.get("productLimit"));
							ticket.setProductName((String)map.get("productName"));
							tickets.add(ticket);
						}
					}
					ticketListResponse.setDataList(tickets);
				} else {
					throw new OpenException(res.getResCode(), res.getResMsg());
				}

			}
		});
	}
	
	/**
	 * 我的银行卡-解绑前校验
	 * @author bianyatian
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
	@RequestMapping("unbindCheck/v_1.0.0")
	public String unbindCheck(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, BankCardUnbindCheckRequest.class, new ControllerCallBack(){

			@Override
			public void doCheck(Request request, Response response) {
				
				BankCardUnbindCheckRequest req = (BankCardUnbindCheckRequest) request;
				Integer userId = req.getUserId();
				Integer bankCardId = req.getBankCardId();
				if (userId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				if (bankCardId == null){
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}
			
			@Override
			public void doOperation(Request request, Response response) {
				BankCardUnbindCheckRequest req = (BankCardUnbindCheckRequest) request;
				BankCardUnbindCheckResponse resp = (BankCardUnbindCheckResponse) response;
				
				ReqMsg_Bank_UnbindCheck unbindCheckRequest = new ReqMsg_Bank_UnbindCheck();
				unbindCheckRequest.setUserId(req.getUserId());
				unbindCheckRequest.setBankCardId(req.getBankCardId());
				unbindCheckRequest.setVersion("1.0.0");
				log.info("==============【我的银行卡-解绑前校验】入参：UserId="+req.getUserId()+";BankCardId="+req.getBankCardId()+"============");
				ResMsg_Bank_UnbindCheck redResponse = appAssetBusinessService.unBindCheck(unbindCheckRequest);
				log.info("==============【我的银行卡-解绑前校验】返回：CheckResult="+redResponse.getCheckResult()+";UserName="+redResponse.getUserName()+";IdCard="+redResponse.getIdCard()+"============");
				if (ConstantUtil.BSRESCODE_SUCCESS.equals(redResponse.getResCode())) {
					resp.setCheckResult(redResponse.getCheckResult());
					resp.setUserName(redResponse.getUserName());
					resp.setIdCard(redResponse.getIdCard());
					resp.setSuccess(true);
				} else {
					resp.setSuccess(false);
					throw new OpenException(redResponse.getResCode(), redResponse.getResMsg());
				}
			}
			
		});
	}


	/**
	 * 解绑卡申请_人脸核身验证v_1.0.0
	 *
	 * @param req
	 * @param res
	 * @return
	 */
	@ResponseBody
	@RequestMapping("unbind_apply_police_verify/v_1.0.0")
	public String unbindApplyPoliceVerify(HttpServletRequest req, HttpServletResponse res) {
		return this.execute(req, UnbindApplyPoliceVerifyRequest.class, new ControllerCallBack() {
			@Override
			public void doCheck(Request request, Response response) {
				UnbindApplyPoliceVerifyRequest unbindApplyPoliceVerifyRequest = (UnbindApplyPoliceVerifyRequest) request;
				if(unbindApplyPoliceVerifyRequest.getUserId() == null) {
					throw new OpenException(OpenMessageEnum.USER_ID_EMPTY.getCode(), OpenMessageEnum.USER_ID_EMPTY.getMessage());
				}
				if(StringUtils.isEmpty(unbindApplyPoliceVerifyRequest.getBankId()) || StringUtils.isEmpty(unbindApplyPoliceVerifyRequest.getVerifyResult())
						|| StringUtils.isEmpty(unbindApplyPoliceVerifyRequest.getIdCardVerifyResult())) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(), OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				UnbindApplyPoliceVerifyRequest unbindApplyPoliceVerifyRequest = (UnbindApplyPoliceVerifyRequest) request;
				UnbindApplyPoliceVerifyResponse unbindApplyPoliceVerifyResponse = (UnbindApplyPoliceVerifyResponse) response;

				ReqMsg_Bank_UnbindApplyPoliceVerify req = new ReqMsg_Bank_UnbindApplyPoliceVerify();
				req.setVersion("1.0.0");
				req.setUserId(unbindApplyPoliceVerifyRequest.getUserId());
				req.setBankId(unbindApplyPoliceVerifyRequest.getBankId());
				req.setVerifyResult(unbindApplyPoliceVerifyRequest.getVerifyResult());
				req.setScore(unbindApplyPoliceVerifyRequest.getScore());
				req.setNote(unbindApplyPoliceVerifyRequest.getNote());
				req.setIdCardVerifyResult(unbindApplyPoliceVerifyRequest.getIdCardVerifyResult());
				log.info("==============【解绑卡申请_人脸核身验证】入参："+ JSONObject.toJSONString(req) +"============");
				ResMsg_Bank_UnbindApplyPoliceVerify res = appAssetBusinessService.unbindApplyPoliceVerify(req);
				log.info("==============【解绑卡申请_人脸核身验证】返回："+ JSONObject.toJSONString(res) +"============");
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					unbindApplyPoliceVerifyResponse.setScoreVerifyResult(String.valueOf(res.getScoreVerifyResult()));
					unbindApplyPoliceVerifyResponse.setResidueDegree(res.getResidueDegree());
					unbindApplyPoliceVerifyResponse.setSerialNo(res.getSerialNo());
				} else {
					throw new OpenException(res.getResCode(), res.getResMsg());
				}
			}
		});
	}

	/**
	 * 人脸核身验证上传检测图片v_1.0.0
	 *
	 * @param req
	 * @param res
	 * @return
	 */
	@ResponseBody
	@RequestMapping("upload_pic_police_verify/v_1.0.0")
	public String uploadPicPoliceVerify(HttpServletRequest req, HttpServletResponse res) {
		return this.execute(req, UploadPicPoliceVerifyRequest.class, new ControllerCallBack() {
			@Override
			public void doCheck(Request request, Response response) {
				UploadPicPoliceVerifyRequest uploadPicPoliceVerifyRequest = (UploadPicPoliceVerifyRequest) request;
				if(uploadPicPoliceVerifyRequest.getUserId() == null) {
					throw new OpenException(OpenMessageEnum.USER_ID_EMPTY.getCode(), OpenMessageEnum.USER_ID_EMPTY.getMessage());
				}
				if(StringUtils.isEmpty(uploadPicPoliceVerifyRequest.getSerialNo()) || StringUtils.isEmpty(uploadPicPoliceVerifyRequest.getIdCardFrontPic())
						|| StringUtils.isEmpty(uploadPicPoliceVerifyRequest.getIdCardBackPic()) || StringUtils.isEmpty(uploadPicPoliceVerifyRequest.getLivenessPicBlink())
						|| StringUtils.isEmpty(uploadPicPoliceVerifyRequest.getLivenessPicSay()) || StringUtils.isEmpty(uploadPicPoliceVerifyRequest.getLivenessPicShakeHead())) {
                    log.info("人脸核身验证上传检测图片缺失必要入参，唯一流水号：{}，是否上传图片，身份证正面：{}，身份证反面：{}，眨眼：{}，张嘴：{}，摇摇头：{}",
                            uploadPicPoliceVerifyRequest.getSerialNo(), !StringUtils.isEmpty(uploadPicPoliceVerifyRequest.getIdCardFrontPic()), !StringUtils.isEmpty(uploadPicPoliceVerifyRequest.getIdCardBackPic()),
                                    !StringUtils.isEmpty(uploadPicPoliceVerifyRequest.getLivenessPicBlink()), !StringUtils.isEmpty(uploadPicPoliceVerifyRequest.getLivenessPicSay()),
                                            !StringUtils.isEmpty(uploadPicPoliceVerifyRequest.getLivenessPicShakeHead()));
				    throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(), OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				UploadPicPoliceVerifyRequest uploadPicPoliceVerifyRequest = (UploadPicPoliceVerifyRequest) request;
				UploadPicPoliceVerifyResponse uploadPicPoliceVerifyResponse = (UploadPicPoliceVerifyResponse) response;
				log.info("人脸核身验证上传检测图片入参，用户编号：{}，唯一流水号：{}", uploadPicPoliceVerifyRequest.getUserId(), uploadPicPoliceVerifyRequest.getSerialNo());
				ReqMsg_Bank_UploadPicPoliceVerify req = new ReqMsg_Bank_UploadPicPoliceVerify();
				req.setVersion("1.0.0");
				req.setUserId(uploadPicPoliceVerifyRequest.getUserId());
				req.setSerialNo(uploadPicPoliceVerifyRequest.getSerialNo());

                // 上传Base64检测图片
				Calendar now = Calendar.getInstance();
                long date = now.getTime().getTime();
                String dynamicPath = "/" + String.valueOf(now.get(Calendar.YEAR)) + "/" + String.valueOf((now.get(Calendar.MONTH) + 1)) + "/"+ req.getUserId() +"/"; // 动态目录 年/月/用户编号
				String saveFullPath = GlobEnvUtil.get("police.verify.img.upload") + dynamicPath; // 上传图片全路径
                String savePath = "/resources/upload/policeVerify"+ dynamicPath; // 图片保存路径
                String extendName = ".jpeg"; // 图片格式
                int count = 1; // 记录图片数

                if (!StringUtils.isEmpty(uploadPicPoliceVerifyRequest.getIdCardFrontPic())) {
                    String path = saveFullPath + date + count + extendName;
                    log.info(">>>>>>>>>>>>>>上传身份证正面照>>>>>>>>>>>>>>>>" + path);
                    if (FileUtil.string2Image(uploadPicPoliceVerifyRequest.getIdCardFrontPic(), path)) {
                        req.setIdCardFrontPic(savePath + date + count + extendName);
                        count ++;
                    }
                }
                if (!StringUtils.isEmpty(uploadPicPoliceVerifyRequest.getIdCardBackPic())) {
					String path = saveFullPath + date + count + extendName;
                    log.info(">>>>>>>>>>>>>>上传身份证背面照>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + path);
                    if (FileUtil.string2Image(uploadPicPoliceVerifyRequest.getIdCardBackPic(), path)) {
                        req.setIdCardBackPic(savePath + date + count + extendName);
                        count ++;
                    }
                }
                if (!StringUtils.isEmpty(uploadPicPoliceVerifyRequest.getLivenessPicBlink())) {
					String path = saveFullPath + date + count + extendName;
                    log.info(">>>>>>>>>>>>>>上传活体检测图-眨眼>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + path);
                    if (FileUtil.string2Image(uploadPicPoliceVerifyRequest.getLivenessPicBlink(), path)) {
                        req.setLivenessPicBlink(savePath + date + count + extendName);
                        count ++;
                    }
                }
				if (!StringUtils.isEmpty(uploadPicPoliceVerifyRequest.getLivenessPicSay())) {
					String path = saveFullPath + date + count + extendName;
					log.info(">>>>>>>>>>>>>>上传活体检测图-张嘴>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + path);
					if (FileUtil.string2Image(uploadPicPoliceVerifyRequest.getLivenessPicSay(), path)) {
						req.setLivenessPicSay(savePath + date + count + extendName);
						count ++;
					}
				}
				if (!StringUtils.isEmpty(uploadPicPoliceVerifyRequest.getLivenessPicRightHead())) {
					String path = saveFullPath + date + count + extendName;
					log.info(">>>>>>>>>>>>>>上传活体检测图-向右摇头>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + path);
					if (FileUtil.string2Image(uploadPicPoliceVerifyRequest.getLivenessPicRightHead(), path)) {
						req.setLivenessPicRightHead(savePath + date + count + extendName);
						count ++;
					}
				}
				if (!StringUtils.isEmpty(uploadPicPoliceVerifyRequest.getLivenessPicLeftHead())) {
					String path = saveFullPath + date + count + extendName;
					log.info(">>>>>>>>>>>>>>上传活体检测图-向左摇头>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + path);
					if (FileUtil.string2Image(uploadPicPoliceVerifyRequest.getLivenessPicLeftHead(), path)) {
						req.setLivenessPicLeftHead(savePath + date + count + extendName);
						count ++;
					}
				}
				if (!StringUtils.isEmpty(uploadPicPoliceVerifyRequest.getLivenessPicRaiseHead())) {
					String path = saveFullPath + date + count + extendName;
					log.info(">>>>>>>>>>>>>>上传活体检测图-抬头>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + path);
					if (FileUtil.string2Image(uploadPicPoliceVerifyRequest.getLivenessPicRaiseHead(), path)) {
						req.setLivenessPicRaiseHead(savePath + date + count + extendName);
						count ++;
					}
				}
                if (!StringUtils.isEmpty(uploadPicPoliceVerifyRequest.getLivenessPicDropHead())) {
					String path = saveFullPath + date + count + extendName;
                    log.info(">>>>>>>>>>>>>>上传活体检测图-低头>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + path);
                    if (FileUtil.string2Image(uploadPicPoliceVerifyRequest.getLivenessPicDropHead(), path)) {
                        req.setLivenessPicDropHead(savePath + date + count + extendName);
                        count ++;
                    }
                }
                if (!StringUtils.isEmpty(uploadPicPoliceVerifyRequest.getLivenessPicShakeHead())) {
					String path = saveFullPath + date + count + extendName;
                    log.info(">>>>>>>>>>>>>>上传活体检测图-摇摇头>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + path);
                    if (FileUtil.string2Image(uploadPicPoliceVerifyRequest.getLivenessPicShakeHead(), path)) {
                        req.setLivenessPicShakeHead(savePath + date + count + extendName);
                        count ++;
                    }
                }

				ResMsg_Bank_UploadPicPoliceVerify res = appAssetBusinessService.uploadPicPoliceVerify(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					uploadPicPoliceVerifyResponse.setUploadResult(String.valueOf(res.getUploadResult()));
					log.info("人脸核身验证上传检测图片出参，唯一流水号：{}，上传结果：{}", uploadPicPoliceVerifyRequest.getSerialNo(), uploadPicPoliceVerifyResponse.getUploadResult());
				} else {
					throw new OpenException(res.getResCode(), res.getResMsg());
				}
			}
		});
	}

	/**
	 * 人脸核身token获取v_1.0.0
	 *
	 * @param req
	 * @param res
	 * @return
	 */
	@ResponseBody
	@RequestMapping("token_police_verify/v_1.0.0")
	public String tokenPoliceVerify(HttpServletRequest req, HttpServletResponse res) {
		return this.execute(req, TokenPoliceVerifyRequest.class, new ControllerCallBack() {
			@Override
			public void doOperation(Request request, Response response) {
				TokenPoliceVerifyResponse tokenPoliceVerifyResponse = (TokenPoliceVerifyResponse) response;
				AuthTokenResModel authTokenResModel = baiduAiService.getAuthToken();
				tokenPoliceVerifyResponse.setAccessToken(authTokenResModel.getAccess_token());
				tokenPoliceVerifyResponse.setExpiresIn(authTokenResModel.getExpires_in());
			}
		});
	}

}
