package scc.flashcards.resources.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import scc.flashcards.persistence.PersistenceHelper;

@Provider
public class CORSFilter implements ContainerResponseFilter {
   @Override
   public void filter(final ContainerRequestContext requestContext,
                      final ContainerResponseContext cres) throws IOException {
      cres.getHeaders().add("Access-Control-Allow-Origin", requestContext.getHeaderString("origin"));
      cres.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
      cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
      cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
      cres.getHeaders().add("Access-Control-Max-Age", "1209600");
      
      PersistenceHelper.filterCloseSession();
   }
}