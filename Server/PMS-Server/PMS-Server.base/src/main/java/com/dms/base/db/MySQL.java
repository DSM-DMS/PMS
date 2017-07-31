package com.dms.base.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pms.base.util.Config;
import com.pms.base.util.Log;

public class MySQL {
	private static Connection connection;
	
	private static String url;
	private static String user = Config.getValue("dbUserName");
	private static String password = Config.getValue("dbPassword");
	
	static {
		StringBuilder sb = new StringBuilder();
		sb.append("jdbc:mysql://localhost:");
		sb.append(Config.getValue("dbPort")).append("/");
		sb.append(Config.getValue("dbTableName")).append("?");
		sb.append("useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		
		url = sb.toString();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private synchronized static PreparedStatement buildQuery(String sql, Object... args) {
		Log.query(sql +" (" + args + ")");
		
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			int placeholderCount = 1;
			for(Object o: args) {
				statement.setObject(placeholderCount++, o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return statement;
	}
	
	public synchronized static ResultSet executeQuery(String sql, Object... args) {
		try {
			return buildQuery(sql, args).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public synchronized static int executeUpdate(String sql, Object... args) {
		try {
			return buildQuery(sql, args).executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
