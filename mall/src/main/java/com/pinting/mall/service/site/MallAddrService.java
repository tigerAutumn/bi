package com.pinting.mall.service.site;

import com.pinting.mall.model.MallConsigneeAddressInfo;

/**
 * 积分商城用户地址相关
 *
 * @author zousheng
 * @project mall
 * @2018-5-16 下午2:09:33
 */
public interface MallAddrService {

    /**
     * 根据用户id查询用户收货地址信息
     *
     * @param userId
     * @return
     * @author zousheng
     */
    MallConsigneeAddressInfo getAddressInfoByUserId(Integer userId);

    /**
     * 保存收货地址信息：如果已有收货地址发生变更，则原有收货地址设置删除，新增收货地址
     *
     * @param userId 用户编号ID
     * @param recName 收货人姓名
     * @param recMobile 收货人手机号
     * @param recAdress 收货人省市区地址
     * @param recAdressDetail 收货人详细地址
     * @return
     */
    MallConsigneeAddressInfo saveAddressInfo(Integer userId, String recName, String recMobile, String recAdress, String recAdressDetail);
}
