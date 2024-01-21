# 营销活动，抽奖活动查询，导出excel优化 
## 需求说明
查询日常运营做的活动数据 ( 数据来源 : 后续一系列活动数据)
#### 好处
1. 运营能够更快/实时获取活动反馈,及时调整活动策略,提取相关数据
2. 降低沟通成本,数据校验中人工失误的成本(提取数据,统计汇总,核验数据,系统发放)
### 菜单位置
营销活动-->抽奖情况查询
## 业务逻辑
### 原先逻辑
#### 涉及表及字段
1. bs_activity_lucky_draw(活动用户抽奖记录表，保存所有的抽奖记录，包括中的和未中的)-所有字段（主要是否抽奖、是否中奖、中奖时间字段）</br>
2. bs_activity_award(活动奖品表)-奖品内容字段</br>
3. bs_activity(活动表)-关联活动id为了筛选活动</br>
4. bs_user(注册用户信息表)-用户手机号、用户的渠道id</br>
5. bs_agent(渠道代理表)-与用户的agentid关联显示用户的渠道名称</br>
#### 表之间关联
bs_activity_lucky_draw的award_id和bs_activity_award的id关联</br>
bs_activity_lucky_draw的activity_id和bs_activity的id关联</br>
bs_user的agent_id和bs_agent的id关联</br>
bs_user的id和bs_activity_lucky_draw的user_id相等</br>
#### 输出字段
1. 活动类型：bs_activity的name字段</br>
2. 中奖时间：bs_activity_lucky_draw的user_draw_time字段</br>
3. 手机号码：bs_user的mobile字段</br>
4. 姓名：bs_user的user_name字段</br>
5. 性别：根据bs_user的id_card字段来判断性别</br>
6. 奖品：bs_activity_award的content字段</br>
7. 交易编号：bs_activity_lucky_draw的id字段</br>
8. 渠道：bs_agent的agent_name字段</br>
9. 是否抽奖：bs_activity_lucky_draw的is_user_draw</br>
10. 是否中奖：bs_activity_lucky_draw的is_win</br>
#### 筛选条件
手机号、姓名、是否抽奖、中奖时间、奖品、是否中奖、活动类型、
#### SQL查询语句
```
select a.*,
	b.content as award_content,
	c.mobile,
	d.agent_name, 
	c.user_name,
	e.name AS activity_name, 
	(case if(length(c.id_card)=18, cast(substring(c.id_card,17,1) as UNSIGNED)%2, if(length(c.id_card)=15,cast(substring(c.id_card,15,1) as UNSIGNED)%2,3)) when 1 then '男' when 0 then '女' else '' end ) as gender 
from 
	bs_activity_lucky_draw a left join bs_activity_award b on a.award_id = b.id LEFT JOIN bs_activity e ON a.activity_id = e.id, 
	bs_user c left join bs_agent d on c.agent_id = d.id 
where a.user_id = c.id 
    and.........
order by a.user_draw_time 
desc LIMIT ?, ?
```
### 现在逻辑
#### 涉及表及字段
1. bs_activity_lucky_draw(活动用户抽奖记录表)--中奖时间、交易编号、是否抽奖、是否中奖、订单号、年化出借额</br>
2. bs_activity_award(活动奖品表)--奖品</br>
3. bs_activity(活动表)--活动类型</br>
4. bs_user(注册用户信息表)--手机号码、性别、姓名</br>
5. bs_agent(渠道代理表)--渠道</br>
6. bs_product(产品信息表)--期限、产品终端</br>
7. bs_sub_account(子账户信息表)--产品名称、利率、购买时间、购买金额</br>
8. bs_red_packet_info(红包信息表)--使用红包</br>
9. bs_interest_ticket_info(加息券信息表)--使用加息券</br>
#### 表之间关联
bs_activity_lucky_draw的award_id和bs_activity_award的id关联</br>
bs_activity_lucky_draw的activity_id和bs_activity的id关联</br>
bs_activity_lucky_draw的order_no和bs_pay_order的order_no关联</br>
bs_activity_lucky_draw的coupon_id和bs_red_packet_info的id关联</br>
bs_activity_lucky_draw的coupon_id和bd_interest_ticket_info的id关联</br>
bs_user的agent_id和bs_agent的id关联</br>
bs_user的id和bs_activity_lucky_draw的user_id相等</br>
bs_sub_account的product_id和bs_product的id关联</br>
bs_sub_account的id和bs_activity_lucky_draw的sub_account_id相等</br>
#### 输出字段(增加字段)
1. 产品名称：bs_sub_account表的product_name字段
2. 产品终端：bs_product表的show_terminal字段
3. 订单号：bs_activity_lucky_draw的order_no字段
4. 购买时间：bs_sub_account的open_time字段
5. 购买金额：bs_sub_account的open_balance字段+bs_red_packet_info的amount字段   
6. 期限：bs_product的term字段
7. 利率：bs_sub_account的product_rate字段
8. 年化出借额：bs_activity_lucky_draw的year_interest字段
9. 使用红包：bs_red_packet_info的amount字段
10. 使用加息券：bs_interest_ticket_info的ticket_apr字段

```
select 
	e.name AS activity_name, 
	a.user_draw_time,
	c.id as user_id,
	c.user_name,
	(case if(length(c.id_card)=18, cast(substring(c.id_card,17,1) as UNSIGNED)%2, if(length(c.id_card)=15,cast(substring(c.id_card,15,1) as UNSIGNED)%2,3)) when 1 then '男' when 0 then '女' else '' end ) as gender,
	b.content as award_content,
	a.id,
	d.agent_name, 
	a.is_user_draw,
	a.is_win,
	h.product_name,
	i.show_terminal,
	a.order_no,
	h.open_time,
	if(a.coupon_type='1',h.open_balance+f.amount,h.open_balance) as purchasing_price,
	i.term,
	h.product_rate,
	a.year_interest,
	if(a.coupon_type='1',f.amount,null)as use_red_packet,
	if(a.coupon_type='2',g.ticket_apr,null)as use_ticket
from 
	bs_activity_lucky_draw a left join bs_activity_award b on a.award_id = b.id LEFT JOIN bs_activity e ON a.activity_id = e.id left join bs_red_packet_info f on a.coupon_id = f.id left join bs_interest_ticket_info g on a.coupon_id = g.id,
	bs_user c left join bs_agent d on c.agent_id = d.id,
	bs_sub_account h left JOIN bs_product i on h.product_id = i.id
	where a.user_id = c.id and a.sub_account_id = h.id
```