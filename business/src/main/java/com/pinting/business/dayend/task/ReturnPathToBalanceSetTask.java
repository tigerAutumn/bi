package com.pinting.business.dayend.task;

import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.BsUserExample;
import com.pinting.business.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
/**
 * 2017年01月01日将用户回款路径设置回款到余额
 * @author Dragon & cat
 * @date 2016-11-2
 */
@Deprecated
public class ReturnPathToBalanceSetTask {
	private Logger log = LoggerFactory.getLogger(ReturnPathToBalanceSetTask.class);
	@Autowired
	private BsUserMapper bsUserMapper;

	/**
	 * 2017年01月01日将用户回款路径设置回款到余额
	 */
	public void execute() {
		// 定时任务{2017年01月01日将用户回款路径设置回款到余额}
		log.info("=========定时任务{2017年01月01日将用户回款路径设置回款到余额}开始=========");
		
		BsUserExample example = new BsUserExample();
		example.createCriteria().andReturnPathEqualTo(Constants.RETURN_PATH_BANKCARD);
		
		List<BsUser> bsUsers = bsUserMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(bsUsers)) {
			String userIdString = "";
			int num = 0;
			for (BsUser bsUser : bsUsers) {
				num++;
				userIdString = userIdString + String.valueOf(bsUser.getId())+",";
				if (num%500==0) {
					log.info("=========定时任务{2017年01月01日将用户回款路径设置回款到余额}>>>>>>>"+num+">>>"+userIdString);
					userIdString = "";
				}
			}
			log.info("=========定时任务{2017年01月01日将用户回款路径设置回款到余额}>>>>>>>"+num+">>>"+userIdString);
			BsUser user = new BsUser();
			user.setReturnPath(Constants.RETURN_PATH_BALANCE);
			Integer count = bsUserMapper.updateByExampleSelective(user, example);
			log.info("=========定时任务{2017年01月01日将用户回款路径设置回款到余额}共修改"+count+"条记录=========");
		}
		log.info("=========定时任务{2017年01月01日将用户回款路径设置回款到余额}结束=========");
	}

}
