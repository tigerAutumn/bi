
package com.pinting.site.regular.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.hessian.site.message.*;
import com.pinting.util.*;
import net.sf.json.JSONArray;

import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_Account_CheckUserIdSubAccountId;
import com.pinting.business.hessian.site.message.ReqMsg_Account_SubAccountById;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_AlreadyUseMoney;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_Pay19BankList;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_Pay19NetBankList;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_QueryBankBin;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_QueryBankById;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_QueryBankInfoByUser;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_QueryCardById;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_QueryDefaultBank;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_bindBankList;
import com.pinting.business.hessian.site.message.ReqMsg_Ecup2016Activity_QueryEcupProductGrid;
import com.pinting.business.hessian.site.message.ReqMsg_Invest_InfoQuery;
import com.pinting.business.hessian.site.message.ReqMsg_PayOrders_GetSuccessPayOrders;
import com.pinting.business.hessian.site.message.ReqMsg_Product_AddInform;
import com.pinting.business.hessian.site.message.ReqMsg_Product_BuyAgreement;
import com.pinting.business.hessian.site.message.ReqMsg_Product_CheckProductIsOff;
import com.pinting.business.hessian.site.message.ReqMsg_Product_InfoQuery;
import com.pinting.business.hessian.site.message.ReqMsg_Product_ListQuery;
import com.pinting.business.hessian.site.message.ReqMsg_Product_ProductDetailInfoQuery;
import com.pinting.business.hessian.site.message.ReqMsg_Product_QueryYunDaiChangeNameDate;
import com.pinting.business.hessian.site.message.ReqMsg_RedPacketInfo_GetRedPacket;
import com.pinting.business.hessian.site.message.ReqMsg_RedPacketInfo_RedPacketList;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_BalanceBuy;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_Buy;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_InfoQuery;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_NetBankbuy;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_Order;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_ReapalQuickCMB;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_ResendCode;
import com.pinting.business.hessian.site.message.ReqMsg_System_Status;
import com.pinting.business.hessian.site.message.ReqMsg_Transfer_BuySubmit;
import com.pinting.business.hessian.site.message.ReqMsg_Transfer_InfoTransfer;
import com.pinting.business.hessian.site.message.ReqMsg_Transfer_TransferDetail;
import com.pinting.business.hessian.site.message.ReqMsg_Transfer_TransferListQuery;
import com.pinting.business.hessian.site.message.ReqMsg_UserProductLimit_UserProductLimitQuery;
import com.pinting.business.hessian.site.message.ReqMsg_User_AddUserAddress;
import com.pinting.business.hessian.site.message.ReqMsg_User_BankListQuery;
import com.pinting.business.hessian.site.message.ReqMsg_User_InfoQuery;
import com.pinting.business.hessian.site.message.ReqMsg_User_IsBindCard;
import com.pinting.business.hessian.site.message.ReqMsg_User_UserBalanceQuery;
import com.pinting.business.hessian.site.message.ReqMsg_User_ValidUser;
import com.pinting.business.hessian.site.message.ResMsg_Account_CheckUserIdSubAccountId;
import com.pinting.business.hessian.site.message.ResMsg_Account_SubAccountById;
import com.pinting.business.hessian.site.message.ResMsg_Bank_AlreadyUseMoney;
import com.pinting.business.hessian.site.message.ResMsg_Bank_Pay19BankList;
import com.pinting.business.hessian.site.message.ResMsg_Bank_Pay19NetBankList;
import com.pinting.business.hessian.site.message.ResMsg_Bank_QueryBankBin;
import com.pinting.business.hessian.site.message.ResMsg_Bank_QueryBankById;
import com.pinting.business.hessian.site.message.ResMsg_Bank_QueryBankInfoByUser;
import com.pinting.business.hessian.site.message.ResMsg_Bank_QueryCardById;
import com.pinting.business.hessian.site.message.ResMsg_Bank_QueryDefaultBank;
import com.pinting.business.hessian.site.message.ResMsg_Bank_bindBankList;
import com.pinting.business.hessian.site.message.ResMsg_Ecup2016Activity_QueryEcupProductGrid;
import com.pinting.business.hessian.site.message.ResMsg_PayOrders_GetSuccessPayOrders;
import com.pinting.business.hessian.site.message.ResMsg_Product_AddInform;
import com.pinting.business.hessian.site.message.ResMsg_Product_BuyAgreement;
import com.pinting.business.hessian.site.message.ResMsg_Product_CheckProductIsOff;
import com.pinting.business.hessian.site.message.ResMsg_Product_InfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_Product_ListQuery;
import com.pinting.business.hessian.site.message.ResMsg_Product_ProductDetailInfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_RedPacketInfo_GetRedPacket;
import com.pinting.business.hessian.site.message.ResMsg_RedPacketInfo_RedPacketList;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_BalanceBuy;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_Buy;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_InfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_NetBankbuy;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_Order;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_ReapalQuickCMB;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_ResendCode;
import com.pinting.business.hessian.site.message.ResMsg_System_Status;
import com.pinting.business.hessian.site.message.ResMsg_Transfer_BuySubmit;
import com.pinting.business.hessian.site.message.ResMsg_Transfer_InfoTransfer;
import com.pinting.business.hessian.site.message.ResMsg_Transfer_TransferDetail;
import com.pinting.business.hessian.site.message.ResMsg_Transfer_TransferListQuery;
import com.pinting.business.hessian.site.message.ResMsg_UserProductLimit_UserProductLimitQuery;
import com.pinting.business.hessian.site.message.ResMsg_User_AddUserAddress;
import com.pinting.business.hessian.site.message.ResMsg_User_BankListQuery;
import com.pinting.business.hessian.site.message.ResMsg_User_InfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_User_IsBindCard;
import com.pinting.business.hessian.site.message.ResMsg_User_UserBalanceQuery;
import com.pinting.business.hessian.site.message.ResMsg_User_ValidUser;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.exception.PTException;
import com.pinting.core.util.ConsMsgUtil;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.interceptor.Token;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;

/**
 * 定期理财
 * @Project: site-java
 * @Title: RegularController.java
 * @author Huang MengJian
 * @date 2015-4-13 下午2:47:27
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Controller
@Scope("prototype")
public class RegularController extends BaseController {

    @Autowired
    private CommunicateBusiness regularService;
    @Autowired
    private CommunicateBusiness siteService;
    @Autowired
    private CommunicateBusiness userService;
    @Autowired
	private WeChatUtil weChatUtil;

    private Logger log = LoggerFactory.getLogger(RegularController.class);

    /**
     * 打开理财产品列表页
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param reqMsg
     * @return
     */
    @RequestMapping("{channel}/regular/list")
    public String regularInit(@PathVariable String channel, HttpServletRequest request,
                              HttpServletResponse response, Map<String, Object> model) {
        // 钱报178理财 PC
//        if ("gen178".equals(channel)) {
//            return "redirect:/gen178/index";
//        }
        log.info("【产品列表页开始】");
        //H5跳转到赞分期产品列表标志
        String zanProductFlag = request.getParameter("zanProductFlag");
        model.put("zanProductFlag", zanProductFlag);

        String qianbao = request.getParameter("qianbao");
        CookieManager cookieManager = new CookieManager(request);
        String userType = cookieManager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_TYPE.getName(), true);
        
        //记录用户登录的IP地址对应的位置信息
        String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
        String ip = cookieManager.getValue(CookieEnums._USER.getName(),
                CookieEnums._USER_IP.getName(), true);
        if (StringUtil.isBlank(ip) && !StringUtil.isBlank(userIdStr)) {
        	ip = AddressUtil.getIp(request);
        	String address = "";
        	try {
    			address = AddressUtil.getAddresses("ip=" + ip, "utf-8");
    		} catch (UnsupportedEncodingException e) {
    			e.printStackTrace();
    		}
        	//用户id和地址都存在时，发起通信入redis
        	if (!StringUtil.isBlank(address)) {
        		ReqMsg_User_AddUserAddress addressReq = new ReqMsg_User_AddUserAddress();
        		addressReq.setUserId(Integer.parseInt(userIdStr));
        		addressReq.setAddress(address);
        		
        		ResMsg_User_AddUserAddress res = (ResMsg_User_AddUserAddress)userService.handleMsg(addressReq);
        		if ("000000".equals(res.getResCode())) {
        			//将用户IP存入cookie，设置有效时间为1天
        			CookieManager ipManager = new CookieManager(request);
        			ipManager.setValue(CookieEnums._USER.getName(),
        					CookieEnums._USER_IP.getName(), ip, true);
        			ipManager.save(response, CookieEnums._USER.getName(), null, "/",
                            -1, true);
        		}
        	}
        }
        
        String link = GlobEnv.getWebURL("/micro2.0/regular/list");
        ReqMsg_Product_ListQuery reqMsg = new ReqMsg_Product_ListQuery();
        ReqMsg_Product_BannerQuery bannerReq = new ReqMsg_Product_BannerQuery();
        
        if (!"PROMOT".equals(userType)) {
            userType = "NORMAL";
        }
        reqMsg.setUserType("NORMAL");
        reqMsg.setStatus(StringUtil.isBlank(request.getParameter("status"))? null : Integer.parseInt(request.getParameter("status")));
        reqMsg.setTerm(StringUtil.isBlank(request.getParameter("term"))? null : request.getParameter("term"));
        reqMsg.setPageNum(Integer.parseInt(StringUtil.isBlank(request.getParameter("page"))? "1" : request.getParameter("page")));
        reqMsg.setReturnType(StringUtil.isBlank(request.getParameter("returnType"))? null : request.getParameter("returnType"));
        if("gen2.0".equals(channel)) {
            reqMsg.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_PC);
            reqMsg.setNumPerPage(12);
            bannerReq.setChannel(channel);
        } else if("gen178".equals(channel)) {
            String flag = cookieManager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
            if(Constants.AGENT_VIEW_ID_KQ.equals(flag)) {
                // 柯桥日报
                reqMsg.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_PC_KQ);
                bannerReq.setChannel(Constants.PRODUCT_SHOW_TERMINAL_PC_KQ);
            }else if (Constants.AGENT_VIEW_ID_HN.equals(flag)) {
            	//海宁日报
            	reqMsg.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_PC_HN);
            	bannerReq.setChannel(Constants.PRODUCT_SHOW_TERMINAL_PC_HN);
			}else if (Constants.AGENT_VIEW_ID_RUIAN.equals(flag)) {
            	//瑞安日报
            	reqMsg.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_PC_RUIAN);
            	bannerReq.setChannel(Constants.PRODUCT_SHOW_TERMINAL_PC_RUIAN);
			}else if (Constants.AGENT_VIEW_ID_QHD_ST.equals(flag)) {
                //视听之友
                reqMsg.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_PC_QHD_ST);
                bannerReq.setChannel(Constants.PC_PAGE_PRODUCT_SHOW_TERMINAL_PC_QHD_ST);
            }  else {
                // 非柯桥日报或海宁日报，默认钱报
                reqMsg.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_PC_178);
                bannerReq.setChannel(Constants.PRODUCT_SHOW_TERMINAL_PC_178);
            }
            reqMsg.setNumPerPage(12);
        } else if("micro2.0".equals(channel) && StringUtil.isNotBlank(qianbao)) {
            String flag = cookieManager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
            if(Constants.AGENT_VIEW_ID_KQ.equals(flag)) {
                // 柯桥日报
                reqMsg.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_H5_KQ);
                bannerReq.setChannel(Constants.PRODUCT_SHOW_TERMINAL_H5_KQ);
            } else if (Constants.AGENT_VIEW_ID_HN.equals(flag)) {
            	//海宁日报
            	reqMsg.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_H5_HN);
            	bannerReq.setChannel(Constants.PRODUCT_SHOW_TERMINAL_H5_HN);
			} else if (Constants.AGENT_VIEW_ID_RUIAN.equals(flag)) {
            	//瑞安日报
            	reqMsg.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_H5_RUIAN);
            	bannerReq.setChannel(Constants.PRODUCT_SHOW_TERMINAL_H5_RUIAN);
			} else if (Constants.AGENT_VIEW_ID_QHD_JT.equals(flag)) {
                //秦皇岛交通广播
                reqMsg.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_H5_QHD_JT);
                bannerReq.setChannel(Constants.PC_PAGE_PRODUCT_SHOW_TERMINAL_H5_QHD_JT);
            }else if (Constants.AGENT_VIEW_ID_QHD_XW.equals(flag)) {
                //秦皇岛新闻891
                reqMsg.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_H5_QHD_XW);
                bannerReq.setChannel(Constants.PC_PAGE_PRODUCT_SHOW_TERMINAL_H5_QHD_XW);
            }else if (Constants.AGENT_VIEW_ID_QHD_TV.equals(flag)) {
                //秦皇岛电视台今日报道
                reqMsg.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_H5_QHD_TV);
                bannerReq.setChannel(Constants.PC_PAGE_PRODUCT_SHOW_TERMINAL_H5_QHD_TV);
            }else if (Constants.AGENT_VIEW_ID_QHD_ST.equals(flag)) {
                //视听之友
                reqMsg.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_H5_QHD_ST);
                bannerReq.setChannel(Constants.PC_PAGE_PRODUCT_SHOW_TERMINAL_H5_QHD_ST);
            }else if (Constants.AGENT_VIEW_ID_QHD_SJC.equals(flag)) {
                //1038私家车广播
                reqMsg.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_H5_QHD_SJC);
                bannerReq.setChannel(Constants.PC_PAGE_PRODUCT_SHOW_TERMINAL_H5_QHD_SJC);
            }else {
                // 非柯桥日报，默认钱报
                reqMsg.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_H5_178);
                bannerReq.setChannel(Constants.PRODUCT_SHOW_TERMINAL_H5_178);
            }
            reqMsg.setNumPerPage(10);
        } else if("micro2.0".equals(channel) && StringUtil.isBlank(qianbao)) {
            reqMsg.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_H5);
            reqMsg.setNumPerPage(10);
            bannerReq.setChannel("micro2.0");	
            // H5 默认显示定期出借计划
            if(StringUtil.isBlank(reqMsg.getReturnType())) {
                reqMsg.setReturnType("FINISH_RETURN_ALL");
            }
        }
        
        ResMsg_Product_ListQuery resMsg = (ResMsg_Product_ListQuery) regularService.handleMsg(reqMsg);
        List<Map<String,Object>> dataList = resMsg.getProductLst();

        // H5产品列表显示等额本息计划列表
        if("micro2.0".equals(channel) && StringUtil.isBlank(qianbao)) {
            reqMsg.setReturnType("AVERAGE_CAPITAL_PLUS_INTEREST");
            ResMsg_Product_ListQuery deResMsg = (ResMsg_Product_ListQuery) regularService.handleMsg(reqMsg);
            if("PROMOT".equals(userType)) {
                for(Map<String,Object> temp : deResMsg.getProductLst()) {
                    String rate = (String)temp.get("rate");
                    String beforeRate = new DecimalFormat("0.0").format(MoneyUtil.subtract(rate, "1.0"));
                    temp.put("beforeRate", beforeRate);
                }
            }
            for(Map<String,Object> temp : deResMsg.getProductLst()) {
            	if (Constants.PROPERTY_SYMBOL_ZAN.equals(temp.get("propertySymbol"))) {
            		temp.put("term", temp.get("dayTerm"));
            	}
			}
            // 产品列表数据调整便于前端显示开始
            productDataChangeForFront(deResMsg.getProductLst());
            // 产品列表数据调整便于前端显示结束
            model.put("deItem", deResMsg.getProductLst());
            reqMsg.setTotolRows(deResMsg.getCount());
            model.put("deTotalCount", reqMsg.getTotalPages());
            model.put("dePage", reqMsg.getPageNum());
        }

        if("PROMOT".equals(userType)) {
        	for(Map<String,Object> temp : dataList) {
        		String rate = (String)temp.get("rate");
        		String beforeRate = new DecimalFormat("0.0").format(MoneyUtil.subtract(rate, "1.0"));
        		temp.put("beforeRate", beforeRate);
        	}

        }
        
        model.put("userType", "NORMAL");
        // 产品列表数据调整便于前端显示开始
        productDataChangeForFront(dataList);


        // 产品列表数据调整便于前端显示结束
        model.put("item", dataList);
        reqMsg.setTotolRows(resMsg.getCount());
        model.put("totalCount", reqMsg.getTotalPages());
        model.put("page", reqMsg.getPageNum());

        
        // 钱报H5
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao) && "micro2.0".equals(channel)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            Map<String,String> sign = weChatUtil.createAuth(request);
            link += "?qianbao=qianbao";
            CookieManager manager = new CookieManager(request);
			String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
            sign.put("link", link);
            model.put("weichat", sign);
            log.info("【产品列表页结束】");
            return "redirect:/micro2.0/index?qianbao=qianbao";
        }
        // H5
        else if("micro2.0".equals(channel)) {
            Map<String,String> sign = weChatUtil.createAuth(request);
            sign.put("link", link);
            model.put("weichat", sign);
        }
        // PC || PC_178
        else if("gen2.0".equals(channel) || "gen178".equals(channel)) {

            //product banner
            bannerReq.setReturnType(StringUtil.isBlank(request.getParameter("returnType"))? null : request.getParameter("returnType"));
            ResMsg_Product_BannerQuery bannerRes = (ResMsg_Product_BannerQuery) siteService.handleMsg(bannerReq);
            log.info("imgPath:{}", bannerRes.getImgPath());
            model.put("imgPath", bannerRes.getImgPath());
            model.put("bannerUrl", bannerRes.getBannerUrl());


            Integer status = null;
            if(!StringUtil.isBlank(request.getParameter("status"))) {
                status = Integer.parseInt(request.getParameter("status"));
            }
            String term = request.getParameter("term");
            model.put("status", status);
            model.put("term", term);
            model.put("totalPages", reqMsg.getTotalPages());
            model.put("returnType", reqMsg.getReturnType());
            
            // 计算当前页显示几行几列
            // 当前页的数据总条数
            Integer cpCount = dataList.size();
            Integer rows = cpCount / 4 + ((cpCount % 4 == 0) ? 0 : 1);
            List<List<Map<String, Object>>> dataGrid = new ArrayList<List<Map<String, Object>>>();
            for (int row = 1; row <= rows; row++) {
                List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
                for (int index = row*4-4; index < row*4; index++) {
                    if(cpCount < index + 1) {
                        break;
                    }
                    datas.add(dataList.get(index));
                }
                dataGrid.add(datas);
            }
            model.put("dataGrid", dataGrid);
            model.put("rows", rows);
        }
        model.put("now", new Date());
        model.put("tomorrow", DateUtil.addDays(new Date(), 1));
        log.info("【产品列表页结束】");
        return channel + "/regular/regular_product_list";
    }

    private void productDataChangeForFront(List<Map<String, Object>> dataList) {
        if(!org.springframework.util.CollectionUtils.isEmpty(dataList)) {
            for (Map<String, Object> product : dataList) {
                Double amount = (Double)product.get("maxSingleInvestAmount");
                if(amount != null) {
                    double a = MoneyUtil.divide(amount, 10000d).doubleValue();
                    int integer = (int)a;
                    if(a == integer) {
                        product.put("maxSingleInvestAmount", integer);
                    } else {
                        product.put("maxSingleInvestAmount", String.valueOf(a));
                    }
                }

                String startTime = (String) product.get("startTime");
                Integer status = (Integer) product.get("status");
                Date now = new Date();
                // 未发放已发布
                if(Constants.PRODUCT_STATUS_PUBLISH_YES.equals(status)) {
                    product.put("flag", "countdown");
                    // 同一天
                    if(DateUtil.isSameDay(DateUtil.parseDateTime(startTime), now)) {
                        product.put("isSameDay", "yes");
                    // 明天
                    }else if(DateUtil.isSameDay(DateUtil.parseDateTime(startTime), DateUtil.addDays(now,1))){
                        product.put("isTomorrow", "yes");
                    }
                    // 明天以后
                    else {
                        product.put("isSameDay", "no");
                    }
                    product.put("progress", 0);
                }
                // 已经手动结束
                if(Constants.PRODUCT_STATUS_FINISH.equals(status)) {
                    product.put("flag", "finish");
                    product.put("progress", 100);
                }
                // 进行中
                if(Constants.PRODUCT_STATUS_OPENING.equals(status)) {
                    product.put("flag", "buy");
                    Double maxTotalAmount = (Double) product.get("maxTotalAmount");
                    Double currTotalAmount = (Double) product.get("currTotalAmount");
                    Double a =  currTotalAmount / maxTotalAmount * 100d;
                    if(a > 0 && a < 1) {
                        product.put("progress", 1);
                    } else if(a > 99 && a < 100) {
                        product.put("progress", 99);
                    } else {
                        product.put("progress", a);
                    }
                }
            }
        }
    }

    /**
     * 分页
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/regular/list/page")
    public String regularInitPage(@PathVariable String channel, HttpServletRequest request,
                              HttpServletResponse response, Map<String, Object> model) {
        model.put("now", new Date());
        model.put("tomorrow", DateUtil.addDays(new Date(), 1));
        String qianbao = request.getParameter("qianbao");
        ReqMsg_Product_ListQuery reqMsg = new ReqMsg_Product_ListQuery();
        reqMsg.setUserType("NORMAL");
        reqMsg.setPageNum(Integer.parseInt(StringUtil.isBlank(request.getParameter("page"))? "1" : request.getParameter("page")));
        if("gen2.0".equals(channel)) {
            reqMsg.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_PC);
        } else if("micro2.0".equals(channel) && StringUtil.isNotBlank(qianbao)) {
            reqMsg.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_H5_178);
        } else if("micro2.0".equals(channel) && StringUtil.isBlank(qianbao)) {
            reqMsg.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_H5);
            reqMsg.setReturnType("FINISH_RETURN_ALL");
        }
        
        ResMsg_Product_ListQuery resMsg = (ResMsg_Product_ListQuery) regularService.handleMsg(reqMsg);
        List<Map<String,Object>> dataList = resMsg.getProductLst();
        // 产品列表数据调整便于前端显示开始
        productDataChangeForFront(dataList);
        // 产品列表数据调整便于前端显示结束
        model.put("item", dataList);
        return channel + "/regular/regular_product_list_page";
    }

    /**
     * 分页
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/regular/list/depage")
    public String depage(@PathVariable String channel, HttpServletRequest request,
                                  HttpServletResponse response, Map<String, Object> model) {
        model.put("now", new Date());
        model.put("tomorrow", DateUtil.addDays(new Date(), 1));
        ReqMsg_Product_ListQuery reqMsg = new ReqMsg_Product_ListQuery();
        reqMsg.setUserType("NORMAL");
        reqMsg.setPageNum(Integer.parseInt(StringUtil.isBlank(request.getParameter("dePage"))? "1" : request.getParameter("dePage")));
        reqMsg.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_H5);
        reqMsg.setReturnType("AVERAGE_CAPITAL_PLUS_INTEREST");

        ResMsg_Product_ListQuery resMsg = (ResMsg_Product_ListQuery) regularService.handleMsg(reqMsg);
        List<Map<String,Object>> dataList = resMsg.getProductLst();
        for(Map<String,Object> temp : dataList) {
        	if (Constants.PROPERTY_SYMBOL_ZAN.equals(temp.get("propertySymbol"))) {
        		temp.put("term", temp.get("dayTerm"));
			}
		}
        // 产品列表数据调整便于前端显示开始
        productDataChangeForFront(dataList);
        // 产品列表数据调整便于前端显示结束
        model.put("deItem", dataList);
        return channel + "/regular/regular_product_list_page_de";
    }

    /**
     * 
     * @Title: regularExplain 
     * @Description: 理财产品说明页
     * @param request
     * @param response
     * @param model
     * @return String
     * @throws
     */
    @RequestMapping("micro2.0/regular/index")
	public String regularIndex(HttpServletRequest request, HttpServletResponse response,
			Map<String,Object> model) {
        log.info("【产品详情页开始】");
        CookieManager cookieManager = new CookieManager(request);

        // 钱报APP信任免登需要通过getAttribute获取参数
    	String pageNum = request.getParameter("pageNum");
        String qianbao = request.getParameter("qianbao");
        String id = request.getParameter("id");
        String rate = request.getParameter("rate");
        String name = request.getParameter("name");
        String minInvestAmount = request.getParameter("minInvestAmount");
        String userType = request.getParameter("userType");

        // 钱报178APP
        String from = StringUtil.isBlank(request.getParameter("from")) ? (String) request.getAttribute("from") : request.getParameter("from");
        if(Constants.FROM_178_APP.equals(from)) {
            id = StringUtil.isBlank(id) ? (String) request.getAttribute("id") : id;
            qianbao = Constants.USER_AGENT_QIANBAO;
            String backUrl = cookieManager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_178_BACK_URL.getName(), true);
            ReqMsg_Product_ProductDetailInfoQuery req = new ReqMsg_Product_ProductDetailInfoQuery();
            req.setProductId(Integer.parseInt(id));
            ResMsg_Product_ProductDetailInfoQuery res = (ResMsg_Product_ProductDetailInfoQuery) siteService.handleMsg(req);
            model.put("pageNum", req.getPageNum());
            model.put("rate", res.getBaseRate());
            model.put("name", res.getName());
            model.put("minInvestAmount", res.getMinInvestAmount());
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            model.put("backUrl", backUrl);
            model.put("from", from);
        }

        if (id == null || "".equals(id)) {
            if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
                model.put("qianbao", Constants.USER_AGENT_QIANBAO);
                log.info("【产品详情页结束】");
                return "redirect:/micro2.0/index?qianbao=qianbao";
            }else{
                log.info("【产品详情页结束】");
                return "redirect:/micro2.0/regular/list";
            }
        }

        if("PROMOT".equals(userType)) {
            String beforeRate = new DecimalFormat("0.0").format(MoneyUtil.subtract(rate, "1.0"));
            model.put("beforeRate", beforeRate);
        }
        model.put("rate", rate);
        model.put("id", id);
        model.put("name", name);
        model.put("minInvestAmount", minInvestAmount);
        model.put("userType", userType);
		
		//新查询产品数据信息
        String resources = GlobEnv.get("news.resources");
        String manageWeb = GlobEnv.get("manage.web");
        String web = GlobEnv.get("gen.server.web");
		
		ReqMsg_Product_ProductDetailInfoQuery reqMsg_Product_ProductDetailInfoQuery = new ReqMsg_Product_ProductDetailInfoQuery();
		reqMsg_Product_ProductDetailInfoQuery.setPageNum(pageNum==null?1:Integer.parseInt(pageNum));
		reqMsg_Product_ProductDetailInfoQuery.setProductId(Integer.parseInt(id));
		ResMsg_Product_ProductDetailInfoQuery resMsg_Product_ProductDetailInfoQuery = (ResMsg_Product_ProductDetailInfoQuery) regularService.handleMsg(reqMsg_Product_ProductDetailInfoQuery);
		resMsg_Product_ProductDetailInfoQuery.setNote(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getNote(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setPropertySummary(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getPropertySummary(), resources, manageWeb, web));
		
		resMsg_Product_ProductDetailInfoQuery.setReturnSource(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getReturnSource(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setFundSecurity(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getFundSecurity(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setOrgnizeCheck(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getOrgnizeCheck(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setRatingGrade(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getRatingGrade(), resources, manageWeb, web));
		
		model.put("pageNum", reqMsg_Product_ProductDetailInfoQuery.getPageNum());
		model.put("totalCount",  (int) Math.ceil((double) resMsg_Product_ProductDetailInfoQuery.getTotalRows() / reqMsg_Product_ProductDetailInfoQuery.getNumPerPage()));
		model.put("productDetail", resMsg_Product_ProductDetailInfoQuery);
		model.put("investRecord", resMsg_Product_ProductDetailInfoQuery.getInvestRecordList());
		model.put("term", resMsg_Product_ProductDetailInfoQuery.getTerm());
		model.put("now", new Date());
        model.put("propertySymbol", resMsg_Product_ProductDetailInfoQuery.getPropertySymbol());

        //详情页显示理财计划开始时间
        Boolean isToday = DateUtil.isSameDay(resMsg_Product_ProductDetailInfoQuery.getStartTime(),new Date());
        Boolean isTomorrow = DateUtil.isSameDay(resMsg_Product_ProductDetailInfoQuery.getStartTime(),DateUtil.addDays(new Date(),1));
        if(isToday){
            //理财计划今天开始
            model.put("timeShow", "today");
        }else if(isTomorrow){
            //理财计划明天开始
            model.put("timeShow", "tomorrow");
        }
        //查询19付支持快捷支付的银行
		ReqMsg_Bank_Pay19BankList reqMsg = new ReqMsg_Bank_Pay19BankList();
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
        if (!"".equals(userId)) {
        	reqMsg.setUserId(Integer.parseInt(userId));
        	if(Constants.PRODUCT_ACTIVITY_TYPE_NEW_BUYER.equals(resMsg_Product_ProductDetailInfoQuery.getActivityType())){
      			//判断是否是新手用户
      			ReqMsg_PayOrders_GetSuccessPayOrders reqOrder = new ReqMsg_PayOrders_GetSuccessPayOrders();
      			reqOrder.setUserId(Integer.valueOf(userId));
      			ResMsg_PayOrders_GetSuccessPayOrders resOrder = (ResMsg_PayOrders_GetSuccessPayOrders)siteService.handleMsg(reqOrder);
      			if(resOrder.getBuyType() != null){
      				model.put("newUserType", resOrder.getBuyType());
      			}
      		}
		}
		ResMsg_Bank_Pay19BankList resMsg = (ResMsg_Bank_Pay19BankList) regularService.handleMsg(reqMsg);
		model.put("bankList", resMsg.getBankList());
		
		model.put("source", "product_detail");
		Map<String,String> sign = weChatUtil.createAuth(request);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
		    Date dt1 = df.parse(Constants.LOAD_MATCH_SHOW_TIME);
		    Date dt2 = df.parse(DateUtil.formatYYYYMMDD(resMsg_Product_ProductDetailInfoQuery.getStartTime()));
		    if (dt1.getTime() > dt2.getTime()) {
				// 钱报的参数
		        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
		            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
		            String link = GlobEnv.getWebURL("/micro2.0/regular/list?qianbao=qianbao");
		            CookieManager manager = new CookieManager(request);
					String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
			        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
			        	link += "&agentViewFlag="+viewAgentFlagCookie;
			        }
		            sign.put("link", link);
		            model.put("weichat", sign);
                    log.info("【产品详情页结束】");
		            return "qianbao178/regular/regular_product_old_index";
		        }
		        
		        String link = GlobEnv.getWebURL("/micro2.0/regular/list");
		        sign.remove("link"); //sign 里面已经存在了一个link
		        sign.put("link", link);
		        model.put("weichat", sign);
                log.info("【产品详情页结束】");
                return "micro2.0/regular/regular_product_old_index";
				
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// 来自于钱报APP
        if(Constants.FROM_178_APP.equals(from)) {
            request.setAttribute("channel", null);
            request.setAttribute("id", null);
            request.setAttribute("from", null);
            log.info("【产品详情页结束】");
            return "qianbao178/app/regular/regular_product_index";
        }
		
		// 钱报的参数
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            String link = GlobEnv.getWebURL("/micro2.0/regular/list?qianbao=qianbao");
            sign.put("link", link);
            model.put("weichat", sign);
            log.info("【产品详情页结束】");
            return "qianbao178/regular/regular_product_index";
        }
        
        String link = GlobEnv.getWebURL("/micro2.0/regular/list");
        sign.remove("link"); //sign 里面已经存在了一个link
        sign.put("link", link);
        model.put("weichat", sign);
        log.info("【产品详情页结束】");
		return "micro2.0/regular/regular_product_index";
	}

    private int canUseTicket(List<HashMap<String, Object>> ticketList, Double amount) {
        int count = 0;
        if(CollectionUtils.isNotEmpty(ticketList)) {
            for(HashMap<String, Object> map : ticketList) {
                Double full = 0d;
                if(map.get("full") instanceof String)
                    full = Double.valueOf((String) map.get("full"));
                else if(map.get("full") instanceof Double)
                    full = (Double) map.get("full");
                if(full.compareTo(amount) <= 0)
                    count++;
            }
        }
        return count;

    }
    @RequestMapping("micro2.0/regular/index_page")
	public String regularIndexPage(HttpServletRequest request, HttpServletResponse response,
			Map<String,Object> model) {
    	String pageNum = request.getParameter("pageNum");
		String id = request.getParameter("id");
		model.put("id", id);
		
		//新查询产品数据信息
        String resources = GlobEnv.get("news.resources");
        String manageWeb = GlobEnv.get("manage.web");
        String web = GlobEnv.get("gen.server.web");
		
		ReqMsg_Product_ProductDetailInfoQuery reqMsg_Product_ProductDetailInfoQuery = new ReqMsg_Product_ProductDetailInfoQuery();
		reqMsg_Product_ProductDetailInfoQuery.setPageNum(Integer.parseInt(pageNum));
		reqMsg_Product_ProductDetailInfoQuery.setProductId(Integer.parseInt(id));
		ResMsg_Product_ProductDetailInfoQuery resMsg_Product_ProductDetailInfoQuery = (ResMsg_Product_ProductDetailInfoQuery) regularService.handleMsg(reqMsg_Product_ProductDetailInfoQuery);
		resMsg_Product_ProductDetailInfoQuery.setNote(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getNote(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setPropertySummary(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getPropertySummary(), resources, manageWeb, web));
		
		resMsg_Product_ProductDetailInfoQuery.setReturnSource(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getReturnSource(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setFundSecurity(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getFundSecurity(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setOrgnizeCheck(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getOrgnizeCheck(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setRatingGrade(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getRatingGrade(), resources, manageWeb, web));
		
		model.put("pageNum", reqMsg_Product_ProductDetailInfoQuery.getPageNum());
		model.put("totalCount",  (int) Math.ceil((double) resMsg_Product_ProductDetailInfoQuery.getTotalRows() / reqMsg_Product_ProductDetailInfoQuery.getNumPerPage()));
		
		model.put("productDetail", resMsg_Product_ProductDetailInfoQuery);
		model.put("investRecord", resMsg_Product_ProductDetailInfoQuery.getInvestRecordList());
		
		
		return "micro2.0/regular/regular_product_index_page";
	}

    /**
     * 进入理财产品购买页面
     * @param request
     * @param model
     * @return
     */
    @Token(save = true)
	@RequestMapping("micro2.0/regular/bind")
	public String bind(HttpServletRequest request, Map<String,Object> model) {
        // 0. 数据校验，若不存在则跳转至理财列表页
        log.info("【购买详情页开始】");
        log.info("进入理财产品购买页面，产品ID：{}", request.getParameter("id"));

        if(StringUtils.isBlank(request.getParameter("id"))){
            if("qianbao".equals(request.getParameter("qianbao"))) {
                return "redirect:/micro2.0/index?qianbao=qianbao";
            } else {
                return "redirect:/micro2.0/regular/list";
            }
        }

        // 1. 获取请求信息：用户ID
        CookieManager manangr = new CookieManager(request);
        String userId = manangr.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        String backUrl = manangr.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_178_BACK_URL.getName(), true);

        int productId = Integer.parseInt(request.getParameter("id"));

        // 存管引导信息（未存管绑卡或激活）
        ReqMsg_DepGuide_FindDepGuideInfo depGuideReq = new ReqMsg_DepGuide_FindDepGuideInfo();
        depGuideReq.setUserId(Integer.parseInt(userId));
        depGuideReq.setContainRisk(true);
        ResMsg_DepGuide_FindDepGuideInfo depGuideRes = (ResMsg_DepGuide_FindDepGuideInfo) siteService.handleMsg(depGuideReq);
        if(Constants.HFBANK_GUIDE_NO_BIND_CARD.equals(depGuideRes.getIsOpened())
                || Constants.HFBANK_GUIDE_FAILED_BIND_HF.equals(depGuideRes.getIsOpened())) {
            // 未存管绑卡或激活
            return "redirect:/micro2.0/bankcard/index?entry=buy";
        } else if(Constants.HFBANK_GUIDE_WAIT_ACTIVATE.equals(depGuideRes.getIsOpened())) {
            return "redirect:/micro2.0/bankcard/activate/index";
        }
        String qianbao = request.getParameter("qianbao");
        if(Constants.is_expire.equals(depGuideRes.getRiskStatus())
                || Constants.is_no.equals(depGuideRes.getRiskStatus())) {
            // 需要进行风险测评
        	if(Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
        		return "redirect:/micro2.0/assets/questionnaireMore?entry=buy&qianbao=qianbao";
        	} else {        		
        		return "redirect:/micro2.0/assets/questionnaireMore?entry=buy";
        	}
        }

        // 2. 分享
        share("micro2.0", model, request, GlobEnv.getWebURL("/micro2.0/regular/list"));

		// 3. 查询当前购买产品的信息
  		ReqMsg_Product_InfoQuery infoReq = new ReqMsg_Product_InfoQuery();
  		infoReq.setId(productId);
  		ResMsg_Product_InfoQuery infoRes = (ResMsg_Product_InfoQuery) siteService.handleMsg(infoReq);

        // 4. 获得用户余额信息
        ReqMsg_User_UserBalanceQuery reqMsg = new ReqMsg_User_UserBalanceQuery();
        reqMsg.setUserId(userId);
        ResMsg_User_UserBalanceQuery resMsg = (ResMsg_User_UserBalanceQuery) regularService.handleMsg(reqMsg);

        ReqMsg_Ticket_TicketList ticketReq = new ReqMsg_Ticket_TicketList();
        ticketReq.setBizType(Constants.TICKET_INTEREST_BIZ_TYPE_BUY);
        ticketReq.setUserId(Integer.parseInt(userId));
        ticketReq.setStatus(Constants.RED_PACKET_STATUS_INIT);
        ticketReq.setProductId(productId);
        if(Constants.IS_SUPPORT_RED_PACKET_TRUE.equals(infoRes.getIsSupportRedPacket())) {
            ticketReq.setType(Constants.TICKET_INTEREST_TYPE_RED_PACKET);
            // 5. 支持红包，则获得红包信息
            ResMsg_Ticket_TicketList redRes = (ResMsg_Ticket_TicketList) regularService.handleMsg(ticketReq);
            if (CollectionUtils.isNotEmpty(redRes.getDataList())) {
                JSONArray jsonArray = new JSONArray();
                jsonArray.addAll(redRes.getDataList());
                model.put("redPacketList", jsonArray);
                model.put("redCount", redRes.getCount());
            }
        }
        if(Constants.IS_SUPPORT_TICKET_INTEREST_TRUE.equals(infoRes.getIsSupportInterestTicket())) {
            // 5. 支持加息券，则获得家加息券信息
            ticketReq.setType(Constants.TICKET_INTEREST_TYPE_INTEREST_TICKET);
            // 加息券
            ResMsg_Ticket_TicketList ticketRes = (ResMsg_Ticket_TicketList) regularService.handleMsg(ticketReq);
            if (CollectionUtils.isNotEmpty(ticketRes.getDataList())) {
                JSONArray jsonArray = new JSONArray();
                jsonArray.addAll(ticketRes.getDataList());
                model.put("ticketList", jsonArray);
                model.put("ticketCount", ticketRes.getCount());
            }
        }

        // 6. 是否显示产品可用总额度和用户使用额度（针对特定产品）
        ReqMsg_Product_InfoQuery proReqMsg = new ReqMsg_Product_InfoQuery();
        proReqMsg.setId(productId);
        ResMsg_Product_InfoQuery proResMsg = (ResMsg_Product_InfoQuery) regularService.handleMsg(proReqMsg);
        model.put("proLimitAmout", MoneyUtil.subtract(proResMsg.getMaxTotalAmount() == null ? 0 : proResMsg.getMaxTotalAmount(),proResMsg.getCurrTotalAmount()== null ? 0 : proResMsg.getCurrTotalAmount()).doubleValue());
        if(productId == Constants.PRODUCT_ID_NEWER_1_MONTH){
            if(StringUtil.isNotEmpty(userId)) {
                ReqMsg_UserProductLimit_UserProductLimitQuery req = new ReqMsg_UserProductLimit_UserProductLimitQuery();
                req.setUserId(Integer.valueOf(userId));
                req.setProductId(Integer.parseInt(request.getParameter("id")));
                ResMsg_UserProductLimit_UserProductLimitQuery res = (ResMsg_UserProductLimit_UserProductLimitQuery)regularService.handleMsg(req);
                model.put("userProLimitAmout", res.getLeftAmount());
            }
        }

        // 7. 用户信息(是否绑卡等)
        ReqMsg_User_InfoQuery user_InfoQuery = new ReqMsg_User_InfoQuery();
        user_InfoQuery.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery resMsg_User_InfoQuery = (ResMsg_User_InfoQuery)regularService.handleMsg(user_InfoQuery);

        model.put("regMobile", resMsg_User_InfoQuery.getMobile());
        model.put("isBindCard", resMsg_User_InfoQuery.getIsBindBank().equals(Constants.IS_BIND_BANK_YES) ? "TRUE" : "FALSE");
        // 8. 响应数据
        model.put("propertyType", infoRes.getPropertyType());
        // 单人投资限额
        model.put("maxInvestAmount", infoRes.getMaxInvestAmount());
        // 单笔投资限额
        model.put("maxSingleInvestAmount", infoRes.getMaxSingleInvestAmount());
        // 产品ID
        model.put("id", request.getParameter("id"));
        // 产品名称
        model.put("name", infoRes.getProductName());
        // 利率
        model.put("rate", infoRes.getRate());
        // 产品期限
        model.put("term", infoRes.getTrem());
        // 起投金额
        model.put("minInvestAmount", infoRes.getMinInvestAmount());
        // 资产合作方ZAN；YUN_DAI；7_DAI
        model.put("propertySymbol", infoRes.getPropertySymbol());
        // 支持红包
        model.put("isSupportRedPacket", infoRes.getIsSupportRedPacket());
        // 投资金额
        model.put("amount", request.getParameter("amount"));
        // 选中的红包ID
        model.put("redPacketId", request.getParameter("redPacketId"));
        // 是否使用红包
        model.put("useFlag", StringUtil.isEmpty(request.getParameter("useFlag"))? "yes": request.getParameter("useFlag"));
        // 用户可用余额
        model.put("balance", resMsg.getDepBalance());// 原本是JSH余额，现在改成了存管DEP_JSH余额
        model.put("isSupportInterestTicket", infoRes.getIsSupportInterestTicket());
        //qianbao178余额购买H5
        String from =  request.getParameter("from");
        if(Constants.FROM_178_APP.equals(from)) {
            model.put("from", from);
            model.put("backUrl", backUrl);
            log.info("【购买详情页结束】");
        	return "qianbao178/app/regular/buy_product_bind";
        }
        log.info("【购买详情页结束】");
        return "micro2.0/regular/buy_product_bind";
	}

	/**
	 * 
	 * @Title: intoBuyPage 
	 * @Description: 进入理财产品购买页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws
	 */
    @Token(save = true)
	@RequestMapping("gen2.0/regular/bind")
	public String genBind(HttpServletRequest request, Map<String,Object> model) {
        log.info("【购买详情页开始】");
        // 0. 前置校验
	    // 判断产品是否已经下线
        ReqMsg_Product_CheckProductIsOff reqOff = new ReqMsg_Product_CheckProductIsOff();
        try {
            reqOff.setProductId(Integer.parseInt(request.getParameter("id")));
        } catch (Exception e) {
            return "/gen2.0/regular/error";
        }
        ResMsg_Product_CheckProductIsOff resOff = (ResMsg_Product_CheckProductIsOff) regularService.handleMsg(reqOff);
        if(resOff.getIsOff()){
            model.put("isOff", true);
            return "/gen2.0/regular/error";
        }
		String userId = new CookieManager(request).getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);

        // 存管引导信息（未存管绑卡或激活）
        ReqMsg_DepGuide_FindDepGuideInfo depGuideReq = new ReqMsg_DepGuide_FindDepGuideInfo();
        depGuideReq.setUserId(Integer.parseInt(userId));
        depGuideReq.setContainRisk(true);
        ResMsg_DepGuide_FindDepGuideInfo depGuideRes = (ResMsg_DepGuide_FindDepGuideInfo) siteService.handleMsg(depGuideReq);
        if(Constants.HFBANK_GUIDE_NO_BIND_CARD.equals(depGuideRes.getIsOpened())
                || Constants.HFBANK_GUIDE_FAILED_BIND_HF.equals(depGuideRes.getIsOpened())) {
            // 未存管绑卡或激活
            return "redirect:/gen2.0/bankcard/index?entry=buy";
        } else if(Constants.HFBANK_GUIDE_WAIT_ACTIVATE.equals(depGuideRes.getIsOpened())) {
            return "redirect:/gen2.0/bankcard/activate/index";
        }
        if(Constants.is_expire.equals(depGuideRes.getRiskStatus())
                || Constants.is_no.equals(depGuideRes.getRiskStatus())) {
            // 需要进行风险测评
            return "redirect:/gen2.0/assets/assets";
        }

        Double buyAmount = request.getParameter("money") == null ? 0 : Double.valueOf(request.getParameter("money"));
        // 1. 查询本产品信息
  		ReqMsg_Product_InfoQuery req = new ReqMsg_Product_InfoQuery();
  		req.setId(Integer.valueOf(request.getParameter("id")));
  		ResMsg_Product_InfoQuery infoRes = (ResMsg_Product_InfoQuery) siteService.handleMsg(req);

        ReqMsg_Ticket_TicketList ticketReq = new ReqMsg_Ticket_TicketList();
        ticketReq.setBizType(Constants.TICKET_INTEREST_BIZ_TYPE_BUY);
        ticketReq.setUserId(Integer.parseInt(userId));
        ticketReq.setStatus(Constants.RED_PACKET_STATUS_INIT);
        ticketReq.setProductId(req.getId());
        DecimalFormat format = new DecimalFormat("#.##");
        if(Constants.IS_SUPPORT_RED_PACKET_TRUE.equals(infoRes.getIsSupportRedPacket())) {
            ticketReq.setType(Constants.TICKET_INTEREST_TYPE_RED_PACKET);
            ticketReq.setAmount(request.getParameter("money") == null ? 0 : Double.valueOf(request.getParameter("money")));
            // 5. 支持红包，则获得红包信息
            ResMsg_Ticket_TicketList redRes = (ResMsg_Ticket_TicketList) regularService.handleMsg(ticketReq);
            if (CollectionUtils.isNotEmpty(redRes.getDataList())) {
                model.put("redCanUseCount", this.canUseTicket(redRes.getDataList(), buyAmount));
                Double full;
                for (HashMap<String, Object> map: redRes.getDataList()) {
                    if(map.get("full") instanceof Double) {
                        full = (Double) map.get("full");
                    } else {
                        String fullStr = (String) map.get("full");
                        full = Double.valueOf(fullStr);
                    }
                    if(full.compareTo(10000d) >= 0) {
                        Double fullWan = MoneyUtil.divide(full, 10000d).doubleValue();
                        map.put("full", format.format(fullWan));
                        map.put("isWan", "yes");
                    } else {
                        map.put("full", format.format(full));
                    }
                }
                model.put("redPacketList", redRes.getDataList());
                model.put("redCount", redRes.getCount());
            }
        }
        if(Constants.IS_SUPPORT_TICKET_INTEREST_TRUE.equals(infoRes.getIsSupportInterestTicket())) {
            // 5. 支持加息券，则获得家加息券信息
            ticketReq.setType(Constants.TICKET_INTEREST_TYPE_INTEREST_TICKET);
            ticketReq.setAmount(request.getParameter("money") == null ? null : Double.valueOf(request.getParameter("money")));
            // 加息券
            ResMsg_Ticket_TicketList ticketRes = (ResMsg_Ticket_TicketList) regularService.handleMsg(ticketReq);
            if (CollectionUtils.isNotEmpty(ticketRes.getDataList())) {
                model.put("ticketCanUseCount", this.canUseTicket(ticketRes.getDataList(), buyAmount));
                Double full;
                for (HashMap<String, Object> map: ticketRes.getDataList()) {
                    if(map.get("full") instanceof Double) {
                        full = (Double) map.get("full");
                    } else {
                        String fullStr = (String) map.get("full");
                        full = Double.valueOf(fullStr);
                    }
                    if(full.compareTo(10000d) >= 0) {
                        Double fullWan = MoneyUtil.divide(full, 10000d).doubleValue();
                        map.put("full", format.format(fullWan));
                        map.put("isWan", "yes");
                    } else {
                        map.put("full", format.format(full));
                    }
                }
                model.put("ticketList", ticketRes.getDataList());
                model.put("ticketCount", ticketRes.getCount());
            }
        }


        // 3. 获得用户余额信息
        ReqMsg_User_UserBalanceQuery reqMsg = new ReqMsg_User_UserBalanceQuery();
        reqMsg.setUserId(userId);
        ResMsg_User_UserBalanceQuery resMsg = (ResMsg_User_UserBalanceQuery) regularService.handleMsg(reqMsg);

        // 4. 用户信息
        ReqMsg_User_InfoQuery user_InfoQuery = new ReqMsg_User_InfoQuery();
        user_InfoQuery.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery resMsg_User_InfoQuery = (ResMsg_User_InfoQuery)regularService.handleMsg(user_InfoQuery);

        // 5. 查询宝付支持网银支付的银行
//        ReqMsg_Bank_Pay19NetBankList reqMsgBank = new ReqMsg_Bank_Pay19NetBankList();
//        ResMsg_Bank_Pay19NetBankList resMsgBank = (ResMsg_Bank_Pay19NetBankList) regularService.handleMsg(reqMsgBank);

        model.put("propertyType", infoRes.getPropertyType());
        model.put("propertySymbol", infoRes.getPropertySymbol());
        model.put("productName", infoRes.getProductName());
        model.put("propertyType", infoRes.getPropertyType());
        // 单人投资限额
        model.put("maxInvestAmount", infoRes.getMaxInvestAmount());
        // 单笔投资限额
        model.put("maxSingleInvestAmount", infoRes.getMaxSingleInvestAmount());
        // 产品名称
        model.put("name", infoRes.getProductName());
        // 利率
        model.put("rate", infoRes.getRate());
        // 产品期限
        model.put("term", infoRes.getTrem());
        // 起投金额
        model.put("minInvestAmount", infoRes.getMinInvestAmount());
        // 资产合作方ZAN；YUN_DAI；7_DAI
        model.put("propertySymbol", infoRes.getPropertySymbol());
        // 支持红包
        model.put("isSupportRedPacket", infoRes.getIsSupportRedPacket());
        // 购买金额
        model.put("buyMoney", request.getParameter("money"));
        // 产品期限（已经换算成天的）
        model.put("dayNum", this.getTerm4Day(infoRes.getTrem()));
        // 产品ID
        model.put("id", request.getParameter("id"));

        model.put("mobile", resMsg_User_InfoQuery.getMobile());
        model.put("userName", resMsg_User_InfoQuery.getUserName());
        model.put("isBindCard", resMsg_User_InfoQuery.getIsBindBank().equals(Constants.IS_BIND_BANK_YES) ? "TRUE" : "FALSE");
        model.put("depBalance", resMsg.getDepBalance());
        model.put("balance", resMsg.getAvailableBalance());
        model.put("isSupportInterestTicket", infoRes.getIsSupportInterestTicket());
//        model.put("netBankList", resMsgBank.getBankList());
        log.info("【购买详情页结束】");
        return "gen2.0/regular/buy_product_bind";
	}

    public Integer getTerm4Day(Integer term) {
        Integer term4Day = 0;
        if (term == null || "".equals(term)) {
            return null;
        }
        if(term < 0){
            term4Day = Math.abs(term);
        }else if(term == 12){
            term4Day = 365;
        }else{
            term4Day = term*30;
        }
        return term4Day;
    }
	
	/**
	 * 
	 * @Title: firstBuyPage 
	 * @Description: 进入第一次购买产品页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws
	 */
    @Deprecated
	@Token(save = true)
	@RequestMapping("{channel}/regular/firstBuy")
	public String firstBuyPage(@PathVariable String channel,HttpServletRequest request, HttpServletResponse response,
			Map<String,Object> model) {
	    
	    // 数据校验，若不存在则跳转至理财列表页
	    if("micro2.0".equals(channel)){
	        if(StringUtils.isBlank(request.getParameter("id")) 
	                || StringUtils.isBlank(request.getParameter("name"))) {
	            if("qianbao".equals(request.getParameter("qianbao"))) {
	                return "redirect:/micro2.0/index?qianbao=qianbao";
	            } else {
	                return "redirect:/micro2.0/regular/list";
	            }
	        }
	    }
	    
		CookieManager cookieManager = new CookieManager(request);
		
		//查询本产品信息
  		ReqMsg_Product_InfoQuery infoReq = new ReqMsg_Product_InfoQuery();
  		infoReq.setId(Integer.valueOf(request.getParameter("id")));
  		ResMsg_Product_InfoQuery infoRes = (ResMsg_Product_InfoQuery) siteService.handleMsg(infoReq);
		model.put("propertyType", infoRes.getPropertyType());
		model.put("maxSingleInvestAmount", infoRes.getMaxSingleInvestAmount());
		model.put("id", request.getParameter("id"));
		model.put("name", request.getParameter("name"));
		model.put("rate", infoRes.getRate());
		model.put("term", infoRes.getTrem());
		model.put("minInvestAmount", infoRes.getMinInvestAmount());
		model.put("buy_money", request.getParameter("buyMoney"));
		model.put("amount", request.getParameter("amount"));
		model.put("productName", infoRes.getProductName());
		model.put("propertySymbol", infoRes.getPropertySymbol());
		
		//查询19付支持快捷支付的银行
		ReqMsg_Bank_Pay19BankList reqMsg = new ReqMsg_Bank_Pay19BankList();
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
        if (!"".equals(userId)) {
        	reqMsg.setUserId(Integer.parseInt(userId));
		}
		ResMsg_Bank_Pay19BankList resMsg = (ResMsg_Bank_Pay19BankList) regularService.handleMsg(reqMsg);
		model.put("bankList", resMsg.getBankList());
		
        String link = GlobEnv.getWebURL("/micro2.0/regular/list");
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            CookieManager manager = new CookieManager(request);
			String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
        }
        // 分享
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        
        // 其他信息回显
        model.put("userName", request.getParameter("userName"));
        model.put("idCard", request.getParameter("idCard"));
        model.put("cardNo", request.getParameter("cardNo"));
        model.put("selbank", request.getParameter("selbank"));
        model.put("mobile", request.getParameter("mobile"));
        
        // 获得红包信息
        if("micro2.0".equals(channel)) {
            model.put("amount", request.getParameter("amount"));
            model.put("redPacketId", request.getParameter("redPacketId"));
            model.put("useFlag", StringUtil.isEmpty(request.getParameter("useFlag"))? "yes": request.getParameter("useFlag"));
            
            ReqMsg_RedPacketInfo_RedPacketList info_RedPacketList = new ReqMsg_RedPacketInfo_RedPacketList();
            info_RedPacketList.setUserId(Integer.parseInt(userId));
            info_RedPacketList.setStatus(Constants.RED_PACKET_STATUS_INIT);
            info_RedPacketList.setProductId(Integer.parseInt(request.getParameter("id")));
            ResMsg_RedPacketInfo_RedPacketList redPacketList = (ResMsg_RedPacketInfo_RedPacketList) regularService.handleMsg(info_RedPacketList);
            ArrayList<HashMap<String, Object>> maps = redPacketList.getDataGrid();
            if (CollectionUtils.isNotEmpty(maps)) {
                JSONArray jsonArray = new JSONArray();
                jsonArray.addAll(maps);
                model.put("redPacketList", jsonArray);
                model.put("redPacket", redPacketList.getDataGrid().get(0));
            }
        } else {
            // 红包列表开始
            ReqMsg_RedPacketInfo_GetRedPacket reqRedPacket = new ReqMsg_RedPacketInfo_GetRedPacket();
            reqRedPacket.setUserId(Integer.parseInt(userId));
            reqRedPacket.setAmount(Double.valueOf(request.getParameter("buyMoney")));
            reqRedPacket.setStatus(Constants.RED_PACKET_STATUS_INIT);
            reqRedPacket.setProductId(Integer.parseInt(request.getParameter("id")));
            ResMsg_RedPacketInfo_GetRedPacket resRedPacket = (ResMsg_RedPacketInfo_GetRedPacket)regularService.handleMsg(reqRedPacket);
            model.put("redPackets", resRedPacket.getDataGrid());
            if (!CollectionUtils.isEmpty(resRedPacket.getDataGrid())) {
                model.put("redPacketId", resRedPacket.getDataGrid().get(0).get("id"));
            }
            // 红包列表结束
        }
        
        //是否显示产品可用总额度和用户使用额度
		int productId = Integer.parseInt(request.getParameter("id"));
		//if(productId == Constants.PRODUCT_ID_NEWER_1_MONTH || productId == Constants.PRODUCT_ID_ADD_RATE_1_YEAR) {
			ReqMsg_Product_InfoQuery proReqMsg = new ReqMsg_Product_InfoQuery();
    		proReqMsg.setId(Integer.parseInt(request.getParameter("id")));
    		ResMsg_Product_InfoQuery proResMsg = (ResMsg_Product_InfoQuery) regularService.handleMsg(proReqMsg);
    		//model.put("proLimitAmout", MoneyUtil.subtract(proResMsg.getMaxTotalAmount() == null ? 0 : proResMsg.getMaxTotalAmount() ,proResMsg.getCurrTotalAmount() == null ? 0 : proResMsg.getCurrTotalAmount()));
    		Double a= MoneyUtil.subtract(proResMsg.getMaxTotalAmount() == null ? 0 : proResMsg.getMaxTotalAmount(),proResMsg.getCurrTotalAmount() == null ? 0 : proResMsg.getCurrTotalAmount()).doubleValue();
			model.put("proLimitAmout", a);
    		if(productId == Constants.PRODUCT_ID_NEWER_1_MONTH) {
    			if(StringUtil.isNotEmpty(userId)) {
        			ReqMsg_UserProductLimit_UserProductLimitQuery req = new ReqMsg_UserProductLimit_UserProductLimitQuery();
        			req.setUserId(Integer.valueOf(userId));
        			req.setProductId(Integer.parseInt(request.getParameter("id")));
        			ResMsg_UserProductLimit_UserProductLimitQuery res = (ResMsg_UserProductLimit_UserProductLimitQuery)regularService.handleMsg(req);
        			model.put("userProLimitAmout", res.getLeftAmount());
        		}
    		}
		//}
        
		return channel + "/regular/buy_product_first";
	}
	
	/**
	 * 
	 * @Title: queryBindBankList 
	 * @Description: 查询绑定的银行卡列表
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("{channel}/regular/bindBankList")
	public Map<String, Object> queryBindBankList(@PathVariable String channel,HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		String userId = new CookieManager(request).getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		ReqMsg_Bank_bindBankList reqMsg = new ReqMsg_Bank_bindBankList();
		reqMsg.setUserId(Integer.parseInt(userId));
		ResMsg_Bank_bindBankList resMsg = (ResMsg_Bank_bindBankList) regularService.handleMsg(reqMsg);
		result.put("banks", resMsg);
		return result;
	}
	
	/**
	 * 
	 * @Title: newBankPage 
	 * @Description: 进入使用新银行卡界面
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws
	 */
//	@RequestMapping("{channel}/regular/newBankPage")
//	public String newBankPage(@PathVariable String channel,HttpServletRequest request,
//			HttpServletResponse response, Map<String,Object> model) {
//		String userId = new CookieManager(request).getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
//		ReqMsg_Bank_UserBankInfo reqMsg = new ReqMsg_Bank_UserBankInfo();
//		reqMsg.setUserId(Integer.parseInt(userId));
//		ResMsg_Bank_UserBankInfo resMsg = (ResMsg_Bank_UserBankInfo) regularService.handleMsg(reqMsg);
//		model.put("userName", resMsg.getUserName());
//		model.put("idCard", resMsg.getIdCard());
//		model.put("id", request.getParameter("id"));
//		model.put("name", request.getParameter("name"));
//		model.put("rate", request.getParameter("rate"));
//		model.put("term", request.getParameter("term"));
//		model.put("minInvestAmount", request.getParameter("minInvestAmount"));
//		model.put("buyAmount", request.getParameter("buyAmount"));
//		model.put("bankList", resMsg.getBankList());
//		return channel + "/regular/buy_product_newbank";
//	}
	
	/**
	 * 
	 * @Title: useMoneyToday 
	 * @Description: 计算用户当日已使用的限额
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("{channel}/regular/useMoneyToday")
	public Map<String, Object> useMoneyToday(@PathVariable String channel,HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		String bankId = request.getParameter("bankId");
		String userId = new CookieManager(request).getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		ReqMsg_Bank_AlreadyUseMoney reqMsg = new ReqMsg_Bank_AlreadyUseMoney();
		reqMsg.setBankId(Integer.parseInt(bankId));
		reqMsg.setUserId(Integer.parseInt(userId));
		ResMsg_Bank_AlreadyUseMoney resMsg = (ResMsg_Bank_AlreadyUseMoney) regularService.handleMsg(reqMsg);
		result.put("amount", resMsg.getAmount());
		return result;
	}

	/**
	 * 
	 * @Title: preOrder 
	 * @Description: 未绑定银行卡用户预下单
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 * @throws
	 */
    @Deprecated
	@ResponseBody
	@RequestMapping("micro2.0/regular/preOrder")
	public Map<String, Object> preOrder(HttpServletRequest request,
			HttpServletResponse response, ReqMsg_RegularBuy_Order reqMsg) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		//验证用户购买金额是否大于标的剩余额度或者大于可买额度
		@SuppressWarnings("unchecked")
		Map<String, Object> checkUserMap = (Map<String, Object>)request.getAttribute("resultMap");
		if(checkUserMap != null && !ConstantUtil.RESCODE_SUCCESS.equals(checkUserMap.get("resCode").toString())) {
			result.put("resCode", checkUserMap.get("resCode").toString());
			result.put("resMsg", checkUserMap.get("resMsg").toString());
			return result;
		}
		
		String userId = new CookieManager(request).getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		reqMsg.setUserId(Integer.parseInt(userId));
		reqMsg.setPlaceOrder(1);
		reqMsg.setTerminalType(1);
		ResMsg_RegularBuy_Order resMsg = (ResMsg_RegularBuy_Order) regularService.handleMsg(reqMsg);
		if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
			result.put("resCode", ConstantUtil.RESCODE_SUCCESS);
			result.put("orderNo", resMsg.getOrderNo());
			result.put("mobile", reqMsg.getMobile());
            result.put("htmlReapalString", resMsg.getHtml());
            if (resMsg.getHtml() !=null && !"fail".equals(resMsg.getHtml())) {
            	CookieManager manager = new CookieManager(request);
            	
            String qianbao = request.getParameter("qianbao");
              if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
              	manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_BS_QIANBAO_USER.getName(),
        	            "ReapalQuickCMB", true);
	        	manager.save(response, CookieEnums._SITE.getName(), null, "/",
	                    5*365 * 24 * 3600, true);
              }else {
                	manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_BS_QIANBAO_USER.getName(),
            	            null, true);
    	        	manager.save(response, CookieEnums._SITE.getName(), null, "/",
    	                    5*365 * 24 * 3600, true);
              }

			}
		}
		else {
			result.put("resCode", ConstantUtil.RESCODE_FAIL);
			result.put("resMsg", resMsg.getResMsg());
		}
		return result;
	}
	
	/**
	 * 
	 * @Title: order 
	 * @Description: 未绑定银行卡用户正式下单
	 * @param channel
	 * @param request
	 * @param response
	 * @param reqMsg
	 * @return
	 * @throws
	 */
    @Deprecated
	@ResponseBody
	@RequestMapping("micro2.0/regular/order")
	public Map<String, Object> order(HttpServletRequest request,
			HttpServletResponse response, ReqMsg_RegularBuy_Order reqMsg) {
		Map<String, Object> result = new HashMap<String, Object>();
//	    String link = GlobEnv.getWebURL("/micro2.0/regular/list");
//		// 钱报的参数
//		String qianbao = request.getParameter("qianbao");
//        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
//            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
//            link += "?qianbao=qianbao";
//        }
//        // 分享
//        Map<String,String> sign = weChatUtil.createAuth(request);
//        sign.put("link", link);
//        model.put("weichat", sign);
		
		//验证用户购买金额是否大于标的剩余额度或者大于可买额度
        @SuppressWarnings("unchecked")
        Map<String, Object> checkUserMap = (Map<String, Object>)request.getAttribute("resultMap");
        if(checkUserMap != null && !ConstantUtil.RESCODE_SUCCESS.equals(checkUserMap.get("resCode").toString())) {
            result.put("errorCode", checkUserMap.get("resCode").toString());
            result.put("errorMsg", checkUserMap.get("resMsg").toString());
            return result;
        }
        
		//重复提交
		if(Util.isRepeatSubmit(request, response)) {
			result.put("errorCode", "999");
			result.put("errorMsg", "购买失败：重复提交的请求！");
			return result;
		}
		
		String userId = new CookieManager(request).getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		reqMsg.setUserId(Integer.parseInt(userId));
		reqMsg.setPlaceOrder(2);
		reqMsg.setTerminalType(1);
		reqMsg.setRedPacketId(StringUtil.isBlank(request.getParameter("redPacketId"))? null : Integer.parseInt(request.getParameter("redPacketId")));

		ResMsg_RegularBuy_Order resMsg = (ResMsg_RegularBuy_Order) regularService.handleMsg(reqMsg);
		if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
			result.put("errorCode", "000");
			result.put("errorMsg", "购买成功");
			return result;
		}
		else {
			result.put("errorCode", "999");
			result.put("errorMsg", "购买失败："+resMsg.getResMsg());
			return result;
		}
	}
	
	/**
	 * 
	 * @Title: preOrder 
	 * @Description: 绑定银行卡用户预下单
	 * @param request
	 * @param response
	 * @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("micro2.0/regular/regularPreOrder")
	public Map<String, Object> regularPreOrder(HttpServletRequest request,
			HttpServletResponse response, ReqMsg_RegularBuy_Order reqMsg) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		//验证用户购买金额是否大于标的剩余额度或者大于可买额度
		@SuppressWarnings("unchecked")
		Map<String, Object> checkUserMap = (Map<String, Object>)request.getAttribute("resultMap");
		if(checkUserMap != null && !ConstantUtil.RESCODE_SUCCESS.equals(checkUserMap.get("resCode").toString())) {
			result.put("resCode", checkUserMap.get("resCode").toString());
			result.put("resMsg", checkUserMap.get("resMsg").toString());
			return result;
		}
        // 重复提交
        if (Util.isRepeatSubmit(request, response, Constants.PRE_TOKEN_KEY, CookieEnums._BIZ_PRE_TOKEN)) {
            result.put("resCode", "999");
            result.put("resMsg", "充值失败：重复提交的请求！");
            return result;
        }
		String userId = new CookieManager(request).getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		ReqMsg_Bank_QueryBankInfoByUser req = new ReqMsg_Bank_QueryBankInfoByUser();
		req.setUserId(Integer.parseInt(userId));
		req.setBankId(reqMsg.getBankId());
		
		ResMsg_Bank_QueryBankInfoByUser res = (ResMsg_Bank_QueryBankInfoByUser) regularService.handleMsg(req);
		if(StringUtil.isNotBlank(res.getIdCard())) {
			reqMsg.setBankName(res.getBankName());
			reqMsg.setCardNo(res.getCardNo());
			reqMsg.setIdCard(res.getIdCard());
			reqMsg.setMobile(res.getMobile());
			reqMsg.setUserId(Integer.parseInt(userId));
			reqMsg.setUserName(res.getUserName());
			reqMsg.setPlaceOrder(1);
			reqMsg.setTerminalType(1);
			ResMsg_RegularBuy_Order resMsg = (ResMsg_RegularBuy_Order) regularService.handleMsg(reqMsg);
			if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {

				result.put("resCode", ConstantUtil.RESCODE_SUCCESS);
				result.put("orderNo", resMsg.getOrderNo());
				result.put("mobile", reqMsg.getMobile());
	            result.put("htmlReapalString", resMsg.getHtml());
	            if (resMsg.getHtml() !=null && !"fail".equals(resMsg.getHtml())) {
	            	CookieManager manager = new CookieManager(request);
	                String qianbao = request.getParameter("qianbao");
	                if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
	                	manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_BS_QIANBAO_USER.getName(),
	          	            "ReapalQuickCMB", true);
	                	manager.save(response, CookieEnums._SITE.getName(), null, "/",
	  	                    5*365 * 24 * 3600, true);
	                }else {
	                	manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_BS_QIANBAO_USER.getName(),
		          	            null, true);
		  	        	manager.save(response, CookieEnums._SITE.getName(), null, "/",
		  	                    5*365 * 24 * 3600, true);
					}

				}
			}
			else {
				result.put("resMsg", resMsg.getResMsg());
				result.put("resCode", ConstantUtil.RESCODE_FAIL);
                Util.createToken(request, response, Constants.PRE_TOKEN_KEY, CookieEnums._BIZ_PRE_TOKEN);
                return result;
			}
		}
		else {
			result.put("resMsg", "预下单失败，用户不存在或银行卡已失效");
			result.put("resCode", ConstantUtil.RESCODE_FAIL);
		}
        result.put("token", Util.createToken(request, response));
		return result;
	}
	
	/**
	 * 
	 * @Title: order 
	 * @Description: 绑定银行卡用户正式下单
	 * @param channel
	 * @param request
	 * @param response
	 * @param reqMsg
	 * @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("micro2.0/regular/regularOrder")
	public Map<String, Object> regularOrder(HttpServletRequest request,HttpServletResponse response,
			ReqMsg_RegularBuy_Order reqMsg) {
		Map<String, Object> result = new HashMap<String, Object>();
//	    String link = GlobEnv.getWebURL("/micro2.0/regular/list");
//        // 钱报的参数
//        String qianbao = request.getParameter("qianbao");
//        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
//            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
//            link += "?qianbao=qianbao";
//        }
//        // 分享
//        Map<String,String> sign = weChatUtil.createAuth(request);
//        sign.put("link", link);
//        model.put("weichat", sign);
		
		//验证用户购买金额是否大于标的剩余额度或者大于可买额度
        @SuppressWarnings("unchecked")
        Map<String, Object> checkUserMap = (Map<String, Object>)request.getAttribute("resultMap");
        if(checkUserMap != null && !ConstantUtil.RESCODE_SUCCESS.equals(checkUserMap.get("resCode").toString())) {
            result.put("errorCode", checkUserMap.get("resCode").toString());
            result.put("errorMsg", checkUserMap.get("resMsg").toString());
            return result;
        }
        
		//重复提交
		if(Util.isRepeatSubmit(request, response)) {
			result.put("errorCode", "999");
			result.put("errorMsg", "购买失败：重复提交的请求！");
			return result;
		}
		String userId = new CookieManager(request).getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		ReqMsg_Bank_QueryBankInfoByUser req = new ReqMsg_Bank_QueryBankInfoByUser();
		req.setUserId(Integer.parseInt(userId));
		req.setBankId(reqMsg.getBankId());
		ResMsg_Bank_QueryBankInfoByUser res = (ResMsg_Bank_QueryBankInfoByUser) regularService.handleMsg(req);
		
		if(StringUtil.isNotBlank(res.getIdCard())) {
			reqMsg.setBankName(res.getBankName());
			reqMsg.setCardNo(res.getCardNo());
			reqMsg.setIdCard(res.getIdCard());
			reqMsg.setMobile(res.getMobile());
			reqMsg.setUserId(Integer.parseInt(userId));
			reqMsg.setUserName(res.getUserName());
			reqMsg.setRedPacketId(StringUtil.isBlank(request.getParameter("redPacketId"))? null : Integer.parseInt(request.getParameter("redPacketId")));
			reqMsg.setPlaceOrder(2);
			reqMsg.setTerminalType(1);
			ResMsg_RegularBuy_Order resMsg = (ResMsg_RegularBuy_Order) regularService.handleMsg(reqMsg);
			if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
				result.put("errorCode", "000");
				result.put("errorMsg", "购买成功");
				return result;
			}
			else {
				result.put("errorCode", "999");
				result.put("errorMsg", "购买失败："+resMsg.getResMsg());
				return result;
			}
		}
		else {
			result.put("errorCode", "999");
			result.put("errorMsg", "购买失败：用户不存在或银行卡已失效");
			return result;
		}
	}
	
	/**
	 * 
	 * @Title: gotoResultPage 
	 * @Description: 正式下单之后跳转到结果页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws
	 */
	@RequestMapping("micro2.0/regular/gotoResultPage")
	public String gotoResultPage(HttpServletRequest request,HttpServletResponse response,Map<String, Object> model) {
	    String link = GlobEnv.getWebURL("/micro2.0/regular/list");
        // 钱报的参数
        CookieManager manager = new CookieManager(request);

        String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
			String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
        }
        // 分享
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
	    String code = request.getParameter("code");
		String msg = request.getParameter("msg");
        String backUrl = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_178_BACK_URL.getName(), true);
        if(StringUtil.isNotBlank(backUrl)) {
            model.put("backUrl", backUrl);
        }
		if("000".equals(code)) {
			return "micro2.0/regular/buy_product_success";
		}
		else {
		    model.put("productId", request.getParameter("productId"));
			model.put("errorMsg", msg);
			return "micro2.0/regular/buy_product_error";
		}
	}
	
	/**
	 * 
	 * @Title: balanceBuy 
	 * @Description: 余额购买
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("micro2.0/regular/balanceBuy")
	public Map<String, Object> microBalanceBuy(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		//验证用户购买金额是否大于标的剩余额度或者大于可买额度
        @SuppressWarnings("unchecked")
        Map<String, Object> checkUserMap = (Map<String, Object>)request.getAttribute("resultMap");
        if(checkUserMap != null && !ConstantUtil.RESCODE_SUCCESS.equals(checkUserMap.get("resCode").toString())) {
            result.put("errorCode", checkUserMap.get("resCode").toString());
            result.put("errorMsg", checkUserMap.get("resMsg").toString());
            return result;
        }
        
		//重复提交
		if(Util.isRepeatSubmit(request, response)) {
			result.put("errorCode", "999");
			result.put("errorMsg", "购买失败：重复提交的请求！");
			return result;
		}
		String userId = new CookieManager(request).getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);

		ReqMsg_User_InfoQuery user_InfoQuery = new ReqMsg_User_InfoQuery();
		user_InfoQuery.setUserId(Integer.parseInt(userId));
	    ResMsg_User_InfoQuery resMsg_User_InfoQuery = (ResMsg_User_InfoQuery)regularService.handleMsg(user_InfoQuery);
		
		
		//校验验证码是否正确
        ReqMsg_User_ValidUser reqMsg_User_ValidUser = new ReqMsg_User_ValidUser();
        reqMsg_User_ValidUser.setMobile(resMsg_User_InfoQuery.getMobile());
        reqMsg_User_ValidUser.setMobileCode(request.getParameter("verifyCode"));
        ResMsg_User_ValidUser resMsg_User_Valid = (ResMsg_User_ValidUser) userService.handleMsg(reqMsg_User_ValidUser);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg_User_Valid.getResCode())) {
        	ReqMsg_RegularBuy_BalanceBuy req = new ReqMsg_RegularBuy_BalanceBuy();
        	req.setUserId(Integer.parseInt(userId));
        	req.setTerminalType(1);
        	req.setAmount(Double.parseDouble(request.getParameter("amount")));
        	req.setProductId(Integer.parseInt(request.getParameter("productId")));
        	req.setTicketId(StringUtil.isBlank(request.getParameter("redPacketId"))?null:Integer.parseInt(request.getParameter("redPacketId")));
            req.setTicketType(StringUtil.isBlank(request.getParameter("ticketType")) ? null: request.getParameter("ticketType"));
            ResMsg_RegularBuy_BalanceBuy res = (ResMsg_RegularBuy_BalanceBuy) regularService.handleMsg(req);
        	if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
        		result.put("errorCode", "000");
				result.put("errorMsg", "购买成功");
				return result;
        	} else {
        		result.put("errorCode", res.getResCode());
				result.put("errorMsg", "余额购买失败："+res.getResMsg());
				return result;
        	}
        }
        else {
        	result.put("errorCode", "999");
			result.put("errorMsg", resMsg_User_Valid.getResMsg());
			return result;
        }
	}

    /**
     * 定期理财购买协议
     * @param channel
     * @param req
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/regular/buyagreement")
    public String regularBuyAgreementInit(@PathVariable String channel, ReqMsg_User_InfoQuery req,
            HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> model, String investId) {
			CookieManager cookieManager = new CookieManager(request);
			String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
			CookieEnums._SITE_USER_ID.getName(), true);
			req.setUserId(Integer.parseInt(userId));
			ResMsg_User_InfoQuery resMsg = (ResMsg_User_InfoQuery) regularService.handleMsg(req);
			model.put("mobile", resMsg.getMobile());
			model.put("IdCard", resMsg.getIdCard());
			//model.put("term", term);
			model.put("userName", resMsg.getUserName());
			//根据subAccountId查询
			ReqMsg_Account_SubAccountById reqAccount = new ReqMsg_Account_SubAccountById();
			try {
			    reqAccount.setId(Integer.parseInt(investId));
			    model.put("financialNo", investId);
            } catch (Exception e) {
                String url = "";
                if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                    model.put("agreementName", "加入记录");
                    url = channel + "/regular/agreement_pc_errorPage";
                } else {
                    String link = GlobEnv.getWebURL("/micro2.0/index");
                    WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                    url = channel + "/agreement/product_not_self_error";
                }
                return url;
            }
			ResMsg_Account_SubAccountById resAccount = (ResMsg_Account_SubAccountById) regularService.handleMsg(reqAccount);
			
			//查询产品信息
			ReqMsg_Product_InfoQuery reqMsg = new ReqMsg_Product_InfoQuery();
			if(null == resAccount.getProductId()){
			    String url = "";
                if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                    model.put("agreementName", "加入记录");
                    url = channel + "/regular/agreement_pc_errorPage";
                }else {
                    String link = GlobEnv.getWebURL("/micro2.0/index");
                    WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                    url = channel + "/agreement/product_not_self_error";
                }
                return url;
			}
			reqMsg.setId(resAccount.getProductId());
			ResMsg_Product_InfoQuery resMsg1 = (ResMsg_Product_InfoQuery) regularService
			.handleMsg(reqMsg);
			
			
			//投资期限转换
			int term = resMsg1.getTrem();
			int term4Day;
			if(term < 0){
				term4Day = Math.abs(term);
			}else if(term == 12){
				term4Day = 365;
			}else{
				term4Day = term*30;
			}
			model.put("dayNum", term4Day);
			model.put("rate", resMsg1.getRate());
			model.put("name", resMsg1.getProductName());
			model.put("amount", resAccount.getBalance());
			
			ReqMsg_Product_BuyAgreement reqMsgPB = new ReqMsg_Product_BuyAgreement();
	        reqMsgPB.setProductId(resAccount.getProductId());
	        reqMsgPB.setUserId(Integer.parseInt(userId));
	        ResMsg_Product_BuyAgreement resPB = (ResMsg_Product_BuyAgreement) siteService.handleMsg(reqMsgPB);
	        if(resPB.getIsSelf() == false) {
	            String url = "";
	            if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                    model.put("agreementName", "加入记录");
                    url = channel + "/regular/agreement_pc_errorPage";
	            }else {
                    String link = GlobEnv.getWebURL("/micro2.0/index");
                    WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                    url = channel + "/agreement/product_not_self_error";
                }
	            return url;
	        }
	        
			//interestBeginDate 起息日
			if (resAccount.getInterestBeginDate() != null) {
			    model.put("times", DateUtil.formatDateTime(resAccount.getInterestBeginDate(), "yyyy年MM月dd日"));
			} else {
			    model.put("times", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
			}
			
			 //到期日
	        if (resAccount.getLastFinishInterestDate() != null) {
	            model.put("endTime", DateUtil.formatDateTime(resAccount.getLastFinishInterestDate(), "yyyy年MM月dd日"));
	        } else {
	            model.put("endTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
	        }
			
	        //签订时间
			if (resAccount.getOpenTime() != null) {
			    model.put("openTime", DateUtil.formatDateTime(resAccount.getOpenTime(), "yyyy年MM月dd日"));
            } else {
			    model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
			}
			String url = "";
			//产品---目标债权类别
			String propertyType= request.getParameter("propertyType");
            if("micro2.0".equals(channel)) {
                if(Constants.PRODUCT_PROPERTY_TYPE_CONSUME.equals(propertyType)){
                    url = channel + "/agreement/financial_have_buy";
                }else{
                    if (null == resMsg1.getPropertySymbol()) {
                        if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                            model.put("agreementName", "加入记录");
                            url = channel + "/regular/agreement_pc_errorPage";
                        }else {
                            String link = GlobEnv.getWebURL("/micro2.0/index");
                            WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                            url = channel + "/agreement/product_not_self_error";
                        }
                        return url;
                    }

                    if (Constants.PROPERTY_SYMBOL_YUN_DAI.equals(resMsg1.getPropertySymbol())) {

                        ReqMsg_Product_QueryYunDaiChangeNameDate queryYunDaiChangeNameDate = new ReqMsg_Product_QueryYunDaiChangeNameDate();
                        ResMsg_Product_QueryYunDaiChangeNameDate queryYunDaiChangeNameDateRes = (ResMsg_Product_QueryYunDaiChangeNameDate) regularService.handleMsg(queryYunDaiChangeNameDate);
                        if (resAccount.getOpenTime().compareTo(queryYunDaiChangeNameDateRes.getChangeNameDate()) <= 0 ) {
                            model.put("secondPartyName", "达飞普惠财富投资管理（北京）有限公司");
                            model.put("sign_img", "PU_HUI");
                        }else {
                            model.put("secondPartyName", "达飞云贷科技（北京）有限公司");
                            model.put("sign_img", "YUN_DAI");
                        }
                        model.put("propertySymbol", Constants.PROPERTY_SYMBOL_YUN_DAI);
                        model.put("secondPartyAddress", "北京市朝阳区四惠地铁站平台二层京通大厦B1区5层");
                        // 7贷老产品3月10号重新匹配后 出借服务协议对应的协议编号
                        model.put("agreementNo", resAccount.getNote() == null ? "" : resAccount.getNote());

                    }else if (Constants.PROPERTY_SYMBOL_7_DAI.equals(resMsg1.getPropertySymbol())) {
                        model.put("propertySymbol", Constants.PROPERTY_SYMBOL_7_DAI);
                        model.put("secondPartyName", "深圳市前海龙汇通互联网金融服务有限公司");
                        model.put("secondPartyAddress", "深圳市福田区华富路1018号中航中心25楼");
                        // 7贷老产品3月10号重新匹配后 出借服务协议对应的协议编号
                        model.put("agreementNo", resAccount.getNote() == null ? "" : resAccount.getNote());
                    } else if(Constants.PROPERTY_SYMBOL_ZAN.equals(resMsg1.getPropertySymbol())){
                        model.put("propertySymbol", Constants.PROPERTY_SYMBOL_7_DAI);
                        model.put("secondPartyName", "深圳市前海龙汇通互联网金融服务有限公司");
                        model.put("secondPartyAddress", "深圳市福田区华富路1018号中航中心25楼");
                        url = channel + "/agreement/financial_have_buy_zan";
                        return url;
                    }else if( Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(resMsg1.getPropertySymbol())) {//H5存管产品对应的借款咨询服务协议
                        //出借金额
                        model.put("openBalance", resAccount.getOpenBalance());
                        ReqMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionReq = new ReqMsg_AgreementVersion_QueryAgreementVersionInfo();
                        agreementVersionReq.setAgreementType(Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_POWERATTORNEY);
                        agreementVersionReq.setAgreementEffectiveStartTime(DateUtil.format(resAccount.getOpenTime()));
                        ResMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionRes =
                                (ResMsg_AgreementVersion_QueryAgreementVersionInfo) siteService.handleMsg(agreementVersionReq);
                        if(Constants.AGREEMENT_VERSION_NUMBER_NO_VERSION.equals(agreementVersionRes.getAgreementVersion())) {
                            url = channel + "/agreement/lend_service_agreement";
                        }else if(Constants.AGREEMENT_VERSION_NUMBER_1_1.equals(agreementVersionRes.getAgreementVersion())) {//H5授权委托书
                            //购买的云贷、7贷理财产品提前终止违约金百分比查询
                            ReqMsg_AgreementVersion_QueryBerachContractRate berachContractRateReq = new ReqMsg_AgreementVersion_QueryBerachContractRate();
                            ResMsg_AgreementVersion_QueryBerachContractRate berachContractRateRes =
                                    (ResMsg_AgreementVersion_QueryBerachContractRate) siteService.handleMsg(berachContractRateReq);
                            model.put("berachContractRate", berachContractRateRes.getBerachContractRate());
                            url = channel + "/agreement/hf_yundai_powerAttorney_"+agreementVersionRes.getAgreementVersion();
                        }else if(Constants.AGREEMENT_VERSION_NUMBER_1_2.equals(agreementVersionRes.getAgreementVersion())) {
                            //购买的云贷、7贷理财产品提前终止违约金百分比查询
                            ReqMsg_AgreementVersion_QueryBerachContractRate berachContractRateReq = new ReqMsg_AgreementVersion_QueryBerachContractRate();
                            ResMsg_AgreementVersion_QueryBerachContractRate berachContractRateRes =
                                    (ResMsg_AgreementVersion_QueryBerachContractRate) siteService.handleMsg(berachContractRateReq);
                            model.put("berachContractRate", berachContractRateRes.getBerachContractRate());
                            url = channel + "/agreement/hf_yundai_powerAttorney_"+agreementVersionRes.getAgreementVersion();
                        }
                        return url;
                    }else if( Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(resMsg1.getPropertySymbol())) {//H5存管产品对应的借款咨询服务协议
                        //出借金额
                        model.put("openBalance", resAccount.getOpenBalance());
                        ReqMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionReq = new ReqMsg_AgreementVersion_QueryAgreementVersionInfo();
                        agreementVersionReq.setAgreementType(Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_POWERATTORNEY);
                        agreementVersionReq.setAgreementEffectiveStartTime(DateUtil.format(resAccount.getOpenTime()));
                        ResMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionRes =
                                (ResMsg_AgreementVersion_QueryAgreementVersionInfo) siteService.handleMsg(agreementVersionReq);
                        if(Constants.AGREEMENT_VERSION_NUMBER_NO_VERSION.equals(agreementVersionRes.getAgreementVersion())) {
                            url = channel + "/agreement/lend_service_agreement";
                        }else if(Constants.AGREEMENT_VERSION_NUMBER_1_1.equals(agreementVersionRes.getAgreementVersion())) {//H5授权委托书
                            //购买的云贷、7贷理财产品提前终止违约金百分比查询
                            ReqMsg_AgreementVersion_QueryBerachContractRate berachContractRateReq = new ReqMsg_AgreementVersion_QueryBerachContractRate();
                            ResMsg_AgreementVersion_QueryBerachContractRate berachContractRateRes =
                                    (ResMsg_AgreementVersion_QueryBerachContractRate) siteService.handleMsg(berachContractRateReq);
                            model.put("berachContractRate", berachContractRateRes.getBerachContractRate());
                            url = channel + "/agreement/hf_yundai_powerAttorney_"+agreementVersionRes.getAgreementVersion();
                        }else if(Constants.AGREEMENT_VERSION_NUMBER_1_2.equals(agreementVersionRes.getAgreementVersion())) {
                            //购买的云贷、7贷理财产品提前终止违约金百分比查询
                            ReqMsg_AgreementVersion_QueryBerachContractRate berachContractRateReq = new ReqMsg_AgreementVersion_QueryBerachContractRate();
                            ResMsg_AgreementVersion_QueryBerachContractRate berachContractRateRes =
                                    (ResMsg_AgreementVersion_QueryBerachContractRate) siteService.handleMsg(berachContractRateReq);
                            model.put("berachContractRate", berachContractRateRes.getBerachContractRate());
                            url = channel + "/agreement/hf_yundai_powerAttorney_"+agreementVersionRes.getAgreementVersion();
                        }
                        return url;
                    }else if( Constants.PROPERTY_SYMBOL_FREE.equals(resMsg1.getPropertySymbol())) {//H5自由产品对应的授权委托书
                        //出借金额
                        model.put("openBalance", resAccount.getOpenBalance());
                        ReqMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionReq = new ReqMsg_AgreementVersion_QueryAgreementVersionInfo();
                        agreementVersionReq.setAgreementType(Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_POWERATTORNEY);
                        agreementVersionReq.setAgreementEffectiveStartTime(DateUtil.format(resAccount.getOpenTime()));
                        ResMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionRes =
                                (ResMsg_AgreementVersion_QueryAgreementVersionInfo) siteService.handleMsg(agreementVersionReq);
                        if(Constants.AGREEMENT_VERSION_NUMBER_1_1.equals(agreementVersionRes.getAgreementVersion())) {//H5授权委托书
                            //购买的云贷、7贷理财产品提前终止违约金百分比查询
                            ReqMsg_AgreementVersion_QueryBerachContractRate berachContractRateReq = new ReqMsg_AgreementVersion_QueryBerachContractRate();
                            ResMsg_AgreementVersion_QueryBerachContractRate berachContractRateRes =
                                    (ResMsg_AgreementVersion_QueryBerachContractRate) siteService.handleMsg(berachContractRateReq);
                            model.put("berachContractRate", berachContractRateRes.getBerachContractRate());
                            url = channel + "/agreement/hf_yundai_powerAttorney_"+agreementVersionRes.getAgreementVersion();
                        }else if(Constants.AGREEMENT_VERSION_NUMBER_1_2.equals(agreementVersionRes.getAgreementVersion())) {
                            //购买的云贷、7贷理财产品提前终止违约金百分比查询
                            ReqMsg_AgreementVersion_QueryBerachContractRate berachContractRateReq = new ReqMsg_AgreementVersion_QueryBerachContractRate();
                            ResMsg_AgreementVersion_QueryBerachContractRate berachContractRateRes =
                                    (ResMsg_AgreementVersion_QueryBerachContractRate) siteService.handleMsg(berachContractRateReq);
                            model.put("berachContractRate", berachContractRateRes.getBerachContractRate());
                            url = channel + "/agreement/hf_yundai_powerAttorney_"+agreementVersionRes.getAgreementVersion();
                        }
                        return url;
                    }else if( Constants.PROPERTY_SYMBOL_ZSD.equals(resMsg1.getPropertySymbol())) {//H5赞时贷产品对应的借款咨询服务协议
                        //出借金额
                        model.put("openBalance", resAccount.getOpenBalance());
                        url = channel + "/agreement/lend_service_agreement";
                        return url;
                    }else if( Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(resMsg1.getPropertySymbol())) {//H5 7贷存管产品对应的借款咨询服务协议
                        //出借金额
                        model.put("openBalance", resAccount.getOpenBalance());
                        url = channel + "/agreement/seven_lend_service_agreement";
                        return url;
                    } else {
                        String link = GlobEnv.getWebURL("/micro2.0/index");
                        WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                        url = channel + "/agreement/product_not_self_error";
                        return url;
                    }
                    //现金循环贷---新协议
                    url = channel +  "/agreement/financial_have_buy_new";
                    // 云贷老产品3月10号重新匹配后 出借服务协议
                    if(StringUtil.isNotBlank(resAccount.getNote())) {
                        //出借金额
                        model.put("openBalance", resAccount.getOpenBalance());
                        model.put("agreementNo", resAccount.getNote());
                        url = channel + "/agreement/rematch_financial_have_buy_new";
                    }
                }
            }

        if("gen2.0".equals(channel) || "gen178".equals(channel)) {
        	
        	if(Constants.PRODUCT_PROPERTY_TYPE_CONSUME.equals(propertyType)){
        		url = channel + "/regular/financial_have_buy";
        	}else{
        		
    			if (null == resMsg1.getPropertySymbol()) {
                   if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                       model.put("agreementName", "加入记录");
                       url = channel + "/regular/agreement_pc_errorPage";
                   }else {
                       String link = GlobEnv.getWebURL("/micro2.0/index");
                       WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                       url = channel + "/agreement/product_not_self_error";
                   }
                   return url;
    			}
    			if (Constants.PROPERTY_SYMBOL_YUN_DAI.equals(resMsg1.getPropertySymbol())) {
    				
    				ReqMsg_Product_QueryYunDaiChangeNameDate queryYunDaiChangeNameDate = new ReqMsg_Product_QueryYunDaiChangeNameDate();
    				ResMsg_Product_QueryYunDaiChangeNameDate queryYunDaiChangeNameDateRes = (ResMsg_Product_QueryYunDaiChangeNameDate) regularService.handleMsg(queryYunDaiChangeNameDate);
    				if (resAccount.getOpenTime().compareTo(queryYunDaiChangeNameDateRes.getChangeNameDate()) <= 0 ) {
    					model.put("secondPartyName", "达飞普惠财富投资管理（北京）有限公司");
                        model.put("sign_img", "PU_HUI");
    				}else {
    					model.put("secondPartyName", "达飞云贷科技（北京）有限公司");
                        model.put("sign_img", "YUN_DAI");
    				}
    				
    				model.put("propertySymbol", Constants.PROPERTY_SYMBOL_YUN_DAI);
    				
    				model.put("secondPartyAddress", "北京市朝阳区四惠地铁站平台二层京通大厦B1区5层");
                    // 7贷老产品3月10号重新匹配后 出借服务协议对应的协议编号
                    model.put("agreementNo", resAccount.getNote() == null ? "" : resAccount.getNote());
    				
    			}else if (Constants.PROPERTY_SYMBOL_7_DAI.equals(resMsg1.getPropertySymbol())) {
                    // 7贷老产品3月10号重新匹配后 出借服务协议对应的协议编号
                    model.put("agreementNo", resAccount.getNote() == null ? "" : resAccount.getNote());

    				model.put("propertySymbol", Constants.PROPERTY_SYMBOL_7_DAI);
    				model.put("secondPartyName", "深圳市前海龙汇通互联网金融服务有限公司");
    				model.put("secondPartyAddress", "深圳市福田区华富路1018号中航中心25楼");
    			}else if (Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(resMsg1.getPropertySymbol())) {//存管产品对应的借款咨询服务协议
                    model.put("propertySymbol", Constants.PROPERTY_SYMBOL_YUN_DAI);
                    //出借金额
                    model.put("openBalance", resAccount.getOpenBalance());
                    ReqMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionReq = new ReqMsg_AgreementVersion_QueryAgreementVersionInfo();
                    agreementVersionReq.setAgreementType(Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_POWERATTORNEY);
                    agreementVersionReq.setAgreementEffectiveStartTime(DateUtil.format(resAccount.getOpenTime()));
                    ResMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionRes =
                            (ResMsg_AgreementVersion_QueryAgreementVersionInfo) siteService.handleMsg(agreementVersionReq);

                    if(Constants.AGREEMENT_VERSION_NUMBER_NO_VERSION.equals(agreementVersionRes.getAgreementVersion())) {
                        url = channel + "/regular/lend_service_agreement";
                    }else if(Constants.AGREEMENT_VERSION_NUMBER_1_1.equals(agreementVersionRes.getAgreementVersion())) {//授权委托书
                        //购买的云贷、7贷理财产品提前终止违约金百分比查询
                        ReqMsg_AgreementVersion_QueryBerachContractRate berachContractRateReq = new ReqMsg_AgreementVersion_QueryBerachContractRate();
                        ResMsg_AgreementVersion_QueryBerachContractRate berachContractRateRes =
                                (ResMsg_AgreementVersion_QueryBerachContractRate) siteService.handleMsg(berachContractRateReq);
                        model.put("berachContractRate", berachContractRateRes.getBerachContractRate());
                        url = channel + "/regular/hf_yundai_powerAttorney_"+agreementVersionRes.getAgreementVersion();
                    }else if(Constants.AGREEMENT_VERSION_NUMBER_1_2.equals(agreementVersionRes.getAgreementVersion())) {//授权委托书
                        //购买的云贷、7贷理财产品提前终止违约金百分比查询
                        ReqMsg_AgreementVersion_QueryBerachContractRate berachContractRateReq = new ReqMsg_AgreementVersion_QueryBerachContractRate();
                        ResMsg_AgreementVersion_QueryBerachContractRate berachContractRateRes =
                                (ResMsg_AgreementVersion_QueryBerachContractRate) siteService.handleMsg(berachContractRateReq);
                        model.put("berachContractRate", berachContractRateRes.getBerachContractRate());
                        url = channel + "/regular/hf_yundai_powerAttorney_"+agreementVersionRes.getAgreementVersion();
                    }
                    return url;
                }else if(Constants.PROPERTY_SYMBOL_ZSD.equals(resMsg1.getPropertySymbol())) {//赞时贷产品对应的借款咨询服务协议
                    model.put("propertySymbol", Constants.PROPERTY_SYMBOL_ZSD);
                    //出借金额
                    model.put("openBalance", resAccount.getOpenBalance());
                    url = channel + "/regular/lend_service_agreement";
                    return url;

                }else if (Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(resMsg1.getPropertySymbol())) {//存管7贷产品对应的借款咨询服务协议
                    model.put("propertySymbol", Constants.PROPERTY_SYMBOL_7_DAI_SELF);
                    //出借金额
                    model.put("openBalance", resAccount.getOpenBalance());
                    ReqMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionReq = new ReqMsg_AgreementVersion_QueryAgreementVersionInfo();
                    agreementVersionReq.setAgreementType(Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_POWERATTORNEY);
                    agreementVersionReq.setAgreementEffectiveStartTime(DateUtil.format(resAccount.getOpenTime()));
                    ResMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionRes =
                            (ResMsg_AgreementVersion_QueryAgreementVersionInfo) siteService.handleMsg(agreementVersionReq);

                    if(Constants.AGREEMENT_VERSION_NUMBER_NO_VERSION.equals(agreementVersionRes.getAgreementVersion())) {
                        url = channel + "/regular/lend_service_agreement";
                    }else if(Constants.AGREEMENT_VERSION_NUMBER_1_1.equals(agreementVersionRes.getAgreementVersion())) {//授权委托书
                        //购买的云贷、7贷理财产品提前终止违约金百分比查询
                        ReqMsg_AgreementVersion_QueryBerachContractRate berachContractRateReq = new ReqMsg_AgreementVersion_QueryBerachContractRate();
                        ResMsg_AgreementVersion_QueryBerachContractRate berachContractRateRes =
                                (ResMsg_AgreementVersion_QueryBerachContractRate) siteService.handleMsg(berachContractRateReq);
                        model.put("berachContractRate", berachContractRateRes.getBerachContractRate());
                        url = channel + "/regular/hf_7dai_powerAttorney_"+agreementVersionRes.getAgreementVersion();
                    }else if(Constants.AGREEMENT_VERSION_NUMBER_1_2.equals(agreementVersionRes.getAgreementVersion())) {//授权委托书
                        //购买的云贷、7贷理财产品提前终止违约金百分比查询
                        ReqMsg_AgreementVersion_QueryBerachContractRate berachContractRateReq = new ReqMsg_AgreementVersion_QueryBerachContractRate();
                        ResMsg_AgreementVersion_QueryBerachContractRate berachContractRateRes =
                                (ResMsg_AgreementVersion_QueryBerachContractRate) siteService.handleMsg(berachContractRateReq);
                        model.put("berachContractRate", berachContractRateRes.getBerachContractRate());
                        url = channel + "/regular/hf_yundai_powerAttorney_"+agreementVersionRes.getAgreementVersion();
                    }
                    return url;
                }else if (Constants.PROPERTY_SYMBOL_FREE.equals(resMsg1.getPropertySymbol())) {//自由产品对应的授权委托书
                    model.put("propertySymbol", Constants.PROPERTY_SYMBOL_FREE);
                    //出借金额
                    model.put("openBalance", resAccount.getOpenBalance());
                    ReqMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionReq = new ReqMsg_AgreementVersion_QueryAgreementVersionInfo();
                    agreementVersionReq.setAgreementType(Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_POWERATTORNEY);
                    agreementVersionReq.setAgreementEffectiveStartTime(DateUtil.format(resAccount.getOpenTime()));
                    ResMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionRes =
                            (ResMsg_AgreementVersion_QueryAgreementVersionInfo) siteService.handleMsg(agreementVersionReq);
                    if(Constants.AGREEMENT_VERSION_NUMBER_1_1.equals(agreementVersionRes.getAgreementVersion())) {//授权委托书
                        //购买的云贷、7贷理财产品提前终止违约金百分比查询
                        ReqMsg_AgreementVersion_QueryBerachContractRate berachContractRateReq = new ReqMsg_AgreementVersion_QueryBerachContractRate();
                        ResMsg_AgreementVersion_QueryBerachContractRate berachContractRateRes =
                                (ResMsg_AgreementVersion_QueryBerachContractRate) siteService.handleMsg(berachContractRateReq);
                        model.put("berachContractRate", berachContractRateRes.getBerachContractRate());
                        url = channel + "/regular/hf_yundai_powerAttorney_"+agreementVersionRes.getAgreementVersion();
                    }else if(Constants.AGREEMENT_VERSION_NUMBER_1_2.equals(agreementVersionRes.getAgreementVersion())) {//授权委托书
                        //购买的云贷、7贷理财产品提前终止违约金百分比查询
                        ReqMsg_AgreementVersion_QueryBerachContractRate berachContractRateReq = new ReqMsg_AgreementVersion_QueryBerachContractRate();
                        ResMsg_AgreementVersion_QueryBerachContractRate berachContractRateRes =
                                (ResMsg_AgreementVersion_QueryBerachContractRate) siteService.handleMsg(berachContractRateReq);
                        model.put("berachContractRate", berachContractRateRes.getBerachContractRate());
                        url = channel + "/regular/hf_yundai_powerAttorney_"+agreementVersionRes.getAgreementVersion();
                    }
                    return url;
                }else {
                    if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                        model.put("agreementName", "加入记录");
                        url = channel + "/regular/agreement_pc_errorPage";
                    } else {
                        String link = GlobEnv.getWebURL("/micro2.0/index");
                        WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                        url = channel + "/agreement/product_not_self_error";
                    }
                    return url;
    			}

        		//现金循环贷---新协议
				url = channel +  "/regular/financial_have_buy_new";
                // 云贷老产品3月10号重新匹配后 出借服务协议
                if(StringUtil.isNotBlank(resAccount.getNote())) {
                    //出借金额
                    model.put("openBalance", resAccount.getOpenBalance());
                    model.put("agreementNo", resAccount.getNote());
                    url = channel + "/regular/rematch_financial_have_buy_new";
                }
            }

        }

        return url;
    }
    
    
    /**
     * 定期理财购买协议-APP
     * @param channel
     * @param req
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/regular/buyAgreementApp")
    public String regularBuyAgreementInitApp(@PathVariable String channel, ReqMsg_User_InfoQuery req,
            HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> model, String investId, String userId) {

			req.setUserId(Integer.parseInt(userId));
			ResMsg_User_InfoQuery resMsg = (ResMsg_User_InfoQuery) regularService.handleMsg(req);
			model.put("mobile", resMsg.getMobile());
			model.put("IdCard", resMsg.getIdCard());
			//model.put("term", term);
			model.put("userName", resMsg.getUserName());
			model.put("financialNo", investId);
			//根据subAccountId查询
			ReqMsg_Account_SubAccountById reqAccount = new ReqMsg_Account_SubAccountById();
			reqAccount.setId(Integer.parseInt(investId));
			ResMsg_Account_SubAccountById resAccount = (ResMsg_Account_SubAccountById) regularService.handleMsg(reqAccount);
			
			//查询产品信息
			ReqMsg_Product_InfoQuery reqMsg = new ReqMsg_Product_InfoQuery();
			reqMsg.setId(resAccount.getProductId());
			ResMsg_Product_InfoQuery resMsg1 = (ResMsg_Product_InfoQuery) regularService
			.handleMsg(reqMsg);
			
			
			if (Constants.PROPERTY_SYMBOL_YUN_DAI.equals(resMsg1.getPropertySymbol())) {
				ReqMsg_Product_QueryYunDaiChangeNameDate queryYunDaiChangeNameDate = new ReqMsg_Product_QueryYunDaiChangeNameDate();
				ResMsg_Product_QueryYunDaiChangeNameDate queryYunDaiChangeNameDateRes = (ResMsg_Product_QueryYunDaiChangeNameDate) regularService.handleMsg(queryYunDaiChangeNameDate);
				if (resAccount.getOpenTime().compareTo(queryYunDaiChangeNameDateRes.getChangeNameDate()) <= 0 ) {
					model.put("secondPartyName", "达飞普惠财富投资管理（北京）有限公司");
                    model.put("sign_img", "PU_HUI");
				}else {
					model.put("secondPartyName", "达飞云贷科技（北京）有限公司");
                    model.put("sign_img", "YUN_DAI");
				}
				
				model.put("propertySymbol", Constants.PROPERTY_SYMBOL_YUN_DAI);
				model.put("secondPartyAddress", "北京市朝阳区四惠地铁站平台二层京通大厦B1区5层");
                // 7贷老产品3月10号重新匹配后 出借服务协议对应的协议编号
                model.put("agreementNo", resAccount.getNote() == null ? "" : resAccount.getNote());
				
			}else if (Constants.PROPERTY_SYMBOL_7_DAI.equals(resMsg1.getPropertySymbol())) {
				model.put("propertySymbol", Constants.PROPERTY_SYMBOL_7_DAI);
				model.put("secondPartyName", "深圳市前海龙汇通互联网金融服务有限公司");
				model.put("secondPartyAddress", "深圳市福田区华富路1018号中航中心25楼");
                // 7贷老产品3月10号重新匹配后 出借服务协议对应的协议编号
                model.put("agreementNo", resAccount.getNote() == null ? "" : resAccount.getNote());
			}

        //投资期限转换
			int term = resMsg1.getTrem();
			int term4Day;
			if(term < 0){
				term4Day = Math.abs(term);
			}else if(term == 12){
				term4Day = 365;
			}else{
				term4Day = term*30;
			}
			model.put("dayNum", term4Day);
			model.put("rate", resMsg1.getRate());
			model.put("name", resMsg1.getProductName());
			model.put("amount", resAccount.getBalance());
			
			//interestBeginDate 起息日
			if (resAccount.getInterestBeginDate() != null) {
			model.put("times",
			DateUtil.formatDateTime(resAccount.getInterestBeginDate(), "yyyy年MM月dd日"));
			} else {
			model.put("times", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
			}
			
			 //到期日
	        if (resAccount.getLastFinishInterestDate() != null) {
	            model.put("endTime",
	                DateUtil.formatDateTime(resAccount.getLastFinishInterestDate(), "yyyy年MM月dd日"));
	        } else {
	            model.put("endTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
	        }
			
	        //签订时间
			if (resAccount.getOpenTime() != null) {
			model.put("openTime",
			DateUtil.formatDateTime(resAccount.getOpenTime(), "yyyy年MM月dd日"));
			} else {
			model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
			}
			 //产品---目标债权类别
			String propertyType= request.getParameter("propertyType");
			String url = "";
			if(Constants.PRODUCT_PROPERTY_TYPE_CASH_LOOP.equals(propertyType)){
        		//现金循环贷---新协议
                url = channel +  "/agreement/financial_have_buy_app_new";
                // 云贷老产品3月10号重新匹配后 出借服务协议
                if(StringUtil.isNotBlank(resAccount.getNote())) {
                    //出借金额
                    model.put("openBalance", resAccount.getOpenBalance());
                    model.put("agreementNo", resAccount.getNote());
                    url = channel + "/agreement/rematch_financial_have_buy_app_new";
                }
        	}else if(Constants.PRODUCT_PROPERTY_TYPE_CONSUME.equals(propertyType)){
        		url = channel + "/agreement/financial_have_buy_app";
        	}
            //存管产品对应的借款咨询服务协议
            if(StringUtil.isNotBlank(resMsg1.getPropertySymbol()) && Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(resMsg1.getPropertySymbol())) {
                model.put("propertySymbol", Constants.PROPERTY_SYMBOL_YUN_DAI_SELF);
                //出借金额
                model.put("openBalance", resAccount.getOpenBalance());
                ReqMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionReq = new ReqMsg_AgreementVersion_QueryAgreementVersionInfo();
                agreementVersionReq.setAgreementType(Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_POWERATTORNEY);
                agreementVersionReq.setAgreementEffectiveStartTime(DateUtil.format(resAccount.getOpenTime()));
                ResMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionRes =
                        (ResMsg_AgreementVersion_QueryAgreementVersionInfo) siteService.handleMsg(agreementVersionReq);
                if(Constants.AGREEMENT_VERSION_NUMBER_NO_VERSION.equals(agreementVersionRes.getAgreementVersion())) {
                    url = channel + "/agreement/lend_service_agreement_app";
                }else if(Constants.AGREEMENT_VERSION_NUMBER_1_1.equals(agreementVersionRes.getAgreementVersion())) {//授权委托书
                    //购买的云贷、7贷理财产品提前终止违约金百分比查询
                    ReqMsg_AgreementVersion_QueryBerachContractRate berachContractRateReq = new ReqMsg_AgreementVersion_QueryBerachContractRate();
                    ResMsg_AgreementVersion_QueryBerachContractRate berachContractRateRes =
                            (ResMsg_AgreementVersion_QueryBerachContractRate) siteService.handleMsg(berachContractRateReq);
                    model.put("berachContractRate", berachContractRateRes.getBerachContractRate());
                    url = channel + "/agreement/hf_yundai_powerAttorney_app_"+agreementVersionRes.getAgreementVersion();
                }else if(Constants.AGREEMENT_VERSION_NUMBER_1_2.equals(agreementVersionRes.getAgreementVersion())) {//授权委托书
                    //购买的云贷、7贷理财产品提前终止违约金百分比查询
                    ReqMsg_AgreementVersion_QueryBerachContractRate berachContractRateReq = new ReqMsg_AgreementVersion_QueryBerachContractRate();
                    ResMsg_AgreementVersion_QueryBerachContractRate berachContractRateRes =
                            (ResMsg_AgreementVersion_QueryBerachContractRate) siteService.handleMsg(berachContractRateReq);
                    model.put("berachContractRate", berachContractRateRes.getBerachContractRate());
                    url = channel + "/agreement/hf_yundai_powerAttorney_app_"+agreementVersionRes.getAgreementVersion();
                }
            }
            //赞时贷产品对应的借款咨询服务协议
            if(StringUtil.isNotBlank(resMsg1.getPropertySymbol()) && Constants.PROPERTY_SYMBOL_ZSD.equals(resMsg1.getPropertySymbol())) {
                model.put("propertySymbol", Constants.PROPERTY_SYMBOL_ZSD);
                //出借金额
                model.put("openBalance", resAccount.getOpenBalance());
                url = channel + "/agreement/lend_service_agreement_app";
            }
            //7贷存管产品对应的借款咨询服务协议
            if(StringUtil.isNotBlank(resMsg1.getPropertySymbol()) && Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(resMsg1.getPropertySymbol())) {
                model.put("propertySymbol", Constants.PROPERTY_SYMBOL_7_DAI_SELF);
                //出借金额
                model.put("openBalance", resAccount.getOpenBalance());
                ReqMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionReq = new ReqMsg_AgreementVersion_QueryAgreementVersionInfo();
                agreementVersionReq.setAgreementType(Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_POWERATTORNEY);
                agreementVersionReq.setAgreementEffectiveStartTime(DateUtil.format(resAccount.getOpenTime()));
                ResMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionRes =
                        (ResMsg_AgreementVersion_QueryAgreementVersionInfo) siteService.handleMsg(agreementVersionReq);
                if(Constants.AGREEMENT_VERSION_NUMBER_NO_VERSION.equals(agreementVersionRes.getAgreementVersion())) {
                    url = channel + "/agreement/seven_lend_service_agreement_app";
                }else if(Constants.AGREEMENT_VERSION_NUMBER_1_1.equals(agreementVersionRes.getAgreementVersion())) {//授权委托书
                    //购买的云贷、7贷理财产品提前终止违约金百分比查询
                    ReqMsg_AgreementVersion_QueryBerachContractRate berachContractRateReq = new ReqMsg_AgreementVersion_QueryBerachContractRate();
                    ResMsg_AgreementVersion_QueryBerachContractRate berachContractRateRes =
                            (ResMsg_AgreementVersion_QueryBerachContractRate) siteService.handleMsg(berachContractRateReq);
                    model.put("berachContractRate", berachContractRateRes.getBerachContractRate());
                    url = channel + "/agreement/hf_7dai_powerAttorney_app_"+agreementVersionRes.getAgreementVersion();
                }else if(Constants.AGREEMENT_VERSION_NUMBER_1_2.equals(agreementVersionRes.getAgreementVersion())) {//授权委托书
                    //购买的云贷、7贷理财产品提前终止违约金百分比查询
                    ReqMsg_AgreementVersion_QueryBerachContractRate berachContractRateReq = new ReqMsg_AgreementVersion_QueryBerachContractRate();
                    ResMsg_AgreementVersion_QueryBerachContractRate berachContractRateRes =
                            (ResMsg_AgreementVersion_QueryBerachContractRate) siteService.handleMsg(berachContractRateReq);
                    model.put("berachContractRate", berachContractRateRes.getBerachContractRate());
                    url = channel + "/agreement/hf_yundai_powerAttorney_app_"+agreementVersionRes.getAgreementVersion();
                }
            }
            //自由产品对应的授权委托书
            if(StringUtil.isNotBlank(resMsg1.getPropertySymbol()) && Constants.PROPERTY_SYMBOL_FREE.equals(resMsg1.getPropertySymbol())) {
                model.put("propertySymbol", Constants.PROPERTY_SYMBOL_FREE);
                //出借金额
                model.put("openBalance", resAccount.getOpenBalance());
                ReqMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionReq = new ReqMsg_AgreementVersion_QueryAgreementVersionInfo();
                agreementVersionReq.setAgreementType(Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_POWERATTORNEY);
                agreementVersionReq.setAgreementEffectiveStartTime(DateUtil.format(resAccount.getOpenTime()));
                ResMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionRes =
                        (ResMsg_AgreementVersion_QueryAgreementVersionInfo) siteService.handleMsg(agreementVersionReq);
                if(Constants.AGREEMENT_VERSION_NUMBER_1_1.equals(agreementVersionRes.getAgreementVersion())) {//授权委托书
                    //购买的云贷、7贷理财产品提前终止违约金百分比查询
                    ReqMsg_AgreementVersion_QueryBerachContractRate berachContractRateReq = new ReqMsg_AgreementVersion_QueryBerachContractRate();
                    ResMsg_AgreementVersion_QueryBerachContractRate berachContractRateRes =
                            (ResMsg_AgreementVersion_QueryBerachContractRate) siteService.handleMsg(berachContractRateReq);
                    model.put("berachContractRate", berachContractRateRes.getBerachContractRate());
                    url = channel + "/agreement/hf_yundai_powerAttorney_app_"+agreementVersionRes.getAgreementVersion();
                }else if(Constants.AGREEMENT_VERSION_NUMBER_1_2.equals(agreementVersionRes.getAgreementVersion())) {//授权委托书
                    //购买的云贷、7贷理财产品提前终止违约金百分比查询
                    ReqMsg_AgreementVersion_QueryBerachContractRate berachContractRateReq = new ReqMsg_AgreementVersion_QueryBerachContractRate();
                    ResMsg_AgreementVersion_QueryBerachContractRate berachContractRateRes =
                            (ResMsg_AgreementVersion_QueryBerachContractRate) siteService.handleMsg(berachContractRateReq);
                    model.put("berachContractRate", berachContractRateRes.getBerachContractRate());
                    url = channel + "/agreement/hf_yundai_powerAttorney_app_"+agreementVersionRes.getAgreementVersion();
                }
            }
			
        return url;
    }
    
    
    /**
     * pc端-持仓凭证
     * @param channel
     * @param req
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/regular/positionVoucher")
    public String positionVoucher(@PathVariable String channel, ReqMsg_User_InfoQuery req,
                                          HttpServletRequest request, HttpServletResponse response,
                                          Map<String, Object> model, String investId) {
    	
        try {
        	CookieManager cookieManager = new CookieManager(request);
            String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
            req.setUserId(Integer.parseInt(userId));
            
            boolean flag = checkUserIdSubAccountId(Integer.parseInt(userId),Integer.parseInt(investId));
            if(!flag){
            	String url = channel + "/regular/position_voucher_error";
            	return url;
            }else{
            	ResMsg_User_InfoQuery resMsg = (ResMsg_User_InfoQuery) regularService.handleMsg(req);
                model.put("mobile", resMsg.getMobile());
                model.put("IdCard", resMsg.getIdCard());
                //model.put("term", term);
                model.put("userName", resMsg.getUserName());
                //根据subAccountId查询
                ReqMsg_Account_SubAccountById reqAccount = new ReqMsg_Account_SubAccountById();
                reqAccount.setId(Integer.parseInt(investId));
                ResMsg_Account_SubAccountById resAccount = (ResMsg_Account_SubAccountById) regularService.handleMsg(reqAccount);

                //查询产品信息
                ReqMsg_Product_InfoQuery reqMsg = new ReqMsg_Product_InfoQuery();
                reqMsg.setId(resAccount.getProductId());
                ResMsg_Product_InfoQuery resMsg1 = (ResMsg_Product_InfoQuery) regularService
                        .handleMsg(reqMsg);
                
                //到期日
                if (resAccount.getLastFinishInterestDate() != null) {
                    model.put("endTime",
                        DateUtil.formatDateTime(resAccount.getLastFinishInterestDate(), "yyyy年MM月dd日"));
                } else {
                    model.put("endTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
                }
                
                //当前时间，查询时间
                model.put("nowTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日 HH:mm:ss"));
             
                //投资期限转换
    			int term = resMsg1.getTrem();
    			int term4Day;
    			if(term < 0){
    				term4Day = Math.abs(term);
    			}else if(term == 12){
    				term4Day = 365;
    			}else{
    				term4Day = term*30;
    			}
                
                model.put("dayNum", term4Day);//投资天数
                model.put("rate", resMsg1.getRate());//年化收益
                model.put("name", resMsg1.getProductName());
                model.put("amount", resAccount.getBalance());
                model.put("code", resMsg1.getCode());
                model.put("interest", MoneyUtil.format(resAccount.getBalance()*resMsg1.getRate()*term4Day/36500));
                
                //interestBeginDate 起息日
                if (resAccount.getInterestBeginDate() != null) {
                    model.put("times",
                        DateUtil.formatDateTime(resAccount.getInterestBeginDate(), "yyyy年MM月dd日"));
                } else {
                    model.put("times", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
                }
                
                //签订时间
                if (resAccount.getOpenTime() != null) {
                    model.put("openTime",
                        DateUtil.formatDateTime(resAccount.getOpenTime(), "yyyy年MM月dd日"));
                } else {
                    model.put("openTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
                }
                
                //产品---目标债权类别
    			String propertyType= request.getParameter("propertyType");
                //子账户-产品类型
                String productType = request.getParameter("productType");

    			String url = "";
                if(Constants.PRODUCT_PROPERTY_TYPE_CASH_LOOP.equals(propertyType)){
                    //新持仓凭证
                    url = channel + "/agreement/position_voucher_new";
                }else{
                    url = channel + "/agreement/position_voucher";
                }

                if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                    if(Constants.PRODUCT_TYPE_AUTH_YUN.equals(productType) ||
                            Constants.PRODUCT_TYPE_AUTH_ZSD.equals(productType) ||
                            Constants.PRODUCT_TYPE_AUTH_FREE.equals(productType)) {
                        //存管持仓凭证
                        model.put("openBalance", resAccount.getOpenBalance());//持仓金额
                        model.put("interest", MoneyUtil.format(resAccount.getOpenBalance()*resMsg1.getRate()*term4Day/36500));//预期收益

                        // 查询加息收益
                        if(StringUtil.isBlank(resAccount.getNote())) {// note为空说明不是存量迁移的产品
                            ReqMsg_Account_InterestAmountQuery interestAmountReq = new ReqMsg_Account_InterestAmountQuery();
                            interestAmountReq.setId(Integer.parseInt(investId));
                            ResMsg_Account_InterestAmountQuery interestAmountRes = (ResMsg_Account_InterestAmountQuery) regularService.handleMsg(interestAmountReq);
                            model.put("interestAmount", interestAmountRes.getInterestAmount());
                        }

                        url = channel + "/regular/position_voucher_custody";

                        // 云贷老产品3月10号重新匹配后 持仓凭证
                        if(StringUtil.isNotBlank(resAccount.getNote())) {
                            url = channel + "/regular/rematch_position_voucher_new";
                        }
                    }else if(Constants.PRODUCT_TYPE_AUTH_7.equals(productType)) {
                        //存管持仓凭证
                        model.put("openBalance", resAccount.getOpenBalance());//持仓金额
                        model.put("interest", MoneyUtil.format(resAccount.getOpenBalance()*resMsg1.getRate()*term4Day/36500));//预期收益

                        // 查询加息收益
                        if(StringUtil.isBlank(resAccount.getNote())) {// note为空说明不是存量迁移的产品
                            ReqMsg_Account_InterestAmountQuery interestAmountReq = new ReqMsg_Account_InterestAmountQuery();
                            interestAmountReq.setId(Integer.parseInt(investId));
                            ResMsg_Account_InterestAmountQuery interestAmountRes = (ResMsg_Account_InterestAmountQuery) regularService.handleMsg(interestAmountReq);
                            model.put("interestAmount", interestAmountRes.getInterestAmount());
                        }

                        url = channel + "/regular/position_voucher_custody";
                        // 7贷老产品3月10号重新匹配后 持仓凭证
                        if(StringUtil.isNotBlank(resAccount.getNote())) {
                            url = channel + "/regular/rematch_position_voucher_new";
                        }

                    }else {
                        if(Constants.PRODUCT_PROPERTY_TYPE_CASH_LOOP.equals(propertyType)){
                            //新持仓凭证
                            url = channel + "/regular/position_voucher_new";
                        }else{
                            url = channel + "/regular/position_voucher";
                        }
                    }
                }
                model.put("investId", investId);
                model.put("propertyType", propertyType);
                model.put("productType", productType);
                return url;
            }
		} catch (Exception e) {
			e.printStackTrace();
			String url = channel + "/regular/position_voucher_error";
        	return url;
		}
    }
    
    /**
     * 校验SubAccountId是否属于该用户
     * @param parseInt
     * @param parseInt2
     */
    private boolean checkUserIdSubAccountId(int userId, int subAccountId) {
		
    	ReqMsg_Account_CheckUserIdSubAccountId reqAccount = new ReqMsg_Account_CheckUserIdSubAccountId();
        reqAccount.setUserId(userId);
        reqAccount.setSubAccountId(subAccountId);
        ResMsg_Account_CheckUserIdSubAccountId resAccount = (ResMsg_Account_CheckUserIdSubAccountId) regularService.handleMsg(reqAccount);
        if(resAccount.getSubAccountId() == subAccountId){
        	return true;
        }else{
        	return false;
        }
    }

	/**
     * 持仓凭证下载
     * @param channel
     * @param request
     * @param response
     * @return
     */
	@RequestMapping("{channel}/regular/positionVoucherPDFDownload")
	 public String positionVoucherPDFDownload(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response, String investId, boolean isOnLine) {
    	CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        try {
        	log.info("===============持仓凭证下载开始=============");
        	 boolean flag = checkUserIdSubAccountId(Integer.parseInt(userId),Integer.parseInt(investId));
             if(!flag){
             	String url = channel + "/regular/position_voucher_error";
             	return url;
             }else{
            	 String relativeVoucherPdfPath = Integer.parseInt(userId) + "/"
     	                + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS")
     	                + "-voucher" + ".pdf";
     	    	String genUrl = GlobEnv.get("gen.server.web");
     	    	//genUrl = genUrl.replace("https://", "http://");
     	        String voucherPdfPath = GlobEnvUtil.get("voucher.pdfDestPath") + relativeVoucherPdfPath;
                //子账户-产品类型
                String productType = request.getParameter("productType");
                String voucherHtml = "";
                if((StringUtil.isNotBlank(productType) && Constants.PRODUCT_TYPE_AUTH_YUN.equals(productType)) ||
                        (StringUtil.isNotBlank(productType) && Constants.PRODUCT_TYPE_AUTH_ZSD.equals(productType)) ||
                        (StringUtil.isNotBlank(productType) && Constants.PRODUCT_TYPE_AUTH_7.equals(productType)) ||
                        (StringUtil.isNotBlank(productType) && Constants.PRODUCT_TYPE_AUTH_FREE.equals(productType))) {//存管产品对应的持仓凭证
                    voucherHtml = genUrl+"/"+channel+GlobEnvUtil.get("voucher.pdfSrcHtml")
                            + "?investId=" + investId + "&u=" + userId+"&propertyType="+request.getParameter("propertyType")
                            + "&productType="+request.getParameter("productType");
                } else {
                    voucherHtml = genUrl+"/"+channel+GlobEnvUtil.get("voucher.pdfSrcHtml")
                            + "?investId=" + investId + "&u=" + userId+"&propertyType="+request.getParameter("propertyType");
                }
     	        ITextPdfUtil.createHtm2Pdf(voucherHtml, voucherPdfPath, "持仓凭证", "持仓凭证");
     	        
     	        
             	File f = new File(voucherPdfPath);
                 if (!f.exists()) {
                     response.sendError(404, "File not found!");
                     return null;
                 }
                 BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
                 byte[] buf = new byte[1024];
                 int len = 0;

                 response.reset(); // 非常重要
                 if (isOnLine) { // 在线打开方式
                     URL u = new URL("file:///" + voucherPdfPath);
                     response.setContentType(u.openConnection().getContentType());
                     response.setHeader("Content-Disposition", "inline; filename=" + f.getName());
                     // 文件名应该编码成UTF-8
                 } else { // 纯下载方式
                     response.setContentType("application/x-msdownload");
                     response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
                 }
                 OutputStream out = response.getOutputStream();
                 while ((len = br.read(buf)) > 0)
                     out.write(buf, 0, len);
                 br.close();
                 out.close();
                 return null;
             }
		} catch (Exception e) {
			e.printStackTrace();
			String url = channel + "/regular/position_voucher_error";
         	return url;
		}
	}
    
    /**
     * pc端-持仓凭证-pdf,html页面
     * @param channel
     * @param req
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/regular/positionVoucherPdfHtml")
    public String positionVoucherPDFHtml(@PathVariable String channel, ReqMsg_User_InfoQuery req,
                                          HttpServletRequest request, HttpServletResponse response,
                                          Map<String, Object> model, String investId, String u) {
    	
        req.setUserId(Integer.valueOf(u));
        ResMsg_User_InfoQuery resMsg = (ResMsg_User_InfoQuery) regularService.handleMsg(req);
        model.put("mobile", resMsg.getMobile());
        model.put("IdCard", resMsg.getIdCard());
        //model.put("term", term);
        model.put("userName", resMsg.getUserName());
        //根据subAccountId查询
        ReqMsg_Account_SubAccountById reqAccount = new ReqMsg_Account_SubAccountById();
        reqAccount.setId(Integer.parseInt(investId));
        ResMsg_Account_SubAccountById resAccount = (ResMsg_Account_SubAccountById) regularService.handleMsg(reqAccount);

        //查询产品信息
        ReqMsg_Product_InfoQuery reqMsg = new ReqMsg_Product_InfoQuery();
        reqMsg.setId(resAccount.getProductId());
        ResMsg_Product_InfoQuery resMsg1 = (ResMsg_Product_InfoQuery) regularService
                .handleMsg(reqMsg);
        
        //到期日
        
        if (resAccount.getLastFinishInterestDate() != null) {
            model.put("endTime",
                DateUtil.formatDateTime(resAccount.getLastFinishInterestDate(), "yyyy年MM月dd日"));
        } else {
            model.put("endTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
        }
        
        //当前时间，查询时间
        model.put("nowTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日 HH:mm:ss"));
        //投资期限转换
		int term = resMsg1.getTrem();
		int term4Day;
		if(term < 0){
			term4Day = Math.abs(term);
		}else if(term == 12){
			term4Day = 365;
		}else{
			term4Day = term*30;
		}
        model.put("dayNum", term4Day);//投资天数
        model.put("rate", resMsg1.getRate());//年化收益
        model.put("name", resMsg1.getProductName());
        model.put("amount", resAccount.getBalance());
        model.put("code", resMsg1.getCode());
        model.put("interest", MoneyUtil.format(resAccount.getBalance()*resMsg1.getRate()*term4Day/36500));
        //interestBeginDate 起息日
        if (resAccount.getInterestBeginDate() != null) {
            model.put("times",
                DateUtil.formatDateTime(resAccount.getInterestBeginDate(), "yyyy年MM月dd日"));
        } else {
            model.put("times", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
        }
        
        //签订时间
        if (resAccount.getOpenTime() != null) {
            model.put("openTime",
                DateUtil.formatDateTime(resAccount.getOpenTime(), "yyyy年MM月dd日"));
        } else {
            model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
        }
        
        //产品---目标债权类别
		String propertyType= request.getParameter("propertyType");
        //子账户-产品类型
        String productType = request.getParameter("productType");

		String url = "";
        if(Constants.PRODUCT_PROPERTY_TYPE_CASH_LOOP.equals(propertyType)){
            //新持仓凭证
            url = channel + "/agreement/position_voucher_pdf_new";
        }else{
            url = channel + "/agreement/position_voucher_pdf";
        }

        if("gen2.0".equals(channel) || "gen178".equals(channel)) {
            if(Constants.PRODUCT_TYPE_AUTH_YUN.equals(productType) ||
                    Constants.PRODUCT_TYPE_AUTH_ZSD.equals(productType) ||
                    Constants.PRODUCT_TYPE_AUTH_FREE.equals(productType)) {
                url = channel + "/regular/position_voucher_pdf_custody";

            }else if(Constants.PRODUCT_TYPE_AUTH_7.equals(productType)) {
                url = channel + "/regular/position_voucher_pdf_custody";
            }else {
                if(Constants.PRODUCT_PROPERTY_TYPE_CASH_LOOP.equals(propertyType)){
                    url = channel + "/regular/position_voucher_pdf_new";

                    // 云贷老产品3月10号重新匹配后-持仓凭证pdf
                    if(StringUtil.isNotBlank(resAccount.getNote())) {
                        model.put("openBalance", resAccount.getOpenBalance());
                        model.put("interest", MoneyUtil.format(resAccount.getOpenBalance()*resMsg1.getRate()*term4Day/36500));
                        url = channel + "/regular/rematch_position_voucher_pdf_new";
                    }
                }else{
                    url = channel + "/regular/position_voucher_pdf";
                }
            }

            //存管持仓凭证PDF
            if((StringUtil.isNotBlank(resMsg1.getPropertySymbol()) && Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(resMsg1.getPropertySymbol())) ||
                    (StringUtil.isNotBlank(resMsg1.getPropertySymbol()) && Constants.PROPERTY_SYMBOL_ZSD.equals(resMsg1.getPropertySymbol())) ||
                    (StringUtil.isNotBlank(resMsg1.getPropertySymbol()) && Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(resMsg1.getPropertySymbol())) ||
                    (StringUtil.isNotBlank(resMsg1.getPropertySymbol()) && Constants.PROPERTY_SYMBOL_FREE.equals(resMsg1.getPropertySymbol()))) {
                model.put("openBalance", resAccount.getOpenBalance());//持仓金额
                model.put("interest", MoneyUtil.format(resAccount.getOpenBalance()*resMsg1.getRate()*term4Day/36500));//预期收益

                // 查询加息收益
                if(StringUtil.isBlank(resAccount.getNote())) { // note为空说明不是存量迁移的产品
                    ReqMsg_Account_InterestAmountQuery interestAmountReq = new ReqMsg_Account_InterestAmountQuery();
                    interestAmountReq.setId(Integer.parseInt(investId));
                    ResMsg_Account_InterestAmountQuery interestAmountRes = (ResMsg_Account_InterestAmountQuery) regularService.handleMsg(interestAmountReq);
                    model.put("interestAmount", interestAmountRes.getInterestAmount());
                }

                url = channel + "/regular/position_voucher_pdf_custody";

            }

        }
        return url;
    }

    /**
     * 定期理财详情页面(pc,178,不包含h5)
     * @param channel
     * @param flag
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/regular/{flag}")
    public String regularDetail(@PathVariable String channel, @PathVariable String flag,
                                HttpServletRequest request, HttpServletResponse response,
                                Map<String, Object> model) {
        log.info("【产品详情页开始】");
        // 判断产品是否已经下线
        ReqMsg_Product_CheckProductIsOff reqOff = new ReqMsg_Product_CheckProductIsOff();
        try {

            reqOff.setProductId(Integer.parseInt(request.getParameter("id")));
        } catch (Exception e) {
            return channel + "/errors/404_new";
        }
        ResMsg_Product_CheckProductIsOff resOff = (ResMsg_Product_CheckProductIsOff) regularService.handleMsg(reqOff);
        if(resOff.getIsOff()){
            model.put("isOff", true);
            return channel + "/errors/404_new";
        }

        CookieManager cookieManager = new CookieManager(request);
        String user = request.getParameter("user"); //当点击用户分享链接过来时会有此参数
        if (user != null && !"".equals(user)) {
            user = user.substring(0, user.length() - 36);
            String recommendId = cookieManager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_RECOMMEND_ID.getName(), true);
            if (recommendId == null || "".equals(recommendId)) { //说明并没有存在推荐人，同时下面将推荐人的id号存入cookie当中
                cookieManager.setValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_RECOMMEND_ID.getName(), user, true);
                cookieManager.save(response, CookieEnums._SITE.getName(), null, "/",
                    5*365 * 24 * 3600, true);
            }
        }
        String userType = cookieManager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_TYPE.getName(), true);
        
        if (!"PROMOT".equals(userType)) {
            userType = "NORMAL";
        }
        
        /*渠道用户月普通用户使用同一套利率，故此处注释*/
        /*if ("PROMOT".equals(userType)) {
        	switch (reqMsg.getId()) {
			case 26:
				reqMsg.setId(15);//渠道1个月
				break;
			case 27:
				reqMsg.setId(30);//渠道3个月
				break;
			case 28:
				reqMsg.setId(31);//渠道6个月
				break;
			case 29:
				reqMsg.setId(32);//渠道1年
				break;
			default:
				break;
			}
        }*/
        
        
        //检查希财用户连接过来
/*        String agent = request.getParameter("agent"); //当希财分享链接过来时会有此参数
        if (agent != null && !"".equals(agent) && Constants.USER_AGENT_CAAI.equals(agent)) {
            cookieManager.setValue(CookieEnums._CSAI.getName(),
                    CookieEnums._CSAI_AGENT_CSAI.getName(), agent, true);
            cookieManager.save(response, CookieEnums._CSAI.getName(), null, "/",
                    300, true);
		}*/
        String agent = request.getParameter("agent"); //当希财分享链接过来时会有此参数
        if (agent != null && !"".equals(agent) && Constants.USER_AGENT_CAAI.equals(agent)) {
            cookieManager.setValue(CookieEnums._CSAI.getName(),
                    CookieEnums._CSAI_AGENT_CSAI.getName(), agent, true);
            cookieManager.save(response, CookieEnums._CSAI.getName(), null, "/",
            		30 * 24 * 3600, true);
		}
        
        String agentName = cookieManager.getValue(CookieEnums._CSAI.getName(),
                CookieEnums._CSAI_AGENT_CSAI.getName(), true);
        
        /*        String proId = String.valueOf(reqMsg.getId());
        //产品码为普通用户，但用户为推广用户
        if (UserTypePrdctCode.isNormalProductCode(proId)
            && !UserTypePrdctCode.isPromotProductCode(proId)
            && Constants.USER_TYPE_PROMOT.equals(userType)) {
            reqMsg.setId(15);
        }
        //产品码为推广用户，但用户为普通用户或无类型
        else if (!UserTypePrdctCode.isNormalProductCode(proId)
                 && UserTypePrdctCode.isPromotProductCode(proId)
                 && !Constants.USER_TYPE_PROMOT.equals(userType)) {
            reqMsg.setId(11);
        } else if (reqMsg.getId() == 0) {
            if (Constants.USER_TYPE_PROMOT.equals(userType)) {
                reqMsg.setId(15);
            } else {
                reqMsg.setId(11);
            }
        }*/
        
		//新查询产品数据信息
        String resources = GlobEnv.get("news.resources");
        String manageWeb = GlobEnv.get("manage.web");
        String web = GlobEnv.get("gen.server.web");
		
		ReqMsg_Product_ProductDetailInfoQuery reqMsg_Product_ProductDetailInfoQuery = new ReqMsg_Product_ProductDetailInfoQuery();
		reqMsg_Product_ProductDetailInfoQuery.setProductId(Integer.parseInt(request.getParameter("id")));
		reqMsg_Product_ProductDetailInfoQuery.setPageNum(Integer.parseInt(StringUtil.isBlank(request.getParameter("page"))? "1" : request.getParameter("page")));
		ResMsg_Product_ProductDetailInfoQuery resMsg_Product_ProductDetailInfoQuery = (ResMsg_Product_ProductDetailInfoQuery) regularService.handleMsg(reqMsg_Product_ProductDetailInfoQuery);
		resMsg_Product_ProductDetailInfoQuery.setNote(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getNote(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setPropertySummary(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getPropertySummary(), resources, manageWeb, web));
		
		resMsg_Product_ProductDetailInfoQuery.setReturnSource(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getReturnSource(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setFundSecurity(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getFundSecurity(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setOrgnizeCheck(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getOrgnizeCheck(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setRatingGrade(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getRatingGrade(), resources, manageWeb, web));
		
		if (resMsg_Product_ProductDetailInfoQuery.getCoopProtocolPics() != null) {
	    	String[] coopProtocolImgs = resMsg_Product_ProductDetailInfoQuery.getCoopProtocolPics().split(";");
	    	model.put("coopProtocolImgNum", (int) Math.ceil((double) coopProtocolImgs.length / 3));
	    	model.put("coopProtocolImgs", coopProtocolImgs);
		}
		if (resMsg_Product_ProductDetailInfoQuery.getLoanProtocolPics() != null) {
	    	String[] loanProtocolImgs = resMsg_Product_ProductDetailInfoQuery.getLoanProtocolPics().split(";");
	    	model.put("loanProtocolImgNum", (int) Math.ceil((double) loanProtocolImgs.length / 3));
	    	model.put("loanProtocolImgs", loanProtocolImgs);
		}
		if (resMsg_Product_ProductDetailInfoQuery.getOrgnizeCheckPics() != null) {
	    	String[] orgnizeCheckImgs = resMsg_Product_ProductDetailInfoQuery.getOrgnizeCheckPics().split(";");
	    	model.put("orgnizeCheckImgNum", (int) Math.ceil((double) orgnizeCheckImgs.length / 3));
	    	model.put("orgnizeCheckImgs", orgnizeCheckImgs);
		}

		if (resMsg_Product_ProductDetailInfoQuery.getRatingGradePics() != null) {
            String[] ratingGradeImgs = resMsg_Product_ProductDetailInfoQuery.getRatingGradePics().split(";");
            model.put("ratingGradeImgNum", (int) Math.ceil((double) ratingGradeImgs.length / 3));
            model.put("ratingGradeImgs", ratingGradeImgs);
        }


    	if (Constants.PRODUCT_STATUS_PUBLISH_YES == resMsg_Product_ProductDetailInfoQuery.getStatus()) {
			//未开放
    		
			//间隔秒数
			int intervalTime = (int) ((resMsg_Product_ProductDetailInfoQuery.getStartTime().getTime() - new Date().getTime()) / 1000);
			model.put("intervalTime", intervalTime);
    		
		}else if (Constants.PRODUCT_STATUS_OPENING == resMsg_Product_ProductDetailInfoQuery.getStatus()) {
			//进行中
			if (resMsg_Product_ProductDetailInfoQuery.getEndTime() != null) {
				int intervalTime = (int) ((resMsg_Product_ProductDetailInfoQuery.getEndTime().getTime() - new Date().getTime() ) / 1000);
				model.put("intervalTime", intervalTime);
			}
			
			
		}else {
			//已结束
			if (resMsg_Product_ProductDetailInfoQuery.getFinishTime() != null ) {
				String finishTime =DateUtil.formatDateTime(resMsg_Product_ProductDetailInfoQuery.getFinishTime(), "yyyy年MM月dd日");
				model.put("finishTime", finishTime);
			}
		}
		
		
		model.put("pageNum", reqMsg_Product_ProductDetailInfoQuery.getPageNum());
		model.put("totalCount",  (int) Math.ceil((double) resMsg_Product_ProductDetailInfoQuery.getTotalRows() / reqMsg_Product_ProductDetailInfoQuery.getNumPerPage()));
		model.put("productDetail", resMsg_Product_ProductDetailInfoQuery);
		model.put("investRecord", resMsg_Product_ProductDetailInfoQuery.getInvestRecordList());
		model.put("toRemind", request.getParameter("to_remind"));
		model.put("tab_flag", request.getParameter("tab_flag"));
        model.put("totalRows",resMsg_Product_ProductDetailInfoQuery.getTotalRows());

        //产品倒计时刷新标记
		if("".equals(request.getParameter("product_status_flag")) || request.getParameter("product_status_flag") == null){
			model.put("product_status_flag", 1);
		}else {
			if (request.getParameter("product_status") != null && Integer.parseInt(request.getParameter("product_status")) != resMsg_Product_ProductDetailInfoQuery.getStatus()) {
				model.put("product_status_flag", 1);
			}else {
				model.put("product_status_flag", Integer.parseInt(request.getParameter("product_status_flag"))+1);
			}
			
		}
        
        
		//查询19付支持快捷支付的银行
		ReqMsg_Bank_Pay19BankList reqMsgBank = new ReqMsg_Bank_Pay19BankList();
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
        if (!"".equals(userId)) {
        	reqMsgBank.setUserId(Integer.parseInt(userId));
		}
		ResMsg_Bank_Pay19BankList resMsgBank = (ResMsg_Bank_Pay19BankList) regularService.handleMsg(reqMsgBank);
		model.put("bankList", resMsgBank.getBankList());

//        ReqMsg_Product_InfoQuery reqMsg = new ReqMsg_Product_InfoQuery();
//        reqMsg.setId(Integer.parseInt(request.getParameter("id")));
//        ResMsg_Product_InfoQuery resMsg = (ResMsg_Product_InfoQuery) regularService.handleMsg(reqMsg);
        //投资期限转换

        String endTime= DateUtil.formatDateTime(DateUtil.addDays(new Date(), resMsg_Product_ProductDetailInfoQuery.getTerm()), "yyyy/MM/dd");
        model.put("endTime", endTime);
        model.put("id", resMsg_Product_ProductDetailInfoQuery.getId());
        model.put("trem", resMsg_Product_ProductDetailInfoQuery.getTermMonth());
        model.put("dayNum", resMsg_Product_ProductDetailInfoQuery.getTerm());
        model.put("rate", resMsg_Product_ProductDetailInfoQuery.getBaseRate());
        model.put("investNum", resMsg_Product_ProductDetailInfoQuery.getInvestNum());
        model.put("currTotalAmount", resMsg_Product_ProductDetailInfoQuery.getCurrTotalAmount());
        model.put("userType", userType);
        model.put("minInvestAmount", resMsg_Product_ProductDetailInfoQuery.getMinInvestAmount());
        Map<String, String> sign = weChatUtil.createAuth(request);
        model.put("weichat", sign);
        //是否显示产品可用总额度和用户使用额度
		int productId = Integer.parseInt(request.getParameter("id"));
		if(productId == Constants.PRODUCT_ID_NEWER_1_MONTH || productId == Constants.PRODUCT_ID_ADD_RATE_1_YEAR) {
			Double a= MoneyUtil.subtract(resMsg_Product_ProductDetailInfoQuery.getMaxTotalAmount() == null ? 0 : resMsg_Product_ProductDetailInfoQuery.getMaxTotalAmount(),resMsg_Product_ProductDetailInfoQuery.getCurrTotalAmount() == null ? 0 : resMsg_Product_ProductDetailInfoQuery.getCurrTotalAmount()).doubleValue();
			model.put("proLimitAmout", a);
			if(productId == Constants.PRODUCT_ID_NEWER_1_MONTH) {
				if(StringUtil.isNotEmpty(userId)) {
					ReqMsg_UserProductLimit_UserProductLimitQuery req = new ReqMsg_UserProductLimit_UserProductLimitQuery();
					req.setUserId(Integer.valueOf(userId));
					req.setProductId(Integer.parseInt(request.getParameter("id")));
					ResMsg_UserProductLimit_UserProductLimitQuery res = (ResMsg_UserProductLimit_UserProductLimitQuery)regularService.handleMsg(req);
					model.put("userProLimitAmout", res.getLeftAmount());
				}
			}
		}
       
		//查询用户余额信息
		if(StringUtil.isNotEmpty(userId)) {
			ReqMsg_User_UserBalanceQuery reqMsg_User_UserBalanceQuery = new ReqMsg_User_UserBalanceQuery();
			reqMsg_User_UserBalanceQuery.setUserId(userId);
			ResMsg_User_UserBalanceQuery resMsg_User_UserBalanceQuery  = (ResMsg_User_UserBalanceQuery)regularService.handleMsg(reqMsg_User_UserBalanceQuery);
			model.put("availableBalance", resMsg_User_UserBalanceQuery.getAvailableBalance());
            //存管账户余额
            model.put("depBalance", resMsg_User_UserBalanceQuery.getDepBalance());

            // 存管引导信息
            ReqMsg_DepGuide_FindDepGuideInfo depGuideReq = new ReqMsg_DepGuide_FindDepGuideInfo();
            depGuideReq.setUserId(Integer.parseInt(userId));
            depGuideReq.setContainRisk(true);
            ResMsg_DepGuide_FindDepGuideInfo depGuideRes =  (ResMsg_DepGuide_FindDepGuideInfo) siteService.handleMsg(depGuideReq);
            request.setAttribute("hfDepGuideInfo", depGuideRes);

	        ReqMsg_User_IsBindCard req = new ReqMsg_User_IsBindCard();
	        req.setUserId(userId);
	        ResMsg_User_IsBindCard res = (ResMsg_User_IsBindCard)regularService.handleMsg(req);
	        model.put("isBindCard", res.isBindCard() ? "YES" : "NO");
		}
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
		    Date dt1 = df.parse(Constants.LOAD_MATCH_SHOW_TIME);
		    Date dt2 = df.parse(DateUtil.formatYYYYMMDD(resMsg_Product_ProductDetailInfoQuery.getStartTime()));
		    if (dt1.getTime() > dt2.getTime()) {
                log.info("【产品详情页结束】");
		    	return channel + "/regular/regular_index_old_product";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
        log.info("【产品详情页结束】");
        return channel + "/regular/regular_" + flag;
        
        
        
        
        
        
        
    }

    /**
     * 定期理财购买，打开输入金额界面
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param reqMsg
     * @return
     */
    @Deprecated
    @RequestMapping("{channel}/regular/buy")
    public String regularBuyInit(@PathVariable String channel, HttpServletRequest request,
                                 HttpServletResponse response, Map<String, Object> model,
                                 ReqMsg_RegularBuy_InfoQuery reqMsg) {

        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        reqMsg.setUserId(Integer.parseInt(userId));
        String nick = cookieManager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_NAME.getName(), true);
        reqMsg.setNick(nick);

        ResMsg_RegularBuy_InfoQuery resMsg = (ResMsg_RegularBuy_InfoQuery) regularService
            .handleMsg(reqMsg);

        resMsg.setBuyNum(resMsg.getBuyNum()); //数据里查询到的是已经购买的次数，而在页面展示的是还可以购买的次数，所以这里要做一次的相减

        model.put("result", resMsg);
        Date tomorrow = DateUtil.addDays(new Date(), 1);
        model.put("interestBeginDate", tomorrow);

        return channel + "/regular/regular_buy";
    }

    /**
     * 手机端中会跳入到选择银行页面中，pc端无这步操作
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param product
     * @return
     */
    @Deprecated
    @RequestMapping("{channel}/regular/choseBank")
    public String regularBuyChoseBank(@PathVariable String channel, HttpServletRequest request,
                                      HttpServletResponse response, Map<String, Object> model,
                                      ReqMsg_RegularBuy_Buy product) {

        model.put("product", product);
        return channel + "/regular/regular_choseBank";
    }

    /**
     * 购买提交
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param product
     * @return
     */
    @RequestMapping("{channel}/regular/buySubmit")
    //@ResponseBody
    public String regularBuySubmit(@PathVariable String channel, HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model,
                                   ReqMsg_RegularBuy_Buy product) {
        try {
            CookieManager manager = new CookieManager(request);
            String userId = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);

            product.setUserId(Integer.parseInt(userId));
            product.setChannel(channel);

            log.info("【页面提交购买】 ，userId = " + userId + ",  amount = " + product.getMoney()
                     + ", productId = " + product.getProductId());

            ResMsg_RegularBuy_Buy resProduct = (ResMsg_RegularBuy_Buy) regularService
                .handleMsg(product);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(resProduct.getResCode())) {
                PrintWriter out = response.getWriter();
                out.print(resProduct.getRetHtml());
                out.flush();
                out.close();
                return null;
            } else {
                model.put("respMsg", resProduct.getResMsg());
            }
        } catch (Exception e) {
            log.error("=========================购买定期理财产品交易提交失败==========================");
            e.printStackTrace();
            model.put("channel", channel);
            return "micro/regular/error";
        }
        model.put("channel", channel);
        return "micro/regular/error";
    }

    /**
     * 购买前检验，是否绑卡，是否实名认证
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param req
     * @return
     */
    @Deprecated
    @RequestMapping("{channel}/regular/checkIsLegal")
    public @ResponseBody
    HashMap<String, Object> regularBuyCheckIsLeagal(@PathVariable String channel,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    Map<String, Object> model,
                                                    ReqMsg_User_InfoQuery req) {
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        try {
            String result = "no";
            CookieManager manager = new CookieManager(request);
            String userId = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
            if (userId == null || "".equals(userId)) {
                result = "noStu";
                dataMap.put("result", result);
                return dataMap;
            }
            req.setUserId(Integer.parseInt(userId));
            ResMsg_User_InfoQuery res = (ResMsg_User_InfoQuery) regularService.handleMsg(req);

            String rightUrl = "";
            String massage = "";
            if (res.getDafyStatus() == Constants.IS_BIND_BANK_NO) {
                massage = "您目前尚未绑定银行卡，无法购买！";
                result = "no";
            } else if (res.getDafyStatus() == Constants.IS_BIND_BANK_BINGING) {
                massage = "您的银行正在绑定中，请稍后！";
                result = "binding";
            } else if (res.getDafyStatus() == Constants.IS_BIND_BANK_FAIL) {
                massage = "您的银行卡绑定失败，请重新绑定！";
                result = "no";
            } else {
                result = "yes";
            }

            dataMap.put("result", result);
            dataMap.put("rightUrl", rightUrl);
            dataMap.put("massage", massage);

        } catch (Exception e) {
            errorRes(dataMap, e);
            log.error("=========================判断购买是否合法==========================");
            e.printStackTrace();
        }

        return dataMap;
    }

    /**
     * 购买付款界面输入金额，选择银行（已废）
     * @param channel
     * @param amount
     * @param productId
     * @param request
     * @param response
     * @param model
     * @param req
     * @deprecated
     * @return
     */
    @Deprecated
    @RequestMapping("{channel}/regular/buyPayment")
    public String regualrPayMemt(@PathVariable String channel, double amount, int productId,
                                 HttpServletRequest request, HttpServletResponse response,
                                 Map<String, Object> model, ReqMsg_User_BankListQuery req) {
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);

        req.setUserId(Integer.parseInt(userId));

        ResMsg_User_BankListQuery res = (ResMsg_User_BankListQuery) regularService.handleMsg(req);

        if (res.getBankCards() == null || res.getBankCards().size() == 0) {
            model.put("bankSize", 0);
        } else {

            for (HashMap<String, Object> bank : res.getBankCards()) {
                String str = (String) bank.get("cardNo");
                str = str.substring(0, 4) + "********"
                      + str.subSequence(str.length() - 4, str.length());
                str = (String) bank.get("bankName") + ":" + str;
                bank.put("no", str);
            }

            model.put("bankList", res.getBankCards());
            model.put("bankSize", res.getBankCards().size());
        }

        if (res.getIsBindName() == Constants.IS_BIND_NAME_YES) {
            model.put("name", "**" + res.getUserName().charAt(res.getUserName().length() - 1));
            model.put(
                "cardId",
                res.getIdCard().charAt(0) + "**********"
                        + res.getIdCard().charAt(res.getIdCard().length() - 1));
            model.put("isBind", res.getIsBindName());
        }
        String transferId = request.getParameter("transferId");
        model.put("amount", amount);
        model.put("productId", productId);
        model.put("transferId", transferId);
        return channel + "/regular/regular_buyPayMent";
    }

    /**
     * 初始化添加转让产品
     * @param channel
     * @param request
     * @param response
     * @param reqMsg
     * @param model
     * @return
     */
    @Deprecated
    @RequestMapping("{channel}/regular/transfer/addTransfer")
    public String regularTransferAdd(@PathVariable String channel, HttpServletRequest request,
                                     HttpServletResponse response, ReqMsg_Invest_InfoQuery reqMsg,
                                     HashMap<String, Object> model) {
        //CookieManager cookieManager = new CookieManager(request);
        /*	String userId = cookieManager.getValue(CookieEnums._SITE.getName(),CookieEnums._SITE_USER_ID.getName(), true);
        	reqMsg.setUserId(Integer.parseInt(userId));
        	
        	ResMsg_Invest_InfoQuery resMsg = (ResMsg_Invest_InfoQuery) regularService.handleMsg(reqMsg);
        	model.put("transfer", resMsg);
        	//截止目前本金+利息的金额
        	double amount = resMsg.getInvestAmount() + resMsg.getInvestAmount() * resMsg.getInvestDay() * resMsg.getInvestRate() * 0.01 / 360;
        	BigDecimal bg = new BigDecimal(amount);
            amount = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        	model.put("amount", amount);
        	return channel + "/transfer/addTransfer";*/
        return channel + "/transfer/transfer_maintenance";
    }

    /**
     * 保存添加转让的产品
     * @param reqMsg
     * @param request
     * @return
     */
    @Deprecated
    @RequestMapping("{channel}/regular/transfer/addTransferSave")
    public @ResponseBody
    HashMap<String, Object> regularTransferSave(ReqMsg_Transfer_InfoTransfer reqMsg,
                                                HttpServletRequest request) {
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        reqMsg.setUserId(Integer.parseInt(userId));
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        try {
            ResMsg_Transfer_InfoTransfer resMsg = (ResMsg_Transfer_InfoTransfer) regularService
                .handleMsg(reqMsg);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
                successRes(dataMap, resMsg);
            } else {
                errorRes(dataMap, resMsg);
            }
        } catch (Exception e) {
            errorRes(dataMap, e);
            log.error("=========================转让定期理财产品交易提交失败==========================");
            e.printStackTrace();
        }
        return dataMap;
    }

    /**
     * 查看转让产品列表
     * @param channel
     * @param request
     * @param response
     * @param reqMsg
     * @param model
     * @return
     */
    @Deprecated
    @RequestMapping("{channel}/regular/transferIndex")
    public String regularTransferIndexInit(@PathVariable String channel,
                                           HttpServletRequest request,
                                           HttpServletResponse response,
                                           ReqMsg_Transfer_TransferListQuery reqMsg,
                                           HashMap<String, Object> model) {

        /*String user = request.getParameter("user");  //当点击用户分享链接过来时会有此参数
        if(user !=null && !"".equals(user)){
        	user = user.substring(0, user.length()-36);
        	CookieManager cookieManager = new CookieManager(request);
        	String recommendId = cookieManager.getValue(CookieEnums._SITE.getName(),CookieEnums._SITE_RECOMMEND_ID.getName(), true);
        	if(recommendId == null || "".equals(recommendId)){ //说明并没有存在推荐人，同时下面将推荐人的id号存入cookie当中
        		cookieManager.setValue(CookieEnums._SITE.getName(),CookieEnums._SITE_RECOMMEND_ID.getName(),user, true);
        		cookieManager.save(response, CookieEnums._SITE.getName(), null, "/", 600, true);
        	}
        }
        
        reqMsg.setPageIndex(0);
        reqMsg.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
        ResMsg_Transfer_TransferListQuery resp = (ResMsg_Transfer_TransferListQuery) regularService.handleMsg(reqMsg);
        model.put("pageIndex", 0);
        model.put("totalCount", resp.getTotal());
        model.put("transferList", resp.getValueList());
        
        
        Map<String,String> sign = WeiChat.creatSign(request);
        
        model.put("weichat", sign);
        return channel + "/transfer/regular_transferIndex";*/

        return channel + "/transfer/transfer_maintenance";
    }

    /**
     * 转让列表分页用是的转让明细
     * @param channel
     * @param request
     * @param response
     * @param reqMsg
     * @param model
     * @return
     */
    @Deprecated
    @RequestMapping("{channel}/regular/transferContent")
    public String fundValueContent(@PathVariable String channel, HttpServletRequest request,
                                   HttpServletResponse response,
                                   ReqMsg_Transfer_TransferListQuery reqMsg,
                                   Map<String, Object> model) {
        try {
            reqMsg.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
            ResMsg_Transfer_TransferListQuery resp = (ResMsg_Transfer_TransferListQuery) regularService
                .handleMsg(reqMsg);

            model.put("transferList", resp.getValueList());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return channel + "/transfer/transfer_valueContent";
    }

    /**
     * 点击转让产品时的详情页
     * @param channel
     * @param request
     * @param response
     * @param req
     * @param model
     * @return
     */
    @Deprecated
    @RequestMapping("{channel}/regular/transferDetail")
    public String regularTransferBuyInit(@PathVariable String channel, HttpServletRequest request,
                                         HttpServletResponse response,
                                         ReqMsg_Transfer_TransferDetail req,
                                         HashMap<String, Object> model) {

        String user = request.getParameter("user"); //当点击用户分享链接过来时会有此参数
        if (user != null && !"".equals(user)) {
            user = user.substring(0, user.length() - 36);
            CookieManager cookieManager = new CookieManager(request);
            String recommendId = cookieManager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_RECOMMEND_ID.getName(), true);
            if (recommendId == null || "".equals(recommendId)) { //说明并没有存在推荐人，同时下面将推荐人的id号存入cookie当中
                cookieManager.setValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_RECOMMEND_ID.getName(), user, true);
                cookieManager.save(response, CookieEnums._SITE.getName(), null, "/",
                    5*365 * 24 * 3600, true);
            }
        }

        ResMsg_Transfer_TransferDetail res = (ResMsg_Transfer_TransferDetail) regularService
            .handleMsg(req);
        model.put("transfer", res);
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        if (Integer.parseInt(userId) == res.getUserId()) { //代表该转让产品是自己转让的。
            model.put("myself", "true");
        }

        Map<String, String> sign = weChatUtil.createAuth(request);

        model.put("weichat", sign);

        return channel + "/transfer/transfer_detail";
    }

    /**
     * 定期理财错误页面
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param req
     * @return
     */
    @Deprecated
    @RequestMapping("{channel}/regular/error")
    public String regularErrorInit(@PathVariable String channel, HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model,
                                   ReqMsg_System_Status req) {

        ResMsg_System_Status resMsg = (ResMsg_System_Status) regularService.handleMsg(req);
        Constants.sysValue = resMsg.getSysValue();
        Constants.tranValue = resMsg.getTranValue();
        String respCode = request.getParameter("respCode");
        String respMsg = request.getParameter("respMsg");
        model.put("respCode", respCode);
        if ("REFU".equals(respCode)) {
            model.put("respMsg", "系统异常，请联系我们");
        } else if ("FAIL".equals(respCode)) {
            model.put("respMsg", respMsg);
        }

        return channel + "/regular/error";
    }

    /**
     * 付款后的返回页面
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @Deprecated
    @RequestMapping("{channel}/regular/pay/return")
    public String regularPayReturnInit(@PathVariable String channel, HttpServletRequest request,
                                       HttpServletResponse response, Map<String, Object> model) {
        String respCode = request.getParameter("v_pstatus");
        if (StringUtil.isEmpty(respCode)) {
            respCode = request.getParameter("respCode");
        }
        model.put("respCode", respCode);
        if ("20".equals(respCode) && "micro".equals(channel)) {
            return channel + "/regular/pay_succ";
        }
        return channel + "/regular/pay_result";
    }

    /**
     * 银行支付说明
     * @param channel
     * @param flag
     * @param request
     * @param response
     * @param model
     * @return
     */
    @Deprecated
    @RequestMapping("{channel}/regular/bank/{flag}")
    public String bankInit(@PathVariable String channel, @PathVariable String flag,
                           HttpServletRequest request, HttpServletResponse response,
                           Map<String, Object> model) {

        return channel + "/regular/bank" + flag;
    }

    /**
     * 用户购买转让产品
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param req
     * @return
     */
    @Deprecated
    @RequestMapping("{channel}/regular/transfer/buySubmit")
    public @ResponseBody
    HashMap<String, Object> regularTransferBuySubmit(@PathVariable String channel,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response,
                                                     Map<String, Object> model,
                                                     ReqMsg_Transfer_BuySubmit req) {
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        try {
            if (req.getMoney() <= 0) {
                dataMap.put(ConsMsgUtil.RESCODE, ConstantUtil.RESCODE_FAIL);
                dataMap.put(ConsMsgUtil.RESMSG, Constants.MONEY_MESSAGE);
            }
            CookieManager manager = new CookieManager(request);
            String userId = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);

            req.setUserId(Integer.parseInt(userId));

            ResMsg_Transfer_BuySubmit resProduct = (ResMsg_Transfer_BuySubmit) regularService
                .handleMsg(req);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(resProduct.getResCode())) {
                successRes(dataMap, resProduct);
            } else {
                errorRes(dataMap, resProduct);
            }
        } catch (Exception e) {
            errorRes(dataMap, e);
            log.error("=========================购买定期理财产品交易提交失败==========================");
            e.printStackTrace();
        }

        return dataMap;
    }

    /**
     * 购买前验证，当前产品还可以购买的次数，剩余购买的金额
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param req
     * @return
     */
    @Deprecated
    @RequestMapping("{channel}/regular/buyNum")
    public @ResponseBody
    HashMap<String, Object> buyNumCheck(@PathVariable String channel, HttpServletRequest request,
                                        HttpServletResponse response, Map<String, Object> model,
                                        ReqMsg_RegularBuy_InfoQuery req) {
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);

        req.setUserId(Integer.parseInt(userId));

        ResMsg_RegularBuy_InfoQuery res = (ResMsg_RegularBuy_InfoQuery) regularService
            .handleMsg(req);
        if (!"000000".equals(res.getResCode())) {
            dataMap.put("error", "yes");
            dataMap.put("message", "系统异常，请重试！");
        } else {

            if (res.getBuyNum() > 0) { //该产品的购买次数还充足
                dataMap.put("result", "yes");

                if (res.getSurplusAmount() < req.getMoney().doubleValue()) { //当剩余金额大于输入的金额时，
                    dataMap.put("surplusResult", "no");
                    dataMap.put("surplusAmount", res.getSurplusAmount());
                } else {
                    dataMap.put("surplusResult", "yes");
                }
            } else {
                dataMap.put("result", "no");
                dataMap.put("num", res.getMaxInvestTimes());
            }
        }
        return dataMap;
    }

    @RequestMapping("{channel}/regular/dispatch_pc")
    public String dispatchPc(@PathVariable String channel, HttpServletRequest request,
                             HttpServletResponse response, Map<String, Object> model) {

        return channel + "/regular/dispatch_pc";
    }

    /**
     * 根据卡bin表 银行卡号查询银行
     * 
     * @param request
     * @param reqMsg
     * @param response
     * @param cardNo
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping("{channel}/regular/queryCardBank")
    public Map<String, Object> queryCardBank(@PathVariable String channel,HttpServletRequest request,
                                             ReqMsg_Bank_QueryBankBin reqMsg,
                                             HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = new CookieManager(request).getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        reqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_Bank_QueryBankBin resMsg = (ResMsg_Bank_QueryBankBin) regularService.handleMsg(reqMsg);
        if(resMsg.getBankCardFuncType() !=null && (!resMsg.getBankCardFuncType().equals(Constants.CARDBIN_TYPE_JIE_JI))){
        	result.put("type", "no");
        }
        result.put("bin", resMsg);
        return result;
    }
    
    /**
     * 网银购买
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param product
     * @return
     */
    @RequestMapping("/gen2.0/regular/netBankbuySubmit")
    @ResponseBody
    public Map<String, Object> netBankbuySubmit(HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model,
                                   ReqMsg_RegularBuy_NetBankbuy product) {
    	
    	Map<String, Object> result = new HashMap<String, Object>();
        
    	//验证用户购买金额是否大于标的剩余额度或者大于可买额度
    	@SuppressWarnings("unchecked")
    	Map<String, Object> checkUserMap = (Map<String, Object>)request.getAttribute("resultMap");
    	if(checkUserMap != null && !ConstantUtil.RESCODE_SUCCESS.equals(checkUserMap.get("resCode").toString())) {
    		result.put("resCode", checkUserMap.get("resCode").toString());
    		result.put("resMsg", checkUserMap.get("resMsg").toString());
    		return result;
    	}
    	
    	
    	try {
            CookieManager manager = new CookieManager(request);
            String userId = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
            product.setUserId(Integer.parseInt(userId));
	            log.info("【页面提交购买】 ，userId = " + userId + ",  amount = " + product.getMoney()
	                     + ", productId = " + product.getProductId());

            ResMsg_RegularBuy_NetBankbuy resProduct = (ResMsg_RegularBuy_NetBankbuy) regularService
                .handleMsg(product);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(resProduct.getResCode())) {
            	/*response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.print(resProduct.getRetHtml());
                log.info("form表单：【" + resProduct.getRetHtml() + "】");
                out.close();
                return null;*/
            	result.put("resCode", "000");
            	result.put("resHTML", resProduct.getRetHtml());
            } else {
            	result.put("resCode", resProduct.getResCode());
            	result.put("resMsg", resProduct.getResMsg());
            }
        } catch (Exception e) {
                log.error("=========================购买定期理财产品交易提交失败==========================");
            e.printStackTrace();
            errorRes(result);
        }
        return result;
    }
    
    /**
     * 跳转至添加银行卡页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/gen2.0/regular/add_bankcard_index")
    public String addBankcardIndex(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        model.put("buyAmount", request.getParameter("buyAmount"));
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_User_InfoQuery reqMsg = new ReqMsg_User_InfoQuery();
        reqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery res = (ResMsg_User_InfoQuery)regularService.handleMsg(reqMsg);
        
        //查询19付支持快捷支付的银行
        ReqMsg_Bank_Pay19BankList req = new ReqMsg_Bank_Pay19BankList();
        req.setUserId(Integer.parseInt(userId));
        ResMsg_Bank_Pay19BankList resp = (ResMsg_Bank_Pay19BankList) regularService.handleMsg(req);
        model.put("bankList", resp.getBankList());
        model.put("userName", res.getUserName());
        model.put("idCard", res.getIdCard());
        return "/gen2.0/regular/buy_product_add_bankcard";
    }
    
    
    
    /**
     * 新用户购买提交(PC)--预下单
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param product
     * @return
     */
    @Token(save = true)
    @RequestMapping("gen2.0/regular/firstBuySubmitPro")
    @ResponseBody
    public Map<String, Object> firstBuySubmitPro( HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model,
                                   ReqMsg_RegularBuy_Order req) {   
    	Map<String, Object> result = new HashMap<String, Object>();
    	CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
             CookieEnums._SITE_USER_ID.getName(), true);
    	//验证用户购买金额是否大于标的剩余额度或者大于可买额度
    	@SuppressWarnings("unchecked")
    	Map<String, Object> checkUserMap = (Map<String, Object>)request.getAttribute("resultMap");
    	if(checkUserMap != null && !ConstantUtil.RESCODE_SUCCESS.equals(checkUserMap.get("resCode").toString())) {
    		result.put("resCode", checkUserMap.get("resCode").toString());
    		result.put("resMsg", checkUserMap.get("resMsg").toString());
    		return result;
    	}
    	
        //String serverToken = (String) request.getSession().getAttribute("token");
    	String serverToken = Util.getServerToken(request, userId);
        if(StringUtil.isEmpty(serverToken)){
        	errorRes(result);
        	return result;
        }
       
        ReqMsg_Bank_QueryBankById reqMsg_Bank_QueryBankById = new ReqMsg_Bank_QueryBankById();
        reqMsg_Bank_QueryBankById.setBankId(req.getBankId());
        ResMsg_Bank_QueryBankById resMsg_Bank_QueryBankById = (ResMsg_Bank_QueryBankById)regularService.handleMsg(reqMsg_Bank_QueryBankById);
        req.setBankName(resMsg_Bank_QueryBankById.getName());
        req.setUserId(Integer.parseInt(userId));
        req.setIsBind(Constants.IS_BIND_BANK_NO);
        req.setTransType(Constants.USER_TRANS_TYPE_CARD);
        req.setTerminalType(2);
        req.setPlaceOrder(1);
       try {
        	 ResMsg_RegularBuy_Order resp = (ResMsg_RegularBuy_Order) regularService.handleMsg(req);
        	 result.put("orderNo", resp.getOrderNo());
        	 
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
            	result.put("resCode", "000");
            	result.put("resMsg", resp.getResMsg());
            	result.put("token", serverToken);
                result.put("htmlReapalString", resp.getHtml());
            	manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_BS_QIANBAO_USER.getName(),
        	            null, true);
	        	manager.save(response, CookieEnums._SITE.getName(), null, "/",
	                    5*365 * 24 * 3600, true);
            } else {
            	result.put("resCode", resp.getResCode());
            	result.put("resMsg", resp.getResMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorRes(result);
        }
        return result;
    }
    
    
    /**
     * 新用户购买提交(PC)--正式下单
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param product
     * @return
     */
    @RequestMapping("gen2.0/regular/firstBuySubmit")
    @ResponseBody
    public Map<String, Object> firstBuySubmit( HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model,
                                   ReqMsg_RegularBuy_Order req) {
        Map<String, Object> result = new HashMap<String, Object>();
        
        //验证用户购买金额是否大于标的剩余额度或者大于可买额度
        @SuppressWarnings("unchecked")
        Map<String, Object> checkUserMap = (Map<String, Object>)request.getAttribute("resultMap");
        if(checkUserMap != null && !ConstantUtil.RESCODE_SUCCESS.equals(checkUserMap.get("resCode").toString())) {
            result.put("resCode", checkUserMap.get("resCode").toString());
            result.put("resMsg", checkUserMap.get("resMsg").toString());
            return result;
        }
        
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_Bank_QueryBankById reqMsg_Bank_QueryBankById = new ReqMsg_Bank_QueryBankById();
        reqMsg_Bank_QueryBankById.setBankId(req.getBankId());
        ResMsg_Bank_QueryBankById resMsg_Bank_QueryBankById = (ResMsg_Bank_QueryBankById)regularService.handleMsg(reqMsg_Bank_QueryBankById);
        req.setBankName(resMsg_Bank_QueryBankById.getName());
        req.setUserId(Integer.parseInt(userId));
        req.setIsBind(Constants.IS_BIND_BANK_NO);
        req.setTransType(Constants.USER_TRANS_TYPE_CARD);
        req.setTerminalType(2);
        req.setPlaceOrder(2);
       
       if(Util.isRepeatSubmit(request, response)){
       	result.put("resCode", "999");
          	result.put("resMsg","请勿重复提交订单！");
       }
       
      try {
    	  ResMsg_RegularBuy_Order resp = (ResMsg_RegularBuy_Order) regularService.handleMsg(req);
           if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
	           	result.put("resCode", "000");
	           	result.put("resMsg", resp.getResMsg());
           } else {
        		result.put("resCode", resp.getResCode());
	           	result.put("resMsg", resp.getResMsg());
           }
       } catch (Exception e) {
           e.printStackTrace();
           errorRes(result);
       }
       return result;
    }
    
    
    /**
     * 【购买】已绑卡用户（预下单）
     * 
     * @param request
     * @param response
     * @return
     */
    @Token(save = true)
    @ResponseBody
    @RequestMapping("/gen2.0/regular/pre_submit_bind")
    public Map<String, Object> preSubmitBind(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        //验证用户购买金额是否大于标的剩余额度或者大于可买额度
        @SuppressWarnings("unchecked")
        Map<String, Object> checkUserMap = (Map<String, Object>)request.getAttribute("resultMap");
        if(checkUserMap != null && !ConstantUtil.RESCODE_SUCCESS.equals(checkUserMap.get("resCode").toString())) {
        	result.put("resCode", checkUserMap.get("resCode").toString());
        	result.put("resMsg", checkUserMap.get("resMsg").toString());
        	return result;
        }
        
        result.put("buyAmount", request.getParameter("buyAmount"));
        result.put("cardId", request.getParameter("cardId"));
        result.put("mobile", request.getParameter("mobile"));
        result.put("bankId", request.getParameter("bankId"));
        
        //String serverToken = (String) request.getSession().getAttribute("token");
        String serverToken = Util.getServerToken(request, userId);
        if(StringUtil.isEmpty(serverToken)){
        	errorRes(result);
        	return result;
        }
        
        String amount = request.getParameter("buyAmount");
        Double amount1 = Double.parseDouble(amount);
        
        // 用户基本信息
        ReqMsg_User_InfoQuery userInfoReqMsg = 
        		new ReqMsg_User_InfoQuery();
        userInfoReqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery userInfo = (ResMsg_User_InfoQuery)siteService.handleMsg(userInfoReqMsg);
        result.put("userName", userInfo.getUserName());
        result.put("idCard", userInfo.getIdCard());
        
        // 查询银行卡信息
        ReqMsg_Bank_QueryCardById req = new ReqMsg_Bank_QueryCardById();
        req.setCardId(Integer.parseInt(request.getParameter("cardId")));
        ResMsg_Bank_QueryCardById res = (ResMsg_Bank_QueryCardById)siteService.handleMsg(req);
        
        if (!userId.equals(String.valueOf(res.getUserId()))) {
        	result.put("resCode", "999");
           	result.put("resMsg", "用户无权限操作此银行卡");
           	return result;
		}
        
        // 预下单
        ReqMsg_RegularBuy_Order reqMsg = new ReqMsg_RegularBuy_Order();
        // 红包
        reqMsg.setRedPacketId(StringUtil.isBlank(request.getParameter("redPacketId"))?null:Integer.parseInt(request.getParameter("redPacketId")));
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setAmount(amount1);
        reqMsg.setBankName(res.getBankName());
        reqMsg.setUserName(userInfo.getUserName());
        reqMsg.setCardNo(res.getCardNo());
        reqMsg.setIdCard(userInfo.getIdCard());
        reqMsg.setMobile(res.getMobile());
        reqMsg.setBankId(res.getBankId());
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setIsBind(Constants.IS_BIND_BANK_YES);
        reqMsg.setTransType(Constants.USER_TRANS_TYPE_CARD);
        reqMsg.setTerminalType(2);  // PC端
        reqMsg.setPlaceOrder(1);    // 预下单
        reqMsg.setProductId(Integer.parseInt(request.getParameter("productId")));
        try {
            ResMsg_RegularBuy_Order resp = (ResMsg_RegularBuy_Order)siteService.handleMsg(reqMsg);
            result.put("orderNo", resp.getOrderNo());
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){

                result.put("htmlReapalString", resp.getHtml());
                if (resp.getHtml() !=null && !"fail".equals(resp.getHtml())) {
                	manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_BS_QIANBAO_USER.getName(),
                	            null, true);
                	manager.save(response, CookieEnums._SITE.getName(), null, "/",
                            5*365 * 24 * 3600, true);
				}
            	result.put("token", serverToken);
                successRes(result);
            } else {
        		result.put("resCode", resp.getResCode());
	           	result.put("resMsg", resp.getResMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorRes(result);
        }
        return result;
    }
    
    
    
    
    /**
     * 【购买】已绑卡用户。（确认下单）
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/gen2.0/regular/submit_bind")
    public Map<String, Object> submitBind(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        
        //验证用户购买金额是否大于标的剩余额度或者大于可买额度
        @SuppressWarnings("unchecked")
        Map<String, Object> checkUserMap = (Map<String, Object>)request.getAttribute("resultMap");
        if(checkUserMap != null && !ConstantUtil.RESCODE_SUCCESS.equals(checkUserMap.get("resCode").toString())) {
            result.put("resCode", checkUserMap.get("resCode").toString());
            result.put("resMsg", checkUserMap.get("resMsg").toString());
            return result;
        }
        
        result.put("buyAmount", request.getParameter("buyAmount"));
        result.put("cardId", request.getParameter("cardId"));
        result.put("mobile", request.getParameter("mobile"));
        result.put("mobileCode", request.getParameter("mobileCode"));
        String amount = request.getParameter("buyAmount");
        Double amount1 = Double.parseDouble(amount);
        
        if(Util.isRepeatSubmit(request, response)){
        	result.put("resCode", "999");
           	result.put("resMsg","请勿重复提交订单！");
        }
        
        // 用户基本信息
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_RegularBuy_Order reqMsg = new ReqMsg_RegularBuy_Order();
        ReqMsg_User_InfoQuery userInfoReqMsg = new ReqMsg_User_InfoQuery();
        userInfoReqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery userInfo = (ResMsg_User_InfoQuery)siteService.handleMsg(userInfoReqMsg);
        result.put("userName", userInfo.getUserName());
        result.put("idCard", userInfo.getIdCard());
        
        // 查询银行卡信息
        ReqMsg_Bank_QueryCardById req = new ReqMsg_Bank_QueryCardById();
        req.setCardId(Integer.parseInt(request.getParameter("cardId")));
        ResMsg_Bank_QueryCardById res = (ResMsg_Bank_QueryCardById)siteService.handleMsg(req);
        
        // 确认下单
        String redPacketId = request.getParameter("redPacketId");
        reqMsg.setRedPacketId(StringUtil.isBlank(redPacketId) ? null : Integer.parseInt(redPacketId));
        reqMsg.setUserName(userInfo.getUserName());
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setAmount(amount1);
        reqMsg.setBankName(res.getBankName());
        reqMsg.setCardNo(res.getCardNo());
        reqMsg.setIdCard(userInfo.getIdCard());
        reqMsg.setMobile(res.getMobile());
        reqMsg.setBankId(res.getBankId());
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setVerifyCode(request.getParameter("mobileCode"));
        reqMsg.setIsBind(Constants.IS_BIND_BANK_YES);
        reqMsg.setTransType(Constants.USER_TRANS_TYPE_CARD);
        reqMsg.setTerminalType(2);  // PC端
        reqMsg.setPlaceOrder(2);    // 下单
        reqMsg.setOrderNo(request.getParameter("orderNo"));
        reqMsg.setProductId(Integer.parseInt(request.getParameter("productId")));
        try {
            ResMsg_RegularBuy_Order resp = (ResMsg_RegularBuy_Order)siteService.handleMsg(reqMsg);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
                successRes(result);
            } else {
        		result.put("resCode", resp.getResCode());
	           	result.put("resMsg", resp.getResMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorRes(result);
        }
        return result;
    }
    
    
    /**
     * 【购买】购买成功页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/gen2.0/regular/regular_success")
    public String regularSuccess(HttpServletRequest request, HttpServletResponse response) {
        return "/gen2.0/regular/regular_success";
    }
    
    /**
     * 【购买】购买失败页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/gen2.0/regular/error")
    public String regularError(HttpServletRequest request, HttpServletResponse response) {
        return "/gen2.0/regular/error";
    }
    
    
    /**
     * 【购买】已绑卡用户新银行卡购买（预下单）
     * 
     * @param request
     * @param response
     * @return
     */
    @Deprecated
    @ResponseBody
    @RequestMapping("/gen2.0/regular/add_bankcard")
    public Map<String, Object> addBankcard(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("buyMoney", request.getParameter("buyMoney"));
        result.put("cardNo", request.getParameter("cardNo"));
        result.put("bankId", request.getParameter("bankName"));
        result.put("mobile", request.getParameter("mobile"));
        
        String amount = request.getParameter("buyMoney");
        Double amount1 = Double.parseDouble(amount);
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        
        // 用户基本信息
        ReqMsg_User_InfoQuery userInfoReqMsg = new ReqMsg_User_InfoQuery();
        userInfoReqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery userInfoRes = (ResMsg_User_InfoQuery)siteService.handleMsg(userInfoReqMsg);
        result.put("userName", userInfoRes.getUserName());
        result.put("idCard", userInfoRes.getIdCard());
        
        
        // 预下单
        ReqMsg_RegularBuy_Order reqMsg = new ReqMsg_RegularBuy_Order();
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setAmount(amount1);
        reqMsg.setCardNo(request.getParameter("cardNo"));
        reqMsg.setIdCard(userInfoRes.getIdCard());
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setUserName(userInfoRes.getUserName());
        reqMsg.setMobile(request.getParameter("mobile"));
        reqMsg.setBankId(Integer.parseInt(request.getParameter("bankName")));
        reqMsg.setIsBind(Constants.IS_BIND_BANK_YES);
        reqMsg.setTransType(Constants.USER_TRANS_TYPE_CARD);
        reqMsg.setTerminalType(2);  // PC端
        reqMsg.setPlaceOrder(1);    // 预下单
        try {
            ResMsg_RegularBuy_Order resp = (ResMsg_RegularBuy_Order)siteService.handleMsg(reqMsg);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
                successRes(result);
            } else {
        		result.put("resCode", resp.getResCode());
	           	result.put("resMsg", resp.getResMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorRes(result);
        }
        return result;
    }
    
    
    /**
     * 【购买】已绑卡用户新银行卡购买（正式下单）
     * 
     * @param request
     * @param response
     * @return
     */
    @Deprecated
    @ResponseBody
    @RequestMapping("/gen2.0/regular/add_bankcard_submit")
    public Map<String, Object> addBankcardSubmit(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("buyMoney", request.getParameter("buyMoney"));
        result.put("cardNo", request.getParameter("cardNo"));
        result.put("bankId", request.getParameter("bankName"));
        result.put("mobile", request.getParameter("mobile"));
        result.put("mobileCode", request.getParameter("mobileCode"));
        String amount = request.getParameter("buyMoney");
        Double amount1 = Double.parseDouble(amount);
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        
        // 用户基本信息
        ReqMsg_User_InfoQuery userInfoReqMsg = new ReqMsg_User_InfoQuery();
        userInfoReqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery userInfoRes = (ResMsg_User_InfoQuery)siteService.handleMsg(userInfoReqMsg);
        result.put("userName", userInfoRes.getUserName());
        result.put("idCard", userInfoRes.getIdCard());
        
        
        // 预下单
        ReqMsg_RegularBuy_Order reqMsg = new ReqMsg_RegularBuy_Order();
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setAmount(amount1);
        reqMsg.setCardNo(request.getParameter("cardNo"));
        reqMsg.setIdCard(userInfoRes.getIdCard());
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setUserName(userInfoRes.getUserName());
        reqMsg.setMobile(request.getParameter("mobile"));
        reqMsg.setBankId(Integer.parseInt(request.getParameter("bankName")));
        reqMsg.setVerifyCode(request.getParameter("mobileCode"));
        reqMsg.setIsBind(Constants.IS_BIND_BANK_YES);
        reqMsg.setTransType(Constants.USER_TRANS_TYPE_CARD);
        reqMsg.setTerminalType(2);  // PC端
        reqMsg.setPlaceOrder(2);    // 预下单
        reqMsg.setOrderNo(request.getParameter("orderNo"));
       try {
            ResMsg_RegularBuy_Order resp = (ResMsg_RegularBuy_Order)siteService.handleMsg(reqMsg);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
                successRes(result);
            } else {
        		result.put("resCode", resp.getResCode());
	           	result.put("resMsg", resp.getResMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorRes(result);
        }
        return result;
    }
    
    
    /**
     * 【购买】已绑卡用户余额购买
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/gen2.0/regular/balance_buy")
    public Map<String, Object> balanceBuy(HttpServletRequest request, HttpServletResponse response) {
    	Map<String, Object> result = new HashMap<String, Object>();
        //重复提交
        if(Util.isRepeatSubmit(request, response)) {
            result.put("resCode", "999");
            result.put("resMsg", "购买失败：重复提交的请求！");
            return result;
        }

    	//验证用户购买金额是否大于标的剩余额度或者大于可买额度
        @SuppressWarnings("unchecked")
        Map<String, Object> checkUserMap = (Map<String, Object>)request.getAttribute("resultMap");
        if(checkUserMap != null && !ConstantUtil.RESCODE_SUCCESS.equals(checkUserMap.get("resCode").toString())) {
            result.put("resCode", checkUserMap.get("resCode").toString());
            result.put("resMsg", checkUserMap.get("resMsg").toString());
            return result;
        }
    	
    	result.put("buyMoney", request.getParameter("buyMoney"));
        result.put("cardNo", request.getParameter("cardNo"));
        result.put("bankId", request.getParameter("bankName"));
        result.put("mobile", request.getParameter("mobile"));
        result.put("mobileCode", request.getParameter("mobileCode"));
        result.put("productId", request.getParameter("productId"));
        result.put("redPacketId", request.getParameter("redPacketId"));
        String amount = request.getParameter("buyMoney");
        Double amount1 = Double.parseDouble(amount);
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        //校验验证码是否正确
        ReqMsg_User_ValidUser  reqMsg_User_ValidUser = new ReqMsg_User_ValidUser();
        reqMsg_User_ValidUser.setMobile(request.getParameter("mobile"));
        reqMsg_User_ValidUser.setMobileCode(request.getParameter("mobileCode"));
        ResMsg_User_ValidUser resMsg_User_Valid = (ResMsg_User_ValidUser)userService.handleMsg(reqMsg_User_ValidUser);
  		result.put("resCode", resMsg_User_Valid.getResCode());
        result.put("resMsg", resMsg_User_Valid.getResMsg());
       
        // 用户基本信息
        ReqMsg_User_InfoQuery userInfoReqMsg = new ReqMsg_User_InfoQuery();
        userInfoReqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery userInfoRes = (ResMsg_User_InfoQuery)siteService.handleMsg(userInfoReqMsg);
        result.put("userName", userInfoRes.getUserName());
        result.put("idCard", userInfoRes.getIdCard());
        
        ReqMsg_RegularBuy_BalanceBuy  reqMsg = new ReqMsg_RegularBuy_BalanceBuy();
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setAmount(amount1);
        reqMsg.setProductId(Integer.parseInt(request.getParameter("productId")));
        reqMsg.setTerminalType(2);
        reqMsg.setTicketId(StringUtils.isBlank(request.getParameter("redPacketId"))?null:Integer.parseInt(request.getParameter("redPacketId")));
        reqMsg.setTicketType(StringUtil.isBlank(request.getParameter("ticketType")) ? null: request.getParameter("ticketType"));
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg_User_Valid.getResCode())) {
        	  try {
                  ResMsg_RegularBuy_BalanceBuy resp = (ResMsg_RegularBuy_BalanceBuy)siteService.handleMsg(reqMsg);
                  if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
                      successRes(result);
                  } else {
                      result.put("resCode", resp.getResCode());
                      result.put("resMsg", resp.getResMsg());
                      Util.createToken(request, response);
                  }
              } catch (Exception e) {
                  e.printStackTrace();
                  errorRes(result);
              }
        } else {
            Util.createToken(request, response);
        }
        return result;
    }
    
    
    
    //钱报178 ------------------------------------------------
	/**
	 * 
	 * @Title: intoBuyPage 
	 * @Description: 进入理财产品购买页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws
	 */
    @Token(save = true)
    @RequestMapping("gen178/regular/bind")
	public String gen178Bind(HttpServletRequest request, HttpServletResponse response,
			Map<String,Object> model) {
        log.info("【购买详情页开始】");

        // 0. 前置校验
        // 判断产品是否已经下线
        ReqMsg_Product_CheckProductIsOff reqOff = new ReqMsg_Product_CheckProductIsOff();
        try {
            reqOff.setProductId(Integer.parseInt(request.getParameter("id")));
        } catch (Exception e) {
            return "/gen178/regular/error";
        }
        ResMsg_Product_CheckProductIsOff resOff = (ResMsg_Product_CheckProductIsOff) regularService.handleMsg(reqOff);
        if(resOff.getIsOff()){
            model.put("isOff", true);
            return "/gen178/regular/error";
        }
        String userId = new CookieManager(request).getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);

        // 存管引导信息（未存管绑卡或激活）
        ReqMsg_DepGuide_FindDepGuideInfo depGuideReq = new ReqMsg_DepGuide_FindDepGuideInfo();
        depGuideReq.setUserId(Integer.parseInt(userId));
        ResMsg_DepGuide_FindDepGuideInfo depGuideRes = (ResMsg_DepGuide_FindDepGuideInfo) siteService.handleMsg(depGuideReq);
        if(Constants.HFBANK_GUIDE_NO_BIND_CARD.equals(depGuideRes.getIsOpened())
                || Constants.HFBANK_GUIDE_FAILED_BIND_HF.equals(depGuideRes.getIsOpened())) {
            // 未存管绑卡或激活
            return "redirect:/gen178/bankcard/index?entry=buy";
        } else if(Constants.HFBANK_GUIDE_WAIT_ACTIVATE.equals(depGuideRes.getIsOpened())) {
            return "redirect:/gen178/bankcard/activate/index";
        }
        if(Constants.is_expire.equals(depGuideRes.getRiskStatus())
                || Constants.is_no.equals(depGuideRes.getRiskStatus())) {
            // 需要进行风险测评
            return "redirect:/gen178/assets/assets";
        }

        Double buyAmount = request.getParameter("money") == null ? 0 : Double.valueOf(request.getParameter("money"));
        // 1. 查询本产品信息
        ReqMsg_Product_InfoQuery req = new ReqMsg_Product_InfoQuery();
        req.setId(Integer.valueOf(request.getParameter("id")));
        ResMsg_Product_InfoQuery infoRes = (ResMsg_Product_InfoQuery) siteService.handleMsg(req);

        ReqMsg_Ticket_TicketList ticketReq = new ReqMsg_Ticket_TicketList();
        ticketReq.setBizType(Constants.TICKET_INTEREST_BIZ_TYPE_BUY);
        ticketReq.setUserId(Integer.parseInt(userId));
        ticketReq.setStatus(Constants.RED_PACKET_STATUS_INIT);
        ticketReq.setProductId(req.getId());
        DecimalFormat format = new DecimalFormat("#.##");
        if(Constants.IS_SUPPORT_RED_PACKET_TRUE.equals(infoRes.getIsSupportRedPacket())) {
            ticketReq.setAmount(request.getParameter("money") == null ? 0 : Double.valueOf(request.getParameter("money")));
            ticketReq.setType(Constants.TICKET_INTEREST_TYPE_RED_PACKET);
            // 5. 支持红包，则获得红包信息
            ResMsg_Ticket_TicketList redRes = (ResMsg_Ticket_TicketList) regularService.handleMsg(ticketReq);
            if (CollectionUtils.isNotEmpty(redRes.getDataList())) {
                model.put("redCanUseCount", this.canUseTicket(redRes.getDataList(), buyAmount));
                Double full;
                for (HashMap<String, Object> map: redRes.getDataList()) {
                    if(map.get("full") instanceof Double) {
                        full = (Double) map.get("full");
                    } else {
                        String fullStr = (String) map.get("full");
                        full = Double.valueOf(fullStr);
                    }
                    if(full.compareTo(10000d) >= 0) {
                        Double fullWan = MoneyUtil.divide(full, 10000d).doubleValue();
                        map.put("full", format.format(fullWan));
                        map.put("isWan", "yes");
                    } else {
                        map.put("full", format.format(full));
                    }
                }
                model.put("redPacketList", redRes.getDataList());
                model.put("redCount", redRes.getCount());
            }
        }
        if(Constants.IS_SUPPORT_TICKET_INTEREST_TRUE.equals(infoRes.getIsSupportInterestTicket())) {
            // 5. 支持加息券，则获得家加息券信息
            ticketReq.setType(Constants.TICKET_INTEREST_TYPE_INTEREST_TICKET);
            ticketReq.setAmount(request.getParameter("money") == null ? null : Double.valueOf(request.getParameter("money")));
            // 加息券
            ResMsg_Ticket_TicketList ticketRes = (ResMsg_Ticket_TicketList) regularService.handleMsg(ticketReq);
            if (CollectionUtils.isNotEmpty(ticketRes.getDataList())) {
                Double full;
                model.put("ticketCanUseCount", this.canUseTicket(ticketRes.getDataList(), buyAmount));
                for (HashMap<String, Object> map: ticketRes.getDataList()) {
                    if(map.get("full") instanceof Double) {
                        full = (Double) map.get("full");
                    } else {
                        String fullStr = (String) map.get("full");
                        full = Double.valueOf(fullStr);
                    }
                    if(full.compareTo(10000d) >= 0) {
                        Double fullWan = MoneyUtil.divide(full, 10000d).doubleValue();
                        map.put("full", format.format(fullWan));
                        map.put("isWan", "yes");
                    } else {
                        map.put("full", format.format(full));
                    }
                }
                model.put("ticketList", ticketRes.getDataList());
                model.put("ticketCount", ticketRes.getCount());
            }
        }

        // 3. 获得用户余额信息
        ReqMsg_User_UserBalanceQuery reqMsg = new ReqMsg_User_UserBalanceQuery();
        reqMsg.setUserId(userId);
        ResMsg_User_UserBalanceQuery resMsg = (ResMsg_User_UserBalanceQuery) regularService.handleMsg(reqMsg);

        // 4. 用户信息
        ReqMsg_User_InfoQuery user_InfoQuery = new ReqMsg_User_InfoQuery();
        user_InfoQuery.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery resMsg_User_InfoQuery = (ResMsg_User_InfoQuery)regularService.handleMsg(user_InfoQuery);

        //  5. 查询宝付支持网银支付的银行
//        ReqMsg_Bank_Pay19NetBankList reqMsgBank = new ReqMsg_Bank_Pay19NetBankList();
//        ResMsg_Bank_Pay19NetBankList resMsgBank = (ResMsg_Bank_Pay19NetBankList) regularService.handleMsg(reqMsgBank);

        model.put("propertyType", infoRes.getPropertyType());
        model.put("propertySymbol", infoRes.getPropertySymbol());
        model.put("productName", infoRes.getProductName());
        model.put("propertyType", infoRes.getPropertyType());
        // 单人投资限额
        model.put("maxInvestAmount", infoRes.getMaxInvestAmount());
        // 单笔投资限额
        model.put("maxSingleInvestAmount", infoRes.getMaxSingleInvestAmount());
        // 产品名称
        model.put("name", infoRes.getProductName());
        // 利率
        model.put("rate", infoRes.getRate());
        // 产品期限
        model.put("term", infoRes.getTrem());
        // 起投金额
        model.put("minInvestAmount", infoRes.getMinInvestAmount());
        // 资产合作方ZAN；YUN_DAI；7_DAI
        model.put("propertySymbol", infoRes.getPropertySymbol());
        // 支持红包
        model.put("isSupportRedPacket", infoRes.getIsSupportRedPacket());
        // 购买金额
        model.put("buyMoney", request.getParameter("money"));
        // 产品期限（已经换算成天的）
//        model.put("dayNum", request.getParameter("dayNum"));
        model.put("dayNum", this.getTerm4Day(infoRes.getTrem()));
        // 产品ID
        model.put("id", request.getParameter("id"));

        model.put("mobile", resMsg_User_InfoQuery.getMobile());
        model.put("userName", resMsg_User_InfoQuery.getUserName());
        model.put("isBindCard", resMsg_User_InfoQuery.getIsBindBank().equals(Constants.IS_BIND_BANK_YES) ? "TRUE" : "FALSE");
        model.put("depBalance", resMsg.getDepBalance());
        model.put("balance", resMsg.getAvailableBalance());
        model.put("isSupportInterestTicket", infoRes.getIsSupportInterestTicket());
//        model.put("netBankList", resMsgBank.getBankList());
        log.info("【购买详情页结束】");

        return "gen178/regular/buy_product_bind";

	}
    
	
	
    /**
     * 网银购买/充值提交
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param product
     * @return
     */
    @RequestMapping("/gen178/regular/netBankbuySubmit")
    @ResponseBody
    public Map<String, Object> netBankbuySubmit178(HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model,
                                   ReqMsg_RegularBuy_NetBankbuy product) {
    	
    	Map<String, Object> result = new HashMap<String, Object>();
        try {
            CookieManager manager = new CookieManager(request);
            String userId = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
            product.setUserId(Integer.parseInt(userId));
	            log.info("【页面提交购买】 ，userId = " + userId + ",  amount = " + product.getMoney()
	                     + ", productId = " + product.getProductId());
	        //钱报178网银购买标记
	        product.setFlag("qianbao178NetBankBuy");
            ResMsg_RegularBuy_NetBankbuy resProduct = (ResMsg_RegularBuy_NetBankbuy) regularService
                .handleMsg(product);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(resProduct.getResCode())) {
            	/*response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.print(resProduct.getRetHtml());
                log.info("form表单：【" + resProduct.getRetHtml() + "】");
                out.close();
                return null;*/
            	result.put("resCode", "000");
            	result.put("resHTML", resProduct.getRetHtml());
            	
            } else {
            	result.put("resCode", resProduct.getResCode());
            	result.put("resMsg", resProduct.getResMsg());
            }
        } catch (Exception e) {
                log.error("=========================购买定期理财产品交易提交失败==========================");
            e.printStackTrace();
            errorRes(result);
        }
        return result;
    }
    
    
    /**
     * 新用户购买提交(PC)--预下单
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param product
     * @return
     */
    @Token(save = true)
    @RequestMapping("gen178/regular/firstBuySubmitPro")
    @ResponseBody
    public Map<String, Object> firstBuySubmitPro178( HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model,
                                   ReqMsg_RegularBuy_Order req) {   
    	 Map<String, Object> result = new HashMap<String, Object>();
    	CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
    	//String serverToken = (String) request.getSession().getAttribute("token");
        String serverToken = Util.getServerToken(request, userId);
        if(StringUtil.isEmpty(serverToken)){
        	errorRes(result);
        	return result;
        }
        
        ReqMsg_Bank_QueryBankById reqMsg_Bank_QueryBankById = new ReqMsg_Bank_QueryBankById();
        reqMsg_Bank_QueryBankById.setBankId(req.getBankId());
        ResMsg_Bank_QueryBankById resMsg_Bank_QueryBankById = (ResMsg_Bank_QueryBankById)regularService.handleMsg(reqMsg_Bank_QueryBankById);
        req.setBankName(resMsg_Bank_QueryBankById.getName());
        req.setUserId(Integer.parseInt(userId));
        req.setIsBind(Constants.IS_BIND_BANK_NO);
        req.setTransType(Constants.USER_TRANS_TYPE_CARD);
        req.setTerminalType(2);
        req.setPlaceOrder(1);
       
       try {
        	 ResMsg_RegularBuy_Order resp = (ResMsg_RegularBuy_Order) regularService.handleMsg(req);
        	 result.put("orderNo", resp.getOrderNo());
        	 
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
            	result.put("resCode", "000");
            	result.put("resMsg", resp.getResMsg());
            	result.put("token", serverToken);
                result.put("htmlReapalString", resp.getHtml());
                if (resp.getHtml() !=null && !"fail".equals(resp.getHtml())) {
                	manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_BS_QIANBAO_USER.getName(),
                	            "ReapalQuickCMB", true);
                	manager.save(response, CookieEnums._SITE.getName(), null, "/",
                            5*365 * 24 * 3600, true);
				}
            } else {
            	result.put("resCode", resp.getResCode());
            	result.put("resMsg", resp.getResMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorRes(result);
        }
        return result;
    }
    
    
    /**
     * 新用户购买提交(PC)--正式下单
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param product
     * @return
     */
    @RequestMapping("gen178/regular/firstBuySubmit")
    @ResponseBody
    public Map<String, Object> firstBuySubmit178( HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model,
                                   ReqMsg_RegularBuy_Order req) {
        
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_Bank_QueryBankById reqMsg_Bank_QueryBankById = new ReqMsg_Bank_QueryBankById();
        reqMsg_Bank_QueryBankById.setBankId(req.getBankId());
        ResMsg_Bank_QueryBankById resMsg_Bank_QueryBankById = (ResMsg_Bank_QueryBankById)regularService.handleMsg(reqMsg_Bank_QueryBankById);
        req.setBankName(resMsg_Bank_QueryBankById.getName());
        req.setUserId(Integer.parseInt(userId));
        req.setIsBind(Constants.IS_BIND_BANK_NO);
        req.setTransType(Constants.USER_TRANS_TYPE_CARD);
        req.setTerminalType(2);
        req.setPlaceOrder(2);
       
       Map<String, Object> result = new HashMap<String, Object>();
       
       if(Util.isRepeatSubmit(request, response)){
       	result.put("resCode", "999");
          	result.put("resMsg","请勿重复提交订单！");
       }
       
      try {
    	  ResMsg_RegularBuy_Order resp = (ResMsg_RegularBuy_Order) regularService.handleMsg(req);
           if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
	           	result.put("resCode", "000");
	           	result.put("resMsg", resp.getResMsg());
           } else {
        		result.put("resCode", resp.getResCode());
	           	result.put("resMsg", resp.getResMsg());
           }
       } catch (Exception e) {
           e.printStackTrace();
           errorRes(result);
       }
       return result;
    }
    
    
    /**
     * 【购买】已绑卡用户（预下单）
     * 
     * @param request
     * @param response
     * @return
     */
    @Token(save = true)
    @ResponseBody
    @RequestMapping("/gen178/regular/pre_submit_bind")
    public Map<String, Object> preSubmitBind178(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        result.put("buyAmount", request.getParameter("buyAmount"));
        result.put("cardId", request.getParameter("cardId"));
        result.put("mobile", request.getParameter("mobile"));
        result.put("bankId", request.getParameter("bankId"));

        //String serverToken = (String) request.getSession().getAttribute("token");
        String serverToken = Util.getServerToken(request, userId);
        if(StringUtil.isEmpty(serverToken)){
        	errorRes(result);
        	return result;
        }
        String amount = request.getParameter("buyAmount");
        Double amount1 = Double.parseDouble(amount);
        
        // 用户基本信息
        
        ReqMsg_User_InfoQuery userInfoReqMsg = 
        		new ReqMsg_User_InfoQuery();
        userInfoReqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery userInfo = (ResMsg_User_InfoQuery)siteService.handleMsg(userInfoReqMsg);
        result.put("userName", userInfo.getUserName());
        result.put("idCard", userInfo.getIdCard());
        
        // 查询银行卡信息
        ReqMsg_Bank_QueryCardById req = new ReqMsg_Bank_QueryCardById();
        req.setCardId(Integer.parseInt(request.getParameter("cardId")));
        ResMsg_Bank_QueryCardById res = (ResMsg_Bank_QueryCardById)siteService.handleMsg(req);
        
        if (!userId.equals(String.valueOf(res.getUserId()))) {
        	result.put("resCode", "999");
           	result.put("resMsg", "用户无权限操作此银行卡");
           	return result;
		}
        
        // 预下单
        ReqMsg_RegularBuy_Order reqMsg = new ReqMsg_RegularBuy_Order();
        String redPacketId = request.getParameter("redPacketId");
        reqMsg.setRedPacketId(StringUtil.isBlank(redPacketId) ? null : Integer.parseInt(redPacketId));
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setAmount(amount1);
        reqMsg.setBankName(res.getBankName());
        reqMsg.setUserName(userInfo.getUserName());
        reqMsg.setCardNo(res.getCardNo());
        reqMsg.setIdCard(userInfo.getIdCard());
        reqMsg.setMobile(res.getMobile());
        reqMsg.setBankId(res.getBankId());
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setIsBind(Constants.IS_BIND_BANK_YES);
        reqMsg.setTransType(Constants.USER_TRANS_TYPE_CARD);
        reqMsg.setTerminalType(2);  // PC端
        reqMsg.setPlaceOrder(1);    // 预下单
        reqMsg.setProductId(Integer.parseInt(request.getParameter("productId")));
        try {
            ResMsg_RegularBuy_Order resp = (ResMsg_RegularBuy_Order)siteService.handleMsg(reqMsg);
            result.put("orderNo", resp.getOrderNo());
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
                result.put("htmlReapalString", resp.getHtml());
                if (resp.getHtml() !=null && !"fail".equals(resp.getHtml())) {
                	manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_BS_QIANBAO_USER.getName(),
                	            "ReapalQuickCMB", true);
                	manager.save(response, CookieEnums._SITE.getName(), null, "/",
                            5*365 * 24 * 3600, true);
				}
            	result.put("token", serverToken);
                successRes(result);
            } else {
        		result.put("resCode", resp.getResCode());
	           	result.put("resMsg", resp.getResMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorRes(result);
        }
        return result;
    }
    
    
    
    
    /**
     * 【购买】已绑卡用户。（确认下单）
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/gen178/regular/submit_bind")
    public Map<String, Object> submitBind178(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("buyAmount", request.getParameter("buyAmount"));
        result.put("cardId", request.getParameter("cardId"));
        result.put("mobile", request.getParameter("mobile"));
        result.put("mobileCode", request.getParameter("mobileCode"));
        String amount = request.getParameter("buyAmount");
        Double amount1 = Double.parseDouble(amount);
        
        if(Util.isRepeatSubmit(request, response)){
        	result.put("resCode", "999");
           	result.put("resMsg","请勿重复提交订单！");
        }
        
        // 用户基本信息
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_RegularBuy_Order reqMsg = new ReqMsg_RegularBuy_Order();
        ReqMsg_User_InfoQuery userInfoReqMsg = new ReqMsg_User_InfoQuery();
        userInfoReqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery userInfo = (ResMsg_User_InfoQuery)siteService.handleMsg(userInfoReqMsg);
        result.put("userName", userInfo.getUserName());
        result.put("idCard", userInfo.getIdCard());
        
        // 查询银行卡信息
        ReqMsg_Bank_QueryCardById req = new ReqMsg_Bank_QueryCardById();
        req.setCardId(Integer.parseInt(request.getParameter("cardId")));
        ResMsg_Bank_QueryCardById res = (ResMsg_Bank_QueryCardById)siteService.handleMsg(req);
        
        // 确认下单
        String redPacketId = request.getParameter("redPacketId");
        reqMsg.setRedPacketId(StringUtil.isBlank(redPacketId) ? null : Integer.parseInt(redPacketId));
        reqMsg.setUserName(userInfo.getUserName());
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setAmount(amount1);
        reqMsg.setBankName(res.getBankName());
        reqMsg.setCardNo(res.getCardNo());
        reqMsg.setIdCard(userInfo.getIdCard());
        reqMsg.setMobile(res.getMobile());
        reqMsg.setBankId(res.getBankId());
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setVerifyCode(request.getParameter("mobileCode"));
        reqMsg.setIsBind(Constants.IS_BIND_BANK_YES);
        reqMsg.setTransType(Constants.USER_TRANS_TYPE_CARD);
        reqMsg.setTerminalType(2);  // PC端
        reqMsg.setPlaceOrder(2);    // 下单
        reqMsg.setOrderNo(request.getParameter("orderNo"));
        reqMsg.setProductId(Integer.parseInt(request.getParameter("productId")));
        try {
            ResMsg_RegularBuy_Order resp = (ResMsg_RegularBuy_Order)siteService.handleMsg(reqMsg);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
                successRes(result);
            } else {
        		result.put("resCode", resp.getResCode());
	           	result.put("resMsg", resp.getResMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorRes(result);
        }
        return result;
    }
    
    
    /**
     * 【购买】购买成功页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/gen178/regular/regular_success")
    public String regularSuccess178(HttpServletRequest request, HttpServletResponse response) {
        return "/gen178/regular/regular_success";
    }
    
    /**
     * 【购买】购买失败页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/gen178/regular/error")
    public String regularError178(HttpServletRequest request, HttpServletResponse response) {
        return "/gen178/regular/error";
    }
    
    
    /**
     * 【购买】已绑卡用户余额购买
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/gen178/regular/balance_buy")
    public Map<String, Object> balanceBuy178(HttpServletRequest request, HttpServletResponse response) {
    	Map<String, Object> result = new HashMap<String, Object>();

        //重复提交
        if(Util.isRepeatSubmit(request, response)) {
            result.put("resCode", "999");
            result.put("resMsg", "购买失败：重复提交的请求！");
            return result;
        }

        result.put("buyMoney", request.getParameter("buyMoney"));
        result.put("cardNo", request.getParameter("cardNo"));
        result.put("bankId", request.getParameter("bankName"));
        result.put("mobile", request.getParameter("mobile"));
        result.put("mobileCode", request.getParameter("mobileCode"));
        result.put("productId", request.getParameter("productId"));
        result.put("redPacketId", request.getParameter("redPacketId"));
        
        String amount = request.getParameter("buyMoney");
        Double amount1 = Double.parseDouble(amount);
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        //校验验证码是否正确
        ReqMsg_User_ValidUser  reqMsg_User_ValidUser = new ReqMsg_User_ValidUser();
        reqMsg_User_ValidUser.setMobile(request.getParameter("mobile"));
        reqMsg_User_ValidUser.setMobileCode(request.getParameter("mobileCode"));
        ResMsg_User_ValidUser resMsg_User_Valid = (ResMsg_User_ValidUser)userService.handleMsg(reqMsg_User_ValidUser);
  		result.put("resCode", resMsg_User_Valid.getResCode());
        result.put("resMsg", resMsg_User_Valid.getResMsg());
       
        // 用户基本信息
        ReqMsg_User_InfoQuery userInfoReqMsg = new ReqMsg_User_InfoQuery();
        userInfoReqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_User_InfoQuery userInfoRes = (ResMsg_User_InfoQuery)siteService.handleMsg(userInfoReqMsg);
        result.put("userName", userInfoRes.getUserName());
        result.put("idCard", userInfoRes.getIdCard());
        
        ReqMsg_RegularBuy_BalanceBuy  reqMsg = new ReqMsg_RegularBuy_BalanceBuy();
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setAmount(amount1);
        reqMsg.setProductId(Integer.parseInt(request.getParameter("productId")));
        reqMsg.setTerminalType(2);
        reqMsg.setTicketId(StringUtils.isBlank(request.getParameter("redPacketId"))?null:Integer.parseInt(request.getParameter("redPacketId")));
        reqMsg.setTicketType(StringUtil.isBlank(request.getParameter("ticketType")) ? null: request.getParameter("ticketType"));
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg_User_Valid.getResCode())) {
        	  try {
              	ResMsg_RegularBuy_BalanceBuy resp = (ResMsg_RegularBuy_BalanceBuy)siteService.handleMsg(reqMsg);
                  if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
                      successRes(result);
                  } else {
                      result.put("resCode", resp.getResCode());
                      result.put("resMsg", resp.getResMsg());
                      Util.createToken(request, response);
                  }
              } catch (Exception e) {
                  e.printStackTrace();
                  errorRes(result);
              }
        } else {
            Util.createToken(request, response);
        }
        return result;
    }
    
    /**
     * 身份证验证
     * @param channel
     * @param request
     * @param response
     * @param idCard 身份证号码
     * @return
     */
    @ResponseBody
    @RequestMapping("{channel}/regular/bankCardIs")
    public Map<String, Object> bankCardIs(@PathVariable String channel,HttpServletRequest request, HttpServletResponse response,String idCard){
    	String str = IDCardUtil.IDCardValidate(idCard);
    	Integer resCode = null;
    	if(str.equalsIgnoreCase("YES")){
    		resCode = 1;
    	}else{
    		resCode = 2;
    	}
    	Map<String, Object> result = new HashMap<String, Object>();
    	result.put("resCode", resCode);
		return result;
    }
    
    
    /**
     * 融宝招行快捷支付重发验证码
     * @param request
     * @param response
     * @param orderNo 订单号
     * @return
     */
    @ResponseBody
    @RequestMapping("/regular/reapal/resendCode")
    public Map<String, Object> reapalResendCode(HttpServletRequest request, HttpServletResponse response,String orderNo){

    	Map<String, Object> result = new HashMap<String, Object>();
    	ReqMsg_RegularBuy_ResendCode req = new   ReqMsg_RegularBuy_ResendCode();
    	req.setOrderNo(orderNo);
        try {
      	  ResMsg_RegularBuy_ResendCode resp = (ResMsg_RegularBuy_ResendCode) regularService.handleMsg(req);
             if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
  	           	result.put("resCode", "000");
  	           	result.put("resMsg", resp.getResMsg());
             } else {
          		result.put("resCode", resp.getResCode());
  	           	result.put("resMsg", resp.getResMsg());
             }
         } catch (Exception e) {
             e.printStackTrace();
             errorRes(result);
         }
		return result;
    }
    
    
    
    /**
     * 【融宝招行快捷支付】购买失败页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/micro2.0/regular/reapal_quick_cmb_fail")
    public String reapalQuickCMBFail(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
    	String reapalMessage = request.getParameter("reapalMessage");
    	String qianbao = request.getParameter("qianbao");
    	model.put("reapalMessage", reapalMessage);
    	model.put("qianbao", qianbao);
        return "/micro2.0/regular/reapal_quick_cmb_fail";
    }
    
    /**
     * 【融宝招行快捷支付】购买失败页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/gen2.0/regular/reapal_quick_cmb_fail")
    public String reapalQuickCMBFailGen(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
    	String reapalMessage = request.getParameter("reapalMessage");
    	model.put("reapalMessage", reapalMessage);
        return "/gen2.0/regular/reapal_quick_cmb_fail";
    }
    /**
     * 【融宝招行快捷支付】购买失败页面
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/gen178/regular/reapal_quick_cmb_fail")
    public String reapalQuickCMBFailGen178(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
    	String reapalMessage = request.getParameter("reapalMessage");
    	model.put("reapalMessage", reapalMessage);
        return "/gen178/regular/reapal_quick_cmb_fail";
    }
    
    
    /**
     * 融宝用户招行快捷购买	
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/regular/reapalQuickCMB")
    public String reapalQuickCMB(HttpServletRequest request, HttpServletResponse response,Map<String,Object> model){
    	
    	CookieManager manager = new CookieManager(request);
        String qianbaoUser = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_BS_QIANBAO_USER.getName(), true);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
        //qianbaoUser = "ReapalQuickCMB";
        
        DESUtil des = new DESUtil("cfgubijn");
/*        String stra = "source=pc&flag=success&orderNo=201601231137531969019022188";
        String urlString = des.encryptStr(stra);*/
    	//解密参数字符串
    	String urlString = request.getQueryString();  //获取参数加密部分
    	String urlPlaintext = des.decryptStr(urlString);
    	//参数字符串明文切割
    	String[]  plaintext = urlPlaintext.split("&");
    	Map<String, String> plaintextMap = new HashMap<String, String>();
    	for (String string : plaintext) {
			String[] str1 = string.split("=");
			plaintextMap.put(str1[0], str1[1]);
		}
    	
    	//提取参数
    	String flag = plaintextMap.get("flag");
    	String source = plaintextMap.get("source");
    	String orderNo = plaintextMap.get("orderNo");
    	String productId = plaintextMap.get("pid");
    	
    	String microUrl = GlobEnv.get("server.web");
    	String genUrl = GlobEnv.get("gen.server.web");
    	if ("h5".equals(source)) {     

			return "redirect:"+microUrl+"/regular/reapalQuickCMBbuy?"+urlString;
    		
		}else {

            if ("ReapalQuickCMB".equals(qianbaoUser)) {
            	return "redirect:"+genUrl+"/regular/reapalQuickCMBbuy?"+urlString;
			}else {
				return "redirect:"+genUrl+"/regular/reapalQuickCMBbuy?"+urlString;
			}
			
		}
    }
    
    /**
     * 融宝用户招行快捷购买	
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/regular/reapalQuickCMBbuy")
    public String reapalQuickCMBbuy(HttpServletRequest request, HttpServletResponse response,Map<String,Object> model){
    	
    	CookieManager manager = new CookieManager(request);
        String qianbaoUser = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_BS_QIANBAO_USER.getName(), true);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
        //qianbaoUser = "ReapalQuickCMB";
        
        DESUtil des = new DESUtil("cfgubijn");
/*        String stra = "source=pc&flag=success&orderNo=201601231137531969019022188";
        String urlString = des.encryptStr(stra);*/
    	//解密参数字符串
    	String urlString = request.getQueryString();  //获取参数加密部分
    	String urlPlaintext = des.decryptStr(urlString);
    	//参数字符串明文切割
    	String[]  plaintext = urlPlaintext.split("&");
    	Map<String, String> plaintextMap = new HashMap<String, String>();
    	for (String string : plaintext) {
			String[] str1 = string.split("=");
			plaintextMap.put(str1[0], str1[1]);
		}
    	
    	//提取参数
    	String flag = plaintextMap.get("flag");
    	String source = plaintextMap.get("source");
    	String orderNo = plaintextMap.get("orderNo");
    	String productId = plaintextMap.get("pid");
    	
    	if (flag != null && "success".equals(flag)) {

        	if (source == null || orderNo ==null) {
        	model.put("reapalMessage", "银行鉴权失败");
        		if ("h5".equals(source)) {     
        			// 钱报的参数
        	    	String link = GlobEnv.getWebURL("/micro2.0/index");
        			if ("ReapalQuickCMB".equals(qianbaoUser)) {
        				model.put("qianbao", Constants.USER_AGENT_QIANBAO);
        				link += "?qianbao=qianbao";
        				String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
        		        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
        		        	link += "&agentViewFlag="+viewAgentFlagCookie;
        		        }
        			}
        			 // 分享
        	        Map<String,String> sign = weChatUtil.createAuth(request);
        	        sign.put("link", link);
        	        model.put("weichat", sign);
        			return "micro2.0/regular/reapal_quick_cmb_fail";
            		
    			}else {

    	            if ("ReapalQuickCMB".equals(qianbaoUser)) {
    	            	return "gen178/regular/reapal_quick_cmb_fail";
					}else {
						return "gen2.0/regular/reapal_quick_cmb_fail";
					}
    				
    			}
			}
        	ReqMsg_RegularBuy_ReapalQuickCMB  reqMsg = new ReqMsg_RegularBuy_ReapalQuickCMB();
        	reqMsg.setOrderNo(orderNo);
        	if (productId !=null && !"".equals(productId)) {
        		reqMsg.setProductId(Integer.parseInt(productId));
			}
        	
        	ResMsg_RegularBuy_ReapalQuickCMB resp = (ResMsg_RegularBuy_ReapalQuickCMB)siteService.handleMsg(reqMsg);
        	if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
               
        		if (productId !=null && !"".equals(productId)) {
        			resp.setTransType(Constants.USER_TRANS_TYPE_CARD);
				}else {
					resp.setTransType(Constants.USER_TRANS_TYPE_RECHANGE);
				}
        		resp.setOrderNo(orderNo);
        		

        		
        		if ("h5".equals(source)) { 
        			resp.setTerminalType(1);
        			model.put("resp", resp);
        			
        			// 钱报的参数
        	    	String link = GlobEnv.getWebURL("/micro2.0/index");
        			if ("ReapalQuickCMB".equals(qianbaoUser)) {
        				model.put("qianbao", Constants.USER_AGENT_QIANBAO);
        				link += "?qianbao=qianbao";
        				String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
        		        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
        		        	link += "&agentViewFlag="+viewAgentFlagCookie;
        		        }
        			}
        			 // 分享
        	        Map<String,String> sign = weChatUtil.createAuth(request);
        	        sign.put("link", link);
        	        model.put("weichat", sign);
        	        

        			return "micro2.0/regular/reapal_quick_cmb_success";
            		
    			}else {
    				resp.setTerminalType(2);
    				model.put("resp", resp);
    	            if ("ReapalQuickCMB".equals(qianbaoUser)) {
    	            	return "gen178/regular/reapal_quick_cmb_success";
					}else {
						return "gen2.0/regular/reapal_quick_cmb_success";
					}
    				
    			}
            } else {
            	model.put("reapalMessage", resp.getResMsg());
        		if ("h5".equals(source)) {     
        			
        			// 钱报的参数
        	    	String link = GlobEnv.getWebURL("/micro2.0/index");
        			if ("ReapalQuickCMB".equals(qianbaoUser)) {
        				model.put("qianbao", Constants.USER_AGENT_QIANBAO);
        				link += "?qianbao=qianbao";
        				String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
        		        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
        		        	link += "&agentViewFlag="+viewAgentFlagCookie;
        		        }
        			}
        			 // 分享
        	        Map<String,String> sign = weChatUtil.createAuth(request);
        	        sign.put("link", link);
        	        model.put("weichat", sign);
        			
        			
        			return "micro2.0/regular/reapal_quick_cmb_fail";
            		
    			}else {

    	            if ("ReapalQuickCMB".equals(qianbaoUser)) {
    	            	return "gen178/regular/reapal_quick_cmb_fail";
					}else {
						return "gen2.0/regular/reapal_quick_cmb_fail";
					}
    				
    			}
            }
        	
		}else {
			model.put("reapalMessage", "银行鉴权失败");
			if ("h5".equals(source)) {      

    			// 钱报的参数
    	    	String link = GlobEnv.getWebURL("/micro2.0/index");
    			if ("ReapalQuickCMB".equals(qianbaoUser)) {
    				model.put("qianbao", Constants.USER_AGENT_QIANBAO);
    				link += "?qianbao=qianbao";
    				String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
    		        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
    		        	link += "&agentViewFlag="+viewAgentFlagCookie;
    		        }
    			}
    			 // 分享
    	        Map<String,String> sign = weChatUtil.createAuth(request);
    	        sign.put("link", link);
    	        model.put("weichat", sign);
    			
    			return "micro2.0/regular/reapal_quick_cmb_fail";
        		
			}else {

	            if ("ReapalQuickCMB".equals(qianbaoUser)) {
	            	return "gen178/regular/reapal_quick_cmb_fail";
				}else {
					return "gen2.0/regular/reapal_quick_cmb_fail";
				}
				
			}
		}
    }
    
    /**
     * 融宝招行快捷新用户购买或者充值提交--正式下单
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param product
     * @return
     */
    @RequestMapping("{channel}/regular/firstBuySubmitReapalQuickCMB")
    @ResponseBody
    public Map<String, Object> firstBuySubmitReapalQuickCMB(@PathVariable String channel,HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model,
                                   ReqMsg_RegularBuy_Order req) {
    	  Map<String, Object> result = new HashMap<String, Object>();
    	//重复提交
		if(Util.isRepeatSubmit(request, response)) {
			result.put("resCode", "999");
			result.put("resMsg", "购买失败：重复提交的请求！");
			return result;
		}
    	
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        
        ReqMsg_Bank_QueryBankById reqMsg_Bank_QueryBankById = new ReqMsg_Bank_QueryBankById();
        reqMsg_Bank_QueryBankById.setBankId(req.getBankId());
        ResMsg_Bank_QueryBankById resMsg_Bank_QueryBankById = (ResMsg_Bank_QueryBankById)regularService.handleMsg(reqMsg_Bank_QueryBankById);
        req.setBankName(resMsg_Bank_QueryBankById.getName());
        req.setUserId(Integer.parseInt(userId));
       
     
       
      try {
    	  ResMsg_RegularBuy_Order resp = (ResMsg_RegularBuy_Order) regularService.handleMsg(req);
           if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
	           	result.put("resCode", "000");
	           	result.put("resMsg", resp.getResMsg());
           } else {
        		result.put("resCode", resp.getResCode());
	           	result.put("resMsg", resp.getResMsg());
           }
       } catch (Exception e) {
           e.printStackTrace();
           errorRes(result);
       }
       return result;
    }
    
    /**
     * 新手校验
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param productId
     * @return
     */
    @RequestMapping("{channel}/regular/newBuyerCheck")
    @ResponseBody
    public Map<String, Object> newBuyerCheck(@PathVariable String channel,HttpServletRequest request,
            HttpServletResponse response, Map<String, Object> model,String productId){
    	Map<String, Object> result = new HashMap<String, Object>();
    	try {
    		CookieManager manager = new CookieManager(request);
    		String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
            if(StringUtils.isBlank(userId)){
            	result.put("resCode", "noLogin");
            	return result;
            }
    		
    		
    		if(StringUtils.isNotBlank(productId)){
    			//查询本产品信息,判断是否是新手标
    	  		ReqMsg_Product_InfoQuery req = new ReqMsg_Product_InfoQuery();
    	  		req.setId(Integer.valueOf(request.getParameter("productId")));
    	  		ResMsg_Product_InfoQuery infoRes = (ResMsg_Product_InfoQuery) siteService.handleMsg(req);
    	  		if(Constants.PRODUCT_ACTIVITY_TYPE_NEW_BUYER.equals(infoRes.getActivityType())){
    	  			//判断是否是新手用户
    	  			ReqMsg_PayOrders_GetSuccessPayOrders reqOrder = new ReqMsg_PayOrders_GetSuccessPayOrders();
    	  			reqOrder.setUserId(Integer.valueOf(userId));
    	  			ResMsg_PayOrders_GetSuccessPayOrders resOrder = (ResMsg_PayOrders_GetSuccessPayOrders)siteService.handleMsg(reqOrder);
    	  			if(resOrder.getBuyType() != null){
    	  				result.put("resCode", resOrder.getBuyType());
    	            	return result;
    	  			}else{
    	  				return result;
    	  			}
    	  		}else{
        			return result;
    	  		}
    		}
		} catch (Exception e) {
			
		}
    	return result;
    }
    
    /**
     * 老用户专享标的购买资格校验
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param productId
     * @return
     */
    @RequestMapping("{channel}/regular/oldBuyerCheck")
    @ResponseBody
    public Map<String, Object> oldBuyerCheck(@PathVariable String channel,HttpServletRequest request,
            HttpServletResponse response, Map<String, Object> model,String productId){
    	Map<String, Object> result = new HashMap<String, Object>();
    	try {
    		CookieManager manager = new CookieManager(request);
    		String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
            if(StringUtils.isBlank(userId)){
            	result.put("resCode", "noLogin");
            	return result;
            }
    		
    		
    		if(StringUtils.isNotBlank(productId)){
    		    
    			//查询本产品信息,判断是否是老用户专享标
    	  		ReqMsg_Product_InfoQuery req = new ReqMsg_Product_InfoQuery();
    	  		req.setId(Integer.valueOf(request.getParameter("productId")));
    	  		ResMsg_Product_InfoQuery infoRes = (ResMsg_Product_InfoQuery) siteService.handleMsg(req);
    	  		if(infoRes.getProductName().contains(Constants.ACTIVITY_SPRING_PRODUCT_NAME)){
    	  			//判断是否是老用户
    	  			ReqMsg_PayOrders_GetSuccessPayOrders reqOrder = new ReqMsg_PayOrders_GetSuccessPayOrders();
    	  			reqOrder.setUserId(Integer.valueOf(userId));
    	  			ResMsg_PayOrders_GetSuccessPayOrders resOrder = (ResMsg_PayOrders_GetSuccessPayOrders)siteService.handleMsg(reqOrder);
    	  			if(resOrder.getBuyType() != null && (resOrder.getBuyType() == 5 || resOrder.getBuyType() == 6)){
    	            	return result;
    	  			}else{
    	  				result.put("resCode", "not_old");
    	  				return result;
    	  			}
    	  		}else{
        			return result;
    	  		}
    		}
		} catch (Exception e) {
			
		}
    	return result;
    }
    
    /**
     * 校验是否有可用红包
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param productId
     * @return
     */
    @RequestMapping("{channel}/regular/redPacketCheck")
    @ResponseBody
    public Map<String, Object> redPacketCheck(@PathVariable String channel,HttpServletRequest request,
            HttpServletResponse response, Map<String, Object> model,
            String productId) {
    	Map<String, Object> result = new HashMap<String, Object>();
    	try {
    		String userId = new CookieManager(request).getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);

    		ReqMsg_RedPacketInfo_GetRedPacket reqRedPacket = new ReqMsg_RedPacketInfo_GetRedPacket();
    		reqRedPacket.setUserId(Integer.parseInt(userId));
    		reqRedPacket.setAmount(Double.valueOf(request.getParameter("money")));
    		reqRedPacket.setStatus(Constants.RED_PACKET_STATUS_INIT);
    		reqRedPacket.setProductId(Integer.parseInt(request.getParameter("productId")));
    		ResMsg_RedPacketInfo_GetRedPacket resRedPacket = (ResMsg_RedPacketInfo_GetRedPacket)regularService.handleMsg(reqRedPacket);
    		if (!CollectionUtils.isEmpty(resRedPacket.getDataGrid())) {
    			result.put("redPacketId", resRedPacket.getDataGrid().get(0).get("id"));
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return result;
    }
    /**
     * 产品未开始的短信提醒添加
     * 不做登录拦截，此处判断是否登录并返回结果
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param req
     * @return
     */
    @RequestMapping("{channel}/regular/product_AddInform")
    @ResponseBody
    public Map<String, Object> product_AddInform(@PathVariable String channel,HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model,
                                   String productId) {
    	Map<String, Object> result = new HashMap<String, Object>();
    	try {
    		String userId = request.getParameter("userReturnId");
            CookieManager manager = new CookieManager(request);
    		if(StringUtils.isBlank(userId)){
                userId = manager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_USER_ID.getName(), true);
                if(StringUtils.isBlank(userId)){
                	result.put("resCode", "noLogin");
        			return result;
                }
    		}
            String mobile = manager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_MOBILE_NUM.getName(), true);
    		
    		if(StringUtils.isNotBlank(productId)){
    			if("micro2.0".equals(channel)) {
	    			//查询本产品信息,判断是否是新手标
	    	  		ReqMsg_Product_InfoQuery reqPro = new ReqMsg_Product_InfoQuery();
	    	  		reqPro.setId(Integer.valueOf(request.getParameter("productId")));
	    	  		ResMsg_Product_InfoQuery infoRes = (ResMsg_Product_InfoQuery) siteService.handleMsg(reqPro);
	    	  		if(Constants.PRODUCT_ACTIVITY_TYPE_NEW_BUYER.equals(infoRes.getActivityType())){
	    	  			//判断是否是新手用户
	    	  			ReqMsg_PayOrders_GetSuccessPayOrders reqOrder = new ReqMsg_PayOrders_GetSuccessPayOrders();
	    	  			reqOrder.setUserId(Integer.valueOf(userId));
	    	  			ResMsg_PayOrders_GetSuccessPayOrders resOrder = (ResMsg_PayOrders_GetSuccessPayOrders)siteService.handleMsg(reqOrder);
	    	  			if(resOrder.getBuyType() != null){
	    	  				result.put("resCode", resOrder.getBuyType());
	    	            	return result;
	    	  			}
	    	  		}
    			}
        		ReqMsg_Product_AddInform req = new ReqMsg_Product_AddInform();
        		req.setUserId(Integer.parseInt(userId));
        		req.setProductId(Integer.parseInt(productId));
        		ResMsg_Product_AddInform res = (ResMsg_Product_AddInform) regularService.handleMsg(req);
        		if("000000".equals(res.getResCode())){
        			
        			if(StringUtils.isNotBlank(res.getTime())){
        				result.put("resCode", "000");
        				mobile = mobile.substring(0,mobile.length()-(mobile.substring(3)).length())+"****"+mobile.substring(7);
            			result.put("mobile", mobile);
            			result.put("time", res.getTime());
        			}else{
        				result.put("resCode", "999");
        			}
        			
        		}else{
        			result.put("resCode", res.getResCode());
        		}
        	}else{
        		result.put("resCode", "999");
        	}
    		
		} catch (Exception e) {
			e.printStackTrace();
			
		}
    	return result;
    }
    
    
    /**
     * 【产品详情】查看合同或合作协议
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/micro2.0/regular/protocol")
    public String protocol(HttpServletRequest request, HttpServletResponse response,String productId,String protocolType,String resourceType, Map<String, Object> model) {
    	
    	
		//新查询产品数据信息
        String resources = GlobEnv.get("news.resources");
        String manageWeb = GlobEnv.get("manage.web");
        String web = GlobEnv.get("gen.server.web");
    	
		ReqMsg_Product_ProductDetailInfoQuery reqMsg_Product_ProductDetailInfoQuery = new ReqMsg_Product_ProductDetailInfoQuery();
		reqMsg_Product_ProductDetailInfoQuery.setProductId(Integer.parseInt(productId));
		ResMsg_Product_ProductDetailInfoQuery resMsg_Product_ProductDetailInfoQuery = (ResMsg_Product_ProductDetailInfoQuery) regularService.handleMsg(reqMsg_Product_ProductDetailInfoQuery);
		resMsg_Product_ProductDetailInfoQuery.setNote(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getNote(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setPropertySummary(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getPropertySummary(), resources, manageWeb, web));
		
		resMsg_Product_ProductDetailInfoQuery.setReturnSource(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getReturnSource(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setFundSecurity(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getFundSecurity(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setOrgnizeCheck(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getOrgnizeCheck(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setRatingGrade(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getRatingGrade(), resources, manageWeb, web));

    	if ("coop".equals(protocolType)) {
    		model.put("protocolType", "合作协议");
    		if (resMsg_Product_ProductDetailInfoQuery.getCoopProtocolPics() !=null) {
    			String[] imgs = resMsg_Product_ProductDetailInfoQuery.getCoopProtocolPics().split(";");
    	    	model.put("imgs", imgs);
    		}
	    	
		}else if ("loan".equals(protocolType)) {
			model.put("protocolType", "借款合同示例");
			if (resMsg_Product_ProductDetailInfoQuery.getLoanProtocolPics() !=null) {
		    	String[] imgs = resMsg_Product_ProductDetailInfoQuery.getLoanProtocolPics().split(";");
		    	model.put("imgs", imgs);
			}

		}else if ("orgnize".equals(protocolType)) {
			model.put("protocolType", "合作方信息披露");
			if (resMsg_Product_ProductDetailInfoQuery.getOrgnizeCheckPics() !=null) {
		    	String[] imgs = resMsg_Product_ProductDetailInfoQuery.getOrgnizeCheckPics().split(";");
		    	model.put("imgs", imgs);
			}
			model.put("content", resMsg_Product_ProductDetailInfoQuery.getOrgnizeCheck());
			
		}else if ("rating".equals(protocolType)) {
			model.put("protocolType", "第三方担保合同");
			if (resMsg_Product_ProductDetailInfoQuery.getRatingGradePics() !=null) {
		    	String[] imgs = resMsg_Product_ProductDetailInfoQuery.getRatingGradePics().split(";");
		    	model.put("imgs", imgs);
			}
			model.put("content", resMsg_Product_ProductDetailInfoQuery.getRatingGrade());

		}else {
			model.put("protocolType", "币港湾");
		}
    	model.put("resourceType", resourceType);
        return "/micro2.0/regular/regular_product_index_protocol";
    }
    
    @RequestMapping("{channel}/regular/quickpayBankDetail")
	public String quickpayBankDetail(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
			Map<String,Object> model) {
        String qianbao = request.getParameter("qianbao");
		
		//查询19付支持快捷支付的银行
		ReqMsg_Bank_Pay19BankList reqMsg = new ReqMsg_Bank_Pay19BankList();
		CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
        if (!"".equals(userId)) {
        	reqMsg.setUserId(Integer.parseInt(userId));
		}
		ResMsg_Bank_Pay19BankList resMsg = (ResMsg_Bank_Pay19BankList) regularService.handleMsg(reqMsg);
		model.put("bankList", resMsg.getBankList());
		
		// 钱报的参数
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
        }
       
		return channel + "/regular/quickpay_bank_detail";
	}
    
    
    
    /**
     * 
     * @Title: regularExplain 
     * @Description: APP调用产品详情页面——IOS
     * @param request
     * @param response
     * @param model
     * @return String
     * @throws
     */
    @RequestMapping("app/regular/index")
	public String regularIndexApp(HttpServletRequest request, HttpServletResponse response,
			Map<String,Object> model) {
        log.info("【产品详情页开始】");
    	String pageNum = request.getParameter("pageNum");
		String id = request.getParameter("id");
		
		String term = request.getParameter("term");
		String rate = request.getParameter("rate");
		String name = request.getParameter("name");
		String minInvestAmount = request.getParameter("minInvestAmount");
		String userType = request.getParameter("userType");
        String client = request.getParameter("client");
		if("PROMOT".equals(userType)) {
			String beforeRate = new DecimalFormat("0.0").format(MoneyUtil.subtract(rate, "1.0"));
			model.put("beforeRate", beforeRate);
		}
		model.put("rate", rate);
		model.put("id", id);
		model.put("term", term);
		model.put("name", name);
		model.put("minInvestAmount", minInvestAmount);
		model.put("userType", userType);
        model.put("client", client);
		
		//新查询产品数据信息
        String resources = GlobEnv.get("news.resources");
        String manageWeb = GlobEnv.get("manage.web");
        String web = GlobEnv.get("gen.server.web");
		
		ReqMsg_Product_ProductDetailInfoQuery reqMsg_Product_ProductDetailInfoQuery = new ReqMsg_Product_ProductDetailInfoQuery();
		reqMsg_Product_ProductDetailInfoQuery.setPageNum(pageNum==null?1:Integer.parseInt(pageNum));
		reqMsg_Product_ProductDetailInfoQuery.setProductId(Integer.parseInt(id));
		ResMsg_Product_ProductDetailInfoQuery resMsg_Product_ProductDetailInfoQuery = (ResMsg_Product_ProductDetailInfoQuery) regularService.handleMsg(reqMsg_Product_ProductDetailInfoQuery);
		resMsg_Product_ProductDetailInfoQuery.setNote(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getNote(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setPropertySummary(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getPropertySummary(), resources, manageWeb, web));
		
		resMsg_Product_ProductDetailInfoQuery.setReturnSource(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getReturnSource(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setFundSecurity(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getFundSecurity(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setOrgnizeCheck(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getOrgnizeCheck(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setRatingGrade(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getRatingGrade(), resources, manageWeb, web));
		
		model.put("pageNum", reqMsg_Product_ProductDetailInfoQuery.getPageNum());
		model.put("totalCount",  (int) Math.ceil((double) resMsg_Product_ProductDetailInfoQuery.getTotalRows() / reqMsg_Product_ProductDetailInfoQuery.getNumPerPage()));
		model.put("productDetail", resMsg_Product_ProductDetailInfoQuery);
		model.put("investRecord", resMsg_Product_ProductDetailInfoQuery.getInvestRecordList());
		
		model.put("source", "product_detail");
        log.info("【产品详情页结束】");
		return "micro2.0/regular/regular_product_index_app";
	}
    
    
    
    /**
     * 
     * @Title: regularExplain 
     * @Description: APP调用产品详情页面_安卓
     * @param request
     * @param response
     * @param model
     * @return String
     * @throws
     */
    @RequestMapping("app_android/regular/index")
	public String regularIndexAppAndroid(HttpServletRequest request, HttpServletResponse response,
			Map<String,Object> model) {
        log.info("【产品详情页开始】");
    	String pageNum = request.getParameter("pageNum");
		String id = request.getParameter("id");
		model.put("id", id);
		

		

		//新查询产品数据信息
        String resources = GlobEnv.get("news.resources");
        String manageWeb = GlobEnv.get("manage.web");
        String web = GlobEnv.get("gen.server.web");
		
		ReqMsg_Product_ProductDetailInfoQuery reqMsg_Product_ProductDetailInfoQuery = new ReqMsg_Product_ProductDetailInfoQuery();
		reqMsg_Product_ProductDetailInfoQuery.setPageNum(pageNum==null?1:Integer.parseInt(pageNum));
		reqMsg_Product_ProductDetailInfoQuery.setProductId(Integer.parseInt(id));
		ResMsg_Product_ProductDetailInfoQuery resMsg_Product_ProductDetailInfoQuery = (ResMsg_Product_ProductDetailInfoQuery) regularService.handleMsg(reqMsg_Product_ProductDetailInfoQuery);
		resMsg_Product_ProductDetailInfoQuery.setNote(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getNote(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setPropertySummary(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getPropertySummary(), resources, manageWeb, web));
		
		resMsg_Product_ProductDetailInfoQuery.setReturnSource(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getReturnSource(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setFundSecurity(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getFundSecurity(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setOrgnizeCheck(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getOrgnizeCheck(), resources, manageWeb, web));
		resMsg_Product_ProductDetailInfoQuery.setRatingGrade(EditorUtil.replace(resMsg_Product_ProductDetailInfoQuery.getRatingGrade(), resources, manageWeb, web));
		
		model.put("now", new Date());
		model.put("pageNum", reqMsg_Product_ProductDetailInfoQuery.getPageNum());
		model.put("totalCount",  (int) Math.ceil((double) resMsg_Product_ProductDetailInfoQuery.getTotalRows() / reqMsg_Product_ProductDetailInfoQuery.getNumPerPage()));
		model.put("productDetail", resMsg_Product_ProductDetailInfoQuery);
		model.put("investRecord", resMsg_Product_ProductDetailInfoQuery.getInvestRecordList());
		
		model.put("source", "product_detail");
        log.info("【产品详情页结束】");
		return "micro2.0/regular/regular_product_index_app_android";
	}
    
    
    /**
     * 定期理财购买协议--PDF
     * @param channel
     * @param req
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/regular/buyagreementPdf")
    public String buyagreementPdf(@PathVariable String channel, ReqMsg_User_InfoQuery req, 
            HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> model,String investId) {

        //根据subAccountId查询
			ReqMsg_Account_SubAccountById reqAccount = new ReqMsg_Account_SubAccountById();
			try {
			    reqAccount.setId(Integer.parseInt(investId));
			    model.put("financialNo", investId);
	        } catch (Exception e) {
	        	e.printStackTrace();
				throw e;
	        }
			ResMsg_Account_SubAccountById resAccount = (ResMsg_Account_SubAccountById) regularService.handleMsg(reqAccount);
			
			//查询产品信息
			ReqMsg_Product_InfoQuery reqMsg = new ReqMsg_Product_InfoQuery();
			if(null == resAccount.getProductId()){
				throw new PTException("查询不到对应产品信息");
			}
			reqMsg.setId(resAccount.getProductId());
			ResMsg_Product_InfoQuery resMsg1 = (ResMsg_Product_InfoQuery) regularService
			.handleMsg(reqMsg);
			if (null == resMsg1.getPropertySymbol()) {
				throw new PTException("查询不到对应资金接收方信息");
			}
			

			//投资期限转换
			int term = resMsg1.getTrem();
			int term4Day;
			if(term < 0){
				term4Day = Math.abs(term);
			}else if(term == 12){
				term4Day = 365;
			}else{
				term4Day = term*30;
			}

			String url = "";
			if (Constants.PROPERTY_SYMBOL_ZAN.equals(resMsg1.getPropertySymbol())) {

				model.put("openBalance", MoneyUtil.format(resAccount.getOpenBalance()));
				model.put("openBalanceDigitUpper", MoneyUtil.digitUppercase(resAccount.getOpenBalance()));
				Double receiptsBalance = resAccount.getOpenBalance()*term4Day/365;

				model.put("receiptsBalance",MoneyUtil.format(receiptsBalance) );
				model.put("receiptsBalanceDigitUpper", MoneyUtil.digitUppercase(receiptsBalance));
				url = channel + "/regular/financial_have_buy_new_pdf_zan";
			}else if (Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(resMsg1.getPropertySymbol())
                    || Constants.PROPERTY_SYMBOL_YUN_DAI.equals(resMsg1.getPropertySymbol())
                    || Constants.PROPERTY_SYMBOL_FREE.equals(resMsg1.getPropertySymbol())) {

				model.put("secondPartyName", "达飞云贷科技（北京）有限公司");
				model.put("secondPartyAddress", "北京市朝阳区慈云寺北里118号苏宁中心11层");

                //出借金额
                model.put("openBalance", resAccount.getOpenBalance());

                //购买的云贷、7贷理财产品提前终止违约金百分比查询
                ReqMsg_AgreementVersion_QueryBerachContractRate berachContractRateReq = new ReqMsg_AgreementVersion_QueryBerachContractRate();
                ResMsg_AgreementVersion_QueryBerachContractRate berachContractRateRes =
                        (ResMsg_AgreementVersion_QueryBerachContractRate) siteService.handleMsg(berachContractRateReq);
                model.put("berachContractRate", berachContractRateRes.getBerachContractRate());
                url = channel + "/regular/power_attorney_buy_pdf";

			}else if (Constants.PROPERTY_SYMBOL_ZSD.equals(resMsg1.getPropertySymbol())) {
				
                //出借金额
                model.put("openBalance", resAccount.getOpenBalance());

				url = channel + "/regular/zsd_lending_advice_services_pdf";

			}else if (Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(resMsg1.getPropertySymbol())
                     || Constants.PROPERTY_SYMBOL_7_DAI.equals(resMsg1.getPropertySymbol())) {

                //出借金额
                model.put("openBalance", resAccount.getOpenBalance());

                //购买的云贷、7贷理财产品提前终止违约金百分比查询
                ReqMsg_AgreementVersion_QueryBerachContractRate berachContractRateReq = new ReqMsg_AgreementVersion_QueryBerachContractRate();
                ResMsg_AgreementVersion_QueryBerachContractRate berachContractRateRes =
                        (ResMsg_AgreementVersion_QueryBerachContractRate) siteService.handleMsg(berachContractRateReq);
                model.put("berachContractRate", berachContractRateRes.getBerachContractRate());
                url = channel + "/regular/power_attorney_buy_pdf";

            }else {
				throw new PTException("资金接收方信息有误");
			}


			//查询用户信息
			if(null == resAccount.getUserId()){
				throw new PTException("查询不到对应用户信息");
			}
			req.setUserId(resAccount.getUserId());
			ResMsg_User_InfoQuery resMsg = (ResMsg_User_InfoQuery) regularService.handleMsg(req);
			model.put("mobile", resMsg.getMobile());
			if ("".equals(resMsg.getIdCard())) {
				model.put("IdCard", "未实名认证 ");
			}else {
				model.put("IdCard", resMsg.getIdCard());
			}
			
			model.put("userName", resMsg.getUserName());
			
			

			model.put("term", term);


			model.put("dayNum", term4Day);
			model.put("rate", resMsg1.getRate());
			model.put("name", resMsg1.getProductName());
			model.put("amount", MoneyUtil.format(resAccount.getBalance()));
			model.put("amountDigitUpper", MoneyUtil.digitUppercase(resAccount.getBalance()));
			
			
			
			ReqMsg_Product_BuyAgreement reqMsgPB = new ReqMsg_Product_BuyAgreement();
	        reqMsgPB.setProductId(resAccount.getProductId());
	        reqMsgPB.setUserId(resAccount.getUserId());
	        ResMsg_Product_BuyAgreement resPB = (ResMsg_Product_BuyAgreement) siteService.handleMsg(reqMsgPB);
	        if(resPB.getIsSelf() == false) {
	        	throw new PTException("当前产品和用户信息不对应");
	        }
	        
	        
			//interestBeginDate 起息日
			if (resAccount.getInterestBeginDate() != null) {
			model.put("times",
			DateUtil.formatDateTime(resAccount.getInterestBeginDate(), "yyyy年MM月dd日"));
			} else {
			model.put("times", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
			}
			
			 //到期日
	        if (resAccount.getLastFinishInterestDate() != null) {
	            model.put("endTime",
	                DateUtil.formatDateTime(resAccount.getLastFinishInterestDate(), "yyyy年MM月dd日"));
	        } else {
	            model.put("endTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
	        }
			
	        //签订时间
			if (resAccount.getOpenTime() != null) {
			model.put("openTime",
			DateUtil.formatDateTime(resAccount.getOpenTime(), "yyyy年MM月dd日"));
			} else {
			model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
			}

		    return url;

    }

    /**
     * 分享
     * @param channel   访问端口。micro2.0-H5；gen2.0-PC；gen178-PC_178
     * @param model
     * @param request
     */
    private void share(String  channel, Map<String, Object> model, HttpServletRequest request, String link) {
        if(Constants.CHANNEL_MICRO.equals(channel)){
            String qianbao = request.getParameter("qianbao");
            if (StringUtil.isNotBlank(qianbao)
                    && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
                model.put("qianbao", Constants.USER_AGENT_QIANBAO);
                link += "?qianbao=qianbao";
                CookieManager manager = new CookieManager(request);
	            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
		        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
		        	link += "&agentViewFlag="+viewAgentFlagCookie;
		        }
                model.put("qianbao", qianbao);
            }
            // 分享
            Map<String, String> sign = weChatUtil.createAuth(request);
            sign.put("link", link);
            model.put("weichat", sign);
        }
    }
    
    /**
     * 产品详情页计算器
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/regular/calculator/index")
   	public String calculatorIndex(@PathVariable String channel,HttpServletRequest request, HttpServletResponse response,
   			Map<String,Object> model) {
    	model.put("termMonth", request.getParameter("termMonth"));
    	model.put("baseRate", request.getParameter("baseRate"));
    	
    	return channel + "/regular/calculator_index";
    }
   
    /**
     * 赞分期产品回款计划表
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/regular/calculator/planList")
   	public String calculatorPlanList(@PathVariable String channel,HttpServletRequest request, HttpServletResponse response,
   			Map<String,Object> model) {
    	String termMonth = request.getParameter("termMonth");
    	Integer term = Integer.parseInt(termMonth);
    	String baseRate= request.getParameter("baseRate");
    	Double rate = Double.valueOf(baseRate);
    	
    	String amountStr= request.getParameter("amount");
    	Double amount = Double.valueOf(amountStr);
    	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	list = calPlanList(amount, rate, term);
 		model.put("list", list);
    	return channel + "/regular/calculator_planList";
    }
    
    
    /**
     * 产品详情页计算器 - APP
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/regular/calculator/index_app")
   	public String calculatorIndexApp(@PathVariable String channel,HttpServletRequest request, HttpServletResponse response,
   			Map<String,Object> model) {
    	model.put("termMonth", request.getParameter("termMonth"));
    	model.put("baseRate", request.getParameter("baseRate"));
    	
    	return channel + "/regular/calculator_index_app";
    }
    
    /**
     * 赞分期产品回款计划表-APP
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/regular/calculator/planList_app")
   	public String calculatorPlanListAPP(@PathVariable String channel,HttpServletRequest request, HttpServletResponse response,
   			Map<String,Object> model) {
    	String termMonth = request.getParameter("termMonth");
    	Integer term = Integer.parseInt(termMonth);
    	String baseRate= request.getParameter("baseRate");
    	Double rate = Double.valueOf(baseRate);
    	
    	String amountStr= request.getParameter("amount");
    	Double amount = Double.valueOf(amountStr);
    	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	list = calPlanList(amount, rate, term);
 		model.put("list", list);
    	return channel + "/regular/calculator_planList_app";
    }

    /**
     * 赞分期产品回款计划 -生成算法
     * @param amount
     * @param rate
     * @param term
     * @return
     */
	private List<Map<String, Object>> calPlanList(Double amount, Double rate,
			Integer term) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		//月利率
 		Double monthRate =  MoneyUtil.divide(rate,1200,20).doubleValue(); 
 		//贷款本金×月利率          amount1
 		Double amount1 = MoneyUtil.multiply(amount, monthRate).doubleValue(); //贷款本金×月利率
 		//(1＋月利率)＾还款月数         term2
 		Double term2 = Math.pow(monthRate+1, term);
 		
 		Double amount4add = 0d;
 		for (int i = 0; i < term; i++) {
 			Map<String, Object> record = new HashMap<String, Object>();
 			//本息=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕
 			Double totalAmount= MoneyUtil.divide(MoneyUtil.multiply(amount1,term2).doubleValue(), term2-1, 2).doubleValue();
 			Double monthAmount;
 			Double rateAmount;
 			if(i == term-1){
 				//每月本金-----总本金减之前的本金
 				monthAmount = MoneyUtil.subtract(amount, amount4add).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
 			}else{
 				//每月本金=贷款本金×月利率×(1+月利率)^(还款月序号-1)÷〔(1+月利率)^还款月数-1〕
 				monthAmount = MoneyUtil.divide(MoneyUtil.multiply(amount1, Math.pow(monthRate+1, i)).doubleValue(),term2-1,2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
 			}
 			//每月利息-----本息-本金
 			rateAmount =  MoneyUtil.subtract(totalAmount, monthAmount).setScale(2, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
 			
 			record.put("planTotal",totalAmount);
 			record.put("planPrincipal",monthAmount);
 			record.put("planInterest",rateAmount);
 			record.put("serial",(i+1));
 			amount4add = MoneyUtil.add(amount4add, monthAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
 			list.add(record);
 		}
		return list;
	}


}
