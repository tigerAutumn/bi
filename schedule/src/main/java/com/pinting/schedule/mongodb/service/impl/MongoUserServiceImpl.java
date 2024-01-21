package com.pinting.schedule.mongodb.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.business.util.ExportUtil;
import com.pinting.business.util.MailUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.schedule.mongodb.dao.impl.MongoUserDaoImpl;
import com.pinting.schedule.mongodb.model.ApplicationAndAddress;
import com.pinting.schedule.mongodb.service.MongoUserService;

@Service
public class MongoUserServiceImpl implements MongoUserService {

	@Autowired
	private BsSysConfigService bsSysConfigService;
	@Autowired
	private MongoUserDaoImpl mongoUserDaoImpl;
	
	private Logger log = LoggerFactory.getLogger(MongoUserServiceImpl.class);
	
	@Override
	public void sendRegisterUserInfoMail() {
		
		// 邮件主题
		int month = DateUtil.getPreMonth();
        String title = month+"月份用户信息报表";

        // 邮件正文
        String htmlContent = "Dear all:\r\n 上月用户数据报表已统计完毕,详情请查看附件。";
        
        // 收件人
        BsSysConfig bsSysConfigMobile = bsSysConfigService.findKey(Constants.PRODUCT_OPERATOR_EMAIL);
        String [] receivers = null;
        if (bsSysConfigMobile == null) {
        	receivers = new String[]{"qixiaoling@dafy.com", "chenyimei@dafy.com"};
        } else {        	
        	String mobiles = bsSysConfigMobile.getConfValue();
        	receivers = mobiles.split(",");
        }
        
        List<ApplicationAndAddress> userList = (List)mongoUserDaoImpl.findAll(com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_APPLICATION_AND_ADDRESS_KEY);
        log.info("method sendRegisterUserInfoMail userList:{}", JSON.toJSONString(Arrays.asList(userList)));
        StringBuffer header = new StringBuffer();
        header.append("用户ID").append(",");
        header.append("注册时间").append(",");
        header.append("地理位置").append(",");
        header.append("应用程序").append(",");
        List<String> content = new ArrayList<>();
        content.add(header.toString());
        if (!CollectionUtils.isEmpty(userList)) {
        	for (ApplicationAndAddress vo: userList) {
                StringBuffer contentBuffer = new StringBuffer();
                contentBuffer.append(vo.getUserId()).append(",");
                contentBuffer.append(vo.getRegisterTime()).append(",");
                contentBuffer.append(vo.getAddress()).append(",");
                contentBuffer.append(vo.getApplication()).append(",");
                content.add(contentBuffer.toString());
            }
        	ExportUtil.exportLocalCSV(GlobEnvUtil.get("register.user.file.path") + File.separator + "monthSnap", content, title + ".csv");
        }
        
        // 附件
        String fileName = GlobEnvUtil.get("register.user.file.path") + File.separator + "monthSnap" + File.separator + title + ".csv";
        List<String> fileList = new ArrayList<>();
        fileList.add(fileName);
        
		MailUtil.sendEmail(title, htmlContent, fileList, receivers);
		log.info("method sendRegisterUserInfoMail receivers:{}", JSON.toJSONString(Arrays.asList(receivers)));
	}
	
}
