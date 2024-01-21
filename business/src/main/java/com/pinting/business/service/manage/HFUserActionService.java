package com.pinting.business.service.manage;

import com.pinting.business.model.vo.UpdateHFUserApplyVO;

import java.util.List;

/**
 * Created by cyb on 2017/10/16.
 */
public interface HFUserActionService {

    /**
     * 申请更改用户手机号
     * @param userName  用户姓名
     * @param mobile    用户当前手机号
     * @param startTime 最近操作时间-开始时间
     * @param endTime   最近操作时间-结束时间
     * @param mUser     操作客服
     * @return
     */
    List<UpdateHFUserApplyVO> applyQuery(String userName, String mobile, String cardMobile, String startTime,
                                         String endTime, String mUser, Integer pageNum, Integer numPerPage);

    int applyQuery(String userName, String mobile, String cardMobile, String startTime,
                   String endTime, String mUser);

    /**
     * 确认更改用户手机号
     * @param userName  用户姓名
     * @param mobile    用户当前手机号
     * @param startTime 最近操作时间-开始时间
     * @param endTime   最近操作时间-结束时间
     * @param mUser     操作客服
     * @return
     */
    List<UpdateHFUserApplyVO> passQuery(String userName, String mobile, String cardMobile, String startTime,
                                        String endTime, String mUser, Integer pageNum, Integer numPerPage);

    int passQuery(String userName, String mobile, String cardMobile, String startTime,
                  String endTime, String mUser);

    /**
     * 申请更新恒丰用户信息
     * @param userId
     * @param mUserId
     * @param mobile
     */
    void apply(Integer userId, Integer mUserId, String mobile);

    /**
     * 更新恒丰用户信息
     * @param id
     * @param mUserId
     */
    void updatePlatAcct(Integer id, Integer mUserId);

}
