package scc.flashcards.resources;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;

import com.owlike.genson.Genson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import scc.flashcards.model.Group;
import scc.flashcards.model.User;
import scc.flashcards.model.UserRole;
import scc.flashcards.persistence.PersistenceHelper;
import scc.flashcards.rest.NewUserRequest;
import scc.flashcards.rest.UpdateUserRequest;

/**
 * Webservice Resource for User model
 */
@Path("/users")
@Api(value="/users", tags={"User Service"})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(@ApiParam(value="Request", required=true) NewUserRequest request){
		try {
			request.validateRequest();
			User user = new User(request.getFirstName(), request.getLastName(),
					request.getEmail(), request.getPassword());
			user.persist();
			return Response.ok(user.getId()).status(Response.Status.OK).build();
		} catch (BadRequestException e){
			return Response.status(Response.Status.BAD_REQUEST).entity(new Genson().serialize(e.getMessage())).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e.getMessage())).build();
		}
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
	 * @param request
	 * @return
	 */
	@Path("/{userid}")
	@POST
	@ApiOperation(value="updateUser",
		notes="Update the Users Name and Password")
	public Response updateUser(
			@ApiParam(value="The Request", required=true) UpdateUserRequest request){
		try {
			request.validateRequest();
			
			User user = PersistenceHelper.getById(request.getId(), User.class);
			user.setFirstName((request.getFirstName().isEmpty()) ? user.getFirstName() : request.getFirstName());
			user.setLastName((request.getLastName().isEmpty()) ? user.getLastName() : request.getLastName());
			user.setLogin((request.getEmail().isEmpty()) ? user.getLogin() : request.getEmail());
			user.setPassword((request.getPassword().isEmpty()) ? user.getPassword() : request.getPassword());
			
			user.persist();
			
			return Response.ok(new Genson().serialize("Success")).status(Response.Status.OK).build();
			
		} catch (BadRequestException e){
			return Response.status(Response.Status.BAD_REQUEST).entity(new Genson().serialize(e.getMessage())).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e.getMessage())).build();
		}
	}
	
	/**
	 * Returns all groups of a user
	 * 
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
		Set<Group> foundGroups = new TreeSet<Group>();
		
		org.hibernate.SessionFactory sessionFactory = PersistenceHelper.getInstance().getSessionFactory();
		Session ses = sessionFactory.openSession();
		List<Group> allGroups = new LinkedList<Group>(ses.createCriteria(Group.class).list());
		ses.close();
		for (Group group : allGroups) {
			TreeSet<User> userSet = new TreeSet<User>(group.getUsers().keySet());
			if(userSet.contains(user)){
				foundGroups.add(group);
			}
		}
		
		return foundGroups;
	}
	
	@Path("/{userid}/groups")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create a Group with the user as owner")
	public Group createGroup(
			@ApiParam(value = "The ID of the owner", required=true) @PathParam("userid") int userid,
			@ApiParam(value = "The Group to be created") @FormParam("title") String title,
			@ApiParam(value = "The Group to be created") @FormParam("description") String description) {
		Group newGroup = new Group();
		newGroup.setTitle(title);
		newGroup.setDescription(description);
		
		User owner = PersistenceHelper.getById(userid, User.class);
		
		TreeMap<User, UserRole> users = new TreeMap<User, UserRole>();
		users.put(owner, UserRole.Admin);
		newGroup.setUsers(users);
				
		try {
			newGroup.persist();
		} catch (Exception e) {
			newGroup = null;
			throw new ServerErrorException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
		}
		return newGroup;
	}
	
}
