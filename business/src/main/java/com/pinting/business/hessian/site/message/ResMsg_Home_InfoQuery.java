package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 根据不同显示端口查询币港湾首页所需数据 出参
 * @author huangmengjian
 * @2015-1-16 下午3:22:22
 */
public class ResMsg_Home_InfoQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5239309042692792540L;
	
	// 产品列表(0-3个理财计划)
	private ArrayList<HashMap<String, Object>> recommendProductList;
	
	// 新手推荐产品
	private HashMap<String, Object> noviceRecommendedProduct;
	
	//用户总收入（收益）
	private double totalIncome;

	private double limit; //目前未赋值

	private double ceiling; //目前未赋值 
	
	/*自动红包可领取金额*/
	private double totalRedPacketSubtract;
	
	// 累计注册用户数/累计平台用户数
	private Integer totalRegUser;
	/*最大年化收益率*/
	private String maxRate;
	/*最小年化收益率*/
	private String minRate;
	/*日投资收益*/
	private Double dayInvestEarnings;
	
	//运营报告--首页展示运营报告时间信息
	private ArrayList<HashMap<String, Object>> reportList;

	//活动是否开始
	private boolean activityIsStart;
	
	/*累计投资额*/
	private String totalInvestAmount;

	/* 总投资数量 */
	private Integer investNum;

	/* 当前投资总金额 */
	private Double currTotalAmount;

	public Double getCurrTotalAmount() {
		return currTotalAmount;
	}

	public void setCurrTotalAmount(Double currTotalAmount) {
		this.currTotalAmount = currTotalAmount;
	}

	public Integer getInvestNum() {
		return investNum;
	}

	public void setInvestNum(Integer investNum) {
		this.investNum = investNum;
	}

	public boolean isActivityIsStart() {
		return activityIsStart;
	}

	public void setActivityIsStart(boolean activityIsStart) {
		this.activityIsStart = activityIsStart;
	}

	public Double getDayInvestEarnings() {
        return dayInvestEarnings;
    }

    public void setDayInvestEarnings(Double dayInvestEarnings) {
        this.dayInvestEarnings = dayInvestEarnings;
    }

    public ArrayList<HashMap<String, Object>> getRecommendProductList() {
        return recommendProductList;
    }

    public void setRecommendProductList(ArrayList<HashMap<String, Object>> recommendProductList) {
        this.recommendProductList = recommendProductList;
    }

    public HashMap<String, Object> getNoviceRecommendedProduct() {
        return noviceRecommendedProduct;
    }

    public void setNoviceRecommendedProduct(HashMap<String, Object> noviceRecommendedProduct) {
        this.noviceRecommendedProduct = noviceRecommendedProduct;
    }

    public double getLimit() {
		return limit;
	}

	public void setLimit(double limit) {
		this.limit = limit;
	}

	public double getCeiling() {
		return ceiling;
	}

	public void setCeiling(double ceiling) {
		this.ceiling = ceiling;
	}

	public double getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(double totalIncome) {
		this.totalIncome = totalIncome;
	}

    public double getTotalRedPacketSubtract() {
        return totalRedPacketSubtract;
    }

    public void setTotalRedPacketSubtract(double totalRedPacketSubtract) {
        this.totalRedPacketSubtract = totalRedPacketSubtract;
    }

    public Integer getTotalRegUser() {
        return totalRegUser;
    }

    public void setTotalRegUser(Integer totalRegUser) {
        this.totalRegUser = totalRegUser;
    }

    public String getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(String maxRate) {
        this.maxRate = maxRate;
    }

    public String getMinRate() {
        return minRate;
    }

    public void setMinRate(String minRate) {
        this.minRate = minRate;
    }

	public ArrayList<HashMap<String, Object>> getReportList() {
		return reportList;
	}

	public void setReportList(ArrayList<HashMap<String, Object>> reportList) {
		this.reportList = reportList;
	}

	public String getTotalInvestAmount() {
		return totalInvestAmount;
	}

	public void setTotalInvestAmount(String totalInvestAmount) {
		this.totalInvestAmount = totalInvestAmount;
	}

}
