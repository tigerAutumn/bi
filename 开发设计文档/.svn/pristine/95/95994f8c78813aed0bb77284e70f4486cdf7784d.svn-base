
-- 借款人逾期金额（当前对投资人已经处于逾期状态的所有借款的金额总和，仅统计本金）
-- 原sql
select ifnull(a.amount+b.amount, 0) from ( select ifnull(sum(c.plan_total), 0) as amount from ln_repay_schedule a, ln_loan b, ln_user d, ln_finance_repay_schedule c, ln_loan_relation e where a.`status` in ('INIT', 'LATE_NOT') and c.relation_id = e.id and a.serial_id = b.period and c.repay_serial = a.serial_id and e.loan_id = b.id and a.loan_id = b.id and d.id = b.ln_user_id and d.partner_code in ('YUN_DAI_SELF','7_DAI_SELF') and b.`status` = 'PAIED' and (to_days(now()) - to_days(a.plan_date)) > 0 ) a, ( select ifnull(sum(c.plan_total), 0) as amount from ln_repay_schedule a, ln_loan b, ln_user d, ln_finance_repay_schedule c, ln_loan_relation e where a.`status` in ('INIT', 'LATE_NOT') and c.relation_id = e.id and c.repay_serial = a.serial_id and e.loan_id = b.id and a.loan_id = b.id and d.id = b.ln_user_id and d.partner_code in('ZAN','ZSD') and b.`status` = 'PAIED' and (to_days(now()) - to_days(a.plan_date)) > 0 ) b;
-- 新sql
select ifnull(a.amount+b.amount, 0) from 
( select ifnull(sum(c.plan_total), 0) as amount from ln_repay_schedule a, ln_loan b, ln_user d, ln_finance_repay_schedule c, ln_loan_relation e 
        where a.`status` in ('LATE_NOT') and c.relation_id = e.id and a.serial_id = b.period and c.repay_serial = a.serial_id 
        and e.loan_id = b.id and a.loan_id = b.id and d.id = b.ln_user_id and d.partner_code in('YUN_DAI_SELF','7_DAI_SELF','ZAN') and b.`status` = 'PAIED' 
) a, 
( select ifnull(sum(c.plan_total), 0) as amount from ln_repay_schedule a, ln_loan b, ln_user d, ln_finance_repay_schedule c, ln_loan_relation e 
        where a.`status` in ('LATE_NOT') and c.relation_id = e.id and c.repay_serial = a.serial_id and e.loan_id = b.id and a.loan_id = b.id 
        and d.id = b.ln_user_id and d.partner_code in('ZAN','ZSD') and b.`status` = 'PAIED' 
) b ;

-- 借款人逾期笔数（当前对投资人处于逾期状态的所有借款的笔数之和）
-- 原sql
select ifnull(a.times+b.times, 0) from ( select count(a.id) as times from ln_repay_schedule a, ln_loan b, ln_user d, ln_finance_repay_schedule c, ln_loan_relation e where a.`status` in ('INIT', 'LATE_NOT') and c.relation_id = e.id and a.serial_id = b.period and c.repay_serial = a.serial_id and e.loan_id = b.id and a.loan_id = b.id and d.id = b.ln_user_id and d.partner_code in ('YUN_DAI_SELF','7_DAI_SELF') and b.`status` = 'PAIED' and (to_days(now()) - to_days(a.plan_date)) > 0 ) a, ( select count(a.id) as times from ln_repay_schedule a, ln_loan b, ln_user d, ln_finance_repay_schedule c, ln_loan_relation e where a.`status` in ('INIT', 'LATE_NOT') and c.relation_id = e.id and c.repay_serial = a.serial_id and e.loan_id = b.id and a.loan_id = b.id and d.id = b.ln_user_id and d.partner_code ='ZSD' and b.`status` = 'PAIED' and (to_days(now()) - to_days(a.plan_date)) > 0 ) b 
-- 新sql
select ifnull(a.times+b.times, 0) from 
( select count(a.id) as times from ln_repay_schedule a, ln_loan b, ln_user d, ln_finance_repay_schedule c, ln_loan_relation e 
        where a.`status` in ('LATE_NOT') and c.relation_id = e.id and a.serial_id = b.period and c.repay_serial = a.serial_id 
        and e.loan_id = b.id and a.loan_id = b.id and d.id = b.ln_user_id and d.partner_code in('YUN_DAI_SELF','7_DAI_SELF','ZAN') and b.`status` = 'PAIED' 
) a, 
( select count(a.id) as times from ln_repay_schedule a, ln_loan b, ln_user d, ln_finance_repay_schedule c, ln_loan_relation e 
        where a.`status` in ('LATE_NOT') and c.relation_id = e.id and c.repay_serial = a.serial_id and e.loan_id = b.id and a.loan_id = b.id 
        and d.id = b.ln_user_id and d.partner_code ='ZSD' and b.`status` = 'PAIED' 
) b ;

-- 累计代偿金额（平台自成立以来，累计的代偿金额，包括本金和利息）
-- 原sql
select ifnull(a.amount + b.amount + c.amount, 0) from (
        select (sum(ifnull(principal.plan_amount, 0) + ifnull(interest.plan_amount,0))) as amount
        from ln_repay_schedule a, ln_repay_schedule_detail principal, ln_repay_schedule_detail interest, ln_loan b, ln_user c
        where a.`status` in ('LATE_NOT', 'LATE_REPAIED')  and principal.subject_code = 'PRINCIPAL' and interest.subject_code = 'INTEREST'
        and principal.plan_id = a.id and interest.plan_id = a.id and a.loan_id = b.id and b.ln_user_id = c.id and c.partner_code = 'ZAN'
) as a, (
        select ifnull(sum(b.principal + b.interest),0) as amount from ln_compensate a, ln_compensate_detail b
        where a.id = b.compensate_id and b.`status` = 'SUCC' and a.partner_code in ('YUN_DAI_SELF', 'ZSD')
) as b, (
        select ifnull(sum(a.total_mount), 0) as amount from ln_compensate a, ln_compensate_detail b
        where a.id = b.compensate_id and b.`status` = 'SUCC' and a.partner_code = '7_DAI_SELF'
) as c;
-- 新sql
select ifnull(a.amount+b.amount, 0) from 
( select ifnull(sum(c.plan_total), 0) as amount from ln_repay_schedule a, ln_loan b, ln_user d, ln_finance_repay_schedule c, ln_loan_relation e 
	where a.`status` in ('LATE_NOT', 'LATE_REPAIED') and c.relation_id = e.id and a.serial_id = b.period and c.repay_serial = a.serial_id 
	and e.loan_id = b.id and a.loan_id = b.id and d.id = b.ln_user_id and d.partner_code in('YUN_DAI_SELF','7_DAI_SELF') and b.`status` = 'PAIED' 
) a, 
( select ifnull(sum(c.plan_total), 0) as amount from ln_repay_schedule a, ln_loan b, ln_user d, ln_finance_repay_schedule c, ln_loan_relation e 
	where a.`status` in ('LATE_NOT', 'LATE_REPAIED') and c.relation_id = e.id and c.repay_serial = a.serial_id and e.loan_id = b.id and a.loan_id = b.id 
	and d.id = b.ln_user_id and d.partner_code in('ZAN','ZSD') and b.`status` = 'PAIED' and c.done_time>='2017-9-20'
) b;


-- 累计代偿笔数（平台自成立以来，累计代偿的笔数之和）-- 利息代偿也算，云贷、七贷、赞时贷 根据通知统计
-- 原sql
select ifnull(a.times + b.times + c.times, 0) from (
    select count(distinct a.id) as times
    from ln_repay_schedule a, ln_repay_schedule_detail principal, ln_repay_schedule_detail interest, ln_loan b, ln_user c
    where a.`status` in ('LATE_NOT', 'LATE_REPAIED')  and principal.subject_code = 'PRINCIPAL' and interest.subject_code = 'INTEREST'
    and principal.plan_id = a.id and interest.plan_id = a.id and a.loan_id = b.id and b.ln_user_id = c.id and c.partner_code = 'ZAN'
) as a, (
    select count(distinct b.id) as times from ln_compensate a, ln_compensate_detail b
    where a.id = b.compensate_id and b.`status` = 'SUCC' and a.partner_code in ('YUN_DAI_SELF', 'ZSD')
) as b, (
    select count(distinct b.id) as times from ln_compensate a, ln_compensate_detail b
    where a.id = b.compensate_id and b.`status` = 'SUCC' and a.partner_code = '7_DAI_SELF'
) as c;
-- 新sql
select ifnull(a.amount+b.amount, 0) from 
( select ifnull(sum(c.plan_total), 0) as amount from ln_repay_schedule a, ln_loan b, ln_user d, ln_finance_repay_schedule c, ln_loan_relation e 
        where a.`status` in ('LATE_NOT', 'LATE_REPAIED') and c.relation_id = e.id and a.serial_id = b.period and c.repay_serial = a.serial_id 
        and e.loan_id = b.id and a.loan_id = b.id and d.id = b.ln_user_id and d.partner_code in('YUN_DAI_SELF','7_DAI_SELF') and b.`status` = 'PAIED' 
) a, 
( select ifnull(sum(c.plan_total), 0) as amount from ln_repay_schedule a, ln_loan b, ln_user d, ln_finance_repay_schedule c, ln_loan_relation e 
        where a.`status` in ('LATE_NOT', 'LATE_REPAIED') and c.relation_id = e.id and c.repay_serial = a.serial_id and e.loan_id = b.id and a.loan_id = b.id 
        and d.id = b.ln_user_id and d.partner_code in('ZAN','ZSD') and b.`status` = 'PAIED' and c.done_time>='2017-9-20'
) b ;



-- 需要运行的脚本
INSERT INTO `bs_schedule_config` (`job_name`, `job_group`, `job_status`, `cron_expression`, `bean_class`, `method_name`, `bean_id`, `is_concurrent`, `description`, `create_time`, `update_time`) VALUES ('platformDataTaskTime', 'SCHEDULE', 'RUNNING', '0 0 0/1 * * ?', 'com.pinting.business.dayend.task.PlatformDataTask', 'overview', 'platformDataTask', 'NO', '平台运营概况以及成交及出借数据统计', '2018-03-12 17:42:54', '2018-03-12 18:10:07');
INSERT INTO `bs_schedule_config` (`job_name`, `job_group`, `job_status`, `cron_expression`, `bean_class`, `method_name`, `bean_id`, `is_concurrent`, `description`, `create_time`, `update_time`) VALUES ('platformDataUserDataTaskTime', 'SCHEDULE', 'RUNNING', '0 0 1 1 * ?', 'com.pinting.business.dayend.task.PlatformDataTask', 'userData', 'platformDataTask', 'NO', '用户数据统计以及逾期及代偿数据统计', '2018-03-12 17:42:54', '2018-03-12 18:10:12');
