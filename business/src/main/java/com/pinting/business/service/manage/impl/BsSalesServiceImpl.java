package com.pinting.business.service.manage.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsDeptMapper;
import com.pinting.business.dao.BsDeptSalesMapper;
import com.pinting.business.dao.BsSalesMapper;
import com.pinting.business.model.BsDept;
import com.pinting.business.model.BsDeptExample;
import com.pinting.business.model.BsDeptManager;
import com.pinting.business.model.BsDeptSales;
import com.pinting.business.model.BsDeptSalesExample;
import com.pinting.business.model.BsSales;
import com.pinting.business.model.vo.BsSalesDirectInviteVO;
import com.pinting.business.model.vo.BsSalesVO;
import com.pinting.business.service.manage.BsSalesService;
import com.pinting.gateway.in.util.MethodRole;

@Service
public class BsSalesServiceImpl implements BsSalesService{
	
	@Autowired
	private BsSalesMapper bsSalesMapper;
	@Autowired
	private BsDeptMapper bsDeptMapper;
	@Autowired
	private BsDeptSalesMapper bsDeptSalesMapper;

	@Override
	public List<BsSalesVO> page(BsSalesVO record) {
		return bsSalesMapper.page(record);
	}

	@Override
	public int count(BsSalesVO record) {
		return bsSalesMapper.count(record);
	}

	@Override
	public List<BsSalesVO> userPage(BsSalesVO record) {
		return bsSalesMapper.userPage(record);
	}

	@Override
	public int userCount(BsSalesVO record) {
		return bsSalesMapper.userCount(record);
	}

	@Override
	public int updateBsSales(BsSales record) {
		return bsSalesMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	@MethodRole("APP")
	public BsSales selectBsSales(BsSales record) {
		return bsSalesMapper.selectBsSales(record);
	}

	@Override
	public BsSales selectByPrimaryKey(Integer id) {
		return bsSalesMapper.selectByPrimaryKey(id);
	}

	@Override
	public int addBsSales(BsSales record) {
		return bsSalesMapper.insertSelective(record);
	}

	@Override
	public List<BsSalesDirectInviteVO> selectSalesDirectInviteUsers(Integer salesId, String pageNum, String numPerPage) {
		BsSales sales = new BsSales();
		sales.setId(salesId);
		sales.setPageNum(Integer.valueOf(pageNum));
		sales.setNumPerPage(Integer.valueOf(numPerPage));
		return bsSalesMapper.selectSalesDirectInviteUsers(sales);
	}

	@Override
	public int countSalesDirectInviteUsers(Integer salesId) {
		BsSales sales = new BsSales();
		sales.setId(salesId);
		return bsSalesMapper.countSalesDirectInviteUsers(sales);
	}

	@Override
	public List<BsDept> selectDeptList() {
		BsDeptExample example = new BsDeptExample();
		example.createCriteria();
		return bsDeptMapper.selectByExample(example);
	}

	@Override
	public List<BsDeptSales> selectDeptBySaleId(Integer id) {
		BsDeptSalesExample example = new BsDeptSalesExample();
		example.createCriteria().andSalesIdEqualTo(id);
		return bsDeptSalesMapper.selectByExample(example);
	}

	@Override
	public void updateBsDeptSales(Integer id, Integer deptId) {
		
		BsDeptSalesExample example = new BsDeptSalesExample();
		example.createCriteria().andSalesIdEqualTo(id);
		List<BsDeptSales> bsDeptSaleList = bsDeptSalesMapper.selectByExample(example);
		

		
		if (!CollectionUtils.isEmpty(bsDeptSaleList)) { //更新

			if(deptId == -1){
				bsDeptSalesMapper.deleteByPrimaryKey(bsDeptSaleList.get(0).getId());
			}else {
				BsDeptSales bsDeptSales = new BsDeptSales();
				bsDeptSales.setDeptId(deptId);
				bsDeptSales.setUpdateTime(new Date());
				bsDeptSalesMapper.updateByExampleSelective(bsDeptSales, example);
			}
			
		}else { //新增
			if(deptId != -1){
				BsDeptSales bsDeptSales = new BsDeptSales();
				bsDeptSales.setSalesId(id);
				bsDeptSales.setDeptId(deptId);
				bsDeptSales.setCreateTime(new Date());
				bsDeptSalesMapper.insertSelective(bsDeptSales);
			}

		}
		
	}

	@Override
	public void addBsDeptSales(Integer saleId, Integer deptId) {
		
		if (deptId != -1) {
			BsDeptSales bsDeptSales = new BsDeptSales();
			bsDeptSales.setSalesId(saleId);
			bsDeptSales.setDeptId(deptId);
			bsDeptSales.setCreateTime(new Date());
			
			bsDeptSalesMapper.insertSelective(bsDeptSales);
		}

		
	}

}
