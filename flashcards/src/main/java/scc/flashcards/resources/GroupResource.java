package scc.flashcards.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import scc.flashcards.model.Group;

@Path("/group")
@Api(value = "/group", tags={"Group Resource"})
public class GroupResource {

	@GET
	@ApiOperation(value="getGroupById")
	@Path("/{group_id}")
	public Group getGroupById(
			@ApiParam(name="group_id", value="The id of the group") @PathParam("group_id") int group_id
	){
		return null;
	}
	
	@POST
	@ApiOperation(value="Create a Group")
	public int createGroup(
			@ApiParam(name="group", value="A Group Object") Group group
	){
		group.persist();
		//TODO: get Id if newly created
		return group.getId();
	}
	
	@POST
	@ApiOperation(value="Update a Group")
	@Path("/{group_id}")
	public int updateGroup(
			@ApiParam(name="group_id", value="The id of the group") @PathParam("group_id") int group_id
	){
		return -1;
	}
	
	@DELETE
	@ApiOperation(value="Delete a Group")
	@Path("/{group_id}")
	public boolean deleteGroup(
			@ApiParam(name="group_id", value="The id of the group") @PathParam("group_id") int group_id
	){
		return true;
	}
	
	@POST
	@ApiOperation(value="Add Box to Group")
	@Path("/{group_id}/boxes")
	public boolean addBoxToGroup(
			@ApiParam(name="group_id", value="The id of the group") @PathParam("group_id") int group_id,
			@ApiParam(name="box_id", value="The id of the box") @FormParam("box_id") int box_id
	){
		return true;
	}
	
	@POST
	@ApiOperation(value="Invite User to Group")
	@Path("/{group_id}/members/{member_id}")
	public boolean inviteUserToGroup(
			@ApiParam(name="group_id", value="The id of the group") @PathParam("group_id") int group_id,
			@ApiParam(name="user_id", value="The id of the new member") @PathParam("user_id") int member_id
	){
		return true;
	}
	
	@DELETE
	@ApiOperation(value="Remove User from Group")
	@Path("/{group_id}/members/{member_id}")
	public boolean removeUserFromGroup(
			@ApiParam(name="group_id", value="The id of the group") @PathParam("group_id") int group_id,
			@ApiParam(name="user_id", value="The id of the new member") @PathParam("user_id") int member_id
	){
		return true;
	}
	
}
