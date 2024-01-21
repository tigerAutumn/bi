package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.BsVipQuitMapper;
import com.pinting.business.model.BsVipQuit;
import com.pinting.business.model.vo.VipQuitVO;
import com.pinting.business.service.manage.VipQuitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 赞分期VIP退出申请服务-管理台
 * Created by shh on 2017/4/24.
 */
@Service
public class VipQuitServiceImpl implements VipQuitService {

    @Autowired
    private BsVipQuitMapper bsVipQuitMapper;

    @Override
    public List<VipQuitVO> queryVipQuitList(VipQuitVO record) {
        List<VipQuitVO> list = bsVipQuitMapper.selectVipQuitList(record);
        return (list != null && list.size() > 0) ? list : null;
    }

    @Override
    public int queryVipQuitCount() {
        return bsVipQuitMapper.selectVipQuitCount();
    }

    @Override
    public int vipQuit(BsVipQuit record) {
        return bsVipQuitMapper.insertSelective(record);
    }

    @Override
    public int vipQuitCheck(BsVipQuit record) {
        return bsVipQuitMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public BsVipQuit queryVipQuitById(Integer id) {
        return bsVipQuitMapper.selectByPrimaryKey(id);
    }
}
