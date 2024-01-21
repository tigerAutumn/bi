package com.pinting.business.service.manage.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsProductInvestViewMapper;
import com.pinting.business.model.BsProductInvestView;
import com.pinting.business.model.BsProductInvestViewExample;
import com.pinting.business.service.manage.ProductInvestService;
import com.pinting.core.util.StringUtil;

@Service
public class ProductInvestServiceImpl implements ProductInvestService {

	@Autowired
	private BsProductInvestViewMapper viewMapper;
	
	@Override
	public List<BsProductInvestView> selectProductInvestList(String startTime, String endTime, String pageNum,
			String numPerPage) {
		BsProductInvestViewExample example = new BsProductInvestViewExample();
		BsProductInvestViewExample.Criteria criteria = example.createCriteria();
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date start = null;
			try {
				start = format.parse(startTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			criteria.andDateGreaterThanOrEqualTo(start);
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date end = null;
			try {
				end = format.parse(endTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			criteria.andDateLessThanOrEqualTo(end);
		}
		int position = (Integer.parseInt(pageNum) - 1) * Integer.parseInt(numPerPage);
		example.setOrderByClause("date desc limit "+position+","+Integer.parseInt(numPerPage));
		List<BsProductInvestView> list = viewMapper.selectByExample(example);
		return list;
	}

	@Override
	public int selectProductInvestCount(String startTime, String endTime) {
		BsProductInvestViewExample example = new BsProductInvestViewExample();
		BsProductInvestViewExample.Criteria criteria = example.createCriteria();
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date start = null;
			try {
				start = format.parse(startTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			criteria.andDateGreaterThanOrEqualTo(start);
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date end = null;
			try {
				end = format.parse(endTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			criteria.andDateLessThanOrEqualTo(end);
		}
		int count = viewMapper.countByExample(example);
		return count;
	}

}
