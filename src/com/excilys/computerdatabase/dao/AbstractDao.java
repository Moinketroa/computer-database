package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AbstractDao {

	protected DaoFactory daoFactory;
	
	public AbstractDao(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	public static PreparedStatement initializationPreparedStatement(Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets) throws SQLException {
	    PreparedStatement preparedStatement = connexion.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);

	    for (int i = 0; i < objets.length; i++)
	        preparedStatement.setObject(i + 1, objets[i]);

	    return preparedStatement;
	}
	
	private static void closeResource(ResultSet resultSet) {
	    if (resultSet != null) {
	        try {
	            resultSet.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	private static void closeResource(Statement statement) {
	    if (statement != null ) {
	        try {
	            statement.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	private static void closeResource(Connection connexion) {
	    if (connexion != null) {
	        try {
	            connexion.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public static void closeResources(Statement statement, Connection connexion, ResultSet resultSet) {
		closeResource(connexion);
		closeResource(resultSet);
		closeResource(statement);
	}
	
	public static void closeResources(Statement statement, ResultSet resultSet) {
		closeResource(resultSet);
		closeResource(statement);
	}
}
