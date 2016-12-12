package scc.flashcards.model;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import scc.flashcards.persistence.PersistenceHelper;

/**
 * Base class for all models, which need to be persisted in database
 * 
 * @author Timi
 *
 */
public abstract class AbstractModel {

	private SessionFactory sessionFactory;
	
	/**
	 * Adds or updates Entity in database
	 */
	public void persist() {
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(this);
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * Removes Entity from Database
	 */
	public void delete() {
		Session session = getSessionFactory().openSession();
		session.delete(this);
		session.close();
	}
	
	protected SessionFactory getSessionFactory(){
		if(this.sessionFactory == null) {
			this.sessionFactory = PersistenceHelper.getInstance().getSessionFactory();
		}
		return this.sessionFactory;
	}
	
}
