package com.pinting.business.accounting.service.impl.process;

import com.pinting.business.accounting.loan.model.RepayInfo;
import com.pinting.business.util.ExportUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangbao on 2017/9/27.
 * 标的还款数据生成文件
 */
public class DataBackUpProcess implements Runnable{
    private Logger log = LoggerFactory.getLogger(DataBackUpProcess.class);

    private List<RepayInfo> repayInfos;
    
    private Integer depRepayScheduleId;

    public void setRepayInfos(List<RepayInfo> repayInfos) {
        this.repayInfos = repayInfos;
    }
    

    public void setDepRepayScheduleId(Integer depRepayScheduleId) {
		this.depRepayScheduleId = depRepayScheduleId;
	}


	@Override
    public void run() {
        log.info("标的还款数据生成备份文件线程开始");
        fileGen(repayInfos,depRepayScheduleId);
        log.info("标的还款数据生成备份文件线程结束");
    }

    /**
     * 标的还款数据生成备份文件
     * @param repayInfos
     */
    public void fileGen(List<RepayInfo> repayInfos,Integer depRepayScheduleId){
        String prePath = GlobEnvUtil.get("dep.offline.return.path");
        List<String> content = new ArrayList<>();
        content.add(detailFileTitles());

        if (!CollectionUtils.isEmpty(repayInfos)) {
            for (RepayInfo data : repayInfos) {
                StringBuffer contentBuffer = new StringBuffer();
                contentBuffer.append(StringUtil.isEmpty(data.getPartner().getCode())? "" : data.getPartner().getCode()).append(",");
                contentBuffer.append(data.getInvestorUserId() == null ? "" : data.getInvestorUserId()).append(",");
                contentBuffer.append(data.getAuthActId() == null ? "" : data.getAuthActId()).append(",");
                contentBuffer.append(data.getDiffActId() == null ? "" : data.getDiffActId()).append(",");
                contentBuffer.append(data.getDiffAmount() == null ? "" : data.getDiffAmount()).append(",");
                contentBuffer.append(data.getPrincipal() == null ? "" : data.getPrincipal()).append(",");
                contentBuffer.append(data.getInterest() == null ? "" : data.getInterest()).append(",");
                contentBuffer.append(data.getServiceFee() == null ? "" : data.getServiceFee()).append(",");
                contentBuffer.append(data.getLnFinancePlanId() == null ? "" : data.getLnFinancePlanId());
                content.add(contentBuffer.toString());
            }
        }
        try {
        	
        	prePath= prePath+File.separator+DateUtil.formatDateTime(new Date(), "MM");
        	log.info("==========================path:"+prePath);
            ExportUtil.exportLocalCSV(prePath, content,"offReturn_" 
            		+ DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss")+"_"+depRepayScheduleId+ ".csv");
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 还款到标的文件的标题
     * @return
     */
    private String detailFileTitles() {
        StringBuffer header = new StringBuffer();
        header.append("合作方code").append(",");
        header.append("理财人用户id").append(",");
        header.append("站岗户id").append(",");
        header.append("补差户id").append(",");
        header.append("补差金额").append(",");
        header.append("本金").append(",");
        header.append("利息").append(",");
        header.append("服务费（币港湾营收）").append(",");
        header.append("LnFinanceRepaySchedule表id");
        return header.toString();
    }
}
