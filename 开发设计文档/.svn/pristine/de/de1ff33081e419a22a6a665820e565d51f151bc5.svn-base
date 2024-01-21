-- 查询用户
BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000056(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1

-- 【待定，看看】
BsActiveUserRecordMapper.selectByRecord - ==>  Preparing: select id, user_id, terminal_type, login_date, src_url, create_time, update_time from bs_active_user_record where user_id = ? and terminal_type = ? and login_date = ? 
BsActiveUserRecordMapper.selectByRecord - ==> Parameters: 2000056(Integer), PC(String), 2018-04-28(Date)
BsActiveUserRecordMapper.selectByRecord - <==      Total: 1

-- 查询注册送多少红包【加缓存、管理台配置清除缓存】
BsRedPacketCheckMapper.autoRedPacketTotalGrid - ==>  Preparing: SELECT a.subtract, b.agent_ids FROM `bs_red_packet_check` a, bs_auto_red_packet_rule b WHERE a.serial_no = b.serial_no AND b.`status` = 'AVAILABLE' AND a.check_status = 'PASS' AND a.distribute_type = 'AUTO' AND (b.agent_ids LIKE '%0%' OR b.agent_ids LIKE '%-1%') AND b.trigger_type = ? AND b.distribute_time_start < NOW() AND b.distribute_time_end > NOW() 
BsRedPacketCheckMapper.autoRedPacketTotalGrid - ==> Parameters: NEW_USER(String)
BsRedPacketCheckMapper.autoRedPacketTotalGrid - <==      Total: 0

-- 根据产品ID查询用户类型产品权限（产品是否已下线）
BsUserTypeAuthMapper.selectByExample - ==>  Preparing: select id, user_type, product_id from bs_user_type_auth WHERE ( product_id = ? ) 
BsUserTypeAuthMapper.selectByExample - ==> Parameters: 3441(Integer)
BsUserTypeAuthMapper.selectByExample - <==      Total: 1

-- 查询DEP_JSH
BsSubAccountMapper.selectDEPJSHSubAccountByUserId - ==>  Preparing: select a.id, a.account_id, a.code, a.product_type, a.open_balance, a.balance, a.available_balance, a.can_withdraw, a.freeze_balance, a.trans_status, a.status, a.open_time, a.note from bs_sub_account a, bs_account b, bs_user c where a.account_id = b.id and b.user_id = c.id and c.id = ? and a.status = '1' and a.product_type = 'DEP_JSH' 
BsSubAccountMapper.selectDEPJSHSubAccountByUserId - ==> Parameters: 2000056(Integer)
BsSubAccountMapper.selectDEPJSHSubAccountByUserId - <==      Total: 1

-- 查询JSH
BsSubAccountMapper.selectJSHSubAccountByUserId - ==>  Preparing: select a.id, a.account_id, a.code, a.product_type, a.open_balance, a.balance, a.available_balance, a.can_withdraw, a.freeze_balance, a.trans_status, a.status, a.open_time, a.note from bs_sub_account a, bs_account b, bs_user c where a.account_id = b.id and b.user_id = c.id and c.id = ? and a.status = '1' and a.product_type = 'JSH' 
BsSubAccountMapper.selectJSHSubAccountByUserId - ==> Parameters: 2000056(Integer)
BsSubAccountMapper.selectJSHSubAccountByUserId - <==      Total: 1

-- 统计银行卡个数
BsBankCardMapper.countByExample - ==>  Preparing: select count(*) from bs_bank_card WHERE ( status in ( ? , ? ) and user_id = ? ) 
BsBankCardMapper.countByExample - ==> Parameters: 1(Integer), 5(Integer), 2000056(Integer)
BsBankCardMapper.countByExample - <==      Total: 1

-- 查询用户
BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000056(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1

-- 查询恒丰绑卡信息
BsHfbankUserExtMapper.selectByExample - ==>  Preparing: select id, user_id, hf_user_id, hf_regist_time, hf_bind_card_time, status, note, create_time, update_time from bs_hfbank_user_ext WHERE ( user_id = ? and status <> ? ) 
BsHfbankUserExtMapper.selectByExample - ==> Parameters: 2000056(Integer), CANCEL(String)
BsHfbankUserExtMapper.selectByExample - <==      Total: 1
BsHfbankUserExtMapper.selectByExample - ==>  Preparing: select id, user_id, hf_user_id, hf_regist_time, hf_bind_card_time, status, note, create_time, update_time from bs_hfbank_user_ext WHERE ( user_id = ? and status = ? ) 
BsHfbankUserExtMapper.selectByExample - ==> Parameters: 2000056(Integer), WAIT_ACTIVATE(String)
BsHfbankUserExtMapper.selectByExample - <==      Total: 0
BsHfbankUserExtMapper.selectByExample - ==>  Preparing: select id, user_id, hf_user_id, hf_regist_time, hf_bind_card_time, status, note, create_time, update_time from bs_hfbank_user_ext WHERE ( user_id = ? and status = ? ) 
BsHfbankUserExtMapper.selectByExample - ==> Parameters: 2000056(Integer), OPEN(String)
BsHfbankUserExtMapper.selectByExample - <==      Total: 1

-- 查询风险测评
BsQuestionnaireMapper.selectByExample - ==>  Preparing: select id, user_id, question_type, question_item, score, expire_days, expire_time, create_time, update_time from bs_questionnaire WHERE ( user_id = ? ) order by update_time desc 
BsQuestionnaireMapper.selectByExample - ==> Parameters: 2000056(Integer)
BsQuestionnaireMapper.selectByExample - <==      Total: 1

-- 查询产品信息
BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3441(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1

-- 购买上限金额
BsSysConfigMapper.selectByPrimaryKey - ==>  Preparing: select conf_key, conf_value, name, note from bs_sys_config where conf_key = ? 
BsSysConfigMapper.selectByPrimaryKey - ==> Parameters: PRICE_LIMIT(String)
BsSysConfigMapper.selectByPrimaryKey - <==      Total: 1

-- 购买下限金额
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

-- 查询产品信息
BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3441(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1

-- 查询加息券
BsInterestTicketInfoMapper.selectBuyTicketINITList - ==>  Preparing: select a.id, a.user_id, b.serial_name, d.invest_limit as full, concat('限', d.term_limit, '天产品使用') as term_limit, d.product_limit, d.ticket_apr as rate, a.use_time_start, a.use_time_end, a.`status`, e.term, e.is_support_incr_interest, ROUND(? * IFNULL(d.ticket_apr,0) * IFNULL(e.term,0)/ 36500,2) as interest from ( select a.id, a.serial_no, a.user_id,a.use_time_start, a.use_time_end, a.`status` from bs_interest_ticket_info a where a.`status` = 'INIT' and a.use_time_end >= NOW() AND a.use_time_start <= NOW() and a.user_id = ? ) as a, bs_ticket_grant_plan_check b, bs_interest_ticket_grant_attribute d, ( select e.id, e.activity_type, e.is_support_incr_interest, case when e.`name` like '%港湾%' then 'BIGANGWAN_SERIAL' when e.`name` like '%涌金%' then 'YONGJIN_SERIAL' when e.`name` like '%跨虹%' then 'KUAHONG_SERIAL' when e.`name` like '%保信%' then 'BAOXIN_SERIAL' end as `name`, case e.term when 1 then 30 when 3 then 90 when 6 then 180 when 12 then 365 end as term from bs_product e where e.id = ? and e.activity_type != 'NEW_BUYER' and e.is_support_incr_interest = 'TRUE' ) as e where a.serial_no = b.serial_no and a.serial_no = d.serial_no and find_in_set(e.term, d.term_limit) and find_in_set(e.`name`, d.product_limit) order by IFNULL(d.ticket_apr,0) * IFNULL(e.term,0)/ 36500 desc, a.use_time_end asc, a.id desc 
BsInterestTicketInfoMapper.selectBuyTicketINITList - ==> Parameters: 100.0(Double), 2000056(Integer), 3441(Integer)
BsInterestTicketInfoMapper.selectBuyTicketINITList - <==      Total: 0

-- 查询JSH
BsSubAccountMapper.selectJSHSubAccountByUserId - ==>  Preparing: select a.id, a.account_id, a.code, a.product_type, a.open_balance, a.balance, a.available_balance, a.can_withdraw, a.freeze_balance, a.trans_status, a.status, a.open_time, a.note from bs_sub_account a, bs_account b, bs_user c where a.account_id = b.id and b.user_id = c.id and c.id = ? and a.status = '1' and a.product_type = 'JSH' 
BsSubAccountMapper.selectJSHSubAccountByUserId - ==> Parameters: 2000056(Integer)
BsSubAccountMapper.selectJSHSubAccountByUserId - <==      Total: 1

-- 查询DEP_JSH
BsSubAccountMapper.selectDEPJSHSubAccountByUserId - ==>  Preparing: select a.id, a.account_id, a.code, a.product_type, a.open_balance, a.balance, a.available_balance, a.can_withdraw, a.freeze_balance, a.trans_status, a.status, a.open_time, a.note from bs_sub_account a, bs_account b, bs_user c where a.account_id = b.id and b.user_id = c.id and c.id = ? and a.status = '1' and a.product_type = 'DEP_JSH' 
BsSubAccountMapper.selectDEPJSHSubAccountByUserId - ==> Parameters: 2000056(Integer)
BsSubAccountMapper.selectDEPJSHSubAccountByUserId - <==      Total: 1

-- 查询用户
BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000056(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1
BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000056(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1

-- 查询用户操作信息
BsUserMainOperationMapper.insertSelective - ==>  Preparing: insert into bs_user_main_operation ( user_id, user_mobile, src_ip, src_agent, url, referer, create_time ) values ( ?, ?, ?, ?, ?, ?, ? ) 
BsUserMainOperationMapper.insertSelective - ==> Parameters: 2000056(Integer), 15868150199(String), 192.168.3.13(String), Mozilla/5.0 &#40;Windows NT 10.0; WOW64&#41; AppleWebKit/537.36 &#40;KHTML, like Gecko&#41; Chrome/64.0.3282.119 Safari/537.36(String), http://192.168.3.13:8080/site/gen2.0/regular/bind?money=100&id=3441&dayNum=90&rate=8.0&productName=%E6%B8%AF%E6%B9%BE%E8%AE%A1%E5%88%92%E7%8B%82%E6%AC%A2%E7%A8%B3%E5%AE%9A%E6%94%B6011%E6%9C%9F(String), http://192.168.3.13:8080/site/gen2.0/regular/index?id=3441(String), 2018-04-28 15:42:43.703(Timestamp)
BsUserMainOperationMapper.insertSelective - <==    Updates: 1
