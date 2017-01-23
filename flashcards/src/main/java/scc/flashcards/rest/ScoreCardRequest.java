package scc.flashcards.rest;

import javax.ws.rs.BadRequestException;

public class ScoreCardRequest extends JsonRequest{

	private Long userId;
	
	private Long cardId;
	
	private Boolean isCorrect;
	
	public ScoreCardRequest() {
		
	}
	
	public boolean validateRequest() {
		if(this.userId == null) {
			throw new BadRequestException("Missing User ID");
		}
		if(this.cardId == null) {
			throw new BadRequestException("Missing Card ID");
		}
		if(this.isCorrect == null) {
			throw new BadRequestException("Missing isCorrect value");
		}
		return true;
	}
	
}
