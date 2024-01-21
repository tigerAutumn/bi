-- 查询注册送多少红包：【加缓存、管理台配置清除缓存】
BsRedPacketCheckMapper.autoRedPacketTotalGrid - ==>  Preparing: SELECT a.subtract, b.agent_ids FROM `bs_red_packet_check` a, bs_auto_red_packet_rule b WHERE a.serial_no = b.serial_no AND b.`status` = 'AVAILABLE' AND a.check_status = 'PASS' AND a.distribute_type = 'AUTO' AND (b.agent_ids LIKE '%0%' OR b.agent_ids LIKE '%-1%') AND b.trigger_type = ? AND b.distribute_time_start < NOW() AND b.distribute_time_end > NOW() 
BsRedPacketCheckMapper.autoRedPacketTotalGrid - ==> Parameters: NEW_USER(String)
BsRedPacketCheckMapper.autoRedPacketTotalGrid - <==      Total: 0

-- 根据产品ID查询用户类型产品权限（产品是否已下线）
BsUserTypeAuthMapper.selectByExample - ==>  Preparing: select id, user_type, product_id from bs_user_type_auth WHERE ( product_id = ? ) 
BsUserTypeAuthMapper.selectByExample - ==> Parameters: 3441(Integer)
BsUserTypeAuthMapper.selectByExample - <==      Total: 1

-- 产品详情
BsProductMapper.selectProductDetailByProductId - ==>  Preparing: SELECT a.id,a.`name`,a.base_rate,a.min_invest_amount, a.status,a.max_single_invest_amount,a.activity_type, CASE WHEN a.term = 12 THEN 365 when a.term = -7 then 7 ELSE a.term*30 END term,a.term term_month,a.curr_total_amount, a.max_total_amount,a.max_total_amount - a.curr_total_amount surplus_amount, a.manage_fee,a.property_type,a.is_support_transfer,a.begin_interest_days, a.return_type,a.interest_type,a.start_time,a.end_time, a.note,a.finish_time, b.coop_protocol_pics, b.property_summary,b.return_source,b.fund_security,b.orgnize_check, b.rating_grade,b.loan_protocol_pics,b.orgnize_check_pics,b.rating_grade_pics, b.property_symbol FROM `bs_product` a LEFT JOIN bs_property_info b ON a.property_id = b.id where a.id = ? 
BsProductMapper.selectProductDetailByProductId - ==> Parameters: 3441(Integer)
BsProductMapper.selectProductDetailByProductId - <==      Total: 1

-- 产品投资列表【放缓存，购买成功时清除】
BsProductMapper.selectInvestRecordByProductId - ==>  Preparing: SELECT concat(SUBSTRING(c.user_name,1,1),"**") user_name, CASE WHEN a.product_type = 'AUTH' THEN a.open_balance WHEN a.product_type in ('AUTH_YUN', 'AUTH_ZSD', 'AUTH_7') THEN a.open_balance + IFNULL(red.open_balance, 0) ELSE a.balance END balance, a.open_time FROM bs_sub_account a LEFT JOIN bs_sub_account_pair p ON p.auth_account_id = a.id LEFT JOIN bs_sub_account red ON red.id = p.red_account_id LEFT JOIN bs_account b ON a.account_id = b.id LEFT JOIN bs_user c ON b.user_id = c.id LEFT JOIN bs_bank_card d ON c.recent_bank_card_id = d.id WHERE (a.product_type in ('REG', 'AUTH', 'AUTH_YUN', 'AUTH_ZSD', 'AUTH_7')) AND a.`status` IN (2,3,5,7) AND a.product_id = ? ORDER BY a.open_time DESC limit ?,? 
BsProductMapper.selectInvestRecordByProductId - ==> Parameters: 3441(Integer), 0(Integer), 20(Integer)
BsProductMapper.selectInvestRecordByProductId - <==      Total: 9
-- 产品投资列表个数【放缓存，购买成功时清除】
BsProductMapper.selectInvestRecordCountByProductId - ==>  Preparing: SELECT COUNT(a.id) FROM bs_sub_account a LEFT JOIN bs_account b ON a.account_id = b.id LEFT JOIN bs_user c ON b.user_id = c.id LEFT JOIN bs_bank_card d ON c.recent_bank_card_id = d.id WHERE (a.product_type in ('REG', 'AUTH', 'AUTH_YUN', 'AUTH_ZSD', 'AUTH_7')) AND a.`status` IN (2,3,5,7) AND a.product_id = ? 
BsProductMapper.selectInvestRecordCountByProductId - ==> Parameters: 3441(Integer)
BsProductMapper.selectInvestRecordCountByProductId - <==      Total: 1

-- 产品开始前x分钟发送短信提醒
BsSysConfigMapper.selectByPrimaryKey - ==>  Preparing: select conf_key, conf_value, name, note from bs_sys_config where conf_key = ? 
BsSysConfigMapper.selectByPrimaryKey - ==> Parameters: PRODUCT_INFORM_MINUTE(String)
BsSysConfigMapper.selectByPrimaryKey - <==      Total: 1

-- 站岗资金等待时间
BsSysConfigMapper.selectByPrimaryKey - ==>  Preparing: select conf_key, conf_value, name, note from bs_sys_config where conf_key = ? 
BsSysConfigMapper.selectByPrimaryKey - ==> Parameters: DAY_4_WAIT_LOAN(String)
BsSysConfigMapper.selectByPrimaryKey - <==      Total: 1

BsHolidayMapper.findNotInHolidayTimeList - ==>  Preparing: SELECT * FROM bs_holiday WHERE ? > DATE_ADD(start_date,INTERVAL -1470 MINUTE) AND DATE_ADD(end_date,INTERVAL 0 MINUTE) > ? 
BsHolidayMapper.findNotInHolidayTimeList - ==> Parameters: 2018-04-28 14:01:32(String), 2018-04-28 14:01:32(String)
BsHolidayMapper.findNotInHolidayTimeList - <==      Total: 1

-- 支持银行列表【放入redis，银行管理配置清除】
Bs19payBankMapper.selectMainBankList - ==>  Preparing: SELECT p.*, b.name bank_name FROM bs_19pay_bank p, bs_bank b WHERE b.id = p.bank_id AND is_main = 1 AND p.pay_type = ? GROUP BY p.bank_id 
Bs19payBankMapper.selectMainBankList - ==> Parameters: 1(Integer)
Bs19payBankMapper.selectMainBankList - <==      Total: 16

-- 【可删除】将这个产品详情合并到BsProductMapper.selectProductDetailByProductId中【】
BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3441(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1

-- 【可以删除，前端未用到】购买上限金额
BsSysConfigMapper.selectByPrimaryKey - ==>  Preparing: select conf_key, conf_value, name, note from bs_sys_config where conf_key = ? 
BsSysConfigMapper.selectByPrimaryKey - ==> Parameters: PRICE_LIMIT(String)
BsSysConfigMapper.selectByPrimaryKey - <==      Total: 1

-- 【可以删除，前端未用到】购买下限金额
BsSysConfigMapper.selectByPrimaryKey - ==>  Preparing: select conf_key, conf_value, name, note from bs_sys_config where conf_key = ? 
BsSysConfigMapper.selectByPrimaryKey - ==> Parameters: PRICE_CEILING(String)
BsSysConfigMapper.selectByPrimaryKey - <==      Total: 1

-- 【可以删除，前端未用到】资产方
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

