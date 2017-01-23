package scc.flashcards.resources;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.owlike.genson.Genson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import scc.flashcards.model.flashcards.FlashCard;
import scc.flashcards.model.learning.CardScore;
import scc.flashcards.model.user.User;
import scc.flashcards.persistence.PersistenceHelper;
import scc.flashcards.rest.ScoreCardRequest;
import scc.flashcards.service.LearningStrategy;
import scc.flashcards.service.LearningStrategyFactory;

/**
 * Webservice Resource for Learning Service
 */
@Api(value = "Learning Service", tags = { "Learning Service" })
@Path("/users/{user_id}/boxes/{box_id}/service")
@Produces(MediaType.APPLICATION_JSON)
public class LearningService {

	@GET
	@ApiOperation(value = "Returns the next cards of a learning session", response = FlashCard.class, responseContainer = "Map")
	@Path("/nextCard")
	public Response getNextCards(@ApiParam(value = "box_id", required = true) @PathParam("box_id") int box_id,
			@ApiParam(value = "user_id", required = true) @PathParam("user_id") int user_id) {
		System.out.println("TeST!");
		try{			
			Map<Integer, FlashCard> response = null;
			return Response.ok(new Genson().serialize(response)).build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			return Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
		} catch (ClientErrorException e) {
			return Response.status(e.getResponse().getStatusInfo()).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (Exception e) {
			// Something else went wrong
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Genson().serialize(e.getMessage())).build();
		} finally {
			PersistenceHelper.closeSession();
		}

	}

	@POST
	@Path("/scorecard/{card_id}")
	public Response scoreCard(@ApiParam(value = "Flashcard ID", required = true) @PathParam("card_id") int cardId,
			@ApiParam(value = "user_id", required = true) @QueryParam("user_id") int userId,
			@ApiParam(value = "request", required = true) ScoreCardRequest request) {
		try {
			Session session = PersistenceHelper.getSession();
			LearningStrategy strategy = LearningStrategyFactory.create(request.getMode());
			CardScore score = getCardScore(userId, cardId);
			CardScore newScore = strategy.scoreCard(score, request.getIsCorrect());
			newScore.persist();
			return Response.ok().build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			return Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
		} catch (ClientErrorException e) {
			return Response.status(e.getResponse().getStatusInfo()).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (Exception e) {
			// Something else went wrong
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Genson().serialize(e.getMessage())).build();
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	private CardScore getCardScore(long userId, long cardId) {
		Session session = PersistenceHelper.getSession();
		// Fetch CardScore
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<CardScore> critQuery = builder.createQuery(CardScore.class);

		Root<CardScore> scoreRoot = critQuery.from(CardScore.class);

		critQuery.select(scoreRoot).where(builder.and(builder.equal(scoreRoot.get("user"), userId),
				builder.equal(scoreRoot.get("flashcard"), cardId)));
		try {
			return session.createQuery(critQuery).getSingleResult();
		} catch (NoResultException e) {
			CardScore newScore = new CardScore();
			newScore.setUser(PersistenceHelper.getById(userId, User.class));
			newScore.setFlashcard(PersistenceHelper.getById(cardId, FlashCard.class));
			newScore.setCreated(Date.from(Instant.now()));
			return newScore;
		}
	}
}
