package com.pinting.business.service.manage;

import com.pinting.business.model.BsProductTag;

/**
 * 产品标签服务
 *
 * @author shh
 * @date 2018/5/2 16:18
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public interface BsProductTagService {

    /**
     * 添加产品标签
     * @param record
     * @return
     */
    public int addProductTag(BsProductTag record);

    /**
     * 更新产品标签信息
     * @param record
     * @return
     */
    public int updatProductTag(BsProductTag record);

    /**
     * 根据产品编号，产品标签类型查询产品标签
     * @param productId
     * @param productTagType
     * @return
     */
    public BsProductTag qureyProductTagByProductId(int productId, String productTagType);

}
