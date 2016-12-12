package scc.flashcards.repositories;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.persistence.jaxb.xmlmodel.XmlTransformation;
import org.eclipse.persistence.jaxb.xmlmodel.XmlTransformation.XmlWriteTransformer;

import java.sql.PreparedStatement;

import scc.flashcards.model.User;

public class UserRepository extends DBRepository{
	static List<User> users = new ArrayList<User>();
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
	
	
	public User findUserById(int id) {
	    for (User user : users) {
	      if (user.getId().equals(id)) {
	        return user;
	      }
	    }
	    return null;
	  }

//	  public void addUser(User user) {
//	    if (users.size() > 0) {
//	      for (int i = users.size() - 1; i >= 0; i--) {
//	        if (users.get(i).getUsername().equals(user.getUsername())) {
//	          users.remove(i);
//	        }
//	      }
//	    }
//	    users.add(user);
//	  }

	  public boolean removeUser(String login) {
	    if (users.size() > 0) {
	      for (int i = users.size() - 1; i >= 0; i--) {
	        if (users.get(i).getLogin().equals(login)) {
	          users.remove(i);
	          return true;
	        }
	      }
	    }
	    return false;
	  }

//	  private static User createUser(long id, String username, String firstName,
//	      String lastName, String email, int userStatus) {
//	    User user = new User();
//	    user.setId(id);
//	    user.setUsername(username);
//	    user.setFirstName(firstName);
//	    user.setLastName(lastName);
//	    user.setEmail(email);
//	    user.setPassword("XXXXXXXXXXX");
//	    user.setUserStatus(userStatus);
//	    return user;
//	  }
	
	
	
	
}
