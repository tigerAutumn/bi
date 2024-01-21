package com.pinting.gateway.baofoo.out.service;

import com.pinting.gateway.baofoo.out.model.req.BindCardReq;
import com.pinting.gateway.baofoo.out.model.req.BindRelationQueryReq;
import com.pinting.gateway.baofoo.out.model.req.PrepareBindCardReq;
import com.pinting.gateway.baofoo.out.model.req.UnbindCardReq;
import com.pinting.gateway.baofoo.out.model.resp.BindCardResp;
import com.pinting.gateway.baofoo.out.model.resp.BindRelationQueryResp;
import com.pinting.gateway.baofoo.out.model.resp.PrepareBindCardResp;
import com.pinting.gateway.baofoo.out.model.resp.UnbindCardResp;

/**
 * Created by 剑钊 on 2016/7/18.
 */
public interface BindCardService {

    /**
     * 预绑卡类交易
     * @param req
     * @return
     */
    PrepareBindCardResp prepareBindCard(PrepareBindCardReq req) throws Exception;

    /**
     * 确认绑卡交易
     * @param req
     * @return
     */
    BindCardResp bindCard(BindCardReq req)throws Exception;

    /**
     * 解绑关系类交易
     * @param req
     * @return
     * @throws Exception
     */
    UnbindCardResp unbindCard(UnbindCardReq req)throws Exception;

    /**
     * 查询绑定关系类交易
     * @param req
     * @return
     */
    BindRelationQueryResp bindRelationQuery(BindRelationQueryReq req) throws Exception;

}
