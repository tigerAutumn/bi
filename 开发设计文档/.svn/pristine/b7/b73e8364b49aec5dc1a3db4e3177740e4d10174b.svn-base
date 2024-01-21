-- 产品详情
2018-05-02 10:28:53,895 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsProductMapper.selectProductDetailByProductId - ==>  Preparing: SELECT a.id,a.`name`,a.base_rate,a.min_invest_amount, a.status,a.max_single_invest_amount,a.activity_type, CASE WHEN a.term = 12 THEN 365 when a.term = -7 then 7 ELSE a.term*30 END term,a.term term_month,a.curr_total_amount, a.max_total_amount,a.max_total_amount - a.curr_total_amount surplus_amount, a.manage_fee,a.property_type,a.is_support_transfer,a.begin_interest_days, a.return_type,a.interest_type,a.start_time,a.end_time, a.note,a.finish_time, b.coop_protocol_pics, b.property_summary,b.return_source,b.fund_security,b.orgnize_check, b.rating_grade,b.loan_protocol_pics,b.orgnize_check_pics,b.rating_grade_pics, b.property_symbol FROM `bs_product` a LEFT JOIN bs_property_info b ON a.property_id = b.id where a.id = ? 
2018-05-02 10:28:53,896 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsProductMapper.selectProductDetailByProductId - ==> Parameters: 3441(Integer)
2018-05-02 10:28:53,931 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsProductMapper.selectProductDetailByProductId - <==      Total: 1

-- 产品投资列表【放缓存，购买成功时清除】
2018-05-02 10:28:53,931 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsProductMapper.selectInvestRecordByProductId - ==>  Preparing: SELECT concat(SUBSTRING(c.user_name,1,1),"**") user_name, CASE WHEN a.product_type = 'AUTH' THEN a.open_balance WHEN a.product_type in ('AUTH_YUN', 'AUTH_ZSD', 'AUTH_7') THEN a.open_balance + IFNULL(red.open_balance, 0) ELSE a.balance END balance, a.open_time FROM bs_sub_account a LEFT JOIN bs_sub_account_pair p ON p.auth_account_id = a.id LEFT JOIN bs_sub_account red ON red.id = p.red_account_id LEFT JOIN bs_account b ON a.account_id = b.id LEFT JOIN bs_user c ON b.user_id = c.id LEFT JOIN bs_bank_card d ON c.recent_bank_card_id = d.id WHERE (a.product_type in ('REG', 'AUTH', 'AUTH_YUN', 'AUTH_ZSD', 'AUTH_7')) AND a.`status` IN (2,3,5,7) AND a.product_id = ? ORDER BY a.open_time DESC limit ?,? 
2018-05-02 10:28:53,932 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsProductMapper.selectInvestRecordByProductId - ==> Parameters: 3441(Integer), 0(Integer), 20(Integer)
2018-05-02 10:28:54,003 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsProductMapper.selectInvestRecordByProductId - <==      Total: 10
-- 产品投资列表个数【放缓存，购买成功时清除】
2018-05-02 10:28:54,003 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsProductMapper.selectInvestRecordCountByProductId - ==>  Preparing: SELECT COUNT(a.id) FROM bs_sub_account a LEFT JOIN bs_account b ON a.account_id = b.id LEFT JOIN bs_user c ON b.user_id = c.id LEFT JOIN bs_bank_card d ON c.recent_bank_card_id = d.id WHERE (a.product_type in ('REG', 'AUTH', 'AUTH_YUN', 'AUTH_ZSD', 'AUTH_7')) AND a.`status` IN (2,3,5,7) AND a.product_id = ? 
2018-05-02 10:28:54,004 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsProductMapper.selectInvestRecordCountByProductId - ==> Parameters: 3441(Integer)
2018-05-02 10:28:54,012 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsProductMapper.selectInvestRecordCountByProductId - <==      Total: 1

-- 产品开始前x分钟发送短信提醒
2018-05-02 10:28:54,013 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsSysConfigMapper.selectByPrimaryKey - ==>  Preparing: select conf_key, conf_value, name, note from bs_sys_config where conf_key = ? 
2018-05-02 10:28:54,013 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsSysConfigMapper.selectByPrimaryKey - ==> Parameters: PRODUCT_INFORM_MINUTE(String)
2018-05-02 10:28:54,026 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsSysConfigMapper.selectByPrimaryKey - <==      Total: 1

-- 站岗资金等待时间
2018-05-02 10:28:54,027 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsSysConfigMapper.selectByPrimaryKey - ==>  Preparing: select conf_key, conf_value, name, note from bs_sys_config where conf_key = ? 
2018-05-02 10:28:54,027 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsSysConfigMapper.selectByPrimaryKey - ==> Parameters: DAY_4_WAIT_LOAN(String)
2018-05-02 10:28:54,039 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsSysConfigMapper.selectByPrimaryKey - <==      Total: 1

2018-05-02 10:28:54,043 [qtp1486365718-32] DEBUG com.pinting.business.dao.BsHolidayMapper.findNotInHolidayTimeList - ==>  Preparing: SELECT * FROM bs_holiday WHERE ? > DATE_ADD(start_date,INTERVAL -1470 MINUTE) AND DATE_ADD(end_date,INTERVAL 0 MINUTE) > ? 
2018-05-02 10:28:54,043 [qtp1486365718-32] DEBUG com.pinting.business.dao.BsHolidayMapper.findNotInHolidayTimeList - ==> Parameters: 2018-05-02 10:28:54(String), 2018-05-02 10:28:54(String)
2018-05-02 10:28:54,054 [qtp1486365718-32] DEBUG com.pinting.business.dao.BsHolidayMapper.findNotInHolidayTimeList - <==      Total: 0

-- 查询支持的银行列表
2018-05-02 10:28:54,055 [qtp1486365718-32] DEBUG com.pinting.business.dao.Bs19payBankMapper.selectFirstBankList - ==>  Preparing: SELECT p.*, b.name bank_name, b.small_logo, b.large_logo FROM bs_19pay_bank p, bs_bank b WHERE b.id = p.bank_id AND channel_priority = 1 AND p.pay_type = ? GROUP BY p.bank_id 
2018-05-02 10:28:54,056 [qtp1486365718-32] DEBUG com.pinting.business.dao.Bs19payBankMapper.selectFirstBankList - ==> Parameters: 1(Integer)
2018-05-02 10:28:54,073 [qtp1486365718-32] DEBUG com.pinting.business.dao.Bs19payBankMapper.selectFirstBankList - <==      Total: 16
2018-05-02 10:28:54,079 [qtp1486365718-26] INFO  com.pinting.core.redis.sentinel.JedisSentinelSharedPool - 当前redis服务：[114.215.203.101:7379]