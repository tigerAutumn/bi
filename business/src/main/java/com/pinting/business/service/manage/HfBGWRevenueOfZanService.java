package com.pinting.business.service.manage;

import com.pinting.business.model.vo.HFBGWRevenueOfZanVO;

import java.util.List;

/**
 * 查询恒丰币港湾营收（赞分期）
 * Created by cyb on 2018/4/20.
 */
public interface HfBGWRevenueOfZanService {

    /**
     * 查询恒丰币港湾营收（赞分期）
     * @param startTime     开始时间（可以为空）
     * @param endTime       结束时间（可以为空）
     * @param pageNum       分页开始页码（1开始）（可以为空）
     * @param numPerPage    分页每页展示条数（可以为空）
     * @return
     */
    List<HFBGWRevenueOfZanVO> queryHFBGWRevenueOfZan(String startTime, String endTime, Integer pageNum, Integer numPerPage);

    /**
     * 查询恒丰币港湾营收（赞分期）总条数
     * @param startTime     开始时间（可以为空）
     * @param endTime       结束时间（可以为空）
     * @return
     */
    int countHFBGWRevenueOfZan(String startTime,  String endTime);

    /**
     *币港湾营收（恒丰）总计
     * @param startTime     开始时间（可以为空）
     * @param endTime       结束时间（可以为空）
     * @return
     */
    Double sumHFBGWRevenueOfZan(String startTime,  String endTime);

}
