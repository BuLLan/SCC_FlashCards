package scc.flashcards.model;

import org.hibernate.Session;

import scc.flashcards.model.user.User;
import scc.flashcards.persistence.PersistenceHelper;


public class UserDAO {

	  public static User getUserByEmail(String email) {
		Session ses = PersistenceHelper.getSession();
		return ses.get(User.class, email);
	  }
	}
