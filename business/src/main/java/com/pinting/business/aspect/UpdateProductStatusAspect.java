package com.pinting.business.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.pinting.business.accounting.service.impl.process.UpdateProductStatusProcess;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsProduct;
import com.pinting.business.service.site.ProductService;
import com.pinting.gateway.hessian.message.qb178.B2GReqMsg_APP178_UpdateProductInfo;
import com.pinting.gateway.out.service.App178TransportService;
/**
 * 钱报178APP平台接入   - 通知产品状态更新切面
 * 1、产品开始
 * 2、产品结束
 * 3、到期回款（待定）  @TODO
 * @project business
 * @title UpdateProductStatusAspect.java
 * @author Dragon & cat
 * @date 2017-8-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Aspect
@Component
@Order(6)
public class UpdateProductStatusAspect {
	private Logger log = LoggerFactory.getLogger(UpdateProductStatusAspect.class);
	
	
	@Autowired
	private App178TransportService app178TransportService;
	@Autowired
	private ProductService productService;
	
	//产品开始（申购中）
	@Pointcut("execution(public * com.pinting.business.service.site.impl.ProductServiceImpl.openProductById(..))")
	public void openProductPointcut(){}
	
	//产品完成（已确权）
	@Pointcut("execution(public * com.pinting.business.service.manage.impl.ProductReleaseServiceImpl.finishProduct(..))")
	public void finishProductPointcut(){}
	
	@AfterReturning("openProductPointcut()")
	public void openProductAfter(JoinPoint call) {
		try {
			BsProduct bsProduct = (BsProduct)call.getArgs()[0];
			if (bsProduct == null ) {
				throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
			}
			Integer productId = bsProduct.getId();
			log.info("钱报178APP平台更新产品状态(发布)切面，productId="+productId);
			BsProduct product = productService.findRegularById(productId);
			if (product == null ) {
				throw new PTMessageException(PTMessageEnum.PRODUCT_INFO_NOT_FOUND_ERROR);
			}
			if (product.getShowTerminal().contains("_178")) {
				log.info("钱报178APP平台更新产品状态(发布)切面，是钱报产品，进行通知");
				B2GReqMsg_APP178_UpdateProductInfo req = new B2GReqMsg_APP178_UpdateProductInfo();
				req.setProduct_code(String.valueOf(productId));
				req.setProduct_status("buying");
				UpdateProductStatusProcess process = new UpdateProductStatusProcess();
				process.setApp178TransportService(app178TransportService);
				process.setUpdateProductInfo(req);
				new Thread(process).start();
			}
		} catch(Exception e) {
			log.error("钱报178APP平台更新产品状态(完成)通知失败：{}",e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	@AfterReturning("finishProductPointcut()")
	public void finishProductAfter(JoinPoint call) {
		try {
			Integer productId = (Integer)call.getArgs()[0];
			log.info("钱报178APP平台更新产品状态(完成)切面，productId="+productId);
			BsProduct product = productService.findRegularById(productId);
			if (product == null ) {
				throw new PTMessageException(PTMessageEnum.PRODUCT_INFO_NOT_FOUND_ERROR);
			}
			if (product.getShowTerminal().contains("_178")) {
				log.info("钱报178APP平台更新产品状态(完成)切面，是钱报产品，进行通知");
				B2GReqMsg_APP178_UpdateProductInfo req = new B2GReqMsg_APP178_UpdateProductInfo();
				req.setProduct_code(String.valueOf(productId));
				req.setProduct_status("confirmed");
				UpdateProductStatusProcess process = new UpdateProductStatusProcess();
				process.setApp178TransportService(app178TransportService);
				process.setUpdateProductInfo(req);
				new Thread(process).start();
			}
		} catch (Exception e) {
			log.error("钱报178APP平台更新产品状态(完成)通知失败：{}",e.getMessage());
			e.printStackTrace();
		}
	}
}
