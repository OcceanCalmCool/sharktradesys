package com.baidu.st.model;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.text.SimpleDateFormat;  
import java.util.Calendar;  
import java.util.Date; 
import java.text.ParseException;

public class TestJsonData {
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String testval = "{\"bt\":\"Mon May 04 2015 15:44:23 GMT+0800\",\"et\":\"Tue May 12 2015 15:44:23 GMT+0800\"}";
		JSONObject obj = JSONObject.fromObject(testval);
		String bt = obj.getString("bt");
		String et = obj.getString("et");
		System.out.println(bt);
		System.out.println(et);
		HashMap<String, String> tmpday = new HashMap<String, String>();
		tmpday.put("Jan", "01");
		tmpday.put("Feb", "02");
		tmpday.put("Mar", "03");
		tmpday.put("Apr", "04");
		tmpday.put("May", "05");
		tmpday.put("Jun", "06");
		tmpday.put("Jul", "07");
		tmpday.put("Aug", "08");
		tmpday.put("Sep", "09");
		tmpday.put("Oct", "10");
		tmpday.put("Nov", "11");
		tmpday.put("Dec", "12");
		String[] bts = bt.split(" ");
		String btstr = bts[3] + "-" + tmpday.get(bts[1]) + "-" + bts[2] + "-" + bts[4];
		System.out.println(btstr);
		for(int i = 0; i < bts.length; i++) {
			System.out.println(bts[i]);
		}
	}

		

}
