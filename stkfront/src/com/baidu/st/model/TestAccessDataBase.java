package com.baidu.st.model;

import java.util.Calendar;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.baidu.st.model.TraderecDAO;
import com.baidu.st.model.TraderecId;
import com.baidu.st.model.Traderec;
import com.baidu.st.model.DeltaDataStruc;
import java.util.List;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.text.SimpleDateFormat;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestAccessDataBase {

	public static List seDBBytime(String beginTime, String endTime){
    	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    	TraderecDAO trdao = (TraderecDAO)context.getBean("TraderecDAO");
    	String sqlQuery;
    	sqlQuery = "WHERE time>='" + beginTime +"'" + " and time<=" + "'" + endTime + "'";
    	System.out.println(sqlQuery);
    	return trdao.findStringQuery(sqlQuery);
    }
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		TraderecDAO tr1 = (TraderecDAO)context.getBean("TraderecDAO");
		//String sqlQuery = "WHERE time>='2015-04-29 09:30:00' and time<='2015-04-29 15:00:00'";
		List retList;
		//retList = tr1.findStringQuery(sqlQuery);
		String beginTime = "";
		String endTime = "";
		String sqlQuery;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("input begin time:");
		try {
			beginTime = br.readLine();
		} catch (IOException e) {
			System.out.println("read begin time failed");
		}
		System.out.println("input end time:");
		try {
			endTime = br.readLine();
		} catch (IOException e) {
			System.out.println("read end time failed");
		}
		
		sqlQuery = "WHERE time>='" + beginTime +"'" + " and time<=" + "'" + endTime + "'";
		System.out.println(sqlQuery);
		retList = tr1.findStringQuery(sqlQuery);
		System.out.println("return db list");
		HashMap<String, ArrayList> hmselect = new HashMap<String, ArrayList>();
		HashMap<String, ArrayList> finalmatch = new HashMap<String, ArrayList>();
		for(int i = 0; i < retList.size(); i++) {
			Traderec trtmp = (Traderec)retList.get(i);
			TraderecId trid = trtmp.getId();
			if(hmselect.containsKey(trid.getCode())) {
				if((trid.getOutter() != 0) || (trid.getInner() != 0)) {
					ArrayList tmplist = hmselect.get(trid.getCode());
					int delta = trid.getOutter() - trid.getInner();
					Date deltatime = trid.getTime();
					DeltaDataStruc val = new DeltaDataStruc();
					val.delta = delta;
					val.timeStamp = deltatime;
					val.price = trid.getCurprice();
					tmplist.add(val);
				}
			} else {
				ArrayList reclist = new ArrayList();
				if((trid.getOutter() != 0) || (trid.getInner() != 0)) {
					int delta = trid.getOutter() - trid.getInner();
				/*
				System.out.println("euxyacg key:" + trid.getCode());
				System.out.println("euxyacg outter:" + trid.getOutter());
				System.out.println("euxyacg innter:" + trid.getInner());
				System.out.println("euxyacg delta:" + delta);
				*/
					Date deltatime = trid.getTime();
					DeltaDataStruc val = new DeltaDataStruc();
					val.delta = delta;
					val.timeStamp = deltatime;
					val.price = trid.getCurprice();
					reclist.add(val);
				}
				hmselect.put(trid.getCode(), reclist);
			}
		}
		Iterator iter = hmselect.entrySet().iterator();
		SimpleDateFormat displaydate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String codekey = (String)entry.getKey();
			ArrayList deltalist = (ArrayList)entry.getValue();
			boolean selectflag = true;
			if(deltalist.size() == 0) selectflag = false;
			for(int i = 0; i < deltalist.size(); i++) {
				DeltaDataStruc printret = (DeltaDataStruc)deltalist.get(i);
				if(printret.delta < 0) selectflag = false;
			}
			
			if(selectflag) finalmatch.put(codekey, deltalist);
		}
		Iterator iterfinal = finalmatch.entrySet().iterator();
		System.out.println(finalmatch.size());
		JSONObject json = new JSONObject();
		while(iterfinal.hasNext()) {
			Map.Entry entry = (Map.Entry)iterfinal.next();
			String ck = (String)entry.getKey();
			System.out.println(ck);
			
			ArrayList valdeltalist = (ArrayList)entry.getValue();
			JSONArray jsonarray = new JSONArray();
			for(int i = 0; i < valdeltalist.size(); i++) {
				DeltaDataStruc printret = (DeltaDataStruc)valdeltalist.get(i);
				System.out.println("euxyacg: " + printret.delta);
				System.out.println("euxyacg: " + printret.price);
				System.out.println("euxyacg: " + printret.timeStamp);
			}
			jsonarray.add("{cat:1}");
			jsonarray.add("{dog:2}");
			json.put("test", jsonarray);
			System.out.println(json.toString());
			
		}
		
	}
}
