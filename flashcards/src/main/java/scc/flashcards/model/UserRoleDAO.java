package scc.flashcards.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import scc.flashcards.persistence.PersistenceHelper;


public class UserRoleDAO {
	
	public static List<UserRole> getUserRolesByEmail(String email) {
  
	    org.hibernate.SessionFactory sessionFactory = PersistenceHelper.getInstance().getSessionFactory();
		Session ses = sessionFactory.openSession();
		final List<UserRole> list = new LinkedList<>();
		for(final Object o : ses.createQuery("from UserRole where email=?").setString(0, email).list()) {
		    list.add((UserRole)o);
		}
		return list;
	  }
	
	  public static void insert(UserRole r) {
		    
			boolean result = true;
			UserRole userRole = r;
			
			
			try {
				userRole.persist();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = false;
			}

	  }
	}