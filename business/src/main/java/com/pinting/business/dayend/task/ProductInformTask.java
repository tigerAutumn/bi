package com.pinting.business.dayend.task;

import com.pinting.business.model.vo.BsProductUserVO;
import com.pinting.business.service.site.BsProductInformService;
import com.pinting.business.service.site.ProductService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品提醒短信发送
 * @author bianyatian
 * @2016-4-21 下午3:38:08
 */
@Service
public class ProductInformTask {
	private Logger log = LoggerFactory.getLogger(ProductInformTask.class);
	
	@Autowired
	private ProductService productService;
	@Autowired
	private SMSServiceClient smsServiceClient;
	@Autowired
	private BsProductInformService bsProductInformService;
	@Autowired
	private SysConfigService sysConfigService;
	
	public void execute() {
		List<BsProductUserVO> list = productService.selectProductWithInformList();
		if(list.size()>0){
			for (BsProductUserVO bsProductUserVO : list) {
				smsServiceClient.sendTemplateMessage(bsProductUserVO.getUserMobile(), 
						TemplateKey.PRODUCT_INFORM_SEND,bsProductUserVO.getName(),
						sysConfigService.findConfigByKey(Constants.PRODUCT_INFORM_MINUTE).getConfValue());
				bsProductInformService.delete(bsProductUserVO.getpInformId());
			}
		}
	}

}
