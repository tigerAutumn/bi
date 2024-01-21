package com.pinting.mall.service.site.impl;

import com.pinting.mall.dao.MallConsigneeAddressInfoMapper;
import com.pinting.mall.enums.MallTrueAndFalseEnum;
import com.pinting.mall.model.MallConsigneeAddressInfo;
import com.pinting.mall.model.MallConsigneeAddressInfoExample;
import com.pinting.mall.service.site.MallAddrService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MallAddrServiceImpl implements MallAddrService {

    private Logger logger = LoggerFactory.getLogger(MallAddrServiceImpl.class);
    @Autowired
    private MallConsigneeAddressInfoMapper addressInfoMapper;

    @Override
    public MallConsigneeAddressInfo getAddressInfoByUserId(Integer userId) {
        MallConsigneeAddressInfoExample example = new MallConsigneeAddressInfoExample();
        example.createCriteria().andUserIdEqualTo(userId)
                .andIsDeleteEqualTo(MallTrueAndFalseEnum.FALSE.getCode())
                .andIsDefaultEqualTo(MallTrueAndFalseEnum.TRUE.getCode());
        List<MallConsigneeAddressInfo> list = addressInfoMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public MallConsigneeAddressInfo saveAddressInfo(Integer userId, String recName, String recMobile, String recAdress, String recAdressDetail) {

        MallConsigneeAddressInfo addressInfo = getAddressInfoByUserId(userId);
        if (addressInfo == null) {
            addressInfo = new MallConsigneeAddressInfo();
            addressInfo.setUserId(userId);
            addressInfo.setRecName(recName);
            addressInfo.setRecMobile(recMobile);
            addressInfo.setRecAdress(recAdress);
            addressInfo.setRecAdressDetail(recAdressDetail);
            addressInfo.setIsDefault(MallTrueAndFalseEnum.TRUE.getCode());
            addressInfo.setIsDelete(MallTrueAndFalseEnum.FALSE.getCode());
            addressInfo.setCreateTime(new Date());
            addressInfo.setUpdateTime(new Date());
            addressInfoMapper.insertSelective(addressInfo);
            return addressInfo;
        } else {
            if (addressInfo.getRecName().equals(recName) && addressInfo.getRecMobile().equals(recMobile)
                    && addressInfo.getRecAdress().equals(recAdress) && addressInfo.getRecAdressDetail().equals(recAdressDetail)) {
                return addressInfo;
            } else {
                addressInfo.setIsDelete(MallTrueAndFalseEnum.TRUE.getCode());
                addressInfo.setUpdateTime(new Date());
                addressInfoMapper.updateByPrimaryKeySelective(addressInfo);

                addressInfo = new MallConsigneeAddressInfo();
                addressInfo.setUserId(userId);
                addressInfo.setRecName(recName);
                addressInfo.setRecMobile(recMobile);
                addressInfo.setRecAdress(recAdress);
                addressInfo.setRecAdressDetail(recAdressDetail);
                addressInfo.setIsDefault(MallTrueAndFalseEnum.TRUE.getCode());
                addressInfo.setIsDelete(MallTrueAndFalseEnum.FALSE.getCode());
                addressInfo.setCreateTime(new Date());
                addressInfo.setUpdateTime(new Date());
                addressInfoMapper.insertSelective(addressInfo);
                return addressInfo;
            }
        }
    }
}
