package com.pinting.business.service.manage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsProductMapper;
import com.pinting.business.model.BsProduct;
import com.pinting.business.model.BsProductExample;
import com.pinting.business.model.vo.MProductVO;
import com.pinting.business.model.vo.ProductDetailVO;
import com.pinting.business.service.manage.MProductService;

@Service
public class MProductServiceImpl implements MProductService{
	@Autowired
	private BsProductMapper productMapper;

	@Override
	public int saveBsProduct(BsProduct product) {
		return productMapper.insertSelective(product);
	}

	@Override
	public int updateBsProduct(BsProduct product) {
		return productMapper.updateByPrimaryKey(product);
	}

	@Override
	public int deleteBsProduct(Integer id) {
		return productMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<MProductVO> findMProductVOsByPage(MProductVO productVO) {
		return productMapper.selectMProductVOsByPage(productVO);
	}

	@Override
	public int findMProductVOsCount(MProductVO productVO) {
		return productMapper.selectMProductVOsCount(productVO);
	}

	@Override
	public ProductDetailVO findMProductVOById(Integer id) {
		return productMapper.selectProductDetailById(id);
	}

	@Override
	public List<BsProduct> findAllProductTerm() {
		return productMapper.selectAllProductTerm();
	}

	@Override
	public List<BsProduct> findAllProductBaseRate() {
		return productMapper.selectAllProductBaseRate();
	}

	@Override
	public BsProduct findBsProductById(Integer id) {
		return productMapper.selectByPrimaryKey(id);
	}

	@Override
	public MProductVO findMProductVoById(Integer id) {
		return productMapper.selectMProductVoById(id);
	}

	@Override
	public List<BsProduct> findAllProductByExample(BsProductExample example) {
		return productMapper.selectByExample(example);
	}

}
