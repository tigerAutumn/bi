package com.pinting.business.service.site.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.BsTransferMapper;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsTransfer;
import com.pinting.business.model.BsTransferExample;
import com.pinting.business.model.vo.BsTransferVO;
import com.pinting.business.service.site.TransferService;

@Service
public class TransferServiceImpl implements TransferService {

	@Autowired
	private BsTransferMapper bsTransferMapper;
	
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean addTransfer(BsTransfer transfer, BsSubAccount subAccount) {
		
		int insertSeccuss = bsTransferMapper.insertSelective(transfer);
		
		int updateSeccuss = bsSubAccountMapper.updateByPrimaryKeySelective(subAccount);
		
		if(insertSeccuss>0 && updateSeccuss>0){
			
			return true;
		}else{
			return false;
		}
		
		
	}

	@Override
	public List<BsTransferVO> findTransferList(Integer startPage, Integer pageSize) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("start", startPage * pageSize);
		data.put("pageSize", pageSize);
		List<BsTransferVO> dataList = bsTransferMapper.selectByExamplePage(data);
		return  dataList.size() > 0? dataList : null;
	}

	@Override
	public int findTransferCount() {
		BsTransferExample example = new BsTransferExample();
		return bsTransferMapper.countByExample(example);
	}

	@Override
	public BsTransferVO findTransferById(Integer id) {
		return bsTransferMapper.selectTransferVOByPrimaryKey(id);
	}

	@Override
	public boolean updateTransferById(BsTransfer bsTransfer) {
		return bsTransferMapper.updateByPrimaryKeySelective(bsTransfer)>0;
	}

	@Override
	public BsTransfer findTransferBySubAccountId(Integer subAccountId) {
		BsTransferExample example = new BsTransferExample();
		example.createCriteria().andSubAccountId1EqualTo(subAccountId);
		List<BsTransfer> ransferList = bsTransferMapper.selectByExample(example);
		
		return ransferList.size()==0?null:ransferList.get(0);
	}

	@Override
	public boolean modifyTransferById(BsTransfer transfer) {
		return bsTransferMapper.updateByPrimaryKeySelective(transfer)>0;
	}

}
