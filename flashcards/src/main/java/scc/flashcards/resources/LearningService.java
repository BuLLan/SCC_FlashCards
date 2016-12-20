package scc.flashcards.resources;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import scc.flashcards.model.Comment;
import scc.flashcards.model.FlashCard;

/**
 * Webservice Resource for Box model
 */
@Path("/service")
@Api(value = "/service", tags={"Learning Service"})
@Produces(MediaType.APPLICATION_JSON)
public class LearningService {

	@GET
	@Path("/boxes/{box_id}/nextCard")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public FlashCard getNextCard(
			@ApiParam(value="box_id", required=true) @PathParam("box_id") int box_id,
			@ApiParam(value="user_id", required=true) @QueryParam("user_id") int user_id
			){
		return null;
	}
	
	@POST
	@Path("/boxes/{box_id}/scoreCard")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public FlashCard scoreCard(
			@ApiParam(value="box_id", required=true) @PathParam("box_id") int box_id,
			@ApiParam(value="user_id", required=true) @QueryParam("user_id") int user_id
			){
		return null;
	}	
}
