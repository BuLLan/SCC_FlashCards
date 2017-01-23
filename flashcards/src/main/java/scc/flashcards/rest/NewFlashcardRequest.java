package scc.flashcards.rest;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import scc.flashcards.model.flashcards.Page;

public class NewFlashcardRequest extends JsonRequest {

	private Page frontpage;
	
	private Page backpage;
	
	public NewFlashcardRequest() {
		
	}
	
	public boolean validateRequest() {
		if(getFrontpage() == null){
			throw new BadRequestException(Response.status(Status.BAD_REQUEST).entity("No Frontpage defined").build());
		}
		if(getBackpage() == null){
			throw new BadRequestException(Response.status(Status.BAD_REQUEST).entity("No Backpage defined").build());
		}
		
		return true;
	}

	public Page getFrontpage() {
		return frontpage;
	}

	public void setFrontpage(Page frontpage) {
		this.frontpage = frontpage;
	}

	public Page getBackpage() {
		return backpage;
	}

	public void setBackpage(Page backpage) {
		this.backpage = backpage;
	}
	
}
