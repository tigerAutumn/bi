-- 1、债权明细 selectDepClaimsListBySubAccountIdNew

SELECT
	tab.id as ln_loan_relation_id,
	tab.init_amount, tab.left_amount, tab.create_time, tab.update_time, tab.trans_mark,
	tab.status,
	tab.loan_name, tab.id_card,
	tab.interest_begin_date,
	tab.open_time,
	tab.balance,
	tab.open_balance,
	tab.loan_time,
	tab.partner_business_flag,
	g.create_time AS transfer_time,
	tab.partner_code
FROM
(
SELECT a.id,a.bs_sub_account_id, a.ln_user_id, a.loan_id, a.init_amount, a.left_amount, a.create_time, a.update_time, a.trans_mark,
	a.status, b.user_name AS loan_name, b.id_card, b.partner_code,
	c.interest_begin_date,
	c.open_time,
	c.balance,
	c.open_balance,
	f.loan_time,
	f.partner_business_flag
FROM
(
	SELECT a.id,a.bs_sub_account_id, a.ln_user_id, a.loan_id, a.init_amount, a.left_amount, a.create_time, a.update_time, a.trans_mark,
	a.status FROM ln_loan_relation a
	where a.status IN ('SUCCESS', 'TRANSFERRED', 'REPAID') AND a.bs_sub_account_id = 268026
	LIMIT 0,10
) a, ln_user b, bs_sub_account c, ln_loan f
where
a.ln_user_id = b.id and a.bs_sub_account_id = c.id and a.loan_id = f.id AND f.status = 'PAIED'
) tab
LEFT JOIN ln_credit_transfer g ON CASE WHEN tab.status = 'TRANSFERRED' THEN tab.id = g.out_loan_relation_id ELSE tab.id = g.in_loan_relation_id END
ORDER BY field(tab.`status`, 'SUCCESS', 'TRANSFERRED', 'REPAID'), tab.update_time DESC;


-- 2、存管港湾产品-借款协议
-- a、根据借款编号查询理财人数据 
-- selectCustodyFinancialManagement
SELECT
	a.id AS loan_relation_id,
	c.user_name,
	c.mobile AS user_mobile,
	c.id_card,
	d.period,
	a.init_amount
FROM
ln_loan_relation a, bs_user c, ln_loan d
WHERE a.loan_id = 123
AND a.bs_user_id=c.id
AND d.id = a.loan_id
AND a.trans_mark NOT IN ('TRANS_IN');


-- b、乙方（借款人）借款人信息
根据主键查询 ln_user
ln_user.user_name
ln_user.id_card
ln_user.mobile
ln_loan.address
ln_loan.email

-- c、借款基本信息
根据主键查询 ln_loan

ln_loan.period 借款期限
ln_loan.purpose 借款用途
ln_loan.agreement_rate 年化利率
ln_loan.approve_amount 出借金额
ln_loan.address
ln_loan.email
ln_loan.loan_time  借款出借日
借款到期日 要计算



-- 3、存管港湾产品-债转协议


-- 根据loanRelationId查询ln_loan_relation的债转状态status、债转标记trans_mark


-- 存管港湾产品-债权转让协议 根据借贷关系编号查询债权转让数据 selectCustodyTransferClaims

SELECT
	a.in_sub_account_id, 
  b.first_term first_term,
	a.amount, a.in_amount, -- 转让债权金额 / 转让价格
  a.create_time transfer_time, -- 转让时间
	uout.id out_user_id, 
  uin.id in_user_id,
	uout.user_name out_user_name, -- 出让人姓名
  uin.user_name in_user_name, -- 受让人姓名
	uout.id_card out_user_idCardNo, -- 出让人身份证
  uin.id_card in_user_idCardNo, -- 受让人姓名
	c.user_name borrow_user_name, -- 借款人姓名
  c.id_card borrow_user_idCardNo, -- 借款人身份证
	d.period term, -- 还款期限
  d.approve_amount, -- 借款本金
  d.agreement_rate, 
  d.partner_business_flag
FROM ln_credit_transfer a,
ln_loan_relation b,
bs_user uin, bs_user uout,
ln_user c, ln_loan d
WHERE a.out_user_id = uout.id AND a.in_user_id = uin.id
AND b.id=a.in_loan_relation_id AND b.ln_user_id=c.id
AND b.loan_id = d.id
AND (a.in_loan_relation_id = 123 OR a.out_loan_relation_id = 123) -- loanRelationId
ORDER BY a.create_time DESC limit 1;

