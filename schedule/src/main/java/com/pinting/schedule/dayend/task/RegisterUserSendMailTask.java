package com.pinting.schedule.dayend.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.schedule.mongodb.service.MongoUserService;

@Service
public class RegisterUserSendMailTask {

	@Autowired
	MongoUserService mongoUserService;
	
    // 定时任务执行方法
    public void execute() {
    	mongoUserService.sendRegisterUserInfoMail();
    }
    
}
