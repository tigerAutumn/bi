package com.pinting.business.service.site.impl;

import com.pinting.business.dao.BsAppActiveMapper;
import com.pinting.business.hessian.site.message.ReqMsg_AppActive_QueryActivePage;
import com.pinting.business.hessian.site.message.ResMsg_AppActive_QueryActivePage;
import com.pinting.business.model.BsAppActive;
import com.pinting.business.model.vo.BsAppActiveVO;
import com.pinting.business.service.site.AppActiveService;
import com.pinting.business.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
/**
 * Author:      shh
 * Date:        2017/7/7
 * Description: H5活动中心
 */
@Service
public class AppActiveServiceImpl implements AppActiveService {

    @Autowired
    private BsAppActiveMapper appActiveMapper;

    @Override
    public void queryActivePage(ReqMsg_AppActive_QueryActivePage req, ResMsg_AppActive_QueryActivePage res) {
        Date now = new Date();
        List<BsAppActive> list = appActiveMapper.selectActivePage(req.getShowTerminal(), req.getStart(), req.getNumPerPage());

        int count = 0;
        count = appActiveMapper.selectActivePage(req.getShowTerminal(), 0, Integer.MAX_VALUE).size();
        res.setCount(count);

        List<BsAppActiveVO> data = new ArrayList<BsAppActiveVO>();

        if(!CollectionUtils.isEmpty(list)) {
            //预热中/进行中的活动集合
            List<BsAppActive> normal = new ArrayList<BsAppActive>();
            //已结束的活动集合
            List<BsAppActive> fail = new ArrayList<BsAppActive>();

            for(BsAppActive active : list) {
                Date endTime = active.getEndTime();
                //now大于endTime返回1;相等返回0;小于返回-1
                if(now.compareTo(endTime) != -1) {
                    fail.add(active);
                }
                else {
                    normal.add(active);
                }
            }

            List<BsAppActiveVO> normal_data = new ArrayList<BsAppActiveVO>();
            List<BsAppActiveVO> fail_data = new ArrayList<BsAppActiveVO>();
            for(BsAppActive active : normal) {
                BsAppActiveVO vo = new BsAppActiveVO();
                Date startTime = active.getStartTime();
                if(now.compareTo(startTime) != 1) {
                    vo.setAppActiveStatus("2");//预热中
                }
                else {
                    vo.setAppActiveStatus("1");//进行中
                }
                vo.setStartTime(active.getStartTime());
                vo.setEndTime(active.getEndTime());
                vo.setPublishTime(active.getPublishTime());
                vo.setTitle(active.getTitle());
                vo.setUrl(active.getUrl());
                vo.setSummary(active.getSummary());
                vo.setImgUrl(active.getImgUrl());
                vo.setShowTerminal(active.getShowTerminal());
                vo.setId(active.getId());
                normal_data.add(vo);
            }
            for(BsAppActive active : fail) {
                BsAppActiveVO vo = new BsAppActiveVO();
                vo.setAppActiveStatus("3");//已结束
                vo.setStartTime(active.getStartTime());
                vo.setEndTime(active.getEndTime());
                vo.setPublishTime(active.getPublishTime());
                vo.setTitle(active.getTitle());
                vo.setUrl(active.getUrl());
                vo.setSummary(active.getSummary());
                vo.setImgUrl(active.getImgUrl());
                vo.setShowTerminal(active.getShowTerminal());
                vo.setId(active.getId());
                normal_data.add(vo);
            }
            data.addAll(normal_data);
            data.addAll(fail_data);
            res.setDataGrid(BeanUtils.classToArrayList(data));
        } else {
            res.setDataGrid(new ArrayList<HashMap<String, Object>>());
        }
    }

}
