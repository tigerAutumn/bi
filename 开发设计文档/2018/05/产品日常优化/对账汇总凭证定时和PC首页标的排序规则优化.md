## 对账汇总凭证定时
1. 需求描述
	当天宝付对账完成后，进行宝付对账信息汇总
		
2. 需求背景
	财务对账需要

3. 业务逻辑
### 修改点
1. 新增宝付对账汇总凭证定时，判断当天是否已对账（查询bs_19pay_check check_time是对账日期的有无记录），如果没有宝付对账结果信息进行日志输出 

2. 检查宝付对账汇总，检查主辅商户各业务汇总的sql其它方法无调用。（其它方法无调用）

3. 主商户对账汇总业务: 余额提现、奖励金提现、宝付代付到归集户、合作方营收划转（赞分期营收、云贷营收、7贷营收、赞时贷营收）、老产品回款
					合作方重复还款划转（云贷重复还款、7贷重复还款、赞时贷重复还款）、存管代偿（云贷合作方代偿、7贷合作方代偿、赞时贷合作方代偿）、
					存管产品还款（赞分期还款、云贷还款、7贷还款、赞时贷还款）、资产合作方线下还款（赞分期线下还款、7贷线下还款、赞时贷线下还款）		
   辅商户对账汇总业务: 存管产品还款（赞分期还款、云贷还款、7贷还款、赞时贷还款）、辅助通道转账到主通道
   
4. 宝付对账汇总sql查询速度优化，资产合作方线下还款sql、代扣还款sql
   
5. 具体执行时间以上线为准:
	<pre>
	INSERT INTO `bs_schedule_config`(`job_name`, `job_group`, `job_status`, `cron_expression`, `bean_class`, `method_name`, `bean_id`, `is_concurrent`, `description`, `create_time`, `update_time`) 
	VALUES ('baofooCheckSummaryTime', 'SCHEDULE', 'RUNNING', '0 30 10 * * ?', 'com.pinting.schedule.dayend.task.BaoFooGachaSummaryTask', 'execute', 'baoFooGachaSummaryTask', 'NO', '宝付对账汇总凭证定时', '2018-05-28 11:30:55', '2018-05-28 11:30:58');
	</pre>


## PC首页标的排序规则优化
1. 需求描述
	活动标的结束后,无其他标的的情况下,标的数据写入缓存导致首页精选产品展示数据异常，禅道bug9845
	新的排序规则:
		更新时间降序(推荐进行中 > 推荐未开始 > 普通进行中 > 普通未开始 > 已完成)
	
2. 需求背景
	线上bug

3. 业务逻辑
### 修改点
	入口HomeFacade类infoQuery方法
1. 精选产品列表原先的sql中有activity_type = 'ACTIVITY'条件判断，现去除。（原有的逻辑只查询了活动标的，先去掉，按需求描述的排序规则进行排序）
	            
针对新手标的，现逻辑已经排除状态是进行中和未开始的，已完成的新手标的如果符合排序规则，可以展示在精选产品列表。            

			