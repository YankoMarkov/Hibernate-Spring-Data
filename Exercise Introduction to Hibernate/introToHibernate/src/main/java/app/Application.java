package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Application {
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("soft_uni");
		EntityManager manager = factory.createEntityManager();
		
		System.out.println("Select an exercise number 2 to 13:");
		Runnable engine = new Engine(manager);
		engine.run();
	}
}
