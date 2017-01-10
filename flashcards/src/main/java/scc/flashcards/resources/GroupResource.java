package scc.flashcards.resources;

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
import javax.ws.rs.core.Response.ResponseBuilder;

import org.hibernate.Session;

import com.owlike.genson.Genson;
import com.owlike.genson.ext.jsr353.GensonJsonGenerator;
import com.owlike.genson.ext.jsr353.GensonJsonGeneratorFactory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javassist.tools.rmi.ObjectNotFoundException;
import scc.flashcards.model.Box;
import scc.flashcards.model.Group;
import scc.flashcards.model.User;
import scc.flashcards.persistence.PersistenceHelper;

@Path("/group")
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/group", tags = { "Group Resource" })
public class GroupResource {

	@GET
	@ApiOperation(value = "getGroupById")
	@Path("/{group_id}")
	public Group getGroupById(
			@ApiParam(name = "group_id", value = "The id of the group") @PathParam("group_id") int group_id) {
		return PersistenceHelper.getById(group_id, Group.class);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create a Group")
	public Group createGroup(@ApiParam(name = "group", value = "The Group to be created") Group group) {
		try {
			group.persist();
		} catch (Exception e) {
			group = null;
			throw new ServerErrorException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
		}
		return group;
	}

	@POST
	@ApiOperation(value = "Update a Group", notes = "Updates title and description of a group")
	@Path("/{group_id}")
	public Response updateGroup(
			@ApiParam(name = "group_id", value = "The ID of the group") @PathParam(value = "group_id") int group_id,
			@ApiParam(name = "group", value = "The Group to be created") Group newGroup) {
		Group group = PersistenceHelper.getById(group_id, Group.class);
		group.setId(group_id);
		group.setTitle(newGroup.getTitle());
		group.setDescription(newGroup.getDescription());
		try {
			group.persist();
		} catch (Exception e) {
			throw new ServerErrorException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
		}
		return Response.ok("Success").status(Response.Status.OK).build();
	}

	@DELETE
	@ApiOperation(value = "Delete a Group")
	@Path("/{group_id}")
	public Response deleteGroup(
			@ApiParam(name = "group_id", value = "The id of the group") @PathParam("group_id") int group_id) {
		Group group = PersistenceHelper.getById(group_id, Group.class);
		try {
			group.delete();
		} catch (Exception e) {
			throw new ServerErrorException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
		}
		return Response.ok("Success").status(Response.Status.OK).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "Add Box to Group")
	@Path("/{group_id}/boxes")
	public Response addBoxToGroup(
			@ApiParam(name = "group_id", value = "The id of the group") @PathParam("group_id") int group_id,
			@ApiParam(name = "box_id", value = "The id of the box") @FormParam("box_id") int box_id) {
		Group group = PersistenceHelper.getById(group_id, Group.class);
		Box box = PersistenceHelper.getById(box_id, Box.class);
		try {
			group.getBoxes().add(box);
			group.persist();
		} catch (Exception e) {
			return Response.serverError().entity(e).status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok("Success").status(Response.Status.OK).build();
	}

	@POST
	@ApiOperation(value = "Add User to Group")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/{group_id}/members/{user_id}")
	public Response inviteUserToGroup(
			@ApiParam(name = "group_id", value = "The id of the group") @PathParam("group_id") int group_id,
			@ApiParam(name = "user_id", value = "The id of the new member") @PathParam("user_id") int user_id) {
		Group group = PersistenceHelper.getById(group_id, Group.class);
		User user= PersistenceHelper.getById(user_id, User.class);
		try{
		group.getUsers().add(user);
		group.persist();
		} catch (Exception e) {
			return Response.serverError().entity(e).status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		Genson jsonHelper = new Genson();
		String json = jsonHelper.serialize(new Object(){
			public int status = 200;
			public String message = "User successfully added to group";
		});
		return Response.ok(json).status(Response.Status.OK).build();
	}

	@DELETE
	@ApiOperation(value = "Remove User from Group")
	@Path("/{group_id}/members/{user_id}")
	public Response removeUserFromGroup(
			@ApiParam(name = "group_id", value = "The id of the group") @PathParam("group_id") int group_id,
			@ApiParam(name = "user_id", value = "The id of the new member") @PathParam("user_id") int user_id) {
		Group group = PersistenceHelper.getById(group_id, Group.class);
		User user= PersistenceHelper.getById(user_id, User.class);
		boolean result;
		if(result = group.getUsers().remove(user)){
			try{
				group.persist();
			} catch (Exception e) {
				return Response.serverError().entity(e).status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		}
		Genson jsonHelper = new Genson();
		
		Object responseObj;
		if(result){
			responseObj = new Object(){
				public int status = 200;
				public String message = "User successfully removed from group.";
			};
		} else {
			responseObj = new Object(){
				public int status = 404;
				public String message = "User not found in group.";
			};
		}
		String json = jsonHelper.serialize(responseObj);
		return Response.ok(json).status(Response.Status.OK).build();
	}

}
