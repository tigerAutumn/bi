package com.pinting.business.service.manage;

import com.pinting.business.model.vo.BsTicketPreDetailVO;

import java.util.List;

public interface BsTicketPreDetailService {
    /**
     * 通过serialNo查询发放卡券用户信息
     * @param req
     * @return
     */
    public List<BsTicketPreDetailVO> getBsUserBySerialNo(BsTicketPreDetailVO req);

    /**
     * 通过serialNo查询发放卡券用户信息数量
     * @param req
     * @return
     */
    public int getCountBySerialNo(BsTicketPreDetailVO req);
}
