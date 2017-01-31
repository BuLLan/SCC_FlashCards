package scc.flashcards.resources;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.soap.AddressingFeature.Responses;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.owlike.genson.Genson;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import scc.flashcards.model.flashcards.Box;
import scc.flashcards.model.flashcards.Category;
import scc.flashcards.model.flashcards.Comment;
import scc.flashcards.model.flashcards.FlashCard;
import scc.flashcards.model.user.User;
import scc.flashcards.persistence.PersistenceHelper;
import scc.flashcards.rest.GetAllBoxesRequest;
import scc.flashcards.rest.NewCategoryRequest;
import scc.flashcards.rest.NewCommentRequest;
import scc.flashcards.rest.NewFlashcardRequest;

/**
 * Webservice Resource for Box model
 */
@Path("/boxes")
@Api(value = "/boxes", tags = { "Box and FlashCard Resources" })
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BoxResource {

	/**
	 * Gets all boxes
	 * 
	 * @return a list of boxes
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@ApiOperation(value = "List all public boxes", 
		response = Box.class, responseContainer = "List")
	public Response getAllBoxes(
			@ApiParam(value = "Request Object", required = false) GetAllBoxesRequest request
			) {
		Session session = PersistenceHelper.getSession();
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Box> query = builder.createQuery(Box.class);
			Root<Box> root = query.from(Box.class);
			if(request != null && request.getCategoryId() != null){
				query.select(root).where(builder.and(
						builder.equal(root.get("isPublic"), true),
						builder.equal(root.get("category"), request.getCategoryId())
						));
			} else {
				query.select(root).where(builder.equal(root.get("isPublic"), true));
			}
			List<Box> resultList = session.createQuery(query).getResultList();
			Response response = ResourceUtil.getResponse(Response.Status.OK, resultList);
			return response;
		} catch (HibernateException e) {
			// Something went wrong with the Database
			return ResourceUtil.getResponse(Response.Status.SERVICE_UNAVAILABLE, e.getMessage());
		} catch (ClientErrorException e) {
			return ResourceUtil.getResponse(e.getResponse().getStatusInfo(), e.getMessage());
		} catch (Exception e) {
			// Something else went wrong
			return ResourceUtil.getResponse(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	/**
	 * Gets a box by id
	 * 
	 * @param boxid
	 * @return
	 */
	@Path("/{box_id}")
	@GET
	@ApiOperation(value = "Get Box by ID", notes = "Returns the box object which has the given ID", response = Box.class)
	public Response getBoxById(@ApiParam(value = "The ID of the box", required = true) @PathParam("box_id") int boxid) {
		try {
			Box box = PersistenceHelper.getById(boxid, Box.class);
			Response response = Response.ok(new Genson().serialize(box)).build();
			return response;
		} catch (HibernateException e) {
			// Something went wrong with the Database
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (ClientErrorException e) {
			return Response.status(e.getResponse().getStatusInfo()).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (Exception e) {
			// Something else went wrong
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e.getMessage()))
					.build();
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	/**
	 * Deletes a user by id
	 * 
	 * @param userid
	 * @return
	 */
	@Path("/{box_id}")
	@DELETE
	@ApiOperation(value = "deleteBox", notes = "Returns true if box was deleted")
	public Response deleteBox(@ApiParam(value = "The ID of the box", required = true) @PathParam("box_id") int boxid) {
		PersistenceHelper.openSession();		
		try {
			Box box = PersistenceHelper.getById(boxid, Box.class);
			if(!ResourceUtil.isOwner(box.getOwner().getId())){
				return Response.status(Response.Status.FORBIDDEN).build();
			}
			box.delete();
			return Response.ok().build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (ClientErrorException e) {
			return Response.status(e.getResponse().getStatusInfo()).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (Exception e) {
			// Something else went wrong
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e.getMessage()))
					.build();
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	/**
	 * Gets all Categories
	 * 
	 * @return a list of boxes
	 */
	@Path("/categories")
	@GET
	@ApiOperation(value = "getAllCategories", notes = "Returns a list of all categories", 
			response = Category.class, responseContainer = "List")
	public Response getAllCategories() {
		Session session = PersistenceHelper.getSession();
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Category> query = builder.createQuery(Category.class);
			query.select(query.from(Category.class));
			List<Category> resultList = session.createQuery(query).getResultList();
			return Response.ok(new Genson().serialize(resultList)).build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (ClientErrorException e) {
			return Response.status(e.getResponse().getStatusInfo()).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (Exception e) {
			// Something else went wrong
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e.getMessage()))
					.build();
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	/**
	 * Adds a category box
	 * 
	 * @return
	 */
	@Path("/categories")
	@POST
	@ApiOperation(value = "addCategory", notes = "Create a new Category", responseReference = "Category")
	public Response addCategory(@ApiParam(value = "Request", required = true) NewCategoryRequest request) {
		try {
			request.validateRequest();
			PersistenceHelper.openSession();
			// Build new Box Object
			Category category = new Category();
			category.setTitle(request.getTitle());
			if (request.hasParentCategory()) {
				Category parentCat = PersistenceHelper.getById(request.getParentCategoryId(), Category.class);
				category.setParentCategory(parentCat);
			}
			category.persist();
			return Response.ok(category).build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (ClientErrorException e) {
			return Response.status(e.getResponse().getStatusInfo()).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (Exception e) {
			// Something else went wrong
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e.getMessage()))
					.build();
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	/**
	 * Deletes a user by id
	 * 
	 * @param userid
	 * @return
	 */
	@Path("/categories/{categoryid}")
	@DELETE
	@ApiOperation(value = "deleteCategory", notes = "Returns true if Category was deleted")
	public Response deleteCategory(
			@ApiParam(value = "The ID of the Category", required = true) @PathParam("categoryid") int categoryid) {
		PersistenceHelper.openSession();
		try {
			PersistenceHelper.getById(categoryid, Category.class).delete();
			return Response.ok().build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (ClientErrorException e) {
			return Response.status(e.getResponse().getStatusInfo()).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (Exception e) {
			// Something else went wrong
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e.getMessage()))
					.build();
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	/**
	 * Adds a new flashcard
	 * 
	 * @return
	 */
	@POST
	@Path("/{box_id}")
	@ApiOperation(value = "addFlashCard", notes = "create a new FlashCard")
	public Response addFlashCard(@ApiParam(value = "request", required = true) NewFlashcardRequest request,
			@ApiParam(value = "boxid", required = true) @PathParam("box_id") int boxid) {
		try {
			//Get current user
			User currentUser = ResourceUtil.getCurrentUser();
			Box box = PersistenceHelper.getById(boxid, Box.class);
			if(currentUser == null || currentUser.getId() != box.getOwner().getId()){
				return Response.status(Response.Status.FORBIDDEN).build();
			}
			
			request.validateRequest();
			Session session = PersistenceHelper.getSession();
			FlashCard fc = new FlashCard();
			fc.setFrontpage(request.getFrontpage());
			fc.setBackpage(request.getBackpage());
			box.getFlashcards().add(fc);
			fc.setBox(box);

			session.beginTransaction();
			session.saveOrUpdate(fc);
			session.saveOrUpdate(box);

			return Response.ok(new Genson().serialize(fc)).build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (ClientErrorException e) {
			return Response.status(e.getResponse().getStatusInfo()).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (Exception e) {
			// Something else went wrong
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e.getMessage()))
					.build();
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	@GET
	@Path("/{box_id}/{fc_id}")
	@ApiOperation(value = "getFlashCard", notes = "Fetches a FlashCard", response = FlashCard.class)
	public Response getFlashCard(@ApiParam(value = "box_id", required = true) @PathParam("box_id") int box_id,
			@ApiParam(value = "fc_id", required = true) @PathParam("fc_id") int fc_id) {
		PersistenceHelper.openSession();
		try {
			FlashCard fc = PersistenceHelper.getById(fc_id, FlashCard.class);
			return Response.ok(new Genson().serialize(fc)).build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (ClientErrorException e) {
			return Response.status(e.getResponse().getStatusInfo()).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (Exception e) {
			// Something else went wrong
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e.getMessage()))
					.build();
		} finally {
			PersistenceHelper.closeSession();
		}
	}
	
	@GET
	@Path("/{box_id}/flashcards")
	@ApiOperation(value = "Returns all flashcards", notes = "Fetches a FlashCard", response = FlashCard.class, responseContainer="Set")
	public Response getAllCards(@ApiParam(value = "box_id", required = true) @PathParam("box_id") int box_id)
	{
		PersistenceHelper.openSession();
		try {
			Set<FlashCard> cards = PersistenceHelper.getById(box_id, Box.class).getFlashcards();
			Response response = Response.ok(new Genson().serialize(cards)).build();
			return response;
		} catch (HibernateException e) {
			// Something went wrong with the Database
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (ClientErrorException e) {
			return Response.status(e.getResponse().getStatusInfo()).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (Exception e) {
			// Something else went wrong
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e.getMessage()))
					.build();
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	@DELETE
	@Path("/{box_id}/{fc_id}")
	@ApiOperation(value = "removeFlashCard", notes = "Removes a FlashCard")
	public Response deleteFlashCard(@ApiParam(value = "box_id", required = true) @PathParam("box_id") int box_id,
			@ApiParam(value = "fc_id", required = true) @PathParam("fc_id") int fc_id) {
		Transaction tx = PersistenceHelper.getSession().beginTransaction();
		try {
			PersistenceHelper.openSession();
			FlashCard fc = PersistenceHelper.getById(fc_id, FlashCard.class);
			Box box = PersistenceHelper.getById(box_id, Box.class);
			User currentUser = ResourceUtil.getCurrentUser();
			if(currentUser == null || currentUser.getId() != box.getOwner().getId()){
				return Response.status(Response.Status.FORBIDDEN).build();
			}
			
			box.getFlashcards().remove(fc);
			PersistenceHelper.getSession().saveOrUpdate(box);
		} catch (HibernateException e) {
			// Something went wrong with the Database
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (ClientErrorException e) {
			return Response.status(e.getResponse().getStatusInfo()).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (Exception e) {
			// Something else went wrong
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e.getMessage()))
					.build();
		} finally {
			PersistenceHelper.closeSession();
		}

		return Response.ok("SUCCESS").status(Response.Status.OK).build();
	}

	@GET
	@Path("/{box_id}/{fc_id}/comments")
	@ApiOperation(value = "getFlashcardComments", notes = "Get comments of a FlashCard", response = Comment.class, responseContainer = "List")
	public Response getComments(@ApiParam(value = "box_id", required = true) @PathParam("box_id") int box_id,
			@ApiParam(value = "fc_id", required = true) @PathParam("fc_id") int fc_id) {
		try {
			PersistenceHelper.openSession();
			List<Comment> comments = PersistenceHelper.getById(fc_id, FlashCard.class).getComments();
			return Response.ok(new Genson().serialize(comments)).build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (ClientErrorException e) {
			return Response.status(e.getResponse().getStatusInfo()).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (Exception e) {
			// Something else went wrong
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e.getMessage()))
					.build();
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	@POST
	@Path("/{box_id}/{fc_id}/comments")
	@ApiOperation(value = "addFlashcardComment", notes = "Add comment to flashcard")
	public Response addComment(@ApiParam(value = "box_id", required = true) @PathParam("box_id") int box_id,
			@ApiParam(value = "fc_id", required = true) @PathParam("fc_id") int fc_id,
			@ApiParam(value = "request", required = true) NewCommentRequest request) {
		try {
			request.validateRequest();
			PersistenceHelper.openSession();
			Session session = PersistenceHelper.getSession();
			FlashCard fc = PersistenceHelper.getById(fc_id, FlashCard.class);
			Comment comment = new Comment();
			User user = PersistenceHelper.getById(request.getAuthorId(), User.class);

			comment.setAuthor(user);
			comment.setTitle(request.getSubject());
			comment.setDescription(request.getContent());
			comment.setFlashcard(fc);
			comment.setCreated_at(Date.from(Instant.now()));
			fc.getComments().add(comment);

			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(comment);
			session.saveOrUpdate(fc);
			return Response.ok().build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (ClientErrorException e) {
			return Response.status(e.getResponse().getStatusInfo()).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (Exception e) {
			// Something else went wrong
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e.getMessage()))
					.build();
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	@DELETE
	@Path("/{box_id}/{fc_id}/comments/{comment_id}")
	@ApiOperation(value = "removeFlashcardComment", notes = "Remove comment from flashcard")
	public Response removeComment(@ApiParam(value = "box_id", required = true) @PathParam("box_id") int box_id,
			@ApiParam(value = "fc_id", required = true) @PathParam("fc_id") int fc_id,
			@ApiParam(value = "comment_id", required = true) @PathParam("comment_id") int comment_id) {
		try {
			Session session = PersistenceHelper.getSession();
			FlashCard fc = PersistenceHelper.getById(fc_id, FlashCard.class);
			Comment comment = PersistenceHelper.getById(comment_id, Comment.class);
			if(!ResourceUtil.isOwner(comment.getAuthor().getId())){
				return Response.status(Response.Status.FORBIDDEN).build();
			}
			List<Comment> comments = fc.getComments();
			comments.remove(comment);
			fc.setComments(comments);

			Transaction tx = session.beginTransaction();
			session.delete(comment);
			session.saveOrUpdate(fc);

			return Response.ok().status(Response.Status.OK).build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (ClientErrorException e) {
			return Response.status(e.getResponse().getStatusInfo()).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (Exception e) {
			// Something else went wrong
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Genson().serialize(e.getMessage()))
					.build();
		} finally {
			PersistenceHelper.closeSession();
		}
	}
}