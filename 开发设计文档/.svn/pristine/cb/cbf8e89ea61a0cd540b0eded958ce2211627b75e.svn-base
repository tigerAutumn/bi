-- 查询用户
2018-05-02 10:33:08,239 [qtp1486365718-26] DEBUG com.pinting.business.dao.BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
2018-05-02 10:33:08,239 [qtp1486365718-26] DEBUG com.pinting.business.dao.BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000056(Integer)
2018-05-02 10:33:08,253 [qtp1486365718-26] DEBUG com.pinting.business.dao.BsUserMapper.selectByPrimaryKey - <==      Total: 1

-- 【待定，看看】
2018-05-02 10:33:08,258 [qtp1486365718-29] DEBUG com.pinting.business.dao.BsActiveUserRecordMapper.selectByRecord - ==>  Preparing: select id, user_id, terminal_type, login_date, src_url, create_time, update_time from bs_active_user_record where user_id = ? and terminal_type = ? and login_date = ? 
2018-05-02 10:33:08,259 [qtp1486365718-29] DEBUG com.pinting.business.dao.BsActiveUserRecordMapper.selectByRecord - ==> Parameters: 2000056(Integer), H5(String), 2018-05-02(Date)
2018-05-02 10:33:08,269 [qtp1486365718-29] DEBUG com.pinting.business.dao.BsActiveUserRecordMapper.selectByRecord - <==      Total: 1

-- 查询DEP_JSH
2018-05-02 10:33:08,281 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsSubAccountMapper.selectDEPJSHSubAccountByUserId - ==>  Preparing: select a.id, a.account_id, a.code, a.product_type, a.open_balance, a.balance, a.available_balance, a.can_withdraw, a.freeze_balance, a.trans_status, a.status, a.open_time, a.note from bs_sub_account a, bs_account b, bs_user c where a.account_id = b.id and b.user_id = c.id and c.id = ? and a.status = '1' and a.product_type = 'DEP_JSH' 
2018-05-02 10:33:08,281 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsSubAccountMapper.selectDEPJSHSubAccountByUserId - ==> Parameters: 2000056(Integer)
2018-05-02 10:33:08,320 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsSubAccountMapper.selectDEPJSHSubAccountByUserId - <==      Total: 1

-- 查询JSH
2018-05-02 10:33:08,321 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsSubAccountMapper.selectJSHSubAccountByUserId - ==>  Preparing: select a.id, a.account_id, a.code, a.product_type, a.open_balance, a.balance, a.available_balance, a.can_withdraw, a.freeze_balance, a.trans_status, a.status, a.open_time, a.note from bs_sub_account a, bs_account b, bs_user c where a.account_id = b.id and b.user_id = c.id and c.id = ? and a.status = '1' and a.product_type = 'JSH' 
2018-05-02 10:33:08,321 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsSubAccountMapper.selectJSHSubAccountByUserId - ==> Parameters: 2000056(Integer)
2018-05-02 10:33:08,329 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsSubAccountMapper.selectJSHSubAccountByUserId - <==      Total: 1

-- 统计银行卡个数
2018-05-02 10:33:08,356 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsBankCardMapper.countByExample - ==>  Preparing: select count(*) from bs_bank_card WHERE ( status in ( ? , ? ) and user_id = ? ) 
2018-05-02 10:33:08,356 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsBankCardMapper.countByExample - ==> Parameters: 1(Integer), 5(Integer), 2000056(Integer)
2018-05-02 10:33:08,365 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsBankCardMapper.countByExample - <==      Total: 1

-- 查询用户
2018-05-02 10:33:08,365 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
2018-05-02 10:33:08,366 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000056(Integer)
2018-05-02 10:33:08,377 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsUserMapper.selectByPrimaryKey - <==      Total: 1

-- 查询恒丰绑卡信息
2018-05-02 10:33:08,399 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsHfbankUserExtMapper.selectByExample - ==>  Preparing: select id, user_id, hf_user_id, hf_regist_time, hf_bind_card_time, status, note, create_time, update_time from bs_hfbank_user_ext WHERE ( user_id = ? and status <> ? ) 
2018-05-02 10:33:08,400 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsHfbankUserExtMapper.selectByExample - ==> Parameters: 2000056(Integer), CANCEL(String)
2018-05-02 10:33:08,480 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsHfbankUserExtMapper.selectByExample - <==      Total: 1
2018-05-02 10:33:08,481 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsHfbankUserExtMapper.selectByExample - ==>  Preparing: select id, user_id, hf_user_id, hf_regist_time, hf_bind_card_time, status, note, create_time, update_time from bs_hfbank_user_ext WHERE ( user_id = ? and status = ? ) 
2018-05-02 10:33:08,481 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsHfbankUserExtMapper.selectByExample - ==> Parameters: 2000056(Integer), WAIT_ACTIVATE(String)
2018-05-02 10:33:08,502 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsHfbankUserExtMapper.selectByExample - <==      Total: 0
2018-05-02 10:33:08,503 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsHfbankUserExtMapper.selectByExample - ==>  Preparing: select id, user_id, hf_user_id, hf_regist_time, hf_bind_card_time, status, note, create_time, update_time from bs_hfbank_user_ext WHERE ( user_id = ? and status = ? ) 
2018-05-02 10:33:08,503 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsHfbankUserExtMapper.selectByExample - ==> Parameters: 2000056(Integer), OPEN(String)
2018-05-02 10:33:08,527 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsHfbankUserExtMapper.selectByExample - <==      Total: 1

-- 查询风险测评
2018-05-02 10:33:08,576 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsQuestionnaireMapper.selectByExample - ==>  Preparing: select id, user_id, question_type, question_item, score, expire_days, expire_time, create_time, update_time from bs_questionnaire WHERE ( user_id = ? ) order by update_time desc 
2018-05-02 10:33:08,577 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsQuestionnaireMapper.selectByExample - ==> Parameters: 2000056(Integer)
2018-05-02 10:33:08,587 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsQuestionnaireMapper.selectByExample - <==      Total: 1
2018-05-02 10:33:08,588 [qtp1486365718-30] INFO  com.pinting.business.service.site.impl.AssetsServiceImpl - 用户2000056已进行风险测评

-- 查询用户
2018-05-02 10:33:08,690 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
2018-05-02 10:33:08,691 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000056(Integer)
2018-05-02 10:33:08,702 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsUserMapper.selectByPrimaryKey - <==      Total: 1

-- 【待定，看看】
2018-05-02 10:33:08,712 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsActiveUserRecordMapper.selectByRecord - ==>  Preparing: select id, user_id, terminal_type, login_date, src_url, create_time, update_time from bs_active_user_record where user_id = ? and terminal_type = ? and login_date = ? 
2018-05-02 10:33:08,713 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsActiveUserRecordMapper.selectByRecord - ==> Parameters: 2000056(Integer), H5(String), 2018-05-02(Date)
2018-05-02 10:33:08,724 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsActiveUserRecordMapper.selectByRecord - <==      Total: 1

-- 查询DEP_JSH
2018-05-02 10:33:08,879 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsSubAccountMapper.selectDEPJSHSubAccountByUserId - ==>  Preparing: select a.id, a.account_id, a.code, a.product_type, a.open_balance, a.balance, a.available_balance, a.can_withdraw, a.freeze_balance, a.trans_status, a.status, a.open_time, a.note from bs_sub_account a, bs_account b, bs_user c where a.account_id = b.id and b.user_id = c.id and c.id = ? and a.status = '1' and a.product_type = 'DEP_JSH' 
2018-05-02 10:33:08,879 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsSubAccountMapper.selectDEPJSHSubAccountByUserId - ==> Parameters: 2000056(Integer)
2018-05-02 10:33:08,906 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsSubAccountMapper.selectDEPJSHSubAccountByUserId - <==      Total: 1

-- 查询JSH
2018-05-02 10:33:08,907 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsSubAccountMapper.selectJSHSubAccountByUserId - ==>  Preparing: select a.id, a.account_id, a.code, a.product_type, a.open_balance, a.balance, a.available_balance, a.can_withdraw, a.freeze_balance, a.trans_status, a.status, a.open_time, a.note from bs_sub_account a, bs_account b, bs_user c where a.account_id = b.id and b.user_id = c.id and c.id = ? and a.status = '1' and a.product_type = 'JSH' 
2018-05-02 10:33:08,907 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsSubAccountMapper.selectJSHSubAccountByUserId - ==> Parameters: 2000056(Integer)
2018-05-02 10:33:08,916 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsSubAccountMapper.selectJSHSubAccountByUserId - <==      Total: 1

-- 统计银行卡个数
2018-05-02 10:33:08,917 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsBankCardMapper.countByExample - ==>  Preparing: select count(*) from bs_bank_card WHERE ( status in ( ? , ? ) and user_id = ? ) 
2018-05-02 10:33:08,917 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsBankCardMapper.countByExample - ==> Parameters: 1(Integer), 5(Integer), 2000056(Integer)
2018-05-02 10:33:08,939 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsBankCardMapper.countByExample - <==      Total: 1

-- 查询用户
2018-05-02 10:33:08,940 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
2018-05-02 10:33:08,940 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000056(Integer)
2018-05-02 10:33:08,952 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsUserMapper.selectByPrimaryKey - <==      Total: 1

-- 查询恒丰绑卡信息
2018-05-02 10:33:08,954 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsHfbankUserExtMapper.selectByExample - ==>  Preparing: select id, user_id, hf_user_id, hf_regist_time, hf_bind_card_time, status, note, create_time, update_time from bs_hfbank_user_ext WHERE ( user_id = ? and status <> ? ) 
2018-05-02 10:33:08,954 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsHfbankUserExtMapper.selectByExample - ==> Parameters: 2000056(Integer), CANCEL(String)
2018-05-02 10:33:08,965 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsHfbankUserExtMapper.selectByExample - <==      Total: 1
2018-05-02 10:33:08,966 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsHfbankUserExtMapper.selectByExample - ==>  Preparing: select id, user_id, hf_user_id, hf_regist_time, hf_bind_card_time, status, note, create_time, update_time from bs_hfbank_user_ext WHERE ( user_id = ? and status = ? ) 
2018-05-02 10:33:08,966 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsHfbankUserExtMapper.selectByExample - ==> Parameters: 2000056(Integer), WAIT_ACTIVATE(String)
2018-05-02 10:33:08,975 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsHfbankUserExtMapper.selectByExample - <==      Total: 0
2018-05-02 10:33:08,977 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsHfbankUserExtMapper.selectByExample - ==>  Preparing: select id, user_id, hf_user_id, hf_regist_time, hf_bind_card_time, status, note, create_time, update_time from bs_hfbank_user_ext WHERE ( user_id = ? and status = ? ) 
2018-05-02 10:33:08,977 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsHfbankUserExtMapper.selectByExample - ==> Parameters: 2000056(Integer), OPEN(String)
2018-05-02 10:33:08,985 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsHfbankUserExtMapper.selectByExample - <==      Total: 1

-- 查询风险测评
2018-05-02 10:33:08,987 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsQuestionnaireMapper.selectByExample - ==>  Preparing: select id, user_id, question_type, question_item, score, expire_days, expire_time, create_time, update_time from bs_questionnaire WHERE ( user_id = ? ) order by update_time desc 
2018-05-02 10:33:08,987 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsQuestionnaireMapper.selectByExample - ==> Parameters: 2000056(Integer)
2018-05-02 10:33:08,997 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsQuestionnaireMapper.selectByExample - <==      Total: 1
2018-05-02 10:33:08,997 [qtp1486365718-31] INFO  com.pinting.business.service.site.impl.AssetsServiceImpl - 用户2000056已进行风险测评

-- 产品详情
2018-05-02 10:33:09,593 [qtp1486365718-29] DEBUG com.pinting.business.dao.BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
2018-05-02 10:33:09,593 [qtp1486365718-29] DEBUG com.pinting.business.dao.BsProductMapper.selectByPrimaryKey - ==> Parameters: 3441(Integer)
2018-05-02 10:33:09,611 [qtp1486365718-29] DEBUG com.pinting.business.dao.BsProductMapper.selectByPrimaryKey - <==      Total: 1

-- 
2018-05-02 10:33:09,612 [qtp1486365718-29] DEBUG com.pinting.business.dao.BsSysConfigMapper.selectByPrimaryKey - ==>  Preparing: select conf_key, conf_value, name, note from bs_sys_config where conf_key = ? 
2018-05-02 10:33:09,612 [qtp1486365718-29] DEBUG com.pinting.business.dao.BsSysConfigMapper.selectByPrimaryKey - ==> Parameters: PRICE_LIMIT(String)
2018-05-02 10:33:09,621 [qtp1486365718-29] DEBUG com.pinting.business.dao.BsSysConfigMapper.selectByPrimaryKey - <==      Total: 1

2018-05-02 10:33:09,621 [qtp1486365718-29] DEBUG com.pinting.business.dao.BsSysConfigMapper.selectByPrimaryKey - ==>  Preparing: select conf_key, conf_value, name, note from bs_sys_config where conf_key = ? 
2018-05-02 10:33:09,622 [qtp1486365718-29] DEBUG com.pinting.business.dao.BsSysConfigMapper.selectByPrimaryKey - ==> Parameters: PRICE_CEILING(String)
2018-05-02 10:33:09,630 [qtp1486365718-29] DEBUG com.pinting.business.dao.BsSysConfigMapper.selectByPrimaryKey - <==      Total: 1

2018-05-02 10:33:09,632 [qtp1486365718-29] DEBUG com.pinting.business.dao.BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
2018-05-02 10:33:09,632 [qtp1486365718-29] DEBUG com.pinting.business.dao.BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
2018-05-02 10:33:09,643 [qtp1486365718-29] DEBUG com.pinting.business.dao.BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

2018-05-02 10:33:09,655 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsSubAccountMapper.selectJSHSubAccountByUserId - ==>  Preparing: select a.id, a.account_id, a.code, a.product_type, a.open_balance, a.balance, a.available_balance, a.can_withdraw, a.freeze_balance, a.trans_status, a.status, a.open_time, a.note from bs_sub_account a, bs_account b, bs_user c where a.account_id = b.id and b.user_id = c.id and c.id = ? and a.status = '1' and a.product_type = 'JSH' 
2018-05-02 10:33:09,655 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsSubAccountMapper.selectJSHSubAccountByUserId - ==> Parameters: 2000056(Integer)
2018-05-02 10:33:09,674 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsSubAccountMapper.selectJSHSubAccountByUserId - <==      Total: 1

2018-05-02 10:33:09,674 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsSubAccountMapper.selectDEPJSHSubAccountByUserId - ==>  Preparing: select a.id, a.account_id, a.code, a.product_type, a.open_balance, a.balance, a.available_balance, a.can_withdraw, a.freeze_balance, a.trans_status, a.status, a.open_time, a.note from bs_sub_account a, bs_account b, bs_user c where a.account_id = b.id and b.user_id = c.id and c.id = ? and a.status = '1' and a.product_type = 'DEP_JSH' 
2018-05-02 10:33:09,674 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsSubAccountMapper.selectDEPJSHSubAccountByUserId - ==> Parameters: 2000056(Integer)
2018-05-02 10:33:09,685 [qtp1486365718-30] DEBUG com.pinting.business.dao.BsSubAccountMapper.selectDEPJSHSubAccountByUserId - <==      Total: 1

2018-05-02 10:33:09,703 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
2018-05-02 10:33:09,703 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.selectByPrimaryKey - ==> Parameters: 3441(Integer)
2018-05-02 10:33:09,715 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.selectByPrimaryKey - <==      Total: 1

2018-05-02 10:33:09,721 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsInterestTicketInfoMapper.selectBuyTicketINITList - ==>  Preparing: select a.id, a.user_id, b.serial_name, d.invest_limit as full, concat('限', d.term_limit, '天产品使用') as term_limit, d.product_limit, d.ticket_apr as rate, a.use_time_start, a.use_time_end, a.`status`, e.term, e.is_support_incr_interest, 0 as interest from ( select a.id, a.serial_no, a.user_id,a.use_time_start, a.use_time_end, a.`status` from bs_interest_ticket_info a where a.`status` = 'INIT' and a.use_time_end >= NOW() AND a.use_time_start <= NOW() and a.user_id = ? ) as a, bs_ticket_grant_plan_check b, bs_interest_ticket_grant_attribute d, ( select e.id, e.activity_type, e.is_support_incr_interest, case when e.`name` like '%港湾%' then 'BIGANGWAN_SERIAL' when e.`name` like '%涌金%' then 'YONGJIN_SERIAL' when e.`name` like '%跨虹%' then 'KUAHONG_SERIAL' when e.`name` like '%保信%' then 'BAOXIN_SERIAL' end as `name`, case e.term when 1 then 30 when 3 then 90 when 6 then 180 when 12 then 365 end as term from bs_product e where e.id = ? and e.activity_type != 'NEW_BUYER' and e.is_support_incr_interest = 'TRUE' ) as e where a.serial_no = b.serial_no and a.serial_no = d.serial_no and find_in_set(e.term, d.term_limit) and find_in_set(e.`name`, d.product_limit) order by IFNULL(d.ticket_apr,0) * IFNULL(e.term,0)/ 36500 desc, a.use_time_end asc, a.id desc 
2018-05-02 10:33:09,721 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsInterestTicketInfoMapper.selectBuyTicketINITList - ==> Parameters: 2000056(Integer), 3441(Integer)
2018-05-02 10:33:09,751 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsInterestTicketInfoMapper.selectBuyTicketINITList - <==      Total: 0

2018-05-02 10:33:09,758 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
2018-05-02 10:33:09,758 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsProductMapper.selectByPrimaryKey - ==> Parameters: 3441(Integer)
2018-05-02 10:33:09,770 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsProductMapper.selectByPrimaryKey - <==      Total: 1

2018-05-02 10:33:09,771 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsSysConfigMapper.selectByPrimaryKey - ==>  Preparing: select conf_key, conf_value, name, note from bs_sys_config where conf_key = ? 
2018-05-02 10:33:09,771 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsSysConfigMapper.selectByPrimaryKey - ==> Parameters: PRICE_LIMIT(String)
2018-05-02 10:33:09,779 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsSysConfigMapper.selectByPrimaryKey - <==      Total: 1

2018-05-02 10:33:09,780 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsSysConfigMapper.selectByPrimaryKey - ==>  Preparing: select conf_key, conf_value, name, note from bs_sys_config where conf_key = ? 
2018-05-02 10:33:09,780 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsSysConfigMapper.selectByPrimaryKey - ==> Parameters: PRICE_CEILING(String)
2018-05-02 10:33:09,791 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsSysConfigMapper.selectByPrimaryKey - <==      Total: 1

2018-05-02 10:33:09,792 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
2018-05-02 10:33:09,792 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
2018-05-02 10:33:09,803 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

2018-05-02 10:33:09,806 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
2018-05-02 10:33:09,807 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000056(Integer)
2018-05-02 10:33:09,821 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsUserMapper.selectByPrimaryKey - <==      Total: 1

2018-05-02 10:33:09,834 [qtp1486365718-32] DEBUG com.pinting.business.dao.BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
2018-05-02 10:33:09,835 [qtp1486365718-32] DEBUG com.pinting.business.dao.BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000056(Integer)
2018-05-02 10:33:09,846 [qtp1486365718-32] DEBUG com.pinting.business.dao.BsUserMapper.selectByPrimaryKey - <==      Total: 1

2018-05-02 10:33:09,847 [qtp1486365718-32] DEBUG com.pinting.business.dao.BsUserMainOperationMapper.insertSelective - ==>  Preparing: insert into bs_user_main_operation ( user_id, user_mobile, src_ip, src_agent, url, referer, create_time ) values ( ?, ?, ?, ?, ?, ?, ? ) 
2018-05-02 10:33:09,848 [qtp1486365718-32] DEBUG com.pinting.business.dao.BsUserMainOperationMapper.insertSelective - ==> Parameters: 2000056(Integer), 15868150199(String), 0:0:0:0:0:0:0:1(String), Mozilla/5.0 &#40;Linux; Android 6.0; Nexus 5 Build/MRA58N&#41; AppleWebKit/537.36 &#40;KHTML, like Gecko&#41; Chrome/64.0.3282.119 Mobile Safari/537.36(String), http://localhost:8080/site/micro2.0/regular/bind(String), http://localhost:8080/site/micro2.0/regular/list(String), 2018-05-02 10:33:09.847(Timestamp)
2018-05-02 10:33:09,919 [qtp1486365718-32] DEBUG com.pinting.business.dao.BsUserMainOperationMapper.insertSelective - <==    Updates: 1
