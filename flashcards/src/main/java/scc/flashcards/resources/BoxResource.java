package scc.flashcards.resources;

import java.io.Serializable;
import java.util.List;

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
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.Session;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import scc.flashcards.model.Box;
import scc.flashcards.model.Category;
import scc.flashcards.model.Comment;
import scc.flashcards.model.FlashCard;
import scc.flashcards.model.User;
import scc.flashcards.persistence.PersistenceHelper;

/**
 * Webservice Resource for Box model
 */
@Path("/boxes")
@Api(value = "/boxes", tags = { "Box and FlashCard Resources" })
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BoxResource {

	/**
	 * Gets all users
	 * 
	 * @return a list of users
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@ApiOperation(value = "getAllPublicBoxes", notes = "Returns a list of all public buxes")
	public List<Box> getAllBoxes() {
		org.hibernate.SessionFactory sessionFactory = PersistenceHelper.getInstance().getSessionFactory();
		Session ses = sessionFactory.openSession();

		List<Box> list = null;
		try {
			CriteriaBuilder builder = ses.getCriteriaBuilder();
			CriteriaQuery<Box> query = builder.createQuery(Box.class);
			Root<Box> root = query.from(Box.class);
			query.select(root).where(builder.equal(root.get("public"), true));
			list = ses.createQuery(query).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ses.close();

		return list;
	}

	/**
	 * Adds a new box
	 * 
	 * @return
	 */
	@POST
	@ApiOperation(value = "addBox", notes = "Create a new Box")
	@Consumes("application/x-www-form-urlencoded")
	public Response addBox(@ApiParam(value = "title", required = true) @FormParam("title") String title,
			@ApiParam(value = "owner", required = true) @FormParam("owner") int owner_id,
			@ApiParam(value = "category_id", required = true) @FormParam("category_id") int category_id,
			@ApiParam(value = "subcategory_id", required = true) @FormParam("subcategory_id") int subcategory_id,
			@ApiParam(value = "tags", required = true) @FormParam("tags") String tags) {
		Session session = PersistenceHelper.getInstance().getSessionFactory().openSession();
		Box box = new Box(title, session.get(Category.class, category_id), session.get(Category.class, subcategory_id),
				tags, owner_id);
		Serializable id = null;

		try {
			box.persist();
		} catch (Exception e) {
			throw new ServerErrorException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
		}

		return Response.ok(box).status(Response.Status.OK).build();
	}

	/**
	 * Gets a box by id
	 * 
	 * @param boxid
	 * @return
	 */
	@Path("/{boxid}")
	@GET
	@ApiOperation(value = "getBoxByID", notes = "Returns the box object which has the given ID", response = Box.class)
	public Box getBoxById(@ApiParam(value = "The ID of the box", required = true) @PathParam("boxid") int boxid) {
		return PersistenceHelper.getById(boxid, Box.class);
	}

	/**
	 * Deletes a user by id
	 * 
	 * @param userid
	 * @return
	 */
	@Path("/{boxid}")
	@DELETE
	@ApiOperation(value = "deleteBox", notes = "Returns true if box was deleted")
	public Response deleteBox(@ApiParam(value = "The ID of the box", required = true) @PathParam("boxid") int boxid) {
		Box box = PersistenceHelper.getById(boxid, Box.class);

		try {
			box.delete();
		} catch (Exception e) {
			throw new ServerErrorException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
		}

		return Response.ok().status(Response.Status.OK).build();
	}

	/**
	 * Adds a new flashcard
	 * 
	 * @return
	 */
	@POST
	@Path("/{boxid}")
	@ApiOperation(value = "addFlashCard", notes = "create a new FlashCard")
	public Response addFlashCard(@ApiParam(value = "flashcard", required = true) FlashCard flashcard,
			@ApiParam(value = "boxid", required = true) @PathParam("boxid") int boxid) {
		FlashCard fc = new FlashCard();
		fc.setFrontpage(flashcard.getFrontpage());
		fc.setBackpage(flashcard.getBackpage());
		
		Box box = PersistenceHelper.getById(boxid, Box.class);
		box.getFlashcards().add(fc);
		fc.setBox(box);
		fc.persist();
		
		try {
			box.persist();
		} catch (Exception e) {
			throw new ServerErrorException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
		}

		return Response.ok(fc).status(Response.Status.OK).build();
	}

	@GET
	@Path("/{boxid}/{fc_id}")
	@ApiOperation(value = "getFlashCard", notes = "Fetches a FlashCard")
	public FlashCard getFlashCard(@ApiParam(value = "box_id", required = true) @PathParam("boxid") int box_id,
			@ApiParam(value = "fc_id", required = true) @PathParam("fc_id") int fc_id) {
		return PersistenceHelper.getById(fc_id, FlashCard.class);
	}

	@DELETE
	@Path("/{boxid}/{fc_id}")
	@ApiOperation(value = "removeFlashCard", notes = "Removes a FlashCard")
	public Response deleteFlashCard(@ApiParam(value = "box_id", required = true) @PathParam("boxid") int box_id,
			@ApiParam(value = "fc_id", required = true) @PathParam("fc_id") int fc_id) {
		FlashCard fc = PersistenceHelper.getById(fc_id, FlashCard.class);
		Box box = PersistenceHelper.getById(box_id, Box.class);
		
		box.getFlashcards().remove(fc);
		
		try {
			box.persist();
		} catch (Exception e) {
			throw new ServerErrorException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
		}

		return Response.ok().status(Response.Status.OK).build();
	}

	@GET
	@Path("/{boxid}/{fc_id}/comments")
	@ApiOperation(value = "getFlashcardComments", notes = "Get comments of a FlashCard")
	public Response getComments(@ApiParam(value = "box_id", required = true) @PathParam("boxid") int box_id,
			@ApiParam(value = "fc_id", required = true) @PathParam("fc_id") int fc_id) {
		List<Comment> comments = PersistenceHelper.getById(fc_id, FlashCard.class).getComments();
		return Response.ok(comments).status(Response.Status.OK).build();
	}

	@POST
	@Path("/{boxid}/{fc_id}/comments")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "addFlashcardComment", notes = "Add comment to flashcard")
	public Response addComment(@ApiParam(value = "box_id", required = true) @PathParam("boxid") int box_id,
			@ApiParam(value = "fc_id", required = true) @PathParam("fc_id") int fc_id,
			@ApiParam(value = "author_id", required = true) @FormParam("author_id") int author_id,
			@ApiParam(value = "subject", required = true) @FormParam("subject") String subject,
			@ApiParam(value = "text", required = true) @FormParam("text") String text) {
		FlashCard fc = PersistenceHelper.getById(fc_id, FlashCard.class);
		Comment comment = new Comment();
		User user = PersistenceHelper.getById(author_id, User.class);
		comment.setAuthor(user);
		comment.setTitle(subject);
		comment.setDescription(text);	
		comment.setFlashcard(fc);
		comment.persist();
		fc.getComments().add(comment);
		
		try {
			fc.persist();
		} catch (Exception e) {
			throw new ServerErrorException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
		}

		return Response.ok().status(Response.Status.OK).build();
	}

	@DELETE
	@Path("/{boxid}/{fc_id}/comments")
	@ApiOperation(value = "removeFlashcardComment", notes = "Remove comment from flashcard")
	public Response removeComment(@ApiParam(value = "box_id", required = true) @PathParam("box_id") int box_id,
			@ApiParam(value = "fc_id", required = true) @PathParam("fc_id") int fc_id,
			@ApiParam(value = "comment_id", required = true) @PathParam("comment_id") int comment_id) {
		FlashCard fc = PersistenceHelper.getById(fc_id, FlashCard.class);
		Comment comment = PersistenceHelper.getById(comment_id, Comment.class);
		List<Comment> comments = fc.getComments();
		
		comments.remove(comment);
		fc.setComments(comments);
		
		try {
			fc.persist();
		} catch (Exception e) {
			throw new ServerErrorException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
		}

		return Response.ok().status(Response.Status.OK).build();
	}
}