package com.pinting.business.dayend.task;

import com.pinting.business.dao.BsSmsPlatformsConfigMapper;
import com.pinting.business.dao.BsSmsRecordJnlMapper;
import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.dayend.task.process.SendSMSProcess;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.vo.SmsMapVO;
import com.pinting.business.service.common.SmsPlatformsDealService;
import com.pinting.business.service.site.BsSmsRecordService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
@Service
public class SendSmTask {
	private Logger log = LoggerFactory.getLogger(SendSmTask.class);
	@Autowired
	private BsSmsRecordJnlMapper bsSmsRecordJnlMapper;
	@Autowired
	private BsSmsPlatformsConfigMapper bsSmsPlatformsConfigMapper;
	@Autowired
	private SmsPlatformsDealService smsPlatformsSendService;
	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;
	@Autowired
	private BsSmsRecordService bsSmsRecordService;
	
	
	//未发送的短信
	public static Map<String, LinkedList<SmsMapVO>> map = new HashMap<String, LinkedList<SmsMapVO>>();
	//待清除的手机号
	public static List<String> clearMapList = new ArrayList<String>();
	
	public void execute() {
		//清除无短信内容发送的手机号
		if(clearMapList!=null && clearMapList.size()>0){
			for (String clearKey : clearMapList) {
				if(map.get(clearKey) != null && map.get(clearKey).isEmpty()){
					map.remove(clearKey);
				}
			}
		}
		
		Set<String> mobiles = map.keySet();// 取得里面的key的集合
		if(mobiles != null && mobiles.size() > 0 && (!map.isEmpty())){
			log.info("=========="+mobiles+"==========");
			for (String mobile : mobiles) {// 遍历set去出里面的的Key
				try {
					LinkedList<SmsMapVO> linkedList = map.get(mobile);
					if(linkedList!=null && !linkedList.isEmpty()){
						SmsMapVO mapVo = linkedList.getFirst();
						String message = mapVo.getMessage(); //获取第一条短信内容
						SendSMSProcess process = new SendSMSProcess();
						process.setMessage(message);
						process.setMobile(mobile);
						process.setBsSmsRecordJnlMapper(bsSmsRecordJnlMapper);
						//用于判断短信签名的
						process.setAddserial(StringUtil.isBlank(mapVo.getAddserial()) ? "1" : mapVo.getAddserial());
						process.setMessageType(mapVo.getMessageType());
						process.setBsSmsPlatformsConfigMapper(bsSmsPlatformsConfigMapper);
						process.setSmsPlatformsSendService(smsPlatformsSendService);
						process.setBsSysConfigMapper(bsSysConfigMapper);
						process.setBsSmsRecordService(bsSmsRecordService);
						new Thread(process).start();
					}
				} catch (Exception e) {
					log.error("==========手机号："+mobile+"执行短信发送异常==========");
				}
			}
		}
	}

}
