package com.pinting.business.service.manage;


import com.pinting.business.model.vo.AscriptionChangeDetailVO;
import com.pinting.business.model.vo.AscriptionChangeUserInfoVO;

/**
 * Created by 剑钊 on 2016/6/24.
 */
public interface AscriptionService {

    /**
     * 查询变更前的理财人信息
     * @param req
     * @return
     */
    AscriptionChangeUserInfoVO queryOwnershipGrid(AscriptionChangeUserInfoVO req);

    /**
     * 查询变更详细信息
     * @param req
     * @return
     */
    AscriptionChangeDetailVO queryOwnershipDetail(AscriptionChangeDetailVO req);

    /**
     * 修改用户归属关系
     * @param req
     */
    void modifyOwnership(AscriptionChangeDetailVO req);
}
