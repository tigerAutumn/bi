package com.pinting.manage.weixin.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.pinting.business.dao.MWxMuserInfoMapper;
import com.pinting.business.hessian.manage.message.*;
import com.pinting.business.model.MWxMuserInfo;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.manage.weixin.message.Article;
import com.pinting.manage.weixin.message.NewsMessage;
import com.pinting.manage.weixin.message.TextMessage;
import com.pinting.manage.weixin.util.MessageUtil;
import com.pinting.manage.weixin.util.OperationalWeChatUtil;
import com.pinting.util.Constants;
import com.pinting.util.WeChatUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.*;

/**
 * 微信服务控制器
 * @author White Wolf
 * @version $Id: WeChatController.java, v 0.1 2015-5-18 下午2:42:41 White Wolf Exp $
 */
@Controller
public class OperationalWeChatController {
    private Logger log = LoggerFactory.getLogger(OperationalWeChatController.class);

    public final static String SEND_CUSTOM_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

    @Autowired
    @Qualifier("dispatchService")
    public HessianService wxUserService;
    @Autowired
    private OperationalWeChatUtil operationalWeChatUtil;
    @Autowired
    private MWxMuserInfoMapper mWxMuserInfoMapper;

    /**
     * 设置回调url
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/operationalWeChat/index", method = { RequestMethod.GET })
    public void weiChatInit(HttpServletRequest request, HttpServletResponse response)
                                                                                     throws ServletException,
                                                                                     IOException {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        log.info("signature:"+signature);
        log.info("timestamp:"+timestamp);
        log.info("nonce:"+nonce);
        log.info("echostr:"+echostr);
        if (signature == null) {
            out.print("非微信服务器传入");
        } else {
            if (operationalWeChatUtil.checkSignature(signature, timestamp, nonce)) {
                out.print(echostr);
            }
        }
        out.close();
        out = null;
    }

    /**
     * 消息回调
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/operationalWeChat/index", method = { RequestMethod.POST })
    public void dealWeiChatMessage(HttpServletRequest request, HttpServletResponse response) {

        try {
            PrintWriter out = response.getWriter();
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号（open_id）
            final String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 消息内容
            final String content = requestMap.get("Content");

            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(msgType);
            textMessage.setFuncFlag(0);
            log.info("消息的类型：" + msgType + "；消息内容："+ content);

            //事件消息接收
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                NewsMessage newsMessage = new NewsMessage();
                newsMessage.setFromUserName(toUserName);
                newsMessage.setCreateTime(new Date().getTime());
                newsMessage.setMsgType(Constants.WX_AUTO_REPLY_MSG_TYPE_NEWS);
                //事件消息接收处理
                eventMessageProcess(msgType, requestMap, textMessage, newsMessage, out);

                /*log.info("=============关注时回复=============");
                if(flag){
                	textXML = MessageUtil.textMessageToXml(textMessage);
                }else{

                	textXML = MessageUtil.newsMessageToXml(newsMessage);
                }
                log.info(textXML);
                out.print(textXML);
                out.flush();
                out.close();*/
            }
            //查询关注用户的权限
            MWxMuserInfo info = mWxMuserInfoMapper.selectByOpenId(fromUserName);
            if (info != null) {
            	//文本消息接收
                if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {

                    //云贷/七贷实时还款数据查询过慢，采用异步客服接口方式
                    if(com.pinting.business.util.Constants.YUN_DAI_REPAY_KEY.equals(content)
                            || com.pinting.business.util.Constants.SEVEN_DAI_REPAY_KEY.equals(content)){
                        log.info("云贷/七贷实时还款数据查询过慢，采用异步客服接口方式");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ReqMsg_OperationalWxAutoReply_SelectAutoReplyMessage  reqMsg_WxAutoReply_SelectAutoReplyMessage = new  ReqMsg_OperationalWxAutoReply_SelectAutoReplyMessage();
                                reqMsg_WxAutoReply_SelectAutoReplyMessage.setAutoReplyMessage(content);
                                ResMsg_OperationalWxAutoReply_SelectAutoReplyMessage  resMsg_WxAutoReply_SelectAutoReplyMessage = (ResMsg_OperationalWxAutoReply_SelectAutoReplyMessage)wxUserService.handleMsg(reqMsg_WxAutoReply_SelectAutoReplyMessage);

                                String retContent = resMsg_WxAutoReply_SelectAutoReplyMessage.getDataGrid().get(0).get("content").toString();
                                log.info("关键字回复消息：" + Constants.WX_AUTO_REPLY_MSG_TYPE_TEXT +":"+resMsg_WxAutoReply_SelectAutoReplyMessage.getDataGrid().get(0).get("content").toString());
                                StringBuffer jsonParams = new StringBuffer();

                                jsonParams.append("{")
                                        .append("\"touser\":\"").append(fromUserName).append("\",")
                                        .append("\"msgtype\":\"text\",")
                                        .append("\"text\":{\"content\":\"").append(retContent).append("\"}")
                                        .append("}");
                                ResMsg_SOperationalWxUtil_GetTokenAndTicket resToken = (ResMsg_SOperationalWxUtil_GetTokenAndTicket)wxUserService.handleMsg(new ReqMsg_SOperationalWxUtil_GetTokenAndTicket());
                                String url = SEND_CUSTOM_URL + resToken.getAccessToken();
                                try {
                                    log.info("推客服消息URL，参数：" + url + "，" + jsonParams.toString());
                                    String result = WeChatUtil.sendPost(url, jsonParams.toString(), false, "UTF-8");
                                    log.info("推送结果响应：" + result);
                                } catch (IOException e) {
                                    log.error("云贷/七贷实时还款数据查询结果推送异常", e);
                                }
                            }
                        }).start();


                        textMessage.setMsgType(Constants.WX_AUTO_REPLY_MSG_TYPE_TEXT);
                        String retContent = "数据量较大，请稍等，勿重复操作，谢谢！";
                        textMessage.setContent(retContent.replace("&lt;", "<").replace("&gt;", ">"));
                        log.info("关键字回复消息：" + Constants.WX_AUTO_REPLY_MSG_TYPE_TEXT +":");
                        String textXML = MessageUtil.textMessageToXml(textMessage);
                        log.info(textXML);
                        out.print(textXML);
                        out.flush();
                        out.close();
                        return;
                    }

                	//根据消息内容匹配自动回复消息内容
                	ReqMsg_OperationalWxAutoReply_SelectAutoReplyMessage  reqMsg_WxAutoReply_SelectAutoReplyMessage = new  ReqMsg_OperationalWxAutoReply_SelectAutoReplyMessage();
                	reqMsg_WxAutoReply_SelectAutoReplyMessage.setAutoReplyMessage(content);
                	ResMsg_OperationalWxAutoReply_SelectAutoReplyMessage  resMsg_WxAutoReply_SelectAutoReplyMessage = (ResMsg_OperationalWxAutoReply_SelectAutoReplyMessage)wxUserService.handleMsg(reqMsg_WxAutoReply_SelectAutoReplyMessage);

                	if (CollectionUtils.isEmpty(resMsg_WxAutoReply_SelectAutoReplyMessage.getDataGrid())) {
                		log.info("自动回复消息：无自动回复的消息");
                        textMessage.setMsgType("transfer_customer_service");
                        String textXML = MessageUtil.textMessageToXml(textMessage);
                        log.info(textXML);
                        out.print(textXML);
                        out.flush();
                        out.close();
    				}else {
    					if (Constants.WX_AUTO_REPLY_MSG_TYPE_TEXT.equals(resMsg_WxAutoReply_SelectAutoReplyMessage.getDataGrid().get(0).get("msgType").toString())) {
    	            		 textMessage.setMsgType(Constants.WX_AUTO_REPLY_MSG_TYPE_TEXT);
    	                     String retContent = resMsg_WxAutoReply_SelectAutoReplyMessage.getDataGrid().get(0).get("content").toString();
    	                     textMessage.setContent(retContent.replace("&lt;", "<").replace("&gt;", ">"));
    	                     log.info("关键字回复消息：" + Constants.WX_AUTO_REPLY_MSG_TYPE_TEXT +":"+resMsg_WxAutoReply_SelectAutoReplyMessage.getDataGrid().get(0).get("content").toString());
    	                     String textXML = MessageUtil.textMessageToXml(textMessage);
    	                     log.info(textXML);
    	                     out.print(textXML);
    	                     out.flush();
    	                     out.close();
    					}else if (Constants.WX_AUTO_REPLY_MSG_TYPE_NEWS.equals(resMsg_WxAutoReply_SelectAutoReplyMessage.getDataGrid().get(0).get("msgType").toString())) {
    						NewsMessage newsMessage = new NewsMessage();
    						newsMessage.setToUserName(fromUserName);
    						newsMessage.setFromUserName(toUserName);
    						newsMessage.setCreateTime(new Date().getTime());
    						newsMessage.setMsgType(Constants.WX_AUTO_REPLY_MSG_TYPE_NEWS);
    						log.info("关键字回复消息：" + Constants.WX_AUTO_REPLY_MSG_TYPE_NEWS);

    						List<Article> articles = new ArrayList<Article>();
    						int num = 0;
    						for (HashMap<String, Object> map : resMsg_WxAutoReply_SelectAutoReplyMessage.getDataGrid()) {
    							if (Constants.WX_AUTO_REPLY_MSG_TYPE_NEWS.equals((String)map.get("msgType"))) {
    								num = num + 1;
    								Article article = new Article();
    								article.setTitle((String)map.get("title"));
    								String retContent = (String)map.get("content");
    								article.setDescription(retContent.replace("&lt;", "<").replace("&gt;", ">"));
    								article.setPicUrl((String)map.get("picUrl"));
    								article.setUrl((String)map.get("url"));
    								articles.add(article);
    							}
    						}
    						newsMessage.setArticleCount(num);
    						newsMessage.setArticles(articles);


    						String textXML = MessageUtil.newsMessageToXml(newsMessage);
    		                log.info(textXML);
    		                out.print(textXML);
    		                out.flush();
    		                out.close();
    					}

    				}


                }
			}
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 事件消息接收处理
     *
     * @param msgType
     * @param requestMap
     * @param textMessage
     */
    private void eventMessageProcess(String msgType, Map<String, String> requestMap,
                                     TextMessage textMessage,NewsMessage newsMessage,PrintWriter out) {
    	//String content = "币港湾开会小差~客官稍等";
        // 事件类型
        String eventType = requestMap.get("Event");
        // 自定义菜单点击事件
        /*if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
            String eventKey = requestMap.get("EventKey");
            //TODO 根据eventKey分别进行菜单业务处理；
            if (EventEnum.COSTOMER_SERVICE.getEventKey().equals(eventKey)) {
                textMessage.setMsgType("transfer_customer_service");
            }

            else {
                content = "币港湾正在建设中，敬请期待";//此处返回内容 需从数据库查回
                textMessage.setMsgType("text");
                textMessage.setContent(content);
            }
        }
        // 自定义菜单点击事件 （url自动跳转，不需处理）
        else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) {
            String eventKey = requestMap.get("EventKey");
            log.info(">>>>>>" + eventKey);
        }*/

        String openId = requestMap.get("FromUserName");
    	// 公众帐号
        String toUserName = requestMap.get("ToUserName");

        log.info("operationalWeChat ticket requestMap:{}", JSON.toJSONString(requestMap));
        // 关注
        if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
            /*String message = weChatFacade.attentionReplyMessage().getResultObj();
            content = message;//此处返回内容 需从数据库查回
            textMessage.setMsgType("text");
            textMessage.setContent(content);*/

        	if(StringUtil.isNotEmpty(openId)) {
                ReqMsg_OperationalWxUser_Subscribe req = new ReqMsg_OperationalWxUser_Subscribe();
                req.setOpenId(openId);
                //req.setAccessToken(WeChatUtil.getAccessToken());
                ResMsg_SOperationalWxUtil_GetTokenAndTicket resToken = (ResMsg_SOperationalWxUtil_GetTokenAndTicket)wxUserService.handleMsg(new ReqMsg_SOperationalWxUtil_GetTokenAndTicket());
                req.setAccessToken(resToken.getAccessToken());
                wxUserService.handleMsg(req);
        	}
        	ReqMsg_OperationalWxAutoReply_GetSubscribeReply wxReq = new ReqMsg_OperationalWxAutoReply_GetSubscribeReply();
        	ResMsg_OperationalWxAutoReply_GetSubscribeReply wxRes = (ResMsg_OperationalWxAutoReply_GetSubscribeReply)wxUserService.handleMsg(wxReq);


        	/*String content = "你好，欢迎关注泓淳测试";
            textMessage.setMsgType("text");
            textMessage.setContent(content);*/
        	String textXML = "";

            if (CollectionUtils.isEmpty(wxRes.getDataGrid())) {
       		 	log.info("自动回复消息：无自动回复的消息,默认回复：你好，欢迎关注币港湾");
       		 	String content = "你好，欢迎关注币港湾";
       		 	textMessage.setMsgType(Constants.WX_AUTO_REPLY_MSG_TYPE_TEXT);
       		 	textMessage.setContent(content);
       		 	textXML = MessageUtil.textMessageToXml(textMessage);
       		 	log.info(textXML);
       		 	out.print(textXML);
       		 	out.flush();
       		 	out.close();
			}else {
				if (Constants.WX_AUTO_REPLY_MSG_TYPE_TEXT.equals(wxRes.getDataGrid().get(0).get("msgType").toString())) {
           		 	textMessage.setMsgType(Constants.WX_AUTO_REPLY_MSG_TYPE_TEXT);
           		 	String retContent = wxRes.getDataGrid().get(0).get("content").toString();
                    textMessage.setContent(retContent.replace("&lt;", "<").replace("&gt;", ">"));
                    log.info("关注时回复消息：" + Constants.WX_AUTO_REPLY_MSG_TYPE_TEXT +":"+wxRes.getDataGrid().get(0).get("content").toString());
                    textXML = MessageUtil.textMessageToXml(textMessage);
                    log.info(textXML);
                    out.print(textXML);
                    out.flush();
                    out.close();
				}else if (Constants.WX_AUTO_REPLY_MSG_TYPE_NEWS.equals(wxRes.getDataGrid().get(0).get("msgType").toString())) {
					newsMessage.setToUserName(openId);
					log.info("关注时回复消息：" + Constants.WX_AUTO_REPLY_MSG_TYPE_NEWS);

					List<Article> articles = new ArrayList<Article>();
					int num = 0;
					for (HashMap<String, Object> map : wxRes.getDataGrid()) {
						num = num + 1;
						Article article = new Article();
						article.setTitle((String)map.get("title"));
						String retContent = (String)map.get("content");
						article.setDescription(retContent.replace("&lt;", "<").replace("&gt;", ">"));
						article.setPicUrl((String)map.get("picUrl"));
						article.setUrl((String)map.get("url"));
						articles.add(article);
					}
					newsMessage.setArticleCount(num);
					newsMessage.setArticles(articles);

					textXML = MessageUtil.newsMessageToXml(newsMessage);
	                log.info(textXML);
	                out.print(textXML);
	                out.flush();
	                out.close();
				}

			}
        }
        // 取消关注
        else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
        	if(StringUtil.isNotEmpty(openId)) {
        		ReqMsg_OperationalWxUser_UnSubscribe req = new ReqMsg_OperationalWxUser_UnSubscribe();
        		req.setOpenId(openId);
        		wxUserService.handleMsg(req);
        	}
        }
        //CLICK(自定义菜单点击事件)
        else if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){
        	String eventKey = requestMap.get("EventKey");

        	//根据消息内容匹配自动回复消息内容
        	ReqMsg_OperationalWxAutoReply_SelectAutoReplyMessage  reqMsg_WxAutoReply_SelectAutoReplyMessage = new  ReqMsg_OperationalWxAutoReply_SelectAutoReplyMessage();
        	reqMsg_WxAutoReply_SelectAutoReplyMessage.setAutoReplyMessage(eventKey);
        	ResMsg_OperationalWxAutoReply_SelectAutoReplyMessage  resMsg_WxAutoReply_SelectAutoReplyMessage = (ResMsg_OperationalWxAutoReply_SelectAutoReplyMessage)wxUserService.handleMsg(reqMsg_WxAutoReply_SelectAutoReplyMessage);

        	if (CollectionUtils.isEmpty(resMsg_WxAutoReply_SelectAutoReplyMessage.getDataGrid())) {
        		log.info("自动回复消息：无自动回复的消息");
                textMessage.setMsgType("transfer_customer_service");
                String textXML = MessageUtil.textMessageToXml(textMessage);
                log.info(textXML);
                out.print(textXML);
                out.flush();
                out.close();
			}else {
				if (Constants.WX_AUTO_REPLY_MSG_TYPE_TEXT.equals(resMsg_WxAutoReply_SelectAutoReplyMessage.getDataGrid().get(0).get("msgType").toString())) {
            		 textMessage.setMsgType(Constants.WX_AUTO_REPLY_MSG_TYPE_TEXT);
            		 String retContent = resMsg_WxAutoReply_SelectAutoReplyMessage.getDataGrid().get(0).get("content").toString();
                     textMessage.setContent(retContent.replace("&lt;", "<").replace("&gt;", ">"));
                     log.info("关键字回复消息：" + Constants.WX_AUTO_REPLY_MSG_TYPE_TEXT +":"+resMsg_WxAutoReply_SelectAutoReplyMessage.getDataGrid().get(0).get("content").toString());
                     String textXML = MessageUtil.textMessageToXml(textMessage);
                     log.info(textXML);
                     out.print(textXML);
                     out.flush();
                     out.close();
				}else if (Constants.WX_AUTO_REPLY_MSG_TYPE_NEWS.equals(resMsg_WxAutoReply_SelectAutoReplyMessage.getDataGrid().get(0).get("msgType").toString())) {
					newsMessage.setToUserName(openId);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(Constants.WX_AUTO_REPLY_MSG_TYPE_NEWS);
					log.info("关键字回复消息：" + Constants.WX_AUTO_REPLY_MSG_TYPE_NEWS);

					List<Article> articles = new ArrayList<Article>();
					int num = 0;
					for (HashMap<String, Object> map : resMsg_WxAutoReply_SelectAutoReplyMessage.getDataGrid()) {
						if (Constants.WX_AUTO_REPLY_MSG_TYPE_NEWS.equals((String)map.get("msgType"))) {
							num = num + 1;
							Article article = new Article();
							article.setTitle((String)map.get("title"));
							String retContent = (String)map.get("content");
							article.setDescription(retContent.replace("&lt;", "<").replace("&gt;", ">"));
							article.setPicUrl((String)map.get("picUrl"));
							article.setUrl((String)map.get("url"));
							articles.add(article);
						}
					}
					newsMessage.setArticleCount(num);
					newsMessage.setArticles(articles);

					String textXML = MessageUtil.newsMessageToXml(newsMessage);
	                log.info(textXML);
	                out.print(textXML);
	                out.flush();
	                out.close();
				}

			}
        }

    }

    /**
     * emoji表情转换(hex -> utf-16)
     *
     * @param hexEmoji
     * @return
     */
    public static String emoji(int hexEmoji) {
        return String.valueOf(Character.toChars(hexEmoji));
    }

    /**
     * 身份获取
     *
     * @param request
     * @param response
     */
    @RequestMapping("/operationalWeChat/authorize")
    public String authorize(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        CookieManager manager = new CookieManager(request);
        try {
            log.info(System.currentTimeMillis() + "================访问authorize页面："
                     + request.getRequestURL() + "?" + request.getQueryString());
            //开始获取access_token
            String getAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
            StringBuffer sb = new StringBuffer();
            sb.append("code=" + code);
            log.info("============code:"+code);
            ResMsg_SOperationalWxUtil_GetTokenAndTicket res = (ResMsg_SOperationalWxUtil_GetTokenAndTicket)wxUserService.handleMsg(new ReqMsg_SOperationalWxUtil_GetTokenAndTicket());
            sb.append("&appid=" + res.getAppid() + "&secret=" + res.getSecret()
                      + "&grant_type=authorization_code");
            String accessToken = operationalWeChatUtil.sendPost(getAccessTokenUrl, sb.toString(), false,
                "UTF-8");
            JSONObject accessTokenJson = JSONObject.fromObject(accessToken);
            if (accessTokenJson.get("access_token") == null) {
                log.info(System.currentTimeMillis() + "================access_token未取到："
                         + request.getRequestURL() + "?" + request.getQueryString());
                return "forward:/weChat/getCode";
            }

            String openid = accessTokenJson.getString("openid");
            manager.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_OPEN_ID.getName(),
            		openid, true);
            manager.save(response, CookieEnums._MANAGE_PLAT_FORM.getName(), null, "/", 5*365*24*3600, true);

            String root = GlobEnvUtil.getWebURL("");
            state = state.replace(root, "");
            String param = state.contains("?") ? "&i=" + openid : "?i=" + openid;
            param += "&menu=yes";
            param += "&v=" + UUID.randomUUID();
            log.info(System.currentTimeMillis() + "================ state = "+state+" param = "+ param +" ======================");
            return "redirect:" + state + param;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 打开身份认证界面
     *
     * @param request
     * @param response
     */
    @RequestMapping("/operationalWeChat/getCode")
    public String getCode(HttpServletRequest request, String burl, HttpServletResponse response) {
        try {
            log.info(System.currentTimeMillis() + "================发起请求getCode请求:"
                     + request.getRequestURL() + "?" + request.getQueryString());
            String state = burl;
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize";
            String redirectUri = GlobEnvUtil.getWebURL("/operationalWeChat/authorize");
            log.info("=================redirectUri:"+redirectUri);
            ResMsg_SOperationalWxUtil_GetTokenAndTicket res = (ResMsg_SOperationalWxUtil_GetTokenAndTicket)wxUserService.handleMsg(new ReqMsg_SOperationalWxUtil_GetTokenAndTicket());
            url += "?appid=" + res.getAppid() + "&redirect_uri=" + URLEncoder.encode(redirectUri)
                   + "&response_type=code&scope=snsapi_base" + "&state=" + state
                   + "#wechat_redirect";
            //            response.sendRedirect(url);
            return "redirect:" + url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 非静默授权
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/operationalWeChat/authorizeNonSilentLicense")
    public String authorizeNonSilentLicense(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        CookieManager manager = new CookieManager(request);
        try {
            log.info(System.currentTimeMillis() + "================访问authorize页面："
                     + request.getRequestURL() + "?" + request.getQueryString());
            //开始获取access_token
            String getAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
            StringBuffer sb = new StringBuffer();
            sb.append("code=" + code);
            log.info("============code:"+code);
            ResMsg_SOperationalWxUtil_GetTokenAndTicket res = (ResMsg_SOperationalWxUtil_GetTokenAndTicket)wxUserService.handleMsg(new ReqMsg_SOperationalWxUtil_GetTokenAndTicket());
            sb.append("&appid=" + res.getAppid() + "&secret=" + res.getSecret()
                      + "&grant_type=authorization_code");
            String accessToken = operationalWeChatUtil.sendPost(getAccessTokenUrl, sb.toString(), false,
                "UTF-8");
            JSONObject accessTokenJson = JSONObject.fromObject(accessToken);
            if (accessTokenJson.get("access_token") == null) {
                log.info(System.currentTimeMillis() + "================access_token未取到："
                         + request.getRequestURL() + "?" + request.getQueryString());
                return "forward:/weChat/getCode";
            }
            // 非静默授权，开始拉取用户信息
            log.info(System.currentTimeMillis() + "================ 非静默授权 开始 ======================");
            String getUserMessageUrl = "https://api.weixin.qq.com/sns/userinfo";
            StringBuffer sb1 = new StringBuffer();
            sb1.append("access_token=" + accessTokenJson.getString("access_token"));
            sb1.append("&openid=" + accessTokenJson.getString("openid"));
            sb1.append("&lang=zh_CN");
            String userMessage = OperationalWeChatUtil.sendGet(getUserMessageUrl, sb1.toString(), false,
                "UTF-8");
            JSONObject userMessageJson = JSONObject.fromObject(userMessage);
            log.info("====== 微信用户信息 ===== " + userMessageJson.toString());
            String wxNick = (String) userMessageJson.get("nickname");
            wxNick = URLEncoder.encode(wxNick, "utf-8");    // 为了避免昵称含有表情存库报错
            String headImgUrl = (String) userMessageJson.get("headimgurl");
//            userMessageJson.get("sex");
//            userMessageJson.get("province");
//            userMessageJson.get("city");
//            userMessageJson.get("country");
//            userMessageJson.get("privilege");
//            userMessageJson.get("unionid");
            manager.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_WX_HEAD_IMG_URL.getName(), headImgUrl, true);
            manager.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_WX_NICK.getName(), wxNick, true);
            log.info(System.currentTimeMillis() + "================ 非静默授权 结束 ======================");

            String openid = accessTokenJson.getString("openid");
            manager.setValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_OPEN_ID.getName(),
                    openid, true);
            manager.save(response, CookieEnums._MANAGE_PLAT_FORM.getName(), null, "/", 5*365*24*3600, true);

            String root = GlobEnvUtil.getWebURL("");
            state = state.replace(root, "");
            String param = state.contains("?") ? "&i=" + openid : "?i=" + openid;
            param += "&menu=yes";
            param += "&v=" + UUID.randomUUID();
            log.info(System.currentTimeMillis() + "================ state = "+state+" param = "+ param +" ======================");
            return "redirect:" + state + param;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 非静默授权 微信OAuth2.0身份认证(微信认证界面，确认按钮)
     * @param request
     * @param burl
     * @param response
     * @return
     */
    @SuppressWarnings("deprecation")
    @RequestMapping("/operationalWeChat/getCodeNonSilentLicense")
    public String getCodeNonSilentLicense(HttpServletRequest request, String burl, HttpServletResponse response) {
        try {
            log.info(System.currentTimeMillis() + "================发起请求getCodeNonSilentLicense请求:"
                     + request.getRequestURL() + "?" + request.getQueryString());
            String state = burl;
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize";
            String redirectUri = GlobEnvUtil.getWebURL("/operationalWeChat/authorizeNonSilentLicense");
            log.info("=================redirectUri:"+redirectUri);
            ResMsg_SOperationalWxUtil_GetTokenAndTicket res = (ResMsg_SOperationalWxUtil_GetTokenAndTicket)wxUserService.handleMsg(new ReqMsg_SOperationalWxUtil_GetTokenAndTicket());
            url += "?appid=" + res.getAppid() + "&redirect_uri=" + URLEncoder.encode(redirectUri)
                   + "&response_type=code&scope=snsapi_userinfo" + "&state=" + state
                   + "#wechat_redirect";
            log.info("非静默授权 微信OAuth2.0身份认证，请求URL：{}", url);
            return "redirect:" + url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
