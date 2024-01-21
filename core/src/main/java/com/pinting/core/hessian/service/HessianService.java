package com.pinting.core.hessian.service;

/***********************************************************************
 * Module:  HessianService.java
 * Author:  dingpf
 * Purpose: Defines the Interface HessianService
 ***********************************************************************/

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.core.hessian.msg.ResMsg;

/** HessianService 父接口 */
public interface HessianService {
   /** 
    * 负责 消息请求接收及响应
    * @return ResMsg
    * @param reqMsg
    * */
   public ResMsg handleMsg(ReqMsg reqMsg);

}