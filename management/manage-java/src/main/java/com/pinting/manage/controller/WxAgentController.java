package com.pinting.manage.controller;

import com.pinting.business.model.vo.WxAgentVO;
import com.pinting.business.service.manage.WxAgentService;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.StringUtil;
import com.pinting.util.Constants;
import com.pinting.util.WeChatUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 公众号渠道管理Controller
 *
 * @author shh
 * @date 2018/6/15 10:19
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Controller
@RequestMapping(value = "/wxAgent")
public class WxAgentController {

    private Logger log = LoggerFactory.getLogger(WxAgentController.class);

    @Autowired
    private WxAgentService wxAgentService;

    @Autowired
    @Qualifier("manageService")
    private HessianService wxHessianService;

    /**
     * 公众号渠道管理列表
     * @param agentName
     * @param pageNum
     * @param numPerPage
     * @param model
     * @return
     */
    @RequestMapping(value = "/getList")
    public String getList(String agentName, Integer pageNum, Integer numPerPage,  Map<String, Object> model) {
        pageNum = pageNum == null ? Constants.MANAGE_DEFAULT_PAGENUM : pageNum;
        numPerPage = numPerPage == null ? Constants.MANAGE_DEFAULT_NUMPERPAGE : numPerPage;
        if (StringUtils.isNotEmpty(agentName)) {
            agentName = agentName.trim();
        }
        Integer count = 0;
        count = wxAgentService.queryWxAgentCount(agentName);
        if(count > 0) {
            List<WxAgentVO> resultList = wxAgentService.queryWxAgentInfo(agentName, pageNum, numPerPage);
            model.put("resultList", resultList);
        }
        model.put("count", count);
        // 数据返回给页面
        model.put("agentName", agentName);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);
        return "/bswxagent/wxAgent_index";
    }

    /**
     * 查看渠道明细
     * @param wxAgentId
     * @param pageNum
     * @param numPerPage
     * @param model
     * @return
     */
    @RequestMapping("/details_review")
    public String detailsReview(Integer wxAgentId, Integer pageNum, Integer numPerPage,  Map<String, Object> model) {
        pageNum = pageNum == null ? Constants.MANAGE_DEFAULT_PAGENUM : pageNum;
        numPerPage = numPerPage == null ? Constants.MANAGE_DEFAULT_NUMPERPAGE : numPerPage;
        Integer count = 0;
        count = wxAgentService.queryWxInfoCountByAgentId(wxAgentId);
        if(count > 0) {
            List<WxAgentVO> detailsList = wxAgentService.queryWxInfoListByAgentId(wxAgentId, pageNum, numPerPage);
            model.put("detailsList", detailsList);
        }
        model.put("count", count);
        // 数据返回给页面
        model.put("wxAgentId", wxAgentId);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);
        return "/bswxagent/detail_review";
    }

    /**
     * 生成永久二维码
     * @param wxAgentId
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/matrixImage")
    public String matrixImage(Integer wxAgentId, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        log.info("进入生成永久二维码的");

        // 渠道二维码，获取的token跟币港湾公众号是同一个
        String accessToken = com.pinting.business.util.WeChatUtil.getAccessToken();
        log.info("生成永久二维码的accessToken:"+accessToken);
        String ticket = null;
        ticket = getTicket(wxAgentId, accessToken);
        if(ticket == null) {
            log.info("生成永久二维码，获取二维码ticket失败");
            return null;
        }
        String showqrcodePath  = null;
        showqrcodePath = getCode(ticket);
        if(showqrcodePath == null) {
            log.info("生成永久二维码，通过ticket换取二维码失败");
            return null;
        }
        model.put("showqrcodePath", showqrcodePath);
        return "/bswxagent/wxAgent_matrix";
    }

    /**
     * 生成永久二维码，获取二维码ticket
     * @param sceneId
     * @param accessToken
     * @return
     */
    private String getTicket(Integer sceneId, String accessToken) {
        try {
            String getTicketUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken;
            String jsonStr = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": "+sceneId+"}}}";
            JSONObject json = JSONObject.fromObject(jsonStr);//将java对象转换为json对象

            String msg = "";
            String ticket = "";
            msg = WeChatUtil.sendPost(getTicketUrl, json.toString(), false, "UTF-8");
            log.info("生成带参数的二维码返回信息msg："+msg);
            JSONObject codeMessageJson = JSONObject.fromObject(msg);
            ticket = codeMessageJson.getString("ticket");
            return ticket;
        } catch (Exception e) {
            log.error("生成带参数的二维码失败"+ e);
        }
        return null;
    }

    /**
     * 通过ticket换取二维码
     * @param ticket
     */
    public String getCode(String ticket) {
        String showqrcodePath  = null;
        try {

            if (StringUtil.isNotBlank(ticket)) {
                String ticketEncoder = URLEncoder.encode(ticket, "UTF-8");
                showqrcodePath = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticketEncoder;
            }

            /*if (StringUtil.isNotBlank(ticket)) {
                String ticketEncoder = URLEncoder.encode(ticket, "UTF-8");
                showqrcodePath = WeChatUtil.sendGet("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticketEncoder, "", false, "UTF-8");
            }*/
            log.info("通过ticket换取二维码的地址："+showqrcodePath);
            return showqrcodePath;
        } catch (IOException e) {
            log.error("通过ticket换取二维码失败"+ e);
        }
        return null;
    }

}
