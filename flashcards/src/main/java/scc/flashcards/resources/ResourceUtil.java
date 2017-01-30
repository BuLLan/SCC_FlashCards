package scc.flashcards.resources;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;

import com.owlike.genson.Genson;

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
	
	public static Response getResponse(Status status) {
		return Response.status(status)
				
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.allow("OPTIONS").build();
	}
	
	public static Response getResponse(Status status, Object entity) {
		return Response.ok(new Genson().serialize(entity))
				.status(status)
				
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.allow("OPTIONS").build();
	}
	
	public static Response getResponse(StatusType status, Object entity) {
		return Response.ok(new Genson().serialize(entity))
				.status(status)
				
				.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
				.allow("OPTIONS").build();
	}
	
}
