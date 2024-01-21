package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsProduct;
import com.pinting.business.model.BsProductExample;
import com.pinting.business.model.vo.MProductVO;
import com.pinting.business.model.vo.ProductDetailVO;

public interface MProductService {
	/**
	 * 新增产品
	 * @param product
	 * @return 
	 * @since JDK 1.7
	 */
	public int saveBsProduct(BsProduct product);
	
	/**
	 * 更新产品
	 * @param product
	 * @return 
	 * @since JDK 1.7
	 */
	public int updateBsProduct(BsProduct product);
	
	/**
	 * 根据主键删除产品
	 * @param id
	 * @return 
	 * @since JDK 1.7
	 */
	public int deleteBsProduct(Integer id);
	
	
	/**
	 * 根据多条件查询产品列表
	 * @param productVO
	 * @return 
	 * @since JDK 1.7
	 */
	public List<MProductVO> findMProductVOsByPage(MProductVO productVO);
	
	/**
	 * 根据多条件查询产品总数
	 * @param productVO
	 * @return 
	 * @since JDK 1.7
	 */
	public int findMProductVOsCount(MProductVO productVO);
	
	/**
	 * 根据主键查询产品
	 * @param id
	 * @return ProductDetailVO
	 * @since JDK 1.7
	 */
	public ProductDetailVO findMProductVOById(Integer id);
	
	/**
	 * 根据主键查询产品
	 * @param id
	 * @return BsProduct
	 * @since JDK 1.7
	 */
	public BsProduct findBsProductById(Integer id);
	
	/**
	 * 查询所有的期限
	 * @return List<BsProduct>
	 * @since JDK 1.7
	 */
	public List<BsProduct> findAllProductTerm();
	
	/**
	 * 查询所有的利率
	 * @return List<BsProduct>
	 * @since JDK 1.7
	 */
	public List<BsProduct> findAllProductBaseRate();
	
	/**
	 * 根据主键查询产品
	 * @param id
	 * @return MProductVO
	 * @since JDK 1.7
	 */
	public MProductVO findMProductVoById(Integer id);
	
	/**
	 * 根据多条件查询所有的产品
	 * @param example
	 * @return List<BsProduct>
	 * @since JDK 1.7
	 */
	public List<BsProduct> findAllProductByExample(BsProductExample example);
}
