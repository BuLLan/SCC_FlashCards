
package scc.flashcards.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.annotations.Api;

/**
 * Webservice Resource for testing Purposes
 */
@Path("/test")
@Api(value="/test", tags={"Test and Debug Service"})
@Produces(MediaType.TEXT_PLAIN)
public class TestResource {
	
	@POST
	@Path("/createDummyData")
	@Produces(MediaType.TEXT_PLAIN)
	public void createDummyData() {
		
	}
	
}
