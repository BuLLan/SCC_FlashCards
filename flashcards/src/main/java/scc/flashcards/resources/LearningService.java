package scc.flashcards.resources;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.owlike.genson.Genson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import scc.flashcards.model.flashcards.FlashCard;

/**
 * Webservice Resource for Box model
 */
@Path("/service")
@Api(value = "/service", tags={"Learning Service"})
@Produces(MediaType.APPLICATION_JSON)
public class LearningService {

	@GET
	@ApiOperation(value="Returns the next cards of a learning session", response=FlashCard.class, responseContainer="Map")
	@Path("/boxes/{box_id}/nextCard")
	public Response getNextCards(
			@ApiParam(value="box_id", required=true) @PathParam("box_id") int box_id,
			@ApiParam(value="user_id", required=true) @QueryParam("user_id") int user_id ){
		Map<Integer, FlashCard> response = null;
		
		return Response.ok(new Genson().serialize(response)).build();
	}
	
	@POST
	@Path("/boxes/{box_id}/scoreCard")
	public Response scoreCard(
			@ApiParam(value="box_id", required=true) @PathParam("box_id") int box_id,
			@ApiParam(value="user_id", required=true) @QueryParam("user_id") int user_id,
			@ApiParam(value="isCorrect", required=true) @QueryParam("isCorrect") boolean isCorrect ){
		
		return Response.ok().build();
	}	
}
