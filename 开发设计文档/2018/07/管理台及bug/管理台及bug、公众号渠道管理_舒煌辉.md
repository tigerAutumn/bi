## 管理台及bug、公众号渠道管理 ##
- 开发分支：（business manage site） fr.20180707.shh 
- 代码merge分支：（business manage site） fr.20180711.ForFix#  #

### 1、财务统计-资产方代收代付菜单，数据查询不到 ###
<pre>
1. 营收转账金额明细表bs_revenue_trans_detail 本身没有数据，所以页面列表没有数据显示，不是bug；
2. 需要模拟营收转账的数据验证，正常流程产生的数据对应的金额能否查询回来；
-- 云贷，赞时贷营收 BsPayOrdersMapper.revenueCollectionRepayYunZSDList
-- 营收转账金额明细表, revenue_amount 合作方营收金额

SELECT 
  bb.user_name  user_name,
  bb.mobile  mobile,
  'REVENUE' type,
  b.revenue_amount revenue_collection, -- 代收金额
  ''  revenue_pay, -- 代付金额
  b.create_time settle_date
FROM
bs_revenue_trans_detail b,
ln_loan ba,
ln_user bb
WHERE
b.loan_id = ba.id
AND ba.ln_user_id = bb.id
AND b.trans_type = 'REVENUE_INCOME' 
AND b.partner_code = 'YUN_DAI_SELF'
AND b.create_time >= '2018-06-20 00:00:00'
AND b.create_time <= '2018-06-20 23:59:59'
LIMIT 0, 100;


-------------------------------------
FinanceStatisticsController
revenueCollectionRepay
</pre>

[资产方代收代付菜单，查询不出代收数据-bug](https://tower.im/projects/3d05349e05b2474dbd3198d04ac77804/todos/cca4eea2b6664d9d98b4e250d911a42f/)


### 2. 日常业务-借款账单管理 ###
<pre>
<font color=#DC143C>已跟产品确认：
导出功能保留，但是不会做全量导出；
数据量大，账单日默认为7天的区间，最大查询时间区间为7天，包含前后；
数据以账单为准，不需要添加本金利息大于0的判断；
</font>

-- 新增日常业务-借款账单管理菜单
INSERT INTO `m_menu` (`url`, `parent_id`, `order_num`, `name`, `note`) VALUES ('/statistics/getLoanBill/index.htm', '14', '28', '借款账单管理', '');


-- 1、正常还款 - 正常还款排除线下还款
<font color=#DC143C>正常还款：查询还款表是已还款REPAIED的，并且不是线下还款的；计划还款编号不是退票处理的；排除大借款人的账单</font>
ln_repay.status = REPAIED
ln_repay.repay_type (repay_type != 'OFFLINE_REPAY' OR repay_type IS NULL)
还款类型-OFFLINE_REPAY线下还款
ln_repay_schedule.partner_repay_id substring(rs.partner_repay_id, 0, 4) != 'RGCL'
-- 排除大借款人的账单
ln_user.id not in (
SELECT conf_value 
			FROM bs_sys_config 
			WHERE conf_key IN ('YUN_DAI_SELF_SUPER_LN_USER', '7_DAI_SELF_SUPER_LN_USER')
)


SELECT 
r.ln_user_id,
g.partner_code, f.partner_business_flag,
rs.plan_date, f.partner_loan_id, rs.partner_repay_id, rs.status,
d.plan_amount AS schedule_principal_plan_amount,
e.plan_amount AS schedule_interest_plan_amount,
-- 还款类型 正常还款
'NORMA_REPAY' AS repay_type ,
-- 还款时间 
r.done_time,
-- 还款总额  还款本金  还款利息
rs.plan_total, b.done_amount AS repay_principal_done_amount, c.done_amount AS repay_interest_done_amount
FROM
(
SELECT id, plan_date, partner_repay_id, status, plan_total, loan_id FROM ln_repay_schedule
-- REPAIED 已还款
-- 账单日间隔不能超过七天
WHERE `status` = 'REPAIED' AND substring(partner_repay_id, 0, 4) != 'RGCL' AND plan_date BETWEEN '2018-07-01' AND '2018-07-07'
) rs
INNER JOIN ln_repay r on r.repay_plan_id=rs.id and r.`status`='REPAIED' and r.repay_type is null 
INNER JOIN ln_repay_detail b ON b.repay_id = r.id
AND b.subject_code = 'PRINCIPAL'
INNER JOIN ln_repay_detail c ON c.repay_id = r.id
AND c.subject_code = 'INTEREST'
INNER JOIN ln_repay_schedule_detail d ON d.plan_id = rs.id
AND d.subject_code = 'PRINCIPAL'
INNER JOIN ln_repay_schedule_detail e ON e.plan_id = rs.id
AND e.subject_code = 'INTEREST'
LEFT JOIN ln_loan f ON rs.loan_id = f.id
LEFT JOIN ln_user g ON f.ln_user_id = g.id
WHERE g.partner_code = 'YUN_DAI_SELF';

（ln_repay_schedule 'loan_id`,`partner_repay_id 有索引）


-- 2、代偿还款
<font color=#DC143C>代偿还款：查询代偿成功ln_compensate_detail.status = SUCC，
ln_repay_schedule.status = 'LATE_NOT'，排除大借款人的账单；</font>


-- 代偿还款
SELECT 
a.partner_code, f.partner_business_flag,
rs.plan_date ,a.partner_loan_id, a.partner_repay_id,
-- 账单状态
rs.status, 
-- 账单本金  账单利息
d.plan_amount AS schedule_principal_plan_amount,
e.plan_amount AS schedule_interest_plan_amount,
-- 还款类型 还款类型 还款本金 还款利息
a.repay_type, a.update_time, a.total, a.principal, a.interest 
FROM
( SELECT 
a.partner_user_id, a.partner_loan_id, a.partner_repay_id, b.partner_code, 
a.total, a.principal, a.interest, a.update_time, 
-- 还款类型 代偿还款
'COMPENSATE_REPAY' AS repay_type 
FROM 
ln_compensate_detail a, ln_compensate b
WHERE a.`status` = 'SUCC'  
AND a.compensate_id = b.id AND b.partner_code = 'YUN_DAI_SELF' AND SUBSTRING(a.create_time,1,10) >= '2018-07-01' AND SUBSTRING(a.create_time,1,10) <= '2018-07-07') a
INNER JOIN ln_repay_schedule rs ON a.partner_repay_id = rs.partner_repay_id and rs.plan_date >= '2018-07-01' AND rs.plan_date <= '2018-07-07' and rs.status = 'LATE_NOT'
INNER JOIN ln_repay_schedule_detail d ON d.plan_id = rs.id
AND d.subject_code = 'PRINCIPAL'
INNER JOIN ln_repay_schedule_detail e ON e.plan_id = rs.id
AND e.subject_code = 'INTEREST'
LEFT JOIN ln_loan f ON rs.loan_id = f.id;


-- 3、线下还款
<font color=#DC143C>线下还款：查询线下还款ln_repay.repay_type = OFFLINE_REPAY，
计划还款编号不是退票处理的ln_repay_schedule.partner_repay_id substring(rs.partner_repay_id, 0, 4) != 'RGCL'；排除大借款人的账单；</font>

SELECT 
r.ln_user_id,
g.partner_code, f.partner_business_flag,
rs.plan_date, f.partner_loan_id, rs.partner_repay_id, rs.status,
d.plan_amount AS schedule_principal_plan_amount,
e.plan_amount AS schedule_interest_plan_amount,
-- 还款类型 正常还款
'NORMA_REPAY' AS repay_type ,
-- 还款时间 
r.done_time,
-- 还款总额  还款本金  还款利息
rs.plan_total, b.done_amount AS repay_principal_done_amount, c.done_amount AS repay_interest_done_amount
FROM
(
SELECT id, plan_date, partner_repay_id, status, plan_total, loan_id FROM ln_repay_schedule
-- REPAIED 已还款
-- 账单日间隔不能超过七天
WHERE `status` = 'REPAIED' AND substring(partner_repay_id, 0, 4) != 'RGCL' AND plan_date BETWEEN '2018-07-01' AND '2018-07-07'
) rs
INNER JOIN ln_repay r on r.repay_plan_id=rs.id and r.`status`='REPAIED' and r.repay_type = 'OFFLINE_REPAY'
INNER JOIN ln_repay_detail b ON b.repay_id = r.id
AND b.subject_code = 'PRINCIPAL'
INNER JOIN ln_repay_detail c ON c.repay_id = r.id
AND c.subject_code = 'INTEREST'
INNER JOIN ln_repay_schedule_detail d ON d.plan_id = rs.id
AND d.subject_code = 'PRINCIPAL'
INNER JOIN ln_repay_schedule_detail e ON e.plan_id = rs.id
AND e.subject_code = 'INTEREST'
LEFT JOIN ln_loan f ON rs.loan_id = f.id
LEFT JOIN ln_user g ON f.ln_user_id = g.id
WHERE g.partner_code = 'YUN_DAI_SELF';


</pre>


### 3、公众号渠道管理 ###
<pre>
<font color=#DC143C><1>、公众号渠道管理新增渠道时，用产品、客服提供的数据，用sql脚本在bs_agent表中新增数据，公众号渠道管理页面不加另外的新增功能；
<2>、新增的渠道公众号用户购买数据结算是否要考虑？后续有相应的需求再统计； </font>

（1）用户微信信息表bs_wx_info新增渠道wx_agent_id，外键关联到bs_wx_agent表;
（2）公众号渠道管理新增数据，生成带参数的二维码，参数为渠道id，<font color=#DC143C>调用微信接口</font>；
（3）用户扫描后，关注后获取用的信息，信息写入微信信息表bs_wx_info,参数agent_id也同时写入；
（4）用户未关注
数据校验：
根据用户的openId查询，如果用户在bs_wx_info有数据，说明用户已经关注币港湾相关的公众号；
如果数据不存在则新增数据，关注后bs_wx_info新增数据is_follow = FOLLOW 关注，同时写入wx_agent_id;
（5）用户取消关注
bs_wx_info数据更新is_follow = UNFOLLOW 取消关注，根据openId查询bsWxInfoMapper.updateByPrimaryKeySelective(wxUser);
（6）查看粉丝明细，只显示净关注的数据is_follow = FOLLOW，取消关注的数据不显示；

-- 1、微信信息表bs_wx_info添加wx_agent_id字段
ALTER TABLE `bs_wx_info` ADD COLUMN `wx_agent_id` int(11) NULL DEFAULT null COMMENT '公众号渠道编号' AFTER `cancel_time`;

-- 2、bs_wx_info wx_agent_id添加外键
ALTER TABLE bs_wx_info ADD CONSTRAINT FK_Reference_wx_agent_id FOREIGN KEY (wx_agent_id) REFERENCES bs_agent (id) 
ON DELETE RESTRICT ON UPDATE RESTRICT;

-- 3、新增公众号渠道管理菜单
INSERT INTO `m_menu` (`url`, `parent_id`, `order_num`, `name`, `note`) VALUES ('/wxAgent/getList.htm', '82', '14', '公众号渠道管理', '');

4. 永久二维码请求说明
生成带参数的二维码接口文档

http请求方式: POST
URL: https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN
POST数据格式：json
POST数据例子：{"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 123}}}

或者也可以使用以下POST数据创建字符串形式的二维码参数：
{"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "test"}}}

5. 通过ticket换取二维码
HTTP GET请求（请使用https协议）https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET
提醒：TICKET记得进行UrlEncode
String ticketEncoder = URLEncoder.encode(ticket, "UTF-8");

6. 扫描生成的二维码，点击关注，调用获取用户信息的接口，用户信息入微信信息表bs_wx_info，渠道的二维码存入wx_agent_id；
</pre>

[生成带参数的二维码-接口文档](https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542)



