package scc.flashcards.repositories;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLXML;

import org.eclipse.persistence.jaxb.xmlmodel.XmlTransformation;
import org.eclipse.persistence.jaxb.xmlmodel.XmlTransformation.XmlWriteTransformer;

import java.sql.PreparedStatement;

import scc.flashcards.model.Box;

public class BoxRepository extends DBRepository{

	/**
	 * TODO: Just a test case
	 * @param box
	 * @return
	 * @throws SQLException
	 */
	public boolean addBox(Box box) throws SQLException{
		Connection con = this.getConnection();
		String insertTableSQL = "INSERT INTO users"
				+ "(TITLE, CATEGORY, SUBCATEGORY) VALUES"
				+ "(?,?)";
		PreparedStatement statement = con.prepareStatement(insertTableSQL);
		statement.setString(1, box.getTitle());
		statement.setInt(2, box.getCategory_id());
		statement.setInt(3, box.getSubcategory_id());
		statement.execute();
		return true;
	}
	
}
