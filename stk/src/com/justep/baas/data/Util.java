package com.justep.baas.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletResponse;
import javax.sql.DataSource;

import com.alibaba.fastjson.JSONObject;

public class Util {

	/**
	 * 获取数据源连接
	 * @param name 数据源资源名
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 */
	public static Connection getConnection(String name) throws SQLException, NamingException {
		InitialContext initCtx = new InitialContext();
		DataSource ds = (DataSource) initCtx.lookup("java:comp/env/" + name);
		Connection conn = ds.getConnection();
		return conn;
	}

	/**
	 * SQL数据查询，按ResultSet的列定义转换并返回Table，支持分页。应从前端传入完整列定义（Baas.getDataColumns(data)）， 以解决oracle等数据库不区分date、time、datetime，导致的数据格式转换问题；兼容了以前只传入列名字符串（data.getColumnIDs()）的写法，但是已不再推荐。
	 * @param conn
	 * @param sql
	 * @param params SQL中问号对应的参数值，按顺序匹配
	 * @param columns 列定义
	 * @param offset 偏移行，null则不分页
	 * @param limit 行数，null则不分页
	 * @return
	 * @throws SQLException
	 */
	public static Table queryData(Connection conn, String sql, List<Object> params, Object columns, Integer offset, Integer limit) throws SQLException {
		if (limit != null && offset != null) {
			if (isMysql(conn)) {
				sql += " LIMIT " + offset + "," + limit;
			} else if (isOracle(conn)) {
				sql = String.format("SELECT * FROM (SELECT rownum no___, A___.* FROM (%s) A___ WHERE rownum <= %d) WHERE no___ > %d", sql, offset + limit, offset);
			}
		}
		System.out.println("euxyacg query data " + sql);
		
		PreparedStatement pstat = conn.prepareStatement(sql);
		try {
			if (params != null) {
				for (int i = 0, len = params.size(); i < len; i++) {
					pstat.setObject(i + 1, params.get(i));
				}
			}
			ResultSet rs = pstat.executeQuery();
			if (limit != null && offset != null && !isMysql(conn) && !isOracle(conn)) {
				for (int i = 0; i < offset; i++) {
					rs.next();
				}
			}
			Table table = null;
			if (columns instanceof JSONObject) {
				table = Transform.createTableByColumnsDefine((JSONObject) columns);
			} else {
				table = Transform.createTableByResultSet(rs, (String) columns);
			}  
			Transform.loadRowsFromResultSet(table, rs, limit);
			return table;
		} finally {
			pstat.close();
		}
	}

	/**
	 * 单表数据查询，按ResultSet的列定义转换返回Table，支持分页，当偏移行等于零时自动返回总行数。应从前端传入完整列定义（Baas.getDataColumns(data)）， 以解决oracle等数据库不区分date、time、datetime，导致的数据格式转换问题；兼容了以前只传入列名字符串（data.getColumnIDs()）的写法，但是已不再推荐。
	 * @param conn
	 * @param tableName 表名
	 * @param columns 列定义
	 * @param filters 过滤条件列表
	 * @param orderBy SQL的orderBy部分
	 * @param params SQL中问号对应的参数值列表
	 * @param offset 偏移行
	 * @param limit 行数
	 * @return
	 * @throws SQLException
	 */
	public static Table queryData(Connection conn, String tableName, Object columns, List<String> filters, String orderBy, 
		List<Object> params, Integer offset, Integer limit) throws SQLException {
		String format = "SELECT %s FROM %s %s %s ";
		
		String where = (filters != null && filters.size() > 0) ? " WHERE " + Util.arrayJoin(filters.toArray(), "(%s)", " AND ") : "";
		orderBy = !Util.isEmptyString(orderBy) ? " ORDER BY " + orderBy : "";
		
		String sql = String.format(format, "*", tableName, where, orderBy);
		Table table = null;
		table = queryData(conn, sql.toString(), params, columns, offset, limit);
		if (offset != null && offset.equals(0)) {
			String sqlTotal = String.format(format, "COUNT(*)", tableName, where, "");
			Object total = Util.getValueBySQL(conn, sqlTotal.toString(), params);
			table.setTotal(Integer.parseInt(total.toString()));
		}
		return table;
	}
		
	private static boolean isMysql(Connection conn) throws SQLException {
		return conn.getMetaData().getDatabaseProductName().toLowerCase().indexOf("mysql") != -1;
	}

	private static boolean isOracle(Connection conn) throws SQLException {
		return conn.getMetaData().getDatabaseProductName().toLowerCase().indexOf("oracle") != -1;
	}

	/**
	 * 执行SQL查询，返回第一行第一列的值
	 * @param conn
	 * @param sql
	 * @param params SQL中问号对应的参数值，按顺序匹配
	 * @return
	 * @throws SQLException
	 */
	public static Object getValueBySQL(Connection conn, String sql, List<Object> params) throws SQLException {
		PreparedStatement pstat = conn.prepareStatement(sql);
		try {
			if (params != null) {
				for (int i = 0, len = params.size(); i < len; i++) {
					pstat.setObject(i + 1, params.get(i));
				}
			}
			ResultSet rs = pstat.executeQuery();
			if (rs.next()) {
				return rs.getObject(1);
			} else {
				return null;
			}
		} finally {
			pstat.close();
		}
	}

	/**
	 * 保存Table数据，自动产生并执行基于where key规则的增删改SQL语句
	 * @param conn
	 * @param table
	 * @param tableName 数据库表名
	 * @throws SQLException
	 */
	public static void saveData(Connection conn, Table table, String tableName) throws SQLException {
		saveData(conn, table, tableName, "");  
	}

	/**
	 * 保存Table数据，并指定列范围
	 * @param conn
	 * @param table
	 * @param tableName 数据库表名
	 * @param columns 列范围
	 * @throws SQLException
	 */
	public static void saveData(Connection conn, Table table, String tableName, String columns) throws SQLException {
		saveData(conn, table, tableName, isEmptyString(columns) ? null : Arrays.asList(columns.split(",")));  
	}

	/**
	 * 保存Table数据，并指定列范围
	 * @param conn
	 * @param table
	 * @param tableName 数据库表名
	 * @param columns 列范围
	 * @throws SQLException
	 */
	public static void saveData(Connection conn, Table table, String tableName, Collection<String> columns) throws SQLException {
		if (columns == null) {
			columns = new ArrayList<String>();
			columns.addAll(table.getColumnNames());
		} 
		String idColumn = table.getIDColumn();
		
		PreparedStatement newStat = conn.prepareStatement(createNewSQL(table, tableName, columns));
		for (Row row : table.getRows(RowState.NEW)) {
			int i = 1;
			for (String column : columns) {
				newStat.setObject(i, row.getValue(column));
				i++;
			}
			newStat.execute();
		}
		PreparedStatement editStat = conn.prepareStatement(createUpdateSQL(table, tableName, columns));
		for (Row row : table.getRows(RowState.EDIT)) {
			int i = 1;
			for (String column : columns) {
				editStat.setObject(i, row.getValue(column));
				i++;
			}
			editStat.setObject(columns.size() + 1, row.isChanged(idColumn) ? row.getOldValue(table.getIDColumn()) : row.getValue(idColumn));
			editStat.execute();
		}
		PreparedStatement deleteStat = conn.prepareStatement(createDeleteSQL(table, tableName));
		for (Row row : table.getRows(RowState.DELETE)) {
			deleteStat.setObject(1, row.isChanged(idColumn) ? row.getOldValue(table.getIDColumn()) : row.getValue(idColumn));
			deleteStat.execute();
		}
	}

	private static String createNewSQL(Table table, String tableName, Collection<String> columns) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO " + tableName);
		sql.append(" (" + arrayJoin(columns.toArray(), "%s", ",") + ") ");
		sql.append(" VALUES (" + arrayJoin(columns.toArray(), "?", ",") + ") ");
		return sql.toString();
	}

	private static String createUpdateSQL(Table table, String tableName, Collection<String> columns) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE " + tableName);
		sql.append(" SET " + arrayJoin(columns.toArray(), "%s=?", ",") + " ");
		sql.append(" WHERE " + table.getIDColumn() + "=? ");
		return sql.toString();
	}

	private static String createDeleteSQL(Table table, String tableName) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM " + tableName);
		sql.append(" WHERE " + table.getIDColumn() + "=? ");
		return sql.toString();
	}
	
	/**
	 * 将一个数组连接为格式化字符串
	 * @param objects
	 * @param format 对数组元素的格式化，例如：'%s'为每个数组元素增加单引号、(%s = ?)将每个数组元素格式化为括号中等于问号
	 * @param separator 分隔符，例如：,、OR、AND
	 * @return
	 */
	public static String arrayJoin(Object[] objects, String format, String separator) {
		StringBuffer buf = new StringBuffer();
		for (Object o : objects) {
			if (buf.length() > 0) {
				buf.append(separator);
			}
			buf.append(String.format(format, o.toString()));
		}
		return buf.toString();
	}
	
	/**
	 * 判断是否空字符串
	 * @param s
	 * @return
	 */
	public static boolean isEmptyString(String s) {
		return s == null || s.trim().length() == 0;
	}

	/**
	 * 输出JSON
	 * @param response
	 * @param json
	 * @throws IOException
	 */
	public static void writeJsonToResponse(ServletResponse response, JSONObject json) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		/* 放开下面注释的代码就可以支持跨域
		if (response instanceof javax.servlet.http.HttpServletResponse) {
			// "*"表明允许任何地址的跨域调用，正式部署时应替换为正式地址
			((javax.servlet.http.HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*"); 
		}
		 */
		response.getWriter().write(json.toJSONString());
	}
	
	/**
	 * 输出Table
	 * @param response
	 * @param json
	 * @throws IOException
	 */
	public static void writeTableToResponse(ServletResponse response, Table table) throws IOException {
		JSONObject json = Transform.tableToJson(table);
		writeJsonToResponse(response, json);
	}
	
}
