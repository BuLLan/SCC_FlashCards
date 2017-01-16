package scc.flashcards.resources;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.Factory;
import org.hibernate.Session;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import scc.flashcards.model.User;
import scc.flashcards.persistence.PersistenceHelper;

/**
 * Webservice Resource for User model
 */
@Path("/users")
@Api(value="/users", tags={"User Service"})
@Produces(MediaType.TEXT_PLAIN)
public class UserResource {
	Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory();
	org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
	
	UserResource(){
	    SecurityUtils.setSecurityManager(securityManager);
	}
	
	/**
	 * Gets all users
	 * @return a list of users
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value="getAllUsers",
	notes="Returns a list of all users")
	public List<User> getAllUsers() {
		org.hibernate.SessionFactory sessionFactory = PersistenceHelper.getInstance().getSessionFactory();
		Session ses = sessionFactory.openSession();
		final List<User> list = new LinkedList<>();
		for(final Object o : ses.createCriteria(User.class).list()) {
		    list.add((User)o);
		}
		return list;
	}
	
	/**
	 * Adds a new user
	 * @return
	 */
	@POST
	@ApiOperation(value="addUser",
	notes="create a new User")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes("application/x-www-form-urlencoded")
	public boolean addUser(@ApiParam(value="firstname", required=true) @FormParam("firstname") String firstname,
						   @ApiParam(value="lastname", required=true) @FormParam("lastname") String lastname,
						   @ApiParam(value="email", required=true) @FormParam("email") String email,
						   @ApiParam(value="password", required=true) @FormParam("password") String password){
		boolean result = true;
		User user = new User(firstname, lastname, email, password);
		generatePassword(user, password);
		
		try {
			user.persist();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		
		return false;
	}
	

	private void generatePassword(User user, String plainTextPassword) {
	  RandomNumberGenerator rng = new SecureRandomNumberGenerator();
	  Object salt = rng.nextBytes();

	  // Now hash the plain-text password with the random salt and multiple
	  // iterations and then Base64-encode the value (requires less space than Hex):
	  String hashedPasswordBase64 = new Sha256Hash(plainTextPassword, salt,1024).toBase64();

	  user.setPassword(hashedPasswordBase64);
	  user.setSalt(salt.toString());
	}
	
	/**
	 * get Token
	 * @return
	 */
	@POST
	@ApiOperation(value="login",
	notes="login")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes("application/x-www-form-urlencoded")
	public boolean login(@ApiParam(value="email", required=true) @FormParam("email") String email,
						   @ApiParam(value="password", required=true) @FormParam("password") String password){

		
		  // get the currently executing user:
		  org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();

		  if (!currentUser.isAuthenticated()) {
		    //collect user principals and credentials in a gui specific manner
		    //such as username/password html form, X509 certificate, OpenID, etc.
		    //We'll use the username/password example here since it is the most common.
		    UsernamePasswordToken token = new UsernamePasswordToken(email,password);
		     //this is all you have to do to support 'remember me' (no config - built in!):
		    token.setRememberMe(true);

		    try {
		        currentUser.login(token);
		        System.out.println("User [" + currentUser.getPrincipal().toString() + "] logged in successfully.");
		        
		        // save current username in the session, so we have access to our User model
		        currentUser.getSession().setAttribute("email", email);
		        return true;
		    } catch (UnknownAccountException uae) {
		      System.out.println("There is no user with username of "
		                + token.getPrincipal());
		    } catch (IncorrectCredentialsException ice) {
		      System.out.println("Password for account " + token.getPrincipal()
		                + " was incorrect!");
		    } catch (LockedAccountException lae) {
		      System.out.println("The account for username " + token.getPrincipal()
		                + " is locked.  "
		                + "Please contact your administrator to unlock it.");
		    }
		  } else {
		    return true; // already logged in
		  }

		  return false;
		
//		boolean result = true;
//		User user = new User(firstname, lastname, email, password);
//		generatePassword(user, password);
//		
//		try {
//			user.persist();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			result = false;
//		}
		
	}
	
	
	
	/**
	 * Gets a user by id
	 * 
	 * @param userid
	 * @return
	 */
	@Path("/{userid}")
	@GET
	@Produces({"application/json","application/xml"})
	@ApiOperation(value="getUserByID",
		notes="Returns the user object which has the given ID")
	public User getUserById(@ApiParam(value="The ID of the user", required=true) @PathParam("userid") int userid){
		org.hibernate.SessionFactory sessionFactory = PersistenceHelper.getInstance().getSessionFactory();
		Session ses = sessionFactory.openSession();
		return ses.get(User.class, userid);
	}
	
	/**
	 * Deletes a user by id
	 * 
	 * @param userid
	 * @return
	 */
	@Path("/{userid}")
	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value="deleteUser",
		notes="Returns true if user was deleted")
	public boolean deleteUser(
			@ApiParam(value="The ID of the user to delete", required=true) @PathParam("userid") int userid){
				org.hibernate.SessionFactory sessionFactory = PersistenceHelper.getInstance().getSessionFactory();
				Session ses = sessionFactory.openSession();
				ses.beginTransaction();
				ses.delete(ses.get(User.class, userid));
				ses.getTransaction().commit();
				ses.close();
		return true;
	}
	
	/**
	 * Updates a user
	 * 
	 * @param userid
	 * @return
	 */
	@Path("/{userid}")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value="updateUser",
		notes="Returns true if user was updated")
	public boolean updateUser(
			@ApiParam(value="The ID of the user", required=true) @PathParam("userid") int userid){
		/**
		 * TODO: implement stub
		 */
		return true;
	}
	
	
	
}
