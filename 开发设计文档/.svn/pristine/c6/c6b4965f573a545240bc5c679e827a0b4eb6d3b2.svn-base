-- 一级列表
BsActiveUserRecordMapper.selectByRecord - ==>  Preparing: select id, user_id, terminal_type, login_date, src_url, create_time, update_time from bs_active_user_record where user_id = ? and terminal_type = ? and login_date = ? 
BsActiveUserRecordMapper.selectByRecord - ==> Parameters: 2000000(Integer), APP(String), 2018-05-02(Date)
BsActiveUserRecordMapper.selectByRecord - <==      Total: 1

BsPayOrdersMapper.countByExample - ==>  Preparing: select count(*) from bs_pay_orders WHERE ( status = ? and trans_type in ( ? , ? ) and user_id = ? ) 
BsPayOrdersMapper.countByExample - ==> Parameters: 6(Integer), CARD_BUY_PRODUCT(String), BALANCE_BUY_PRODUCT(String), 2000000(Integer)
BsPayOrdersMapper.countByExample - <==      Total: 1

BsProductMapper.selectActivityAndNoviceProduct - ==>  Preparing: (SELECT a.* FROM bs_product a, bs_user_type_auth u WHERE a.`status` IN (5,6) AND FIND_IN_SET(?, a.show_terminal) AND u.product_id = a.id AND a.activity_type = ? ORDER BY a.is_suggest DESC, field(a.`status`,'6','5'), a.update_time DESC LIMIT 1) UNION (SELECT a.* FROM bs_product a, bs_user_type_auth u WHERE a.`status` = 7 AND FIND_IN_SET(?, a.show_terminal) AND u.product_id = a.id AND a.activity_type = ? ORDER BY a.is_suggest DESC, field(a.`status`,'7'), a.update_time DESC LIMIT 1) LIMIT 1 
BsProductMapper.selectActivityAndNoviceProduct - ==> Parameters: APP(String), ACTIVITY(String), APP(String), ACTIVITY(String)
BsProductMapper.selectActivityAndNoviceProduct - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 6(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectActivityAndNoviceProduct - ==>  Preparing: (SELECT a.* FROM bs_product a, bs_user_type_auth u WHERE a.`status` IN (5,6) AND FIND_IN_SET(?, a.show_terminal) AND u.product_id = a.id AND a.activity_type = ? ORDER BY a.is_suggest DESC, field(a.`status`,'6','5'), a.update_time DESC LIMIT 1) UNION (SELECT a.* FROM bs_product a, bs_user_type_auth u WHERE a.`status` = 7 AND FIND_IN_SET(?, a.show_terminal) AND u.product_id = a.id AND a.activity_type = ? ORDER BY a.is_suggest DESC, field(a.`status`,'7'), a.update_time DESC LIMIT 1) LIMIT 1 
BsProductMapper.selectActivityAndNoviceProduct - ==> Parameters: APP(String), NEW_BUYER(String), APP(String), NEW_BUYER(String)
BsProductMapper.selectActivityAndNoviceProduct - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 1(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectProductListCount - ==>  Preparing: SELECT count(*) FROM bs_product p, bs_user_type_auth u WHERE u.product_id = p.id AND p.return_type = ? AND p.status IN (5, 6) AND FIND_IN_SET(?, p.show_terminal) 
BsProductMapper.selectProductListCount - ==> Parameters: FINISH_RETURN_ALL(String), APP(String)
BsProductMapper.selectProductListCount - <==      Total: 1
BsProductMapper.selectProductList - ==>  Preparing: SELECT p.* FROM bs_product p, bs_user_type_auth u WHERE u.product_id = p.id AND p.return_type = ? AND p.status IN (5, 6) AND FIND_IN_SET(?, p.show_terminal) ORDER BY p.is_suggest DESC, p.`status` DESC, p.update_time DESC, p.id DESC LIMIT ?,? 
BsProductMapper.selectProductList - ==> Parameters: FINISH_RETURN_ALL(String), APP(String), 0(Integer), 24(Integer)
BsProductMapper.selectProductList - <==      Total: 24

BsProductMapper.selectProductListCount - ==>  Preparing: SELECT count(*) FROM bs_product p, bs_user_type_auth u WHERE u.product_id = p.id AND p.return_type = ? AND p.status IN (5, 6) AND FIND_IN_SET(?, p.show_terminal) 
BsProductMapper.selectProductListCount - ==> Parameters: AVERAGE_CAPITAL_PLUS_INTEREST(String), APP(String)
BsProductMapper.selectProductListCount - <==      Total: 1
BsProductMapper.selectProductList - ==>  Preparing: SELECT p.* FROM bs_product p, bs_user_type_auth u WHERE u.product_id = p.id AND p.return_type = ? AND p.status IN (5, 6) AND FIND_IN_SET(?, p.show_terminal) ORDER BY p.is_suggest DESC, p.`status` DESC, p.update_time DESC, p.id DESC LIMIT ?,? 
BsProductMapper.selectProductList - ==> Parameters: AVERAGE_CAPITAL_PLUS_INTEREST(String), APP(String), 0(Integer), 3(Integer)
BsProductMapper.selectProductList - <==      Total: 3



-- 二级列表
-- 产品列表个数【加缓存，默认全部，重载方法，sql需要优化，购买成功时清除】
BsProductMapper.selectProductListCount - ==>  Preparing: SELECT count(*) FROM bs_product p, bs_user_type_auth u WHERE u.product_id = p.id AND p.return_type = ? AND p.status IN (5, 6) AND FIND_IN_SET(?, p.show_terminal) 
BsProductMapper.selectProductListCount - ==> Parameters: FINISH_RETURN_ALL(String), APP(String)
BsProductMapper.selectProductListCount - <==      Total: 1

-- 产品列表【加缓存，默认全部，重载方法，sql需要优化，购买成功时清除】
BsProductMapper.selectProductList - ==>  Preparing: SELECT p.* FROM bs_product p, bs_user_type_auth u WHERE u.product_id = p.id AND p.return_type = ? AND p.status IN (5, 6) AND FIND_IN_SET(?, p.show_terminal) ORDER BY p.is_suggest DESC, p.`status` DESC, p.update_time DESC, p.id DESC LIMIT ?,? 
BsProductMapper.selectProductList - ==> Parameters: FINISH_RETURN_ALL(String), APP(String), 0(Integer), 24(Integer)
BsProductMapper.selectProductList - <==      Total: 24

-- 查询产品信息
BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3441(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
-- 查询资产方
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3458(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3455(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 1(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3451(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 6(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3448(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 6(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3447(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3446(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3444(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3445(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3443(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3442(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3440(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3422(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3423(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 6(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3419(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 6(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3421(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 6(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3420(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3416(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 6(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3417(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 6(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3418(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 6(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3414(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3415(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3413(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3412(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1
