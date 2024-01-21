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
import com.pinting.business.model.dto.AddressDTO;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.schedule.mongodb.dao.impl.MongoDataBaseImpl;
import com.pinting.schedule.mongodb.model.ApplicationAndAddress;
import com.pinting.schedule.util.Constants;

@Service
public class AddAddressTask {
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport
			.getInstance(com.pinting.business.util.Constants.REDIS_SUBSYS_BUSINESS);
	private Logger log = LoggerFactory.getLogger(AddAddressTask.class);

	@Autowired
	private MongoDataBaseImpl mongoDataBaseImpl;
	@Autowired
    private BsUserMapper userMapper;
	
	public void execute() {
		log.info(">>>address_queue start<<<");
		try {
			Long len = jsClientDaoSupport.llen("address_queue");
			log.info(">>>位置队列大小" + len + "<<<");
			if (len == null) {
				return;
			}
			for (int i = 0; i < len; i++) {
				String item = jsClientDaoSupport.lpop("address_queue");
				log.info(">>>redis单条数据：" + item + "<<<");
				if (StringUtil.isEmpty(item)) {
					log.error("pop位置队列数据为空，等待下一次定时pop");
					return;
				}
				AddressDTO addressDTO = JSON.parseObject(item,
						AddressDTO.class);
				if (addressDTO != null) {
					try {
						// 线程开始，存放redis数据
						Map<String, String> map = jsClientDaoSupport
								.getHashMapFromObj("address_process");
						if (MapUtils.isEmpty(map) || map.size() == 0) {
							map = new HashMap<>();
						}
						map.put(addressDTO.getUserId().toString(), addressDTO.getUserId()
								.toString());
						jsClientDaoSupport.addObjOfHashMap(map, "address_process", -1);
						try {
							// 调用记录应用列表业务
							Integer userId = addressDTO.getUserId();
							String address = addressDTO.getAddress();
							try {
								Criteria arrCri = Criteria.where("user_id").is(userId);
								Object object = mongoDataBaseImpl.findOne(arrCri, Constants.MONGODB_SCHEDULE_APPLICATION_AND_ADDRESS_KEY);
								if (object == null) {
									//插入用户的编号、地址、注册日期
									BsUser user = userMapper.selectByPrimaryKey(userId);
									Date registerTime = user.getRegisterTime();
									ApplicationAndAddress info = new ApplicationAndAddress();
									info.setUserId(userId);
									info.setAddress(address);
									info.setApplication("");
									info.setRegisterTime(DateUtil.format(registerTime));
									mongoDataBaseImpl.insert(info, Constants.MONGODB_SCHEDULE_APPLICATION_AND_ADDRESS_KEY);
								} else {
									//更新用户的地址
									HashMap<String, Object> queryParams = new HashMap<String, Object>();
									queryParams.put("user_id", userId);
									HashMap<String, Object> updateParams = new HashMap<String, Object>();
									updateParams.put("address", address);
									mongoDataBaseImpl.update(queryParams, updateParams, Constants.MONGODB_SCHEDULE_APPLICATION_AND_ADDRESS_KEY);
								}
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
						} catch (Exception e) {
							log.error("位置队列线程执行异常", e);
						} finally {
							// 删除redis数据
							jsClientDaoSupport.deleteObjOfHashMap(addressDTO.getUserId()
									.toString(), "address_process");
						}
					} catch (RejectedExecutionException e) {
						log.error("ThreadPool Rejected. 重新放入位置队列", e);
						// 重新放入位置队列
						jsClientDaoSupport.rpush("address_queue",
								JSON.toJSONString(addressDTO));
					}
				} else {
					log.info(">>>位置队列lpop为空<<<");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("位置队列定时执行异常", e);
		}

		log.info(">>>address_queue end<<<");
	}
}
