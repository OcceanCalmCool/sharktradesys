package com.baidu.st.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStream;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.text.SimpleDateFormat;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baidu.st.model.TraderecDAO;
import com.baidu.st.model.TraderecId;
import com.baidu.st.model.Traderec;
import com.baidu.st.model.DeltaDataStruc;

import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestServlet extends HttpServlet{
	
	    protected void doGet(HttpServletRequest request,  
	            HttpServletResponse response) throws ServletException, IOException {  
	        request.setCharacterEncoding("UTF-8");  
	        response.setCharacterEncoding("UTF-8");  
	        PrintWriter out = response.getWriter();  
	        response.setContentType("text/html;charset=UTF-8");  
	        String name = request.getParameter("name");  
	        out.println("Hello " + name);  
	        out.println("This is the output from doGet method!");  
	    }  
	    
	    protected void doPost(HttpServletRequest request,  
	            HttpServletResponse response) throws ServletException, IOException {  
	        request.setCharacterEncoding("UTF-8"); 
	        String rtval = processRequest(request);
	        response.setCharacterEncoding("UTF-8");
	        String[] timepara = parseJsonTime(rtval);
	        /*timepara[0]:begintime, timepara[1]:endtime */
	        PrintWriter out = response.getWriter();
	        List stocknumber = seDBBytime(timepara[0], timepara[1]);
	        HashMap<String, ArrayList> selectstock = deltaincMode(stocknumber);
	        JSONObject retjson = new JSONObject();
	        Iterator iter = selectstock.entrySet().iterator();
	        out.println(selectstock.size());
	        
	        while(iter.hasNext()) {
	        	Map.Entry entry = (Map.Entry) iter.next();
	        	String codekey = (String)entry.getKey();
	        	ArrayList stklist = (ArrayList)entry.getValue();
	        	JSONArray jsonarray = new JSONArray();
	        	for(int i = 0; i < stklist.size(); i++) {
	        		DeltaDataStruc tmp = (DeltaDataStruc)stklist.get(i);
	        		String strdelta = "{" + "delta:" + String.valueOf(tmp.delta) + "}";
	        		String strprice = "{" + "price:" + String.valueOf(tmp.price) + "}";
	        		String strtimestamp = "{" + "timestamp:" + String.valueOf(tmp.timeStamp) + "}";
	        		jsonarray.add(strdelta);
	        		jsonarray.add(strprice);
	        		jsonarray.add(strtimestamp);
	        	}
	        	retjson.put(codekey, jsonarray);
	        }
	        
	        response.setContentType("application/json");
	        
	        try {
	        	response.getWriter().write(retjson.toString());
	        	response.getWriter().flush();
	        	response.getWriter().close();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	    } 
	    public String[] parseJsonTime(String reqtime){
	    	JSONObject obj = JSONObject.fromObject(reqtime);
			String bt = obj.getString("bt");
			String et = obj.getString("et");
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
			String[] ets = et.split(" ");
			String btstr = bts[3] + "-" + tmpday.get(bts[1]) + "-" + bts[2] + " " + bts[4];
			String etstr = ets[3] + "-" + tmpday.get(ets[1]) + "-" + ets[2] + " " + ets[4];
			String[] ret = new String[]{btstr, etstr};
			return ret;
	    }
	    
	    public List seDBBytime(String beginTime, String endTime){
	    	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	    	TraderecDAO trdao = (TraderecDAO)context.getBean("TraderecDAO");
	    	String sqlQuery = new String();
	    	sqlQuery = "WHERE time>='" + beginTime +"'" + " and time<=" + "'" + endTime + "'";
	    	return trdao.findStringQuery(sqlQuery);
	    }
	    
	    public HashMap<String, ArrayList> deltaincMode(List rec) {
	    	HashMap<String, ArrayList> hmselect = new HashMap<String, ArrayList>();
			HashMap<String, ArrayList> finalmatch = new HashMap<String, ArrayList>();
			for(int i = 0; i < rec.size(); i++) {
				Traderec trtmp = (Traderec)rec.get(i);
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
			return finalmatch;
	    }
	    
	    protected String processRequest(HttpServletRequest request) {
	    	try {
                	request.setCharacterEncoding("UTF-8");
                	int size = request.getContentLength();
                	System.out.println(size);
                	InputStream is = request.getInputStream();
                	byte[] reqBodyBytes = readBytes(is, size);
                	String res = new String(reqBodyBytes);
                	return res;
        		} catch (Exception e) {
        			return "faile to pasrse requset";
        	}
	    }
	    
	    public static final byte[] readBytes(InputStream is, int contentLen) {
            if (contentLen > 0) {
                    int readLen = 0;
                    int readLengthThisTime = 0;
                    byte[] message = new byte[contentLen];
                    try {
                            while (readLen != contentLen) {
                                    readLengthThisTime = is.read(message, readLen, contentLen
                                                    - readLen);
                                    if (readLengthThisTime == -1) {// Should not happen.
                                            break;
                                    }
                                    readLen += readLengthThisTime;
                            }
                            return message;
                    } catch (IOException e) {
                            // Ignore
                            // e.printStackTrace();
                    }
            }
            return new byte[] {};
	    }
}