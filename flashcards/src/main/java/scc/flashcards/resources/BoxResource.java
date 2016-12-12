package scc.flashcards.resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import scc.flashcards.model.Box;
import scc.flashcards.model.User;
import scc.flashcards.repositories.BoxRepository;

import java.sql.SQLException;

/**
 * Webservice Resource for Box model
 */
@Path("/boxes")
@Api(value="Flashcards REST Service")
@Produces(MediaType.TEXT_PLAIN)
public class BoxResource {
	
	@Path("/testdb")
	@ApiOperation(value="testDbConnection", notes="Tests the MYSQL Database connection")
	@Produces({"application/json","application/xml"})
	@GET
	public Box testDB(){
		boolean result = true;
		BoxRepository boxRep = new BoxRepository();
		Box box = new Box();
		box.setTitle("test_box");
		box.setCategory_id(22);
		box.setSubcategory_id(24);
		
		/*
		 * Test DB Write
		 */
		try {
			boxRep.addBox(box);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		
		return box;
	}
	
	/**
	 * Gets all boxes
	 * @return a list of boxes
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value="getAllBoxes",
	notes="Returns a list of all boxes")
	public Collection<Box> getAllBoxes() {
		Collection<Box> allBoxes = new Vector<Box>();
		return allBoxes;
	}
	
	/**
	 * Adds a new box
	 * @return
	 */
	@POST
	public boolean addBox(){
		return false;
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
		User testuser = new User(13, "Max", "Mustermann", "MaMu", "test123");
		Collection<String> col = new ArrayList<String>();
		return new Box(22, "Softwaretechnologie", 21, 24, col, testuser);
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
		/**
		 * TODO: implement stub
		 */
		return true;
	}
	
	/**
	 * Updates a user
	 * 
	 * @param userid
	 * @return
	 */
	@Path("/{boxid}")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value="updateBox",
		notes="Returns true if user was updated")
	public boolean updatBox(
			@ApiParam(value="The ID of the box", required=true) @PathParam("boxid") int boxid){
		/**
		 * TODO: implement stub
		 */
		return true;
	}
	
	
	
}
