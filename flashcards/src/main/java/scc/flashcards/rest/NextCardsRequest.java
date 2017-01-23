package scc.flashcards.rest;

import io.swagger.annotations.ApiParam;

public class NextCardsRequest {

	@ApiParam(required=false, value="Number of cards to be fetched")
	private int number;
	
	
	public NextCardsRequest() {
		this.number = 20;
	}


	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}
	
	
}
