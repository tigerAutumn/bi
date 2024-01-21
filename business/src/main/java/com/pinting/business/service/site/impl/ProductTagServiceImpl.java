package com.pinting.business.service.site.impl;

import com.pinting.business.dao.BsProductTagMapper;
import com.pinting.business.model.vo.BsProductTagContentVO;
import com.pinting.business.service.site.ProductTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 产品标签服务-site层使用
 *
 * @author shh
 * @date 2018/5/2 20:13
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class ProductTagServiceImpl implements ProductTagService {

    @Autowired
    private BsProductTagMapper bsProductTagMapper;

    @Override
    public BsProductTagContentVO queryProductTagContentById(int productId) {
        BsProductTagContentVO contentVo = bsProductTagMapper.selectProductTagContentById(productId);
        return contentVo;
    }
}
