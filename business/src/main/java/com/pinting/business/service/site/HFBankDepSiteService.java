package com.pinting.business.service.site;

import com.pinting.business.model.vo.DepGuideVO;
import com.pinting.business.model.vo.WaiteActivateInfoVO;

/**
 * Author:      cyb
 * Date:        2017/4/12
 * Description: 存管引导相关（前端相关）
 */
public interface HFBankDepSiteService {

    /**
     * 根据用户查询存管引导信息
     * @param userId    用户ID
     * @return
     */
    DepGuideVO findDepGuideInfo(Integer userId);

    /**
     * 待激活用户界面信息
     * @param userId    用户ID
     * @return
     */
    WaiteActivateInfoVO waiteActivateInfo(Integer userId);

    /**
     * 恒丰存管激活用户
     * @param userId    用户ID
     */
    void activate(Integer userId);
}
