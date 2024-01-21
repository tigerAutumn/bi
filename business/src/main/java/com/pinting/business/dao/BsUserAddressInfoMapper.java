package com.pinting.business.dao;

import com.pinting.business.model.BsUserAddressInfo;
import com.pinting.business.model.BsUserAddressInfoExample;
import java.util.List;

import com.pinting.business.model.vo.UserAddressInfoVO;
import org.apache.ibatis.annotations.Param;

public interface BsUserAddressInfoMapper {
    int countByExample(BsUserAddressInfoExample example);

    int deleteByExample(BsUserAddressInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserAddressInfo record);

    int insertSelective(BsUserAddressInfo record);

    List<BsUserAddressInfo> selectByExample(BsUserAddressInfoExample example);

    BsUserAddressInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUserAddressInfo record, @Param("example") BsUserAddressInfoExample example);

    int updateByExample(@Param("record") BsUserAddressInfo record, @Param("example") BsUserAddressInfoExample example);

    int updateByPrimaryKeySelective(BsUserAddressInfo record);

    int updateByPrimaryKey(BsUserAddressInfo record);

    /**
     * 用户地址管理列表查询
     * @param userName
     * @param mobile
     * @param start
     * @param numPerPage
     * @return
     */
    List<UserAddressInfoVO> selecUserAddressInfoList(@Param("userName") String userName, @Param("mobile") String mobile,
                                                     @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**
     * 用户地址管理记录数统计
     * @param userName
     * @param mobile
     * @return
     */
    Integer selectUserAddressInfoCount(@Param("userName") String userName, @Param("mobile") String mobile);

    /**
     * 根据userId修改，把与之对应的地址信息默认值为YES的记录，默认值更新为NO
     * @param userId
     * @return
     */
    Integer updateIsDefaultForNo(@Param("userId") Integer userId);

    /**
     * 用户地址信息批量导入
     * @param sql
     */
    void insertUserAddressInfo(@Param("sql")String sql);

    /**
     * 用户地址信息批量导入-库中已有的记录is_default值YES更新NO
     * @param userIdList
     * @return
     */
    Integer updateIsDefaultListForNo(@Param("userIdList") List<Integer> userIdList);

    /**
     * 查询需要更新的地址信息记录的id-批量导入
     * @param userIdList
     * @return
     */
    List<BsUserAddressInfo> selectByUserId(@Param("userIdList") List<Integer> userIdList);

    /**
     * 批量导入时把最后一条入库的地址信息，更新为默认地址
     * @param idList
     * @return
     */
    Integer updateIsDefaultListForYes(@Param("idList") List<Integer> idList);

    /**
     * 根据userId查询地址详情
     * @param userId
     * @return
     */
    List<UserAddressInfoVO> selectDetailReview(@Param("userId") Integer userId);

    /**
     * 根据userId 查找该用户最后一条入库的记录
     * @param userId
     * @return
     */
    BsUserAddressInfo selectLastRecordByUserId(@Param("userId") Integer userId);

}