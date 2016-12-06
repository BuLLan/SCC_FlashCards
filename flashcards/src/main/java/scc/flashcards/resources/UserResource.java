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

import java.sql.Connection;
import java.sql.DriverManager;
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
	@GET
	public boolean testDB(){
		boolean result = true;
		Connection conn = null;
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
		    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javabase","root","root");
		    // Do something with the Connection
		    conn.close();
		} catch (SQLException ex) {
		    // handle any errors
			result = false;
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	
		return result;
	}
	
	/**
	 * Gets alls users
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
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value="getUserByID",
		notes="Returns the user object which has the given ID")
	public User getUserById(
			@ApiParam(value="The ID of the user", required=true) @PathParam("userid") int userid){
		return new User();
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
