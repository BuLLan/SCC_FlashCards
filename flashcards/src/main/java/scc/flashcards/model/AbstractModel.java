package scc.flashcards.model;

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
		Transaction tx = PersistenceHelper.getSession().beginTransaction();
		PersistenceHelper.getSession().saveOrUpdate(this);
		tx.commit();
	}
	
	/**
	 * Removes Entity from Database
	 */
	public void delete() {
		Transaction tx = PersistenceHelper.getSession().beginTransaction();
		PersistenceHelper.getSession().delete(this);
		tx.commit();
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
