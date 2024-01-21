package com.pinting.business.service.manage.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pinting.core.util.ConstantUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsBankCardMapper;
import com.pinting.business.dao.BsPayOrdersJnlMapper;
import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.dao.BsPayReceiptMapper;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.enums.BaoFooEnum;
import com.pinting.business.model.BsBankCard;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.BsPayOrdersJnl;
import com.pinting.business.model.BsPayReceipt;
import com.pinting.business.model.BsPayReceiptExample;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.vo.BsBindBankVO;
import com.pinting.business.service.manage.BaofooBatchService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_BindCardConfirm;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_BindCardConfirm;
import com.pinting.gateway.out.service.BaoFooTransportService;


/**
 * 
 *  * @author shh    2016/9/11
 * 
 * 
 * 批量绑卡
 * 
 * 
 * 1  如果bindCardMobile为空，查询本地库中所有绑卡成功的银行卡信息，进行批量绑卡
 * 
 * 2  如果bindCardMobile不为空，校验手机号格式是否已逗号隔开，手机号去重；根据手机号查询绑卡表，绑卡表中记录存在并且绑卡成功的记录，进行批量绑卡
 * 
 * 3  组装数据
 * 
 * 4  发起批量绑卡操作
 * 
 * 5  处理返回结果
 *   5.1 捕获异常
 *  	5.1.1  插入订单表bs_pay_orders
 *  	5.2.2  插入订单流水bs_pay_orders_jnl
 *  	5.3.3  支付签约回执表 bs_pay_receipt：如果bs_pay_receipt表中不存在宝付记录则新增，如果存在则更新
 *  
 *   5.2 返回成功
 *  	5.2.1  插入订单表bs_pay_orders
 *  	5.2.2  插入订单流水bs_pay_orders_jnl
 *  	5.2.3  如果bs_pay_receipt表中不存在宝付记录则新增；如果有，判断是否存在状态为失败的记录，是则将状态改为成功
 *  	
 *   5.3 返回失败
 *  	5.3.1  插入订单表bs_pay_orders
 *  	5.3.2  插入订单流水bs_pay_orders_jnl
 *  	5.3.3  支付签约回执表 bs_pay_receipt：如果bs_pay_receipt表中不存在宝付记录则新增；如果有，则不需操作
 *  	5.3.4  修改用户绑卡表，将之前成功的状态置为失败
 *  	5.3.5  修改用户绑卡状态，将用户状态置为未绑卡
 *  6  一次批量绑卡请求处理完成，休眠50毫秒，防止服务卡住
 */

@Service
public class BaofooBatchServiceImpl implements BaofooBatchService {
	
	 private Logger logger = LoggerFactory.getLogger(BaofooServiceImpl.class);

     @Autowired
     private BaoFooTransportService baoFooTransportService;
     @Autowired
     private BsBankCardMapper bankCardMapper;
     @Autowired
     private BsPayOrdersMapper bsPayOrdersMapper;
     @Autowired
     private BsPayOrdersJnlMapper bsPayOrdersJnlMapper;
     @Autowired
     private BsPayReceiptMapper bsPayReceiptMapper;
     @Autowired
     private BsUserMapper bsUserMapper;

	@Override
	public String batchBindCard(String bindCardMobile) {
		// userList组装后的数据 用来发起宝付绑卡
		List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
		// count存放宝付绑卡成功的记录条数
		Integer count=0;
		if (bindCardMobile == null || "".equals(bindCardMobile)) {
			//1、批量绑卡手机号为空，查询库中绑卡成功的银行卡信息
			List<BsBindBankVO> sucessCardList = bankCardMapper.selectCardSuccess();
			if(sucessCardList != null && sucessCardList.size() != 0) {
				for(int i=0;i<sucessCardList.size();i++){
					Map<String, Object> userMap = new HashMap<String, Object>();
					BsBindBankVO bsUserCard = sucessCardList.get(i);
					String cardNo = (String)bsUserCard.getCardNo();
					String mobile = (String)bsUserCard.getMobile();
					String idCard = (String)bsUserCard.getIdCard();
					String userName = (String)bsUserCard.getUserName();
					Integer bankId = bsUserCard.getBankId();
					Integer userId = bsUserCard.getUserId();
					Integer bankCardId = bsUserCard.getId();
					
					userMap.put("cardNo", cardNo);
					userMap.put("mobile", mobile);
					userMap.put("idCard", idCard);
					userMap.put("userName", userName);
					userMap.put("bankId", bankId);
					userMap.put("userId", userId);
					userMap.put("bankCardId",bankCardId);
					//3、组装数据
					userList.add(userMap);
				}
			}else{
				return "没有存在的银行卡绑定信息";
			}
		} else {
			//2、批量绑卡手机号不为空，解析字符串数据
			String trimStr = StringUtil.trimStr(bindCardMobile);
			String[] phones = trimStr.replace("，", ",").split(",");
			List<String> phoneList = Arrays.asList(phones);
			
			//根据手机号查询绑卡成功的银行卡信息是否存在
			List<BsBindBankVO> sucessCardList = bankCardMapper.selectBankCardByMobile(phoneList);
			if(sucessCardList != null && sucessCardList.size() != 0) {
				for(int i=0;i<sucessCardList.size();i++){
					Map<String, Object> userMap = new HashMap<String, Object>();
					BsBindBankVO bsUserCard = sucessCardList.get(i);
					String cardNo = (String)bsUserCard.getCardNo();
					String mobile = (String)bsUserCard.getMobile();
					String idCard = (String)bsUserCard.getIdCard();
					String userName = (String)bsUserCard.getUserName();
					Integer bankId = bsUserCard.getBankId();
					Integer userId = bsUserCard.getUserId();
					Integer bankCardId = bsUserCard.getId();
					
					userMap.put("cardNo", cardNo);
					userMap.put("mobile", mobile);
					userMap.put("idCard", idCard);
					userMap.put("userName", userName);
					userMap.put("bankId", bankId);
					userMap.put("userId", userId);
					userMap.put("bankCardId",bankCardId);
					//3 、组装数据
					userList.add(userMap);
				}
			}else{
				return "没有存在的银行卡绑定信息";
			}
		}
		
		//批量绑卡
		for (Map<String, Object> user : userList) {
			String transId = Util.generateUserTransNo4BaoFoo();
			String trans_serial_no = Util.generateUserOrderNo4Pay19((Integer) user.get("userId"));

	        //4、发起绑卡认证
	        B2GReqMsg_BaoFooQuickPay_BindCardConfirm req = new B2GReqMsg_BaoFooQuickPay_BindCardConfirm();
            req.setMobile((String)user.get("mobile"));
            req.setAcc_no((String)user.get("cardNo"));
            req.setId_card((String)user.get("idCard"));
            req.setId_holder((String)user.get("userName"));
            req.setPay_code(BaoFooEnum.bankMap.get(BaoFooEnum.cardBinMap.get(user.get("bankId").toString())));
            req.setSms_code(null);
            req.setTrans_id(transId);
            req.setTrans_serial_no(trans_serial_no);
            B2GResMsg_BaoFooQuickPay_BindCardConfirm res = null;
            
            try {
            	logger.info("宝付批量绑卡请求信息：{}", req);
            	//5、绑卡后的返回的数据
                res = baoFooTransportService.bindCardConfirm(req);
            } catch (Exception e) {
            	//5.1 捕获异常
            	logger.error("宝付批量绑卡操作异常：{}", e.getMessage());

	   			 //5.1.1  插入订单表bs_pay_orders
            	 BsPayOrders order = new BsPayOrders();
		         order.setOrderNo(Util.generateOrderNo((Integer)user.get("userId")));
		         order.setUserId((Integer)user.get("userId"));
		         order.setChannel(Constants.CHANNEL_BAOFOO);
		         order.setStatus(Constants.ORDER_STATUS_FAIL);
		         order.setBankName(BaoFooEnum.cardBinMap.get(user.get("bankId").toString()));
		         order.setTerminalType(Constants.PAY_ORDER_TERMINAL_PC);
		         order.setCreateTime(new Date());
		         order.setUpdateTime(new Date());
		         order.setBankId((Integer)user.get("bankId"));
		         order.setBankCardNo((String)user.get("cardNo"));
		         order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
		         order.setTransType(Constants.TRANS_USER_BIND_CARD);
		         order.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
		         order.setMobile((String)user.get("mobile"));
		         order.setIdCard((String)user.get("idCard"));
		         order.setUserName((String)user.get("userName"));
		         order.setReturnCode(Constants.ORDER_TRANS_CODE_FAIL);
		         order.setReturnMsg("批量绑卡失败：网络通信异常");// e.getMessage()
				 order.setAmount(0d);
		         bsPayOrdersMapper.insertSelective(order);
		         
		         //5.1.2  插入订单流水bs_pay_orders_jnl
	             BsPayOrdersJnl jnl = new BsPayOrdersJnl();
	             jnl.setOrderId(order.getId());
	             jnl.setOrderNo(order.getOrderNo());
	             jnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
	             jnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
	             jnl.setSysTime(new Date());
	             jnl.setUserId(order.getUserId());
	             jnl.setCreateTime(new Date());
	             jnl.setReturnCode(Constants.ORDER_TRANS_CODE_FAIL);
	             jnl.setReturnMsg("批量绑卡失败：网络通信异常");//e.getMessage()
	             bsPayOrdersJnlMapper.insertSelective(jnl);
	             
	             //5.1.3  支付签约回执表 bs_pay_receipt，如果bs_pay_receipt表中不存在宝付记录则新增，如果存在则更新
	             BsPayReceiptExample receiptExample = new BsPayReceiptExample();
	             receiptExample.createCriteria().andUserIdEqualTo(order.getUserId()).andBankCardNoEqualTo(order.getBankCardNo()).andChannelEqualTo(Constants.CHANNEL_BAOFOO);
	             List<BsPayReceipt> receiptList = bsPayReceiptMapper.selectByExample(receiptExample);
	             if(CollectionUtils.isEmpty(receiptList)) {
	                 BsPayReceipt bsPayReceipt = new BsPayReceipt();
	                 bsPayReceipt.setBankCardNo(order.getBankCardNo());
	                 bsPayReceipt.setUserId(order.getUserId());
	                 bsPayReceipt.setUserName(order.getUserName());
	                 bsPayReceipt.setBankName(order.getBankName());
	                 bsPayReceipt.setIdCard(order.getIdCard());
	                 bsPayReceipt.setMobile(order.getMobile());
	                 bsPayReceipt.setChannel(Constants.CHANNEL_BAOFOO);
	                 bsPayReceipt.setIsBindCard(Constants.BAOFOO_BIND_NO);
	                 bsPayReceipt.setCreateTime(new Date());
	                 bsPayReceipt.setReceiptNo(res.getBind_id());
	                 bsPayReceipt.setUpdateTime(new Date());
	                 bsPayReceiptMapper.insertSelective(bsPayReceipt);
	             }else {
					if (Constants.BAOFOO_BIND_NO.equals(receiptList.get(0).getIsBindCard())) {
		                 BsPayReceipt bsPayReceipt = new BsPayReceipt();
		                 bsPayReceipt.setId(receiptList.get(0).getId());
		                 bsPayReceipt.setBankCardNo(order.getBankCardNo());
		                 bsPayReceipt.setUserId(order.getUserId());
		                 bsPayReceipt.setUserName(order.getUserName());
		                 bsPayReceipt.setBankName(order.getBankName());
		                 bsPayReceipt.setIdCard(order.getIdCard());
		                 bsPayReceipt.setMobile(order.getMobile());
		                 bsPayReceipt.setChannel(Constants.CHANNEL_BAOFOO);
		                 bsPayReceipt.setIsBindCard(Constants.BAOFOO_BIND_NO);
		                 bsPayReceipt.setCreateTime(new Date());
		                 bsPayReceipt.setReceiptNo(res.getBind_id());
		                 bsPayReceipt.setUpdateTime(new Date());
		                 bsPayReceiptMapper.updateByPrimaryKeySelective(bsPayReceipt);
					}
	             }
                continue;
            }
            //5.2 返回成功
            if (res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {
            	 count++;
            	 logger.info("宝付批量绑卡成功：{}", res);
            	 //5.2.1  插入订单表bs_pay_orders
	   			 BsPayOrders order = new BsPayOrders();
		         order.setOrderNo(Util.generateOrderNo((Integer)user.get("userId")));
		         order.setUserId((Integer)user.get("userId"));
		         order.setChannel(Constants.CHANNEL_BAOFOO);
		         order.setStatus(Constants.ORDER_STATUS_SUCCESS);
		         order.setBankName(BaoFooEnum.cardBinMap.get(user.get("bankId").toString()));
		         order.setTerminalType(Constants.PAY_ORDER_TERMINAL_PC);
		         order.setCreateTime(new Date());
		         order.setUpdateTime(new Date());
		         order.setBankId((Integer)user.get("bankId"));
		         order.setBankCardNo((String)user.get("cardNo"));
		         order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
		         order.setTransType(Constants.TRANS_USER_BIND_CARD);
		         order.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
		         order.setMobile((String)user.get("mobile"));
		         order.setIdCard((String)user.get("idCard"));
		         order.setUserName((String)user.get("userName"));
		         order.setReturnCode(res.getResCode());
		         order.setReturnMsg(res.getResMsg());
		         order.setAmount(0d);
		         bsPayOrdersMapper.insertSelective(order);
		         
		         //5.2.2  插入订单流水bs_pay_orders_jnl
	             BsPayOrdersJnl jnl = new BsPayOrdersJnl();
	             jnl.setOrderId(order.getId());
	             jnl.setOrderNo(order.getOrderNo());
	             jnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
	             jnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
	             jnl.setSysTime(new Date());
	             jnl.setUserId(order.getUserId());
	             jnl.setCreateTime(new Date());
	             jnl.setReturnCode(res.getResCode());
	             jnl.setReturnMsg(res.getResMsg());
	             bsPayOrdersJnlMapper.insertSelective(jnl);
	             
	             //5.2.3  如果bs_pay_receipt表中不存在宝付记录则新增；如果有，判断是否存在状态为失败的记录，是则将状态改为成功
	             BsPayReceiptExample receiptExample = new BsPayReceiptExample();
	             receiptExample.createCriteria().andUserIdEqualTo(order.getUserId()).andBankCardNoEqualTo(order.getBankCardNo()).andChannelEqualTo(Constants.CHANNEL_BAOFOO)
	             .andUserNameEqualTo(order.getUserName()).andMobileEqualTo(order.getMobile()).andIdCardEqualTo(order.getIdCard());
	             List<BsPayReceipt> receiptList = bsPayReceiptMapper.selectByExample(receiptExample);
	             if(CollectionUtils.isEmpty(receiptList)) {
	                 BsPayReceipt bsPayReceipt = new BsPayReceipt();
	                 bsPayReceipt.setBankCardNo(order.getBankCardNo());
	                 bsPayReceipt.setUserId(order.getUserId());
	                 bsPayReceipt.setUserName(order.getUserName());
	                 bsPayReceipt.setBankName(order.getBankName());
	                 bsPayReceipt.setIdCard(order.getIdCard());
	                 bsPayReceipt.setMobile(order.getMobile());
	                 bsPayReceipt.setChannel(Constants.CHANNEL_BAOFOO);
	                 bsPayReceipt.setIsBindCard(Constants.BAOFOO_BIND_YES);
	                 bsPayReceipt.setCreateTime(new Date());
	                 bsPayReceipt.setReceiptNo(res.getBind_id());
	                 bsPayReceiptMapper.insertSelective(bsPayReceipt);
	             }else {
	            	 BsPayReceipt bsPayReceipt = new BsPayReceipt();
	            	 bsPayReceipt.setId(receiptList.get(0).getId());
	                 bsPayReceipt.setBankCardNo(order.getBankCardNo());
	                 bsPayReceipt.setUserId(order.getUserId());
	                 bsPayReceipt.setUserName(order.getUserName());
	                 bsPayReceipt.setBankName(order.getBankName());
	                 bsPayReceipt.setIdCard(order.getIdCard());
	                 bsPayReceipt.setMobile(order.getMobile());
	                 bsPayReceipt.setChannel(Constants.CHANNEL_BAOFOO);
	                 bsPayReceipt.setIsBindCard(Constants.BAOFOO_BIND_YES);
	                 bsPayReceipt.setCreateTime(new Date());
	                 bsPayReceipt.setReceiptNo(res.getBind_id());
	                 bsPayReceiptMapper.updateByPrimaryKeySelective(bsPayReceipt);
	             }
            } else {
            	//5.3 返回失败
            	logger.error("宝付批量绑卡绑定失败：{}", res);
            	
            	 //5.3.1  插入订单表bs_pay_orders
	   			 BsPayOrders order = new BsPayOrders();
		         order.setOrderNo(Util.generateOrderNo((Integer)user.get("userId")));
		         order.setUserId((Integer)user.get("userId"));
		         order.setChannel(Constants.CHANNEL_BAOFOO);
		         order.setStatus(Constants.ORDER_STATUS_FAIL);
		         order.setBankName(BaoFooEnum.cardBinMap.get(user.get("bankId").toString()));
		         order.setTerminalType(Constants.PAY_ORDER_TERMINAL_PC);
		         order.setCreateTime(new Date());
		         order.setUpdateTime(new Date());
		         order.setBankId((Integer)user.get("bankId"));
		         order.setBankCardNo((String)user.get("cardNo"));
		         order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
		         order.setTransType(Constants.TRANS_USER_BIND_CARD);
		         order.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
		         order.setMobile((String)user.get("mobile"));
		         order.setIdCard((String)user.get("idCard"));
		         order.setUserName((String)user.get("userName"));
                 if (res.getExtendMap()!= null) {
                     String thirdReturnCode =  (String)res.getExtendMap().get("thirdReturnCode");
                     order.setReturnCode(thirdReturnCode);
				 }else {
					 order.setReturnCode(res.getResCode());
				 }
		         order.setReturnMsg("批量绑卡失败：");//res.getResMsg()
				 order.setAmount(0d);
		         bsPayOrdersMapper.insertSelective(order);
		     
		         //5.3.2  插入订单流水bs_pay_orders_jnl
	             BsPayOrdersJnl jnl = new BsPayOrdersJnl();
	             jnl.setOrderId(order.getId());
	             jnl.setOrderNo(order.getOrderNo());
	             jnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
	             jnl.setChannelTransType(Constants.CHANNEL_TRS_QUICKPAY);
	             jnl.setSysTime(new Date());
	             jnl.setUserId(order.getUserId());
	             jnl.setCreateTime(new Date());
                 jnl.setReturnCode(Constants.ORDER_TRANS_CODE_FAIL);
	             jnl.setReturnMsg("批量绑卡失败：");//res.getResMsg()
	             bsPayOrdersJnlMapper.insertSelective(jnl);
	             
	             //5.3.3  支付签约回执表 bs_pay_receipt：如果bs_pay_receipt表中不存在宝付记录则新增；如果有，则不需操作
	             BsPayReceiptExample receiptExample = new BsPayReceiptExample();
	             receiptExample.createCriteria().andUserIdEqualTo(order.getUserId()).andBankCardNoEqualTo(order.getBankCardNo()).andChannelEqualTo(Constants.CHANNEL_BAOFOO)
	             .andUserNameEqualTo(order.getUserName()).andMobileEqualTo(order.getMobile()).andIdCardEqualTo(order.getIdCard());
	             List<BsPayReceipt> receiptList = bsPayReceiptMapper.selectByExample(receiptExample);
	             if(CollectionUtils.isEmpty(receiptList)) {
	                 BsPayReceipt bsPayReceipt = new BsPayReceipt();
	                 bsPayReceipt.setBankCardNo(order.getBankCardNo());
	                 bsPayReceipt.setUserId(order.getUserId());
	                 bsPayReceipt.setUserName(order.getUserName());
	                 bsPayReceipt.setBankName(order.getBankName());
	                 bsPayReceipt.setIdCard(order.getIdCard());
	                 bsPayReceipt.setMobile(order.getMobile());
	                 bsPayReceipt.setChannel(Constants.CHANNEL_BAOFOO);
	                 bsPayReceipt.setIsBindCard(Constants.BAOFOO_BIND_NO);
	                 bsPayReceipt.setCreateTime(new Date());
	                 bsPayReceipt.setReceiptNo(res.getBind_id());
	                 bsPayReceiptMapper.insertSelective(bsPayReceipt);
	             }else {
	                 BsPayReceipt bsPayReceipt = new BsPayReceipt();
	                 bsPayReceipt.setId(receiptList.get(0).getId());
	                 bsPayReceipt.setBankCardNo(order.getBankCardNo());
	                 bsPayReceipt.setUserId(order.getUserId());
	                 bsPayReceipt.setUserName(order.getUserName());
	                 bsPayReceipt.setBankName(order.getBankName());
	                 bsPayReceipt.setIdCard(order.getIdCard());
	                 bsPayReceipt.setMobile(order.getMobile());
	                 bsPayReceipt.setChannel(Constants.CHANNEL_BAOFOO);
	                 bsPayReceipt.setIsBindCard(Constants.BAOFOO_BIND_NO);
	                 bsPayReceipt.setCreateTime(new Date());
	                 bsPayReceipt.setReceiptNo(res.getBind_id());
	                 bsPayReceiptMapper.updateByPrimaryKeySelective(bsPayReceipt);
		          }
	             //5.3.4  更新银行卡信息表,修改用户绑卡表，将之前成功的状态置为失败
                 BsBankCard bsBankCard = new BsBankCard();
                 bsBankCard.setId((Integer)user.get("bankCardId"));
                 bsBankCard.setStatus(Constants.DAFY_BINDING_STAUTS_BIND_FAIL);
                 bsBankCard.setIsFirst(Constants.IS_FIRST_BANK_NO);
                 bsBankCard.setBindTime(new Date());
                 bankCardMapper.updateByPrimaryKeySelective(bsBankCard);
                 
                 //5.3.5 更新用户表,修改用户状态，将用户状态置为未绑卡
                 BsUser bsUser = new BsUser();
                 bsUser.setId((Integer)user.get("userId"));
                 bsUser.setIsBindBank(Constants.IS_BIND_BANK_YES);
                 bsUser.setIsBindName(Constants.IS_BIND_NAME_YES);
                 bsUser.setUserName(order.getUserName());
                 bsUser.setIdCard(order.getIdCard());
                 bsUserMapper.updateByPrimaryKeySelective(bsUser);
            }
            try {
            	//6、一次批量绑卡请求处理完成，休眠50毫秒，防止服务卡住
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}

		}		
		return "success,"+count;
	}

}
