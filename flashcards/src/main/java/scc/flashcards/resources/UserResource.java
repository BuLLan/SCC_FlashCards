package scc.flashcards.resources;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import scc.flashcards.model.Group;
import scc.flashcards.model.User;
import scc.flashcards.persistence.PersistenceHelper;

/**
 * Webservice Resource for User model
 */
@Path("/users")
@Api(value="/users", tags={"User Service"})
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
	
	/**
	 * Gets all users
	 * @return a list of users
	 */
	@GET
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
	notes="Create a new User and returns the id")
	@Consumes("application/x-www-form-urlencoded")
	public Response addUser(@ApiParam(value="firstname", required=true) @FormParam("firstname") String firstname,
						   @ApiParam(value="lastname", required=true) @FormParam("lastname") String lastname,
						   @ApiParam(value="email", required=true) @FormParam("email") String email,
						   @ApiParam(value="password", required=true) @FormParam("password") String password){
		boolean result = true;
		User user = new User(firstname, lastname, email, password);
		
		
		try {
			user.persist();
		} catch (Exception e) {
			throw new ServerErrorException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
		}
		
		return Response.ok(user.getId()).status(Response.Status.OK).build();
	}
	
	
	/**
	 * Gets a user by id
	 * 
	 * @param userid
	 * @return
	 */
	@Path("/{userid}")
	@GET
	@ApiOperation(value="getUserByID",
		notes="Returns the user object which has the given ID")
	public Response getUserById(@ApiParam(value="The ID of the user", required=true) @PathParam("userid") int userid){
		User user = PersistenceHelper.getById(userid, User.class);
		
		return Response.ok(user).status(Response.Status.OK).build();
	}
	
	/**
	 * Deletes a user by id
	 * 
	 * @param userid
	 * @return
	 */
	@Path("/{userid}")
	@DELETE
	@ApiOperation(value="deleteUser",
		notes="Returns true if user was deleted")
	public Response deleteUser(
			@ApiParam(value="The ID of the user to delete", required=true) @PathParam("userid") int userid){
		
		User user= PersistenceHelper.getById(userid, User.class);
		try {
			user.delete();
		} catch (Exception e) {
			throw new ServerErrorException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
		}
		return Response.ok("Success").status(Response.Status.OK).build();
	}
	
	/**
	 * Updates a user
	 * 
	 * @param userid
	 * @param updatedUser
	 * @return
	 */
	@Path("/{userid}")
	@POST
	@ApiOperation(value="updateUser",
		notes="Update the Users Name and Password")
	public Response updateUser(
			@ApiParam(value="The ID of the user", required=true) @PathParam("userid") int userid,
			@ApiParam(name = "group", value = "The Group to be created") User updatedUser){
		User user = PersistenceHelper.getById(userid, User.class);
		
		user.setFirstName(updatedUser.getFirstName());
		user.setLastName(updatedUser.getLastName());
		user.setPassword(updatedUser.getPassword());
		
		return Response.ok("Success").status(Response.Status.OK).build();
	}
	
	/**
	 * Returns all groups of a user
	 * @param userid
	 * @return
	 */
	@Path("/{userid}/groups")
	@GET
	@ApiOperation(value="getUserGroups",
		notes="Return all groups of a user")
	public Set<Group> getUserGroups(
			@ApiParam(value="The ID of the user", required=true) @PathParam("userid") int userid
			) {
		User user = PersistenceHelper.getById(userid, User.class);
		Set<Group> groups = user.getGroups();
		
		return groups;
	}
	
}
