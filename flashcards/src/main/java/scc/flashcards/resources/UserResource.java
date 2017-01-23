package scc.flashcards.resources;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.owlike.genson.Genson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import scc.flashcards.model.flashcards.Box;
import scc.flashcards.model.user.Group;
import scc.flashcards.model.user.User;
import scc.flashcards.model.user.UserRole;
import scc.flashcards.persistence.PersistenceHelper;
import scc.flashcards.rest.NewGroupRequest;
import scc.flashcards.rest.NewUserRequest;
import scc.flashcards.rest.UpdateUserRequest;

/**
 * Webservice Resource for User model
 */
@Path("/users")
@Api(value = "/users", tags = { "User Service" })
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

	/**
	 * Gets all users
	 * 
	 * @return a list of users
	 */
	@GET
	@ApiOperation(value = "getAllUsers", notes = "Returns a list of all users")
	public List<User> getAllUsers() {
		Session session = PersistenceHelper.getSession();
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> critQuery = builder.createQuery(User.class);
			Root<User> userRoot = critQuery.from(User.class);
			critQuery.select(userRoot);
			List<User> allUsers = session.createQuery(critQuery).getResultList();
			return allUsers;
		} catch (HibernateException e) {
			// Something went wrong with the Database
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new ServiceUnavailableException(response);
		} catch (Exception e) {
			// Something else went wrong
			Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Genson().serialize(e)).build();
			throw new InternalServerErrorException(response);
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	/**
	 * Adds a new user
	 * 
	 * @return
	 */
	@POST
	@ApiOperation(value = "addUser", notes = "Create a new User and returns the id")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUser(@ApiParam(value = "Request", required = true) NewUserRequest request) {
		try {
			PersistenceHelper.openSession();
			
			request.validateRequest();
			User user = new User(request.getFirstName(), request.getLastName(), request.getEmail(),
					request.getPassword());
			user.persist();
			return Response.ok(user.getId()).status(Response.Status.OK).build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new ServiceUnavailableException(response);
		} catch (BadRequestException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(new Genson().serialize(e.getMessage())).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e.getMessage()))
					.build();
		} finally {
			PersistenceHelper.closeSession();
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
	@ApiOperation(value = "getUserByID", notes = "Returns the user object which has the given ID")
	public User getUserById(
			@ApiParam(value = "The ID of the user", required = true) @PathParam("userid") int userid) {
		PersistenceHelper.openSession();
		
		try{		
			User user = PersistenceHelper.getById(userid, User.class);
			return user;
		} catch (HibernateException e) {
			// Something went wrong with the Database
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new ServiceUnavailableException(response);
		} catch (Exception e) {
			// Something else went wrong
			Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new InternalServerErrorException(response);
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	/**
	 * Deletes a user by id
	 * 
	 * @param userid
	 * @return
	 */
	@Path("/{userid}")
	@DELETE
	@ApiOperation(value = "deleteUser", notes = "Returns true if user was deleted")
	public Response deleteUser(
			@ApiParam(value = "The ID of the user to delete", required = true) @PathParam("userid") int userid) {

		User user = PersistenceHelper.getById(userid, User.class);
		try {
			user.delete();
			return Response.ok().build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new ServiceUnavailableException(response);
		} catch (Exception e) {
			// Something else went wrong
			Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new InternalServerErrorException(response);
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	/**
	 * Updates a user
	 * 
	 * @param request
	 * @return
	 */
	@Path("/{userid}")
	@POST
	@ApiOperation(value = "updateUser", notes = "Update the Users Name and Password")
	public Response updateUser(@ApiParam(value = "The Request", required = true) UpdateUserRequest request) {
		PersistenceHelper.openSession();
		
		try {
			request.validateRequest();

			User user = PersistenceHelper.getById(request.getId(), User.class);
			user.setFirstName((request.getFirstName().isEmpty()) ? user.getFirstName() : request.getFirstName());
			user.setLastName((request.getLastName().isEmpty()) ? user.getLastName() : request.getLastName());
			user.setLogin((request.getEmail().isEmpty()) ? user.getLogin() : request.getEmail());
			user.setPassword((request.getPassword().isEmpty()) ? user.getPassword() : request.getPassword());

			user.persist();

			return Response.ok().build();

		} catch (HibernateException e) {
			// Something went wrong with the Database
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new ServiceUnavailableException(response);
		} catch (Exception e) {
			// Something else went wrong
			Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new InternalServerErrorException(response);
		} finally {
			PersistenceHelper.closeSession();
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
	@ApiOperation(value = "getUserGroups", notes = "Return all groups of a user")
	public List<Group> getUserGroups(
			@ApiParam(value = "The ID of the user", required = true) @PathParam("userid") int userid) {
		Session session = PersistenceHelper.getSession();

		try {
			/*
			 * Damn, this really works!
			 */
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Group> critQuery = builder.createQuery(Group.class);
			
			Root<Group> groupRoot = critQuery.from(Group.class);
			MapJoin<Group, User, UserRole> groups = groupRoot.joinMap("users");
			
			critQuery.select(groupRoot);
			critQuery.where(builder.equal(groups.key(),userid));

			return session.createQuery(critQuery).getResultList();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e)).build();
			throw new ServiceUnavailableException(response);
		} catch (Exception e) {
			// Something else went wrong
			Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Genson().serialize(e)).build();
			throw new InternalServerErrorException(response);
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	@Path("/{userid}/groups")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create a Group with the user as owner")
	public Group createGroup(@ApiParam(value = "The ID of the owner", required = true) @PathParam("userid") int userid,
			@ApiParam(value = "The Group to be created") NewGroupRequest request) {
		PersistenceHelper.openSession();
		
		try{
			Group newGroup = new Group();
			newGroup.setTitle(request.getTitle());
			newGroup.setDescription(request.getDescription());

			User owner = PersistenceHelper.getById(userid, User.class);

			TreeMap<User, UserRole> users = new TreeMap<User, UserRole>();
			users.put(owner, UserRole.Admin);
			newGroup.setUsers(users);
			newGroup.persist();
			
			return newGroup;
		} catch (HibernateException e) {
			// Something went wrong with the Database
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new ServiceUnavailableException(response);
		} catch (Exception e) {
			// Something else went wrong
			Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new InternalServerErrorException(response);
		} finally {
			PersistenceHelper.closeSession();
		}
	}

}
