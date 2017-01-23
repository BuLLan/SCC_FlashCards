package scc.flashcards.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import scc.flashcards.model.user.UserRole;
import scc.flashcards.persistence.PersistenceHelper;


public class UserRoleDAO {
	
	public static List<UserRole> getUserRolesByEmail(String email) {
  
		Session ses = PersistenceHelper.getSession();
		final List<UserRole> list = new LinkedList<>();
		for(final Object o : ses.createQuery("from UserRole where email=?").setString(0, email).list()) {
		    list.add((UserRole)o);
		}
		ses.close();
		return list;
	  }

	}