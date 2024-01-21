BsUserMapper.selectByPrimaryKey - ==>  Preparing: select id, nick, user_name, mobile, urgent_name, urgent_mobile, relation, email, password, pay_password, login_time, logout_time, login_fail_times, login_fail_time, login_always_fail_times, login_lock_time, pay_fail_times, pay_fail_time, id_card, status, is_bind_name, is_bind_bank, current_interest, current_bonus, total_interest, total_bonus, can_withdraw, total_trans, recommend_id, create_channel, user_type, agent_id, register_time, reg_terminal, first_buy_time, return_path, recent_bank_card_id, last_app_version, last_app_time from bs_user where id = ? 
BsUserMapper.selectByPrimaryKey - ==> Parameters: 2000056(Integer)
BsUserMapper.selectByPrimaryKey - <==      Total: 1

-- 【待定，看看】
BsActiveUserRecordMapper.selectByRecord - ==>  Preparing: select id, user_id, terminal_type, login_date, src_url, create_time, update_time from bs_active_user_record where user_id = ? and terminal_type = ? and login_date = ? 
BsActiveUserRecordMapper.selectByRecord - ==> Parameters: 2000056(Integer), null, 2018-04-28(Date)
BsActiveUserRecordMapper.selectByRecord - <==      Total: 0

-- 【可删除】查询欧洲杯产品【删除】
BsProductMapper.selectEcupProductGrid - ==>  Preparing: SELECT a.* FROM bs_product a, bs_user_type_auth u WHERE a.`status` IN (5,6,7) AND u.product_id = a.id AND a.activity_type = 'NORMAL' AND DATE(a.start_time) = DATE(NOW()) AND a.`name` LIKE "%欧洲杯新手专享%" ORDER BY a.start_time DESC 
BsProductMapper.selectEcupProductGrid - ==> Parameters: 
BsProductMapper.selectEcupProductGrid - <==      Total: 0
BsProductMapper.selectEcupProductGrid - ==>  Preparing: SELECT a.* FROM bs_product a, bs_user_type_auth u WHERE a.`status` IN (5,6,7) AND u.product_id = a.id AND a.activity_type = 'NORMAL' AND DATE(a.start_time) < DATE(NOW()) AND a.`name` LIKE "%欧洲杯新手专享%" ORDER BY a.start_time DESC 
BsProductMapper.selectEcupProductGrid - ==> Parameters: 
BsProductMapper.selectEcupProductGrid - <==      Total: 3

BsUserMapper.countByExample - ==>  Preparing: select count(*) from bs_user WHERE ( mobile = ? and status = ? ) 
BsUserMapper.countByExample - ==> Parameters: 15868150199(String), 1(Integer)
BsUserMapper.countByExample - <==      Total: 1

UcUserMapper.selectByExample - ==>  Preparing: select id, mobile, hf_user_id, user_name, id_card, status, create_time, update_time from uc_user WHERE ( mobile = ? and status = ? ) 
UcUserMapper.selectByExample - ==> Parameters: 15868150199(String), OPEN(String)
UcUserMapper.selectByExample - <==      Total: 2

UcUserExtMapper.selectByExample - ==>  Preparing: select id, uc_user_id, user_type, user_id, create_time from uc_user_ext WHERE ( uc_user_id = ? ) 
UcUserExtMapper.selectByExample - ==> Parameters: 10779734(Integer)
UcUserExtMapper.selectByExample - <==      Total: 1

2018-04-28 14:08:02,232 [qtp1078912108-28] ERROR com.pinting.business.hessian.site.service.SiteHessianServiceImpl - ======交易[User-InfoValidation]发生错误： com.pinting.core.exception.PTException: com.pinting.business.exception.PTMessageException: 手机已存在======

BsAuthMapper.selectByExample - ==>  Preparing: select id, user_id, mobile, mobile_code, mobile_time, mobile_times, mobile_code_use_times, mobile_last_time, email, email_code, email_time, email_code_use_times, email_last_time from bs_auth WHERE ( mobile = ? ) 
BsAuthMapper.selectByExample - ==> Parameters: 15868150199(String)
BsAuthMapper.selectByExample - <==      Total: 1
BsAuthMapper.selectByExample - ==>  Preparing: select id, user_id, mobile, mobile_code, mobile_time, mobile_times, mobile_code_use_times, mobile_last_time, email, email_code, email_time, email_code_use_times, email_last_time from bs_auth WHERE ( mobile = ? ) 
BsAuthMapper.selectByExample - ==> Parameters: 15868150199(String)
BsAuthMapper.selectByExample - <==      Total: 1
BsAuthMapper.updateByPrimaryKey - ==>  Preparing: update bs_auth set user_id = ?, mobile = ?, mobile_code = ?, mobile_time = ?, mobile_times = ?, mobile_code_use_times = ?, mobile_last_time = ?, email = ?, email_code = ?, email_time = ?, email_code_use_times = ?, email_last_time = ? where id = ? 
BsAuthMapper.updateByPrimaryKey - ==> Parameters: null, 15868150199(String), 1124(String), 2018-04-28 14:08:03.145(Timestamp), 1(Integer), 0(Integer), null, null, null, null, null, null, 143089(Integer)
BsAuthMapper.updateByPrimaryKey - <==    Updates: 1
