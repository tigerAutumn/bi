package com.pinting.business.facade.site;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.accounting.service.AccountHandleService;
import com.pinting.business.accounting.service.RecordAccountingService;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.ReqMsg_Transfer_BuySubmit;
import com.pinting.business.hessian.site.message.ReqMsg_Transfer_InfoTransfer;
import com.pinting.business.hessian.site.message.ReqMsg_Transfer_TransferDetail;
import com.pinting.business.hessian.site.message.ReqMsg_Transfer_TransferListQuery;
import com.pinting.business.hessian.site.message.ResMsg_Transfer_BuySubmit;
import com.pinting.business.hessian.site.message.ResMsg_Transfer_InfoTransfer;
import com.pinting.business.hessian.site.message.ResMsg_Transfer_TransferDetail;
import com.pinting.business.hessian.site.message.ResMsg_Transfer_TransferListQuery;
import com.pinting.business.model.BsBankCard;
import com.pinting.business.model.BsCardBin;
import com.pinting.business.model.BsProduct;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsTransfer;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.vo.BsSubAccountVO;
import com.pinting.business.model.vo.BsTransferVO;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.service.site.ProductService;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.service.site.TransferService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.CalculatorUtil;
import com.pinting.business.util.Constants;
import com.pinting.business.util.TransCode;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.out.service.DafyTransportService;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_BuyProduct;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_BuyProduct;

/**
 * 
 * @Project: business
 * @Title: TransferFacade.java
 * @author Huang MengJian
 * @date 2015-2-4 下午5:54:49
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("Transfer")
public class TransferFacade {

	@Autowired
	private SubAccountService subAccountService;
	
	@Autowired
	private TransferService transferService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BankCardService bandCardService;
	
	@Autowired
	private AccountHandleService accountHandleService;
	
	@Autowired
	private DafyTransportService dafyTransportService;
	@Autowired
	private RecordAccountingService recordAccountingService;
	
	public void infoTransfer(ReqMsg_Transfer_InfoTransfer req, ResMsg_Transfer_InfoTransfer res) {
		/**
		 * 1.判断出让金额是否满足。
		 * 2.查询当前是否已经有转让记录，如果有则执行3，没有执行4
		 * 3.创建转让表，更新子账户表。（事务完成）
		 * 4.更新装让表
		 */
		BsSubAccountVO myInvest = subAccountService.findMyInvestByUserIdAndId(req.getUserId(),req.getSubAccountId());
		
		if(myInvest == null){
			throw new PTMessageException(PTMessageEnum.ACCOUNT_BALANCE_ERROR);
		}
		
		//1.判断出让金额是否满足。
		Double amountTemp = MoneyUtil.multiply(myInvest.getBalance(), myInvest.getInvestDay()).doubleValue();
		amountTemp = MoneyUtil.multiply(amountTemp, myInvest.getProductRate()).doubleValue();
		amountTemp = MoneyUtil.divide(amountTemp, 36000, 2).doubleValue();
		double amount = MoneyUtil.add(myInvest.getBalance(), amountTemp).doubleValue();
				//myInvest.getBalance() + myInvest.getBalance() * myInvest.getInvestDay() * myInvest.getProductRate() * 0.01 / 360;
		BigDecimal bg = new BigDecimal(amount);
        amount = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		if(amount < req.getTransferAmount()){  //出让金额大于最大金额时
			throw new PTMessageException(PTMessageEnum.TRANSFER_AMOUNT_ERROR);
		}
		
		//2.创建转让表，更新子账户表。（事务完成）
		
		//开始组织转让表内容
		BsTransfer transfer = new BsTransfer();
		
		transfer.setSubAccountId1(req.getSubAccountId());
		transfer.setUserId1(req.getUserId());
		transfer.setOldRate(myInvest.getProductRate());
		transfer.setPrice(req.getTransferAmount());
		transfer.setProductId(myInvest.getProductId());
		transfer.setStatus(Constants.TRANSFER_STATUS_WAITE);
		transfer.setDistributeTime(new Date());
		//总本息  = 投资总钱 * 期限 * 年化率  / 360
		Double worthTemp = MoneyUtil.multiply(myInvest.getBalance(), myInvest.getTerm()).doubleValue();
		worthTemp = MoneyUtil.multiply(worthTemp, myInvest.getProductRate()).doubleValue();
		worthTemp = MoneyUtil.multiply(worthTemp, 30).doubleValue();
		worthTemp = MoneyUtil.divide(worthTemp, 36000, 2).doubleValue();
		
		double worth = MoneyUtil.add(myInvest.getBalance(), worthTemp).doubleValue();
				//myInvest.getBalance() + myInvest.getBalance() * myInvest.getTerm() * 30 * myInvest.getProductRate() * 0.01 / 360;
		transfer.setWorth(worth);
		
		//原本总息：100 元  年利率：8%  期限：3个月   ----》 存满三个月，总本息： 100 * 0.08 * 3 * 30 / 360 = 2 + 100 = 102 元
		//假设 A--》 1个月之后转让，1个月的总本息：100 * 30 * 0.08 / 360 = 0.67 + 100 = 100.67  ---》假设A以100.35元转让， 让利：0.32元
		//B--》以100.35 购买，剩余2个月，获得该产品的总本息：100 * 30 * 3 * 0.08 / 360 + 100 = 102元  - 0.35元 = 101.65元
		//实际利率：(( 102-100.35 ) * 360/(2*100.35*30)   = 9.8
		//转让后的利率 =((原本总息 - 转让价格)* 360 / (剩余天数 * 转让价格) 
		
		double newRate = CalculatorUtil.calculate("(a-a)*a/(a*a-a)*a", worth,req.getTransferAmount(),360d,
				(double)myInvest.getTerm(),30d,myInvest.getInvestDay().doubleValue(),req.getTransferAmount());
				//(worth - req.getTransferAmount()) * 360 / ((myInvest.getTerm() * 30 - myInvest.getInvestDay()) * req.getTransferAmount());
		bg = new BigDecimal(newRate);
		newRate = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); //保留两位小数
		newRate = newRate * 100 ; //转换成百分百
		transfer.setRealRate(newRate);
		
		//更新原子账户表中的状态。
		BsSubAccount subAccount = new BsSubAccount();
		subAccount.setId(req.getSubAccountId());
		subAccount.setStatus(Constants.SUBACCOUNT_STATUS_TRANSFER);
		
		
		//查询当前是否已经有转让表，如果则只需要更新转让表即可
		BsTransfer bsTransfer = transferService.findTransferBySubAccountId(req.getSubAccountId());
		
		if(bsTransfer == null){
		
			boolean isSeccuss = transferService.addTransfer(transfer,subAccount);
			
			if(!isSeccuss){
				throw new PTMessageException(PTMessageEnum.DB_CUD_NO_EFFECT);
			}
		}else{
			transfer.setId(bsTransfer.getId());
			boolean isSeccuss = transferService.modifyTransferById(transfer);
			if(!isSeccuss){
				throw new PTMessageException(PTMessageEnum.DB_CUD_NO_EFFECT);
			}
		}
		
	}
	
	
	public void transferListQuery(ReqMsg_Transfer_TransferListQuery req, ResMsg_Transfer_TransferListQuery resp) {
		
		List<BsTransferVO> transfer = transferService.findTransferList(req.getPageIndex(), req.getPageSize());
		int pageSize=req.getPageSize();
		resp.setTotal((int)Math.ceil((double)transferService.findTransferCount()/pageSize));
		
		ArrayList<HashMap<String,Object>> bean = BeanUtils.classToArrayList(transfer);
		
		resp.setValueList(bean);
		resp.setPageIndex(req.getPageIndex());
		
		
	}
	
	public void transferDetail(ReqMsg_Transfer_TransferDetail req,ResMsg_Transfer_TransferDetail res){
		
		BsTransferVO transfer = transferService.findTransferById(req.getId());
		
		res.setId(transfer.getId());
		res.setOldRate(transfer.getOldRate());
		res.setPrice(transfer.getPrice());
		res.setProductId(transfer.getProductId());
		res.setRealRate(transfer.getRealRate());
		res.setWorth(transfer.getWorth());
		res.setSurplusTime(transfer.getSurplusTime());
		res.setUserId(transfer.getUserId1());
	}
	
	/**
	 * 暂时不用该功能
	 * @param reqMsg
	 * @param resMsg
	 */
	public void buySubmit(ReqMsg_Transfer_BuySubmit reqMsg, ResMsg_Transfer_BuySubmit resMsg){
		
		/**
		 * 1.判断该转让产品是否是本人转让，判断金额是否正确，判断购买次数是否充足，判断该银行卡卡bin是否满足
		 * 2.插入产品子账户，当前状态为银行处理中
		 * 3.流水登记
		 * 4.第三方支付确认，支付成功。（未做）
		 * 5.更新流水表
		 * 6.更新用户表，实名认证。
		 * 7.更新转让表，转让成功，更新原购买者子账户表，状态为转让成功
		 * 8.更新子账户表，状态购买成功。
		 * 9.判断是否为新的银行卡，是则插入新的银行卡。
		 */
		//判断该转让的产品不是本人转让
		BsTransferVO transfer = transferService.findTransferById(reqMsg.getTransferId());
		if(transfer.getUserId1() == reqMsg.getUserId()){
			throw new PTMessageException(PTMessageEnum.TRANSFER_NOT_MYSELF);
		}
		
		if(transfer.getPrice() != reqMsg.getMoney()){
			throw new PTMessageException(PTMessageEnum.TRANSFER_AMOUNT_ERROR);
		}
		
		//判断当前购买的次数
		int currBuyNum = userService.countBuyNum(reqMsg.getUserId());
		int buyNum = Integer.parseInt(sysConfigService.findDaliyBuyTimes().getConfValue());
		if(currBuyNum > buyNum){
			throw new PTMessageException(PTMessageEnum.REGULAR_BUY_NUM_REACHLIMIT);
		}
		
		//根据cardBin查询当前银行卡属于什么银行
		String carBin = reqMsg.getCardNo().substring(0,6);
		BsCardBin cardBin = bandCardService.findCardBinByCarBin(carBin); //根据银行卡前六位，得到银行信息
		
		if(cardBin == null){  //cardBin 为空，无法确认银行信息
			throw new PTMessageException(PTMessageEnum.BANK_CARDBIN_NO_FIND);
		}
		
		
		//查询账户结算户
		BsSubAccount account = subAccountService.findJSHSubAccountByUserId(reqMsg.getUserId());

		//开始创建产品子账户表
		BsSubAccount subAccount = new BsSubAccount(); 
		//原先购买的子账户表记录
		BsSubAccount old = subAccountService.findSubAccountById(transfer.getSubAccountId1());
		
		subAccount.setAccountId(account.getAccountId()); //主账户Id号

		//开始对产品信息记录进行插入
		BsProduct product = productService.findRegularById(reqMsg.getProductId());
		
		/*子账户编码规则：
        code+uer_id+yyyyMMddHHmmss+4位随机数
                    其中code可以为：
        2001：结算户
        2002：产品户-3个月
        2003：产品户-6个月
        2004：产品户-12个月*/
		String code = "";
		if(Constants.PRODUCT_CODE_3_80.equals(product.getCode())){
			code = accountHandleService.generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_THREE_80, reqMsg.getUserId());
		}else if(Constants.PRODUCT_CODE_6_90.equals(product.getCode())){
			code = accountHandleService.generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_SIX_90, reqMsg.getUserId());			
		}else if(Constants.PRODUCT_CODE_12_132.equals(product.getCode())){
			code = accountHandleService.generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_YEAR_132, reqMsg.getUserId());
		}else if(Constants.PRODUCT_CODE_1_70.equals(product.getCode())){
			code = accountHandleService.generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_MONTH_70, reqMsg.getUserId());
		}else if(Constants.PRODUCT_CODE_1_120.equals(product.getCode())){
			code = accountHandleService.generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_MONTH_120, reqMsg.getUserId());
		}else if(Constants.PRODUCT_CODE_1_75.equals(product.getCode())){
			code = accountHandleService.generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_MONTH_75, reqMsg.getUserId());
		}else if(Constants.PRODUCT_CODE_3_85.equals(product.getCode())){
			code = accountHandleService.generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_THREE_85, reqMsg.getUserId());
		}else if(Constants.PRODUCT_CODE_6_105.equals(product.getCode())){
			code = accountHandleService.generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_SIX_105, reqMsg.getUserId());
		}else if(Constants.PRODUCT_CODE_9_115.equals(product.getCode())){
			code = accountHandleService.generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_NINE_115, reqMsg.getUserId());
		}else if(Constants.PRODUCT_CODE_1_132.equals(product.getCode())){
			code = accountHandleService.generateAccount(Constants.SUB_ACCOUNT_PREFIX_CP_MONTH_132, reqMsg.getUserId());
		}
		
		subAccount.setCode(code);
		subAccount.setProductId(product.getId());
		subAccount.setProductCode(product.getCode());
		subAccount.setProductType(product.getType());
		subAccount.setProductName(product.getName());
		subAccount.setProductRate(product.getBaseRate());
		
		subAccount.setOpenBalance(reqMsg.getMoney());
		subAccount.setBalance(reqMsg.getMoney());
		subAccount.setAvailableBalance(0.0);
		
		subAccount.setExtraRate(transfer.getRealRate() - transfer.getOldRate());  //转让产品额外提高的利率
		
		subAccount.setBankCard(reqMsg.getCardNo());   //插入银行卡号，确认同卡进同卡出
		
		subAccount.setInterestBeginDate(old.getInterestBeginDate());
		subAccount.setOpenTime(new Date());
		subAccount.setStatus(Constants.SUBACCOUNT_STATUS_OPEN); //等待第三方支付确定，是否成功
		boolean isSuccess = subAccountService.addSubAccount(subAccount);
		
		if(!isSuccess){ 
			throw new PTMessageException(PTMessageEnum.DB_CUD_NO_EFFECT);
		}
		
		//{ 	开始记录流水账表
		
		// 发起第三方支付通道进行赎回申请
		// send ... ...
		// ... ... receive
		// 购买登记成功，3-5天内回款>>>>>模拟成功
		
		//开始更新流水		}
	
		//TODO:转让的记账，暂时空着，后续增加
		
		//开始组装子账户表和结算户表，进行更新操作
		//该处new出一个BsSubAccount 是为了防止对已经插入的数据搞混，实际上可以用上面的subAccount
		BsSubAccount subAccountModify = new BsSubAccount();
		subAccountModify.setId(subAccount.getId()); //此处的id，是数据库当中新增之后记录上的Id号
		
		subAccountModify.setStatus(Constants.SUBACCOUNT_STATUS_FINANCING);
		
		boolean flag = subAccountService.modifySubAccountById(subAccountModify);
		//当flag= true 的时候代表子账户和结算户都成功，否则失败回滚
		if(!flag){
			throw new PTMessageException(PTMessageEnum.DB_CUD_NO_EFFECT);
		}
		
		//更新转让表 ---》状态改为转让成功
		BsTransfer bsTransfer = new BsTransfer();
		bsTransfer.setId(reqMsg.getTransferId());
		bsTransfer.setStatus(Constants.TRANSFER_STATUS_SECCUSS);
		bsTransfer.setUserId2(reqMsg.getUserId());
		bsTransfer.setDealTime(new Date());
		bsTransfer.setSubAccountId2(subAccount.getId());
		flag = transferService.updateTransferById(bsTransfer);
		
		if(!flag){
			throw new PTMessageException(PTMessageEnum.DB_CUD_NO_EFFECT);
		}
		
		//更新原购买者子账户 ---》状态改为转让成功
		BsSubAccount oldSubAccount = new BsSubAccount();
		oldSubAccount.setId(transfer.getSubAccountId1());
		oldSubAccount.setStatus(Constants.SUBACCOUNT_STATUS_TRANSFERED);
		flag = subAccountService.modifySubAccountById(oldSubAccount);
		
		//更新用户表
		BsUser bsUser = userService.findUserById(reqMsg.getUserId());
		if(bsUser.getIsBindName() == Constants.IS_BIND_NAME_NO){
			bsUser.setUserName(reqMsg.getUserName());
			bsUser.setIdCard(reqMsg.getIdCard());
			bsUser.setIsBindBank(Constants.IS_BIND_BANK_YES); //绑定银行卡
			bsUser.setIsBindName(Constants.IS_BIND_NAME_YES); //实名认证
			isSuccess = userService.updateBsUser(bsUser);
			if(!isSuccess){
				throw new PTMessageException(PTMessageEnum.DB_CUD_NO_EFFECT);
			}
		}
		
		//插入银行卡
		BsBankCard bsBankCard = bandCardService.findBankCardInfoByCardNoAndUserId(reqMsg.getCardNo(),reqMsg.getUserId());
		if(bsBankCard == null){  //当不为空时，证明当前银行卡是新绑定
			
			//拿到cardBin后开始组装，插入新的银行卡
			
			BsBankCard bankCard = new BsBankCard();
			bankCard.setUserId(reqMsg.getUserId());
			bankCard.setCardNo(reqMsg.getCardNo());
			bankCard.setBindTime(new Date());
			bankCard.setCardOwner(bsUser.getUserName());
			bankCard.setBankId(cardBin.getBankId());
			bankCard.setStatus(Constants.BANK_CARD_NORMAL);
			bankCard.setIsFirst(Constants.BANK_CARD_USERED);
			bankCard.setBindTime(new Date());
			
			boolean count = bandCardService.addBankCard(bankCard);
			if(!count){
				throw new PTMessageException(PTMessageEnum.DB_CUD_NO_EFFECT);
			}
		}
		
	}
}
