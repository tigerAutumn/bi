package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.BsProductTagMapper;
import com.pinting.business.model.BsProductTag;
import com.pinting.business.model.BsProductTagExample;
import com.pinting.business.service.manage.BsProductTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品标签服务实现类
 *
 * @author shh
 * @date 2018/5/2 16:19
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class BsProductTagServiceImpl implements BsProductTagService {

    @Autowired
    private BsProductTagMapper productTagMapper;

    @Override
    public int addProductTag(BsProductTag record) {
        return productTagMapper.insert(record);
    }

    @Override
    public int updatProductTag(BsProductTag record) {
        BsProductTagExample example = new BsProductTagExample();
        example.createCriteria().andProductIdEqualTo(record.getProductId()).andProductTagTypeEqualTo(record.getProductTagType());
        return productTagMapper.updateByExampleSelective(record, example);
    }

    @Override
    public BsProductTag qureyProductTagByProductId(int productId, String productTagType) {
        BsProductTagExample example = new BsProductTagExample();
        example.createCriteria().andProductIdEqualTo(productId).andProductTagTypeEqualTo(productTagType);
        List<BsProductTag> productTagList = productTagMapper.selectByExample(example);
        return productTagList.size() > 0 ? productTagList.get(0) : null;
    }
}
