package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConfig {
	public static Connection getConnection() {

		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:1508/crm_application", "root", "admin");
		} catch (Exception e) {
			System.out.println("MysqlConfig" + e.getLocalizedMessage());
		}
		return connection;
	}
}