package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Engine implements Runnable {
	
	private Connection connection;
	
	Engine(Connection connection) {
		this.connection = connection;
	}
	
	public void run() {
		Scanner scan = new Scanner(System.in);
		int number = Integer.valueOf(scan.nextLine());
		try {
			switch (number) {
				case 2:
					getVillainsNames();
					break;
				case 3:
					getMinionNames();
					break;
				case 4:
					addMinion();
					break;
				case 5:
					changeTownNamesCasing();
					break;
				case 6:
					removeVillain();
					break;
				case 7:
					printAllMinionNames();
					break;
				case 8:
					increaseMinionsAge();
					break;
				case 9:
					increaseAgeStoredProcedure();
					break;
				default:
					System.out.println("Wrong number.");
					break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Problem 9. Increase Age Stored Procedure
	 */
	private void increaseAgeStoredProcedure() throws IOException, SQLException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		int inputId = Integer.valueOf(read.readLine());
		
		String query = "CALL usp_get_older(?)";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setInt(1, inputId);
		preparedStatement.execute();
		printMinionsById(inputId);
	}
	
	private void printMinionsById(int minionId) throws SQLException {
		String query = String.format("SELECT m.name, m.age FROM minions AS m where m.id = %d", minionId);
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			System.out.printf("%s %d%n", resultSet.getString(1), resultSet.getInt(2));
		}
	}
	
	/**
	 * Problem 8. Increase Minions Age
	 */
	private void increaseMinionsAge() throws IOException, SQLException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		String[] inputs = read.readLine().split("\\s+");
		
		for (String input : inputs) {
			String query = "CALL usp_get_older(?)";
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setInt(1, Integer.valueOf(input));
			preparedStatement.execute();
		}
		printMinions();
	}
	
	private void printMinions() throws SQLException {
		String query = "SELECT m.name, m.age FROM minions AS m";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			System.out.printf("%s %d%n", resultSet.getString(1), resultSet.getInt(2));
		}
	}
	
	/**
	 * Problem 7. Print All Minion Names
	 */
	private void printAllMinionNames() throws SQLException {
		List<String> minions = new ArrayList<String>();
		
		String query = "SELECT m.id, m.name FROM minions AS m";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			minions.add(resultSet.getString(2));
		}
		
		for (int i = 0; i < minions.size() / 2; i++) {
			System.out.println(minions.get(i));
			System.out.println(minions.get(minions.size() - i - 1));
		}
	}
	
	/**
	 * Problem 6. Remove Villain
	 */
	private void removeVillain() throws IOException, SQLException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		int villainId = Integer.valueOf(read.readLine());
		
		dropForeignKey("minions_villains", "fk_villains_minions");
		dropForeignKey("minions_villains", "fk_minions_villains");
		
		String query = "SELECT * FROM villains AS v WHERE v.id = ?";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setInt(1, villainId);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (resultSet.next()) {
			deleteVillain("villains", villainId);
			releasesMinions("minions_villains", villainId);
		}
		addForeignKey("minions_villains", "fk_villains_minions", "villain_id", "villains", "id");
		addForeignKey("minions_villains", "fk_minions_villains", "minion_id", "minions", "id");
	}
	
	private void releasesMinions(String tableName, int villainId) throws SQLException {
		int minionCount = countMinions(villainId);
		String query = String.format("DELETE FROM %s WHERE villain_id = 1", tableName, villainId);
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.execute();
		
		System.out.printf("%d minions released%n", minionCount);
	}
	
	private void addForeignKey(String tableName, String foreignKey, String tableNameId, String tableName2, String tableName2Id) throws SQLException {
		String query = String.format("ALTER TABLE %s ADD FOREIGN KEY %s (%s) REFERENCES %s (%s)", tableName, foreignKey, tableNameId, tableName2, tableName2Id);
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.execute();
	}
	
	private void dropForeignKey(String tableName, String foreignKey) throws SQLException {
		String query = String.format("ALTER TABLE %s DROP FOREIGN KEY %s", tableName, foreignKey);
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.execute();
	}
	
	private int countMinions(int villainId) throws SQLException {
		List<Integer> minions = new ArrayList<Integer>();
		String query = "SELECT mv.minion_id FROM minions_villains AS mv WHERE mv.villain_id = ?";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setInt(1, villainId);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			minions.add(resultSet.getInt(1));
		}
		return minions.size();
	}
	
	private void deleteVillain(String tableName, int villainId) throws SQLException {
		String villainName = getEntityName(villainId, "villains");
		String query = String.format("DELETE FROM %s WHERE id = %d", tableName, villainId);
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.execute();
		System.out.printf("%s was deleted%n", villainName);
	}
	
	private String getEntityName(int villainId, String tableName) throws SQLException {
		String query = String.format("SELECT a.name FROM %s AS a WHERE a.id = %d", tableName, villainId);
		
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		
		return resultSet.getString(1);
	}
	
	/**
	 * Problem 5. Change Town Names Casing
	 */
	private void changeTownNamesCasing() throws IOException, SQLException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		String input = read.readLine();
		List<String> result = new ArrayList<String>();
		
		String query = String.format("SELECT UPPER(t.name) FROM towns AS t WHERE t.country = '%s'", input);
		
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			result.add(resultSet.getString(1));
		}
		if (result.isEmpty()) {
			System.out.println("No town names were affected.");
		} else {
			System.out.printf("%d town names were affected.%n", result.size());
			System.out.println(result.toString());
		}
	}
	
	/**
	 * Problem 4. Add Minion
	 */
	private void addMinion() throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		String[] inputMinion = read.readLine().split("\\s+");
		String[] inputVillain = read.readLine().split("\\s+");
		
		String minionName = inputMinion[1];
		int minionAge = Integer.valueOf(inputMinion[2]);
		String minionTown = inputMinion[3];
		String villainName = inputVillain[1];
		
		try {
			if (!checkIfExist(minionTown, "towns")) {
				insertTown(minionTown);
			}
			if (!checkIfExist(villainName, "villains")) {
				insertVillain(villainName);
			}
			insertMinion(minionName, minionTown, minionAge, villainName);
			insertMinionInVillain(minionName, villainName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void insertMinionInVillain(String minionName, String villainName) throws SQLException {
		int minionId = getEntityId(minionName, "minions");
		int villainId = getEntityId(villainName, "villains");
		
		String query = String.format("INSERT INTO minions_villains (minion_id, villain_id) VALUES (%d, %d)", minionId, villainId);
		
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.execute();
	}
	
	private void insertMinion(String minionName, String minionTown, int minionAge, String villainName) throws SQLException {
		int townId = getEntityId(minionTown, "towns");
		String query = String.format("INSERT INTO minions (name, age, town_id) VALUES ('%s', %d, %d)", minionName, minionAge, townId);
		
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.execute();
		
		System.out.printf("Successfully added %s to be minion of %s.%n", minionName, villainName);
	}
	
	private void insertVillain(String villainName) throws SQLException {
		String query = "INSERT INTO villains (name, evilness_factor) VALUES (?, 'evil')";
		
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setString(1, villainName);
		preparedStatement.execute();
		
		System.out.printf("Villain %s was added to the database.%n", villainName);
	}
	
	private void insertTown(String minionTown) throws SQLException {
		String query = "INSERT INTO towns (name, country) VALUES (?, NULL)";
		
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setString(1, minionTown);
		preparedStatement.execute();
		
		System.out.printf("Town %s was added to the database.%n", minionTown);
	}
	
	private boolean checkIfExist(String name, String tableName) throws SQLException {
		String query = String.format("SELECT * FROM %s AS a WHERE a.name = '%s'", tableName, name);
		
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (resultSet.next()) {
			return true;
		}
		return false;
	}
	
	private int getEntityId(String name, String tableName) throws SQLException {
		String query = String.format("SELECT a.id FROM %s AS a WHERE a.name = '%s'", tableName, name);
		
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		
		return resultSet.getInt(1);
	}
	
	/**
	 * Problem 3. Get Minion Names
	 */
	private void getMinionNames() throws SQLException, IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		int number = Integer.valueOf(read.readLine());
		String query = "SELECT v.name, m.name, m.age FROM minions AS m " +
				"INNER JOIN minions_villains AS mv ON m.id = mv.minion_id " +
				"INNER JOIN villains AS v ON mv.villain_id = v.id " +
				"WHERE v.id = ?";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setInt(1, number);
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		System.out.printf("Villain: %s%n", resultSet.getString(1));
		int count = 1;
		
		while (resultSet.next()) {
			System.out.printf("%d. %s %d%n", count++, resultSet.getString(2), resultSet.getInt(3));
		}
	}
	
	/**
	 * Problem 2. Get Villains’ Names
	 */
	private void getVillainsNames() throws SQLException, IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		int number = Integer.valueOf(read.readLine());
		String query = "SELECT v.name, COUNT(mv.minion_id) FROM villains AS v " +
				"INNER JOIN minions_villains AS mv ON v.id = mv.villain_id " +
				"GROUP BY v.name " +
				"HAVING COUNT(mv.minion_id) > ? " +
				"ORDER BY COUNT(mv.minion_id) DESC";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setInt(1, number);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {
			System.out.printf("%s %d%n", resultSet.getString(1), resultSet.getInt(2));
		}
	}
}
