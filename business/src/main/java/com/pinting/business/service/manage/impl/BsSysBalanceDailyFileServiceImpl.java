package com.pinting.business.service.manage.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsSysBalanceDailyFileMapper;
import com.pinting.business.dao.BsSysBalanceDailySnapMapper;
import com.pinting.business.model.BsSysBalanceDailyFile;
import com.pinting.business.model.BsSysBalanceDailyFileExample;
import com.pinting.business.model.vo.BsSysBalanceDailyFileVO;
import com.pinting.business.service.manage.BsSysBalanceDailyFileService;
import com.pinting.business.util.ExportUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;

@Service
public class BsSysBalanceDailyFileServiceImpl implements BsSysBalanceDailyFileService {

	private Logger logger = LoggerFactory.getLogger(BsSysBalanceDailyFileServiceImpl.class);
	
	@Autowired
	private BsSysBalanceDailySnapMapper bsSysBalanceDailySnapMapper;
	@Autowired
	private BsSysBalanceDailyFileMapper bsSysBalanceDailyFileMapper;
	
	@Override
	public void generateSysBalanceDailyFile(String time) {
		List<BsSysBalanceDailyFileVO> balanceList = bsSysBalanceDailySnapMapper.querySysBalanceDaily(time);
        StringBuffer header = new StringBuffer();
        header.append("账户类型").append(",");
        header.append("账户").append(",");
        header.append("余额").append(",");
        header.append("可用余额").append(",");
        header.append("冻结余额").append(",");
        List<String> content = new ArrayList<>();
        content.add(header.toString());
        if (!CollectionUtils.isEmpty(balanceList)) {
        	logger.info("generateSysBalanceDailyFile list is not null, size{}", balanceList.size());
        	for (BsSysBalanceDailyFileVO vo: balanceList) {
                StringBuffer contentBuffer = new StringBuffer();
                contentBuffer.append(vo.getAccountType()).append(",");
                contentBuffer.append(vo.getCode()).append(",");
                contentBuffer.append(vo.getBalance()).append(",");
                contentBuffer.append(vo.getAvailableBalance()).append(",");
                contentBuffer.append(vo.getFreezeBalance()).append(",");
                content.add(contentBuffer.toString());
            }
        	ExportUtil.exportLocalCSV(GlobEnvUtil.get("dep.sys.balance.daily.path") + File.separator + "dailySnap", content, "dailySnap_" + com.pinting.business.util.DateUtil.format(DateUtil.addDays(new Date(), -1), "yyyyMMdd") + ".csv");
        	//
        	String snapDate = DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -1));
        	BsSysBalanceDailyFileExample sysBalanceDailyFileExample = new BsSysBalanceDailyFileExample();
        	sysBalanceDailyFileExample.createCriteria().andSnapDateEqualTo(DateUtil.parseDate(snapDate));
        	List<BsSysBalanceDailyFile> list = bsSysBalanceDailyFileMapper.selectByExample(sysBalanceDailyFileExample);
        	if (CollectionUtils.isEmpty(list)) {        	
        		BsSysBalanceDailyFile sysBalanceDailyFile = new BsSysBalanceDailyFile();
        		sysBalanceDailyFile.setFileName("dailySnap_" + com.pinting.business.util.DateUtil.format(DateUtil.addDays(new Date(), -1), "yyyyMMdd") + ".csv");
        		sysBalanceDailyFile.setFileAddress(GlobEnvUtil.get("dep.sys.balance.daily.path") + File.separator + "dailySnap"+ File.separator + "dailySnap_" + com.pinting.business.util.DateUtil.format(DateUtil.addDays(new Date(), -1), "yyyyMMdd") + ".csv");
        		sysBalanceDailyFile.setCreateTime(new Date());
        		sysBalanceDailyFile.setUpdateTime(new Date());
        		sysBalanceDailyFile.setSnapDate(DateUtil.parseDate(snapDate));
        		bsSysBalanceDailyFileMapper.insertSelective(sysBalanceDailyFile);
        	} 
        }
	}

	@Override
	public int countSysBalanceDailyFile(Date snapBeginTime, Date snapEndTime) {
		return bsSysBalanceDailyFileMapper.countSysBalanceDailyFile(snapBeginTime, snapEndTime);
	}

	@Override
	public List<BsSysBalanceDailyFile> querySysBalanceDailyFileList(
			Date snapBeginTime, Date snapEndTime, int pageNum, int numPerPage) {
		int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
		List<BsSysBalanceDailyFile> list = bsSysBalanceDailyFileMapper.querySysBalanceDailyFileList(snapBeginTime, 
				snapEndTime, start, numPerPage);
		return (CollectionUtils.isNotEmpty(list)) ? list : null;
	}
	
}
