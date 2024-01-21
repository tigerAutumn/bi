package com.pinting.business.service.manage;

import com.pinting.business.model.BsUser;
import com.pinting.business.model.BsUserAddressInfo;
import com.pinting.business.model.vo.UserAddressInfoVO;

import java.util.List;

/**
 * 用户地址管理服务
 *
 * @author shh
 * @date 2018/5/28 14:32
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public interface UserAddressInfoService {

    /**
     * 用户地址管理列表查询
     * @param userName
     * @param mobile
     * @param pageNum
     * @param numPerPage
     * @return
     */
    public List<UserAddressInfoVO> queryUserAddressInfoList(String userName, String mobile,
                                                            Integer pageNum, Integer numPerPage);

    /**
     * 用户地址管理记录数统计
     * @param userName
     * @param mobile
     * @return
     */
    public Integer queryUserAddressInfoCount(String userName, String mobile);

    /**
     * 用户地址管理保存-单条记录
     * @param record
     * @return
     */
    public int addUserAddressInfoSingle(BsUserAddressInfo record);

    /**
     * 校验地址信息是否存在-列表页新增
     * 1、先根据姓名、注册手机号查询用户是否存在
     * 2、数据唯一性校验：userId、收货人、收货手机号、收货地址
     * @param record
     * @return
     */
    public List<BsUserAddressInfo> uniquenessCheck(BsUserAddressInfo record);

    /**
     * 校验地址信息是否存在-详情页唯一性校验
     * 1、先根据姓名、注册手机号查询用户是否存在
     * 2、数据唯一性校验：userId、收货人、收货手机号、收货地址、默认地址
     * @param record
     * @return
     */
    public List<BsUserAddressInfo> uniquenessCheckDetail(BsUserAddressInfo record);


    /**
     * 根据姓名、注册手机号查询用户是否存在
     * @param userName
     * @param mobile
     * @return
     */
    public List<BsUser> queryUserByMobileAndName(String userName, String mobile);

    /**
     * 根据userId修改地址信息，把与之对应的地址信息默认值为YES的记录，默认值更新为NO
     * @param userId
     * @return
     */
    public Integer updateIsDefaultForNo(Integer userId);

    /**
     * 根据userId删除地址信息
     * @param userId
     * @return
     */
    public Integer deleteByUserId(Integer userId);

    /**
     * 用户地址信息批量导入
     * 1、更新导入effectiveList，userId默认值
     * 2、设置导入后的默认值
     * @param effectiveList
     */
    public void batchInsertUserAddressInfo(List<UserAddressInfoVO> effectiveList);

    /**
     * 根据userId查询地址详情
     * @param userId
     * @return
     */
    public List<UserAddressInfoVO> queryDetailReview(Integer userId);

    /**
     * 根据主键删除地址信息表的记录
     * @param id
     * @return
     */
    public Integer deleteAddressInfoById(Integer id);

    /**
     * 根据主键查询地址信息表的记录
     * @param id
     * @return
     */
    public BsUserAddressInfo queryAddressInfoById(Integer id);

    /**
     * 根据userId查询地址信息表的记录
     * @param userId
     * @return
     */
    public List<BsUserAddressInfo> queryAddressInfoByUserId(Integer userId);

    /**
     * 更新地址信息表的记录
     * @param record
     * @return
     */
    public Integer updateAddressInfo(BsUserAddressInfo record);

    /**
     * 根据userId 更新该用户最后一条入库的记录，设置为默认地址
     * @param userId
     * @return
     */
    public Integer updateLastRecordByUserId(Integer userId);

}
