package com.pinting.site.weichat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.hessian.site.message.*;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.message.Article;
import com.pinting.site.weichat.message.LocationMessage;
import com.pinting.site.weichat.message.NewsMessage;
import com.pinting.site.weichat.message.TextMessage;
import com.pinting.site.weichat.util.MessageUtil;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;

/**
 * 微信服务控制器
 * @author White Wolf
 * @version $Id: WeChatController.java, v 0.1 2015-5-18 下午2:42:41 White Wolf Exp $
 */
@Controller
public class WeChatController{
    private Logger log = LoggerFactory.getLogger(WeChatController.class);

    @Autowired
    private CommunicateBusiness wxUserService;
    @Autowired
    private WeChatUtil weChatUtil;


    /**
     * 设置回调url
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/weChat/index", method = { RequestMethod.GET })
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
        if (signature == null) {
            out.print("非微信服务器传入");
        } else {
            if (weChatUtil.checkSignature(signature, timestamp, nonce)) {
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
    @RequestMapping(value = "/weChat/index", method = { RequestMethod.POST })
    public void dealWeiChatMessage(HttpServletRequest request, HttpServletResponse response) {

        try {
            PrintWriter out = response.getWriter();
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 消息内容
            String content = requestMap.get("Content");

            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(msgType);
            textMessage.setFuncFlag(0);
            log.info("消息的类型：" + msgType + "；消息内容："+ content);

            //文本消息接收
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
            	//根据消息内容匹配自动回复消息内容
            	ReqMsg_WxAutoReply_SelectAutoReplyMessage  reqMsg_WxAutoReply_SelectAutoReplyMessage = new  ReqMsg_WxAutoReply_SelectAutoReplyMessage();
            	reqMsg_WxAutoReply_SelectAutoReplyMessage.setAutoReplyMessage(content);
            	ResMsg_WxAutoReply_SelectAutoReplyMessage  resMsg_WxAutoReply_SelectAutoReplyMessage = (ResMsg_WxAutoReply_SelectAutoReplyMessage)wxUserService.handleMsg(reqMsg_WxAutoReply_SelectAutoReplyMessage);

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
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                textMessage.setMsgType("transfer_customer_service");
                String textXML = MessageUtil.textMessageToXml(textMessage);
                log.info(textXML);
                out.print(textXML);
                out.flush();
                out.close();
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                LocationMessage locationMessage = new LocationMessage();
                locationMessage = (LocationMessage) MessageUtil.convert2Bean(requestMap,
                    locationMessage);
                log.info(locationMessage.getLocation_X() + "," + locationMessage.getLocation_Y());
                log.info(locationMessage.getLabel());

                textMessage.setMsgType("transfer_customer_service");
                String textXML = MessageUtil.textMessageToXml(textMessage);
                log.info(textXML);
                out.print(textXML);
                out.flush();
                out.close();
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                textMessage.setMsgType("transfer_customer_service");
                String textXML = MessageUtil.textMessageToXml(textMessage);
                log.info(textXML);
                out.print(textXML);
                out.flush();
                out.close();
            }
            // 音频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                textMessage.setMsgType("transfer_customer_service");
                String textXML = MessageUtil.textMessageToXml(textMessage);
                log.info(textXML);
                out.print(textXML);
                out.flush();
                out.close();
            }
            // 视频
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
                textMessage.setMsgType("transfer_customer_service");
                String textXML = MessageUtil.textMessageToXml(textMessage);
                log.info(textXML);
                out.print(textXML);
                out.flush();
                out.close();
            }
            //小视频
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)) {
                textMessage.setMsgType("transfer_customer_service");
                String textXML = MessageUtil.textMessageToXml(textMessage);
                log.info(textXML);
                out.print(textXML);
                out.flush();
                out.close();
            }
            //事件消息接收
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
            	NewsMessage newsMessage = new NewsMessage();
				newsMessage.setFromUserName(toUserName);
				newsMessage.setCreateTime(new Date().getTime());
				newsMessage.setMsgType(Constants.WX_AUTO_REPLY_MSG_TYPE_NEWS);
                //事件消息接收处理
                eventMessageProcess(msgType, requestMap, textMessage, newsMessage, out);

               /* log.info("=============关注时回复=============");
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

        log.info("用户关注公众号后推送的requestMap信息："+requestMap);

        // 关注
        if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
            /*String message = weChatFacade.attentionReplyMessage().getResultObj();
            content = message;//此处返回内容 需从数据库查回
            textMessage.setMsgType("text");
            textMessage.setContent(content);*/

        	if(StringUtil.isNotEmpty(openId)) {
                String eventKey = requestMap.get("EventKey");

                // 公众号渠道二维码用户关注
                if(StringUtil.isNotEmpty(eventKey)) {
                    log.info("公众号渠道二维码用户关注eventKey: "+eventKey);
                    String wxAgentId = eventKey.split("_")[1];
                    ReqMsg_WxUser_SubscribeWxAgent req = new ReqMsg_WxUser_SubscribeWxAgent();
                    req.setOpenId(openId);
                    if(StringUtil.isNotEmpty(wxAgentId)) {
                        req.setWxAgentId(Integer.parseInt(wxAgentId));
                    }
                    ResMsg_SWxUtil_GetTokenAndTicket resToken = (ResMsg_SWxUtil_GetTokenAndTicket)wxUserService.handleMsg(new ReqMsg_SWxUtil_GetTokenAndTicket());
                    req.setAccessToken(resToken.getAccessToken());
                    wxUserService.handleMsg(req);
                }else {
                    ReqMsg_WxUser_Subscribe req = new ReqMsg_WxUser_Subscribe();
                    req.setOpenId(openId);
                    //req.setAccessToken(WeChatUtil.getAccessToken());
                    ResMsg_SWxUtil_GetTokenAndTicket resToken = (ResMsg_SWxUtil_GetTokenAndTicket)wxUserService.handleMsg(new ReqMsg_SWxUtil_GetTokenAndTicket());
                    req.setAccessToken(resToken.getAccessToken());
                    wxUserService.handleMsg(req);
                }
        	}
        	ReqMsg_WxAutoReply_GetSubscribeReply wxReq = new ReqMsg_WxAutoReply_GetSubscribeReply();
        	ResMsg_WxAutoReply_GetSubscribeReply wxRes = (ResMsg_WxAutoReply_GetSubscribeReply)wxUserService.handleMsg(wxReq);


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
        		ReqMsg_WxUser_UnSubscribe req = new ReqMsg_WxUser_UnSubscribe();
        		req.setOpenId(openId);
        		wxUserService.handleMsg(req);
        	}
        }
        //CLICK(自定义菜单点击事件)
        else if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){
        	String eventKey = requestMap.get("EventKey");

        	//根据消息内容匹配自动回复消息内容
        	ReqMsg_WxAutoReply_SelectAutoReplyMessage  reqMsg_WxAutoReply_SelectAutoReplyMessage = new  ReqMsg_WxAutoReply_SelectAutoReplyMessage();
        	reqMsg_WxAutoReply_SelectAutoReplyMessage.setAutoReplyMessage(eventKey);
        	ResMsg_WxAutoReply_SelectAutoReplyMessage  resMsg_WxAutoReply_SelectAutoReplyMessage = (ResMsg_WxAutoReply_SelectAutoReplyMessage)wxUserService.handleMsg(reqMsg_WxAutoReply_SelectAutoReplyMessage);

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
    @RequestMapping("/weChat/authorize")
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
            ResMsg_SWxUtil_GetTokenAndTicket res = (ResMsg_SWxUtil_GetTokenAndTicket)wxUserService.handleMsg(new ReqMsg_SWxUtil_GetTokenAndTicket());
            sb.append("&appid=" + res.getAppid() + "&secret=" + res.getSecret()
                      + "&grant_type=authorization_code");
            String accessToken = weChatUtil.sendPost(getAccessTokenUrl, sb.toString(), false,
                "UTF-8");
            JSONObject accessTokenJson = JSONObject.fromObject(accessToken);
            if (accessTokenJson.get("access_token") == null) {
                log.info(System.currentTimeMillis() + "================access_token未取到："
                         + request.getRequestURL() + "?" + request.getQueryString());
                return "forward:/weChat/getCode";
            }

            String openid = accessTokenJson.getString("openid");
            manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_OPEN_ID.getName(),
            		openid, true);
            manager.save(response, CookieEnums._SITE.getName(), null, "/", 5*365*24*3600, true);

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
    @RequestMapping("/weChat/getCode")
    public String getCode(HttpServletRequest request, String burl, HttpServletResponse response) {
        try {
            log.info(System.currentTimeMillis() + "================发起请求getCode请求:"
                     + request.getRequestURL() + "?" + request.getQueryString());
            String state = burl;
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize";
            String redirectUri = GlobEnvUtil.getWebURL("/weChat/authorize");
            log.info("=================redirectUri:"+redirectUri);
            ResMsg_SWxUtil_GetTokenAndTicket res = (ResMsg_SWxUtil_GetTokenAndTicket)wxUserService.handleMsg(new ReqMsg_SWxUtil_GetTokenAndTicket());
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
    @RequestMapping("/weChat/authorizeNonSilentLicense")
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
            ResMsg_SWxUtil_GetTokenAndTicket res = (ResMsg_SWxUtil_GetTokenAndTicket)wxUserService.handleMsg(new ReqMsg_SWxUtil_GetTokenAndTicket());
            sb.append("&appid=" + res.getAppid() + "&secret=" + res.getSecret()
                      + "&grant_type=authorization_code");
            String accessToken = weChatUtil.sendPost(getAccessTokenUrl, sb.toString(), false,
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
            String userMessage = WeChatUtil.sendGet(getUserMessageUrl, sb1.toString(), false,
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
            manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_WX_HEAD_IMG_URL.getName(), headImgUrl, true);
            manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_WX_NICK.getName(), wxNick, true);
            log.info(System.currentTimeMillis() + "================ 非静默授权 结束 ======================");

            String openid = accessTokenJson.getString("openid");
            manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_OPEN_ID.getName(),
                    openid, true);
            manager.save(response, CookieEnums._SITE.getName(), null, "/", 5*365*24*3600, true);

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
    @RequestMapping("/weChat/getCodeNonSilentLicense")
    public String getCodeNonSilentLicense(HttpServletRequest request, String burl, HttpServletResponse response) {
        try {
            log.info(System.currentTimeMillis() + "================发起请求getCodeNonSilentLicense请求:"
                     + request.getRequestURL() + "?" + request.getQueryString());
            String state = burl;
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize";
            String redirectUri = GlobEnvUtil.getWebURL("/weChat/authorizeNonSilentLicense");
            log.info("=================redirectUri:"+redirectUri);
            ResMsg_SWxUtil_GetTokenAndTicket res = (ResMsg_SWxUtil_GetTokenAndTicket)wxUserService.handleMsg(new ReqMsg_SWxUtil_GetTokenAndTicket());
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
