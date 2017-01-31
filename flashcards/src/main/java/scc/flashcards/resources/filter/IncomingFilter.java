package scc.flashcards.resources.filter;

import java.io.IOException;

import javax.persistence.Persistence;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import scc.flashcards.persistence.PersistenceHelper;

@Provider
public class IncomingFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		PersistenceHelper.openSession();
	}
  
}