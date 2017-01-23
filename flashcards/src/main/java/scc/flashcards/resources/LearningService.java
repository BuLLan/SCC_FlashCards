package scc.flashcards.resources;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import scc.flashcards.model.flashcards.FlashCard;
import scc.flashcards.model.learning.AbstractLearningProfile;
import scc.flashcards.model.user.User;
import scc.flashcards.persistence.PersistenceHelper;
import scc.flashcards.service.LearningStrategy;
import scc.flashcards.service.NaiveLearningStrategy;

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
			@ApiParam(value="user_id", required=true) @QueryParam("user_id") int user_id ){
		return null;
	}
	
	@POST
	@Path("/boxes/{box_id}/scoreCard")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response scoreCard(
			@ApiParam(value="box_id", required=true) @PathParam("box_id") int box_id,
			@ApiParam(value="user_id", required=true) @QueryParam("user_id") int user_id,
			@ApiParam(value="isCorrect", required=true) @QueryParam("isCorrect") boolean isCorrect ){
		
		return Response.ok().build();
	}	
}
