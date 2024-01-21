BsPayOrdersMapper.insertSelective - <==    Updates: 1

BsPayOrdersJnlMapper.insertSelective - ==>  Preparing: insert into bs_pay_orders_jnl ( order_id, order_no, trans_code, trans_amount, sys_time, user_id, sub_account_id, create_time ) values ( ?, ?, ?, ?, ?, ?, ?, ? ) 
BsPayOrdersJnlMapper.insertSelective - ==> Parameters: 306346(Integer), 201805021556492000000071941759(String), SUCCESS(String), 100.0(Double), 2018-05-02 15:56:49.778(Timestamp), 2000000(Integer), 201072(Integer), 2018-05-02 15:56:49.778(Timestamp)
BsPayOrdersJnlMapper.insertSelective - <==    Updates: 1

BsUserTransDetailMapper.insertSelective - ==>  Preparing: insert into bs_user_trans_detail ( user_id, trans_type, trans_status, order_no, create_time, update_time, amount ) values ( ?, ?, ?, ?, ?, ?, ? ) 
BsUserTransDetailMapper.insertSelective - ==> Parameters: 2000000(Integer), BUY(String), 2(String), 201805021556492000000071941759(String), 2018-05-02 15:56:49.778(Timestamp), 2018-05-02 15:56:49.778(Timestamp), -100.0(Double)
BsUserTransDetailMapper.insertSelective - <==    Updates: 1

BsSubAccountMapper.selectSingleSubActByUserAndType - ==>  Preparing: SELECT s.* FROM bs_account a, bs_sub_account s WHERE a.user_id = ? AND a.id = s.account_id AND s.product_type = ? 
BsSubAccountMapper.selectSingleSubActByUserAndType - ==> Parameters: 2000000(Integer), DEP_JSH(String)
BsSubAccountMapper.selectSingleSubActByUserAndType - <==      Total: 1

BsSubAccountMapper.selectSubAccountByIdForLock - ==>  Preparing: SELECT s.* FROM bs_sub_account s WHERE s.id = ? FOR UPDATE 
BsSubAccountMapper.selectSubAccountByIdForLock - ==> Parameters: 200050(Integer)
BsSubAccountMapper.selectSubAccountByIdForLock - <==      Total: 1

BsSubAccountMapper.updateByPrimaryKeySelective - ==>  Preparing: update bs_sub_account SET balance = ?, available_balance = ?, can_withdraw = ? where id = ? 
BsSubAccountMapper.updateByPrimaryKeySelective - ==> Parameters: 703.0(Double), 703.0(Double), 703.0(Double), 200050(Integer)
BsSubAccountMapper.updateByPrimaryKeySelective - <==    Updates: 1

BsSysSubAccountMapper.selectByCode - ==>  Preparing: select id, code, open_balance, balance, available_balance, can_withdraw, freeze_balance, last_trans_date, note from bs_sys_sub_account where code = ? 
BsSysSubAccountMapper.selectByCode - ==> Parameters: BGW_USER(String)
BsSysSubAccountMapper.selectByCode - <==      Total: 1

BsSysSubAccountMapper.selectSysSubAccountForLock - ==>  Preparing: select * from bs_sys_sub_account where id = ? for update 
BsSysSubAccountMapper.selectSysSubAccountForLock - ==> Parameters: 15(Integer)
BsSysSubAccountMapper.selectSysSubAccountForLock - <==      Total: 1

BsSysSubAccountMapper.updateByPrimaryKeySelective - ==>  Preparing: update bs_sys_sub_account SET balance = ?, available_balance = ?, can_withdraw = ? where id = ? 
BsSysSubAccountMapper.updateByPrimaryKeySelective - ==> Parameters: 4.273194727E7(Double), 4.857949828E7(Double), 4.857949828E7(Double), 15(Integer)
BsSysSubAccountMapper.updateByPrimaryKeySelective - <==    Updates: 1

BsSysSubAccountMapper.selectByCode - ==>  Preparing: select id, code, open_balance, balance, available_balance, can_withdraw, freeze_balance, last_trans_date, note from bs_sys_sub_account where code = ? 
BsSysSubAccountMapper.selectByCode - ==> Parameters: BGW_AUTH_YUN(String)
BsSysSubAccountMapper.selectByCode - <==      Total: 1

BsSysSubAccountMapper.selectSysSubAccountForLock - ==>  Preparing: select * from bs_sys_sub_account where id = ? for update 
BsSysSubAccountMapper.selectSysSubAccountForLock - ==> Parameters: 17(Integer)
BsSysSubAccountMapper.selectSysSubAccountForLock - <==      Total: 1

BsSysSubAccountMapper.updateByPrimaryKeySelective - ==>  Preparing: update bs_sys_sub_account SET balance = ?, available_balance = ?, can_withdraw = ? where id = ? 
BsSysSubAccountMapper.updateByPrimaryKeySelective - ==> Parameters: 1.2956010738E8(Double), 1.2956010738E8(Double), 1.2956010738E8(Double), 17(Integer)
BsSysSubAccountMapper.updateByPrimaryKeySelective - <==    Updates: 1

BsUserMapper.selectByPKForLock - ==>  Preparing: select * from bs_user where id = ? for update 
BsUserMapper.selectByPKForLock - ==> Parameters: 2000000(Integer)
BsUserMapper.selectByPKForLock - <==      Total: 1

BsUserMapper.updateByPrimaryKeySelective - ==>  Preparing: update bs_user SET can_withdraw = ? where id = ? 
BsUserMapper.updateByPrimaryKeySelective - ==> Parameters: 703.0(Double), 2000000(Integer)
BsUserMapper.updateByPrimaryKeySelective - <==    Updates: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3441(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.updateByPrimaryKeySelective - ==>  Preparing: update bs_product SET name = ?, type = ?, activity_type = ?, code = ?, interest_type = ?, base_rate = ?, term = ?, max_total_amount = ?, min_invest_amount = ?, max_invest_amount = ?, max_invest_times = ?, start_time = ?, curr_total_amount = ?, status = ?, note = ?, invest_num = ?, serial_name = ?, property_type = ?, begin_interest_days = ?, return_type = ?, interest_deal = ?, is_support_transfer = ?, manage_fee = ?, show_terminal = ?, property_id = ?, is_suggest = ?, checker = ?, check_time = ?, distribute_time = ?, is_support_red_packet = ?, create_time = ?, update_time = ?, is_support_incr_interest = ? where id = ? 
BsProductMapper.updateByPrimaryKeySelective - ==> Parameters: 港湾计划狂欢稳定收011期(String), REG(String), NORMAL(String), 4090(String), 1(Integer), 8.0(Double), 3(Integer), 100000.0(Double), 100.0(Double), 1.0E10(Double), 1000000(Integer), 2018-02-27 09:27:44.0(Timestamp), 70300.0(Double), 6(Integer), 本计划资金投资于达飞财富推荐的小微债权项目，借款人主要为蓝领工人、服务业人员等消费者，借款小额分散，风险可控。每个借款人经过达飞财富与币港湾的严格筛选，并由达飞财富承诺回购。(String), 32(Integer), CUSTOMIZE(String), CASH_LOOP(String), NEXT_DAY(String), FINISH_RETURN_ALL(String), FINISH_RETURN(String), NO(String), 0.0(Double), APP,PC,H5(String), 4(Integer), YES(String), 1(Integer), 2018-02-27 09:25:42.0(Timestamp), 2018-02-27 09:25:42.0(Timestamp), FALSE(String), 2018-02-27 09:25:34.0(Timestamp), 2018-02-27 09:27:44.0(Timestamp), TRUE(String), 3441(Integer)
BsProductMapper.updateByPrimaryKeySelective - <==    Updates: 1

BsSubAccountMapper.selectByExample - ==>  Preparing: select id, account_id, code, product_id, product_type, product_code, product_name, product_rate, bank_card, extra_rate, open_balance, balance, available_balance, can_withdraw, freeze_balance, trans_status, status, check_status, interest_begin_date, last_trans_date, last_cal__interest_date, last_finish_interest_date, accumulation_inerest, open_time, close_time, transfer_time, note from bs_sub_account WHERE ( account_id = ? and status not in ( ? , ? ) and product_type in ( ? , ? , ? , ? , ? ) ) 
BsSubAccountMapper.selectByExample - ==> Parameters: 37343(Integer), 1(Integer), 6(Integer), REG(String), AUTH(String), AUTH_YUN(String), AUTH_ZSD(String), AUTH_7(String)
BsSubAccountMapper.selectByExample - <==      Total: 19

BsAccountJnlMapper.insertSelective - ==>  Preparing: insert into bs_account_jnl ( trans_time, trans_code, trans_name, trans_amount, sys_time, cd_flag1, user_id1, account_id1, sub_account_id1, sub_account_code1, before_balance1, after_balance1, before_avialable_balance1, after_avialable_balance1, before_freeze_balance1, after_freeze_balance1, cd_flag2, user_id2, account_id2, sub_account_id2, before_balance2, after_balance2, before_avialable_balance2, after_avialable_balance2, before_freeze_balance2, after_freeze_balance2, fee ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) 
BsAccountJnlMapper.insertSelective - ==> Parameters: 2018-05-02 15:56:50.526(Timestamp), BALANCE_BUY_PRODUCT(String), 余额购买(String), 100.0(Double), 2018-05-02 15:56:50.526(Timestamp), 2(Integer), 2000000(Integer), 37343(Integer), 200050(Integer), 40012000000201801181529399911(String), 803.0(Double), 703.0(Double), 803.0(Double), 703.0(Double), 0.0(Double), 0.0(Double), 1(Integer), 2000000(Integer), 37343(Integer), 201072(Integer), 0.0(Double), 100.0(Double), 0.0(Double), 100.0(Double), 0.0(Double), 0.0(Double), 0.0(Double)
BsAccountJnlMapper.insertSelective - <==    Updates: 1

BsSysAccountJnlMapper.insertSelective - ==>  Preparing: insert into bs_sys_account_jnl ( trans_time, trans_code, trans_name, trans_amount, sys_time, cd_flag1, sub_account_code1, before_balance1, after_balance1, before_avialable_balance1, after_avialable_balance1, before_freeze_balance1, after_freeze_balance1, cd_flag2, sub_account_code2, before_balance2, after_balance2, before_avialable_balance2, after_avialable_balance2, before_freeze_balance2, after_freeze_balance2, fee, status ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) 
BsSysAccountJnlMapper.insertSelective - ==> Parameters: 2018-05-02 15:56:50.595(Timestamp), USER_BALANCE_BUY(String), 用户购买(String), 100.0(Double), 2018-05-02 15:56:50.595(Timestamp), 2(Integer), BGW_USER(String), 4.273204727E7(Double), 4.273194727E7(Double), 4.857959828E7(Double), 4.857949828E7(Double), -5847387.15(Double), -5847387.15(Double), 1(Integer), BGW_AUTH_YUN(String), 1.2956000738E8(Double), 1.2956010738E8(Double), 1.2956000738E8(Double), 1.2956010738E8(Double), 0.0(Double), 0.0(Double), 0.0(Double), 1(Integer)
BsSysAccountJnlMapper.insertSelective - <==    Updates: 1

BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000000(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1

BsSysConfigMapper.selectByPrimaryKey - ==>  Preparing: select conf_key, conf_value, name, note from bs_sys_config where conf_key = ? 
BsSysConfigMapper.selectByPrimaryKey - ==> Parameters: REGISTDATE_SEPARATE_4_BONUS(String)
BsSysConfigMapper.selectByPrimaryKey - <==      Total: 1

BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000000(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1

BsActivityMapper.selectByPrimaryKey - ==>  Preparing: select id, name, start_time, end_time, note, create_time, update_time from bs_activity where id = ? 
BsActivityMapper.selectByPrimaryKey - ==> Parameters: 31(Integer)
BsActivityMapper.selectByPrimaryKey - <==      Total: 1

BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000011(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1

BsSubAccountMapper.selectByPrimaryKey - ==>  Preparing: select id, account_id, code, product_id, product_type, product_code, product_name, product_rate, bank_card, extra_rate, open_balance, balance, available_balance, can_withdraw, freeze_balance, trans_status, status, check_status, interest_begin_date, last_trans_date, last_cal__interest_date, last_finish_interest_date, accumulation_inerest, open_time, close_time, transfer_time, note from bs_sub_account where id = ? 
BsSubAccountMapper.selectByPrimaryKey - ==> Parameters: 201072(Integer)
BsSubAccountMapper.selectByPrimaryKey - <==      Total: 1

BsAutoRedPacketRuleMapper.selectByExample - ==>  Preparing: select id, serial_no, agent_ids, trigger_type, valid_term_type, status, trigger_amount_start, trigger_amount_end, trigger_invite_num, distribute_time_start, distribute_time_end, available_days, create_time, update_time from bs_auto_red_packet_rule WHERE ( trigger_type = ? and status = ? and distribute_time_start <= ? and distribute_time_end >= ? and trigger_amount_start <= ? and trigger_amount_end >= ? ) 
BsAutoRedPacketRuleMapper.selectByExample - ==> Parameters: BUY_FULL(String), AVAILABLE(String), 2018-05-02 15:56:50.718(Timestamp), 2018-05-02 15:56:50.718(Timestamp), 100.0(Double), 100.0(Double)
BsAutoRedPacketRuleMapper.selectByExample - <==      Total: 0

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3441(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1

BsWxUserInfoMapper.selectByExample - ==>  Preparing: select id, open_id, user_id, create_time, update_time from bs_wx_user_info WHERE ( user_id = ? ) 
BsWxUserInfoMapper.selectByExample - ==> Parameters: 2000000(Integer)
BsWxUserInfoMapper.selectByExample - <==      Total: 0

BsSysConfigMapper.selectByPrimaryKey - ==>  Preparing: select conf_key, conf_value, name, note from bs_sys_config where conf_key = ? 
BsSysConfigMapper.selectByPrimaryKey - ==> Parameters: BONUS_RATE_4_REFERRER_3MONTH_NEW(String)
BsSysConfigMapper.selectByPrimaryKey - <==      Total: 1
BsSysConfigMapper.selectByPrimaryKey - ==>  Preparing: select conf_key, conf_value, name, note from bs_sys_config where conf_key = ? 
BsSysConfigMapper.selectByPrimaryKey - ==> Parameters: BONUS_RATE_4_SELF_3MONTH_NEW(String)
BsSysConfigMapper.selectByPrimaryKey - <==      Total: 1

BsBonusGrantPlanMapper.insertSelective - ==>  Preparing: insert into bs_bonus_grant_plan ( user_id, be_recommend_user_id, sub_account_id, serial_no, amount, grant_type, grant_date, status, create_time, update_time ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) 
BsBonusGrantPlanMapper.insertSelective - ==> Parameters: 2000011(Integer), 2000000(Integer), 201072(Integer), 1(Integer), 0.04(Double), EACH_TAKE_PART(String), 2018-05-02(Date), INIT(String), 2018-05-02 15:56:50.843(Timestamp), 2018-05-02 15:56:50.843(Timestamp)
BsBonusGrantPlanMapper.insertSelective - <==    Updates: 1

BsUserCustomerManagerMapper.selectByExample - ==>  Preparing: select id, customer_manager_dafy_id, user_id, grade, create_time, update_time from bs_user_customer_manager WHERE ( user_id = ? and grade = ? ) 
BsUserCustomerManagerMapper.selectByExample - ==> Parameters: 2000000(Integer), 1(Integer)
BsUserCustomerManagerMapper.selectByExample - <==      Total: 0

BsActivityMapper.selectByPrimaryKey - ==>  Preparing: select id, name, start_time, end_time, note, create_time, update_time from bs_activity where id = ? 
BsActivityMapper.selectByPrimaryKey - ==> Parameters: 29(Integer)
BsActivityMapper.selectByPrimaryKey - <==      Total: 1

BsBonusGrantPlanMapper.insertSelective - <==    Updates: 1
BsBonusGrantPlanMapper.insertSelective - ==>  Preparing: insert into bs_bonus_grant_plan ( user_id, be_recommend_user_id, sub_account_id, serial_no, amount, grant_type, grant_date, status, create_time, update_time ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) 
BsBonusGrantPlanMapper.insertSelective - ==> Parameters: 2000000(Integer), 2000000(Integer), 201072(Integer), 1(Integer), 0.04(Double), EACH_TAKE_PART(String), 2018-05-02(Date), INIT(String), 2018-05-02 15:56:50.991(Timestamp), 2018-05-02 15:56:50.991(Timestamp)

BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000000(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1

BsUserMapper.selectByPKForLock - ==>  Preparing: select * from bs_user where id = ? for update 
BsUserMapper.selectByPKForLock - ==> Parameters: 2000011(Integer)
BsUserMapper.selectByPKForLock - <==      Total: 1

BsActivityMapper.selectByPrimaryKey - ==>  Preparing: select id, name, start_time, end_time, note, create_time, update_time from bs_activity where id = ? 
BsActivityMapper.selectByPrimaryKey - ==> Parameters: 2(Integer)

BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000000(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1

BsUserMapper.updateByPrimaryKeySelective - ==>  Preparing: update bs_user SET current_bonus = ?, total_bonus = ? where id = ? 
BsUserMapper.updateByPrimaryKeySelective - ==> Parameters: 90.11(Double), 190.43(Double), 2000011(Integer)
BsUserMapper.updateByPrimaryKeySelective - <==    Updates: 1

BsSubAccountMapper.selectSingleSubActByUserAndType - ==>  Preparing: SELECT s.* FROM bs_account a, bs_sub_account s WHERE a.user_id = ? AND a.id = s.account_id AND s.product_type = ? 
BsSubAccountMapper.selectSingleSubActByUserAndType - ==> Parameters: 2000011(Integer), JLJ(String)
BsSubAccountMapper.selectSingleSubActByUserAndType - <==      Total: 1

BsSubAccountMapper.selectSubAccountByIdForLock - ==>  Preparing: SELECT s.* FROM bs_sub_account s WHERE s.id = ? FOR UPDATE 
BsSubAccountMapper.selectSubAccountByIdForLock - ==> Parameters: 200203(Integer)
BsSubAccountMapper.selectSubAccountByIdForLock - <==      Total: 1

BsSubAccountMapper.updateByPrimaryKeySelective - ==>  Preparing: update bs_sub_account SET balance = ?, available_balance = ?, can_withdraw = ? where id = ? 
BsSubAccountMapper.updateByPrimaryKeySelective - ==> Parameters: 90.11(Double), 90.11(Double), 90.11(Double), 200203(Integer)
BsSubAccountMapper.updateByPrimaryKeySelective - <==    Updates: 1

BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsPropertyInfoMapper.checkPropertySymbol - ==>  Preparing: SELECT a.property_symbol FROM bs_property_info a, bs_product b, bs_sub_account c WHERE a.id = b.property_id AND b.id = c.product_id AND c.id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000000(Integer)
BsPropertyInfoMapper.checkPropertySymbol - ==> Parameters: 201072(Integer)
BsPropertyInfoMapper.checkPropertySymbol - <==      Total: 1
BsUserMapper.selectByPrimaryKey - <==      Total: 1

BsAccountJnlMapper.insertSelective - ==>  Preparing: insert into bs_account_jnl ( trans_time, trans_code, trans_name, trans_amount, sys_time, cd_flag1, user_id1, cd_flag2, user_id2, account_id2, sub_account_id2, sub_account_code2, before_balance2, after_balance2, before_avialable_balance2, after_avialable_balance2, before_freeze_balance2, after_freeze_balance2, fee ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) 
BsAccountJnlMapper.insertSelective - ==> Parameters: 2018-05-02 15:56:51.209(Timestamp), RECOMMEND_BONUS(String), 获得推荐奖励(String), 0.04(Double), 2018-05-02 15:56:51.209(Timestamp), 2(Integer), 2000011(Integer), 1(Integer), 2000011(Integer), 37354(Integer), 200203(Integer), 30012000011201801222017206139(String), 90.07(Double), 90.11(Double), 90.07(Double), 90.11(Double), 0.0(Double), 0.0(Double), 0.0(Double)
BsAccountJnlMapper.insertSelective - <==    Updates: 1

BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000000(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1

BsDailyBonusMapper.insertSelective - ==>  Preparing: insert into bs_daily_bonus ( user_id, be_recommend_user_id, sub_account_id, bonus, time, type ) values ( ?, ?, ?, ?, ?, ? ) 
BsDailyBonusMapper.insertSelective - ==> Parameters: 2000011(Integer), 2000000(Integer), 201072(Integer), 0.04(Double), 2018-05-02 15:56:51.248(Timestamp), RECOMMEND(String)
BsDailyBonusMapper.insertSelective - <==    Updates: 1

BsUserMainOperationMapper.insertSelective - ==>  Preparing: insert into bs_user_main_operation ( user_id, user_mobile, src_ip, src_agent, url, create_time ) values ( ?, ?, ?, ?, ?, ? ) 
BsUserMainOperationMapper.insertSelective - ==> Parameters: 2000000(Integer), 15558017627(String), 192.168.4.196(String), %E5%B8%81%E6%B8%AF%E6%B9%BE/2.5.7 CFNetwork/897.15 Darwin/17.5.0(String), http://192.168.3.82:8083/gateway/remote/mobile/product/balanceBuy/v_1.0.1(String), 2018-05-02 15:56:51.256(Timestamp)
BsUserMainOperationMapper.insertSelective - <==    Updates: 1

BsBonusGrantPlanMapper.updateByPrimaryKeySelective - ==>  Preparing: update bs_bonus_grant_plan SET finish_date = ?, status = ?, update_time = ? where id = ? 
BsBonusGrantPlanMapper.updateByPrimaryKeySelective - ==> Parameters: 2018-05-02 15:56:51.28(Timestamp), SUCC(String), 2018-05-02 15:56:51.28(Timestamp), 303018(Integer)
BsBonusGrantPlanMapper.updateByPrimaryKeySelective - <==    Updates: 1

BsWxUserInfoMapper.selectByExample - ==>  Preparing: select id, open_id, user_id, create_time, update_time from bs_wx_user_info WHERE ( user_id = ? ) 
BsWxUserInfoMapper.selectByExample - ==> Parameters: 2000011(Integer)
BsWxUserInfoMapper.selectByExample - <==      Total: 0

BsUserMapper.selectByPKForLock - ==>  Preparing: select * from bs_user where id = ? for update 
BsUserMapper.selectByPKForLock - ==> Parameters: 2000000(Integer)
BsUserMapper.selectByPKForLock - <==      Total: 1

BsUserMapper.updateByPrimaryKeySelective - ==>  Preparing: update bs_user SET current_bonus = ?, total_bonus = ? where id = ? 
BsUserMapper.updateByPrimaryKeySelective - ==> Parameters: 11.38(Double), 225.0(Double), 2000000(Integer)
BsUserMapper.updateByPrimaryKeySelective - <==    Updates: 1

BsSubAccountMapper.selectSingleSubActByUserAndType - ==>  Preparing: SELECT s.* FROM bs_account a, bs_sub_account s WHERE a.user_id = ? AND a.id = s.account_id AND s.product_type = ? 
BsSubAccountMapper.selectSingleSubActByUserAndType - ==> Parameters: 2000000(Integer), JLJ(String)
BsSubAccountMapper.selectSingleSubActByUserAndType - <==      Total: 1

BsSubAccountMapper.selectSubAccountByIdForLock - ==>  Preparing: SELECT s.* FROM bs_sub_account s WHERE s.id = ? FOR UPDATE 
BsSubAccountMapper.selectSubAccountByIdForLock - ==> Parameters: 200049(Integer)
BsSubAccountMapper.selectSubAccountByIdForLock - <==      Total: 1

BsSubAccountMapper.updateByPrimaryKeySelective - ==>  Preparing: update bs_sub_account SET balance = ?, available_balance = ?, can_withdraw = ? where id = ? 
BsSubAccountMapper.updateByPrimaryKeySelective - ==> Parameters: 11.38(Double), 11.38(Double), 11.38(Double), 200049(Integer)
BsSubAccountMapper.updateByPrimaryKeySelective - <==    Updates: 1

BsAccountJnlMapper.insertSelective - ==>  Preparing: insert into bs_account_jnl ( trans_time, trans_code, trans_name, trans_amount, sys_time, cd_flag1, user_id1, cd_flag2, user_id2, account_id2, sub_account_id2, sub_account_code2, before_balance2, after_balance2, before_avialable_balance2, after_avialable_balance2, before_freeze_balance2, after_freeze_balance2, fee ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) 
BsAccountJnlMapper.insertSelective - ==> Parameters: 2018-05-02 15:56:51.421(Timestamp), RECOMMEND_BONUS(String), 获得推荐奖励(String), 0.04(Double), 2018-05-02 15:56:51.421(Timestamp), 2(Integer), 2000000(Integer), 1(Integer), 2000000(Integer), 37343(Integer), 200049(Integer), 30012000000201801181529393993(String), 11.34(Double), 11.38(Double), 11.34(Double), 11.38(Double), 0.0(Double), 0.0(Double), 0.0(Double)
BsAccountJnlMapper.insertSelective - <==    Updates: 1
BsDailyBonusMapper.insertSelective - ==>  Preparing: insert into bs_daily_bonus ( user_id, be_recommend_user_id, sub_account_id, bonus, time, type ) values ( ?, ?, ?, ?, ?, ? ) 
BsDailyBonusMapper.insertSelective - ==> Parameters: 2000000(Integer), 2000000(Integer), 201072(Integer), 0.04(Double), 2018-05-02 15:56:51.453(Timestamp), RECOMMEND(String)
BsDailyBonusMapper.insertSelective - <==    Updates: 1

BsBonusGrantPlanMapper.updateByPrimaryKeySelective - ==>  Preparing: update bs_bonus_grant_plan SET finish_date = ?, status = ?, update_time = ? where id = ? 
BsBonusGrantPlanMapper.updateByPrimaryKeySelective - ==> Parameters: 2018-05-02 15:56:51.471(Timestamp), SUCC(String), 2018-05-02 15:56:51.471(Timestamp), 303019(Integer)
BsBonusGrantPlanMapper.updateByPrimaryKeySelective - <==    Updates: 1

BsWxUserInfoMapper.selectByExample - ==>  Preparing: select id, open_id, user_id, create_time, update_time from bs_wx_user_info WHERE ( user_id = ? ) 
BsWxUserInfoMapper.selectByExample - ==> Parameters: 2000000(Integer)
BsWxUserInfoMapper.selectByExample - <==      Total: 0

BsBonusGrantPlanMapper.insertSelective - ==>  Preparing: insert into bs_bonus_grant_plan ( user_id, be_recommend_user_id, sub_account_id, serial_no, amount, grant_type, grant_date, status, create_time, update_time ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) 
BsBonusGrantPlanMapper.insertSelective - ==> Parameters: 2000011(Integer), 2000000(Integer), 201072(Integer), 2(Integer), 0.04(Double), EACH_TAKE_PART(String), 2018-06-01(Date), INIT(String), 2018-05-02 15:56:51.544(Timestamp), 2018-05-02 15:56:51.544(Timestamp)
BsBonusGrantPlanMapper.insertSelective - <==    Updates: 1
BsBonusGrantPlanMapper.insertSelective - ==>  Preparing: insert into bs_bonus_grant_plan ( user_id, be_recommend_user_id, sub_account_id, serial_no, amount, grant_type, grant_date, status, create_time, update_time ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) 
BsBonusGrantPlanMapper.insertSelective - ==> Parameters: 2000000(Integer), 2000000(Integer), 201072(Integer), 2(Integer), 0.04(Double), EACH_TAKE_PART(String), 2018-06-01(Date), INIT(String), 2018-05-02 15:56:52.073(Timestamp), 2018-05-02 15:56:52.073(Timestamp)
BsBonusGrantPlanMapper.insertSelective - <==    Updates: 1
BsBonusGrantPlanMapper.insertSelective - ==>  Preparing: insert into bs_bonus_grant_plan ( user_id, be_recommend_user_id, sub_account_id, serial_no, amount, grant_type, grant_date, status, create_time, update_time ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) 
BsBonusGrantPlanMapper.insertSelective - ==> Parameters: 2000011(Integer), 2000000(Integer), 201072(Integer), 3(Integer), 0.05(Double), EACH_TAKE_PART(String), 2018-07-01(Date), INIT(String), 2018-05-02 15:56:52.097(Timestamp), 2018-05-02 15:56:52.097(Timestamp)
BsBonusGrantPlanMapper.insertSelective - <==    Updates: 1
BsBonusGrantPlanMapper.insertSelective - ==>  Preparing: insert into bs_bonus_grant_plan ( user_id, be_recommend_user_id, sub_account_id, serial_no, amount, grant_type, grant_date, status, create_time, update_time ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) 
BsBonusGrantPlanMapper.insertSelective - ==> Parameters: 2000000(Integer), 2000000(Integer), 201072(Integer), 3(Integer), 0.05(Double), EACH_TAKE_PART(String), 2018-07-01(Date), INIT(String), 2018-05-02 15:56:52.118(Timestamp), 2018-05-02 15:56:52.118(Timestamp)
BsBonusGrantPlanMapper.insertSelective - <==    Updates: 1
