package scc.flashcards.rest;

import javax.ws.rs.BadRequestException;

import io.swagger.annotations.ApiModelProperty;

public class AddBoxToGroupRequest extends JsonRequest{

	@ApiModelProperty(required=true)
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
