package scc.flashcards.resources;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
import scc.flashcards.rest.NewBoxRequest;
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
	@ApiOperation(value = "getAllPublicBoxes", notes = "Returns a list of all public boxes", response = Box.class, responseContainer = "List")
	public Response getAllBoxes() {
		Session session = PersistenceHelper.getSession();
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Box> query = builder.createQuery(Box.class);
			Root<Box> root = query.from(Box.class);
			query.select(root).where(builder.equal(root.get("isPublic"), true));
			List<Box> resultList = session.createQuery(query).getResultList();
			return Response.ok(new Genson().serialize(resultList)).build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new ServiceUnavailableException(response);
		} catch (ClientErrorException e) {
			return Response.status(e.getResponse().getStatusInfo()).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (Exception e) {
			// Something else went wrong
			Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new InternalServerErrorException(response);
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	/**
	 * Adds a new box
	 * 
	 * @return
	 */
	@POST
	@ApiOperation(value = "addBox", notes = "Create a new Box")
	public Response addBox(@ApiParam(value = "Request", required = true) NewBoxRequest request) {
		try {
			request.validateRequest();
			PersistenceHelper.openSession();
			// Build new Box Object
			Box box = new Box();
			box.setTitle(request.getTitle());
			box.setCategory(PersistenceHelper.getById(request.getCategoryId(), Category.class));
			box.setOwner(PersistenceHelper.getById(request.getOwnerId(), User.class));
			box.setTags(request.getTags());
			box.setPublic(request.isPublic());
			// Try to Save Box Object
			box.persist();
			return Response.ok(box).build();
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
	 * Gets a box by id
	 * 
	 * @param boxid
	 * @return
	 */
	@Path("/{boxid}")
	@GET
	@ApiOperation(value = "getBoxByID", notes = "Returns the box object which has the given ID", response = Box.class)
	public Response getBoxById(@ApiParam(value = "The ID of the box", required = true) @PathParam("boxid") int boxid) {
		PersistenceHelper.openSession();
		try {
			Box box = PersistenceHelper.getById(boxid, Box.class);
			return Response.ok(new Genson().serialize(box)).build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new ServiceUnavailableException(response);

		} catch (ClientErrorException e) {
			return Response.status(e.getResponse().getStatusInfo()).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (Exception e) {
			// Something else went wrong
			Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new InternalServerErrorException(response);
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
	@Path("/{boxid}")
	@DELETE
	@ApiOperation(value = "deleteBox", notes = "Returns true if box was deleted")
	public Response deleteBox(@ApiParam(value = "The ID of the box", required = true) @PathParam("boxid") int boxid) {
		PersistenceHelper.openSession();
		try {
			PersistenceHelper.getById(boxid, Box.class).delete();

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
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@ApiOperation(value = "getAllCategories", notes = "Returns a list of all categories", response = Category.class, responseContainer = "List")
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
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new ServiceUnavailableException(response);
		} catch (ClientErrorException e) {
			return Response.status(e.getResponse().getStatusInfo()).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (Exception e) {
			// Something else went wrong
			Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new InternalServerErrorException(response);
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
	@Path("/{boxid}")
	@ApiOperation(value = "addFlashCard", notes = "create a new FlashCard")
	public Response addFlashCard(@ApiParam(value = "request", required = true) NewFlashcardRequest request,
			@ApiParam(value = "boxid", required = true) @PathParam("boxid") int boxid) {
		try {
			request.validateRequest();
			Session session = PersistenceHelper.getSession();
			FlashCard fc = new FlashCard();
			fc.setFrontpage(request.getFrontpage());
			fc.setBackpage(request.getBackpage());
			Box box = PersistenceHelper.getById(boxid, Box.class);
			box.getFlashcards().add(fc);
			fc.setBox(box);

			session.beginTransaction();
			session.saveOrUpdate(fc);
			session.saveOrUpdate(box);
			session.getTransaction().commit();

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
	@Path("/{boxid}/{fc_id}")
	@ApiOperation(value = "getFlashCard", notes = "Fetches a FlashCard", response = FlashCard.class)
	public Response getFlashCard(@ApiParam(value = "box_id", required = true) @PathParam("boxid") int box_id,
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

	@DELETE
	@Path("/{boxid}/{fc_id}")
	@ApiOperation(value = "removeFlashCard", notes = "Removes a FlashCard")
	public Response deleteFlashCard(@ApiParam(value = "box_id", required = true) @PathParam("boxid") int box_id,
			@ApiParam(value = "fc_id", required = true) @PathParam("fc_id") int fc_id) {
		try {
			PersistenceHelper.openSession();
			FlashCard fc = PersistenceHelper.getById(fc_id, FlashCard.class);
			Box box = PersistenceHelper.getById(box_id, Box.class);
			box.getFlashcards().remove(fc);
			box.persist();
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
	@Path("/{boxid}/{fc_id}/comments")
	@ApiOperation(value = "getFlashcardComments", notes = "Get comments of a FlashCard", response = Comment.class, responseContainer = "List")
	public Response getComments(@ApiParam(value = "box_id", required = true) @PathParam("boxid") int box_id,
			@ApiParam(value = "fc_id", required = true) @PathParam("fc_id") int fc_id) {
		try {
			PersistenceHelper.openSession();
			List<Comment> comments = PersistenceHelper.getById(fc_id, FlashCard.class).getComments();
			return Response.ok(new Genson().serialize(comments)).build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new ServiceUnavailableException(response);
		} catch (ClientErrorException e) {
			return Response.status(e.getResponse().getStatusInfo()).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (Exception e) {
			// Something else went wrong
			Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new InternalServerErrorException(response);
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	@POST
	@Path("/{boxid}/{fc_id}/comments")
	@ApiOperation(value = "addFlashcardComment", notes = "Add comment to flashcard")
	public Response addComment(@ApiParam(value = "box_id", required = true) @PathParam("boxid") int box_id,
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
			tx.commit();
			return Response.ok().build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new ServiceUnavailableException(response);
		} catch (ClientErrorException e) {
			return Response.status(e.getResponse().getStatusInfo()).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (Exception e) {
			// Something else went wrong
			Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new InternalServerErrorException(response);
		} finally {
			PersistenceHelper.closeSession();
		}
	}

	@DELETE
	@Path("/{boxid}/{fc_id}/comments")
	@ApiOperation(value = "removeFlashcardComment", notes = "Remove comment from flashcard")
	public Response removeComment(@ApiParam(value = "box_id", required = true) @PathParam("box_id") int box_id,
			@ApiParam(value = "fc_id", required = true) @PathParam("fc_id") int fc_id,
			@ApiParam(value = "comment_id", required = true) @PathParam("comment_id") int comment_id) {
		try {
			Session session = PersistenceHelper.getSession();
			FlashCard fc = PersistenceHelper.getById(fc_id, FlashCard.class);
			Comment comment = PersistenceHelper.getById(comment_id, Comment.class);
			List<Comment> comments = fc.getComments();
			comments.remove(comment);
			fc.setComments(comments);

			Transaction tx = session.beginTransaction();
			session.delete(comment);
			session.saveOrUpdate(fc);
			tx.commit();

			return Response.ok().status(Response.Status.OK).build();
		} catch (HibernateException e) {
			// Something went wrong with the Database
			Response response = Response.status(Response.Status.SERVICE_UNAVAILABLE)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new ServiceUnavailableException(response);
		} catch (ClientErrorException e) {
			return Response.status(e.getResponse().getStatusInfo()).entity(new Genson().serialize(e.getMessage()))
					.build();
		} catch (Exception e) {
			// Something else went wrong
			Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity(new Genson().serialize(e.getMessage())).build();
			throw new InternalServerErrorException(response);
		} finally {
			PersistenceHelper.closeSession();
		}
	}
}