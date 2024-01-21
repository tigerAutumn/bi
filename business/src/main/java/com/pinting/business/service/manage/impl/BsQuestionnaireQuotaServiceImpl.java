package com.pinting.business.service.manage.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsQuestionnaireQuotaMapper;
import com.pinting.business.model.BsQuestionnaireQuota;
import com.pinting.business.model.BsQuestionnaireQuotaExample;
import com.pinting.business.service.manage.BsQuestionnaireQuotaService;
import com.pinting.core.util.MoneyUtil;

/**
 *
 * @author SHENGUOPING
 * @date  2018年1月19日 下午1:43:52
 */
@Service
public class BsQuestionnaireQuotaServiceImpl implements BsQuestionnaireQuotaService {

	@Autowired
	private BsQuestionnaireQuotaMapper bsQuestionnaireQuotaMapper;

	@Override
	public List<BsQuestionnaireQuota> selectList() {
		List<BsQuestionnaireQuota> list = bsQuestionnaireQuotaMapper.findById(null);
		list = CollectionUtils.isEmpty(list) ? null : list;
		return list;
	}

	@Override
	public int countQuestionnaireQuota() {
		BsQuestionnaireQuotaExample example = new BsQuestionnaireQuotaExample();
		return bsQuestionnaireQuotaMapper.countByExample(example);
	}

	@Override
	public BsQuestionnaireQuota findById(Integer id) {
		List<BsQuestionnaireQuota> list = bsQuestionnaireQuotaMapper.findById(id);
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void operateQuestionnaireQuota(Integer quotaId, Integer mUserId, Integer periodLimit, Double amountLimit) {
		// 数据限制先不判断
		BsQuestionnaireQuota record = new BsQuestionnaireQuota();
		record.setId(quotaId);
		record.setOperateUserId(mUserId);
		record.setAmountLimit(MoneyUtil.multiply(amountLimit, 10000d).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		record.setPeriodLimit(periodLimit);
		record.setUpdateTime(new Date());
		bsQuestionnaireQuotaMapper.updateByPrimaryKeySelective(record);
	}

}
