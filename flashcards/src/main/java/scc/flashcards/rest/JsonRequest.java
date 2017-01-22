package scc.flashcards.rest;

import io.swagger.annotations.ApiModel;

@ApiModel(description="Base class for describing REST request objects")
public abstract class JsonRequest {

	public abstract boolean validateRequest();
	
}
