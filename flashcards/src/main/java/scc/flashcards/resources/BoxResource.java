package scc.flashcards.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import scc.flashcards.model.Box;
import scc.flashcards.model.ContentType;
import scc.flashcards.model.FlashCard;
import scc.flashcards.model.Page;
import scc.flashcards.model.User;
import scc.flashcards.model.Category;
import scc.flashcards.model.Comment;
import scc.flashcards.persistence.PersistenceHelper;

import java.io.Serializable;
import java.sql.SQLException;

/**
 * Webservice Resource for Box model
 */
@Path("/boxes")
@Api(value = "/boxes", tags={"Box and FlashCard Resources"})
@Produces(MediaType.APPLICATION_JSON)
public class BoxResource {
	
	/**
	 * Gets all users
	 * @return a list of users
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@ApiOperation(value="getAllPublicBoxes",
	notes="Returns a list of all public buxes")
	public List<Box> getAllBoxes() {
		org.hibernate.SessionFactory sessionFactory = PersistenceHelper.getInstance().getSessionFactory();
		Session ses = sessionFactory.openSession();
		
		List<Box> list = null;
		try{
			CriteriaBuilder builder = ses.getCriteriaBuilder();
			CriteriaQuery<Box> query = builder.createQuery( Box.class );
			Root<Box> root = query.from( Box.class );
			query.select(root)
				.where(builder.equal(root.get("public"), true));
			list = ses.createQuery(query).getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		ses.close();
		
		return list;
	}
	
	/**
	 * Adds a new box
	 * @return
	 */
	@POST
	@ApiOperation(value="addBox",
	notes="create a new Box")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes("application/x-www-form-urlencoded")
	public boolean addBox(@ApiParam(value="title", required=true) @FormParam("title") String title,
						   @ApiParam(value="owner", required=true) @FormParam("owner") int owner_id,
						   @ApiParam(value="category_id", required=true) @FormParam("category_id") int category_id,
						   @ApiParam(value="subcategory_id", required=true) @FormParam("subcategory_id") int subcategory_id,
						   @ApiParam(value="tags", required=true) @FormParam("tags") String tags){
		boolean result = true;
		Session session = PersistenceHelper.getInstance().getSessionFactory().openSession();
		Box box = new Box(title, session.get(Category.class, category_id), 
							session.get(Category.class, subcategory_id), tags, owner_id);
		Serializable id = null;
		
		
		try {
			box.persist();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		
		return result;
	}
	
	
	
	/**
	 * Gets a box by id
	 * 
	 * @param boxid
	 * @return
	 */
	@Path("/{boxid}")
	@GET
	@Produces({"application/json","application/xml"})
	@ApiOperation(value="getBoxByID",
		notes="Returns the box object which has the given ID",
		response = Box.class)
	public Box getBoxById(
			@ApiParam(value="The ID of the box", required=true) @PathParam("boxid") int boxid){
		Session session = PersistenceHelper.getInstance().getSessionFactory().openSession();
		Box box;
		try {
			box = session.get(Box.class, boxid);
		} catch (Exception e) {
			e.printStackTrace();
			session.close();
			return null;
		}
		session.close();
		return box;
	}
	
	/**
	 * Deletes a user by id
	 * 
	 * @param userid
	 * @return
	 */
	@Path("/{boxid}")
	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value="deleteBox",
		notes="Returns true if box was deleted")
	public boolean deleteBox(
			@ApiParam(value="The ID of the box", required=true) @PathParam("boxid") int boxid){
		Session session = PersistenceHelper.getInstance().getSessionFactory().openSession();
		try {
			session.delete(session.get(Box.class, boxid));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		session.close();
		
		return true;
	}
	
	/**
	 * Adds a new flashcard
	 * @return
	 */
	@POST
	@Path("/{boxid}")
	@ApiOperation(value="addFlashCard",
	notes="create a new FlashCard")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes("application/x-www-form-urlencoded")
	public boolean addFlashCard(@ApiParam(value="frontpage", required=true) @FormParam("frontpage") String frontpage,
								@ApiParam(value="frontpage_type", required=true) @FormParam("frontpage_type") ContentType frontpage_type,
						   		@ApiParam(value="backpage", required=true) @FormParam("backpage") String backpage,
						   		@ApiParam(value="backpage_type", required=true) @FormParam("backpage_type") ContentType backpage_type,
						   		@ApiParam(value="boxid", required=true) @PathParam("boxid") int boxid){
		
		org.hibernate.SessionFactory sessionFactory = PersistenceHelper.getInstance().getSessionFactory();
		Session session = sessionFactory.openSession();
		
		Box box = session.get(Box.class, boxid);
		Set<FlashCard> fc_set = box.getFlashcards();
		fc_set.add(new FlashCard(
				new Page(frontpage_type, frontpage),
				new Page(backpage_type, backpage), boxid));
		box.setFlashcards(fc_set);
		
		session.close();
		
		try {
			box.persist();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@GET
	@Path("/{boxid}/{fc_id}")
	@ApiOperation(value="getFlashCard",
	notes="Fetches a FlashCard")
	@Produces(MediaType.TEXT_PLAIN)
	public FlashCard getFlashCard(
			@ApiParam(value="box_id", required=true) @PathParam("box_id") int box_id,
			@ApiParam(value="fc_id", required=true) @PathParam("fc_id") int fc_id
			){
		return null;
	}
	
	@DELETE
	@Path("/{boxid}/{fc_id}")
	@ApiOperation(value="removeFlashCard",
	notes="Removes a FlashCard")
	@Produces(MediaType.TEXT_PLAIN)
	public FlashCard deleteFlashCard(
			@ApiParam(value="box_id", required=true) @PathParam("box_id") int box_id,
			@ApiParam(value="fc_id", required=true) @PathParam("fc_id") int fc_id
			){
		return null;
	}
	
	@GET
	@Path("/{boxid}/{fc_id}/comments")
	@ApiOperation(value="getFlashcardComments",
	notes="Get comments of a FlashCard")
	@Produces(MediaType.TEXT_PLAIN)
	public List<Comment> getComments(
			@ApiParam(value="box_id", required=true) @PathParam("box_id") int box_id,
			@ApiParam(value="fc_id", required=true) @PathParam("fc_id") int fc_id
			){
		return null;
	}
	
	@POST
	@Path("/{boxid}/{fc_id}/comments")
	@ApiOperation(value="addFlashcardComment",
	notes="Add comment to flashcard")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean addComment(
			@ApiParam(value="box_id", required=true) @PathParam("box_id") int box_id,
			@ApiParam(value="fc_id", required=true) @PathParam("fc_id") int fc_id,
			@ApiParam(value="comment", required=true) @FormParam("comment") Comment comment
			){
		return false;
	}
	
	@DELETE
	@Path("/{boxid}/{fc_id}/comments")
	@ApiOperation(value="removeFlashcardComment",
	notes="Remove comment from flashcard")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean removeComment(
			@ApiParam(value="box_id", required=true) @PathParam("box_id") int box_id,
			@ApiParam(value="fc_id", required=true) @PathParam("fc_id") int fc_id,
			@ApiParam(value="comment_id", required=true) @PathParam("comment_id") int comment_id
			){
		return false;
	}
}