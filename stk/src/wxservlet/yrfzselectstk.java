package wxservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.justep.baas.data.Table;
import com.justep.baas.data.Util;

/**
 * Servlet implementation class yrfzselectstk
 */
public class yrfzselectstk extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DATASOURCE_STK = "jdbc/ruyifund";
	private static final String TABLE_TRADE_DATA = "traderec";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public yrfzselectstk() {
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
			if ("querySelect".equals(action)) {
				querySelect(request, response);
			}
			if ("querySelectByCode".equals(action)) {
				querySelectByCode(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	private static void querySelectByCode(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, NamingException {
		// 参数序列化
		JSONObject params = (JSONObject) JSONObject.parse(request.getParameter("params"));

		// 获取参数
		Object columns = params.get("columns"); // 列定义
		Integer limit = params.getInteger("limit"); // 分页查询的行数
		Integer offset = params.getInteger("offset"); // 分页查询的行偏移
		String search = params.getString("search"); // 检索关键字后面重新赋值了这个变量

		// 获取params里面json格式的前端数据
		// {code:"",stime:"",etime:""}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject reqSelectCode = (JSONObject) JSONObject.parse(search);
		String stkcode = reqSelectCode.getString("code");
		
		Date cstime = null;
		try {
			cstime = df.parse(reqSelectCode.getString("stime"));
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		Date cetime = null;
		try {
			cetime = df.parse(reqSelectCode.getString("etime"));
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		String strCstime = df2.format(cstime);
		String strEstime = df2.format(cetime);
		
		// 存放SQL中的参数值
		List<Object> sqlParams = new ArrayList<Object>();
		// 存放SQL中的过滤条件
		List<String> filters = new ArrayList<String>();
		if (!Util.isEmptyString(stkcode)) {
			// 增加过滤条件
			filters.add("time BETWEEN " + "'" + strCstime + "'" + " AND " + "'" + strEstime + "'" + " " + 
						" AND code LIKE ?");
			// 检索关键字中如果没有%，则前后自动加%
			search = stkcode;
			
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
			Util.writeTableToResponse(response, table);
		}
	}

	private static void querySelect(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, NamingException {
		// 参数序列化
		JSONObject params = (JSONObject) JSONObject.parse(request.getParameter("params"));

		// 获取参数
		Object columns = params.get("columns"); // 列定义
		Integer limit = params.getInteger("limit"); // 分页查询的行数
		Integer offset = params.getInteger("offset"); // 分页查询的行偏移
		String search = params.getString("search"); // 检索关键字后面重新赋值了这个变量

		// 获取params里面json格式的前端数据
		// {gonetime:"",stkcode:""}

		JSONObject reqSelectModel = (JSONObject) JSONObject.parse(search);

		// 这里不能直接用out打印debug日志，会影响输出导致数据无法加载
		Integer gonetime = reqSelectModel.getInteger("gonetime");

		// 计算当前时间根据前端的搜索时间按照天粒度倒退回去
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String endtime = df.format(d);
		String starttime = df.format(new Date(d.getTime() - (gonetime) * 24 * 60 * 60 * 1000));
		String stkcode = reqSelectModel.getString("stkcode");

		// 存放SQL中的参数值
		List<Object> sqlParams = new ArrayList<Object>();
		// 存放SQL中的过滤条件
		List<String> filters = new ArrayList<String>();
		if (!Util.isEmptyString(String.valueOf(gonetime))) {
			// 增加过滤条件
			filters.add("time BETWEEN " + "'" + starttime + "'" + " AND " + "'" + endtime + "'" + " " + " AND code LIKE ?");
			// 检索关键字中如果没有%，则前后自动加%
			search = stkcode;
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
			Util.writeTableToResponse(response, table);
		}
	}
}
