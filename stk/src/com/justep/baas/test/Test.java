package com.justep.baas.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.alibaba.fastjson.JSONObject;
import com.justep.baas.data.Row;
import com.justep.baas.data.Table;
import com.justep.baas.data.Transform;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/takeout", "root", "x5");
		try {
			Statement stat = conn.createStatement();
			try {
				// 执行SQL，返回ResultSet
				ResultSet rs = stat.executeQuery("select * from takeout_food");
				// 转换为Table
				Table table = Transform.resultSetToTable(rs, "", null);
				
				System.out.println("遍历数据************************************************");
				for (Row row : table.getRows()) {
					System.out.println("	" + row.getString("fName"));
				}
				
				// 转换为JSON
				JSONObject json = Transform.tableToJson(table);
				
				System.out.println("输出JSON************************************************");
				System.out.println(json.toJSONString());
			} finally {
				stat.close();
			}
		} finally {
			conn.close();
		}
	}

}
