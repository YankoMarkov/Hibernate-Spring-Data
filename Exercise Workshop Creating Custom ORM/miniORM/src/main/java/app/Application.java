package app;

import app.entities.User;
import app.orm.Connector;
import app.orm.EntityManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Application {
	public static void main(String[] args) throws IOException, SQLException, IllegalAccessException, InstantiationException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		
		String username = read.readLine().trim();
		String password = read.readLine().trim();
		String dbName = read.readLine().trim();
		
		Connector.createConnection(username, password, dbName);
		EntityManager<User> entityManager = new EntityManager<>(Connector.getConnection(), User.class);
		
		User testUser = new User("Atanas", 30);
//		User anotherUser = new User(2,"Ivan", 30);
//
		entityManager.persist(testUser);
//		entityManager.persist(anotherUser);
		List<User> found = (List<User>) entityManager.find();
		found.forEach(f -> System.out.printf("%s%n",f));
		
		//Connector.getConnection().close();
	}
}
