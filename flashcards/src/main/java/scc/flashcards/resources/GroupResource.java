package scc.flashcards.resources;

import java.util.TreeMap;
import java.util.TreeSet;

import javax.ws.rs.ClientErrorException;
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
import javax.ws.rs.core.Response.Status;

import org.hibernate.HibernateException;

import com.owlike.genson.Genson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import scc.flashcards.model.flashcards.Box;
import scc.flashcards.model.user.Group;
import scc.flashcards.model.user.User;
import scc.flashcards.model.user.UserRole;
import scc.flashcards.persistence.PersistenceHelper;
import scc.flashcards.rest.AddBoxToGroupRequest;
import scc.flashcards.rest.NewGroupRequest;

@Path("/group")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/group", tags = { "Group Resource" })
public class GroupResource {

	@GET
	@ApiOperation(value = "getGroupById", response = Group.class)
	@Path("/{group_id}")
	public Response getGroupById(
			@ApiParam(name = "group_id", value = "The id of the group") @PathParam("group_id") int group_id) {
		try {
			PersistenceHelper.openSession();
			Group group = PersistenceHelper.getById(group_id, Group.class);
			return Response.ok(new Genson().serialize(group)).build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new ServiceUnavailableException(response);
		} catch (ClientErrorException e) {
			return e.getResponse();
		} catch (Exception e) {
			// Something else went wrong
			Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e))
					.build();
			throw new InternalServerErrorException(response);
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	@POST
	@ApiOperation(value = "Update a Group", notes = "Updates title and description of a group")
	@Path("/{group_id}")
	public Response updateGroup(
			@ApiParam(name = "group_id", value = "The ID of the group") @PathParam(value = "group_id") int group_id,
			@ApiParam(name = "request", value = "The Group to be created_") NewGroupRequest request) {
		try {
			request.validateRequest();
			PersistenceHelper.openSession();
			Group group = PersistenceHelper.getById(group_id, Group.class);
			group.setId(group_id);
			group.setTitle(request.getTitle());
			group.setDescription(request.getDescription());
			group.persist();
			return Response.ok().build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new ServiceUnavailableException(response);
		} catch (ClientErrorException e) {
			return e.getResponse();
		} catch (Exception e) {
			// Something else went wrong
			Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e))
					.build();
			throw new InternalServerErrorException(response);
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	@DELETE
	@ApiOperation(value = "Delete a Group")
	@Path("/{group_id}")
	public Response deleteGroup(
			@ApiParam(name = "group_id", value = "The id of the group") @PathParam("group_id") int group_id) {
		try {
			PersistenceHelper.openSession();
			Group group = PersistenceHelper.getById(group_id, Group.class);
			group.delete();
			return Response.ok().build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new ServiceUnavailableException(response);
		} catch (ClientErrorException e) {
			return e.getResponse();
		} catch (Exception e) {
			// Something else went wrong
			Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e))
					.build();
			throw new InternalServerErrorException(response);
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	@POST
	@ApiOperation(value = "Add Box to Group")
	@Path("/{group_id}/boxes")
	public Response addBoxToGroup(
			@ApiParam(name = "group_id", value = "The id of the group") @PathParam("group_id") int group_id,
			@ApiParam(name = "box_id", value = "The id of the box") AddBoxToGroupRequest request) {
		try {
			request.validateRequest();
			PersistenceHelper.openSession();
			Group group = PersistenceHelper.getById(group_id, Group.class);
			Box box = PersistenceHelper.getById(request.getBoxId(), Box.class);
			TreeSet<Box> boxes = new TreeSet<Box>(group.getBoxes());
			boxes.add(box);
			group.setBoxes(boxes);
			group.persist();
			return Response.ok().build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new ServiceUnavailableException(response);
		} catch (ClientErrorException e) {
			return e.getResponse();
		} catch (Exception e) {
			// Something else went wrong
			Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e))
					.build();
			throw new InternalServerErrorException(response);
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "Remove Box from Group")
	@Path("/{group_id}/boxes")
	public Response removeBoxFromGroup(
			@ApiParam(name = "group_id", value = "The id of the group") @PathParam("group_id") int group_id,
			@ApiParam(name = "box_id", value = "The id of the box") AddBoxToGroupRequest request) {
		try {
			request.validateRequest();
			PersistenceHelper.openSession();
			Group group = PersistenceHelper.getById(group_id, Group.class);
			Box box = PersistenceHelper.getById(request.getBoxId(), Box.class);
			TreeSet<Box> boxes = new TreeSet<Box>(group.getBoxes());
			boxes.remove(box);
			group.setBoxes(boxes);
			group.persist();
			return Response.ok().build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new ServiceUnavailableException(response);
		} catch (ClientErrorException e) {
			return e.getResponse();
		} catch (Exception e) {
			// Something else went wrong
			Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e))
					.build();
			throw new InternalServerErrorException(response);
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	@POST
	@ApiOperation(value = "Add User to Group")
	@Path("/{group_id}/members/{user_id}")
	public Response inviteUserToGroup(
			@ApiParam(name = "group_id", value = "The id of the group") @PathParam("group_id") int group_id,
			@ApiParam(name = "user_id", value = "The id of the new member") @PathParam("user_id") int user_id) {
		try {
			PersistenceHelper.openSession();
			Group group = PersistenceHelper.getById(group_id, Group.class);
			User user = PersistenceHelper.getById(user_id, User.class);
			TreeMap<User, UserRole> users = new TreeMap<User, UserRole>(group.getUsers());
			users.put(user, UserRole.Member);
			group.setUsers(users);
			group.persist();
			return Response.ok().build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new ServiceUnavailableException(response);
		} catch (ClientErrorException e) {
			return e.getResponse();
		} catch (Exception e) {
			// Something else went wrong
			Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e))
					.build();
			throw new InternalServerErrorException(response);
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	@DELETE
	@ApiOperation(value = "Remove User from Group")
	@Path("/{group_id}/members/{user_id}")
	public Response removeUserFromGroup(
			@ApiParam(name = "group_id", value = "The id of the group") @PathParam("group_id") int group_id,
			@ApiParam(name = "user_id", value = "The id of the new member") @PathParam("user_id") int user_id) {
		try {
			PersistenceHelper.openSession();
			Group group = PersistenceHelper.getById(group_id, Group.class);
			User user = PersistenceHelper.getById(user_id, User.class);
			if (group.getUsers().remove(user) != null) {
				group.persist();
				return Response.ok().build();
			} else {
				return Response.status(Status.CONFLICT)
						.entity(new Genson().serialize("Requested user is not a member of requested group.")).build();
			}
		} catch (HibernateException e) {
			// Something went wrong with the Database
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new ServiceUnavailableException(response);
		} catch (ClientErrorException e) {
			return e.getResponse();
		} catch (Exception e) {
			// Something else went wrong
			Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e))
					.build();
			throw new InternalServerErrorException(response);
		} finally {
			PersistenceHelper.closeSession();
		}
	}

}
