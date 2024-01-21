package com.pinting.gateway.business.out.service.impl;

import com.pinting.business.hessian.site.message.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.business.out.service.AppProductBusinessService;

@Service
public class AppProductBusinessServiceImpl implements AppProductBusinessService{

	@Autowired
	@Qualifier("gatewayMobileService")
	private HessianService siteService;
	
	@Override
	public ResMsg_Bank_Pay19BankList appProductBankList(ReqMsg_Bank_Pay19BankList req) {
		ResMsg_Bank_Pay19BankList res = (ResMsg_Bank_Pay19BankList) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Product_ListQuery appProductProductList(ReqMsg_Product_ListQuery req) {
		ResMsg_Product_ListQuery res = (ResMsg_Product_ListQuery) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Bank_QueryDefaultBank appProductUserLastBank(ReqMsg_Bank_QueryDefaultBank req) {
		ResMsg_Bank_QueryDefaultBank res = (ResMsg_Bank_QueryDefaultBank) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Bank_bindBankList appProductBindBank(ReqMsg_Bank_bindBankList req) {
		ResMsg_Bank_bindBankList res = (ResMsg_Bank_bindBankList) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Bank_AlreadyUseMoney appProductUserMoney(ReqMsg_Bank_AlreadyUseMoney req) {
		ResMsg_Bank_AlreadyUseMoney res = (ResMsg_Bank_AlreadyUseMoney) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Bank_QueryBankBin appProductBankBin(ReqMsg_Bank_QueryBankBin req) {
		ResMsg_Bank_QueryBankBin res = (ResMsg_Bank_QueryBankBin) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_RegularBuy_Order appProductPlaceOrder(ReqMsg_RegularBuy_Order req) {
		ResMsg_RegularBuy_Order res = (ResMsg_RegularBuy_Order) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Bank_QueryBankInfoByUser appProductBankInfo(ReqMsg_Bank_QueryBankInfoByUser req) {
		ResMsg_Bank_QueryBankInfoByUser res = (ResMsg_Bank_QueryBankInfoByUser) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_User_ValidUser appProductUserValid(ReqMsg_User_ValidUser req) {
		ResMsg_User_ValidUser res = (ResMsg_User_ValidUser) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_RegularBuy_BalanceBuy appProductBalanceBuy(ReqMsg_RegularBuy_BalanceBuy req) {
		ResMsg_RegularBuy_BalanceBuy res = (ResMsg_RegularBuy_BalanceBuy) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_RegularBuy_ReapalQuickCMB appProductReapalQuickCMB(
			ReqMsg_RegularBuy_ReapalQuickCMB req) {
		ResMsg_RegularBuy_ReapalQuickCMB res = (ResMsg_RegularBuy_ReapalQuickCMB) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Product_FindRate appProductFindRate(ReqMsg_Product_FindRate req) {
		ResMsg_Product_FindRate res = (ResMsg_Product_FindRate) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Product_FindSuggest appProductFindSuggest(ReqMsg_Product_FindSuggest req) {
		ResMsg_Product_FindSuggest res = (ResMsg_Product_FindSuggest) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Product_IsExistRedPacket appProductIsExistRedPacket(ReqMsg_Product_IsExistRedPacket req) {
		ResMsg_Product_IsExistRedPacket res = (ResMsg_Product_IsExistRedPacket) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Product_SaveProductInform appProductInform(ReqMsg_Product_SaveProductInform req) {
		ResMsg_Product_SaveProductInform res = (ResMsg_Product_SaveProductInform) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Product_SelectProductInform appSelectProductInform(ReqMsg_Product_SelectProductInform req) {
		ResMsg_Product_SelectProductInform res = (ResMsg_Product_SelectProductInform) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Product_NewBuyer appNewBuyer(ReqMsg_Product_NewBuyer req) {
		ResMsg_Product_NewBuyer res = (ResMsg_Product_NewBuyer) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Product_SingleProduct selectSingleProduct(ReqMsg_Product_SingleProduct req) {
		ResMsg_Product_SingleProduct res = (ResMsg_Product_SingleProduct) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Bank_ChannelBank baofooBankList(ReqMsg_Bank_ChannelBank req) {
		ResMsg_Bank_ChannelBank res = (ResMsg_Bank_ChannelBank) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Product_ListIndexQuery appProductProductListIndex(
			ReqMsg_Product_ListIndexQuery req) {
		ResMsg_Product_ListIndexQuery res = (ResMsg_Product_ListIndexQuery) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Product_ProductListReturnType appProductProductListReturnType(
			ReqMsg_Product_ProductListReturnType req) {
		ResMsg_Product_ProductListReturnType res = (ResMsg_Product_ProductListReturnType) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Product_ProductListStatusFinish30 appProductProductListStatusFinish30(
			ReqMsg_Product_ProductListStatusFinish30 req) {
		ResMsg_Product_ProductListStatusFinish30 res = (ResMsg_Product_ProductListStatusFinish30) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Product_CheckBeforeBuy checkBeforeBuy(
			ReqMsg_Product_CheckBeforeBuy req) {
		ResMsg_Product_CheckBeforeBuy res = (ResMsg_Product_CheckBeforeBuy) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Product_BalanceBuyInfo balanceBuyInfo(
			ReqMsg_Product_BalanceBuyInfo req) {
		ResMsg_Product_BalanceBuyInfo res = (ResMsg_Product_BalanceBuyInfo) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Product_ListIndexInfoQuery appProductProductListInfoIndex(
			ReqMsg_Product_ListIndexInfoQuery req) {
		ResMsg_Product_ListIndexInfoQuery res = (ResMsg_Product_ListIndexInfoQuery) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Product_ListIndexInfoQuery4User isNewUser(
			ReqMsg_Product_ListIndexInfoQuery4User req) {
		ResMsg_Product_ListIndexInfoQuery4User res = (ResMsg_Product_ListIndexInfoQuery4User) siteService.handleMsg(req);
		return res;
	}

}
