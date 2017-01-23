package scc.flashcards.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import scc.flashcards.persistence.PersistenceHelper;


public class UserDAO {

	  public static User getUserByEmail(String email) {
		org.hibernate.SessionFactory sessionFactory = PersistenceHelper.getInstance().getSessionFactory();
		Session ses = sessionFactory.openSession();
		return ses.get(User.class, email);
	  }
	}
