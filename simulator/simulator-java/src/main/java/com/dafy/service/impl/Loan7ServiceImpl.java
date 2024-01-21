package com.dafy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dafy.core.util.TxtUtil;
import com.dafy.model.vo.loan7.QueryBillResModel;
import com.dafy.service.Loan7Service;
import org.springframework.stereotype.Service;
@Service
public class Loan7ServiceImpl implements Loan7Service {

	@Override
	public QueryBillResModel queryBill(String loanId) {
		
		try {
			String filePath = ("D:/self/loan7/"+loanId+".txt");
			String jsonString = TxtUtil.readTxtFile(filePath);
			QueryBillResModel queryBillResModel = JSONObject.parseObject(jsonString, QueryBillResModel.class);//对于javabean直接给出class实例
			return queryBillResModel;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
