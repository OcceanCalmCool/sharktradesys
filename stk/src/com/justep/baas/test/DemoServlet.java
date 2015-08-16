package com.justep.baas.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
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

public class DemoServlet extends HttpServlet {

	private static final long serialVersionUID = 1452041171629615960L;

	private static final String DATASOURCE_TAKEOUT = "jdbc/takeout";
	
//	private static final String TABLE_TAKEOUT_FOOD = "takeout_food";
	private static final String TABLE_TAKEOUT_USER = "takeout_user";
	private static final String TABLE_TAKEOUT_ORDER = "takeout_order";
	private static final String TABLE_TAKEOUT_REGION = "takeout_region";

	@Override
	// Servlet入口，通过判断action参数，进入各自对应的实现方法
	public void service(ServletRequest request, ServletResponse response) throws ServletException {
		try {
			String action = request.getParameter("action");
			if ("queryOrder".equals(action)) {
				queryOrder(request, response);
			} else if ("saveOrder".equals(action)) {
				saveOrder(request, response);
			} else if ("queryRegionTree".equals(action)) {
				queryRegionTree(request, response);
			} else if ("queryRegionTreeByParent".equals(action)) {
				queryRegionTreeByParent(request, response);
			} else if ("queryUser".equals(action)) {
				queryUser(request, response);
			} else if ("saveUser".equals(action)) {
				saveUser(request, response);
			} else if ("saveMasterDetail".equals(action)) {
				saveMasterDetail(request, response);
			} 
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	// 查询订单，实现了分页查询和按检索关键字过滤
	private static void queryOrder(ServletRequest request, ServletResponse response) throws SQLException, IOException, NamingException {
		// 参数序列化
		JSONObject params = (JSONObject) JSONObject.parse(request.getParameter("params"));

		// 获取参数
		Object columns = params.get("columns"); // 列定义
		Integer limit = params.getInteger("limit"); // 分页查询的行数
		Integer offset = params.getInteger("offset"); // 分页查询的行偏移
		String search = params.getString("search"); // 检索关键字

		// 存放SQL中的参数值
		List<Object> sqlParams = new ArrayList<Object>();
		// 存放SQL中的过滤条件
		List<String> filters = new ArrayList<String>();
		if (!Util.isEmptyString(search)) {
			// 增加过滤条件
			filters.add("fUserName LIKE ? OR fPhoneNumber LIKE ? OR fAddress LIKE ? OR fContent LIKE ?");
			// 检索关键字中如果没有%，则前后自动加%
			search = (search.indexOf("%") != -1) ? search : "%" + search + "%";
			// sqlParams的参数个数和顺序必须与过滤条件的?相匹配 
			for (int i = 0; i < 4; i++) {
				sqlParams.add(search);
			}
		}
		
		// 按用户ID过滤，用于主从数据示例
		String userID = params.getString("userID");
		if (!Util.isEmptyString(userID)) {
			filters.add("fUserID = ?");
			sqlParams.add(userID);
		}
		
		Table table = null;
		// 获取数据源连接
		Connection conn = Util.getConnection(DATASOURCE_TAKEOUT);
		try {
			// 执行单表数据查询，返回Table
			table = Util.queryData(conn, TABLE_TAKEOUT_ORDER, columns, filters, "fCreateTime DESC", sqlParams, offset, limit);
		} finally {
			// 必须关闭数据源连接
			conn.close();
		}

		// 输出Table做为返回结果，这里会自动转换为Table的JSON格式 
		Util.writeTableToResponse(response, table);
	}

	// 保存订单
	private static void saveOrder(ServletRequest request, ServletResponse response) throws ParseException, SQLException, NamingException {
		// 参数序列化
		JSONObject params = (JSONObject) JSONObject.parse(request.getParameter("params"));
		// 获取参数
		JSONObject data = params.getJSONObject("data"); // 订单数据的JSON格式

		// JSON转换Table
		Table table = Transform.jsonToTable(data);

		// 获取数据源连接
		Connection conn = Util.getConnection(DATASOURCE_TAKEOUT);
		try {
			// 开启事务
			conn.setAutoCommit(false);
			try {
				// 保存Table
				Util.saveData(conn, table, TABLE_TAKEOUT_ORDER);
				// 提交事务
				conn.commit();
			} catch (SQLException e) {
				// 如果发生异常，首先回滚事务，然后把异常继续抛出
				conn.rollback();
				throw e;
			}
		} finally {
			// 必须关闭数据源连接
			conn.close();
		}
	}
	
	private static void queryRegionTree(ServletRequest request, ServletResponse response) throws SQLException, IOException, NamingException {
		// 参数序列化
		JSONObject params = (JSONObject) JSONObject.parse(request.getParameter("params"));

		// 获取参数
		Object columns = params.get("columns");
		
		Table table = null;
		Connection conn = Util.getConnection(DATASOURCE_TAKEOUT);
		try {
			// 获取数据
			table = Util.queryData(conn, TABLE_TAKEOUT_REGION, columns, null, "fID ASC", null, null, null);
		} finally {
			conn.close();
		}

		// 转换为树形数据
		table.setIDColumn("fID");
		JSONObject tableJSON = Transform.tableToTreeJson(table, "fParentID");
		
		// 输出返回结果 
		Util.writeJsonToResponse(response, tableJSON);	
	}

	private static void queryRegionTreeByParent(ServletRequest request, ServletResponse response) throws SQLException, IOException, NamingException {
		// 参数序列化
		JSONObject params = (JSONObject) JSONObject.parse(request.getParameter("params"));

		// 获取参数
		Object columns = params.get("columns");
		Integer limit = params.getInteger("limit");
		Integer offset = params.getInteger("offset");
		String parent = params.getString("parent");
		
		// 存放SQL中的参数值
		List<Object> sqlParams = new ArrayList<Object>();
		// 构造过滤条件
		List<String> filters = new ArrayList<String>();
		// 构造父过滤条件
		if (Util.isEmptyString(parent)) {
			filters.add("fParentID is null");
		} else {
			filters.add("fParentID = ?");
			sqlParams.add(parent);
		}
		
		Table table = null;
		Connection conn = Util.getConnection(DATASOURCE_TAKEOUT);
		try {
			// 获取数据
			table = Util.queryData(conn, TABLE_TAKEOUT_REGION, columns, filters, "fID ASC", sqlParams, offset, limit);
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
		Object columns = params.get("columns"); // 列定义
		Integer limit = params.getInteger("limit");
		Integer offset = params.getInteger("offset");
		String search = params.getString("search");
	
		// 存放SQL中的参数值
		List<Object> sqlParams = new ArrayList<Object>();
		// 构造where
		String sqlWhere = "";
		if (!Util.isEmptyString(search)) {
			sqlWhere = " WHERE (u.fID LIKE ? OR u.fName LIKE ? OR u.fPhoneNumber LIKE ? OR u.fAddress LIKE ?) ";
			// 多个问号参数的值
			search = (search.indexOf("%") != -1) ? search : "%" + search + "%";
			for (int i = 0; i < 4; i++) {
				sqlParams.add(search);
			}
		}

		// 复杂查询
		String sql = "SELECT u.fID, u.fName, u.fPhoneNumber, u.fAddress, COUNT(ord.fID) AS orderCount "
				+ " FROM takeout_user u "
				+ " 	LEFT JOIN takeout_order ord ON u.fID = ord.fUserID "
				+ sqlWhere
				+ " GROUP BY u.fID, u.fName, u.fPhoneNumber, u.fAddress ";
		
		Table table = null;
		Connection conn = Util.getConnection(DATASOURCE_TAKEOUT);
		try {
			table = Util.queryData(conn, sql, sqlParams, columns, offset, limit);
			if (offset != null && offset.equals(0)) {
				String sqlTotal = "SELECT COUNT(*) FROM takeout_user u "	+ sqlWhere;
				Object total = Util.getValueBySQL(conn, sqlTotal, sqlParams);
				table.setTotal(Integer.parseInt(total.toString()));
			}
		} finally {
			conn.close();
		}

		// 输出返回结果 
		Util.writeTableToResponse(response, table);
	}

	private static void saveUser(ServletRequest request, ServletResponse response) throws ParseException, SQLException, NamingException {
		// 参数序列化
		JSONObject params = (JSONObject) JSONObject.parse(request.getParameter("params"));
		// 获取参数
		JSONObject data = params.getJSONObject("data");

		// 转换Table
		Table table = Transform.jsonToTable(data);

		Connection conn = Util.getConnection(DATASOURCE_TAKEOUT);
		try {
			conn.setAutoCommit(false);
			try {
				// 排除不能保存的列
				Collection<String> columns = table.getColumnNames();
				columns.remove("orderCount");
				// 保存Table
				Util.saveData(conn, table, TABLE_TAKEOUT_USER, columns);
				
				conn.commit();
			} catch (SQLException e) {
				conn.rollback();
				throw e;
			}
		} finally {
			conn.close();
		}
	}
	
	private static void saveMasterDetail(ServletRequest request, ServletResponse response) throws ParseException, SQLException, NamingException {
		// 参数序列化
		JSONObject params = (JSONObject) JSONObject.parse(request.getParameter("params"));
		// 获取参数
		JSONObject userData = params.getJSONObject("userData");
		JSONObject orderData = params.getJSONObject("orderData");

		// 转换Table
		Table userTable = Transform.jsonToTable(userData);
		Table orderTable = Transform.jsonToTable(orderData);

		Connection conn = Util.getConnection(DATASOURCE_TAKEOUT);
		try {
			conn.setAutoCommit(false);
			try {
				// 排除不能保存的列
				Collection<String> userColumns = userTable.getColumnNames();
				userColumns.remove("orderCount");

				// 一个事务内同时保存多张表
				Util.saveData(conn, userTable, TABLE_TAKEOUT_USER, userColumns);
				Util.saveData(conn, orderTable, TABLE_TAKEOUT_ORDER);
				
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
