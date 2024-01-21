-- 【待定，看看】
BsActiveUserRecordMapper.selectByRecord - ==>  Preparing: select id, user_id, terminal_type, login_date, src_url, create_time, update_time from bs_active_user_record where user_id = ? and terminal_type = ? and login_date = ? 
BsActiveUserRecordMapper.selectByRecord - ==> Parameters: 2000000(Integer), APP(String), 2018-05-02(Date)
BsActiveUserRecordMapper.selectByRecord - <==      Total: 1

-- 查询产品信息
BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3441(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1

-- 【待定，看看】
BsActiveUserRecordMapper.selectByRecord - ==>  Preparing: select id, user_id, terminal_type, login_date, src_url, create_time, update_time from bs_active_user_record where user_id = ? and terminal_type = ? and login_date = ? 
BsActiveUserRecordMapper.selectByRecord - ==> Parameters: 2000000(Integer), APP(String), 2018-05-02(Date)
BsActiveUserRecordMapper.selectByRecord - <==      Total: 1

BsUserMapper.selectUserAssetByUserId - ==>  Preparing: select c.*, a.id as account_Id, d.status dafyStatus, SUM( CASE WHEN b.product_type = 'AUTH_YUN' THEN b.open_balance WHEN b.product_type = 'RED' THEN b.open_balance WHEN b.product_type = 'AUTH_ZSD' THEN b.open_balance WHEN b.product_type = 'RED_ZSD' THEN b.open_balance WHEN b.product_type = 'AUTH_7' THEN b.open_balance WHEN b.product_type = 'RED_7' THEN b.open_balance ELSE b.balance END ) as total_balance, (select count(*) from bs_sub_account sa where sa.account_id = b.account_id and sa.status in (2,7) AND (sa.product_type='AUTH' OR sa.product_type='REG' or sa.product_type = 'AUTH_YUN' or sa.product_type = 'AUTH_ZSD' or sa.product_type = 'AUTH_7')) investNum, (select count(*) from bs_bank_card k where k.user_id = c.id and k.`status` = '1' ) bankcard_count from bs_account a, bs_user c left join dafy_user_ext d on c.id = d.user_id ,bs_sub_account b where c.id=a.user_id and a.id=b.account_id and c.id=? and ( (b.status=1 and b.product_type = 'JSH') or (b.status=1 and b.product_type = 'JLJ') or ((b.status=1 and b.product_type = 'DEP_JSH')) or ((b.status=2 or b.status=3 or b.status=7) and b.product_type = 'REG') or ((b.status=2 or b.status=3 or b.status=4) and b.product_type = 'REG_D') or ((b.status=2 or b.status=3 or b.status=4) and b.product_type = 'AUTH') or ((b.status=2 or b.status=3 or b.status=7) and b.product_type = 'AUTH_YUN') or ((b.status=2 or b.status=3 or b.status=7) and b.product_type = 'RED') or ((b.status=2 or b.status=3 or b.status=7) and b.product_type = 'RED_ZSD') or ((b.status=2 or b.status=3 or b.status=7) and b.product_type = 'RED_7') or ((b.status=2 or b.status=3 or b.status=7) and b.product_type = 'AUTH_ZSD') or ((b.status=2 or b.status=3 or b.status=7) and b.product_type = 'AUTH_7') ) 
BsUserMapper.selectUserAssetByUserId - ==> Parameters: 2000000(Integer)
BsUserMapper.selectUserAssetByUserId - <==      Total: 1

BsSubAccountMapper.selectInvestProportionList - ==>  Preparing: SELECT tt.product_type,tt.invest_amount,tt.invest_num, CASE WHEN ROUND(ROUND(tt.invest_amount/st.invest_amount,4)*100,2) is null THEN 0 ELSE ROUND(ROUND(tt.invest_amount/st.invest_amount,4)*100,2) END as proportion_rate from ( select 'GW' as product_type, CASE WHEN sum(open_balance) is NULL THEN 0 ELSE sum(open_balance) END as invest_amount, COUNT( CASE WHEN bsa.product_type IN ('RED', 'RED_ZSD','RED_7') THEN NULL ELSE bsa.id END ) invest_num,u.id from bs_user u,bs_account ba,bs_sub_account bsa,bs_product bp where u.id = ? and u.id = ba.user_id and ba.id = bsa.account_id and bsa.product_id = bp.id and bsa.`status` in (2,7) and bsa.product_type in ('REG', 'AUTH_YUN', 'RED', 'AUTH_ZSD', 'RED_ZSD','AUTH_7', 'RED_7') and bsa.open_balance > 0 UNION select 'ZAN' as product_type, ( SELECT( (SELECT CASE WHEN SUM(a.open_balance) IS NULL THEN 0 ELSE SUM(a.open_balance) END FROM bs_sub_account a, bs_account b WHERE a.account_id = b.id AND a.product_type = 'AUTH' AND a.`status` NOT IN (1, 6) AND b.user_id = ? ) - (SELECT CASE WHEN SUM(a.amount) IS NULL THEN 0 ELSE SUM(a.amount) END FROM bs_standby_return a WHERE a.user_id = ? ) - ( SELECT CASE WHEN SUM(d.principal) IS NULL THEN 0 ELSE SUM(d.principal) END FROM ln_loan_relation a ,ln_finance_repay_schedule b, bs_sub_account c, bs_loan_finance_repay d WHERE a.id = b.relation_id AND c.id = a.bs_sub_account_id AND c.product_type IN ('REG_D', 'AUTH') AND d.finance_repay_schedule_id = b.id AND d.`status` = 'REPAIED' AND a.bs_user_id = ? ) - ( select CASE WHEN sum(lc.in_amount-lc.amount) IS NULL THEN 0 ELSE sum(lc.in_amount-lc.amount) END from ln_finance_repay_schedule a ,ln_credit_transfer lc, bs_sub_account auth where repay_serial = 1 and a.`status` = 'REPAIED' and lc.in_loan_relation_id = relation_id AND lc.in_user_id = ? AND auth.id = lc.in_sub_account_id AND auth.product_type = 'REG_D' ) ) as invest_amount ), ( SELECT COUNT(auth.id) FROM bs_sub_account auth, bs_sub_account regd, bs_sub_account_pair pair, bs_product p, bs_account b WHERE auth.product_type = 'AUTH' AND regd.product_type = 'REG_D' AND pair.auth_account_id = auth.id AND pair.reg_d_account_id = regd.id AND auth.`status` NOT IN (1, 6) AND p.id = auth.product_id AND auth.account_id = b.id AND b.user_id = ? AND ( ( regd.`status` NOT IN (1, 5, 6, 7) AND DATEDIFF(NOW(),ADDDATE(auth.interest_begin_date, (SELECT config.conf_value FROM bs_sys_config config WHERE config.conf_key = 'DAY_4_WAIT_LOAN'))) >= 0 AND regd.balance > 0 ) OR ( regd.`status` NOT IN (1, 6) AND DATEDIFF(NOW(),ADDDATE(auth.interest_begin_date, (SELECT config.conf_value FROM bs_sys_config config WHERE config.conf_key = 'DAY_4_WAIT_LOAN'))) < 0 ) ) ) invest_num,u.id from bs_user u,bs_account ba,bs_sub_account bsa,bs_product bp, bs_sub_account bsa1,bs_sub_account_pair bsap where u.id = ? and u.id = ba.user_id and ba.id = bsa.account_id and bsa.product_id = bp.id and bsa.`status` in (2,7) and bsa.product_type ='AUTH' and bsa.id = bsap.auth_account_id and bsap.reg_d_account_id = bsa1.id and bsa1.`status` in (2,7) AND DATEDIFF(NOW(),ADDDATE(bsa.interest_begin_date, (SELECT config.conf_value FROM bs_sys_config config WHERE config.conf_key = 'DAY_4_WAIT_LOAN'))) != 0 ) as tt,( SELECT( (SELECT CASE WHEN SUM(a.open_balance) IS NULL THEN 0 ELSE SUM(a.open_balance) END FROM bs_sub_account a, bs_account b WHERE a.account_id = b.id AND a.product_type = 'AUTH' AND a.`status` NOT IN (1, 6) AND b.user_id = ? ) - (SELECT CASE WHEN SUM(a.amount) IS NULL THEN 0 ELSE SUM(a.amount) END FROM bs_standby_return a WHERE a.user_id = ? ) - ( SELECT CASE WHEN SUM(d.principal) IS NULL THEN 0 ELSE SUM(d.principal) END FROM ln_loan_relation a ,ln_finance_repay_schedule b, bs_sub_account c, bs_loan_finance_repay d WHERE a.id = b.relation_id AND c.id = a.bs_sub_account_id AND c.product_type IN ('REG_D', 'AUTH') AND d.finance_repay_schedule_id = b.id AND d.`status` = 'REPAIED' AND a.bs_user_id = ? ) - ( select CASE WHEN sum(lc.in_amount-lc.amount) IS NULL THEN 0 ELSE sum(lc.in_amount-lc.amount) END from ln_finance_repay_schedule a ,ln_credit_transfer lc, bs_sub_account auth where repay_serial = 1 and a.`status` = 'REPAIED' and lc.in_loan_relation_id = relation_id AND lc.in_user_id = ? AND auth.id = lc.in_sub_account_id AND auth.product_type = 'REG_D' ) + ( select CASE WHEN sum(open_balance) is NULL THEN 0 ELSE sum(open_balance) END from bs_user u,bs_account ba,bs_sub_account bsa,bs_product bp where u.id = ? and u.id = ba.user_id and ba.id = bsa.account_id and bsa.product_id = bp.id and bsa.`status` in (2,7) and bsa.product_type in ('REG', 'AUTH_7', 'RED_7', 'AUTH_YUN', 'RED', 'AUTH_ZSD', 'RED_ZSD') and bsa.open_balance > 0 ) ) as invest_amount)as st 
BsSubAccountMapper.selectInvestProportionList - ==> Parameters: 2000000(Integer), 2000000(Integer), 2000000(Integer), 2000000(Integer), 2000000(Integer), 2000000(Integer), 2000000(Integer), 2000000(Integer), 2000000(Integer), 2000000(Integer), 2000000(Integer), 2000000(Integer)
BsSubAccountMapper.selectInvestProportionList - <==      Total: 1

-- 查询DEP_JSH
BsSubAccountMapper.selectDEPJSHSubAccountByUserId - ==>  Preparing: select a.id, a.account_id, a.code, a.product_type, a.open_balance, a.balance, a.available_balance, a.can_withdraw, a.freeze_balance, a.trans_status, a.status, a.open_time, a.note from bs_sub_account a, bs_account b, bs_user c where a.account_id = b.id and b.user_id = c.id and c.id = ? and a.status = '1' and a.product_type = 'DEP_JSH' 
BsSubAccountMapper.selectDEPJSHSubAccountByUserId - ==> Parameters: 2000000(Integer)
BsSubAccountMapper.selectDEPJSHSubAccountByUserId - <==      Total: 1

-- 查询JSH
BsSubAccountMapper.selectJSHSubAccountByUserId - ==>  Preparing: select a.id, a.account_id, a.code, a.product_type, a.open_balance, a.balance, a.available_balance, a.can_withdraw, a.freeze_balance, a.trans_status, a.status, a.open_time, a.note from bs_sub_account a, bs_account b, bs_user c where a.account_id = b.id and b.user_id = c.id and c.id = ? and a.status = '1' and a.product_type = 'JSH' 
BsSubAccountMapper.selectJSHSubAccountByUserId - ==> Parameters: 2000000(Integer)
BsSubAccountMapper.selectJSHSubAccountByUserId - <==      Total: 1

-- 查询JLJ
BsSubAccountMapper.selectJLJSubAccountByUserId - ==>  Preparing: select a.id, a.account_id, a.code, a.product_type, a.open_balance, a.balance, a.available_balance, a.can_withdraw, a.freeze_balance, a.trans_status, a.status, a.open_time, a.note from bs_sub_account a, bs_account b, bs_user c where a.account_id = b.id and b.user_id = c.id and c.id = ? and a.status = '1' and a.product_type = 'JLJ' 
BsSubAccountMapper.selectJLJSubAccountByUserId - ==> Parameters: 2000000(Integer)
BsSubAccountMapper.selectJLJSubAccountByUserId - <==      Total: 1

BsDailyInterestMapper.selectByExample - ==>  Preparing: select id, user_id, interest, time, note from bs_daily_interest WHERE ( user_id = ? and time < ? and time > ? ) 
BsDailyInterestMapper.selectByExample - ==> Parameters: 2000000(Integer), 2018-05-03 00:00:00.0(Timestamp), 2018-05-02 00:00:00.0(Timestamp)
BsDailyInterestMapper.selectByExample - <==      Total: 0

BsSubAccountMapper.selectByExamplePage - ==>  Preparing: SELECT * FROM ( select b.id id,p.`name` as product_name,p.property_id, b.open_balance as balance, b.open_time openTime, b.interest_begin_date interestBeginDate, b.status, b.product_rate product_rate, p.id productId, p.term term,p.property_type propertyType, case when p.term = 12 then (365 - case when datediff(now(), b.interest_begin_date) < 0 then 0 else datediff(now(), b.interest_begin_date) end ) when p.term = -7 then (7 - case when datediff(now(), b.interest_begin_date) < 0 then 0 else datediff(now(), b.interest_begin_date) end ) else (p.term * 30 - case when datediff(now(), b.interest_begin_date) < 0 then 0 else datediff(now(), b.interest_begin_date) end ) end investDay, b.last_finish_interest_date investEndTime, c.amount AS red_amount, p.start_time,0 AS ticket_apr,0 AS interest_amount from bs_sub_account b ,bs_account a,bs_product p, bs_pay_orders o LEFT JOIN bs_red_packet_info c ON o.order_no = c.order_no AND c.`status` = 'USED' WHERE b.account_id = a.id and b.product_id = p.id and b.status != 1 and b.status != 6 and b.product_type IN ('REG') and (o.trans_type = 'CARD_BUY_PRODUCT' OR o.trans_type = 'BALANCE_BUY_PRODUCT') AND a.user_id=? and ( b.status = ? or b.status = 7 ) AND o.sub_account_id = b.id UNION select b.id id,p.`name` as product_name,p.property_id, b.open_balance + IFNULL(red.open_balance,0) as balance, b.open_time openTime, b.interest_begin_date interestBeginDate, b.status, b.product_rate product_rate, p.id productId, p.term term,p.property_type propertyType, case when p.term = 12 then (365 - case when datediff(now(), b.interest_begin_date) < 0 then 0 else datediff(now(), b.interest_begin_date) end ) when p.term = -7 then (7 - case when datediff(now(), b.interest_begin_date) < 0 then 0 else datediff(now(), b.interest_begin_date) end ) else (p.term * 30 - case when datediff(now(), b.interest_begin_date) < 0 then 0 else datediff(now(), b.interest_begin_date) end ) end investDay, b.last_finish_interest_date investEndTime, red.open_balance AS red_amount, p.start_time, ifnull(ti.ticket_apr, '0') AS ticket_apr, ifnull(ti.interest_amount, '0') AS interest_amount from bs_sub_account b LEFT JOIN bs_sub_account_pair pr ON pr.auth_account_id = b.id LEFT JOIN bs_sub_account red ON red.id = pr.red_account_id LEFT JOIN bs_interest_ticket_info ti ON b.id = ti.auth_account_id ,bs_account a,bs_product p WHERE b.account_id = a.id and b.product_id = p.id and b.status != 1 and b.status != 6 and b.product_type IN ('AUTH_YUN','AUTH_ZSD','AUTH_7') AND a.user_id=? and ( b.status = ? or b.status = 7 ) ) aa order by aa.investEndTime desc , aa.openTime desc, aa.id desc limit ?, ? 
BsSubAccountMapper.selectByExamplePage - ==> Parameters: 2000000(Integer), 2(Integer), 2000000(Integer), 2(Integer), 0(Integer), 2147483647(Integer)
BsSubAccountMapper.selectByExamplePage - <==      Total: 13

BsUserTransDetailMapper.countProcessingNoWithdraw - ==>  Preparing: select count(*) from (select DISTINCT(order_no) from bs_user_trans_detail where trans_status = 1 and trans_type != 'WITHDRAW' and trans_type != 'DEP_WITHDRAW' and trans_type != 'WITHDRAW_FEE' and trans_type != 'TOP_UP_FEE' and user_id = ? )t 
BsUserTransDetailMapper.countProcessingNoWithdraw - ==> Parameters: 2000000(Integer)
BsUserTransDetailMapper.countProcessingNoWithdraw - <==      Total: 1

BsUserTransDetailMapper.countProcessingWithdraw - ==>  Preparing: select count(*) from (select * from bs_user_trans_detail where (trans_status = '1' or trans_status = '4') and trans_type in ('WITHDRAW', 'DEP_WITHDRAW') and user_id = ? )t 
BsUserTransDetailMapper.countProcessingWithdraw - ==> Parameters: 2000000(Integer)
BsUserTransDetailMapper.countProcessingWithdraw - <==      Total: 1

BsUserTransDetailMapper.countByExample - ==>  Preparing: select count(*) from bs_user_trans_detail WHERE ( user_id = ? and trans_type = ? and trans_status = ? ) 
BsUserTransDetailMapper.countByExample - ==> Parameters: 2000000(Integer), BUY(String), 1(String)
BsUserTransDetailMapper.countByExample - <==      Total: 1

BsRedPacketInfoMapper.countByExample - ==>  Preparing: select count(*) from bs_red_packet_info WHERE ( user_id = ? and status = ? and use_time_end > ? ) 
BsRedPacketInfoMapper.countByExample - ==> Parameters: 2000000(Integer), INIT(String), 2018-05-02 15:55:54.56(Timestamp)
BsRedPacketInfoMapper.countByExample - <==      Total: 1

BsInterestTicketInfoMapper.countByExample - ==>  Preparing: select count(*) from bs_interest_ticket_info WHERE ( user_id = ? and status = ? and use_time_end >= ? and use_time_start <= ? ) 
BsInterestTicketInfoMapper.countByExample - ==> Parameters: 2000000(Integer), INIT(String), 2018-05-02 15:55:54.58(Timestamp), 2018-05-02 15:55:54.58(Timestamp)
BsInterestTicketInfoMapper.countByExample - <==      Total: 1

-- 查询DEP_JSH
BsSubAccountMapper.selectDEPJSHSubAccountByUserId - ==>  Preparing: select a.id, a.account_id, a.code, a.product_type, a.open_balance, a.balance, a.available_balance, a.can_withdraw, a.freeze_balance, a.trans_status, a.status, a.open_time, a.note from bs_sub_account a, bs_account b, bs_user c where a.account_id = b.id and b.user_id = c.id and c.id = ? and a.status = '1' and a.product_type = 'DEP_JSH' 
BsSubAccountMapper.selectDEPJSHSubAccountByUserId - ==> Parameters: 2000000(Integer)
BsSubAccountMapper.selectDEPJSHSubAccountByUserId - <==      Total: 1

-- 查询JSH
BsSubAccountMapper.selectJSHSubAccountByUserId - ==>  Preparing: select a.id, a.account_id, a.code, a.product_type, a.open_balance, a.balance, a.available_balance, a.can_withdraw, a.freeze_balance, a.trans_status, a.status, a.open_time, a.note from bs_sub_account a, bs_account b, bs_user c where a.account_id = b.id and b.user_id = c.id and c.id = ? and a.status = '1' and a.product_type = 'JSH' 
BsSubAccountMapper.selectJSHSubAccountByUserId - ==> Parameters: 2000000(Integer)
BsSubAccountMapper.selectJSHSubAccountByUserId - <==      Total: 1

-- 统计银行卡个数
BsBankCardMapper.countByExample - ==>  Preparing: select count(*) from bs_bank_card WHERE ( status in ( ? , ? ) and user_id = ? ) 
BsBankCardMapper.countByExample - ==> Parameters: 1(Integer), 5(Integer), 2000000(Integer)
BsBankCardMapper.countByExample - <==      Total: 1

-- 查询用户
BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000000(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1

-- 查询恒丰绑卡信息
BsHfbankUserExtMapper.selectByExample - ==>  Preparing: select id, user_id, hf_user_id, hf_regist_time, hf_bind_card_time, status, note, create_time, update_time from bs_hfbank_user_ext WHERE ( user_id = ? and status <> ? ) 
BsHfbankUserExtMapper.selectByExample - ==> Parameters: 2000000(Integer), CANCEL(String)
BsHfbankUserExtMapper.selectByExample - <==      Total: 1
BsHfbankUserExtMapper.selectByExample - ==>  Preparing: select id, user_id, hf_user_id, hf_regist_time, hf_bind_card_time, status, note, create_time, update_time from bs_hfbank_user_ext WHERE ( user_id = ? and status = ? ) 
BsHfbankUserExtMapper.selectByExample - ==> Parameters: 2000000(Integer), WAIT_ACTIVATE(String)
BsHfbankUserExtMapper.selectByExample - <==      Total: 0
BsHfbankUserExtMapper.selectByExample - ==>  Preparing: select id, user_id, hf_user_id, hf_regist_time, hf_bind_card_time, status, note, create_time, update_time from bs_hfbank_user_ext WHERE ( user_id = ? and status = ? ) 
BsHfbankUserExtMapper.selectByExample - ==> Parameters: 2000000(Integer), OPEN(String)
BsHfbankUserExtMapper.selectByExample - <==      Total: 1
BsQuestionnaireMapper.selectByExample - ==>  Preparing: select id, user_id, question_type, question_item, score, expire_days, expire_time, create_time, update_time from bs_questionnaire WHERE ( user_id = ? ) order by update_time desc 
BsQuestionnaireMapper.selectByExample - ==> Parameters: 2000000(Integer)
BsQuestionnaireMapper.selectByExample - <==      Total: 1

-- 查询用户
BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000000(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1

-- 【待定，看看】
BsActiveUserRecordMapper.selectByRecord - ==>  Preparing: select id, user_id, terminal_type, login_date, src_url, create_time, update_time from bs_active_user_record where user_id = ? and terminal_type = ? and login_date = ? 
BsActiveUserRecordMapper.selectByRecord - ==> Parameters: 2000000(Integer), APP(String), 2018-05-02(Date)
BsActiveUserRecordMapper.selectByRecord - <==      Total: 1
BsActiveUserRecordMapper.selectByRecord - ==>  Preparing: select id, user_id, terminal_type, login_date, src_url, create_time, update_time from bs_active_user_record where user_id = ? and terminal_type = ? and login_date = ? 
BsActiveUserRecordMapper.selectByRecord - ==> Parameters: 2000000(Integer), APP(String), 2018-05-02(Date)
BsActiveUserRecordMapper.selectByRecord - <==      Total: 1
BsActiveUserRecordMapper.selectByRecord - ==>  Preparing: select id, user_id, terminal_type, login_date, src_url, create_time, update_time from bs_active_user_record where user_id = ? and terminal_type = ? and login_date = ? 
BsActiveUserRecordMapper.selectByRecord - ==> Parameters: 2000000(Integer), APP(String), 2018-05-02(Date)
BsActiveUserRecordMapper.selectByRecord - <==      Total: 1
BsActiveUserRecordMapper.selectByRecord - ==>  Preparing: select id, user_id, terminal_type, login_date, src_url, create_time, update_time from bs_active_user_record where user_id = ? and terminal_type = ? and login_date = ? 
BsActiveUserRecordMapper.selectByRecord - ==> Parameters: 2000000(Integer), APP(String), 2018-05-02(Date)
BsActiveUserRecordMapper.selectByRecord - <==      Total: 1

BsPayOrdersMapper.countByExample - ==>  Preparing: select count(*) from bs_pay_orders WHERE ( status in ( ? , ? ) and trans_type in ( ? , ? ) and user_id = ? ) 
BsPayOrdersMapper.countByExample - ==> Parameters: 5(Integer), 6(Integer), CARD_BUY_PRODUCT(String), BALANCE_BUY_PRODUCT(String), 2000000(Integer)
BsPayOrdersMapper.countByExample - <==      Total: 1

-- 查询产品信息
BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3441(Integer)
BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3441(Integer)
BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3441(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsProductMapper.selectByPrimaryKey - <==      Total: 1

-- 查询加息券
BsInterestTicketInfoMapper.selectBuyTicketINITList - ==>  Preparing: select a.id, a.user_id, b.serial_name, d.invest_limit as full, concat('限', d.term_limit, '天产品使用') as term_limit, d.product_limit, d.ticket_apr as rate, a.use_time_start, a.use_time_end, a.`status`, e.term, e.is_support_incr_interest, 0 as interest from ( select a.id, a.serial_no, a.user_id,a.use_time_start, a.use_time_end, a.`status` from bs_interest_ticket_info a where a.`status` = 'INIT' and a.use_time_end >= NOW() AND a.use_time_start <= NOW() and a.user_id = ? ) as a, bs_ticket_grant_plan_check b, bs_interest_ticket_grant_attribute d, ( select e.id, e.activity_type, e.is_support_incr_interest, case when e.`name` like '%港湾%' then 'BIGANGWAN_SERIAL' when e.`name` like '%涌金%' then 'YONGJIN_SERIAL' when e.`name` like '%跨虹%' then 'KUAHONG_SERIAL' when e.`name` like '%保信%' then 'BAOXIN_SERIAL' end as `name`, case e.term when 1 then 30 when 3 then 90 when 6 then 180 when 12 then 365 end as term from bs_product e where e.id = ? and e.activity_type != 'NEW_BUYER' and e.is_support_incr_interest = 'TRUE' ) as e where a.serial_no = b.serial_no and a.serial_no = d.serial_no and find_in_set(e.term, d.term_limit) and find_in_set(e.`name`, d.product_limit) order by IFNULL(d.ticket_apr,0) * IFNULL(e.term,0)/ 36500 desc, a.use_time_end asc, a.id desc 
BsInterestTicketInfoMapper.selectBuyTicketINITList - ==> Parameters: 2000000(Integer), 3441(Integer)
BsInterestTicketInfoMapper.selectBuyTicketINITList - <==      Total: 0

BsSubAccountMapper.selectJSHSubAccountByUserId - ==>  Preparing: select a.id, a.account_id, a.code, a.product_type, a.open_balance, a.balance, a.available_balance, a.can_withdraw, a.freeze_balance, a.trans_status, a.status, a.open_time, a.note from bs_sub_account a, bs_account b, bs_user c where a.account_id = b.id and b.user_id = c.id and c.id = ? and a.status = '1' and a.product_type = 'JSH' 
BsSubAccountMapper.selectJSHSubAccountByUserId - ==> Parameters: 2000000(Integer)
BsSubAccountMapper.selectJSHSubAccountByUserId - <==      Total: 1

BsPayOrdersMapper.selectByExample - ==>  Preparing: select id, payment_channel_id, order_no, amount, user_id, sub_account_id, type, channel, pay_path, status, bank_name, money_type, terminal_type, create_time, update_time, start_jnl_no, end_jnl_no, bank_id, bank_card_no, account_type, trans_type, channel_trans_type, mobile, id_card, user_name, return_code, return_msg, note from bs_pay_orders WHERE ( user_id = ? and status = ? ) 
BsPayOrdersMapper.selectByExample - ==> Parameters: 2000000(Integer), 5(Integer)
BsPayOrdersMapper.selectByExample - <==      Total: 0

BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000000(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1

BsSubAccountMapper.selectDEPJSHSubAccountByUserId - ==>  Preparing: select a.id, a.account_id, a.code, a.product_type, a.open_balance, a.balance, a.available_balance, a.can_withdraw, a.freeze_balance, a.trans_status, a.status, a.open_time, a.note from bs_sub_account a, bs_account b, bs_user c where a.account_id = b.id and b.user_id = c.id and c.id = ? and a.status = '1' and a.product_type = 'DEP_JSH' 
BsSubAccountMapper.selectDEPJSHSubAccountByUserId - ==> Parameters: 2000000(Integer)
BsSubAccountMapper.selectDEPJSHSubAccountByUserId - <==      Total: 1
