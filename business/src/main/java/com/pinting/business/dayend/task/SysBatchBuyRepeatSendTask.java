package com.pinting.business.dayend.task;

import com.pinting.business.accounting.finance.service.SysProductBuyService;
import com.pinting.business.accounting.finance.service.impl.process.SysBuyProductSendProcess;
import com.pinting.business.dao.BsBatchBuyMapper;
import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.model.BsBatchBuy;
import com.pinting.business.model.BsBatchBuyExample;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.BsPayOrdersExample;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_SysBatchBuyProduct;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_SysBatchBuyProduct;
import com.pinting.gateway.hessian.message.dafy.model.Products;
import com.pinting.gateway.out.service.DafyTransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 扫描系统产品表购买失败的产品记录，重新发起购买通知
 * @Project: business
 * @Title: SysBatchBuyRepeatSendTask.java
 * @author dingpf
 * @date 2015-11-23 下午1:21:42
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Deprecated
public class SysBatchBuyRepeatSendTask {
	private Logger log = LoggerFactory.getLogger(SysBatchBuyRepeatSendTask.class);
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private SysProductBuyService sysProductBuyService;
	@Autowired
	private DafyTransportService dafyTransportService;
	@Autowired
	private SpecialJnlService specialJnlService;
	@Autowired
	private BsBatchBuyMapper bsBatchBuyMapper;
	@Autowired
	private BsPayOrdersMapper bsPayOrdersMapper;

	/**
	 * 任务执行
	 */
	public void execute() {
		// 定时任务{理财购买重发}
		try {
			money2DafyRepeatSend();
		} catch (Exception e) {
			log.error("=========定时任务{理财购买重发}失败=========", e);
		}
		
		// 定时任务{理财购买重发-购买失败}  废除，See：money2DafyRepeatSend()
		//batchBuyRepeatSend();
	}
	
	private void money2DafyRepeatSend(){
		log.info("=========定时任务{理财购买重发}开始=========");
		try {
			//查询确认失败需重发的理财购买
			List<BsBatchBuy> batchBuys = bsBatchBuyMapper.selectBsBatchBuy4RepeatDafySend();
			if(batchBuys!=null && batchBuys.size()>0){
				for (BsBatchBuy bsBatchBuy : batchBuys) {
					try {
						//查询对应订单信息
						BsPayOrdersExample example = new BsPayOrdersExample();
						example.createCriteria().andOrderNoEqualTo(bsBatchBuy.getPay19OrderNo());
						List<BsPayOrders> orderList = bsPayOrdersMapper.selectByExample(example);
						BsPayOrders order = orderList.get(0);
						//查询对应系统产品列表，组织系统购买产品明细
						BsBatchBuyExample productsExample = new BsBatchBuyExample();
						List<String> failStatuss = new ArrayList<String>();
						failStatuss.add(Constants.BATCHBUY_STATUS_DAFY_PAY_FAIL);
						failStatuss.add(Constants.BATCHBUY_STATUS_DAFY_FAIL);
						productsExample.createCriteria().andPay19OrderNoEqualTo(bsBatchBuy.getPay19OrderNo())
							.andStatusIn(failStatuss);
						final List<BsBatchBuy> batchBuyProducts = bsBatchBuyMapper.selectByExample(productsExample);
						if(batchBuyProducts != null && batchBuyProducts.size() > 0){
							final B2GReqMsg_Payment_SysBatchBuyProduct payReq = new B2GReqMsg_Payment_SysBatchBuyProduct();
							transactionTemplate.execute(new TransactionCallbackWithoutResult(){
								@Override
								protected void doInTransactionWithoutResult(TransactionStatus status) {
									List<Products> products = new ArrayList<Products>();
									for (BsBatchBuy batchBuyProduct : batchBuyProducts) {
										Products product = new Products();
					        			product.setProductAmount(batchBuyProduct.getAmount());
					        			product.setProductCode(batchBuyProduct.getProductCode());
					        			String productOrderNo = "";//重发批次号生成规则："原始订单号-XX"，其中XX为重发次数
					        			String[] productOrderNos = batchBuyProduct.getSendBatchId().split("-");
					        			if(productOrderNos.length == 2){
					        				int tempVal = Integer.valueOf(productOrderNos[1]) + 1;
					        				productOrderNo = productOrderNos[0] + "-" + tempVal;
					        			}else{
					        				productOrderNo = productOrderNos[0] + "-1";
					        			}
					        			product.setProductOrderNo(productOrderNo);
					        			products.add(product);
					        			//更新批次号
					        			BsBatchBuy batchBuyProductTemp = new BsBatchBuy();
					        			batchBuyProductTemp.setId(batchBuyProduct.getId());
					        			batchBuyProductTemp.setSendBatchId(productOrderNo);
					        			bsBatchBuyMapper.updateByPrimaryKeySelective(batchBuyProductTemp);
									}
									//组织购买产品明细
									payReq.setProducts(products);
								}
							});
							
							//触发产品购买通知
				        	log.info("=========定时任务{理财购买重发}另起线程通知达飞进行产品购买=========");
				        	SysBuyProductSendProcess process = new SysBuyProductSendProcess();
				        	process.setSysProductBuyService(sysProductBuyService);
				        	payReq.setAmount(bsBatchBuy.getAmount());
				        	payReq.setPayFinshTime(order.getUpdateTime());
				        	payReq.setPayOrderNo(bsBatchBuy.getPay19OrderNo());
				        	payReq.setPay19MpOrderNo(batchBuyProducts.get(0).getPay19MpOrderNo());
				        	payReq.setPayReqTime(order.getCreateTime());
							payReq.setPropertySymbol(bsBatchBuy.getPropertySymbol());
				        	process.setReq(payReq);
				        	new Thread(process).start();
						}
					} catch (Exception e) {
						log.error("=========定时任务{理财购买重发}19订单号["+bsBatchBuy.getPay19OrderNo()+"]购买重发失败=========", e);
						//告警
						specialJnlService.warn4Fail(bsBatchBuy.getAmount(), 
								"定时任务{理财购买重发}19订单号["+bsBatchBuy.getPay19OrderNo()+"]购买重发失败："+StringUtil.left(e.getMessage(), 20),
								bsBatchBuy.getPay19OrderNo(),"理财购买重发",true);
					}
				}
			}else{
				log.info("=========定时任务{理财购买重发}无需重发=========");
			}
			log.info("=========定时任务{理财购买重发}结束=========");
		} catch (Exception e) {
			log.error("=========定时任务{理财购买重发}失败=========", e);
			//告警
			specialJnlService.warn4Fail(null, "定时任务{理财购买重发}理财购买发起异常："+StringUtil.left(e.getMessage(), 20),
					null,"理财购买重发",true);
		}
	}
	
	
	@Deprecated
	private void batchBuyRepeatSend(){
		log.info("=========定时任务{理财购买重发-购买失败}开始=========");
		try {
			//查询需重发的理财购买
			BsBatchBuyExample example = new BsBatchBuyExample();
			example.createCriteria().andStatusEqualTo(Constants.BATCHBUY_STATUS_DAFY_FAIL);
			final List<BsBatchBuy> batchBuys = bsBatchBuyMapper.selectByExample(example);
			String newSendBatchIds = "";
			if(batchBuys!=null && batchBuys.size()>0){
				final B2GReqMsg_Payment_SysBatchBuyProduct payReq = new B2GReqMsg_Payment_SysBatchBuyProduct();
				newSendBatchIds = transactionTemplate.execute(new TransactionCallback<String>() {
					@Override
					public String doInTransaction(TransactionStatus status) {
						List<Products> products = new ArrayList<Products>();
						BigDecimal totalAmount = new BigDecimal(0);
						String orders = "";
						for (BsBatchBuy bsBatchBuy : batchBuys) {
							totalAmount = totalAmount.add(new BigDecimal(bsBatchBuy.getAmount()));
							//组织产品明细
			    			Products product = new Products();
			    			product.setProductAmount(bsBatchBuy.getAmount());
			    			product.setProductCode(String.valueOf(bsBatchBuy.getProductCode()));
			    			String productOrderNo = "";//重发批次号生成规则："原始订单号-XX"，其中XX为重发次数
			    			String[] productOrderNos = bsBatchBuy.getSendBatchId().split("-");
			    			if(productOrderNos.length == 2){
			    				int tempVal = Integer.valueOf(productOrderNos[1]) + 1;
			    				productOrderNo = productOrderNos[0] + "-" + tempVal;
			    			}else{
			    				productOrderNo = productOrderNos[0] + "-1";
			    			}
			    			product.setProductOrderNo(productOrderNo);
			    			products.add(product);
			    			//更新批次号
			    			BsBatchBuy bsBatchBuyTemp = new BsBatchBuy();
			    			bsBatchBuyTemp.setId(bsBatchBuy.getId());
			    			bsBatchBuyTemp.setSendBatchId(productOrderNo);
		        			bsBatchBuyMapper.updateByPrimaryKeySelective(bsBatchBuyTemp);
			    			
			    			orders += productOrderNo + ",";
						}
						payReq.setAmount(totalAmount.doubleValue());
			        	payReq.setPayFinshTime(null);
			        	payReq.setPayOrderNo(null);//失败理财重发，不需传递参数：19订单号
			        	payReq.setPayReqTime(null);
			        	payReq.setProducts(products);
			        	
			        	return orders;
					}
				});
				
				//发起
	        	B2GResMsg_Payment_SysBatchBuyProduct res = dafyTransportService.sysBatchBuyProduct(payReq);
	        	
	    		//提交购买成功
	    		if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())){
	    			//系统购买产品表更新状态为 达飞购买理财处理中
	    			log.info("=========定时任务{理财购买重发-购买失败}提交购买成功，等待通知确认=========");
	    			for (BsBatchBuy bsBatchBuy : batchBuys) {
	    				if(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI.equals(bsBatchBuy.getPropertySymbol())){
	    					BsBatchBuy bsBatchBuyTemp = new BsBatchBuy();
		    				bsBatchBuyTemp.setId(bsBatchBuy.getId());
		    				bsBatchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_DAFY_PROCCESS);
		    				bsBatchBuyTemp.setUpdateTime(new Date());
		    				bsBatchBuyMapper.updateByPrimaryKeySelective(bsBatchBuyTemp);
	    				}else if(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI.equals(bsBatchBuy.getPropertySymbol())){
	    					BsBatchBuy bsBatchBuyTemp = new BsBatchBuy();
		    				bsBatchBuyTemp.setId(bsBatchBuy.getId());
		    				bsBatchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_DAFY_SUCCESS);
		    				bsBatchBuyTemp.setUpdateTime(new Date());
		    				bsBatchBuyMapper.updateByPrimaryKeySelective(bsBatchBuyTemp);
	    				}
	    			}
	    		}
	    		//提交购买失败
	    		else{
	    			//系统购买产品表更新状态为 达飞购买理财失败
	    			log.info("=========定时任务{理财购买重发-购买失败}提交购买失败=========");
	    			for (BsBatchBuy bsBatchBuy : batchBuys) {
	    				BsBatchBuy bsBatchBuyTemp = new BsBatchBuy();
	    				bsBatchBuyTemp.setId(bsBatchBuy.getId());
	    				bsBatchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_DAFY_FAIL);
	    				bsBatchBuyTemp.setUpdateTime(new Date());
	    				bsBatchBuyTemp.setDafyReturnCode(res.getResCode());
	    				bsBatchBuyTemp.setDafyReturnMsg(res.getResMsg());
	    				bsBatchBuyMapper.updateByPrimaryKeySelective(bsBatchBuyTemp);
	    			}
	    			//告警
	    			specialJnlService.warn4Fail(payReq.getAmount(), "定时任务{理财购买重发-购买失败}产品批次号["+newSendBatchIds+"]购买达飞理财重发失败:"+res.getResMsg(),
	    					null,"理财购买重发-购买失败",true);
	    			
	    		}
			}else{
				log.info("=========定时任务{理财购买重发-购买失败}无需重发=========");
			}
			log.info("=========定时任务{理财购买重发-购买失败}结束=========");
		} catch (Exception e) {
			log.error("=========定时任务{理财购买重发-购买失败}失败=========", e);
			//告警
			specialJnlService.warn4Fail(null, "定时任务{理财购买重发-购买失败}理财购买发起异常："+StringUtil.left(e.getMessage(), 20),
					null,"理财购买重发-购买失败",true);
		}
	}

}
