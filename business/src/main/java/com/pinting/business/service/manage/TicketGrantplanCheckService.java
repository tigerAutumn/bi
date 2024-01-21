package com.pinting.business.service.manage;

import com.pinting.business.model.BsTicketGrantPlanCheck;
import com.pinting.business.model.vo.BsInterestTicketGrantAttrVO;
import com.pinting.business.model.vo.BsTicketGrantPlanCheckVO;

import java.util.List;

/**
 * 加息券发放计划
 *
 * @author zousheng
 * @2018-04-02
 */
public interface TicketGrantplanCheckService {

    /**
     * 查询加息券发放计划
     *
     * @return
     */
    public List<BsTicketGrantPlanCheckVO> getGrantManagerment(BsTicketGrantPlanCheckVO req);

    /**
     * 查询加息券发放计划总数量
     *
     * @return
     */
    public int getGrantManagermentCount(BsTicketGrantPlanCheckVO req);

    /**
     * 新增卡券发放计划
     *
     * @param check
     */
    public void addTicketGrantPlan(BsTicketGrantPlanCheck check);

    /**
     * 新增自动加息券发放计划
     *
     * @param req
     */
    public void addAutoTicketGrantPlan(BsInterestTicketGrantAttrVO req);

    /**
     * 新增手工加息券发放计划
     *
     * @param req
     */
    public void addManualTicketGrantPlan(final BsInterestTicketGrantAttrVO req);

    /**
     * 卡券发放计划停用
     *
     * @param id
     */
    public void disableTicket(Integer id);

    /**
     * 查询自动加息券计划详情
     *
     * @param req
     * @return
     */
    public BsInterestTicketGrantAttrVO getAutoTicketGrantDetail(BsInterestTicketGrantAttrVO req);

    /**
     * 查询手工加息券计划详情
     *
     * @param req
     * @return
     */
    public BsInterestTicketGrantAttrVO getManualTicketGrantDetail(BsInterestTicketGrantAttrVO req);

    /**
     * 加息券发放计划审核结果
     * @param checkId
     * @param isPass
     * @param checher
     */
    public void checkTicketGrantPlan(Integer checkId, boolean isPass, Integer checher);

    /**
     * 发放手工加息券
     * @param checkIdStr
     */
    public void manualInterestTicketSend(String checkIdStr);
}
