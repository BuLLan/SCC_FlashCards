package scc.flashcards.persistence;

import javax.inject.Singleton;
import javax.ws.rs.NotFoundException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

@Singleton
public class PersistenceHelper {

	private static PersistenceHelper instance;
	private SessionFactory sessionFactory;
	private static Session session;
	private static Transaction tx;

	protected PersistenceHelper() {
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		try {
			this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had
			// trouble building the SessionFactory
			// so destroy it manually.
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}

	/**
	 * Returns the instance
	 * @return
	 */
	public static PersistenceHelper getInstance() {
		if (instance == null) {
			instance = new PersistenceHelper();
		}
		return instance;
	}
	
	/**
	 * Opens a Session
	 */
	public static void openSession() {
		getSession();
	}
	
	/**
	 * Returns the current session, or opens a new one, if no open session exists.
	 * @return
	 */
	public static Session getSession(){
		if(session == null || !session.isOpen()){
			session = getInstance().getSessionFactory().openSession();
		}
		return session;
	}
	
	public static void flushAndCloseCurrentSession(){
		getSession().flush();
		closeSession();
	}
	
	public static void closeSession() {
		if(getSession() == null){
			return;
		}
		getSession().close();
	}

	/**
	 * Returns a persistent object by its Id
	 * 
	 * @param id Primary Id of Object in Store
	 * @param class Class of the Object
	 * @return
	 */
	public static <C> C getById(long id, Class<C> objectClass) {
		Session ses = PersistenceHelper.getSession();

		C element = null;
		element = ses.find(objectClass, id);
		if (element == null) {
			throw new NotFoundException(objectClass.getName() + " with id " + id + " not found");
		}
		return element;
	}

	/**
	 * Returns the sessionfactory instance
	 * @return
	 */
	protected SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

}
