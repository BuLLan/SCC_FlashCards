package scc.flashcards.resources;

import java.util.List;
import java.util.TreeMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Root;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.owlike.genson.Genson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
	@ApiOperation(value = "getAllUsers", notes = "Returns a list of all users", response = User.class, responseContainer = "List")
	public Response getAllUsers() {
		Session session = PersistenceHelper.getSession();
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> critQuery = builder.createQuery(User.class);
			Root<User> userRoot = critQuery.from(User.class);
			critQuery.select(userRoot);
			List<User> allUsers = session.createQuery(critQuery).getResultList();
			return Response.ok(new Genson().serialize(allUsers)).build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (ClientErrorException e) {
			return e.getResponse();
		} catch (Exception e) {
			// Something else went wrong
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e)).build();
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
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (ClientErrorException e) {
			return e.getResponse();
		} catch (Exception e) {
			// Something else went wrong
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e)).build();
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
	@ApiOperation(value = "getUserByID", notes = "Returns the user object which has the given ID", response = User.class)
	public Response getUserById(
			@ApiParam(value = "The ID of the user", required = true) @PathParam("userid") int userid) {
		try {
			PersistenceHelper.openSession();
			User user = PersistenceHelper.getById(userid, User.class);
			return Response.ok(new Genson().serialize(user)).build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (ClientErrorException e) {
			return e.getResponse();
		} catch (Exception e) {
			// Something else went wrong
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e)).build();
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

		try {
			PersistenceHelper.openSession();
			User user = PersistenceHelper.getById(userid, User.class);
			user.delete();
			return Response.ok().build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (ClientErrorException e) {
			return e.getResponse();
		} catch (Exception e) {
			// Something else went wrong
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e)).build();
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
			PersistenceHelper.openSession();
			User user = PersistenceHelper.getById(request.getId(), User.class);
			user.setFirstName((request.getFirstName().isEmpty()) ? user.getFirstName() : request.getFirstName());
			user.setLastName((request.getLastName().isEmpty()) ? user.getLastName() : request.getLastName());
			user.setLogin((request.getEmail().isEmpty()) ? user.getLogin() : request.getEmail());
			user.setPassword((request.getPassword().isEmpty()) ? user.getPassword() : request.getPassword());
			user.persist();
			return Response.ok().build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (ClientErrorException e) {
			return e.getResponse();
		} catch (Exception e) {
			// Something else went wrong
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e)).build();
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
	@ApiOperation(value = "getUserGroups", notes = "Return all groups of a user", response = Group.class, responseContainer = "List")
	public Response getUserGroups(
			@ApiParam(value = "The ID of the user", required = true) @PathParam("userid") int userid) {
		try {
			Session session = PersistenceHelper.getSession();
			/*
			 * Damn, this really works!
			 */
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Group> critQuery = builder.createQuery(Group.class);

			Root<Group> groupRoot = critQuery.from(Group.class);
			MapJoin<Group, User, UserRole> groups = groupRoot.joinMap("users");

			critQuery.select(groupRoot);
			critQuery.where(builder.equal(groups.key(), userid));

			List<Group> groupList = session.createQuery(critQuery).getResultList();
			return Response.ok(new Genson().serialize(groupList)).build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new Genson().serialize(e))
					.build();
			throw new ServiceUnavailableException(response);
		} catch (Exception e) {
			// Something else went wrong
			Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e))
					.build();
			throw new InternalServerErrorException(response);
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	@Path("/{userid}/groups")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create a Group with the user as owner", response=Group.class)
	public Response createGroup(@ApiParam(value = "The ID of the owner", required = true) @PathParam("userid") int userid,
			@ApiParam(value = "The Group to be created") NewGroupRequest request) {
		try {
			PersistenceHelper.openSession();
			Group newGroup = new Group();
			newGroup.setTitle(request.getTitle());
			newGroup.setDescription(request.getDescription());

			User owner = PersistenceHelper.getById(userid, User.class);

			TreeMap<User, UserRole> users = new TreeMap<User, UserRole>();
			users.put(owner, UserRole.Admin);
			newGroup.setUsers(users);
			newGroup.persist();

			return Response.ok(new Genson().serialize(newGroup)).build();
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
