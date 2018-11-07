package app.orm;

import app.orm.annotations.Column;
import app.orm.annotations.Entity;
import app.orm.annotations.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManager<E> implements DbContext<E> {
	private static final String SELECT_QUERY_TEMPLATE = "SELECT * FROM {0}";
	private static final String SELECT_WHERE_QUERY_TEMPLATE = "SELECT * FROM {0} WHERE {1}";
	private static final String SELECT_SINGLE_QUERY_TEMPLATE = "SELECT * FROM {0} LIMIT 1";
	private static final String SELECT_SINGLE_WHERE_QUERY_TEMPLATE = "SELECT * FROM {0} WHERE {1} LIMIT 1";
	private static final String INSERT_QUERY_TEMPLATE = "INSERT INTO {0}({1}) VALUES({2})";
	private static final String UPDATE_QUERY_TEMPLATE = "UPDATE {0} SET {1} WHERE {2}={3}";
	private static final String DELETE_QUERY_TEMPLATE = "DELETE FROM {0} WHERE {1}";
	private static final String SET_QUERY_TEMPLATE = "{0}={1}";
	private static final String WHERE_PRIMARY_KEY = " {0}={1} ";
	
	private final Connection connection;
	private final Class<E> klass;
	
	public EntityManager(Connection connection, Class<E> klass) throws SQLException {
		this.connection = connection;
		this.klass = klass;
		
		if (checkIfTableExist()) {
			updateTable();
		} else {
			createTable();
		}
	}
	
	@Override
	public boolean persist(E entity) throws IllegalAccessException, SQLException {
		Field primary = getIdField();
		primary.setAccessible(true);
		int value = (int) primary.get(entity);
		
		if (value > 0) {
			return doUpdate(entity);
		}
		return doInsert(entity);
	}
	
	@Override
	public Iterable<E> find() throws IllegalAccessException, SQLException, InstantiationException {
		return find(null);
	}
	
	@Override
	public Iterable<E> find(String where) throws IllegalAccessException, SQLException, InstantiationException {
		String query = where == null ? SELECT_QUERY_TEMPLATE : SELECT_WHERE_QUERY_TEMPLATE;
		
		return find(query, where);
	}
	
	@Override
	public E findFirst() throws IllegalAccessException, SQLException, InstantiationException {
		return find(SELECT_SINGLE_QUERY_TEMPLATE, null).get(0);
	}
	
	@Override
	public E findFirst(String where) throws IllegalAccessException, SQLException, InstantiationException {
		return find(SELECT_SINGLE_WHERE_QUERY_TEMPLATE, where).get(0);
	}
	
	@Override
	public E findById(long id) throws IllegalAccessException, SQLException, InstantiationException {
		String primaryKeyName = getIdField().getAnnotation(Id.class).name();
		
		String whereString = MessageFormat.format(WHERE_PRIMARY_KEY, primaryKeyName, id);
		
		return findFirst(whereString);
	}
	
	@Override
	public boolean delete(String where) throws SQLException {
		String query = MessageFormat.format(DELETE_QUERY_TEMPLATE, getTableName(), where);
		
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		
		return preparedStatement.execute();
	}
	
	private boolean doUpdate(E entity) throws IllegalAccessException, SQLException {
		List<String> updatesQuery = getColumnFields().stream()
				.map(field -> {
					field.setAccessible(true);
					try {
						String columnName = field.getAnnotation(Column.class).name();
						Object value = field.get(entity);
						
						if (value instanceof String) {
							value = "\'" + value + "\'";
						}
						return MessageFormat.format(SET_QUERY_TEMPLATE, columnName, value);
						
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					return null;
				})
				.collect(Collectors.toList());
		String updatesQueryString = String.join(", ", updatesQuery);
		
		Field primaryKey = getIdField();
		primaryKey.setAccessible(true);
		String primaryKeyName = primaryKey.getAnnotation(Id.class).name();
		int primaryKeyValue = (int) primaryKey.get(entity);
		String query = MessageFormat.format(UPDATE_QUERY_TEMPLATE, getTableName(), updatesQueryString, primaryKeyName, primaryKeyValue);
		
		return connection.prepareStatement(query).execute();
	}
	
	private boolean doInsert(E entity) throws SQLException {
		List<String> columns = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		
		getColumnFields()
				.forEach(field -> {
					try {
						field.setAccessible(true);
						String columnName = field.getAnnotation(Column.class).name();
						Object value = field.get(entity);
						columns.add(columnName);
						values.add(value);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				});
		String columnsNames = String.join(", ", columns);
		String columnsValues = values.stream()
				.map(value -> {
					if (value instanceof String) {
						return "\'" + value + "\'";
					}
					return value;
				})
				.map(o -> {
					if (o == null) {
						return "NULL";
					}
					return o.toString();
				})
				.collect(Collectors.joining(", "));
		String query = MessageFormat.format(INSERT_QUERY_TEMPLATE, getTableName(), columnsNames, columnsValues);
		
		return connection.prepareStatement(query).execute();
	}
	
	private List<E> find(String template, String where) throws SQLException, IllegalAccessException, InstantiationException {
		String query = MessageFormat.format(template, getTableName(), where);
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		return toList(resultSet);
	}
	
	private void createTable() throws SQLException {
		Field primaryKeyField = getIdField();
		String primaryKeyColumnName = primaryKeyField.getAnnotation(Id.class).name();
		String primaryKeyColumnType = getColumnType(primaryKeyField);
		
		String primaryKeyColumnDefinition = String.
				format("%s %s PRIMARY KEY AUTO_INCREMENT",
						primaryKeyColumnName,
						primaryKeyColumnType);
		
		List<Field> columnFields = getColumnFields();
		List<String> columnParams = new ArrayList<>();
		
		columnFields.forEach(field -> {
			String columnName = field.getAnnotation(Column.class).name();
			String columnType = getColumnType(field);
			
			String columnDefinition = String.
					format("%s %s", columnName, columnType);
			columnParams.add(columnDefinition);
		});
		String createTableBody = String.format("%s, %s",
				primaryKeyColumnDefinition,
				String.join(", ", columnParams));
		
		String query = String
				.format("CREATE TABLE %s (%s)", getTableName(), createTableBody);
		
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.execute();
	}
	
	private void updateTable() throws SQLException {
		List<String> entityColumnNames = getColumnFields().stream()
				.map(field -> field.getAnnotation(Column.class).name())
				.collect(Collectors.toList());
		entityColumnNames.add(getIdField().getAnnotation(Id.class).name());
		
		List<String> tableColumnNames = getTableColumnNames();
		
		if (checkColumnsEquals(tableColumnNames, entityColumnNames)) {
			return;
		}
		
		List<String> newColumnNames = entityColumnNames.stream()
				.filter(tableColumnNames::contains)
				.collect(Collectors.toList());
		
		List<Field> newFields = getColumnFields().stream()
				.filter(field -> newColumnNames.contains(field.getAnnotation(Column.class).name()))
				.collect(Collectors.toList());
		
		List<String> columnDefinitions = new ArrayList<>();
		
		newFields.forEach(field -> {
			String columnDefinition = String.format("ADD COLUMN %s %s",
					field.getAnnotation(Column.class).name(),
					getColumnType(field));
			columnDefinitions.add(columnDefinition);
		});
		String queryBody = String.join(", ", columnDefinitions);
		String query = String.format("ALTER TABLE %s %s",
				getTableName(), queryBody);
		
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.execute();
	}
	
	private boolean checkColumnsEquals(List<String> tableColumnNames, List<String> entityColumnNames) {
		return new HashSet<>(tableColumnNames).equals(new HashSet<>(entityColumnNames));
	}
	
	private List<String> getTableColumnNames() throws SQLException {
		String query = String
				.format("SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = '%s'",
						getTableName());
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<String> columnNames = new ArrayList<>();
		
		while (resultSet.next()) {
			columnNames.add(resultSet.getString(1));
		}
		return columnNames;
	}
	
	private String getColumnType(Field primaryKeyField) {
		if (primaryKeyField.getType() == int.class || primaryKeyField.getType() == Integer.class) {
			return "INT";
		} else if (primaryKeyField.getType() == String.class) {
			return "VARCHAR(255)";
		}
		return null;
	}
	
	private boolean checkIfTableExist() throws SQLException {
		String query = String.
				format("SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = '%s'",
						getTableName());
		
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		return resultSet.next();
	}
	
	private List<E> toList(ResultSet resultSet) throws SQLException, InstantiationException, IllegalAccessException {
		List<E> result = new ArrayList<>();
		
		while (resultSet.next()) {
			E entity = createEntity(resultSet);
			result.add(entity);
		}
		
		return result;
	}
	
	private E createEntity(ResultSet resultSet) throws IllegalAccessException, InstantiationException, SQLException {
		E entity = klass.newInstance();
		List<Field> columnFields = getColumnFields();
		Field primaryKeyField = getIdField();
		
		primaryKeyField.setAccessible(true);
		String primaryKeyColumnName = primaryKeyField.getAnnotation(Id.class).name();
		int primaryKeyValue = resultSet.getInt(primaryKeyColumnName);
		primaryKeyField.set(entity, primaryKeyValue);
		
		columnFields.forEach(field -> {
			String columnName = field.getAnnotation(Column.class).name();
			try {
				field.setAccessible(true);
				
				if (field.getType() == Integer.class || field.getType() == int.class) {
					int value = resultSet.getInt(columnName);
					field.set(entity, value);
				} else if (field.getType() == String.class) {
					String value = resultSet.getString(columnName);
					field.set(entity, value);
				}
			} catch (SQLException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		return entity;
	}
	
	private String getTableName() {
		Annotation annotation = Arrays.stream(klass.getAnnotations())
				.filter(a -> a.annotationType() == Entity.class)
				.findFirst()
				.orElse(null);
		if (annotation == null) {
			return klass.getSimpleName().toLowerCase() + "s";
		}
		return klass.getAnnotation(Entity.class).name();
	}
	
	private List<Field> getColumnFields() {
		return Arrays.stream(klass.getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(Column.class))
				.collect(Collectors.toList());
	}
	
	private Field getIdField() {
		return Arrays.stream(klass.getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(Id.class))
				.findFirst()
				.orElseThrow(() -> new UnsupportedOperationException("Class " + klass.getSimpleName() + " does not have primary key"));
	}
}
