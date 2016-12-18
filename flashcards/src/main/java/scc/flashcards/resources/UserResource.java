package scc.flashcards.resources;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.persistence.sessions.factories.SessionFactory;
import org.hibernate.Session;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import scc.flashcards.model.Box;
import scc.flashcards.model.User;
import scc.flashcards.persistence.PersistenceHelper;

import java.io.Serializable;
import java.sql.SQLException;

/**
 * Webservice Resource for User model
 */
@Path("/users")
@Api(value="Flashcards REST Service")
@Produces(MediaType.TEXT_PLAIN)
public class UserResource {
	
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
		Serializable id = null;
		
		
		try {
			user.persist();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		
		return false;
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
