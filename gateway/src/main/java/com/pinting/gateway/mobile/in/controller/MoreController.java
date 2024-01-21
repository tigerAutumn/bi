package com.pinting.gateway.mobile.in.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.hessian.site.message.*;
import com.pinting.open.pojo.request.more.*;
import com.pinting.open.pojo.response.more.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.business.out.service.AppMoreBusinessService;
import com.pinting.gateway.mobile.in.enums.OpenMessageEnum;
import com.pinting.gateway.mobile.in.util.Constants;
import com.pinting.open.base.controller.ControllerCallBack;
import com.pinting.open.base.controller.OpenController;
import com.pinting.open.base.exception.OpenException;
import com.pinting.open.base.request.Request;
import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.more.BsUser;
import com.pinting.open.pojo.model.more.News;
import com.pinting.open.pojo.request.asset.NewVersionRequest;
import com.pinting.open.pojo.response.asset.NewVersionResponse;

@Controller
@Scope("prototype")
@RequestMapping("mobile/more")
public class MoreController extends OpenController {

	@Autowired
	private AppMoreBusinessService appMoreBusinessService;
	
	/**
	 * 意见反馈
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
    @RequestMapping("feedback/v_1.0.0")
    public String feedback(HttpServletRequest req, HttpServletResponse resp) {
        return this.execute(req, FeedbackRequest.class, new ControllerCallBack() {
            
            @Override
            public void doCheck(Request request, Response response) {
                super.doCheck(request, response);
                FeedbackRequest feedbackRequest = (FeedbackRequest)request;
                if(null == feedbackRequest.getUserId()) {
                    throw new OpenException(OpenMessageEnum.USER_ID_EMPTY.getCode(),OpenMessageEnum.USER_ID_EMPTY.getMessage());
                }
                if(StringUtil.isBlank(feedbackRequest.getInfo())) {
                    throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
                }
                if(feedbackRequest.getInfo().length() > 500) {
                    throw new OpenException(OpenMessageEnum.LENGTH_LARGE_ERROR.getCode(),OpenMessageEnum.LENGTH_LARGE_ERROR.getMessage());
                }
            }
            
            @Override
            public void doOperation(Request request, Response response) {
                FeedbackRequest feedbackRequest = (FeedbackRequest)request;
                FeedbackResponse feedbackResponse = (FeedbackResponse)response;
                ReqMsg_User_Feedback reqMsg = new ReqMsg_User_Feedback();
                reqMsg.setVersion("1.0.0");
                reqMsg.setInfo(feedbackRequest.getInfo());
                reqMsg.setUserId(feedbackRequest.getUserId());
                ResMsg_User_Feedback resMsg = appMoreBusinessService.feedback(reqMsg);
                if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())){
                    feedbackResponse.setSuccess(true);
                } else {
                    feedbackResponse.setSuccess(false);
                    throw new OpenException(resMsg.getResCode(), resMsg.getResMsg());
                }
            }
        });
	}
	
	/**
	 * 我的推荐
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
	@RequestMapping("myRecommend/v_1.0.0")
	public String myRecommend(HttpServletRequest req, HttpServletResponse resp) {
	    return this.execute(req, MyRecommendRequest.class, new ControllerCallBack() {
	        
	        @Override
	        public void doCheck(Request request, Response response) {
	            super.doCheck(request, response);
	            MyRecommendRequest myRecommendRequest = (MyRecommendRequest) request;
	            if(null == myRecommendRequest.getUserId()) {
                    throw new OpenException(OpenMessageEnum.USER_ID_EMPTY.getCode(),OpenMessageEnum.USER_ID_EMPTY.getMessage());
                }
	            if(null == myRecommendRequest.getPage()) {
                    throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
                }
	        }
	        
            @Override
            public void doOperation(Request request, Response response) {
                MyRecommendRequest myRecommendRequest = (MyRecommendRequest) request;
                MyRecommendResponse recommendResponse = (MyRecommendResponse) response;
                ReqMsg_User_BsSubUserListQuery req = new ReqMsg_User_BsSubUserListQuery();
                req.setVersion("1.0.0");
                req.setUserId(myRecommendRequest.getUserId());
                req.setPageIndex(myRecommendRequest.getPage());
                req.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
                List<BsUser> dataList = new ArrayList<BsUser>();
                ResMsg_User_BsSubUserListQuery resMsg = appMoreBusinessService.queryMyRecommend(req);
                
                if (null != resMsg.getBsUserList() && 0 != resMsg.getBsUserList().size()) {
                    for (HashMap<String, Object> map : resMsg.getBsUserList()) {
                        BsUser bsUser = new BsUser();
                        bsUser.setMobile((String)map.get("mobile"));
                        bsUser.setRegisterTime(DateUtil.format((Date)map.get("registerTime")));
                        if(map.get("firstBuyTime") instanceof Date){
                            Date date = (Date)map.get("firstBuyTime");
                            if(date != null) {
                                bsUser.setStatus(true);
                            } else {
                                bsUser.setStatus(false);
                            }
                        } else if(map.get("firstBuyTime") instanceof String){
                            if(!StringUtil.isBlank((String)map.get("firstBuyTime"))) {
                                bsUser.setStatus(true);
                            } else {
                                bsUser.setStatus(false);
                            }
                        }
                        dataList.add(bsUser);
                    }
                } 
                recommendResponse.setTotalPage(resMsg.getTotalCount());
                recommendResponse.setDataList(dataList);
            }
        });
	}
	
	/**
	 * 推荐奖励金
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
    @RequestMapping("recommend/v_1.0.0")
    public String recommend(HttpServletRequest req, HttpServletResponse resp) {
        return this.execute(req, RecommendRequest.class, new ControllerCallBack() {
            
            @Override
            public void doCheck(Request request, Response response) {
                super.doCheck(request, response);
            }
            
            @Override
            public void doOperation(Request request, Response response) {
                RecommendResponse recommendResponse = (RecommendResponse) response;
                recommendResponse.setTitle(Constants.SHARE_TITLE);
                recommendResponse.setContent(Constants.SHARE_CONTENT);
                recommendResponse.setLogo(Constants.SHARE_LOGO);
            }
        });
    }
	
	/**
	 * 检查新版本
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
    @RequestMapping("newVersion/v_1.0.0")
    public String newVersion(HttpServletRequest req, HttpServletResponse resp) {
        return this.execute(req, NewVersionRequest.class, new ControllerCallBack() {
            
            @Override
            public void doCheck(Request request, Response response) {
            	NewVersionRequest versionRequest = (NewVersionRequest) request;
                if(StringUtil.isBlank(versionRequest.getAppType())) {
                    throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
                }
            }
            
            @Override
            public void doOperation(Request request, Response response) {
            	NewVersionRequest versionRequest = (NewVersionRequest) request;
            	NewVersionResponse versionResponse = (NewVersionResponse) response;
            	String appVersion = StringUtil.isBlank(versionRequest.getAppVersion())?"":versionRequest.getAppVersion();
            	ReqMsg_System_NewVersion reqMsg = new ReqMsg_System_NewVersion();
            	reqMsg.setVersion("1.0.0");
            	reqMsg.setAppType(versionRequest.getAppType());
            	reqMsg.setAppVersion(appVersion);
            	ResMsg_System_NewVersion resMsg = appMoreBusinessService.newVersion(reqMsg);
            	versionResponse.setAppType(resMsg.getAppType());
            	versionResponse.setContent(resMsg.getContent());
            	versionResponse.setIsMandatory(resMsg.getIsMandatory());
            	versionResponse.setUrl(resMsg.getUrl());
            	versionResponse.setVersion(resMsg.getAppVersion());
            }
        });
    }
	
	/**
	 * 港湾资讯
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("newsList/v_1.0.0")
	@ResponseBody
	public String newsList(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, NewsListRequest.class, new ControllerCallBack() {
			
			@Override
            public void doCheck(Request request, Response response) {
				NewsListRequest newsRequest = (NewsListRequest) request;
                if(newsRequest.getPage() == null) {
                    throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
                }
            }
			
			@Override
			public void doOperation(Request request, Response response) {
				NewsListRequest newsRequest = (NewsListRequest) request;
				NewsListResponse newsResponse = (NewsListResponse) response;
				ReqMsg_News_QueryNewsPage reqMsg = new ReqMsg_News_QueryNewsPage();
				reqMsg.setVersion("1.0.0");
				reqMsg.setNumPerPage(20);
				reqMsg.setPageNum(newsRequest.getPage());
				reqMsg.setReceiverType("BGW");
				reqMsg.setType("not_notice");
				ResMsg_News_QueryNewsPage resMsg = appMoreBusinessService.queryNewsList(reqMsg);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
					List<News> list = new ArrayList<News>();
					for(Map<String,Object> map : resMsg.getDataGrid()) {
						News news = new News();
						news.setPublishTime(DateUtil.formatYYYYMMDD((Date)map.get("publishTime")));
						news.setSubject((String)map.get("subject"));
						news.setId((Integer)map.get("id"));
						news.setType((String)map.get("type"));
						list.add(news);
					}
					newsResponse.setDataList(list);
					newsResponse.setTotalPage(resMsg.getCount()%20==0?resMsg.getCount()/20:resMsg.getCount()/20+1);
				}
				else {
					throw new OpenException(resMsg.getResCode(),resMsg.getResMsg());
				}
			}
		});
	}
	
	/**
	 * 港湾资讯详情
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("newsDetail/v_1.0.0")
	@ResponseBody
	public String newsDetail(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, NewsDetailRequest.class, new ControllerCallBack() {
			
			@Override
            public void doCheck(Request request, Response response) {
				NewsDetailRequest newsRequest = (NewsDetailRequest) request;
                if(newsRequest.getId() == null || StringUtil.isBlank(newsRequest.getType())) {
                    throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
                }
            }
			
			@Override
			public void doOperation(Request request, Response response) {
				NewsDetailRequest newsRequest = (NewsDetailRequest) request;
				NewsDetailResponse newsResponse = (NewsDetailResponse) response;
				ReqMsg_News_Details req = new ReqMsg_News_Details();
				req.setId(newsRequest.getId());
				req.setType(newsRequest.getType());
				req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW);
				req.setVersion("1.0.0");
				ResMsg_News_Details res = appMoreBusinessService.queryNewsDetail(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					HashMap<String, Object> map = res.getDetails();
					newsResponse.setContent((String)map.get("content"));
					newsResponse.setId((Integer)map.get("id"));
					newsResponse.setPublishTime(DateUtil.formatYYYYMMDD((Date)map.get("publishTime")));
					newsResponse.setSubject((String)map.get("subject"));
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}

	/**
	 * 港湾资讯、平台公告-列表
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("newsList/v_1.0.1")
	@ResponseBody
	public String newsList_1(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, NewsListInfoRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				NewsListInfoRequest newsRequest = (NewsListInfoRequest) request;
				if(newsRequest.getPage() == null || newsRequest.getType() == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				NewsListInfoRequest newsRequest = (NewsListInfoRequest) request;
				NewsListInfoResponse newsResponse = (NewsListInfoResponse) response;
				ReqMsg_News_QueryNewsPageInfo reqMsg = new ReqMsg_News_QueryNewsPageInfo();
				reqMsg.setVersion("1.0.1");
				reqMsg.setNumPerPage(20);
				reqMsg.setPageNum(newsRequest.getPage());
				reqMsg.setReceiverType("BGW");
				reqMsg.setType(newsRequest.getType());
				ResMsg_News_QueryNewsPageInfo resMsg = appMoreBusinessService.queryNewsListInfo(reqMsg);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
					List<News> list = new ArrayList<News>();
					for(Map<String,Object> map : resMsg.getDataGrid()) {
						News news = new News();
						news.setPublishTime(DateUtil.formatYYYYMMDD((Date)map.get("publishTime")));
						news.setSubject((String)map.get("subject"));
						news.setId((Integer)map.get("id"));
						news.setType((String)map.get("type"));
						news.setSummary((String)map.get("summary"));
						if(Constants.SYS_NEWS_TYPE_NOTICE.equals((String)map.get("type"))) {
							news.setContent((String)map.get("content"));
						}
						if(!Constants.SYS_NEWS_TYPE_NOTICE.equals((String)map.get("type"))) {
							if(StringUtil.isNotBlank((String)map.get("subjectImg"))) {
								news.setSubjectImg((String)map.get("subjectImg"));
							}else {
								news.setSubjectImg("");
							}
						}
						list.add(news);
					}
					newsResponse.setDataList(list);
					newsResponse.setTotalPage(resMsg.getCount()%20==0?resMsg.getCount()/20:resMsg.getCount()/20+1);
				}
				else {
					throw new OpenException(resMsg.getResCode(),resMsg.getResMsg());
				}
			}
		});
	}
	
	/**
	 * 风险测评信息
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("myQuestionnaire/v_1.0.0")
	@ResponseBody
	public String myQuestionnaire(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, MyQuestionnaireRequest.class, new ControllerCallBack() {
			
			@Override
            public void doCheck(Request request, Response response) {
				super.doCheck(request, response);
				MyQuestionnaireRequest questionnaireRequest = (MyQuestionnaireRequest)request;
                if(null == questionnaireRequest.getUserId()) {
                    throw new OpenException(OpenMessageEnum.USER_ID_EMPTY.getCode(), OpenMessageEnum.USER_ID_EMPTY.getMessage());
                }
            }
			
			@Override
            public void doOperation(Request request, Response response) {
                MyQuestionnaireRequest questionnaireRequest = (MyQuestionnaireRequest) request; 
				MyQuestionnaireResponse questionnaireResponse = (MyQuestionnaireResponse) response;
				ReqMsg_User_Questionnaire reqMsg = new ReqMsg_User_Questionnaire();
                reqMsg.setVersion("1.0.0");
                reqMsg.setUserId(questionnaireRequest.getUserId());
                ResMsg_User_Questionnaire resMsg = appMoreBusinessService.myQuestionnaire(reqMsg);
                if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())){
                	questionnaireResponse.setSuccess(true);
                	questionnaireResponse.setHasQuestionnaire(resMsg.getHasQuestionnaire());
                	questionnaireResponse.setIsExpired(resMsg.getIsExpired());
                	questionnaireResponse.setAssessType(resMsg.getAssessType());
                } else {
                	questionnaireResponse.setSuccess(false);
                    throw new OpenException(resMsg.getResCode(), resMsg.getResMsg());
                }
            }
		});
	}	
		
	/**
	 * 进行风险测评
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("questionnaire/v_1.0.0")
	@ResponseBody
	public String questionnaire(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, QuestionnaireRequest.class, new ControllerCallBack() {
			
			@Override
            public void doCheck(Request request, Response response) {
				super.doCheck(request, response);
				QuestionnaireRequest questionnaireRequest = (QuestionnaireRequest)request;
                if (null == questionnaireRequest.getUserId()) {
                    throw new OpenException(OpenMessageEnum.USER_ID_EMPTY.getCode(), OpenMessageEnum.USER_ID_EMPTY.getMessage());
                }
                if (questionnaireRequest.getScore() == null) {
                	throw new OpenException(OpenMessageEnum.QUESTIONNAIRE_SCORE_ERROR.getCode(), OpenMessageEnum.QUESTIONNAIRE_SCORE_ERROR.getMessage());
                }
            }
			
			@Override
            public void doOperation(Request request, Response response) {
                QuestionnaireRequest questionnaireRequest = (QuestionnaireRequest) request; 
				QuestionnaireResponse questionnaireResponse = (QuestionnaireResponse) response;
				ReqMsg_User_SaveQuestionnaire reqMsg = new ReqMsg_User_SaveQuestionnaire();
                reqMsg.setVersion("1.0.0");
                reqMsg.setUserId(questionnaireRequest.getUserId());
                reqMsg.setScore(questionnaireRequest.getScore());
                ResMsg_User_SaveQuestionnaire resMsg = appMoreBusinessService.saveQuestionnaire(reqMsg);
                if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())){
                	questionnaireResponse.setSuccess(true);
                	questionnaireResponse.setAssessType(resMsg.getAssessType());
                } else {
                	questionnaireResponse.setSuccess(false);
                    throw new OpenException(resMsg.getResCode(), resMsg.getResMsg());
                }
            }
		});
	}	
	
	/**
	 * 风险测评信息再次打开
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("questionnaireMore/v_1.0.0")
	@ResponseBody
	public String questionnaireMore(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, QuestionnaireMoreRequest.class, new ControllerCallBack() {
			
			@Override
            public void doCheck(Request request, Response response) {
				super.doCheck(request, response);
				QuestionnaireMoreRequest questionnaireRequest = (QuestionnaireMoreRequest)request;
                if(null == questionnaireRequest.getUserId()) {
                    throw new OpenException(OpenMessageEnum.USER_ID_EMPTY.getCode(), OpenMessageEnum.USER_ID_EMPTY.getMessage());
                }
            }
			
			@Override
            public void doOperation(Request request, Response response) {
                QuestionnaireMoreRequest questionnaireMoreRequest = (QuestionnaireMoreRequest) request; 
				QuestionnaireMoreResponse questionnaireMoreResponse = (QuestionnaireMoreResponse) response;
				ReqMsg_User_QuestionnaireMoreQuery reqMsg = new ReqMsg_User_QuestionnaireMoreQuery();
                reqMsg.setVersion("1.0.0");
                reqMsg.setUserId(questionnaireMoreRequest.getUserId());
                ResMsg_User_QuestionnaireMoreQuery resMsg = appMoreBusinessService.questionnaireMore(reqMsg);
                if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())){
                	questionnaireMoreResponse.setSuccess(true);
                	questionnaireMoreResponse.setHasQuestionnaire(resMsg.getHasQuestionnaire());
                	questionnaireMoreResponse.setAssessType(resMsg.getAssessType());
                	questionnaireMoreResponse.setExpireTime(resMsg.getExpireTime());
                } else {
                	questionnaireMoreResponse.setSuccess(false);
                    throw new OpenException(resMsg.getResCode(), resMsg.getResMsg());
                }
            }
		});
	}	
	
	/**
	 * 已获得推荐奖励
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	@ResponseBody
    @RequestMapping("recommendAmount/v_1.0.0")
    public String recommendAmount(HttpServletRequest req, HttpServletResponse resp) {
        return this.execute(req, RecommendAmountRequest.class, new ControllerCallBack() {
            
            @Override
            public void doCheck(Request request, Response response) {
                super.doCheck(request, response);
                RecommendAmountRequest recommendAmountRequest = (RecommendAmountRequest)request;
                if(null == recommendAmountRequest.getUserId()) {
                    throw new OpenException(OpenMessageEnum.USER_ID_EMPTY.getCode(), OpenMessageEnum.USER_ID_EMPTY.getMessage());
                }
            }
            
            @Override
            public void doOperation(Request request, Response response) {
            	RecommendAmountRequest recommendAmountRequest = (RecommendAmountRequest)request;
            	RecommendAmountResponse recommendAmountResponse = (RecommendAmountResponse)response;
                ReqMsg_User_RecommendAmount reqMsg = new ReqMsg_User_RecommendAmount();
                reqMsg.setVersion("1.0.0");
                reqMsg.setUserId(recommendAmountRequest.getUserId());
                ResMsg_User_RecommendAmount resMsg = appMoreBusinessService.recommendAmount(reqMsg);
                if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())){
                	recommendAmountResponse.setSuccess(true);
                	recommendAmountResponse.setRecommendAmountTotal(resMsg.getRecommendAmountTotal());
                } else {
                	recommendAmountResponse.setSuccess(false);
                    throw new OpenException(resMsg.getResCode(), resMsg.getResMsg());
                }
            }
        });
    }


	/**
	 * 平台数据
	 * @param req
	 * @param res
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/platformData/v_1.0.0")
	public String platformData(HttpServletRequest req, HttpServletResponse res) {

		return this.execute(req, PlatformDataRequest.class, new ControllerCallBack() {
			@Override
			public void doCheck(Request request, Response response) {
				super.doCheck(request, response);
				PlatformDataRequest platformDataRequest = (PlatformDataRequest)request;
				if(null == platformDataRequest.getType()) {
					throw new OpenException(OpenMessageEnum.EMPTY_PLATFORM_TYPE.getCode(), OpenMessageEnum.EMPTY_PLATFORM_TYPE.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				PlatformDataRequest platformDataRequest = (PlatformDataRequest)request;
				PlatformDataResponse platformDataResponse = (PlatformDataResponse)response;

				ReqMsg_Invest_PlatformData platformDataReq = new ReqMsg_Invest_PlatformData();
				platformDataReq.setType(platformDataRequest.getType());
				platformDataReq.setVersion("1.0.0");
				ResMsg_Invest_PlatformData res = appMoreBusinessService.platformData(platformDataReq);

				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())){
					platformDataResponse.setSuccess(true);
					if(res.getAmount() != null) {
						platformDataResponse.setValue(String.valueOf(res.getAmount()));
					} else if(res.getTimes() != null) {
						platformDataResponse.setValue(String.valueOf(res.getTimes()));
					} else {
						platformDataResponse.setValue("0");
					}
				} else {
					platformDataResponse.setSuccess(false);
					throw new OpenException(res.getResCode(), res.getResMsg());
				}
			}
		});
	}


	/**
	 * 风险测评
	 * @param req
	 * @param res
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/risk/v_1.0.0")
	public String risk(HttpServletRequest req, HttpServletResponse res) {

		return this.execute(req, RiskRequest.class, new ControllerCallBack() {
			@Override
			public void doCheck(Request request, Response response) {
				super.doCheck(request, response);
				RiskRequest riskRequest = (RiskRequest)request;
				if(StringUtil.isBlank(riskRequest.getUserId())) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(), OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				RiskRequest riskRequest = (RiskRequest)request;
				RiskResponse riskResponse = (RiskResponse)response;

				ReqMsg_DepGuide_Risk req = new ReqMsg_DepGuide_Risk();
				req.setUserId(Integer.parseInt(riskRequest.getUserId()));
				req.setVersion("1.0.0");
				ResMsg_DepGuide_Risk res = appMoreBusinessService.risk(req);

				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())){
					riskResponse.setSuccess(true);
					riskResponse.setRiskStatus(res.getRiskStatus());
				} else {
					riskResponse.setSuccess(false);
					throw new OpenException(res.getResCode(), res.getResMsg());
				}
			}
		});
	}
}