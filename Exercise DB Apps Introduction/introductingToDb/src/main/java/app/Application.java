package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Application {
	
	public static void main(String[] args) throws SQLException {
		String user = "root";
		String password = "pupalka";
		
		Properties properties = new Properties();
		properties.setProperty("user", user);
		properties.setProperty("password", password);
		
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);
		
		System.out.println("Select an exercise number 2 to 9:");
		Engine engine = new Engine(connection);
		engine.run();
	}
}
