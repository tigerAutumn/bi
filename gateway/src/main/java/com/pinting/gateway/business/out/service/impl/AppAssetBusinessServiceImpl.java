package com.pinting.gateway.business.out.service.impl;

import java.util.Date;

import com.pinting.business.hessian.site.message.*;

import com.pinting.core.util.MoneyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.business.out.service.AppAssetBusinessService;
import com.pinting.gateway.mobile.in.enums.OpenMessageEnum;
import com.pinting.open.base.exception.OpenException;
import com.pinting.open.pojo.request.asset.MyBonusWithdrawInfoRequest;
import com.pinting.open.pojo.request.asset.WithdrawalIndexRequest;
import com.pinting.open.pojo.response.asset.MyBonusWithdrawInfoResponse;
import com.pinting.open.pojo.response.asset.WithdrawalIndexResponse;

@Service
public class AppAssetBusinessServiceImpl implements AppAssetBusinessService {

	@Autowired
	@Qualifier("gatewayMobileService")
	private HessianService siteService;

	@Override
	public ResMsg_User_AssetInfoQuery assetInfoQuery(
			ReqMsg_User_AssetInfoQuery req) {
		ResMsg_User_AssetInfoQuery res = (ResMsg_User_AssetInfoQuery) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Invest_EarningsListQuery dayInvestEarnings(
			ReqMsg_Invest_EarningsListQuery req) {
		ResMsg_Invest_EarningsListQuery res = (ResMsg_Invest_EarningsListQuery) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Bonus_RecommendBonusListQuery myBonus(
			ReqMsg_Bonus_RecommendBonusListQuery req) {
		// 查询用户表奖励数据
		ResMsg_Bonus_RecommendBonusListQuery res = (ResMsg_Bonus_RecommendBonusListQuery) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Bonus_BonusToJSH myBonusToJSH(ReqMsg_Bonus_BonusToJSH req) {
		// 奖励金转出到余额
		ResMsg_Bonus_BonusToJSH res = (ResMsg_Bonus_BonusToJSH) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Invest_InvestListQuery myInvestment(
			ReqMsg_Invest_InvestListQuery req) {
		
		ResMsg_Invest_InvestListQuery res = (ResMsg_Invest_InvestListQuery) siteService.handleMsg(req);
		//获取用户当前正在处理中的购买的数量
		ReqMsg_User_ProcessingOrder reqMsg_User_ProcessingOrder = new ReqMsg_User_ProcessingOrder();
		reqMsg_User_ProcessingOrder.setVersion("1.0.0");
		reqMsg_User_ProcessingOrder.setUserId(req.getUserId());
		ResMsg_User_ProcessingOrder resMsg_User_ProcessingOrder = (ResMsg_User_ProcessingOrder)siteService.handleMsg(reqMsg_User_ProcessingOrder);
		
		res.setProcessingNum(resMsg_User_ProcessingOrder.getProcessingNum());
		return res;
	}

	@Override
	public ResMsg_TransDetail_QueryByUserId tradingDetail(
			ReqMsg_TransDetail_QueryByUserId req) {
		ResMsg_TransDetail_QueryByUserId res = (ResMsg_TransDetail_QueryByUserId) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_User_BankListQuery bankCard(ReqMsg_User_BankListQuery req) {
        ResMsg_User_BankListQuery res = (ResMsg_User_BankListQuery) siteService.handleMsg(req);
		return res;
	}

   @Override
    public ResMsg_User_InfoQuery safeCenter(ReqMsg_User_InfoQuery req) {
        //发起Hessian通讯（资产信息查询）
        ResMsg_User_InfoQuery res = (ResMsg_User_InfoQuery) siteService.handleMsg(req);
        return res;
    }
	   
    @Override
    public ResMsg_RegularBuy_Order rechargePreOrder(ReqMsg_RegularBuy_Order req) {
        if (1 == req.getIsBind()) { // 已绑卡
            ReqMsg_Bank_QueryBankInfoByUser reqMsg = new ReqMsg_Bank_QueryBankInfoByUser();
            reqMsg.setVersion("1.0.0");
            reqMsg.setUserId(req.getUserId());
            reqMsg.setBankId(reqMsg.getBankId());
            ResMsg_Bank_QueryBankInfoByUser resMsg = (ResMsg_Bank_QueryBankInfoByUser) siteService.handleMsg(reqMsg);
            if(StringUtil.isNotBlank(resMsg.getIdCard())) {
                req.setBankName(resMsg.getBankName());
                req.setCardNo(resMsg.getCardNo());
                req.setIdCard(resMsg.getIdCard());
                req.setMobile(resMsg.getMobile());
                req.setUserName(resMsg.getUserName());
            }
        }
        ResMsg_RegularBuy_Order res = (ResMsg_RegularBuy_Order) siteService.handleMsg(req);
        return res;
    }

    @Override
    public ResMsg_RegularBuy_Order rechargeOrder(ReqMsg_RegularBuy_Order req) {
        if (1 == req.getIsBind()) { // 已绑卡
            ReqMsg_Bank_QueryBankInfoByUser reqMsg = new ReqMsg_Bank_QueryBankInfoByUser();
            reqMsg.setVersion("1.0.0");
            reqMsg.setUserId(req.getUserId());
            reqMsg.setBankId(reqMsg.getBankId());
            ResMsg_Bank_QueryBankInfoByUser resMsg = (ResMsg_Bank_QueryBankInfoByUser) siteService.handleMsg(reqMsg);
            if(StringUtil.isNotBlank(resMsg.getIdCard())) {
                req.setBankName(resMsg.getBankName());
                req.setCardNo(resMsg.getCardNo());
                req.setIdCard(resMsg.getIdCard());
                req.setMobile(resMsg.getMobile());
                req.setUserName(resMsg.getUserName());
            }
        }
        ResMsg_RegularBuy_Order res = (ResMsg_RegularBuy_Order) siteService.handleMsg(req);
        return res;
    }

    @Override
    public ResMsg_Invest_BuyAgreeMent buyAgreeMent(
            ReqMsg_Invest_BuyAgreeMent req) {
        ResMsg_Invest_BuyAgreeMent  buyAgreeMent  = new ResMsg_Invest_BuyAgreeMent();
        //查询用户信息
        ReqMsg_User_InfoQuery reqInfoQuery = new ReqMsg_User_InfoQuery();
        reqInfoQuery.setVersion("1.0.0");
        reqInfoQuery.setUserId(req.getUserId());
        ResMsg_User_InfoQuery resInfoQuery = (ResMsg_User_InfoQuery) siteService.handleMsg(reqInfoQuery);
        

        
        //根据subAccountId查询
        ReqMsg_Account_SubAccountById reqAccount = new ReqMsg_Account_SubAccountById();
        reqAccount.setVersion("1.0.0");
        reqAccount.setId(req.getSubAccountId());
        ResMsg_Account_SubAccountById resAccount = (ResMsg_Account_SubAccountById) siteService.handleMsg(reqAccount);
        
        if (resAccount.getProductId() == null ) {
            throw new OpenException(OpenMessageEnum.SUB_ACCOUNT_IS_NOT_EXIT.getCode(),OpenMessageEnum.SUB_ACCOUNT_IS_NOT_EXIT.getMessage());
        }
        
        //查询产品信息
        ReqMsg_Product_InfoQuery reqProductInfoQuery = new ReqMsg_Product_InfoQuery();
        reqProductInfoQuery.setVersion("1.0.0");
        reqProductInfoQuery.setId(resAccount.getProductId());
        ResMsg_Product_InfoQuery resProductInfoQuery = (ResMsg_Product_InfoQuery) siteService.handleMsg(reqProductInfoQuery);
        
        
        //返回数据处理
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(resInfoQuery.getResCode())) {

        }
        else {
            throw new OpenException(resInfoQuery.getResCode(),resInfoQuery.getResMsg());
        }
        
        //返回数据处理
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(resInfoQuery.getResCode())) {

            
        }
        else {
            throw new OpenException(resAccount.getResCode(),resAccount.getResMsg());
        }   
        
        
        //返回数据处理
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(resInfoQuery.getResCode())) {
    
            
        }
        else {
            throw new OpenException(resProductInfoQuery.getResCode(),resProductInfoQuery.getResMsg());
        }   
        /**
             private String            mobile;                                  //  手机号     
            private String            idCard;                                  //   身份证号码   
            private String            userName;                                //   用户真实姓名
            private Integer           dayNum;                                   //  投资期限
            private Double            rate;                                     //  利率
            private String            name;                                     //  产品名称
            private Double            amount;                                   //  投资金额
            private String            times;                                    //  
            private String            openTime;                                 //  协议签署日期
            private String            beginTime;
         */
        buyAgreeMent.setMobile(resInfoQuery.getMobile());
        buyAgreeMent.setIdCard(resInfoQuery.getIdCard());
        buyAgreeMent.setUserName(resInfoQuery.getUserName());
        buyAgreeMent.setDayNum(resProductInfoQuery.getTerm4Day());
        buyAgreeMent.setRate(resProductInfoQuery.getRate());
        buyAgreeMent.setName(resProductInfoQuery.getProductName());
        buyAgreeMent.setAmount(resAccount.getBalance());
        //interestBeginDate 起息日
        if (resAccount.getInterestBeginDate() != null) {
            buyAgreeMent.setTimes(DateUtil.formatDateTime(resAccount.getInterestBeginDate(), "yyyy年MM月dd日"));
        } else {
            buyAgreeMent.setTimes(DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
        }
        if (resAccount.getOpenTime() != null) {
            buyAgreeMent.setOpenTime(DateUtil.formatDateTime(resAccount.getOpenTime(), "yyyy年MM月dd日"));
        } else {
            buyAgreeMent.setBeginTime(DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
        }
        
        buyAgreeMent.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        return buyAgreeMent;
    }
    
    @Override
    public ResMsg_User_InfoQuery queryAccountBalance(ReqMsg_User_InfoQuery req) {
        ResMsg_User_InfoQuery res = (ResMsg_User_InfoQuery) siteService.handleMsg(req);
        return res;
    }

    @Override
    public ResMsg_Bank_bindBankList queryBindBank(ReqMsg_Bank_bindBankList req) {
        ResMsg_Bank_bindBankList res = (ResMsg_Bank_bindBankList) siteService.handleMsg(req);
        return res;
    }
    
    @Override
    public ResMsg_Bank_ReturnPath safeReturnBankCard(ReqMsg_Bank_ReturnPath req) {
        ResMsg_Bank_ReturnPath res = (ResMsg_Bank_ReturnPath) siteService.handleMsg(req);
        return res;
    }

	@Override
	public ResMsg_User_SetUpTraderPassword safePayPasswordSet(
			ReqMsg_User_SetUpTraderPassword req) {
		 ResMsg_User_SetUpTraderPassword res = (ResMsg_User_SetUpTraderPassword) siteService.handleMsg(req);
		 return res;
	}

	@Override
	public ResMsg_Profile_PayPasswordModify safePayPasswordChange(
			ReqMsg_Profile_PayPasswordModify req) {
        // 修改交易密码
        ResMsg_Profile_PayPasswordModify res = (ResMsg_Profile_PayPasswordModify) siteService.handleMsg(req);
        return res;
	}

	@Override
	public ResMsg_User_FindPayPassword safePayPasswordForget(
			ReqMsg_User_FindPayPassword req) {
        ResMsg_User_FindPayPassword res = (ResMsg_User_FindPayPassword) siteService.handleMsg(req);
        return res;
	}

	@Override
	public ResMsg_User_CheckPayPassword checkPayPassword(
			ReqMsg_User_CheckPayPassword req) {
        ResMsg_User_CheckPayPassword res = (ResMsg_User_CheckPayPassword) siteService.handleMsg(req);
        return res;
	}

    @Override
    public ResMsg_UserBalance_Withdraw balanceWithdraw(ReqMsg_UserBalance_Withdraw req) {
        ResMsg_UserBalance_Withdraw res = (ResMsg_UserBalance_Withdraw) siteService.handleMsg(req);
        return res;
    }

    @Override
    public ResMsg_Bank_SetIsFirstBankCard safeReturnPathChange(
            ReqMsg_Bank_SetIsFirstBankCard req) {
         ResMsg_Bank_SetIsFirstBankCard res = (ResMsg_Bank_SetIsFirstBankCard)siteService.handleMsg(req);
         return res;
    }

    @Override
    public void withdrawalIndexRequest(WithdrawalIndexRequest indexRequest,
                                       WithdrawalIndexResponse indexResponse) {
      ReqMsg_Bank_QueryFirstCard reqMsg = new ReqMsg_Bank_QueryFirstCard();
      reqMsg.setVersion("1.0.0");
      reqMsg.setUserId(indexRequest.getUserId());
      ResMsg_Bank_QueryFirstCard resp = (ResMsg_Bank_QueryFirstCard) siteService.handleMsg(reqMsg);
      if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
          indexResponse.setBankName(resp.getBankName());
          indexResponse.setCardNo(resp.getCardNo());
          indexResponse.setCardId(resp.getCardId());
          indexResponse.setCanWithdraw(resp.getCan_withdraw());
          indexResponse.setWithdrawTimes(resp.getCan_withdraw_times() == null ? 0 : resp.getCan_withdraw_times());
          indexResponse.setEachMonthWithdrawTimes(resp.getEachMonthWithdrawTimes() == null ? 0 :resp.getEachMonthWithdrawTimes());
          indexResponse.setWithdrawCounterFee(resp.getWithdrawCounterFee());

      } else {
          throw new OpenException(resp.getResCode(), resp.getResMsg());
      }
      
      //获取提现时间限制
      ReqMsg_UserWithdraw_CanWithdrawTime reqcw = new ReqMsg_UserWithdraw_CanWithdrawTime();
      reqcw.setVersion("1.0.0");
      ResMsg_UserWithdraw_CanWithdrawTime rescw = (ResMsg_UserWithdraw_CanWithdrawTime) siteService.handleMsg(reqcw);
      if(ConstantUtil.BSRESCODE_SUCCESS.equals(rescw.getResCode())){
          indexResponse.setStartTime(rescw.getBegin());
          indexResponse.setEndTime(rescw.getEnd());
      } else {
          throw new OpenException(rescw.getResCode(), rescw.getResMsg());
      }
    }
    
    
    @Override
    public void withdrawalIndexRequest_1(WithdrawalIndexRequest indexRequest,
                                       WithdrawalIndexResponse indexResponse) {
      ReqMsg_Bank_QueryFirstCard reqMsg = new ReqMsg_Bank_QueryFirstCard();
      reqMsg.setVersion("1.0.0");
      reqMsg.setUserId(indexRequest.getUserId());
      ResMsg_Bank_QueryFirstCard resp = (ResMsg_Bank_QueryFirstCard) siteService.handleMsg(reqMsg);
      if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
          indexResponse.setBankName(resp.getBankName());
          indexResponse.setCardNo(resp.getCardNo());
          indexResponse.setCardId(resp.getCardId());
          indexResponse.setCanWithdraw(resp.getCan_withdraw());
          indexResponse.setWithdrawTimes(resp.getCan_withdraw_times() == null ? 0 : resp.getCan_withdraw_times());
          indexResponse.setEachMonthWithdrawTimes(resp.getEachMonthWithdrawTimes() == null ? 0 :resp.getEachMonthWithdrawTimes());
          indexResponse.setWithdrawCounterFee(resp.getWithdrawCounterFee());
          indexResponse.setSmallLogo(resp.getSmallLogo());
          indexResponse.setLargeLogo(resp.getLargeLogo());
          
          indexResponse.setSingleWithdrawUpperLimit(resp.getSingleWithdrawUpperLimit());
          indexResponse.setDayWithdrawUpperLimit(resp.getDayWithdrawUpperLimit());
          indexResponse.setUserWithdrawAmount(resp.getUserWithdrawAmount());
      } else {
          throw new OpenException(resp.getResCode(), resp.getResMsg());
      }
      
      //获取提现时间限制
      ReqMsg_UserWithdraw_CanWithdrawTime reqcw = new ReqMsg_UserWithdraw_CanWithdrawTime();
      reqcw.setVersion("1.0.0");
      ResMsg_UserWithdraw_CanWithdrawTime rescw = (ResMsg_UserWithdraw_CanWithdrawTime) siteService.handleMsg(reqcw);
      if(ConstantUtil.BSRESCODE_SUCCESS.equals(rescw.getResCode())){
          indexResponse.setStartTime(rescw.getBegin());
          indexResponse.setEndTime(rescw.getEnd());
      } else {
          throw new OpenException(rescw.getResCode(), rescw.getResMsg());
      }
    }
    
    
    
    @Override
    public void withdrawalIndexRequest_2(WithdrawalIndexRequest indexRequest,
                                       WithdrawalIndexResponse indexResponse) {
	      ReqMsg_Bank_QueryFirstCard reqMsg = new ReqMsg_Bank_QueryFirstCard();
	      reqMsg.setVersion("1.0.0");
	      reqMsg.setUserId(indexRequest.getUserId());
	      ResMsg_Bank_QueryFirstCard resp = (ResMsg_Bank_QueryFirstCard) siteService.handleMsg(reqMsg);
	      if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
	          indexResponse.setBankName(resp.getBankName());
	          indexResponse.setCardNo(resp.getCardNo());
	          indexResponse.setCardId(resp.getCardId());
	          indexResponse.setCanWithdraw(resp.getCan_withdraw());
	          indexResponse.setWithdrawTimes(resp.getCan_withdraw_times() == null ? 0 : resp.getCan_withdraw_times());
	          indexResponse.setEachMonthWithdrawTimes(resp.getEachMonthWithdrawTimes() == null ? 0 :resp.getEachMonthWithdrawTimes());
	          indexResponse.setWithdrawCounterFee(resp.getWithdrawCounterFee());
	          indexResponse.setSmallLogo(resp.getSmallLogo());
	          indexResponse.setLargeLogo(resp.getLargeLogo());
              if(resp.getWithdrawApplyLimit().compareTo(10000d) >= 0) {
                  indexResponse.setWithdrawApplyLimit(String.valueOf(MoneyUtil.divide(resp.getWithdrawApplyLimit(), 10000d).intValue()) + "万");
              } else {
                  indexResponse.setWithdrawApplyLimit(String.valueOf(MoneyUtil.multiply(resp.getWithdrawApplyLimit(), 1).intValue()));
              }
	          indexResponse.setSingleWithdrawUpperLimit(resp.getSingleWithdrawUpperLimit());
	          indexResponse.setDayWithdrawUpperLimit(resp.getDayWithdrawUpperLimit());
	          indexResponse.setUserWithdrawAmount(resp.getUserWithdrawAmount());
	      } else {
	          throw new OpenException(resp.getResCode(), resp.getResMsg());
	      }
	      
	      //获取提现时间限制
	      ReqMsg_UserWithdraw_CanWithdrawTime reqcw = new ReqMsg_UserWithdraw_CanWithdrawTime();
	      reqcw.setVersion("1.0.0");
	      ResMsg_UserWithdraw_CanWithdrawTime rescw = (ResMsg_UserWithdraw_CanWithdrawTime) siteService.handleMsg(reqcw);
	      if(ConstantUtil.BSRESCODE_SUCCESS.equals(rescw.getResCode())){
	          indexResponse.setStartTime(rescw.getBegin());
	          indexResponse.setEndTime(rescw.getEnd());
	      } else {
	          throw new OpenException(rescw.getResCode(), rescw.getResMsg());
	      }
      
      
	  	  // 查询
		 ReqMsg_User_AssetInfoQuery  reqAssetInfoQuery = new ReqMsg_User_AssetInfoQuery();
		 reqAssetInfoQuery.setVersion("1.0.1");
		 reqAssetInfoQuery.setUserId(indexRequest.getUserId());
		 ResMsg_User_AssetInfoQuery resAssetInfoQuery = (ResMsg_User_AssetInfoQuery) siteService.handleMsg(reqAssetInfoQuery);
		 if(ConstantUtil.BSRESCODE_SUCCESS.equals(rescw.getResCode())){
			 indexResponse.setDepAccountStatus(resAssetInfoQuery.getDepAccountStatus());
			 indexResponse.setDepBalance(resAssetInfoQuery.getDepCanWithdraw());
		 } else {
			 throw new OpenException(rescw.getResCode(), rescw.getResMsg());
		 }
      
    }

	@Override
	public ResMsg_User_MessageList messageList(ReqMsg_User_MessageList listRequest) {
		ResMsg_User_MessageList res = (ResMsg_User_MessageList) siteService.handleMsg(listRequest);
		return res;
	}

	@Override
	public ResMsg_User_MessageInfo messageInfo(ReqMsg_User_MessageInfo infoRequest) {
		ResMsg_User_MessageInfo res = (ResMsg_User_MessageInfo) siteService.handleMsg(infoRequest);
		return res;
	}

	@Override
	public ResMsg_RedPacketInfo_RedPacketList redPacketList(ReqMsg_RedPacketInfo_RedPacketList redRequest) {
		ResMsg_RedPacketInfo_RedPacketList res = (ResMsg_RedPacketInfo_RedPacketList) siteService.handleMsg(redRequest);
		return res;
	}

    @Override
    public ResMsg_RedPacketInfo_QueryRedPacketList queryRedPacketList(ReqMsg_RedPacketInfo_QueryRedPacketList redRequest) {
        ResMsg_RedPacketInfo_QueryRedPacketList res = (ResMsg_RedPacketInfo_QueryRedPacketList) siteService.handleMsg(redRequest);
        return res;
    }

    @Override
	public ResMsg_Invest_LoadMatch myloadMatch(ReqMsg_Invest_LoadMatch req) {
		ResMsg_Invest_LoadMatch res = (ResMsg_Invest_LoadMatch) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Invest_SysLoadMatchTime loadMatchTime(ReqMsg_Invest_SysLoadMatchTime req) {
		ResMsg_Invest_SysLoadMatchTime res = (ResMsg_Invest_SysLoadMatchTime) siteService.handleMsg(req);
		return res;
	}
	
	@Override
	public ResMsg_Bank_PreBindCard preBindCard(ReqMsg_Bank_PreBindCard req) {
		ResMsg_Bank_PreBindCard res = (ResMsg_Bank_PreBindCard) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Bank_BindCard bindCard(ReqMsg_Bank_BindCard bindCardRequest) {
		ResMsg_Bank_BindCard res = (ResMsg_Bank_BindCard) siteService.handleMsg(bindCardRequest);
		return res;
	}

	@Override
	public ResMsg_Invest_AccountBalance accountBalance(
			ReqMsg_Invest_AccountBalance reqMsg) {
		ResMsg_Invest_AccountBalance res = (ResMsg_Invest_AccountBalance) siteService.handleMsg(reqMsg);
		return res;
	}

	@Override
	public ResMsg_Invest_InvestEntrustListQuery myInvestmentEntrust(
			ReqMsg_Invest_InvestEntrustListQuery reqMsg) {
		ResMsg_Invest_InvestEntrustListQuery res = (ResMsg_Invest_InvestEntrustListQuery) siteService.handleMsg(reqMsg);
		return res;
	}

    @Override
    public ResMsg_TransDetail_QueryZanReturnDetail queryZanReturnDetail(ReqMsg_TransDetail_QueryZanReturnDetail queryZanReturnDetail) {
        ResMsg_TransDetail_QueryZanReturnDetail res = (ResMsg_TransDetail_QueryZanReturnDetail) siteService.handleMsg(queryZanReturnDetail);
        return res;
    }

    @Override
    public ResMsg_RepaySchedule_RepaymentPlanList repaymentPlanList(ReqMsg_RepaySchedule_RepaymentPlanList repaymentPlanList) {
        ResMsg_RepaySchedule_RepaymentPlanList res = (ResMsg_RepaySchedule_RepaymentPlanList) siteService.handleMsg(repaymentPlanList);
        return res;
    }

    @Override
    public ResMsg_RepaySchedule_RepayScheduleDetails repayScheduleDetails(ReqMsg_RepaySchedule_RepayScheduleDetails repayScheduleDetails) {
        ResMsg_RepaySchedule_RepayScheduleDetails res = (ResMsg_RepaySchedule_RepayScheduleDetails) siteService.handleMsg(repayScheduleDetails);
        return res;
    }

	@Override
	public ResMsg_User_ActivatePageInfo activatePageInfo(
			ReqMsg_User_ActivatePageInfo activatePageInfoRequest) {
		ResMsg_User_ActivatePageInfo res = (ResMsg_User_ActivatePageInfo) siteService.handleMsg(activatePageInfoRequest);
	    return res;
	}

	@Override
	public ResMsg_User_ActivateDepAccount activateDepAccount(
			ReqMsg_User_ActivateDepAccount activateDepAccountRequest) {
		ResMsg_User_ActivateDepAccount res = (ResMsg_User_ActivateDepAccount) siteService.handleMsg(activateDepAccountRequest);
	    return res;
	}

	@Override
	public void myBonusWithdrawInfo(
			MyBonusWithdrawInfoRequest withdrawInfoRequest,
			MyBonusWithdrawInfoResponse withdrawInfoResponse) {
		
//	      ReqMsg_Bank_QueryFirstCard reqMsg = new ReqMsg_Bank_QueryFirstCard();
//	      reqMsg.setVersion("1.0.0");
//	      reqMsg.setUserId(withdrawInfoRequest.getUserId());
//	      ResMsg_Bank_QueryFirstCard resp = (ResMsg_Bank_QueryFirstCard) siteService.handleMsg(reqMsg);
//	      if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
//	          withdrawInfoResponse.setBankName(resp.getBankName());
//	          withdrawInfoResponse.setCardNo(resp.getCardNo());
//	          withdrawInfoResponse.setCardId(resp.getCardId());
//	          
//	          withdrawInfoResponse.setSmallLogo(resp.getSmallLogo());
//	          withdrawInfoResponse.setLargeLogo(resp.getLargeLogo());
//	          
//	          withdrawInfoResponse.setSingleWithdrawUpperLimit(resp.getSingleWithdrawUpperLimit());
//	          withdrawInfoResponse.setDayWithdrawUpperLimit(resp.getDayWithdrawUpperLimit());
//	          withdrawInfoResponse.setUserWithdrawAmount(resp.getUserWithdrawAmount());
//	      } else {
//	          throw new OpenException(resp.getResCode(), resp.getResMsg());
//	      }
	      
	      //获取提现奖励金相关信息
	      ReqMsg_UserWithdraw_MyBonusWithdrawInfo reqcw = new ReqMsg_UserWithdraw_MyBonusWithdrawInfo();
	      reqcw.setVersion("1.0.0");
	      reqcw.setUserId(withdrawInfoRequest.getUserId());
	      ResMsg_UserWithdraw_MyBonusWithdrawInfo resp = (ResMsg_UserWithdraw_MyBonusWithdrawInfo) siteService.handleMsg(reqcw);
	      if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
	          withdrawInfoResponse.setBankName(resp.getBankName());
	          withdrawInfoResponse.setCardNo(resp.getCardNo());
	          withdrawInfoResponse.setCardId(resp.getCardId());
	          
	          withdrawInfoResponse.setSmallLogo(resp.getSmallLogo());
	          withdrawInfoResponse.setLargeLogo(resp.getLargeLogo());
	          
	          withdrawInfoResponse.setSingleWithdrawUpperLimit(resp.getSingleWithdrawUpperLimit());
	          withdrawInfoResponse.setDayWithdrawUpperLimit(resp.getDayWithdrawUpperLimit());
	          withdrawInfoResponse.setUserWithdrawAmount(resp.getUserWithdrawAmount());
	          
	          withdrawInfoResponse.setTotalAmount(resp.getTotalAmount());
	          withdrawInfoResponse.setLimitWithdraw(resp.getLimitWithdraw());
              withdrawInfoResponse.setCanWithdraw(resp.getCanWithdraw());
	          
	      } else {
	          throw new OpenException(resp.getResCode(), resp.getResMsg());
	      }
    
  }

	@Override
	public ResMsg_UserWithdraw_MyBonusWithdraw myBonusWithdraw(
			ReqMsg_UserWithdraw_MyBonusWithdraw req) {
		ResMsg_UserWithdraw_MyBonusWithdraw res = (ResMsg_UserWithdraw_MyBonusWithdraw) siteService.handleMsg(req);
	    return res;
	}

    @Override
    public ResMsg_Ticket_TicketList ticketList(ReqMsg_Ticket_TicketList req) {
        ResMsg_Ticket_TicketList res = (ResMsg_Ticket_TicketList) siteService.handleMsg(req);
        return res;
    }

    @Override
    public ResMsg_Bank_UnbindApplyPoliceVerify unbindApplyPoliceVerify(ReqMsg_Bank_UnbindApplyPoliceVerify req) {
        ResMsg_Bank_UnbindApplyPoliceVerify res = (ResMsg_Bank_UnbindApplyPoliceVerify) siteService.handleMsg(req);
        return res;
    }

    @Override
    public ResMsg_Bank_UploadPicPoliceVerify uploadPicPoliceVerify(ReqMsg_Bank_UploadPicPoliceVerify req) {
        ResMsg_Bank_UploadPicPoliceVerify res = (ResMsg_Bank_UploadPicPoliceVerify) siteService.handleMsg(req);
        return res;
    }

	@Override
	public ResMsg_Bank_UnbindCheck unBindCheck(ReqMsg_Bank_UnbindCheck req) {
		ResMsg_Bank_UnbindCheck res = (ResMsg_Bank_UnbindCheck)siteService.handleMsg(req);
		return res;
	}
}
