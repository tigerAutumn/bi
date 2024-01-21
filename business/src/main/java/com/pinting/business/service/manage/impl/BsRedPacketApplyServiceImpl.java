package com.pinting.business.service.manage.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsRedPacketApplyMapper;
import com.pinting.business.dao.BsRedPacketInfoMapper;
import com.pinting.business.dao.LnBadDetailMapper;
import com.pinting.business.model.BsRedPacketApply;
import com.pinting.business.model.vo.RedPaktBudgetStatVO;
import com.pinting.business.model.vo.BsRedPacketApplyVO;
import com.pinting.business.service.manage.BsRedPacketApplyService;
import com.pinting.core.util.MoneyUtil;

@Service
public class BsRedPacketApplyServiceImpl implements BsRedPacketApplyService {
	
	@Autowired
	private BsRedPacketApplyMapper redPacketApplyMapper;
	@Autowired
	private BsRedPacketInfoMapper redPacketInfoMapper;
	@Autowired
	private LnBadDetailMapper lnBadDetailMapper;

	@Override
	public ArrayList<BsRedPacketApplyVO> findBsRedPacketApplyList(String rpName,
			String checkStatus, Integer creator, Integer pageNum, Integer numPerPage, String orderDirection,
			String orderField) {
		BsRedPacketApplyVO bsRedPacketApply = new BsRedPacketApplyVO();
		if (rpName != null && !"".equals(rpName)) {
			bsRedPacketApply.setRpName(rpName);
		}
		if (checkStatus != null && !"".equals(checkStatus)) {
			bsRedPacketApply.setCheckStatus(checkStatus);
		}
		if (orderDirection != null &&(!"".equals(orderDirection)) && orderField != null && (!"".equals(orderField))) {
			bsRedPacketApply.setOrderDirection(orderDirection);
			bsRedPacketApply.setOrderField(orderField);
		}
		bsRedPacketApply.setCreator(creator);
		bsRedPacketApply.setPageNum(pageNum);
		bsRedPacketApply.setNumPerPage(numPerPage);
		return redPacketApplyMapper.selectByRedPacketApplyListInfo(bsRedPacketApply);
	}

	@Override
	public int findBsRedPacketApplyCount(String rpName, String checkStatus, Integer creator) {
		BsRedPacketApply bsRedPacketApply = new BsRedPacketApply();
		if (rpName != null && !"".equals(rpName)) {
			bsRedPacketApply.setRpName(rpName);
		}
		if (checkStatus != null && !"".equals(checkStatus)) {
			bsRedPacketApply.setCheckStatus(checkStatus);
		}
		bsRedPacketApply.setCreator(creator);
		return redPacketApplyMapper.selectCountRedPacketApply(bsRedPacketApply);
	}

	@Override
	public int addBsRedPacketApply(BsRedPacketApply record) {
		return redPacketApplyMapper.insert(record);
	}

	@Override
	public RedPaktBudgetStatVO queryRedPaktBudgetStat() {
		Double totalBudgetAmount = redPacketApplyMapper.selectTotalBudgetAmount();
		Double expiryAmount = redPacketInfoMapper.selectExpiryAmount();
		Double usedRedPaktAmount = redPacketInfoMapper.selectUsedRedPaktAmount();
		Double unUsedRedPaktAmount = redPacketInfoMapper.selectUnUsedRedPaktAmount();
		Double badloansZanAccBalance = lnBadDetailMapper.selectBadloansZanAmount();
		RedPaktBudgetStatVO vo = new RedPaktBudgetStatVO();
		vo.setExpiryAmount(expiryAmount!=null ? expiryAmount:0);
		vo.setTotalBudgetAmount(totalBudgetAmount!=null ? totalBudgetAmount:0);
		vo.setUnUsedRedPaktAmount(unUsedRedPaktAmount!=null ? unUsedRedPaktAmount:0);
		vo.setUsedRedPaktAmount(usedRedPaktAmount!=null ? usedRedPaktAmount:0);
		vo.setUsedBudgetAmount(MoneyUtil.add(unUsedRedPaktAmount!=null ? unUsedRedPaktAmount:0,
				usedRedPaktAmount!=null ? usedRedPaktAmount:0).doubleValue());
		vo.setBadloansZanAccBalance(badloansZanAccBalance);
		return vo;
	}
	
	@Override
	public BsRedPacketApply findBsRedPacketApply(BsRedPacketApply record) {
		return redPacketApplyMapper.selectByRedPacketApply(record);
	}

	@Override
	public BsRedPacketApply findByPrimaryKey(Integer id) {
		return redPacketApplyMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateBsRedPacketApply(BsRedPacketApply record) {
		return redPacketApplyMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<BsRedPacketApply> findCanPutPacket() {
		return redPacketApplyMapper.findCanPutPacket();
	}

	@Override
	public BsRedPacketApplyVO selectByApplyNo(String applyNo) {
		BsRedPacketApplyVO apply = redPacketApplyMapper.selectByApplyNo(applyNo,new Date());//申请信息，在审核中和已已审核的总金额
		//Double expiryAmount = redPacketInfoMapper.selectExpiryAmountByApplyNo(applyNo,new Date()); //已发放的过期未使用红包总金额
		//Double disableUnSendAmount = redPacketInfoMapper.selectDisableUnSendAmountByApplyNo(applyNo,new Date());//autoRule表中已停用，且未发送给用户的总金额
		//Double canPutAmount = MoneyUtil.subtract(apply.getBudget()==null?0:apply.getBudget(), apply.getCheckAmount()==null?0:apply.getCheckAmount()).doubleValue();
		//canPutAmount = MoneyUtil.add(canPutAmount==null?0:canPutAmount, expiryAmount==null?0:expiryAmount).doubleValue();
		//canPutAmount = MoneyUtil.add(canPutAmount, disableUnSendAmount==null?0:disableUnSendAmount).doubleValue(); 
		apply.setCanPutAmount(apply.getCanPutAmount());
		return apply;
	}

	@Override
	public List<BsRedPacketApplyVO> findBsRedPacketApplyCreator(
			BsRedPacketApplyVO record) {
		return redPacketApplyMapper.selectByRedPacketApplyCreator(record);
	}

}
