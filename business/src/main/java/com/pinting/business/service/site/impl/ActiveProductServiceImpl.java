package com.pinting.business.service.site.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsActivityMapper;
import com.pinting.business.dao.BsProductMapper;
import com.pinting.business.model.BsProduct;
import com.pinting.business.service.site.ActiveProductService;

@Service("activeProductService")
public class ActiveProductServiceImpl implements ActiveProductService {
	
	@Autowired
	private BsProductMapper productMapper;
	
	@Autowired
	private BsActivityMapper bsActivityMapper;

	@Override
	public List<BsProduct> select00ActiveProdcutList(String terminal) {
		List<BsProduct> bsProducts = productMapper.select00ActiveProduct(terminal);
		 return bsProducts;
	}

}
