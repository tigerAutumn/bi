package com.pinting.business.service.manage;

import com.pinting.business.model.BsVipQuit;
import com.pinting.business.model.vo.VipQuitVO;

import java.util.List;

/**
 * 赞分期VIP退出申请服务-管理台
 * Created by shh on 2017/4/24.
 */
public interface VipQuitService {

    /**
     * 查询赞分期VIP退出申请-列表
     * @param record
     * @return
     */
    List<VipQuitVO> queryVipQuitList(VipQuitVO record);

    /**
     * 查询赞分期VIP退出申请-统计
     * @return
     */
    int queryVipQuitCount();

    /**
     * 申请退出
     * @param record
     * @return
     */
    int vipQuit(BsVipQuit record);

    /**
     * 退出审核
     * @param record
     * @return
     */
    int vipQuitCheck(BsVipQuit record);

    /**
     * 通过主键id查询
     * @param id
     * @return
     */
    BsVipQuit queryVipQuitById(Integer id);

}
