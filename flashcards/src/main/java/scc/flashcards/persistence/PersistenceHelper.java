package scc.flashcards.persistence;

import javax.inject.Singleton;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

@Singleton
public class PersistenceHelper {

	private static PersistenceHelper instance;
	private SessionFactory sessionFactory;
	
	protected PersistenceHelper(){
		// A SessionFactory is set up once for an application!
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure("hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
				.build();
		try {
			this.sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
		}
		catch (Exception e) {
			// The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
			// so destroy it manually.
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy( registry );
		}
	}
	
	public static PersistenceHelper getInstance(){
		if(instance == null){
			instance = new PersistenceHelper();
		}
		return instance;
	}
	
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}
	
}
