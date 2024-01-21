package com.pinting.business.facade.site;

import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.pinting.business.enums.Ecup2016TeamsEnum;
import com.pinting.business.hessian.site.message.ReqMsg_Ecup2016Activity_GetChampionSilverList;
import com.pinting.business.hessian.site.message.ReqMsg_Ecup2016Activity_GetEcup2016SupportorList;
import com.pinting.business.hessian.site.message.ReqMsg_Ecup2016Activity_GetEcup2016WinnerList;
import com.pinting.business.hessian.site.message.ReqMsg_Ecup2016Activity_GetUserActInfo;
import com.pinting.business.hessian.site.message.ReqMsg_Ecup2016Activity_SaveUserChampionSilver;
import com.pinting.business.hessian.site.message.ResMsg_Ecup2016Activity_GetChampionSilverList;
import com.pinting.business.hessian.site.message.ResMsg_Ecup2016Activity_GetEcup2016SupportorList;
import com.pinting.business.hessian.site.message.ResMsg_Ecup2016Activity_GetEcup2016WinnerList;
import com.pinting.business.hessian.site.message.ResMsg_Ecup2016Activity_GetUserActInfo;
import com.pinting.business.hessian.site.message.ResMsg_Ecup2016Activity_SaveUserChampionSilver;
import com.pinting.business.model.BsEcup2016Supportor;
import com.pinting.business.model.vo.BsEcup2016ActivityUserInfoVO;
import com.pinting.business.hessian.site.message.ReqMsg_Ecup2016Activity_QueryEcupProductGrid;
import com.pinting.business.hessian.site.message.ReqMsg_Ecup2016Activity_SaveSupportor;
import com.pinting.business.hessian.site.message.ResMsg_Ecup2016Activity_QueryEcupProductGrid;
import com.pinting.business.hessian.site.message.ResMsg_Ecup2016Activity_SaveSupportor;
import com.pinting.business.model.vo.BsProductVO;
import com.pinting.business.service.site.Ecup2016ActivityService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.StringUtil;

/**
 * 2016欧洲杯活动相关
 * @author bianyatian
 * @2016-6-21 下午4:39:56
 */
@Component("Ecup2016Activity")
public class Ecup2016ActivityFacade {
	
	@Autowired
	private Ecup2016ActivityService ecup2016ActivityService;
    
	/**
     * 查询欧洲杯活动标
     * @param req
     * @param res
     * showTerminal  展示端口
     * type  类型（NORMAL：欧洲杯产品；NEW_USER：新用户产品）
     */
    public void queryEcupProductGrid(ReqMsg_Ecup2016Activity_QueryEcupProductGrid req, ResMsg_Ecup2016Activity_QueryEcupProductGrid res) {
        List<BsProductVO> vos = ecup2016ActivityService.queryEcupProductGrid(req.getShowTerminal(), req.getType());
        if(!CollectionUtils.isEmpty(vos)) {
            List<HashMap<String, Object>> grid = BeanUtils.classToArrayList(vos);
            res.setDataGrid(grid);
        } else {
            res.setDataGrid(new ArrayList<HashMap<String, Object>>());
        }
    }
    
    /**
     * 添加助力者（好友助力）
     * @param req
     * @param res
     */
    public void saveSupportor(ReqMsg_Ecup2016Activity_SaveSupportor req, ResMsg_Ecup2016Activity_SaveSupportor res) {
        ecup2016ActivityService.saveSupportor(req.getUserId(), req.getWxId(), req.getWxNick());
    }
    
    
	/**
	 * 用户竞猜结果查询（用户助力值查询+排名查询）
	 * @param req
	 * @param res
	 */
	public void getUserActInfo(ReqMsg_Ecup2016Activity_GetUserActInfo req, ResMsg_Ecup2016Activity_GetUserActInfo res){
		BsEcup2016ActivityUserInfoVO record = ecup2016ActivityService.selectEcup2016ActivityUserInfo(req.getUserId());
		if(record != null && StringUtil.isNotBlank(record.getChampion())){
			Double rate1 = (double)record.getChampionCount()/record.getCount()*100;
			Double rate2 = (double)record.getSilverCount()/record.getCount()*100;
			NumberTool tool = new NumberTool();
			res.setChampion(record.getChampion());
			if(rate1 <1){
				res.setChampionRate(1);
			}else{
				res.setChampionRate(Integer.parseInt(tool.integer(rate1)));
			}
			
			res.setSilver(record.getSilver());
			
			if(rate2 <1){
				res.setSilverRate(1);
			}else{
				res.setSilverRate(Integer.parseInt(tool.integer(rate2)));
			}
			res.setUserId(req.getUserId());
			res.setSupportNum(record.getSupportNum());
			if(record.getSupportNum() < 20){
				res.setSupportRanking(-1);
				res.setOverLuckyNum(500-record.getLuckyNum());
			}else{
				res.setSupportRanking(record.getSupportRanking());
			}
			
			
		}
	}
	
	/**
	 * 保存用户猜冠亚军记录（检查数据完整）
	 * @param req
	 * @param res
	 */
	public void saveUserChampionSilver(ReqMsg_Ecup2016Activity_SaveUserChampionSilver req, ResMsg_Ecup2016Activity_SaveUserChampionSilver res){
		boolean flag = ecup2016ActivityService.saveUserChampionSilver(req.getUserId(), req.getChampion(), req.getSilver());
		res.setFlag(flag);
	}
	
	/**
	 * 冠亚军列表支持率
	 * @param req
	 * @param res
	 */
	public void getChampionSilverList(ReqMsg_Ecup2016Activity_GetChampionSilverList req, ResMsg_Ecup2016Activity_GetChampionSilverList res){
		List<BsEcup2016ActivityUserInfoVO> cList = ecup2016ActivityService.selectEcup2016ChampionList(); //查询的到冠军列表
		List<BsEcup2016ActivityUserInfoVO> sList = ecup2016ActivityService.selectEcup2016SilverList();
		List<BsEcup2016ActivityUserInfoVO> championList = new ArrayList<BsEcup2016ActivityUserInfoVO>();
		List<BsEcup2016ActivityUserInfoVO> silverList = new ArrayList<BsEcup2016ActivityUserInfoVO>();
		if(!CollectionUtils.isEmpty(cList)) {
			//冠军列表不为空
			for(Ecup2016TeamsEnum teams : EnumSet.allOf(Ecup2016TeamsEnum.class)){
				boolean flag = false;
				for (BsEcup2016ActivityUserInfoVO champion : cList) {
					if(champion.getChampion().equals(teams.getTeamName())){
						Double rate= (double)champion.getChampionCount()/champion.getCount()*100;
						NumberTool tool = new NumberTool();
						flag = true;
						if(champion.getChampionCount()>0 && rate< 1){
							champion.setChampionRate(1);
						}else{
							champion.setChampionRate(Integer.parseInt(tool.integer(rate)));
						}
						championList.add(champion);
						
						break;
					}
				}
				if(!flag){
					BsEcup2016ActivityUserInfoVO record = new BsEcup2016ActivityUserInfoVO();
					record.setChampion(teams.getTeamName());
					record.setChampionCount(0);
					record.setChampionRate(0);
					championList.add(record);
				}
			}
            
        } else {
        	//冠军列表为空
        	for(Ecup2016TeamsEnum teams : EnumSet.allOf(Ecup2016TeamsEnum.class)){
        		BsEcup2016ActivityUserInfoVO record = new BsEcup2016ActivityUserInfoVO();
				record.setChampion(teams.getTeamName());
				record.setChampionCount(0);
				record.setChampionRate(0);
				championList.add(record);
        	}
        }
		
		List<HashMap<String, Object>> grid4Champion = BeanUtils.classToArrayList(championList);
        res.setChampionList(grid4Champion);
        
        if(!CollectionUtils.isEmpty(sList)) {
			//亚军列表不为空
			for(Ecup2016TeamsEnum teams : EnumSet.allOf(Ecup2016TeamsEnum.class)){
				boolean flag = false;
				for (BsEcup2016ActivityUserInfoVO silver : sList) {
					if(silver.getSilver().equals(teams.getTeamName())){
						flag = true;
						Double rate= (double)silver.getSilverCount()/silver.getCount()*100;
						NumberTool tool = new NumberTool();
						if(silver.getSilverCount()>0 && rate< 1){
							silver.setSilverRate(1);
						}else{
							silver.setSilverRate(Integer.parseInt(tool.integer(rate)));
						}
						silverList.add(silver);
						break;
					}
				}
				if(!flag){
					BsEcup2016ActivityUserInfoVO record = new BsEcup2016ActivityUserInfoVO();
					record.setSilver(teams.getTeamName());
					record.setSilverCount(0);
					record.setSilverRate(0);
					silverList.add(record);
				}
			}
            
        } else {
        	//亚军列表为空
        	for(Ecup2016TeamsEnum teams : EnumSet.allOf(Ecup2016TeamsEnum.class)){
        		BsEcup2016ActivityUserInfoVO record = new BsEcup2016ActivityUserInfoVO();
        		record.setSilver(teams.getTeamName());
				record.setSilverCount(0);
				record.setSilverRate(0);
				silverList.add(record);
        	}
        }
		
		List<HashMap<String, Object>> grid4Silver = BeanUtils.classToArrayList(silverList);
        res.setSilverList(grid4Silver);
	}
	
	/**
	 * 好友助力列表查询
	 * @param req
	 * @param res
	 */
	public void getEcup2016SupportorList(ReqMsg_Ecup2016Activity_GetEcup2016SupportorList req, 
			ResMsg_Ecup2016Activity_GetEcup2016SupportorList res){
		int count = ecup2016ActivityService.countEcup2016SupportorList(req.getUserId());
		if(count >0){
			List<BsEcup2016Supportor> list = ecup2016ActivityService.getEcup2016SupportorList(req.getUserId(), req.getPageIndex(), req.getPageSize());
			if(list != null && list.size() > 0){
				 res.setList(BeanUtils.classToArrayList(list));
			}
			res.setTotalCount((int)Math.ceil((double)count/req.getPageSize()));
		}
		res.setCount(count);
		res.setPageIndex(req.getPageIndex());
	}
	
	/**
	 * 助力值排行榜查询
	 * @param req
	 * @param res
	 */
	public void getEcup2016WinnerList(ReqMsg_Ecup2016Activity_GetEcup2016WinnerList req,
			ResMsg_Ecup2016Activity_GetEcup2016WinnerList res){
		int count = ecup2016ActivityService.countEcup2016WinnerList();
		if(count >0){
			List<BsEcup2016ActivityUserInfoVO> list = ecup2016ActivityService.getEcup2016WinnerList(req.getPageIndex(), req.getPageSize());
			if(list != null && list.size() > 0){
				 res.setList(BeanUtils.classToArrayList(list));
			}
			res.setTotalCount((int)Math.ceil((double)count/req.getPageSize()));
		}
		res.setCount(count);
		res.setPageIndex(req.getPageIndex());
	}
}
