
BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000056(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1

BsActiveUserRecordMapper.selectByRecord - ==>  Preparing: select id, user_id, terminal_type, login_date, src_url, create_time, update_time from bs_active_user_record where user_id = ? and terminal_type = ? and login_date = ? 
BsActiveUserRecordMapper.selectByRecord - ==> Parameters: 2000056(Integer), PC(String), 2018-04-28(Date)
BsActiveUserRecordMapper.selectByRecord - <==      Total: 1

BsUserMapper.countByExample - ==>  Preparing: select count(*) from bs_user WHERE ( mobile = ? and status = ? ) 
BsUserMapper.countByExample - ==> Parameters: 15868150199(String), 1(Integer)
BsUserMapper.countByExample - <==      Total: 1

BsAuthMapper.selectByExample - ==>  Preparing: select id, user_id, mobile, mobile_code, mobile_time, mobile_times, mobile_code_use_times, mobile_last_time, email, email_code, email_time, email_code_use_times, email_last_time from bs_auth WHERE ( mobile = ? ) 
BsAuthMapper.selectByExample - ==> Parameters: 15868150199(String)
BsAuthMapper.selectByExample - <==      Total: 1

BsAuthMapper.selectByExample - ==>  Preparing: select id, user_id, mobile, mobile_code, mobile_time, mobile_times, mobile_code_use_times, mobile_last_time, email, email_code, email_time, email_code_use_times, email_last_time from bs_auth WHERE ( mobile = ? ) 
BsAuthMapper.selectByExample - ==> Parameters: 15868150199(String)
BsAuthMapper.selectByExample - <==      Total: 1

BsAuthMapper.updateByPrimaryKey - ==>  Preparing: update bs_auth set user_id = ?, mobile = ?, mobile_code = ?, mobile_time = ?, mobile_times = ?, mobile_code_use_times = ?, mobile_last_time = ?, email = ?, email_code = ?, email_time = ?, email_code_use_times = ?, email_last_time = ? where id = ? 
BsAuthMapper.updateByPrimaryKey - ==> Parameters: null, 15868150199(String), 1124(String), 2018-04-28 14:08:03.0(Timestamp), 1(Integer), 1(Integer), null, null, null, null, null, null, 143089(Integer)
BsAuthMapper.updateByPrimaryKey - <==    Updates: 1


BsAuthMapper.updateByPrimaryKey - ==>  Preparing: update bs_auth set user_id = ?, mobile = ?, mobile_code = ?, mobile_time = ?, mobile_times = ?, mobile_code_use_times = ?, mobile_last_time = ?, email = ?, email_code = ?, email_time = ?, email_code_use_times = ?, email_last_time = ? where id = ? 
BsAuthMapper.updateByPrimaryKey - ==> Parameters: null, 15868150199(String), verify or expired(String), 2018-04-28 14:08:03.0(Timestamp), 1(Integer), 1(Integer), null, null, null, null, null, null, 143089(Integer)
BsAuthMapper.updateByPrimaryKey - <==    Updates: 1

BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000056(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3441(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1

BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsSysConfigMapper.selectByPrimaryKey - ==>  Preparing: select conf_key, conf_value, name, note from bs_sys_config where conf_key = ? 
BsSysConfigMapper.selectByPrimaryKey - ==> Parameters: SUPER_FINANCE_USER_ID(String)
BsSysConfigMapper.selectByPrimaryKey - <==      Total: 1

BsSysConfigMapper.selectByPrimaryKey - ==>  Preparing: select conf_key, conf_value, name, note from bs_sys_config where conf_key = ? 
BsSysConfigMapper.selectByPrimaryKey - ==> Parameters: ZSD_SUPER_FINANCE_USER_ID(String)
BsSysConfigMapper.selectByPrimaryKey - <==      Total: 1

BsSysConfigMapper.selectByPrimaryKey - ==>  Preparing: select conf_key, conf_value, name, note from bs_sys_config where conf_key = ? 
BsSysConfigMapper.selectByPrimaryKey - ==> Parameters: 7_DAI_SELF_SUPER_FINANCE_USER_ID(String)
BsSysConfigMapper.selectByPrimaryKey - <==      Total: 1


BsSysConfigMapper.selectByPrimaryKey - ==>  Preparing: select conf_key, conf_value, name, note from bs_sys_config where conf_key = ? 
BsSysConfigMapper.selectByPrimaryKey - ==> Parameters: YUN_DAI_SELF_SUPER_FINANCE_USER_ID(String)
BsSysConfigMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3441(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1

2018-04-28 14:08:55,991 [qtp1078912108-29] INFO  c.pinting.business.accounting.finance.service.impl.DepUserBalanceBuyServiceImpl - 固定期限产品余额购买开始:{"sendTime":"","terminalType":2,"version":"","productId":3441,"amount":100,"sendDate":"","extendMap":null,"ticketId":0,"ticketType":"","userId":2000056,"msgID":"","channel":"","transCode":"RegularBuy-BalanceBuy"}
2018-04-28 14:08:55,996 [qtp1078912108-29] INFO  com.pinting.core.redis.sentinel.JedisSentinelSharedPool - 当前redis服务：[114.215.203.101:7379]
2018-04-28 14:08:56,027 [qtp1078912108-29] INFO  c.pinting.business.accounting.finance.service.impl.DepUserBalanceBuyServiceImpl - 余额购买前置校验开始:{"sendTime":"","terminalType":2,"version":"","productId":3441,"amount":100,"sendDate":"","extendMap":null,"ticketId":0,"ticketType":"","userId":2000056,"msgID":"","channel":"","transCode":"RegularBuy-BalanceBuy"}

BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000056(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1

BsHfbankUserExtMapper.selectByExample - ==>  Preparing: select id, user_id, hf_user_id, hf_regist_time, hf_bind_card_time, status, note, create_time, update_time from bs_hfbank_user_ext WHERE ( user_id = ? and status = ? ) 
BsHfbankUserExtMapper.selectByExample - ==> Parameters: 2000056(Integer), OPEN(String)
BsHfbankUserExtMapper.selectByExample - <==      Total: 1

BsPayOrdersMapper.calculateBuyProductTotal - ==>  Preparing: SELECT sum(amount) FROM bs_pay_orders WHERE STATUS = 6 AND trans_type in ('CARD_BUY_PRODUCT','BALANCE_BUY_PRODUCT') AND update_time >= ? AND update_time <= ? 
BsPayOrdersMapper.calculateBuyProductTotal - ==> Parameters: 2018-04-28 00:00:00(String), 2018-04-28 23:59:59(String)
BsPayOrdersMapper.calculateBuyProductTotal - <==      Total: 1

BsSysConfigMapper.selectByPrimaryKey - ==>  Preparing: select conf_key, conf_value, name, note from bs_sys_config where conf_key = ? 
BsSysConfigMapper.selectByPrimaryKey - ==> Parameters: PRICE_CEILING(String)
BsSysConfigMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectProByUser - ==>  Preparing: SELECT p.* FROM bs_product p, bs_user_type_auth a, bs_user u WHERE u.id = ? AND a.user_type = u.user_type AND a.product_id = p.id AND p.id = ? AND p.status = 6 
BsProductMapper.selectProByUser - ==> Parameters: 2000056(Integer), 3441(Integer)
BsProductMapper.selectProByUser - <==      Total: 1

BsSubAccountMapper.selectSubAccount - ==>  Preparing: SELECT s.* FROM bs_account a, bs_sub_account s WHERE a.user_id = ? AND a.id = s.account_id AND s.product_type = ? AND s.status = ? 
BsSubAccountMapper.selectSubAccount - ==> Parameters: 2000056(Integer), DEP_JSH(String), 1(Integer)
BsSubAccountMapper.selectSubAccount - <==      Total: 1

BsQuestionnaireMapper.selectByExample - ==>  Preparing: select id, user_id, question_type, question_item, score, expire_days, expire_time, create_time, update_time from bs_questionnaire WHERE ( user_id = ? ) order by update_time desc 
BsQuestionnaireMapper.selectByExample - ==> Parameters: 2000056(Integer)
BsQuestionnaireMapper.selectByExample - <==      Total: 1

2018-04-28 14:08:56,550 [qtp1078912108-29] INFO  com.pinting.business.service.site.impl.AssetsServiceImpl - 用户2000056已进行风险测评
2018-04-28 14:08:56,551 [qtp1078912108-29] INFO  c.pinting.business.accounting.finance.service.impl.DepUserBalanceBuyServiceImpl - 余额购买前置校验结束:{"sendTime":"","terminalType":2,"version":"","productId":3441,"amount":100,"sendDate":"","extendMap":null,"ticketId":0,"ticketType":"","userId":2000056,"msgID":"","channel":"","transCode":"RegularBuy-BalanceBuy"}

BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsAccountMapper.selectByExample - ==>  Preparing: select id, account_code, user_id, open_time, close_time, last_trans_time, status from bs_account WHERE ( user_id = ? ) 
BsAccountMapper.selectByExample - ==> Parameters: 2000056(Integer)
BsAccountMapper.selectByExample - <==      Total: 1

BsProductSubAccountPrefixMapper.selectByExample - ==>  Preparing: select product_id, sub_account_prefix from bs_product_sub_account_prefix WHERE ( product_id = ? ) 
BsProductSubAccountPrefixMapper.selectByExample - ==> Parameters: 3441(Integer)
BsProductSubAccountPrefixMapper.selectByExample - <==      Total: 1

BsSubAccountMapper.insertSelective - ==>  Preparing: insert into bs_sub_account ( account_id, code, product_id, product_type, product_code, product_name, product_rate, extra_rate, open_balance, balance, available_balance, can_withdraw, freeze_balance, trans_status, status, interest_begin_date, last_finish_interest_date, accumulation_inerest, open_time ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) 
BsSubAccountMapper.insertSelective - ==> Parameters: 37399(Integer), 51672000056201804281408563806(String), 3441(Integer), AUTH_YUN(String), 4090(String), 港湾计划狂欢稳定收011期(String), 8.0(Double), 0.0(Double), 100.0(Double), 100.0(Double), 100.0(Double), 100.0(Double), 0.0(Double), 0(Integer), 2(Integer), 2018-04-29(Date), 2018-07-28(Date), 0.0(Double), 2018-04-28 14:08:56.733(Timestamp)
BsSubAccountMapper.insertSelective - <==    Updates: 1

BsProductSubAccountPrefixMapper.selectByExample - ==>  Preparing: select product_id, sub_account_prefix from bs_product_sub_account_prefix WHERE ( product_id = ? ) 
BsProductSubAccountPrefixMapper.selectByExample - ==> Parameters: 3441(Integer)
BsProductSubAccountPrefixMapper.selectByExample - <==      Total: 1

BsSubAccountMapper.insertSelective - ==>  Preparing: insert into bs_sub_account ( account_id, code, product_id, product_type, product_code, product_name, product_rate, extra_rate, open_balance, balance, available_balance, can_withdraw, freeze_balance, trans_status, status, interest_begin_date, last_finish_interest_date, accumulation_inerest, open_time ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) 
BsSubAccountMapper.insertSelective - ==> Parameters: 37399(Integer), 51672000056201804281408572480(String), 3441(Integer), DIFF(String), 4090(String), 港湾计划狂欢稳定收011期(String), 8.0(Double), 0.0(Double), 0.0(Double), 0.0(Double), 0.0(Double), 0.0(Double), 0.0(Double), 0(Integer), 2(Integer), 2018-04-29(Date), 2018-07-28(Date), 0.0(Double), 2018-04-28 14:08:56.733(Timestamp)
BsSubAccountMapper.insertSelective - <==    Updates: 1

BsSubAccountPairMapper.insertSelective - ==>  Preparing: insert into bs_sub_account_pair ( auth_account_id, diff_account_id, term, create_time, update_time ) values ( ?, ?, ?, ?, ? ) 
BsSubAccountPairMapper.insertSelective - ==> Parameters: 201066(Integer), 201067(Integer), 3(Integer), 2018-04-28 14:08:56.733(Timestamp), 2018-04-28 14:08:56.733(Timestamp)
BsSubAccountPairMapper.insertSelective - <==    Updates: 1

BsPayOrdersMapper.insertSelective - ==>  Preparing: insert into bs_pay_orders ( order_no, amount, user_id, sub_account_id, status, money_type, terminal_type, create_time, update_time, account_type, trans_type, mobile, id_card, user_name ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) 
BsPayOrdersMapper.insertSelective - ==> Parameters: 201804281408562000056007523487(String), 100.0(Double), 2000056(Integer), 201066(Integer), 6(Integer), 0(Integer), 2(Integer), 2018-04-28 14:08:56.551(Timestamp), 2018-04-28 14:08:56.551(Timestamp), 1(Integer), BALANCE_BUY_PRODUCT(String), 15868150199(String), 33012719930116511X(String), 程裕兵(String)
BsPayOrdersMapper.insertSelective - <==    Updates: 1

BsPayOrdersJnlMapper.insertSelective - ==>  Preparing: insert into bs_pay_orders_jnl ( order_id, order_no, trans_code, trans_amount, sys_time, user_id, sub_account_id, create_time ) values ( ?, ?, ?, ?, ?, ?, ?, ? ) 
BsPayOrdersJnlMapper.insertSelective - ==> Parameters: 306343(Integer), 201804281408562000056007523487(String), SUCCESS(String), 100.0(Double), 2018-04-28 14:08:56.551(Timestamp), 2000056(Integer), 201066(Integer), 2018-04-28 14:08:56.551(Timestamp)
BsPayOrdersJnlMapper.insertSelective - <==    Updates: 1

BsUserTransDetailMapper.insertSelective - ==>  Preparing: insert into bs_user_trans_detail ( user_id, trans_type, trans_status, order_no, create_time, update_time, amount ) values ( ?, ?, ?, ?, ?, ?, ? ) 
BsUserTransDetailMapper.insertSelective - ==> Parameters: 2000056(Integer), BUY(String), 2(String), 201804281408562000056007523487(String), 2018-04-28 14:08:56.551(Timestamp), 2018-04-28 14:08:56.551(Timestamp), -100.0(Double)
BsUserTransDetailMapper.insertSelective - <==    Updates: 1

BsSubAccountMapper.selectSingleSubActByUserAndType - ==>  Preparing: SELECT s.* FROM bs_account a, bs_sub_account s WHERE a.user_id = ? AND a.id = s.account_id AND s.product_type = ? 
BsSubAccountMapper.selectSingleSubActByUserAndType - ==> Parameters: 2000056(Integer), DEP_JSH(String)
BsSubAccountMapper.selectSingleSubActByUserAndType - <==      Total: 1

BsSubAccountMapper.selectSubAccountByIdForLock - ==>  Preparing: SELECT s.* FROM bs_sub_account s WHERE s.id = ? FOR UPDATE 
BsSubAccountMapper.selectSubAccountByIdForLock - ==> Parameters: 200891(Integer)
BsSubAccountMapper.selectSubAccountByIdForLock - <==      Total: 1

BsSubAccountMapper.updateByPrimaryKeySelective - ==>  Preparing: update bs_sub_account SET balance = ?, available_balance = ?, can_withdraw = ? where id = ? 
BsSubAccountMapper.updateByPrimaryKeySelective - ==> Parameters: 99750.0(Double), 99750.0(Double), 99750.0(Double), 200891(Integer)
BsSubAccountMapper.updateByPrimaryKeySelective - <==    Updates: 1

BsSysSubAccountMapper.selectByCode - ==>  Preparing: select id, code, open_balance, balance, available_balance, can_withdraw, freeze_balance, last_trans_date, note from bs_sys_sub_account where code = ? 
BsSysSubAccountMapper.selectByCode - ==> Parameters: BGW_USER(String)
BsSysSubAccountMapper.selectByCode - <==      Total: 1

BsSysSubAccountMapper.selectSysSubAccountForLock - ==>  Preparing: select * from bs_sys_sub_account where id = ? for update 
BsSysSubAccountMapper.selectSysSubAccountForLock - ==> Parameters: 15(Integer)
BsSysSubAccountMapper.selectSysSubAccountForLock - <==      Total: 1

BsSysSubAccountMapper.updateByPrimaryKeySelective - ==>  Preparing: update bs_sys_sub_account SET balance = ?, available_balance = ?, can_withdraw = ? where id = ? 
BsSysSubAccountMapper.updateByPrimaryKeySelective - ==> Parameters: 4.273224727E7(Double), 4.857979828E7(Double), 4.857979828E7(Double), 15(Integer)
BsSysSubAccountMapper.updateByPrimaryKeySelective - <==    Updates: 1

BsSysSubAccountMapper.selectByCode - ==>  Preparing: select id, code, open_balance, balance, available_balance, can_withdraw, freeze_balance, last_trans_date, note from bs_sys_sub_account where code = ? 
BsSysSubAccountMapper.selectByCode - ==> Parameters: BGW_AUTH_YUN(String)
BsSysSubAccountMapper.selectByCode - <==      Total: 1

BsSysSubAccountMapper.selectSysSubAccountForLock - ==>  Preparing: select * from bs_sys_sub_account where id = ? for update 
BsSysSubAccountMapper.selectSysSubAccountForLock - ==> Parameters: 17(Integer)
BsSysSubAccountMapper.selectSysSubAccountForLock - <==      Total: 1

BsSysSubAccountMapper.updateByPrimaryKeySelective - ==>  Preparing: update bs_sys_sub_account SET balance = ?, available_balance = ?, can_withdraw = ? where id = ? 
BsSysSubAccountMapper.updateByPrimaryKeySelective - ==> Parameters: 1.2955980738E8(Double), 1.2955980738E8(Double), 1.2955980738E8(Double), 17(Integer)
BsSysSubAccountMapper.updateByPrimaryKeySelective - <==    Updates: 1

BsUserMapper.selectByPKForLock - ==>  Preparing: select * from bs_user where id = ? for update 
BsUserMapper.selectByPKForLock - ==> Parameters: 2000056(Integer)
BsUserMapper.selectByPKForLock - <==      Total: 1

BsUserMapper.updateByPrimaryKeySelective - ==>  Preparing: update bs_user SET can_withdraw = ? where id = ? 
BsUserMapper.updateByPrimaryKeySelective - ==> Parameters: 99750.0(Double), 2000056(Integer)
BsUserMapper.updateByPrimaryKeySelective - <==    Updates: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3441(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.updateByPrimaryKeySelective - ==>  Preparing: update bs_product SET name = ?, type = ?, activity_type = ?, code = ?, interest_type = ?, base_rate = ?, term = ?, max_total_amount = ?, min_invest_amount = ?, max_invest_amount = ?, max_invest_times = ?, start_time = ?, curr_total_amount = ?, status = ?, note = ?, invest_num = ?, serial_name = ?, property_type = ?, begin_interest_days = ?, return_type = ?, interest_deal = ?, is_support_transfer = ?, manage_fee = ?, show_terminal = ?, property_id = ?, is_suggest = ?, checker = ?, check_time = ?, distribute_time = ?, is_support_red_packet = ?, create_time = ?, update_time = ?, is_support_incr_interest = ? where id = ? 
BsProductMapper.updateByPrimaryKeySelective - ==> Parameters: 港湾计划狂欢稳定收011期(String), REG(String), NORMAL(String), 4090(String), 1(Integer), 8.0(Double), 3(Integer), 100000.0(Double), 100.0(Double), 1.0E10(Double), 1000000(Integer), 2018-02-27 09:27:44.0(Timestamp), 70000.0(Double), 6(Integer), 本计划资金投资于达飞财富推荐的小微债权项目，借款人主要为蓝领工人、服务业人员等消费者，借款小额分散，风险可控。每个借款人经过达飞财富与币港湾的严格筛选，并由达飞财富承诺回购。(String), 29(Integer), CUSTOMIZE(String), CASH_LOOP(String), NEXT_DAY(String), FINISH_RETURN_ALL(String), FINISH_RETURN(String), NO(String), 0.0(Double), APP,PC,H5(String), 4(Integer), YES(String), 1(Integer), 2018-02-27 09:25:42.0(Timestamp), 2018-02-27 09:25:42.0(Timestamp), FALSE(String), 2018-02-27 09:25:34.0(Timestamp), 2018-02-27 09:27:44.0(Timestamp), TRUE(String), 3441(Integer)
BsProductMapper.updateByPrimaryKeySelective - <==    Updates: 1

BsSubAccountMapper.selectByExample - ==>  Preparing: select id, account_id, code, product_id, product_type, product_code, product_name, product_rate, bank_card, extra_rate, open_balance, balance, available_balance, can_withdraw, freeze_balance, trans_status, status, check_status, interest_begin_date, last_trans_date, last_cal__interest_date, last_finish_interest_date, accumulation_inerest, open_time, close_time, transfer_time, note from bs_sub_account WHERE ( account_id = ? and status not in ( ? , ? ) and product_type in ( ? , ? , ? , ? , ? ) ) 
BsSubAccountMapper.selectByExample - ==> Parameters: 37399(Integer), 1(Integer), 6(Integer), REG(String), AUTH(String), AUTH_YUN(String), AUTH_ZSD(String), AUTH_7(String)
BsSubAccountMapper.selectByExample - <==      Total: 3

BsAccountJnlMapper.insertSelective - ==>  Preparing: insert into bs_account_jnl ( trans_time, trans_code, trans_name, trans_amount, sys_time, cd_flag1, user_id1, account_id1, sub_account_id1, sub_account_code1, before_balance1, after_balance1, before_avialable_balance1, after_avialable_balance1, before_freeze_balance1, after_freeze_balance1, cd_flag2, user_id2, account_id2, sub_account_id2, before_balance2, after_balance2, before_avialable_balance2, after_avialable_balance2, before_freeze_balance2, after_freeze_balance2, fee ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) 
BsAccountJnlMapper.insertSelective - ==> Parameters: 2018-04-28 14:08:58.329(Timestamp), BALANCE_BUY_PRODUCT(String), 余额购买(String), 100.0(Double), 2018-04-28 14:08:58.329(Timestamp), 2(Integer), 2000056(Integer), 37399(Integer), 200891(Integer), 40012000056201803261101026786(String), 99850.0(Double), 99750.0(Double), 99850.0(Double), 99750.0(Double), 0.0(Double), 0.0(Double), 1(Integer), 2000056(Integer), 37399(Integer), 201066(Integer), 0.0(Double), 100.0(Double), 0.0(Double), 100.0(Double), 0.0(Double), 0.0(Double), 0.0(Double)
BsAccountJnlMapper.insertSelective - <==    Updates: 1

BsSysAccountJnlMapper.insertSelective - ==>  Preparing: insert into bs_sys_account_jnl ( trans_time, trans_code, trans_name, trans_amount, sys_time, cd_flag1, sub_account_code1, before_balance1, after_balance1, before_avialable_balance1, after_avialable_balance1, before_freeze_balance1, after_freeze_balance1, cd_flag2, sub_account_code2, before_balance2, after_balance2, before_avialable_balance2, after_avialable_balance2, before_freeze_balance2, after_freeze_balance2, fee, status ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) 
BsSysAccountJnlMapper.insertSelective - ==> Parameters: 2018-04-28 14:08:58.54(Timestamp), USER_BALANCE_BUY(String), 用户购买(String), 100.0(Double), 2018-04-28 14:08:58.54(Timestamp), 2(Integer), BGW_USER(String), 4.273234727E7(Double), 4.273224727E7(Double), 4.857989828E7(Double), 4.857979828E7(Double), -5847387.15(Double), -5847387.15(Double), 1(Integer), BGW_AUTH_YUN(String), 1.2955970738E8(Double), 1.2955980738E8(Double), 1.2955970738E8(Double), 1.2955980738E8(Double), 0.0(Double), 0.0(Double), 0.0(Double), 1(Integer)
BsSysAccountJnlMapper.insertSelective - <==    Updates: 1

2018-04-28 14:08:58,678 [qtp1078912108-29] INFO  c.pinting.business.accounting.finance.service.impl.DepUserBalanceBuyServiceImpl - =========【使用账户余额购买产品成功】币港湾订单号：201804281408562000056007523487=========

BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000056(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1

BsSysConfigMapper.selectByPrimaryKey - ==>  Preparing: select conf_key, conf_value, name, note from bs_sys_config where conf_key = ? 
BsSysConfigMapper.selectByPrimaryKey - ==> Parameters: REGISTDATE_SEPARATE_4_BONUS(String)
BsSysConfigMapper.selectByPrimaryKey - <==      Total: 1

BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000056(Integer)

BsActivityMapper.selectByPrimaryKey - ==>  Preparing: select id, name, start_time, end_time, note, create_time, update_time from bs_activity where id = ? 
BsActivityMapper.selectByPrimaryKey - ==> Parameters: 31(Integer)
BsActivityMapper.selectByPrimaryKey - <==      Total: 1

2018-04-28 14:08:58,798 [Thread-195] INFO  c.p.b.accounting.finance.service.impl.process.DepUserBonusGrant4BuyProcess - 【2018年1月1日之后的用户奖励金发放处理】开始
BsUserMapper.selectByPrimaryKey - <==      Total: 1
BsAutoRedPacketRuleMapper.selectByExample - ==>  Preparing: select id, serial_no, agent_ids, trigger_type, valid_term_type, status, trigger_amount_start, trigger_amount_end, trigger_invite_num, distribute_time_start, distribute_time_end, available_days, create_time, update_time from bs_auto_red_packet_rule WHERE ( trigger_type = ? and status = ? and distribute_time_start <= ? and distribute_time_end >= ? and trigger_amount_start <= ? and trigger_amount_end >= ? ) 
BsAutoRedPacketRuleMapper.selectByExample - ==> Parameters: BUY_FULL(String), AVAILABLE(String), 2018-04-28 14:08:58.801(Timestamp), 2018-04-28 14:08:58.801(Timestamp), 100.0(Double), 100.0(Double)
BsAutoRedPacketRuleMapper.selectByExample - <==      Total: 0
BsWxUserInfoMapper.selectByExample - ==>  Preparing: select id, open_id, user_id, create_time, update_time from bs_wx_user_info WHERE ( user_id = ? ) 
BsWxUserInfoMapper.selectByExample - ==> Parameters: 2000056(Integer)
BsWxUserInfoMapper.selectByExample - <==      Total: 0

2018-04-28 14:08:58,934 [qtp1078912108-29] INFO  c.pinting.business.accounting.finance.service.impl.DepUserBalanceBuyServiceImpl - 固定期限产品余额购买微信消息返回信息：该用户无openId
2018-04-28 14:08:58,942 [qtp1078912108-29] INFO  c.pinting.business.accounting.finance.service.impl.DepUserBalanceBuyServiceImpl - 固定期限产品余额购买结束:{"extendMap":{"product":{"totalRows":0,"serialId":0,"checkTime":null,"currTotalAmount":70000,"type":"","endTime":null,"pageNum":1,"startTime":null,"orderDirection":"","finishTime":null,"updateTime":null,"terminator":0,"status":0,"serialOrder":0,"orderField":"","maxInvestAmount":0,"code":"","manageFee":0,"start":0,"numPerPage":20,"baseRate":0,"isSupportRedPacket":"","firstPage":true,"serialName":"","activityType":"","totalPages":0,"createTime":null,"sysReturnRate":0,"maxInvestTimes":0,"showTerminal":"","interestType":0,"id":3441,"sord":"","propertyId":0,"name":"港湾计划狂欢稳定收011期","term4Day":0,"isSuggest":"","distributor":0,"maxTotalAmount":100000,"end":19,"note":"","lastPage":false,"beginInterestDays":"","investNum":0,"isSupportIncrInterest":"","distributeTime":null,"isSupportTransfer":"","maxRate":0,"interestDeal":"","checker":0,"returnType":"","propertyType":"","term":0,"minInvestAmount":100,"maxSingleInvestAmount":0},"userId":2000056,"investAmount":100,"subAccountId":201066},"isAutoRedPocket":"no","resCode":"","resMsg":"","msgID":"","version":""}
2018-04-28 14:08:58,943 [qtp1078912108-29] INFO  com.pinting.core.redis.sentinel.JedisSentinelSharedPool - 当前redis服务：[114.215.203.101:7379]
BsUserCustomerManagerMapper.selectByExample - ==>  Preparing: select id, customer_manager_dafy_id, user_id, grade, create_time, update_time from bs_user_customer_manager WHERE ( user_id = ? and grade = ? ) 
BsUserCustomerManagerMapper.selectByExample - ==> Parameters: 2000056(Integer), 1(Integer)
BsUserCustomerManagerMapper.selectByExample - <==      Total: 0

-- 可以删除
2018-04-28 14:08:59,047 [qtp1078912108-29] INFO  com.pinting.business.service.site.impl.ActivityServiceImpl - 2017感恩节活动产生投资号，用户ID：2000056，站岗户ID：201066
BsActivityMapper.selectByPrimaryKey - ==>  Preparing: select id, name, start_time, end_time, note, create_time, update_time from bs_activity where id = ? 
BsActivityMapper.selectByPrimaryKey - ==> Parameters: 29(Integer)
BsActivityMapper.selectByPrimaryKey - <==      Total: 1
2018-04-28 14:08:59,057 [qtp1078912108-29] INFO  com.pinting.business.service.site.impl.ActivityServiceImpl - 感恩节幸运号活动 已结束。开始时间：2017-11-14 00:00:00；结束时间：2017-11-23 23:59:59

BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000056(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1

BsUserSignSealMapper.selectByExample - ==>  Preparing: select id, user_id, user_name, id_card, p10, key_identifier, dn, sequence_no, serial_no, start_time, end_time, signature_cert, encryption_cert, encryption_private_key, pfx_data, pfx_path, pfx_password, seal_person, seal_org, seal_name, seal_code, seal_password, create_time, update_time from bs_user_sign_seal WHERE ( id_card = ? and user_name = ? ) order by id desc 
BsUserSignSealMapper.selectByExample - ==> Parameters: 33012719930116511X(String), 程裕兵(String)
BsUserSignSealMapper.selectByExample - <==      Total: 1

BsUserSealFileMapper.insertSelective - ==>  Preparing: insert into bs_user_seal_file ( user_id, user_src, agreement_no, src_address, seal_type, relative_info, seal_status, create_time ) values ( ?, ?, ?, ?, ?, ?, ?, ? ) 
BsUserSealFileMapper.insertSelective - ==> Parameters: 2000014(Integer), BIGANGWAN(String), 201066(String), https://mtest1.bigangwan.com/micro2.0/regular/buyagreementPdf?investId=201066(String), BUY(String), 201066(String), UNDOWNLOAD(String), 2018-04-28 14:09:03.154(Timestamp)
BsUserSealFileMapper.insertSelective - <==    Updates: 1

BsPropertyInfoMapper.checkPropertySymbol - ==>  Preparing: SELECT a.property_symbol FROM bs_property_info a, bs_product b, bs_sub_account c WHERE a.id = b.property_id AND b.id = c.product_id AND c.id = ? 
BsPropertyInfoMapper.checkPropertySymbol - ==> Parameters: 201066(Integer)
BsPropertyInfoMapper.checkPropertySymbol - <==      Total: 1

BsFileSealRelationMapper.insertSelective - ==>  Preparing: insert into bs_file_seal_relation ( seal_file_id, content_type, keywords, user_seal_id, create_time, update_time ) values ( ?, ?, ?, ?, ?, ? ) 
BsFileSealRelationMapper.insertSelective - ==> Parameters: 250482(Integer), PERSON(String), 甲方（委托方）：程裕兵(String), 24152(Integer), 2018-04-28 14:09:03.251(Timestamp), 2018-04-28 14:09:03.251(Timestamp)
BsFileSealRelationMapper.insertSelective - <==    Updates: 1

BsUserSignSealMapper.selectByExample - ==>  Preparing: select id, user_id, user_name, id_card, p10, key_identifier, dn, sequence_no, serial_no, start_time, end_time, signature_cert, encryption_cert, encryption_private_key, pfx_data, pfx_path, pfx_password, seal_person, seal_org, seal_name, seal_code, seal_password, create_time, update_time from bs_user_sign_seal WHERE ( user_name = ? ) 
BsUserSignSealMapper.selectByExample - ==> Parameters: 杭州币港湾科技有限公司(String)
BsUserSignSealMapper.selectByExample - <==      Total: 1

BsFileSealRelationMapper.insertSelective - ==>  Preparing: insert into bs_file_seal_relation ( seal_file_id, content_type, keywords, user_seal_id, create_time, update_time ) values ( ?, ?, ?, ?, ?, ? ) 
BsFileSealRelationMapper.insertSelective - ==> Parameters: 250482(Integer), COMPANY(String), 乙方（受托方）：杭州币港湾科技有限公司(String), 24155(Integer), 2018-04-28 14:09:03.251(Timestamp), 2018-04-28 14:09:03.251(Timestamp)
BsFileSealRelationMapper.insertSelective - <==    Updates: 1

2018-04-28 14:09:03,833 [qtp1078912108-29] INFO  com.pinting.business.aspect.ProductBuyResultAspect - >>>签章入redis走定时-file_id:250482
2018-04-28 14:09:03,897 [qtp1078912108-29] INFO  com.pinting.core.redis.sentinel.JedisSentinelSharedPool - 当前redis服务：[114.215.203.101:7379]
BsActivityMapper.selectByPrimaryKey - ==>  Preparing: select id, name, start_time, end_time, note, create_time, update_time from bs_activity where id = ? 
BsActivityMapper.selectByPrimaryKey - ==> Parameters: 2(Integer)
BsActivityMapper.selectByPrimaryKey - <==      Total: 1

--删除
2018-04-28 14:09:04,160 [qtp1078912108-29] INFO  com.pinting.business.aspect.ProductBuyResultAspect - 用户 2000056 购买了非币港湾理财平台产品 100.0 元，无元宵活动奖励金，子账户编号：201066
2018-04-28 14:09:04,160 [qtp1078912108-29] INFO  com.pinting.business.service.site.impl.ActivityServiceImpl - 购买之后，解锁元宝开始
2018-04-28 14:09:04,197 [qtp1078912108-29] INFO  com.pinting.business.service.site.impl.ActivityServiceImpl - 购买之后，解锁元宝开始，但未到解锁时间：2017-03-18


BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000056(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1
BsRedPacketCheckMapper.autoRedPacketTotalGrid - ==>  Preparing: SELECT a.subtract, b.agent_ids FROM `bs_red_packet_check` a, bs_auto_red_packet_rule b WHERE a.serial_no = b.serial_no AND b.`status` = 'AVAILABLE' AND a.check_status = 'PASS' AND a.distribute_type = 'AUTO' AND (b.agent_ids LIKE '%0%' OR b.agent_ids LIKE '%-1%') AND b.trigger_type = ? AND b.distribute_time_start < NOW() AND b.distribute_time_end > NOW() 
BsRedPacketCheckMapper.autoRedPacketTotalGrid - ==> Parameters: NEW_USER(String)
BsRedPacketCheckMapper.autoRedPacketTotalGrid - <==      Total: 0
