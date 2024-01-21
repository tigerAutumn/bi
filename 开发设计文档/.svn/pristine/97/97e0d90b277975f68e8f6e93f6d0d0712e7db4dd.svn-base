-- 查询注册送多少红包：【加缓存、管理台配置清除缓存】
BsRedPacketCheckMapper.autoRedPacketTotalGrid - ==>  Preparing: SELECT a.subtract, b.agent_ids FROM `bs_red_packet_check` a, bs_auto_red_packet_rule b WHERE a.serial_no = b.serial_no AND b.`status` = 'AVAILABLE' AND a.check_status = 'PASS' AND a.distribute_type = 'AUTO' AND (b.agent_ids LIKE '%0%' OR b.agent_ids LIKE '%-1%') AND b.trigger_type = ? AND b.distribute_time_start < NOW() AND b.distribute_time_end > NOW() 
BsRedPacketCheckMapper.autoRedPacketTotalGrid - ==> Parameters: NEW_USER(String)
BsRedPacketCheckMapper.autoRedPacketTotalGrid - <==      Total: 0

-- 【可删除】查询双十一活动是否开始【删除】
BsActivityMapper.selectByPrimaryKey - ==>  Preparing: select id, name, start_time, end_time, note, create_time, update_time from bs_activity where id = ? 
BsActivityMapper.selectByPrimaryKey - ==> Parameters: 2(Integer)
BsActivityMapper.selectByPrimaryKey - <==      Total: 1

-- 【可删除，直接读取redis数据】查询累计投资额【加缓存，购买成功时清除】
BsProductMapper.selectAccumulatedInvestment - ==>  Preparing: SELECT IFNULL((SUM(a.open_balance)), 0) total_invest FROM bs_sub_account a WHERE a.`status` NOT IN (1, 6) AND a.product_type IN ('REG', 'AUTH', 'AUTH_YUN', 'AUTH_ZSD', 'RED', 'RED_ZSD', 'AUTH_7', 'RED_7') 
BsProductMapper.selectAccumulatedInvestment - ==> Parameters: 
BsProductMapper.selectAccumulatedInvestment - <==      Total: 1

-- 【可删除，未用到】统计所有产品的当前投资额和投资总次数【删除】
BsProductMapper.sumCurrTotalAmountAndInvestNum - ==>  Preparing: select IFNULL(SUM(a.invest_num), 0) as invest_num, IFNULL(SUM(a.curr_total_amount), 0) as curr_total_amount from bs_product a 
BsProductMapper.sumCurrTotalAmountAndInvestNum - ==> Parameters: 
BsProductMapper.sumCurrTotalAmountAndInvestNum - <==      Total: 1

-- 新手推荐产品（一个）【加缓存，购买成功时清除】
BsProductMapper.selectNewUserProduct - ==>  Preparing: SELECT a.* FROM bs_product a, bs_user_type_auth u WHERE a.`status` IN (5,6) AND FIND_IN_SET(?, a.show_terminal) AND u.product_id = a.id AND a.activity_type = 'NEW_BUYER' ORDER BY a.is_suggest DESC, a.`status` DESC, a.update_time DESC LIMIT 1 
BsProductMapper.selectNewUserProduct - ==> Parameters: PC(String)
BsProductMapper.selectNewUserProduct - <==      Total: 0
-- 新手推荐产品（一个）【加缓存，购买成功时清除】
BsProductMapper.selectNoviceRecommendedProduct - ==>  Preparing: SELECT a.* FROM bs_product a, bs_user_type_auth u WHERE a.`status` = 6 AND FIND_IN_SET(?, a.show_terminal) AND u.product_id = a.id ORDER BY a.term ASC, a.base_rate DESC, a.start_time ASC LIMIT 1 
BsProductMapper.selectNoviceRecommendedProduct - ==> Parameters: PC(String)
BsProductMapper.selectNoviceRecommendedProduct - <==      Total: 1
BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select * from bs_property_info where id = ? 
BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: PC(String)
BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 0
BsProductSerialMapper.selectByPrimaryKey - ==>  Preparing: select id, serial_name, term, create_time, update_time from bs_product_serial where id = ? 
BsProductSerialMapper.selectByPrimaryKey - ==> Parameters: 1(Integer)
BsProductSerialMapper.selectByPrimaryKey - <==      Total: 1

-- 产品列表【加缓存，sql需要优化，购买成功时清除】
BsProductMapper.selectProductGrid - ==> SELECT * FROM (( SELECT * FROM ( SELECT ( SELECT COUNT(sub.id) FROM bs_sub_account sub WHERE sub.product_type in ('REG', 'AUTH', 'AUTH_YUN', 'AUTH_ZSD') AND sub.`status` IN (2,5,7) AND sub.product_id = a.id ) AS invest_count, a.*, c.property_symbol FROM bs_product a, bs_user_type_auth b, bs_property_info c WHERE FIND_IN_SET(?, a.show_terminal) AND b.user_type = ? AND a.id = b.product_id AND a.`status` IN (6, 5) AND a.property_id = c.id ORDER BY a.is_suggest DESC, a.`status` DESC, a.update_time DESC, a.id DESC ) AS aa ) UNION (SELECT * FROM ( SELECT ( SELECT COUNT(sub.id) FROM bs_sub_account sub WHERE sub.product_type in ('REG', 'AUTH', 'AUTH_YUN', 'AUTH_ZSD') AND sub.`status` IN (2,5,7) AND sub.product_id = a2.id ) AS invest_count, a2.*, c2.property_symbol FROM bs_product a2, bs_user_type_auth b2, bs_property_info c2 WHERE FIND_IN_SET(?, a2.show_terminal) AND b2.user_type = ? AND a2.id = b2.product_id AND a2.`status` = 7 AND a2.property_id = c2.id ORDER BY a2.update_time DESC, a2.id DESC) AS cc)) AS aaa LIMIT ?, ? 
BsProductMapper.selectProductGrid - ==> Parameters: PC(String), NORMAL(String), PC(String), NORMAL(String), 0(Integer), 5(Integer)
BsProductMapper.selectProductGrid - <==      Total: 5
-- 产品列表个数【加缓存，sql需要优化，购买成功时清除】
BsProductMapper.countProductGrid - ==>  Preparing: SELECT COUNT(a.id) FROM bs_product a, bs_user_type_auth b WHERE FIND_IN_SET(?, a.show_terminal) AND b.user_type = ? AND a.id = b.product_id AND a.`status` IN (6, 5, 7) 
BsProductMapper.countProductGrid - ==> Parameters: PC(String), NORMAL(String)
BsProductMapper.countProductGrid - <==      Total: 1

-- 【可删除，将sql合并到产品列表sql中】循环上述列表，并查询产品状态以及是否推荐【删除，将sql合并到产品列表sql中】
BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3441(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3461(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3460(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3458(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 3457(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1

-- 产品列表（活动标）
BsProductMapper.selectProductGrid - ==> Preparing: SELECT * FROM (( SELECT * FROM ( SELECT ( SELECT COUNT(sub.id) FROM bs_sub_account sub WHERE sub.product_type in ('REG', 'AUTH', 'AUTH_YUN', 'AUTH_ZSD') AND sub.`status` IN (2,5,7) AND sub.product_id = a.id ) AS invest_count, a.*, c.property_symbol FROM bs_product a, bs_user_type_auth b, bs_property_info c WHERE FIND_IN_SET(?, a.show_terminal) AND b.user_type = ? AND a.id = b.product_id AND a.`status` IN (6, 5) AND a.property_id = c.id ORDER BY a.is_suggest DESC, a.`status` DESC, a.update_time DESC, a.id DESC ) AS aa ) UNION (SELECT * FROM ( SELECT ( SELECT COUNT(sub.id) FROM bs_sub_account sub WHERE sub.product_type in ('REG', 'AUTH', 'AUTH_YUN', 'AUTH_ZSD') AND sub.`status` IN (2,5,7) AND sub.product_id = a2.id ) AS invest_count, a2.*, c2.property_symbol FROM bs_product a2, bs_user_type_auth b2, bs_property_info c2 WHERE FIND_IN_SET(?, a2.show_terminal) AND b2.user_type = ? AND a2.id = b2.product_id AND a2.`status` = 7 AND a2.property_id = c2.id ORDER BY a2.update_time DESC, a2.id DESC ) AS cc ) ) AS aaa WHERE aaa.activity_type = ? LIMIT ?, ? 
BsProductMapper.selectProductGrid - ==> Parameters: PC(String), NORMAL(String), PC(String), NORMAL(String), ACTIVITY(String), 0(Integer), 5(Integer)
BsProductMapper.selectProductGrid - <==      Total: 2
-- 产品列表（活动标）个数
BsProductMapper.countProductGrid - ==>  Preparing: SELECT COUNT(a.id) FROM bs_product a, bs_user_type_auth b WHERE FIND_IN_SET(?, a.show_terminal) AND b.user_type = ? AND a.id = b.product_id AND a.`status` IN (6, 5, 7) 
BsProductMapper.countProductGrid - ==> Parameters: PC(String), NORMAL(String)
BsProductMapper.countProductGrid - <==      Total: 1
-- 【可删除，将sql合并到产品列表sql中】循环上述列表，并查询产品状态以及是否推荐
BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 604(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1
BsProductMapper.selectByPrimaryKey - ==>  Preparing: select id, name, type, activity_type, code, interest_type, base_rate, max_rate, term, max_total_amount, min_invest_amount, max_single_invest_amount, max_invest_amount, max_invest_times, start_time, end_time, curr_total_amount, status, note, invest_num, sys_return_rate, serial_id, serial_order, serial_name, property_type, begin_interest_days, return_type, interest_deal, is_support_transfer, manage_fee, show_terminal, property_id, is_suggest, terminator, finish_time, checker, check_time, distributor, distribute_time, is_support_red_packet, create_time, update_time, is_support_incr_interest from bs_product where id = ? 
BsProductMapper.selectByPrimaryKey - ==> Parameters: 667(Integer)
BsProductMapper.selectByPrimaryKey - <==      Total: 1

-- 查询六个运营报告信息
BsOperationReportMapper.selectByExample - ==>  Preparing: select id, report_name, display_time, img_url, storage_address, is_sugguest, show_terminal, op_user_id, create_time, update_time from bs_operation_report order by is_sugguest DESC,update_time DESC LIMIT 0,6 
BsOperationReportMapper.selectByExample - ==> Parameters: 
BsOperationReportMapper.selectByExample - <==      Total: 6

-- 【可删除】查询最小最大年化收益率【删除】
BsProductMapper.selectProductRateIndex - ==>  Preparing: SELECT min(p.base_rate) minrate, max(p.base_rate) maxrate FROM bs_product p LEFT JOIN (SELECT id,concat(show_terminal,',') show_terminal_n FROM bs_product) c ON p.id = c.id, bs_user_type_auth u WHERE u.user_type = ? AND u.product_id = p.id AND p.activity_type = ? AND p.status in (5,6) AND c.show_terminal_n like concat(concat('%', ?),',%') 
BsProductMapper.selectProductRateIndex - ==> Parameters: NORMAL(String), NORMAL(String), PC(String)
BsProductMapper.selectProductRateIndex - <==      Total: 1

-- 用户累计收益
BsUserMapper.countUserIncome - ==>  Preparing: select sum(total_interest+total_bonus) as totalIncome from bs_user 
BsUserMapper.countUserIncome - ==> Parameters: 
BsUserMapper.countUserIncome - <==      Total: 1

-- 用户累计注册人数
BsUserMapper.countByExample - ==>  Preparing: select count(*) from bs_user WHERE ( status = ? ) 
BsUserMapper.countByExample - ==> Parameters: 1(Integer)
BsUserMapper.countByExample - <==      Total: 1

-- 查询最新的5条新闻-公告【放缓存，发布时清除】
BsSysNewsMapper.selectCurrentNews - ==>  Preparing: SELECT * FROM `bs_sys_news` a WHERE a.`status` = 'PUBLISHED' AND FIND_IN_SET(?,a.receiver_type) AND a.type = ? ORDER BY a.priority DESC, a.publish_time DESC LIMIT 0, ? 
BsSysNewsMapper.selectCurrentNews - ==> Parameters: BGW(String), NOTICE(String), 5(Integer)
BsSysNewsMapper.selectCurrentNews - <==      Total: 5

-- 是否阅读过
BsUserMessageReadMapper.selectByExample - ==>  Preparing: select 'true' as QUERYID, id, user_id, position, read_time, create_time, update_time from bs_user_message_read WHERE ( user_id = ? and position = ? ) order by read_time desc 
BsUserMessageReadMapper.selectByExample - ==> Parameters: 2000056(Integer), NOTICE(String)
BsUserMessageReadMapper.selectByExample - <==      Total: 0

-- 查询最新的1条新闻-公告
BsSysNewsMapper.selectLatestNews - ==>  Preparing: SELECT * FROM `bs_sys_news` a WHERE a.`status` = 'PUBLISHED' AND FIND_IN_SET(?,a.receiver_type) AND a.type = ? ORDER BY a.publish_time DESC LIMIT 1 
BsSysNewsMapper.selectLatestNews - ==> Parameters: BGW(String), NOTICE(String)
BsSysNewsMapper.selectLatestNews - <==      Total: 1

-- 查询最新的3条公司动态【放缓存，发布时清除】
BsSysNewsMapper.selectCurrentNews - ==>  Preparing: SELECT * FROM `bs_sys_news` a WHERE a.`status` = 'PUBLISHED' AND FIND_IN_SET(?,a.receiver_type) AND a.type = ? ORDER BY a.priority DESC, a.publish_time DESC LIMIT 0, ? 
BsSysNewsMapper.selectCurrentNews - ==> Parameters: BGW(String), COMPANY_DYNAMIC(String), 3(Integer)
BsSysNewsMapper.selectCurrentNews - <==      Total: 3

-- 【可删除，代码优化，非公告的不查询这条记录】查询最新的1条公告
BsSysNewsMapper.selectLatestNews - ==>  Preparing: SELECT * FROM `bs_sys_news` a WHERE a.`status` = 'PUBLISHED' AND FIND_IN_SET(?,a.receiver_type) AND a.type = ? ORDER BY a.publish_time DESC LIMIT 1 
BsSysNewsMapper.selectLatestNews - ==> Parameters: BGW(String), NOTICE(String)
BsSysNewsMapper.selectLatestNews - <==      Total: 1

-- 查询最新的3条媒体报道【放缓存，发布时清除】
BsSysNewsMapper.selectCurrentNews - ==>  Preparing: SELECT * FROM `bs_sys_news` a WHERE a.`status` = 'PUBLISHED' AND FIND_IN_SET(?,a.receiver_type) AND a.type = ? ORDER BY a.priority DESC, a.publish_time DESC LIMIT 0, ? 
BsSysNewsMapper.selectCurrentNews - ==> Parameters: BGW(String), NEWS(String), 3(Integer)
BsSysNewsMapper.selectCurrentNews - <==      Total: 3

-- 【可删除，代码优化，非公告的不查询这条记录】查询最新的1条公告
BsSysNewsMapper.selectLatestNews - ==>  Preparing: SELECT * FROM `bs_sys_news` a WHERE a.`status` = 'PUBLISHED' AND FIND_IN_SET(?,a.receiver_type) AND a.type = ? ORDER BY a.publish_time DESC LIMIT 1 
BsSysNewsMapper.selectLatestNews - ==> Parameters: BGW(String), NOTICE(String)
BsSysNewsMapper.selectLatestNews - <==      Total: 1

-- 查询最新的3条湾粉活动【放缓存，发布时清除】
BsSysNewsMapper.selectCurrentNews - ==>  Preparing: SELECT * FROM `bs_sys_news` a WHERE a.`status` = 'PUBLISHED' AND FIND_IN_SET(?,a.receiver_type) AND a.type = ? ORDER BY a.priority DESC, a.publish_time DESC LIMIT 0, ? 
BsSysNewsMapper.selectCurrentNews - ==> Parameters: BGW(String), WFANS_ACTIVITY(String), 3(Integer)
BsSysNewsMapper.selectCurrentNews - <==      Total: 3

-- 【可删除，代码优化，非公告的不查询这条记录】查询最新的1条公告
BsSysNewsMapper.selectLatestNews - ==>  Preparing: SELECT * FROM `bs_sys_news` a WHERE a.`status` = 'PUBLISHED' AND FIND_IN_SET(?,a.receiver_type) AND a.type = ? ORDER BY a.publish_time DESC LIMIT 1 
BsSysNewsMapper.selectLatestNews - ==> Parameters: BGW(String), NOTICE(String)
BsSysNewsMapper.selectLatestNews - <==      Total: 1

-- 查询5条banner【放缓存，发布时清除】
BsBannerConfigMapper.getListByBannerConfig - ==>  Preparing: select bc.*,m.name as m_user from bs_banner_config bc left join m_user m on m.id = bc.m_user_id where bc.channel = ? and show_page = 'INDEX' and bc.status = ? order by bc.status desc,bc.priority LIMIT ?, ? 
BsBannerConfigMapper.getListByBannerConfig - ==> Parameters: GEN(String), SHOW(String), 0(Integer), 5(Integer)
BsBannerConfigMapper.getListByBannerConfig - <==      Total: 4

