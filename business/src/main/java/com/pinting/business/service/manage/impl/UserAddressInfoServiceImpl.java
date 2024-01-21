package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.BsUserAddressInfoMapper;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.BsUserAddressInfo;
import com.pinting.business.model.BsUserAddressInfoExample;
import com.pinting.business.model.BsUserExample;
import com.pinting.business.model.vo.UserAddressInfoVO;
import com.pinting.business.service.manage.UserAddressInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户地址管理服务
 *
 * @author shh
 * @date 2018/5/28 14:37
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class UserAddressInfoServiceImpl implements UserAddressInfoService {

    private Logger log = LoggerFactory.getLogger(UserAddressInfoServiceImpl.class);

    @Autowired
    private BsUserAddressInfoMapper bsUserAddressInfoMapper;

    @Autowired
    private BsUserMapper bsUserMapper;

    @Override
    public List<UserAddressInfoVO> queryUserAddressInfoList(String userName, String mobile, Integer pageNum, Integer numPerPage) {
        int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
        List<UserAddressInfoVO> resultList = bsUserAddressInfoMapper.selecUserAddressInfoList(userName, mobile, start, numPerPage);
        return CollectionUtils.isEmpty(resultList) ? null : resultList;
    }

    @Override
    public Integer queryUserAddressInfoCount(String userName, String mobile) {
        Integer count = bsUserAddressInfoMapper.selectUserAddressInfoCount(userName, mobile);
        return count == null ? 0 : count;
    }

    @Override
    public int addUserAddressInfoSingle(BsUserAddressInfo record) {
        return bsUserAddressInfoMapper.insertSelective(record);
    }

    @Override
    public List<BsUserAddressInfo> uniquenessCheck(BsUserAddressInfo record) {
        BsUserAddressInfoExample userAddressInfoExample = new BsUserAddressInfoExample();
        userAddressInfoExample.createCriteria().andUserIdEqualTo(record.getUserId())
                .andConsigneeEqualTo(record.getConsignee())
                .andConsigneeMobileEqualTo(record.getConsigneeMobile())
                .andConsigneeAddressEqualTo(record.getConsigneeAddress());
        List<BsUserAddressInfo> resultList = bsUserAddressInfoMapper.selectByExample(userAddressInfoExample);
        return CollectionUtils.isEmpty(resultList) ? null : resultList;
    }

    @Override
    public List<BsUserAddressInfo> uniquenessCheckDetail(BsUserAddressInfo record) {
        BsUserAddressInfoExample userAddressInfoExample = new BsUserAddressInfoExample();
        userAddressInfoExample.createCriteria().andUserIdEqualTo(record.getUserId())
                .andConsigneeEqualTo(record.getConsignee())
                .andConsigneeMobileEqualTo(record.getConsigneeMobile())
                .andConsigneeAddressEqualTo(record.getConsigneeAddress())
                .andIsDefaultEqualTo(record.getIsDefault());
        List<BsUserAddressInfo> resultList = bsUserAddressInfoMapper.selectByExample(userAddressInfoExample);
        return CollectionUtils.isEmpty(resultList) ? null : resultList;
    }

    @Override
    public List<BsUser> queryUserByMobileAndName(String userName, String mobile) {
        BsUserExample userExample = new BsUserExample();
        userExample.createCriteria().andMobileEqualTo(mobile).andUserNameEqualTo(userName);
        List<BsUser> userList = bsUserMapper.selectByExample(userExample);
        return CollectionUtils.isEmpty(userList) ? null : userList;
    }

    @Override
    public Integer updateIsDefaultForNo(Integer userId) {
        Integer rows = bsUserAddressInfoMapper.updateIsDefaultForNo(userId);
        return rows == null ? 0 : rows;
    }

    @Override
    public Integer deleteByUserId(Integer userId) {
        BsUserAddressInfoExample userAddressInfoExample = new BsUserAddressInfoExample();
        userAddressInfoExample.createCriteria().andUserIdEqualTo(userId);
        Integer rows = bsUserAddressInfoMapper.deleteByExample(userAddressInfoExample);
        return rows;
    }

    @Override
    public void batchInsertUserAddressInfo(List<UserAddressInfoVO> effectiveList) {

        List<Integer> userIdList = new ArrayList<Integer>();
        HashMap<Integer, Object> dataMap = new HashMap<Integer, Object>();
        int userId = 0;
        // 1、批量插入
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());
        String sql = "insert into bs_user_address_info (user_id, consignee, consignee_mobile, consignee_address, is_default,note, create_time, update_time)  values";
        if(CollectionUtils.isNotEmpty(effectiveList)) {
            StringBuffer insert = new StringBuffer();
            for(UserAddressInfoVO obj : effectiveList) {
                insert.append("("+obj.getUserId()+",");
                insert.append("'"+obj.getConsignee()+"'"+",");
                insert.append("'"+obj.getConsigneeMobile()+"'"+",");
                insert.append("'"+obj.getConsigneeAddress()+"'"+",");
                insert.append("'NO'"+",");
                insert.append("''"+",");
                insert.append("'"+time+"'"+",");
                insert.append("'"+time+"'),");

                dataMap.put(obj.getUserId(), obj.getUserId());
            }
            sql += insert.toString();
            sql = sql.substring(0, sql.length()-1);
            log.info("用户地址批量导入sql："+sql);
            bsUserAddressInfoMapper.insertUserAddressInfo(sql);
        }

        for (Map.Entry<Integer, Object> entry : dataMap.entrySet()) {
            userIdList.add(entry.getKey());
        }

        // 2、批量更新，更新导入effectiveList，userId默认值 YES NO
        if(CollectionUtils.isNotEmpty(userIdList)) {
            bsUserAddressInfoMapper.updateIsDefaultListForNo(userIdList);
        }

        // 3、设置导入后的默认值
        List<BsUserAddressInfo> resultList = new ArrayList<BsUserAddressInfo>();
        if(CollectionUtils.isNotEmpty(userIdList)) {
            resultList = bsUserAddressInfoMapper.selectByUserId(userIdList);
        }

        // 地址信息表id集合
        List<Integer> idList = new ArrayList<Integer>();
        if(CollectionUtils.isNotEmpty(resultList)) {
            for(BsUserAddressInfo bsUserAddressInfo : resultList) {
                idList.add(bsUserAddressInfo.getId());
            }
        }
        if(CollectionUtils.isNotEmpty(idList)) {
            bsUserAddressInfoMapper.updateIsDefaultListForYes(idList);
        }
    }

    @Override
    public List<UserAddressInfoVO> queryDetailReview(Integer userId) {
        List<UserAddressInfoVO> resultList = bsUserAddressInfoMapper.selectDetailReview(userId);
        return CollectionUtils.isEmpty(resultList) ? null : resultList;
    }

    @Override
    public Integer deleteAddressInfoById(Integer id) {
        Integer rows = bsUserAddressInfoMapper.deleteByPrimaryKey(id);
        return rows == null ? 0 : rows;
    }

    @Override
    public BsUserAddressInfo queryAddressInfoById(Integer id) {
        return bsUserAddressInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<BsUserAddressInfo> queryAddressInfoByUserId(Integer userId) {
        BsUserAddressInfoExample userAddressInfoExample = new BsUserAddressInfoExample();
        userAddressInfoExample.createCriteria().andUserIdEqualTo(userId);
        List<BsUserAddressInfo> resultList = bsUserAddressInfoMapper.selectByExample(userAddressInfoExample);
        return CollectionUtils.isEmpty(resultList) ? null : resultList;
    }

    @Override
    public Integer updateAddressInfo(BsUserAddressInfo record) {
        Integer rows = bsUserAddressInfoMapper.updateByPrimaryKeySelective(record);
        return rows == null ? 0 : rows;
    }

    @Override
    public Integer updateLastRecordByUserId(Integer userId) {
        BsUserAddressInfo bsUserAddressInfo = bsUserAddressInfoMapper.selectLastRecordByUserId(userId);
        bsUserAddressInfo.setUpdateTime(new Date());
        bsUserAddressInfo.setIsDefault("YES");
        Integer rows = bsUserAddressInfoMapper.updateByPrimaryKeySelective(bsUserAddressInfo);
        return rows;
    }
}
