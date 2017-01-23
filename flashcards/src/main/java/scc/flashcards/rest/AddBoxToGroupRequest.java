package scc.flashcards.rest;

import javax.ws.rs.BadRequestException;

public class AddBoxToGroupRequest extends JsonRequest{

	private Long boxId;
	
	public AddBoxToGroupRequest() {
		super();
	}

	public long getBoxId() {
		return boxId;
	}

	public void setBoxId(long boxId) {
		this.boxId = boxId;
	}

	@Override
	public boolean validateRequest() {
		if(boxId == null){
			throw new BadRequestException("No Box ID given!");
		}
		return true;
	}
	
	
}
