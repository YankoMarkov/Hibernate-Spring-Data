package app.orm;

import java.sql.SQLException;

public interface DbContext<E> {
	boolean persist(E entity) throws IllegalAccessException, SQLException;
	
	Iterable<E> find() throws IllegalAccessException, SQLException, InstantiationException;
	
	Iterable<E> find(String where) throws IllegalAccessException, SQLException, InstantiationException;
	
	E findFirst() throws IllegalAccessException, SQLException, InstantiationException;
	
	E findFirst(String where) throws IllegalAccessException, SQLException, InstantiationException;
	
	E findById(long id) throws IllegalAccessException, SQLException, InstantiationException;
	
	boolean delete(String where) throws SQLException;
}
