package scc.flashcards.resources;

import java.util.List;
import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import scc.flashcards.model.flashcards.Box;
import scc.flashcards.model.flashcards.Category;
import scc.flashcards.model.user.User;

@Path("/search")
@Api(value = "/search", tags={"Searching Resources"})
public class SearchServiceProvider {

	@GET
	@ApiOperation(value="Returns list of users with given nickname")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/users/{name}")
	public List<User> searchUsers(
			@ApiParam(name="name", value="nickname of user") @PathParam("name") String name
	){
		//TODO
		return null;
	}
	
	@GET
	@ApiOperation(value="Returns list of categories containing the query")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/category/{query}")
	public List<Category> searchByCategory(
			@ApiParam(name="query", value="Id of the category") @PathParam("query") String name
	){
		//TODO
		return null;
	}
	
	@GET
	@ApiOperation(value="Returns list of box with given tags")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/box/tag/{tags}")
	public List<Box> searchByTags(
			@ApiParam(name="tags", value="Id of the category") @PathParam("tags") Set<String> tags
	){
		//TODO
		return null;
	}
}
