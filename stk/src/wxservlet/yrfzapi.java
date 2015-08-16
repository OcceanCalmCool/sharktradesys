package wxservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.justep.baas.data.ColumnValue;
import com.justep.baas.data.Row;
import com.justep.baas.data.RowState;
import com.justep.baas.data.Table;
import com.justep.baas.data.Util;

/**
 * Servlet implementation class yrfzapi
 */
public class yrfzapi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DATASOURCE_STK = "jdbc/ruyifund";
	private static final String TABLE_TRADE_DATA = "dailysearch";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public yrfzapi() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String action = request.getParameter("action");
			if ("queryOrder".equals(action)) {
				queryOrder(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	private static void queryOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, NamingException {
		// 参数序列化
		JSONObject params = (JSONObject) JSONObject.parse(request.getParameter("params"));

		// 获取参数
		Object columns = params.get("columns"); // 列定义
		Integer limit = params.getInteger("limit"); // 分页查询的行数
		Integer offset = params.getInteger("offset"); // 分页查询的行偏移
		String search = params.getString("search"); // 检索关键字

		// 获取params里面json格式的前端数据
		// {gonetime:"",range:"",tstep:"",modelid:""}
		JSONObject reqSelectModel = (JSONObject) JSONObject.parse(search);

		// 这里不能直接用out打印debug日志，会影响输出导致数据无法加载
		Integer gonetime = reqSelectModel.getInteger("gonetime");

		// 计算当前时间根据前端的搜索时间按照天粒度倒退回去
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String endtime = df.format(d);
		String starttime = df.format(new Date(d.getTime() - gonetime * 24 * 60 * 60 * 1000));
		Integer modeltype = reqSelectModel.getInteger("modelid");

		// 存放SQL中的参数值
		List<Object> sqlParams = new ArrayList<Object>();
		// 存放SQL中的过滤条件
		List<String> filters = new ArrayList<String>();
		if (!Util.isEmptyString(String.valueOf(gonetime))) {
			// 增加过滤条件
			filters.add("time BETWEEN " + "'" + starttime + "'" + " AND " + "'" + endtime + "'" + " AND modeltype LIKE ?");
			// 检索关键字中如果没有%，则前后自动加%
			search = String.valueOf(modeltype);
			search = (search.indexOf("%") != -1) ? search : "%" + search + "%";
			// sqlParams的参数个数和顺序必须与过滤条件的?相匹配
			for (int i = 0; i < 1; i++) {
				sqlParams.add(search);
			}

			Table table = null;
			// 获取数据源连接
			Connection conn = Util.getConnection(DATASOURCE_STK);
			try {
				// 执行单表数据查询，返回Table
				table = Util.queryData(conn, TABLE_TRADE_DATA, columns, filters, "", sqlParams, offset, limit);
			} finally {
				// 必须关闭数据源连接
				conn.close();
			}
			// 输出Table做为返回结果，这里会自动转换为Table的JSON格式
			// 添加HashMap作为merge的数据结构
			Object tcolobj = null;
			Object mcolobj = null;
			Integer rownum = 0;
			Map<String, Integer> stkmap = new HashMap<String, Integer>();
			for (Row row : table.getRows()) {
				String[] stklist = (row.getValue("searchret")).toString().split(",");
				tcolobj = row.getValue("time");
				mcolobj = row.getValue("modeltype");
				// 遍历stklist填充stkmap
				for (int i = 0; i < stklist.length; i++) {
					if (stkmap.containsKey(stklist[i])) {
						Integer appear = stkmap.get(stklist[i]);
						appear++;
						stkmap.remove(stklist[i]);
						stkmap.put(stklist[i], appear);
					} else {
						Integer appear = new Integer("1");
						stkmap.put(stklist[i], appear);
					}
				}
				rownum++;
			}
			String retstr = new String();
			if (!stkmap.isEmpty()) { 
				Iterator it = stkmap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry entry = (Map.Entry) it.next();
					Integer tmpcnt = (Integer) entry.getValue();
					if (tmpcnt.equals(rownum)) {
						retstr = retstr + (String) entry.getKey() + ",";
					}
				}
				retstr = retstr.substring(0, retstr.length() - 1);
			}
			System.out.println(retstr);
			// 新添加一个Row的数据返回前端，作为table的last row
			Map<String, ColumnValue> values = new HashMap<String, ColumnValue>();
			values.put("time", new ColumnValue(tcolobj));
			values.put("modeltype", new ColumnValue(mcolobj));
			values.put("searchret", new ColumnValue(retstr));
			table.appendRow(new Row(values, RowState.NONE));

			Util.writeTableToResponse(response, table);
		}
	}
}
