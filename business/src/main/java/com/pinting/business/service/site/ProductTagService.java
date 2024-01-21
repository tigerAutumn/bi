package com.pinting.business.service.site;

import com.pinting.business.model.vo.BsProductTagContentVO;

/**
 * 产品标签服务-site层使用
 *
 * @author shh
 * @date 2018/5/2 20:12
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public interface ProductTagService {

    /**
     * 根据产品id查询提醒、加息标签的内容
     * @param productId
     * @return
     */
    public BsProductTagContentVO queryProductTagContentById(int productId);
}
