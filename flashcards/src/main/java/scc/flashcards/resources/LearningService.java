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

import org.hibernate.Session;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import scc.flashcards.model.AbstractLearningProfile;
import scc.flashcards.model.Box;
import scc.flashcards.model.FlashCard;
import scc.flashcards.model.LearningProfile;
import scc.flashcards.model.User;
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
			@ApiParam(value="user_id", required=true) @QueryParam("user_id") int user_id
			){
		return null;
	}
	
	@POST
	@Path("/boxes/{box_id}/scoreCard")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public FlashCard scoreCard(
			@ApiParam(value="box_id", required=true) @PathParam("box_id") int box_id,
			@ApiParam(value="user_id", required=true) @QueryParam("user_id") int user_id,
			@ApiParam(value="isCorrect", required=true) @QueryParam("isCorrect") boolean isCorrect
			){
		org.hibernate.SessionFactory sessionFactory = PersistenceHelper.getInstance().getSessionFactory();
		Session ses = sessionFactory.openSession();
		ses.beginTransaction();
		
		List<AbstractLearningProfile> list = null;
		try{
			CriteriaBuilder builder = ses.getCriteriaBuilder();
			CriteriaQuery<AbstractLearningProfile> query = builder.createQuery( AbstractLearningProfile.class );
			Root<AbstractLearningProfile> root = query.from( AbstractLearningProfile.class );
			
			query.select(root) //That's why I hate Java :/
				.where(builder.and(
						builder.equal(root.get("user_id"), user_id),
						builder.equal(root.get("box_id"), box_id)
				)
			);
			list = ses.createQuery(query).getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		AbstractLearningProfile profile = null;
		
		if(list == null || list.isEmpty()){
			return null;
		} else {
			profile = list.get(0);
		}
		
		if(profile == null) {
			LearningStrategy strategy = new NaiveLearningStrategy();
			profile = strategy.createLearningProfile(new User());
			profile.persist();
		}
		
		ses.getTransaction().commit();
		
		ses.close();
		
		return null;
		
	}	
}
