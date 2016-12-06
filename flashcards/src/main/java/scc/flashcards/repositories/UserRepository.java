package scc.flashcards.repositories;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLXML;

import org.eclipse.persistence.jaxb.xmlmodel.XmlTransformation;
import org.eclipse.persistence.jaxb.xmlmodel.XmlTransformation.XmlWriteTransformer;

import java.sql.PreparedStatement;

import scc.flashcards.model.User;

public class UserRepository extends DBRepository{

	/**
	 * TODO: Just a test case
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public boolean addUser(User user) throws SQLException{
		Connection con = this.getConnection();
		String insertTableSQL = "INSERT INTO users"
				+ "(USERNAME, PASSWORD) VALUES"
				+ "(?,?)";
		PreparedStatement statement = con.prepareStatement(insertTableSQL);
		statement.setString(1, user.getLogin());
		statement.setString(2, user.getPassword());
		statement.execute();
		return true;
	}
	
}
