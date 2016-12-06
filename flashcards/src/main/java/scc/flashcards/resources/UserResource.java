package scc.flashcards.resources;

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
import scc.flashcards.model.User;
import scc.flashcards.repositories.UserRepository;

import java.sql.SQLException;

/**
 * Webservice Resource for User model
 */
@Path("/users")
@Api(value="Flashcards REST Service")
@Produces(MediaType.TEXT_PLAIN)
public class UserResource {
	
	@Path("/testdb")
	@ApiOperation(value="testDbConnection", notes="Tests the MYSQL Database connection")
	@Produces({"application/json","application/xml"})
	@GET
	public User testDB(){
		boolean result = true;
		UserRepository userRep = new UserRepository();
		User user = new User();
		user.setLogin("test_user");
		user.setPassword("test user Password");
		
		/*
		 * Test DB Write
		 */
		try {
			userRep.addUser(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		
		return user;
	}
	
	/**
	 * Gets all users
	 * @return a list of users
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value="getAllUsers",
	notes="Returns a list of all users")
	public Collection<User> getAllUsers() {
		Collection<User> allUsers = new Vector<User>();
		return allUsers;
	}
	
	/**
	 * Adds a new user
	 * @return
	 */
	@POST
	public boolean addUser(){
		return false;
	}
	
	
	/**
	 * Gets a user by id
	 * 
	 * @param userid
	 * @return
	 */
	@Path("/{userid}")
	@GET
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	@ApiOperation(value="getUserByID",
		notes="Returns the user object which has the given ID")
	public User getUserById(
			@ApiParam(value="The ID of the user", required=true) @PathParam("userid") int userid){
		return new User(15,"Peter", "Petersen", "peteboy69@yahoo.de","ultrageheimespassword123");
	}
	
	/**
	 * Deletes a user by id
	 * 
	 * @param userid
	 * @return
	 */
	@Path("/{userid}")
	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value="deleteUser",
		notes="Returns true if user was deleted")
	public boolean deleteUser(
			@ApiParam(value="The ID of the user", required=true) @PathParam("userid") int userid){
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
	@Path("/{userid}")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value="updateUser",
		notes="Returns true if user was updated")
	public boolean updateUser(
			@ApiParam(value="The ID of the user", required=true) @PathParam("userid") int userid){
		/**
		 * TODO: implement stub
		 */
		return true;
	}
	
	
	
}
