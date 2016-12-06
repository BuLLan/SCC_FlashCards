package scc.flashcards.repositories;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import com.mysql.jdbc.PreparedStatement;

public abstract class DBRepository implements Repository {

	@Override
	public Connection getConnection() throws SQLException {
		DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
	    return DriverManager.getConnection("jdbc:mysql://localhost:3306/javabase","root","root");
	}

}
