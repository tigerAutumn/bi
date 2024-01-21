BsAuthMapper.selectByExample - ==>  Preparing: select id, user_id, mobile, mobile_code, mobile_time, mobile_times, mobile_code_use_times, mobile_last_time, email, email_code, email_time, email_code_use_times, email_last_time from bs_auth WHERE ( mobile = ? ) 
BsAuthMapper.selectByExample - ==> Parameters: 15558017627(String)
BsAuthMapper.selectByExample - <==      Total: 1

BsAuthMapper.selectByExample - ==>  Preparing: select id, user_id, mobile, mobile_code, mobile_time, mobile_times, mobile_code_use_times, mobile_last_time, email, email_code, email_time, email_code_use_times, email_last_time from bs_auth WHERE ( mobile = ? ) 
BsAuthMapper.selectByExample - ==> Parameters: 15558017627(String)
BsAuthMapper.selectByExample - <==      Total: 1

BsAuthMapper.updateByPrimaryKey - ==>  Preparing: update bs_auth set user_id = ?, mobile = ?, mobile_code = ?, mobile_time = ?, mobile_times = ?, mobile_code_use_times = ?, mobile_last_time = ?, email = ?, email_code = ?, email_time = ?, email_code_use_times = ?, email_last_time = ? where id = ? 
BsAuthMapper.updateByPrimaryKey - ==> Parameters: null, 15558017627(String), 4702(String), 2018-05-02 15:56:20.558(Timestamp), 1(Integer), 0(Integer), null, null, null, null, null, null, 143075(Integer)
BsAuthMapper.updateByPrimaryKey - <==    Updates: 1
