package scc.flashcards.resources;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;

import scc.flashcards.model.UserDAO;
import scc.flashcards.model.user.User;

public class ResourceUtil {

	/**
	 * Checks if a user is the owner of a resource
	 * 
	 * @deprecated use getCurrentUser instead
	 * 
	 * @param ownerId
	 * @return
	 */
	public static boolean isOwner(long ownerId){
		org.apache.shiro.mgt.SecurityManager securityManager = new IniSecurityManagerFactory().getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
		
		if(currentUser.isAuthenticated()){
			User user = UserDAO.getUserByEmail((String)currentUser.getSession().getAttribute("username"));
			return (user.getId() == ownerId);
		}
		return false;
	}
	
	public static User getCurrentUser() {
		org.apache.shiro.mgt.SecurityManager securityManager = new IniSecurityManagerFactory().getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
		
		if(currentUser.isAuthenticated()){
			User user = UserDAO.getUserByEmail((String)currentUser.getSession().getAttribute("username"));
			return user;
		}
		return null;
	}
	
}
