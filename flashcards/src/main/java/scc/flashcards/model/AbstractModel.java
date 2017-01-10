package scc.flashcards.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import scc.flashcards.persistence.PersistenceHelper;

/**
 * Base class for all models, which need to be persisted in database
 * 
 * @author Timi
 *
 */
public abstract class AbstractModel implements Comparable<AbstractModel> {

	private SessionFactory sessionFactory;
	
	private int id;
	
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
		session.beginTransaction();
		session.delete(this);
		session.getTransaction().commit();
		session.close();
	}
	
	protected SessionFactory getSessionFactory(){
		if(this.sessionFactory == null) {
			this.sessionFactory = PersistenceHelper.getInstance().getSessionFactory();
		}
		return this.sessionFactory;
	}

	@Override
	/**
	 * By implementing this method we don't have to check for duplicate occurences,
	 * if we add a Entity to another entities collection.
	 */
	public int compareTo(AbstractModel o) {
		if(this.getId() == o.getId()){
			return 0;
		}
		return -1;
	}
	
	public abstract int getId();
	
	public abstract void setId(int id);
	
}
