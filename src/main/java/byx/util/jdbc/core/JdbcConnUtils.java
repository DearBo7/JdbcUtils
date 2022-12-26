package byx.util.jdbc.core;

import byx.util.jdbc.exception.DbException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * jdbc
 *
 * @author wb
 */
public class JdbcConnUtils {
	/**
	 * sql server: com.microsoft.sqlserver.jdbc.SQLServerDriver
	 * mysql: com.mysql.jdbc.Driver
	 */
	private static String DEFAULT_DRIVER;
	/**
	 * sql server: jdbc:sqlserver://localhost:1433;databaseName=test
	 * mysql: jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Shanghai&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowMultiQueries=true
	 */
	private static String DEFAULT_URL;
	private static String DEFAULT_USER_NAME;
	private static String DEFAULT_PASSWORD;

	static {
		ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
		if (bundle != null) {
			DEFAULT_DRIVER = bundle.getString("jdbc.driver");
			DEFAULT_URL = bundle.getString("jdbc.url");
			DEFAULT_USER_NAME = bundle.getString("jdbc.userName");
			DEFAULT_PASSWORD = bundle.getString("jdbc.password");
		}
	}

	public static Connection getConn() throws SQLException {
		return getConn(null);
	}

	/**
	 * 连接数据库
	 *
	 * @return Connection
	 */
	public static Connection getConn(DataSource dataSource) throws SQLException {
		Connection con;
		if (dataSource != null) {
			con = dataSource.getConnection();
		} else {
			try {
				Class.forName(DEFAULT_DRIVER);
			} catch (ClassNotFoundException e) {
				throw new DbException("Load 【" + DEFAULT_DRIVER + "】 driver error.", e);
			}
			con = DriverManager.getConnection(DEFAULT_URL, DEFAULT_USER_NAME, DEFAULT_PASSWORD);
		}
		return con;
	}
}
