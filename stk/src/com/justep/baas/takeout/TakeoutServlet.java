package com.justep.baas.takeout;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import com.alibaba.fastjson.JSONObject;
import com.justep.baas.data.Table;
import com.justep.baas.data.Transform;
import com.justep.baas.data.Util;

public class TakeoutServlet extends HttpServlet  {

	private static final long serialVersionUID = 5506302727994136101L;

	private static final String DATASOURCE_TAKEOUT = "jdbc/takeout";
	
	private static final String TABLE_TAKEOUT_FOOD = "takeout_food";
	private static final String TABLE_TAKEOUT_USER = "takeout_user";
	private static final String TABLE_TAKEOUT_ORDER = "takeout_order";

	public void service(ServletRequest request, ServletResponse response) throws ServletException {
		try {
			String action = request.getParameter("action");
			if ("queryFood".equals(action)) {
				queryFood(request, response);
			} else if ("queryUser".equals(action)) {
				queryUser(request, response);
			} else if ("queryOrder".equals(action)) {
				queryOrder(request, response);
			} else if ("save".equals(action)) {
				save(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	private static void queryFood(ServletRequest request, ServletResponse response) throws SQLException, IOException, NamingException {
		// 参数序列化
		JSONObject params = (JSONObject) JSONObject.parse(request.getParameter("params"));

		// 获取参数
		Object columns = params.get("columns");
		Integer limit = params.getInteger("limit");
		Integer offset = params.getInteger("offset");

		Table table = null;
		Connection conn = Util.getConnection(DATASOURCE_TAKEOUT);
		try {
			table = Util.queryData(conn, TABLE_TAKEOUT_FOOD, columns, null, "fID ASC", null, offset, limit);
		} finally {
			conn.close();
		}

		// 输出返回结果 
		Util.writeTableToResponse(response, table);
	}

	private static void queryUser(ServletRequest request, ServletResponse response) throws SQLException, IOException, NamingException {
		// 参数序列化
		JSONObject params = (JSONObject) JSONObject.parse(request.getParameter("params"));

		// 获取参数
		Object columns = params.get("columns");

		String id = params.getString("id");

		List<Object> sqlParams = new ArrayList<Object>();
		List<String> filters = new ArrayList<String>();
		filters.add("fID = ?");
		sqlParams.add(id);
		
		Table table = null;
		Connection conn = Util.getConnection(DATASOURCE_TAKEOUT);
		try {
			table = Util.queryData(conn, TABLE_TAKEOUT_USER, columns, filters, "fID ASC", sqlParams, null, null);
		} finally {
			conn.close();
		}

		// 输出返回结果 
		Util.writeTableToResponse(response, table);
	}

	private static void queryOrder(ServletRequest request, ServletResponse response) throws SQLException, IOException, NamingException {
		// 参数序列化
		JSONObject params = (JSONObject) JSONObject.parse(request.getParameter("params"));

		// 获取参数
		Object columns = params.get("columns");
		Integer limit = params.getInteger("limit");
		Integer offset = params.getInteger("offset");
		String search = params.getString("search");

		// 存放SQL中的参数值
		List<Object> sqlParams = new ArrayList<Object>();
		// 构造过滤条件
		List<String> filters = new ArrayList<String>();
		if (!Util.isEmptyString(search)) {
			filters.add("fUserName LIKE ? OR fPhoneNumber LIKE ? OR fAddress LIKE ? OR fContent LIKE ?");
			// 多个问号参数的值
			search = (search.indexOf("%") != -1) ? search : "%" + search + "%";
			for (int i = 0; i < 4; i++) {
				sqlParams.add(search);
			}
		}
		
		// 按用户ID过滤
		String userID = params.getString("userID");
		if (!Util.isEmptyString(userID)) {
			filters.add("fUserID = ?");
			sqlParams.add(userID);
		}

		Table table = null;
		Connection conn = Util.getConnection(DATASOURCE_TAKEOUT);
		try {
			table = Util.queryData(conn, TABLE_TAKEOUT_ORDER, columns, filters, "fCreateTime DESC", sqlParams, offset, limit);
		} finally {
			conn.close();
		}

		// 输出返回结果 
		Util.writeTableToResponse(response, table);
	}

	private static void save(ServletRequest request, ServletResponse response) throws ParseException, SQLException, NamingException {
		// 参数序列化
		JSONObject params = (JSONObject) JSONObject.parse(request.getParameter("params"));
		// 获取参数
		JSONObject userData = params.getJSONObject("userData");
		JSONObject orderData = params.getJSONObject("orderData");

		Connection conn = Util.getConnection(DATASOURCE_TAKEOUT);
		try {
			conn.setAutoCommit(false);
			try {
				if (userData != null) {
					Table userTable = Transform.jsonToTable(userData);
					Util.saveData(conn, userTable, TABLE_TAKEOUT_USER);
				}
				if (orderData != null) {
					Table orderTable = Transform.jsonToTable(orderData);
					Util.saveData(conn, orderTable, TABLE_TAKEOUT_ORDER);
				}
				
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
		} finally {
			conn.close();
		}
	}
	
}
