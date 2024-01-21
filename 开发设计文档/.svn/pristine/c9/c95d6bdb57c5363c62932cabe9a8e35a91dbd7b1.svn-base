-- 【可删除】查询双十一活动是否开始【删除】
2018-05-02 09:55:28,548 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsActivityMapper.selectByPrimaryKey - ==>  Preparing: select id, name, start_time, end_time, note, create_time, update_time from bs_activity where id = ? 
2018-05-02 09:55:28,548 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsActivityMapper.selectByPrimaryKey - ==> Parameters: 2(Integer)
2018-05-02 09:55:28,565 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsActivityMapper.selectByPrimaryKey - <==      Total: 1

-- 【可删除，直接读取redis数据】查询累计投资额【加缓存，购买成功时清除】
2018-05-02 09:55:28,566 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.selectAccumulatedInvestment - ==>  Preparing: SELECT IFNULL((SUM(a.open_balance)), 0) total_invest FROM bs_sub_account a WHERE a.`status` NOT IN (1, 6) AND a.product_type IN ('REG', 'AUTH', 'AUTH_YUN', 'AUTH_ZSD', 'RED', 'RED_ZSD', 'AUTH_7', 'RED_7') 
2018-05-02 09:55:28,567 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.selectAccumulatedInvestment - ==> Parameters: 
2018-05-02 09:55:28,843 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.selectAccumulatedInvestment - <==      Total: 1

-- 【可删除，未用到】统计所有产品的当前投资额和投资总次数【删除】
2018-05-02 09:55:28,844 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.sumCurrTotalAmountAndInvestNum - ==>  Preparing: select IFNULL(SUM(a.invest_num), 0) as invest_num, IFNULL(SUM(a.curr_total_amount), 0) as curr_total_amount from bs_product a 
2018-05-02 09:55:28,845 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.sumCurrTotalAmountAndInvestNum - ==> Parameters: 
2018-05-02 09:55:28,882 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.sumCurrTotalAmountAndInvestNum - <==      Total: 1

-- 查询活动标
2018-05-02 09:55:28,883 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.selectHomeProduct - ==>  Preparing: select * from bs_product a where `status` in (5,6) and activity_type = ? and find_in_set(?, a.show_terminal) order by is_suggest desc, field(`status`, 6,5), distribute_time desc limit 1 
2018-05-02 09:55:28,884 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.selectHomeProduct - ==> Parameters: ACTIVITY(String), H5(String)
2018-05-02 09:55:28,901 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.selectHomeProduct - <==      Total: 0

-- 查询新手标
2018-05-02 09:55:28,902 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.selectHomeProduct - ==>  Preparing: select * from bs_product a where `status` in (5,6) and activity_type = ? and find_in_set(?, a.show_terminal) order by is_suggest desc, field(`status`, 6,5), distribute_time desc limit 1 
2018-05-02 09:55:28,903 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.selectHomeProduct - ==> Parameters: NEW_BUYER(String), H5(String)
2018-05-02 09:55:28,921 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.selectHomeProduct - <==      Total: 0

-- 查询普通标
2018-05-02 09:55:28,922 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.selectHomeProduct - ==>  Preparing: select * from bs_product a where `status` in (5,6) and activity_type = ? and find_in_set(?, a.show_terminal) order by is_suggest desc, field(`status`, 6,5), distribute_time desc limit 1 
2018-05-02 09:55:28,922 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.selectHomeProduct - ==> Parameters: NORMAL(String), H5(String)
2018-05-02 09:55:28,940 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.selectHomeProduct - <==      Total: 1

-- 查询资产方信息
2018-05-02 09:55:28,941 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsPropertyInfoMapper.selectByPrimaryKey - ==>  Preparing: select id, property_name, property_symbol, property_summary, return_source, fund_security, orgnize_check, orgnize_check_pics, coop_protocol_pics, rating_grade, rating_grade_pics, loan_protocol_pics, create_time, update_time from bs_property_info where id = ? 
2018-05-02 09:55:28,942 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsPropertyInfoMapper.selectByPrimaryKey - ==> Parameters: 4(Integer)
2018-05-02 09:55:28,955 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsPropertyInfoMapper.selectByPrimaryKey - <==      Total: 1

-- 【H5无运营报告信息，可删除】查询运营报告信息
2018-05-02 09:55:28,957 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsOperationReportMapper.selectByExample - ==>  Preparing: select id, report_name, display_time, img_url, storage_address, is_sugguest, show_terminal, op_user_id, create_time, update_time from bs_operation_report order by is_sugguest DESC,update_time DESC LIMIT 0,6 
2018-05-02 09:55:28,957 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsOperationReportMapper.selectByExample - ==> Parameters: 
2018-05-02 09:55:28,974 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsOperationReportMapper.selectByExample - <==      Total: 6

-- 【可删除】查询最小最大年化收益率【删除】
2018-05-02 09:55:28,976 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.selectProductRateIndex - ==>  Preparing: SELECT min(p.base_rate) minrate, max(p.base_rate) maxrate FROM bs_product p LEFT JOIN (SELECT id,concat(show_terminal,',') show_terminal_n FROM bs_product) c ON p.id = c.id, bs_user_type_auth u WHERE u.user_type = ? AND u.product_id = p.id AND p.activity_type = ? AND p.status in (5,6) AND c.show_terminal_n like concat(concat('%', ?),',%') 
2018-05-02 09:55:28,977 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.selectProductRateIndex - ==> Parameters: NORMAL(String), NORMAL(String), H5(String)
2018-05-02 09:55:29,001 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsProductMapper.selectProductRateIndex - <==      Total: 1

-- 【可删除，直接获取redis数据】用户累计收益【获取平台数据redis】
2018-05-02 09:55:29,002 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsUserMapper.countUserIncome - ==>  Preparing: select sum(total_interest+total_bonus) as totalIncome from bs_user 
2018-05-02 09:55:29,002 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsUserMapper.countUserIncome - ==> Parameters: 
2018-05-02 09:55:29,057 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsUserMapper.countUserIncome - <==      Total: 1

-- 用户累计注册人数
2018-05-02 09:55:29,058 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsUserMapper.countByExample - ==>  Preparing: select count(*) from bs_user WHERE ( status = ? ) 
2018-05-02 09:55:29,058 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsUserMapper.countByExample - ==> Parameters: 1(Integer)
2018-05-02 09:55:29,087 [qtp1486365718-34] DEBUG com.pinting.business.dao.BsUserMapper.countByExample - <==      Total: 1

-- 查询最新的5条新闻-公告【放缓存，发布时清除】
2018-05-02 09:55:29,093 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectCurrentNews - ==>  Preparing: SELECT * FROM `bs_sys_news` a WHERE a.`status` = 'PUBLISHED' AND FIND_IN_SET(?,a.receiver_type) AND a.type = ? ORDER BY a.priority DESC, a.publish_time DESC LIMIT 0, ? 
2018-05-02 09:55:29,093 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectCurrentNews - ==> Parameters: BGW(String), NOTICE(String), 5(Integer)
2018-05-02 09:55:29,114 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectCurrentNews - <==      Total: 5

-- 查询最新的1条新闻-公告
2018-05-02 09:55:29,115 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectLatestNews - ==>  Preparing: SELECT * FROM `bs_sys_news` a WHERE a.`status` = 'PUBLISHED' AND FIND_IN_SET(?,a.receiver_type) AND a.type = ? ORDER BY a.publish_time DESC LIMIT 1 
2018-05-02 09:55:29,116 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectLatestNews - ==> Parameters: BGW(String), NOTICE(String)
2018-05-02 09:55:29,154 [qtp1486365718-33] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectLatestNews - <==      Total: 1

-- 【H5可删除】查询最新的3条公司动态
2018-05-02 09:55:29,161 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectCurrentNews - ==>  Preparing: SELECT * FROM `bs_sys_news` a WHERE a.`status` = 'PUBLISHED' AND FIND_IN_SET(?,a.receiver_type) AND a.type = ? ORDER BY a.priority DESC, a.publish_time DESC LIMIT 0, ? 
2018-05-02 09:55:29,161 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectCurrentNews - ==> Parameters: BGW(String), COMPANY_DYNAMIC(String), 3(Integer)
2018-05-02 09:55:29,206 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectCurrentNews - <==      Total: 3

-- 【可删除，代码优化，非公告的不查询这条记录】查询最新的1条公告
2018-05-02 09:55:29,207 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectLatestNews - ==>  Preparing: SELECT * FROM `bs_sys_news` a WHERE a.`status` = 'PUBLISHED' AND FIND_IN_SET(?,a.receiver_type) AND a.type = ? ORDER BY a.publish_time DESC LIMIT 1 
2018-05-02 09:55:29,207 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectLatestNews - ==> Parameters: BGW(String), NOTICE(String)
2018-05-02 09:55:29,222 [qtp1486365718-31] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectLatestNews - <==      Total: 1

-- 【H5可删除】查询最新的3条港湾资讯
2018-05-02 09:55:29,228 [qtp1486365718-32] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectCurrentNews - ==>  Preparing: SELECT * FROM `bs_sys_news` a WHERE a.`status` = 'PUBLISHED' AND FIND_IN_SET(?,a.receiver_type) AND a.type = ? ORDER BY a.priority DESC, a.publish_time DESC LIMIT 0, ? 
2018-05-02 09:55:29,228 [qtp1486365718-32] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectCurrentNews - ==> Parameters: BGW(String), NEWS(String), 3(Integer)
2018-05-02 09:55:29,249 [qtp1486365718-32] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectCurrentNews - <==      Total: 3

-- 【可删除，代码优化，非公告的不查询这条记录】查询最新的1条公告
2018-05-02 09:55:29,250 [qtp1486365718-32] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectLatestNews - ==>  Preparing: SELECT * FROM `bs_sys_news` a WHERE a.`status` = 'PUBLISHED' AND FIND_IN_SET(?,a.receiver_type) AND a.type = ? ORDER BY a.publish_time DESC LIMIT 1 
2018-05-02 09:55:29,251 [qtp1486365718-32] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectLatestNews - ==> Parameters: BGW(String), NOTICE(String)
2018-05-02 09:55:29,263 [qtp1486365718-32] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectLatestNews - <==      Total: 1

-- 【H5可删除】查询最新的3条湾粉活动
2018-05-02 09:55:29,269 [qtp1486365718-26] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectCurrentNews - ==>  Preparing: SELECT * FROM `bs_sys_news` a WHERE a.`status` = 'PUBLISHED' AND FIND_IN_SET(?,a.receiver_type) AND a.type = ? ORDER BY a.priority DESC, a.publish_time DESC LIMIT 0, ? 
2018-05-02 09:55:29,270 [qtp1486365718-26] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectCurrentNews - ==> Parameters: BGW(String), WFANS_ACTIVITY(String), 3(Integer)
2018-05-02 09:55:29,304 [qtp1486365718-26] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectCurrentNews - <==      Total: 3

-- 【可删除，代码优化，非公告的不查询这条记录】查询最新的1条公告
2018-05-02 09:55:29,305 [qtp1486365718-26] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectLatestNews - ==>  Preparing: SELECT * FROM `bs_sys_news` a WHERE a.`status` = 'PUBLISHED' AND FIND_IN_SET(?,a.receiver_type) AND a.type = ? ORDER BY a.publish_time DESC LIMIT 1 
2018-05-02 09:55:29,305 [qtp1486365718-26] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectLatestNews - ==> Parameters: BGW(String), NOTICE(String)
2018-05-02 09:55:29,315 [qtp1486365718-26] DEBUG com.pinting.business.dao.BsSysNewsMapper.selectLatestNews - <==      Total: 1

-- 查询5条banner【放缓存，发布时清除】
2018-05-02 09:55:29,322 [qtp1486365718-29] DEBUG com.pinting.business.dao.BsBannerConfigMapper.getListByBannerConfig - ==>  Preparing: select bc.*,m.name as m_user from bs_banner_config bc left join m_user m on m.id = bc.m_user_id where bc.channel = ? and show_page = 'INDEX' and bc.status = ? order by bc.status desc,bc.priority LIMIT ?, ? 
2018-05-02 09:55:29,322 [qtp1486365718-29] DEBUG com.pinting.business.dao.BsBannerConfigMapper.getListByBannerConfig - ==> Parameters: MICRO(String), SHOW(String), 0(Integer), 5(Integer)
2018-05-02 09:55:29,333 [qtp1486365718-29] DEBUG com.pinting.business.dao.BsBannerConfigMapper.getListByBannerConfig - <==      Total: 3
