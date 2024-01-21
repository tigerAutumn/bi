package com.dafy.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

import com.dafy.model.vo.QueryBillResModel;
import com.dafy.service.YunSelfLoanService;
import com.google.gson.Gson;

@Service
public class YunSelfLoanServiceImpl implements YunSelfLoanService {


	@Override
	public QueryBillResModel queryBill(String userId, String loanId) {
		
		try {
			String filePath = ("D:/self/"+loanId+".txt");
			String jsonString = readTxtFile(filePath);
			
			Gson gson = new Gson();
			QueryBillResModel queryBillResModel = gson.fromJson(jsonString, QueryBillResModel.class);//对于javabean直接给出class实例
			QueryBillResModel resModel = new QueryBillResModel();
			resModel = queryBillResModel;
			return resModel;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
    public static String readTxtFile(String filePath){
        try {
                String encoding="GBK";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    String text = "";
                    while((lineTxt = bufferedReader.readLine()) != null){
                    	text = text + lineTxt;
                    }
                    read.close();
                    return text;
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return null;
    }

}
