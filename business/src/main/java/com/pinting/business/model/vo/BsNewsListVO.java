package com.pinting.business.model.vo;

import java.util.List;

import com.pinting.business.model.BsSysNews;

public class BsNewsListVO implements java.io.Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1480217922691749261L;


	private List<BsSysNews> currentNews;
	
	
	private List<BsSysNews> latestNews;


	public List<BsSysNews> getCurrentNews() {
		return currentNews;
	}


	public void setCurrentNews(List<BsSysNews> currentNews) {
		this.currentNews = currentNews;
	}


	public List<BsSysNews> getLatestNews() {
		return latestNews;
	}


	public void setLatestNews(List<BsSysNews> latestNews) {
		this.latestNews = latestNews;
	}
	
	

}
