package scc.flashcards.rest;

import javax.ws.rs.BadRequestException;

import io.swagger.annotations.ApiParam;

public class ScoreCardRequest extends JsonRequest{
	
	@ApiParam(required=true)
	private Boolean isCorrect;
	
	@ApiParam(required=false, value="Defines the way the score is calculated",
			allowableValues="linear,flat_exponential,steep_exponential,advanced", defaultValue="linear")
	private String mode;
		
	public ScoreCardRequest() {
		
	}
	
	public boolean validateRequest() {
		if(this.isCorrect == null) {
			throw new BadRequestException("Missing isCorrect value");
		}
		return true;
	}

	public Boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
}
