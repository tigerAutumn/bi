package com.pinting.business.facade.manage;

import com.pinting.business.accounting.finance.service.UserCardOperateService;
import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.manage.message.*;
import com.pinting.business.model.BsBank;
import com.pinting.business.model.BsBankCard;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.vo.BsBankCardVO;
import com.pinting.business.model.vo.BsPayOrdersVO;
import com.pinting.business.service.manage.BsBankCardService;
import com.pinting.business.service.manage.BsBankService;
import com.pinting.business.service.manage.BsSubAccountService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



@Component("BsBankCard")
public class BsBankCardFacade {
	
	@Autowired
	private BsBankCardService bsBankCardService;
	
	@Autowired
	private BsSubAccountService bsSubAccountService;
	
	@Autowired
	private BsPayOrdersMapper bsPayOrdersMapper;

	@Autowired
	private BsBankService bsBankService;

	@Autowired
	private UserCardOperateService userCardOperateService;
	
	public void bankCardListQuery(ReqMsg_BsBankCard_BankCardListQuery req,ResMsg_BsBankCard_BankCardListQuery res){
		String pageNum = req.getPageNum();
		String numPerPage = req.getNumPerPage();
		int totalRows = bsBankCardService.bankCardUserCount(req.getCardOwner(),req.getMobile(),
				req.getObligateMobile(),req.getIdCard(),req.getBankId(),req.getStatus(),req.getIsFirst(),req.getCardNo());
		if (totalRows > 0) {
			List<BsBankCardVO> list = bsBankCardService.findBsBankCardByPage(req.getCardOwner(),req.getMobile(),
					req.getObligateMobile(),req.getIdCard(),req.getBankId(),req.getStatus(),req.getIsFirst(),req.getCardNo(),
					pageNum,numPerPage);
			ArrayList<HashMap<String, Object>> mapList = BeanUtils.classToArrayList(list);
			res.setBankCards(mapList);
		}
		//List<BsBankCardVO> bsBankCardVOs = bsBankCardService.groupByBankName();
		 List<BsBank> bsBanks = bsBankService.groupByBankName();
		ArrayList<HashMap<String, Object>> bankNameList = BeanUtils.classToArrayList(bsBanks);
		res.setBankNameList(bankNameList);
		res.setPageNum(pageNum);
		res.setNumPerPage(numPerPage);
		res.setTotalRows(String.valueOf(totalRows));
	}
	
	public void cardRecordListQuery(ReqMsg_BsBankCard_CardRecordListQuery req,ResMsg_BsBankCard_CardRecordListQuery res){
		String pageNum = req.getPageNum();
		String numPerPage = req.getNumPerPage();
		int totalRows = bsBankCardService.cardRecordCount(req.getBankCardNo(), req.getStatus());
		if (totalRows > 0) {
			List<BsPayOrdersVO> list = bsBankCardService.findCardRecordByPage(req.getBankCardNo(), req.getStatus(), pageNum, numPerPage);
			ArrayList<HashMap<String, Object>> mapList = BeanUtils.classToArrayList(list);
			res.setPayOrders(mapList);
		}
		res.setPageNum(pageNum);
		res.setNumPerPage(numPerPage);
		res.setTotalRows(String.valueOf(totalRows));
	}
	
	public void bsBankCardStatusModify(ReqMsg_BsBankCard_BsBankCardStatusModify req,ResMsg_BsBankCard_BsBankCardStatusModify res){
		BsBankCard card = bsBankCardService.find(req.getId());
		//查询子账户中是否有余额，并且不是投资中或处理中
		/*BsSubAccount record = new BsSubAccount();
		record.setBankCard(card.getCardNo());
		List<BsSubAccount> bsSubAccountList = bsSubAccountService.findList(record);
		if(bsSubAccountList.size() > 0 || bsSubAccountList != null){
			for (BsSubAccount bsSubAccount : bsSubAccountList) {
				if(bsSubAccount.getAvailableBalance() > 0 && bsSubAccount.getProductType().equals("JSH")){//说明子账号中有余额
					res.setSignMsg(ResMsg_BsBankCard_BsBankCardStatusModify.NOT_NULL_BALANCE);
					res.setMsg("账户存在余额，需处理后才能解绑！");
					return;
				}
				if(bsSubAccount.getStatus() == 2&& bsSubAccount.getProductType().equals("REG")){//status=2（投资中）
					res.setSignMsg(ResMsg_BsBankCard_BsBankCardStatusModify.NOT_NULL_BANKCARD_STATUS_2);
					res.setMsg("账户存在投资中，需处理后才能解绑！");
					return;
				}
				if(bsSubAccount.getStatus() == 3&& bsSubAccount.getProductType().equals("REG")){//3（转让中）
					res.setSignMsg(ResMsg_BsBankCard_BsBankCardStatusModify.NOT_NULL_BANKCARD_STATUS_3);
					res.setMsg("账户存在转让中，需处理后才能解绑！");
					return;
			    }
				if(bsSubAccount.getStatus() == 7&& bsSubAccount.getProductType().equals("REG")){//7(结算中)
					res.setSignMsg(ResMsg_BsBankCard_BsBankCardStatusModify.NOT_NULL_BANKCARD_STATUS_7);
					res.setMsg("账户存在结算中，需处理后才能解绑！");
					return;
				}
			}
		}*/
		//查询订单信息类型为（卡购买，充值，回款到用户银行卡，余额提现为非处理中的）为支付处理中的status = 5
		BsPayOrdersVO bsPayOrdersVO = new BsPayOrdersVO();
		bsPayOrdersVO.setStatus(5);
		//bsPayOrdersVO.setBankCardNo(card.getCardNo());
		bsPayOrdersVO.setUserId(req.getUserId());
		ArrayList<String> list = new ArrayList<String>();
		list.add(Constants.TRANS_CARD_BUY_PRODUCT);
		list.add(Constants.TRANS_BALANCE_BUY_PRODUCT);
		list.add(Constants.TRANS_TOP_UP);
		list.add(Constants.TRANS_BALANCE_WITHDRAW);
		list.add(Constants.TRANS_RETURN_2_USER_BANK_CARD);
		list.add(Constants.TRANS_BONUS_WITHDRAW);
		bsPayOrdersVO.setTransTypeList(list);
		List<BsPayOrders> bsPayOrdersList = bsPayOrdersMapper.selectBsPayOrders(bsPayOrdersVO);
		if(bsPayOrdersList.size() >0 && bsPayOrdersList != null){
			res.setSignMsg(ResMsg_BsBankCard_BsBankCardStatusModify.NOT_NULL_PAYORDERS_STATUS);
			res.setMsg("有处理中的订单，需处理后才能解绑！");
			return;
		}
		
		//发起恒丰批量换卡接口
		try {
			userCardOperateService.unBindCard(card.getUserId(), card.getCardNo());
			res.setSignMsg(ResMsg_BsBankCard_BsBankCardStatusModify.SUCCESS);
		} catch (PTMessageException e) {
			res.setMsg("解绑失败：" + e.getErrMessage());
			res.setSignMsg(ResMsg_BsBankCard_BsBankCardStatusModify.OTHER_DATA_ERROR_STATUS);
		} catch (Exception e){
			res.setSignMsg(ResMsg_BsBankCard_BsBankCardStatusModify.NOT_NULL_PAYORDERS_STATUS);
			res.setMsg("解绑失败：" + e.getMessage());
		}

	}

}
