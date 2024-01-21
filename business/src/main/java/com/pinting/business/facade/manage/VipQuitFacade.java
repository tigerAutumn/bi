package com.pinting.business.facade.manage;

import com.pinting.business.hessian.manage.message.ReqMsg_VipQuit_VipQuitList;
import com.pinting.business.hessian.manage.message.ResMsg_VipQuit_VipQuitList;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.vo.VipQuitVO;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.business.service.manage.MAccountService;
import com.pinting.business.service.manage.VipQuitService;
import com.pinting.business.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 赞分期VIP退出申请Facade
 * Created by shh on 2017/4/24.
 */
@Component("VipQuit")
public class VipQuitFacade {

    @Autowired
    private VipQuitService vipQuitService;

    @Autowired
    private MAccountService mAccountService;

    @Autowired
    private BsUserService bsUserService;

    /**
     * VIP退出申请-列表
     * @param req
     * @param res
     */
    public void vipQuitList(ReqMsg_VipQuit_VipQuitList req, ResMsg_VipQuit_VipQuitList res) {
        Integer pageNum = req.getPageNum();
        Integer numPerPage = req.getNumPerPage();
        VipQuitVO vo = new VipQuitVO();
        vo.setPageNum(pageNum);
        vo.setNumPerPage(numPerPage);
        int totalRows = vipQuitService.queryVipQuitCount();
        if(totalRows > 0) {
            List<VipQuitVO> resultList =  vipQuitService.queryVipQuitList(vo);
            res.setValueList(BeanUtils.classToArrayList(resultList));
        }
        //查询站岗余额
        res.setZanVipAuthAmount(mAccountService.queryZanAuthAmount(req.getUserId()));
        //代偿人信息
        List<BsUser> userList = bsUserService.queryZanCompensateInfo();
        res.setZanUserList(BeanUtils.classToArrayList(userList));
        res.setPageNum(req.getPageNum());
        res.setNumPerPage(req.getNumPerPage());
        res.setTotalRows(totalRows);
    }

}
