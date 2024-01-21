package com.pinting.business.service.manage;

import com.pinting.business.model.vo.LoanNoticeVO;

import java.util.List;

/**
 * 驻留订单相关
 * Created by shh on 2016/10/9 15:24.
 */
public interface ResidentOrderService {

    /**
     * 驻留订单管理
     * @param orderNo 订单号
     * @return List<LoanNoticeVO>
     */
    List<LoanNoticeVO> residentOrderManage(String orderNo);

    /**
     * 添加驻留订单
     * @param orderNo
     * @return
     */
    int addResidentOrder(String orderNo,Integer orderStatus);
    
    /**
     * 删除驻留订单
     * @param orderNo 订单号
     */
    boolean deleteResidentOrder(String orderNo);

}
