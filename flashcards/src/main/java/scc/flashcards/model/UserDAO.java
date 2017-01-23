package scc.flashcards.model;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.shiro.authc.UnknownAccountException;
import org.hibernate.Session;

import scc.flashcards.model.user.User;
import scc.flashcards.persistence.PersistenceHelper;

public class UserDAO {

	public static User getUserByEmail(String email) {
		try{
			Session session = PersistenceHelper.getSession();			
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> critQuery = builder.createQuery(User.class);
			Root<User> userRoot = critQuery.from(User.class);
			critQuery.select(userRoot).where(builder.equal(userRoot.get("login"), email));
			return session.createQuery(critQuery).getSingleResult();
			
		} catch (NoResultException e) {
			throw new UnknownAccountException();
		} finally {
			PersistenceHelper.closeSession();
		}
	}
}
