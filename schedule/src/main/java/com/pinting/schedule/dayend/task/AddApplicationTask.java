package com.pinting.schedule.dayend.task;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.dto.ApplictionsDTO;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.schedule.mongodb.dao.impl.MongoDataBaseImpl;
import com.pinting.schedule.mongodb.model.ApplicationAndAddress;
import com.pinting.schedule.util.Constants;

@Service
public class AddApplicationTask {
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport
			.getInstance(com.pinting.business.util.Constants.REDIS_SUBSYS_BUSINESS);
	private Logger log = LoggerFactory.getLogger(AddApplicationTask.class);

	@Autowired
	private MongoDataBaseImpl mongoDataBaseImpl;
	@Autowired
    private BsUserMapper userMapper;

	public void execute() {
		log.info(">>>application_queue start<<<");
		try {
			Long len = jsClientDaoSupport.llen("application_queue");
			log.info(">>>应用队列大小" + len + "<<<");
			if (len == null) {
				return;
			}
			for (int i = 0; i < len; i++) {
				String item = jsClientDaoSupport.lpop("application_queue");
				log.info(">>>redis单条数据：" + item + "<<<");
				if (StringUtil.isEmpty(item)) {
					log.error("pop应用队列数据为空，等待下一次定时pop");
					return;
				}
				ApplictionsDTO applictionsDTO = JSON.parseObject(item,
						ApplictionsDTO.class);
				if (applictionsDTO != null) {
					try {
						// 线程开始，存放redis数据
						Map<String, String> map = jsClientDaoSupport
								.getHashMapFromObj("application_process");
						if (MapUtils.isEmpty(map) || map.size() == 0) {
							map = new HashMap<>();
						}
						map.put(applictionsDTO.getUserId().toString(), applictionsDTO
								.getUserId().toString());
						jsClientDaoSupport.addObjOfHashMap(map, "application_process", -1);
						try {
							// 调用记录应用列表业务
							Integer userId = applictionsDTO.getUserId();
							String application = applictionsDTO.getApplications();
							try {
								Criteria arrCri = Criteria.where("user_id").is(userId);
								Object object = mongoDataBaseImpl.findOne(arrCri, Constants.MONGODB_SCHEDULE_APPLICATION_AND_ADDRESS_KEY);
								if (object == null) {
									// 插入用户的编号、应用列表、注册日期、地址
									BsUser user = userMapper.selectByPrimaryKey(userId);
									Date registerTime = user.getRegisterTime();
									ApplicationAndAddress info = new ApplicationAndAddress();
									info.setUserId(userId);
									info.setAddress("");
									info.setApplication(application);
									info.setRegisterTime(DateUtil.format(registerTime));
									mongoDataBaseImpl.insert(info, Constants.MONGODB_SCHEDULE_APPLICATION_AND_ADDRESS_KEY);
								} else {
									// 更新用户的应用列表、地址
									HashMap<String, Object> queryParams = new HashMap<String, Object>();
									queryParams.put("user_id", userId);
									HashMap<String, Object> updateParams = new HashMap<String, Object>();
									updateParams.put("application", application);
									mongoDataBaseImpl.update(queryParams, updateParams, Constants.MONGODB_SCHEDULE_APPLICATION_AND_ADDRESS_KEY);
								}
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
						} catch (Exception e) {
							log.error("应用队列线程执行异常", e);
						} finally {
							// 删除redis数据
							jsClientDaoSupport.deleteObjOfHashMap(applictionsDTO.getUserId()
									.toString(), "application_process");
						}
					} catch (RejectedExecutionException e) {
						log.error("ThreadPool Rejected. 重新放入应用队列", e);
						// 重新放入应用队列
						jsClientDaoSupport.rpush("application_queue",
								JSON.toJSONString(applictionsDTO));
					}
				} else {
					log.info(">>>应用队列lpop为空<<<");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("应用队列定时执行异常", e);
		}

		log.info(">>>applicaiton_queue end<<<");
	}
}
