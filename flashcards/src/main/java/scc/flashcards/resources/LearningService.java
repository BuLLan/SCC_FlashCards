package scc.flashcards.resources;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import scc.flashcards.model.flashcards.Box;
import scc.flashcards.model.flashcards.FlashCard;
import scc.flashcards.model.learning.CardScore;
import scc.flashcards.model.user.User;
import scc.flashcards.persistence.PersistenceHelper;
import scc.flashcards.rest.NextCardsRequest;
import scc.flashcards.rest.ScoreCardRequest;
import scc.flashcards.service.LearningStrategy;
import scc.flashcards.service.LearningStrategyFactory;

/**
 * Webservice Resource for Learning Service
 */
@Api(value = "Learning Service", tags = { "Learning Service" })
@Path("/users/me/boxes/{box_id}/service")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LearningService {
	
	@ApiModel
	private class ScoredFlashCard extends FlashCard{
		
		private int score;
		
		public ScoredFlashCard(FlashCard card, int score) {
			setId(card.getId());
			setFrontpage(card.getFrontpage());
			setBackpage(card.getBackpage());
			setComments(card.getComments());
			setBox(card.getBox());
			this.score = score;
		}

		public int getScore() {
			return score;
		}

		public void setScore(int score) {
			this.score = score;
		}
		
	}

	@POST
	@ApiOperation(value = "Returns the next cards of a learning session", response = ScoredFlashCard.class, responseContainer = "List")
	@Path("/nextCard")
	public Response getNextCards(@ApiParam(value = "box_id", required = true) @PathParam("box_id") int box_id,
			@ApiParam(value = "request", required = true) NextCardsRequest request) {
		try{
			User currentUser = ResourceUtil.getCurrentUser();
			if(currentUser == null){
				return Response.status(Response.Status.FORBIDDEN).build();
			}
			
			int limit = (request.getNumber() > 0) ? request.getNumber() : 20;
			int halfLimit = limit / 2;
			Map<FlashCard, Integer> dueCards = getNextDueCards(currentUser.getId(), box_id, halfLimit);
			
			if(dueCards.size() < halfLimit){
				halfLimit += dueCards.size()-halfLimit;
			}
			
			List<FlashCard> newCards = getNewCards(currentUser.getId(), box_id, halfLimit);
			
			List<ScoredFlashCard> response = new ArrayList<ScoredFlashCard>();
			for (FlashCard flashCard : dueCards.keySet()) {
				response.add(new ScoredFlashCard(flashCard, dueCards.get(flashCard)));
			}
			for (FlashCard flashCard : newCards) {
				response.add(new ScoredFlashCard(flashCard, 0));
			}
			
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
	public Response scoreCard(@ApiParam(value = "box_id", required = true) @PathParam("box_id") int box_id,
			@ApiParam(value = "Flashcard ID", required = true) @PathParam("card_id") int cardId,
			@ApiParam(value = "request", required = true) ScoreCardRequest request) {
		try {
			Session session = PersistenceHelper.getSession();
			User currentUser = ResourceUtil.getCurrentUser();
			if(currentUser == null){
				return Response.status(Response.Status.FORBIDDEN).build();
			}
			if(request.getMode() == null) {
				request.setMode("linear");
			}
			LearningStrategy strategy = LearningStrategyFactory.create(request.getMode());
			CardScore score = getCardScore(currentUser.getId(), cardId);
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
	
	private Map<FlashCard, Integer> getNextDueCards(long userId, long boxId, int limit) {
		Session session = PersistenceHelper.getSession();
		
		Date currentDate = Date.from(Instant.now());
		
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Object[]> critQuery = builder.createQuery(Object[].class);
		
		Root<CardScore> scoreRoot = critQuery.from(CardScore.class);
		Join<CardScore, User> userJoin = scoreRoot.join("user");
		Join<CardScore, FlashCard> cardJoin = scoreRoot.join("flashcard");
		Join<FlashCard, Box> boxJoin = cardJoin.join("box");
		
		critQuery.multiselect(cardJoin, scoreRoot.get("score")).where(builder.and(
					builder.equal(userJoin, userId),
					builder.equal(boxJoin, boxId),
					builder.lessThanOrEqualTo(scoreRoot.<Date>get("due"), currentDate)
				));
		
		List<Object[]> resultList = session.createQuery(critQuery).setMaxResults(limit).getResultList();
		Map<FlashCard, Integer> result = new HashMap<FlashCard, Integer>();
		for(Object[] value : resultList) {
			result.put((FlashCard) value[0], (Integer) value[1]);
		}
		
		return result;
	}
	
	private List<FlashCard> getNewCards(long userId, long boxId, int limit) {
		Session session = PersistenceHelper.getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		
		CriteriaQuery<FlashCard> critQuery = builder.createQuery(FlashCard.class);
		
		Root<Box> boxRoot = critQuery.from(Box.class);
		Root<CardScore> scoreRoot = critQuery.from(CardScore.class);
		SetJoin<Box, FlashCard> boxCardsJoin = boxRoot.joinSet("flashcards");
		Join<CardScore, FlashCard> scoreCardJoin = scoreRoot.join("flashcard");
		Join<CardScore, User> scoreUserJoin = scoreRoot.join("user");
		
		//Get All Cards which have a score
		critQuery.select(boxCardsJoin).where(builder.and(
				builder.equal(boxRoot, boxId),
				builder.equal(boxCardsJoin, scoreCardJoin),
				builder.equal(scoreUserJoin, userId)
			)).distinct(true);
		
		List<FlashCard> scoredCards = session.createQuery(critQuery).getResultList();
		
		//Get all cards which belong to the box
		critQuery.select(boxCardsJoin).where(builder.and(
				builder.equal(boxRoot, boxId)
			)).distinct(true);
		
		List<FlashCard> allCards = session.createQuery(critQuery).getResultList();
		
		//Remove the scored cards from the list
		allCards.removeAll(scoredCards);
		
		//If there are more cards then the limit, only take the first entries
		if(allCards.size() > limit){
			allCards = allCards.subList(0, limit-1);
		}
		
		return allCards;
	}
}
