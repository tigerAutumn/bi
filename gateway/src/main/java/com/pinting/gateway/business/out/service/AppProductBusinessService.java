package com.pinting.gateway.business.out.service;

import com.pinting.business.hessian.site.message.*;

public interface AppProductBusinessService {

	public ResMsg_Product_ListQuery appProductProductList(ReqMsg_Product_ListQuery req);
	
	public ResMsg_Bank_Pay19BankList appProductBankList(ReqMsg_Bank_Pay19BankList req);
	
	public ResMsg_Bank_QueryDefaultBank appProductUserLastBank(ReqMsg_Bank_QueryDefaultBank req);
	
	public ResMsg_Bank_bindBankList appProductBindBank(ReqMsg_Bank_bindBankList req);
	
	public ResMsg_Bank_AlreadyUseMoney appProductUserMoney(ReqMsg_Bank_AlreadyUseMoney req);
	
	public ResMsg_Bank_QueryBankBin appProductBankBin(ReqMsg_Bank_QueryBankBin req);
	
	public ResMsg_RegularBuy_Order appProductPlaceOrder(ReqMsg_RegularBuy_Order req);
	
	public ResMsg_Bank_QueryBankInfoByUser appProductBankInfo(ReqMsg_Bank_QueryBankInfoByUser req);
	
	public ResMsg_User_ValidUser appProductUserValid(ReqMsg_User_ValidUser req);
	
	public ResMsg_RegularBuy_BalanceBuy appProductBalanceBuy(ReqMsg_RegularBuy_BalanceBuy req);
	
	public ResMsg_RegularBuy_ReapalQuickCMB appProductReapalQuickCMB(ReqMsg_RegularBuy_ReapalQuickCMB req);
	
	public ResMsg_Product_FindRate appProductFindRate(ReqMsg_Product_FindRate req);
	
	public ResMsg_Product_FindSuggest appProductFindSuggest(ReqMsg_Product_FindSuggest req);

	public ResMsg_Product_IsExistRedPacket appProductIsExistRedPacket(ReqMsg_Product_IsExistRedPacket req);

	public ResMsg_Product_SaveProductInform appProductInform(ReqMsg_Product_SaveProductInform req);
	
	public ResMsg_Product_SelectProductInform appSelectProductInform(ReqMsg_Product_SelectProductInform req);
	
	public ResMsg_Product_NewBuyer appNewBuyer(ReqMsg_Product_NewBuyer req);
	
	public ResMsg_Product_SingleProduct selectSingleProduct(ReqMsg_Product_SingleProduct req);

	public ResMsg_Bank_ChannelBank baofooBankList(ReqMsg_Bank_ChannelBank req);

	public ResMsg_Product_ListIndexQuery appProductProductListIndex(ReqMsg_Product_ListIndexQuery req);

	public ResMsg_Product_ProductListReturnType appProductProductListReturnType(ReqMsg_Product_ProductListReturnType req);

	public ResMsg_Product_ProductListStatusFinish30 appProductProductListStatusFinish30(ReqMsg_Product_ProductListStatusFinish30 req);

	public ResMsg_Product_CheckBeforeBuy checkBeforeBuy(ReqMsg_Product_CheckBeforeBuy req);

	public ResMsg_Product_BalanceBuyInfo balanceBuyInfo(ReqMsg_Product_BalanceBuyInfo req);
	
	public ResMsg_Product_ListIndexInfoQuery appProductProductListInfoIndex(ReqMsg_Product_ListIndexInfoQuery req);

	public ResMsg_Product_ListIndexInfoQuery4User isNewUser(ReqMsg_Product_ListIndexInfoQuery4User req);
}
