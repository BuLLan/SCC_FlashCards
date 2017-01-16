package scc.flashcards.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.GenericGenerator;

import scc.flashcards.persistence.PersistenceHelper;

/**
 * Base class for all models, which need to be persisted in database
 * 
 * @author Timi
 *
 */
public abstract class AbstractModel implements Comparable<AbstractModel> {

	private SessionFactory sessionFactory;
	
	protected int id;
	
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
	
	public boolean equals(Object o) {
		if(o == null) return false;
		if(this.compareTo((AbstractModel)o) == 0) return true;
		return false;
	}
	
	public abstract int getId();
	
	public abstract void setId(int id);
	
}
