BsActiveUserRecordMapper.selectByRecord - ==>  Preparing: select id, user_id, terminal_type, login_date, src_url, create_time, update_time from bs_active_user_record where user_id = ? and terminal_type = ? and login_date = ? 
BsActiveUserRecordMapper.selectByRecord - ==> Parameters: 2000000(Integer), APP(String), 2018-05-02(Date)
BsActiveUserRecordMapper.selectByRecord - <==      Total: 2

BsUserMapper.selectUserAssetByUserId - ==>  Preparing: select c.*, a.id as account_Id, d.status dafyStatus, SUM( CASE WHEN b.product_type = 'AUTH_YUN' THEN b.open_balance WHEN b.product_type = 'RED' THEN b.open_balance WHEN b.product_type = 'AUTH_ZSD' THEN b.open_balance WHEN b.product_type = 'RED_ZSD' THEN b.open_balance WHEN b.product_type = 'AUTH_7' THEN b.open_balance WHEN b.product_type = 'RED_7' THEN b.open_balance ELSE b.balance END ) as total_balance, (select count(*) from bs_sub_account sa where sa.account_id = b.account_id and sa.status in (2,7) AND (sa.product_type='AUTH' OR sa.product_type='REG' or sa.product_type = 'AUTH_YUN' or sa.product_type = 'AUTH_ZSD' or sa.product_type = 'AUTH_7')) investNum, (select count(*) from bs_bank_card k where k.user_id = c.id and k.`status` = '1' ) bankcard_count from bs_account a, bs_user c left join dafy_user_ext d on c.id = d.user_id ,bs_sub_account b where c.id=a.user_id and a.id=b.account_id and c.id=? and ( (b.status=1 and b.product_type = 'JSH') or (b.status=1 and b.product_type = 'JLJ') or ((b.status=1 and b.product_type = 'DEP_JSH')) or ((b.status=2 or b.status=3 or b.status=7) and b.product_type = 'REG') or ((b.status=2 or b.status=3 or b.status=4) and b.product_type = 'REG_D') or ((b.status=2 or b.status=3 or b.status=4) and b.product_type = 'AUTH') or ((b.status=2 or b.status=3 or b.status=7) and b.product_type = 'AUTH_YUN') or ((b.status=2 or b.status=3 or b.status=7) and b.product_type = 'RED') or ((b.status=2 or b.status=3 or b.status=7) and b.product_type = 'RED_ZSD') or ((b.status=2 or b.status=3 or b.status=7) and b.product_type = 'RED_7') or ((b.status=2 or b.status=3 or b.status=7) and b.product_type = 'AUTH_ZSD') or ((b.status=2 or b.status=3 or b.status=7) and b.product_type = 'AUTH_7') ) 
BsUserMapper.selectUserAssetByUserId - ==> Parameters: 2000000(Integer)
BsUserMapper.selectUserAssetByUserId - <==      Total: 1

-- 查询5条banner【放缓存，发布时清除】
BsBannerConfigMapper.getListByBannerConfig - ==>  Preparing: select bc.*,m.name as m_user from bs_banner_config bc left join m_user m on m.id = bc.m_user_id where bc.channel = ? and show_page = 'INDEX' and bc.status = ? order by bc.status desc,bc.priority LIMIT ?, ? 
BsBannerConfigMapper.getListByBannerConfig - ==> Parameters: APP_START(String), SHOW(String), 0(Integer), 1(Integer)
BsBannerConfigMapper.getListByBannerConfig - <==      Total: 1


BsSubAccountMapper.selectInvestProportionList - ==>  Preparing: SELECT tt.product_type,tt.invest_amount,tt.invest_num, CASE WHEN ROUND(ROUND(tt.invest_amount/st.invest_amount,4)*100,2) is null THEN 0 ELSE ROUND(ROUND(tt.invest_amount/st.invest_amount,4)*100,2) END as proportion_rate from ( select 'GW' as product_type, CASE WHEN sum(open_balance) is NULL THEN 0 ELSE sum(open_balance) END as invest_amount, COUNT( CASE WHEN bsa.product_type IN ('RED', 'RED_ZSD','RED_7') THEN NULL ELSE bsa.id END ) invest_num,u.id from bs_user u,bs_account ba,bs_sub_account bsa,bs_product bp where u.id = ? and u.id = ba.user_id and ba.id = bsa.account_id and bsa.product_id = bp.id and bsa.`status` in (2,7) and bsa.product_type in ('REG', 'AUTH_YUN', 'RED', 'AUTH_ZSD', 'RED_ZSD','AUTH_7', 'RED_7') and bsa.open_balance > 0 UNION select 'ZAN' as product_type, ( SELECT( (SELECT CASE WHEN SUM(a.open_balance) IS NULL THEN 0 ELSE SUM(a.open_balance) END FROM bs_sub_account a, bs_account b WHERE a.account_id = b.id AND a.product_type = 'AUTH' AND a.`status` NOT IN (1, 6) AND b.user_id = ? ) - (SELECT CASE WHEN SUM(a.amount) IS NULL THEN 0 ELSE SUM(a.amount) END FROM bs_standby_return a WHERE a.user_id = ? ) - ( SELECT CASE WHEN SUM(d.principal) IS NULL THEN 0 ELSE SUM(d.principal) END FROM ln_loan_relation a ,ln_finance_repay_schedule b, bs_sub_account c, bs_loan_finance_repay d WHERE a.id = b.relation_id AND c.id = a.bs_sub_account_id AND c.product_type IN ('REG_D', 'AUTH') AND d.finance_repay_schedule_id = b.id AND d.`status` = 'REPAIED' AND a.bs_user_id = ? ) - ( select CASE WHEN sum(lc.in_amount-lc.amount) IS NULL THEN 0 ELSE sum(lc.in_amount-lc.amount) END from ln_finance_repay_schedule a ,ln_credit_transfer lc, bs_sub_account auth where repay_serial = 1 and a.`status` = 'REPAIED' and lc.in_loan_relation_id = relation_id AND lc.in_user_id = ? AND auth.id = lc.in_sub_account_id AND auth.product_type = 'REG_D' ) ) as invest_amount ), ( SELECT COUNT(auth.id) FROM bs_sub_account auth, bs_sub_account regd, bs_sub_account_pair pair, bs_product p, bs_account b WHERE auth.product_type = 'AUTH' AND regd.product_type = 'REG_D' AND pair.auth_account_id = auth.id AND pair.reg_d_account_id = regd.id AND auth.`status` NOT IN (1, 6) AND p.id = auth.product_id AND auth.account_id = b.id AND b.user_id = ? AND ( ( regd.`status` NOT IN (1, 5, 6, 7) AND DATEDIFF(NOW(),ADDDATE(auth.interest_begin_date, (SELECT config.conf_value FROM bs_sys_config config WHERE config.conf_key = 'DAY_4_WAIT_LOAN'))) >= 0 AND regd.balance > 0 ) OR ( regd.`status` NOT IN (1, 6) AND DATEDIFF(NOW(),ADDDATE(auth.interest_begin_date, (SELECT config.conf_value FROM bs_sys_config config WHERE config.conf_key = 'DAY_4_WAIT_LOAN'))) < 0 ) ) ) invest_num,u.id from bs_user u,bs_account ba,bs_sub_account bsa,bs_product bp, bs_sub_account bsa1,bs_sub_account_pair bsap where u.id = ? and u.id = ba.user_id and ba.id = bsa.account_id and bsa.product_id = bp.id and bsa.`status` in (2,7) and bsa.product_type ='AUTH' and bsa.id = bsap.auth_account_id and bsap.reg_d_account_id = bsa1.id and bsa1.`status` in (2,7) AND DATEDIFF(NOW(),ADDDATE(bsa.interest_begin_date, (SELECT config.conf_value FROM bs_sys_config config WHERE config.conf_key = 'DAY_4_WAIT_LOAN'))) != 0 ) as tt,( SELECT( (SELECT CASE WHEN SUM(a.open_balance) IS NULL THEN 0 ELSE SUM(a.open_balance) END FROM bs_sub_account a, bs_account b WHERE a.account_id = b.id AND a.product_type = 'AUTH' AND a.`status` NOT IN (1, 6) AND b.user_id = ? ) - (SELECT CASE WHEN SUM(a.amount) IS NULL THEN 0 ELSE SUM(a.amount) END FROM bs_standby_return a WHERE a.user_id = ? ) - ( SELECT CASE WHEN SUM(d.principal) IS NULL THEN 0 ELSE SUM(d.principal) END FROM ln_loan_relation a ,ln_finance_repay_schedule b, bs_sub_account c, bs_loan_finance_repay d WHERE a.id = b.relation_id AND c.id = a.bs_sub_account_id AND c.product_type IN ('REG_D', 'AUTH') AND d.finance_repay_schedule_id = b.id AND d.`status` = 'REPAIED' AND a.bs_user_id = ? ) - ( select CASE WHEN sum(lc.in_amount-lc.amount) IS NULL THEN 0 ELSE sum(lc.in_amount-lc.amount) END from ln_finance_repay_schedule a ,ln_credit_transfer lc, bs_sub_account auth where repay_serial = 1 and a.`status` = 'REPAIED' and lc.in_loan_relation_id = relation_id AND lc.in_user_id = ? AND auth.id = lc.in_sub_account_id AND auth.product_type = 'REG_D' ) + ( select CASE WHEN sum(open_balance) is NULL THEN 0 ELSE sum(open_balance) END from bs_user u,bs_account ba,bs_sub_account bsa,bs_product bp where u.id = ? and u.id = ba.user_id and ba.id = bsa.account_id and bsa.product_id = bp.id and bsa.`status` in (2,7) and bsa.product_type in ('REG', 'AUTH_7', 'RED_7', 'AUTH_YUN', 'RED', 'AUTH_ZSD', 'RED_ZSD') and bsa.open_balance > 0 ) ) as invest_amount)as st 
BsSubAccountMapper.selectInvestProportionList - ==> Parameters: 2000000(Integer), 2000000(Integer), 2000000(Integer), 2000000(Integer), 2000000(Integer), 2000000(Integer), 2000000(Integer), 2000000(Integer), 2000000(Integer), 2000000(Integer), 2000000(Integer), 2000000(Integer)
BsSubAccountMapper.selectInvestProportionList - <==      Total: 1

BsBannerConfigMapper.getListByBannerConfig - ==>  Preparing: select bc.*,m.name as m_user from bs_banner_config bc left join m_user m on m.id = bc.m_user_id where bc.channel = ? and show_page = 'INDEX' and bc.status = ? order by bc.status desc,bc.priority LIMIT ?, ? 
BsBannerConfigMapper.getListByBannerConfig - ==> Parameters: APP(String), SHOW(String), 0(Integer), 5(Integer)
BsBannerConfigMapper.getListByBannerConfig - <==      Total: 4

BsActiveUserRecordMapper.selectByRecord - ==>  Preparing: select id, user_id, terminal_type, login_date, src_url, create_time, update_time from bs_active_user_record where user_id = ? and terminal_type = ? and login_date = ? 
BsActiveUserRecordMapper.selectByRecord - ==> Parameters: 2000000(Integer), APP(String), 2018-05-02(Date)
BsActiveUserRecordMapper.selectByRecord - ==>  Preparing: select id, user_id, terminal_type, login_date, src_url, create_time, update_time from bs_active_user_record where user_id = ? and terminal_type = ? and login_date = ? 
BsActiveUserRecordMapper.selectByRecord - ==> Parameters: 2000000(Integer), APP(String), 2018-05-02(Date)
BsActiveUserRecordMapper.selectByRecord - <==      Total: 2
BsActiveUserRecordMapper.selectByRecord - <==      Total: 2

BsProductMapper.selectProductRateIndex - ==>  Preparing: SELECT min(p.base_rate) minrate, max(p.base_rate) maxrate FROM bs_product p LEFT JOIN (SELECT id,concat(show_terminal,',') show_terminal_n FROM bs_product) c ON p.id = c.id, bs_user_type_auth u WHERE u.user_type = ? AND u.product_id = p.id AND p.activity_type = ? AND p.status in (5,6) AND c.show_terminal_n like concat(concat('%', ?),',%') 
BsProductMapper.selectProductRateIndex - ==> Parameters: NORMAL(String), NORMAL(String), APP(String)
BsProductMapper.selectProductRateIndex - <==      Total: 1

-- 查询最新的5条新闻-公告【放缓存，发布时清除】
BsSysNewsMapper.selectCurrentNews - ==>  Preparing: SELECT * FROM `bs_sys_news` a WHERE a.`status` = 'PUBLISHED' AND FIND_IN_SET(?,a.receiver_type) AND a.type = ? ORDER BY a.priority DESC, a.publish_time DESC LIMIT 0, ? 
BsSysNewsMapper.selectCurrentNews - ==> Parameters: BGW(String), NOTICE(String), 5(Integer)
BsSysNewsMapper.selectCurrentNews - <==      Total: 5

-- 是否阅读过
BsUserMessageReadMapper.selectByExample - ==>  Preparing: select 'true' as QUERYID, id, user_id, position, read_time, create_time, update_time from bs_user_message_read WHERE ( user_id = ? and position = ? ) order by read_time desc 
BsUserMessageReadMapper.selectByExample - ==> Parameters: 2000000(Integer), NOTICE(String)
BsUserMessageReadMapper.selectByExample - <==      Total: 0

-- 查询最新的1条新闻-公告
BsSysNewsMapper.selectLatestNews - ==>  Preparing: SELECT * FROM `bs_sys_news` a WHERE a.`status` = 'PUBLISHED' AND FIND_IN_SET(?,a.receiver_type) AND a.type = ? ORDER BY a.publish_time DESC LIMIT 1 
BsSysNewsMapper.selectLatestNews - ==> Parameters: BGW(String), NOTICE(String)
BsSysNewsMapper.selectLatestNews - <==      Total: 1

-- 是否阅读过
BsUserMessageReadMapper.selectByExample - ==>  Preparing: select 'true' as QUERYID, id, user_id, position, read_time, create_time, update_time from bs_user_message_read WHERE ( user_id = ? and position = ? ) order by read_time desc 
BsUserMessageReadMapper.selectByExample - ==> Parameters: 2000000(Integer), ACTIVITY(String)
BsUserMessageReadMapper.selectByExample - <==      Total: 0

-- 查询最近的app活动
BsAppActiveMapper.selectLatestActive - ==>  Preparing: select id, start_time, end_time, publish_time, title, url, summary, img_url, create_time, update_time, show_terminal from bs_app_active where end_time >= now() and start_time <= now() and FIND_IN_SET('BGW_H5', show_terminal) order by publish_time desc limit 1 
BsAppActiveMapper.selectLatestActive - ==> Parameters: 
BsAppActiveMapper.selectLatestActive - <==      Total: 1

BsProductMapper.selectHomeProduct - ==>  Preparing: select * from bs_product a where `status` in (5,6) and activity_type = ? and find_in_set(?, a.show_terminal) order by is_suggest desc, field(`status`, 6,5), distribute_time desc limit 1 
BsProductMapper.selectHomeProduct - ==> Parameters: ACTIVITY(String), APP(String)
BsProductMapper.selectHomeProduct - <==      Total: 0
BsProductMapper.selectHomeProduct - ==>  Preparing: select * from bs_product a where `status` in (5,6) and activity_type = ? and find_in_set(?, a.show_terminal) order by is_suggest desc, field(`status`, 6,5), distribute_time desc limit 1 
BsProductMapper.selectHomeProduct - ==> Parameters: NEW_BUYER(String), APP(String)
BsProductMapper.selectHomeProduct - <==      Total: 1

BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000000(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1

BsProductMapper.selectHomeProduct - ==>  Preparing: select * from bs_product a where `status` in (5,6) and activity_type = ? and find_in_set(?, a.show_terminal) order by is_suggest desc, field(`status`, 6,5), distribute_time desc limit 1 
BsProductMapper.selectHomeProduct - ==> Parameters: NORMAL(String), APP(String)
BsProductMapper.selectHomeProduct - <==      Total: 1

BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 6(Integer)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

BsRedPacketInfoMapper.selectOptimalRedPacket - ==>  Preparing: SELECT a.id, a.user_id, b.serial_name, b.`full`, b.term_limit, b.subtract, a.use_time_start, a.use_time_end, a.`status` FROM bs_red_packet_info a, bs_red_packet_check b WHERE a.serial_no = b.serial_no AND a.user_id = ? AND a.`status` = 'INIT' AND a.use_time_end >= NOW() AND a.use_time_start <= NOW() AND FIND_IN_SET(?,b.term_limit) ORDER BY b.subtract DESC ,b.`full` ASC,a.use_time_end DESC LIMIT 0,1 
BsRedPacketInfoMapper.selectOptimalRedPacket - ==> Parameters: 2000000(Integer), 3(Integer)
BsRedPacketInfoMapper.selectOptimalRedPacket - <==      Total: 1

BsAppActiveMapper.selectActivePage - ==>  Preparing: SELECT id, start_time, end_time, publish_time, title, url, summary, img_url, create_time, update_time, show_terminal FROM bs_app_active WHERE 1 = 1 AND FIND_IN_SET(?, show_terminal) ORDER BY publish_time DESC LIMIT ?, ? 
BsAppActiveMapper.selectActivePage - ==> Parameters: BGW_APP(String), 0(Integer), 20(Integer)
BsAppActiveMapper.selectActivePage - <==      Total: 20

-- 查询DEPJSH
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
BsDailyInterestMapper.selectByExample - <==      Total: 1

BsSubAccountMapper.selectByExamplePage - ==>  Preparing: SELECT * FROM ( select b.id id,p.`name` as product_name,p.property_id, b.open_balance as balance, b.open_time openTime, b.interest_begin_date interestBeginDate, b.status, b.product_rate product_rate, p.id productId, p.term term,p.property_type propertyType, case when p.term = 12 then (365 - case when datediff(now(), b.interest_begin_date) < 0 then 0 else datediff(now(), b.interest_begin_date) end ) when p.term = -7 then (7 - case when datediff(now(), b.interest_begin_date) < 0 then 0 else datediff(now(), b.interest_begin_date) end ) else (p.term * 30 - case when datediff(now(), b.interest_begin_date) < 0 then 0 else datediff(now(), b.interest_begin_date) end ) end investDay, b.last_finish_interest_date investEndTime, c.amount AS red_amount, p.start_time,0 AS ticket_apr,0 AS interest_amount from bs_sub_account b ,bs_account a,bs_product p, bs_pay_orders o LEFT JOIN bs_red_packet_info c ON o.order_no = c.order_no AND c.`status` = 'USED' WHERE b.account_id = a.id and b.product_id = p.id and b.status != 1 and b.status != 6 and b.product_type IN ('REG') and (o.trans_type = 'CARD_BUY_PRODUCT' OR o.trans_type = 'BALANCE_BUY_PRODUCT') AND a.user_id=? and ( b.status = ? or b.status = 7 ) AND o.sub_account_id = b.id UNION select b.id id,p.`name` as product_name,p.property_id, b.open_balance + IFNULL(red.open_balance,0) as balance, b.open_time openTime, b.interest_begin_date interestBeginDate, b.status, b.product_rate product_rate, p.id productId, p.term term,p.property_type propertyType, case when p.term = 12 then (365 - case when datediff(now(), b.interest_begin_date) < 0 then 0 else datediff(now(), b.interest_begin_date) end ) when p.term = -7 then (7 - case when datediff(now(), b.interest_begin_date) < 0 then 0 else datediff(now(), b.interest_begin_date) end ) else (p.term * 30 - case when datediff(now(), b.interest_begin_date) < 0 then 0 else datediff(now(), b.interest_begin_date) end ) end investDay, b.last_finish_interest_date investEndTime, red.open_balance AS red_amount, p.start_time, ifnull(ti.ticket_apr, '0') AS ticket_apr, ifnull(ti.interest_amount, '0') AS interest_amount from bs_sub_account b LEFT JOIN bs_sub_account_pair pr ON pr.auth_account_id = b.id LEFT JOIN bs_sub_account red ON red.id = pr.red_account_id LEFT JOIN bs_interest_ticket_info ti ON b.id = ti.auth_account_id ,bs_account a,bs_product p WHERE b.account_id = a.id and b.product_id = p.id and b.status != 1 and b.status != 6 and b.product_type IN ('AUTH_YUN','AUTH_ZSD','AUTH_7') AND a.user_id=? and ( b.status = ? or b.status = 7 ) ) aa order by aa.investEndTime desc , aa.openTime desc, aa.id desc limit ?, ? 
BsSubAccountMapper.selectByExamplePage - ==> Parameters: 2000000(Integer), 2(Integer), 2000000(Integer), 2(Integer), 0(Integer), 2147483647(Integer)
BsSubAccountMapper.selectByExamplePage - <==      Total: 14

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
BsRedPacketInfoMapper.countByExample - ==> Parameters: 2000000(Integer), INIT(String), 2018-05-02 20:25:49.982(Timestamp)
BsRedPacketInfoMapper.countByExample - <==      Total: 1

BsInterestTicketInfoMapper.countByExample - ==>  Preparing: select count(*) from bs_interest_ticket_info WHERE ( user_id = ? and status = ? and use_time_end >= ? and use_time_start <= ? ) 
BsInterestTicketInfoMapper.countByExample - ==> Parameters: 2000000(Integer), INIT(String), 2018-05-02 20:25:49.991(Timestamp), 2018-05-02 20:25:49.991(Timestamp)
BsInterestTicketInfoMapper.countByExample - <==      Total: 1

-- 查询DEPJSH
BsSubAccountMapper.selectDEPJSHSubAccountByUserId - ==>  Preparing: select a.id, a.account_id, a.code, a.product_type, a.open_balance, a.balance, a.available_balance, a.can_withdraw, a.freeze_balance, a.trans_status, a.status, a.open_time, a.note from bs_sub_account a, bs_account b, bs_user c where a.account_id = b.id and b.user_id = c.id and c.id = ? and a.status = '1' and a.product_type = 'DEP_JSH' 
BsSubAccountMapper.selectDEPJSHSubAccountByUserId - ==> Parameters: 2000000(Integer)
BsSubAccountMapper.selectDEPJSHSubAccountByUserId - <==      Total: 1

-- 查询JSH
BsSubAccountMapper.selectJSHSubAccountByUserId - ==>  Preparing: select a.id, a.account_id, a.code, a.product_type, a.open_balance, a.balance, a.available_balance, a.can_withdraw, a.freeze_balance, a.trans_status, a.status, a.open_time, a.note from bs_sub_account a, bs_account b, bs_user c where a.account_id = b.id and b.user_id = c.id and c.id = ? and a.status = '1' and a.product_type = 'JSH' 
BsSubAccountMapper.selectJSHSubAccountByUserId - ==> Parameters: 2000000(Integer)
BsSubAccountMapper.selectJSHSubAccountByUserId - <==      Total: 1

-- 查询银行卡数量
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

-- 查询风险测评
BsQuestionnaireMapper.selectByExample - ==>  Preparing: select id, user_id, question_type, question_item, score, expire_days, expire_time, create_time, update_time from bs_questionnaire WHERE ( user_id = ? ) order by update_time desc 
BsQuestionnaireMapper.selectByExample - ==> Parameters: 2000000(Integer)
BsQuestionnaireMapper.selectByExample - <==      Total: 1

BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000000(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1
