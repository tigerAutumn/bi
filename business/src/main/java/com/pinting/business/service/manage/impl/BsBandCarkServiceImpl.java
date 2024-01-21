package com.pinting.business.service.manage.impl;

import com.pinting.business.accounting.finance.enums.UnBindCheckResultEnum;
import com.pinting.business.accounting.finance.service.UserCardOperateService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.BsUserStatus;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsBankCardVO;
import com.pinting.business.model.vo.BsBindBankVO;
import com.pinting.business.model.vo.BsPayOrdersVO;
import com.pinting.business.model.vo.UnBindBankCardRes;
import com.pinting.business.service.manage.BsBankCardService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.IdcardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BsBandCarkServiceImpl implements BsBankCardService{

	@Autowired
	private BsBankCardMapper bsBankCardMapper;
	
	@Autowired
	private BsPayOrdersMapper bsPayOrdersMapper;
	
	@Autowired
	private BsPayReceiptMapper bsPayReceiptMapper;

	@Autowired
    private BsUserTransDetailMapper bsUserTransDetailMapper;
	
	@Autowired
	private BsUserMapper bsUserMapper;
	
	@Autowired
	private UserCardOperateService userCardOperateService;
	
	@Autowired
	private SpecialJnlService specialJnlService;
	
	@Override
	public List<BsBankCardVO> findBsBankCardByPage(String cardOwner,
			String mobile, String obligateMobile, String idCard,
			Integer bankId, Integer status,Integer isFirst,String cardNo, String pageNum,
			String numPerPage) {
		BsBankCardVO bsBankCardVO = new BsBankCardVO();
		bsBankCardVO.setCardOwner(cardOwner);
		bsBankCardVO.setMobile(mobile);
		bsBankCardVO.setObligateMobile(obligateMobile);
		bsBankCardVO.setIdCard(idCard);
		bsBankCardVO.setBankId(bankId);
		bsBankCardVO.setStatus(status);
		bsBankCardVO.setIsFirst(isFirst);
		bsBankCardVO.setCardNo(cardNo);
		bsBankCardVO.setPageNum(Integer.parseInt(pageNum));
		bsBankCardVO.setNumPerPage(Integer.parseInt(numPerPage));
		List<BsBankCardVO> bsBankCardList = bsBankCardMapper.bankCardUserPage(bsBankCardVO);
		return bsBankCardList.size()>0 ? bsBankCardList : null;
	}

	@Override
	public int bankCardUserCount(String cardOwner, String mobile,
			String obligateMobile, String idCard, Integer bankId, Integer status,
			Integer isFirst,String cardNo) {
		BsBankCardVO bsBankCardVO = new BsBankCardVO();
		bsBankCardVO.setCardOwner(cardOwner);
		bsBankCardVO.setMobile(mobile);
		bsBankCardVO.setObligateMobile(obligateMobile);
		bsBankCardVO.setIdCard(idCard);
		bsBankCardVO.setBankId(bankId);
		bsBankCardVO.setStatus(status);
		bsBankCardVO.setIsFirst(isFirst);
		bsBankCardVO.setCardNo(cardNo);
		return bsBankCardMapper.bankCardUserCount(bsBankCardVO);
	}

	@Override
	public List<BsBankCardVO> groupByBankName() {
		return bsBankCardMapper.groupByBankName();
	}

	@Override
	public int cardRecordCount(String bankCardNo, Integer status) {
		BsPayOrdersVO bsPayOrdersVO = new BsPayOrdersVO();
		bsPayOrdersVO.setStatus(status);
		bsPayOrdersVO.setBankCardNo(bankCardNo);
		return bsPayOrdersMapper.cardRecordCount(bsPayOrdersVO);
	}

	@Override
	public List<BsPayOrdersVO> findCardRecordByPage(String bankCardNo,
			Integer status, String pageNum, String numPerPage) {
		BsPayOrdersVO bsPayOrdersVO = new BsPayOrdersVO();
		bsPayOrdersVO.setStatus(status);
		bsPayOrdersVO.setBankCardNo(bankCardNo);
		bsPayOrdersVO.setPageNum(Integer.parseInt(pageNum));
		bsPayOrdersVO.setNumPerPage(Integer.parseInt(numPerPage));
		return bsPayOrdersMapper.findCardRecordByPage(bsPayOrdersVO);
	}

	@Override
	public int updateBsBankCard(BsBankCard record) {
		return bsBankCardMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public BsBankCard find(Integer id) {
		return bsBankCardMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<BsBankCard> findBankCards() {
		return bsBankCardMapper.selectBankCards();
	}

	@Override
	public BsPayReceipt findCardReceipt(Integer userId, String bankCardNo,
			String channel) {
		BsPayReceiptExample ex = new BsPayReceiptExample();
		ex.createCriteria().andBankCardNoEqualTo(bankCardNo).andChannelEqualTo(channel)
			.andUserIdEqualTo(userId).andIsBindCardEqualTo(Constants.IS_BIND_BANK_YES);
		List<BsPayReceipt> receipts = bsPayReceiptMapper.selectByExample(ex);
		if(CollectionUtils.isEmpty(receipts) || receipts.size() > 1){
			return null;
		}
		return receipts.get(0);
	}

	@Override
	public void modifyCardReceiptUnBind(Integer id) {
		
		BsPayReceipt receipt = new BsPayReceipt();
		receipt.setId(id);
		receipt.setIsBindCard(Constants.IS_BIND_BANK_NO);
		receipt.setReceiptNo("");
		receipt.setUpdateTime(new Date());
		bsPayReceiptMapper.updateByPrimaryKeySelective(receipt);
		
	}

	@Override
	public BsBindBankVO queryZanBankCardInfo(Integer userId) {
		List<BsBindBankVO> list = bsBankCardMapper.selectSafeBankCard(userId);
		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public BsBankCard findBsBankCardByUserIdAndCardNo(String cardNo, Integer status) {
		BsBankCardExample example = new BsBankCardExample();
		example.createCriteria().andCardNoEqualTo(cardNo).andStatusEqualTo(status);
		List<BsBankCard> bsBankCards =  bsBankCardMapper.selectByExample(example);

		if (CollectionUtils.isEmpty(bsBankCards) || bsBankCards.size() > 1) {
			return null;
		}

		return bsBankCards.get(0);
	}

	@Override
	public UnBindBankCardRes unBindCard4ManagePoliceVerify(Integer userId,
			Integer bankCardId) {
		UnBindBankCardRes res = new UnBindBankCardRes();
		BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
		//0、校验用户数据和绑卡数据
		if(bsUser == null){
			res.setErrorCode(UnBindCheckResultEnum.NO_USER.getCode());
			res.setErrorMsg(UnBindCheckResultEnum.NO_USER.getDescription());
			return res;
		}
		// 0-1 用户状态校验
		BsUserExample userExample = new BsUserExample();
		userExample.createCriteria().andIdEqualTo(userId).andStatusEqualTo(BsUserStatus.USER_STATUS_FREEZE.getIntValue());
		List<BsUser> list = bsUserMapper.selectByExample(userExample);
		if (!CollectionUtils.isEmpty(list)) {
			res.setErrorCode(UnBindCheckResultEnum.USER_STATUS_FREEZE.getCode());
			res.setErrorMsg(UnBindCheckResultEnum.USER_STATUS_FREEZE.getDescription());
			return res;
		}
		
		BsBankCardExample example = new BsBankCardExample();
        example.createCriteria().andUserIdEqualTo(userId).andIsFirstEqualTo(Constants.IS_FIRST_BANK_YES)
                .andStatusEqualTo(Constants.BANK_CARD_NORMAL).andIdEqualTo(bankCardId);
        List<BsBankCard> bankCards = bsBankCardMapper.selectByExample(example);
		if(org.apache.commons.collections.CollectionUtils.isEmpty(bankCards)){
			res.setErrorCode(UnBindCheckResultEnum.NO_BANK_CARD.getCode());
			res.setErrorMsg(UnBindCheckResultEnum.NO_BANK_CARD.getDescription());
			return res;
		}
		
		//1、校验年龄
		if(IdcardUtils.isOldAge(bsUser.getIdCard(), Constants.OLD_MAN_AGE_LIMIT)){
			res.setErrorCode(UnBindCheckResultEnum.AGE_OUT_RANGE.getCode());
			res.setErrorMsg(UnBindCheckResultEnum.AGE_OUT_RANGE.getDescription());
			return res;
		}
		
		//2、进行中的提现和充值校验
		UnBindCheckResultEnum transtypeCheck = userWithdrawTopUpCheck(userId);
		if(transtypeCheck != null){
			res.setErrorCode(transtypeCheck.getCode());
			res.setErrorMsg(transtypeCheck.getDescription());
			return res;
		}
		
		//4、调用恒丰绑卡接口
		BsBankCard card = find(bankCardId);
		//发起恒丰批量换卡接口
		try {
			userCardOperateService.unBindCard(card.getUserId(), card.getCardNo());
		} catch (PTMessageException e) {
			res.setErrorCode(UnBindCheckResultEnum.OTHER_ERROR.getCode());
			res.setErrorMsg(e.getErrMessage());
			//告警通知技术
			specialJnlService.warnDevelop4Fail(null, "人脸核身-解绑卡失败", null, "解绑BsBankCard.id="+bankCardId+"，错误信息"+e.getErrMessage(),false);
			return res;
		} catch (Exception e){
			res.setErrorCode(UnBindCheckResultEnum.OTHER_ERROR.getCode());
			res.setErrorMsg(e.getMessage());
			//告警通知技术
			specialJnlService.warnDevelop4Fail(null, "人脸核身-解绑卡失败", null, "解绑BsBankCard.id="+bankCardId+"，错误信息"+e.getMessage(),false);
			return res;
		}
		return res;
		
	}

	@Override
	public UnBindCheckResultEnum userWithdrawTopUpCheck(Integer userId) {
		BsUserTransDetail record = new BsUserTransDetail(); 
		record.setUserId(userId);
		int count = bsUserTransDetailMapper.countProcessingAllWithdraw(record);
		if(count > 0){
			return UnBindCheckResultEnum.WITHDRAW_PROGRESS;
		}
		
		BsPayOrdersVO bsPayOrdersWithdraw = new BsPayOrdersVO();
		bsPayOrdersWithdraw.setStatus(5);
		bsPayOrdersWithdraw.setUserId(userId);
		ArrayList<String> list = new ArrayList<String>();
		list.add(Constants.TRANS_BALANCE_WITHDRAW);
		list.add(Constants.TRANS_BONUS_WITHDRAW);
		bsPayOrdersWithdraw.setTransTypeList(list);
		List<BsPayOrders> bsPayOrdersWithdrawList = bsPayOrdersMapper.selectBsPayOrders(bsPayOrdersWithdraw);
		if(org.apache.commons.collections.CollectionUtils.isNotEmpty(bsPayOrdersWithdrawList)){
			return UnBindCheckResultEnum.WITHDRAW_PROGRESS;
		}
		
		
		BsPayOrdersVO bsPayOrdersTopup = new BsPayOrdersVO();
		bsPayOrdersTopup.setStatus(5);
		bsPayOrdersTopup.setUserId(userId);
		ArrayList<String> topupList = new ArrayList<String>();
		topupList.add(Constants.TRANS_TOP_UP);
		bsPayOrdersTopup.setTransTypeList(topupList);
		List<BsPayOrders> bsPayOrdersTopupList = bsPayOrdersMapper.selectBsPayOrders(bsPayOrdersTopup);
		if(org.apache.commons.collections.CollectionUtils.isNotEmpty(bsPayOrdersTopupList)){
			return UnBindCheckResultEnum.TOP_UP_PROGRESS;
		}
		return null;
	}
}
