package scc.flashcards.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Vector;

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
import scc.flashcards.model.FlashCard;
import scc.flashcards.model.User;
import scc.flashcards.persistence.PersistenceHelper;

import java.io.Serializable;
import java.sql.SQLException;

/**
 * Webservice Resource for Box model
 */
@Path("/boxes")
@Api(value="Flashcards REST Service")
@Produces(MediaType.TEXT_PLAIN)
public class BoxResource {
	
	/**
	 * Gets all users
	 * @return a list of users
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value="getAllBoxes",
	notes="Returns a list of all users")
	public List<User> getAllBoxes() {
		org.hibernate.SessionFactory sessionFactory = PersistenceHelper.getInstance().getSessionFactory();
		Session ses = sessionFactory.openSession();
		List<User> allBoxes = ses.createCriteria(Box.class).list();
		ses.close();
		return allBoxes;
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
						   @ApiParam(value="owner", required=true) @FormParam("owner") int boxowner,
						   @ApiParam(value="category_id", required=true) @FormParam("category_id") int category_id,
						   @ApiParam(value="subcategory_id", required=true) @FormParam("subcategory_id") int subcategory_id,
						   @ApiParam(value="tags", required=true) @FormParam("tags") String tags){
		boolean result = true;
		Box box = new Box(title, category_id, subcategory_id, tags, boxowner);
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
		notes="Returns the box object which has the given ID")
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
	
//	/**
//	 * Updates a user
//	 * 
//	 * @param userid
//	 * @return
//	 */
//	@Path("/{boxid}")
//	@POST
//	@Produces(MediaType.TEXT_PLAIN)
//	@ApiOperation(value="updateBox",
//		notes="Returns true if user was updated")
//	public boolean updatBox(
//			@ApiParam(value="The ID of the box", required=true) @PathParam("boxid") int boxid){
//		/**
//		 * TODO: implement stub
//		 */
//		return true;
//	}
	
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
						   		@ApiParam(value="backpage", required=true) @FormParam("backpage") String backpage,
						   		@ApiParam(value="boxid", required=true) @PathParam("boxid") int boxid){
		boolean result = true;
		org.hibernate.SessionFactory sessionFactory = PersistenceHelper.getInstance().getSessionFactory();
		FlashCard fc = new FlashCard(frontpage, backpage,boxid);
		Serializable id = null;
		Session se = sessionFactory.openSession();
		Box box = se.get(Box.class, boxid);
		Set<FlashCard> fc_set = box.getFlashcards();
		fc_set.add(fc);
		box.setFlashcards(fc_set);
		se.close();
		
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
	 * Save answer of last card and fetch next card
	 * 
	 * @param user_id
	 * @param box_id
	 * @param fc_id
	 * @param isCorrect
	 */
	@GET
	@Path("/{box_id}/{fc_id}")
	public FlashCard nextCard(@ApiParam(value="user_id", required=true) @QueryParam("user_id") int user_id,
							  @ApiParam(value="box_id", required=true) @PathParam("box_id") int box_id,
							  @ApiParam(value="fc_id", required=true) @PathParam("fc_id") int fc_id,
							  @ApiParam(value="iscorrect", required=true) @QueryParam("iscorrect") boolean iscorrect){
		return null;
		
	}
	
}
