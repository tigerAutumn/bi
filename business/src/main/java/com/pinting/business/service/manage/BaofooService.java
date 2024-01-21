package com.pinting.business.service.manage;

import com.pinting.business.model.vo.*;

import java.util.List;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public interface BaofooService {


/*    List<BsBaofooPayUser> queryBindCardUserList(BsBaofooPayUserExample example);*/

    /**
     * 查询用户绑卡列表
     *
     * @param bindCardQueryVO
     * @return
     */
/*    List<BsBaofooPayUser> queryBindCardUserListPage(BindCardQueryVO bindCardQueryVO);*/

    /**
     * 预绑卡
     *
     * @param bsBaoFooPreBindCardVO
     */
    String preBindCard(BsBaoFooPreBindCardVO bsBaoFooPreBindCardVO);

    /**
     * 绑卡
     *
     * @param bsBaoFooBindCardVO
     */
    String bindCard(BsBaoFooBindCardVO bsBaoFooBindCardVO);

    /**
     * 解绑卡
     *
     * @param bsBaoFooUnBindCardVO
     */
    String unBindCard(BsBaoFooUnBindCardVO bsBaoFooUnBindCardVO);


    /**
     * 分页查询报复订单列表
     * @param vo
     * @return
     */
/*    List<BsBaofooPayOrders> queryOrderListPage(BaoFooOrdersQueryVO vo);
*/
    /**
     * 查询宝付订单列表
     *
     * @param example
     * @return
     */
/*    List<BsBaofooPayOrders> queryOrderList(BsBaofooPayOrdersExample example);*/

    /**
     * 预充值
     *
     * @param bsBaoFooPreTopUpVO
     */
    PreTopUpResVO preTopUp(BsBaoFooPreTopUpVO bsBaoFooPreTopUpVO);


    /**
     * 充值
     *
     * @param bsBaoFooTopUpVO
     * @return
     */
    String topUp(BsBaoFooTopUpVO bsBaoFooTopUpVO);

    /**
     * 预提现
     *
     * @param bsBaofooPayOrders
     */
/*    Integer preWithdraw(BsBaofooPayOrders bsBaofooPayOrders);*/

    /**
     * 提现
     * @param bsBaoFooWithdrawVO
     * @return
     */
    String withdraw(BsBaoFooWithdrawVO bsBaoFooWithdrawVO);

    /**
     * 更新订单状态
     * @param bsBaofooPayOrders
     */
/*    void updateOrder(BsBaofooPayOrders bsBaofooPayOrders);*/

    /**
     * 同步订单状态
     * @param vo
     * @return
     */
    String syncOrderStatus(BsBaoFooOrderVO vo);

    /**
     * 网银
     * @param req
     * @return
     */
    String ebank(BsBaoFooEBankVO req);
    
}
