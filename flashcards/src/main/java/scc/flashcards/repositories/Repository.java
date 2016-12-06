package scc.flashcards.repositories;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

public interface Repository {

	public Connection getConnection() throws SQLException ;
		
}
