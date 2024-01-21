package com.pinting.manage.controller;

import com.pinting.business.enums.BaoFooEnum;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.manage.message.*;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_QueryBankBin;
import com.pinting.business.hessian.site.message.ResMsg_Bank_QueryBankBin;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.manage.BaofooService;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.service.site.SMSService;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.baofoo.*;
import com.pinting.gateway.out.service.BaoFooTransportService;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.manage.model.dto.PreTopUpDTO;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 剑钊 on 2016/8/3.
 */
@Controller
@RequestMapping(value = "/baofoo")
public class BaofooController {

    @Autowired
    private BaofooService baofooService;
    @Autowired
    private SMSService smsService;
    @Autowired
    private BankCardService bankCardService;

    /* @RequestMapping(value = "/bind_card/index")
    public String queryBindCardList(ReqMsg_BaoFoo_BindCardListQuery reqMsg, HttpServletRequest request, HttpServletResponse response,
                                    Map<String, Object> model) {

       String pageNum = reqMsg.getPageNum();
        String numPerPage = reqMsg.getNumPerPage();

        if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
            pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
            numPerPage = String.valueOf(Constants.MANAGE_200_NUMPERPAGE);
            reqMsg.setPageNum(pageNum);
            reqMsg.setNumPerPage(numPerPage);
        }

        BindCardQueryVO vo = new BindCardQueryVO();
        vo.setNumPerPage(Integer.parseInt(numPerPage));
        vo.setPageNum(Integer.parseInt(pageNum));

        model.put("req", reqMsg);
        BsBaofooPayUserExample example = new BsBaofooPayUserExample();
        if (StringUtils.isNotBlank(reqMsg.getCardOwner())) {
            vo.setUserName(reqMsg.getCardOwner().trim());
            example.createCriteria().andUserNameEqualTo(reqMsg.getCardOwner().trim());
        }
        List<BsBaofooPayUser> list = baofooService.queryBindCardUserListPage(vo);
        model.put("bindUserList", list);
        list = baofooService.queryBindCardUserList(example);
        model.put("totalNum", list == null ? 0 : list.size());
        return "/baofoo/index";
    } */

    /**
     * 跳转用户绑卡页面
     *
     * @param request
     * @return
     */
/*    @RequestMapping(value = "/bind_card/detail")
    public String bindCardDetail(HttpServletRequest request, Map<String, Object> model) {

        model.put("bankList", BaoFooEnum.cardBinMap);
        return "/baofoo/detail";
    }*/

    /**
     * 预绑卡
     *
     * @param reqMsg
     * @param request
     * @return
     */
/*    @ResponseBody
    @RequestMapping(value = "/pre/bind_card")
    public String bindCard(ReqMsg_BaoFoo_BindCard reqMsg, HttpServletRequest request) {

        //判断是否已绑卡
        BsBaofooPayUserExample example = new BsBaofooPayUserExample();
        example.createCriteria().andCardNoEqualTo(reqMsg.getAccNo()).andBindStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode());
        List<BsBaofooPayUser> baofooPayUserList = baofooService.queryBindCardUserList(example);
        if (CollectionUtils.isEmpty(baofooPayUserList) || baofooPayUserList.size() == 0) {
            BsBaoFooPreBindCardVO vo = new BsBaoFooPreBindCardVO();
            vo.setAccNo(reqMsg.getAccNo());
            vo.setIdCard(reqMsg.getIdCard());
            vo.setMobile(reqMsg.getMobile());
            vo.setPayCode(BaoFooEnum.cardBinMap.get(reqMsg.getPayCode()));
            vo.setUserName(reqMsg.getIdHolder());
            return baofooService.preBindCard(vo);

        } else {
            return "961003";
        }

    }*/

    /**
     * 绑卡确认
     *
     * @param reqMsg
     * @param request
     * @return
     */
/*    @ResponseBody
    @RequestMapping(value = "/bind_card")
    public String bindCardConfirm(ReqMsg_BaoFoo_BindCardConfirm reqMsg, HttpServletRequest request) {

        //防止多人同时绑一张卡
        BsBaofooPayUserExample example = new BsBaofooPayUserExample();
        example.createCriteria().andCardNoEqualTo(reqMsg.getAccNo()).andBindStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode());
        List<BsBaofooPayUser> baofooPayUserList = baofooService.queryBindCardUserList(example);

        if (CollectionUtils.isEmpty(baofooPayUserList) || baofooPayUserList.size() == 0) {
            BsBaoFooBindCardVO vo = new BsBaoFooBindCardVO();
            vo.setAccNo(reqMsg.getAccNo());
            vo.setSmsCode(reqMsg.getSmsCode());

            return baofooService.bindCard(vo);
        } else {
            return "961003";
        }
    }*/

    /**
     * 解绑卡
     *
     * @param vo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/unbind_card")
    public Map<Object, Object> unBindCard(BsBaoFooUnBindCardVO vo) {

        String result = baofooService.unBindCard(vo);
        if (result.equals("000000")) {
            return ReturnDWZAjax.success("解绑成功！");
        }
        if (result.equals("900001")) {
            return ReturnDWZAjax.fail("没有找到绑卡成功用户信息！");
        } else {
            return ReturnDWZAjax.fail("解绑失败！");
        }
    }

    /**
     * 查询订单列表
     *
     * @return
     */
/*    @RequestMapping(value = "/order/index")
    public String queryOrderList(ReqMsg_BaoFoo_OrderListQuery req, HttpServletRequest request, Map<String, Object> model) {

        String pageNum = req.getPageNum();
        String numPerPage = req.getNumPerPage();

        if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
            pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
            numPerPage = String.valueOf(Constants.MANAGE_200_NUMPERPAGE);
            req.setPageNum(pageNum);
            req.setNumPerPage(numPerPage);
        }

        BaoFooOrdersQueryVO vo = new BaoFooOrdersQueryVO();
        vo.setMobile(req.getMobile());
        vo.setUserName(req.getUserName());
        vo.setBankCard(req.getBankCard());
        vo.setOrderNo(req.getOrderNo());
        vo.setBusinessType(req.getBusinessType());
        vo.setTransStatus(req.getTransStatus());

        List<BsBaofooPayOrders> list = baofooService.queryOrderListPage(vo);
        model.put("orderList", list);

        BsBaofooPayOrdersExample example = new BsBaofooPayOrdersExample();
        BsBaofooPayOrdersExample.Criteria criteria=example.createCriteria();
        if(StringUtils.isNotBlank(req.getMobile())){
            criteria.andMobileLike(req.getMobile());
        }
        if(StringUtils.isNotBlank(req.getUserName())){
            criteria.andUserNameLike(req.getUserName());
        }
        if(StringUtils.isNotBlank(req.getBankCard())){
            criteria.andBankCardEqualTo(req.getBankCard());
        }
        if(StringUtils.isNotBlank(req.getOrderNo())){
            criteria.andOrderNoEqualTo(req.getOrderNo());
        }
        if(StringUtils.isNotBlank(req.getBusinessType())){
            criteria.andBusinessTypeEqualTo(req.getBusinessType());
        }
        if(StringUtils.isNotBlank(req.getTransStatus())){
            criteria.andTransStatusEqualTo(req.getTransStatus());
        }
        list = baofooService.queryOrderList(example);
        model.put("totalNum", list.size());

        model.put("req", req);

        return "/baofoo/order_index";
    }*/

    /**
     * 跳转充值页面
     *
     * @return
     */
    @RequestMapping(value = "/top_up/detail")
    public String topUpDetail() {

        return "/baofoo/top_up";
    }

    /**
     * 预充值
     *
     * @param reqMsg
     * @return
     * @throws UnknownHostException
     */
    @ResponseBody
    @RequestMapping(value = "/pre/top_up")
    public PreTopUpResVO topUp(ReqMsg_BaoFoo_TopUp reqMsg) throws UnknownHostException {

        BsBaoFooPreTopUpVO vo = new BsBaoFooPreTopUpVO();
        vo.setAccNo(reqMsg.getAccNo());
        vo.setTxnAccount(reqMsg.getAmount());
        return baofooService.preTopUp(vo);
    }

    /**
     * 确认支付
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/top_up")
    public String quickPayConfirm(ReqMsg_BaoFoo_TopUpConfirm req) {

        BsBaoFooTopUpVO vo = new BsBaoFooTopUpVO();
        vo.setSmsCode(req.getSmsCode());
        vo.setTransId(req.getTransId());
        return baofooService.topUp(vo);
    }

    /**
     * 跳转提现页面
     *
     * @return
     */
    @RequestMapping(value = "/balance_withdraw/detail")
    public String withdrawDetail() {

        return "/baofoo/withdraw";
    }

    /**
     * 预提现
     *
     * @param req
     * @return
     */
/*    @ResponseBody
    @RequestMapping(value = "/pre/withdraw")
    public String withdraw(ReqMsg_BaoFoo_Withdraw req) {

        //新增提现数据
        BsBaofooPayUserExample example = new BsBaofooPayUserExample();
        example.createCriteria().andCardNoEqualTo(req.getAccNo()).andBindStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode());

        List<BsBaofooPayUser> baofooPayUserList = baofooService.queryBindCardUserList(example);

        if (CollectionUtils.isNotEmpty(baofooPayUserList) && baofooPayUserList.size() > 0) {

            String transId = System.currentTimeMillis() + "";
            BsBaofooPayOrders bsBaofooPayOrders = new BsBaofooPayOrders();
            bsBaofooPayOrders.setUpdateTime(new Date());
            bsBaofooPayOrders.setAmount(req.getAmount());
            bsBaofooPayOrders.setBankCard(baofooPayUserList.get(0).getCardNo());
            bsBaofooPayOrders.setBankName(baofooPayUserList.get(0).getBankName());
            bsBaofooPayOrders.setBindUserId(baofooPayUserList.get(0).getId());
            bsBaofooPayOrders.setBusinessType("BALANCE_WITHDRAW");
            bsBaofooPayOrders.setCreateTime(new Date());
            bsBaofooPayOrders.setIdCard(baofooPayUserList.get(0).getIdCard());
            bsBaofooPayOrders.setMobile(baofooPayUserList.get(0).getMobile());
            bsBaofooPayOrders.setTransStatus(BaoFooEnum.INIT.getCode());
            bsBaofooPayOrders.setTransType("DF");
            bsBaofooPayOrders.setOrderNo(transId);
            bsBaofooPayOrders.setUserName(baofooPayUserList.get(0).getUserName());
            baofooService.preWithdraw(bsBaofooPayOrders);
            //发送验证码
            smsService.sendIdentify(req.getMobile());

            BsBaofooPayOrders temp = new BsBaofooPayOrders();
            temp.setId(bsBaofooPayOrders.getId());
            temp.setTransStatus(BaoFooEnum.PRE_ORDER_SUCCESS.getCode());
            temp.setUpdateTime(new Date());
            baofooService.updateOrder(temp);
            return transId;
        } else {
            return null;
        }

    }*/

    /**
     * 确认提现
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/balance_withdraw")
    public String withdrawConfirm(ReqMsg_BaoFoo_WithdrawConfirm req) {

        if (smsService.validateIdentity(req.getMobile(), req.getSmsCode())) {

            BsBaoFooWithdrawVO vo = new BsBaoFooWithdrawVO();
            vo.setTransId(req.getTransId());
            return baofooService.withdraw(vo);
        }

        return null;
    }


    /**
     * 根据卡bin表 银行卡号查询银行
     *
     * @param request
     * @param reqMsg
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/regular/queryCardBank")
    public Map<String, Object> queryCardBank(HttpServletRequest request,
                                             ReqMsg_Bank_QueryBankBin reqMsg,
                                             HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();

        BsCardBin bsCardBin = bankCardService.queryBankBin(reqMsg.getCardNo());
        ResMsg_Bank_QueryBankBin res = new ResMsg_Bank_QueryBankBin();
        res.setBankCardLen(bsCardBin.getBankCardLen());
        res.setBankId(bsCardBin.getBankId());
        res.setCardBin(bsCardBin.getCardBin());
        res.setCardBinLen(bsCardBin.getCardBinLen());
        res.setBankCardFuncType(bsCardBin.getBankCardFuncType());
        result.put("bin", res);
        return result;
    }

    @RequestMapping("/order_status")
    @ResponseBody
    public Map<Object, Object> queryOrderStatus(BsBaoFooOrderVO vo) {


        if (baofooService.syncOrderStatus(vo).equals(PTMessageEnum.NO_DATA_FOUND.getCode())) {

            return ReturnDWZAjax.fail("找不到订单");
        } else {

            return ReturnDWZAjax.success("同步成功");
        }
    }

/*    @RequestMapping(value = "/query_user")
    @ResponseBody
    public List<BsBaofooPayUser> queryUser(String userName,String accNo){

        BsBaofooPayUserExample example = new BsBaofooPayUserExample();
        if(StringUtils.isNotBlank(userName)) {
            example.createCriteria().andUserNameEqualTo(userName).andBindStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode());
        }else if(StringUtils.isNotBlank(accNo)){
            example.createCriteria().andCardNoEqualTo(accNo).andBindStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode());
        }
        List<BsBaofooPayUser> baofooPayUserList = baofooService.queryBindCardUserList(example);

        return baofooPayUserList;
    }*/

    /**
     * 网银详情页
     * @param model
     * @return
     */
    @RequestMapping(value = "/ebank/detail")
    public String  ebankDetail(Map<String, Object> model){

        model.put("bankList", BaoFooEnum.ebankMap);
        return "/baofoo/ebank";
    }


    @RequestMapping(value = "/ebank")
    public String ebank(String amount,String bankCode,Map<String, Object> model){

        BsBaoFooEBankVO vo=new BsBaoFooEBankVO();
        vo.setAmount(Double.parseDouble(amount));
        vo.setBankCode(bankCode);
        model.put("ebank", baofooService.ebank(vo));

        return "/baofoo/ebankUrl";
    }
    
    
    
    /**
     * 进入批量绑卡填写用户信息页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/bind_card/batch_bind")
    public String queryBindCardList(HttpServletRequest request, HttpServletResponse response) {
        return "/baofoo/batch_bind";
    }

}
