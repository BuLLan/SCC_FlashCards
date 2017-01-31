package scc.flashcards.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.GenericGenerator;

import com.owlike.genson.annotation.JsonIgnore;

import scc.flashcards.persistence.PersistenceHelper;

@MappedSuperclass
public abstract class AbstractModel implements Comparable<AbstractModel> {

	@Transient
	@JsonIgnore
	private SessionFactory sessionFactory;

	@Id
	@GenericGenerator(name = "increment", strategy = "increment")
	@GeneratedValue(generator = "increment")
	protected long id;
	
	/**
	 * Adds or updates Entity in database
	 */
	public void persist() {
		PersistenceHelper.getSession().beginTransaction();
		PersistenceHelper.getSession().saveOrUpdate(this);
	}
	
	/**
	 * Removes Entity from Database
	 */
	public void delete() {
		PersistenceHelper.getSession().beginTransaction();
			PersistenceHelper.getSession().delete(this);
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
	
	public long getId(){
		return this.id;
	}
	
	public void setId(long id){
		this.id = id;
	}

	
}
